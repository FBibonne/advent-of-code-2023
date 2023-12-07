package bib.aoc;

import bib.aoc.day7.Hand;
import bib.aoc.day7.HandJoker;
import bib.aoc.day7.HandSimple;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public final class Day7 extends Puzzle {

    @Override
    protected PuzzlePart part1() {
        return totalWinnings(HandSimple::of);
    }



    @Override
    protected PuzzlePart part2() {
        return totalWinnings(HandJoker::of);
    }

    private <T extends Hand> PuzzlePart totalWinnings(Function<String, T> lineToHand) {
        return lines -> {
            var hands = lines.map(lineToHand).sorted().toList();
            var result = 0L;
            var i = 1;
            for (T hand : hands) {
                result += (long) i * hand.bid();
                i++;
            }
            return String.valueOf(result);
        };
    }

}
