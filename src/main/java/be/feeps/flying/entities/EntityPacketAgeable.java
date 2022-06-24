package be.feeps.flying.entities;

import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.MethodInvoker;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class EntityPacketAgeable extends LivingEntityPacket{
    private static final Class<?> entityAgeableClass = Reflection.getMinecraftClass("EntityAgeable");
    private static final MethodInvoker setAgeMethod = Reflection.getMethod(entityAgeableClass, "setAge", Integer.TYPE);

    public EntityPacketAgeable(EntityType type, Location loc){
        super(type, loc);
    }

    public void setAge(int age){
        setAgeMethod.invoke(this.livingEntity, age);
    }
}
