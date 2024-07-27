package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.Animation;
import components.Collider;
import main.Game;
import scenes.GameState;
import utils.Renderable;
import utils.Updateable;

public class MenuButton implements Renderable, Updateable {
    private Animation buttonStates;
    private final int BUTTON_WIDTH_DEFAULT = 140, BUTTON_HEIGHT_DEFAULT = 56;
    private final int BUTTON_WIDTH = (int) (BUTTON_WIDTH_DEFAULT * Game.SCALE),
            BUTTON_HEIGHT = (int) (BUTTON_HEIGHT_DEFAULT * Game.SCALE);
    private int xPosition, yPosition;
    private GameState nextState;
    private final String MENU_BUTTON_ATLAS = "assets/menu_button_atlas.png";
    private Collider collider;
    private float mouseX, mouseY;
    private boolean mouseClicked;

    public enum ButtonState {
        NORMAL, HOVER, CLICKED;
    }

    private ButtonState buttonState = ButtonState.NORMAL;

    public MenuButton(int xPosition, int yPosition, GameState nextState, int atlasRow) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextState = nextState;
        buttonStates = new Animation(MENU_BUTTON_ATLAS, 0, BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT,
                new int[][] { { 0, atlasRow }, { 1, atlasRow }, { 2, atlasRow } });
        collider = new Collider(xPosition, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public Collider getCollider() {
        return collider;
    }

    public void centerX() {
        xPosition = Game.GAME_WIDTH / 2 - BUTTON_WIDTH;
        collider.moveHitBox(xPosition, yPosition);
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

    public void setMouseState(float mouseX, float mouseY, boolean mouseClicked) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.mouseClicked = mouseClicked;
    }

    public ButtonState getButtonState() {
        return buttonState;
    }

    public GameState getNexState() {
        return nextState;
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
}
