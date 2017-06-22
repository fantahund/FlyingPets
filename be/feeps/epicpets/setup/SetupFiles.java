package be.feeps.epicpets.setup;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.animations.EpicAniFiles;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.config.MainConfig;
import be.feeps.epicpets.config.MessageConfig;
import be.feeps.epicpets.config.SkinsConfig;
import be.feeps.epicpets.skins.EpicSkins;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * Created by feeps on 17/06/2017.
 */
public class SetupFiles {

    private SkinsConfig textures = SkinsConfig.getInstance();
    private CacheConfig cache = CacheConfig.getInstance();

    public SetupFiles(JavaPlugin plugin, ClassLoader classLoader){
        plugin.getLogger().log(Level.INFO, "Loading files");
        cache.setupData(plugin);
        textures.setupData(plugin);

        try {
            Main.getI().mainConfig = new MainConfig(new File(plugin.getDataFolder(), "config.yml"));
            Main.getI().mainConfig.load();
            Main.getI().msgConfig = new MessageConfig(new File(plugin.getDataFolder(), "message.yml"));
            Main.getI().msgConfig.load();

        } catch(final InvalidConfigurationException ex) {
            ex.printStackTrace();
            plugin.getLogger().log(Level.SEVERE, "Something went wrong while loading the configuration !");
            Bukkit.getPluginManager().disablePlugin(plugin);

        }

        EpicSkins.loadSkins();
        EpicAniFiles.loadAnimations();

        Path aniDirectory = Paths.get("plugins/EpicPets/Animations/");

        if(EpicAniFiles.listAnimations.size() == 0){
            InputStream source = classLoader.getResourceAsStream("be/feeps/epicpets/animations/walk.epicpetsani");
            Path destination = Paths.get("plugins/EpicPets/Animations/walk.epicpetsani");
            try {
                Files.createDirectory(aniDirectory);
                Files.copy(source, destination);
            }catch (IOException e){ e.printStackTrace(); }
            EpicAniFiles.loadAnimations();
        }
    }
}
