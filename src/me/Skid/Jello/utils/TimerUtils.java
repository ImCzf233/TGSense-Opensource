package me.Skid.Jello.utils;

public class TimerUtils {

    private static long prevMS;
    private static long lastMS;

    public TimerUtils() {
        TimerUtils.prevMS = 0L;
        TimerUtils.lastMS = -1L;
    }

    public static boolean delay(float milliSec) {
        return (float) (getTime() - TimerUtils.prevMS) >= milliSec;
    }

    public static void reset() {
        TimerUtils.prevMS = getTime();
    }

    public static long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public static boolean hasReached(long milliseconds) {
        return getCurrentMS() - TimerUtils.lastMS >= milliseconds;
    }

    public static boolean hasReached(double milliseconds) {
        return (double) (getCurrentMS() - TimerUtils.lastMS) >= milliseconds;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (getTime() >= time) {
            if (reset) {
                reset();
            }

            return true;
        } else {
            return false;
        }
    }
}
