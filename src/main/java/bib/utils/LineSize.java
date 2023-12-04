package bib.utils;

public record LineSize(int size) {
    public static LineSize of(int size) {
        return new LineSize(size);
    }
}
