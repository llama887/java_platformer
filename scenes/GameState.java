package scenes;

import main.GamePanel;

public enum GameState {
    UNASSIGNED, PLAYING, MENU;

    private static GameState state = UNASSIGNED;

    public static void setState(GameState newState, GamePanel gamePanel) {
        if (GameState.state != newState) {
            GameState previousState = GameState.state;
            GameState.state = newState;
            gamePanel.updateInputHandler(previousState, GameState.state);
        }
    }

    public static GameState getState() {
        return state;
    }
}
