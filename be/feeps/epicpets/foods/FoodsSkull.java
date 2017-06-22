package be.feeps.epicpets.foods;

import be.feeps.epicpets.Main;
import be.feeps.epicpets.utils.ItemsUtil;
import be.feeps.epicpets.utils.MessageUtil;
import be.feeps.epicpets.utils.SkinLoader;
import be.feeps.epicpets.utils.VaultUtils;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

/**
 * Created by feeps on 10/06/2017.
 */

public enum FoodsSkull {
    BREAD(              nameInCfg("bread"),                          priceInCfg("bread"),                           foodsAmountInCfg("bread"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM0ODdkNDU3ZjkwNjJkNzg3YTNlNmNlMWM0NjY0YmY3NDAyZWM2N2RkMTExMjU2ZjE5YjM4Y2U0ZjY3MCJ9fX0="),
    NUTELLA(            nameInCfg("nutella"),                        priceInCfg("nutella"),                         foodsAmountInCfg("nutella"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE1ZGNiMmRhMDJjZjczNDgyOWUxZTI3M2UzMDI1NjE3ZDgwNzE1MTZmOTUzMjUxYjUyNTQ1ZGE4ZDNlOGRiOCJ9fX0="),
    COOKIE(             nameInCfg("cookie"),                         priceInCfg("cookie"),                          foodsAmountInCfg("cookie"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjU5MmNmOWY0MmE1YThjOTk1OTY4NDkzZmRkMWIxMWUwYjY5YWFkNjQ3M2ZmNDUzODRhYmU1OGI3ZmM3YzcifX19"),
    CAKE(               nameInCfg("cake"),                           priceInCfg("cake"),                            foodsAmountInCfg("cake"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjkxMzY1MTRmMzQyZTdjNTIwOGExNDIyNTA2YTg2NjE1OGVmODRkMmIyNDkyMjAxMzllOGJmNjAzMmUxOTMifX19"),
    CHOCOLATE_MUFFIN(   nameInCfg("chocolate_muffin"),               priceInCfg("chocolate_muffin"),                foodsAmountInCfg("chocolate_muffin"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM3OTRjNzM2ZmM3NmU0NTcwNjgzMDMyNWI5NTk2OTQ2NmQ4NmY4ZDdiMjhmY2U4ZWRiMmM3NWUyYWIyNWMifX19"),
    CHERRY(             nameInCfg("cherry"),                         priceInCfg("cherry"),                          foodsAmountInCfg("cherry"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDUyNTcwNzY5NmJjZDE1YTE3MzA1NmZhMzkyOTZlODBmZjQxMTY4YmIwYWRkNTUyZjQ1MjNlMjU1OGEzMTE5In19fQ=="),
    APPLE(              nameInCfg("apple"),                          priceInCfg("apple"),                           foodsAmountInCfg("apple"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JiMzExZjNiYTFjMDdjM2QxMTQ3Y2QyMTBkODFmZTExZmQ4YWU5ZTNkYjIxMmEwZmE3NDg5NDZjMzYzMyJ9fX0="),
    MELON(              nameInCfg("melon"),                          priceInCfg("melon"),                           foodsAmountInCfg("melon"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNmZWQ1MTRjM2UyMzhjYTdhYzFjOTRiODk3ZmY2NzExYjFkYmU1MDE3NGFmYzIzNWM4ZjgwZDAyOSJ9fX0="),
    STRAWBERRY(         nameInCfg("strawberry"),                     priceInCfg("strawberry"),                      foodsAmountInCfg("strawberry"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JjODI2YWFhZmI4ZGJmNjc4ODFlNjg5NDQ0MTRmMTM5ODUwNjRhM2Y4ZjA0NGQ4ZWRmYjQ0NDNlNzZiYSJ9fX0="),
    CARROT(             nameInCfg("carrot"),                         priceInCfg("carrot"),                          foodsAmountInCfg("carrot"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE1OTk4ODQ0M2VlNzVkMjBjNDljODlhYmFkNTU0NWJiNGZiNDUzYTQyYjAzODhiNTQ2NjM1NTY5OGJmYSJ9fX0="),
    TACO(               nameInCfg("taco"),                           priceInCfg("taco"),                            foodsAmountInCfg("taco"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThjZWQ3NGEyMjAyMWE1MzVmNmJjZTIxYzhjNjMyYjI3M2RjMmQ5NTUyYjcxYTM4ZDU3MjY5YjM1MzhjZiJ9fX0="),
    FRIES(              nameInCfg("fries"),                          priceInCfg("fries"),                           foodsAmountInCfg("fries"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBlYWNhYzQxYTllYWYwNTEzNzZlZjJmOTU5NzAxZTFiYmUxYmY0YWE2NzE1YWRjMzRiNmRjMjlhMTNlYTkifX19"),
    HAMBURGER(          nameInCfg("hamburger"),                      priceInCfg("hamburger"),                       foodsAmountInCfg("hamburger"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZlZjFjMjVmNTE2ZjJlN2Q2Zjc2Njc0MjBlMzNhZGNmM2NkZjkzOGNiMzdmOWE0MWE4YjM1ODY5ZjU2OWIifX19"),
    POPCORN(            nameInCfg("popcorn"),                        priceInCfg("popcorn"),                         foodsAmountInCfg("popcorn"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ5N2IxNDdjZmFlNTIyMDU1OTdmNzJlM2M0ZWY1MjUxMmU5Njc3MDIwZTRiNGZhNzUxMmMzYzZhY2RkOGMxIn19fQ=="),
    SUSHI_ROLL(         nameInCfg("sushi_roll"),                     priceInCfg("sushi_roll"),                      foodsAmountInCfg("sushi_roll"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUxMmYyNjc5NTNlNzZhZTY2YThkZDAyNWEzMjg2YWVjYmM2NGI0YWQ5OGVlYjEwYjNjNjdhNjlhYWUxNSJ9fX0="),
    HAM(                nameInCfg("ham"),                            priceInCfg("ham"),                             foodsAmountInCfg("ham"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjYzMzZmNWJiOTk3NWJmNTdlMTRkYjY2MTVjMTg5NmM1YzRiOWMzOWFhZDE3YjE3ZTRlZTIwYjIzMWNmNiJ9fX0="),
    LEMON(              nameInCfg("lemon"),                          priceInCfg("lemon"),                           foodsAmountInCfg("lemon"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU3ZmQ1NmNhMTU5Nzg3NzkzMjRkZjUxOTM1NGI2NjM5YThkOWJjMTE5MmM3YzNkZTkyNWEzMjliYWVmNmMifX19"),
    WHITE_FROSTED_DONUT(nameInCfg("white_frosted_donut"),            priceInCfg("white_frosted_donut"),             foodsAmountInCfg("white_frosted_donut"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA3YjhjNTFhY2VjMmE1MDhiYjJmYTY1MmZiNmU0YTA4YjE5NDg1MTU5YTA5OWY1OTgyY2NiODhkZjFmZTI3ZSJ9fX0="),
    TURKEY(             nameInCfg("turkey"),                         priceInCfg("turkey"),                          foodsAmountInCfg("turkey"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjA2NTU1NzA2YjY0MWZkYWY0MzZjMDc2NjNmOTIzYWZjNWVlNzIxNDZmOTAxOTVmYjMzN2I5ZGU3NjY1ODhkIn19fQ=="),
    COCONUT(            nameInCfg("coconut"),                        priceInCfg("coconut"),                         foodsAmountInCfg("coconut"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJjNjJmZDhlNDc0ZDA5OTQwNjA0ZjgyNzEyYTQ0YWJiMjQ5ZDYzYWZmODdmOTk4Mzc0Y2E4NDlhYjE3NDEyIn19fQ=="),
    TOMATO(             nameInCfg("tomato"),                         priceInCfg("tomato"),                          foodsAmountInCfg("tomato"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTkxNzIyMjZkMjc2MDcwZGMyMWI3NWJhMjVjYzJhYTU2NDlkYTVjYWM3NDViYTk3NzY5NWI1OWFlYmQifX19"),
    ORANGE(             nameInCfg("orange"),                         priceInCfg("orange"),                          foodsAmountInCfg("orange"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdiM2QyOTFkM2I5OWJjZDRjMzdhMTgzOWRjMTYwZDg4NWVjZDRlMjM3YjNhZWExYmFmMGFkYmIxNzc1Y2Q2NCJ9fX0="),
    LETTUCE(            nameInCfg("lettuce"),                        priceInCfg("lettuce"),                         foodsAmountInCfg("lettuce"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDc3ZGQ4NDJjOTc1ZDhmYjAzYjFhZGQ2NmRiODM3N2ExOGJhOTg3MDUyMTYxZjIyNTkxZTZhNGVkZTdmNSJ9fX0="),
    PURPLE_GRAPES(      nameInCfg("purple_grapes"),                  priceInCfg("purple_grapes"),                   foodsAmountInCfg("purple_grapes"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWU1OTM1ODYzYzUzYTk5NmY1MzM0ZTkwZjU1ZGU1MzhlODNmZmM1ZjZiMGI4ZTgzYTRkYzRmNmU2YjEyMDgifX19"),
    PEPSI(              nameInCfg("pepsi"),                          priceInCfg("pepsi"),                           foodsAmountInCfg("pepsi"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJiYWU2ZGY5OWRjODJiZWFmNDlkMDY0ZGY3NGExYmJjMTVlOGUzNzY1MzMyNzY5MTJjOGM4ZmU1OWNiNGY0In19fQ=="),
    COCA_COLA(          nameInCfg("coca_cola"),                      priceInCfg("coca_cola"),                       foodsAmountInCfg("coca_cola"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNiMDFmYjJmNmJhNDdjOWQ3NjM4NDkxZjM3Y2Q4NTgyYTkzNzczMTE4NmRmNGQxZWNjZDU5YjY1YmYzNyJ9fX0="),
    SPRITE(             nameInCfg("sprite"),                         priceInCfg("sprite"),                          foodsAmountInCfg("sprite"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhhMzRkODZhN2JiMTNkNDVhZmRjNTBkM2RjZTVlZWQ5NWUxODQ0ZmJkZWUwY2NhNzUzYzZkMzM0NmUzMzllIn19fQ=="),
    MELLO_YELlO(        nameInCfg("mello_yello"),                    priceInCfg("mello_yello"),                     foodsAmountInCfg("mello_yello"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjg2YjUxZmIzMGI1MTM4YTQzNDRjYzNlNjM5N2RhMjhkZjM5NjI0MTM0MWJlOTIxMjFkNWJhZWVmOTk3ZmI0In19fQ=="),
    FANTA(              nameInCfg("fanta"),                          priceInCfg("fanta"),                           foodsAmountInCfg("fanta"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJlOTUwNWEzOGExNGQxNTEyYzc4OTJmYzQ0ZDNkN2NlNjMzOGIxYmYwZjkxMjM3MjFiMTIxYTE0YjA5NWEzIn19fQ=="),
    MOUNTAIN_DEW(       nameInCfg("mountain_dew"),                   priceInCfg("mountain_dew"),                    foodsAmountInCfg("mountain_dew"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlNWJmNjU3YWI4OTdhZDVlNTQ4NjdhNGMzYzJlNzFiMmRhMjRlNzUxOGIyZjgzNDQ4OGRhNzZmNjJmNTIxNiJ9fX0="),
    HONEY_POT(          nameInCfg("honey_pot"),                      priceInCfg("honey_pot"),                       foodsAmountInCfg("honey_pot"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThjMmNkZGVlZDYyNGE1MzhmMzQ5ZDhjNzAzNWZlZjkxOGU2ZDdhMTc4MTYzMWVhYzUxZWMxODI3MTJhNTRkYSJ9fX0="),
    BACON(              nameInCfg("bacon"),                          priceInCfg("bacon"),                           foodsAmountInCfg("bacon"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTdiYTIyZDVkZjIxZTgyMWE2ZGU0YjhjOWQzNzNhM2FhMTg3ZDhhZTc0ZjI4OGE4MmQyYjYxZjI3MmU1In19fQ=="),
    CANDY_CANE(         nameInCfg("candy_cane"),                     priceInCfg("candy_cane"),                      foodsAmountInCfg("candy_cane"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNjM2Y3ODFjOTIzYTI4ODdmMTRjMWVlYTExMDUwMTY2OTY2ZjI2MDI1Nzg0MDFmMTQ1MWU2MDk3Yjk3OWRmIn19fQ=="),
    HAM_CHEESE_SANDWICH(nameInCfg("ham_cheese_sandwich"),            priceInCfg("ham_cheese_sandwich"),             foodsAmountInCfg("ham_cheese_sandwich"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFlZTg0ZDE5Yzg1YWZmNzk2Yzg4YWJkYTIxZWM0YzkyYzY1NWUyZDY3YjcyZTVlNzdiNWFhNWU5OWVkIn19fQ=="),
    BEER(               nameInCfg("beer"),                           priceInCfg("beer"),                            foodsAmountInCfg("beer"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA1M2UyNjg2N2JiNTc1MzhlOTc4OTEzN2RiYmI1Mzc3NGUxOGVkYTZmZWY1MWNiMmVkZjQyNmIzNzI2NCJ9fX0="),
    BOWL_OF_SPAGHETTI(  nameInCfg("bowl_of_spaghetti_and_meatballs"),priceInCfg("bowl_of_spaghetti_and_meatballs"), foodsAmountInCfg("bowl_of_spaghetti_and_meatballs"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ3ZmU2NWViNzQ1NDY4ZTg2ODczYTFiZGE0OGE1YTQ4OWZlZjkxY2M1MjJkODVlMDM2NGI1NWQ1M2Y4NjdlIn19fQ==");

    private String texture;
    private String name;
    private int price;
    private int amount;

    FoodsSkull(String name, int price,int amount, String texture){
        this.name = name;
        this.texture = texture;
        this.price = price;
        this.amount = amount;
    }

    private static String nameInCfg(String path){
        return MessageUtil.translate(Main.getI().getMsgCfg().invFoods.get(path));
    }

    private static int priceInCfg(String path){
        return Main.getI().getMainCfg().FoodsPrices.get(path);
    }

    private static int foodsAmountInCfg(String path){
        return Main.getI().getMainCfg().FoodsAmounts.get(path);
    }

    public String getTexture(){
        return this.texture;
    }

    public String getName(){
        return  this.name;
    }

    public int getPrice(){
        return this.price;
    }

    public int getAmount(){
        return this.amount;
    }

    public void createItem(int slot, Inventory inv){
        if(!VaultUtils.isEconNull()){
            ItemsUtil.add(SkinLoader.getCustomSkull(getTexture())
                    , inv, slot, MessageUtil.translate(getName()), Arrays.asList(MessageUtil.translate(Main.getI().getMsgCfg().price).replace("%price%", String.valueOf(this.price)),
                            MessageUtil.translate(Main.getI().getMsgCfg().amountFood).replace("%food%",String.valueOf(amount))));
        }else{
            ItemsUtil.add(SkinLoader.getCustomSkull(getTexture())
                    , inv, slot, MessageUtil.translate(getName()), Arrays.asList(MessageUtil.translate(Main.getI().getMsgCfg().amountFood).replace("%food%",String.valueOf(amount))));
        }
    }

}
