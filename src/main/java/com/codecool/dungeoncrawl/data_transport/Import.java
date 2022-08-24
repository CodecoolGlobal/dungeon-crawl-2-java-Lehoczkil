package com.codecool.dungeoncrawl.data_transport;

import com.codecool.dungeoncrawl.model.GameState;
import com.google.gson.Gson;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Import {

    private Stage primaryStage;

    public Import(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public GameState importGame() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fc.showOpenDialog(primaryStage);
        if (file != null) {
            try(Reader reader = Files.newBufferedReader(Paths.get(file.getPath()))) {
                Gson gson = new Gson();
                return gson.fromJson(reader, GameState.class);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
