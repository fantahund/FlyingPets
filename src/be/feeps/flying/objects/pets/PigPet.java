package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.EntityPacketAgeable;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PigPet extends Pet{
    private static final Vector pigOffset = new Vector(-0.8, 1.6, 0.8);
    private static final Vector nameOffset = new Vector(0, 0.65,0);
    private EntityPacketAgeable pig;
    private Object noCollidePacket;

    public PigPet(Player player){
        super(player, PetType.PIG, nameOffset);
        pig = new EntityPacketAgeable(EntityType.PIG, player.getLocation());
        pig.setAge(-1);

        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) pig.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
        IgnorePets.add(pig.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(pig.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{pig.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), pig.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw(pigOffset, player.getLocation().getYaw()));
            Object posPacket = pig.getTeleportPacket(location);
            Object headPacket = pig.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}
