package main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import components.Animation;

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
    public int x = 100, y = 100, xStep = 5, yStep = 5, width = 100, height = 100;
    private final int spriteWidth = 64, spriteHeight = 40;
    private BufferedImage image;
    private BufferedImage[][] animations = new BufferedImage[9][6];
    private int animationTick, animationIndex, animationSpeed = 12;
    private Animation idleAnimation, runAnimation;
    private BufferedImage currentFrame;

    enum AnimationType {
        IDLE, RUN
    }

    private AnimationType currentAnimation = AnimationType.IDLE;

    public GamePanel() {
        setPreferredSize(new Dimension(1280, 800));
        // importing images
        try {
            image = ImageIO.read(new File("assets/player_sprites.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // set animation frames
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        idleAnimation = new Animation("assets/player_sprites.png", spriteWidth, spriteHeight,
                new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 } });
        runAnimation = new Animation("assets/player_sprites.png", spriteWidth, spriteHeight,
                new int[][] { { 1, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } });
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
                        currentAnimation = AnimationType.RUN;
                        y -= yStep;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        currentAnimation = AnimationType.RUN;
                        y += yStep;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        currentAnimation = AnimationType.RUN;
                        x -= xStep;
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        currentAnimation = AnimationType.RUN;
                        x += xStep;
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

    private void updateAnimation() {
        if (animationTick >= animationSpeed) {
            if (currentAnimation == AnimationType.IDLE) {
                currentFrame = idleAnimation.nextFrame();
            } else {
                currentFrame = runAnimation.nextFrame();
            }
            animationTick = 0;
        }
        animationTick++;
    }

    public void paintComponent(Graphics g) {
        updateAnimation();
        super.paintComponent(g);
        g.drawImage(currentFrame, x, y, 128, 80, null);
    }
}
