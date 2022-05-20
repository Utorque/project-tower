package ch.tower.tower;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Game {

    public static String SPAWN_MAP = "Spawn";
    public static String TOWER_MAP = "Tower";

    public enum GameState {
        WAITING, RUNNING, ENDED;
    }
    private GameState state = GameState.WAITING;

    public GameState getState() {
        return state;
    }

    public boolean teleportToSpawn(Player p){
        World spawn = Bukkit.getWorld(SPAWN_MAP);
        if(spawn!=null){
            p.teleport(new Location(spawn, 0.5, 50.1, 0.5));
            return true;
        }
        return false;
    }

    public void reloadMap(){
        World current = Bukkit.getWorld(TOWER_MAP); //get the actual tower map where players just played
        File currentWorldFile = new File(Tower.get().getServer().getWorldContainer(), TOWER_MAP); //get the world's file
        File originalWorldFile = new File(Tower.get().getServer().getWorldContainer(), TOWER_MAP+"_Original"); //get the world's file
        //orld original = Bukkit.getWorld(TOWER_MAP+"_Original"); //

        if(current != null){ //check if the map isn't null before trying to get players on it
            current.getPlayers().forEach(p->{
                if(!teleportToSpawn(p)){ //if we can't get the spawn map (unloaded) so cant tp player, we kick it
                    p.kickPlayer("La map dans laquelle tu étais n'est plus disponible.");
                }
            });

            Bukkit.unloadWorld(current, false); //after we kicked all players of the map, we can unload it
            if(currentWorldFile.exists()){
                currentWorldFile.delete();
            }
        }

        try{
            Files.copy(Paths.get(originalWorldFile.toURI()), Paths.get(currentWorldFile.toURI()), StandardCopyOption.COPY_ATTRIBUTES);
        }catch(IOException e){
            System.err.println("Can't copy original tower map...");
        }

        World w = Bukkit.createWorld(WorldCreator.name(TOWER_MAP));
        if(w!=null){
            w.setAutoSave(false);
            w.setTime(0);
            w.setClearWeatherDuration(9999);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        }


    }
}
