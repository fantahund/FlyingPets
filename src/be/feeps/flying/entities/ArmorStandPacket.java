package be.feeps.flying.entities;

import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.MethodInvoker;
import be.feeps.flying.Reflection.ConstructorInvoker;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;

public class ArmorStandPacket extends LivingEntityPacket {
    private static final Class<?> entityArmorStandClass = Reflection.getClass("{nms}.EntityArmorStand");
    private static final Class<?> vector3fClass = Reflection.getMinecraftClass("Vector3f");

    private static final ConstructorInvoker vector3fConstructor = Reflection.getConstructor(vector3fClass, Float.TYPE, Float.TYPE, Float.TYPE);

    private static final MethodInvoker setMarkerMethod = Reflection.getMethod(entityArmorStandClass, "setMarker", Boolean.TYPE);
    private static final MethodInvoker setSmallMethod = Reflection.getMethod(entityArmorStandClass, "setSmall", Boolean.TYPE);
    private static final MethodInvoker setBasePlateMethod = Reflection.getMethod(entityArmorStandClass, "setBasePlate", Boolean.TYPE);
    private static final MethodInvoker setBodyPoseMethod = Reflection.getMethod(entityArmorStandClass, "setBodyPose", vector3fClass);
    private static final MethodInvoker setHeadPose = Reflection.getMethod(entityArmorStandClass, "setHeadPose", vector3fClass);
    private static final MethodInvoker setLeftArmPoseMethod = Reflection.getMethod(entityArmorStandClass, "setLeftArmPose", vector3fClass);
    private static final MethodInvoker setRightArmPoseMethod = Reflection.getMethod(entityArmorStandClass, "setRightArmPose", vector3fClass);
    private static final MethodInvoker setInvisibleMethod = Reflection.getMethod(entityArmorStandClass, "setInvisible", Boolean.TYPE);
    private static final MethodInvoker setArmsMethod = Reflection.getMethod(entityArmorStandClass, "setArms", Boolean.TYPE);

    public ArmorStandPacket(Location loc){
        super(EntityType.ARMOR_STAND, loc);
    }

    public void setInvisible(boolean invisible){
        setInvisibleMethod.invoke(this.livingEntity, invisible);
    }

    public void setMarker(boolean marker){
        setMarkerMethod.invoke(this.livingEntity, marker);
    }

    public void setSmall(boolean small){
        setSmallMethod.invoke(this.livingEntity, small);
    }

    public void setBasePlate(boolean basePlate){
        setBasePlateMethod.invoke(this.livingEntity, basePlate);
    }

    public void setBodyPose(EulerAngle eulerAngle){
        setBodyPoseMethod.invoke(this.livingEntity, vector3fConstructor.invoke(
                (float) eulerAngle.getX(),
                (float) eulerAngle.getY(),
                (float) eulerAngle.getZ()
        ));
    }

    public void setHeadPose(EulerAngle eulerAngle){
        setHeadPose.invoke(this.livingEntity, vector3fConstructor.invoke(
                (float) eulerAngle.getX(),
                (float) eulerAngle.getY(),
                (float) eulerAngle.getZ()
        ));
    }

    public void setRightArmPose(EulerAngle eulerAngle){
        setRightArmPoseMethod.invoke(this.livingEntity, vector3fConstructor.invoke(
                (float) eulerAngle.getX(),
                (float) eulerAngle.getY(),
                (float) eulerAngle.getZ()
        ));
    }

    public void setLeftArmPose(EulerAngle eulerAngle){
        setLeftArmPoseMethod.invoke(this.livingEntity, vector3fConstructor.invoke(
                (float) eulerAngle.getX(),
                (float) eulerAngle.getY(),
                (float) eulerAngle.getZ()
        ));
    }

    public void setArms(boolean show){
        setArmsMethod.invoke(this.livingEntity, show);
    }
}
