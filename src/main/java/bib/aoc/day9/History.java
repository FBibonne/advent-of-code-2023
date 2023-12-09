package bib.aoc.day9;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@Slf4j
public record History(List<Integer> values) {

    public static History of(@NonNull String line) {
        return new History(stream(line.split(" ")).map(Integer::valueOf).toList());
    }

    public long extrapolateLast() {
        List<List<Integer>> reductions = computeReductions();
        return reductions.reversed().stream()
                .map(List::getLast)
                .mapToLong(Integer::longValue).sum();
    }

    private List<List<Integer>> computeReductions() {
        var reduction=values;
        List<List<Integer>> reductions=new ArrayList<>();
        reductions.add(values);
        while(!allEqualsSameValue(reduction)){
            try {
                reduction=reduce(reduction);
            } catch (IllegalStateException e) {
                log.error(STR."Reductions = \{reductions}",e);
                throw e;
            }
            reductions.add(reduction);
        }
        return reductions;
    }

    private List<Integer> reduce(List<Integer> reduction) {
        if (reduction.size()<2){
            throw new IllegalStateException("Impossible de réduire une liste de taille <2");
        }
        List<Integer> retour=new ArrayList<>(reduction.size()-1);
        for(int i=0;i<reduction.size()-1;i++){
            retour.add(reduction.get(i+1)-reduction.get(i));
        }
        return retour;
    }

    private boolean allEqualsSameValue(List<Integer> reduction) {
        return reduction.stream().skip(1).allMatch(reduction.getFirst()::equals);
    }

    public long extrapolateFirst() {
        List<List<Integer>> reductions = computeReductions();
        return reductions.reversed().stream()
                .map(List::getFirst)
                .mapToLong(Integer::longValue)
                .reduce((s,x)->x-s)
                .getAsLong();
    }
}
