package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.List;

public class Display {
    private GameDatabaseManager gdm;
    private int player_id;

    public Display(GameDatabaseManager gdm) {
        this.gdm = gdm;
    }

    private GridPane generateUI(Label healthLabel, Label playerInventory) {
        int fontSize = 50;
        GridPane ui = new GridPane();
        ui.setPrefWidth(600);
        ui.setPadding(new Insets(10));

        Label health = new Label("Health: ");
        health.setTextFill(Color.WHITE);
        health.setStyle("-fx-font-size: " + fontSize);
        healthLabel.setTextFill(Color.RED);
        healthLabel.setStyle("-fx-font-size: " + fontSize);

        ui.add(health, 0, 0);
        ui.add(healthLabel, 0, 1);

        Label inventory = new Label("\nInventory:\n");
        inventory.setTextFill(Color.WHITE);
        inventory.setStyle("-fx-font-size: " + fontSize);
        playerInventory.setTextFill(Color.WHITE);
        playerInventory.setStyle("-fx-font-size: " + fontSize);

        ui.add(inventory, 0, 2);
        ui.add(playerInventory, 0, 3);
        ui.add(new Label("\n"), 0, 4);

        Label pickUp = new Label("Pick up item: F");
        pickUp.setTextFill(Color.WHITE);
        pickUp.setStyle("-fx-font-size: " + fontSize);
        Label attack = new Label("Hit enemy: SPACE");
        attack.setTextFill(Color.WHITE);
        attack.setStyle("-fx-font-size: " + fontSize);

        ui.add(pickUp, 0, 5);
        ui.add(attack, 0, 6);
        return ui;
    }

    public Scene generateGameWindow(Label healthLabel, Canvas canvas, Label inventory) {
        GridPane ui = generateUI(healthLabel, inventory);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #472d3c");

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        return new Scene(borderPane);
    }

    public Scene createEndGameScene(Stage stage) {
        BorderPane gameOver = new BorderPane();
        gameOver.setMinWidth(stage.getWidth());
        gameOver.setMinHeight(stage.getHeight());
        gameOver.setStyle("-fx-background-color: #000000");

        Label gameOverText = new Label("GAME OVER");
        gameOverText.setTextFill(Color.RED);
        gameOverText.setStyle("-fx-font-size: 80");
        gameOver.setCenter(gameOverText);

        return new Scene(gameOver);
    }

    public Scene createWinScene(Stage stage) {
        BorderPane winScene = new BorderPane();
        winScene.setMinWidth(stage.getWidth());
        winScene.setMinHeight(stage.getHeight());
        winScene.setStyle("-fx-background-color: #ffffff");
        Label winSceneText = new Label("CONGRATS");
        winSceneText.setTextFill(Color.RED);
        winSceneText.setStyle("-fx-font-size: 80");
        Label winSceneStats = new Label("Coins collected: " + gdm.itemsManagerDaoJdbc.getItems(player_id).get("coin"));
        winSceneStats.setTextFill(Color.RED);
        winSceneStats.setStyle("-fx-font-size: 40");
        winScene.setCenter(winSceneText);
        winScene.setBottom(winSceneStats);

        return new Scene(winScene);
    }

    public void displayGame(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.setFullScreen(true);
        primaryStage.show();
        Tiles.setTileWidth(primaryStage.getHeight()/10);
    }

    public void updateInventory (Label inventory) {
        HashMap<String, Integer> items = gdm.itemsManagerDaoJdbc.getItems(player_id);
        StringBuilder sb = new StringBuilder();
        if (items.size() > 0) {
            for (String item: items.keySet()) {
                sb.append(item);
                if (items.get(item) > 1) {
                    sb.append(" ").append(items.get(item));
                }
                sb.append("\n");
            }
        }
        inventory.setText(sb.toString());
    }

