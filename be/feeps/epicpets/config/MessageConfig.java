package be.feeps.epicpets.config;

import be.feeps.epicpets.utils.Skyoconfig;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
/**
 * Created by feeps on 5/04/17.
 */
public class MessageConfig extends Skyoconfig {

    @ConfigOptions(name = "prefix")
    public String prefix = "&6EpicPets &7|&r ";

    @ConfigOptions(name = "inventories.items.enabled")
    public String enabledItem = "&aEnable";

    @ConfigOptions(name = "inventories.items.disabled")
    public String disabledItem = "&cDisable";

    @ConfigOptions(name = "inventories.items.enabledItem")
    public String enabledItemLore = "&7Click to enable";

    @ConfigOptions(name = "inventories.items.disabledItem")
    public String disabledItemLore = "&7Click to disable";

    @ConfigOptions(name = "inventories.AnimationsInv.name")
    public String invNameAnimations = "&8Animations";
    @ConfigOptions(name = "inventories.AnimationsInv.items")
    public HashMap<String, String> invAnimations = new HashMap<String, String>() {
        {
            put("Back", "&7Back");
            put("Reset", "&cReset animation");
        }
    };

    @ConfigOptions(name = "inventories.ParticlesInv.name")
    public String invNameParticles = "&8Particles";
    @ConfigOptions(name = "inventories.ParticlesInv.items")
    public HashMap<String, String> invParticles = new HashMap<String, String>() {
        {
            put("Love","&cLove");
            put("FrostLord","&fFrostLord");
            put("BloodHelix","&cBloodHelix");
            put("Sparks","&aSparks");
            put("Ring","&dRing");
            put("Back", "&7Back");
            put("Reset", "&cReset your effect");
        }
    };

    @ConfigOptions(name = "inventories.TakeCare.name")
    public String invNameTakeCare = "&8Taking care of the pet";
    @ConfigOptions(name = "inventories.TakeCare.items")
    public HashMap<String, String> invTakeCare = new HashMap<String, String>() {
        {
            put("Name", "&eName your pet");
            put("Preferences", "&7Preferences");
            put("Foods", "&6Feed your pet");
            put("Back", "&7Back");
        }
    };


    @ConfigOptions(name = "inventories.SkinsInv.name")
    public String invNameSkins = "&8Skins";
    @ConfigOptions(name = "inventories.SkinsInv.items")
    public HashMap<String, String> invSkins = new HashMap<String, String>() {
        {
            put("Back", "&7Back");
            put("PlayerHead", "&6Use a player head");
        }
    };

    @ConfigOptions(name = "inventories.MainInv.name")
    public String invNameMain = "&8EpicPets";
    @ConfigOptions(name = "inventories.MainInv.items")
    public HashMap<String, String> invMain = new HashMap<String, String>() {
        {
            put("RemovePet", "&cRemove your pet");
            put("SpawnPet", "&fSpawn your pet");
            put("ResurrectPet", "&fResurrect your pet");
            put("Animations", "&eAnimations");
            put("Helmet", "&6Helmet");
            put("TakeCare", "&6Taking care of the pet");
            put("Particles", "&6Particles");
            put("Chestplate", "&6Chestplate");
            put("Leggings", "&6Leggings");
            put("Boots", "&6Boots");
        }
    };

    @ConfigOptions(name = "inventories.PreferencesInv.name")
    public String invNamePreferences = "&8Preferences";
    @ConfigOptions(name = "inventories.PreferencesInv.items")
    public HashMap<String, String> invPreferences = new HashMap<String, String>() {
        {
            put("SizePet", "&eMake the pet small");
            put("ShowName", "&eShow the name");
            put("ShowArmorStand", "&eShow the armorstand");
            put("ShowArms", "&eShow the arms");
            put("SneakProtect","&eDisable name on sneak");
            put("Back", "&7Back");
        }
    };

    @ConfigOptions(name = "inventories.ColorSelectorInv.name")
    public String invNameColorSelector = "&8Select a color";
    @ConfigOptions(name = "inventories.ColorSelectorInv.items")
    public HashMap<String, String> invColorSelector = new HashMap<String, String>() {
        {
            put("CurrentItem", "&6Current item");
            put("Skins", "&6Skins");
            put("Back", "&7Back");
            put("Reset", "&cReset the current item");
            put("Black", "&0Black");
            put("Gray", "&7Gray");
            put("White", "&fWhite");
            put("Yellow", "&eYellow");
            put("Orange", "&6Orange");
            put("Fuchsia", "&dFuschia");
            put("Red", "&cRed");
            put("Purple", "&5Purple");
            put("Blue", "&1Blue");
            put("Aqua", "&bAqua");
            put("Green", "&2Green");
        }
    };

