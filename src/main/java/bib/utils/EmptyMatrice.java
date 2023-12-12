package bib.utils;

import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class EmptyMatrice<T> extends Matrice<T> {

    private static final String EMPTY_SYMBOL = ".";
    /**
     * Map< line_index, Map< column_index, Point<T>>>
     */
    private Map<Integer, Map<Integer,T>> cells;
    private int nbRows;
    private int nbCols;
    public EmptyMatrice(@NonNull Stream<String> lines, @NonNull Function<String, T> toT, @NonNull Predicate<T> isEmpty) {
        super(List.of(), toT);
        var listOfLines=lines.toList();
        nbRows=listOfLines.size();
        nbCols=listOfLines.getFirst().length();
        cells=new HashMap<>();
        int i=0;
        for(String line:listOfLines){
            var newLine=toCells(line,i,toT,isEmpty);
            if(!newLine.isEmpty()){
                cells.put(i,newLine);
            }
            i++;
        }

    }

    private Map<Integer, T> toCells(String line, int i, Function<String, T> toT, Predicate<T> isEmpty) {
        var j=0;
        Map<Integer, T> retour=new HashMap<>();
        for(String symbol:line.split("")){
            T value=toT.apply(symbol);
            if(! isEmpty.test(value)){
                retour.put(j,value);
            }
            j++;
        }
        return retour;
    }

    @Override
    public List<? extends Line<T>> allEmptyLines() {
        return IntStream.range(0, nbRows).filter(i->!cells.containsKey(i))
                .mapToObj(i->new LineOfEmptyMatrix<>(i, this))
                .toList();
    }

    @Override
    public List<? extends Column<T>> allEmptyColumns() {
        Set<Integer> notEmptyCols=cells.values().stream()
                .flatMap(v->v.keySet().stream())
                .collect(Collectors.toSet());
        return IntStream.range(0, nbCols).filter(i->!notEmptyCols.contains(i))
                .mapToObj(i->new ColumnOfEmptyMatrix<>(i, this))
                .toList();
    }

    @Override
    public void addEmptyLine(int times, int position) {
        Map<Integer, Map<Integer,T>> newCells=new HashMap<>();
        for(int i:cells.keySet()){
            if (i>=position){
                newCells.put(i+times,cells.get(i));
            }else{
                newCells.put(i,cells.get(i));
            }
        }
        nbRows+=times;
        cells=newCells;
    }

    @Override
    public void addEmptyColumn(int times, int position) {
        for(var line:cells.values()){
            addEmptyColumn(line, times, position);
        }
        nbCols+=times;
    }

    private void addEmptyColumn(Map<Integer,T> line, int times, int position) {
        List<Integer> positionsToUpdate=line.keySet().stream().filter(k->k>=position).toList();
        for(int i:positionsToUpdate){
            line.put(i+times,line.remove(i));
        }
    }

    @Override
    public List<Point<T>> findAllPoints(Predicate<T> finder) {
        return cells.entrySet().stream()
                .flatMap(lineEntry->lineEntry.getValue().entrySet().stream().map(cellEntry->new Point<>(cellEntry.getValue(),lineEntry.getKey(), cellEntry.getKey())))
                .filter(p->finder.test(p.t()))
                .toList();
    }


    @Override
    public String toString() {
        return IntStream.range(0,nbRows)
                .mapToObj(i->toString(cells.get(i)))
                .reduce((a,b)-> STR."\{a}\n\{b}")
                .orElse("");
    }

    private String toString(Map<Integer, T> lineOfCells) {
        if(lineOfCells==null){
            return emptyLine(nbCols);
        }
        return IntStream.range(0,nbCols)
                .mapToObj(j->ofNullable(lineOfCells.get(j)).map(T::toString).orElse(EMPTY_SYMBOL))
                .collect(Collectors.joining());
    }

    private String emptyLine(int nbCols) {
        return new StringBuilder().repeat(EMPTY_SYMBOL,nbCols).toString();
    }
}
