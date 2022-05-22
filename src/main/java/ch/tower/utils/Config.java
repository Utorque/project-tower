package ch.tower.utils;

import ch.luca008.SpigotApi.Api.JSONApi;
import ch.luca008.SpigotApi.Api.TeamAPI;
import ch.luca008.SpigotApi.SpigotApi;
import ch.tower.tower.Teams;
import ch.tower.tower.Tower;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.io.File;

public class Config {

    public static File config_file = new File(Tower.get().getDataFolder(), "config.json");

    public static JSONApi.JSONReader readConfig(){
        return SpigotApi.getJSONApi().readerFromFile(config_file);
    }

    public static JSONApi.JSONWriter writeConfig(){
        return SpigotApi.getJSONApi().getWriter(readConfig().asJson());
    }

    public static void saveConfig(JSONApi.JSONWriter json){
        json.writeToFile(config_file, true);
    }

    @Nullable
    private static JSONApi.JSONReader readerAt(String multikey){
        JSONApi.JSONReader defaultReader = readConfig();
        if(multikey!=null){
            String[] keys = multikey.contains(".") ? multikey.split("\\.") : new String[]{multikey};
            JSONApi.JSONReader r = defaultReader;
            try{
                for(String key : keys){
                    r = r.getJson(key);
                }
            }catch(Exception e){
                System.err.println("Can't find json at given key " + multikey);
            }
            return r;
        }
        return null;
    }

    private static Location readLocation(String key){
        JSONApi.JSONReader r = readerAt(key);
        if(r!=null&&r.c("World")&&r.c("X")&&r.c("Y")&&r.c("Z")){
            Location l = new Location(Bukkit.getWorld(r.getString("World")), r.getDouble("X"), r.getDouble("Y"), r.getDouble("Z"));
            if(r.c("Pitch")){
                l.setPitch((float)r.getDouble("Pitch"));
            }
            if(r.c("Yaw")){
                l.setYaw((float)r.getDouble("Yaw"));
            }
        }
        return new Location(Bukkit.getWorlds().get(0), 0.5, 60, 0.5);
    }

    public static Location getSpawnLocation(Teams.PlayerTeam team){
        TeamAPI.Team t = team.getTeam();
        if(t!=null){
            return readLocation("teams."+t.getUniqueName()+".locations.spawn");
        }
        return new Location(Bukkit.getWorlds().get(0), 0.5, 60, 0.5);
    }

}
