package com.codecool.dungeoncrawl.display;

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
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        ui.add(new Label("\nInventory:\n"), 0, 1);
            ui.add(playerInventory, 0, 2);
            ui.add(new Label("\n"), 0, 3);

        ui.add(new Label("Pick up item: F"), 0, 4);
        ui.add(new Label("Hit enemy: SPACE"), 0, 5);
        return ui;
    }

    public static Scene generateGameWindow(Label healthLabel, Canvas canvas, Label inventory) {
        GridPane ui = generateUI(healthLabel, inventory);
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        return new Scene(borderPane);
    }

    public static Scene createEndGameScene() {
        BorderPane gameOver = new BorderPane();
        gameOver.setPrefWidth(1000);
        gameOver.setPrefHeight(640);
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
        primaryStage.show();
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
