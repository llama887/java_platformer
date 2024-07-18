package components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Animation {
    private BufferedImage spriteAtlas;
    private BufferedImage[] animation;
    private int spriteWidth, spriteHeight;
    private int currentIndex = 0;
    private int animationTick = 0, animationSpeed = 12;

    public Animation(String path, int animationSpeed, int spriteWidth, int spriteHeight, int[][] indexes) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.animationSpeed = animationSpeed;
        try {
            spriteAtlas = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new BufferedImage[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            animation[i] = spriteAtlas.getSubimage(indexes[i][0] * spriteWidth, indexes[i][1] * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }
    }

    public BufferedImage getFrame() {
        return animation[currentIndex];
    }

    public BufferedImage getFrame(int index) {
        return animation[index];
    }

    public BufferedImage nextFrame() {
        currentIndex = (currentIndex + 1) % animation.length;
        return animation[currentIndex];
    }

    public int getAnimationLength() {
        return animation.length;
    }

    public boolean isLastFrame() {
        return currentIndex == animation.length - 1;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getAnimationTick() {
        return animationTick;
    }

    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

}
