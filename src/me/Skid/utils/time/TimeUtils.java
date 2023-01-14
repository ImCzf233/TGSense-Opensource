package me.Skid.utils.time;

import net.ccbluex.liquidbounce.utils.misc.RandomUtils;

public final class TimeUtils {

    private int tick;
    private long currentMS = System.currentTimeMillis();

    public void update() {
        ++this.tick;
    }

    public boolean hasElapsed(long milliseconds) {
        return this.elapsed() > milliseconds;
    }

    public long elapsed() {
        return System.currentTimeMillis() - this.currentMS;
    }

    public void reset() {
        this.currentMS = System.currentTimeMillis();
    }

    public static long randomDelay(int minDelay, int maxDelay) {
        return (long) RandomUtils.nextInt(minDelay, maxDelay);
    }

    public static long randomClickDelay(int minCPS, int maxCPS) {
        return (long) (Math.random() * (double) (1000 / minCPS - 1000 / maxCPS + 1) + (double) (1000 / maxCPS));
    }
}
