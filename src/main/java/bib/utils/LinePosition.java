package bib.utils;

public record LinePosition(int position) {
    public static LinePosition of(int position) {
        return new LinePosition(position);
    }
}
