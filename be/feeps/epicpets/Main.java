package be.feeps.epicpets;

import be.feeps.epicpets.commands.EpicCommands;
import be.feeps.epicpets.config.MainConfig;
import be.feeps.epicpets.config.MessageConfig;
import be.feeps.epicpets.events.updater.Updater;
import be.feeps.epicpets.setup.SetupFiles;
import be.feeps.epicpets.setup.SetupListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by feeps on 3/04/17.
 */
public class Main extends JavaPlugin{

    private static Main instance;
    private final Logger logger = getLogger();
    public MainConfig mainConfig;
    public MessageConfig msgConfig;
    private static Economy econ = null;
    private static Permission perms = null;

    @Override
    public void onEnable() {
        instance = this;
        new SetupListener(this);
        new SetupFiles(this, this.getClassLoader());
        logger.log(Level.INFO, "You are currently in " + getVersion());
        logger.log(Level.INFO, "Starting the task");
        new Updater();
        logger.log(Level.INFO, "Loading commands");
        getCommand("epicpets").setExecutor(new EpicCommands());
        logger.log(Level.INFO, "Checking if Vault is installed...");
        setupEconomy();
        setupPermissions();

    }

    @Override
    public void onDisable() {
        EpicPetsPlayer.getEpicPlayers().values().forEach((epicPetsPlayer) -> {
            if(epicPetsPlayer.getPet() != null) {
                epicPetsPlayer.getPet().remove();
            }
        });
        Main.getI().getServer().getScheduler().cancelTasks(this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            logger.log(Level.INFO, "Vault is not installed!");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
            perms = rsp.getProvider();
            return perms != null;
        }
        return false;
    }

    public String getVersion() {
        return org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static Main getI() {
        return instance;
    }

    public MessageConfig getMsgCfg(){
        return msgConfig;
    }

    public Economy getEcon(){
        return econ;
    }

    public Permission getPerms(){
        return perms;
    }

    public MainConfig getMainCfg(){
        return mainConfig;
    }
}
