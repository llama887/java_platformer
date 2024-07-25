package main;

import javax.swing.JPanel;

import scenes.GameState;

import java.awt.Graphics;
import java.awt.Dimension;

public class GamePanel extends JPanel {
    private Game game;
    private boolean key_w, key_a, key_s, key_d, key_space, key_enter;

    public GamePanel(Game game) {
        this.game = game;
        // set game panel size
        setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT));
        // handle inputs
        addKeyListener(game.level1.getKeyListener());
        addMouseListener(game.level1.getMouseListener());
        addMouseMotionListener(game.level1.getMouseMotionListener());
    }

    public void updateInputHandler() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
}