    public Scene createMenu (Stage primaryStage) {
        List<String> players = gdm.getPlayerDao().getPlayerNames();

        Button newGame = new Button();
        newGame.setStyle("-fx-background-color: " + Color.BLANCHEDALMOND);
        newGame.setText("NEW GAME");
        newGame.setStyle("-fx-font-size: 80");
        newGame.setTextFill(Color.CHOCOLATE);
        newGame.setId("gameBtn");

        Button exitGame = new Button();
        exitGame.setStyle("-fx-background-color: " + Color.BLANCHEDALMOND);
        exitGame.setText("EXIT GAME");
        exitGame.setStyle("-fx-font-size: 80");
        exitGame.setTextFill(Color.CHOCOLATE);
        exitGame.setId("exitBtn");

        Button loadGame = new Button();
        loadGame.setStyle("-fx-background-color: " + Color.BLANCHEDALMOND);
        loadGame.setText("LOAD GAME");
        loadGame.setStyle("-fx-font-size: 80");
        loadGame.setTextFill(Color.CHOCOLATE);
        loadGame.setId("loadBtn");
        if (players.isEmpty()) {
            loadGame.setDisable(true);
        }

        Label logo = new Label("Rolling Winter Wombat Ltd");
        logo.setTextFill(Color.CORNSILK);
        logo.setStyle("-fx-font-size: 60");
        logo.setPadding(new Insets(50, 0, 0, 0));

        VBox menuPane = new VBox(30, newGame, loadGame, exitGame, logo);
        menuPane.setPrefWidth(primaryStage.getWidth());
        menuPane.setPrefHeight(primaryStage.getHeight());
        menuPane.setStyle("-fx-background-color: #999999");

        menuPane.setAlignment(Pos.CENTER);

        return new Scene(menuPane, primaryStage.getWidth(), primaryStage.getHeight());
    }

    public Scene createInGameMenu (Stage primaryStage) {

        Button continueButton = new Button();
        continueButton.setStyle("-fx-background-color: " + Color.BLANCHEDALMOND);
        continueButton.setText("CONTINUE");
        continueButton.setStyle("-fx-font-size: 80");
        continueButton.setTextFill(Color.CHOCOLATE);
        continueButton.setId("continueBtn");

        Button exitGame = new Button();
        exitGame.setStyle("-fx-background-color: " + Color.BLANCHEDALMOND);
        exitGame.setText("EXIT GAME");
        exitGame.setStyle("-fx-font-size: 80");
        exitGame.setTextFill(Color.CHOCOLATE);
        exitGame.setId("exitBtn");

        Button exportButton = new Button();
        exportButton.setStyle("-fx-background-color: " + Color.BLANCHEDALMOND);
        exportButton.setText("EXPORT");
        exportButton.setStyle("-fx-font-size: 80");
        exportButton.setTextFill(Color.CHOCOLATE);
        exportButton.setId("exportBtn");

        Label logo = new Label("Rolling Winter Wombat Ltd");
        logo.setTextFill(Color.CORNSILK);
        logo.setStyle("-fx-font-size: 60");
        logo.setPadding(new Insets(50, 0, 0, 0));

        VBox menuPane = new VBox(30, continueButton, exportButton, exitGame, logo);
        menuPane.setPrefWidth(primaryStage.getWidth());
        menuPane.setPrefHeight(primaryStage.getHeight());
        menuPane.setStyle("-fx-background-color: #999999");

        menuPane.setAlignment(Pos.CENTER);

        return new Scene(menuPane, primaryStage.getWidth(), primaryStage.getHeight());
    }

    public Scene createLoadMenu(Stage primaryStage) {
        List<String> players = gdm.getPlayerDao().getPlayerNames();

        VBox menuPane = new VBox(30);
        menuPane.setPrefWidth(primaryStage.getWidth());
        menuPane.setPrefHeight(primaryStage.getHeight());
        menuPane.setStyle("-fx-background-color: #999999");
        menuPane.setId("container");

        for (String player: players) {
            Button btn = new Button();
            btn.setStyle("-fx-background-color: " + Color.BLANCHEDALMOND);
            btn.setText(player);
            btn.setStyle("-fx-font-size: 80");
            btn.setTextFill(Color.CHOCOLATE);
            btn.setId("playerBtn");
            menuPane.getChildren().add(btn);
        }

        menuPane.setAlignment(Pos.CENTER);

        return new Scene(menuPane, primaryStage.getWidth(), primaryStage.getHeight());
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

}
