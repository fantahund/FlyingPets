package be.feeps.epicpets.particles.ParticlesList;

import be.feeps.epicpets.particles.EpicParticles;
import be.feeps.epicpets.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static be.feeps.epicpets.utils.CheckMoveUtil.isMoving;
import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;

/**
 * Created by feeps on 06/06/2017.
 */
public class ParticlesRing extends EpicParticles
{
    public ParticlesRing(Player player){
        super(EpicParticlesType.RING, player);
    }

    private Location loc = this.epicPetsPlayer.getPet().getPetLoc().add(0,0.6,0);
    private double t = 0, r = 0.5;
    private double x,y,z;
    public void update(){
        if(!isMoving(this.epicPetsPlayer.getPlayer())){
            t = t + Math.PI/16;
            x = r*cos(t);
            y = r*sin(t);
            z = r*sin(t);
            loc = this.epicPetsPlayer.getPet().getPetLoc().add(0+ x, 0.6 + y, 0 + z);
            ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,255,255), loc, 50);
            loc = this.epicPetsPlayer.getPet().getPetLoc().add(0 - x, 0.6 - y, 0 - z);
        }else{
            ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,255,255), this.epicPetsPlayer.getPet().getPetLoc(), 50);
        }

    }
}
