package com.codecool.dungeoncrawl.data_transport;


import com.codecool.dungeoncrawl.model.GameState;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Export {

    private GameState gameState;
    private String path;

    public Export(GameState gameState, String path) {
         this.gameState= gameState;
        this.path = path;
    }

    public Export(GameState gameState) {
        this.gameState = gameState;
        this.path = "src/main/resources/test.json";
    }

    public void export() {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(gameState);
            out.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
