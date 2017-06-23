package be.feeps.epicpets.listener.events;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by feeps on 3/04/17.
 */
public class JoinQuitEvent implements Listener {

    @EventHandler
    public void JoinEvent(PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskLater(Main.getI(), () -> {
            Player player = event.getPlayer();

            if(Boolean.parseBoolean(Main.getI().getMainCfg().itemOnJoin.get("enable")) == true){
                ItemsUtil.add(new ItemStack(Material.getMaterial(Main.getI().getMainCfg().itemOnJoin.get("material"))), player.getInventory(), Integer.parseInt(Main.getI().getMainCfg().itemOnJoin.get("slot")),
                        MessageUtil.translate(Main.getI().getMainCfg().itemOnJoin.get("name")), Arrays.asList(new String[] { "" }));
            }
        }, 3);
    }

     @EventHandler
     public void QuitEvent(PlayerQuitEvent event){
         Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getI(), () -> {
             Player player = event.getPlayer();
             EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
             if(epicPetsPlayer.getPet() != null){
                 epicPetsPlayer.getPet().remove();
             }
         }, 5);
     }
}
