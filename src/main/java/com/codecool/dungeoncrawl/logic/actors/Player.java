package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Door;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Player extends Actor {
    private final int MAX_HEALTH;

    private int id;

    private final String name;

    private boolean hasSword = false;
    private boolean hasArmor = false;
    private PlayerState playerState = PlayerState.NAKED;

    private GameDatabaseManager gdm;


    public Player(Cell cell) {
        super(cell);
        this.name = "player";
        MAX_HEALTH = 12;
    }

    public String getTileName() {
        return "player";
    }

    public boolean isOnItem() {
        return cell.getItem() != null && cell.getItem().getTileName() != "open door";
    }

    public void pickUp(Item item) {
        if (item.getTileName().equals("heal") && health <= MAX_HEALTH){
            health += 3;
            health = Math.min(health, MAX_HEALTH);
        } else if (!item.getTileName().equals("heal")){
            gdm.itemsManagerDaoJdbc.addItem(item.getTileName(), id);
        }
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
        } else if (nextCell.getItem() != null && nextCell.getItem().getTileName() == "closed door" && gdm.itemsManagerDaoJdbc.hasItem("key", id)) {
            ((Door) nextCell.getItem()).setOpen();
            gdm.itemsManagerDaoJdbc.decrementItem("key", id);
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

    public String getName() {
        return name;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setGdm(GameDatabaseManager gdm) {
        this.gdm = gdm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
