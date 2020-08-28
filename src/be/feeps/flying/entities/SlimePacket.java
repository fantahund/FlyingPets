package be.feeps.flying.entities;

import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.MethodInvoker;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class SlimePacket extends LivingEntityPacket {
    private static final Class<?> entityArmorStandClass = Reflection.getClass("{nms}.EntitySlime");
    private static final MethodInvoker setSizeMethod = Reflection.getMethod(entityArmorStandClass, "setSize", Integer.TYPE, Boolean.TYPE);

    public SlimePacket(EntityType type, Location location){
        super(type, location);
    }

    public SlimePacket(Location location){
        super(EntityType.SLIME, location);
    }

    public void setSize(int size){
        setSizeMethod.invoke(this.livingEntity, size, false);
    }
}
