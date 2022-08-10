package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Armor extends Item{
    public Armor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "armor";
    }
}
