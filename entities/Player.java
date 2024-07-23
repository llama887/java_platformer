package entities;

import components.Animation;
import components.Collider;
import components.PhysicsController;
import main.Game;
import scenes.Level1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;

import utils.Renderable;
import utils.Updateable;
import utils.Vector2D;

public class Player implements Renderable, Updateable {
    private final int SPRITE_WIDTH = 64, SPRITE_HEIGHT = 40, ANIMATION_SPEED = 30;
    private final String PLAYER_ATLAS = "assets/player_sprites.png";
    private Animation idleAnimation, runAnimation, jumpAnimation, fallingAnimation, groundAnimation, hitAnimation,
            attack1Animation, jumpAttack1Animation, jumpAttack2Animation;
    private BufferedImage currentFrame;
    private Animation currentAnimation;
    private PhysicsController physicsController;
    private Collider collider;
    private Collider groundCollider;
    private float xColliderOffset = 22 * Game.TILE_SCALE, yColliderOffset = 3 * Game.TILE_SCALE;
    private float xColliderWidth = 18 * Game.TILE_SCALE, yColliderHeight = 28 * Game.TILE_SCALE;
    private float xGroundedColliderOffset = 28 * Game.TILE_SCALE, yGroundedColliderOffset = 5 * Game.TILE_SCALE;

    private boolean jump = false, isGrounded = false;
    private final float jumpForce = -25f * Game.TILE_SCALE;
    private Vector2D lastMovementDirection = new Vector2D(0, 0);

    public Player(float x, float y, float speed, float width, float height) {
        physicsController = new PhysicsController(x, y, speed, width, height);
        physicsController.setAcceleration(new Vector2D(0, Level1.GRAVITY));
        collider = new Collider(x + xColliderOffset, y + yColliderOffset, xColliderWidth, yColliderHeight);
        xGroundedColliderOffset = collider.getHitBox().width / 2;
        yGroundedColliderOffset = collider.getHitBox().height + 1;
        groundCollider = new Collider(collider.getX() + xGroundedColliderOffset,
                collider.getY() + yGroundedColliderOffset, 1, 1);
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
        if (jump && isGrounded) {
            physicsController.getAcceleration().add(new Vector2D(0, jumpForce));
            physicsController.setVelocity(new Vector2D(physicsController.getVelocity().getX(), 0));
            jump = false;
        } else {
            physicsController.getAcceleration().setY(Level1.GRAVITY);
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
    public BufferedImage render(Graphics g) {
        if (lastMovementDirection.magnitude() == 0) {
            currentAnimation = idleAnimation;
        } else if (Math.abs(lastMovementDirection.getX()) > 0) {
            currentAnimation = runAnimation;
        } else if (lastMovementDirection.getY() < 0) {
            currentAnimation = jumpAnimation;
        } else if (lastMovementDirection.getY() > 0) {
            currentAnimation = fallingAnimation;
        }
        if (currentAnimation.getAnimationTick() >= currentAnimation.getAnimationSpeed()) {
            currentAnimation.nextFrame();
            currentFrame = currentAnimation.getFrame();
            currentAnimation.setAnimationTick(0);
        }
        currentAnimation.setAnimationTick(currentAnimation.getAnimationTick() + 1);

        g.drawImage(currentFrame, (int) physicsController.getX(),
                (int) physicsController.getY(),
                (int) physicsController.getWidth(),
                (int) physicsController.getHeight(),
                null);
        collider.drawHitBox(g);
        groundCollider.drawHitBox(g);
        return currentAnimation.getFrame();
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
}
