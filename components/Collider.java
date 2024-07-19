package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Collider {
    private Rectangle2D.Float hitBox;
    private ArrayList<Collider> otherColliders = new ArrayList<Collider>();
    private boolean isActive = true;

    public Collider(float x, float y, float width, float height) {
        this.hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle2D.Float hitBox) {
        this.hitBox = hitBox;
    }

    public void moveHitBox(float x, float y) {
        hitBox.setFrame(x, y, hitBox.getWidth(), hitBox.getHeight());
    }

    public void moveHitBox(float x, float y, float xColliderOffset, float yColliderOffset) {
        hitBox.setFrame(x + xColliderOffset, y + yColliderOffset, hitBox.getWidth(), hitBox.getHeight());
    }

    public void drawHitBox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
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
