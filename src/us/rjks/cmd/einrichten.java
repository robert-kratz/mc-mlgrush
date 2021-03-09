package us.rjks.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.utils.CustomMap;
import us.rjks.utils.Inventory;
import us.rjks.utils.NCP;
import us.rjks.utils.SpawnManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:38
 *
 **************************************************************************/

public class einrichten implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender.hasPermission("system.server.setup")) {
            if(args.length == 1) {
                if(sender instanceof Player) {
                    if(args[0].equalsIgnoreCase("invvillager") ||
                            args[0].equalsIgnoreCase("queue") ||
                            args[0].equalsIgnoreCase("stats-1") ||
                            args[0].equalsIgnoreCase("stats-2") ||
                            args[0].equalsIgnoreCase("stats-3") ||
                            args[0].equalsIgnoreCase("spawn")) {
                        sender.sendMessage("§8» §eMLG Rush §8┃ §aDu hast erfolgreich den spawn für " + args[0].toUpperCase() + " gesetzt");
                        SpawnManager.setSpawn((Player) sender, args[0].toLowerCase());
                    } else if(args[0].equalsIgnoreCase("queuue")) {
                        ((Player) sender).openInventory(Inventory.getTypeSelection(((Player) sender).getPlayer()));
                    } else if(args[0].equalsIgnoreCase("reloadstats")) {
                        NCP.loadNPCs();
                        sender.sendMessage("reloaded.");
                    }
                } else {
                    sender.sendMessage("§cYou have to be a player");
                }
            } else if(args.length == 2) {
                 if(args[0].equalsIgnoreCase("load")) {
                    if(args[1] != null) {
                        CustomMap.paste(args[1] + ".schematic", ((Player) sender).getPlayer().getLocation());
                    } else {
                        sender.sendMessage("§cDiese Schematic could not be found");
                    }
                }
            } else{
                sender.sendMessage("§cUsage: /einrichten [invvillager, queue, stats-1, stats-2, stats-3, spawn]");
            }
        } else {
            sender.sendMessage("§cYou dont have permission to execute this command");
        }
        return true;
    }

}
