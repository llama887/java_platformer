package entities;

import components.Animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.Renderable;

public class Player extends Entity implements Renderable {
    private final int spriteWidth = 64, spriteHeight = 40;
    private Animation idleAnimation, runAnimation, jumpAnimation, fallingAnimation, groundAnimation, hitAnimation,
            attack1Animation, jumpAttack1Animation, jumpAttack2Animation;
    private BufferedImage currentFrame;

    enum AnimationType {
        IDLE, RUN, JUMP, FALLING, GROUND, HIT, ATTACK_1, JUMP_ATTACK_1, JUMP_ATTACK_2
    }

    private AnimationType currentAnimationType = AnimationType.IDLE;

    public Player(double x, double y, double speed, double width, double height) {
        super(x, y, speed, width, height);
        idleAnimation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 } });
        runAnimation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } });
        jumpAnimation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 2 }, { 1, 2 }, { 2, 2 } });
        fallingAnimation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 3 } });
        groundAnimation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 4 }, { 1, 4 } });
        hitAnimation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 3, 5 } });
        attack1Animation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 6 }, { 1, 6 }, { 2, 6 }, { 3, 6 } });
        jumpAttack1Animation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 7 }, { 1, 7 }, { 2, 7 } });
        jumpAttack2Animation = new Animation("assets/player_sprites.png", 30, spriteWidth, spriteHeight,
                new int[][] { { 0, 8 }, { 1, 8 }, { 2, 8 } });
    }

    @Override
    public void update() {
        super.update();
        System.err.println("Player Position= " + x + ", " + y);
    }

    @Override
    public BufferedImage render(Graphics g) {
        if (movementDirection.magnitude() == 0) {
            currentAnimationType = AnimationType.IDLE;
        } else if (Math.abs(movementDirection.getX()) > 0) {
            currentAnimationType = AnimationType.RUN;
        } else if (movementDirection.getY() < 0) {
            currentAnimationType = AnimationType.JUMP;
        } else if (movementDirection.getY() > 0) {
            currentAnimationType = AnimationType.FALLING;
        }
        Animation currentAnimation = null;
        switch (currentAnimationType) {
            case IDLE:
                currentAnimation = idleAnimation;
                break;
            case RUN:
                currentAnimation = runAnimation;
                break;
            case JUMP:
                currentAnimation = jumpAnimation;
                break;
            case FALLING:
                currentAnimation = fallingAnimation;
                break;
            case GROUND:
                currentAnimation = groundAnimation;
                break;
            case HIT:
                currentAnimation = hitAnimation;
                break;
            case ATTACK_1:
                currentAnimation = attack1Animation;
                break;
            case JUMP_ATTACK_1:
                currentAnimation = jumpAttack1Animation;
                break;
            case JUMP_ATTACK_2:
                currentAnimation = jumpAttack2Animation;
                break;
            default:
                currentAnimation = idleAnimation;
                break;
        }
        if (currentAnimation.getAnimationTick() >= currentAnimation.getAnimationSpeed()) {
            currentAnimation.nextFrame();
            currentFrame = currentAnimation.getFrame();
            currentAnimation.setAnimationTick(0);
        }
        currentAnimation.setAnimationTick(currentAnimation.getAnimationTick() + 1);

        g.drawImage(currentFrame, (int) x, (int) y, (int) width, (int) height, null);
        return currentAnimation.getFrame();
    }
}
