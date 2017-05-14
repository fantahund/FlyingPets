package be.feeps.epicpets.config;

import be.feeps.epicpets.utils.Skyoconfig;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by feeps on 5/04/17.
 */
public class MainConfig extends Skyoconfig {

    @ConfigOptions(name = "GiveItemOnJoin")
    public HashMap<String, String> itemOnJoin = new HashMap<String, String>() {
        {
            put("enable", "true");
            put("material" , "BONE");
            put("name", "&e&lEpicPets menu");
            put("slot", "1");
        }
    };

    public MainConfig(final File configFile) {
        super(configFile, Arrays.asList("============CONFIG.YML============ #"));
    }
}
