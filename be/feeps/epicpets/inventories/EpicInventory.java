package be.feeps.epicpets.inventories;

import java.util.HashMap;
import java.util.List;

import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by feeps on 30/04/2017.
 */
public abstract class EpicInventory{

    private static HashMap<String, EpicInventory> inventories = new HashMap<String, EpicInventory>();
    private boolean registered = false;
    protected Inventory inv;
    protected Material currentItem;
    protected CacheConfig cache = CacheConfig.getInstance();

    public EpicInventory(Material currentItem){
        this.currentItem = currentItem;
    }

    public abstract String name();
    public abstract int size();
    public abstract void contents(Player player, Inventory inv);
    public abstract void onClick(Player player, Inventory inv, ItemStack current, int slot);

    public static Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onClick(InventoryClickEvent event){
                if (event.getInventory() == null) return;
                if (event.getCurrentItem() == null) return;
                if (event.getCurrentItem().getType() == Material.AIR) return;
                if (!(event.getWhoClicked() instanceof Player)) return;

                Inventory inv = event.getInventory();
                if (inventories.containsKey(event.getClickedInventory().getName())) {
                    //Player player = (Player) event.getWhoClicked();
                    EpicInventory currentInv = inventories.get(event.getClickedInventory().getName());
                    if(event.getInventory().getName().contains(currentInv.getInv().getName())){
                        event.setCancelled(true);
                        ItemStack current = event.getCurrentItem();
                        currentInv.onClick((Player)event.getWhoClicked(), inv, current, event.getSlot());
                    }
                }
            }

            /*@EventHandler
            public void onClose(InventoryCloseEvent event) {
                if (event.getPlayer() instanceof Player) {
                    if(event.getInventory() != null){
                        if (inventories.containsKey(event.getInventory().getName())) {
                            EpicInventory currentInv = inventories.get(event.getInventory().getName());
                            if(event.getInventory().getName().contains(currentInv.getInv().getName())){
                                //currentInv.epicPetsPlayer.getInventory().getInv().clear();
                                //currentInv.epicPetsPlayer.setInv(null);
                                //currentInv.unRegister();
                            }
                        }
                    }
                }
            }*/
        };
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

    private void register() {
        if (!registered) {
            inventories.put(this.name(), this);
            registered = true;
        }
    }

    public Inventory getInv(){
        return this.inv;
    }

    public void open(Player player){
        this.inv = null;
        this.inv = Bukkit.createInventory(null, this.size(), this.name());
        contents(player, this.inv);
        register();
        player.openInventory(this.inv);

    }
}
