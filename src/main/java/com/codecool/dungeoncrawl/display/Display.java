package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.database.Manager;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import java.util.HashMap;

public class Display {

    private static GridPane generateUI(Label healthLabel) {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        ui.add(new Label("\nInventory:\n"), 0, 1);
        HashMap<String, Integer> inventory = Manager.getItems();
        if (inventory.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String item: inventory.keySet()) {
                sb.append(item);
                if (inventory.get(item) > 1) {
                    sb.append(" ").append(inventory.get(item));
                }
                sb.append("\n");
            }
            ui.add(new Label(sb.toString()), 0, 2);
            ui.add(new Label("\n"), 0, 3);
        } else {
            ui.add(new Label("\n"),0 ,2);
        }

        ui.add(new Label("Pick up item: F"), 0, 4);
        ui.add(new Label("Hit enemy: SPACE"), 0, 5);
        return ui;
    }

    public static Scene generateGameWindow(Label healthLabel, Canvas canvas) {
        GridPane ui = generateUI(healthLabel);
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        return new Scene(borderPane);
    }

    public static void displayGame(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }
}
