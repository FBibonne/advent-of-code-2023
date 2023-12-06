package bib.aoc;

import bib.aoc.day5.Mapper;
import bib.aoc.day5.Range;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.LongStream;

import static java.util.function.Predicate.not;

@Slf4j
public final class Day5 extends Puzzle {

    private Mapper seedToSoil = null;
    private Mapper soilToFertilizer = null;
    private Mapper fertilizerToWater = null;
    private Mapper waterToLight = null;
    private Mapper lightToTemperature = null;
    private Mapper temperatureToHumidity = null;
    private Mapper humidityToLocation = null;


    @Override
    protected PuzzlePart part1() {
        return lines -> {
            var linesIterator = lines.iterator();
            long[] allSeeds = findAllSeeds(linesIterator);
            initializeMappers(linesIterator);
            return String.valueOf(process(Arrays.stream(allSeeds)));
        };
    }

    private static long[] findAllSeeds(Iterator<String> linesIterator) {
        return Arrays.stream(linesIterator.next().split(":")[1].split(" "))
                .filter(not(String::isEmpty))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    private long process(LongStream seeds) {
        return seeds.parallel()
                .map(seedToSoil::map)
                .map(soilToFertilizer::map)
                .map(fertilizerToWater::map)
                .map(waterToLight::map)
                .map(lightToTemperature::map)
                .map(temperatureToHumidity::map)
                .map(humidityToLocation::map)
                .min().getAsLong();
    }

    private void initializeMappers(Iterator<String> linesIterator) {
        while (linesIterator.hasNext()) {
            var line = linesIterator.next();
            switch (line) {
                case "seed-to-soil map:":
                    seedToSoil = initializeMapper(linesIterator);
                    break;
                case "soil-to-fertilizer map:":
                    soilToFertilizer = initializeMapper(linesIterator);
                    break;
                case "fertilizer-to-water map:":
                    fertilizerToWater = initializeMapper(linesIterator);
                    break;
                case "water-to-light map:":
                    waterToLight = initializeMapper(linesIterator);
                    break;
                case "light-to-temperature map:":
                    lightToTemperature = initializeMapper(linesIterator);
                    break;
                case "temperature-to-humidity map:":
                    temperatureToHumidity = initializeMapper(linesIterator);
                    break;
                case "humidity-to-location map:":
                    humidityToLocation = initializeMapper(linesIterator);
                    break;
                default:
                    if (!line.isEmpty()) {
                        log.error("Ligne intraitable : {}", line);
                    }
            }
        }
    }

    private Mapper initializeMapper(Iterator<String> linesIterator) {

        List<Range> ranges = new LinkedList<>();
        while (linesIterator.hasNext()) {
            var line = linesIterator.next();
            if (line == null || line.isEmpty()) {
                return Mapper.of(ranges.toArray(Range[]::new));
            }
            ranges.add(Range.of(line));
        }
        return Mapper.of(ranges.toArray(Range[]::new));
    }

    @Override
    protected PuzzlePart part2() {
        return lines -> {
            var linesIterator = lines.iterator();
            List<Range> seedsRanges = findSeedsRange(linesIterator);
            initializeMappers(linesIterator);

            return String.valueOf(
                    seedsRanges.stream()
                            .mapToLong(range -> process(Arrays.stream(range.toArray())))
                            .min()
                            .getAsLong()
            );
        };
    }

    private static List<Range> findSeedsRange(Iterator<String> linesIterator) {
        List<Range> seedsRanges=new ArrayList<>();
        var numbers = Arrays.stream(linesIterator.next().split(":")[1].split(" "))
                .filter(not(String::isEmpty))
                .mapToLong(Long::parseLong)
                .iterator();
        while ((numbers.hasNext())) {
            var start = numbers.next();
            var length = numbers.next();
            seedsRanges.add(new Range(0, start, length));
        }
        return seedsRanges;
    }

}
