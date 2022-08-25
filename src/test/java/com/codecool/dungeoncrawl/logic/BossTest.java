package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Boss;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BossTest {

    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void bossReturnsItsCorrectName() {
        Boss boss = new Boss(gameMap.getCell(2,1));
        assertEquals("boss", boss.getTileName());
    }

    @Test
    void bossCanAttackPlayer() {
        Boss boss = new Boss(gameMap.getCell(2,1));
        Player player = new Player(gameMap.getCell(1,1));
        boss.attack(player);
        assertEquals(4, player.getHealth());
    }

    @Test
    void creatingBossInstanceDoesNotThrowException() {
        assertDoesNotThrow(() -> new Boss(gameMap.getCell(2,1)));
    }

}
