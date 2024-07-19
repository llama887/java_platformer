package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.Collider;
import utils.Renderable;

public class Tile implements Renderable {
    private BufferedImage defaultSprite;
    private int x, y, width, height;
    private Collider collider;

    public Tile(BufferedImage defaultSprite, int x, int y, int width, int height) {
        this.defaultSprite = defaultSprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.collider = new Collider(x, y, width, height);
    }

    @Override
    public BufferedImage render(Graphics g) {
        g.drawImage(defaultSprite, x, y, width, height, null);
        return defaultSprite;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getDefaultSprite() {
        return defaultSprite;
    }

    public void setDefaultSprite(BufferedImage defaultSprite) {
        this.defaultSprite = defaultSprite;
    }
}
