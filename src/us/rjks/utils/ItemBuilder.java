package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 19:13
 *
 **************************************************************************/

public class ItemBuilder {

    public ItemStack stack;
    public ItemMeta meta;

    public ItemBuilder(Material material, String displayname) {
        stack = new ItemStack(material);
        meta = stack.getItemMeta();
        meta.setDisplayName(displayname);
    }

    public ItemBuilder setDamage(short amount) {
        stack.setDurability(amount);
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        stack.setType(material);
        return this;
    }

    public ItemBuilder addEntchantments(Map<Enchantment, Integer> enchantments) {
        stack.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder addEntchantments(Enchantment enchantments, int value) {
        stack.addUnsafeEnchantment(enchantments, value);
        return this;
    }

    public ItemBuilder setType(Material material) {
        stack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayname(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        List<String> list = new ArrayList<>();
        list.add(lore);
        meta.setLore(list);
        return this;
    }

    public ItemBuilder setSkull(String name) {
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(name);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemStack checkout() {
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemBuilder setUnbreakable(boolean state) {
        meta.spigot().setUnbreakable(state);
        return this;
    }

    public static ItemStack getPlayerSkull(String paramPlayer, String display) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(paramPlayer);
        meta.setDisplayName(display);
        skull.setItemMeta(meta);
        return skull;
    }
}
