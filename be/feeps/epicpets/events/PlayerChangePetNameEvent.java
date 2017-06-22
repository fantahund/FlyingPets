package be.feeps.epicpets.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by feeps on 21/06/2017.
 */
public class PlayerChangePetNameEvent extends Event {
    public static final HandlerList handlers = new HandlerList();

    private Player player;

    public PlayerChangePetNameEvent(Player player){
        this.player = player;
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
