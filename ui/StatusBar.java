package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import entities.Player;
import main.Game;
import utils.Renderable;
import utils.Updateable;

public class StatusBar implements Renderable, Updateable {
    private final int STATUS_BAR_WIDTH = (int) (192 * Game.SCALE);
    private final int STATUS_BAR_HEIGHT = (int) (58 * Game.SCALE);
    private final int statusBarX = (int) (10 * Game.SCALE);
    private final int statusBarY = (int) (10 * Game.SCALE);
    private final int HEALTH_BAR_WIDTH = (int) (150 * Game.SCALE);
    private final int HEALTH_BAR_HEIGHT = (int) (4 * Game.SCALE);
    private final int HEALTH_BAR_X_START = (int) (34 * Game.SCALE);
    private final int HEALTH_BAR_Y_START = (int) (14 * Game.SCALE);
    private int currentHealthBarWidth = HEALTH_BAR_WIDTH;
    private final String STATUS_BAR_PATH = "assets/health_power_bar.png";
    private BufferedImage statusBar;
    private Player player;

    public StatusBar(Player player) {
        try {
            statusBar = ImageIO.read(new File(STATUS_BAR_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.player = player;
    }

    @Override
    public void update() {
        currentHealthBarWidth = (int) ((player.getHealth() / (float) player.getMaxHealth()) * HEALTH_BAR_WIDTH);
        return;
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        g.drawImage(statusBar, statusBarX, statusBarY, STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT, null);
        g.setColor(Color.RED);
        g.fillRect(HEALTH_BAR_X_START + statusBarX, HEALTH_BAR_Y_START + statusBarY, HEALTH_BAR_WIDTH,
                HEALTH_BAR_HEIGHT);
    }
}
