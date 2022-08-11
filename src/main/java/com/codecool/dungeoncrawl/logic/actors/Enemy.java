package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Actor{

    protected final int[][] possibleMoves = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public Enemy(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "enemy";
    }

    @Override
    public void attack(Actor enemy) {
        enemy.takeDamage(2);
    }


    @Override
    protected boolean isValidMove(Cell cell) {
        if (lookForPlayer()) return false;
        if (cell.getTileName().equals("floor")) {
            if (cell.getActor() != null) {
                return false;
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

    boolean lookForPlayer() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Cell nextCell = this.cell.getNeighbor(i, j);
                if (nextCell.getType().equals(CellType.FLOOR) && nextCell.getActor() != null
                        && nextCell.getActor().getTileName().equals("player")) {
                    return true;
                }
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

    public void attackPlayer(Player player) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (Math.abs(i) - Math.abs(j) != 0) {
                    Cell nextCell = this.cell.getNeighbor(i, j);
                    if (nextCell.getType().equals(CellType.FLOOR) && nextCell.getActor() != null
                            && nextCell.getActor().getTileName().equals("player")) {
                        attack(player);
                    }
                }
            }
        }
    }
}
