package bib;

import bib.aoc.Puzzle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

@Slf4j
public class AdventOfCode {

    public static void main(String[] args) throws IOException, URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        solveDay(5);
    }

    private static void solveDay(int d) throws IOException, URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Puzzle dayd=Puzzle.newInstance(d);
        var result=dayd.solve();
        log.info(STR."Day \{d}\n\tPart 1 : \{result.first()}\n\tPart 2 : \{result.second()}");
    }


}
