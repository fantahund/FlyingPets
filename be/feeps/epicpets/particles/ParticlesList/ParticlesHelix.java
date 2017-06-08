package be.feeps.epicpets.particles.ParticlesList;

import be.feeps.epicpets.particles.EpicParticles;
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

    private double phi = 0;
    private int i = 0;

    public ParticlesHelix(Player player){
        super(EpicParticlesType.HELIX,player);
    }

    public void update(){
        if(!isMoving(this.epicPetsPlayer.getPlayer())){
            if(i == 2){
                phi = phi + Math.PI/8;
                double x, y, z;
                Location loc = this.epicPetsPlayer.getPet().getPetLoc();
                for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){

                    x = 0.2*(2*Math.PI-t)*0.5*cos(t + phi + 1*Math.PI);
                    y = 0.3*t;
                    z = 0.2*(2*Math.PI-t)*0.5*sin(t + phi + 1*Math.PI);
                    loc.add(x, y, z);

                    ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,0,0), loc, 50);
                    loc.subtract(x,y,z);
                }

                i = 0;
            }
            i++;
        }else{
            ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,0,0), this.epicPetsPlayer.getPet().getPetLoc(), 50);
        }


    }
}
