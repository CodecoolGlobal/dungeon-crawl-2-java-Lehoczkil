package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Door;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void setOpenSetsIsOpenTrue() {
        Door door = new Door(map.getCell(1, 1));
        door.setOpen();
        assertTrue(door::isOpen);
    }

    @Test
    void getTileNameReturnsOpenDoorIfIsOpenIsTrue() {
        Door door = new Door(map.getCell(1, 1));
        door.setOpen();
        assertEquals("open door", door.getTileName());
    }
}