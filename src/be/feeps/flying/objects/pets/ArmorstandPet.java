package be.feeps.flying.objects.pets;

import be.feeps.flying.Main;
import be.feeps.flying.entities.ArmorStandPacket;
import be.feeps.flying.inventories.EditorInventory;
import be.feeps.flying.inventories.FlyingInventory;
import be.feeps.flying.inventories.FlyingItem;
import be.feeps.flying.inventories.MainInventory;
import be.feeps.flying.utilities.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ArmorstandPet extends Pet {
    private static final Vector armorstandOffset = new Vector(-0.7, 1.2, 0.8);
    private static final Vector nameOffset = new Vector(0,0.9, 0);
    private static final ItemStack defaultHelmet = new ItemStack(Material.LEATHER_HELMET);
    private static final ItemStack defaultChest = new ItemStack(Material.LEATHER_CHESTPLATE);
    private static final ItemStack defaultLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    private static final ItemStack defaultBoots = new ItemStack(Material.LEATHER_BOOTS);

    private ArmorStandPacket armorstand;
    private Object[] equipmentsPackets;

    private Boolean isHead;
    private Integer helmetOrdinal;
    private Integer chestOrdinal;
    private Integer leggingsOrdinal;
    private Integer bootsOrdinal;

    private ItemStack helmetItem = defaultHelmet.clone();
    private ItemStack chestItem = defaultChest.clone();
    private ItemStack leggingsItem = defaultLeggings.clone();
    private ItemStack bootsItem = defaultBoots.clone();

    private boolean isInvisible = false;

    public ArmorstandPet(Player player){
        super(player, PetType.ARMORSTAND, nameOffset);
        armorstand = new ArmorStandPacket(player.getLocation());
        armorstand.setSmall(true);
        armorstand.setBasePlate(true);
        armorstand.setMarker(true);

        equipmentsPackets = new Object[6]; // LArm, RArm, Helmet, Chest, Leggings, Boots

        FlyingUtils.sendPacket(player.getWorld(), this.getInitPackets()); // TODO Check if null object creates NullPointerException
    }

    @Override
    public void update(Updater.Type type) {
        if (Updater.Type.TICK == type){
            location = player.getLocation().add(FlyingUtils.rotateYaw(armorstandOffset, player.getLocation().getYaw()));
            FlyingUtils.sendPacket(player.getWorld(), armorstand.getTeleportPacket(location));
        }
        super.update(type);
    }

    @Override
    public Object[] getInitPackets(){
        return ArrayUtils.addAll(super.getInitPackets(), ArrayUtils.addAll(new Object[]{armorstand.getSpawnPacket()}, equipmentsPackets));
    }

    @Override
    public Object[] getDestroyPackets() {
        return ArrayUtils.add(super.getDestroyPackets(), armorstand.getDestroyPacket());
    }

    @Override
    public void setData(HashMap<String, Object> data) {
        super.setData(data);
        setInvisible((boolean) data.get("invisible"));
        setHelmet((Integer) data.get("head"), (Boolean) data.get("ishead"));
        setChestplate((Integer) data.get("chest"));
        setLeggings((Integer) data.get("leggings"));
        setBoots((Integer) data.get("boots"));
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> data = super.getData();
        data.put("invisible", isInvisible);
        data.put("ishead", isHead);
        data.put("head", helmetOrdinal);
        data.put("chest", chestOrdinal);
        data.put("leggings", leggingsOrdinal);
        data.put("boots", bootsOrdinal);
        return data;
    }

    @Override
    public ArmorstandInventory getEditInv() {
        return new ArmorstandInventory();
    }


    public void setInvisible(boolean invisible){
        isInvisible = invisible;
        armorstand.setInvisible(invisible);
        FlyingUtils.sendPacket(player.getWorld(), armorstand.getMetaDataPacket());
    }


    public void setHelmet(Integer ordinal, Boolean isHead){
        this.isHead = isHead;
        helmetOrdinal = ordinal;

        if (ordinal == null){
            this.isHead = null;
            helmetItem = defaultHelmet.clone();
            removeEquipment(5);
            return;
        }

        helmetItem = !isHead ? new Item(Material.LEATHER_HELMET).color(ArmorColor.values()[ordinal].color).make() : Skin.skinsList.get(ordinal).getHead();
        equipmentsPackets[5] = armorstand.getEquipmentPacket(5, helmetItem);
        FlyingUtils.sendPacket(player.getWorld(), equipmentsPackets[5]);
    }

    public void setHelmet(Integer ordinal){
        setHelmet(ordinal, false);
    }

    public void setChestplate(Integer ordinal){
        chestOrdinal = ordinal;

        if (ordinal == null){
            chestItem = defaultChest.clone();
            removeEquipment(4);
            return;
        }

        chestItem = new Item(Material.LEATHER_CHESTPLATE).color(ArmorColor.values()[ordinal].color).make();
        equipmentsPackets[4] = armorstand.getEquipmentPacket(4, chestItem);
        FlyingUtils.sendPacket(player.getWorld(), equipmentsPackets[4]);
    }

    public void setLeggings(Integer ordinal){
        leggingsOrdinal = ordinal;

        if (ordinal == null){
            leggingsItem = defaultLeggings.clone();
            removeEquipment(3);
            return;
        }

        leggingsItem = new Item(Material.LEATHER_LEGGINGS).color(ArmorColor.values()[ordinal].color).make();
        equipmentsPackets[3] = armorstand.getEquipmentPacket(3, leggingsItem);
        FlyingUtils.sendPacket(player.getWorld(), equipmentsPackets[3]);
    }

    public void setBoots(Integer ordinal){
        bootsOrdinal = ordinal;

        if (ordinal == null){
            bootsItem = defaultBoots.clone();
            removeEquipment(2);
            return;
        }

        bootsItem = new Item(Material.LEATHER_BOOTS).color(ArmorColor.values()[ordinal].color).make();
        equipmentsPackets[2] = armorstand.getEquipmentPacket(2, bootsItem);
        FlyingUtils.sendPacket(player.getWorld(), equipmentsPackets[2]);
    }

    private void removeEquipment(Integer slot){
        equipmentsPackets[slot] = null;
        FlyingUtils.sendPacket(player.getWorld(), armorstand.getEquipmentPacket(slot, FlyingUtils.airItem));
    }
    /*
        Inventories
     */
    private class ArmorstandInventory extends EditorInventory {
        public ArmorstandInventory(){
            super(ArmorstandPet.this.player, 9*5);

            fItems.add(new FlyingItem(helmetItem, 0, event -> new ColorInventory(Material.LEATHER_HELMET, ArmorstandPet.this::setHelmet).open()));
            fItems.add(new FlyingItem(chestItem, 9, event -> new ColorInventory(Material.LEATHER_CHESTPLATE, ArmorstandPet.this::setChestplate).open()));
            fItems.add(new FlyingItem(leggingsItem, 18, event -> new ColorInventory(Material.LEATHER_LEGGINGS, ArmorstandPet.this::setLeggings).open()));
            fItems.add(new FlyingItem(bootsItem, 27, event -> new ColorInventory(Material.LEATHER_BOOTS, ArmorstandPet.this::setBoots).open()));

            fItems.add(new FlyingItem(new Item(Material.LINGERING_POTION).addEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 0,0)).make(), 16));
            fItems.add(new FlyingItem(isInvisible ? FlyingUtils.enabled : FlyingUtils.disabled, 25, event -> {
                setInvisible(!isInvisible);
                event.setCurrentItem(isInvisible ? FlyingUtils.enabled : FlyingUtils.disabled);
            }));

            fItems.add(new FlyingItem(FlyingUtils.backItem, 40, event -> new MainInventory(player).open()));
        }
    }

    private class SkinsInventory extends FlyingInventory {
        private static final int maxItems = 9*4;
        public final int pageIndex;

        public SkinsInventory(int pageIndex) {
            super(ArmorstandPet.this.player, 9*5, "Skins");
            this.pageIndex = pageIndex;

            // Pages calculation -> TODO Cache IT !
            int startIndex = pageIndex * maxItems;
            int endIndex = startIndex + maxItems;

            for (int i = startIndex; i < endIndex; i++){
                Skin skin = Skin.skinsList.get(i);
                if (skin != null)
                    fItems.add(new FlyingItem(skin.getHead(), skin.slot - startIndex, event -> setHelmet(skin.slot, true)));
            }
            // End

            // If an existing slot is behind the endIndex, it means that there are remaining items after the current page. So, create the "Next Button"
            if (Skin.skinsList.keySet().stream().anyMatch(slot -> slot >= endIndex))
                fItems.add(new FlyingItem(FlyingUtils.Head.RIGHT_ARROW.builder().name(FlyingUtils.L("inventories.previous")).make(), 44, e -> new SkinsInventory(pageIndex + 1).open()));

            // If we're not at the first page, create the "Previous Button"
            if (pageIndex > 0)
                fItems.add(new FlyingItem(FlyingUtils.Head.LEFT_ARROW.builder().name(FlyingUtils.L("inventories.next")).make(), 36, e -> new SkinsInventory(pageIndex - 1).open()));

            // Back to the ColorInventory
            fItems.add(new FlyingItem(FlyingUtils.backItem, 40, event -> new ColorInventory(Material.LEATHER_HELMET, ArmorstandPet.this::setHelmet).open()));
        }
    }

    private class ColorInventory extends FlyingInventory{
        public ColorInventory(Material mat, Consumer<Integer> callback){
            super(ArmorstandPet.this.player, 9*5, "Equip " + mat.name());

            fItems.add(new FlyingItem(new ItemStack(mat), 4));

            for (ArmorColor color : ArmorColor.values()){
                fItems.add(new FlyingItem(color.getItem(), color.slot, event -> {
                    callback.accept(color.ordinal());
                }));
            }

            if (mat == Material.LEATHER_HELMET) {
                fItems.add(new FlyingItem(new ItemStack(Material.DRAGON_HEAD), 36, event -> {
                    new SkinsInventory(0).open();
                }));
            }

            fItems.add(new FlyingItem(FlyingUtils.removeItem, 44, event -> callback.accept(null)));
            fItems.add(new FlyingItem(FlyingUtils.backItem, 40, event -> getEditInv().open()));
        }
    }

    private enum ArmorColor{
        BLACK("Black", Color.BLACK, 19, "http://textures.minecraft.net/texture/9ddebbb062f6a385a91ca05f18f5c0acbe33e2d06ee9e7416cef6ee43dfe2fb"),
        GRAY("Gray", Color.GRAY, 20, "http://textures.minecraft.net/texture/f2f085c6b3cb228e5ba81df562c4786762f3c257127e9725c77b7fd301d37"),
        WHITE("White", Color.WHITE, 21, "http://textures.minecraft.net/texture/e5a770e7e44b3a1e6c3b83a97ff6997b1f5b26550e9d7aa5d5021a0c2b6ee"),
        YELLOW("Yellow", Color.YELLOW, 22,"http://textures.minecraft.net/texture/14c4141c1edf3f7e41236bd658c5bc7b5aa7abf7e2a852b647258818acd70d8"),
        ORANGE("Orange", Color.ORANGE, 23,"http://textures.minecraft.net/texture/fea590b681589fb9b0e8664ee945b41eb3851faf66aaf48525fba169c34270"),
        FUCHSIA("Fuchsia", Color.FUCHSIA, 24,"http://textures.minecraft.net/texture/607326d31858ea57e7bc55f3e75e6c85b34ff4bfd28088f94f11eb8e0d1cf"),
        RED("Red", Color.RED, 25,"http://textures.minecraft.net/texture/97c1f1ead4d531caa4a5b0d69edbce29af789a2550e5ddbd23775be05e2df2c4"),
        PURPLE("Purple", Color.PURPLE, 29,"http://textures.minecraft.net/texture/e9352bcabfc27edb44ceb51b04786542f26a299a0529475346186ee94738f"),
        BLUE("Blue", Color.BLUE,30,"http://textures.minecraft.net/texture/c96540ce762125e398ca53d4cd9b668396d0467e128b30da5aa62be9ce060"),
        AQUA("Aqua", Color.AQUA, 32,"http://textures.minecraft.net/texture/3b19dc4d467882dbca1b5c37465f0cfc70ff1f829ecf4a865796b8e5c2809a"),
        GREEN("Green", Color.GREEN, 33,"http://textures.minecraft.net/texture/36f69f7b7538b41dc3439f3658abbd59facca366f190bcf1d6d0a026c8f96");
        public final String name;
        public final Color color;
        public final int slot;
        public final String url;
        private final ItemStack item;

        ArmorColor(String name, Color color, int slot, String url){
            this.name = name;
            this.color = color;
            this.slot = slot;
            this.url = url;
            this.item = new Item(Material.PLAYER_HEAD).name(name).skullBase64(FlyingUtils.getBase64SHead(url)).make();
        }

        public ItemStack getItem(){
            return item.clone();
        }
    }

    private static class Skin{
        private static final Map<Integer, Skin> skinsList = new HashMap<>();
        private final int slot;
        private final String name;
        private final String url;
        private final String permission;
        private final ItemStack head;

        public Skin(int slot, String name, String url){
            this.slot = slot;
            this.name = name;
            this.url = url;

            permission = "flyingpets.armorstandpet.skins." + slot;
            head = new Item(Material.PLAYER_HEAD).name(name).skullBase64(FlyingUtils.getBase64SHead(url)).make();
            skinsList.put(slot, this); // Add the initialized skin to the list
        }

        public ItemStack getHead(){
            return head.clone();
        }
    }

    public static final class Config implements Listener {
        // Useful cause we need the JavaPlugin instance and a method that is called when the server starts
        public Config(){
            File textureFile = new File(Main.getInstance().getDataFolder(), "textures.yml");
            if (!textureFile.exists())
                Main.getInstance().saveResource("textures.yml", false); // Get the default one on the jar and put it in the plugin data folder if doesn't exist

            YamlConfiguration config = YamlConfiguration.loadConfiguration(textureFile);

            config.getConfigurationSection("textures").getKeys(false).forEach((key) -> {
                String name = FlyingUtils.toColorable(config.getString("textures." + key + ".name"));
                String url = config.getString("textures." + key + ".texture");

                new Skin(Integer.parseInt(key), name, url);
            });
        }
    }
}