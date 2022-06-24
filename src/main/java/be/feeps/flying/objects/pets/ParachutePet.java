package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class ParachutePet extends ShapePet {
    private static final ItemStack parachuteHead = PetType.PARACHUTE.getItem();
    private static final Vector parachuteOffset =  new Vector(-0.7, 0.8, 0.7);
    private static final Vector nameOffset = new Vector(0, 2,0);

    public ParachutePet(Player player) {
        super(player, PetType.PARACHUTE, nameOffset, parachuteOffset);

        Struct researcher = shape.add(new Vector(0, 0.6, 0));
        researcher.equip(5, parachuteHead);
        researcher.getArmorstand().setSmall(true);

        for(int i = 0; i<3; i++){
            Struct top = shape.add(new Vector(0,1.35 + (i == 1 ? 0.09 : 0),-0.43 + 0.43*i));
            top.getArmorstand().setSmall(true);
            top.getArmorstand().setHeadPose(new EulerAngle(0, 0,25 - 25*i));
            top.equip(5, new ItemStack(Material.BROWN_CARPET));
        }

        for(int i = 0; i<=1; i++){
            Struct stick = shape.add(new Vector(0,1.2 - i * 0.35,0.4 - i*0.8));
            stick.getArmorstand().setSmall(true);
            stick.getArmorstand().setRightArmPose(new EulerAngle(0,90,240 - i* 100));
            stick.equip(0, new ItemStack(Material.STICK));
        }


        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
    }
}