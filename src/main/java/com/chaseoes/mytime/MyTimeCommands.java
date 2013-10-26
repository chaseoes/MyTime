package com.chaseoes.mytime;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.chaseoes.mytime.utilities.TimeUtilities;

public class MyTimeCommands implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        String player = cs.getName();
        if (strings.length > 0 && strings[0].equalsIgnoreCase("top")) {
            int size = 5;
            if (strings.length == 2) {
                if (TimeUtilities.getStringIsInteger(strings[1])) {
                    size = TimeUtilities.getIntegerFromString(strings[1]);
                } else {
                    cs.sendMessage(ChatColor.RED + "You must supply a number as an argument.");
                    return true;
                }
            }
            
            cs.sendMessage(ChatColor.DARK_GREEN + "Top " + size + " active players:");
            for (TimeProfile topPlayer : TimeUtilities.getTopPlayers(size)) {
                cs.sendMessage(ChatColor.DARK_AQUA + topPlayer.getName() + ChatColor.GRAY + ": " + TimeUtilities.getTimeString(topPlayer.getSecondsOnline()));
            }
            return true;
        }
        
        if (strings.length > 0) {
            player = strings[0];
        }

        long secondsPlayed = MyTime.getInstance().getTimeProfile(player).getSecondsOnline();
        
        if (player.equalsIgnoreCase(cs.getName())) {
            cs.sendMessage(ChatColor.DARK_GREEN + "You have played for:");
        } else {
            cs.sendMessage(ChatColor.DARK_GREEN + player + " has played for:");
        }
        
        cs.sendMessage(TimeUtilities.getTimeString(secondsPlayed));
        return true;
    }

}
