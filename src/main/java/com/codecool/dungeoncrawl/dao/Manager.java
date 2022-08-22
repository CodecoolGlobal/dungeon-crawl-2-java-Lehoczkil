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

public class Manager {

    static DataSource dataSource;


    public Manager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void insertItem(String name) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "INSERT INTO inventory" +
                    "(player_id, name, quantity)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, 1);
            statement.setString(2, name);
            statement.setInt(3, 1);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void incrementItem(String name) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "UPDATE inventory " +
                    "SET " +
                    "quantity = quantity + 1 " +
                    "WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.setInt(1, value);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    private void deleteGarbageItems() {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "DELETE FROM inventory " +
                    "WHERE quantity <= 0";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public HashMap<String, Integer> getItems() {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT name, quantity " +
                    "FROM inventory";
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            HashMap<String, Integer> result = new HashMap<>();
            while (resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getInt(2));
            }
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void addItem(String name) {
        if (hasItem(name)) {
            incrementItem(name);
        } else {
            insertItem(name);
        }
    }

    public void decrementItem(String name) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "UPDATE inventory " +
                    "SET " +
                    "quantity = quantity - 1 " +
                    "WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.setInt(1, value);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        deleteGarbageItems();
    }

    public boolean hasItem(String name) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT name, quantity " +
                    "FROM inventory " +
                    "WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void restoreDB (String SQLScriptPath) {
        try (Connection connection = dataSource.getConnection()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(SQLScriptPath));
                String str;
                StringBuffer sb = new StringBuffer();
                while ((str = in.readLine()) != null) {
                    sb.append(str).append("\n");
                }
                in.close();
                PreparedStatement stmt = connection.prepareStatement(sb.toString());
                stmt.executeUpdate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throw new RuntimeException();
        }
    }
}
