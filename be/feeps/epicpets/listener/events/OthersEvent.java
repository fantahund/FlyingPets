package be.feeps.epicpets.listener.events;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.events.PlayerCreatePetEvent;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Created by feeps on 09/04/2017.
 */
public class OthersEvent implements Listener {
    protected CacheConfig cache = CacheConfig.getInstance();

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
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
                    if(input.length() > 16){
                        player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().msgLongNameError));
                    }else{
                        cache.getData().set(player.getUniqueId().toString() + ".pet.name", input);
                        cache.saveData();
                        cache.reloadData();
                        epicPetsPlayer.getPet().setName(input);
                    }
                } else {
                    player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().msgNoNameError));
                }
                event.setCancelled(true);
                b.removeMetadata("setName", Main.getI());
                b.setType(Material.AIR);
            } else if (b.hasMetadata("playerSkull")) {
                if (input.length() > 0) {
                    epicPetsPlayer.getPet().setPlayerHead(input);
                }
                event.setCancelled(true);
                b.removeMetadata("playerSkull", Main.getI());
                b.setType(Material.AIR);
            }
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

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf((Player)event.getWhoClicked());
        if (event.getInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (epicPetsPlayer.getEpicInv() == null) return;
        if (!event.getInventory().getName().contains(epicPetsPlayer.getEpicInv().getInv().getName())) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        event.setCancelled(true);
        epicPetsPlayer.getEpicInv().onClick(event.getCurrentItem(), event.getSlot());
        if(Main.getI().getMainCfg().enableSoundClickMenu){
            ((Player) event.getWhoClicked()).playSound((event.getWhoClicked()).getLocation(), Sounds.CLICK.bukkitSound(), 10f, 1f);
        }
    }

    @EventHandler
    public void closeEvent(InventoryCloseEvent event) {
        EpicPetsPlayer epicPetsPlayer = EpicPetsPlayer.instanceOf((Player) event.getPlayer());
        if(epicPetsPlayer.getEpicInv() != null) {
            if(epicPetsPlayer.getEpicInv().getInv().equals(event.getInventory())) {
                epicPetsPlayer.getEpicInv().clear();
            }
        }
    }
}
