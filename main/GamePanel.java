package main;

import javax.swing.JPanel;

import scenes.GameState;

import java.awt.Graphics;
import java.awt.Dimension;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        // set game panel size
        setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT));
    }

    public void updateInputHandler(GameState previous, GameState target) {
        switch (previous) {
            case PLAYING:
                removeKeyListener(game.level1.getKeyListener());
                removeMouseListener(game.level1.getMouseListener());
                removeMouseMotionListener(game.level1.getMouseMotionListener());
                break;
            case MENU:
                removeKeyListener(game.menu.getKeyListener());
                removeMouseListener(game.menu.getMouseListener());
                removeMouseMotionListener(game.menu.getMouseMotionListener());
                break;
            default:
                break;
        }
        switch (target) {
            case PLAYING:
                addKeyListener(game.level1.getKeyListener());
                addMouseListener(game.level1.getMouseListener());
                addMouseMotionListener(game.level1.getMouseMotionListener());
                break;
            case MENU:
                addKeyListener(game.menu.getKeyListener());
                addMouseListener(game.menu.getMouseListener());
                addMouseMotionListener(game.menu.getMouseMotionListener());
                break;
            default:
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
}
