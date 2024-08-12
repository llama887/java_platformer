package entities;

import components.Animation;
import components.Collider;
import components.PhysicsController;
import main.Game;
import scenes.Level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.Renderable;
import utils.Updateable;
import utils.Vector2D;

public class Player implements Renderable, Updateable {
    private final int SPRITE_WIDTH = 64, SPRITE_HEIGHT = 40, ANIMATION_SPEED = 30;
    private final float PLAYER_WIDTH = 64 * Game.SCALE, PLAYER_HEIGHT = 40 * Game.SCALE;
    private final String PLAYER_ATLAS = "assets/player_sprites.png";
    private Animation idleAnimation, runAnimation, jumpAnimation, fallingAnimation, groundAnimation, hitAnimation,
            attack1Animation, jumpAttack1Animation, jumpAttack2Animation;
    private BufferedImage currentFrame;
    private Animation currentAnimation;
    private PhysicsController physicsController;
    private Collider collider;
    private Collider groundCollider;
    private float xColliderOffset = 22 * Game.SCALE, yColliderOffset = 3 * Game.SCALE;
    private float xColliderWidth = 18 * Game.SCALE, yColliderHeight = 28 * Game.SCALE;
    private float xGroundedColliderOffset = 28 * Game.SCALE, yGroundedColliderOffset = 5 * Game.SCALE;

    private boolean jump = false, isGrounded = false, isFastFalling = false;
    private final float jumpForce = -0.75f * Game.SCALE;
    private final float fastFall = 0.1f * Game.SCALE;
    private Vector2D lastMovementDirection = new Vector2D(0, 0);

    public Player(float x, float y, float speed) {
        physicsController = new PhysicsController(x, y, speed * Game.SCALE, PLAYER_WIDTH, PLAYER_HEIGHT);
        physicsController.setAcceleration(new Vector2D(0, Level.GRAVITY));
        collider = new Collider(x + xColliderOffset, y + yColliderOffset, xColliderWidth, yColliderHeight);
        xGroundedColliderOffset = 0;
        yGroundedColliderOffset = collider.getHitBox().height + 1;
        groundCollider = new Collider(collider.getX() + xGroundedColliderOffset,
                collider.getY() + yGroundedColliderOffset, collider.getHitBox().width, 1);
        idleAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 } });
        runAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } });
        jumpAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 2 }, { 1, 2 }, { 2, 2 } });
        fallingAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 3 } });
        groundAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 4 }, { 1, 4 } });
        hitAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 3, 5 } });
        attack1Animation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 6 }, { 1, 6 }, { 2, 6 }, { 3, 6 } });
        jumpAttack1Animation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 7 }, { 1, 7 }, { 2, 7 } });
        jumpAttack2Animation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 8 }, { 1, 8 }, { 2, 8 } });
    }

    @Override
    public void update() {
        float initialX = physicsController.getX();
        float initialY = physicsController.getY();
        physicsController.unblockAllMovement();
        if (isGrounded) {
            isFastFalling = false;
        }
        if (jump && isGrounded) {
            physicsController.getAcceleration().add(new Vector2D(0, jumpForce));
            // zero out accumulated velocity in the y direction
            physicsController.setVelocity(new Vector2D(physicsController.getVelocity().getX(), 0));
            jump = false;
        } else {
            physicsController.getAcceleration().setY(Level.GRAVITY);
            if (isFastFalling) {
                physicsController.getAcceleration().setY(physicsController.getAcceleration().getY() + fastFall);
            }
            jump = false;
        }
        Vector2D testPosition = physicsController.testYUpdate();
        collider.moveHitBox(testPosition.getX(), testPosition.getY(), xColliderOffset, yColliderOffset);
        if (!collider.checkCollision()) {
            physicsController.yUpdate();
        } else {
            collider.moveHitBox(physicsController.getX(), physicsController.getY(), xColliderOffset, yColliderOffset);
            float deltaY = testPosition.getY() - physicsController.getY();
            try {
                Vector2D distanceToNearestCollider = collider.distanceToNextCollider(new Vector2D(0, deltaY));
                physicsController.setY(physicsController.getY() +
                        distanceToNearestCollider.getY());
                if (distanceToNearestCollider.getY() != 0) {
                    physicsController.setVelocity(new Vector2D(physicsController.getVelocity().getX(), 0));
                }
                collider.moveHitBox(physicsController.getX(), physicsController.getY(), xColliderOffset,
                        yColliderOffset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        testPosition = physicsController.testXUpdate();
        collider.moveHitBox(testPosition.getX(), testPosition.getY(), xColliderOffset, yColliderOffset);
        if (!collider.checkCollision()) {
            physicsController.xUpdate();
        } else {
            float deltaX = testPosition.getX() - physicsController.getX();
            collider.moveHitBox(physicsController.getX(), physicsController.getY(), xColliderOffset, yColliderOffset);
            try {
                Vector2D distanceToNearestCollider = collider.distanceToNextCollider(new Vector2D(deltaX, 0));
                physicsController.setX(physicsController.getX() +
                        distanceToNearestCollider.getX());
                if (distanceToNearestCollider.getX() != 0) {
                    physicsController.setVelocity(new Vector2D(physicsController.getVelocity().getX(), 0));
                }
                collider.moveHitBox(physicsController.getX(), physicsController.getY(), xColliderOffset,
                        yColliderOffset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isGrounded = groundCollider.checkCollision();
        collider.moveHitBox(physicsController.getX(), physicsController.getY(), xColliderOffset, yColliderOffset);
        groundCollider.moveHitBox(collider.getX(), collider.getY(), xGroundedColliderOffset, yGroundedColliderOffset);
        lastMovementDirection = new Vector2D(physicsController.getX() - initialX, physicsController.getY() - initialY);
    }

    @Override
    public void render(Graphics g, int xLevelOffset, int yLevelOffset) {
        if (lastMovementDirection.magnitude() == 0) {
            currentAnimation = idleAnimation;
        } else if (lastMovementDirection.getY() < 0 && !isGrounded) {
            currentAnimation = jumpAnimation;
        } else if (lastMovementDirection.getY() > 0) {
            currentAnimation = fallingAnimation;
        } else if (Math.abs(lastMovementDirection.getX()) > 0) {
            currentAnimation = runAnimation;
        }
        if (currentAnimation.getAnimationTick() >= currentAnimation.getAnimationSpeed()) {
            currentAnimation.nextFrame();
            currentFrame = currentAnimation.getFrame();
            currentAnimation.setAnimationTick(0);
        }
        currentAnimation.setAnimationTick(currentAnimation.getAnimationTick() + 1);

        g.drawImage(currentFrame, (int) physicsController.getX() - xLevelOffset,
                (int) physicsController.getY() - yLevelOffset,
                (int) physicsController.getWidth(),
                (int) physicsController.getHeight(),
                null);
        collider.drawHitBox(g, xLevelOffset, yLevelOffset);
        groundCollider.drawHitBox(g, xLevelOffset, yLevelOffset);
    }

    public PhysicsController getPhysicsController() {
        return physicsController;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isJumping() {
        return jump;
    }

    public Collider getGroundCollider() {
        return groundCollider;
    }

    public void setFastFalling(boolean isFastFalling) {
        this.isFastFalling = isFastFalling;
    }
}
