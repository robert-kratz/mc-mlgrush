package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.rjks.utils.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:42
 *
 **************************************************************************/

public class EntityDamage implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        try {
            boolean realPlayer = false;
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.equals((Player)event.getEntity())) {
                    realPlayer = true;
                }
            }
            if(!realPlayer) return;

            if(event.getDamager() != null && event.getEntity() != null) {
                if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                    if(((Player) event.getDamager()).getPlayer().getInventory().getItemInHand() != null && ((Player) event.getDamager()).getPlayer().getInventory().getItemInHand().getItemMeta() != null && ((Player) event.getDamager()).getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eHerausfordern §8«")) {
                        event.setCancelled(true);
                        Player p = (Player) event.getDamager();
                        Player t = (Player) event.getEntity();

                        if(!Challanger.challanger.containsKey(p)) {
                            Challanger.challanger.put(p, t);
                            t.sendMessage("§8» §eMLG Rush §8┃ §7Du wurdest von §f" + p.getName() + " §7herausgefordert.");
                            p.sendMessage("§8» §eMLG Rush §8┃ §7Du hast §f" + t.getName() + " §7herausgefordert.");
                        } else {
                            Challanger.challanger.remove(p);
                            t.sendMessage("§8» §eMLG Rush §8┃ §f" + p.getName() + " §7hat seine Anfrage zurückgezogen.");
                            p.sendMessage("§8» §eMLG Rush §8┃ §7Du hast die Anfrage an §f" + t.getName() + " §7zurückgezogen.");
                        }

                        if(Challanger.challanger.containsKey(p) && Challanger.challanger.get(p).equals(t) && Challanger.challanger.containsKey(t) && Challanger.challanger.get(t).equals(p)) {
                            Challanger.challanger.remove(p);
                            Challanger.challanger.remove(t);
                            p.sendMessage("§8» §eMLG Rush §8┃ §aDu spielst nun gegen " + t.getName());
                            t.sendMessage("§8» §eMLG Rush §8┃ §aDu spielst nun gegen " + p.getName());

                            Round r = new Round(p, t);
                            r.loadMap();
                        }
                    } else {
                        if(Round.playerInRound((Player) event.getDamager()).equals(Round.playerInRound((Player) event.getEntity()))) {
                            if(Round.playerInSpec((Player) event.getDamager()) != null && Round.playerInSpec((Player) event.getEntity()) != null) {
                                event.setCancelled(false);
                                event.setDamage(0.0);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}