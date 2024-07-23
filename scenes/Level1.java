package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Player;
import entities.Tile;
import main.Game;
import utils.Renderable;
import utils.Updateable;

public class Level1 implements Renderable, Updateable {
    public static final float GRAVITY = 1f * Game.TILE_SCALE;
    private Scene scene = new Scene();
    private BufferedImage levelData = null;
    private Tile[][] map;
    private Player player = new Player(100, 200, 0.75f, Game.TILE_SCALE * 64, Game.TILE_SCALE * 40);

    public Level1() {
        BufferedImage levelAtlas = null;
        final int ATLAS_WIDTH = 12;
        final int ATLAS_HEIGHT = 4;
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
        int[] blankTile = { 11 };
        map = LevelBuilder.generateMap(levelData, levelAtlas, ATLAS_WIDTH, ATLAS_HEIGHT, blankTile);
        scene.addToScene(player);
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                player.getCollider().addOtherCollider(map[y][x].getCollider());
                player.getGroundCollider().addOtherCollider(map[y][x].getCollider());
            }
        }
    }

    @Override
    public BufferedImage render(Graphics g) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x].render(g);
            }
        }
        scene.render(g);
        return levelData;
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
