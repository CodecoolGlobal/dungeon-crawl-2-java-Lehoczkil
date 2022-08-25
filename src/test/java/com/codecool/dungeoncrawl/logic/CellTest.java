package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Coin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        Cell finalCell = cell;
        assertThrows(IndexOutOfBoundsException.class, () -> finalCell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        Cell finalCell1 = cell;
        assertThrows(IndexOutOfBoundsException.class, () -> finalCell1.getNeighbor(0, 1));
    }

    @Test
    void returnsCorrectGameMapObject() {
        Cell cell = map.getCell(1, 1);
        assertEquals(map, cell.getGameMap());
    }

    @Test
    void returnsCorrectCellTypeName() {
        Cell cell = map.getCell(1, 1);
        assertEquals("floor", cell.getTileName());
    }

    @Test
    void returnsNullIfHasNoActor() {
        Cell cell = map.getCell(1, 1);
        assertNull(cell.getActor());
    }

    @Test
    void returnsNotNullIfHasActor() {
        Cell cell = map.getCell(1, 1);
        cell.setActor(new Player(cell));
        assertNotNull(cell.getActor());
    }

    @Test
    void returnsNullIfHasNoItem() {
        Cell cell = map.getCell(1, 1);
        assertNull(cell.getItem());
    }

    @Test
    void returnsNotNullIfHasItem() {
        Cell cell = map.getCell(1, 1);
        cell.setItem(new Coin(cell));
        assertNotNull(cell.getItem());
    }

}