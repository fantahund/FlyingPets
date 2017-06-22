package be.feeps.epicpets.listener.tasks;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.events.updater.UpdateEvent;
import be.feeps.epicpets.events.updater.UpdateType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by feeps on 19/06/2017.
 */
public class FoodsTask implements Listener {

    @EventHandler
    public void updateFoods(UpdateEvent event){
        EpicPetsPlayer.getEpicPlayers().values().forEach((epicPlayer) -> {
            if(epicPlayer.getPet() != null){
                if(epicPlayer.getFoods() != null){
                    if(event.getType() == UpdateType.HALF_MINUTE){
                        epicPlayer.getFoods().update();
                    }
                }
            }
        });
    }
}
