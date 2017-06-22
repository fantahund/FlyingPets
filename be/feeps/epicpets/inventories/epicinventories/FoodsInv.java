package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.foods.FoodsSkull;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.VaultUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 18/06/2017.
 */
public class FoodsInv extends EpicInventories {
    public FoodsInv(Player player){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNameFoods), 45);
        this.create();
    }

    @Override
    public void create() {
        int i = 0;
        for(FoodsSkull color : FoodsSkull.values()){
            color.createItem(i, this.getInv());
            i++;
        }

        this.setItem(new ItemStack(Material.ARROW), 40, MessageUtil.translate(Main.getI().getMsgCfg().invFoods.get("Back")), null);
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        switch (current.getType()){
            case ARROW:
                if(EpicPermissions.OPENGUITAKECARE.hasPerm(player)){
                    new TakeCareInv(this.player).openInv();
                }
                break;
            case SKULL_ITEM:
                if(this.epicPetsPlayer.getPet() != null){
                    for(FoodsSkull color : FoodsSkull.values()){
                        if (current.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.translate(color.getName()))){
                            if(this.epicPetsPlayer.getFoods().getFood() < 100){
                                if(VaultUtils.withdrawMoney(player, color.getPrice())){
                                    this.epicPetsPlayer.getFoods().addFoods(color.getAmount());
                                    this.player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().purchase));
                                    this.player.closeInventory();
                                }
                            }
                        }
                    }
                }
                break;
        }
    }
}
