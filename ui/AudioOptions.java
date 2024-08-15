package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.GamePanel;
import scenes.Scene;

public class AudioOptions extends Scene {
    private SoundButton musicButton = new SoundButton((int) (450 * Game.SCALE), (int) (140 * Game.SCALE));
    private SoundButton sfxButton = new SoundButton((int) (450 * Game.SCALE), (int) (186 * Game.SCALE));
    private VolumnSlider volumnSlider = new VolumnSlider((int) (309 * Game.SCALE), (int) (278 * Game.SCALE));

    public AudioOptions(GamePanel gamePanel) {
        super(gamePanel);
        keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Button.mousePressed = true;
                volumnSlider.setDragging(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Button.mousePressed = false;
                volumnSlider.setDragging(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };

        mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                volumnSlider.setSliderPosition(e.getX());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Button.mouseX = e.getX();
                Button.mouseY = e.getY();
            }
        };
    }

    @Override
    public void update() {
        /*
         * if (musicButton.getButtonState() == ButtonState.ACTIVATED) {
         * Game.musicEnabled = !Game.musicEnabled;
         * musicButton.setSoundEnabled(Game.musicEnabled);
         * }
         * if (sfxButton.getButtonState() == ButtonState.ACTIVATED) {
         * Game.sfxEnabled = !Game.sfxEnabled;
         * sfxButton.setSoundEnabled(Game.sfxEnabled);
         * }
         */
        volumnSlider.update();
        musicButton.update();
        sfxButton.update();
        Button.resetMouseStates();
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        volumnSlider.render(g, 0, 0);
        musicButton.render(g, 0, 0);
        sfxButton.render(g, 0, 0);
    }
}
