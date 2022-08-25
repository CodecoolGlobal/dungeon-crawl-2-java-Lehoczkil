package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Coin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ItemTest {

    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void itemConstructorDoesNotThrow() {
        assertDoesNotThrow(() ->new Coin(map.getCell(1, 1)));
    }

    @Test
    void getCellReturnsCell() {
        Coin coin = new Coin(map.getCell(1, 1));
        assertEquals(coin.getCell(), map.getCell(1, 1) );
    }
}
