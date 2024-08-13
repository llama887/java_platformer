package entities;

import components.Animation;
import components.Collider;
import components.PhysicsController;
import main.Game;
import scenes.Level;
import ui.StatusBar;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.Renderable;
import utils.Updateable;
import utils.Vector2D;

public class Player implements Renderable, Updateable {
    private final int SPRITE_WIDTH = 64, SPRITE_HEIGHT = 40, ANIMATION_SPEED = 30;
    private final float PLAYER_WIDTH = 64 * Game.SCALE, PLAYER_HEIGHT = 40 * Game.SCALE;
    private final String PLAYER_ATLAS = "assets/player_sprites.png";
    private Animation idleAnimation, runAnimation, jumpAnimation, fallingAnimation,
            attackAnimation, hitAnimation, deathAnimation;
    private BufferedImage currentFrame;
    private Animation currentAnimation;
    private PhysicsController physicsController;
    private Collider collider;
    private Collider groundCollider;
    private final float COLLIDER_OFFSET_X = 22 * Game.SCALE, COLLIDER_OFFSET_Y = 3 * Game.SCALE,
            COLLIDER_WIDTH = 18 * Game.SCALE, COLLIDER_HEIGHT = 28 * Game.SCALE;
    private final float GROUNDED_COLLIDER_X_OFFSET, GROUNDED_COLLIDER_Y_OFFSET;
    private boolean jump = false, isGrounded = false, isFastFalling = false;
    private final float JUMP_FORCE = -0.75f * Game.SCALE;
    private final float FAST_FALL = 0.1f * Game.SCALE;
    private Vector2D lastUpdateDirection = new Vector2D(0, 0);
    private final int MAX_HEALTH = 100;
    private int health = MAX_HEALTH;
    private Collider attackCollider;
    private final int ATTACK_COLLIDER_WIDTH = (int) (20 * Game.SCALE), ATTACK_COLLIDER_HEIGHT = (int) (20 * Game.SCALE);
    private final float LEFT_ATTACK_COLLIDER_OFFSET = COLLIDER_WIDTH + 10 * Game.SCALE,
            RIGHT_ATTACK_COLLIDER_OFFSET = -COLLIDER_WIDTH - 10 * Game.SCALE,
            ATTACK_COLLIDER_Y_OFFSET = 10 * Game.SCALE;
    private float xAttackColliderOffset = LEFT_ATTACK_COLLIDER_OFFSET;
    private boolean attacking = false;

