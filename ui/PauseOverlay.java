package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

        keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Button.mousePressed = true;
                volumnSlider.setDragging(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Button.mousePressed = false;
                volumnSlider.setDragging(false);
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
                volumnSlider.setSliderPosition(e.getX());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Button.mouseX = e.getX();
                Button.mouseY = e.getY();
            }
        };
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
