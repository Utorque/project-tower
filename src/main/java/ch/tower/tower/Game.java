package ch.tower.tower;

public class Game {
    public enum GameState {
        WAITING, RUNNING, ENDED;
    }
    private GameState state = GameState.WAITING;

    public GameState getState() {
        return state;
    }
}
