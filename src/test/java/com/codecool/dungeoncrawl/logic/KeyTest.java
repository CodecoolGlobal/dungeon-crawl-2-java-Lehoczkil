package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getTileNameReturnsKey() {
        Key key = new Key(map.getCell(1, 1));
        assertEquals(key.getTileName(), "key");
    }

}
