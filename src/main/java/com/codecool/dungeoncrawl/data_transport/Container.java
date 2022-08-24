package com.codecool.dungeoncrawl.data_transport;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

public class Container {
    private GameState gameState;
    private PlayerModel playerModel;

    public Container(GameState gameState, PlayerModel playerModel) {
        this.gameState = gameState;
        this.playerModel = playerModel;
    }
}
