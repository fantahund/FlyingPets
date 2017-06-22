package be.feeps.epicpets.skins;

import be.feeps.epicpets.config.SkinsConfig;

import java.util.ArrayList;

/**
 * Created by feeps on 16/06/2017.
 */
public class EpicSkins {
    public static ArrayList<EpicSkins> skinsList = new ArrayList<>();
    private String name;
    private String texture;
    private String permission;
    private int slot;
    private int price;

    public EpicSkins(String name, String texture, String permission, int slot, int price){
        this.name = name;
        this.texture= texture;
        this.permission = permission;
        this.slot = slot;
        this.price = price;
        skinsList.add(this);
    }

    public static void loadSkins(){
        SkinsConfig.getInstance().getData().getConfigurationSection("skins").getKeys(false).forEach((key) ->{
            String name = SkinsConfig.getInstance().getData().getString("skins." + key + ".name");
            String texture = SkinsConfig.getInstance().getData().getString("skins." + key + ".texture");
            String slot = SkinsConfig.getInstance().getData().getString("skins." + key + ".slot");
            String permission = SkinsConfig.getInstance().getData().getString("skins." + key + ".permission");
            String price = SkinsConfig.getInstance().getData().getString("skins." + key + ".price");

            if(price == null){
                new EpicSkins(name,texture,permission, Integer.parseInt(slot), 0);
            }else{
                new EpicSkins(name,texture,permission, Integer.parseInt(slot), Integer.parseInt(price));
            }

        });
    }

    public String getName() {
        return this.name;
    }

    public int getSlot() {
        return this.slot;
    }

    public int getPrice(){
        return this.price;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getTexture() {
        return this.texture;
    }
}
