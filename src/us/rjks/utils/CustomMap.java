package us.rjks.utils;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 01.03.2021 / 11:10
 *
 **************************************************************************/

public class CustomMap {

    public static ArrayList<CustomMap> maps = new ArrayList<>();

    private String name, configname;
    private Location spec, A, B;
    private Block a_bed_top, a_bed_bottom, b_bed_top, b_bed_bottom;
    private int center;

    public CustomMap(String name, int center) {
        File logs = new File("plugins/Mlgrush/Maps", name + ".yml");
        if(!logs.exists()) {
            return;
        }
        YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);
        try {
            this.name = locscfg.getString(name + ".name");
            this.configname = name;
            this.center = center;

            Location spec = new Location(Bukkit.getWorld("stickfight"), 0, 0, 0);
            spec.setX((locscfg.getDouble(name + ".spec.x") + center));
            spec.setY((locscfg.getDouble(name + ".spec.y") + 100));
            spec.setZ(locscfg.getDouble(name + ".spec.z"));
            spec.setYaw((float)locscfg.getDouble(name + ".spec.yaw"));
            spec.setPitch((float)locscfg.getDouble(name + ".spec.pitch"));
            this.spec = spec;

            Location a = new Location(Bukkit.getWorld("stickfight"), 0, 0, 0);
            a.setX((locscfg.getDouble(name + ".a.x") + center));
            a.setY((locscfg.getDouble(name + ".a.y") + 100));
            a.setZ(locscfg.getDouble(name + ".a.z"));
            a.setYaw((float)locscfg.getDouble(name + ".a.yaw"));
            a.setPitch((float)locscfg.getDouble(name + ".a.pitch"));
            this.A = a;

            Location b = new Location(Bukkit.getWorld("stickfight"), 0, 0, 0);
            b.setX((locscfg.getDouble(name + ".b.x") + center));
            b.setY((locscfg.getDouble(name + ".b.y") + 100));
            b.setZ(locscfg.getDouble(name + ".b.z"));
            b.setYaw((float)locscfg.getDouble(name + ".b.yaw"));
            b.setPitch((float)locscfg.getDouble(name + ".b.pitch"));
            this.B = b;

            this.a_bed_bottom = Bukkit.getWorld("stickfight").getBlockAt((locscfg.getInt(name + ".a.bed.bottom.x")+ center),
                    (locscfg.getInt(name + ".a.bed.bottom.y") + 100),
                    locscfg.getInt(name + ".a.bed.bottom.z"));

            this.a_bed_top = Bukkit.getWorld("stickfight").getBlockAt((locscfg.getInt(name + ".a.bed.top.x")+ center),
                    (locscfg.getInt(name + ".a.bed.top.y") + 100),
                    locscfg.getInt(name + ".a.bed.top.z"));

            this.b_bed_bottom = Bukkit.getWorld("stickfight").getBlockAt((locscfg.getInt(name + ".b.bed.bottom.x")+ center),
                    (locscfg.getInt(name + ".b.bed.bottom.y") + 100),
                    locscfg.getInt(name + ".b.bed.bottom.z"));

            this.b_bed_top = Bukkit.getWorld("stickfight").getBlockAt((locscfg.getInt(name + ".b.bed.top.x")+ center),
                    (locscfg.getInt(name + ".b.bed.top.y") + 100),
                    locscfg.getInt(name + ".b.bed.top.z"));
            System.out.println("Loaded map " + name);
            maps.add(this);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void loapMap() {
        paste(getName(), new Location(Bukkit.getWorld("stickfight"), center, 100, 0));
    }

    public Block getB_bed_top() {
        return b_bed_top;
    }

    public Block getB_bed_bottom() {
        return b_bed_bottom;
    }

    public Block getA_bed_top() {
        return a_bed_top;
    }

    public Block getA_bed_bottom() {
        return a_bed_bottom;
    }

    public String getConfigname() {
        return configname;
    }

    public Location getB() {
        return B;
    }

    public Location getA() {
        return A;
    }

    public String getName() {
        return name;
    }

    public Location getSpec() {
        return spec;
    }

    //WorldEdit API

    public static void save(Player player, String schematicName) {
        try {
            File schematic = new File("plugins/Mlgrush/Maps/Schematic/" + schematicName + ".schematic");
            File dir = new File("plugins/Mlgrush/Maps/Schematic");
            if (!dir.exists())
                dir.mkdirs();

            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
            WorldEdit we = wep.getWorldEdit();

            LocalPlayer localPlayer = wep.wrapPlayer(player);
            LocalSession localSession = we.getSession(localPlayer);
            ClipboardHolder selection = localSession.getClipboard();
            EditSession editSession = localSession.createEditSession(localPlayer);

            Vector min = selection.getClipboard().getMinimumPoint();
            Vector max = selection.getClipboard().getMaximumPoint();

            editSession.enableQueue();
            CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min), min);
            clipboard.copy(editSession);
            SchematicFormat.MCEDIT.save(clipboard, schematic);
            editSession.flushQueue();

            player.sendMessage("Saved schematic!");
        } catch (IOException | DataException ex) {
            ex.printStackTrace();
        } catch (EmptyClipboardException ex) {
            ex.printStackTrace();
        }
    }


    public static void paste(String schematicName, Location pasteLoc) {
        try {
            File dir = new File("plugins/Mlgrush/Maps/Schematic/" + schematicName + ".schematic");

            if(!dir.exists()) {
                System.out.println("This schemattic does not exists: " + schematicName);
            }

            EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), 999999999);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(dir);
            CuboidClipboard clipboard = schematic.load(dir);

            clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), true);
            editSession.flushQueue();
        } catch (DataException | IOException ex) {
            ex.printStackTrace();
        } catch (MaxChangedBlocksException ex) {
            ex.printStackTrace();
        }
    }

}
