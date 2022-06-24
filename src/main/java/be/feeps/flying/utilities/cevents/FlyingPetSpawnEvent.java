package be.feeps.flying.utilities.cevents;

import be.feeps.flying.objects.pets.Pet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class FlyingPetSpawnEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Pet pet;

    public FlyingPetSpawnEvent(Pet pet) {
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