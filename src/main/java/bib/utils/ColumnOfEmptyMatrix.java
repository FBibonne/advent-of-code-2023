package bib.utils;

public class ColumnOfEmptyMatrix<T> extends Column<T>{

    private final int position;
    private final EmptyMatrice<T> matrix;
    public ColumnOfEmptyMatrix(int position, EmptyMatrice<T> emptyMatrice) {
        super(position,null);
        this.position = position;
        this.matrix = emptyMatrice;
    }
}
