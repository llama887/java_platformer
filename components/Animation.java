package components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Animation {
    private BufferedImage image;
    private BufferedImage[] animation;
    private int spriteWidth, spriteHeight;
    private int currentIndex = 0;

    public Animation(String path, int spriteWidth, int spriteHeight, int[][] indexes) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new BufferedImage[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            animation[i] = image.getSubimage(indexes[i][0] * spriteWidth, indexes[i][1] * spriteHeight, spriteWidth,
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
}
