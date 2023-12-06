package bib.aoc.day3;

import bib.utils.ColumnPosition;
import bib.utils.ColumnSize;
import bib.utils.LinePosition;
import bib.utils.LineSize;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static bib.utils.Matrice.*;
import static bib.utils.StringUtils.toStringArray;
import static java.lang.Integer.max;
import static java.lang.Math.abs;

public record Schematic(String[][] matrice, List<PartNumberCandidate> partNumberCandidates, List<GearCandidate> gears) {

    private static final Pattern symbolPattern=Pattern.compile("[^\\d\\.]");

    public static Schematic of(Stream<String> lines) {
        var lineNb = 0;
        var listOfLines = lines.toList();
        var nbLines = listOfLines.size();
        var nbColumns = listOfLines.getFirst().length();
        List<GearCandidate> gearCandidates=new ArrayList<>(3*nbLines);
        List<PartNumberCandidate> partNumberCandidates = new ArrayList<>(10 * nbLines);
        var matrice = newMatriceWithDim(LineSize.of(nbColumns), ColumnSize.of(nbLines));
        for (String line : listOfLines) {
            gearCandidates.addAll(GearCandidate.of(line, lineNb));
            inMatrice(matrice).fillLine(lineNb).with(toStringArray(line));
            partNumberCandidates.addAll(PartNumberCandidate.of(line, lineNb));
            lineNb++;
        }
        ((ArrayList<?>) partNumberCandidates).trimToSize();
        ((ArrayList<?>) gearCandidates).trimToSize();
        return new Schematic(matrice, partNumberCandidates, gearCandidates);
    }



    public IntStream partNumbers() {
        return partNumberCandidates.stream()
                .filter(this::isPartNumber)
                .mapToInt(PartNumberCandidate::value);
    }

    private boolean isPartNumber(PartNumberCandidate partNumberCandidate) {
        for (int c = partNumberCandidate.start(); c <= partNumberCandidate.end(); c++) {
            if (isPointAdjacentWithSymbol(partNumberCandidate.lineNb(), c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPointAdjacentWithSymbol(int line, int column) {
        return findAdjacentPointsOf(matrice).at(LinePosition.of(line), ColumnPosition.of(column))
                .streamValuesWithoutOrigin()
                .anyMatch(this::isSymbol);
    }

    boolean isSymbol(String symbol) {
        return symbolPattern.matcher(symbol).matches();
    }

    public IntStream gearRatios() {
        return gears.stream()
                .mapToInt(this::gearRatioOrZero);
    }

    private int gearRatioOrZero(GearCandidate gearCandidate) {
        var adjacentPartNumbers=partNumberCandidates.stream()
                .filter(gearCandidate::isAdjacentWith)
                .toList();
        return adjacentPartNumbers.size()==2
                ?adjacentPartNumbers.stream().mapToInt(PartNumberCandidate::value).reduce((a,b)->a*b).getAsInt()
                :0;
    }

    public static boolean areAdjacents(int line1, int column1, int line2, int column2) {
        return max(abs(line1-line2),abs(column1-column2))<=1;
    }
}
