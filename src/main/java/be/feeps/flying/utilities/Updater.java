package be.feeps.flying.utilities;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Updater implements Runnable{
    private final JavaPlugin plugin;

    public Updater(JavaPlugin plugin){
        this.plugin = plugin;
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,this, 0L, 1L);
    }

    @Override
    public void run() {
        for (Type updateType : Type.values()) {
            if (updateType.elapsed()) {
                plugin.getServer().getPluginManager().callEvent(new Event(updateType));
            }
        }
    }

    public enum Type {
        MINUTE(60000),
        HALF_MINUTE(30000),
        FIFTEEN_SECONDS(15000),
        EIGHT_SECONDS(8000),
        FIVE_SECONDS(5000),
        SECOND(1000),
        HALF_SECOND(500),
        FOUR_TICK(200),
        THREE_TICK(150),
        TWO_TICK(100),
        FAST(75),
        TICK(49); // Cause of a detected little changes

        private final long milliseconds;
        private long currentTime;

        Type(long milliseconds) {
            this.milliseconds = milliseconds;
            this.currentTime = System.currentTimeMillis();
        }

        public boolean elapsed() {
            if (System.currentTimeMillis() - currentTime >= milliseconds) {
                currentTime = System.currentTimeMillis();
                return true;
            }
            return false;
        }

        public long getMilliseconds() {
            return milliseconds;
        }

        public long getCurrentTime() {
            return currentTime;
        }
    }

    public static class Event extends org.bukkit.event.Event {
        private static final HandlerList handlers = new HandlerList();
        private final Type type;

        public Event(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList(){
            return handlers;
        }
    }
}
