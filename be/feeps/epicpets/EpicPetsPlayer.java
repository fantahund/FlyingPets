package be.feeps.epicpets;

import be.feeps.epicpets.animations.EpicAnimations;
import be.feeps.epicpets.inventories.EpicInventory;
import be.feeps.epicpets.pets.DefaultPet;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by feeps on 4/04/17.
 */
public class EpicPetsPlayer {
    private static Map<UUID, EpicPetsPlayer> epicPlayers = new HashMap();
    private Player player;
    private DefaultPet pet;
    private EpicInventory epicInv;
    private EpicAnimations epicAnim;

    public EpicPetsPlayer(Player paramPlayer) {
        this.player = paramPlayer;

    }

    public static EpicPetsPlayer instanceOf(Player paramPlayer) {
        //JAVA 8 METHOD
        //epicPlayers.putIfAbsent(paramPlayer.getUniqueId(), new EpicPetsPlayer(paramPlayer));
        if(!epicPlayers.containsKey(paramPlayer.getUniqueId())){
            epicPlayers.put(paramPlayer.getUniqueId(), new EpicPetsPlayer(paramPlayer));
        }

        if (epicPlayers.containsKey(paramPlayer.getUniqueId())) {
            (epicPlayers.get(paramPlayer.getUniqueId())).updatePlayer(paramPlayer);
        }
        return epicPlayers.get(paramPlayer.getUniqueId());
    }

    public Player getPlayer(){
        return this.player;
    }

    public DefaultPet getPet(){
        return this.pet;
    }

    public void setPet(DefaultPet pet){
        this.pet = pet;
    }

    public EpicInventory getInventory(){
        return this.epicInv;
    }

    public void setInv(EpicInventory epicInv){
        this.epicInv = null;
        this.epicInv = epicInv;
    }

    public EpicAnimations getAnim(){
        return this.epicAnim;
    }

    public void setAnim(EpicAnimations epicAnim){
        this.epicAnim = null;
        this.epicAnim = epicAnim;
    }


    public static Map<UUID, EpicPetsPlayer>  getEpicPlayers(){
        return epicPlayers;
    }

    public void updatePlayer(Player player){
        this.player = player;
    }

    public void removePet(){
        if(this.pet != null){
            this.pet.remove();
        }
        this.pet = null;

    }
}
