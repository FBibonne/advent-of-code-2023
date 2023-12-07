package bib.aoc;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {


    @Test
    void part1Test() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Day6 day6= (Day6) Puzzle.newInstance(6);
        assertEquals("288",day6.part1().solve("""
                Time:      7  15   30
                Distance:  9  40  200
                """.lines()));
    }
}