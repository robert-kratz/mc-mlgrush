package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Bed;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 26.02.2021 / 13:54
 *
 **************************************************************************/

public class Map {

    public static ArrayList<Map> activmaps = new ArrayList<>();

    private ArrayList<Block> blocks = new ArrayList<>();
    private String worldname = "stickfight";
    private CustomMap map;
    private Integer center;

    public Map(Integer c) {
        center = c;
    }

    public void loadMap() {
        String[] pathnames = new File("plugins/Mlgrush/Maps").list();

        ArrayList<String> maps = new ArrayList<>();

        for (int i = 0; i < pathnames.length; i++) {
            if(!pathnames[i].equalsIgnoreCase("Schematic")) {
                maps.add(pathnames[i]);
            }
        }
        maps.forEach(map -> {
            System.out.println(map);
        });

        if(maps.size() == 0) {
            System.out.println("Warning, no maps were found! Please add one by running the /map command");
        } else {
            int selected = new Random().nextInt(maps.size());
            System.out.println(maps.get(selected) + " s");
            System.out.println("selected: " + maps.get(selected));
            map = new CustomMap(maps.get(selected).replaceAll(".yml", ""), center);
            map.loapMap();
        }
    }

    public void clearMap() {
        for(org.bukkit.block.Block block : blocks) {
            block.setType(Material.AIR);
        }
        blocks.clear();
    }

    public void removeBlock(Block block) {
        blocks.remove(block);
    }

    public void deleteMap() {
        for(int x = (center - 40); x < (center + 40); x++) {
            for(int y = (100 - 40); y < (100 + 40); y++) {
                for(int z = -40; z < 40; z++) {
                    Bukkit.getWorld(worldname).getBlockAt(new Location(Bukkit.getWorld(worldname), x, y, z)).setType(Material.AIR);
                }
            }
        }
    }

    public Location getSpecLocation() {
        return map.getSpec();
    }

    public Location getA() {
        return map.getA();
    }

    public Location getB() {
        return map.getB();
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setWorldname(String worldname) {
        this.worldname = worldname;
    }

    public String getWorldname() {
        return worldname;
    }

    public Integer getCenter() {
        return center;
    }

    public CustomMap getMap() {
        return map;
    }

    public static int findLocation() {
        Integer highest = 0;
        for(Map map : activmaps) {
            if(map.getCenter() > highest) {
                highest = map.getCenter();
                System.out.println(map.getCenter());
            }
        }
        System.out.println(highest + 150);
        return (highest + 150);
    }


}
