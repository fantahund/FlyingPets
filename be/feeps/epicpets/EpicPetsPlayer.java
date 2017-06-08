package be.feeps.epicpets;

import be.feeps.epicpets.animations.EpicAnimations;
import be.feeps.epicpets.inventories.EpicInventory;
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
    private EpicInventory epicInv;
    private EpicAnimations epicAnim;
    private EpicParticles epicParticles;

    public EpicPetsPlayer(Player player)
    {
        this.player = player;
    }

    public static EpicPetsPlayer instanceOf(Player player)
    {
        if(!epicPlayers.containsKey(player.getUniqueId())){
            epicPlayers.put(player.getUniqueId(), new EpicPetsPlayer(player));
        }

        if (epicPlayers.containsKey(player.getUniqueId())) {
            (epicPlayers.get(player.getUniqueId())).updatePlayer(player);
        }
        return epicPlayers.get(player.getUniqueId());
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public DefaultPet getPet()
    {
        return this.pet;
    }

    public void setPet(DefaultPet pet)
    {
        this.pet = pet;
    }

    /**
     * @return the current EpicInventory of a player
     */
    public EpicInventory getEpicInv()
    {
        return this.epicInv;
    }

    /**
     * Set the current EpicInventory of a player
     */
    public void setEpicInv(EpicInventory epicInv)
    {
        this.epicInv = null;
        this.epicInv = epicInv;
    }

    public EpicAnimations getEpicAnim()
    {
        return this.epicAnim;
    }

    public void setEpicAnim(EpicAnimations epicAnim)
    {
        this.epicAnim = null;
        this.epicAnim = epicAnim;
    }

    public EpicParticles getEpicParticles()
    {
        return this.epicParticles;
    }

    public void setEpicParticles(EpicParticles epicParticles)
    {
        this.epicParticles = null;
        this.epicParticles = epicParticles;
    }

    public static Map<UUID, EpicPetsPlayer>  getEpicPlayers()
    {
        return epicPlayers;
    }

    public void updatePlayer(Player player)
    {
        this.player = player;
    }

    public void removePet()
    {
        if(this.pet != null){
            this.pet.remove();
        }
        this.pet = null;
    }
}
