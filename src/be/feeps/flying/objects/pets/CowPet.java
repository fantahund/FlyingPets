package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.EntityPacketAgeable;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CowPet extends Pet{
    private static final Vector cowOffset = new Vector(-0.8, 1.5, 0.8);
    private static final Vector nameOffset = new Vector(0,0.8,0);
    private EntityPacketAgeable cow;
    private Object noCollidePacket;

    public CowPet(Player player){
        super(player, PetType.COW, nameOffset);
        cow = new EntityPacketAgeable(EntityType.COW, player.getLocation());
        cow.setAge(-1);

        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) cow.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
        IgnorePets.add(cow.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(cow.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{cow.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), cow.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw(cowOffset, player.getLocation().getYaw()));
            Object posPacket = cow.getTeleportPacket(location);
            Object headPacket = cow.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}
