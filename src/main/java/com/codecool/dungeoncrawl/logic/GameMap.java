package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> result = new ArrayList<>();
        for (Cell[] row: cells) {
            for (Cell cell: row) {
                if (cell.getActor() != null && (cell.getActor().getTileName().equals("skeleton") ||
                        cell.getActor().getTileName().equals("ghost") ||
                        cell.getActor().getTileName().equals("boss"))) {
                    result.add((Enemy) cell.getActor());
                }
            }
        }
        return result;
    }

    public boolean isLevelOver() {
        for (Cell[] row: cells) {
            for (Cell cell: row) {
                if (cell.getActor() != null && cell.getItem() != null &&
                    cell.getItem().getTileName().equals("open door") &&
                    cell.getActor().getTileName().equals("player")) {
                    return true;
                }
            }
        }
        return false;
    }
}
