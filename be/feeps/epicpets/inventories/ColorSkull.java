package be.feeps.epicpets.inventories;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SkinLoader;
import org.bukkit.Color;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

/**
 * Created by feeps on 08/04/2017.
 */
public enum ColorSkull {
    BLACK(nameInCfg("Black"), Color.BLACK , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWRkZWJiYjA2MmY2YTM4NWE5MWNhMDVmMThmNWMwYWNiZTMzZTJkMDZlZTllNzQxNmNlZjZlZTQzZGZlMmZiIn19fQ=="),
    GRAY(nameInCfg("Gray"), Color.GRAY , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJmMDg1YzZiM2NiMjI4ZTViYTgxZGY1NjJjNDc4Njc2MmYzYzI1NzEyN2U5NzI1Yzc3YjdmZDMwMWQzNyJ9fX0="),
    WHITE(nameInCfg("White"), Color.WHITE , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVhNzcwZTdlNDRiM2ExZTZjM2I4M2E5N2ZmNjk5N2IxZjViMjY1NTBlOWQ3YWE1ZDUwMjFhMGMyYjZlZSJ9fX0="),
    YELLOW(nameInCfg("Yellow"), Color.YELLOW , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRjNDE0MWMxZWRmM2Y3ZTQxMjM2YmQ2NThjNWJjN2I1YWE3YWJmN2UyYTg1MmI2NDcyNTg4MThhY2Q3MGQ4In19fQ=="),
    ORANGE(nameInCfg("Orange"), Color.ORANGE , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmVhNTkwYjY4MTU4OWZiOWIwZTg2NjRlZTk0NWI0MWViMzg1MWZhZjY2YWFmNDg1MjVmYmExNjljMzQyNzAifX19"),
    FUCHSIA(nameInCfg("Fuchsia"), Color.FUCHSIA , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA3MzI2ZDMxODU4ZWE1N2U3YmM1NWYzZTc1ZTZjODViMzRmZjRiZmQyODA4OGY5NGYxMWViOGUwZDFjZiJ9fX0="),
    RED(nameInCfg("Red"), Color.RED , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTdjMWYxZWFkNGQ1MzFjYWE0YTViMGQ2OWVkYmNlMjlhZjc4OWEyNTUwZTVkZGJkMjM3NzViZTA1ZTJkZjJjNCJ9fX0="),
    PURPLE(nameInCfg("Purple"), Color.PURPLE , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkzNTJiY2FiZmMyN2VkYjQ0Y2ViNTFiMDQ3ODY1NDJmMjZhMjk5YTA1Mjk0NzUzNDYxODZlZTk0NzM4ZiJ9fX0="),
    BLUE(nameInCfg("Blue"), Color.BLUE ,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk2NTQwY2U3NjIxMjVlMzk4Y2E1M2Q0Y2Q5YjY2ODM5NmQwNDY3ZTEyOGIzMGRhNWFhNjJiZTljZTA2MCJ9fX0="),
    AQUA(nameInCfg("Aqua"), Color.AQUA , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2IxOWRjNGQ0Njc4ODJkYmNhMWI1YzM3NDY1ZjBjZmM3MGZmMWY4MjllY2Y0YTg2NTc5NmI4ZTVjMjgwOWEifX19"),
    GREEN(nameInCfg("Green"), Color.GREEN , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=");

    private Color color;
    private String texture;
    private String name;

    ColorSkull(String name, Color color, String texture){
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public String getTexture(){
        return this.texture;
    }

    public Color getColor(){
        return this.color;
    }

    public String getName(){
        return  this.name;
    }

    public void createItem(int slot, Inventory inv){
        ItemsUtil.add(SkinLoader.getCustomSkull(getTexture())
                , inv, slot, MessageUtil.translate(getName()), Arrays.asList(new String[] { "" }));
    }

    private static String nameInCfg(String path){
        return MessageUtil.translate(Main.getI().getMsgCfg().invColorSelector.get(path));
    }

}
