package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.database.Manager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Door;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Player extends Actor {

    private boolean hasSword = false;

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

    @Override
    public void attack(Actor enemy) {
        if (hasSword) {
            enemy.takeDamage(5);
        } else {
            enemy.takeDamage(3);
        }
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.cell.getNeighbor(dx, dy);
        if (isValidMove(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (nextCell.getItem() != null && nextCell.getItem().getTileName() == "closed door" && Manager.hasItem("key")) {
            ((Door) nextCell.getItem()).setOpen();
            Manager.decrementItem("key");
        }
    }
}
