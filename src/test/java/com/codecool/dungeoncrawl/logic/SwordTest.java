package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwordTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getTileNameReturnsSword() {
        Sword sword = new Sword(map.getCell(1, 1));
        assertEquals(sword.getTileName(), "sword");
    }
}
