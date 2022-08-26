package com.codecool.dungeoncrawl.logic.actor;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2,1));
        assertThrows(IndexOutOfBoundsException.class, () -> player.move(1,0));
    }

    @Test
    void playerIsDeadIfHPisZero() {
        Player player = new Player(gameMap.getCell(2,1));
        player.takeDamage(10);
        assertTrue(player.isDead());
    }

    @Test
    void playerTakesDamageWithTakeDamageMethod() {
        Player player = new Player(gameMap.getCell(2,1));
        player.takeDamage(5);
        assertEquals(5, player.getHealth());
    }

    @Test
    void playerCanShowItsHealthWithGetter() {
        Player player = new Player(gameMap.getCell(2,1));
        assertEquals(10, player.getHealth());
    }

    @Test
    void playerCanShowItsXWithGetter() {
        Player player = new Player(gameMap.getCell(2,1));
        assertEquals(2, player.getX());
    }

    @Test
    void playerCanShowItsYWithGetter() {
        Player player = new Player(gameMap.getCell(2,1));
        assertEquals(1, player.getY());
    }

    @Test
    void actorCanAttackOtherActors() {
        Player player = new Player(gameMap.getCell(2,1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(1,1));
        player.attack(skeleton);
        assertEquals(7, skeleton.getHealth());
    }

    @Test
    void playerCanShowItsCellWithGetter() {
        Cell expectedCell = new Cell(gameMap, 2,1, CellType.FLOOR);
        Player player = new Player(expectedCell);
        assertEquals(expectedCell, player.getCell());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }
}