package be.feeps.flying.entities;

import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.MethodInvoker;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class CatPacket extends EntityPacketAgeable{
    private static final Class<?> entityArmorStandClass = Reflection.getClass("{nms}.EntityCat");
    private static final MethodInvoker setCatTypeMethod = Reflection.getMethod(entityArmorStandClass, "setCatType", Integer.TYPE);

    public CatPacket(Location location){
        super(EntityType.CAT, location);
    }

    public void setCatType(int type){
        setCatTypeMethod.invoke(this.livingEntity, type);
    }
}
