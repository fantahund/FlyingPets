package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Updater;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class ShapePet extends Pet{
    protected ArmorStandShape shape;
    private Vector offset;

    public ShapePet(Player player, PetType type, Vector nameOffset, Vector offset){
        super(player, type, nameOffset);
        this.shape = new ArmorStandShape(player.getLocation());
        this.offset = offset;
    }

    public ShapePet(Player player, PetType type, Vector nameOffset){
        this(player, type, nameOffset, FlyingUtils.ZERO);
    }

    @Override
    public void update(Updater.Type type) {
        if (type == Updater.Type.TICK) {
            location = player.getLocation().add(FlyingUtils.rotateYaw(offset, player.getLocation().getYaw()));
            shape.setBaseLoc(location);
            shape.getStructs().forEach(struct -> {
                FlyingUtils.sendPacket(player.getWorld(), struct.getArmorstand().getTeleportPacket(struct.getRelativeLoc()));
            });
        }

        super.update(type);
    }

    @Override
    public Object[] getInitPackets() {
        Object[] packets = super.getInitPackets();

        for(Struct struct : shape.getStructs()){
            Object spawnPacket = struct.getArmorstand().getSpawnPacket();
            Object[] equipmentsPackets = struct.getEquipmentsPackets();

            Object[] both = ArrayUtils.addAll(new Object[]{spawnPacket}, equipmentsPackets);
            packets = ArrayUtils.addAll(both, packets);
        }
        return packets;
    }

    @Override
    public Object[] getDestroyPackets() {
        Object[] packets = super.getDestroyPackets();

        for(Struct struct : shape.getStructs()){
            packets = ArrayUtils.add(packets, struct.getArmorstand().getDestroyPacket());
        }

        return packets;
    }

    public Vector getOffset() {
        return offset;
    }

    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    public ArmorStandShape getShape(){
        return shape;
    }
}
