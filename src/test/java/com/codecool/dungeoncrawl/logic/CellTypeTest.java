package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTypeTest {

    @Test
    void returnsCorrectStringForName() {
        CellType floor = CellType.FLOOR;
        assertEquals("floor", floor.getTileName());

        CellType wall = CellType.WALL;
        assertEquals("wall", wall.getTileName());

        CellType empty = CellType.EMPTY;
        assertEquals("empty", empty.getTileName());
    }
}
