package com.codecool.dungeoncrawl.logic.actors;

public enum PlayerState {
    NAKED(25),
    ARMED(27),
    ARMORED(30),
    FULL(28);

    private final int tileNumber;
    PlayerState(int i) {
        tileNumber = i;
    }

    public int getTileNumber() {
        return tileNumber;
    }
}
