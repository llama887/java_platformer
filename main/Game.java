package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread thread;
    private final int FPS = 120;
    private final int UPS = 200;
    private final static int TILE_SIZE_DEFAULT = 32;
    public final static float SCALE = 1.5f;
    public final static int HEIGHT_IN_TILES = 14;
    public final static int LENGTH_IN_TILES = 26;
    public final static int WIDTH = LENGTH_IN_TILES * (int) (TILE_SIZE_DEFAULT * SCALE);
    public final static int HEIGHT = HEIGHT_IN_TILES * (int) (TILE_SIZE_DEFAULT * SCALE);

    public class GameObjects {
        public Player player;
    }

    public GameObjects gameObjects = new GameObjects();

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
        gameObjects.player = new Player(100, 100, 1, 128, 80);
    }

    public void update() {
        gameObjects.player.update();
    }

    public void render(Graphics g) {
        gameObjects.player.render(g);
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
        gameObjects.player.setMovementDirection(0, 0);
    }
}
