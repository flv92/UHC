package fr.flv92.commands;

import fr.flv92.UHC;
import org.bukkit.command.CommandSender;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public interface OnUHCCommand {

    public boolean onCommand(UHC uhc, CommandSender sender, String[] args);
}
