package be.feeps.flying.objects.pets;

import be.feeps.flying.objects.Animation;
import be.feeps.flying.objects.pets.ArmorStandShape.Struct;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import be.feeps.flying.utilities.Updater;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class SquidPet extends ShapePet {
    private static final Vector nameOffset = new Vector(0,1.85,0);
    private static final ItemStack squidHead = PetType.SQUID.getItem();
    private static final ItemStack inkSacItem = new Item(Material.INK_SAC).make();
    private static final Vector[] squidDetailsOffset = new Vector[]{
        new Vector(-0.575,-0.3,0.15),
        new Vector(-0.15,-0.3,-0.575),
        new Vector(0.575,-0.3,-0.15),
        new Vector(0.15,-0.3,0.575)
    };

    private final Vector squidOffset = new Vector(-0.8, 0.55, 0.8);
    private final Animation squidAnimation;

    public SquidPet(Player player) {
        super(player, PetType.SQUID, nameOffset);
        this.setOffset(squidOffset);

        Struct body = shape.add(new Vector());
        body.equip(5, squidHead);

        for (int i = 0; i < 4; i++) {
            Struct detail = shape.add(squidDetailsOffset[i], i * 90, 0);
            detail.equip(1, inkSacItem);
            detail.getArmorstand().setLeftArmPose(new EulerAngle(270, 0,0));
        }

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets());


        //Animations things
        squidAnimation = new Animation(0, 4, Updater.Type.FOUR_TICK, animation -> {
            // Total to add 4*.5
            if (animation.getFrame() <= 3) {
                Location playerLoc = player.getLocation().add(0,1,0);
                player.getWorld().spawnParticle(Particle.SQUID_INK, playerLoc.add(FlyingUtils.rotateYaw(this.squidOffset, playerLoc.getYaw())),0, 0, -0.1, 0);
                squidOffset.add(new Vector(0, .1, 0));
            } else{
                squidOffset.subtract(new Vector(0, 4*.1, 0));
            }
        });
    }

    @Override
    public void update(Updater.Type type) {
        super.update(type);
        squidAnimation.update(type);

        if (type == Updater.Type.EIGHT_SECONDS)
            squidAnimation.play();
    }
}