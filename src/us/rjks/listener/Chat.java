package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.rjks.utils.Round;

import java.util.List;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:39
 *
 **************************************************************************/

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();

        System.out.println(p.getName()+ ": " + event.getMessage());
        event.setCancelled(true);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage("§7" + event.getPlayer().getName() + "§8: §f" + event.getMessage());
        }

    }

}
