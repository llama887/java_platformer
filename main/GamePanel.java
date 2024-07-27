package main;

import javax.swing.JPanel;

import scenes.Scene;

import java.awt.Graphics;
import java.awt.Dimension;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        // set game panel size
        setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT));
    }

    public void updateInputHandler(Scene previousScene, Scene scene) {
        removeKeyListener(previousScene.getKeyListener());
        removeMouseListener(previousScene.getMouseListener());
        removeMouseMotionListener(previousScene.getMouseMotionListener());
        addKeyListener(scene.getKeyListener());
        addMouseListener(scene.getMouseListener());
        addMouseMotionListener(scene.getMouseMotionListener());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
}
