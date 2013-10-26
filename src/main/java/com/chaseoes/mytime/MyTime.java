package com.chaseoes.mytime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.chaseoes.mytime.utilities.DataFile;

public class MyTime extends JavaPlugin {

    public static int TIME_INCREMENT = 1;
    public static int AWAY_TIME = 240;
    private static MyTime instance;
    private HashMap<String, TimeProfile> profiles = new HashMap<String, TimeProfile>();

    public static MyTime getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        DataFile.getDataFile().reloadDataConfig();
        DataFile.getDataFile().saveDataConfig();
        getCommand("mytime").setExecutor(new MyTimeCommands());
        getServer().getScheduler().runTaskTimer(this, new TimeTask(), 0L, TIME_INCREMENT * 20L);
    }

    public void onDisable() {
        instance = null;
        saveAllProfiles();
        profiles.clear();
        DataFile.getDataFile().saveDataConfig();
    }
    
    public List<TimeProfile> getTimeProfiles() {
        return new ArrayList<TimeProfile>(profiles.values());
    }
    
    public List<TimeProfile> getAllTimeProfiles() {
        saveAllProfiles();
        List<TimeProfile> allProfiles = new ArrayList<TimeProfile>();
        for (String player : DataFile.getDataFile().getDataConfig().getKeys(false)) {
            allProfiles.add(new TimeProfile(player));
        }
        return allProfiles;
    }

    public TimeProfile getTimeProfile(String name) {
        if (profiles.containsKey(name)) {
            return profiles.get(name);
        }
        
        TimeProfile profile = new TimeProfile(name);
        profiles.put(name, profile);
        return profile;
    }
    
    public void saveAllProfiles() {
        for (TimeProfile profile : getTimeProfiles()) {
            profile.saveProfile();
        }
    }

}
