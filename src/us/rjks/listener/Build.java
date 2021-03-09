package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import us.rjks.cmd.map;
import us.rjks.core.Main;
import us.rjks.utils.CustomMap;
import us.rjks.utils.Inventory;
import us.rjks.utils.Round;
import us.rjks.utils.Stats;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:42
 *
 **************************************************************************/

public class Build implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPick(PlayerPickupItemEvent e) {
        if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeaves(LeavesDecayEvent e)
    {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        e.setDeathMessage(null);
    }

    @EventHandler
    public void onErfolg(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBrake(BlockBreakEvent e) {
        if(e.getBlock().getType().equals(Material.BED_BLOCK) && e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            if(map.a_bottom.containsKey(e.getPlayer())) {
                map.getByName(map.a_bottom.get(e.getPlayer()).getName()).setA_bed_bottom(e.getBlock());
                e.getPlayer().sendMessage("Du hast die Unterseite für das Bett von Team Rot gesetzt");
                e.getPlayer().sendMessage("Setze das Bed für Rot: §a/map " + map.getByName(map.a_bottom.get(e.getPlayer()).getName()).getName() + " set bed_red_top");
                map.a_bottom.remove(e.getPlayer());
                e.setCancelled(true);
            } else if(map.a_Top.containsKey(e.getPlayer())) {
                map.getByName(map.a_Top.get(e.getPlayer()).getName()).setA_bed_top(e.getBlock());
                e.getPlayer().sendMessage("Du hast die Oberseite für das Bett von Team Rot gesetzt");
                e.getPlayer().sendMessage("Setze das Bed für Blau: §a/map " + map.getByName(map.a_Top.get(e.getPlayer()).getName()).getName() + " set bed_blue_bottom");
                map.a_Top.remove(e.getPlayer());
                e.setCancelled(true);
            } else if(map.b_bottom.containsKey(e.getPlayer())) {
                map.getByName(map.b_bottom.get(e.getPlayer()).getName()).setB_bed_bottom(e.getBlock());
                e.getPlayer().sendMessage("Du hast die Untterseite für das Bett von Team Blau gesetzt");
                e.getPlayer().sendMessage("Setze das Bed für Blau: §a/map " + map.getByName(map.b_bottom.get(e.getPlayer()).getName()).getName() + " set bed_blue_top");
                map.b_bottom.remove(e.getPlayer());
                e.setCancelled(true);
            } else if(map.b_Top.containsKey(e.getPlayer())) {
                map.getByName(map.b_Top.get(e.getPlayer()).getName()).setB_bed_top(e.getBlock());
                e.getPlayer().sendMessage("Du hast die Oberseite für das Bett von Team Blau gesetzt");
                e.getPlayer().sendMessage("Gebe nun §a/map checkout " + map.getByName(map.b_Top.get(e.getPlayer()).getName()).getName());
                map.b_Top.remove(e.getPlayer());
                e.setCancelled(true);
            }
        }

        if(Round.playerInRound(e.getPlayer()) != null) {
            Round round = Round.playerInRound(e.getPlayer());
            if(e.getBlock().getType().equals(Material.BED_BLOCK)) {
                e.setCancelled(true);

                if(round.getA().getPlayer().equals(e.getPlayer())) {
                    if(round.getMap().getMap().getA_bed_bottom().equals(e.getBlock()) ||
                            round.getMap().getMap().getA_bed_top().equals(e.getBlock())) {
                        e.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §cDu kannst dein eigenes Bett nicht abbauen!");
                        return;
                    }
                } else if(round.getB().getPlayer().equals(e.getPlayer())) {
                    if(round.getMap().getMap().getB_bed_bottom().equals(e.getBlock()) ||
                            round.getMap().getMap().getB_bed_top().equals(e.getBlock())) {
                        e.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §cDu kannst dein eigenes Bett nicht abbauen!");
                        return;
                    }
                }

                round.addPointTo(e.getPlayer());
                round.getMap().clearMap();
                round.tpBack();


                if(round.getB().getPoints() == 5) {
                    Round.PlayingPlayer A = round.getA();
                    Round.PlayingPlayer B = round.getB();

                    B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §c" + A.getPlayer().getName() + " hat Spiel das gewonnen");

                    A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aDu hast gewonnen");
                    A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+1 Win");
                    A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+50 Punkte");

                    Stats.setPoints(A.getPlayer(), Stats.getPoints(A.getPlayer()) + 50);
                    Stats.setWins(A.getPlayer(), Stats.getWinns(A.getPlayer()) + 1);

                    Stats.setDeaths(A.getPlayer(), Stats.getDeaths(A.getPlayer()) + B.getPoints());
                    Stats.setKills(A.getPlayer(), Stats.getKills(A.getPlayer()) + A.getPoints());

                    Stats.setGames(A.getPlayer(), Stats.getDeaths(A.getPlayer()) + 1);

                    Stats.setBrakedBeds(A.getPlayer(), Stats.getBrakedBeds(A.getPlayer()) + A.getPoints());
                    Stats.setBrakedBeds(B.getPlayer(), Stats.getBrakedBeds(B.getPlayer()) + B.getPoints());

                    round.stop();

                } else if(round.getA().getPoints() == 5) {
                    Round.PlayingPlayer A = round.getA();
                    Round.PlayingPlayer B = round.getB();

                    A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §c" + B.getPlayer().getName() + " hat Spiel das gewonnen");

                    B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aDu hast gewonnen");
                    B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+1 Win");
                    B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+50 Punkte");

                    Stats.setPoints(B.getPlayer(), Stats.getPoints(B.getPlayer()) + 50);
                    Stats.setWins(B.getPlayer(), Stats.getWinns(B.getPlayer()) + 1);

                    Stats.setDeaths(B.getPlayer(), Stats.getDeaths(B.getPlayer()) + A.getPoints());
                    Stats.setKills(B.getPlayer(), Stats.getKills(B.getPlayer()) + B.getPoints());

                    Stats.setGames(B.getPlayer(), Stats.getDeaths(B.getPlayer()) + 1);

                    Stats.setBrakedBeds(A.getPlayer(), Stats.getBrakedBeds(A.getPlayer()) + A.getPoints());
                    Stats.setBrakedBeds(B.getPlayer(), Stats.getBrakedBeds(B.getPlayer()) + B.getPoints());

                    round.stop();
                }

            } else if(round.getMap().getBlocks().contains(e.getBlock())){
                e.setCancelled(false);
                round.getMap().removeBlock(e.getBlock());
                e.getBlock().setType(Material.AIR);
            } else {
                e.setCancelled(true);
            }
        } else {
            if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBrake(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if(Round.playerInRound(e.getPlayer()) != null) {
            if(e.getBlock().getY() < 105) {
                e.setCancelled(false);
                Round r = Round.playerInRound(e.getPlayer());
                r.getMap().getBlocks().add(e.getBlock());
            } else {
                e.setCancelled(true);
            }
        } else {
            if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

}
