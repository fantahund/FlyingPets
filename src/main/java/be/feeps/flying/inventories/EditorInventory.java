package be.feeps.flying.inventories;

import be.feeps.flying.objects.pets.Pet;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import be.feeps.flying.utilities.Reference;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EditorInventory extends FlyingInventory {
    private final Pet pet;

    public EditorInventory(Player player, int size){
        super(player, size, "Pet Editor");
        pet = fPlayer.getPetManager().getPet();

        put(Editor.NAME, 3);
        put(Editor.SPAWN_JOIN, 5);
    }

    public EditorInventory(Player player){
        this(player, 9*3);
        fItems.add(new FlyingItem(FlyingUtils.backItem, 22, event -> new MainInventory(player).open()));
    }

    public void put(Editor editor, int slot){
        editor.put(this, slot);
    }

    // Use the Reference's Object of the value himself
    public void switcher(Reference<Boolean> reference, ItemStack item, int slot, int switcherSlot, Consumer<Reference<Boolean>> callback){
        fItems.add(new FlyingItem(item, slot)); // Simple item

        // The switcher
        fItems.add(new FlyingItem(FlyingUtils.getBoolItem(reference.getValue()), switcherSlot, event -> {
            reference.setValue(!reference.getValue());
            event.setCurrentItem(FlyingUtils.getBoolItem(reference.getValue()));
            callback.accept(reference);
        }));
    }

    public void switcher(Reference<Boolean> reference, ItemStack item, int slot, Consumer<Reference<Boolean>> callback){
        switcher(reference, item, slot, slot + 9, callback);
    }

    public enum Editor{
        NAME((inv, slot) -> {
            inv.fItems.add(new FlyingItem(new Item(Material.NAME_TAG).name(FlyingUtils.L("inventories.editor.editname")).make(), slot, event -> {
                FlyingUtils.SignGUI.open(inv.player, texts -> inv.pet.setName(String.join("", texts)));
            }));
        }),
        SPAWN_JOIN((inv, slot) -> {
            inv.switcher(inv.fPlayer.getPlayerData().getSpawnJoin(), new Item(Material.RED_BED).name(FlyingUtils.L("inventories.editor.spawnjoin")).make(), slot, reference -> {
                // Do nothing
            });
        });
        private final BiConsumer<EditorInventory, Integer> consumer;

        Editor(BiConsumer<EditorInventory, Integer> consumer){
            this.consumer = consumer;
        }

        public void put(EditorInventory inv, int slot){
            consumer.accept(inv, slot);
        }
    }
}
