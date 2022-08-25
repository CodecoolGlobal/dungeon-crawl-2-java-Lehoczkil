package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.HealPotion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealPotionTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getTileNameReturnsHeal() {
        HealPotion hp = new HealPotion(map.getCell(1, 1));
        assertEquals(hp.getTileName(), "heal");
    }

    @Test
    void getHealingPowerReturns3() {
        HealPotion hp = new HealPotion(map.getCell(1, 1));
        assertEquals(hp.getHealingPower(), 3);
    }
}
