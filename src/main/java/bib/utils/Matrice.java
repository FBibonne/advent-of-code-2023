package bib.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Matrice<T> {

    private final List<List<T>> lines;
    private Collection<Line<T>> allLines;
    private Collection<Column<T>> allColumns;


    private final T empty;

    public Matrice(@NonNull Stream<String> stringLines, @NonNull Function<String, T> toT, T empty) {
        this(stringLines.toList(), toT, empty);
    }

    public Matrice(@NonNull List<String> listLines, @NonNull Function<String, T> toT){
        this(listLines, toT, null);
    }

    public Matrice(@NonNull List<String> listLines, @NonNull Function<String, T> toT, T empty) {
        lines = new ArrayList<>();
        for (String line : listLines) {
            List<T> matriceLine = new ArrayList<>();
            var elements = line.split("");
            for (String element : elements) {
                matriceLine.add(toT.apply(element));
            }
            lines.add(matriceLine);
        }
        this.empty=empty;

    }


    public static String[][] newMatriceWithDim(@NonNull LineSize lineSize, @NonNull ColumnSize columnSize) {
        return new String[lineSize.size()][columnSize.size()];
    }

    public static MatriceSetter inMatrice(@NonNull String[][] matrice) {
        return new MatriceSetter(matrice);
    }

    public static AdjacentFinder findAdjacentPointsOf(String[][] matrice) {
        return new AdjacentFinder(matrice);
    }

    private Collection<Line<T>> allLines() {
        if (allLines == null) {
            this.allLines = new ArrayList<>();
            var i = 0;
            for (var line : lines) {
                allLines.add(new Line<>(i, new ArrayList<>(line)));
                i++;
            }
        }
        return allLines;
    }

    private void addLine(Line<T> newLine, int times, int position) {
        if (allLines==null) allLines();
        if (allColumns==null) allColumns();
        for (int nb=1; nb<=times; nb++ ){
            lines.add(position, new ArrayList<>(newLine.getVector()));
            addVectorInVectorsCollection(new Line<>(position, newLine.getVector()), allLines);
            addVectorInTransposedVectorsCollection(newLine.getVector(), position, allColumns);
        }
    }

    private void addColumn(Column<T> column, int times, int position) {
        if (allLines==null) allLines();
        if (allColumns==null) allColumns();
        for (int nb=1; nb<=times; nb++ ){
            var i=0;
            for (var line : lines) {
                line.add(position, column.get(i));
                i++;
            }
            addVectorInVectorsCollection(new Column<>(position, column.getVector()), allColumns);
            addVectorInTransposedVectorsCollection(column.getVector(), position, allLines);
        }
    }

    public Collection<Column<T>> allColumns() {

        if (allColumns == null) {
            this.allColumns = new ArrayList<>();
            for (int j = 0; j < lines.getFirst().size(); j++) {
                allColumns.add(new Column<>(j, copyColumn(j)));
            }
        }
        return allColumns;
    }

    private List<T> copyColumn(int j) {
        List<T> retour = new ArrayList<>();
        for(var line:lines){
            retour.add(line.get(j));
        }
        return retour;
    }

    private <U extends VectorWithPosition<T, U>> void addVectorInVectorsCollection(U added, Collection<U> targets) {
        int position = added.getPosition();
        for (U target : targets) {
            if (target.getPosition() >= position) {
                target.increasePosition();
            }
        }
        targets.add(added.copy());
    }

    private <U extends VectorWithPosition<T, U>> void addVectorInTransposedVectorsCollection(List<T> added, int position, Collection<U> targets) {
        for (U target : targets) {
            target.getVector().add(position, added.get(target.getPosition()));
        }
    }


    public List<Point<T>> findAllPoints(Predicate<T> finder) {

        List<Point<T>> retour = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).size(); j++) {
                if (finder.test(lines.get(i).get(j))) {
                    retour.add(new Point<>(lines.get(i).get(j), i, j));
                }
            }
        }
        return retour;
    }

    @Override
    public String toString() {
        return lines.stream().map(this::lineToString).reduce((a, b) -> STR."\{a}\n\{b}").orElse("");
    }

    private String lineToString(List<T> points) {
        return points.stream().map(T::toString).collect(Collectors.joining());
    }

    public List<? extends Line<T>>  allEmptyLines() {
        List<Line<T>> retour=new ArrayList<>();
        for(var line : allLines()){
            if (isEmpty(line)){
                retour.add(line);
            }
        }
        return retour;
    }

    public List<? extends Column<T>> allEmptyColumns() {
        List<Column<T>> retour=new ArrayList<>();
         for(Column<T> column : allColumns()){
            if (isEmpty(column)){
                retour.add(column);
            }
        }
         return retour;
    }

    private boolean isEmpty(VectorWithPosition<T, ?> vector) {
        return vector.getVector().stream().allMatch(empty::equals);
    }

    public void addEmptyLine(int times, int position) {
        addLine(emptyLine().copy(), times, position);
    }

    private Line<T> emptyLine() {
        return new Line<>(0,IntStream.range(0, lines.getFirst().size()).mapToObj(i ->this.empty).toList());
    }

    private Column<T> emptyColumn() {
        return new Column<>(0, IntStream.range(0, lines.size()).mapToObj(i->this.empty).toList());
    }

    public void addEmptyColumn(int times, int position) {
        addColumn(emptyColumn().copy(), times, position);
    }



    @RequiredArgsConstructor
    public static class AdjacentFinder {

        private final String[][] matrice;
        private LinePosition linePosition;
        private ColumnPosition columnPosition;

        public AdjacentFinder at(LinePosition linePosition, ColumnPosition columnPosition) {
            this.linePosition = linePosition;
            this.columnPosition = columnPosition;
            return this;
        }

        public Stream<String> streamValuesWithoutOrigin() {

            var nbLines = matrice.length;
            var nbColumns = matrice[0].length;
            List<String> values = new ArrayList<>(8);
            for (int i = max(0, linePosition.position() - 1);
                 i <= min(linePosition.position() + 1, nbLines - 1);
                 i++) {

                for (int j = max(0, columnPosition.position() - 1);
                     j <= min(columnPosition.position() + 1, nbColumns - 1);
                     j++) {
                    if (i != linePosition.position() || j != columnPosition.position())
                        values.add(matrice[i][j]);
                }
            }
            return values.stream();
        }
    }

    @RequiredArgsConstructor
    public static class MatriceSetter {

        private final String[][] matrice;
        private int line;

        public MatriceSetter fillLine(int lineNb) {
            this.line = lineNb;
            return this;
        }

        public void with(@NonNull String[] values) {
            this.matrice[line] = values;
        }

    }
}
