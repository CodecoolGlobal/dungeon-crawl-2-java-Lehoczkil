package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.io.Serializable;

public abstract class Actor implements Drawable, Serializable {

    protected Cell cell;
    protected boolean isAlive = true;
    protected int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public abstract void attack(Actor enemy);

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }

    public boolean isDead() {
        return !isAlive;
    }

    protected boolean isValidMove(Cell cell) {
        if (cell.getTileName().equals("floor")) {
            if (cell.getActor() != null) {
                return !cell.getActor().getTileName().equals("skeleton") &&
                        !cell.getActor().getTileName().equals("ghost") &&
                        !cell.getActor().getTileName().equals("boss");
            } else if (cell.getItem() != null) {
                return !cell.getItem().getTileName().equals("closed door");
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean move(int dx, int dy) {
        Cell nextCell = this.cell.getNeighbor(dx, dy);
        if (isValidMove(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        return true;
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
