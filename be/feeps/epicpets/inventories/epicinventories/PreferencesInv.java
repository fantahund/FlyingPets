package be.feeps.epicpets.inventories.epicinventories;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by feeps on 18/06/2017.
 */
public class PreferencesInv extends EpicInventories{
    public PreferencesInv(Player player){
        super(player, MessageUtil.translate(Main.getI().getMsgCfg().invNamePreferences), 36);
        this.create();
    }

    @Override
    public void create() {
        createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.setSmall"), 20, Material.MUSHROOM_SOUP, "SizePet");
        createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.showName"), 21, Material.NAME_TAG, "ShowName");
        createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.setVisible"), 22, Material.POTION, "ShowArmorStand");
        createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.showArms"), 23, Material.STICK, "ShowArms");
        createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.sneakProtect"), 24, Material.QUARTZ, "SneakProtect");

        this.setItem(new ItemStack(Material.ARROW), 31, MessageUtil.translate(Main.getI().getMsgCfg().invPreferences.get("Back")), null);
    }

    @Override
    public void onClick(ItemStack current, int slot) {
        if (current.getType() == Material.ARROW) {
            new TakeCareInv(player).openInv();
        }
        switch(slot){
            case 20:
                if(EpicPermissions.PREFERENCESSETSMALL.hasPerm(player)){
                    CacheConfig.getInstance().doOpposite(player, "setSmall");
                    createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.setSmall"), 20, Material.MUSHROOM_SOUP, "SizePet");
                }
                break;
            case 21:
                if(EpicPermissions.PREFERENCESSHOWNAME.hasPerm(player)){
                    CacheConfig.getInstance().doOpposite(player, "showName");
                    createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.showName"), 21, Material.NAME_TAG, "ShowName");
                }
                break;
            case 22:
                if(EpicPermissions.PREFERENCESSETVISIBLE.hasPerm(player)){
                    CacheConfig.getInstance().doOpposite(player, "setVisible");
                    createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.setVisible"), 22, Material.POTION, "ShowArmorStand");
                }
                break;
            case 23:
                if(EpicPermissions.PREFERENCESSHOWARMS.hasPerm(player)){
                    CacheConfig.getInstance().doOpposite(player, "showArms");
                    createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.showArms"), 23, Material.STICK, "ShowArms");
                }
                break;
            case 24:
                if(EpicPermissions.PREFERENCESSNEAKPROTECT.hasPerm(player)){
                    CacheConfig.getInstance().doOpposite(player, "sneakProtect");
                    createItem(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.sneakProtect"), 24, Material.QUARTZ, "SneakProtect");
                }
                break;
        }
        if(epicPetsPlayer.getPet() != null){
            epicPetsPlayer.getPet().updateInfo();
        }
    }

    public void createItem(boolean stat, int slot, Material mat, String name){
        ItemsUtil.add(new ItemStack(mat), this.getInv(), slot - 9, MessageUtil.translate(Main.getI().getMsgCfg().invPreferences.get(name)), Arrays.asList(new String[] { "" }));

        if(stat){
            this.setItem(new ItemStack(Material.INK_SACK, 1, Input.ENABLE.getData()), slot, MessageUtil.translate(Input.ENABLE.getName()), Arrays.asList(new String[] { MessageUtil.translate(Input.ENABLE.getLore()) }));
        }else{
            this.setItem(new ItemStack(Material.INK_SACK, 1, Input.DISABLE.getData()), slot, MessageUtil.translate(Input.DISABLE.getName()), Arrays.asList(new String[] { MessageUtil.translate(Input.DISABLE.getLore()) }));
        }
    }

    enum Input{
        ENABLE(Main.getI().getMsgCfg().enabledItem,Main.getI().getMsgCfg().disabledItemLore, (byte) 10),
        DISABLE(Main.getI().getMsgCfg().disabledItem,Main.getI().getMsgCfg().enabledItemLore, (byte) 8);

        private String name,lore;
        private short data;
        Input(String pName, String pLore, byte pData){
            this.name = pName;
            this.lore = pLore;
            this.data = pData;
        }
        public String getName(){
            return this.name;
        }
        public String getLore(){
            return this.lore;
        }
        public short getData(){
            return this.data;
        }
    }

}
