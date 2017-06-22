package be.feeps.epicpets.particles.ParticlesList;

import be.feeps.epicpets.particles.EpicParticles;
import be.feeps.epicpets.utils.MathUtils;
import be.feeps.epicpets.utils.ParticleEffect;
import org.bukkit.entity.Player;

/**
 * Created by feeps on 08/06/2017.
 */
public class ParticlesLove extends EpicParticles{

    public ParticlesLove(Player player){
        super(player);
    }

    private int i = 0;

    public void update(){
        if(i == 20){
            for (int i = 0; i < 3; i++) {
                ParticleEffect.HEART.display(0,0,0,0, 1, this.epicPetsPlayer.getPet().getPetLoc().clone().add(MathUtils.randomDouble(-0.5, 0.5),
                                                                                                                                                    MathUtils.randomDouble(-0.2 , 0.2),
                                                                                                                                                    MathUtils.randomDouble(-0.5 , 0.5)), 100);
            }
            i = 0;
        }
        i++;
    }
}
