package scenes;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import components.SceneEntities;
import main.GamePanel;
import utils.InputHandler;
import utils.Renderable;
import utils.Updateable;

public abstract class Scene implements Updateable, Renderable, InputHandler {
    protected KeyListener keyListener;
    protected MouseListener mouseListener;
    protected MouseMotionListener mouseMotionListener;
    protected GamePanel gamePanel;
    protected SceneEntities sceneEntities = new SceneEntities();

    public Scene(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public KeyListener getKeyListener() {
        return keyListener;
    }

    @Override
    public MouseListener getMouseListener() {
        return mouseListener;
    }

    @Override
    public MouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }

    public void initialize() {
    }
}
