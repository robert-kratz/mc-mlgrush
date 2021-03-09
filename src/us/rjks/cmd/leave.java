package us.rjks.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.core.Data;
import us.rjks.utils.Round;
import us.rjks.utils.SpawnManager;
import us.rjks.utils.Stats;
import us.rjks.utils.Visibility;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:38
 *
 **************************************************************************/

public class leave  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            try {
                Round round = Round.playerInRound((Player)sender);
                if(round != null) {
                    round.stop();
                    SpawnManager.tp(round.getA().getPlayer(), "spawn");
                    SpawnManager.tp(round.getB().getPlayer(), "spawn");
                    round.getA().getPlayer().sendMessage("§8» §eMLG Rush §8┃ §c" + sender.getName() + " hat die runde verlassen.");
                    round.getB().getPlayer().sendMessage("§8» §eMLG Rush §8┃ §c" + sender.getName() + " hat die runde verlassen.");

                    round.leavePlayer(((Player) sender).getPlayer());

                } else if(Round.playerInSpec((Player)sender) != null) {
                    Round r = Round.playerInSpec((Player)sender);
                    SpawnManager.tp((Player) sender, "spawn");
                    r.leaveSpectator(((Player) sender).getPlayer());
                } else {
                    SpawnManager.tp((Player) sender, "spawn");
                }
                ((Player) sender).getPlayer().setAllowFlight(false);
                ((Player) sender).getPlayer().setFlying(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("§cYou have to be a player to execute this command");
        }

        return false;
    }
}
