package be.feeps.flying.utilities;

import be.feeps.flying.Main;
import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.ConstructorInvoker;
import be.feeps.flying.Reflection.FieldAccessor;
import be.feeps.flying.Reflection.MethodInvoker;
import be.feeps.flying.inventories.FlyingInventory;
import be.feeps.flying.listener.events.InteractListener;
import be.feeps.flying.listener.events.JoinQuitListener;
import be.feeps.flying.objects.PetManager;
import be.feeps.flying.objects.pets.ArmorstandPet;
import be.feeps.flying.utilities.packets.OnPacketInEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

public final class FlyingUtils {
    /*
        Listener class are put here, easier for me
        Instantiated in the main class with the empty constructor ( empty because we can get the plugin's instance with
        Main#getInstance() method )
     */
    public static final List<Class<? extends Listener>> listenerClass = new ArrayList<Class<? extends Listener>>() {
        {
            add(PetManager.PetUpdater.class); // Update position of pets
            add(JoinQuitListener.class);
            add(FlyingInventory.InvListener.class);
            add(InteractListener.class);
            add(ArmorstandPet.Config.class);
            add(SignGUI.class);
            add(IgnorePets.class);
        }
    };

    /*
        Multiples uses
     */
    public static final Vector ZERO = new Vector(0,0,0);
    public static final ItemStack airItem = new ItemStack(Material.AIR);
    public static final ItemStack backItem = new Item(Material.ARROW).name(L("inventories.back")).make();
    public static final ItemStack removeItem = new Item(Material.BARRIER).name(L("inventories.remove")).make();
    public static final ItemStack enabled = new Item(Material.LIME_DYE).name(L("inventories.editor.enabled")).make();
    public static final ItemStack disabled = new Item(Material.GRAY_DYE).name(L("inventories.editor.disabled")).make();


    public static ItemStack getBoolItem(boolean value){
        return value ? enabled : disabled;
    }

    // Values are variable, sees the game differently than the server
    public static float getReachDistance(Player player) {
        return player.getGameMode() == GameMode.CREATIVE ? 6.0f : 3f;
    }

