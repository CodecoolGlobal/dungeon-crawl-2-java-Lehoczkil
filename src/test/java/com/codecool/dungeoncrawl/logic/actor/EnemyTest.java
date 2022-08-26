package com.codecool.dungeoncrawl.logic.actor;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    GameMap gameMap = new GameMap(3,3, CellType.FLOOR);

    @Test
    void creatingEnemyInstanceDoesNotThrowException() {
        assertDoesNotThrow(() -> new Enemy(gameMap.getCell(2,1)));
    }

    @Test
    void enemyReturnsCorrectName() {
        Enemy enemy = new Enemy(gameMap.getCell(2,1));
        assertEquals("enemy", enemy.getTileName());
    }

    @Test
    void enemyCanAttackOtherActors() {
        Enemy enemy = new Enemy(gameMap.getCell(2,1));
        Player player = new Player(gameMap.getCell(1,1));
        enemy.attack(player);
        assertEquals(8,player.getHealth());
    }

}
