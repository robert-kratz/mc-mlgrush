package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 26.02.2021 / 13:17
 *
 **************************************************************************/

public class Stats {

    public static File logs = new File("plugins/Mlgrush/", "stats.yml");
    public static YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);

    public static void setKills(Player p, int kills) {
        locscfg.set(p.getUniqueId().toString() + ".kills", kills);
        save();
    }

    public static void setDeaths(Player p, int deaths) {
        locscfg.set(p.getUniqueId().toString() + ".deaths", deaths);
        save();
    }

    public static void setWins(Player p, int deaths) {
        locscfg.set(p.getUniqueId().toString() + ".wins", deaths);
        save();
    }

    public static void setGames(Player p, int games) {
        locscfg.set(p.getUniqueId().toString() + ".games", games);
        save();
    }

    public static void setPoints(Player p, int points) {
        locscfg.set(p.getUniqueId().toString() + ".points", points);
        save();
    }

    public static void setBrakedBeds(Player p, int points) {
        locscfg.set(p.getUniqueId().toString() + ".bed", points);
        save();
    }

    public static int getKills(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".kills") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".kills");
        }
        return 0;
    }

    public static int getBrakedBeds(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".bed") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".bed");
        }
        return 0;
    }

    public static int getWinns(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".wins") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".wins");
        }
        return 0;
    }

    public static int getDeaths(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".deaths") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".deaths");
        }
        return 0;
    }

    public static int getPoints(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".points") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".points");
        }
        return 0;
    }

    public static int getGames(Player p) {
        if(locscfg.get(p.getUniqueId().toString() + ".games") != null) {
            return locscfg.getInt(p.getUniqueId().toString() + ".games");
        }
        return 0;
    }

    public static int getWinns(String uuid) {
        if(locscfg.get(uuid + ".wins") != null) {
            return locscfg.getInt(uuid + ".wins");
        }
        return 0;
    }

    public static HashMap<Integer, String> getTopThree() {
        HashMap<Integer, String> top = new HashMap<>();

        //FIRST
        int highest = 0; String first = "";
        for(String tops : locscfg.getConfigurationSection("").getKeys(true)) {
            if(locscfg.getInt(tops + ".wins") > highest) {
                highest = locscfg.getInt(tops + ".wins");
                first = tops;
            }
        }

        //SECOND
        highest = 0; String second = "";
        for(String tops : locscfg.getConfigurationSection("").getKeys(true)) {
            if(!tops.equalsIgnoreCase(first)) {
                if(locscfg.getInt(tops + ".wins") > highest) {
                    highest = locscfg.getInt(tops + ".wins");
                    second = tops;
                }
            }
        }

        //THIRD
        highest = 0; String third = "";
        for(String tops : locscfg.getConfigurationSection("").getKeys(true)) {
            if(!tops.equalsIgnoreCase(first) && !tops.equalsIgnoreCase(second)) {
                if(locscfg.getInt(tops + ".wins") > highest) {
                    highest = locscfg.getInt(tops + ".wins");
                    third = tops;
                }
            }
        }

        top.put(1, first);
        top.put(2, second);
        top.put(3, third);

        return top;
    }

    public static int getRank(Player p) {
        ArrayList<String> list = new ArrayList<>();
        for(String tops : locscfg.getConfigurationSection("").getKeys(true)) {
            list.add(tops);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if(getWinns(list.get(i)) > getWinns(list.get(j))) {
                    String temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }

        int pos = -1, counter = 0;
        for (String uuids : list) {
            if(p.getUniqueId().toString().equalsIgnoreCase(uuids)) {
                pos = counter;
                break;
            }
            counter++;
        }
        return (list.size() - pos);
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
