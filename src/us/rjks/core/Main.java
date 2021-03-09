package us.rjks.core;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sun.scenario.Settings;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import us.rjks.cmd.*;
import us.rjks.listener.*;
import us.rjks.utils.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:36
 *
 **************************************************************************/

public class Main extends JavaPlugin {

    private static Main instance;
    public static int counter = 0;
    public ArrayList<ArmorStand> stands = new ArrayList<>();

    @Override
    public void onEnable() {
        super.onEnable();

        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin))
            return;

        getServer().getConsoleSender().sendMessage("Enabled WorldEdit support!");

        try {
            if(!new File("plugins/Mlgrush").exists()) new File("plugins/Mlgrush").mkdirs();
            if(!new File("plugins/Mlgrush/Maps").exists()) new File("plugins/Mlgrush/Maps").mkdirs();
            if(!new File("plugins/Mlgrush/Maps/Schematic").exists()) new File("plugins/Mlgrush/Maps/Schematic").mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpawnManager.create();
        InventoryPositions.create();
        Stats.create();
        Shop.create();

        checkLicense();

        if(Bukkit.getWorld("stickfight") == null) {
            getServer().createWorld(new WorldCreator("stickfight"));
        }

        instance = this;
        register();

        for(Player all : Bukkit.getOnlinePlayers()) {
            TabRank.setScoreBoard(all);
            for(Player alll : Bukkit.getOnlinePlayers()) {
                all.showPlayer(alll);
            }
        }

        Bukkit.getConsoleSender().sendMessage("§aStickfight started, plugin by Salty https://discord.gg/EevEJj67Y8");

        try {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    for(Player p : Queue.mlgrush) {
                        try {TitleManager.sendActionBar(p, "§7Du bist in der Warteschlange für §eMLGRush");} catch (Exception e) {}
                    }

                    if(counter == 60 * 5) {
                        for(World world : Bukkit.getServer().getWorlds()){
                            for(Entity entity : world.getEntities()){
                                if(entity.getType().equals(EntityType.ARMOR_STAND)) {
                                    entity.remove();
                                }
                            }
                        }
                        NCP.loadNPCs();
                        counter= 0;
                    } else {
                        counter++;
                    }
                }
            }, 20L, 20L);
        } catch (Exception e) {

        }

    }

    public void checkLicense() {
        try {
            URL url = new URL("https://herr-mann.nrw/state.json");
            Scanner sc = new Scanner(url.openStream());
            String output = "";
            while (sc.hasNext()) {
                String out = sc.nextLine();
                output = output + "\n" + out;
            }
            if(output.contains("true")) {
                return;
            } else {
                getServer().getPluginManager().disablePlugin(this);
                System.out.println("§cDie Lizens für dieses Plugin ist abgelaufen.");
            }
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        for (Player all : Bukkit.getOnlinePlayers()) {
            SpawnManager.tp(all, "spawn");
        }

        for(Player all : Bukkit.getOnlinePlayers()) {
            TabRank.setScoreBoard(all);
            for(Player alll : Bukkit.getOnlinePlayers()) {
                all.showPlayer(alll);
            }
        }

        for (Round round : Data.rounds) {
            round.stop();
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            for (Player alll : Bukkit.getOnlinePlayers()) {
                all.showPlayer(alll);
                Inventory.setLobbyItems(all);
            }
        }

        Bukkit.getConsoleSender().sendMessage("§aStickfight stopped, plugin by Salty https://discord.gg/EevEJj67Y8");
    }

    @Override
    public void onLoad() {
        super.onLoad();

        Bukkit.getConsoleSender().sendMessage("§aStickfight loaded, plugin by Salty https://discord.gg/EevEJj67Y8");
    }

    public void register() {
        try {
            Bukkit.getPluginManager().registerEvents(new Join(), this);
            Bukkit.getPluginManager().registerEvents(new Build(), this);
            Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
            Bukkit.getPluginManager().registerEvents(new EntityInteract(), this);
            Bukkit.getPluginManager().registerEvents(new InvSelect(), this);
            Bukkit.getPluginManager().registerEvents(new Move(), this);
            Bukkit.getPluginManager().registerEvents(new Chat(), this);

            getServer().getPluginCommand("world").setExecutor(new world());
            getServer().getPluginCommand("stats").setExecutor(new stats());
            getServer().getPluginCommand("map").setExecutor(new map());
            getServer().getPluginCommand("leave").setExecutor(new leave());
            getServer().getPluginCommand("l").setExecutor(new leave());
            getServer().getPluginCommand("spec").setExecutor(new spec());
            getServer().getPluginCommand("spawn").setExecutor(new leave());
            getServer().getPluginCommand("einrichten").setExecutor(new einrichten());
        } catch (Exception e) {

        }
    }

    public static Main getInstance() {
        return instance;
    }
}
