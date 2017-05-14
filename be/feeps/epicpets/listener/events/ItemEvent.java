package be.feeps.epicpets.listener.events;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.epicpetsinv.MainInventory;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by feeps on 3/04/17.
 */
public class ItemEvent implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void rightClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        /**@getItemInMainHand ne fonctionne pas dans la version 1.8 de minecraft*/

        if ((player.getInventory().getItemInHand().getType() == Material.BONE) &&
                ((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK)) &&
                (player.getInventory().getItemInHand().hasItemMeta()) &&
                (player.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(MessageUtil.translate(Main.getI().getMainCfg().itemOnJoin.get("name"))))) {
            /*==============Méthode : Ouvrir le GUI 'Editeur de pets'==============*/
            if(EpicPermissions.OPENGUIMAIN.hasPerm(player)){
                new MainInventory().open(player);
            }
        }
    }
}
