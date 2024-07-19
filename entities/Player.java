package entities;

import components.Animation;
import components.PhysicsController;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.Renderable;
import utils.Vector2D;

public class Player implements Renderable {
        private final int SPRITE_WIDTH = 64, SPRITE_HEIGHT = 40, ANIMATION_SPEED = 30;
        private final String PLAYER_ATLAS = "assets/player_sprites.png";
        private Animation idleAnimation, runAnimation, jumpAnimation, fallingAnimation, groundAnimation, hitAnimation,
                        attack1Animation, jumpAttack1Animation, jumpAttack2Animation;
        private BufferedImage currentFrame;
        private Animation currentAnimation;
        private PhysicsController physicsController;

        public Player(double x, double y, double speed, double width, double height) {
                physicsController = new PhysicsController(x, y, speed, width, height);
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

        public void update() {
                physicsController.update();
        }

        @Override
        public BufferedImage render(Graphics g) {
                Vector2D movementDirection = physicsController.getMovementDirection();
                if (movementDirection.magnitude() == 0) {
                        currentAnimation = idleAnimation;
                } else if (Math.abs(movementDirection.getX()) > 0) {
                        currentAnimation = runAnimation;
                } else if (movementDirection.getY() < 0) {
                        currentAnimation = jumpAnimation;
                } else if (movementDirection.getY() > 0) {
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
                return currentAnimation.getFrame();
        }

        public PhysicsController getPhysicsController() {
                return physicsController;
        }
}
