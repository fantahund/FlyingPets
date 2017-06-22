package be.feeps.epicpets.listener.tasks;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.particles.ParticlesList.*;
import be.feeps.epicpets.events.updater.UpdateEvent;
import be.feeps.epicpets.events.updater.UpdateType;
import be.feeps.epicpets.utils.CheckMoveUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by feeps on 17/06/2017.
 */
public class ParticlesTask implements Listener {

    @EventHandler
    public void checkMove(UpdateEvent event){
        EpicPetsPlayer.getEpicPlayers().values().forEach((epicPlayer) -> {
            if(epicPlayer.getEpicParticles() != null){
                if (event.getType() == UpdateType.TICK) {
                    CheckMoveUtil.checkEntity(epicPlayer.getPlayer());
                }
            }
        });
    }

    @EventHandler
    public void updateParticles(UpdateEvent event){
        EpicPetsPlayer.getEpicPlayers().values().forEach((epicPlayer) -> {
            if(epicPlayer.getEpicParticles() != null){
                if(epicPlayer.getEpicParticles() instanceof ParticlesFrostLord) {
                    if (event.getType() == UpdateType.TWO_TICK) {
                        epicPlayer.getEpicParticles().update();
                    }
                }
                if (epicPlayer.getEpicParticles() instanceof ParticlesLove) {
                    if (event.getType() == UpdateType.THREE_TICK) {
                        epicPlayer.getEpicParticles().update();
                    }
                }
                if(epicPlayer.getEpicParticles() instanceof ParticlesRing || epicPlayer.getEpicParticles() instanceof ParticlesSparks || epicPlayer.getEpicParticles() instanceof ParticlesHelix) {
                    if (event.getType() == UpdateType.TICK) {
                        epicPlayer.getEpicParticles().update();
                    }
                }
            }
        });
    }
}