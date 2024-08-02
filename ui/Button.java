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
    public static float mouseX, mouseY;
    public static boolean mousePressed, mouseClicked;

    public enum ButtonState {
        NORMAL, HOVER, CLICKED, ACTIVATED;
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

    public ButtonState getButtonState() {
        return buttonState;
    }

    @Override
    public void update() {
        if (buttonState == ButtonState.CLICKED) {
            buttonState = ButtonState.ACTIVATED;
            activate();
            return;
        }
        buttonState = ButtonState.NORMAL;
        if (collider.getHitBox().contains(mouseX, mouseY)) {
            buttonState = ButtonState.HOVER;
            if (mousePressed) {
                buttonState = ButtonState.CLICKED;
            }
        }
    }

    public void activate() {
    };

    public static void resetMouseStates() {
        mousePressed = false;
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
            default:
                break;
        }
        g.drawImage(buttonStates.getFrame(), xPosition, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT, null);
        collider.drawHitBox(g);
        return buttonStates.getFrame();
    }
}
