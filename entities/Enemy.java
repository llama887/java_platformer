package entities;

import java.awt.image.BufferedImage;

import components.Animation;
import components.Collider;
import components.PhysicsController;
import main.Game;
import utils.Renderable;
import utils.Updateable;

public abstract class Enemy implements Renderable, Updateable {
    protected BufferedImage currentFrame;
    protected Animation currentAnimation;
    protected PhysicsController physicsController;
    protected Collider collider;
    protected float xColliderOffset, yColliderOffset;
    protected float xColliderWidth, yColliderHeight;
    public static final int CRABBY_INDEX = 0;
    public static Player player;

    public Enemy(float x, float y, float speed, float width, float height, float xColliderWidth, float yColliderHeight,
            float xColliderOffset, float yColliderOffset) {
        physicsController = new PhysicsController(x, y, speed, width, height);
        collider = new Collider(x + xColliderOffset, y + yColliderOffset, xColliderWidth, yColliderHeight);
        this.xColliderOffset = xColliderOffset;
        this.yColliderOffset = yColliderOffset;
        this.xColliderWidth = collider.getHitBox().width;
        this.yColliderHeight = collider.getHitBox().height;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public PhysicsController getPhysicsController() {
        return physicsController;
    }

    public void setPhysicsController(PhysicsController physicsController) {
        this.physicsController = physicsController;
    }

    public boolean canSeePlayer(float rangeX) {
        int playerTileY = (int) (player.getPhysicsController().getY() / Game.TILE_SIZE);
        int enemyTileY = (int) (physicsController.getY() / Game.TILE_SIZE);
        if (playerTileY != enemyTileY ||
                Math.abs(player.getPhysicsController().getX() - physicsController.getX()) > rangeX) {
            return false;
        }
        return true;
    }

}
