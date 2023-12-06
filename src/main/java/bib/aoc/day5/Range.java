package bib.aoc.day5;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public record Range(long destinationStart, long sourceStart, long length, long exclusiveSourceEnd, long offset) {

    private static Pattern rangePattern = Pattern.compile("(?<destinationStart>\\d+) (?<sourceStart>\\d+) (?<length>\\d+)");

    public Range(long destinationStart, long start, long length) {
        this(destinationStart, start, length, start + length, destinationStart - start);
    }

    public static Range of(@NonNull String line) {
        var matcher = rangePattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(STR."Impossible de convertir en Range : \{line}");
        }
        long destinationStart = Long.parseLong(matcher.group("destinationStart"));
        long sourceStart = Long.parseLong(matcher.group("sourceStart"));
        long length = Long.parseLong(matcher.group("length"));
        return new Range(destinationStart, sourceStart, length);
    }

    public boolean contains(long l) {
        return sourceStart <= l && l < exclusiveSourceEnd;
    }

    public long map(long l) {
        return offset + l;
    }

    public long[] toArray() {
        log.info("Process {}", this);
        long[] retour = new long[Math.toIntExact(length)];
        for (long i = 0; i < length; i++) {
            retour[(int) i] = i + sourceStart;
        }
        return retour;
    }
}
