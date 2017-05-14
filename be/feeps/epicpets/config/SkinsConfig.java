package be.feeps.epicpets.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by feeps on 8/04/17.
 */
public class SkinsConfig {
    private static SkinsConfig instance = new SkinsConfig();
    private File locationFile;
    private FileConfiguration locationFileData;
    private Plugin plug;
    public static SkinsConfig getInstance() {
        return instance;
    }

    public void setupData(Plugin plugin){
        plug = plugin;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        if (locationFile == null) {
            locationFile = new File(plugin.getDataFolder(), "textures.yml");
        }
        locationFileData = YamlConfiguration.loadConfiguration(locationFile);
        // Looks for defaults in the jar
        Reader defConfigStream = new InputStreamReader(plugin.getResource("textures.yml"));
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            locationFileData.setDefaults(defConfig);
            locationFileData.options().copyDefaults(true);
            saveData();
        }
    }


    public void setData(String path, Object value) {
        locationFileData.set(path, value);
    }

    public Object getData(String path) {
        return locationFileData.get(path);
    }

    public boolean containsData(String path) {
        return locationFileData.contains(path);
    }

    public void saveData() {
        try {
            locationFileData.save(locationFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadData() {
        locationFileData = YamlConfiguration.loadConfiguration(locationFile);
    }

    public FileConfiguration getData() {
        return locationFileData;
    }

    public Plugin getPlugin(){
        return plug;
    }
}