    public Player(float x, float y, float speed) {
        physicsController = new PhysicsController(x, y, speed * Game.SCALE, PLAYER_WIDTH, PLAYER_HEIGHT);
        physicsController.setAcceleration(new Vector2D(0, Level.GRAVITY));
        collider = new Collider(x + COLLIDER_OFFSET_X, y + COLLIDER_OFFSET_Y, COLLIDER_WIDTH, COLLIDER_HEIGHT);
        GROUNDED_COLLIDER_X_OFFSET = 0;
        GROUNDED_COLLIDER_Y_OFFSET = collider.getHitBox().height + 1;
        attackCollider = new Collider(collider.getX() + xAttackColliderOffset,
                collider.getY() + ATTACK_COLLIDER_Y_OFFSET,
                ATTACK_COLLIDER_WIDTH, ATTACK_COLLIDER_HEIGHT);
        groundCollider = new Collider(collider.getX() + GROUNDED_COLLIDER_X_OFFSET,
                collider.getY() + GROUNDED_COLLIDER_Y_OFFSET, collider.getHitBox().width, 1);
        idleAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 } });
        runAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } });
        jumpAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 2 }, { 1, 2 }, { 2, 2 } });
        fallingAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 3 } });
        attackAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 4 }, { 1, 4 }, { 2, 4 } });
        hitAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 3, 5 } });
        deathAnimation = new Animation(PLAYER_ATLAS, ANIMATION_SPEED, SPRITE_WIDTH, SPRITE_HEIGHT,
                new int[][] { { 0, 6 }, { 1, 6 }, { 2, 6 }, { 3, 6 }, { 4, 6 }, { 5, 6 }, { 6, 6 }, { 7, 6 } });
    }

    @Override
    public void update() {
        if (lastUpdateDirection.getX() < 0) {
            xAttackColliderOffset = RIGHT_ATTACK_COLLIDER_OFFSET;
        } else if (lastUpdateDirection.getX() > 0) {
            xAttackColliderOffset = LEFT_ATTACK_COLLIDER_OFFSET;
        }
        float initialX = physicsController.getX();
        float initialY = physicsController.getY();
        physicsController.unblockAllMovement();
        if (isGrounded) {
            isFastFalling = false;
        }
        if (jump && isGrounded) {
            physicsController.getAcceleration().add(new Vector2D(0, JUMP_FORCE));
            // zero out accumulated velocity in the y direction
            physicsController.setVelocity(new Vector2D(physicsController.getVelocity().getX(), 0));
            jump = false;
        } else {
            physicsController.getAcceleration().setY(Level.GRAVITY);
            if (isFastFalling) {
                physicsController.getAcceleration().setY(physicsController.getAcceleration().getY() + FAST_FALL);
            }
            jump = false;
        }
        Vector2D testPosition = physicsController.testYUpdate();
        collider.moveHitBox(testPosition.getX(), testPosition.getY(), COLLIDER_OFFSET_X, COLLIDER_OFFSET_Y);
        if (!collider.checkCollision()) {
            physicsController.yUpdate();
        } else {
            collider.moveHitBox(physicsController.getX(), physicsController.getY(), COLLIDER_OFFSET_X,
                    COLLIDER_OFFSET_Y);
            float deltaY = testPosition.getY() - physicsController.getY();
            try {
                Vector2D distanceToNearestCollider = collider.distanceToNextCollider(new Vector2D(0, deltaY));
                physicsController.setY(physicsController.getY() +
                        distanceToNearestCollider.getY());
                if (distanceToNearestCollider.getY() != 0) {
                    physicsController.setVelocity(new Vector2D(physicsController.getVelocity().getX(), 0));
                }
                collider.moveHitBox(physicsController.getX(), physicsController.getY(), COLLIDER_OFFSET_X,
                        COLLIDER_OFFSET_Y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        testPosition = physicsController.testXUpdate();
        collider.moveHitBox(testPosition.getX(), testPosition.getY(), COLLIDER_OFFSET_X, COLLIDER_OFFSET_Y);
        if (!collider.checkCollision()) {
            physicsController.xUpdate();
        } else {
            float deltaX = testPosition.getX() - physicsController.getX();
            collider.moveHitBox(physicsController.getX(), physicsController.getY(), COLLIDER_OFFSET_X,
                    COLLIDER_OFFSET_Y);
            try {
                Vector2D distanceToNearestCollider = collider.distanceToNextCollider(new Vector2D(deltaX, 0));
                physicsController.setX(physicsController.getX() +
                        distanceToNearestCollider.getX());
                if (distanceToNearestCollider.getX() != 0) {
                    physicsController.setVelocity(new Vector2D(physicsController.getVelocity().getX(), 0));
                }
                collider.moveHitBox(physicsController.getX(), physicsController.getY(), COLLIDER_OFFSET_X,
                        COLLIDER_OFFSET_Y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isGrounded = groundCollider.checkCollision();
        collider.moveHitBox(physicsController.getX(), physicsController.getY(), COLLIDER_OFFSET_X, COLLIDER_OFFSET_Y);
        groundCollider.moveHitBox(collider.getX(), collider.getY(), GROUNDED_COLLIDER_X_OFFSET,
                GROUNDED_COLLIDER_Y_OFFSET);
        attackCollider.moveHitBox(collider.getX(), collider.getY(), xAttackColliderOffset, ATTACK_COLLIDER_Y_OFFSET);
        lastUpdateDirection.set(physicsController.getX() - initialX, physicsController.getY() - initialY);
    }

    @Override
    public void render(Graphics g, int xLevelOffset, int yLevelOffset) {
        if (attacking) {
            currentAnimation = attackAnimation;
            attacking = !currentAnimation.isLastFrame();
        } else if (lastUpdateDirection.magnitude() == 0) {
            currentAnimation = idleAnimation;
        } else if (lastUpdateDirection.getY() < 0 && !isGrounded) {
            currentAnimation = jumpAnimation;
        } else if (lastUpdateDirection.getY() > 0) {
            currentAnimation = fallingAnimation;
        } else if (Math.abs(lastUpdateDirection.getX()) > 0) {
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
        attackCollider.drawHitBox(g, xLevelOffset, yLevelOffset);
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

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
