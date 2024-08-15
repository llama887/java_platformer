package scenes;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.GamePanel;
import ui.Button;
import ui.Button.ButtonState;
import ui.URMButton;

public class OptionsMenu extends Scene {
    BufferedImage background, menuBackground;
    private final String BACKGROUND_PATH = "assets/options_background.png",
            MENU_BACKGROUND_PATH = "assets/background_menu.png";
    private final int MENU_X, MENU_Y, MENU_WIDTH, MENU_HEIGHT;
    private URMButton menuButton = new URMButton((int) (387 * Game.SCALE), (int) (325 * Game.SCALE), 2);

    public OptionsMenu(GamePanel gamePanel) {
        super(gamePanel);
        try {
            background = ImageIO.read(new File(BACKGROUND_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            menuBackground = ImageIO.read(new File(MENU_BACKGROUND_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MENU_WIDTH = (int) (background.getWidth() * Game.SCALE);
        MENU_HEIGHT = (int) (background.getHeight() * Game.SCALE);
        MENU_X = Game.GAME_WIDTH / 2 - MENU_WIDTH / 2;
        MENU_Y = (int) (33 * Game.SCALE);
    }

    @Override
    public void update() {
        menuButton.update();
        Game.audioOptions.update();
        if (menuButton.getButtonState() == ButtonState.ACTIVATED) {
            Game.changeScene(Game.menu, gamePanel);
        }
        Button.resetMouseStates();
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        g.drawImage(menuBackground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(background, MENU_X, MENU_Y, MENU_WIDTH, MENU_HEIGHT, null);
        Game.audioOptions.render(g, 0, 0);
        menuButton.render(g, 0, 0);
    }

    @Override
    public MouseListener getMouseListener() {
        return Game.audioOptions.getMouseListener();
    }

    @Override
    public MouseMotionListener getMouseMotionListener() {
        return Game.audioOptions.getMouseMotionListener();
    }
}
