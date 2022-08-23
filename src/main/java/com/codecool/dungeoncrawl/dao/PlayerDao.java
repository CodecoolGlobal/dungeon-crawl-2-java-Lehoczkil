package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    PlayerModel add(PlayerModel player);
    void update(PlayerModel player);
    int get(String name);
    List<PlayerModel> getAll();
}
