package be.feeps.epicpets;

import be.feeps.epicpets.commands.EpicCommands;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.config.MainConfig;
import be.feeps.epicpets.config.MessageConfig;
import be.feeps.epicpets.config.SkinsConfig;
import be.feeps.epicpets.listener.events.EventsRegister;
import be.feeps.epicpets.listener.tasks.FollowOwnerTask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by feeps on 3/04/17.
 */
public class Main extends JavaPlugin{

    private FollowOwnerTask followOwnerTask;
    private static Main instance;
    private MainConfig mainConfig;
    private MessageConfig msgConfig;
    private SkinsConfig textures = SkinsConfig.getInstance();
    private CacheConfig cache = CacheConfig.getInstance();

    @Override
    public void onEnable() {
        final Logger logger = getLogger();

        //Démarrage du plugin 'EpicPets'
        instance = this;
        logger.log(Level.INFO, "You are currently in " + getVersion());
        EventsRegister.registerEvents(this);
        logger.log(Level.INFO, "Starting the task");
        this.followOwnerTask = new FollowOwnerTask();
        this.followOwnerTask.runTaskTimerAsynchronously(this,1L, 1L);

        logger.log(Level.INFO, "Loading commands");
        getCommand("epicpets").setExecutor(new EpicCommands());

        cache.setupData(this);
        textures.setupData(this);

        File animationFile = new File("plugins/EpicPets/Animations/");
        if (!animationFile.exists()) {
            animationFile.mkdirs();
        }

        File[] listOfFiles = animationFile.listFiles();
        if(listOfFiles.length == 0){
            InputStream defaultAnimation = getClassLoader().getResourceAsStream("be/feeps/epicpets/animations/walk.epicpetsani");
            try{
                FileOutputStream fos = new FileOutputStream("plugins/EpicPets/Animations/walk.epicpetsani");
                byte[] buf = new byte[2048];
                int r;
                while(-1 != (r = defaultAnimation.read(buf))) {
                    fos.write(buf, 0, r);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        try {
            mainConfig = new MainConfig(new File(this.getDataFolder(), "config.yml"));
            mainConfig.load();
            msgConfig = new MessageConfig(new File(this.getDataFolder(), "message.yml"));
            msgConfig.load();

        } catch(final InvalidConfigurationException ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Something went wrong while loading the configuration !");
            Bukkit.getPluginManager().disablePlugin(this);

        }
    }

    @Override
    public void onDisable() {
        System.out.print("[EpicPets] Removal of pets ");

        for(EpicPetsPlayer epicPlayer : EpicPetsPlayer.getEpicPlayers().values()){
            if(epicPlayer.getPet() != null) {
                epicPlayer.getPet().remove();
            }
        }
        this.followOwnerTask.cancel();
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
    public MainConfig getMainCfg(){
        return mainConfig;
    }



}
