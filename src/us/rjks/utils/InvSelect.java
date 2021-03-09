package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:45
 *
 **************************************************************************/

public class InvSelect implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            if(event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getItemMeta() != null) {
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eHerausfordern §8«") ||
                        event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eZuschauen  §8«") ||
                        event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eVerlassen §8«") ||
                        event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eZurück zur Lobby §8«")) {
                    event.setCancelled(true);
                }
            }

            if(event.getInventory().getName().equalsIgnoreCase("§8» §eZuschauen §8«")) {
                event.setCancelled(true);
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getItemMeta() != null) {
                    String name = event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§c", "").replaceAll(" §7vs §9", ":");
                    System.out.println(name);

                    String[] args = name.split(":");
                    System.out.println(args[0]);
                    if(Round.playerInRound(Bukkit.getPlayer(args[0])) != null) {
                        Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "spec " + args[0]);
                    } else {
                        event.getWhoClicked().sendMessage("§8» §eMLG Rush §8┃ §cDieser Spieler ist nicht online!");
                    }
                }
            }

            if(event.getInventory().getName().equalsIgnoreCase("§8» §eInventarsortierung §8«")) {
                if(event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getItemMeta() != null) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(" ")) {
                        event.setCancelled(true);
                    }
                }
            }
            //SHOP
            else if(event.getInventory().getName().equalsIgnoreCase("§8» §eEinstellungen")) {
                event.setCancelled(true);
                if(event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getItemMeta() != null) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eSticks")) {
                        event.getWhoClicked().openInventory(Inventory.getStickShop((Player) event.getWhoClicked()));
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eInventar")) {
                        event.getWhoClicked().openInventory(Inventory.getInvSort((Player) event.getWhoClicked()));
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eBlöcke")) {
                        event.getWhoClicked().openInventory(Inventory.getBlockShop((Player) event.getWhoClicked()));
                    }
                }
            } else if(event.getInventory().getName().equalsIgnoreCase("§8» §eBlöcke")) {
                event.setCancelled(true);
                if(event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getItemMeta() != null) {
                    Player p = (Player)event.getWhoClicked();
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eSandstein")) {
                        Shop.selectBlock(p, "SANDSTONE");
                        p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fSandstein §agewählt.");
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eRoter Sandstein")) {
                        if(Shop.hasRedSandStone(p)) {
                            Shop.selectBlock(p, "RED");
                            p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fRoter Sandstein §agekauft.");
                        } else {
                            if (Shop.buyRedSandstone(p)) {
                                p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fRoter Sandstein §agekauft.");
                            } else {
                                p.sendMessage("§8» §eMLG Rush §8┃ §cDu hast zu wenig Punkte");
                            }
                        }
                        event.getWhoClicked().closeInventory();
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eTon")) {
                        if(Shop.hasClay(p)) {
                            Shop.selectBlock(p, "CLAY");
                            p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fTon §agewählt.");
                        } else {
                            if(Shop.buyClay(p)) {
                                p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fTon §agekauft.");
                            } else {
                                p.sendMessage("§8» §eMLG Rush §8┃ §cDu hast zu wenig Punkte");
                            }
                        }
                        event.getWhoClicked().closeInventory();
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eLaterne")) {
                        if(Shop.hasSeaLatern(p)) {
                            Shop.selectBlock(p, "SEA");
                            p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fLaterne §agewählt.");
                        } else {
                            if(Shop.buySeaLatern(p)) {
                                p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fLaterne §agekauft.");
                            } else {
                                p.sendMessage("§8» §eMLG Rush §8┃ §cDu hast zu wenig Punkte");
                            }
                        }
                        event.getWhoClicked().closeInventory();
                    }
                    event.getWhoClicked().closeInventory();
                }
            } else if(event.getInventory().getName().equalsIgnoreCase("§8» §eSticks")) {
                event.setCancelled(true);
                if(event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getItemMeta() != null) {
                    Player p = (Player)event.getWhoClicked();
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eStick")) {
                        Shop.selectStick(p, "STICK");
                        p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fStick §agewählt.");
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eFeder")) {
                        if(Shop.hasFeather(p)) {
                            Shop.selectStick(p, "FEATHER");
                            p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fFeder §agewählt.");
                        } else {
                            if(Shop.buyFeather(p)) {
                                p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fFeather §agekauft.");
                            } else {
                                p.sendMessage("§8» §eMLG Rush §8┃ §cDu hast zu wenig Punkte");
                            }
                        }
                        event.getWhoClicked().closeInventory();
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eLohenrute")) {
                        if(Shop.hasRod(p)) {
                            Shop.selectStick(p, "ROD");
                            p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fLohenrute §agewählt.");
                        } else {
                            if(Shop.buyRod(p)) {
                                p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fLohenrute §agekauft.");
                            } else {
                                p.sendMessage("§8» §eMLG Rush §8┃ §cDu hast zu wenig Punkte");
                            }
                        }
                        event.getWhoClicked().closeInventory();
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eBanner")) {
                        if(Shop.hasBanner(p)) {
                            Shop.selectStick(p, "BANNER");
                            p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fBanner §agewählt.");
                        } else {
                            if(Shop.buyBanner(p)) {
                                p.sendMessage("§8» §eMLG Rush §8┃ §aDu hast das Item §fBanner §agekauft.");
                                event.getWhoClicked().closeInventory();
                            } else {
                                p.sendMessage("§8» §eMLG Rush §8┃ §cDu hast zu wenig Punkte");
                            }
                        }
                        event.getWhoClicked().closeInventory();
                    }
                    event.getWhoClicked().closeInventory();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(event.getInventory().getName().equalsIgnoreCase("§8» §eInventarsortierung §8«")) {
            int stick = 0, block = 2, pick = 1;
            for(int i = 0; i < event.getInventory().getContents().length; i++) {
                if(event.getInventory().getItem(i) != null) {
                    if(event.getInventory().getItem(i).getType() == Material.STICK ||
                            event.getInventory().getItem(i).getType() == Material.FEATHER||
                            event.getInventory().getItem(i).getType() == Material.BLAZE_ROD ||
                            event.getInventory().getItem(i).getType() == Material.BANNER) {
                        stick = (i - 9);
                    } else if(event.getInventory().getItem(i).getType() == Material.SANDSTONE||
                            event.getInventory().getItem(i).getType() == Material.RED_SANDSTONE||
                            event.getInventory().getItem(i).getType() == Material.HARD_CLAY ||
                            event.getInventory().getItem(i).getType() == Material.SEA_LANTERN) {
                        block = (i - 9);
                    } else if(event.getInventory().getItem(i).getType() == Material.STONE_PICKAXE) {
                        pick = (i - 9);
                    }
                }
            }
            event.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aDu hast dein §fInventar §agespeichert.");
            Inventory.setLobbyItems((Player)event.getPlayer());
            InventoryPositions.save((Player)event.getPlayer(), stick, block, pick);
        }
    }

}
