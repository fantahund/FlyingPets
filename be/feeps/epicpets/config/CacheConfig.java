package be.feeps.epicpets.config;

/**
 * Created by feeps on 6/04/17.
 */
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CacheConfig {
    private static CacheConfig instance = new CacheConfig();
    private File locationFile;
    private FileConfiguration locationFileData;
    private Plugin plug;
    public static CacheConfig getInstance() {
        return instance;
    }

    public void setupData(Plugin plugin){
        plug = plugin;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        if (locationFile == null) {
            locationFile = new File(plugin.getDataFolder(), "cache.yml");
        }
        locationFileData = YamlConfiguration.loadConfiguration(locationFile);
        // Looks for defaults in the jar
        Reader defConfigStream = new InputStreamReader(plugin.getResource("cache.yml"));
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            locationFileData.setDefaults(defConfig);
            //locationFileData.options().copyDefaults(true);
            saveData();
        }
    }

    public void doOpposite(Player player, String path){
        if(getData().getBoolean(player.getUniqueId().toString() + ".pet." + path)){
            CacheConfig.getInstance().getData().set( player.getUniqueId().toString() + ".pet." + path, false);
        }else{
            CacheConfig.getInstance().getData().set( player.getUniqueId().toString() + ".pet." + path, true);
        }
        saveData();
        reloadData();
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

