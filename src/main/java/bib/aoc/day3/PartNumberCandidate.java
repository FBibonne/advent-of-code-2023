package bib.aoc.day3;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public record PartNumberCandidate(int value, int lineNb, int start, int end) {
    private static final Pattern numberPattern=Pattern.compile("\\d+");

    public static List<PartNumberCandidate> of(@NonNull String line, int lineNb) {
        var numberMatcher=numberPattern.matcher(line);
        List<PartNumberCandidate> retour=new ArrayList<>();
        while(numberMatcher.find()){
            retour.add(new PartNumberCandidate(Integer.parseInt(numberMatcher.group()), lineNb, numberMatcher.start(), numberMatcher.end()-1));
        }
        return retour;
    }
}
