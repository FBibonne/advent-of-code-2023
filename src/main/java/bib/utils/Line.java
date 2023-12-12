package bib.utils;

import java.util.ArrayList;
import java.util.List;

public class Line<T> extends VectorWithPosition<T, Line<T>> {

    public Line(int position, List<T> vector) {
        super(position, vector);
    }

    @Override
    protected Line<T> newInstance(int position, ArrayList<T> ts) {
        return new Line<>(position, ts);
    }
}
