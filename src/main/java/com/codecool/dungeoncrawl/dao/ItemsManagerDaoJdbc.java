package com.codecool.dungeoncrawl.dao;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ItemsManagerDaoJdbc {

    DataSource dataSource;


    public ItemsManagerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void insertItem(String name, int player_id) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "INSERT INTO items" +
                    "(player_id, name, quantity)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, player_id);
            statement.setString(2, name);
            statement.setInt(3, 1);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void incrementItem(String name, int player_id) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "UPDATE items " +
                    "SET " +
                    "quantity = quantity + 1 " +
                    "WHERE name = ? AND player_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, name);
            statement.setInt(2, player_id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    private void deleteGarbageItems() {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "DELETE FROM items " +
                    "WHERE quantity <= 0";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public HashMap<String, Integer> getItems(int player_id) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT name, quantity " +
                    "FROM items " +
                    "WHERE player_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, player_id);
            ResultSet resultSet = statement.executeQuery();
            HashMap<String, Integer> result = new HashMap<>();
            while (resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getInt(2));
            }
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void addItem(String name, int player_id) {
        if (hasItem(name, player_id)) {
            incrementItem(name, player_id);
        } else {
            insertItem(name, player_id);
        }
    }

    public void decrementItem(String name, int player_id) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "UPDATE items " +
                    "SET " +
                    "quantity = quantity - 1 " +
                    "WHERE name = ? AND player_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, name);
            statement.setInt(2, player_id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        deleteGarbageItems();
    }

    public boolean hasItem(String name, int player_id) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT name, quantity " +
                    "FROM items " +
                    "WHERE name = ? AND player_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, name);
            statement.setInt(2, player_id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

}
