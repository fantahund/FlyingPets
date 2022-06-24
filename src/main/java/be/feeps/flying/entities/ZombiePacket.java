package be.feeps.flying.entities;

import be.feeps.flying.Reflection;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class ZombiePacket extends LivingEntityPacket{
    private static final Class<?> entityZombieClass = Reflection.getClass("{nms}.EntityZombie");
    private static final Reflection.MethodInvoker setBabyMethod = Reflection.getMethod(entityZombieClass, "setBaby", Boolean.TYPE);

    public ZombiePacket(Location location){
        super(EntityType.ZOMBIE, location);
    }

    public void setBaby(boolean b){
        setBabyMethod.invoke(this.livingEntity, b);
    }
}
