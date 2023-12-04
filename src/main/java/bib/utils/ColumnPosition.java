package bib.utils;

public record ColumnPosition(int position) {
    public static ColumnPosition of(int position) {
        return new ColumnPosition(position);
    }
}
