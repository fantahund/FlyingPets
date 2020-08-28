package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.ArmorStandPacket;
import be.feeps.flying.objects.Animation;
import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class DragonPet extends ShapePet {
    private static final Vector dragonOffset = new Vector(-0.8, 1.7, 0.8);
    private static final Vector nameOffset = new Vector(0,1.25,0);

    private static final ItemStack witherHead = new Item(Material.DRAGON_HEAD).make();
    private static final ItemStack blackHead = new Item(Material.PLAYER_HEAD).skullBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UxZTVjODFmYjlkNjRiNzQ5OTZmZDE3MTQ4OWRlYWRiYjhjYjc3MmQ1MmNmOGI1NjZlM2JjMTAyMzAxMDQ0In19fQ==").make();
    private static final ItemStack bannerItem = new Item(Material.BLACK_BANNER).make();
    private static final ItemStack concreteItem = new Item(Material.BLACK_CONCRETE).make();
    private static final ItemStack fireItem = new Item(Material.FIRE_CHARGE).make();

    // Dragon
    private final Animation wingsAnimation;

    // Fireball
    private final Animation fireAnimation;
    private ArmorStandPacket fireArmorstand;
    private Location fireLocation;
    private Vector fireDirection;

    public DragonPet(Player player){
        super(player, PetType.DRAGON, nameOffset, dragonOffset);

        Struct head = shape.add(new Vector());
        head.equip(5, witherHead);
        head.getArmorstand().setSmall(true);

        for (int i = 0; i < 3; i++){
            Struct body = shape.add(new Vector(-0.15 - 0.1 * i, -0.3 - 0.2 * i, 0));
            body.equip(5, blackHead);
            body.getArmorstand().setSmall(true);
            body.getArmorstand().setHeadPose(new EulerAngle(25 * (i == 2 ? -1 : 1), 0,0));
        }

        Struct leftWing = shape.add(new Vector(-0.35, -0.25, 0.4));
        leftWing.equip(5, bannerItem);
        leftWing.getArmorstand().setSmall(true);

        Struct rightWing = shape.add(new Vector(-0.35, -0.25, -0.4));
        rightWing.equip(5, bannerItem);
        rightWing.getArmorstand().setSmall(true);

        for(int i = 0; i < 2; i++){
            Struct foot = shape.add(new Vector(i * - 0.45, 0.2 - i * 0.65, 0));
            foot.getArmorstand().setSmall(true);
            foot.getArmorstand().setLeftArmPose(new EulerAngle(0,0,6));
            foot.getArmorstand().setRightArmPose(new EulerAngle(0,0,360 - 6));
            foot.equip(0, concreteItem);
            foot.equip(1, concreteItem);
        }


        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());

        /**
        Animations
         */

        wingsAnimation = new Animation(-2, 2, Updater.Type.TWO_TICK, animation -> {
            int offSet = 5 * animation.getFrame();
            leftWing.getArmorstand().setHeadPose(new EulerAngle(offSet,150 + offSet,90));
            rightWing.getArmorstand().setHeadPose(new EulerAngle(offSet,210 - offSet,-90));
            FlyingUtils.sendPacket(player.getWorld(), leftWing.getArmorstand().getMetaDataPacket(), rightWing.getArmorstand().getMetaDataPacket());
        });
        wingsAnimation.setInfinite(true);
        wingsAnimation.play();

        // Setup the fireball's animations
        fireArmorstand = new ArmorStandPacket(player.getLocation());
        fireArmorstand.setInvisible(true);
        fireArmorstand.setMarker(true);
        fireAnimation = new Animation(0, 15, Updater.Type.TWO_TICK, animation -> {
            if (animation.getFrame() < 6){
                head.getArmorstand().setHeadPose(new EulerAngle(animation.getFrame() * -15, 0,0));
                FlyingUtils.sendPacket(player.getWorld(), head.getArmorstand().getMetaDataPacket());
            }

            if (animation.getFrame() == 6){
                head.getArmorstand().setHeadPose(new EulerAngle(0, 0,0));
                FlyingUtils.sendPacket(player.getWorld(), head.getArmorstand().getMetaDataPacket());

                fireLocation = player.getLocation().add(FlyingUtils.rotateYaw(dragonOffset, player.getLocation().getYaw())).subtract(0,1.4,0);
                fireDirection = fireLocation.getDirection().setY(0).normalize();
                fireArmorstand.setLocation(fireLocation.add(fireDirection)); // Will directly be in the good position on spawn
                FlyingUtils.sendPacket(player.getWorld(), fireArmorstand.getSpawnPacket(), fireArmorstand.getEquipmentPacket(5, fireItem));
                player.getWorld().playSound(fireLocation, Sound.ENTITY_ENDER_DRAGON_SHOOT, 1, 1);
                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, fireLocation.clone().add(0, 1.5,0), 1);
            }

            if (animation.getFrame() > 6 && animation.getFrame() != animation.getMax())
                FlyingUtils.sendPacket(player.getWorld(), fireArmorstand.getTeleportPacket(fireLocation.add(fireDirection)));
        });

        fireAnimation.setStopPlayer(stopPlayer -> {
            FlyingUtils.sendPacket(player.getWorld(), fireArmorstand.getDestroyPacket());
        });
    }

    @Override
    public void remove() {
        super.remove();
        fireAnimation.stop();
    }

    @Override
    public void update(Updater.Type type) {
        super.update(type);
        wingsAnimation.update(type);
        fireAnimation.update(type);

        if (type == Updater.Type.MINUTE && getLevel() > 5){
            fireAnimation.play();
        }
    }
}