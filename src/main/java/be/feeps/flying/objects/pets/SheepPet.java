package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.EntityPacketAgeable;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SheepPet extends Pet{
    private static final Vector sheepOffset = new Vector(-0.8, 1.5, 0.8);
    private static final Vector nameOffset = new Vector(0,0.65,0);
    private final EntityPacketAgeable sheep;
    private final Object noCollidePacket;

    public SheepPet(Player player){
        super(player, PetType.SHEEP,nameOffset);
        sheep = new EntityPacketAgeable(EntityType.SHEEP, player.getLocation());
        sheep.setAge(-1);
        //sheep.setCustomName("jeb_");
        //sheep.setCustomNameVisible(false);

        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) this.sheep.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
        IgnorePets.add(sheep.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(sheep.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{sheep.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), sheep.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw(sheepOffset, player.getLocation().getYaw()));
            Object posPacket = sheep.getTeleportPacket(location);
            Object headPacket = sheep.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}
