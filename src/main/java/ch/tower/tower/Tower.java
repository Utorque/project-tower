package ch.tower.tower;

import org.bukkit.plugin.java.JavaPlugin;

public final class Tower extends JavaPlugin {
    private static Tower INSTANCE;
    private Game game;
    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        game = new Game();
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
