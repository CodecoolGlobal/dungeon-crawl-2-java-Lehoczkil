package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Door;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {

    GameMap map;

    @BeforeEach
    void setMap() {
        map = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void throwsExceptionForOutOfRangeCoordinates() {
        assertThrows(IndexOutOfBoundsException.class, () -> map.getCell(4, 0));
    }

    @Test
    void returnsCorrectCellFromCells() {
        assertEquals(0, map.getCell(0, 0).getX());
        assertEquals(0, map.getCell(0, 0).getY());
    }

    @Test
    void returnsNullIfHasNoPlayer() {
        assertNull(map.getPlayer());
    }

    @Test
    void returnsCorrectPlayerObject() {
        Player player = new Player(map.getCell(0, 0));
        map.setPlayer(player);
        assertEquals(player, map.getPlayer());
    }

    @Test
    void returnsCorrectSizeValues() {
        assertEquals(3, map.getHeight());
        assertEquals(3, map.getWidth());
    }

    @Test
    void collectEnemiesFromCells() {
        Cell cell1 = map.getCell(1, 1);
        Cell cell2 = map.getCell(2, 2);
        Skeleton skeleton = new Skeleton(cell1);
        Ghost ghost = new Ghost(cell2);
        cell1.setActor(skeleton);
        cell2.setActor(ghost);
        List<Enemy> enemies = map.getEnemies();

        assertEquals(skeleton, enemies.get(0));
        assertEquals(ghost, enemies.get(1));
    }

    @Test
    void findsAllEnemiesOnCells() {
        Cell cell1 = map.getCell(1, 1);
        Cell cell2 = map.getCell(2, 2);
        Skeleton skeleton = new Skeleton(cell1);
        Ghost ghost = new Ghost(cell2);
        cell1.setActor(skeleton);
        cell2.setActor(ghost);
        List<Enemy> enemies = map.getEnemies();

        assertEquals(2, enemies.size());
    }

    @Test
    void levelIsOverIfPlayerIsOnOpenDoor() {
        Cell doorCell = map.getCell(1, 0);
        Door door = new Door(doorCell);
        Player player = new Player(doorCell);
        door.setOpen();
        doorCell.setItem(door);

        assertTrue(map.isLevelOver());
    }
}
