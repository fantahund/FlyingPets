package be.feeps.flying.objects;

import be.feeps.flying.FlyingPlayer;
import be.feeps.flying.Main;
import be.feeps.flying.Reflection;
import be.feeps.flying.objects.pets.Pet;
import be.feeps.flying.objects.pets.PetType;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Updater;
import be.feeps.flying.utilities.cevents.FlyingPetRemoveEvent;
import be.feeps.flying.utilities.cevents.FlyingPetSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class PetManager {
    private Player player;

    //The current pet instance
    private PetType type;
    private Pet pet;

    public PetManager(Player player){
        this.player = player;
        type = PetType.ARMORSTAND; // Default
    }

    // Update pet component
    public void update(Updater.Type type){
        if (exists()) {
            pet.update(type);
        }
    }

    public void setType(PetType type){
        remove();
        this.type = type;
    }

    public void spawn(){ // Now void
        remove(); // Already check if the pet exists in the method

        if (!FlyingUtils.hasPerm(player, type.getPermission())) return;

        // Create a new instance of the pet with the petType class
        pet = (Pet) Reflection.getConstructor(type.getPetClass(), Player.class).invoke(player);
        Bukkit.getPluginManager().callEvent(new FlyingPetSpawnEvent(pet));
    }

    public void remove(){
        if (exists()){
            pet.remove();
            Bukkit.getPluginManager().callEvent(new FlyingPetRemoveEvent(pet));
            pet = null;
        }
    }

    // Need to be reworked with the other methos
    public void sendToMission(){
        if (!exists()) return;

        if (pet.getLastMission() == null) {
            pet.sendToMission();
            remove();
        }else{
            // Check if one hour elasped
            Timestamp current = Timestamp.valueOf(LocalDateTime.now());
            Timestamp lastMission = pet.getLastMission();

            long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(current.getTime() - lastMission.getTime());
            int minBMission = Main.getInstance().getConfig().getInt("this-server.minutes-between-missions");
            if (elapsedMinutes >= minBMission){ // One hour
                pet.sendToMission();
                remove();
            }else{
                player.sendMessage(FlyingUtils.L("messages.waitmission", true).replace("{minutes}", String.valueOf(minBMission - elapsedMinutes)));
            }
        }
    }

    public boolean exists(){
        return pet != null;
    }

    // Return the current pet's type. The type will be always the same as the spawned pet.
    public PetType getType(){
        return type;
    }

    public Pet getPet() {
        return pet;
    }


    /*
                The pet updater, note that PetEffect are updated in the PetManager, so the PetUpdater includes Effects
             */
    public static class PetUpdater implements Listener {

        @EventHandler
        public void petUpdate(Updater.Event event){
            FlyingPlayer.getFlyingPlayers().forEach((id, fPlayer) -> fPlayer.getPetManager().update(event.getType()));
        }
    }
}
