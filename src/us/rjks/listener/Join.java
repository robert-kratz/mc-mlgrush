package us.rjks.listener;

import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.rjks.core.Data;
import us.rjks.core.Main;
import us.rjks.utils.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:39
 *
 **************************************************************************/

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        for (Player all : Bukkit.getOnlinePlayers()) {
            if(Round.playerInRound(all) != null || Round.playerInSpec(all) != null) {
                event.getPlayer().hidePlayer(all);
            }
        }

        TabRank.setScoreBoard(event.getPlayer());

        //Visibility.showPlayerInCurrentState(event.getPlayer());

        //NCP.loadNPCs(event.getPlayer());

        Inventory.setLobbyItems(event.getPlayer());
        SpawnManager.tp(event.getPlayer(), "spawn");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Queue.mlgrush.remove(event.getPlayer());

        TabRank.setScoreBoard(event.getPlayer());

        if(Round.playerInRound(event.getPlayer()) != null) {
            try {
                Round round = Round.playerInRound(event.getPlayer());
                if(round != null) {
                    round.stop();
                    SpawnManager.tp(round.getA().getPlayer(), "spawn");
                    SpawnManager.tp(round.getB().getPlayer(), "spawn");
                    round.getA().getPlayer().sendMessage("§8» §eMLG Rush §8┃ §7" + event.getPlayer().getName() + " hat die runde verlassen.");
                    round.getB().getPlayer().sendMessage("§8» §eMLG Rush §8┃ §7" + event.getPlayer().getName() + " hat die runde verlassen.");
                    Data.rounds.remove(round);
                } else if(Round.playerInSpec(event.getPlayer()) != null) {
                    Round.playerInSpec(event.getPlayer()).leavePlayer(event.getPlayer());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        event.setQuitMessage(null);
    }

}
