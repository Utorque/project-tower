package ch.tower.tower;

public class Game {
    public enum GameState {
        WAITING, RUNNING, ENDED;
    }
    private GameState state;

    public GameState getState() {
        return state;
    }
}
