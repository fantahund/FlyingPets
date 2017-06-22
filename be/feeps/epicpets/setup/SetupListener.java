package be.feeps.epicpets.setup;

import be.feeps.epicpets.listener.events.ItemEvent;
import be.feeps.epicpets.listener.events.JoinQuitEvent;
import be.feeps.epicpets.listener.events.OthersEvent;
import be.feeps.epicpets.listener.tasks.AnimationsTask;
import be.feeps.epicpets.listener.tasks.FoodsTask;
import be.feeps.epicpets.listener.tasks.ParticlesTask;
import be.feeps.epicpets.listener.tasks.PetTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by feeps on 17/06/2017.
 */
public class SetupListener {
    private PluginManager pm = Bukkit.getPluginManager();

    public SetupListener(JavaPlugin plugin){
        pm.registerEvents(new ItemEvent(), plugin);
        pm.registerEvents(new JoinQuitEvent(), plugin);
        pm.registerEvents(new OthersEvent(), plugin);
        pm.registerEvents(new PetTask(), plugin);
        pm.registerEvents(new AnimationsTask(), plugin);
        pm.registerEvents(new ParticlesTask(), plugin);
        pm.registerEvents(new FoodsTask(), plugin);
    }
}
