package be.feeps.epicpets.listener.tasks;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.events.updater.UpdateEvent;
import be.feeps.epicpets.events.updater.UpdateType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by feeps on 17/06/2017.
 */
public class AnimationsTask implements Listener {

    @EventHandler
    public void updateAnimations(UpdateEvent event){
        EpicPetsPlayer.getEpicPlayers().values().forEach((epicPlayer) -> {
            if(epicPlayer.getEpicAnim() != null){
                if(event.getType() == UpdateType.TICK){
                    epicPlayer.getEpicAnim().update();
                }
            }
        });
    }
}