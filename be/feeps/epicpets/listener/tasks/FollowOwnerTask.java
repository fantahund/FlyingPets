package be.feeps.epicpets.listener.tasks;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.utils.CheckMoveUtil;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by feeps on 3/04/17.
 */
public class FollowOwnerTask extends BukkitRunnable {
    public void run() {
        for(EpicPetsPlayer epicPlayer : EpicPetsPlayer.getEpicPlayers().values()){
            if(epicPlayer.getPet() != null) {
                //=====================================
                epicPlayer.getPet().update();

                if(epicPlayer.getEpicAnim() != null){
                    epicPlayer.getEpicAnim().update();
                }
                if(epicPlayer.getEpicParticles() != null){
                    CheckMoveUtil.checkEntity(epicPlayer.getPlayer());
                    epicPlayer.getEpicParticles().update();
                }
            }
        }

    }

}
