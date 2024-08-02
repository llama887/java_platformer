package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import components.SceneEntities;
import entities.Player;
import entities.Tile;
import main.Game;
import main.GamePanel;
import ui.PauseOverlay;

import java.awt.event.MouseEvent;

public class Level extends Scene {
    public static float GRAVITY;
    private BufferedImage levelData = null;
    private Tile[][] map;
    private Player player = new Player(100, 200, 1f, Game.SCALE * 64, Game.SCALE * 40);
    private boolean key_w, key_s, key_a, key_d, key_space, key_enter;
    private boolean paused;
    private PauseOverlay pauseOverlay;

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
        int[] blankTile = { 11, 13 };
        map = LevelBuilder.generateMap(levelData, levelAtlas, ATLAS_WIDTH, ATLAS_HEIGHT, blankTile);
        sceneEntities.addToScene(player);
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x].getCollider().isActive()) {
                    player.getCollider().addOtherCollider(map[y][x].getCollider());
                    player.getGroundCollider().addOtherCollider(map[y][x].getCollider());
                }
            }
        }
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
                    case KeyEvent.VK_SPACE:
                        key_space = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        key_enter = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
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
                    case KeyEvent.VK_SPACE:
                        key_space = false;
                        break;
                    case KeyEvent.VK_ENTER:
                        key_enter = false;
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
        if (key_enter) {
            paused = true;
        }
        player.getPhysicsController()
                .setMovementDirection(playerXDirection, playerYDirection);
    }

    @Override
    public BufferedImage render(Graphics g) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x].render(g);
            }
        }
        sceneEntities.render(g);
        if (paused) {
            pauseOverlay.render(g);
        }
        return levelData;
    }

    @Override
    public void update() {
        handleKeyInputs();
        if (paused) {
            pauseOverlay.update();
            return;
        }
        sceneEntities.update();
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
