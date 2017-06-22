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
 * Created by feeps on 06/06/2017.
 */
public class ParticlesRing extends EpicParticles
{
    public ParticlesRing(Player player){
        super(player);
    }

    private Location loc = this.epicPetsPlayer.getPet().getPetLoc().add(0,0.6,0);
    private float t = 0, r = 0.5f;
    private float x,y,z;
    public void update(){
        if(!isMoving(this.epicPetsPlayer.getPlayer())){
            t = t + MathUtils.PI/16;
            x = r* MathUtils.cos(t);
            y = r* MathUtils.sin(t);
            z = r* MathUtils.sin(t);
            loc = this.epicPetsPlayer.getPet().getPetLoc().add(0+ x, 0.6 + y, 0 + z);
            ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,255,255), loc, 100);
            loc = this.epicPetsPlayer.getPet().getPetLoc().add(0 - x, 0.6 - y, 0 - z);
        }else{
            ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(255,255,255), this.epicPetsPlayer.getPet().getPetLoc(), 100);
        }

    }
}
