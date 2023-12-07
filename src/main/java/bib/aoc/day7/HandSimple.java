package bib.aoc.day7;

import lombok.NonNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public record HandSimple(String hand, TypeOfHand typeOfHand, int bid) implements Hand {

    public static HandSimple of(@NonNull String line ) {
        var parts = line.split(" ");
        return new HandSimple(
                parts[0],
                computeTypeOfHand(parts[0]),
                Integer.parseInt(parts[1])
        );
    }

    private static final Map<String, String> convertChars = new HashMap<>();

    static {
        convertChars.put("2", "A");
        convertChars.put("3", "B");
        convertChars.put("4", "C");
        convertChars.put("5", "D");
        convertChars.put("6", "E");
        convertChars.put("7", "F");
        convertChars.put("8", "G");
        convertChars.put("9", "H");
        convertChars.put("T", "I");
        convertChars.put("J", "J");
        convertChars.put("Q", "K");
        convertChars.put("K", "L");
        convertChars.put("A", "M");
    }


    private static TypeOfHand computeTypeOfHand(String hand) {
        Map<String, Integer> cardCounts = new HashMap<>();
        Arrays.stream(hand.split("")).forEach(c -> cardCounts.merge(c, 1, Integer::sum));
        List<Integer> counts = new ArrayList<>(cardCounts.values());
        counts.sort(Integer::compareTo);
        return switch (counts.getLast()) {
            case 5 -> TypeOfHand.FIVE;
            case 4 -> TypeOfHand.FOUR;
            case 3 -> counts.getFirst() == 2 ? TypeOfHand.FULL : TypeOfHand.THREE;
            case 2 -> counts.get(1) == 2 ? TypeOfHand.TWO_PAIRS : TypeOfHand.PAIR;
            default -> TypeOfHand.HIGH_CARD;
        };
    }

    @Override
    public Map<String, String> convertChars() {
        return convertChars;
    }
}
