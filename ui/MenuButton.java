package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.Animation;
import components.Collider;
import main.Game;
import scenes.Scene;

public class MenuButton extends Button {
    private Scene nextScene;

    public MenuButton(int xPosition, int yPosition, Scene nextScene, int atlasRow) {
        super(xPosition, yPosition, 140, 56,
                new Animation("assets/menu_button_atlas.png", 0, 140, 56,
                        new int[][] { { 0, atlasRow }, { 1, atlasRow }, { 2, atlasRow } }));
        this.nextScene = nextScene;
    }

    public void centerX() {
        xPosition = Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2;
        collider.moveHitBox(xPosition, yPosition);
    }

    public Scene getNexState() {
        return nextScene;
    }

}
