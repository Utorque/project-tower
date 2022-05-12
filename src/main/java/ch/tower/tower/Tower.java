package ch.tower.tower;

import org.bukkit.plugin.java.JavaPlugin;
import ch.tower.tower.Game.GameState;

public final class Tower extends JavaPlugin {
    private static Tower INSTANCE;
    private Game game;
    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        game = new Game();
        GameState state = game.getState();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Tower get() {
        return INSTANCE;
    }
    public Game getGame() {
        return game;
    }
}
