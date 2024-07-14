package components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Animation {
    private BufferedImage image;
    private BufferedImage[] animations;
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
        animations = new BufferedImage[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            animations[i] = image.getSubimage(indexes[i][0] * spriteWidth, indexes[i][1] * spriteHeight, spriteWidth,
                    spriteHeight);
        }
    }

    public BufferedImage getFrame() {
        return animations[currentIndex];
    }

    public BufferedImage getFrame(int index) {
        return animations[index];
    }

    public BufferedImage nextFrame() {
        currentIndex = (currentIndex + 1) % animations.length;
        return animations[currentIndex];
    }

    public getAnimationLength() {
        return animation.length;
    }
}
