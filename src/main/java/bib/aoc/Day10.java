package bib.aoc;

import bib.aoc.day10.Cardinal;
import bib.aoc.day10.Line;
import bib.aoc.day10.Point;
import bib.aoc.day10.Tile;
import bib.utils.Pair;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Predicate;

import static bib.aoc.day10.Tile.NO_PIPE;
import static bib.aoc.day10.Tile.START;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.stream;

@Slf4j
public final class Day10 extends Puzzle {

    private int xSize;
    private int ySize;

    @Override
    protected PuzzlePart part1() {
        return lines -> {
            var linesList=lines.toList();
            xSize=linesList.size();
            ySize=linesList.get(0).length();
            var sketch = new Tile[xSize][];
            var i = 0;
            for (String line :linesList ) {
                sketch[i] = Tile.arrayFromLine(line);
                i++;
            }
            Pair<Integer, Integer> start = findStart(sketch);
            var steps = 1;
            var position = findPositionAfter(start, sketch);
            Pair<Integer, Integer> previousPosition = start;
            while (!position.equals(start)) {
                var newPosition = nextPosition(previousPosition, position, sketch);
                previousPosition = position;
                position = newPosition;
                steps++;
            }

            return String.valueOf(steps / 2);
        };
    }

    private Pair<Integer, Integer> findPositionAfter(@NonNull Pair<Integer, Integer> start, Tile[][] sketch) {
        for (Cardinal cardinal : Cardinal.values()) {
            var next = Cardinal.findPositionFrom(start).goingTo(cardinal);
            var cardinalOfStart = Cardinal.of(start).relativeTo(next);
            if (tileAt(sketch, next).map(t->t.hasCardinal(cardinalOfStart)).orElse(false)) {
                return next;
            }
        }
        throw new IllegalStateException("No position after start found");

    }

    private Point nextPosition(Point previousPosition, Point position, Point[][] sketch) {
        Cardinal cardinalOfPreviousPosition = Cardinal.of(previousPosition.getPosition()).relativeTo(position.getPosition());
        Cardinal cardinalOfNextPosition = tileAt(sketch, position.getPosition()).get().getTile().findNextCardinalFrom(cardinalOfPreviousPosition);
        return tileAt(sketch, Cardinal.findPositionFrom(position.getPosition()).goingTo(cardinalOfNextPosition)).get();
    }

    private Pair<Integer, Integer> nextPosition(Pair<Integer, Integer> previousPosition, Pair<Integer, Integer> position, Tile[][] sketch) {
        Cardinal cardinalOfPreviousPosition = Cardinal.of(previousPosition).relativeTo(position);
        Cardinal cardinalOfNextPosition = tileAt(sketch, position).get().findNextCardinalFrom(cardinalOfPreviousPosition);
        return Cardinal.findPositionFrom(position).goingTo(cardinalOfNextPosition);
    }

    private <T> Optional<T> tileAt(T[][] sketch, Pair<Integer, Integer> position) {
        return 0<=position.first()&& position.first()<xSize
        &&0<=position.second() && position.second()< ySize ? Optional.of(sketch[position.first()][position.second()])
                :Optional.empty();
    }

    private Pair<Integer, Integer> findStart(Tile[][] sketch) {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (sketch[i][j] == START) return new Pair<>(i, j);
            }
        }
        throw new IllegalStateException("No start found");
    }


    @Override
    protected PuzzlePart part2() {
        return lines -> {
            var linesList=lines.toList();
            xSize=linesList.size();
            ySize=linesList.get(0).length();
            var sketch = new Point[xSize][];
            var i = 0;
            for (String line : linesList) {
                sketch[i] = Point.arrayFromLine(line, i);
                i++;
            }
            Point start = findStart(sketch);
            Set<Line> limits = new HashSet<>();
            List<Point> connectedPipes=new ArrayList<>();
            connectedPipes.add(start);
            var position = findPositionAfter(start, sketch);
            Point previousPosition = start;
            limits.add(new Line(previousPosition, position));
            while (!position.equals(start)) {
                connectedPipes.add(position);
                var newPosition = nextPosition(previousPosition, position, sketch);
                previousPosition = position;
                position = newPosition;
                var newLimit=new Line(previousPosition, position);
                if (limits.contains(newLimit)){
                    throw new IllegalStateException(STR."\{newLimit} déjà existante");
                }
                limits.add(newLimit);
            }
            transformNotConnectedPipes(sketch, connectedPipes);
            computeEnclosedForAllNonPipe(sketch, limits);
            inferEnclosedForAllNonPipe(sketch);

            return String.valueOf(stream(sketch).flatMap(Arrays::stream).filter(Point::isEnclosed).count());
        };
    }

    private void transformNotConnectedPipes(Point[][] sketch, List<Point> connectedPipes) {
        for(Point[] points:sketch){
            for (Point point:points){
                if(!connectedPipes.contains(point)){
                    point.setTile(NO_PIPE);
                }
            }
        }

    }

    private void inferEnclosedForAllNonPipe(Point[][] sketch) {
        int count;
        do{
            count=0;
            for (Point[] points : sketch) {
                for (Point point : points) {
                    if (point.isEnclosed()) {
                        count += encloseAround(point, sketch);
                    }
                }
            }
        }while(count>0);
    }

    private int encloseAround(Point point, Point[][] sketch) {

        int count=0;
        for(int i = max(0,point.getX()-1); i<=min(point.getX()+1, xSize-1); i++){
            for(int j = max(0,point.getY()-1); j<=min(point.getY()+1, ySize-1); j++){
                if (i!=point.getX() && j!=point.getY() && sketch[i][j].isCandidateEnclosed() && !sketch[i][j].isEnclosed() ){
                    count++;
                    sketch[i][j].setEnclosed(Optional.of(true));
                }
            }
        }
        return count;

    }

    private void computeEnclosedForAllNonPipe(Point[][] sketch, Set<Line> limits) {
        stream(sketch).flatMap(Arrays::stream)
                .filter(Point::isCandidateEnclosed)
                .forEach(p -> {
                    if (isEnclosedByLimits(p, limits)){
                        p.setEnclosed(Optional.of(true));
                    }
                });
    }

    private boolean isEnclosedByLimits(Point p, Set<Line> limits) {
        return p.northLines().stream().allMatch(line -> check(limits, line::isHorizontalMoreThan))
                && p.southLines().stream().allMatch(line -> check(limits, line::isHorizontalLessThan))
                && p.eastLines().stream().allMatch(line -> check(limits, line::isVerticalLessThan))
                && p.westLines().stream().allMatch(line -> check(limits, line::isVerticalMoreThan));
    }

    private boolean check(Set<Line> limits, Predicate<Line> lineTest) {
        return limits.stream().filter( lineTest)
                .count() % 2 == 1;
    }


    private Point findStart(Point[][] sketch) {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (sketch[i][j].getTile() == START) return sketch[i][j];
            }
        }
        throw new IllegalStateException("No start found");

    }

    private Point findPositionAfter(@NonNull Point start, Point[][] sketch) {
        for (Cardinal cardinal : Cardinal.values()) {
            var next = Cardinal.findPositionFrom(start.getPosition()).goingTo(cardinal);
            var cardinalOfStart = Cardinal.of(start.getPosition()).relativeTo(next);
            if (tileAt(sketch, next).map(t->t.getTile().hasCardinal(cardinalOfStart)).orElse(false)) {
                return tileAt(sketch, next).get();
            }
        }
        throw new IllegalStateException("No position after start found");

    }


}
