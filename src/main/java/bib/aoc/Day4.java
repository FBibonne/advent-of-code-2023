package bib.aoc;

import bib.aoc.day4.Card;

public final class Day4 extends Puzzle {


    @Override
    protected PuzzlePart part1() {
        return lines -> String.valueOf(lines.map(Card::of)
                .mapToInt(Card::points)
                .sum()
        );
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> {
            var cards=lines.map(Card::of).sorted().toList();
            int sum=0;
            for (Card card:cards){
                card.computeNumberOfMatchesAndUpdateCopies(cards);
                sum+=card.getNbInstances();
            }
            return String.valueOf(sum);
        };
    }
}
