package main;

public class Game {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
    }
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}