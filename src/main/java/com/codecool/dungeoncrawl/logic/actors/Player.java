package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Door;

public class Player extends Actor {
    private final int MAX_HEALTH;

    private int id;

    private String name;

    private boolean hasSword = false;
    private boolean hasArmor = false;

    private boolean hasKey = false;

    private boolean usedKey = false;
    private PlayerState playerState = PlayerState.NAKED;


    public Player(Cell cell) {
        super(cell);
        MAX_HEALTH = 12;
    }

    public String getTileName() {
        return "player";
    }

    public boolean isOnItem() {
        return cell.getItem() != null && !cell.getItem().getTileName().equals("open door");
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
        } else if (nextCell.getItem() != null && nextCell.getItem().getTileName() == "closed door" && hasKey) {
            ((Door) nextCell.getItem()).setOpen();
            usedKey = true;
            return false;
        }
        return true;
    }

    @Override
    public void takeDamage(int damage) {
        if (hasArmor) {
            damage /= 2;
        }
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }

    public void checkGear() {
        if (hasArmor && hasSword) {
            playerState = PlayerState.FULL;
        } else if (hasArmor && !hasSword) {
            playerState = PlayerState.ARMORED;
        } else if (!hasArmor && hasSword) {
            playerState = PlayerState.ARMED;
        } else {
            playerState = PlayerState.NAKED;
        }
        Tiles.updatePlayerImage(playerState.getTileNumber());
    }

    public String getName() {
        return name;
    }

    public void increaseHealth(int amount) {
        this.health = Math.min(health + amount, MAX_HEALTH);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
    }

    public void setHasArmor(boolean hasArmor) {
        this.hasArmor = hasArmor;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean hasUsedKey() {
        return usedKey;
    }

    public void setUsedKey(boolean usedKey) {
        this.usedKey = usedKey;
    }
}
