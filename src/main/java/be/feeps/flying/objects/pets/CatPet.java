package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.CatPacket;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CatPet extends Pet{
    private static final Vector catOffset = new Vector(-0.7, 1.7, 0.7);
    private static final Vector nameOffset = new Vector(0,0.15,0);
    private final CatPacket cat;
    private final Object noCollidePacket;

    public CatPet(Player player){
        super(player, PetType.CAT, nameOffset);
        cat = new CatPacket(player.getLocation());
        cat.setAge(-1);
        cat.setCatType(-1); // NMS Will automatically choose a random one

        noCollidePacket = FlyingUtils.getNoCollidePacket((LivingEntity) cat.getBukkitEntity());
        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
        IgnorePets.add(cat.getId());
    }

    @Override
    public void remove() {
        super.remove();
        IgnorePets.remove(cat.getId());
    }

    @Override
    public Object[] getInitPackets() {
        return ArrayUtils.addAll(super.getInitPackets(), new Object[]{cat.getSpawnPacket(), noCollidePacket});
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), cat.getDestroyPacket());
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK){
            location = player.getLocation().add(FlyingUtils.rotateYaw(catOffset, player.getLocation().getYaw()));
            Object posPacket = cat.getTeleportPacket(location);
            Object headPacket = cat.getHeadRotationPacket(location.getYaw());
            FlyingUtils.sendPacket(player.getWorld(), posPacket, headPacket);
        }
        super.update(type);
    }
}
