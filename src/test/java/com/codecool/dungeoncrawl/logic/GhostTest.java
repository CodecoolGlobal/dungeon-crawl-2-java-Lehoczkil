package com.codecool.dungeoncrawl.logic;
import com.codecool.dungeoncrawl.logic.actors.Boss;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GhostTest {

    GameMap gameMap = new GameMap(3,3, CellType.FLOOR);

    @Test
    void creatingGhostInstanceDoesNotThrowsError() {
        assertDoesNotThrow(() -> new Ghost(gameMap.getCell(2,1)));
    }

    @Test
    void ghostCanAttackPlayer() {
        Ghost ghost = new Ghost(gameMap.getCell(2,1));
        Player player = new Player(gameMap.getCell(1,1));
        ghost.attack(player);
        assertEquals(6, player.getHealth());
    }

    @Test
    void ghostReturnsItsCorrectName() {
        Ghost ghost = new Ghost(gameMap.getCell(2,1));
        assertEquals("ghost", ghost.getTileName());
    }

}
