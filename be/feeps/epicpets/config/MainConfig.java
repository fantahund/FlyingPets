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

    @ConfigOptions(name = "enableSoundClickMenu")
    public boolean enableSoundClickMenu = true;


    @ConfigOptions(name = "ResurrectPrice")
    public int resurrectPrice = 100;

    @ConfigOptions(name = "Foods.Prices")
    public HashMap<String, Integer> FoodsPrices = new HashMap<String, Integer>() {
        {
            put("bread", 150);
            put("nutella",150);
            put("cookie", 150);
            put("cake", 150);
            put("chocolate_muffin", 150);
            put("cherry", 150);
            put("apple", 150);
            put("melon", 150);
            put("strawberry", 150);
            put("carrot", 150);
            put("taco", 150);
            put("fries", 150);
            put("hamburger", 150);
            put("popcorn", 150);
            put("sushi_roll", 150);
            put("ham", 150);
            put("lemon", 150);
            put("white_frosted_donut", 150);
            put("turkey", 150);
            put("coconut", 150);
            put("tomato", 150);
            put("orange", 150);
            put("lettuce", 150);
            put("purple_grapes", 150);
            put("pepsi", 150);
            put("coca_cola", 150);
            put("sprite", 150);
            put("mello_yello", 150);
            put("fanta", 150);
            put("mountain_dew", 150);
            put("honey_pot", 150);
            put("bacon", 150);
            put("candy_cane", 150);
            put("ham_cheese_sandwich", 150);
            put("beer", 150);
            put("bowl_of_spaghetti_and_meatballs", 150);
        }
    };

    @ConfigOptions(name = "Foods.Amounts")
    public HashMap<String, Integer> FoodsAmounts = new HashMap<String, Integer>() {
        {
            put("bread", 30);
            put("nutella",40);
            put("cookie", 10);
            put("cake", 50);
            put("chocolate_muffin", 30);
            put("cherry", 5);
            put("apple", 10);
            put("melon", 30);
            put("strawberry", 5);
            put("carrot", 15);
            put("taco", 13);
            put("fries", 20);
            put("hamburger", 60);
            put("popcorn", 45);
            put("sushi_roll", 34);
            put("ham", 52);
            put("lemon", 75);
            put("white_frosted_donut", 30);
            put("turkey", 36);
            put("coconut", 21);
            put("tomato", 20);
            put("orange", 21);
            put("lettuce", 25);
            put("purple_grapes", 75);
            put("pepsi", 43);
            put("coca_cola", 56);
            put("sprite", 12);
            put("mello_yello", 64);
            put("fanta", 30);
            put("mountain_dew", 14);
            put("honey_pot", 65);
            put("bacon", 43);
            put("candy_cane", 25);
            put("ham_cheese_sandwich", 63);
            put("beer", 24);
            put("bowl_of_spaghetti_and_meatballs", 23);
        }
    };


    public MainConfig(final File configFile) {
        super(configFile, Arrays.asList("============CONFIG.YML============ #"));
    }
}
