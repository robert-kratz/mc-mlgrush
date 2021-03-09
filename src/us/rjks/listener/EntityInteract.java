package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import us.rjks.utils.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:42
 *
 **************************************************************************/

public class EntityInteract implements Listener {

    @EventHandler
    public void onHit(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player) {
            if(((Player) event.getRightClicked()).getPlayer().getName().equalsIgnoreCase("§71 §egegen §71")) {
                event.setCancelled(true);
                if(Queue.mlgrush.contains(event.getPlayer())) {
                    Queue.mlgrush.remove(event.getPlayer());
                    event.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §cDu hast die §fMLG Rush§c Warteschlange verlassen.");
                } else {
                    event.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aDu hast die §fMLG Rush§a Warteschlange betreten.");
                    System.out.println(Queue.mlgrush.size());
                    if(Queue.mlgrush.size() >= 1) {
                        Player t = Queue.mlgrush.get(0), p = (Player) event.getPlayer();

                        Queue.mlgrush.remove(p);
                        Queue.mlgrush.remove(t);

                        Round r = new Round(p, t);
                        r.loadMap();
                    } else {
                        Queue.mlgrush.add(event.getPlayer());
                        TabRank.setScoreBoard(event.getPlayer());
                    }
                }
            } else if(((Player) event.getRightClicked()).getPlayer().getName().equalsIgnoreCase("§eInventar")) {
                event.setCancelled(true);
                event.getPlayer().openInventory(Inventory.getInvSort(event.getPlayer()));
            }

        }
    }

    @EventHandler
    public void onHit(PlayerInteractEvent event) {
        try {
            if(event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.BED_BLOCK) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);
            }
            if(event.getPlayer().getInventory().getItemInHand() != null && event.getPlayer().getInventory().getItemInHand().getItemMeta() != null) {
                if(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eZuschauen §8«")) {
                    event.setCancelled(true);
                    event.getPlayer().openInventory(Inventory.getGames(event.getPlayer()));
                } else if(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eZurück zur Lobby §8«")) {
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(event.getPlayer(), "spawn");
                } else if(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eEinstellungen §8«")) {
                    event.setCancelled(true);
                    event.getPlayer().openInventory(Inventory.getSettingsOverview(event.getPlayer()));
                } else if(event.getPlayer().getInventory().getItemInHand().getType().equals(Material.BANNER)) {
                    event.setCancelled(true);
                }
            }
        } catch (Exception e) {

        }
    }
}
