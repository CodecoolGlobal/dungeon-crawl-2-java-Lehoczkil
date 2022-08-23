package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
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
    public List<byte[]> get(String playerName) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT current_map " +
                    "FROM game_state " +
                    "JOIN player p on game_state.player_id = p.id " +
                    "WHERE p.player_name = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            List<byte[]> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getBytes(1));
            }
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
