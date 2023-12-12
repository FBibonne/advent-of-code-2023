package bib.aoc;

import bib.aoc.day11.Pixel;
import bib.utils.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class Day11 extends Puzzle {


    @Override
    protected PuzzlePart part1() {
        return lines -> {
            Matrice<Pixel> universe=new Matrice<>(lines, Pixel::of,Pixel.EMPTY);
            log.debug("\n{}", universe);
            universe=expandAllEmptyLines(universe, 2);
            log.debug("\n{}", universe);
            universe=expandAllEmptyColumns(universe, 2);
            log.debug("\n{}", universe);
            List<Point<Pixel>> galaxies=universe.findAllPoints(Pixel.GALAXY::equals);
            log.debug("\n{}", universe);
            log.debug("nb galaxies : {}", galaxies.size());
            return String.valueOf(computeAllDistances(galaxies).stream()
                    .mapToLong(Long::valueOf)
                    .sum()
            );
        };
    }

    private List<Long> computeAllDistances(List<Point<Pixel>> galaxies) {
        List<Long>  retour=new ArrayList<>();
        for(int i=0;i<galaxies.size();i++){
            for(int j =i+1; j<galaxies.size();j++){
                retour.add(galaxies.get(i).manhattanDistanceTo(galaxies.get(j)));
            }
        }
        return retour;
    }

    private Matrice<Pixel> expandAllEmptyColumns(Matrice<Pixel> universe, int times) {
        List<Integer> positionsForAdd= universe.allEmptyColumns().stream()
                .map(Column::getPosition)
                .sorted().toList();
        var offset=0;
        for(int i:positionsForAdd){
            universe.addEmptyColumn(times-1, i+offset);
            offset+=times-1;
        }
        return universe;
    }
    private Matrice<Pixel> expandAllEmptyLines(Matrice<Pixel> universe, int times) {
        List<Integer> positionsForAdd= universe.allEmptyLines().stream()
                .map(Line::getPosition)
                .sorted().toList();
        var offset=0;
        for(int i:positionsForAdd){
            universe.addEmptyLine(times-1, i+offset);
            offset+=times-1;
        }
        return universe;
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> {
            Matrice<Pixel> universe=new EmptyMatrice<>(lines, Pixel::of, Pixel.EMPTY::equals);
            log.debug("BEGINNING\n{}", universe);
            universe=expandAllEmptyLines(universe, 1_000_000);
            log.debug("AFTER LINES\n{}", universe);
            universe=expandAllEmptyColumns(universe,1_000_000) ;
            log.debug("AFTER COLUMNS\n{}", universe);
            List<Point<Pixel>> galaxies=universe.findAllPoints(Pixel.GALAXY::equals);
            log.debug("nb galaxies : {}", galaxies.size());
            return String.valueOf(computeAllDistances(galaxies).stream()
                    .mapToLong(Long::valueOf)
                    .sum()
            );
        };
    }


}
