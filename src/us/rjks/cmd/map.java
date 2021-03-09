package us.rjks.cmd;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.rjks.utils.CustomMap;
import us.rjks.utils.Round;
import us.rjks.utils.SpawnManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 05.03.2021 / 17:35
 *
 **************************************************************************/

public class map implements CommandExecutor {

    public static ArrayList<SetUpMap> setupmaps = new ArrayList<>();
    public static HashMap<Player , SetUpMap> a_bottom = new HashMap<>(), a_Top = new HashMap<>(), b_bottom = new HashMap<>(), b_Top = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if(sender.hasPermission("*")) {
                if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("checkout")) {
                        if(getByName(args[1]) != null) {
                            SetUpMap maps = getByName(args[1]);
                            sender.sendMessage("Achtung, die map wird gespeichert:");
                            sender.sendMessage(maps.getName());
                            sender.sendMessage(maps.getConfigname());
                            sender.sendMessage(maps.getSpec().toString());
                            sender.sendMessage(maps.getA().toString());
                            sender.sendMessage(maps.getB().toString());
                            sender.sendMessage(maps.getStartloc().toString());
                            sender.sendMessage(maps.a_bed_top.toString());
                            sender.sendMessage(maps.b_bed_top.toString());
                            sender.sendMessage(maps.a_bed_bottom.toString());
                            sender.sendMessage(maps.b_bed_bottom.toString());
                            sender.sendMessage("Gebe §a/map checkout " + args[1] + " confirm §fein um die map zu speichern");
                        }
                    }
                } else if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("setup")) {
                        if(getByName(args[2]) == null) {
                            sender.sendMessage("Achtung, die map wird gleich an deiner Position geladen");
                            sender.sendMessage("Gebe §a/map setup " + args[1] + " " + args[2] + " confirm §fein");
                        } else {
                            sender.sendMessage("Es läuft bereits eine installation dieser map");
                        }
                    } else if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("spec")) {
                        if(getByName(args[0]) != null) {
                            setupmaps.get(getPosByName(args[0])).setSpec(((Player) sender).getPlayer().getLocation());
                            sender.sendMessage("Der Spectator Spawn wurde gesetzt");
                            sender.sendMessage("Setze den Spawnpunkt für Rot §a/map " + args[0] + " set red");
                        } else {
                            sender.sendMessage("Diese Mapinstallatioon wurde nicht gefunden");
                        }
                    } else if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("red")) {
                        if(getByName(args[0]) != null) {
                            setupmaps.get(getPosByName(args[0])).setA(((Player) sender).getPlayer().getLocation());
                            sender.sendMessage("Der Spectator Spawn wurde gesetzt");
                            sender.sendMessage("Setze den Spawnpunkt für Blau §a/map " + args[0] + " set blue");
                        } else {
                            sender.sendMessage("Diese Mapinstallatioon wurde nicht gefunden");
                        }
                    } else if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("blue")) {
                        if(getByName(args[0]) != null) {
                            setupmaps.get(getPosByName(args[0])).setB(((Player) sender).getPlayer().getLocation());
                            sender.sendMessage("Der Spectator Spawn wurde gesetzt");
                            sender.sendMessage("Setze das Bed für Rot: §a/map " + args[0] + " set bed_red_bottom");
                        } else {
                            sender.sendMessage("Diese Mapinstallatioon wurde nicht gefunden");
                        }
                    } else if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("bed_red_bottom")) {
                        if(getByName(args[0]) != null) {
                            a_bottom.put(((Player) sender).getPlayer(), setupmaps.get(getPosByName(args[0])));
                            sender.sendMessage("Linksklicke nun auf den unteren rand des roten bettes");
                        } else {
                            sender.sendMessage("Diese Mapinstallatioon wurde nicht gefunden");
                        }
                    } else if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("bed_red_top")) {
                        if(getByName(args[0]) != null) {
                            a_Top.put(((Player) sender).getPlayer(), setupmaps.get(getPosByName(args[0])));
                            sender.sendMessage("Linksklicke nun auf den oberen rand des roten bettes");
                        } else {
                            sender.sendMessage("Diese Mapinstallatioon wurde nicht gefunden");
                        }
                    } else if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("bed_blue_bottom")) {
                        if(getByName(args[0]) != null) {
                            b_bottom.put(((Player) sender).getPlayer(), setupmaps.get(getPosByName(args[0])));
                            sender.sendMessage("Linksklicke nun auf den unteren rand des blauen bettes");
                        } else {
                            sender.sendMessage("Diese Mapinstallatioon wurde nicht gefunden");
                        }
                    } else if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("bed_blue_top")) {
                        if(getByName(args[0]) != null) {
                            b_Top.put(((Player) sender).getPlayer(), setupmaps.get(getPosByName(args[0])));
                            sender.sendMessage("Linksklicke nun auf den oberen rand des blauen bettes");
                        } else {
                            sender.sendMessage("Diese Mapinstallatioon wurde nicht gefunden");
                        }
                    } else if(args[0].equalsIgnoreCase("checkout") && args[2].equalsIgnoreCase("confirm")) {
                        if(getByName(args[1]) != null) {
                            SetUpMap map = getByName(args[1]);

                            if(map.getName() != null && map.getA() != null && map.getB() != null && map.getConfigname() != null && map.getSpec() != null && map.getStartloc() != null
                                    && map.getA_bed_bottom() != null && map.getA_bed_top() != null && map.getB_bed_bottom() != null && map.getB_bed_top() != null) {

                                Location center = map.getStartloc();
                                int x = center.getBlockX(), y = center.getBlockY(), z = center.getBlockZ();

                                String name = map.getConfigname();

                                File logs = new File("plugins/Mlgrush/Maps", map.getConfigname() + ".yml");
                                YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);

                                locscfg.set(name + ".name", map.getName());

                                //Location A
                                locscfg.set(name + ".a.x", (map.getA().getX() - x));
                                locscfg.set(name + ".a.y", (map.getA().getY() - y));
                                locscfg.set(name + ".a.z", (map.getA().getZ() - z));

                                locscfg.set(name + ".a.yaw", (map.getA().getYaw()));
                                locscfg.set(name + ".a.pitch", (map.getA().getPitch()));

                                //Location B
                                locscfg.set(name + ".b.x", (map.getB().getX() - x));
                                locscfg.set(name + ".b.y", (map.getB().getY() - y));
                                locscfg.set(name + ".b.z", (map.getB().getZ() - z));

                                locscfg.set(name + ".b.yaw", (map.getB().getYaw()));
                                locscfg.set(name + ".b.pitch", (map.getB().getPitch()));

                                //Location Spec
                                locscfg.set(name + ".spec.x", (map.getSpec().getX() - x));
                                locscfg.set(name + ".spec.y", (map.getSpec().getY() - y));
                                locscfg.set(name + ".spec.z", (map.getSpec().getZ() - z));

                                locscfg.set(name + ".spec.yaw", (map.getSpec().getYaw()));
                                locscfg.set(name + ".spec.pitch", (map.getSpec().getPitch()));

                                //Bed Red Top
                                locscfg.set(name + ".a.bed.top.x", (map.getA_bed_top().getX() - x));
                                locscfg.set(name + ".a.bed.top.y", (map.getA_bed_top().getY() - y));
                                locscfg.set(name + ".a.bed.top.z", (map.getA_bed_top().getZ() - z));

                                //Bed Red Bottom
                                locscfg.set(name + ".a.bed.bottom.x", (map.getA_bed_bottom().getX() - x));
                                locscfg.set(name + ".a.bed.bottom.y", (map.getA_bed_bottom().getY() - y));
                                locscfg.set(name + ".a.bed.bottom.z", (map.getA_bed_bottom().getZ() - z));

                                //Bed Red Top
                                locscfg.set(name + ".b.bed.top.x", (map.getB_bed_top().getX() - x));
                                locscfg.set(name + ".b.bed.top.y", (map.getB_bed_top().getY() - y));
                                locscfg.set(name + ".b.bed.top.z", (map.getB_bed_top().getZ() - z));

                                //Bed Red Bottom
                                locscfg.set(name + ".b.bed.bottom.x", (map.getB_bed_bottom().getX() - x));
                                locscfg.set(name + ".b.bed.bottom.y", (map.getB_bed_bottom().getY() - y));
                                locscfg.set(name + ".b.bed.bottom.z", (map.getB_bed_bottom().getZ() - z));

                                try { locscfg.save(logs); } catch (Exception e) {}
                                CustomMap.maps.remove(map);
                                sender.sendMessage("Die Map wurde gespeichert");
                            } else {
                                sender.sendMessage("Es wurden nicht alle einstellungen Richtig getroffen");
                            }
                        } else {
                            sender.sendMessage("Es läuft bereits eine installation dieser map");
                        }
                    }
                } else if(args.length == 4) {
                    if(args[0].equalsIgnoreCase("setup") && args[3].equalsIgnoreCase("confirm")) {
                        if(getByName(args[1]) == null) {
                            try {
                                CustomMap.paste(args[1], ((Player) sender).getPlayer().getLocation());
                                SetUpMap map = new SetUpMap(((Player) sender).getPlayer().getLocation());
                                map.setConfigname(args[2]);
                                map.setName(args[1]);
                                setupmaps.add(map);
                                sender.sendMessage("Map erfolgreich geladen.");
                                sender.sendMessage("Setze den Spectator Spawn: §a/map " + args[1] + " set spec");
                            } catch (Exception e) {
                                sender.sendMessage("Die map wurde nicht gefunden");
                            }
                        } else {
                            sender.sendMessage("Es läuft bereits eine installation dieser map");
                        }
                    }
                } else {
                    sender.sendMessage("/map setup [CONFIG NAME] [MAPNAME]");
                }
            }
        } else {
            sender.sendMessage("§cYou have to be a player to execute this command");
        }

        return false;
    }

    public class SetUpMap {

        private String name, configname;
        private Location spec, A, B, startloc;
        private Block a_bed_top, a_bed_bottom, b_bed_top, b_bed_bottom;

        public SetUpMap(Location loc) {
            startloc = loc;
        }

        public void setB(Location b) {
            B = b;
        }

        public void setA(Location a) {
            A = a;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setConfigname(String configname) {
            this.configname = configname;
        }

        public void setSpec(Location spec) {
            this.spec = spec;
        }

        public Location getSpec() {
            return spec;
        }

        public String getName() {
            return name;
        }

        public Location getA() {
            return A;
        }

        public Location getStartloc() {
            return startloc;
        }

        public Location getB() {
            return B;
        }

        public String getConfigname() {
            return configname;
        }

        public void setA_bed_bottom(Block a_bed_bottom) {
            this.a_bed_bottom = a_bed_bottom;
        }

        public void setA_bed_top(Block a_bed_top) {
            this.a_bed_top = a_bed_top;
        }

        public void setB_bed_bottom(Block b_bed_bottom) {
            this.b_bed_bottom = b_bed_bottom;
        }

        public void setB_bed_top(Block b_bed_top) {
            this.b_bed_top = b_bed_top;
        }

        public void setStartloc(Location startloc) {
            this.startloc = startloc;
        }

        public Block getA_bed_bottom() {
            return a_bed_bottom;
        }

        public Block getA_bed_top() {
            return a_bed_top;
        }

        public Block getB_bed_bottom() {
            return b_bed_bottom;
        }

        public Block getB_bed_top() {
            return b_bed_top;
        }
    }

    public static SetUpMap getByName(String name) {
        for(SetUpMap maps : setupmaps) {
            if(maps.getName().equalsIgnoreCase(name)) {
                return maps;
            }
        }

        return null;
    }

    public static int getPosByName(String name) {
        for(int i = 0; i < setupmaps.size(); i++) {
            if(setupmaps.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return 0;
    }
}
