package bib.aoc.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchematicTest {

    @Test
    void isSymbol_test() {
        var schematic=new Schematic(null,null, null);
        assertTrue(schematic.isSymbol("#"));
        assertFalse(schematic.isSymbol("1"));
        assertTrue(schematic.isSymbol("@"));
        assertFalse(schematic.isSymbol("."));
    }
}