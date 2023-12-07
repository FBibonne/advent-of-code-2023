package bib.aoc.day7;

import lombok.NonNull;

import java.util.*;

public record HandJoker(String hand, TypeOfHand typeOfHand, int bid) implements Hand {

    public static HandJoker of(@NonNull String line ) {
        var parts = line.split(" ");
        return new HandJoker(
                parts[0],
                computeTypeOfHand(parts[0]),
                Integer.parseInt(parts[1])
        );
    }

    private static TypeOfHand computeTypeOfHand(String hand) {
        Map<String, Integer> cardCounts = new HashMap<>();
        Arrays.stream(hand.split("")).forEach(c -> cardCounts.merge(c, 1, Integer::sum));
        var jokerCount=cardCounts.getOrDefault("J", 0);
        cardCounts.remove("J");
        List<Integer> counts = new ArrayList<>(cardCounts.values());
        counts.sort(Integer::compareTo);
        return switch ((counts.isEmpty()?0:counts.getLast())+jokerCount) {
            case 5 -> TypeOfHand.FIVE;
            case 4 -> TypeOfHand.FOUR;
            case 3 -> counts.getFirst() == 2 ? TypeOfHand.FULL : TypeOfHand.THREE;
            case 2 -> counts.get(1) == 2 ? TypeOfHand.TWO_PAIRS : TypeOfHand.PAIR;
            default -> TypeOfHand.HIGH_CARD;
        };
    }

    private static final Map<String, String> convertChars = new HashMap<>();

    static {
        convertChars.put("J", "A");
        convertChars.put("2", "B");
        convertChars.put("3", "C");
        convertChars.put("4", "D");
        convertChars.put("5", "E");
        convertChars.put("6", "F");
        convertChars.put("7", "G");
        convertChars.put("8", "H");
        convertChars.put("9", "I");
        convertChars.put("T", "J");
        convertChars.put("Q", "K");
        convertChars.put("K", "L");
        convertChars.put("A", "M");
    }

    @Override
    public Map<String, String> convertChars() {
        return convertChars;
    }
}
