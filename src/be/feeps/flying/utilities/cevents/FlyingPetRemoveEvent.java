package be.feeps.flying.utilities.cevents;

import be.feeps.flying.objects.pets.Pet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class FlyingPetRemoveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Pet pet;

    public FlyingPetRemoveEvent(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}