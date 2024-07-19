package utils;

public class Vector2D {
    private float x;
    private float y;
    private boolean normal = false;

    public Vector2D(float x, float y, boolean normal) {
        this.x = x;
        this.y = y;
        this.normal = normal;
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
        this.normal = other.normal;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public boolean normalize() {
        if (normal) {
            float magnitude = magnitude();
            if (magnitude == 0) {
                return false;
            }
            x /= magnitude;
            y /= magnitude;
        }
        return normal;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        normalize();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        normalize();
    }

    public boolean getNormal() {
        return normal;
    }

    public void set(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
        this.normal = other.normal;
        normalize();
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
        normalize();
    }

    public void set(float x, float y, boolean normal) {
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

    public float dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString() {
        return "Vector2D{" + "x=" + x + ", y=" + y + ", normal=" + normal + '}';
    }
}
