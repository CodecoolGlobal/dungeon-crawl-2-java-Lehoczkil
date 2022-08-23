package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao, Serializable {
    @Override
    public void add(GameState state) {

    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(int id) {
        return null;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
