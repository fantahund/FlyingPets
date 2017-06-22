package be.feeps.epicpets.events;

import be.feeps.epicpets.pets.DefaultPet;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by feeps on 19/06/2017.
 */
public class PlayerCreatePetEvent extends Event {
    public static final HandlerList handlers = new HandlerList();

    private Player player;
    private DefaultPet pet;

    public PlayerCreatePetEvent(Player player, DefaultPet pet){
        this.player = player;
        this.pet = pet;
    }

    public DefaultPet getPet(){
        return this.pet;
    }

    public Player getPlayer(){
        return this.player;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList(){ return handlers; }

}
