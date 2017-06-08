package be.feeps.epicpets.pets;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.config.SkinsConfig;
import be.feeps.epicpets.inventories.ColorSkull;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SkinLoader;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
/**
 * Created by feeps on 3/04/17.
 */
public class DefaultPet{
    private Player player;
    private EpicPetsPlayer epicPetsPlayer;
    private ArmorStand pet;
    private ArmorStand name;
    private CacheConfig cache = CacheConfig.getInstance();
    private SkinsConfig textures = SkinsConfig.getInstance();

    public DefaultPet(Player player){
        this.player = player;
        this.epicPetsPlayer = EpicPetsPlayer.instanceOf(this.player);
        this.epicPetsPlayer.removePet();
        this.epicPetsPlayer.setPet(this);
        this.create();

    }

    public void create(){


        this.pet = this.player.getWorld().spawn(getPetLoc(), ArmorStand.class);
        this.pet.setMetadata("EpicPets", new FixedMetadataValue(Main.getI(), true));
        this.pet.setBasePlate(false);
        this.pet.setArms(true);
        this.pet.setCanPickupItems(false);
        this.pet.setGravity(false);
        this.pet.setMarker(true);
        this.pet.setCustomNameVisible(false);

        cache.getData().addDefault(this.player.getUniqueId().toString() + ".player.name", this.player.getName());
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.showName", true);
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.setSmall", true);
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.setVisible", true);
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.showArms", true);
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.sneakProtect", true);
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.name", MessageUtil.translate(Main.getI().getMsgCfg().defaultNamePet));
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.head", MessageUtil.translate(textures.getData().getString("skins.1.name")));

        //Nouveau
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.helmet", "NULL");
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.chestplate", "NULL");
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.leggings", "NULL");
        cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.boots", "NULL");
        cache.getData().options().copyDefaults(true);

        cache.saveData();
        cache.reloadData();


        if(cache.getData().getBoolean(this.player.getUniqueId().toString() + ".pet.setSmall")){
            this.name = this.player.getWorld().spawn(getPetLoc().add(0,1,0), ArmorStand.class);
        }else{
            this.name = this.player.getWorld().spawn(getPetLoc().add(0,1.8,0), ArmorStand.class);
        }

        this.name.setMetadata("EpicPets", new FixedMetadataValue(Main.getI(), true));
        this.name.setMarker(true);
        this.name.setVisible(false);
        this.name.setSmall(false);
        this.name.setGravity(false);
        this.name.setArms(false);
        this.name.setBasePlate(false);

        this.updateInfo();
    }

    public void remove(){
        if(this.epicPetsPlayer.getEpicAnim() != null){
            this.epicPetsPlayer.getEpicAnim().stop();
        }

        EpicPetsPlayer.instanceOf(this.player).setPet(null);
        this.name.remove();
        this.pet.remove();
        this.epicPetsPlayer.setEpicAnim(null);
        this.epicPetsPlayer.setEpicParticles(null);
    }

    public void update() {
        this.pet.teleport(getPetLoc());

        if(cache.getData().getBoolean(this.player.getUniqueId().toString() + ".pet.setSmall")){
            this.name.teleport(getPetLoc().add(0,1,0));
        }else{
            this.name.teleport(getPetLoc().add(0,1.8,0));
        }
    }

    public void setName(String name) {
        cache.getData().set(this.player.getUniqueId().toString() + ".pet.showName", true);
        cache.saveData();
        cache.reloadData();
        this.updateInfo();
        this.name.setCustomName(name);
    }

    public void setPlayerHead(String player){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(player);
        meta.setDisplayName(ChatColor.GREEN + player);
        skull.setItemMeta(meta);

        this.pet.getEquipment().setHelmet(skull);
    }


    public void updateInfo(){
        cache.getData().set(this.player.getUniqueId().toString() + ".player.name", this.player.getName());
        this.pet.setSmall(cache.getData().getBoolean(this.player.getUniqueId().toString() + ".pet.setSmall"));

        this.name.setCustomNameVisible(cache.getData().getBoolean(this.player.getUniqueId().toString() + ".pet.showName"));
        this.name.setCustomName(cache.getData().getString(this.player.getUniqueId().toString() + ".pet.name"));

        this.pet.setVisible(cache.getData().getBoolean(this.player.getUniqueId().toString() + ".pet.setVisible"));
        this.pet.setArms(cache.getData().getBoolean(this.player.getUniqueId().toString() + ".pet.showArms"));

        for (String key : textures.getData().getConfigurationSection("skins").getKeys(false)) {
            String name = textures.getData().getString("skins." + key + ".name");
            String texture = textures.getData().getString("skins." + key + ".texture");
            if(cache.getData().getString(this.player.getUniqueId().toString() + ".pet.head").equalsIgnoreCase(MessageUtil.translate(name))){
                this.pet.setHelmet(SkinLoader.getCustomSkull(texture));
            }
        }

        //Nouveau
        for(ColorSkull color : ColorSkull.values()) {
            if (color.getName().equals(cache.getData().getString(this.player.getUniqueId().toString() + ".pet.helmet"))) {
                this.pet.setHelmet(ItemsUtil.createDyeLeather(Material.LEATHER_HELMET, color.getName(), color.getColor()));
            }
            if (color.getName().equals(cache.getData().getString(this.player.getUniqueId().toString() + ".pet.chestplate"))) {
                this.pet.setChestplate(ItemsUtil.createDyeLeather(Material.LEATHER_CHESTPLATE, color.getName(), color.getColor()));
            }
            if (color.getName().equals(cache.getData().getString(this.player.getUniqueId().toString() + ".pet.leggings"))) {
                this.pet.setLeggings(ItemsUtil.createDyeLeather(Material.LEATHER_LEGGINGS, color.getName(), color.getColor()));
            }
            if (color.getName().equals(cache.getData().getString(this.player.getUniqueId().toString() + ".pet.boots"))) {
                this.pet.setBoots(ItemsUtil.createDyeLeather(Material.LEATHER_BOOTS, color.getName(), color.getColor()));
            }
        }

    }

    public Location getPetLoc(){
        Location petLoc = this.player.getLocation();
        petLoc.setPitch(0.0F);
        petLoc.setYaw(petLoc.getYaw() + 50.0F);
        petLoc.add(petLoc.getDirection().normalize().multiply(1.2D));
        petLoc.add(0.0D, 1.3D, 0.0D);
        petLoc.setYaw(this.player.getLocation().getYaw());
        petLoc.setPitch(this.player.getLocation().getPitch());

        return petLoc;
    }

    public ArmorStand getArmorStand(){
        return this.pet;
    }

    public ArmorStand getName(){
        return this.name;
    }
}
