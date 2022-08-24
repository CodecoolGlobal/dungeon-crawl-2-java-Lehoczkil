package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends Enemy{
    public Ghost(Cell cell) {
        super(cell);
        this.health = 12;
    }

    @Override
    public void attack(Actor enemy) {
        enemy.takeDamage(4);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void move() {
        int[] move = possibleMoves[ThreadLocalRandom.current().nextInt(0,4)];
        Cell nextCell = null;
        if ((cell.getX() + move[0]) > 0 &&
            (cell.getX() + move[0]) < cell.getGameMap().getWidth() - 1 &&
            (cell.getY() + move[1]) > 0 &&
            (cell.getY() + move[1]) < cell.getGameMap().getHeight() - 1) {
                nextCell = this.cell.getNeighbor(move[0], move[1]);
        }
        if (nextCell != null && isValidMove(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    @Override
    protected boolean isValidMove(Cell cell) {
        if (lookForPlayer()) return false;
        return cell.getActor() == null;
    }
}
