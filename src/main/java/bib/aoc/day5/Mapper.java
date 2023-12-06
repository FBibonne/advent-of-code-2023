package bib.aoc.day5;

import lombok.NonNull;

import java.util.Arrays;

public record Mapper(Range[] orderedRanges, long maxBound) {

    public static Mapper of(@NonNull Range[] ranges){
        //Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
        Arrays.sort(ranges, (r1, r2) -> Long.signum(r1.sourceStart()==r2.sourceStart()?r2.length()-r1.length():r1.sourceStart()-r2.sourceStart()));
        return new Mapper(ranges, ranges[ranges.length-1].length()+ranges[ranges.length-1].sourceStart()-1);
    }

    public long map(long l) {
        if (l>maxBound){
            return l;
        }
        for(Range range : orderedRanges){
            if(l< range.sourceStart()){
                return l;
            }else if (range.contains(l)){
                return range.map(l);
            }
        }
        throw new IllegalStateException(STR."\{l} n'appartient à aucun rang du Mapper mais est <= borneSup : \{this}");
    }
}
