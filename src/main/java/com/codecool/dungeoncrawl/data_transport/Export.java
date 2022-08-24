package com.codecool.dungeoncrawl.data_transport;


import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Export {

    private Container container;
    private String path;

    public Export(Container container, String path) {
        this.container = container;
        this.path = path;
    }

    public void export() {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(container);
            out.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
