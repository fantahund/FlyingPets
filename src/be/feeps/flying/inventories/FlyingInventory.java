package be.feeps.flying.inventories;

import be.feeps.flying.FlyingPlayer;
import be.feeps.flying.utilities.Updater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;

public abstract class FlyingInventory{
    protected Player player;
    protected FlyingPlayer fPlayer;
    protected ArrayList<FlyingItem> fItems;
    private Inventory inv;

    public FlyingInventory(Player player, int size, String name){
        this.player = player;
        fPlayer = FlyingPlayer.instanceOf(player);
        fItems = new ArrayList<>();
        inv = Bukkit.createInventory(null, size, name);

        BoundingBox boundingBox = new BoundingBox();
    }

    public void open(){
        fPlayer.setInventory(this); // Change the inventory
        fItems.forEach(item -> inv.setItem(item.slot, item.bukkitItem));
        player.openInventory(inv);
    }

    public void update(Updater.Type type){
        // Nothing
    }

    public void onClose(InventoryCloseEvent event){

    }

    public Inventory getBukkitInventory(){
        return inv;
    }

    public static class InvListener implements Listener {

        @EventHandler
        public void onInventoryUpdate(Updater.Event event){
            FlyingPlayer.getFlyingPlayers().forEach((id, fPlayer) -> {
                if (fPlayer.getInventory() != null) fPlayer.getInventory().update(event.getType());
            });
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event){
            FlyingPlayer fPlayer = FlyingPlayer.instanceOf((Player) event.getWhoClicked());
            FlyingInventory fInventory = fPlayer.getInventory();

            if (fInventory == null) return;
            event.setCancelled(true);
            if (fInventory.getBukkitInventory().equals(event.getClickedInventory())){
                fInventory.fItems.stream().filter(item -> event.getSlot() == item.slot).findAny().ifPresent(item -> {
                    item.listener.accept(event);
                });
            }
        }

        @EventHandler
        public void onItemMove(InventoryMoveItemEvent event){
            FlyingPlayer.getFlyingPlayers().forEach((id, fplayer) -> {
                if (fplayer.getInventory().getBukkitInventory() == event.getInitiator())
                    event.setCancelled(true);
            });
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event){
            FlyingPlayer fPlayer = FlyingPlayer.instanceOf((Player) event.getPlayer());

            if (fPlayer.getInventory() == null) return;
            if (fPlayer.getInventory().getBukkitInventory() == event.getInventory()){
                fPlayer.getInventory().onClose(event);
                fPlayer.setInventory(null);
            }
        }
    }
}
