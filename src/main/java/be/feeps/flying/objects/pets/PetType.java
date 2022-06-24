package be.feeps.flying.objects.pets;

import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import static be.feeps.flying.utilities.FlyingUtils.L;

public enum PetType {
    WITHER(L("pets.wither"), 0, null, WitherPet.class, "http://textures.minecraft.net/texture/cdf74e323ed41436965f5c57ddf2815d5332fe999e68fbb9d6cf5c8bd4139f"),
    PIG(L("pets.pig"), 1, null, PigPet.class, "http://textures.minecraft.net/texture/621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4"),
    PARROT(L("pets.parrot"), 2, null, ParrotPet.class, "http://textures.minecraft.net/texture/707dab2cbebea539b64d5ad246f9ccc1fcda7aa94b88e59fc2829852f46071"),
    SQUID(L("pets.squid"), 9, null, SquidPet.class, "http://textures.minecraft.net/texture/01433be242366af126da434b8735df1eb5b3cb2cede39145974e9c483607bac"),
    ARMORSTAND(L("pets.armorstand"), 10, "ishead BOOL, head SMALLINT, chest TINYINT, leggings TINYINT, boots TINYINT, invisible BOOL", ArmorstandPet.class, "http://textures.minecraft.net/texture/c6e69b1c7e69bcd49ed974f5ac36ea275efabb8c649cb2b1fe9d6ea6166ec3"),
    SHEEP(L("pets.sheep"), 11, null, SheepPet.class, "http://textures.minecraft.net/texture/618cd22b7f573395202d60bb68d3ad57d7a7a2754464a15dc12ff617f26756"),
    COW(L("pets.cow"), 18, null, CowPet.class, "http://textures.minecraft.net/texture/5bc4f7477d6b5969db4c81a31b1cb7e6f3730de19b50f78c96217b9d392f097b"),
    PANDA(L("pets.panda"), 19, null, PandaPet.class, "http://textures.minecraft.net/texture/e8c6ff82ca901c71a98f95f214b66317c3702357cdebd4da92e3380083066cf6"),
    MAGMA_CUBE(L("pets.magmacube"), 20, null, MagmaCubePet.class, "http://textures.minecraft.net/texture/d0d549f367abcbea39252ba275f5c3c1cb16945fcf472123ee26d9c9dd40e"),
    CAT(L("pets.cat"), 27, null, CatPet.class, "http://textures.minecraft.net/texture/89398493d48bb67cd397651b914bd1dc1de5800869b26952e733c63e86788d"),
    FOX(L("pets.fox"), 28, null, FoxPet.class, "http://textures.minecraft.net/texture/bae216305b54f7b98fb589e4c94edfa70d085bf382cf5e8ca234f419495c7f8a"),
    TURTLE(L("pets.turtle"), 29, null, TurtlePet.class, "http://textures.minecraft.net/texture/212b58c841b394863dbcc54de1c2ad2648af8f03e648988c1f9cef0bc20ee23c"),
    SNOWMAN(L("pets.snowman"), 36, null, SnowmanPet.class, "http://textures.minecraft.net/texture/98e334e4bee04264759a766bc1955cfaf3f56201428fafec8d4bf1bb36ae6"),
    ZOMBIE(L("pets.zombie"), 37, null, ZombiePet.class, "http://textures.minecraft.net/texture/56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11"),
    FROG(L("pets.frog"), 38, null, FrogPet.class, "http://textures.minecraft.net/texture/b554e67fd2f39232f097d0f2ada71be7db8d6080ea7fda63506ab81d472c5eb"),
    PARACHUTE(L("pets.parachute"), 45, null, ParachutePet.class, "http://textures.minecraft.net/texture/cc455b303b4ce665afdc5b4e737abe01446d6ce3d571843865446f0fb1b71"),
    BAT(L("pets.bat"), 46, null, BatPet.class, "http://textures.minecraft.net/texture/9e99deef919db66ac2bd28d6302756ccd57c7f8b12b9dca8f41c3e0a04ac1cc"),
    DRAGON(L("pets.dragon"), 47, null, DragonPet.class, "http://textures.minecraft.net/texture/a1cd6d2d03f135e7c6b5d6cdae1b3a68743db4eb749faf7341e9fb347aa283b");

    private final String textureUrl;
    private final String name;
    private final String permission;
    private final int invSlot;
    private final String sqlTable;
    private final ItemStack item;
    private final Class<? extends Pet> petClass;

    PetType(String name, int invSlot, String sqlTable, Class<? extends Pet> petClass, String url) {
        this.name = name;
        this.invSlot = invSlot;
        this.sqlTable = sqlTable;
        this.textureUrl = url;
        this.petClass = petClass;
        this.permission = "flyingpets.pets." + name().toLowerCase();
        this.item = new Item(Material.PLAYER_HEAD).name(name).skullBase64(FlyingUtils.getBase64SHead(url)).make();
    }

    public int getInvSlot() {
        return invSlot;
    }

    public String getName() {
        return name;
    }

    public String getTextureUrl() {
        return textureUrl;
    }

    public Class<? extends Pet> getPetClass() {
        return petClass;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public String getPermission() {
        return permission;
    }

    @Nullable
    public String getSqlTable() {
        return sqlTable;
    }
}
