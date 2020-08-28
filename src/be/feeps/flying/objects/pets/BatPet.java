package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.BatPacket;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BatPet extends Pet{
    private static final Vector batOffset = new Vector(-0.7, 1.5, 0.8);
    private static final Vector nameOffset = new Vector(0,0.6,0);
    private BatPacket bat;
    private Object noCollidePacket;

    public BatPet(Player player){
        super(player, PetType.BAT, nameOffset);
        bat = new BatPacket(player.getLocation());
        bat.setAwake(false);

        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) bat.getBukkitEntity());

        FlyingUtils.sendPacket(player.getWorld(), getInitPackets());
        IgnorePets.add(bat.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(bat.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{bat.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), bat.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw(batOffset, player.getLocation().getYaw()));
            Object posPacket = bat.getTeleportPacket(location);
            Object headPacket = bat.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}