package be.feeps.epicpets.events.updater;

import be.feeps.epicpets.Main;

/**
 * Created by feeps on 17/06/2017.
 */
public class Updater implements Runnable {

    public Updater() {
        Main.getI().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getI(), this, 0L, 1L);
    }

    @Override
    public void run() {
        for (UpdateType updateType : UpdateType.values()) {
            if (updateType != null) {
                if (updateType.elapsed()) {
                    Main.getI().getServer().getPluginManager().callEvent(new UpdateEvent(updateType));
                }
            }
        }
    }
}