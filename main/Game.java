package main;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread thread;
    private final int FPS = 120;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        long lastFrameTime = System.nanoTime();
        long now = System.nanoTime();
        while (true) {
            now = System.nanoTime();
            if (now - lastFrameTime >= timePerFrame) {
                gamePanel.repaint();
                lastFrameTime = now;
            }
        }
    }
}
