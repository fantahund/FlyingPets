package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class SnowmanPet extends ShapePet{
    private static final Vector snowmanOffset = new Vector(-0.8, 0.2, 0.8);
    private static final Vector nameOffset = new Vector(0,2.25,0);
    private static final ItemStack snowHead = new Item(Material.PLAYER_HEAD).skullBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRkNmZlMjY3YTQxOGRjYzdmMzdhOGY3Njg1NWI1MzI4YjEzMDM4OTdiMzQyYTEwN2NmMTYyZjE0ZmUzZCJ9fX0=").make();
    private static final ItemStack snowmanHead = PetType.SNOWMAN.getItem();
    private static final ItemStack stickItem = new Item(Material.STICK).make();

    public SnowmanPet(Player player) {
        super(player, PetType.SNOWMAN, nameOffset, snowmanOffset);

        Struct headStruct = shape.add(new Vector(0, 1.3, 0));
        headStruct.equip(5, snowmanHead);
        headStruct.equip(0, stickItem);
        headStruct.equip(1, stickItem);
        headStruct.getArmorstand().setRightArmPose(new EulerAngle(0, 270, 110));
        headStruct.getArmorstand().setLeftArmPose(new EulerAngle(-130, 135, 70));
        headStruct.getArmorstand().setSmall(true);

        Struct bodyStruct = shape.add(new Vector());
        bodyStruct.equip(5, snowHead);

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
    }
}