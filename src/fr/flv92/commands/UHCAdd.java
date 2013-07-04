package fr.flv92.commands;

import fr.flv92.UHC;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UHCAdd implements OnUHCCommand {

    @Override
    public boolean onCommand(UHC uhc, CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equals("@a")) {
            ArrayList<String> returnString = new ArrayList();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!uhc.uhcPlayers.contains(p)) {
                    uhc.uhcPlayers.add(p);

                    if (returnString.isEmpty()) {
                        returnString.add("The following players have been successfully added");
                    }
                    returnString.add(" - " + p.getName());
                }
            }
            sender.sendMessage(returnString.toArray(new String[returnString.size()]));
            return true;
        } else if (args.length >= 1) {
            ArrayList<String> returnString = new ArrayList();
            ArrayList<String> returnStringBad = new ArrayList();
            for (String s : args) {
                Player p = Bukkit.getPlayer(s);
                if (p != null && !uhc.uhcPlayers.contains(p)) {
                    if (returnString.isEmpty()) {
                        returnString.add("The following players have been successfully added");
                    }
                    uhc.uhcPlayers.add(p);
                    returnString.add(ChatColor.GREEN + " - " + p.getName());
                } else {
                    returnStringBad.add(ChatColor.RED + " - " + s);
                }

            }
            if (!returnStringBad.isEmpty()) {
                returnString.add("The following players can't be added");
            }
            returnString.addAll(returnStringBad);
            sender.sendMessage(returnString.toArray(new String[returnString.size()]));
            return true;
        }
        return false;
    }
}
