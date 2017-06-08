package be.feeps.epicpets.inventories.epicpetsinv;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.SkinsConfig;
import be.feeps.epicpets.inventories.EpicInventory;
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
public class SkullInventory extends EpicInventory {
    //Pour get le joueur this.player
    private SkinsConfig textures = SkinsConfig.getInstance();

    public SkullInventory(){
        super(null);
    }

    @Override
    public String name() {
        return Main.getI().getMsgCfg().invNameSkins;
    }

    @Override
    public int size() {
        return 45;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        this.setItem(new ItemStack(Material.ARROW), 40, MessageUtil.translate(Main.getI().getMsgCfg().invSkins.get("Back")), null);
        this.setItem(new ItemStack(Material.SIGN), 44 , MessageUtil.translate(Main.getI().getMsgCfg().invSkins.get("PlayerHead")), null);

        for (String key : SkinsConfig.getInstance().getData().getConfigurationSection("skins").getKeys(false)) {
            //ConfigurationSection skinSection = SkinsConfig.getInstance().getConfig().getConfigurationSection("skins." + key);
            String name = SkinsConfig.getInstance().getData().getString("skins." + key + ".name");
            String texture = SkinsConfig.getInstance().getData().getString("skins." + key + ".texture");
            String slot = SkinsConfig.getInstance().getData().getString("skins." + key + ".slot");
            this.setItem(SkinLoader.getCustomSkull(texture), Integer.parseInt(slot) , MessageUtil.translate(name), null);
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        switch(current.getType()){
            case ARROW:
                new MainInventory().open(player);
                break;
            case SIGN:
                player.closeInventory();
                if(epicPetsPlayer.getPet() != null)
                    SignsUtil.setSign(player, "playerSkull");
                break;
            case SKULL_ITEM:
                if(epicPetsPlayer.getPet() != null){
                    for (String key : textures.getData().getConfigurationSection("skins").getKeys(false)) {
                        String name = textures.getData().getString("skins." + key + ".name");
                        String permission = textures.getData().getString("skins." + key + ".permission");

                        if(player.hasPermission(permission)) {
                            if (current.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.translate(name))) {
                                cache.getData().set(player.getUniqueId().toString() + ".pet.helmet", "NULL");
                                cache.getData().set(player.getUniqueId().toString() + ".pet.head", MessageUtil.translate(name));
                            }
                        }
                    }
                    epicPetsPlayer.getPet().updateInfo();
                    cache.saveData();
                    cache.reloadData();
                }
                break;
        }
    }
}