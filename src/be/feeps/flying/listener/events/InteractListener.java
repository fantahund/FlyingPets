package be.feeps.flying.listener.events;

import be.feeps.flying.FlyingDatabase;
import be.feeps.flying.FlyingPlayer;
import be.feeps.flying.Reflection;
import be.feeps.flying.Reflection.FieldAccessor;
import be.feeps.flying.objects.pets.Pet;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.cevents.FlyingPetRemoveEvent;
import be.feeps.flying.utilities.cevents.FlyingPetSpawnEvent;
import be.feeps.flying.utilities.packets.OnPacketOutEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;

public class InteractListener implements Listener {

    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        FlyingPlayer.instanceOf(event.getEntity()).getPetManager().remove();
    }

    private static final Class<?> namedEntitySpawn = Reflection.getMinecraftClass("PacketPlayOutNamedEntitySpawn");
    private static final FieldAccessor<Integer> idField = Reflection.getField(namedEntitySpawn, Integer.TYPE, 0);

    @EventHandler
    public void onNamedEntityPacketSent(OnPacketOutEvent event){
        Object packet = event.getPacket();
        if (namedEntitySpawn.isInstance(packet)){
            // The server is sending a player spawn packet, so, spawn his pet with !
            int playerId = idField.get(packet);
            Bukkit.getServer().getOnlinePlayers().stream().filter(p -> p.getEntityId() == playerId).findAny().ifPresent(player -> {
                FlyingPlayer fPlayer = FlyingPlayer.instanceOf(player);
                if (fPlayer.getPetManager().exists())
                    FlyingUtils.sendPacket(event.getReceiver(), fPlayer.getPetManager().getPet().getInitPackets());
            });
        }
    }

    @EventHandler
    public void onSneakToggle(PlayerToggleSneakEvent event){
        if (!event.isCancelled()){
            Player player = event.getPlayer();
            FlyingPlayer fPlayer = FlyingPlayer.instanceOf(player);

            if (!fPlayer.getPetManager().exists())
                return;

            Pet pet = fPlayer.getPetManager().getPet();

            if (event.isSneaking()){
                FlyingUtils.sendPacket(player.getWorld(), pet.getDestroyPackets());
            }else{
                FlyingUtils.sendPacket(player.getWorld(), pet.getInitPackets());
            }
        }
    }

    @EventHandler
    public void onPetSpawn(FlyingPetSpawnEvent event){
        Pet pet = event.getPet();
        FlyingPlayer fPlayer = FlyingPlayer.instanceOf(pet.getPlayer());
        HashMap data = fPlayer.getSQLCache().get(FlyingDatabase.getTableName(pet.getType()));

        if (!data.isEmpty())
            pet.setData(data);

        // Set the last type on sqlcache
        fPlayer.getPlayerData().getLastPet().setValue(pet.getType().ordinal());
    }

    @EventHandler
    public void onPetRemove(FlyingPetRemoveEvent event){
        Pet pet = event.getPet();
        FlyingPlayer fPlayer = FlyingPlayer.instanceOf(pet.getPlayer());
        fPlayer.getSQLCache().put(FlyingDatabase.getTableName(pet.getType()), pet.getData());
    }
}