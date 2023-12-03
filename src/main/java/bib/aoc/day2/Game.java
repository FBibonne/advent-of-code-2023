package bib.aoc.day2;

import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public record Game(int id, List<Handfull> handfulls) {

    public static Game of(@NonNull String line) {
        var parts = line.split(":");
        var id = parseInt(parts[0].substring(5));
        var handfullSources = parts[1].split(";");
        return new Game(id, Arrays.stream(handfullSources)
                .map(Handfull::of)
                .toList());
    }

    public boolean isPermited() {
        return handfulls.stream().allMatch(Handfull::isPermitted);
    }

    public int power(){
        var maxRed=handfulls().stream().mapToInt(Handfull::red).max().getAsInt();
        var maxGreen=handfulls().stream().mapToInt(Handfull::green).max().getAsInt();
        var maxBlue=handfulls().stream().mapToInt(Handfull::blue).max().getAsInt();
        return maxBlue*maxGreen*maxRed;
    }
}
