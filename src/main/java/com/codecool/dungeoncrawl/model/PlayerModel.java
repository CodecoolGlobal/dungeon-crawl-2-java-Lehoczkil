package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();

    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHp() {
        return hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
