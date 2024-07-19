package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Collider {
    private Rectangle hitBox;
    private ArrayList<Collider> otherColliders = new ArrayList<Collider>();
    private boolean isActive = true;

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

    public boolean checkCollision(Collider other) {
        return hitBox.intersects(other.getHitBox()) && other.isActive();
    }

    public boolean checkCollision() {
        for (Collider other : otherColliders) {
            if (other.isActive() && hitBox.intersects(other.getHitBox())) {
                return true;
            }
        }
        return false;

    }

    public void addOtherCollider(Collider other) {
        otherColliders.add(other);
    }

    public void setOtherColliders(ArrayList<Collider> otherColliders) {
        this.otherColliders = otherColliders;
    }

    public ArrayList<Collider> getOtherColliders() {
        return otherColliders;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
