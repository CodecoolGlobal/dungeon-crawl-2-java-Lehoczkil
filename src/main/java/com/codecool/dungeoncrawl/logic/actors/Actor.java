package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.Door;

public abstract class Actor implements Drawable {

    protected Cell cell;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    private boolean isValidMove(Cell cell) {
        if (cell.getTileName().equals("floor")) {
            if (cell.getActor() != null) {
                if (!cell.getActor().getTileName().equals("skeleton")) {
                    return true;
                }
            } else if (cell.getItem() != null) {
                if (cell.getItem().getTileName().equals("closed door")) {
                    return false;
                }
                return true;
            } else {
                return true;
            }
        }
        return false;
    }

    public void move(int dx, int dy) {
        Cell nextCell = this.cell.getNeighbor(dx, dy);
        if (isValidMove(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
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
