/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.flv92;

import fr.flv92.commands.UHCSpawn;
import fr.flv92.commands.UHCAdd;
import fr.flv92.commands.UHCList;
import fr.flv92.commands.UHCStart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UHC extends JavaPlugin implements Listener {

    public ArrayList<Player> uhcPlayers = new ArrayList<Player>();
    public ArrayList<Location> spawns = new ArrayList<Location>();
    public HashMap<ChatColor, Player> uchPlayersWithTeams = new HashMap<ChatColor, Player>();
    public boolean isGameStarted = false;
    public int mapSize = 1000;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getWorlds().get(0).setDifficulty(Difficulty.HARD);
        ScoreboardManager scm = Bukkit.getScoreboardManager();
        Scoreboard sc = scm.getMainScoreboard();
        Objective obj1 = sc.registerNewObjective("Health", "health");
        obj1.setDisplaySlot(DisplaySlot.BELOW_NAME);
        obj1.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.isOp()) {
            boolean returnCode = false;
            if (cmd.getName().equalsIgnoreCase("uhc-addPlayer")) {
                returnCode = (new UHCAdd()).onCommand(this, sender, args);
            } else if (cmd.getName().equalsIgnoreCase("uhc-list")) {
                returnCode = (new UHCList()).onCommand(this, sender, args);
            } else if (cmd.getName().equalsIgnoreCase("uhc-start")) {
                returnCode = (new UHCStart()).onCommand(this, sender, args);
            } else if (cmd.getName().equalsIgnoreCase("uhc-addSpawn")) {
                returnCode = (new UHCSpawn()).onCommand(this, sender, args);
            }
            for (Player p : uhcPlayers) {
                if (!p.getDisplayName().startsWith(ChatColor.GREEN + "")) {
                    p.setPlayerListName(ChatColor.GREEN + p.getName() + ChatColor.RESET);
                    p.setDisplayName(ChatColor.GREEN + p.getName() + ChatColor.RESET);
                    ScoreboardManager scm = Bukkit.getScoreboardManager();
                    Scoreboard sc = scm.getMainScoreboard();
                    sc.getObjective(commandLabel).getScore(p).setScore(20);
                }
            }
            return returnCode;
        } else {
            sender.sendMessage(ChatColor.RED + "You're not op, umad!");
            return true;
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (isGameStarted && uhcPlayers.contains(e.getEntity())) {
            e.getEntity().setPlayerListName(ChatColor.RED + e.getEntity().getName() + ChatColor.RESET);
            e.getEntity().setDisplayName(ChatColor.RED + e.getEntity().getName() + ChatColor.RED);

        }
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntityType() == EntityType.PLAYER && e.getRegainReason() == RegainReason.SATIATED && isGameStarted) {
            if (uhcPlayers.contains((Player) e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (isGameStarted) {
            Block block = e.getBlock();
            if (block.getTypeId() == 20) {
                boolean mur1 = block.getLocation().getBlockX() > -mapSize / 2
                        && block.getLocation().getBlockX() < mapSize / 2
                        && block.getLocation().getBlockZ() == -mapSize / 2;
                boolean mur2 = block.getLocation().getBlockX() > -mapSize / 2
                        && block.getLocation().getBlockX() < mapSize / 2
                        && block.getLocation().getBlockZ() == mapSize / 2;
                boolean mur3 = block.getLocation().getBlockZ() > -mapSize / 2
                        && block.getLocation().getBlockZ() < mapSize / 2
                        && block.getLocation().getBlockX() == -mapSize / 2;
                boolean mur4 = block.getLocation().getBlockZ() > -mapSize / 2
                        && block.getLocation().getBlockZ() < mapSize / 2
                        && block.getLocation().getBlockX() == mapSize / 2;
                if (mur1 || mur2 || mur3 || mur4) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
