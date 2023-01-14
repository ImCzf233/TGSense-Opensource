package me.Skid.utils;

import java.awt.Color;
import java.util.Random;

public class PixelUtils {

    public static final int REPLACE = 0;
    public static final int NORMAL = 1;
    public static final int MIN = 2;
    public static final int MAX = 3;
    public static final int ADD = 4;
    public static final int SUBTRACT = 5;
    public static final int DIFFERENCE = 6;
    public static final int MULTIPLY = 7;
    public static final int HUE = 8;
    public static final int SATURATION = 9;
    public static final int VALUE = 10;
    public static final int COLOR = 11;
    public static final int SCREEN = 12;
    public static final int AVERAGE = 13;
    public static final int OVERLAY = 14;
    public static final int CLEAR = 15;
    public static final int EXCHANGE = 16;
    public static final int DISSOLVE = 17;
    public static final int DST_IN = 18;
    public static final int ALPHA = 19;
    public static final int ALPHA_TO_GRAY = 20;
    private static Random randomGenerator = new Random();
    private static final float[] hsb1 = new float[3];
    private static final float[] hsb2 = new float[3];

    public static int clamp(int c) {
        return c < 0 ? 0 : (c > 255 ? 255 : c);
    }

    public static int interpolate(int v1, int v2, float f) {
        return clamp((int) ((float) v1 + f * (float) (v2 - v1)));
    }

    public static int brightness(int rgb) {
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;

        return (r + g + b) / 3;
    }

    public static boolean nearColors(int rgb1, int rgb2, int tolerance) {
        int r1 = rgb1 >> 16 & 255;
        int g1 = rgb1 >> 8 & 255;
        int b1 = rgb1 & 255;
        int r2 = rgb2 >> 16 & 255;
        int g2 = rgb2 >> 8 & 255;
        int b2 = rgb2 & 255;

        return Math.abs(r1 - r2) <= tolerance && Math.abs(g1 - g2) <= tolerance && Math.abs(b1 - b2) <= tolerance;
    }

    public static int combinePixels(int rgb1, int rgb2, int op) {
        return combinePixels(rgb1, rgb2, op, 255);
    }

    public static int combinePixels(int rgb1, int rgb2, int op, int extraAlpha, int channelMask) {
        return rgb2 & ~channelMask | combinePixels(rgb1 & channelMask, rgb2, op, extraAlpha);
    }

    public static int combinePixels(int rgb1, int rgb2, int op, int extraAlpha) {
        if (op == 0) {
            return rgb1;
        } else {
            int a1 = rgb1 >> 24 & 255;
            int r1 = rgb1 >> 16 & 255;
            int g1 = rgb1 >> 8 & 255;
            int b1 = rgb1 & 255;
            int a2 = rgb2 >> 24 & 255;
            int r2 = rgb2 >> 16 & 255;
            int g2 = rgb2 >> 8 & 255;
            int b2 = rgb2 & 255;
            int a3;

            switch (op) {
            case 1:
            case 16:
            default:
                break;

            case 2:
                r1 = Math.min(r1, r2);
                g1 = Math.min(g1, g2);
                b1 = Math.min(b1, b2);
                break;

            case 3:
                r1 = Math.max(r1, r2);
                g1 = Math.max(g1, g2);
                b1 = Math.max(b1, b2);
                break;

            case 4:
                r1 = clamp(r1 + r2);
                g1 = clamp(g1 + g2);
                b1 = clamp(b1 + b2);
                break;

            case 5:
                r1 = clamp(r2 - r1);
                g1 = clamp(g2 - g1);
                b1 = clamp(b2 - b1);
                break;

            case 6:
                r1 = clamp(Math.abs(r1 - r2));
                g1 = clamp(Math.abs(g1 - g2));
                b1 = clamp(Math.abs(b1 - b2));
                break;

            case 7:
                r1 = clamp(r1 * r2 / 255);
                g1 = clamp(g1 * g2 / 255);
                b1 = clamp(b1 * b2 / 255);
                break;

            case 8:
            case 9:
            case 10:
            case 11:
                Color.RGBtoHSB(r1, g1, b1, PixelUtils.hsb1);
                Color.RGBtoHSB(r2, g2, b2, PixelUtils.hsb2);
                switch (op) {
                case 8:
                    PixelUtils.hsb2[0] = PixelUtils.hsb1[0];
                    break;

                case 9:
                    PixelUtils.hsb2[1] = PixelUtils.hsb1[1];
                    break;

                case 10:
                    PixelUtils.hsb2[2] = PixelUtils.hsb1[2];
                    break;

                case 11:
                    PixelUtils.hsb2[0] = PixelUtils.hsb1[0];
                    PixelUtils.hsb2[1] = PixelUtils.hsb1[1];
                }

                rgb1 = Color.HSBtoRGB(PixelUtils.hsb2[0], PixelUtils.hsb2[1], PixelUtils.hsb2[2]);
                r1 = rgb1 >> 16 & 255;
                g1 = rgb1 >> 8 & 255;
                b1 = rgb1 & 255;
                break;

            case 12:
                r1 = 255 - (255 - r1) * (255 - r2) / 255;
                g1 = 255 - (255 - g1) * (255 - g2) / 255;
                b1 = 255 - (255 - b1) * (255 - b2) / 255;
                break;

            case 13:
                r1 = (r1 + r2) / 2;
                g1 = (g1 + g2) / 2;
                b1 = (b1 + b2) / 2;
                break;

            case 14:
                int s = 255 - (255 - r1) * (255 - r2) / 255;

                a3 = r1 * r2 / 255;
                r1 = (s * r1 + a3 * (255 - r1)) / 255;
                s = 255 - (255 - g1) * (255 - g2) / 255;
                a3 = g1 * g2 / 255;
                g1 = (s * g1 + a3 * (255 - g1)) / 255;
                s = 255 - (255 - b1) * (255 - b2) / 255;
                a3 = b1 * b2 / 255;
                b1 = (s * b1 + a3 * (255 - b1)) / 255;
                break;

            case 15:
                b1 = 255;
                g1 = 255;
                r1 = 255;
                break;

            case 17:
                if ((PixelUtils.randomGenerator.nextInt() & 255) <= a1) {
                    r1 = r2;
                    g1 = g2;
                    b1 = b2;
                }
                break;

            case 18:
                r1 = clamp(r2 * a1 / 255);
                g1 = clamp(g2 * a1 / 255);
                b1 = clamp(b2 * a1 / 255);
                a1 = clamp(a2 * a1 / 255);
                return a1 << 24 | r1 << 16 | g1 << 8 | b1;

            case 19:
                a1 = a1 * a2 / 255;
                return a1 << 24 | r2 << 16 | g2 << 8 | b2;

            case 20:
                int na = 255 - a1;

                return a1 << 24 | na << 16 | na << 8 | na;
            }

            if (extraAlpha != 255 || a1 != 255) {
                a1 = a1 * extraAlpha / 255;
                a3 = (255 - a1) * a2 / 255;
                r1 = clamp((r1 * a1 + r2 * a3) / 255);
                g1 = clamp((g1 * a1 + g2 * a3) / 255);
                b1 = clamp((b1 * a1 + b2 * a3) / 255);
                a1 = clamp(a1 + a3);
            }

            return a1 << 24 | r1 << 16 | g1 << 8 | b1;
        }
    }
}
