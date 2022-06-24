package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.SlimePacket;
import be.feeps.flying.objects.Animation;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MagmaCubePet extends Pet{
    private static final Vector magmaCubeOffset = new Vector(-0.6, 1.6, 0.7);
    private static final Vector nameOffset = new Vector(0, .3, 0);
    private final SlimePacket magmacube;
    private final Object noCollidePacket;

    private final Animation magmaAnimation;

    public MagmaCubePet(Player player){
        super(player, PetType.MAGMA_CUBE, nameOffset);
        magmacube = new SlimePacket(EntityType.MAGMA_CUBE, player.getLocation());
        magmacube.setSize(1);
        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) magmacube.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), getInitPackets());

        magmaAnimation = new Animation(2, 3, Updater.Type.THREE_TICK, animation -> {
            if (animation.getFrame() != animation.getMax()){
                magmacube.setSize(animation.getFrame());
            }else{
                magmacube.setSize(1);
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation().add(FlyingUtils.rotateYaw(magmaCubeOffset, player.getLocation().getYaw())).subtract(0,0.1,0), 10);
            }
            FlyingUtils.sendPacket(player.getWorld(), magmacube.getMetaDataPacket());
        });

        IgnorePets.add(magmacube.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(magmacube.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{magmacube.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), magmacube.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw( magmaCubeOffset, player.getLocation().getYaw()));
            FlyingUtils.sendPacket(player.getWorld(),
                    magmacube.getTeleportPacket(location),
                    magmacube.getHeadRotationPacket(location.getYaw())
            );
        }

        super.update(type);

        magmaAnimation.update(type);
        if (type == Updater.Type.MINUTE)
            magmaAnimation.play();
    }
}