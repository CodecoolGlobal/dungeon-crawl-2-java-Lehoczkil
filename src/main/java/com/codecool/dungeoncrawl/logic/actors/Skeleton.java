package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class Skeleton extends Actor {

    private final int[][] possibleMoves = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public void attack(Actor enemy) {
        enemy.takeDamage(2);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    protected boolean isValidMove(Cell cell) {
        if (cell.getTileName().equals("floor")) {
            if (cell.getActor() != null) {
                if (!cell.getActor().getTileName().equals("player")) {
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

    public void move() {
        int[] move = possibleMoves[ThreadLocalRandom.current().nextInt(0,4)];
        Cell nextCell = this.cell.getNeighbor(move[0], move[1]);
        if (isValidMove(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }
}
