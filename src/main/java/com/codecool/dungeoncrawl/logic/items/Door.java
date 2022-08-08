package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class Door extends Actor {

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
