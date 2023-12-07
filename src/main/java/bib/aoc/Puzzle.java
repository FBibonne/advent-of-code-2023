package bib.aoc;

import bib.utils.Pair;
import bib.utils.PathToFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;

public sealed abstract class Puzzle permits Day1, Day2, Day3, Day4, Day5, Day6 {
    private static final Class<? extends Puzzle>[] puzzlesClass = (Class<? extends Puzzle>[]) Puzzle.class.getPermittedSubclasses();

    private int d;

    public static Puzzle newInstance(int d) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var simpleClassName=STR."Day\{d}";
        Puzzle instance= (Puzzle) Arrays.stream(puzzlesClass)
                .filter(c->simpleClassName.equals(c.getSimpleName()))
                .findAny()
                .get()
                .getConstructors()[0]
                .newInstance();
        instance.d=d;
        return instance;
    }

    public Pair<String, String> solve(PathToFile pathToFile) throws IOException, URISyntaxException {
        return new Pair<>(part1().solve(pathToFile), part2().solve(pathToFile));
    }

    protected abstract PuzzlePart part1();

    protected abstract PuzzlePart part2();

    public Pair<String, String> solve() throws IOException, URISyntaxException {
        return solve(new PathToFile(STR."input\{d}.txt"));
    }

}
