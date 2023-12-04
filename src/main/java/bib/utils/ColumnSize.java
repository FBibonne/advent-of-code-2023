package bib.utils;

public record ColumnSize(int size) {
    public static ColumnSize of(int size) {
        return new ColumnSize(size);
    }
}
