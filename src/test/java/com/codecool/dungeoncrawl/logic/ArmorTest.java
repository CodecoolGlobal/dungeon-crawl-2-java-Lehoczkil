package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Armor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmorTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getTileNameReturnsArmor() {
        Armor armor = new Armor(map.getCell(1, 1));
        assertEquals("armor", armor.getTileName());
    }
}
