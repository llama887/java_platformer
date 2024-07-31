package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.Animation;

public class SoundButton extends Button {
    private boolean muted = false;

    public SoundButton(int xPosition, int yPosition) {
        super(xPosition, yPosition, 42, 42,
                new Animation("assets/sound_button.png", 0, 42, 42,
                        new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }));
    }

    @Override
    public void update() {
        buttonState = ButtonState.NORMAL;
        if (collider.getHitBox().contains(mouseX, mouseY)) {
            buttonState = ButtonState.HOVER;
            if (mouseClicked) {
                buttonState = ButtonState.CLICKED;
                muted = !muted;
            }
        }
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    @Override
    public BufferedImage render(Graphics g) {
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
        }
        System.out.println(buttonStates.getCurrentIndex() + " " + buttonState);
        g.drawImage(buttonStates.getFrame(), xPosition, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT, null);
        collider.drawHitBox(g);
        return buttonStates.getFrame();
    }
}
