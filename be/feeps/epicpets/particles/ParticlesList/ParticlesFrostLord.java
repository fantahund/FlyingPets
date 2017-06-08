package be.feeps.epicpets.particles.ParticlesList;

import be.feeps.epicpets.particles.EpicParticles;
import be.feeps.epicpets.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static be.feeps.epicpets.utils.CheckMoveUtil.isMoving;

/**
 * Created by feeps on 08/06/2017.
 */
public class ParticlesFrostLord extends EpicParticles {

    private int step = 0;
    private float stepY = 0;
    private float radius = 1.0f;
    private int i = 0;

    public ParticlesFrostLord(Player player){
        super(EpicParticlesType.FROSTLORD,player);
    }

    public void update(){
        if(i == 2){
            if(!isMoving(this.epicPetsPlayer.getPlayer())){
                for (int i = 0; i < 6; i++) {
                    Location location = this.epicPetsPlayer.getPet().getPetLoc();
                    double inc = (2 * Math.PI) / 100;
                    double angle = step * inc + stepY + i;
                    Vector v = new Vector();
                    v.setX(Math.cos(angle) * radius);
                    v.setZ(Math.sin(angle) * radius);
                    ParticleEffect.SNOW_SHOVEL.display(0,0,0,0,1, location.add(v).add(0, stepY, 0),50);
                    location.subtract(v).subtract(0, stepY, 0);
                    if (stepY < 3) {
                        radius -= 0.022;
                        stepY += 0.045;
                    } else {
                        stepY = 0;
                        step = 0;
                        radius = 1.0f;
                        ParticleEffect.SNOW_SHOVEL.display(0,0,0, 0.3f,32, location.clone().add(0, 3, 0),50);
                    }
                }
            }else{
                ParticleEffect.SNOW_SHOVEL.display(0,0,0, 0.1f,2, this.epicPetsPlayer.getPet().getPetLoc().clone().add(0,0.5,0),50);
            }
            i = 0;
        }
        i++;
    }
}
