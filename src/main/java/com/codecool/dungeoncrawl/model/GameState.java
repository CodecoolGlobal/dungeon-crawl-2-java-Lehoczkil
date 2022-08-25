package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;
import org.apache.commons.lang3.SerializationUtils;
import java.sql.Date;

public class GameState extends BaseModel {
    private Date savedAt;
    private byte[] currentMap;
    private PlayerModel player;

    private int mapNumber;

    public GameState(GameMap currentMap, Date savedAt, PlayerModel player, int mapNumber) {
        this.savedAt = savedAt;
        this.player = player;
        serialize(currentMap);
        this.mapNumber = mapNumber;
    }



    public Date getSavedAt() {
        return savedAt;
    }

    public byte[] getCurrentMap() {
        return currentMap;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    private void serialize(GameMap map) {
        this.currentMap = SerializationUtils.serialize(map);
    }

    public GameMap deSerialize(byte[] map) {
        return SerializationUtils.deserialize(map);
    }

    public int getMapNumber() {
        return mapNumber;
    }
}
