package be.feeps.epicpets.particles.ParticlesList;

import be.feeps.epicpets.particles.EpicParticles;
import be.feeps.epicpets.utils.MathUtils;
import be.feeps.epicpets.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static be.feeps.epicpets.utils.CheckMoveUtil.isMoving;
import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;

/**
 * Created by feeps on 07/06/2017.
 */
public class ParticlesHelix extends EpicParticles {

    private float phi = 0;
    private int i = 0;

    public ParticlesHelix(Player player){
        super(player);
    }

    public void update(){
        if(!isMoving(this.epicPetsPlayer.getPlayer())){
            if(i == 2){
                phi = phi + MathUtils.PI/8;
                float x, y, z;
                Location loc = this.epicPetsPlayer.getPet().getPetLoc();
                for (float t = 0; t <= 2*Math.PI; t = t + MathUtils.PI/16){

                    x = 0.2f*(2*MathUtils.PI-t)*0.5f* MathUtils.cos(t + phi + 1*MathUtils.PI);
                    y = 0.3f*t;
                    z = 0.2f*(2*MathUtils.PI-t)*0.5f* MathUtils.sin(t + phi + 1*MathUtils.PI);
                    loc.add(x, y, z);

                    ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,0,0), loc, 100);
                    loc.subtract(x,y,z);
                }

                i = 0;
            }
            i++;
        }else{
            ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,0,0), this.epicPetsPlayer.getPet().getPetLoc(), 100);
        }


    }
}
