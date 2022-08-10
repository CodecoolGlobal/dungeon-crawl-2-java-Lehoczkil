package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.database.Manager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Door;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Player extends Actor {

    private boolean hasSword = false;
    private boolean hasArmor = false;
    private PlayerState playerState = PlayerState.NAKED;

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
        if (item.getTileName().equals("sword")) {
            hasSword = true;
            playerState = PlayerState.ARMED;
        } else if (item.getTileName().equals("armor")) {
            hasArmor = true;
            if (!hasSword) {
                playerState = PlayerState.ARMORED;
            }
        }
        if (hasSword && hasArmor) {
            playerState = PlayerState.FULL;
        }
        Tiles.updatePlayerImage(playerState.getTileNumber());
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
    public boolean move(int dx, int dy) {
        Cell nextCell = this.cell.getNeighbor(dx, dy);
        if (isValidMove(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (nextCell.getItem() != null && nextCell.getItem().getTileName() == "closed door" && Manager.hasItem("key")) {
            ((Door) nextCell.getItem()).setOpen();
            Manager.decrementItem("key");
            return false;
        }
        return true;
    }

    @Override
    public void takeDamage(int damage) {
        if (hasArmor) damage /= 2;
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }
}
