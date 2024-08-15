package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.GamePanel;
import scenes.Level;
import scenes.Scene;
import ui.Button.ButtonState;

public class PauseOverlay extends Scene {
    private final int overlayX, overlayY, overlayWidth, overlayHeight;
    private BufferedImage background = null;
    private final String BACKGROUND_PATH = "assets/pause_overlay.png";
    private URMButton unpauseButton = new URMButton((int) (462 * Game.SCALE), (int) (325 * Game.SCALE), 0);
    private URMButton replayButton = new URMButton((int) (387 * Game.SCALE), (int) (325 * Game.SCALE), 1);
    private URMButton menuButton = new URMButton((int) (313 * Game.SCALE), (int) (325 * Game.SCALE), 2);
    private Level level;

    public PauseOverlay(GamePanel gamePanel, Level level) {
        super(gamePanel);
        this.level = level;
        try {
            background = ImageIO.read(new File(BACKGROUND_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        overlayWidth = (int) (background.getWidth() * Game.SCALE);
        overlayHeight = (int) (background.getHeight() * Game.SCALE);
        overlayX = Game.GAME_WIDTH / 2 - overlayWidth / 2;
        overlayY = (int) (25 * Game.SCALE);
        sceneEntities.addToScene(menuButton);
        sceneEntities.addToScene(replayButton);
        sceneEntities.addToScene(unpauseButton);
    }

    @Override
    public void update() {
        sceneEntities.update();
        Game.audioOptions.update();
        if (menuButton.getButtonState() == ButtonState.ACTIVATED) {
            Game.changeScene(Game.menu, gamePanel);
        }
        if (replayButton.getButtonState() == ButtonState.ACTIVATED) {
            level.initialize();
        }
        if (unpauseButton.getButtonState() == ButtonState.ACTIVATED) {
            level.setPaused(false);
        }
        Button.resetMouseStates();
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        g.setColor(new Color(0, 0, 0, 150)); // darken pause background
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(background, overlayX, overlayY, overlayWidth, overlayHeight,
                gamePanel);
        Game.audioOptions.render(g, 0, 0);
        sceneEntities.render(g, 0, 0);
    }

}
