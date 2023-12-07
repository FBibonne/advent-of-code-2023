package bib.aoc.day7;

import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Hand extends Comparable<Hand> {
    String hand();

    TypeOfHand typeOfHand();

    int bid();
    
    default int compareTo(Hand o) {
        var result = typeOfHand().ordinal() - o.typeOfHand().ordinal();
        if (result == 0) {
            result = toComparableString().compareTo(o.toComparableString());
        }
        return result;
    }

    default String toComparableString(){
        return Arrays.stream(hand().split("")).map(convertChars()::get).collect(Collectors.joining());
    }

    Map<String, String> convertChars();



}
