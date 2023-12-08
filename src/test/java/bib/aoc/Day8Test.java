package bib.aoc;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {

    @Test
    void part2() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Day8 day8= (Day8) Puzzle.newInstance(8);
        var lines= """
                LR
                                
                DDA = (DDB, XXX)
                DDB = (XXX, DDZ)
                DDZ = (DDB, XXX)
                EEA = (EEB, XXX)
                EEB = (EEC, EEC)
                EEC = (EEZ, EEZ)
                EEZ = (EEB, EEB)
                XXX = (XXX, XXX)
                """;
        assertEquals("6",day8.part2().solve(lines.lines()));
    }
}