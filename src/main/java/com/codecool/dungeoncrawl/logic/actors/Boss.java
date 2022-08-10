package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Boss extends Enemy{
    public Boss(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "boss";
    }

    @Override
    public void attack(Actor enemy) {
        enemy.takeDamage(6);
    }

}


