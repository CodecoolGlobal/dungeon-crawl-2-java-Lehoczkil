package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.database.Manager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public boolean isOnItem() {
        return cell.getItem() != null && cell.getItem().getTileName() != "open door";
    }

    public void pickUp(Item item) {
        Manager.addItem(item.getTileName());
    }
}
