package bib.aoc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@Slf4j
public final class Day9 extends Puzzle {

    @Override
    protected PuzzlePart part1() {
        return lines -> String.valueOf(
                lines.map(Day9::toHistory)
                        .map(Day9::computeReductions)
                        .mapToLong(Day9::extrapolateLast)
                        .sum()
        );
    }

    private static List<Integer> toHistory(String line) {
        return stream(line.split(" ")).map(Integer::valueOf).toList();
    }

    public static long extrapolateLast(List<List<Integer>> reductions) {
        return reductions.reversed().stream()
                .map(List::getLast)
                .mapToLong(Integer::longValue)
                .sum();
    }

    private static List<List<Integer>> computeReductions(List<Integer> values) {
        var reduction = values;
        List<List<Integer>> reductions = new ArrayList<>();
        reductions.add(values);
        while (!allEqualsSameValue(reduction)) {
            reduction = doReductionAndSave(reduction, reductions);
        }
        return reductions;
    }

    private static List<Integer> doReductionAndSave(List<Integer> reduction, List<List<Integer>> reductions) {
        try {
            reduction = reduce(reduction);
        } catch (IllegalStateException e) {
            log.error(STR."Reductions = \{reductions}", e);
            throw e;
        }
        reductions.add(reduction);
        return reduction;
    }

    private static List<Integer> reduce(List<Integer> reduction) {
        checkSizeOfReduction(reduction);
        List<Integer> retour = new ArrayList<>(reduction.size() - 1);
        for (int i = 0; i < reduction.size() - 1; i++) {
            retour.add(reduction.get(i + 1) - reduction.get(i));
        }
        return retour;
    }

    private static void checkSizeOfReduction(List<Integer> reduction) {
        if (reduction.size() < 2) {
            throw new IllegalStateException("Impossible de réduire une liste de taille < 2");
        }
    }

    private static boolean allEqualsSameValue(List<Integer> reduction) {
        return reduction.stream().skip(1).allMatch(reduction.getFirst()::equals);
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> String.valueOf(
                lines.map(Day9::toHistory)
                        .map(Day9::computeReductions)
                        .mapToLong(Day9::extrapolateFirst)
                        .sum());
    }

    private static long extrapolateFirst(List<List<Integer>> reductions) {
        return reductions.reversed().stream()
                .map(List::getFirst)
                .mapToLong(Integer::longValue)
                .reduce((s,x)->x-s)
                .getAsLong();
    }


}
