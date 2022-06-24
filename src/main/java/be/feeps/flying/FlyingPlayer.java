package be.feeps.flying;

import be.feeps.flying.inventories.FlyingInventory;
import be.feeps.flying.objects.PetManager;
import be.feeps.flying.objects.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlyingPlayer {
    private static final Map<UUID, FlyingPlayer> flyingPlayers = new HashMap<>();
    private final HashMap<String, HashMap<String, Object>> sqlCache = new HashMap<>(); // Table -> Column, Value
    private Player player;
    private final UUID uuid;
    private final PlayerData playerData;
    private PetManager petManager;
    private FlyingInventory inventory;

    public FlyingPlayer(Player player){
        this.player = player;
        playerData = new PlayerData();
        uuid = player.getUniqueId();
    }

    public static FlyingPlayer instanceOf(Player player) {
        flyingPlayers.putIfAbsent(player.getUniqueId(), new FlyingPlayer(player));
        if (flyingPlayers.containsKey(player.getUniqueId())) {
            (flyingPlayers.get(player.getUniqueId())).setPlayer(player);
        }
        return flyingPlayers.get(player.getUniqueId());
    }

    public static Map<UUID, FlyingPlayer> getFlyingPlayers() {
        return flyingPlayers;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPetManager(PetManager petManager) {
        this.petManager = petManager;
    }

    public FlyingInventory getInventory() {
        return inventory;
    }

    public PetManager getPetManager() {
        if (petManager == null){
            petManager = new PetManager(player);
        }
        return petManager;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public void setInventory(FlyingInventory inventory) {
        this.inventory = inventory;
    }

    public HashMap<String, HashMap<String, Object>> getSQLCache() {
        return sqlCache;
    }
}
