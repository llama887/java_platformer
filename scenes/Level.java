package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import components.SceneEntities;
import entities.Crabby;
import entities.Enemy;
import entities.Player;
import entities.Tile;
import main.Game;
import main.GamePanel;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import ui.StatusBar;

import java.awt.event.MouseEvent;

public class Level extends Scene {
    public static float GRAVITY;
    private BufferedImage levelData = null;
    private Tile[][] map;
    private Player player;
    private boolean key_w, key_s, key_a, key_d, key_space, key_p, key_j, key_esc;
    private boolean paused;
    private PauseOverlay pauseOverlay;
    private final float MAX_WIDTH, MAX_HEIGHT;
    private final int MAX_WIDTH_IN_TILES, MAX_HEIGHT_IN_TILES;
    private int xLevelOffset;
    private int yLevelOffset;
    private final int LEFT_BOARDER = (int) (Game.GAME_WIDTH * 0.2);
    private final int RIGHT_BOARDER = (int) (Game.GAME_WIDTH * 0.8);
    private final int TOP_BOARDER = (int) (Game.GAME_HEIGHT * 0.2);
    private final int BOTTOM_BOARDER = (int) (Game.GAME_HEIGHT * 0.8);
    private int maxTilesOffsetX, maxTilesOffsetY;
    private int maxOffsetX, maxOffsetY;
    private final String LEVEL_BACKGROUND_PATH = "assets/level_background.png";
    private BufferedImage levelBackground, bigClouds, smallClouds;
    private final String BIG_CLOUDS_PATH = "assets/big_clouds.png";
    private final int BIG_CLOUD_WIDTH_DEFAULT = 448;
    private final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
    private final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
    private final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
    private final String SMALL_CLOUDS_PATH = "assets/small_clouds.png";
    private final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
    private final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
    private final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT *
            Game.SCALE);
    private final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT *
            Game.SCALE);
    private int[] smallCloudPositionsY;
    private Random random = new Random();
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    private StatusBar status;
    private GameOverOverlay gameOverOverlay;

    public Level(Player player, float GRAVITY, String levelAtlasPath, String levelDataPath, GamePanel gamePanel) {
        super(gamePanel);
        pauseOverlay = new PauseOverlay(gamePanel, this);
        this.player = player;
        Level.GRAVITY = GRAVITY;
        BufferedImage levelAtlas = null;
        final int ATLAS_WIDTH = 12;
        final int ATLAS_HEIGHT = 4;
        try {
            levelAtlas = ImageIO.read(new File(levelAtlasPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            levelData = ImageIO.read(new File(levelDataPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            levelBackground = ImageIO.read(new File(LEVEL_BACKGROUND_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bigClouds = ImageIO.read(new File(BIG_CLOUDS_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            smallClouds = ImageIO.read(new File(SMALL_CLOUDS_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] blankTile = { 11, 13 };
        map = LevelBuilder.generateMap(levelData, levelAtlas, ATLAS_WIDTH, ATLAS_HEIGHT, blankTile);
        Enemy.map = map;
        sceneEntities.addToScene(player);
        ArrayList<Enemy> enemies = LevelBuilder.generateEnemies(levelData);
        for (Enemy enemy : enemies) {
            crabbies.add(new Crabby(enemy));
        }
        for (Crabby enemy : crabbies) {
            sceneEntities.addToScene(enemy);
        }
        Enemy.player = player;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x].getCollider().isActive()) {
                    player.getCollider().addOtherCollider(map[y][x].getCollider());
                    player.getGroundCollider().addOtherCollider(map[y][x].getCollider());
                    for (Crabby enemy : crabbies) {
                        enemy.getCollider().addOtherCollider(map[y][x].getCollider());
                        enemy.getFloorDector().addOtherCollider(map[y][x].getCollider());
                    }
                }
            }
        }
        status = new StatusBar(player);
        sceneEntities.addToScene(status);
        MAX_WIDTH_IN_TILES = map[0].length;
        MAX_WIDTH = MAX_WIDTH_IN_TILES * Game.SCALE;
        MAX_HEIGHT_IN_TILES = map.length;
        MAX_HEIGHT = MAX_HEIGHT_IN_TILES * Game.SCALE;
        maxTilesOffsetX = MAX_WIDTH_IN_TILES - Game.WIDTH_IN_TILES;
        maxTilesOffsetY = MAX_HEIGHT_IN_TILES - Game.HEIGHT_IN_TILES;
        maxOffsetX = maxTilesOffsetX * Game.TILE_SIZE;
        maxOffsetY = maxTilesOffsetY * Game.TILE_SIZE;
        smallCloudPositionsY = new int[8];
        for (int i = 0; i < smallCloudPositionsY.length; i++) {
            smallCloudPositionsY[i] = (int) (90 * Game.SCALE) + random.nextInt((int) (120
                    * Game.SCALE));
        }
        gameOverOverlay = new GameOverOverlay();
        // create input handlers
        keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        key_w = true;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        key_s = true;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        key_a = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        key_d = true;
                        break;
                    case KeyEvent.VK_J:
                        key_j = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        key_space = true;
                        break;
                    case KeyEvent.VK_P:
                        key_p = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        key_esc = true;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        key_w = false;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        key_s = false;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        key_a = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        key_d = false;
                        break;
                    case KeyEvent.VK_J:
                        key_j = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        key_space = false;
                        break;
                    case KeyEvent.VK_P:
                        key_p = false;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        key_esc = false;
                        break;
                    default:
                        break;
                }
            }

        };
        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
    }

    public void handleKeyInputs() {
        player.getPhysicsController()
                .setMovementDirection(0, 0);
        int playerXDirection = 0, playerYDirection = 0;
        if (key_w) {
            // playerYDirection--;
        }
        if (key_s) {
            player.setFastFalling(true);
        }
        if (key_a) {
            playerXDirection--;
        }
        if (key_d) {
            playerXDirection++;
        }
        if (key_space) {
            player.setJump(true);
        }
        if (key_p) {
            paused = true;
        }
        if (key_j) {
            player.setAttacking(true);
        }
        if (key_esc && player.getHealth() <= 0) {
            Game.changeScene(Game.menu, gamePanel);
        }
        player.getPhysicsController()
                .setMovementDirection(playerXDirection, playerYDirection);
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        g.drawImage(levelBackground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigClouds, 0 + i * BIG_CLOUD_WIDTH - (int) (xLevelOffset * 0.3), (int) (204 * Game.SCALE),
                    BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT,
                    null);
        }
        for (int i = 0; i < smallCloudPositionsY.length; i++) {
            g.drawImage(smallClouds, SMALL_CLOUD_WIDTH * 3 * i - (int) (xLevelOffset * 0.6), smallCloudPositionsY[i],
                    SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x].render(g, xLevelOffset, yLevelOffset);
            }
        }
        sceneEntities.render(g, xLevelOffset, yLevelOffset);
        if (player.getHealth() <= 0) {
            gameOverOverlay.render(g, xLevelOffset, yLevelOffset);
        } else if (paused) {
            pauseOverlay.render(g, xLevelOffset, yLevelOffset);
        }
    }

    @Override
    public void update() {
        handleKeyInputs();
        if (player.getHealth() <= 0) {
            return;
        } else if (paused) {
            pauseOverlay.update();
            return;
        }
        sceneEntities.update();
        int playerX = (int) player.getPhysicsController().getX();
        int playerY = (int) player.getPhysicsController().getY();
        int deltaX = playerX - xLevelOffset;
        int deltaY = playerY - yLevelOffset;
        if (deltaX < LEFT_BOARDER) {
            xLevelOffset += deltaX - LEFT_BOARDER;
        } else if (deltaX > RIGHT_BOARDER) {
            xLevelOffset += deltaX - RIGHT_BOARDER;
        }
        if (deltaY < TOP_BOARDER) {
            yLevelOffset += deltaY - TOP_BOARDER;
        } else if (deltaY > BOTTOM_BOARDER) {
            yLevelOffset += deltaY - BOTTOM_BOARDER;
        }
        if (xLevelOffset < 0) {
            xLevelOffset = 0;
        } else if (xLevelOffset > maxOffsetX) {
            xLevelOffset = maxOffsetX;
        }
        if (yLevelOffset < 0) {
            yLevelOffset = 0;
        } else if (yLevelOffset > maxOffsetY) {
            yLevelOffset = maxOffsetY;
        }
    }

    public SceneEntities getSceneEntities() {
        return sceneEntities;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public MouseListener getMouseListener() {
        return pauseOverlay.getMouseListener();
    }

    @Override
    public MouseMotionListener getMouseMotionListener() {
        return pauseOverlay.getMouseMotionListener();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
