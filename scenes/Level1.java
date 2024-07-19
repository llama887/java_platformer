package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Player;
import main.Game;
import utils.Renderable;
import utils.Updateable;

public class Level1 implements Renderable, Updateable {
    private Scene scene = new Scene();
    private BufferedImage[] tileMap;
    private BufferedImage levelData = null;
    private int[][] map;
    private Player player = new Player(100, 100, 0.75, 2 * 64, 2 * 40);

    public Level1() {
        BufferedImage levelAtlas = null;
        final int ATLAS_WIDTH = 12;
        final int ATLAS_HEIGHT = 4;
        final int ATLAS_SIZE = ATLAS_WIDTH * ATLAS_HEIGHT;
        try {
            levelAtlas = ImageIO.read(new File("assets/outside_sprites.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            levelData = ImageIO.read(new File("assets/level_one_data.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        map = LevelBuilder.generateMap(levelData, ATLAS_SIZE);
        scene.addToScene(player);
        tileMap = new BufferedImage[ATLAS_SIZE];
        for (int y = 0; y < ATLAS_HEIGHT; y++) {
            for (int x = 0; x < ATLAS_WIDTH; x++) {
                tileMap[y * ATLAS_WIDTH + x] = levelAtlas.getSubimage(x * Game.TILE_SIZE_DEFAULT,
                        y * Game.TILE_SIZE_DEFAULT,
                        Game.TILE_SIZE_DEFAULT, Game.TILE_SIZE_DEFAULT);
            }
        }
    }

    @Override
    public BufferedImage render(Graphics g) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                g.drawImage(tileMap[map[y][x]], x * Game.TILE_SIZE, y * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,
                        null);
            }
        }
        scene.render(g);
        return tileMap[2];
    }

    @Override
    public void update() {
        scene.update();
    }

    public Scene getScene() {
        return scene;
    }

    public Player getPlayer() {
        return player;
    }
}
