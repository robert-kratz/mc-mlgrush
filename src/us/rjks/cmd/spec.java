package us.rjks.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.core.Data;
import us.rjks.utils.Queue;
import us.rjks.utils.Round;
import us.rjks.utils.SpawnManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 27.02.2021 / 20:49
 *
 **************************************************************************/

public class spec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            if(args.length == 1) {
                boolean online = false;
                for(Player all : Bukkit.getOnlinePlayers()) {
                    if(all.getName().equalsIgnoreCase(args[0])) {
                        online = true;
                        break;
                    }
                }
                if(!online) {
                    sender.sendMessage("§8» §eMLG Rush §8┃ §cDieser Spieler ist nicht online");
                    return false;
                }

                if(Round.playerInRound(Bukkit.getPlayer(args[0])) == null) {
                    sender.sendMessage("§8» §eMLG Rush §8┃ §cDieser Spieler ist in keiner Runde");
                }

                Queue.mlgrush.remove((Player) ((Player) sender).getPlayer());

                Round round = Round.playerInRound(Bukkit.getPlayer(args[0]));

                round.joinSpectator((Player) sender);
                ((Player) sender).getPlayer().teleport(round.getMap().getSpecLocation());
                ((Player) sender).getPlayer().setAllowFlight(true);
                ((Player) sender).getPlayer().setFlying(true);
            }
        } else {
            sender.sendMessage("§cYou have to be a player to execute this command");
        }

        return false;
    }
}
