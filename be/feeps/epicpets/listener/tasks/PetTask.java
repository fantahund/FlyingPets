package be.feeps.epicpets.listener.tasks;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.events.updater.UpdateEvent;
import be.feeps.epicpets.pets.DefaultPet;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by feeps on 17/06/2017.
 */
public class PetTask implements Listener {

    @EventHandler
    public void followOwner(UpdateEvent event){
        EpicPetsPlayer.getEpicPlayers().values().forEach((epicPlayer) -> {
            if (epicPlayer.getPet() != null) {
                try{
                    epicPlayer.getPet().update();
                }catch (IllegalStateException e){
                    epicPlayer.getPet().remove();
                    Bukkit.getScheduler().runTaskLater(Main.getI(), () -> {
                        new DefaultPet(epicPlayer.getPlayer());
                    }, 5);
                }
            }
        });
    }
}
