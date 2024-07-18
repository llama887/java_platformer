package utils;

public class Vector2D {
    private double x;
    private double y;
    private boolean normal = false;

    public Vector2D(double x, double y, boolean normal) {
        this.x = x;
        this.y = y;
        this.normal = normal;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public boolean normalize() {
        if (normal) {
            double magnitude = magnitude();
            if (magnitude == 0) {
                return false;
            }
            x /= magnitude;
            y /= magnitude;
        }
        return normal;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        normalize();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        normalize();
    }

    public void set(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
        normalize();
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
        normalize();
    }

    public void set(double x, double y, boolean normal) {
        this.x = x;
        this.y = y;
        this.normal = normal;
        normalize();
    }

    public void set(Vector2D other, boolean normal) {
        this.x = other.x;
        this.y = other.y;
        this.normal = normal;
        normalize();
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public double dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString() {
        return "Vector2D{" + "x=" + x + ", y=" + y + ", normal=" + normal + '}';
    }
}
