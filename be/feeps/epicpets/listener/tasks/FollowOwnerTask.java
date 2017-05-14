package be.feeps.epicpets.listener.tasks;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.animations.ArmorStandAnimation;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by feeps on 3/04/17.
 */
public class FollowOwnerTask extends BukkitRunnable {
    public void run() {//runTaskTimerAsynchronously
        //JAVA 8 METHOD
        /*EpicPetsPlayer.getEpicPlayers().values().forEach((player) -> {
            if(player.getPet() != null) {
                player.getPet().update();
            }
        });*/


        for(EpicPetsPlayer epicPlayer : EpicPetsPlayer.getEpicPlayers().values()){
            if(epicPlayer.getPet() != null) {
                //=====================================
                epicPlayer.getPet().update();

                if(epicPlayer.getAnim() != null){
                    epicPlayer.getAnim().update();
                }
            }
        }

    }

}
