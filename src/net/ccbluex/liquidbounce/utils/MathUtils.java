package net.ccbluex.liquidbounce.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

public final class MathUtils {

    private static final Random random = new Random();

    public static double round(double value, double inc) {
        if (inc == 0.0D) {
            return value;
        } else if (inc == 1.0D) {
            return (double) Math.round(value);
        } else {
            double halfOfInc = inc / 2.0D;
            double floored = Math.floor(value / inc) * inc;

            return value >= floored + halfOfInc ? (new BigDecimal(Math.ceil(value / inc) * inc)).doubleValue() : (new BigDecimal(floored)).doubleValue();
        }
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653D;
        double output = 1.0D / Math.sqrt(2.0D * PI * (double) (sigma * sigma));

        return (float) (output * Math.exp((double) (-(x * x)) / (2.0D * (double) (sigma * sigma))));
    }

    public static int clamp_int(int p_clamp_int_0_, int p_clamp_int_1_, int p_clamp_int_2_) {
        return p_clamp_int_0_ < p_clamp_int_1_ ? p_clamp_int_1_ : (p_clamp_int_0_ > p_clamp_int_2_ ? p_clamp_int_2_ : p_clamp_int_0_);
    }

    public static int getRandomInRange(int min, int max) {
        return (int) (Math.random() * (double) (max - min) + (double) min);
    }

    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
        return interpolate((double) oldValue, (double) newValue, (double) ((float) interpolationValue)).floatValue();
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return Double.valueOf(oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static double roundToHalf(double d) {
        return (double) Math.round(d * 2.0D) / 2.0D;
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            BigDecimal bd = new BigDecimal(value);

            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    public static double roundToDecimalPlace(double value, double inc) {
        double halfOfInc = inc / 2.0D;
        double floored = StrictMath.floor(value / inc) * inc;

        return value >= floored + halfOfInc ? (new BigDecimal(StrictMath.ceil(value / inc) * inc, MathContext.DECIMAL64)).stripTrailingZeros().doubleValue() : (new BigDecimal(floored, MathContext.DECIMAL64)).stripTrailingZeros().doubleValue();
    }

    public static double incValue(double val, double inc) {
        double one = 1.0D / inc;

        return (double) Math.round(val * one) / one;
    }

    public static float clampValue(float value, float floor, float cap) {
        return value < floor ? floor : Math.min(value, cap);
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }
}
