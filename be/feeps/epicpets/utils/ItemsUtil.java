package be.feeps.epicpets.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

/**
 * Created by feeps on 4/04/17.
 */
public class ItemsUtil {
    public static void add(ItemStack item, Inventory inv, int Slot, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        inv.setItem(Slot, item);
    }


    public static ItemStack createDyeLeather(Material leatherPiece, String displayName, Color color) {
        ItemStack item = new ItemStack(leatherPiece);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setColor(Color.fromRGB(color.asRGB()));
        item.setItemMeta(meta);
        return item;
    }

    //Example d'utilisation : Items.add(new ItemStack(Material.BED, 1), Navigateur, 4, "Ex", Arrays.asList(new String[] { "TEST", "BLABLA" }));
}
