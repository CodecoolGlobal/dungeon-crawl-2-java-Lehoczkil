package com.codecool.dungeoncrawl.database;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Manager {

    static DataSource dataSource;

    public void setup() {
        try {
            dataSource = new Connect().connect();
        } catch (SQLException e) {
            System.err.println("Could not connect to database, gigamega failure!");
        }
    }
}
