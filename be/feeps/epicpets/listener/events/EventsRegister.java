package be.feeps.epicpets.listener.events;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.inventories.EpicInventory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * Created by feeps on 3/04/17.
 */
public class EventsRegister {
    public static void registerEvents(Main pl) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new ItemEvent(), pl);
        pm.registerEvents(new JoinQuitEvent(), pl);
        pm.registerEvents(new OthersEvent(), pl);
        pm.registerEvents(EpicInventory.getListener(), pl);
    }
}