    @ConfigOptions(name = "inventories.FoodsInv.name")
    public String invNameFoods = "&8Foods";
    @ConfigOptions(name = "inventories.FoodsInv.items")
    public HashMap<String, String> invFoods = new HashMap<String, String>() {
        {
            put("Back", "&7Back");
            put("bread", "&6Bread");
            put("nutella", "&7Nutella");
            put("cookie", "&cCookie");
            put("cake", "&fCake");
            put("chocolate_muffin", "&8Chocolate Muffin");
            put("cherry", "&cCherry");
            put("apple", "&aApple");
            put("melon", "&aMelon");
            put("strawberry", "&cStrawberry");
            put("carrot", "&6Carrot");
            put("taco", "&fTaco");
            put("fries", "&eFries");
            put("hamburger", "&6Hamburger");
            put("popcorn", "&ePopcorn");
            put("sushi_roll", "&0Sushi Roll");
            put("ham", "&dHam");
            put("lemon", "&eLemon");
            put("white_frosted_donut", "&aWhite Frosted Donut");
            put("turkey", "&cTurkey");
            put("coconut", "&6Coconut");
            put("tomato", "&cTomato");
            put("orange", "&6Orange");
            put("lettuce", "&bLettuce");
            put("purple_grapes", "&aPurple Grapes");
            put("pepsi", "&cPepsi");
            put("coca_cola", "&cCoca Cola");
            put("sprite", "&2Sprite");
            put("mello_yello", "&cMello Yello");
            put("fanta", "&6Fanta");
            put("mountain_dew", "&cMountain Dew");
            put("honey_pot", "&aHoney Pot");
            put("bacon", "&cBacon");
            put("candy_cane", "&4Candy Cane");
            put("ham_cheese_sandwich", "&fHam Cheese Sandwich");
            put("beer", "&6Beer");
            put("bowl_of_spaghetti_and_meatballs", "&cBowl of Spaghetti and Meatballs");
        }
    };

    @ConfigOptions(name = "messages.msgLongNameError")
    public String msgLongNameError ="&cThe name is too long";

    @ConfigOptions(name = "messages.msgNoNameError")
    public String msgNoNameError ="&cYou must give a name";

    @ConfigOptions(name = "messages.msgDefaultNamePet")
    public String defaultNamePet = "&lNo name";

    @ConfigOptions(name = "messages.msgConsoleError")
    public String msgConsoleError = "You must be a player to run this command";

    @ConfigOptions(name = "messages.msgSpawnPet")
    public String msgSpawnPet = "&aYour pet has appeared";

    @ConfigOptions(name = "messages.msgRemovePet")
    public String msgRemovePet ="&cYour pet has been dispared";

    @ConfigOptions(name = "messages.msgInvalidArgs")
    public String msgInvalidArgs = "&cIncorrect arguments.Type /epicpets help if you need help";

    @ConfigOptions(name = "messages.noPermission")
    public String noPerm = "&cYou don't have the permission";

    @ConfigOptions(name = "messages.petDead")
    public String deadPet = "&cYour pet is dead";

    @ConfigOptions(name = "messages.noMoney")
    public String noMoney = "&cYou do not have enough money";

    @ConfigOptions(name = "messages.purchase")
    public String purchase = "&aYour purchase has been made";

    @ConfigOptions(name = "messages.resurrectedPet")
    public String resurrectedPet = "&aYou resurrected your pet.";

    @ConfigOptions(name = "messages.petHungry")
    public String petHungry = "&cYour pet is hungry";

    @ConfigOptions(name = "lores.price")
    public String price = "&7Price:&6 %price%$";

    @ConfigOptions(name = "lores.hasPermission")
    public String hasPermissionLore = "&aYou have the permission";

    @ConfigOptions(name = "lores.noPermission")
    public String noPermissionLore = "&cYou don't have the permission";

    @ConfigOptions(name = "lores.foodsStatus")
    public String foodsStatus = "&7Level of hunger: %bar%";

    @ConfigOptions(name = "lores.AmountFood")
    public String amountFood = "&7Amount of food:&6 %food%";



    public MessageConfig(final File configFile) {
        super(configFile, Arrays.asList("============MESSAGE.YML============ #"));
    }
}
