package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import main.Game;
import main.GamePanel;
import scenes.Level;
import scenes.Scene;
import ui.Button.ButtonState;

public class LevelCompletedOverlay extends Scene {
    private final int overlayX, overlayY, overlayWidth, overlayHeight;
    private BufferedImage background = null;
    private final String BACKGROUND_PATH = "assets/completed_sprite.png";
    private URMButton nextButton = new URMButton((int) (445 * Game.SCALE), (int) (195 * Game.SCALE), 0);
    private URMButton menuButton = new URMButton((int) (330 * Game.SCALE), (int) (195 * Game.SCALE), 2);
    private Level level;

    public LevelCompletedOverlay(GamePanel gamePanel, Level level) {
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
        overlayY = (int) (75 * Game.SCALE);

        sceneEntities.addToScene(nextButton);
        sceneEntities.addToScene(menuButton);
        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Button.mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Button.mousePressed = false;
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
                Button.mouseX = e.getX();
                Button.mouseY = e.getY();
            }
        };
    }

    @Override
    public void update() {
        sceneEntities.update();
        if (menuButton.getButtonState() == ButtonState.ACTIVATED) {
            Game.changeScene(Game.menu, gamePanel);
        }
        if (nextButton.getButtonState() == ButtonState.ACTIVATED) {
            Game.changeScene(level.getNextScene(), gamePanel);
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
