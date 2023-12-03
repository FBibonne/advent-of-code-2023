package bib.aoc.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchematicTest {

    @Test
    void isSymbol_test() {
        String[][] matrice={
                {"#","1"},
                {".","@"}
        };
        var schematic=new Schematic(matrice,2,2,null, null);
        assertTrue(schematic.isSymbol(0,0));
        assertFalse(schematic.isSymbol(0,1));
        assertTrue(schematic.isSymbol(1,1));
        assertFalse(schematic.isSymbol(1,0));
    }
}