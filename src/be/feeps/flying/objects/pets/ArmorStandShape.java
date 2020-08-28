package be.feeps.flying.objects.pets;

import be.feeps.flying.entities.ArmorStandPacket;
import be.feeps.flying.utilities.FlyingUtils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ArmorStandShape {
    private final ArrayList<Struct> structs = new ArrayList<Struct>();
    private Location baseLoc;

    public ArmorStandShape(Location baseLoc){
        this.baseLoc = baseLoc;
    }

    public Struct add(Vector offset, float yaw, float pitch){
        Struct newStruct = new Struct(offset, yaw, pitch);
        structs.add(newStruct);
        return newStruct;
    }

    public Struct add(Vector offset){
        return add(offset, 0,0);
    }

    public ArrayList<Struct> getStructs(){
        return structs;
    }

    public void setBaseLoc(Location location){
        this.baseLoc = location;
    }

    public Location getBaseLoc(){
        return this.baseLoc;
    }

    public class Struct{
        private ArmorStandPacket armorstand;
        private Object[] equipmentsPackets;

        // Location things
        private Vector offset;
        private float pitch;
        private float yaw;

        public Struct(Vector offset, float yaw, float pitch) {
            this.offset = offset;
            this.yaw = yaw;
            this.pitch = pitch;

            this.armorstand = new ArmorStandPacket(baseLoc);
            this.armorstand.setMarker(true);
            this.armorstand.setInvisible(true);

            equipmentsPackets = new Object[6];
        }

        public void equip(int slot, ItemStack item){
            equipmentsPackets[slot] = this.armorstand.getEquipmentPacket(slot, item);
        }

        public Location getRelativeLoc(){
            Location structPartLoc = baseLoc.clone();
            structPartLoc.add(FlyingUtils.rotateYaw(offset, structPartLoc.getYaw()));
            structPartLoc.setYaw(structPartLoc.getYaw() + yaw);
            structPartLoc.setPitch(structPartLoc.getPitch() + pitch);
            return structPartLoc;
        }

        public Vector getOffset(){
            return this.offset;
        }

        public Object[] getEquipmentsPackets(){
            return equipmentsPackets;
        }

        public ArmorStandPacket getArmorstand(){
            return this.armorstand;
        }
    }
}
