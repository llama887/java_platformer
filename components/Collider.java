package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * The Collider class represents a hitbox used for collision detection.
 */
public class Collider {
    private Rectangle2D.Float hitBox;
    private ArrayList<Collider> otherColliders = new ArrayList<>();
    private boolean isActive = true;
    private boolean collidingTop = false, collidingBottom = false, collidingLeft = false, collidingRight = false;

    /**
     * Constructs a Collider with the specified position and size.
     *
     * @param x      the x-coordinate of the hitbox
     * @param y      the y-coordinate of the hitbox
     * @param width  the width of the hitbox
     * @param height the height of the hitbox
     */
    public Collider(float x, float y, float width, float height) {
        this.hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    public float getX() {
        return hitBox.x;
    }

    public float getY() {
        return hitBox.y;
    }

    /**
     * Returns the hitbox of this Collider.
     *
     * @return the hitbox
     */
    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    /**
     * Sets the hitbox of this Collider.
     *
     * @param hitBox the new hitbox
     */
    public void setHitBox(Rectangle2D.Float hitBox) {
        this.hitBox = hitBox;
    }

    /**
     * Moves the hitbox to a new position.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void moveHitBox(float x, float y) {
        hitBox.setFrame(x, y, hitBox.getWidth(), hitBox.getHeight());
    }

    /**
     * Moves the hitbox to a new position with specified offsets.
     *
     * @param x               the new x-coordinate
     * @param y               the new y-coordinate
     * @param xColliderOffset the x offset for the collider
     * @param yColliderOffset the y offset for the collider
     */
    public void moveHitBox(float x, float y, float xColliderOffset, float yColliderOffset) {
        hitBox.setFrame(x + xColliderOffset, y + yColliderOffset, hitBox.getWidth(), hitBox.getHeight());
    }

    public void setX(float x) {
        hitBox.x = x;
    }

    public void setY(float y) {
        hitBox.y = y;
    }

    public void setX(float x, float xColliderOffset) {
        hitBox.x = x + xColliderOffset;
    }

    public void setY(float y, float yColliderOffset) {
        hitBox.y = y + yColliderOffset;
    }

    /**
     * Draws the hitbox on the given Graphics object.
     *
     * @param g the Graphics object
     */
    public void drawHitBox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    /**
     * Checks for collision with another Collider.
     *
     * @param other the other Collider
     * @return true if there is a collision, false otherwise
     */
    public boolean checkCollision(Collider other) {
        return other.isActive() && hitBox.intersects(other.getHitBox());
    }

    /**
     * Checks for collision with any other Collider in the list.
     *
     * @return true if there is a collision, false otherwise
     */
    public boolean checkCollision() {
        for (Collider other : otherColliders) {
            if (checkCollision(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for collision on the Y-axis and returns the overlap.
     *
     * @return the Y-axis overlap if there is a collision, 0 otherwise
     */
    public float checkCollisionY() {
        for (Collider other : otherColliders) {
            if (checkCollision(other)) {
                boolean otherIsBelow = other.getHitBox().y > hitBox.y;
                if (otherIsBelow) {
                    float yOverlap = hitBox.y + hitBox.height - other.getHitBox().y;
                    boolean collidingBottom = yOverlap > 0;
                    if (collidingBottom) {
                        return -yOverlap;
                    }
                }
                boolean otherIsAbove = other.getHitBox().y < hitBox.y;
                if (otherIsAbove) { // if other is above
                    float yOverlap = other.getHitBox().y + other.getHitBox().height - hitBox.y;
                    boolean collidingTop = yOverlap > 0;
                    if (collidingTop) {
                        return yOverlap;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Checks for collision on the X-axis and returns the overlap.
     *
     * @return the X-axis overlap if there is a collision, 0 otherwise
     */
    public float checkCollisionX() {
        for (Collider other : otherColliders) {
            if (checkCollision(other)) {
                boolean otherIsRight = other.getHitBox().x > hitBox.x;
                if (otherIsRight) { // if other is right
                    float xOverlap = hitBox.x + hitBox.width - other.getHitBox().x;
                    boolean collidingRight = xOverlap > 0;
                    if (collidingRight) {
                        return -xOverlap;
                    }
                }
                boolean otherIsLeft = other.getHitBox().x < hitBox.x;
                if (otherIsLeft) { // if other is left
                    float xOverlap = other.getHitBox().x + other.getHitBox().width - hitBox.x;
                    boolean collidingLeft = xOverlap > 0;
                    if (collidingLeft) {
                        return xOverlap;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Adds another Collider to the list of colliders to check against.
     *
     * @param other the other Collider
     */
    public void addOtherCollider(Collider other) {
        otherColliders.add(other);
    }

    /**
     * Sets the list of other Colliders.
     *
     * @param otherColliders the new list of other Colliders
     */
    public void setOtherColliders(ArrayList<Collider> otherColliders) {
        this.otherColliders = otherColliders;
    }

    /**
     * Returns the list of other Colliders.
     *
     * @return the list of other Colliders
     */
    public ArrayList<Collider> getOtherColliders() {
        return otherColliders;
    }

    /**
     * Returns whether this Collider is active.
     *
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status of this Collider.
     *
     * @param isActive true to activate, false to deactivate
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void resetCollisions() {
        collidingTop = false;
        collidingBottom = false;
        collidingLeft = false;
        collidingRight = false;
    }

    public boolean isCollidingTop() {
        return collidingTop;
    }

    public boolean isCollidingBottom() {
        return collidingBottom;
    }

    public boolean isCollidingLeft() {
        return collidingLeft;
    }

    public boolean isCollidingRight() {
        return collidingRight;
    }

    @Override
    public String toString() {
        return "Collider{" +
                "hitBox=" + hitBox +
                ", isActive=" + isActive +
                '}';
    }
}
