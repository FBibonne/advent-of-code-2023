package bib.aoc;

import bib.utils.Matrice;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static java.util.function.Predicate.not;

@Slf4j
public final class Day6 extends Puzzle {

    @Override
    protected PuzzlePart part1() {
        return lines -> {
            var allLines = lines.toList();
            int[] times = toArray(allLines.get(0));
            int[] distances = toArray(allLines.get(1));
            long result = 1;
            for (int i = 0; i < times.length; i++) {
                var borneInf = times[i] / 2.0d - Math.sqrt(Math.pow(times[i] / 2.0d, 2) - distances[i]);
                var borneSup = times[i] / 2.0d + Math.sqrt(Math.pow(times[i] / 2.0d, 2) - distances[i]);
                var diff = boundSupToInt(borneSup) - boundInfToInt(borneInf)+1;
                log.info("Process {}, time = {}, distance = {} => [{} ; {}] => {}", i, times[i], distances[i], borneInf, borneSup, diff);
                result = result * diff;
            }
            return String.valueOf(result);
        };
    }

    private static long boundSupToInt(double borneSup) {
        var floored=Math.floor(borneSup);
        if (floored==borneSup){
            return (long) floored-1;
        }
        return (long) floored;
    }

    private static long boundInfToInt(double borneInf) {
        var ceiled=Math.ceil(borneInf);
        if (ceiled==borneInf){
            return (long) ceiled+1;
        }
        return (long) ceiled;
    }

    private static int[] toArray(String line) {
        return Arrays.stream(line.split(":")[1].split(" "))
                .filter(not(String::isEmpty))
                .mapToInt(Integer::parseInt)
                .toArray();
    }


    @Override
    protected PuzzlePart part2() {
        return lines -> {
            var allLines = lines.toList();
            var time=Long.parseLong(allLines.get(0).split(":")[1].replace(" ",""));
            var distance=Long.parseLong(allLines.get(1).split(":")[1].replace(" ",""));
            var borneInf = time / 2.0d - Math.sqrt(Math.pow(time / 2.0d, 2) - distance);
            var borneSup = time / 2.0d + Math.sqrt(Math.pow(time / 2.0d, 2) - distance);
            var diff = boundSupToInt(borneSup) - boundInfToInt(borneInf)+1;
            log.info("Process time = {}, distance = {} => [{} ; {}] => {}",time, distance, borneInf, borneSup, diff);
            return String.valueOf(diff);
        };
    }

}
