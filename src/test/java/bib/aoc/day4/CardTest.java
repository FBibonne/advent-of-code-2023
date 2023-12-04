package bib.aoc.day4;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void of_test() {
        var input="Card 126:  8 86 94 98  6 55 69 73 36 19 | 34 95 80 32 84 69 73 47 98 56 92  8 50 26  6 94  4 86 21  7 78 85 55 82 19";
        var expectedWinningw= Set.of(8, 86, 94, 98, 6, 55, 69, 73, 36, 19);
        var expectedYours= List.of(34, 95, 80, 32, 84, 69, 73, 47, 98, 56, 92, 8, 50, 26, 6, 94, 4, 86, 21, 7, 78, 85, 55, 82, 19);
        var card=Card.of(input);
        assertEquals(expectedWinningw, card.getWinningNumbers());
        assertEquals(expectedYours, card.getNumbersYouHave());
        assertEquals(126, card.getId());
    }
}