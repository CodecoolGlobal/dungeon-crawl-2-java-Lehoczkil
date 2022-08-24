package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Door extends Item {

    public Door(Cell cell) {
        super(cell);
    }

    public void setOpen() {
        isOpen = true;
    }

    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String getTileName() {
        return isOpen() ? "open door" : "closed door";
    }
}
