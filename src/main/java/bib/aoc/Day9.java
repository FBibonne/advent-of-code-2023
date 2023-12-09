package bib.aoc;

import bib.aoc.day9.History;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Day9 extends Puzzle {

    @Override
    protected PuzzlePart part1() {
        return lines -> String.valueOf(lines.map(History::of)
                .mapToLong(History::extrapolateLast)
                .sum());
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> String.valueOf(lines.map(History::of)
                .mapToLong(History::extrapolateFirst)
                .sum());
    }


}
