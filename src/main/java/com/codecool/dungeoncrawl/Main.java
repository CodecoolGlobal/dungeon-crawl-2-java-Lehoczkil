package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.display.Display;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Coin;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.vdurmont.emoji.EmojiParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    private Scene MAIN_MENU;
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
    private final int displayRange = 5;

    private GameDatabaseManager gdm;
    private Display display;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initGameState();
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);

        Scene menu = display.createMenu(primaryStage);
        Button newGame = (Button) menu.lookup("#gameBtn");
        newGame.setOnAction(ActionEvent -> {
            TextInputDialog td = new TextInputDialog("Enter Player name:");
            td.setHeaderText("Choose a name");
            td.show();
            String name = td.getEditor().getText();
            System.out.println(name);




//            Scene scene = display.generateGameWindow(healthLabel, canvas, inventory);
//            scene.setOnKeyPressed(this::onKeyPressed);
//            display.displayGame(primaryStage, scene);
//            canvas.setHeight(2 * displayRange * Tiles.TILE_WIDTH);
//            canvas.setWidth(2 * displayRange * Tiles.TILE_WIDTH);
//            initPlayer();
//            display.setPlayer_id(player.getId());
//            refresh();
        });
        Button exit = (Button) menu.lookup("#exitBtn");
        exit.setOnAction(ActionEvent -> primaryStage.close());

        this.MAIN_MENU = menu;
        display.displayGame(primaryStage, menu);

    }

    private void checkForEnemy(int dx, int dy) {
        if (player.getCell().getNeighbor(dx, dy).getActor() != null &&
           (player.getCell().getNeighbor(dx, dy).getActor().getTileName().equals("skeleton") ||
            player.getCell().getNeighbor(dx, dy).getActor().getTileName().equals("ghost") ||
            player.getCell().getNeighbor(dx, dy).getActor().getTileName().equals("boss"))) {
                Enemy enemy = (Enemy) player.getCell().getNeighbor(dx, dy).getActor();
                player.attack(enemy);
                if (!enemy.isAlive()) {
                    player.getCell().getNeighbor(dx, dy).setActor(null);
                    if (enemy.getTileName() == "skeleton") {
                        player.getCell().getNeighbor(dx, dy).setItem(new Coin(enemy.getCell()));
                    }
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
                display.displayGame(primaryStage, MAIN_MENU);
        }
        if (!moved) {
            display.updateInventory(inventory);
            refresh();
        }
    }

    private void moveEnemies() {
        for (Enemy enemy: map.getEnemies()) {
            enemy.attackPlayer(player);
            enemy.move();
        }
    }

    private void refresh() {
        if (currentMap == 3) {
            boolean isBossAlive = false;
            for (Enemy enemy: map.getEnemies()) {
                if (enemy.getTileName() == "boss") {
                    isBossAlive = true;
                }
            }
            if (!isBossAlive) {
                Scene winningScene = display.createWinScene(primaryStage);
                winningScene.setOnKeyPressed(KeyEvent -> {
                    if (KeyEvent.getCode() == KeyCode.ESCAPE) {
                        primaryStage.close();
                    }
                });
                display.displayGame(primaryStage, winningScene);
            }
        }
        moveEnemies();
        if (!map.getPlayer().isAlive()) {
            Scene endGame = display.createEndGameScene(primaryStage);
            endGame.setOnKeyPressed(KeyEvent -> {
                if (KeyEvent.getCode() == KeyCode.ESCAPE) {
                    primaryStage.close();
                }
            });
            display.displayGame(primaryStage, endGame);
        } else if (map.isLevelOver() && currentMap < 3) {
            map = MapLoader.loadNextLevel(currentMap, player);
            refresh();
            currentMap++;
        } else {
            context.setFill(Color.color(0.28, 0.18, 0.24));
            context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            int playerX = player.getCell().getX();
            int playerY = player.getCell().getY();
            double canvasX = canvas.getWidth() / 2 - Tiles.TILE_WIDTH / 2;
            double canvasY = canvas.getHeight() / 2 - Tiles.TILE_WIDTH / 2;
            for (int x = -displayRange; x <= displayRange; x++) {
                for (int y = -displayRange; y <= displayRange; y++) {
                    if (playerX + x >= 0 && playerX + x < map.getWidth()
                        && playerY + y >= 0 && playerY + y < map.getHeight()) {
                        Cell cell = map.getCell(playerX + x, playerY + y);
                        if (cell.getActor() != null) {
                            Tiles.drawTile(context, cell.getActor(), canvasX + (x * Tiles.TILE_WIDTH),
                                    canvasY + (y * Tiles.TILE_WIDTH));
                        } else if (cell.getItem() != null) {
                            Tiles.drawTile(context, cell.getItem(), canvasX + (x * Tiles.TILE_WIDTH),
                                    canvasY + (y * Tiles.TILE_WIDTH));
                        } else {
                            Tiles.drawTile(context, cell, canvasX + (x * Tiles.TILE_WIDTH),
                                    canvasY + (y * Tiles.TILE_WIDTH));
                        }
                    }
                }
            }
        }
        if (map.getPlayer().getHealth() > 0) {
            healthLabel.setText(EmojiParser.parseToUnicode(":heart:").repeat(map.getPlayer().getHealth()));
        }
    }

    private void initGameState() {
        this.gdm = new GameDatabaseManager();
        this.gdm.run();
        display = new Display(gdm);
    }

    private void initPlayer() {
        player = map.getPlayer();
        player.setGdm(gdm);
        gdm.savePlayer(player);
    }
}
