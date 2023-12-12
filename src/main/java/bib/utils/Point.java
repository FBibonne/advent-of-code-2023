package bib.utils;

import static java.lang.Math.abs;

public record Point<T>(T t, int x, int y) {
    public long manhattanDistanceTo(Point<T> point) {
        return ((long)abs(x- point.x))+abs(y-point.y);
    }
}
