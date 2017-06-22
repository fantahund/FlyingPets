package be.feeps.epicpets.events.updater;

import be.feeps.epicpets.utils.MathUtils;

/**
 * Created by feeps on 17/06/2017.
 */
public enum UpdateType {
    MINUTE(60000),
    HALF_MINUTE(30000),
    FIFTEEN_SECOND(15000),
    SECOND(1000),
    HALF_SECOND(500),
    FOUR_TICK(200),
    THREE_TICK(150),
    TWO_TICK(100),
    FAST(75),
    TICK(50);

    private long milliseconds;
    private long currentTime;

    UpdateType(long milliseconds) {
        this.milliseconds = milliseconds;
        this.currentTime = System.currentTimeMillis();
    }

    public boolean elapsed() {
        if (MathUtils.elapsed(currentTime, milliseconds)) {
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