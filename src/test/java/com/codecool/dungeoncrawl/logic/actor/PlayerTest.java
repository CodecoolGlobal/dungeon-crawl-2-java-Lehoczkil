package com.codecool.dungeoncrawl.logic.actor;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Coin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    GameMap map;
    Player player;

    @BeforeEach
    void initializePlayer() {
        map = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(map.getCell(0, 0));
    }

    @Test
    void playerIsOnItemReturnsTrue() {
        Cell testCell = map.getCell(0, 0);
        Coin coin = new Coin(testCell);
        testCell.setItem(coin);
        testCell.setActor(player);

        assertTrue(player.isOnItem());
    }

    @Test
    void canAttackEnemy() {
        Skeleton skeleton = new Skeleton(map.getCell(1, 0));
        player.attack(skeleton);
        assertEquals(7, skeleton.getHealth());
    }

    @Test
    void moveOnFreeCellReturnsTrue() {
        assertTrue(player.move(1, 0));
    }

}
