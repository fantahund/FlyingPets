package be.feeps.epicpets.listener.events;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by feeps on 3/04/17.
 */
public class JoinQuitEvent implements Listener {

    @EventHandler
    public void JoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        //Donne le selecteur lorsque qu'on rejoint le serveur
        if(Boolean.parseBoolean(Main.getI().getMainCfg().itemOnJoin.get("enable")) == true){
            ItemsUtil.add(new ItemStack(Material.getMaterial(Main.getI().getMainCfg().itemOnJoin.get("material"))), player.getInventory(), Integer.parseInt(Main.getI().getMainCfg().itemOnJoin.get("slot")),
                    MessageUtil.translate(Main.getI().getMainCfg().itemOnJoin.get("name")), Arrays.asList(new String[] { "" }));
        }
    }

     @EventHandler
     public void QuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        if(epicPetsPlayer.getPet() != null){
            epicPetsPlayer.getPet().remove();
        }
     }


    public ItemStack craftItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(name);
        item.setItemMeta(itemmeta);
        return item;
    }
}
