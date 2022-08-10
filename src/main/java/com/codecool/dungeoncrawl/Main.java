package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.database.Manager;
import com.codecool.dungeoncrawl.display.Display;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    int currentMap = 1;
    GameMap map = MapLoader.loadMap("/map.txt", null);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventory = new Label();
    Stage primaryStage;

    Player player;

    public static void main(String[] args) {
        new Manager().setup();
        Manager.restoreDB("src/main/java/com/codecool/dungeoncrawl/database/inventory.sql");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        player = map.getPlayer();
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);

        Scene menu = Display.createMenu(primaryStage, healthLabel, canvas, inventory);
        Button newGame = (Button) menu.lookup("#gameBtn");
        newGame.setOnAction(ActionEvent -> {
            Scene scene = Display.generateGameWindow(healthLabel, canvas, inventory);
            scene.setOnKeyPressed(this::onKeyPressed);
            Display.displayGame(primaryStage, scene);
            canvas.setHeight(map.getHeight() * Tiles.TILE_WIDTH);
            canvas.setWidth(map.getWidth() * Tiles.TILE_WIDTH);
            refresh();
        });
        Button exit = (Button) menu.lookup("#exitBtn");
        exit.setOnAction(ActionEvent -> primaryStage.close());

        Display.displayGame(primaryStage, menu);

    }

    private void checkForEnemy(int dx, int dy) {
        if (player.getCell().getNeighbor(dx, dy).getActor() != null &&
           (player.getCell().getNeighbor(dx, dy).getActor().getTileName().equals("skeleton") ||
            player.getCell().getNeighbor(dx, dy).getActor().getTileName().equals("ghost") ||
            player.getCell().getNeighbor(dx, dy).getActor().getTileName().equals("boss"))) {
                Enemy enemy = (Enemy) player.getCell().getNeighbor(dx, dy).getActor();
                player.attack(enemy);
                enemy.attack(player);
                if (!enemy.isAlive()) {
                    player.getCell().getNeighbor(dx, dy).setActor(null);
                }
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Player player = map.getPlayer();
        boolean moved = false;
        switch (keyEvent.getCode()) {
            case UP:
                moved = player.move(0, -1);
                refresh();
                break;
            case DOWN:
                moved = player.move(0, 1);
                refresh();
                break;
            case LEFT:
                moved = player.move(-1, 0);
                refresh();
                break;
            case RIGHT:
                moved = player.move(1,0);
                refresh();
                break;
            case F:
                if (player.isOnItem()) {
                    player.pickUp(player.getCell().getItem());
                    player.getCell().setItem(null);
                }
                refresh();
                break;
            case SPACE:
                checkForEnemy(0, -1);
                checkForEnemy(0,1);
                checkForEnemy(-1,0);
                checkForEnemy(1,0);
                break;
            case ESCAPE:
                primaryStage.close();
        }
        if (!moved) {
            Display.updateInventory(inventory);
            refresh();
        }
    }

    private void moveEnemies() {
        for (Enemy enemy: map.getEnemies()) {
            enemy.move();
        }
    }

    private void refresh() {
        if (!map.getPlayer().isAlive()) {
            Scene endGame = Display.createEndGameScene(primaryStage);
            Display.displayGame(primaryStage, endGame);
        } else if (map.isLevelOver() && currentMap < 3) {
            map = MapLoader.loadNextLevel(currentMap, player);
            refresh();
            currentMap++;
        } else {
            moveEnemies();
            context.setFill(Color.BLACK);
            context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int x = 0; x < map.getWidth(); x++) {
                for (int y = 0; y < map.getHeight(); y++) {
                    Cell cell = map.getCell(x, y);
                    if (cell.getActor() != null) {
                        Tiles.drawTile(context, cell.getActor(), x, y);
                    } else if (cell.getItem() != null) {
                        Tiles.drawTile(context, cell.getItem(), x, y);
                    } else {
                        Tiles.drawTile(context, cell, x, y);
                    }
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}
