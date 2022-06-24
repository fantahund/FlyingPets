package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.LivingEntityPacket;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParrotPet extends Pet {
    private static final Vector parrotOffset = new Vector(-0.8, 1.5, 0.8);
    private static final Vector nameOffset = new Vector(0, 0.65,0);

    private final LivingEntityPacket parrot;
    private final Object noCollidePacket;

    public ParrotPet(Player player){
        super(player, PetType.PARROT, nameOffset);
        parrot = new LivingEntityPacket(EntityType.PARROT, player.getLocation());
        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) parrot.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
        IgnorePets.add(parrot.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(parrot.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{parrot.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), parrot.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK) {
            location = player.getLocation().add(FlyingUtils.rotateYaw(parrotOffset, player.getLocation().getYaw()));
            Object posPacket = parrot.getTeleportPacket(location);
            Object headPacket = parrot.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}
