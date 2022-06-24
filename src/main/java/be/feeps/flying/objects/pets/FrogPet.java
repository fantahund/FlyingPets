package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class FrogPet extends ShapePet {
    private static final Vector frogOffset = new Vector(-0.7, 1.1, 0.7);
    private static final Vector nameOffset = new Vector(0,1,0);
    private static final ItemStack frogHead = PetType.FROG.getItem();

    public FrogPet(Player player){
        super(player, PetType.FROG, nameOffset, frogOffset);
        Struct headStruct = shape.add(FlyingUtils.ZERO);
        headStruct.equip(5, frogHead);
        headStruct.getArmorstand().setSmall(true);

        Struct foot1 = shape.add(new Vector(-0.05, 0.05, -.35));
        foot1.equip(0, new ItemStack(Material.GREEN_BANNER));
        foot1.getArmorstand().setRightArmPose(new EulerAngle(10, -15, 90));
        foot1.getArmorstand().setSmall(true);

        Struct foot2 = shape.add(new Vector(-0.16, 0.07, -.56));
        foot2.equip(0, new ItemStack(Material.GREEN_BANNER));
        foot2.getArmorstand().setRightArmPose(new EulerAngle(-10, -15, 90));
        foot2.getArmorstand().setSmall(true);

        FlyingUtils.sendPacket(player.getWorld(), getInitPackets());
    }
}
