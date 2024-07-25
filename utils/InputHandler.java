package utils;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface InputHandler {
    public KeyListener getKeyListener();

    public MouseListener getMouseListener();

    public MouseMotionListener getMouseMotionListener();
}
