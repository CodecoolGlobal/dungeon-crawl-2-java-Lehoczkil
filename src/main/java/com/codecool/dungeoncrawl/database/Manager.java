package com.codecool.dungeoncrawl.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Manager {

    static DataSource dataSource;

    public void setup() {
        try {
            dataSource = new Connect().connect();
        } catch (SQLException e) {
            System.err.println("Could not connect to database, gigamega failure!");
        }
    }

    private static void insertItem(String name) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "INSERT INTO inventory" +
                    "(name, quantity)" +
                    "VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, name);
            statement.setInt(2, 1);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void incrementItem(String name) {
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
    
    private static void deleteGarbageItems() {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "DELETE FROM inventory " +
                    "WHERE quantity <= 0";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static HashMap<String, Integer> getItems() {
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

    public static void addItem(String name) {
        if (hasItem(name)) {
            incrementItem(name);
        } else {
            insertItem(name);
        }
    }

    public static void decrementItem(String name) {
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

    private static boolean hasItem(String name) {
        try(Connection connection = dataSource.getConnection()) {
            String SQL = "SELECT name, quantity " +
                    "FROM inventory " +
                    "WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, name);
            ResultSet resultSet = statement.getResultSet();
            return resultSet.next();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
