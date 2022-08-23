package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao, Serializable {

    private final DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBytes(1, state.getCurrentMap());
            statement.setDate(2, state.getSavedAt());
            statement.setInt(3, state.getPlayer().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
