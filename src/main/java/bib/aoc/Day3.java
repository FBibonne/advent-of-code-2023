package bib.aoc;

import bib.aoc.day3.Schematic;

public final class Day3 extends Puzzle{


    @Override
    protected PuzzlePart part1() {
        return lines -> String.valueOf(Schematic.of(lines)
                .partNumbers()
                .sum()
        );
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> String.valueOf(Schematic.of(lines)
                .gearRatios()
                .sum()
        );
    }
}
