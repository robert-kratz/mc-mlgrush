package us.rjks.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:39
 *
 **************************************************************************/

public class world implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.isOp()) {
                if(args.length == 0) {
                    p.sendMessage("§7List of active worlds:");
                    for (World world : Bukkit.getWorlds()) {
                        p.sendMessage("§8- §7" + world.getName());
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
                        int exists = 0;
                        for (World world : Bukkit.getWorlds()) {
                            if(world.getName().equalsIgnoreCase(args[1].toString())) {
                                exists = 1;
                                Location loc = world.getSpawnLocation();
                                p.teleport(loc);
                                p.sendMessage("§aDu bist du auf " + world.getName());
                            }
                        }
                        if(exists == 0) {
                            p.sendMessage("§cDiese Welt existiert nicht");
                        }
                    }
                }
            }
        } else {
            sender.sendMessage("§cYou have to be a player to execute this command.");
        }

        return false;
    }
}
