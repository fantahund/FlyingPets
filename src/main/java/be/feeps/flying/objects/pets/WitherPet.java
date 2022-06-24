package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class WitherPet extends ShapePet {
    private static final Vector witherOffset = new Vector(-0.8, 0.65, 0.5);
    private static final Vector nameOffset = new Vector(0,1.9,0);
    private static final ItemStack witherHead = PetType.WITHER.getItem();
    private static final ItemStack chestItem = new Item(Material.LEATHER_CHESTPLATE).color(Color.fromBGR(30, 30, 30)).make();

    public WitherPet(Player player){
        super(player, PetType.WITHER, nameOffset, witherOffset);

        Struct left = shape.add(new Vector(0,0.72,-0.55));
        left.equip(5, witherHead);
        left.getArmorstand().setSmall(true);
        left.getArmorstand().setHeadPose(new EulerAngle(0,0,15));

        Struct center = shape.add(new Vector());
        center.equip(5, witherHead);
        center.equip(4, chestItem);

        Struct right = shape.add(new Vector(0,0.72,0.55));
        right.equip(5, witherHead);
        right.getArmorstand().setSmall(true);
        right.getArmorstand().setHeadPose(new EulerAngle(0,0,-15));

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
    }
}
