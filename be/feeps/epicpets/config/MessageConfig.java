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

    @ConfigOptions(name = "inventories.items.stainedGlassPaneItem")
    public String stainedGlassPaneItem = "&3EpicPets";

    @ConfigOptions(name = "inventories.items.enabled")
    public String enabledItem = "&aEnable";

    @ConfigOptions(name = "inventories.items.disabled")
    public String disabledItem = "&cDisable";

    @ConfigOptions(name = "inventories.items.enabledItem")
    public String enabledItemLore = "&7Click to enable";

    @ConfigOptions(name = "inventories.items.disabledItem")
    public String disabledItemLore = "&7Click to disable";

    @ConfigOptions(name = "inventories.AnimationsInv.name")
    public String invNameAnimations = "&7Animations";
    @ConfigOptions(name = "inventories.AnimationsInv.items")
    public HashMap<String, String> invAnimations = new HashMap<String, String>() {
        {
            put("Back", "&7Back");
            put("Reset", "&cReset animation");
        }
    };

    @ConfigOptions(name = "inventories.SkinsInv.name")
    public String invNameSkins = "&7Skins";
    @ConfigOptions(name = "inventories.SkinsInv.items")
    public HashMap<String, String> invSkins = new HashMap<String, String>() {
        {
            put("PlayerHead", "&6Use a player head");
            put("Back", "&7Back");
        }
    };

    @ConfigOptions(name = "inventories.MainInv.name")
    public String invNameMain = "&7EpicPets";
    @ConfigOptions(name = "inventories.MainInv.items")
    public HashMap<String, String> invMain = new HashMap<String, String>() {
        {
            put("RemovePet", "&cRemove your pet");
            put("SpawnPet", "&fSpawn your pet");
            put("Preferences", "&7Preferences");
            put("Name", "&6Name your pet");
            put("Particles", "&eAnimations");
            put("Helmet", "&6Helmet");
            put("Chestplate", "&6Chestplate");
            put("Leggings", "&6Leggings");
            put("Boots", "&6Boots");
        }
    };

    @ConfigOptions(name = "inventories.PreferencesInv.name")
    public String invNamePreferences = "&7Preferences";
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
    public String invNameColorSelector = "&7Select a color";
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

    @ConfigOptions(name = "pet.msgLongNameError")
    public String msgLongNameError = prefix+"&cThe name is too long";

    @ConfigOptions(name = "pet.msgNoNameError")
    public String msgNoNameError = prefix+"&cYou must give a name";

    @ConfigOptions(name = "pet.msgDefaultNamePet")
    public String defaultNamePet = "&lNo name";

    @ConfigOptions(name = "messages.msgConsoleError")
    public String msgConsoleError = prefix+"You must be a player to run this command";

    @ConfigOptions(name = "messages.msgSpawnPet")
    public String msgSpawnPet = prefix+"&aYour pet has appeared";

    @ConfigOptions(name = "messages.msgRemovePet")
    public String msgRemovePet = prefix+"&cYour pet has been dispared";

    @ConfigOptions(name = "messages.msgInvalidArgs")
    public String msgInvalidArgs = prefix+"&cIncorrect arguments.Type /epicpets help if you need help";

    @ConfigOptions(name = "messages.noPermission")
    public String noPerm = "&cYou don't have the permission";

    public MessageConfig(final File configFile) {
        super(configFile, Arrays.asList("============MESSAGE.YML============ #"));
    }
}
