package com.chaseoes.mytime.utilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;

import com.chaseoes.mytime.MyTime;
import com.chaseoes.mytime.TimeProfile;

public class TimeUtilities {

    public static String getTimeString(long seconds) {
        int day = (int)TimeUnit.SECONDS.toDays(seconds);        
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        ChatColor numbers = ChatColor.GOLD;
        ChatColor text = ChatColor.GREEN;
        return numbers + "" + day + text + " days, " + numbers + hours + text + " hours, " + numbers + minute + text + " minutes, " + numbers + second + text + " seconds.";
    }

    public static List<TimeProfile> getTopPlayers(int size) {
        List<TimeProfile> topPlayers = MyTime.getInstance().getAllTimeProfiles();
        Collections.sort(topPlayers, new Comparator<TimeProfile>(){
            public int compare(TimeProfile player1, TimeProfile player2){
                return ((Long) player2.getSecondsOnline()).compareTo((Long) player1.getSecondsOnline());
            }});

        if (topPlayers.size() > size){
            System.out.println(topPlayers.size() + " > " + size);
            topPlayers = topPlayers.subList(0, size);
        }

        return topPlayers;
    }
    
    public static int getIntegerFromString(String s) {
        return Integer.parseInt(s);
    }
    
    public static boolean getStringIsInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
