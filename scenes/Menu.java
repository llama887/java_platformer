package scenes;

import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import components.SceneEntities;
import main.Game;
import main.GamePanel;
import ui.MenuButton;
import ui.MenuButton.ButtonState;

public class Menu extends Scene {
    private MenuButton playButton;
    private MenuButton optionsButton;
    private MenuButton quitButton;
    private float mouseX, mouseY;
    private boolean mouseClicked;

    public Menu(GamePanel gamePanel) {
        super(gamePanel);
        playButton = new MenuButton(0, (int) (150 * Game.SCALE), GameState.PLAYING, 0);
        optionsButton = new MenuButton(0, (int) (220 * Game.SCALE), GameState.MENU, 1);
        quitButton = new MenuButton(0, (int) (290 * Game.SCALE), GameState.MENU, 2);
        playButton.centerX();
        optionsButton.centerX();
        quitButton.centerX();
        sceneEntities.addToScene(playButton);
        sceneEntities.addToScene(optionsButton);
        sceneEntities.addToScene(quitButton);
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
        playButton.setMouseState(mouseX, mouseY, mouseClicked);
        optionsButton.setMouseState(mouseX, mouseY, mouseClicked);
        quitButton.setMouseState(mouseX, mouseY, mouseClicked);
        sceneEntities.update();
        if (playButton.getButtonState() == ButtonState.CLICKED) {
            GameState.setState(GameState.PLAYING, gamePanel);
        } else if (optionsButton.getButtonState() == ButtonState.CLICKED) {
            GameState.setState(GameState.OPTIONS, gamePanel);
        } else if (quitButton.getButtonState() == ButtonState.CLICKED) {
            GameState.setState(GameState.QUIT, gamePanel);
        }
    }

    @Override
    public BufferedImage render(Graphics g) {
        sceneEntities.render(g);
        return null;
    }
}
