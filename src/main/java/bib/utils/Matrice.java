package bib.utils;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Matrice {

    public static String[][] newMatriceWithDim(@NonNull LineSize lineSize, @NonNull ColumnSize columnSize){
        return new String[lineSize.size()][columnSize.size()];
    }

    public static MatriceSetter inMatrice(@NonNull String[][] matrice){
        return new MatriceSetter(matrice);
    }

    public static AdjacentFinder findAdjacentPointsOf(String[][] matrice){
        return new AdjacentFinder(matrice);
    }

    @RequiredArgsConstructor
    public static class AdjacentFinder{

        private final String[][] matrice;
        private LinePosition linePosition;
        private ColumnPosition columnPosition;

        public AdjacentFinder at(LinePosition linePosition, ColumnPosition columnPosition) {
            this.linePosition=linePosition;
            this.columnPosition=columnPosition;
            return this;
        }

        public Stream<String> streamValuesWithoutOrigin(){

            var nbLines=matrice.length;
            var nbColumns=matrice[0].length;
            List<String> values=new ArrayList<>(8);
            for (int i = max(0, linePosition.position() - 1);
                 i <= min(linePosition.position() + 1, nbLines - 1);
                 i++) {

                for (int j = max(0, columnPosition.position() - 1);
                     j <= min(columnPosition.position() + 1, nbColumns - 1);
                     j++) {
                    if (i!=linePosition.position() || j!=columnPosition.position())
                        values.add(matrice[i][j]);
                    }
                }
            return values.stream();
        }
    }

    @RequiredArgsConstructor
    public static class MatriceSetter {

        private final String[][] matrice;
        private int line;

        public MatriceSetter fillLine(int lineNb){
            this.line=lineNb;
            return this;
        }

        public void with(@NonNull String[] values){
            this.matrice[line]=values;
        }

    }
}
