package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.Animation;

public class SoundButton extends Button {
    private boolean muted = false;
    private static final int SOUND_BUTTON_WIDTH_DEFAULT = 42, SOUND_BUTTON_HEIGHT_DEFAULT = 42;
    private static final Animation SOUND_BUTTON_STATES = new Animation("assets/sound_button.png", 0, 42, 42,
            new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } });

    public SoundButton(int xPosition, int yPosition) {
        super(xPosition, yPosition, SOUND_BUTTON_WIDTH_DEFAULT, SOUND_BUTTON_HEIGHT_DEFAULT, SOUND_BUTTON_STATES);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void activate() {
        muted = !muted;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    @Override
    public void render(Graphics g, int xLevelOffset, int yLevelOffset) {
        int buttonIndex = muted ? 3 : 0;
        switch (buttonState) {
            case NORMAL:
                buttonStates.setCurrentIndex(buttonIndex);
                break;
            case HOVER:
                buttonStates.setCurrentIndex(buttonIndex + 1);
                break;
            case CLICKED:
                buttonStates.setCurrentIndex(buttonIndex + 2);
                break;
            default:
                break;
        }
        g.drawImage(buttonStates.getFrame(), xPosition - xLevelOffset, yPosition - yLevelOffset, BUTTON_WIDTH,
                BUTTON_HEIGHT, null);
    }
}
