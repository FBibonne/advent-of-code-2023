package bib.aoc;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    void part2() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Day10 day10 = (Day10) Puzzle.newInstance(10);
        var lines = """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L
                """;
        assertEquals("10", day10.part2().solve(lines.lines()));

    }
}