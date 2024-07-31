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

public class PauseOverlay extends Scene {
    private final int overlayX, overlayY, overlayWidth, overlayHeight;
    private BufferedImage background = null;
    private final String BACKGROUND_PATH = "assets/pause_overlay.png";
    private float mouseX, mouseY;
    private boolean mouseClicked;
    private SoundButton musicButton = new SoundButton((int) (450 * Game.SCALE), (int) (140 * Game.SCALE));
    private SoundButton sfxButton = new SoundButton((int) (450 * Game.SCALE), (int) (186 * Game.SCALE));

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
                mouseClicked = true;
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                mouseClicked = false;
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
                mouseX = e.getX();
                mouseY = e.getY();
            }
        };
    }

    @Override
    public void update() {
        musicButton.setMouseState(mouseX, mouseY, mouseClicked);
        sfxButton.setMouseState(mouseX, mouseY, mouseClicked);
        sceneEntities.update();
    }

    @Override
    public BufferedImage render(Graphics g) {
        g.drawImage(background, overlayX, overlayY, overlayWidth, overlayHeight, gamePanel);
        sceneEntities.render(g);
        return background;
    }

}
