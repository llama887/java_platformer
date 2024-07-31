package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.Animation;

public class SoundButton extends Button {

    public SoundButton(int xPosition, int yPosition, int atlasRow) {
        super(xPosition, yPosition, 42, 42,
                new Animation("assets/sound_button.png", 0, 42, 42,
                        new int[][] { { 0, atlasRow }, { 1, atlasRow }, { 2, atlasRow } }));
    }

}
