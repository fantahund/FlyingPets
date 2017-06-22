package be.feeps.epicpets.listener.events;

import be.feeps.epicpets.EpicPermissions;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.epicinventories.MainInv;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        Bukkit.getScheduler().runTaskLater(Main.getI(), () -> {
            if ((event.getPlayer().getInventory().getItemInHand().getType() == Material.BONE) &&
                    ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) &&
                    (event.getPlayer().getInventory().getItemInHand().hasItemMeta()) &&
                    (event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().contains(MessageUtil.translate(Main.getI().getMainCfg().itemOnJoin.get("name"))))) {

                if(EpicPermissions.OPENGUIMAIN.hasPerm(event.getPlayer())){
                    new MainInv(event.getPlayer()).openInv();
                }

            }
        }, 2);
    }
}
