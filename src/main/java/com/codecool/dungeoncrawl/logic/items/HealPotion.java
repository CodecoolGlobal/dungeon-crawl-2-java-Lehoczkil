package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class HealPotion extends Item{
    public HealPotion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "heal";
    }
}
