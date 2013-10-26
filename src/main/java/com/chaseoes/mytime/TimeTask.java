package com.chaseoes.mytime;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeTask extends BukkitRunnable {
 
    public void run() {
        MyTime plugin = MyTime.getInstance();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            TimeProfile profile = MyTime.getInstance().getTimeProfile(player.getName());
            profile.add();
            
            if (profile.compareLocations(player)) {
                profile.addSecondsAway();
            } else {
                profile.clearSecondsAway();
            }
            
            profile.setLastLocation(player);
            
            if (profile.getSecondsAway() == MyTime.AWAY_TIME) {
                player.kickPlayer(ChatColor.RED + "You have been kicked for being AFK.");
                profile.subtract(MyTime.AWAY_TIME);
                profile.clearSecondsAway();
                profile.saveProfile();
            }
        }
    }

}
