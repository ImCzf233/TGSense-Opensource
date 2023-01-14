package net.ccbluex.liquidbounce.utils.timer;

public final class MSTimer {

    public long time = -1L;
    private long lastMS;

    public boolean hasTimePassed(long MS) {
        return System.currentTimeMillis() >= this.time + MS;
    }

    public long hasTimeLeft(long MS) {
        return MS + this.time - System.currentTimeMillis();
    }

    public void resetTwo() {
        this.lastMS = this.getCurrentMS();
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double milliseconds) {
        return (double) (this.getCurrentMS() - this.lastMS) >= milliseconds;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }
}
