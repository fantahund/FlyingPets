package be.feeps.epicpets.inventories;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by feeps on 18/06/2017.
 */
public abstract class EpicInventories {
    private Inventory inv;
    protected Player player;
    protected EpicPetsPlayer epicPetsPlayer;
    protected CacheConfig cache = CacheConfig.getInstance();

    public EpicInventories(Player player, String name, int size){
        this.player = player;
        this.epicPetsPlayer = EpicPetsPlayer.instanceOf(this.player);
        this.inv = Bukkit.createInventory(null, size, name);
        this.epicPetsPlayer.setEpicInv(this);

    }

    public void setItem(ItemStack item, int slot, String name, List<String> lore) {
        ItemMeta im = item.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        if (name != null) {
            im.setDisplayName(MessageUtil.translate(name));
        }
        if (lore != null) {
            im.setLore(MessageUtil.listTranslate(lore));
        }
        item.setItemMeta(im);
        this.inv.setItem(slot, item);
    }

    public abstract void create();
    public abstract void onClick(ItemStack current, int slot);

    public void openInv(){
        this.player.openInventory(this.inv);
    }

    public void update(){
        this.inv.clear();
        this.create();
    }

    public void clear(){
        this.inv.clear();
        this.inv = null;
        this.epicPetsPlayer.setEpicInv(null);
    }

    public Inventory getInv(){
        return this.inv;
    }

}
