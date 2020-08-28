package be.feeps.flying.utilities;

import be.feeps.flying.Reflection;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/**
 * Easily create itemstacks, without messing your hands.
 * <i>Note that if you do use this in one of your projects, leave this notice.</i>
 * <i>Please do credit me if you do use this in one of your projects.</i>
 * @author NonameSL
 */
public class Item {
    private final ItemStack item;
    private final ItemMeta itemM;

    public Item(final Material itemType){
        item = new ItemStack(itemType);
        itemM = item.getItemMeta();
    }

    public Item(final ItemStack itemStack){
        item = itemStack;
        itemM = item.getItemMeta();
    }

    public Item(){
        item = new ItemStack(Material.AIR);
        itemM = item.getItemMeta();
    }

    public Item type(final Material material){
        make().setType(material);
        return this;
    }

    public Item amount(final Integer itemAmt){
        make().setAmount(itemAmt);
        return this;
    }

    public Item name(final String name){
        meta().setDisplayName(name);
        make().setItemMeta(meta());
        return this;
    }

    public Item lore(final String lore){
        List<String> lores = meta().getLore();
        if(lores == null){lores = new ArrayList<>();}
        lores.add(lore);
        meta().setLore(lores);
        make().setItemMeta(meta());
        return this;
    }

    public Item lores(final String[] lores){
        List<String> loresList = meta().getLore();
        if(loresList == null){loresList = new ArrayList<>();}
        else{loresList.clear();}
        Collections.addAll(loresList, lores);
        meta().setLore(loresList);
        return this;
    }

    public Item durability(final int durability){
        make().setDurability((short) durability);
        return this;
    }

    @SuppressWarnings("deprecation")
    public Item data(final int data){
        make().setData(new MaterialData(make().getType(), (byte)data));
        return this;
    }

    public Item enchantment(final Enchantment enchantment, final int level){
        make().addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public Item addEffect(PotionEffect effect){
        PotionMeta potionMeta = (PotionMeta) meta();
        potionMeta.addCustomEffect(effect, true);
        make().setItemMeta(meta());
        return this;
    }
    public Item enchantment(final Enchantment enchantment){
        make().addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public Item enchantments(final Enchantment[] enchantments, final int level){
        make().getEnchantments().clear();
        for(Enchantment enchantment : enchantments){
            make().addUnsafeEnchantment(enchantment, level);
        }
        return this;
    }

    public Item enchantments(final Enchantment[] enchantments){
        make().getEnchantments().clear();
        for(Enchantment enchantment : enchantments){
            make().addUnsafeEnchantment(enchantment, 1);
        }
        return this;
    }

    public Item clearEnchantment(final Enchantment enchantment){
        Map<Enchantment, Integer> itemEnchantments = make().getEnchantments();
        for(Enchantment enchantmentC : itemEnchantments.keySet()){
            if(enchantment == enchantmentC){
                itemEnchantments.remove(enchantmentC);
            }
        }
        return this;
    }

    public Item clearEnchantments(){
        make().getEnchantments().clear();
        return this;
    }

    public Item clearLore(final String lore){
        meta().getLore().remove(lore);
        make().setItemMeta(meta());
        return this;
    }

    public Item clearLores(){
        meta().getLore().clear();
        make().setItemMeta(meta());
        return this;
    }

    public Item color(final Color color){
        if(make().getType() == Material.LEATHER_HELMET
                || make().getType() == Material.LEATHER_CHESTPLATE
                || make().getType() == Material.LEATHER_LEGGINGS
                || make().getType() == Material.LEATHER_BOOTS ){
            LeatherArmorMeta meta = (LeatherArmorMeta) meta();
            meta.setColor(color);
            make().setItemMeta(meta);
        }
        return this;
    }

    public Item clearColor(){
        if(make().getType() == Material.LEATHER_HELMET
                || make().getType() == Material.LEATHER_CHESTPLATE
                || make().getType() == Material.LEATHER_LEGGINGS
                || make().getType() == Material.LEATHER_BOOTS ){
            LeatherArmorMeta meta = (LeatherArmorMeta) meta();
            meta.setColor(null);
            make().setItemMeta(meta);
        }
        return this;
    }

    public Item skullOwner(final String name){
        if(make().getType() == Material.PLAYER_HEAD){
            SkullMeta skullMeta = (SkullMeta) meta();
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
            make().setItemMeta(meta());
        }
        return this;
    }

    public Item skullBase64(String base64url){
        if(make().getType() == Material.PLAYER_HEAD){
            SkullMeta headMeta = (SkullMeta) meta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", base64url));

            Reflection.getField(headMeta.getClass(), "profile", profile.getClass()).set(headMeta, profile);
            make().setItemMeta(headMeta);
        }
        return this;
    }


    public ItemMeta meta(){
        return itemM;
    }

    public ItemStack make(){
        return item;
    }
}