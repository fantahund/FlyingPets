package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SignsUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by feeps on 18/06/2017.
 */
public class TakeCareInv extends EpicInventories {
    public TakeCareInv(Player player){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNameTakeCare), 27);
        this.create();
    }

    @Override
    public void create() {
        this.setItem(new ItemStack(Material.NAME_TAG), 11, MessageUtil.translate(Main.getI().getMsgCfg().invTakeCare.get("Name")), null);

        if(this.epicPetsPlayer.getPet() != null){
            this.setItem(new ItemStack(Material.BREAD), 13, MessageUtil.translate(Main.getI().getMsgCfg().invTakeCare.get("Foods")),
                    Arrays.asList(MessageUtil.translate(Main.getI().getMsgCfg().foodsStatus).replace("%bar%", this.epicPetsPlayer.getFoods().getBar())));
        }else{
            this.setItem(new ItemStack(Material.BREAD), 13, MessageUtil.translate(Main.getI().getMsgCfg().invTakeCare.get("Foods")), null);
        }

        this.setItem(new ItemStack(Material.BREWING_STAND_ITEM), 15, MessageUtil.translate(Main.getI().getMsgCfg().invTakeCare.get("Preferences")), null);

        this.setItem(new ItemStack(Material.ARROW), 22, MessageUtil.translate(Main.getI().getMsgCfg().invTakeCare.get("Back")), null);
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        switch(current.getType()){
            case NAME_TAG:
                if(EpicPermissions.RENAMEPET.hasPerm(player)){
                    player.closeInventory();
                    if(epicPetsPlayer.getPet() != null)
                        SignsUtil.setSign(player, "setName");
                }
                break;
            case BREWING_STAND_ITEM:
                if(EpicPermissions.OPENGUIPREFERENCES.hasPerm(player)){
                    new PreferencesInv(player).openInv();
                }
                break;
            case BREAD:
                if(EpicPermissions.OPENGUIFOODS.hasPerm(player)){
                    new FoodsInv(player).openInv();
                }
                break;
            case ARROW:
                new MainInv(player).openInv();
                break;
        }
    }
}
