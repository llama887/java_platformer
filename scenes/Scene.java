package scenes;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import utils.InputHandler;
import utils.Renderable;
import utils.Updateable;

public abstract class Scene implements Updateable, Renderable, InputHandler {
    protected KeyListener keyListener;
    protected MouseListener mouseListener;
    protected MouseMotionListener mouseMotionListener;
}
