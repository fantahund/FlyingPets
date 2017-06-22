package be.feeps.epicpets.foods;

import be.feeps.epicpets.EpicPetsPlayer;
import be.feeps.epicpets.Main;
import be.feeps.epicpets.config.CacheConfig;
import be.feeps.epicpets.utils.MathUtils;
import be.feeps.epicpets.utils.MessageUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

/**
 * Created by feeps on 10/06/2017.
 */
public class EpicFoods {

    private Player player;
    private EpicPetsPlayer epicPetsPlayer;
    private int food;
    private CacheConfig cache = CacheConfig.getInstance();

    public EpicFoods(Player player){
        this.player = player;
        this.epicPetsPlayer = EpicPetsPlayer.instanceOf(player);
        this.cache.getData().addDefault(this.player.getUniqueId().toString() + ".pet.foods", 100);
        this.food = cache.getData().getInt(this.player.getUniqueId().toString() + ".pet.foods");
        this.epicPetsPlayer.setFoods(this);
    }

    /**
     * @return the percentage of the food in a bar
     */
    public String getBar() {
        //    x*30:100
        return "§a§l" + StringUtils.repeat("|", ((this.food*30/100))) + "§c§l" + StringUtils.repeat("|", (30 - (this.food*30/100)));
    }

    public void addFoods(int amount){
        this.food = this.food + amount;
        if(this.food > 100){
            this.food = 100;
        }
        this.cache.setData(this.player.getUniqueId().toString() + ".pet.foods", this.food);
        this.cache.saveData();
        this.cache.reloadData();
    }

    public void setFoods(int amount){
        this.food = amount;
        this.cache.setData(this.player.getUniqueId().toString() + ".pet.foods", this.food);
        this.cache.saveData();
        this.cache.reloadData();
    }


    public void update(){
        this.cache.setData(this.player.getUniqueId().toString() + ".pet.foods", this.food);
        if(this.food > 0){
            if(MathUtils.random(0,1) == MathUtils.random(0,1)){
                this.food--;
            }
            if(food < 10){
                player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().petHungry));
            }
        }else{
            this.epicPetsPlayer.getPet().remove();
            this.cache.setData(this.player.getUniqueId().toString() + ".pet.isDead", true);
            player.sendMessage(MessageUtil.translate(Main.getI().getMsgCfg().prefix + Main.getI().getMsgCfg().deadPet));
        }
        this.cache.saveData();
        this.cache.reloadData();
    }

    /**
     * Get the percentage of the food
     * @return int
     */
    public int getFood(){
        return this.food;
    }
}
