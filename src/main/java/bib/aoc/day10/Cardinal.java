package bib.aoc.day10;

import bib.utils.Pair;
import lombok.*;

import java.util.Objects;
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Cardinal {
    N(-1,0),
    E(0,1),
    S(1,0),
    W(0,-1);

    private final int lineOffset;

    private final int columnOffset;


    public static CardinalFinder of(@NonNull Pair<Integer, Integer> previousPosition) {
        return new CardinalFinder(previousPosition);
    }

    public static PositionFinder findPositionFrom(@NonNull Pair<Integer, Integer> position) {
        return new PositionFinder(position);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CardinalFinder {

        private Pair<Integer, Integer> previousPosition;

        public Cardinal relativeTo(@NonNull Pair<Integer, Integer> position) {
            if (Objects.equals(position.first(), previousPosition.first())){
                return position.second()> previousPosition.second()?W:E;
            }else{
                return position.first()> previousPosition.first()?N:S;
            }

        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PositionFinder {
        private Pair<Integer, Integer> position;

        public Pair<Integer, Integer> goingTo(Cardinal cardinal) {
            return new Pair<>(position.first()+cardinal.lineOffset, position.second()+cardinal.columnOffset);
        }
    }
}
