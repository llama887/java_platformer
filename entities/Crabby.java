package entities;

import java.awt.Graphics;
import java.nio.Buffer;

import components.Animation;
import main.Game;

public class Crabby extends Enemy {
    private static final int CRABBY_WIDTH_DEFAULT = 72, CRABBY_HEIGHT_DEFAULT = 32, ANIMATION_SPEED = 30;
    private static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE),
            CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);
    private Animation idleAnimation, walkAnimation, attackAnimation, hitAnimation, deathAnimation;
    private final String CRABBY_ATLAS = "assets/crabby_sprite.png";

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY_WIDTH, CRABBY_HEIGHT, 0, 0);
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
    }

    @Override
    public void render(Graphics g, int xLevelOffset, int yLevelOffset) {
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
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'update'");
        return;
    }

}
