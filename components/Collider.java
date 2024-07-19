package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Collider {
    private Rectangle hitBox;

    public Collider(int x, int y, int width, int height) {
        this.hitBox = new Rectangle(x, y, width, height);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public void moveHitBox(int x, int y) {
        hitBox.setLocation(x, y);
    }

    public void drawHitBox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public boolean checkLevelCollision(int[][] levelData) {
        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length; j++) {
                if (levelData[i][j] == 1) {
                    Rectangle tile = new Rectangle(j * 32, i * 32, 32, 32);
                    if (hitBox.intersects(tile)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

}
