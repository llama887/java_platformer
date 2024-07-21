package components;

import utils.Vector2D;

public class PhysicsController {
    private float x, y, speed, width, height;
    private Vector2D movementDirection, velocity, acceleration;
    boolean canMoveUp = true, canMoveDown = true, canMoveLeft = true, canMoveRight = true;

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
        velocity.setX(movementDirection.getX() * speed);
        velocity.setY(movementDirection.getY() * speed);
        if (acceleration.magnitude() != 0) {
            velocity.add(acceleration);
        }
        if (velocity.magnitude() != 0) {
            float xMove = velocity.getX() * speed;
            float yMove = velocity.getY() * speed;
            if (xMove > 0) {
                if (canMoveRight) {
                    x += xMove;
                }
            } else if (xMove < 0) {
                if (canMoveLeft) {
                    x += xMove;
                }
            }
            if (yMove > 0) {
                if (canMoveDown) {
                    y += yMove;
                }
            } else if (yMove < 0) {
                if (canMoveUp) {
                    y += yMove;
                }
            }
        }
        return new Vector2D(x, y);
    }

    public Vector2D yUpdate() {
        velocity.setY(movementDirection.getY() * speed);
        if (acceleration.magnitude() != 0) {
            velocity.add(acceleration);
        }
        if (velocity.magnitude() != 0) {
            float yMove = velocity.getY() * speed;
            if (yMove > 0) {
                if (canMoveDown) {
                    y += yMove;
                }
            } else if (yMove < 0) {
                if (canMoveUp) {
                    y += yMove;
                }
            }
        }
        return new Vector2D(x, y);
    }

    public Vector2D xUpdate() {
        velocity.setX(movementDirection.getX() * speed);
        if (acceleration.magnitude() != 0) {
            velocity.add(acceleration);
        }
        if (velocity.magnitude() != 0) {
            float xMove = velocity.getX() * speed;
            if (xMove > 0) {
                if (canMoveRight) {
                    x += xMove;
                }
            } else if (xMove < 0) {
                if (canMoveLeft) {
                    x += xMove;
                }
            }
        }
        return new Vector2D(x, y);
    }

    public Vector2D testUpdate() {
        Vector2D tempVelocity = new Vector2D(movementDirection.getX() * speed, movementDirection.getY() * speed);
        if (acceleration.magnitude() != 0) {
            tempVelocity.add(acceleration);
        }
        float tempX = x;
        float tempY = y;
        if (tempVelocity.magnitude() != 0) {
            float xMove = tempVelocity.getX() * speed;
            float yMove = tempVelocity.getY() * speed;
            if (xMove > 0) {
                if (canMoveRight) {
                    tempX += xMove;
                }
            } else if (xMove < 0) {
                if (canMoveLeft) {
                    tempX += xMove;
                }
            }
            if (yMove > 0) {
                if (canMoveDown) {
                    tempY += yMove;
                }
            } else if (yMove < 0) {
                if (canMoveUp) {
                    tempY += yMove;
                }
            }
        }
        return new Vector2D(tempX, tempY);
    }

    public Vector2D testYUpdate() {
        Vector2D tempVelocity = new Vector2D(0, movementDirection.getY() * speed);
        if (acceleration.magnitude() != 0) {
            tempVelocity.add(acceleration);
        }
        float tempY = y;
        if (tempVelocity.magnitude() != 0) {
            float yMove = tempVelocity.getY() * speed;
            if (yMove > 0) {
                if (canMoveDown) {
                    tempY += yMove;
                }
            } else if (yMove < 0) {
                if (canMoveUp) {
                    tempY += yMove;
                }
            }
        }
        return new Vector2D(x, tempY);
    }

    public Vector2D testXUpdate() {
        Vector2D tempVelocity = new Vector2D(movementDirection.getX() * speed, 0);
        if (acceleration.magnitude() != 0) {
            tempVelocity.add(acceleration);
        }
        float tempX = x;
        if (tempVelocity.magnitude() != 0) {
            float xMove = tempVelocity.getX() * speed;
            if (xMove > 0) {
                if (canMoveRight) {
                    tempX += xMove;
                }
            } else if (xMove < 0) {
                if (canMoveLeft) {
                    tempX += xMove;
                }
            }
        }
        return new Vector2D(tempX, y);
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

    public void canMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public void canMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }

    public void canMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public void canMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }

    public boolean canMoveUp() {
        return canMoveUp;
    }

    public boolean canMoveDown() {
        return canMoveDown;
    }

    public boolean canMoveLeft() {
        return canMoveLeft;
    }

    public boolean canMoveRight() {
        return canMoveRight;
    }

    public void unblockAllMovement() {
        canMoveUp = true;
        canMoveDown = true;
        canMoveLeft = true;
        canMoveRight = true;
    }
}
