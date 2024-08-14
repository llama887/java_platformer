package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import utils.Vector2D;

/**
 * The Collider class represents a hitbox used for collision detection.
 */
public class Collider {
    private Rectangle2D.Float hitBox;
    private ArrayList<Collider> otherColliders = new ArrayList<>();
    private TreeMap<Float, List<Collider>> otherCollidersByX = new TreeMap<>();
    private TreeMap<Float, List<Collider>> otherCollidersByY = new TreeMap<>();
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

    /**
     * @return float
     */
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
    public void drawHitBox(Graphics g, int xLevelOffset, int yLevelOffset) {
        g.setColor((isActive) ? Color.BLUE : Color.RED);
        g.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y - yLevelOffset, (int) hitBox.width,
                (int) hitBox.height);
    }

    /**
     * Checks for collision with another Collider.
     *
     * @param other the other Collider
     * @return true if there is a collision, false otherwise
     */
    public boolean checkCollision(Collider other) {

        if (!other.isActive() || !isActive) {
            return false;
        }
        // Get the hitboxes of both colliders
        Rectangle2D.Float hitBox1 = this.getHitBox();
        Rectangle2D.Float hitBox2 = other.getHitBox();

        // Check if the other Collider is active
        if (!other.isActive()) {
            return false;
        }

        // Calculate the boundaries of the hitboxes
        float x1Min = hitBox1.x;
        float x1Max = hitBox1.x + hitBox1.width;
        float y1Min = hitBox1.y;
        float y1Max = hitBox1.y + hitBox1.height;

        float x2Min = hitBox2.x;
        float x2Max = hitBox2.x + hitBox2.width;
        float y2Min = hitBox2.y;
        float y2Max = hitBox2.y + hitBox2.height;

        // Check for intersection excluding edges
        boolean xOverlap = (x1Min < x2Max && x1Max > x2Min) && !(x1Max == x2Min || x1Min == x2Max);
        boolean yOverlap = (y1Min < y2Max && y1Max > y2Min) && !(y1Max == y2Min || y1Min == y2Max);

        return xOverlap && yOverlap;
        // return other.isActive() && hitBox.intersects(other.getHitBox());
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
        addColliderToMap(otherCollidersByX, other.getX(), other);
        addColliderToMap(otherCollidersByY, other.getY(), other);
    }

    /**
     * Adds a collider to the specified TreeMap.
     *
     * @param map   the TreeMap
     * @param key   the key
     * @param value the collider
     */
    private void addColliderToMap(TreeMap<Float, List<Collider>> map, float key, Collider value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    /**
     * Sets the list of other Colliders.
     *
     * @param otherColliders the new list of other Colliders
     */
    public void setOtherColliders(ArrayList<Collider> otherColliders) {
        this.otherColliders = otherColliders;
        otherCollidersByX.clear();
        otherCollidersByY.clear();
        for (Collider collider : otherColliders) {
            addColliderToMap(otherCollidersByX, collider.getX(), collider);
            addColliderToMap(otherCollidersByY, collider.getY(), collider);
        }
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

    /**
     * Given a horizontal or vertical movement direction, returns the distance to
     * the next collider in that direction.
     * 
     * @param movementDirection
     * @return Vector2D
     * @throws Exception
     */
    public Vector2D distanceToNextCollider(Vector2D movementDirection) throws Exception {
        if (movementDirection.getX() * movementDirection.getY() != 0) {
            throw new Exception(
                    "distanceToNextCollider only accepts movement directions that are either horizontal or vertical");
        }

        float shortestDistance = Float.MAX_VALUE;

        if (movementDirection.getX() > 0) { // Moving right
            for (Float key : otherCollidersByX.tailMap((float) this.hitBox.getMaxX(), true).keySet()) {
                for (Collider other : otherCollidersByX.get(key)) {
                    if (other.isActive() && other.getX() >= this.hitBox.getMaxX()
                            && hitBox.getY() >= other.getHitBox().getMaxY()
                            && hitBox.getMaxY() <= other.getHitBox().getY()) {
                        shortestDistance = (float) Math.min(shortestDistance, other.getX() - this.hitBox.getMaxX());
                    }
                }
            }
        } else if (movementDirection.getX() < 0) { // Moving left
            for (Float key : otherCollidersByX.headMap(this.getX(), true).descendingKeySet()) {
                for (Collider other : otherCollidersByX.get(key)) {
                    if (other.isActive() && other.getHitBox().getMaxX() <= this.hitBox.getX()
                            && hitBox.getY() >= other.getHitBox().getMaxY()
                            && hitBox.getMaxY() <= other.getHitBox().getY()) {
                        shortestDistance = (float) Math.min(shortestDistance,
                                this.hitBox.getX() - other.getHitBox().getMaxX());
                    }
                    break;
                }
            }
        } else if (movementDirection.getY() > 0) { // Moving down
            for (Float key : otherCollidersByY.tailMap((float) this.hitBox.getMaxY(), true).keySet()) {
                for (Collider other : otherCollidersByY.get(key)) {
                    if (other.isActive() && other.getY() >= this.hitBox.getMaxY()
                            && hitBox.getX() <= other.getHitBox().getMaxX()
                            && hitBox.getMaxX() >= other.getHitBox().getX()) {
                        shortestDistance = (float) Math.min(shortestDistance, other.getY() - this.hitBox.getMaxY());
                        break;
                    }
                }
            }
        } else if (movementDirection.getY() < 0) { // Moving up
            for (Float key : otherCollidersByY.headMap(this.getY(), true).descendingKeySet()) {
                for (Collider other : otherCollidersByY.get(key)) {
                    if (other.isActive() && other.getHitBox().getMaxY() <= this.hitBox.getY()
                            && hitBox.getX() <= other.getHitBox().getMaxX()
                            && hitBox.getMaxX() >= other.getHitBox().getX()) {
                        shortestDistance = (float) Math.min(shortestDistance,
                                this.hitBox.getY() - (other.getY() + other.getHitBox().height));
                        break;
                    }
                }
            }
        }

        if (shortestDistance == Float.MAX_VALUE) {
            return new Vector2D(0, 0); // No colliders in the direction of movement
        }

        // Return the shortest distance in the direction of movement
        return new Vector2D(
                movementDirection.getX() == 0 ? 0 : shortestDistance * Math.signum(movementDirection.getX()),
                movementDirection.getY() == 0 ? 0 : shortestDistance * Math.signum(movementDirection.getY()));
    }

    @Override
    public String toString() {
        return "Collider{" +
                "hitBox=" + hitBox +
                ", isActive=" + isActive +
                '}';
    }
}
