package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.SkinsConfig;
import be.feeps.epicpets.inventories.ColorSkull;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.pets.DefaultPet;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SkinLoader;
import be.feeps.epicpets.utils.VaultUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by feeps on 18/06/2017.
 */
public class MainInv extends EpicInventories {
    public MainInv(Player player){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNameMain), 36);
        this.create();
    }


    @Override
    public void create() {
        if(this.epicPetsPlayer.getPet() != null){
            this.setItem(new ItemStack(Material.FERMENTED_SPIDER_EYE), 10, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("RemovePet")), null);
        }else{
            if(this.cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.isDead")){
                if(!VaultUtils.isEconNull()){
                    this.setItem(new ItemStack(Material.GHAST_TEAR), 10, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("ResurrectPet")),
                            Arrays.asList(MessageUtil.translate(Main.getI().getMsgCfg().price).replace("%price%", String.valueOf(Main.getI().getMainCfg().resurrectPrice))));
                }else{
                    this.setItem(new ItemStack(Material.GHAST_TEAR), 10, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("ResurrectPet")), null);
                }
            }else{
                this.setItem(new ItemStack(Material.NETHER_STAR), 10, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("SpawnPet")), null);
            }

        }

        this.setItem(new ItemStack(Material.BOOK), 20, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("TakeCare")), null );
        this.setItem(new ItemStack(Material.GLOWSTONE_DUST), 16, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Animations")), null);
        this.setItem(new ItemStack(Material.BLAZE_POWDER), 24, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Particles")), null);

        this.setItem(new ItemStack(Material.LEATHER_HELMET), 4, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Helmet")), null);
        this.setItem(new ItemStack(Material.LEATHER_CHESTPLATE), 13, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Chestplate")), null);
        this.setItem(new ItemStack(Material.LEATHER_LEGGINGS), 22, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Leggings")), null);
        this.setItem(new ItemStack(Material.LEATHER_BOOTS), 31, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Boots")), null);

        for (String key : SkinsConfig.getInstance().getData().getConfigurationSection("skins").getKeys(false)) {
            String name = SkinsConfig.getInstance().getData().getString("skins." + key + ".name");
            String texture = SkinsConfig.getInstance().getData().getString("skins." + key + ".texture");
            if(cache.getData().getString(player.getUniqueId().toString() + ".pet.head") != null){
                if(cache.getData().getString(player.getUniqueId().toString() + ".pet.head").equalsIgnoreCase(MessageUtil.translate(name))){
                    this.setItem(SkinLoader.getCustomSkull(texture), 4, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Helmet")), null);
                }
            }
        }

        for(ColorSkull color : ColorSkull.values()) {
            if (color.getName().equals(cache.getData().getString(player.getUniqueId().toString() + ".pet.helmet"))) {
                this.setItem(ItemsUtil.createDyeLeather(Material.LEATHER_HELMET, color.getName(), color.getColor()), 4, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Helmet")), null);
            }
            if (color.getName().equals(cache.getData().getString(player.getUniqueId().toString() + ".pet.chestplate"))) {
                this.setItem(ItemsUtil.createDyeLeather(Material.LEATHER_CHESTPLATE, color.getName(), color.getColor()), 13, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Chestplate")), null);
            }
            if (color.getName().equals(cache.getData().getString(player.getUniqueId().toString() + ".pet.leggings"))) {
                this.setItem(ItemsUtil.createDyeLeather(Material.LEATHER_LEGGINGS, color.getName(), color.getColor()), 22, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Leggings")), null);
            }
            if (color.getName().equals(cache.getData().getString(player.getUniqueId().toString() + ".pet.boots"))) {
                this.setItem(ItemsUtil.createDyeLeather(Material.LEATHER_BOOTS, color.getName(), color.getColor()), 31, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Boots")), null);
            }
        }
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        switch(current.getType()){
            case NETHER_STAR:
                if(EpicPermissions.SPAWNPET.hasPerm(player)){
                    new DefaultPet(player);
                    player.closeInventory();
                }
                break;
            case GHAST_TEAR:
                if(EpicPermissions.RESURRECT.hasPerm(player)){
                    if(VaultUtils.withdrawMoney(player, Main.getI().getMainCfg().resurrectPrice)){
                        this.cache.getData().set(this.player.getUniqueId().toString() + ".pet.foods", 100);
                        this.cache.getData().set(this.player.getUniqueId().toString() + ".pet.isDead", false);
                        this.cache.saveData();
                        this.cache.reloadData();
                        new DefaultPet(player);
                        player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().resurrectedPet));
                    }
                    player.closeInventory();
                }
                break;
            case FERMENTED_SPIDER_EYE:
                if(EpicPermissions.REMOVEPET.hasPerm(player)){
                    epicPetsPlayer.removePet();
                    player.closeInventory();
                }
                break;
            case GLOWSTONE_DUST:
                if(EpicPermissions.OPENGUIANIMATIONS.hasPerm(player)){
                    new AnimationsInv(player).openInv();
                }
                break;
            case BLAZE_POWDER:
                if(EpicPermissions.OPENGUIPARTICLES.hasPerm(player)){
                    new ParticlesInv(player).openInv();
                }
                break;
            case BOOK:
                if(EpicPermissions.OPENGUITAKECARE.hasPerm(player)){
                    new TakeCareInv(player).openInv();
                }
                break;
            case SKULL_ITEM:
                if(EpicPermissions.OPENGUIHELMET.hasPerm(player)){
                    new ColorsSelectorInv(player, Material.LEATHER_HELMET).openInv();
                }
                break;
            case LEATHER_HELMET:
                if(EpicPermissions.OPENGUIHELMET.hasPerm(player)){
                    new ColorsSelectorInv(player, Material.LEATHER_HELMET).openInv();
                }
                break;
            case LEATHER_CHESTPLATE:
                if(EpicPermissions.OPENGUICHESTPLATE.hasPerm(player)){
                    new ColorsSelectorInv(player, Material.LEATHER_CHESTPLATE).openInv();
                }
                break;
            case LEATHER_LEGGINGS:
                if(EpicPermissions.OPENGUILEGGINGS.hasPerm(player)){
                    new ColorsSelectorInv(player, Material.LEATHER_LEGGINGS).openInv();
                }
                break;
            case LEATHER_BOOTS:
                if(EpicPermissions.OPENGUIBOOTS.hasPerm(player)){
                    new ColorsSelectorInv(player, Material.LEATHER_BOOTS).openInv();
                }
                break;
        }
    }
}
