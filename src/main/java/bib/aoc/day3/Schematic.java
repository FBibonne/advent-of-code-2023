package bib.aoc.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static bib.utils.StringUtils.toStringArray;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.abs;

public record Schematic(String[][] matrice, int nbLines, int nbColumns, List<PartNumberCandidate> partNumberCandidates, List<GearCandidate> gears) {

    private static final Pattern symbolPattern=Pattern.compile("[^\\d\\.]");

    public static Schematic of(Stream<String> lines) {
        var lineNb = 0;
        var listOfLines = lines.toList();
        var nbLines = listOfLines.size();
        var nbColumns = listOfLines.getFirst().length();
        List<GearCandidate> gearCandidates=new ArrayList<>(3*nbLines);
        List<PartNumberCandidate> partNumberCandidates = new ArrayList<>(10 * nbLines);
        String[][] matrice = new String[nbColumns][nbLines];
        for (String line : listOfLines) {
            gearCandidates.addAll(GearCandidate.of(line, lineNb));
            matrice[lineNb] = toStringArray(line);
            partNumberCandidates.addAll(PartNumberCandidate.of(line, lineNb));
            lineNb++;
        }
        ((ArrayList<?>) partNumberCandidates).trimToSize();
        ((ArrayList<?>) gearCandidates).trimToSize();
        return new Schematic(matrice, nbLines, nbColumns, partNumberCandidates, gearCandidates);
    }



    public IntStream partNumbers() {
        return partNumberCandidates.stream()
                .filter(this::isPartNumber)
                .mapToInt(PartNumberCandidate::value);
    }

    private boolean isPartNumber(PartNumberCandidate partNumberCandidate) {
        for (int c = partNumberCandidate.start(); c <= partNumberCandidate.end(); c++) {
            if (isAdjacent(partNumberCandidate.lineNb(), c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdjacent(int line, int column) {
        for (int i = max(0, line - 1); i <= min(line + 1, nbLines - 1); i++) {
            for (int j = max(0, column - 1); j <= min(column + 1, nbColumns - 1); j++) {
                if (isSymbol(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isSymbol(int i, int j) {
        return symbolPattern.matcher(matrice[i][j]).matches();
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
