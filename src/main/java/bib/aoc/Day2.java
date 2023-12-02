package bib.aoc;

import bib.aoc.day2.Game;

public final class Day2 extends Puzzle{


    @Override
    protected PuzzlePart part1() {
        return lines -> String.valueOf(lines.map(Game::of)
                .filter(Game::isPermited)
                .mapToInt(Game::id)
                .sum());
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> String.valueOf(lines.map(Game::of)
                .mapToInt(Game::power)
                .sum()
        );
    }
}
