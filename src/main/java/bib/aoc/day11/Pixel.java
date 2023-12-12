package bib.aoc.day11;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Pixel {

    EMPTY("."), GALAXY("#");

    private final String symbol;

    @Override
    public String toString() {
        return  symbol ;
    }

    public static Pixel of(@NonNull String element) {
        return switch (element){
            case "." -> EMPTY;
            case "#"->GALAXY;
            default -> throw new IllegalArgumentException(STR."Pas de pixel pour \{element}");
        };
    }
}
