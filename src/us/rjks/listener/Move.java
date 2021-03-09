package us.rjks.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import us.rjks.utils.Challanger;
import us.rjks.utils.Round;
import us.rjks.utils.SpawnManager;
import us.rjks.utils.Stats;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:42
 *
 **************************************************************************/

public class Move implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(Round.playerInRound(event.getPlayer()) != null && Round.playerInSpec(event.getPlayer()) != null) {
            if(event.getPlayer().getLocation().getY() <= 91.5) {
                Round round = Round.playerInRound(event.getPlayer());
                round.tpBack(event.getPlayer());
            }
        }
    }

}
