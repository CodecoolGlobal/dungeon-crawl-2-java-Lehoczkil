package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(GameState state);
    void update(GameState state);
    List<byte[]> get(String playerName);
    List<GameState> getAll();
}
