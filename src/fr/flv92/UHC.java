/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.flv92;

import fr.flv92.commands.UHCAdd;
import fr.flv92.commands.UHCList;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UHC extends JavaPlugin {

    public ArrayList<Player> uhcPlayers = new ArrayList<Player>();

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("uhc-add")) {
            return (new UHCAdd()).onCommand(this, sender, args);
        } else if(cmd.getName().equalsIgnoreCase("uhc-list")){
            return (new UHCList()).onCommand(this, sender, args);
        }
        return false;
    }
}
