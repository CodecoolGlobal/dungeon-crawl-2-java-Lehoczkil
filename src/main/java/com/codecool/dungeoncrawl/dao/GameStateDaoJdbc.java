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
            String sql = "INSERT INTO game_state (current_map, mapnumber, saved_at, player_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBytes(1, state.getCurrentMap());
            statement.setInt(2, state.getMapNumber());
            statement.setDate(3, state.getSavedAt());
            statement.setInt(4, state.getPlayer().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state " +
                    "SET current_map = ?, mapnumber = ?, saved_at = ? " +
                    "WHERE player_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBytes(1, state.getCurrentMap());
            statement.setInt(2, state.getMapNumber());
            statement.setDate(3, state.getSavedAt());
            statement.setInt(4, state.getPlayer().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] get(String playerName) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT current_map " +
                    "FROM game_state " +
                    "JOIN player p on game_state.player_id = p.id " +
                    "WHERE p.player_name = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            byte[] result = null;
            while (resultSet.next()) {
                result = (resultSet.getBytes(1));
            }
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public int getMapNumber(int id) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT mapNumber " +
                    "FROM game_state " +
                    "WHERE player_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
