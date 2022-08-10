package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Enemy{
    public Ghost(Cell cell) {
        super(cell);
    }

    @Override
    public void attack(Actor enemy) {
        enemy.takeDamage(4);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }


}
