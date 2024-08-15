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
                float previousVolumn = volumnSlider.getPercentage();
                volumnSlider.setSliderPosition(e.getX());
                float newVolumn = volumnSlider.getPercentage();
                if (previousVolumn != newVolumn)
                    Game.audioPlayer.setVolume(newVolumn);
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
        volumnSlider.update();
        musicButton.update();
        Game.audioPlayer.toggleSongMute(musicButton.isMuted());
        sfxButton.update();
        Game.audioPlayer.toggleEffectMute(sfxButton.isMuted());
        Button.resetMouseStates();
    }

    @Override
    public void render(Graphics g, int xLevelOffset_UNUSED, int yLevelOffset_UNUSED) {
        volumnSlider.render(g, 0, 0);
        musicButton.render(g, 0, 0);
        sfxButton.render(g, 0, 0);
    }
}
