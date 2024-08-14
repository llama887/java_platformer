package ui;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import utils.Renderable;

public class GameOverOverlay implements Renderable {
    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_HEIGHT, Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
        g.drawString("press ENTER to enter Main Menu", Game.GAME_WIDTH / 2, 300);
    }
}
