package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private Date savedAt;
    private String currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private PlayerModel player;

    public GameState(GameMap currentMap, Date savedAt, PlayerModel player) {
        this.savedAt = savedAt;
        this.player = player;
        stringifyMap(currentMap);
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
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

    private void stringifyMap(GameMap map) {
        byte[] byteMap = SerializationUtils.serialize(map);
        StringBuilder stringMap = new StringBuilder();
        for (byte elem: byteMap) {
            stringMap.append(elem).append("\n");
        }
        this.currentMap = stringMap.toString();
    }
}
