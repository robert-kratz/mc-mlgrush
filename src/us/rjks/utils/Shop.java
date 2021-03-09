package us.rjks.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 28.02.2021 / 21:50
 *
 **************************************************************************/

public class Shop {

    public static File logs = new File("plugins/Mlgrush/", "stats.yml");
    public static YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);

    public static void save(Player p, int pos_stick, int pos_block) {
        locscfg.set(p.getUniqueId().toString() + ".stick", pos_stick);
        locscfg.set(p.getUniqueId().toString() + ".block", pos_block);

        save();
    }

    public static boolean buyRedSandstone(Player p) {
        if(Stats.getPoints(p) >= 150) {
            Stats.setPoints(p, Stats.getPoints(p) - 150);
            locscfg.set(p.getUniqueId().toString() + ".redsandstone", true);
            save();
            return true;
        } else {
            return false;
        }
    }

    public static boolean buyClay(Player p) {
        if(Stats.getPoints(p) >= 450) {
            Stats.setPoints(p, Stats.getPoints(p) - 450);
            locscfg.set(p.getUniqueId().toString() + ".clay", true);
            save();
            return true;
        } else {
            return false;
        }
    }

    public static boolean buySeaLatern(Player p) {
        if(Stats.getPoints(p) >= 800) {
            Stats.setPoints(p, Stats.getPoints(p) - 800);
            locscfg.set(p.getUniqueId().toString() + ".sealatern", true);
            save();
            return true;
        } else {
            return false;
        }
    }

    //Blocks

    public static boolean hasRedSandStone(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".redsandstone") != null) {
            return locscfg.getBoolean(p.getUniqueId().toString() + ".redsandstone");
        }
        return false;
    }

    public static boolean hasClay(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".clay") != null) {
            return locscfg.getBoolean(p.getUniqueId().toString() + ".clay");
        }
        return false;
    }

    public static boolean hasSeaLatern(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".sealatern") != null) {
            return locscfg.getBoolean(p.getUniqueId().toString() + ".sealatern");
        }
        return false;
    }

    //Sticks

    public static boolean hasFeather(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".father") != null) {
            return locscfg.getBoolean(p.getUniqueId().toString() + ".father");
        }
        return false;
    }

    public static boolean hasRod(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".rod") != null) {
            return locscfg.getBoolean(p.getUniqueId().toString() + ".rod");
        }
        return false;
    }

    public static boolean hasBanner(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".banner") != null) {
            return locscfg.getBoolean(p.getUniqueId().toString() + ".banner");
        }
        return false;
    }

    public static boolean buyFeather(Player p) {
        if(Stats.getPoints(p) >= 450) {
            Stats.setPoints(p, Stats.getPoints(p) - 450);
            locscfg.set(p.getUniqueId().toString() + ".father", true);
            save();
            return true;
        } else {
            return false;
        }
    }

    public static boolean buyRod(Player p) {
        if(Stats.getPoints(p) >= 850) {
            Stats.setPoints(p, Stats.getPoints(p) - 850);
            locscfg.set(p.getUniqueId().toString() + ".rod", true);
            save();
            return true;
        } else {
            return false;
        }
    }

    public static boolean buyBanner(Player p) {
        if(Stats.getPoints(p) >= 1450) {
            Stats.setPoints(p, Stats.getPoints(p) - 1450);
            locscfg.set(p.getUniqueId().toString() + ".banner", true);
            save();
            return true;
        } else {
            return false;
        }
    }

    public static void selectStick(Player p, String type) {
        locscfg.set(p.getUniqueId().toString() + ".selectedstick", type);
    }

    public static void selectBlock(Player p, String type) {
        locscfg.set(p.getUniqueId().toString() + ".selectedblock", type);
    }

    public static String getSelectedStick(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".selectedstick") != null) {
            return locscfg.getString(p.getUniqueId().toString() + ".selectedstick");
        }
        return "STICK";
    }

    public static String getSelectedBlock(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".selectedblock") != null) {
            return locscfg.getString(p.getUniqueId().toString() + ".selectedblock");
        }
        return "SANDSTONE";
    }

    public static ItemStack getStickAsItemStack(Player p) {

        ItemBuilder stick = new ItemBuilder(Material.STICK, "§8» §eStick");

        switch (getSelectedStick(p)) {
            case "FEATHER": stick.setMaterial(Material.FEATHER);break;
            case "ROD": stick.setMaterial(Material.BLAZE_ROD);break;
            case "BANNER": stick.setMaterial(Material.BANNER);break;
        }

        ItemStack sttack = stick.checkout();
        sttack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        return sttack;
    }

    public static ItemStack getBlockAsItemStack(Player p) {

        ItemBuilder stick = new ItemBuilder(Material.SANDSTONE, "§8» §eBlöcke");

        switch (getSelectedBlock(p)) {
            case "RED": stick.setMaterial(Material.RED_SANDSTONE);break;
            case "CLAY": stick.setMaterial(Material.HARD_CLAY).setDamage((short) 14);break;
            case "SEA": stick.setMaterial(Material.SEA_LANTERN);break;
        }

        ItemStack sttack = stick.checkout();
        sttack.setAmount(64);
        return sttack;
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
