package be.feeps.flying.entities;

import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.MethodInvoker;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class BatPacket extends LivingEntityPacket {
    private static final Class<?> batClass = Reflection.getClass("{nms}.EntityBat");
    private static final MethodInvoker setASleepMethod = Reflection.getMethod(batClass, "setAsleep", Boolean.TYPE);

    public BatPacket(Location location){
        super(EntityType.BAT, location);
    }

    public void setAwake(boolean awake){
        setASleepMethod.invoke(this.livingEntity, awake);
    }
}
