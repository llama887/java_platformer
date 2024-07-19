package components;

import utils.Vector2D;

public class PhysicsController {
    private double x, y, speed, width, height;
    private Vector2D movementDirection, velocity, acceleration;

    public PhysicsController(double x, double y, double speed, double width, double height) {
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
        if (acceleration.magnitude() != 0) {
            velocity.add(acceleration);
        }
        if (velocity.magnitude() != 0) {
            movementDirection.set(velocity, true);
        }
        x += movementDirection.getX() * speed;
        y += movementDirection.getY() * speed;
        return new Vector2D(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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
