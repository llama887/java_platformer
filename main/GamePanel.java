package main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Dimension;
import java.io.File;

public class GamePanel extends JPanel {
    public int x = 100, y = 100, x_step = 5, y_step = 5, width = 100, height = 100;
    private BufferedImage image;

    public GamePanel() {
        setPreferredSize(new Dimension(1280, 800));
        // importing images
        try {
            image = ImageIO.read(new File("assets/player_sprites.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        y -= y_step;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        y += y_step;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        x -= x_step;
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        x += x_step;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getSubimage(0, 0, 64, 40), x, y, null);
    }
}
