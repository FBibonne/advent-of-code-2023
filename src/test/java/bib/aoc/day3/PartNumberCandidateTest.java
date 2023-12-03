package bib.aoc.day3;

import org.junit.jupiter.api.Test;

class PartNumberCandidateTest {

    @Test
    void of_test() {
        var string = """
7.....*.969.349..354.....................156.666....@......10........-378..-......9.........632....*..........363*........./......976.871...1
        """;
        System.out.println(PartNumberCandidate.of(string, 111));
    }
}