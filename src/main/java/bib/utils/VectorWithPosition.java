package bib.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public abstract class VectorWithPosition<T, U extends VectorWithPosition<T, U>> {

    @Setter
    private int position;

    private final List<T> vector;

    public T get(int j) {
        return  getVector().get(j);
    }

    public boolean allMatch(Predicate<T> matcher) {
        return getVector().stream().allMatch(matcher);
    }

    public void increasePosition() {
        this.position=this.position+1;
    }

    public U copy() {
        return newInstance(this.position, new ArrayList<>(this.vector));
    }

    protected abstract U newInstance(int position, ArrayList<T> ts);
}
