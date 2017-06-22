package be.feeps.epicpets.events.updater;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by feeps on 17/06/2017.
 */
public class UpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private UpdateType type;

    public UpdateEvent(UpdateType type) {
        this.type = type;
    }

    public UpdateType getType() {
        return type;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}