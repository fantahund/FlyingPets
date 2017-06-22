package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.animations.EpicAniFiles;
import be.feeps.epicpets.animations.EpicAnimations;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 18/06/2017.
 */
public class AnimationsInv extends EpicInventories {
    public AnimationsInv(Player player){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNameAnimations), 27);
        this.create();
    }

    @Override
    public void create() {
        for(EpicAniFiles epicAniFiles : EpicAniFiles.listAnimations){
            String itemType = null;
            int itemSlot = 0;
            String itemName = "";
            for(String line : epicAniFiles.getLines()){
                if(line.startsWith("TypeItem")){
                    itemType = (line.split(" ")[1]);
                }
                if (line.startsWith("NameItem")) {
                    itemName = (line.split(" ")[1]);
                }
                if (line.startsWith("SlotItem")) {
                    itemSlot =  Integer.parseInt((line.split(" ")[1]));
                    this.setItem(new ItemStack(Material.valueOf(itemType)), itemSlot, MessageUtil.translate(itemName), null);
                }
            }
        }

        this.setItem(new ItemStack(Material.ARROW), 22, MessageUtil.translate(Main.getI().getMsgCfg().invAnimations.get("Back")), null);

        if(this.epicPetsPlayer.getEpicAnim() != null){
            this.setItem(new ItemStack(Material.BARRIER), 26 , MessageUtil.translate(Main.getI().getMsgCfg().invAnimations.get("Reset")), null);
        }
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        if(current.getType() == Material.ARROW){
            new MainInv(player).openInv();
        }

        if(current.getType() == Material.BARRIER){
            if(this.epicPetsPlayer.getEpicAnim() != null){
                this.epicPetsPlayer.getEpicAnim().stop();
                this.cache.getData().set(player.getUniqueId().toString() + ".pet.currentanimation", "NULL");
                this.getInv().clear(26);
            }
        }
        if(current.getType() != Material.ARROW && current.getType() != Material.BARRIER){
            if(this.epicPetsPlayer.getEpicAnim() != null){
                this.epicPetsPlayer.getEpicAnim().stop();
            }
            if(this.epicPetsPlayer.getPet() != null) {

                for(EpicAniFiles epicAniFiles : EpicAniFiles.listAnimations){
                    String permission = "";
                    String name = "";
                    for(String line : epicAniFiles.getLines()){
                        if(line.startsWith("NameItem")){
                            name = (line.split(" ")[1]);
                        }
                        if(line.startsWith("Permission")){
                            permission = (line.split(" ")[1]);
                            if(current.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.translate(name))){
                                if(player.hasPermission(permission)){
                                    this.epicPetsPlayer.setEpicAnim(new EpicAnimations(player, epicAniFiles.getPath().getFileName().toString()));
                                    player.closeInventory();
                                }else{
                                    player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().noPerm));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
