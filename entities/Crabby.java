package entities;

import java.awt.Graphics;

import components.Animation;
import components.Collider;
import main.Game;
import scenes.Level;
import utils.Vector2D;

public class Crabby extends Enemy {
    private static final int CRABBY_WIDTH_DEFAULT = 72, CRABBY_HEIGHT_DEFAULT = 32, ANIMATION_SPEED = 30;
    private static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE),
            CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);
    private static final float COLLIDER_WIDTH = 22 * Game.SCALE, COLLIDER_HEIGHT = 19 * Game.SCALE;
    private Animation idleAnimation, walkAnimation, attackAnimation, hitAnimation, deathAnimation;
    private final String CRABBY_ATLAS = "assets/crabby_sprite.png";
    private static final int X_COLLIDER_OFFSET = (int) (26 * Game.SCALE), Y_COLLIDER_OFFSET = (int) (9 * Game.SCALE);
    private Collider floorDector;
    private float xFloorDectorOffset = -1, yFloorDectorOffset = collider.getHitBox().height + 1;
    private Vector2D lastMovementDirection = new Vector2D(0, 0);
    private static float SPEED = 0.5f;
    private boolean touchedFloor = false;
    private float sightRangeX = 200 * Game.SCALE;
    private float attackRangeX = 30 * Game.SCALE;
    private Collider attackCollider;
    private final int ATTACK_COLLIDER_X_OFFSET = (int) (Game.SCALE * -30),
            ATTACK_COLLIDER_WIDTH = (int) (82 * Game.SCALE), ATTACK_COLLIDER_HEIGHT = (int) COLLIDER_HEIGHT;
    private boolean facingLeft = true, addedPlayerHitbox = false, playerDamaged = false;
    private final int ATTACK_DAMAGE = 20;

    public enum CrabbyState {
        IDLE, WALK, ATTACK, HIT, DEATH
    }

    private boolean dead = false;

    private CrabbyState aiState = CrabbyState.IDLE;

    public Crabby(float x, float y) {
        super(x, y, SPEED * Game.SCALE, CRABBY_WIDTH, CRABBY_HEIGHT, COLLIDER_WIDTH, COLLIDER_HEIGHT, X_COLLIDER_OFFSET,
                Y_COLLIDER_OFFSET);
        idleAnimation = new Animation(CRABBY_ATLAS, ANIMATION_SPEED, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT,
                new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 },
                        { 8, 0 } });
        walkAnimation = new Animation(CRABBY_ATLAS, ANIMATION_SPEED, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT,
                new int[][] { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } });
        attackAnimation = new Animation(CRABBY_ATLAS, ANIMATION_SPEED, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT,
                new int[][] { { 0, 2 }, { 1, 2 }, { 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, { 6, 2 } });
        hitAnimation = new Animation(CRABBY_ATLAS, ANIMATION_SPEED, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT,
                new int[][] { { 0, 3 }, { 1, 3 }, { 2, 3 }, { 3, 3 } });
        deathAnimation = new Animation(CRABBY_ATLAS, ANIMATION_SPEED, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT,
                new int[][] { { 0, 4 }, { 1, 4 }, { 2, 4 }, { 3, 4 }, { 4, 4 } });
        currentAnimation = idleAnimation;
        floorDector = new Collider(collider.getX() + xFloorDectorOffset, collider.getY() + yFloorDectorOffset,
                1, 1);
        physicsController.setMovementDirection(-1, 0);
        attackCollider = new Collider(collider.getX() + ATTACK_COLLIDER_X_OFFSET, collider.getY(),
                ATTACK_COLLIDER_WIDTH, ATTACK_COLLIDER_HEIGHT);
    }

    public Crabby(Enemy enemy) {
        this(enemy.getPhysicsController().getX(), enemy.getPhysicsController().getY());
    }

    @Override
    public void render(Graphics g, int xLevelOffset, int yLevelOffset) {
        if (dead) {
            return;
        }
        if (currentAnimation.getAnimationTick() >= currentAnimation.getAnimationSpeed()) {
            currentAnimation.nextFrame();
            currentFrame = currentAnimation.getFrame();
            if (currentAnimation.isLastFrame() && aiState == CrabbyState.ATTACK) {
                setAiState(CrabbyState.WALK);
            }
            currentAnimation.setAnimationTick(0);
        }
        currentAnimation.setAnimationTick(currentAnimation.getAnimationTick() + 1);

        g.drawImage(currentFrame, (int) physicsController.getX() - xLevelOffset + (facingLeft ? 0 : CRABBY_WIDTH),
                (int) physicsController.getY() - yLevelOffset,
                (int) physicsController.getWidth() * (facingLeft ? 1 : -1),
                (int) physicsController.getHeight(),
                null);
        // collider.drawHitBox(g, xLevelOffset, yLevelOffset);
        // floorDector.drawHitBox(g, xLevelOffset, yLevelOffset);
        // attackCollider.drawHitBox(g, xLevelOffset, yLevelOffset);
    }

    @Override
    public void update() {
        if (!addedPlayerHitbox) {
            attackCollider.addOtherCollider(player.getCollider());
            addedPlayerHitbox = true;
        }
        if (aiState != CrabbyState.ATTACK) {
            playerDamaged = false;
        }
        if (collider.checkCollision(player.getAttackCollider())) {
            setAiState(CrabbyState.HIT);
        }
        float initialX = physicsController.getX();
        float initialY = physicsController.getY();
        physicsController.unblockAllMovement();
        switch (aiState) {
            case IDLE:
                setAiState(CrabbyState.WALK);
                break;
            case WALK:
                if (lastMovementDirection.getX() > 0) {
                    facingLeft = false;
                } else if (lastMovementDirection.getX() < 0) {
                    facingLeft = true;
                }
                if (canSeePlayer(sightRangeX)) {
                    physicsController
                            .setMovementDirection(
                                    (int) (player.getPhysicsController().getX() - physicsController.getX()), 0);
                    physicsController.getMovementDirection().normalize();
                }
                if (physicsController.getMovementDirection().getX() == 0) {
                    physicsController.setMovementDirection(-1, 0);
                }
                if (physicsController.getMovementDirection().getX() > 0) {
                    xFloorDectorOffset = collider.getHitBox().width + 1;
                } else if (physicsController.getMovementDirection().getX() < 0) {
                    xFloorDectorOffset = -1;
                }
                if (!floorDector.checkCollision()) {
                    physicsController.getMovementDirection().setX(physicsController.getMovementDirection().getX() * -1);
                }
                if (!touchedFloor) {
                    physicsController.getAcceleration().setY(Level.GRAVITY);
                    Vector2D testPosition = physicsController.testYUpdate();
                    collider.moveHitBox(testPosition.getX(), testPosition.getY(), xColliderOffset, yColliderOffset);
                    if (!collider.checkCollision()) {
                        physicsController.yUpdate();
                    } else {
                        touchedFloor = true;
                        collider.moveHitBox(physicsController.getX(), physicsController.getY(), xColliderOffset,
                                yColliderOffset);
                        float deltaY = testPosition.getY() - physicsController.getY();
                        try {
                            Vector2D distanceToNearestCollider = collider
                                    .distanceToNextCollider(new Vector2D(0, deltaY));
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
                }
                Vector2D testPosition = physicsController.testXUpdate();
                collider.moveHitBox(testPosition.getX(), testPosition.getY(), xColliderOffset, yColliderOffset);
                if (!collider.checkCollision()) {
                    physicsController.xUpdate();
                } else {
                    float deltaX = testPosition.getX() - physicsController.getX();
                    collider.moveHitBox(physicsController.getX(), physicsController.getY(), xColliderOffset,
                            yColliderOffset);
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
                floorDector.moveHitBox(collider.getX(), collider.getY(), xFloorDectorOffset, yFloorDectorOffset);
                lastMovementDirection = new Vector2D(physicsController.getX() - initialX,
                        physicsController.getY() - initialY);
                attackCollider.moveHitBox(collider.getX(), collider.getY(), ATTACK_COLLIDER_X_OFFSET, 0);
                if (canSeePlayer(attackRangeX)) {
                    setAiState(CrabbyState.ATTACK);
                    break;
                }
                break;
            case ATTACK:
                physicsController.setVelocity(new Vector2D(0.0f, 0.0f));
                if (attackAnimation.isLastFrame()) {
                    setAiState(CrabbyState.WALK);
                }
                if (attackCollider.checkCollision() && attackAnimation.getCurrentIndex() == 3 && !playerDamaged) {
                    player.takeDamage(ATTACK_DAMAGE);
                    playerDamaged = true;
                }
                break;
            case HIT:
                collider.setActive(false);
                if (hitAnimation.isLastFrame()) {
                    setAiState(CrabbyState.DEATH);
                }
                break;
            case DEATH:
                if (deathAnimation.isLastFrame()) {
                    dead = true;
                }
                break;
            default:
                break;
        }
        return;
    }

    public Collider getFloorDector() {
        return floorDector;
    }

    public void setFloorDector(Collider floorDector) {
        this.floorDector = floorDector;
    }

    public void setAiState(CrabbyState aiState) {
        this.aiState = aiState;
        switch (aiState) {
            case IDLE:
                idleAnimation.setCurrentIndex(0);
                currentAnimation = idleAnimation;
                break;
            case WALK:
                walkAnimation.setCurrentIndex(0);
                currentAnimation = walkAnimation;
                break;
            case ATTACK:
                physicsController.setMovementDirection(0, 0);
                attackAnimation.setCurrentIndex(0);
                currentAnimation = attackAnimation;
                break;
            case HIT:
                hitAnimation.setCurrentIndex(0);
                currentAnimation = hitAnimation;
                break;
            case DEATH:
                deathAnimation.setCurrentIndex(0);
                currentAnimation = deathAnimation;
                break;
            default:
                break;
        }
    }

    public boolean isDead() {
        return dead;
    }
}
