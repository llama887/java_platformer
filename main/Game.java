package main;

import java.awt.Graphics;
import scenes.Menu;

import entities.Player;
import scenes.GameState;
import scenes.Level;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread thread;
    private final int FPS = 120;
    private final int UPS = 200;

    public final static int TILE_SIZE_DEFAULT = 32;
    public final static float SCALE = 1.5f;
    public final static int TILE_SIZE = (int) (TILE_SIZE_DEFAULT * SCALE);
    public final static int WIDTH_IN_TILES = 26;
    public final static int HEIGHT_IN_TILES = 14;
    public final static int GAME_WIDTH = WIDTH_IN_TILES * TILE_SIZE;
    public final static int GAME_HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;

    public Menu menu;
    public Level level1;

    public Game() {
        gamePanel = new GamePanel(this);
        initialize();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        thread = new Thread(this);
        thread.start();
    }

    public void initialize() {
        menu = new Menu(gamePanel);
        Player player = new Player(100, 200, 1.1f, Game.SCALE * 64, Game.SCALE * 40);
        level1 = new Level(player, 0.028f * Game.SCALE, "assets/outside_sprites.png", "assets/level_one_data.png",
                gamePanel);
        GameState.setState(GameState.MENU, gamePanel);
    }

    public void update() {
        switch (GameState.getState()) {
            case PLAYING:
                level1.update();
                break;
            case MENU:
                menu.update();
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.getState()) {
            case PLAYING:
                level1.render(g);
                break;
            case MENU:
                menu.render(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() { // game loop
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
        long previousTime = System.nanoTime();
        double updateDelta = 0;
        double renderDelta = 0;
        while (true) {
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - previousTime;
            updateDelta += (deltaTime) / timePerUpdate;
            renderDelta += (deltaTime) / timePerFrame;
            if (updateDelta >= 1) {
                update();
                updateDelta--;
            }
            if (renderDelta >= 1) {
                gamePanel.repaint();
                renderDelta--;
            }
            previousTime = currentTime;
        }
    }

    public void windowFocusLost() {
    }
}
