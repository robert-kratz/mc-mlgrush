package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.rjks.core.Data;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 19:12
 *
 **************************************************************************/

public class Inventory {

    public static void setLobbyItems(Player p) {
        p.getInventory().clear();
        p.setGameMode(GameMode.SURVIVAL);

        ItemStack stack = new ItemBuilder(Material.WOOD_SWORD, "§8» §eHerausfordern §8«").setUnbreakable(true).checkout();
        stack.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);

        p.getInventory().setItem(1, stack);
        p.getInventory().setItem(7, new ItemBuilder(Material.COMPASS, "§8» §eZuschauen §8«").checkout());
        p.getInventory().setItem(4, new ItemBuilder(Material.REDSTONE_COMPARATOR, "§8» §eEinstellungen §8«").checkout());

        p.getInventory().setHeldItemSlot(4);
    }

    public static void setSpecItems(Player p) {
        p.getInventory().clear();
        p.setGameMode(GameMode.SURVIVAL);
        p.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM, "§8» §eZurück zur Lobby §8«").checkout());

        p.getInventory().setHeldItemSlot(5);
    }

    public static org.bukkit.inventory.Inventory getGames() {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 45, "§8» §eHerausfordern §8«");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, " ").setDamage((short)7).checkout());
        }

        int pos = 9;
        for(Round round : Data.rounds) {
            inv.setItem(pos, new ItemBuilder(Material.getMaterial(351), "§c" + round.getA().getPlayer().getName() + "§7 vs §9" + round.getB().getPlayer().getName()).setDamage((short)10).checkout());
            pos++;
        }

        return inv;
    }

    public static org.bukkit.inventory.Inventory getTypeSelection(Player p) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 27, "§8» §eWähle dein Spielmodus §8«");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        for(int i = 18; i < 27; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        String one = "warten", three = "warten", stack = "warten";
        if(us.rjks.utils.Queue.mlgrush.size() == 1) {
            one = "wartet";
        }

        inv.setItem(13, new ItemBuilder(Material.getMaterial(180), "§eThreestack").setLore("§7" + Queue.mlgrush.size() + " Spieler " + one).checkout());

        return inv;
    }

    public static org.bukkit.inventory.Inventory getChallangerInv(Player p) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 27, "» §eHerausvordern §8«");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        for(int i = 18; i < 27; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        inv.setItem(10, new ItemBuilder(Material.getMaterial(182), "§eOneline").checkout());
        inv.setItem(13, new ItemBuilder(Material.getMaterial(180), "§eThreestack").checkout());
        inv.setItem(16, new ItemBuilder(Material.getMaterial(179), "§eOnestack").setDamage((short) 2).checkout());

        return inv;
    }

    public static org.bukkit.inventory.Inventory getInvSort(Player p) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 27, "§8» §eInventarsortierung §8«");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        for(int i = 18; i < 27; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        inv.setItem((InventoryPositions.getSickPos(p) + 9), Shop.getStickAsItemStack(p));
        inv.setItem((InventoryPositions.getPickPos(p) + 9), new ItemBuilder(Material.STONE_PICKAXE, "§8» §eSpitzhacke").setUnbreakable(true).checkout());
        inv.setItem((InventoryPositions.getBlockPos(p) + 9), Shop.getBlockAsItemStack(p));

        return inv;
    }

    public static org.bukkit.inventory.Inventory getGames(Player p) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 54, "§8» §eZuschauen §8«");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        for(int i = 45; i < 54; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }
        int pos = 9;
        for(Round round : Data.rounds) {
            inv.setItem(pos, new ItemBuilder(Material.STORAGE_MINECART, "§c" + round.getA().getPlayer().getName() + " §7vs §9" + round.getB().getPlayer().getName()).setDamage((short) 10).setLore("§7Spielen auf " + round.getMap().getMap().getName().toUpperCase()).checkout());
            pos++;
        }

        return inv;
    }

    public static org.bukkit.inventory.Inventory getSettingsOverview(Player p) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 45, "§8» §eEinstellungen");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        for(int i = 36; i < 45; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        inv.setItem(19, new ItemBuilder(Material.STICK, "§8» §eSticks").checkout());
        inv.setItem(22, new ItemBuilder(Material.ENDER_PEARL, "§8» §eInventar").checkout());
        inv.setItem(25, new ItemBuilder(Material.SANDSTONE, "§8» §eBlöcke").checkout());

        return inv;
    }

    public static org.bukkit.inventory.Inventory getBlockShop(Player p) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 45, "§8» §eBlöcke");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        for(int i = 36; i < 45; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        ItemStack stack0 = new ItemBuilder(Material.SANDSTONE, "§8» §eSandstein").setLore("§8* §eFreigeschaltet").checkout();

        if(Shop.getSelectedBlock(p).equalsIgnoreCase("SANDSTONE")) {
            stack0.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(19, stack0);

        ItemBuilder red = new ItemBuilder(Material.RED_SANDSTONE, "§8» §eRoter Sandstein");
        if(!Shop.hasRedSandStone(p)) {
            red.setLore("§8* §e150 Punkte, um es dir Freizuschalten.");
        } else {
            red.setLore("§8* §eFreigeschaltet");
        }
        ItemStack stack1 = red.checkout();

        if(Shop.getSelectedBlock(p).equalsIgnoreCase("RED")) {
            stack1.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(21, stack1);

        ItemBuilder ton = new ItemBuilder(Material.HARD_CLAY, "§8» §eTon");
        if(!Shop.hasClay(p)) {
            ton.setLore("§8* §e450 Punkte, um es dir Freizuschalten.");
        } else {
            ton.setLore("§8* §eFreigeschaltet");
        }

        ItemStack stack2 = ton.checkout();

        if(Shop.getSelectedBlock(p).equalsIgnoreCase("CLAY")) {
            stack2.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(23, stack2);

        ItemBuilder latern = new ItemBuilder(Material.SEA_LANTERN, "§8» §eLaterne");
        if(!Shop.hasSeaLatern(p)) {
            latern.setLore("§8* §e800 Punkte, um es dir Freizuschalten.").setDamage((short) 14);
        } else {
            latern.setLore("§8* §eFreigeschaltet");
        }

        ItemStack stack3 = latern.checkout();

        if(Shop.getSelectedBlock(p).equalsIgnoreCase("SEA")) {
            stack3.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(25, stack3);

        return inv;
    }

    public static org.bukkit.inventory.Inventory getStickShop(Player p) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 45, "§8» §eSticks");

        for(int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        for(int i = 36; i < 45; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE,  " ").setDamage((short)7).checkout());
        }

        ItemStack stack0 = new ItemBuilder(Material.STICK, "§8» §eStick").checkout();

        if(Shop.getSelectedStick(p).equalsIgnoreCase("STICK")) {
            stack0.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(19, stack0);

        ItemBuilder father = new ItemBuilder(Material.FEATHER, "§8» §eFeder");
        if(!Shop.hasFeather(p)) {
            father.setLore("§8* §e450 Punkte, um es dir Freizuschalten.");
        } else {
            father.setLore("§8* §eFreigeschaltet");
        }

        ItemStack stack1 = father.checkout();

        if(Shop.getSelectedStick(p).equalsIgnoreCase("FEATHER")) {
            stack1.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(21, stack1);

        ItemBuilder rod = new ItemBuilder(Material.BLAZE_ROD, "§8» §eLohenrute");
        if(!Shop.hasRod(p)) {
            rod.setLore("§8* §e850 Punkte, um es dir Freizuschalten.");
        } else {
            rod.setLore("§8* §eFreigeschaltet");
        }

        ItemStack stack2 = rod.checkout();

        if(Shop.getSelectedStick(p).equalsIgnoreCase("ROD")) {
            stack2.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(23, stack2);

        ItemBuilder banner = new ItemBuilder(Material.BANNER, "§8» §eBanner");
        if(!Shop.hasBanner(p)) {
            banner.setLore("§8* §e1450 Punkte, um es dir Freizuschalten.");
        } else {
            banner.setLore("§8* §eFreigeschaltet");
        }

        ItemStack stack3 = banner.checkout();

        if(Shop.getSelectedStick(p).equalsIgnoreCase("ROD")) {
            stack3.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        }
        inv.setItem(25, stack3);

        return inv;
    }

}
