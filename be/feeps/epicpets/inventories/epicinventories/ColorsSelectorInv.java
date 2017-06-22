package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.SkinsConfig;
import be.feeps.epicpets.inventories.ColorSkull;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SkinLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 18/06/2017.
 */
public class ColorsSelectorInv extends EpicInventories {
    private Material currentItem;

    public ColorsSelectorInv(Player player, Material currentItem){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNameColorSelector), 45);
        this.currentItem = currentItem;
        this.create();
    }

    @Override
    public void create() {
        this.setItem(new ItemStack(this.currentItem), 4,
                MessageUtil.translate(Main.getI().getMsgCfg().invColorSelector.get("CurrentItem")), null);

        this.setItem(new ItemStack(Material.ARROW), 40, MessageUtil.translate(Main.getI().getMsgCfg().invColorSelector.get("Back")), null);
        this.setItem(new ItemStack(Material.BARRIER), 44, MessageUtil.translate(Main.getI().getMsgCfg().invColorSelector.get("Reset")), null);

        if(this.currentItem.equals(Material.LEATHER_HELMET)){
            for (String key : SkinsConfig.getInstance().getData().getConfigurationSection("skins").getKeys(false)) {
                String name = SkinsConfig.getInstance().getData().getString("skins." + key + ".name");
                String texture = SkinsConfig.getInstance().getData().getString("skins." + key + ".texture");
                if(cache.getData().getString(player.getUniqueId().toString() + ".pet.head") != null){
                    if(cache.getData().getString(player.getUniqueId().toString() + ".pet.head").equalsIgnoreCase(MessageUtil.translate(name))){
                        this.setItem(SkinLoader.getCustomSkull(texture), 36, MessageUtil.translate(Main.getI().getMsgCfg().invColorSelector.get("Skins")), null);
                    }
                }
            }
        }


        int i = 0;
        int slot[] = {19,20,21,22,23,24,25,29,30,32,33};
        for(ColorSkull color : ColorSkull.values()){
            color.createItem(slot[i], this.getInv());
            i++;

        }
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        switch(current.getType()){
            case ARROW:
                new MainInv(player).openInv();
                break;
            case BARRIER:
                if (this.currentItem == Material.LEATHER_HELMET) {
                    cache.getData().set(player.getUniqueId().toString() + ".pet.helmet", "NULL");
                    if(epicPetsPlayer.getPet() != null){
                        epicPetsPlayer.getPet().getArmorStand().getEquipment().setHelmet(new ItemStack(Material.AIR));
                    }
                }
                if (this.currentItem == Material.LEATHER_CHESTPLATE) {
                    cache.getData().set(player.getUniqueId().toString() + ".pet.chestplate", "NULL");
                    if(epicPetsPlayer.getPet() != null){
                        epicPetsPlayer.getPet().getArmorStand().getEquipment().setChestplate(new ItemStack(Material.AIR));
                    }
                }
                if (this.currentItem == Material.LEATHER_LEGGINGS) {
                    cache.getData().set(player.getUniqueId().toString() + ".pet.leggings","NULL");
                    if(epicPetsPlayer.getPet() != null){
                        epicPetsPlayer.getPet().getArmorStand().getEquipment().setLeggings(new ItemStack(Material.AIR));
                    }
                }
                if (this.currentItem == Material.LEATHER_BOOTS) {
                    cache.getData().set(player.getUniqueId().toString() + ".pet.boots", "NULL");
                    if (epicPetsPlayer.getPet() != null) {
                        epicPetsPlayer.getPet().getArmorStand().getEquipment().setBoots(new ItemStack(Material.AIR));
                    }
                }
                cache.saveData();
                cache.reloadData();
                if (epicPetsPlayer.getPet() != null) {
                    epicPetsPlayer.getPet().updateInfo();
                }

                break;
            case SKULL_ITEM:
                if(current.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.translate(Main.getI().getMsgCfg().invColorSelector.get("Skins")))){
                    new SkullInv(player).openInv();
                }

                if(epicPetsPlayer.getPet() != null){
                    for(ColorSkull color : ColorSkull.values()){
                        if(color.getName().equalsIgnoreCase(current.getItemMeta().getDisplayName())){
                            if (this.currentItem == Material.LEATHER_HELMET) {
                                cache.getData().set(player.getUniqueId().toString() + ".pet.helmet", color.getName());
                            }
                            if (this.currentItem == Material.LEATHER_CHESTPLATE) {
                                cache.getData().set(player.getUniqueId().toString() + ".pet.chestplate", color.getName());
                            }
                            if (this.currentItem == Material.LEATHER_LEGGINGS) {
                                cache.getData().set(player.getUniqueId().toString() + ".pet.leggings", color.getName());
                            }
                            if (this.currentItem == Material.LEATHER_BOOTS) {
                                cache.getData().set(player.getUniqueId().toString() + ".pet.boots", color.getName());
                            }
                            cache.saveData();
                            cache.reloadData();
                            epicPetsPlayer.getPet().updateInfo();
                        }
                    }
                }
                break;
        }
    }
}