    // Same as rotateAroundAxisY, but converts directly the degrees to radians and create a new Vector
    public static Vector rotateYaw(final Vector v, final float yawDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);
        double z = v.getZ() * cosYaw - v.getX() * sinYaw;
        double x = v.getZ() * sinYaw + v.getX() * cosYaw;
        return v.clone().setX(x).setZ(z);
    }

    // The angles sent by packets is compressed by Minecraft ( lighter packets )
    public static byte getCompressedAngle(float angle){
        return (byte)(int)(angle * 256f / 360f);
    }

    public static boolean hasPerm(Player player, String permission, boolean noPermError){
        boolean hasPerm = player.hasPermission(permission);
        if (!hasPerm && noPermError)
            player.sendMessage(L("messages.nopermission", true));

        return hasPerm;
    }

    public static boolean hasPerm(Player player, String permission){
        return hasPerm(player, permission, false);
    }

    // More efficient with TinyProtocol
    public static void sendPacket(final Player player, final Object... packets){
        for (Object packet : packets)
            if (packet != null)
                Main.getInstance().getProtocol().sendPacket(player, packet);
    }

    public static void sendPacket(final World world, final Object... packets){
        for (Player player : world.getPlayers())
            sendPacket(player, packets);
    }

    /*
        String utilities ( Convert, Format, Language, ... )
     */
    public static String L(String path, boolean prefix){
        FileConfiguration config = Main.getInstance().getConfig();
        return toColorable((prefix ? config.getString("languages.prefix") : "") + config.getString("languages." + path));
    }

    public static String L(String path){
        return L(path, false);
    }

    public static String base64Encode(String value) {
        try {
            return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8.toString()));
        } catch(UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getBase64SHead(String url){
        return base64Encode("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}");
    }

    public static String toColorable(String string) {
        return string.replace("&", "§");
    }

    public static String[] toColorable(String[] string) {
        for (int i = 0; i < string.length; i++) {
            string[i] = toColorable(string[i]);
        }
        return string;
    }

    /*
        NoCollide system
        Why we are not just creating a noCollideTeam per entity uuid ?
            - We can't because when the player is on a Bungeecord server,
              he is kicked when he receives a packet from the creation
              of a team that already exists. The problem is that pet#getInitPackets()
              will contains this packets and can be sent many times.

        actionsField used: (Integer)
        CREATE 0
        ADD_PLAYER 3
     */

    private static final String noCollideTeamName = "flyingpetsnocoll"; // No more than 16 characters
    private static final Class<?> teamPacketClass = Reflection.getMinecraftClass("PacketPlayOutScoreboardTeam");
    private static final ConstructorInvoker teamPacketConstructor = Reflection.getConstructor(teamPacketClass);
    private static final FieldAccessor playerNamesField = Reflection.getField(teamPacketClass, "h", Collection.class);
    private static final FieldAccessor collisionRuleField = Reflection.getField(teamPacketClass, "f", String.class);
    private static final FieldAccessor actionField = Reflection.getField(teamPacketClass, "i", Integer.TYPE);
    private static final FieldAccessor nameField = Reflection.getField(teamPacketClass, "a", String.class);

    public static final Object noCollideTeamPacket = teamPacketConstructor.invoke(); // This packet is sent onPlayerJoin in JoinQuitListener class
    static {
        collisionRuleField.set(noCollideTeamPacket, "never");
        actionField.set(noCollideTeamPacket, 0);
        nameField.set(noCollideTeamPacket, noCollideTeamName);
    }

    public static Object getNoCollidePacket(LivingEntity livingEntity){
        Object teamPacket = teamPacketConstructor.invoke();
        playerNamesField.set(teamPacket, Collections.singletonList(livingEntity.getUniqueId().toString()));
        actionField.set(teamPacket, 3);
        nameField.set(teamPacket, noCollideTeamName);
        return teamPacket;
    }

    /*
        Texturized heads

        Preload them to avoid lags
     */
    public enum Head{
        RIGHT_ARROW("http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf"),
        LEFT_ARROW("http://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9");
        public final String url;
        private final ItemStack item;

        Head(String url){
            this.url = url;
            item = new Item(Material.PLAYER_HEAD).skullBase64(FlyingUtils.getBase64SHead(url)).make();
        }

        public ItemStack getItem(){
            return item.clone();
        }

        public Item builder(){
            return new Item(getItem());
        }
    }

    /*
        Simple SignGUI

        1) Create a Sign block by packet ( The chunk where the sign is created
                must be in a loaded chunk for the client to prevent from crashing )
        2) Send the sign editor packet of this block
        3) Listen to the result (PacketPlayInUpdateSign)
     */

    public static class SignGUI implements Listener{
        private static final Class<?> openSignPacket = Reflection.getMinecraftClass("PacketPlayOutOpenSignEditor");
        private static final Class<?> updateSignClass = Reflection.getMinecraftClass("PacketPlayInUpdateSign");
        private static final Class<?> blockPositionClass = Reflection.getMinecraftClass("BlockPosition");

        private static final ConstructorInvoker openSignConstructor = Reflection.getConstructor(openSignPacket, blockPositionClass);
        private static final ConstructorInvoker blockPositionConstructor = Reflection.getConstructor(blockPositionClass, Integer.TYPE, Integer.TYPE, Integer.TYPE);

        private static final FieldAccessor blockPositionField = Reflection.getField(updateSignClass, blockPositionClass, 0);
        private static final FieldAccessor textField = Reflection.getField(updateSignClass, String[].class, 0);

        private static final MethodInvoker getXMethod = Reflection.getMethod(blockPositionClass, "getX");
        private static final MethodInvoker getYMethod = Reflection.getMethod(blockPositionClass, "getY");
        private static final MethodInvoker getZMethod = Reflection.getMethod(blockPositionClass, "getZ");

        private static final HashMap<UUID, Consumer<String[]>> signList = new HashMap<>();

        public static void open(Player player, Consumer<String[]> callback){
            Location location = player.getLocation();
            location.setY(0);
            sendPacket(player, openSignConstructor.invoke(blockPositionConstructor.invoke(location.getBlockX(), location.getBlockY(), location.getBlockZ())));
            player.sendBlockChange(location, Material.OAK_SIGN.createBlockData()); // TODO, remove it after ?
            signList.put(player.getUniqueId(), callback);
        }

        @EventHandler
        public void onSignUpdate(OnPacketInEvent event){
            Object packet = event.getPacket();
            if (!updateSignClass.isInstance(packet)) return;

            Object blockPosition = blockPositionField.get(packet);
            Consumer<String[]> consumer = signList.get(event.getSender().getUniqueId());
            if (consumer != null){
                Location location = event.getSender().getLocation();
                location.setY(0);
                if ((int) getXMethod.invoke(blockPosition) == location.getBlockX() &&
                        (int) getYMethod.invoke(blockPosition) == location.getBlockY() &&
                        (int) getZMethod.invoke(blockPosition) == location.getBlockZ()){

                    consumer.accept((String[]) textField.get(packet));
                    signList.remove(event.getSender().getUniqueId());
                    event.getSender().sendBlockChange(location, Material.AIR.createBlockData());
                    event.setCancelled(true);
                }
            }
        }
    }
}
