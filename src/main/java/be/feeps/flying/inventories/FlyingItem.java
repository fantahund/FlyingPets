package be.feeps.flying.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class FlyingItem {
    public ItemStack bukkitItem;
    public int slot;
    public Consumer<InventoryClickEvent> listener;

    public FlyingItem(ItemStack bukkitItem, int slot, Consumer<InventoryClickEvent> listener){
        this.bukkitItem = bukkitItem;
        this.slot = slot;
        this.listener = listener;
    }

    public FlyingItem(ItemStack bukkitItem, int slot){
        this(bukkitItem, slot, event -> {}); // Empty Consumer<InventoryClickEvent>
    }
}
