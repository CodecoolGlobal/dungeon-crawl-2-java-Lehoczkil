package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Coin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getTileNameReturnsCoin() {
        Coin coin = new Coin(map.getCell(1, 1));
        assertEquals("coin", coin.getTileName());
    }

}
