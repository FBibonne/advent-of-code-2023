package bib.aoc.day3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static bib.aoc.day3.Schematic.areAdjacents;

public record GearCandidate(int line, int column) {


    private static final Pattern gearPattern = Pattern.compile("\\*");

    public static Collection<GearCandidate> of(String line, int lineNb) {
        var gearMatcher=gearPattern.matcher(line);
        List<GearCandidate> retour=new ArrayList<>();
        while(gearMatcher.find()){
            retour.add(new GearCandidate(lineNb, gearMatcher.start()));
        }
        return retour;
    }

    public boolean isAdjacentWith(PartNumberCandidate partNumberCandidate) {
        return areAdjacents(line,column,partNumberCandidate.lineNb(),partNumberCandidate.start())
                || areAdjacents(line,column,partNumberCandidate.lineNb(),partNumberCandidate.end());
    }

}
