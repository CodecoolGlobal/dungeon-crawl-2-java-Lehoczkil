package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private Date savedAt;
    private byte[] currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private PlayerModel player;

    public GameState(GameMap currentMap, Date savedAt, PlayerModel player) {
        this.savedAt = savedAt;
        this.player = player;
        serialize(currentMap);
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public byte[] getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(GameMap currentMap) {
        serialize(currentMap);
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
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
        return SerializationUtils.deserialize(currentMap);
    }
}
