package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameStateTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);
    long millis = System.currentTimeMillis();
    Date date = new Date(millis);
    GameState gameState = new GameState(map, date, new PlayerModel(new Player(map.getCell(1, 1))), 1);


    @Test
    void getSavedAtReturnsDate() {
        assertEquals(date, gameState.getSavedAt());
    }

    @Test
    void getCurrentMapReturnsByteArray() {
        assertTrue(gameState.getCurrentMap().getClass().equals(byte[].class));
    }
}
