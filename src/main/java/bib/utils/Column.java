package bib.utils;

import java.util.ArrayList;
import java.util.List;

public class Column<T> extends VectorWithPosition<T, Column<T>> {

    public Column(int position, List<T> vector) {
        super(position, vector);
    }

    @Override
    protected Column<T> newInstance(int position, ArrayList<T> ts) {
        return new Column<>(position, ts);
    }
}
