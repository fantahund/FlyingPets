package be.feeps.epicpets.listener.tasks;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.events.updater.UpdateEvent;
import be.feeps.epicpets.events.updater.UpdateType;
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
                if(event.getType() == UpdateType.THREE_TICK){
                    epicPlayer.getPet().update();
                }
            }
        });
    }
}
