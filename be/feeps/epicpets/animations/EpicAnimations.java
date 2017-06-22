package be.feeps.epicpets.animations;

import be.feeps.epicpets.EpicPetsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import java.io.File;

/**
 * Created by feeps on 16/04/2017.
 */
public class EpicAnimations {
    private Player player;
    private EpicPetsPlayer epicPetsPlayer;
    private ArmorStandAnimation animation;

    public EpicAnimations(Player player, String nameFile){
        this.player = player;
        this.epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        this.epicPetsPlayer.setEpicAnim(this);

        animation = new ArmorStandAnimation(new File("plugins/EpicPets/Animations", nameFile), epicPetsPlayer.getPet().getArmorStand());

    }

    public void update(){
        this.animation.update();
        this.animation.setStartLocation(this.epicPetsPlayer.getPet().getPetLoc());
    }

    public void stop(){
        this.animation.stop();
        this.animation.remove();
        this.animation.removeCurrentCache();
        this.animation = null;
        this.epicPetsPlayer.setEpicAnim(null);
        this.epicPetsPlayer.getPet().getArmorStand().getEquipment().clear();
        this.epicPetsPlayer.getPet().getArmorStand().setLeftArmPose(new EulerAngle(Math.toRadians(0f),0f,0f));
        this.epicPetsPlayer.getPet().getArmorStand().setRightArmPose(new EulerAngle(Math.toRadians(0f),0f,0f));
        this.epicPetsPlayer.getPet().getArmorStand().setLeftLegPose(new EulerAngle(Math.toRadians(0f),0f,0f));
        this.epicPetsPlayer.getPet().getArmorStand().setRightLegPose(new EulerAngle(Math.toRadians(0f),0f,0f));
        this.epicPetsPlayer.getPet().getArmorStand().setHeadPose(new EulerAngle(Math.toRadians(0f),0f,0f));
        this.epicPetsPlayer.getPet().getArmorStand().setBodyPose(new EulerAngle(Math.toRadians(0f),0f,0f));
        this.epicPetsPlayer.getPet().updateInfo();

    }

    public ArmorStandAnimation getAnim(){
        return this.animation;
    }


}
