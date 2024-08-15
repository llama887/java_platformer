package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.AudioPlayer;
import main.Game;
import main.GamePanel;
import scenes.Level;
import scenes.Scene;
import ui.Button.ButtonState;

public class GameOverOverlay extends Scene {
    private final int overlayX, overlayY, overlayWidth, overlayHeight;
    private BufferedImage background = null;
    private final String BACKGROUND_PATH = "assets/death_screen.png";
    private URMButton nextButton = new URMButton((int) (440 * Game.SCALE), (int) (195 * Game.SCALE), 0);
    private URMButton menuButton = new URMButton((int) (335 * Game.SCALE), (int) (195 * Game.SCALE), 2);
    private Level level;

    public GameOverOverlay(GamePanel gamePanel, Level level) {
        super(gamePanel);
        this.level = level;
        try {
            background = ImageIO.read(new File(BACKGROUND_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }

        overlayWidth = (int) (background.getWidth() * Game.SCALE);
        overlayHeight = (int) (background.getHeight() * Game.SCALE);
        overlayX = Game.GAME_WIDTH / 2 - overlayWidth / 2;
        overlayY = (int) (100 * Game.SCALE);

        sceneEntities.addToScene(nextButton);
        sceneEntities.addToScene(menuButton);
    }

    @Override
    public void update() {
        sceneEntities.update();
        if (menuButton.getButtonState() == ButtonState.ACTIVATED) {
            Game.changeScene(Game.menu, gamePanel);
        }
        if (nextButton.getButtonState() == ButtonState.ACTIVATED) {
            level.initialize();
        }
        Button.resetMouseStates();
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(background, overlayX, overlayY, overlayWidth, overlayHeight, null);
        menuButton.render(g, 0, 0);
        nextButton.render(g, 0, 0);
    }
}
