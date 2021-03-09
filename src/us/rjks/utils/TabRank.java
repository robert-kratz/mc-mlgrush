package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 27.02.2021 / 22:50
 *
 **************************************************************************/

public class TabRank {

    public static void setScoreBoard(Player player) {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();


        if(Round.playerInRound(player) != null) {
            Round round = Round.playerInRound(player);

            Objective o = board.registerNewObjective("Scoreboard", "");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eMLG Rush"));

            Score spacer1 = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7"));
            spacer1.setScore(6);

            Score server = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7Map"));
            server.setScore(5);

            Score servername = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &e" + round.getMap().getMap().getName().toUpperCase()));
            servername.setScore(4);

            Score spacer2 = o.getScore(ChatColor.translateAlternateColorCodes('&', "  "));
            spacer2.setScore(3);

            Score players = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7Punkte"));
            players.setScore(2);

            Score playercount = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &cRot &8- &c" + round.getA().getPoints()));
            playercount.setScore(1);

            Score spacer3 = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &9Blau &8- &9" + round.getB().getPoints()));
            spacer3.setScore(0);

            Score spacer4 = o.getScore(ChatColor.translateAlternateColorCodes('&', " "));
            spacer4.setScore(-1);

            player.setScoreboard(board);
        } else if(Round.playerInSpec(player) != null) {
            Round round = Round.playerInSpec(player);

            Objective o = board.registerNewObjective("Scoreboard", "");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eMLG Rush"));

            Score spacer1 = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7"));
            spacer1.setScore(6);

            Score server = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7Map"));
            server.setScore(5);

            Score servername = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &e" + round.getMap().getMap().getName().toUpperCase()));
            servername.setScore(4);

            Score spacer2 = o.getScore(ChatColor.translateAlternateColorCodes('&', "  "));
            spacer2.setScore(3);

            Score players = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7Punkte"));
            players.setScore(2);

            Score playercount = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &cRot &8- &c" + round.getA().getPoints()));
            playercount.setScore(1);

            Score spacer3 = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &9Blau &8- &9" + round.getB().getPoints()));
            spacer3.setScore(0);

            Score spacer4 = o.getScore(ChatColor.translateAlternateColorCodes('&', " "));
            spacer4.setScore(-1);

            player.setScoreboard(board);
        } else {
            String queuename = "&8-";
            if(Queue.mlgrush.contains(player)) {
                queuename = "MLG Rush";
            }

            Objective o = board.registerNewObjective("Scoreboard", "");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eMLG Rush"));

            Score spacer1 = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7"));
            spacer1.setScore(9);

            Score server = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7Warteschlange"));
            server.setScore(8);

            Score servername = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &e" + queuename));
            servername.setScore(7);

            Score spacer2 = o.getScore(ChatColor.translateAlternateColorCodes('&', "  "));
            spacer2.setScore(6);

            Score players = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7Ranking"));
            players.setScore(5);

            Score playercount = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &e#" + Stats.getRank(player)));
            playercount.setScore(4);

            Score spacer4 = o.getScore(ChatColor.translateAlternateColorCodes('&', " §6  "));
            spacer4.setScore(3);

            Score a = o.getScore(ChatColor.translateAlternateColorCodes('&', "&7Points"));
            a.setScore(2);

            Score b = o.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &e" + Stats.getPoints(player)));
            b.setScore(1);

            Score c = o.getScore(ChatColor.translateAlternateColorCodes('&', " "));
            c.setScore(0);

            player.setScoreboard(board);
        }
    }

}
