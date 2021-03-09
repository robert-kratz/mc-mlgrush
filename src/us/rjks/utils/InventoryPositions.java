package us.rjks.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 27.02.2021 / 15:13
 *
 **************************************************************************/

public class InventoryPositions {

    public static File logs = new File("plugins/Mlgrush/", "invs.yml");
    public static YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);

    public static void save(Player p, int pos_stick, int pos_block, int pos_pick) {
        System.out.println(pos_block);
        System.out.println(pos_stick);
        if(pos_stick >=0 && pos_stick <= 8 && pos_pick >=0 && pos_pick <= 8 && pos_block >=0 && pos_block <= 8 && pos_block != pos_stick && pos_block != pos_pick && pos_pick != pos_stick) {
            locscfg.set(p.getUniqueId().toString() + ".stick", pos_stick);
            locscfg.set(p.getUniqueId().toString() + ".block", pos_block);
            locscfg.set(p.getUniqueId().toString() + ".pick", pos_pick);
        } else {
            locscfg.set(p.getUniqueId().toString() + ".stick", 0);
            locscfg.set(p.getUniqueId().toString() + ".block", 1);
            locscfg.set(p.getUniqueId().toString() + ".block", 2);
        }

        save();
    }

    public static void set(Player p) {
        p.getInventory().clear();
        p.getInventory().setHeldItemSlot(0);

        p.getInventory().setItem(getSickPos(p), Shop.getStickAsItemStack(p));
        p.getInventory().setItem(getPickPos(p), new ItemBuilder(Material.STONE_PICKAXE, "§8» §eSpitzhacke").setUnbreakable(true).checkout());
        p.getInventory().setItem(getBlockPos(p), Shop.getBlockAsItemStack(p));
    }

    public static int getSickPos(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".stick") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".stick");
        }
        return 0;
    }

    public static int getPickPos(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".pick") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".pick");
        }
        return 1;
    }

    public static int getBlockPos(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".block") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".block");
        }
        return 2;
    }

    public static boolean create() {
        if(!logs.exists()) {
            try {
                logs.createNewFile();
                return true;
            }
            catch (Exception localException) {}
        }
        return false;
    }

    public static void save() {
        try { locscfg.save(logs); } catch (Exception e) {}
    }

}
