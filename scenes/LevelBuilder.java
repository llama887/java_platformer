package scenes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import entities.Enemy;
import entities.Tile;
import main.Game;

public class LevelBuilder {
    public static Tile[][] generateMap(BufferedImage levelData, BufferedImage levelAtlas, int atlasWidth,
            int atlasHeight, int[] blankTiles)
            throws IndexOutOfBoundsException {
        int atlasSize = atlasWidth * atlasHeight;
        Tile[][] tileMap = new Tile[levelData.getHeight()][levelData.getWidth()];
        for (int y = 0; y < levelData.getHeight(); y++) {
            for (int x = 0; x < levelData.getWidth(); x++) {
                int value = new Color(levelData.getRGB(x, y)).getRed();
                if (value >= atlasSize) {
                    throw new IndexOutOfBoundsException("Tile value greater than the atlas size");
                } else {
                    int atlasXIndex = value % atlasWidth;
                    int atlasYIndex = value / atlasWidth;
                    int globalX = x * Game.TILE_SIZE;
                    int globalY = y * Game.TILE_SIZE;
                    BufferedImage tileSprite = levelAtlas.getSubimage(atlasXIndex * Game.TILE_SIZE_DEFAULT,
                            atlasYIndex * Game.TILE_SIZE_DEFAULT, Game.TILE_SIZE_DEFAULT, Game.TILE_SIZE_DEFAULT);
                    tileMap[y][x] = new Tile(
                            tileSprite,
                            globalX, globalY,
                            Game.TILE_SIZE, Game.TILE_SIZE);
                    for (int blankTile : blankTiles) {
                        if (value == blankTile) {
                            tileMap[y][x].getCollider().setActive(false);
                        }
                    }
                }
            }
        }
        return tileMap;
    }

    public static ArrayList<Enemy> generateEnemies(BufferedImage levelData) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int y = 0; y < levelData.getHeight(); y++) {
            for (int x = 0; x < levelData.getWidth(); x++) {
                int value = new Color(levelData.getRGB(x, y)).getGreen();
                if (value == Enemy.CRABBY_INDEX) {
                    enemies.add(new Crabby(x * Game.TILE_SIZE, y * Game.TILE_SIZE));
                }
            }
        }
        return enemies;
    }
}
