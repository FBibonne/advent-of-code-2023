package bib.aoc.day10;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import static bib.aoc.day10.Cardinal.*;
import static java.util.Arrays.stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Tile {
    NORTH_SOUTH("|", N, S),
    EAST_WEST("-", E, W),
    NORTH_EAST("L", N, E),
    NORTH_WEST("J", N, W),
    SOUTH_WEST("7", S, W),
    SOUTH_EAST("F", S, E),
    NO_PIPE("." ,null, null),
    START("S", null, null);

    @NonNull
    private final String symbol;
    private final Cardinal cardinal1;
    private final Cardinal cardinal2;

    public static Tile[] arrayFromLine(@NonNull String line) {
        return stream(line.split("")).map(Tile::ofSymbol).toArray(Tile[]::new);
    }

    public static Tile ofSymbol(@NonNull String symbol) {
        return switch (symbol) {
            case "|" -> NORTH_SOUTH;
            case "-" -> EAST_WEST;
            case "L" -> NORTH_EAST;
            case "J" -> NORTH_WEST;
            case "7" -> SOUTH_WEST;
            case "F" -> SOUTH_EAST;
            case "." -> NO_PIPE;
            case "S" -> START;
            default -> throw new IllegalArgumentException(symbol);
        };
    }

    public Cardinal findNextCardinalFrom(@NonNull Cardinal cardinal) {
        return cardinal1==cardinal?cardinal2:cardinal1;
    }

    public boolean hasCardinal(Cardinal cardinalOfStart) {
        return cardinal1==cardinalOfStart || cardinal2==cardinalOfStart;
    }
}
