package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class HealPotion extends Item{

    private int healingPower;
    public HealPotion(Cell cell) {
        super(cell);
        healingPower = 3;
    }

    @Override
    public String getTileName() {
        return "heal";
    }

    public int getHealingPower() {
        return healingPower;
    }
}
