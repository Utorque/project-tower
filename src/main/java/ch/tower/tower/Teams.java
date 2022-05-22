package ch.tower.tower;

import ch.luca008.SpigotApi.APIPlayer;
import ch.luca008.SpigotApi.Api.TeamAPI;
import ch.luca008.SpigotApi.Packets.ScoreboardTeam;
import ch.luca008.SpigotApi.SpigotApi;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Teams {

    public enum PlayerTeam{
        BLUE("blue"), RED("red"), SPECTATOR("spectator");

        String teamid;
        PlayerTeam(String teamid){
            this.teamid = teamid;
        }
        @Nullable
        public TeamAPI.Team getTeam(){
            return SpigotApi.getTeamApi().getTeam(teamid);
        }
    }

    public enum TeamLocation{
        SPAWN
    }

    public static void register(){
        SpigotApi.getTeamApi().registerTeam(
                new TeamAPI.TeamBuilder(PlayerTeam.BLUE.teamid)
                        .setDisplayName("Team bleue")
                        .setCollisions(ScoreboardTeam.TeamPush.NEVER)
                        .setFriendlyFire(false)
                        .setColor(ScoreboardTeam.TeamColor.BLUE)
                        .setSortOrder(1)
                        .create());

        SpigotApi.getTeamApi().registerTeam(
                new TeamAPI.TeamBuilder(PlayerTeam.RED.teamid)
                        .setDisplayName("Team rouge")
                        .setCollisions(ScoreboardTeam.TeamPush.NEVER)
                        .setFriendlyFire(false)
                        .setColor(ScoreboardTeam.TeamColor.RED)
                        .setSortOrder(2)
                        .create());

        SpigotApi.getTeamApi().registerTeam(
                new TeamAPI.TeamBuilder(PlayerTeam.SPECTATOR.teamid)
                        .setDisplayName("Team spectateur")
                        .setCollisions(ScoreboardTeam.TeamPush.NEVER)
                        .setFriendlyFire(false)
                        .setColor(ScoreboardTeam.TeamColor.GRAY)
                        .setSortOrder(3)
                        .create());
    }

    public static void unregister(){
        SpigotApi.getTeamApi().unregisterTeam(PlayerTeam.BLUE.getTeam());
        SpigotApi.getTeamApi().unregisterTeam(PlayerTeam.RED.getTeam());
        SpigotApi.getTeamApi().unregisterTeam(PlayerTeam.SPECTATOR.getTeam());
    }

    @Nullable
    private static PlayerTeam fromTeam(TeamAPI.Team team){
        if(team!=null) {
            try{
                return PlayerTeam.valueOf(team.getUniqueName().toUpperCase());
            }catch(Exception e){
                System.err.println("Can't find playerteam with name " + team.getUniqueName());
            }
        }
        return null;
    }

    public static void setTeam(Player p, PlayerTeam team){
        APIPlayer player = SpigotApi.getPlayer(p.getUniqueId());
        if(player!=null){
            player.setTeam(team.teamid, true); //if 2nd arg is false, api wont add the player in the new team if he already has one
        }
    }

    @Nullable
    public static PlayerTeam getTeam(Player p){
        APIPlayer player = SpigotApi.getPlayer(p.getUniqueId());
        if(player!=null){
            return fromTeam(player.getTeam());
        }
        return null;
    }

    public static boolean isBlue(Player p){
        return PlayerTeam.BLUE.equals(getTeam(p));
    }

    public static boolean isRed(Player p){
        return PlayerTeam.RED.equals(getTeam(p));
    }

    public static boolean isSpectator(Player p){
        return PlayerTeam.SPECTATOR.equals(getTeam(p));
    }


}
