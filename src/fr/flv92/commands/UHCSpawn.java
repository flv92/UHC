package fr.flv92.commands;

import fr.flv92.UHC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UHCSpawn implements OnUHCCommand {

    @Override
    public boolean onCommand(UHC uhc, CommandSender sender, String[] args) {
        Block block = ((Player) sender).getTargetBlock(null, 16);
        if ((block.getType() == Material.SIGN_POST) || (block.getType() == Material.WALL_SIGN)) {
            Sign sign = (Sign) block.getState();
            sign.setLine(0, "" + ChatColor.GREEN + "[UHC]");
            sign.setLine(3, ChatColor.BLUE + "Spawn " + (uhc.spawns.size() + 1));
            sign.update();
            ((Player) sender).sendMessage(ChatColor.RED + "You just registered a spawn!");
            uhc.spawns.add(block.getLocation());
            return true;

        }
        return true;
    }
}
