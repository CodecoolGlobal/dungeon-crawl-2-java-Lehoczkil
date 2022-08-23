package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabaseManager {
    private PlayerDaoJdbc playerDao;
    public ItemsManagerDaoJdbc itemsManagerDaoJdbc;

    public GameStateDaoJdbc gameStateDaoJdbc;
    public PlayerDaoJdbc getPlayerDao() {
        return playerDao;
    }

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        itemsManagerDaoJdbc = new ItemsManagerDaoJdbc(dataSource);
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDaoJdbc = new GameStateDaoJdbc();
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        model = playerDao.add(model);
        player.setId(model.getId());
    }

    public static DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(System.getenv("DB_NAME"));
        dataSource.setUser(System.getenv("DB_USER"));
        dataSource.setPassword(System.getenv("DB_PW"));

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");
        return dataSource;
    }

    public void run() {
        try {
            setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
