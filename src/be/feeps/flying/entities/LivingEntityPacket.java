package be.feeps.flying.entities;

import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.ConstructorInvoker;
import be.feeps.flying.Reflection.FieldAccessor;
import be.feeps.flying.Reflection.MethodInvoker;
import be.feeps.flying.utilities.FlyingUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class LivingEntityPacket {
    // Static things
    protected static final Class<?> entityClass = Reflection.getMinecraftClass("Entity");
    protected static final Class<?> entityLivingClass = Reflection.getMinecraftClass("EntityLiving");
    protected static final Class<?> entityTypes = Reflection.getMinecraftClass("EntityTypes");
    protected static final Class<?> nmsWorld = Reflection.getMinecraftClass("World");
    protected static final Class<?> dataWatcherClass = Reflection.getMinecraftClass("DataWatcher");
    protected static final Class<?> craftItemStackClass = Reflection.getClass("{obc}.inventory.CraftItemStack");
    protected static final Class<?> chatBaseComponentClass = Reflection.getMinecraftClass("IChatBaseComponent");

    protected static final Class<?> packetSpawnEntityLivingClass = Reflection.getMinecraftClass("PacketPlayOutSpawnEntityLiving");
    protected static final Class<?> packetDestroyClass = Reflection.getMinecraftClass("PacketPlayOutEntityDestroy");
    protected static final Class<?> packetTeleportClass = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
    protected static final Class<?> packetMetaDataClass = Reflection.getMinecraftClass("PacketPlayOutEntityMetadata");
    protected static final Class<?> packetEquipClass = Reflection.getMinecraftClass("PacketPlayOutEntityEquipment");
    protected static final Class<?> packetEntityLookClass = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
    protected static final Class<?> packetHeadRotationClass = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");

    protected static final Class<?> enumItemSlotClass = Reflection.getMinecraftClass("EnumItemSlot");
    protected static final Class<?> itemStackClass = Reflection.getMinecraftClass("ItemStack");

    protected static final ConstructorInvoker packetSpawnConstructor =  Reflection.getConstructor(packetSpawnEntityLivingClass, entityLivingClass);
    protected static final ConstructorInvoker packetDestroyConstructor =  Reflection.getConstructor(packetDestroyClass, int[].class);
    protected static final ConstructorInvoker packetTeleportConstructor =  Reflection.getConstructor(packetTeleportClass, entityClass);
    protected static final ConstructorInvoker packetMetadataConstructor = Reflection.getConstructor(packetMetaDataClass, Integer.TYPE, dataWatcherClass, Boolean.TYPE);
    protected static final ConstructorInvoker packetEquipConstructor = Reflection.getConstructor(packetEquipClass, Integer.TYPE, enumItemSlotClass, itemStackClass);

    protected static final ConstructorInvoker packetEntityLookConstructor = Reflection.getConstructor(packetEntityLookClass, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
    protected static final ConstructorInvoker packetHeadRotationConstructor = Reflection.getConstructor(packetHeadRotationClass, entityClass, Byte.TYPE);

    protected static final MethodInvoker getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle");
    protected static final MethodInvoker setLocationMethod = Reflection.getMethod(entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
    protected static final MethodInvoker getIdMethod = Reflection.getMethod(entityClass, "getId");
    protected static final MethodInvoker asNMSCopyMethod = Reflection.getMethod(craftItemStackClass, "asNMSCopy", ItemStack.class);
    protected static final MethodInvoker getBukkitEntityMethod = Reflection.getMethod(entityClass, "getBukkitEntity");
    protected static final MethodInvoker setCustomeNameVisibleMethod = Reflection.getMethod(entityClass, "setCustomNameVisible", Boolean.TYPE);
    protected static final MethodInvoker setCustomNameMethod = Reflection.getMethod(entityClass, "setCustomName", chatBaseComponentClass);
    protected static final MethodInvoker chatBaseComponentSerializerMethod = Reflection.getMethod(Reflection.getMinecraftClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
    protected static final MethodInvoker getDataWatcherMethod = Reflection.getMethod(entityClass, "getDataWatcher");

    // Object classes
    protected final Class<?> objectEntityClass;
    protected final ConstructorInvoker objectEntityClassConstructor;
    protected final FieldAccessor<?> typeField;

    protected EntityType type;
    protected Object livingEntity;
    protected Entity bukkitEntity;
    protected int id;

    public LivingEntityPacket(EntityType type, Location loc){
        this.type = type;

        // Final things
        this.typeField = Reflection.getField(entityTypes, type.toString(), entityTypes);
        this.objectEntityClass = Reflection.getMinecraftClass("Entity" + type.getEntityClass().getSimpleName());
        this.objectEntityClassConstructor = Reflection.getConstructor(this.objectEntityClass, entityTypes, nmsWorld);

        this.livingEntity = this.objectEntityClassConstructor.invoke(this.typeField.get(entityTypes), getHandleWorldMethod.invoke(loc.getWorld()));
        this.setLocation(loc); // Need to be done before the creation of the spawnPacket

        this.bukkitEntity = (Entity) getBukkitEntityMethod.invoke(this.livingEntity);
        this.id = (Integer) getIdMethod.invoke(this.livingEntity);
    }

    public void setLocation(Location loc){
        setLocationMethod.invoke(this.livingEntity, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    // Getters for packets
    public Object getTeleportPacket(Location loc){
        setLocation(loc);
        return packetTeleportConstructor.invoke(this.livingEntity);
    }

    public Object getSpawnPacket(){
        return packetSpawnConstructor.invoke(this.livingEntity);
    }

    public Entity getBukkitEntity(){
        return this.bukkitEntity;
    }

    public Object getHeadRotationPacket(float yaw){
        return packetHeadRotationConstructor.invoke(this.livingEntity, FlyingUtils.getCompressedAngle(yaw));
    }

    public Object getEntityLookPacket(float yaw, float pitch){
        return packetEntityLookConstructor.invoke(this.id, FlyingUtils.getCompressedAngle(yaw), FlyingUtils.getCompressedAngle(pitch), false);
    }

    public void setCustomNameVisible(boolean isVisible){
        setCustomeNameVisibleMethod.invoke(this.livingEntity, isVisible);
    }

    public void setCustomName(String name){
        Object chatBaseComponent = chatBaseComponentSerializerMethod.invoke(chatBaseComponentClass, "{\"text\":\"" + FlyingUtils.toColorable(name) + "\"}");
        setCustomNameMethod.invoke(this.livingEntity, chatBaseComponent);
    }

    public Object getDestroyPacket(){
        return packetDestroyConstructor.invoke(new int[]{this.id});
    }

    public int getId(){
        return this.id;
    }

    public Object getMetaDataPacket(){
        return packetMetadataConstructor.invoke(this.id, getDataWatcherMethod.invoke(this.livingEntity), true);
    }

    /*
        MAINHAND(EnumItemSlot.Function.HAND, 0, 0, "mainhand"),     0
        OFFHAND(EnumItemSlot.Function.HAND, 1, 5, "offhand"),       1
        FEET(EnumItemSlot.Function.ARMOR, 0, 1, "feet"),            2
        LEGS(EnumItemSlot.Function.ARMOR, 1, 2, "legs"),            3
        CHEST(EnumItemSlot.Function.ARMOR, 2, 3, "chest"),          4
        HEAD(EnumItemSlot.Function.ARMOR, 3, 4, "head"); ²          5
     */
    public Object getEquipmentPacket(int slot, ItemStack item) {
        return packetEquipConstructor.invoke(this.id, enumItemSlotClass.getEnumConstants()[slot], asNMSCopyMethod.invoke(craftItemStackClass, item));
    }
}
