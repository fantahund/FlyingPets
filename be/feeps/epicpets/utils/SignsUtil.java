package be.feeps.epicpets.utils;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.nms.Reflection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by feeps on 7/04/17.
 */
public final class SignsUtil {
    public static void openSign(Player p, Block b) {
        try {
            Object world = b.getWorld().getClass().getMethod("getHandle").invoke(b.getWorld());

            Object blockPos = Reflection.PackageType.MINECRAFT_SERVER.getClass("BlockPosition").getConstructor(int.class, int.class, int.class).newInstance(b.getX(), b.getY(), b.getZ());
            Object sign = world.getClass().getMethod("getTileEntity", Reflection.PackageType.MINECRAFT_SERVER.getClass("BlockPosition")).invoke(world, blockPos);
            Object player = p.getClass().getMethod("getHandle").invoke(p);
            player.getClass().getMethod("openSign", Reflection.PackageType.MINECRAFT_SERVER.getClass("TileEntitySign")).invoke(player, sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Block findAnAirBlock(Location l) {
        while(l.getY() < 255 && l.getBlock().getType() != Material.AIR) {
            l.add(0, 1, 0);
        }
        return l.getY() < 255 && l.getBlock().getType() == Material.AIR ? l.getBlock() : null;
    }

    public static void setSign(Player player, String metaData) {
        Block b = SignsUtil.findAnAirBlock(player.getLocation());
        b.setType(Material.SIGN_POST);
        SignsUtil.openSign(player, b);
        b.setMetadata(metaData, new FixedMetadataValue(Main.getI(), true));
    }
}
