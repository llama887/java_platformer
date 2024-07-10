package main;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class GamePanel extends JPanel{
    public GamePanel(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        System.out.println("UP");
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        System.out.println("DOWN");
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        System.out.println("LEFT");
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        System.out.println("RIGHT");
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
                System.out.println("Mouse Clicked");
            }
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse Pressed");
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse Released");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse Entered");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse Exited");
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("Mouse Dragged");
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Mouse Moved");
            }
        
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(100, 100, 100, 100);
    }
}
