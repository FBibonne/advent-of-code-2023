package bib.aoc;

import bib.utils.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static bib.utils.StringUtils.findAllWithOverLap;

@Slf4j
public final class Day1 extends Puzzle{

    private static final Set<String> digits = Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private static final Pattern ciffersAndLetters = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight|nine");

    private static final Map<String, String> numbers = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9");


    private static int toDigit(String digit) {
        return Integer.parseInt(numbers.getOrDefault(digit, digit));
    }

    private Pair<String, String> findFirstAndLastDigit(String line) {
        var matches = findAllWithOverLap(line, ciffersAndLetters);
        log.debug(STR."\{line} -> \{matches} -> ");
        return new Pair<>(matches.getFirst(), matches.getLast());
    }


    private int convertToCalibrationValue(String s) {
        var ints = s.chars().mapToObj(this::charToString)
                .filter(this::isDigit)
                .mapToInt(Integer::valueOf)
                .toArray();
        log.debug(String.valueOf(findFirst(ints) * 10 + findLast(ints)));
        return findFirst(ints) * 10 + findLast(ints);
    }

    private boolean isDigit(String s) {
        return digits.contains(s);
    }

    private String charToString(int charint) {
        return String.valueOf((char) charint);
    }

    private static int findLast(int[] ints) {
        return ints[ints.length - 1];
    }

    private static int findFirst(int[] ints) {
        return ints[0];
    }


    @Override
    protected PuzzlePart part1() {
        return lines -> String.valueOf(
                    lines.mapToInt(this::convertToCalibrationValue)
                    .sum()
        );
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> String.valueOf(lines.map(this::findFirstAndLastDigit)
                .mapToInt(pair -> toDigit(pair.first()) * 10 + toDigit(pair.second()))
                .sum());
    }
}
