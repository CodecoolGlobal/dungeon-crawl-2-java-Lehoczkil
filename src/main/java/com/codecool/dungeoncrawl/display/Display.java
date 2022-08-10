package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.database.Manager;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.HashMap;

public class Display {

    private static GridPane generateUI(Label healthLabel, Label playerInventory) {
        int fontSize = 50;
        GridPane ui = new GridPane();
        ui.setPrefWidth(600);
        ui.setPadding(new Insets(10));

        Label health = new Label("Health: ");
        health.setTextFill(Color.WHITE);
        health.setStyle("-fx-font-size: " + fontSize);
        healthLabel.setTextFill(Color.WHITE);
        healthLabel.setStyle("-fx-font-size: " + fontSize);

        ui.add(health, 0, 0);
        ui.add(healthLabel, 1, 0);

        Label inventory = new Label("\nInventory:\n");
        inventory.setTextFill(Color.WHITE);
        inventory.setStyle("-fx-font-size: " + fontSize);
        playerInventory.setTextFill(Color.WHITE);
        playerInventory.setStyle("-fx-font-size: " + fontSize);

        ui.add(inventory, 0, 1);
        ui.add(playerInventory, 0, 2);
        ui.add(new Label("\n"), 0, 3);

        Label pickUp = new Label("Pick up item: F");
        pickUp.setTextFill(Color.WHITE);
        pickUp.setStyle("-fx-font-size: " + fontSize);
        Label attack = new Label("Hit enemy: SPACE");
        attack.setTextFill(Color.WHITE);
        attack.setStyle("-fx-font-size: " + fontSize);

        ui.add(pickUp, 0, 4);
        ui.add(attack, 0, 5);
        return ui;
    }

    public static Scene generateGameWindow(Label healthLabel, Canvas canvas, Label inventory) {
        GridPane ui = generateUI(healthLabel, inventory);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #472d3c");

        borderPane.setLeft(canvas);
        borderPane.setRight(ui);

        return new Scene(borderPane);
    }

    public static Scene createEndGameScene(Stage stage) {
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

    public static void displayGame(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.setFullScreen(true);
        primaryStage.show();
        Tiles.setTileWidth(primaryStage.getHeight()/20);
    }

    public static void updateInventory (Label inventory) {
        HashMap<String, Integer> items = Manager.getItems();
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
}
