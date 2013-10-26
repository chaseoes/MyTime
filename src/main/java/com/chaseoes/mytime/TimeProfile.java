package com.chaseoes.mytime;

import org.bukkit.entity.Player;

import com.chaseoes.mytime.utilities.DataFile;

public class TimeProfile {

    private String name;
    private long secondsOnline = 0;
    private long secondsAway = 0;
    private String lastLocation = "0:0:0";

    public TimeProfile(String name) {
        this.name = name;
        loadProfile();
    }

    private void loadProfile() {
        if (DataFile.getDataFile().getDataConfig().getString(name) != null) {
            secondsOnline = DataFile.getDataFile().getDataConfig().getLong(name);
        } else {
            saveProfile();
        }
    }

    public String getName() {
        return name;
    }

    public void saveProfile() {
        if (MyTime.getInstance().getServer().getOfflinePlayer(name).hasPlayedBefore()) {
            DataFile.getDataFile().getDataConfig().set(name, secondsOnline);
            DataFile.getDataFile().saveDataConfig();
        }
    }

    public void add() {
        secondsOnline = secondsOnline + MyTime.TIME_INCREMENT;
    }
    
    public void subtract(long seconds) {
        secondsOnline = secondsOnline - seconds;
    }

    public long getSecondsOnline() {
        return secondsOnline;
    }
    
    public void addSecondsAway() {
        secondsAway = secondsAway + MyTime.TIME_INCREMENT;
    }
    
    public void clearSecondsAway() {
        secondsAway = 0;
    }
    
    public long getSecondsAway() {
        return secondsAway;
    }
    
    public void setLastLocation(Player player) {
        lastLocation = player.getLocation().getBlockX() + ":" + player.getLocation().getBlockY() + ":" + player.getLocation().getBlockZ();
    }
    
    public boolean compareLocations(Player player) {
        String[] location = lastLocation.split(":");
        return player.getLocation().getBlockX() == Integer.parseInt(location[0]) && player.getLocation().getBlockY() == Integer.parseInt(location[1]) && player.getLocation().getBlockZ() == Integer.parseInt(location[2]);
    }

}
