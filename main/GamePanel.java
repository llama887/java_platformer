package main;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Dimension;

public class GamePanel extends JPanel {
    private Game game;
    private boolean key_w, key_a, key_s, key_d, key_space;

    public GamePanel(Game game) {
        this.game = game;
        // set game panel size
        setPreferredSize(new Dimension(1280, 800));
        // handle inputs
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        key_w = true;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        key_s = true;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        key_a = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        key_d = true;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        key_w = false;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        key_s = false;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        key_a = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        key_d = false;
                        break;
                    default:
                        break;
                }
            }

        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    public void handleInputs() {
        game.gameObjects.player.setMovementDirection(0, 0);
        int playerXDirection = 0, playerYDirection = 0;
        if (key_w) {
            playerYDirection--;
        }
        if (key_s) {
            playerYDirection++;
        }
        if (key_a) {
            playerXDirection--;
        }
        if (key_d) {
            playerXDirection++;
        }
        game.gameObjects.player.setMovementDirection(playerXDirection, playerYDirection);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
}
