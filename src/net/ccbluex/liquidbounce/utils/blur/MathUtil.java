package net.ccbluex.liquidbounce.utils.blur;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import net.minecraft.util.math.MathHelper;

public final class MathUtil {

    public static double getDistance(double srcX, double srcZ, double dstX, double dstZ) {
        double xDiff = dstX - srcX;
        double zDiff = dstZ - srcZ;

        return (double) MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653589793D;
        double output = 1.0D / Math.sqrt(2.0D * PI * (double) (sigma * sigma));

        return (float) (output * Math.exp((double) (-(x * x)) / (2.0D * (double) (sigma * sigma))));
    }

    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        double range = (double) (max - min);
        double scaled = random.nextDouble() * range;

        if (scaled > (double) max) {
            scaled = (double) max;
        }

        double shifted = scaled + (double) min;

        if (shifted > (double) max) {
            shifted = (double) max;
        }

        return (float) shifted;
    }

    public static double tryParseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    public static double getDifference(double base, double yaw) {
        double bigger;

        if (base >= yaw) {
            bigger = base - yaw;
        } else {
            bigger = yaw - base;
        }

        return bigger;
    }

    public static double tryParseFloat(String value, float defaultValue) {
        try {
            return (double) Float.parseFloat(value);
        } catch (NumberFormatException numberformatexception) {
            return (double) defaultValue;
        }
    }

    public static boolean tryParseBoolean(String value, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            return (new BigDecimal(value)).setScale(places, RoundingMode.HALF_UP).doubleValue();
        }
    }

    public static double round(double value, int places, double increment) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            double flooredValue = Math.floor(value / increment) * increment;
            double ceiledValue = Math.ceil(value / increment) * increment;
            boolean aboveHalfIncrement = value >= flooredValue + increment / 2.0D;

            return (new BigDecimal(aboveHalfIncrement ? ceiledValue : flooredValue)).setScale(places, RoundingMode.HALF_UP).doubleValue();
        }
    }

    public static float round(float value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            return (new BigDecimal((double) value)).setScale(places, RoundingMode.HALF_UP).floatValue();
        }
    }

    public static float round(float value, int places, float increment) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            double flooredValue = Math.floor((double) (value / increment)) * (double) increment;
            double ceiledValue = Math.ceil((double) (value / increment)) * (double) increment;
            boolean aboveHalfIncrement = (double) value >= flooredValue + (double) increment / 2.0D;

            return (new BigDecimal(aboveHalfIncrement ? ceiledValue : flooredValue)).setScale(places, RoundingMode.HALF_UP).floatValue();
        }
    }

    public static double randomDouble(double min, double max) {
        return min > max ? min : (new Random()).nextDouble() * (max - min) + min;
    }

    public static float randomFloat(float min, float max) {
        return min > max ? min : (new Random()).nextFloat() * (max - min) + min;
    }

    public static long randomLong(long min, long max) {
        return min > max ? min : (new Random()).nextLong() * (max - min) + min;
    }

    public static int randomInt(int min, int max) {
        return min > max ? min : (new Random()).nextInt(max) + min;
    }

    public static byte randomByte(byte min, byte max) {
        return min > max ? min : (byte) ((new Random()).nextInt(max) + min);
    }

    public static boolean randomBoolean() {
        return randomBoolean(1.0D, 0.5D);
    }

    public static boolean randomBoolean(double range, double value) {
        return randomDouble(0.0D, range) > value;
    }

    public static byte[] randomBytes(int minSize, int maxSize, byte min, byte max) {
        int size = randomInt(minSize, maxSize);
        byte[] out = new byte[size];

        for (int i = 0; i < size; ++i) {
            out[i] = randomByte(min, max);
        }

        return out;
    }
}
