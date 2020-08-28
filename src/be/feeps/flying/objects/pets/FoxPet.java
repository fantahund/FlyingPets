package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.EntityPacketAgeable;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FoxPet extends Pet{
    private static final Vector foxOffset = new Vector(-0.8, 1.7, 0.8);
    private static final Vector nameOffset = new Vector(0, 0.3, 0);
    private EntityPacketAgeable fox;
    private Object noCollidePacket;

    public FoxPet(Player player){
        super(player, PetType.FOX, nameOffset);
        fox = new EntityPacketAgeable(EntityType.FOX, player.getLocation());
        fox.setAge(-1);

        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) fox.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), getInitPackets());
        IgnorePets.add(fox.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(fox.getId());
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), fox.getDestroyPacket());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{fox.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw(foxOffset, player.getLocation().getYaw()));
            Object posPacket = fox.getTeleportPacket(location);
            Object headPacket = fox.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}
