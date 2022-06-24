package be.feeps.flying.objects.pets;

import be.feeps.flying.FlyingPlayer;
import be.feeps.flying.entities.ArmorStandPacket;
import be.feeps.flying.inventories.EditorInventory;
import be.feeps.flying.utilities.FlyingUtils;
import be.feeps.flying.utilities.Updater;
import be.feeps.flying.utilities.cevents.FlyingPetLevelUpEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public abstract class Pet implements PetData{
    protected Player player;
    protected FlyingPlayer fPlayer;
    protected PetType type;

    protected Location location;
    protected ArmorStandPacket nameAs;
    protected Vector nameVector;

    private String name;
    private int level;
    private int exp;
    private Timestamp lastMission;

    public Pet(Player player, PetType type, Vector nameVector){
        this.player = player;
        this.type = type;
        this.nameVector = nameVector;
        name = type.getName();
        fPlayer = FlyingPlayer.instanceOf(player);

        nameAs = new ArmorStandPacket(player.getLocation());
        nameAs.setInvisible(true);
        nameAs.setMarker(true);
    }

    // We can consider that the spawn function is the constructor ( For now... )
    public void remove(){
        FlyingUtils.sendPacket(player.getWorld(), getDestroyPackets());
    }

    public void update(Updater.Type type){
        if (type == Updater.Type.TICK)
            FlyingUtils.sendPacket(player.getWorld(), nameAs.getTeleportPacket(location.clone().add(nameVector)));
    }

    public Object[] getInitPackets(){
        return new Object[]{nameAs.getSpawnPacket()};
    }

    public Object[] getDestroyPackets(){
        return new Object[]{nameAs.getDestroyPacket()};
    }

    public void setName(String name){
        this.name = name;
        nameAs.setCustomNameVisible(true);
        nameAs.setCustomName(FlyingUtils.toColorable(name + "&f") + FlyingUtils.L("pets.levelprefix").replace("{level}", String.valueOf(level)));
        FlyingUtils.sendPacket(player.getWorld(), nameAs.getMetaDataPacket());
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("level", level);
        data.put("exp", exp);
        data.put("lastmission", lastMission);
        return data;
    }

    @Override
    public void setData(HashMap<String, Object> data) {
        level = (Integer) data.get("level");
        exp = (Integer) data.get("exp");
        setName((String) data.get("name"));
        lastMission = (Timestamp) data.get("lastmission");
    }

    public void sendToMission(){
        lastMission = Timestamp.valueOf(LocalDateTime.now()); // Put the time from mysql

        exp++;

        if (exp >= neededExp(level)){ // Level up
            FlyingPetLevelUpEvent levelUpEvent = new FlyingPetLevelUpEvent(this);
            Bukkit.getPluginManager().callEvent(levelUpEvent);

            if (levelUpEvent.isCancelled()) return;

            exp = 0;
            level++;
            player.sendMessage(FlyingUtils.L("messages.levelup", true));
            setName(name); // Update the name
        }else{ // No level up, avoid to send two messages so, here is a condition
            player.sendMessage(FlyingUtils.L("messages.missionsent", true));
        }
    }

    public EditorInventory getEditInv(){
        return new EditorInventory(player);
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public Timestamp getLastMission(){
        return lastMission;
    }

    public String getName(){
        return name;
    }

    public Player getPlayer(){
        return player;
    }

    public PetType getType(){
        return type;
    }

    public static int neededExp(int level){
        return (int) Math.ceil(Math.sqrt(level * 2));
    }
}
