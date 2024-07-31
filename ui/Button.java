package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.Animation;
import components.Collider;
import main.Game;
import utils.Renderable;
import utils.Updateable;

public abstract class Button implements Renderable, Updateable {
    protected Animation buttonStates;
    protected final int BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT;
    protected final int BUTTON_WIDTH, BUTTON_HEIGHT;
    protected int xPosition, yPosition;
    protected Collider collider;
    protected float mouseX, mouseY;
    protected boolean mouseClicked;

    public enum ButtonState {
        NORMAL, HOVER, CLICKED;
    }

    ButtonState buttonState = ButtonState.NORMAL;

    public Button(int xPosition, int yPosition, int buttonWidth, int buttonHeight, Animation buttonStates) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.buttonStates = buttonStates;
        BUTTON_WIDTH_DEFAULT = buttonWidth;
        BUTTON_HEIGHT_DEFAULT = buttonHeight;
        BUTTON_WIDTH = (int) (BUTTON_WIDTH_DEFAULT * Game.SCALE);
        BUTTON_HEIGHT = (int) (BUTTON_HEIGHT_DEFAULT * Game.SCALE);
        collider = new Collider(xPosition, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public Collider getCollider() {
        return collider;
    }

    public void setMouseState(float mouseX, float mouseY, boolean mouseClicked) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.mouseClicked = mouseClicked;
    }

    public ButtonState getButtonState() {
        return buttonState;
    }

    @Override
    public void update() {
        buttonState = ButtonState.NORMAL;
        if (collider.getHitBox().contains(mouseX, mouseY)) {
            buttonState = ButtonState.HOVER;
            if (mouseClicked) {
                buttonState = ButtonState.CLICKED;
            }
        }
    }

    @Override
    public BufferedImage render(Graphics g) {
        switch (buttonState) {
            case NORMAL:
                buttonStates.setCurrentIndex(0);
                break;
            case HOVER:
                buttonStates.setCurrentIndex(1);
                break;
            case CLICKED:
                buttonStates.setCurrentIndex(2);
                break;
        }
        g.drawImage(buttonStates.getFrame(), xPosition, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT, null);
        collider.drawHitBox(g);
        return buttonStates.getFrame();
    }
}
