package us.rjks.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 27.02.2021 / 15:44
 *
 **************************************************************************/

public class NCP {
    // get base 64 skin from https://sessionserver.mojang.com/session/minecraft/profile/{UUID}?unsigned=false
    private static final String SKIN = "ewogICJ0aW1lc3RhbXAiIDogMTYwOTUzNTg4MzM1MywKICAicHJvZmlsZUlkIiA6ICI1NWY2YmYwNTU1NTU0NzA1OGQ3NDQ0ZWEyMWM1OTRmYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJOYWNoZ2VzYWx6dCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yNmQwYWVkMzUwNjIwMjFkMWI0MjcyZTIzM2Q4ZDIwNTkyMDM4ODRmYjA4NTc0Yjg0ZDZlYjExMWVhYWE3YzIyIgogICAgfQogIH0KfQ==";
    private static final String SIGNATURE = "RPEfb72OqVsnX/4Yy2aogvdp/XJMei9kb0ArtLPANZR2CIldtaRxQas7ef2vTZsc0HBD4cf1I8d6Ogj64S9E/ONh8geki7rumEg1auk3zwJ5qrt8DT3wIWz/DprpPk2CkJkWSFANoXD7vym2r4gdE4ft2xEH6mHw7BCBzFw4BZI3JDwnpOuVvxBdrHIOg3SDiULQ5JV7OyXDHX0hCxnB7UoYDMIqCv6rbHPjfocM8IWgxL/pT6QiJKcjlC1PUb2lMDv68Dr0GQx+PnUMQX/wxo6onS4U5Wu4raU874/1zj/Z6liYjQr7CZeUHM5Yu85+o8NOUeYVoWY8vxn+llKjwaKlCg3v2nqSYTeAzCLp+nxujbFWHazPjs0Zcz1TSvUE4WRwQ0PskgV+CZixPh0UDHeUhfuUV8agjQQTkDF7AXK0wd1lrlgReT5Dj6UxWC8EyquWeJ7TVbuNLB8auN4Cx5maALP+D+yXXpR+v/JFwcjsEXosh7vVs6rb0LprbWp1dn1URSJuPXEnvLZNSjPDW/4YFo5UB5wpH+n1JKfzxZ/FYaL/5EgrUqs1JvyJP3GrArt8kXhCeBlsSrdSsbPtoHMtTdfWW0NMCVIapo2EjM7CVOWbtG4XsRydWFGExEjCtlorMQrbWqU5tYXFyrLHdXCVfWNupkM3udFtiDr9DPY=";
    private static final Random RANDOM = new Random();

    private int entityId;
    private Location location;
    private GameProfile profile;

    public NCP(Location location, UUID uuid, String name) {
        this.entityId = RANDOM.nextInt(1000) + 2000;
        this.profile = new GameProfile(uuid, name);
        this.profile.getProperties().put("textures", new Property("textures", SKIN, SIGNATURE));
        this.location = location;
    }

    public void spawn(Player p) {
        PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = infoPacket.new PlayerInfoData(profile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(profile.getName())[0]);
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(infoPacket, "b");
        players.add(data);
        setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(infoPacket, "b", players);

        PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn();
        setValue(spawnPacket, "a", entityId);
        setValue(spawnPacket, "b", profile.getId());
        setValue(spawnPacket, "c", MathHelper.floor(location.getX() * 32.0D));
        setValue(spawnPacket, "d", MathHelper.floor(location.getY() * 32.0D));
        setValue(spawnPacket, "e", MathHelper.floor(location.getZ() * 32.0D));
        setValue(spawnPacket, "f",  (byte)((int)(location.getYaw() * 256.0F / 360.0F)));
        setValue(spawnPacket, "g",  (byte)((int)(location.getPitch() * 256.0F / 360.0F)));
        setValue(spawnPacket, "h", 0);
        DataWatcher watcher = new DataWatcher(null);
        watcher.a(6, (float) 20);
        watcher.a(10, (byte) 127);
        setValue(spawnPacket, "i", watcher);

        sendPacket(p, infoPacket);
        sendPacket(p, spawnPacket);
    }

    public void despawn(Player p) {
        PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = infoPacket.new PlayerInfoData(profile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(profile.getName())[0]);
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(infoPacket, "b");
        players.add(data);
        setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(infoPacket, "b", players);

        PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(entityId);

        sendPacket(p, infoPacket);
        sendPacket(p, destroyPacket);
    }

    private void setValue(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private Object getValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendPacket(Packet packet) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, packet);
        }
    }

    private void sendPacket(Player player, Packet packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void loadNPCs() {

        try {
            int i = 1;
            HashMap<Integer, String> stats = Stats.getTopThree();
            for(Integer in : Stats.getTopThree().keySet()) {
                if(stats.get(in) != null) {
                    String username = NameFetcher.getName(stats.get(in));

                    ArmorStand as = (ArmorStand) SpawnManager.getLocation( "stats-" + i).getWorld().spawn(SpawnManager.getLocation("stats-" + i), ArmorStand.class);

                    as.setGravity(false);
                    as.setCanPickupItems(false);
                    as.setCustomName("§e" + username + "§8(§7#" + i + "§8)");
                    as.setCustomNameVisible(true);

                    as.setHelmet(ItemBuilder.getPlayerSkull(username, ""));
                    as.setArms(true);

                    if(i == 1) {
                        as.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
                        as.setBoots(new ItemStack(Material.GOLD_BOOTS));
                        as.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
                    } else if(i == 2) {
                        as.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        as.setBoots(new ItemStack(Material.IRON_BOOTS));
                        as.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    } else if(i == 3) {
                        as.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                        as.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                        as.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                    }
                }
                i++;
            }
        } catch (Exception e) {

        }
    }
}
