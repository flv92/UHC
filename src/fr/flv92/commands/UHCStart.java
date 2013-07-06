package fr.flv92.commands;

import fr.flv92.UHC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UHCStart implements OnUHCCommand {

    @Override
    public boolean onCommand(UHC uhc, final CommandSender sender, String[] args) {
        if(uhc.isGameStarted){
            sender.sendMessage(ChatColor.RED + "Game already started!");
            return true;
        }
        if(uhc.uhcPlayers.isEmpty()){
            sender.sendMessage(ChatColor.RED + "No player!");
            return true;
        }
        if(uhc.spawns.isEmpty()){
            sender.sendMessage(ChatColor.RED + "No spawn registered!");
            return true;
        }
        if(uhc.spawns.size() < uhc.uhcPlayers.size()){
            sender.sendMessage(ChatColor.GOLD + "[WARNING] More players than available spawns, players will spawn twice at the same place!");
        }
        int mapSize = 1000;
        ArrayList<Player> players = uhc.uhcPlayers;
        if (args.length > 1) {
            return false;
        } else if (args.length == 1) {
            mapSize = Integer.parseInt(args[0]);
        }
        final int mapSizeFinal = mapSize;
        sender.sendMessage(ChatColor.BLUE + "Starting glass wall creation...");
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for (int k = 0; k < 200; k++) {
                    try {
                        if (k % 2 == 0 && k != 0) {
                            sender.sendMessage(ChatColor.GREEN + String.valueOf(k / 2) + "%");
                        }
                        for (int i = -mapSizeFinal / 2; i < mapSizeFinal / 2; i++) {
                            Bukkit.getWorlds().get(0).getBlockAt(i, k, -mapSizeFinal / 2).setTypeId(20, false);
                            Bukkit.getWorlds().get(0).getBlockAt(i, k, mapSizeFinal / 2).setTypeId(20, false);
                            Bukkit.getWorlds().get(0).getBlockAt(-mapSizeFinal / 2, k, i).setTypeId(20, false);
                            Bukkit.getWorlds().get(0).getBlockAt(mapSizeFinal / 2, k, i).setTypeId(20, false);
                            if (i % 100 == 0) {
                                Thread.sleep(1L);
                            }
                        }

                        if (k == 199) {
                            sender.sendMessage(ChatColor.GREEN + "100%");
                            sender.sendMessage(ChatColor.BLUE + "Glass wall successfully created!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(UHCStart.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        Thread t1 = new Thread(r1);
        t1.start();
        uhc.mapSize = mapSize;
        uhc.isGameStarted = true;
        Iterator<Recipe> it = Bukkit.getServer().recipeIterator();
        while (it.hasNext()) {
            Recipe r = it.next();
            if (r.getResult().getType().equals(Material.SPECKLED_MELON)) {
                it.remove();
            }
        }
        ShapelessRecipe goldBlockToMagic = new ShapelessRecipe(new ItemStack(Material.SPECKLED_MELON, 1));
        goldBlockToMagic.addIngredient(Material.GOLD_BLOCK);
        goldBlockToMagic.addIngredient(Material.MELON);
        Bukkit.addRecipe(goldBlockToMagic);
        
        //tp everyone
        ArrayList<Location> randomSpawns = uhc.spawns;
        Collections.shuffle(randomSpawns);
        int i = 0;
        for(Player p : uhc.uhcPlayers){
            if(i >= randomSpawns.size()){
                i  = 0;
            }
            p.teleport(randomSpawns.get(i));
            i++;
        }
        return true;
    }
}
