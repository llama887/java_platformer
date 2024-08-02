package ui;

import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.GamePanel;
import scenes.Scene;
import ui.Button.ButtonState;

public class PauseOverlay extends Scene {
    private final int overlayX, overlayY, overlayWidth, overlayHeight;
    private BufferedImage background = null;
    private final String BACKGROUND_PATH = "assets/pause_overlay.png";
    private SoundButton musicButton = new SoundButton((int) (450 * Game.SCALE), (int) (140 * Game.SCALE));
    private SoundButton sfxButton = new SoundButton((int) (450 * Game.SCALE), (int) (186 * Game.SCALE));
    private URMButton unpauseButton = new URMButton((int) (462 * Game.SCALE), (int) (325 * Game.SCALE), 0);
    private URMButton replayButton = new URMButton((int) (387 * Game.SCALE), (int) (325 * Game.SCALE), 1);
    private URMButton menuButton = new URMButton((int) (313 * Game.SCALE), (int) (325 * Game.SCALE), 2);

    public PauseOverlay(GamePanel gamePanel) {
        super(gamePanel);
        try {
            background = ImageIO.read(new File(BACKGROUND_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        overlayWidth = (int) (background.getWidth() * Game.SCALE);
        overlayHeight = (int) (background.getHeight() * Game.SCALE);
        overlayX = Game.GAME_WIDTH / 2 - overlayWidth / 2;
        overlayY = (int) (25 * Game.SCALE);
        sceneEntities.addToScene(musicButton);
        sceneEntities.addToScene(sfxButton);
        sceneEntities.addToScene(menuButton);
        sceneEntities.addToScene(replayButton);
        sceneEntities.addToScene(unpauseButton);

        keyListener = new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
            }
        };

        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Button.mouseClicked = true;
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Button.mousePressed = true;
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                Button.mousePressed = false;
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
            }
        };

        mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                Button.mouseX = e.getX();
                Button.mouseY = e.getY();
            }
        };
    }

    @Override
    public void update() {
        sceneEntities.update();
        Button.resetMouseStates();
        if (menuButton.getButtonState() == ButtonState.ACTIVATED) {
            Game.changeScene(Game.menu, gamePanel);
        }
    }

    @Override
    public BufferedImage render(Graphics g) {
        g.drawImage(background, overlayX, overlayY, overlayWidth, overlayHeight, gamePanel);
        sceneEntities.render(g);
        return background;
    }

}
