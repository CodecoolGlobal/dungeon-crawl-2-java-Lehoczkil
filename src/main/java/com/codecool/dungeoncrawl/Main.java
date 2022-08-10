package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.database.Manager;
import com.codecool.dungeoncrawl.display.Display;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    int currentMap = 1;
    GameMap map = MapLoader.loadMap("/map.txt");
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventory = new Label();
    Stage primaryStage;

    public static void main(String[] args) {
        new Manager().setup();
        Manager.restoreDB("src/main/java/com/codecool/dungeoncrawl/database/inventory.sql");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        Scene scene = Display.generateGameWindow(healthLabel, canvas, inventory);
        scene.setOnKeyPressed(this::onKeyPressed);
        Display.displayGame(primaryStage, scene);
        refresh();

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
                if (player.getCell().getNeighbor(0,-1).getActor() != null &&
                    player.getCell().getNeighbor(0,-1).getActor().getTileName().equals("skeleton")) {
                    Skeleton enemy = (Skeleton) player.getCell().getNeighbor(0,-1).getActor();
                    player.attack(enemy);
                    enemy.attack(player);
                    if (!enemy.isAlive()) {
                        player.getCell().getNeighbor(0,-1).setActor(null);
                    }
                } else if (player.getCell().getNeighbor(0,1).getActor() != null &&
                           player.getCell().getNeighbor(0,1).getActor().getTileName().equals("skeleton")) {
                    Skeleton enemy = (Skeleton) player.getCell().getNeighbor(0,1).getActor();
                    player.attack(enemy);
                    enemy.attack(player);
                    if (!enemy.isAlive()) {
                        player.getCell().getNeighbor(0,1).setActor(null);
                    }
                } else if (player.getCell().getNeighbor(-1,0).getActor() != null &&
                           player.getCell().getNeighbor(-1,0).getActor().getTileName().equals("skeleton")) {
                    Skeleton enemy = (Skeleton) player.getCell().getNeighbor(-1,0).getActor();
                    player.attack(enemy);
                    enemy.attack(player);
                    if (!enemy.isAlive()) {
                        player.getCell().getNeighbor(-1,0).setActor(null);
                    }
                } else if (player.getCell().getNeighbor(1,0).getActor() != null &&
                           player.getCell().getNeighbor(1,0).getActor().getTileName().equals("skeleton")) {
                    Skeleton enemy = (Skeleton) player.getCell().getNeighbor(1,0).getActor();
                    player.attack(enemy);
                    enemy.attack(player);
                    if (!enemy.isAlive()) {
                        player.getCell().getNeighbor(1,0).setActor(null);
                    }
                }
                break;
        }
        if (!moved) {
            refresh();
        }
    }

    private void moveSkeletons() {
        for (Skeleton skeleton: map.getSkeletons()) {
            skeleton.move();
        }
    }

    private void refresh() {
        if (!map.getPlayer().isAlive()) {
            Scene endGame = Display.createEndGameScene();
            Display.displayGame(primaryStage, endGame);
        } else if (map.isLevelOver() && currentMap < 3) {
            map = MapLoader.loadNextLevel(currentMap);
            currentMap++;
        } else {
            moveSkeletons();
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
        Display.updateInventory(inventory);
    }
}
