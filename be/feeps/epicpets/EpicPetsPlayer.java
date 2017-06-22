package be.feeps.epicpets;

import be.feeps.epicpets.animations.EpicAnimations;
import be.feeps.epicpets.foods.EpicFoods;
import be.feeps.epicpets.inventories.EpicInventories;
import be.feeps.epicpets.particles.EpicParticles;
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
    private EpicAnimations epicAnim;
    private EpicParticles epicParticles;
    private EpicInventories epicInventories;
    private EpicFoods foods;

    public EpicPetsPlayer(Player player) {
        this.player = player;
    }

    public static EpicPetsPlayer instanceOf(Player player) {
        epicPlayers.putIfAbsent(player.getUniqueId(), new EpicPetsPlayer(player));
        if (epicPlayers.containsKey(player.getUniqueId())) {
            (epicPlayers.get(player.getUniqueId())).updatePlayer(player);
        }
        return epicPlayers.get(player.getUniqueId());
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public DefaultPet getPet() {
        return this.pet;
    }

    public void setPet(DefaultPet pet) {
        this.pet = pet;
    }

    public EpicInventories getEpicInv() {
        return this.epicInventories;
    }

    public void setEpicInv(EpicInventories epicInv) {
        this.epicInventories = null;
        this.epicInventories = epicInv;
    }

    public EpicAnimations getEpicAnim() {
        return this.epicAnim;
    }

    public void setEpicAnim(EpicAnimations epicAnim) {
        this.epicAnim = null;
        this.epicAnim = epicAnim;
    }

    public EpicFoods getFoods()
    {
        return this.foods;
    }

    public void setFoods(EpicFoods epicParticles) {
        this.foods = null;
        this.foods = epicParticles;
    }

    public EpicParticles getEpicParticles() {
        return this.epicParticles;
    }

    public void setEpicParticles(EpicParticles epicParticles) {
        this.epicParticles = null;
        this.epicParticles = epicParticles;
    }

    public static Map<UUID, EpicPetsPlayer>  getEpicPlayers() {
        return epicPlayers;
    }

    public void updatePlayer(Player player) {
        this.player = player;
    }

    public void removePet() {
        if(this.pet != null){
            this.pet.remove();
        }
        this.pet = null;
    }
}
