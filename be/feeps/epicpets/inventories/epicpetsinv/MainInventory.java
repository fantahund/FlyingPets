package be.feeps.epicpets.inventories.epicpetsinv;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.SkinsConfig;
import be.feeps.epicpets.inventories.ColorSkull;
import be.feeps.epicpets.inventories.EpicInventory;
import be.feeps.epicpets.pets.DefaultPet;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SignsUtil;
import be.feeps.epicpets.utils.SkinLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 4/04/17.
 */
public class MainInventory extends EpicInventory {

    public MainInventory(){
        super(null);
    }
    @Override
    public String name() {
        return MessageUtil.translate(Main.getI().getMsgCfg().invNameMain);
    }
    @Override
    public int size() {
        return 36;
    }
    @Override
    public void contents(Player player,Inventory inv) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        if(epicPetsPlayer.getPet() != null){
            this.setItem(new ItemStack(Material.FERMENTED_SPIDER_EYE), 10, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("RemovePet")), null);
        }else{
            this.setItem(new ItemStack(Material.NETHER_STAR), 10, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("SpawnPet")), null);
        }

        this.setItem(new ItemStack(Material.BOOK), 20, MessageUtil.translate(Main.getI().getMsgCfg().invMain.get("Edit")), null);
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
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        switch(current.getType()){
            case NETHER_STAR:
                if(EpicPermissions.SPAWNPET.hasPerm(player)){
                    new DefaultPet(player);
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
                    new AnimationsInv().open(player);
                }
                break;
            case BLAZE_POWDER:
                if(EpicPermissions.OPENGUIPARTICLES.hasPerm(player)){
                    new ParticlesInv().open(player);
                }
                break;
            case BOOK:
                if(EpicPermissions.OPENGUIEDIT.hasPerm(player)){
                    new EditInv().open(player);
                }
                break;
            case SKULL_ITEM:
                if(EpicPermissions.OPENGUIHELMET.hasPerm(player)){
                    new ColorsSelectorInv( Material.LEATHER_HELMET).open(player);
                }
                break;
            case LEATHER_HELMET:
                if(EpicPermissions.OPENGUIHELMET.hasPerm(player)){
                    new ColorsSelectorInv(Material.LEATHER_HELMET).open(player);
                }
                break;
            case LEATHER_CHESTPLATE:
                if(EpicPermissions.OPENGUICHESTPLATE.hasPerm(player)){
                    new ColorsSelectorInv(Material.LEATHER_CHESTPLATE).open(player);
                }
                break;
            case LEATHER_LEGGINGS:
                if(EpicPermissions.OPENGUILEGGINGS.hasPerm(player)){
                    new ColorsSelectorInv(Material.LEATHER_LEGGINGS).open(player);
                }
                break;
            case LEATHER_BOOTS:
                if(EpicPermissions.OPENGUIBOOTS.hasPerm(player)){
                    new ColorsSelectorInv(Material.LEATHER_BOOTS).open(player);
                }
                break;
        }
    }
}
