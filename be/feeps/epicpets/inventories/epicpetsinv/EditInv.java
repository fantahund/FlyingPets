package be.feeps.epicpets.inventories.epicpetsinv;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.EpicInventory;
import be.feeps.epicpets.particles.ParticlesList.*;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SignsUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by feeps on 08/06/2017.
 */
public class EditInv extends EpicInventory {
    public EditInv(){
        super(null);
    }

    @Override
    public String name() {
        return MessageUtil.translate(Main.getI().getMsgCfg().invNameParticles);
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        this.setItem(new ItemStack(Material.NAME_TAG), 11, MessageUtil.translate(Main.getI().getMsgCfg().invEdit.get("Name")), null);
        this.setItem(new ItemStack(Material.BREWING_STAND_ITEM), 15, MessageUtil.translate(Main.getI().getMsgCfg().invEdit.get("Preferences")), null);

        this.setItem(new ItemStack(Material.ARROW), 22, MessageUtil.translate(Main.getI().getMsgCfg().invEdit.get("Back")), null);

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
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
                    new PreferencesInventory().open(player);
                }
                break;
            case ARROW:
                new MainInventory().open(player);
                break;
        }
    }
}
