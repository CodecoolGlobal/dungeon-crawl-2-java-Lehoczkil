package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlayerModelTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);
    PlayerModel model = new PlayerModel(new Player(map.getCell(1,1)));

    @Test
    void getPlayerNameReturnsName() {
        assertNull(model.getPlayerName());
    }

    @Test
    void getHpReturns10() {
        assertEquals(10, model.getHp());
    }

    @Test
    void getXReturns1() {
        assertEquals(1, model.getX());
    }

    @Test
    void getYReturns1() {
        assertEquals(1, model.getY());
    }
}
