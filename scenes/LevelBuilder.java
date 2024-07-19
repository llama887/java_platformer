package scenes;

import java.awt.Color;
import java.awt.image.BufferedImage;

import main.Game;

public class LevelBuilder {
    public static int[][] generateMap(BufferedImage levelData, int atlasSize) throws IndexOutOfBoundsException {
        int[][] map = new int[Game.HEIGHT_IN_TILES][Game.WIDTH_IN_TILES];
        for (int y = 0; y < levelData.getHeight(); y++) {
            for (int x = 0; x < levelData.getWidth(); x++) {
                int value = new Color(levelData.getRGB(x, y)).getRed();
                if (value >= atlasSize) {
                    throw new IndexOutOfBoundsException("Tile value greater than the atlas size");
                } else {
                    map[y][x] = value;
                }
            }
        }
        return map;
    }
}
