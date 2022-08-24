package com.codecool.dungeoncrawl.data_transport;


import com.codecool.dungeoncrawl.model.GameState;
import com.google.gson.Gson;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Export {

    private GameState gameState;
    private Stage primaryStage;


    public Export(GameState gameState, Stage primaryStage) {
        this.gameState = gameState;
        this.primaryStage = primaryStage;
    }

    public void exportGame() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fc.showSaveDialog(primaryStage);
        if (file != null) {
            String path = file.getPath();
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String jsonString = gson.toJson(gameState);
                out.write(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
