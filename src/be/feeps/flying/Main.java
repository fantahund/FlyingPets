package be.feeps.flying;

import be.feeps.flying.listener.commands.PetCommand;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import be.feeps.flying.utilities.Updater;
import be.feeps.flying.utilities.packets.OnPacketInEvent;
import be.feeps.flying.utilities.packets.OnPacketOutEvent;
import be.feeps.flying.utilities.packets.TinyProtocol;
import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Main extends JavaPlugin {
    private static Main instance;
    private FlyingDatabase database;
    private TinyProtocol protocol;

    @Override
    public void onEnable() {
        // Put the Main instance
        instance = this;

        // Put the default config if doesn't exist
        saveDefaultConfig();

        /*
            MySQL - Trying to connect
         */
        try {
            getLogger().info("MySQL - Connecting....");
            database = new FlyingDatabase(this);
            getLogger().info("MySQL - Connected to the database !");
        } catch (SQLException e) {
            getLogger().severe("MySQL - Failed to connect to the database ! Disabling the plugin ...");
            getPluginLoader().disablePlugin(this);
            e.printStackTrace();
            return;
        }

        // TODO Do a better comand managment
        getCommand("flyingpets").setExecutor(new PetCommand());

        // Register listeners
        FlyingUtils.listenerClass.forEach(clazz -> {
            getServer().getPluginManager().registerEvents((Listener) Reflection.getConstructor(clazz).invoke(), this);
        });

        /*
            Initiate TinyProtocol
         */
        protocol = new TinyProtocol(this){
            @Override
            public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
                // Server#isPrimaryThread() checks the current thread against the expected primary thread for the server.
                OnPacketInEvent onPacketInEvent = new OnPacketInEvent(sender, channel, packet, !Bukkit.getServer().isPrimaryThread());
                plugin.getServer().getPluginManager().callEvent(onPacketInEvent);

                if (onPacketInEvent.isCancelled())
                    return null;

                return packet;
            }

            @Override
            public Object onPacketOutAsync(Player receiver, Channel channel, Object packet) {
                OnPacketOutEvent onPacketOutEvent = new OnPacketOutEvent(receiver, channel, packet, !Bukkit.getServer().isPrimaryThread());
                plugin.getServer().getPluginManager().callEvent(onPacketOutEvent);

                if (onPacketOutEvent.isCancelled())
                    return null;

                return packet;
            }
        };

        new Updater(this); // Creating the updater when configurations files are initialized

        // If there are online players, it means it was a reload so load the player data but Sync
        database.sync();
        for (Player player : Bukkit.getOnlinePlayers())
            database.loadPlayer(FlyingPlayer.instanceOf(player), () -> {}); // No callback
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving players data...");
        database.sync();
        FlyingPlayer.getFlyingPlayers().forEach((id, fPlayer) -> {
            fPlayer.getPetManager().remove();
            database.savePlayer(fPlayer);
        });

        FlyingPlayer.getFlyingPlayers().clear();

        instance = null;
        database.close();
        protocol.close(); // I removed this part in the TinyProtocol code
    }

    public FlyingDatabase getDatabase(){
        return database;
    }

    public TinyProtocol getProtocol() {
        return protocol;
    }

    public static Main getInstance(){
        return instance;
    }
}