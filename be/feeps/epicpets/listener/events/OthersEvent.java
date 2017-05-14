package be.feeps.epicpets.listener.events;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Created by feeps on 09/04/2017.
 */
public class OthersEvent implements Listener {
    protected CacheConfig cache = CacheConfig.getInstance();

    @EventHandler
    public void onSignChange(final SignChangeEvent event) {
        final Block b = event.getBlock();
        Player player = event.getPlayer();
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        if (epicPetsPlayer.getPet() != null) {
            String input = "";
            for (String line : event.getLines()) {
                if (line != null && line.length() > 0) {
                    input += ChatColor.translateAlternateColorCodes('&', line);
                }
            }
            if (b.hasMetadata("setName")) {
                if (input.length() > 0) {
                    /**@input.length > 16 sert a déterminé si le nom du pet est plus grand 16*/
                    if(input.length() > 16){
                        //Mettre le message dans la configuration message.yml
                        player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().msgLongNameError));
                    }else{
                        cache.getData().set(player.getUniqueId().toString() + ".pet.name", input);
                        cache.saveData();
                        cache.reloadData();
                        epicPetsPlayer.getPet().setName(input);
                    }
                } else {
                    player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().msgNoNameError));
                }
            }
            if (b.hasMetadata("playerSkull")) {
                if (input.length() > 0) {
                    /**@input.length > 16 sert a déterminé si le nom du pet est plus grand 16*/
                    epicPetsPlayer.getPet().setPlayerHead(input);

                }
            }
            event.setCancelled(true);
            b.removeMetadata("playerSkull", Main.getI());
            b.removeMetadata("setName", Main.getI());
            b.setType(Material.AIR);

        }
    }
    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        if(epicPetsPlayer.getPet() != null){
            if(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.showName")){
                if(cache.getData().getBoolean(player.getUniqueId().toString() + ".pet.sneakProtect")){
                    if (player.isSneaking()) {
                        epicPetsPlayer.getPet().getName().setCustomNameVisible(true);
                    }else{
                        epicPetsPlayer.getPet().getName().setCustomNameVisible(false);
                    }
                }
            }
        }
    }
}
