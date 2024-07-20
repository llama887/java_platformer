package components;

import utils.Vector2D;

public class PhysicsController {
    private float x, y, speed, width, height;
    private Vector2D movementDirection, velocity, acceleration;

    public PhysicsController(float x, float y, float speed, float width, float height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.movementDirection = new Vector2D();
        this.velocity = new Vector2D();
        this.acceleration = new Vector2D();
    }

    public Vector2D update() {
        // System.out.println("PRE UPDATE: Acceleration: " + acceleration + " Velocity:
        // " + velocity
        // + " Movement Direction: " + movementDirection + " X: " + x + " Y: " + y);
        velocity.setX(movementDirection.getX() * speed);
        velocity.setY(movementDirection.getY() * speed);
        if (acceleration.magnitude() != 0) {
            velocity.add(acceleration);
        }
        if (velocity.magnitude() != 0) {
            x += velocity.getX() * speed;
            y += velocity.getY() * speed;
        }
        // System.out.println("POST UPDATE: Acceleration: " + acceleration + " Velocity:
        // " + velocity
        // + " Movement Direction: " + movementDirection + " X: " + x + " Y: " + y);
        return new Vector2D(x, y);
    }

    public Vector2D testUpdate() {
        Vector2D tempVelocity = new Vector2D(velocity);
        if (acceleration.magnitude() != 0) {
            tempVelocity.add(acceleration);
        }
        Vector2D tempMovementDirection = new Vector2D(movementDirection);
        float tempX = x;
        float tempY = y;
        if (tempVelocity.magnitude() != 0) {
            tempX += velocity.getX() * speed;
            tempY += velocity.getY() * speed;
        }
        tempX += tempMovementDirection.getX() * speed;
        tempY += tempMovementDirection.getY() * speed;
        return new Vector2D(tempX, tempY);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    public Vector2D getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Vector2D movementDirection) {
        this.movementDirection.set(movementDirection, true);
    }

    public void setMovementDirection(int x, int y) {
        this.movementDirection.set(x, y, true);
    }

}
