package be.feeps.flying.listener.events;

import be.feeps.flying.FlyingDatabase;
import be.feeps.flying.FlyingPlayer;
import be.feeps.flying.Main;
import be.feeps.flying.objects.PetManager;
import be.feeps.flying.objects.PlayerData;
import be.feeps.flying.objects.pets.PetType;
import be.feeps.flying.utilities.FlyingUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        FlyingPlayer fPlayer = FlyingPlayer.instanceOf(player);
        FlyingDatabase database = Main.getInstance().getDatabase();

        //FlyingUtils.sendPacket(player, FlyingUtils.noCollideTeamPacket);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (!Bukkit.getPlayer(fPlayer.getUniqueId()).isOnline()) return;

            database.async();
            database.loadPlayer(fPlayer, () -> {
                // All is loaded and cached

                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    if (!Bukkit.getPlayer(fPlayer.getUniqueId()).isOnline()) return;

                    fPlayer.setPetManager(new PetManager(player));

                    PlayerData plyData = fPlayer.getPlayerData();

                    if (plyData.getSpawnJoin().getValue() && plyData.getLastPet().getValue() != null){
                        fPlayer.getPetManager().setType(PetType.values()[plyData.getLastPet().getValue()]);
                        fPlayer.getPetManager().spawn();
                    }
                });
            });
        }, 40);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        FlyingPlayer fPlayer = FlyingPlayer.instanceOf(player);
        FlyingDatabase database = Main.getInstance().getDatabase();

        fPlayer.getPetManager().remove(); // Remove the pet

        // Save the players data
        database.async();
        System.out.println("quit");
        database.savePlayer(fPlayer);



        FlyingPlayer.getFlyingPlayers().remove(player.getUniqueId()); // Avoid memory leak ?
    }
}
