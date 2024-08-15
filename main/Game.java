package main;

import java.awt.Graphics;
import java.util.Optional;

import scenes.Menu;
import scenes.OptionsMenu;
import scenes.Scene;
import ui.AudioOptions;
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

    public static Menu menu;
    public static OptionsMenu optionsMenu;
    public static Level level1;
    public static Level level2;
    public static Level level3;
    public static Level[] levels = { level1, level2, level3 };
    public static Optional<Scene> currentScene = Optional.empty();
    public static AudioOptions audioOptions;

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
        audioOptions = new AudioOptions(gamePanel);
        menu = new Menu(gamePanel);
        optionsMenu = new OptionsMenu(gamePanel);
        level1 = new Level(0.028f * Game.SCALE, "assets/outside_sprites.png", "assets/1.png",
                gamePanel);
        level2 = new Level(0.028f * Game.SCALE, "assets/outside_sprites.png", "assets/2.png",
                gamePanel);
        level3 = new Level(0.028f * Game.SCALE, "assets/outside_sprites.png", "assets/3.png",
                gamePanel);
        level1.setNextScene(level2);
        level2.setNextScene(level3);
        level3.setNextScene(menu);
        Game.changeScene(menu, gamePanel);
    }

    public void update() {
        currentScene.get().update();
    }

    public void render(Graphics g) {
        currentScene.get().render(g, 0, 0);
    }

    public static void changeScene(Scene newScene, GamePanel gamePanel) {
        if (currentScene.isEmpty() || currentScene.get() != newScene) {
            Scene previousScene = currentScene.orElse(menu);
            currentScene = Optional.of(newScene);
            currentScene.get().initialize();
            gamePanel.updateInputHandler(previousScene, newScene);
        }
    }

    public Scene getCurrentScene() {
        return currentScene.get();
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

    public Level getLevel1() {
        return level1;
    }

}
