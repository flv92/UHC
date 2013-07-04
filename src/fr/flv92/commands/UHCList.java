package fr.flv92.commands;

import fr.flv92.UHC;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UHCList implements OnUHCCommand {

    @Override
    public boolean onCommand(UHC uhc, CommandSender sender, String[] args) {
        ArrayList<Player> players = uhc.uhcPlayers;
        ArrayList<String> message = new ArrayList();
        for (Player p : players) {
            if (message.isEmpty()) {
                message.add("The following players will play the UHC game: ");
            }
            message.add(ChatColor.GREEN + " - " + p.getName());
        }
        if (!message.isEmpty()) {
            sender.sendMessage(message.toArray(new String[message.size()]));
        }
        else{
            sender.sendMessage(ChatColor.GOLD + "No UHC player :(");
        }
        return true;
    }
}
