package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.ZombiePacket;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ZombiePet extends Pet{
    private static final Vector zombieOffset = new Vector(-0.7, 1.5, 0.7);
    private static final Vector nameOffset = new Vector(0,.95,0);
    private final ZombiePacket zombie;
    private final Object noCollidePacket;

    public ZombiePet(Player player){
        super(player, PetType.ZOMBIE, nameOffset);
        zombie = new ZombiePacket(player.getLocation());
        zombie.setBaby(true);

        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) zombie.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
        IgnorePets.add(zombie.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(zombie.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{zombie.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), zombie.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw(zombieOffset, player.getLocation().getYaw()));
            Object posPacket = zombie.getTeleportPacket(location);
            Object headPacket = zombie.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}
