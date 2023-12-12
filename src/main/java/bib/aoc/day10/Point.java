package bib.aoc.day10;

import bib.utils.Pair;
import lombok.*;

import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.stream;

@Getter
@EqualsAndHashCode(of={"x", "y"})
public class Point {

    private final int x;
    private final int y;
    @Setter
    private Tile tile;
    @Setter
    private Optional<Boolean> enclosed=Optional.empty();

    public Point(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public static Point[] arrayFromLine(@NonNull String line, int i) {
        var points=new Point[line.length()];
        var j=0;
        for(String symbol:line.split("")){
            points[j]=new Point(i,j,Tile.ofSymbol(symbol));
            j++;
        }
        return points;
    }

    public Pair<Integer, Integer> getPosition() {
        return new Pair<>(x,y);
    }

    public boolean isEnclosed() {
        return enclosed.orElse(false);
    }

    public boolean isCandidateEnclosed() {
        return tile==Tile.NO_PIPE;
    }

    public Set<Line> horizontal(int xLine) {
        return Set.of(new Line(xLine,y-1,xLine,y), new Line(xLine, y, xLine, y+1));
    }

    public Set<Line> northLines() {
        return horizontal(x-1);
    }

    public Set<Line> southLines() {
        return horizontal(x+1);
    }

    public Set<Line> vertical(int yLine) {
        return Set.of(new Line(x-1,yLine,x,yLine), new Line(x, yLine, x+1, yLine));
    }

    public Set<Line> eastLines() {
        return vertical(y+1);
    }

    public Set<Line> westLines() {
        return vertical(y-1);
    }
}
