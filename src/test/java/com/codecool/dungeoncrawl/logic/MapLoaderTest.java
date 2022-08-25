package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MapLoaderTest {

    GameMap referenceMap = new GameMap(3, 3, CellType.WALL);

    @Test
    void cratesCorrectMapFromTextFile() {
        Player player = new Player(referenceMap.getCell(0, 0));
        GameMap map = MapLoader.loadMap("/test_map.txt", player);
        assertEquals(referenceMap.getCell(0, 0).getTileName(), map.getCell(0, 0).getTileName());
        assertEquals(referenceMap.getCell(0, 1).getTileName(), map.getCell(0, 1).getTileName());
        assertEquals(referenceMap.getCell(0, 2).getTileName(), map.getCell(0, 2).getTileName());
        assertEquals(referenceMap.getCell(1, 0).getTileName(), map.getCell(1, 0).getTileName());
        assertEquals(referenceMap.getCell(1, 1).getTileName(), map.getCell(1, 1).getTileName());
        assertEquals(referenceMap.getCell(1, 2).getTileName(), map.getCell(1, 2).getTileName());
        assertEquals(referenceMap.getCell(2, 0).getTileName(), map.getCell(2, 0).getTileName());
        assertEquals(referenceMap.getCell(2, 1).getTileName(), map.getCell(2, 1).getTileName());
        assertEquals(referenceMap.getCell(2, 2).getTileName(), map.getCell(2, 2).getTileName());
    }
}
