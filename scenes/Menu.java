package scenes;

import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import components.SceneEntities;
import main.Game;
import main.GamePanel;
import ui.Button;
import ui.MenuButton;
import ui.Button.ButtonState;

public class Menu extends Scene {
    private MenuButton playButton;
    private MenuButton optionsButton;
    private MenuButton quitButton;
    private float mouseX, mouseY;
    private boolean mousePressed, mouseReleased;
    private BufferedImage menuBackground, background;
    private final String menuBackgroundPath = "assets/menu_background.png",
            backgroundPath = "assets/background_menu.png";
    private final int menuX, menuY, menuWidth, menuHeight;

    public Menu(GamePanel gamePanel) {
        super(gamePanel);
        playButton = new MenuButton(0, (int) (160 * Game.SCALE), Game.level1, 0);
        optionsButton = new MenuButton(0, (int) (230 * Game.SCALE), Game.menu, 1);
        quitButton = new MenuButton(0, (int) (300 * Game.SCALE), Game.menu, 2);
        playButton.centerX();
        optionsButton.centerX();
        quitButton.centerX();
        sceneEntities.addToScene(playButton);
        sceneEntities.addToScene(optionsButton);
        sceneEntities.addToScene(quitButton);
        try {
            menuBackground = ImageIO.read(new File(menuBackgroundPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            background = ImageIO.read(new File(backgroundPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        menuWidth = (int) (menuBackground.getWidth() * Game.SCALE);
        menuHeight = (int) (menuBackground.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = Game.GAME_HEIGHT / 2 - menuHeight / 2;
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
        if (playButton.getButtonState() == ButtonState.ACTIVATED) {
            Game.changeScene(Game.level1, gamePanel);
        }
        Button.resetMouseStates();
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(menuBackground, menuX, menuY, menuWidth, menuHeight, null);
        sceneEntities.render(g, 0, 0);
    }
}
