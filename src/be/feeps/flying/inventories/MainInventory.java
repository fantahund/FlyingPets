package be.feeps.flying.inventories;

import be.feeps.flying.objects.Animation;
import be.feeps.flying.objects.pets.PetType;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import be.feeps.flying.utilities.Updater;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainInventory extends FlyingInventory {
    private static final Material[] rainbowGlass = new Material[]{
        Material.YELLOW_STAINED_GLASS_PANE,
        Material.LIGHT_BLUE_STAINED_GLASS_PANE,
        Material.LIME_STAINED_GLASS_PANE,
        Material.PINK_STAINED_GLASS_PANE,
        Material.MAGENTA_STAINED_GLASS_PANE,
        Material.PURPLE_STAINED_GLASS_PANE,
        Material.RED_STAINED_GLASS_PANE,
        Material.ORANGE_STAINED_GLASS_PANE
    };

    private Animation animation;
    private ItemStack glassItem;

    public MainInventory(Player player){
        super(player, 9 * 6, FlyingUtils.L("inventories.main.title"));
        glassItem = new Item(rainbowGlass[0]).name(" ").make();

        for (PetType type : PetType.values()){
            fItems.add(new FlyingItem(type.getItem(), fPlayer.getPetManager().getType() != type ? type.getInvSlot() : 42, event -> {
                if (fPlayer.getPetManager() == null){
                    player.closeInventory();
                    return;
                }
                fPlayer.getPetManager().setType(type);
                fPlayer.getPetManager().spawn();
                player.closeInventory();
            }));
        }

        final ItemStack spawnItem = new Item(Material.NETHER_STAR).name(FlyingUtils.L("inventories.main.spawnpet")).make();
        final ItemStack removeItem = new Item(Material.FERMENTED_SPIDER_EYE).name(FlyingUtils.L("inventories.main.removepet")).make();

        fItems.add(new FlyingItem(fPlayer.getPetManager().exists() ? removeItem : spawnItem, 16, event -> {
            if (fPlayer.getPetManager() == null){
                player.closeInventory();
                return;
            }

            if (fPlayer.getPetManager().exists())
                fPlayer.getPetManager().remove();
            else
                fPlayer.getPetManager().spawn();

            player.closeInventory();
        }));

        fItems.add(new FlyingItem(new Item(Material.BREWING_STAND).name(FlyingUtils.L("inventories.main.peteditor")).make(), 24, event -> {
            fPlayer.getPetManager().getPet().getEditInv().open();
        }));

        fItems.add(new FlyingItem(new Item(Material.LEAD).name(FlyingUtils.L("inventories.main.sendtomission")).make(), 34, event -> {
            fPlayer.getPetManager().sendToMission();
        }));

        // Glass animations
        animation = new Animation(0, rainbowGlass.length - 1, Updater.Type.SECOND, anim -> {
            glassItem.setType(rainbowGlass[anim.getFrame()]);

            for (int i = 4; i <= 49; i += 9)
                getBukkitInventory().setItem(i, glassItem);
        });
        animation.setInfinite(true);
        animation.play();
    }


    @Override
    public void update(Updater.Type type) {
        if (Updater.Type.SECOND != type) return;
        animation.update(type);
    }
}
