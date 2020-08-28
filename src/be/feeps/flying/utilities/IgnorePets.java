package be.feeps.flying.utilities;

import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.FieldAccessor;
import be.feeps.flying.utilities.packets.OnPacketInEvent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;

public class IgnorePets implements Listener {
    private static final HashSet<Integer> ignoredEntities = new HashSet<>();

    public static void add(int entID){
        ignoredEntities.add(entID);
    }

    public static void remove(int entID){
        ignoredEntities.remove(entID);
    }

    /*
        public static enum EnumEntityUseAction {
            INTERACT,         -- 0
            ATTACK,           -- 1
            INTERACT_AT;      -- 2

            private EnumEntityUseAction() {
            }
        }
     */
    private static final Class<?> packetPlayInUseEntityClass = Reflection.getMinecraftClass("PacketPlayInUseEntity");
    private static final Class<?> enumEntityUseActionClass = packetPlayInUseEntityClass.getDeclaredClasses()[0];
    private static final FieldAccessor<Integer> idPacketPlayInUseField = Reflection.getField(packetPlayInUseEntityClass, Integer.TYPE, 0);
    private static final FieldAccessor<?> actionPacketPlayInUseField = Reflection.getField(packetPlayInUseEntityClass, "action", enumEntityUseActionClass);

    @EventHandler
    public void onPacketInUseEntity(OnPacketInEvent event){
        Object packet = event.getPacket();
        Player sender = event.getSender();

        if (!packetPlayInUseEntityClass.isInstance(packet)) return; // PacketPlayInUseEntity
        Object action = actionPacketPlayInUseField.get(packet);

        if (action == enumEntityUseActionClass.getEnumConstants()[1]){ // Attack
            int entityId = idPacketPlayInUseField.get(packet);
            if (ignoredEntities.contains(entityId)){
                // Ignore the attack of the pet by replacing the current id by the targeted entity
                Location senderPos = sender.getEyeLocation();

                Entity target = sender.getWorld().rayTraceEntities(senderPos,
                            senderPos.getDirection(),
                            FlyingUtils.getReachDistance(sender),
                            0,
                            entity -> entity != sender)
                        .getHitEntity();

                if (target != null)
                    idPacketPlayInUseField.set(event.getPacket(), target.getEntityId()); // Replace the entity's id
            }
        }
    }
}
