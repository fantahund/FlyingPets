package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.skins.EpicSkins;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SignsUtil;
import be.feeps.epicpets.utils.SkinLoader;
import be.feeps.epicpets.utils.VaultUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by feeps on 18/06/2017.
 */
public class SkullInv extends EpicInventories {
    public SkullInv(Player player){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNameSkins), 45);
        this.create();
    }

    @Override
    public void create() {
        EpicSkins.skinsList.forEach((skins) -> {
            if(VaultUtils.hasPerms(player, skins.getPermission())){
                this.setItem(SkinLoader.getCustomSkull(skins.getTexture()), skins.getSlot() , skins.getName(), Arrays.asList(MessageUtil.translate(Main.getI().getMsgCfg().hasPermissionLore)));
            }else{
                if(!VaultUtils.isPermsNull()){
                    this.setItem(SkinLoader.getCustomSkull(skins.getTexture()), skins.getSlot() , skins.getName(),
                            Arrays.asList(MessageUtil.translate(Main.getI().getMsgCfg().noPermissionLore),
                                    MessageUtil.translate(Main.getI().getMsgCfg().price).replace("%price%", String.valueOf(skins.getPrice()))));
                }else{
                    this.setItem(SkinLoader.getCustomSkull(skins.getTexture()), skins.getSlot() , skins.getName(), Arrays.asList(MessageUtil.translate(Main.getI().getMsgCfg().noPermissionLore)));
                }
            }

        });

        this.setItem(new ItemStack(Material.ARROW), 40, MessageUtil.translate(Main.getI().getMsgCfg().invSkins.get("Back")), null);
        this.setItem(new ItemStack(Material.SIGN), 44 , MessageUtil.translate(Main.getI().getMsgCfg().invSkins.get("PlayerHead")), null);
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        switch(current.getType()){
            case ARROW:
                new MainInv(player).openInv();
                break;
            case SIGN:
                player.closeInventory();
                if(epicPetsPlayer.getPet() != null)
                    SignsUtil.setSign(player, "playerSkull");
                break;
            case SKULL_ITEM:
                if(epicPetsPlayer.getPet() != null){
                    EpicSkins.skinsList.forEach((skins) -> {
                        if(VaultUtils.hasPerms(player, skins.getPermission())){
                            if (current.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.translate(skins.getName()))) {
                                this.cache.getData().set(player.getUniqueId().toString() + ".pet.head", MessageUtil.translate(skins.getName()));
                            }
                        }else{
                            if (current.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.translate(skins.getName()))) {
                                 if(VaultUtils.withdrawMoney(player, skins.getPrice())){
                                     VaultUtils.addPerms(player, skins.getPermission());
                                     this.cache.getData().set(player.getUniqueId().toString() + ".pet.head", MessageUtil.translate(skins.getName()));
                                     player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().purchase));
                                     player.closeInventory();
                                 }
                            }
                        }
                    });
                    epicPetsPlayer.getPet().updateInfo();
                    cache.saveData();
                    cache.reloadData();
                }
                break;
        }
    }
}
