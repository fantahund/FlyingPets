package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class TurtlePet extends ShapePet {
    private static final Vector turtleOffset = new Vector(-0.8, 0.55, 0.8);
    private static final Vector nameOffset = new Vector(0,1.9,0);
    private static final ItemStack shellHead = new Item(Material.PLAYER_HEAD).skullBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjY0Njk4ZmVhNWVjM2YyZmQ3ZGI3YThlM2Y0ZTk4OWExNzE2MDM1YzJiZDM2NjY0MzRiYTFhZjU4MTU3In19fQ==").make();
    private static final ItemStack turtleHead = PetType.TURTLE.getItem();
    private static final ItemStack footItem = new Item(Material.LIME_TERRACOTTA).data(5).make();

    public TurtlePet(Player player){
        super(player, PetType.TURTLE, nameOffset, turtleOffset);

        Struct shell = shape.add(new Vector());
        shell.equip(5, shellHead);

        Struct head = shape.add(new Vector(.25, 0.8, 0));
        head.getArmorstand().setSmall(true);
        head.equip(5, turtleHead);

        for(int i = 0; i < 2; i++){
            Struct foot = shape.add(new Vector(0.1 - 0.4 * i, 1, 0));
            foot.getArmorstand().setSmall(true);
            foot.getArmorstand().setLeftArmPose(new EulerAngle(0,0,6));
            foot.getArmorstand().setRightArmPose(new EulerAngle(0,0,360 - 6));
            foot.equip(0, footItem);
            foot.equip(1, footItem);
        }

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
    }
}