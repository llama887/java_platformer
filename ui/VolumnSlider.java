package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import components.Animation;
import main.Game;

public class VolumnSlider extends Button {
    private static final int VOLUME_BUTTON_WIDTH_DEFAULT = 28, VOLUME_BUTTON_HEIGHT_DEFAULT = 44;
    private static final int SLIDER_DEFAULT_WIDTH = 215, SLIDER_HEIGHT_DEFAULT = VOLUME_BUTTON_HEIGHT_DEFAULT;
    private static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE),
            SLIDER_HEIGHT = (int) (SLIDER_HEIGHT_DEFAULT * Game.SCALE);
    private static final String SLIDER_PATH = "assets/volume_slider.png";
    private static final Animation VOLUME_BUTTON_STATES = new Animation(SLIDER_PATH, 0, VOLUME_BUTTON_WIDTH_DEFAULT,
            VOLUME_BUTTON_HEIGHT_DEFAULT,
            new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 } });
    private BufferedImage slider;
    private int sliderX, sliderY;
    private int minX, maxX;
    private boolean draggable = false;
    private boolean isDragging = false;

    public VolumnSlider(int xPosition, int yPosition) {
        super(xPosition + SLIDER_WIDTH / 2 - (int) (VOLUME_BUTTON_WIDTH_DEFAULT * Game.SCALE / 2), yPosition,
                VOLUME_BUTTON_WIDTH_DEFAULT,
                VOLUME_BUTTON_HEIGHT_DEFAULT, VOLUME_BUTTON_STATES);
        sliderX = xPosition;
        sliderY = yPosition;
        minX = sliderX + super.BUTTON_WIDTH / 2;
        maxX = sliderX + SLIDER_WIDTH - super.BUTTON_WIDTH;
        try {
            slider = ImageIO.read(new File(SLIDER_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        slider = slider.getSubimage(3 * VOLUME_BUTTON_WIDTH_DEFAULT, 0, SLIDER_DEFAULT_WIDTH, SLIDER_HEIGHT_DEFAULT);
    }

    public void setSliderPosition(int x) {
        if (!draggable) {
            return;
        }
        xPosition = x;
        if (xPosition < minX) {
            xPosition = minX;
        } else if (xPosition > maxX) {
            xPosition = maxX;
        }
        collider.moveHitBox(xPosition, yPosition);
    }

    @Override
    public void render(Graphics g, int xLevelOffset, int yLevelOffset) {
        g.drawImage(slider, sliderX - xLevelOffset, sliderY - yLevelOffset, SLIDER_WIDTH, SLIDER_HEIGHT, null);
        super.render(g, xLevelOffset, yLevelOffset);
    }

    @Override
    public void activate() {
        draggable = true;
    }

    @Override
    public void update() {
        super.update();
    }

    public void setDragging(boolean dragging) {
        this.isDragging = dragging;
        if (!dragging) {
            draggable = false;
        }
    }

    public boolean isDragging() {
        return isDragging;
    }

    public float getPercentage() {
        float range = maxX - minX;
        float value = xPosition - minX;
        float floatValue = value / range;
        // System.out.println(floatValue);
        return floatValue;
    }

}
