package main;

import java.awt.Graphics;

import entities.Player;
import scenes.GameState;
import scenes.Level1;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread thread;
    private final int FPS = 120;
    private final int UPS = 200;

    public final static int TILE_SIZE_DEFAULT = 32;
    public final static float TILE_SCALE = 1.5f;
    public final static int TILE_SIZE = (int) (TILE_SIZE_DEFAULT * TILE_SCALE);
    public final static int WIDTH_IN_TILES = 26;
    public final static int HEIGHT_IN_TILES = 14;
    public final static int GAME_WIDTH = WIDTH_IN_TILES * TILE_SIZE;
    public final static int GAME_HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;

    public Level1 level1 = new Level1();

    public Game() {
        initialize();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        thread = new Thread(this);
        thread.start();
    }

    public void initialize() {
    }

    public void update() {
        switch (GameState.state) {
            case PLAYING:
                level1.update();
                break;
            case MENU:
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case PLAYING:
                level1.render(g);
                break;
            case MENU:
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
            level1.handleInputs();
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
