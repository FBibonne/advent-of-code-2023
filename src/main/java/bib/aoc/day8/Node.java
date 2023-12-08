package bib.aoc.day8;

import java.util.regex.Pattern;

public record Node(String id, String left, String right) {
    private static final Pattern nodePattern=Pattern.compile("(?<id>[A-Z]{3}) = \\((?<left>[A-Z]{3}), (?<right>[A-Z]{3})\\)");

    public static Node of(String line) {
        var matcher=nodePattern.matcher(line);
        matcher.matches();
        return new Node(matcher.group("id"), matcher.group("left"), matcher.group("right") );
    }
}
