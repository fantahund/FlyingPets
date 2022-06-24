package be.feeps.flying.objects;

import be.feeps.flying.objects.pets.PetData;
import be.feeps.flying.utilities.Reference;

import java.util.HashMap;

public class PlayerData implements PetData {
    private Reference<Integer> last;
    private Reference<Boolean> spawnJoin;

    @Override
    public void setData(HashMap<String, Object> data) {
        last = new Reference<>((Integer) data.get("last"));

        Boolean bool = (Boolean) data.get("spawn_join");
        spawnJoin = new Reference<>(bool != null && bool);
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("last", last.getValue());
        data.put("spawn_join", spawnJoin.getValue());
        return data;
    }

    public Reference<Boolean> getSpawnJoin() {
        return spawnJoin;
    }

    public Reference<Integer> getLastPet() {
        return last;
    }
}
