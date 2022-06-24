package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class PandaPet extends ShapePet{
    private static final Vector pandaOffset = new Vector(-0.8, 0, 0.8);
    private static final Vector nameOffset =  new Vector(0, 2.25,0);
    private static final ItemStack blackHead = new Item(Material.PLAYER_HEAD).skullBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY3YTJmMjE4YTZlNmUzOGYyYjU0NWY2YzE3NzMzZjRlZjliYmIyODhlNzU0MDI5NDljMDUyMTg5ZWUifX19").make();
    private static final ItemStack whiteHead = new Item(Material.PLAYER_HEAD).skullBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTdjMjE0NGZkY2I1NWMzZmMxYmYxZGU1MWNhYmRmNTJjMzg4M2JjYjU3ODkyMzIyNmJlYjBkODVjYjJkOTgwIn19fQ==").make();
    private static final ItemStack pandaHead = PetType.PANDA.getItem();
    private static final ItemStack bambooItem = new Item(Material.BAMBOO).make();

    public PandaPet(Player player) {
        super(player, PetType.PANDA, nameOffset, pandaOffset);

        Struct headStruct = shape.add(new Vector(0, 1.3, 0));
        headStruct.equip(5, pandaHead);
        headStruct.equip(0, bambooItem);
        headStruct.getArmorstand().setRightArmPose(new EulerAngle(215, 320, 310));
        headStruct.getArmorstand().setSmall(true);

        Struct bodyStruct = shape.add(new Vector());
        bodyStruct.equip(5, blackHead);

        Struct bellyStruct = shape.add(new Vector(0.1, 0.8, 0));
        bellyStruct.equip(5, whiteHead);
        bellyStruct.getArmorstand().setSmall(true);

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());
    }
}