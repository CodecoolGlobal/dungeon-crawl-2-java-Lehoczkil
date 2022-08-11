package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static double TILE_WIDTH = 48;
    public static int TXT_TILE_WIDTH = 32;
    public static int MARGIN = 2;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TXT_TILE_WIDTH + MARGIN);
            y = j * (TXT_TILE_WIDTH + MARGIN);
            w = TXT_TILE_WIDTH;
            h = TXT_TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(25, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("sword", new Tile(0, 31));
        tileMap.put("key", new Tile(17, 23));
        tileMap.put("closed door", new Tile(0, 9));
        tileMap.put("open door", new Tile(2, 9));
        tileMap.put("armor", new Tile(1, 23));
        tileMap.put("boss", new Tile(30,6));
        tileMap.put("ghost", new Tile(27, 6));
        tileMap.put("heal", new Tile(23,22));
    }

    public static void updatePlayerImage(int col) {
        tileMap.replace("player", new Tile(col, 0));
    }

    public static void drawTile(GraphicsContext context, Drawable d, double x, double y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x, y, TILE_WIDTH, TILE_WIDTH);
    }

    public static void setTileWidth(double tileWidth) {
        TILE_WIDTH = tileWidth;
    }
}
