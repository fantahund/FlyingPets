package be.feeps.epicpets.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by feeps on 07/06/2017.
 */
public class CheckMoveUtil {
    private static List<Entity> currentlyMoving = new ArrayList();
    private static final HashMap<Entity, Location> lastLocation = new HashMap();

    public static void checkEntity(Entity en) {
        Location currentloc = en.getLocation();
        Location lastLoc = lastLocation.get(en);
        if (lastLocation.get(en) == null) {
            lastLocation.put(en, currentloc);
            lastLoc = lastLocation.get(en);
        }
        lastLocation.put(en, en.getLocation());
        if ((lastLoc.getX() != currentloc.getX()) || (lastLoc.getY() != currentloc.getY()) || (lastLoc.getZ() != currentloc.getZ())) {
            if (!currentlyMoving.contains(en)) {
                currentlyMoving.add(en);
            }
        }
        else if (currentlyMoving.contains(en)) {
            currentlyMoving.remove(en);
        }
    }

    public static boolean isMoving(Entity e) {
        if (currentlyMoving.contains(e)) {
            return true;
        }
        return false;
    }
}
