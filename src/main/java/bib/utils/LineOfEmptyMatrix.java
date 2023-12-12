package bib.utils;

import lombok.NonNull;

public class LineOfEmptyMatrix<T> extends Line<T>{
    private final int position;
    private final EmptyMatrice<T> matrix;


    public LineOfEmptyMatrix(int position, @NonNull EmptyMatrice<T> matrix) {
        super(position,null);
        this.position = position;
        this.matrix = matrix;
    }
}
