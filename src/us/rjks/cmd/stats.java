package us.rjks.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.utils.Stats;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:37
 *
 **************************************************************************/

public class stats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage("§8» §eMLG Rush §8┃ §7Deine Statistiken§8.");
            p.sendMessage(" ");
            p.sendMessage("§8» §eMLG Rush §8┃ §7Kills §8» §e" + Stats.getKills(p) + " §8* §7Tode §8» §e" + Stats.getDeaths(p));
            p.sendMessage("§8» §eMLG Rush §8┃ §7Spiele §8» §e" + Stats.getGames(p));
            p.sendMessage(" ");
            p.sendMessage("§8» §eMLG Rush §8┃ §7Gewonnen §8» §e" + Stats.getWinns(p));
            p.sendMessage(" ");
            p.sendMessage("§8» §eMLG Rush §8┃ §7Abgebaute Betten §8» §e" + Stats.getBrakedBeds(p));
            p.sendMessage(" ");
            p.sendMessage("§8» §eMLG Rush §8┃ §7Ranking §8» §e#" + Stats.getRank(p));
            p.sendMessage(" ");
            p.sendMessage("§8» §eMLG Rush §8┃ §7Punkte §8» §e" + Stats.getPoints(p));

        } else {
            sender.sendMessage("§cYou have to be a player to execute this command");
        }

        return false;
    }
}
