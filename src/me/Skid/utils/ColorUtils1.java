package me.Skid.utils;

import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0013\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0007J\u0018\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0007J \u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0018\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000eJ\u0010\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0007J \u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000eH\u0007J \u0010\u001f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u00102\b\b\u0002\u0010\u001a\u001a\u00020\u000eJ\b\u0010\"\u001a\u00020\u000bH\u0007J\u0010\u0010\"\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u0010H\u0007J\u0010\u0010\"\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000eH\u0007J\u0010\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\tH\u0007J\u0018\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0010H\u0007J\u0018\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u000eH\u0007J\u0010\u0010$\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000eH\u0007J\u000e\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u0019J\u0018\u0010\'\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000eH\u0007J\u0014\u0010(\u001a\u0004\u0018\u00010\u00192\b\u0010)\u001a\u0004\u0018\u00010\u0019H\u0007J\u0010\u0010*\u001a\u00020\u00192\u0006\u0010+\u001a\u00020\u0019H\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"},
    d2 = { "Lme/Skid/utils/ColorUtils1;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "hexColors", "", "startTime", "", "LiquidSlowly", "Ljava/awt/Color;", "time", "count", "", "qd", "", "sq", "blend", "color1", "color2", "ratio", "", "colorCode", "code", "", "alpha", "colorFromInt", "color", "fade", "index", "healthColor", "hp", "maxHP", "rainbow", "offset", "rainbowWithAlpha", "randomMagicText", "text", "reAlpha", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", "LiquidBounce"}
)
public final class ColorUtils1 {

    private static final Pattern COLOR_PATTERN;
    @JvmField
    @NotNull
    public static final int[] hexColors;
    private static final long startTime;
    public static final ColorUtils1 INSTANCE;

    @JvmStatic
    @Nullable
    public static final String stripColor(@Nullable String input) {
        return input != null ? ColorUtils1.COLOR_PATTERN.matcher((CharSequence) input).replaceAll("") : null;
    }

    @JvmStatic
    @NotNull
    public static final Color fade(@NotNull Color color, int index, int count) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        float[] hsb = new float[3];

        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float f = ((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F - 1.0F;
        boolean flag = false;
        float brightness = Math.abs(f);

        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    @JvmStatic
    @Nullable
    public static final Color LiquidSlowly(long time, int count, float qd, float sq) {
        Color color = new Color(Color.HSBtoRGB(((float) time + (float) count * -3000000.0F) / (float) 2 / 1.0E9F, qd, sq));

        return new Color((float) color.getRed() / 255.0F * (float) 1, (float) color.getGreen() / 255.0F * (float) 1, (float) color.getBlue() / 255.0F * (float) 1, (float) color.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final String translateAlternateColorCodes(@NotNull String textToTranslate) {
        Intrinsics.checkParameterIsNotNull(textToTranslate, "textToTranslate");
        boolean flag = false;
        char[] achar = textToTranslate.toCharArray();

        Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
        char[] chars = achar;
        int i = 0;

        for (int i = chars.length - 1; i < i; ++i) {
            if (chars[i] == 38 && StringsKt.contains((CharSequence) "0123456789AaBbCcDdEeFfKkLlMmNnOoRr", chars[i + 1], true)) {
                chars[i] = 167;
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }

        boolean flag1 = false;

        return new String(chars);
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, int alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color blend(@NotNull Color color1, @NotNull Color color2, double ratio) {
        Intrinsics.checkParameterIsNotNull(color1, "color1");
        Intrinsics.checkParameterIsNotNull(color2, "color2");
        float r = (float) ratio;
        float ir = 1.0F - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
    }

    @JvmStatic
    @NotNull
    public static final Color blend(@NotNull Color color1, @NotNull Color color2) {
        Intrinsics.checkParameterIsNotNull(color1, "color1");
        Intrinsics.checkParameterIsNotNull(color2, "color2");
        return blend(color1, color2, 0.5D);
    }

    @JvmStatic
    @NotNull
    public static final Color colorFromInt(int color) {
        Color c = new Color(color);

        return new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
    }

    @NotNull
    public final Color colorCode(@NotNull String code, int alpha) {
        Intrinsics.checkParameterIsNotNull(code, "code");
        boolean flag = false;
        String s = code.toLowerCase();

        Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
        String s1 = s;

        switch (s1.hashCode()) {
        case 48:
            if (s1.equals("0")) {
                return new Color(0, 0, 0, alpha);
            }
            break;

        case 49:
            if (s1.equals("1")) {
                return new Color(0, 0, 170, alpha);
            }
            break;

        case 50:
            if (s1.equals("2")) {
                return new Color(0, 170, 0, alpha);
            }
            break;

        case 51:
            if (s1.equals("3")) {
                return new Color(0, 170, 170, alpha);
            }
            break;

        case 52:
            if (s1.equals("4")) {
                return new Color(170, 0, 0, alpha);
            }
            break;

        case 53:
            if (s1.equals("5")) {
                return new Color(170, 0, 170, alpha);
            }
            break;

        case 54:
            if (s1.equals("6")) {
                return new Color(255, 170, 0, alpha);
            }
            break;

        case 55:
            if (s1.equals("7")) {
                return new Color(170, 170, 170, alpha);
            }
            break;

        case 56:
            if (s1.equals("8")) {
                return new Color(85, 85, 85, alpha);
            }
            break;

        case 57:
            if (s1.equals("9")) {
                return new Color(85, 85, 255, alpha);
            }
            break;

        case 97:
            if (s1.equals("a")) {
                return new Color(85, 255, 85, alpha);
            }
            break;

        case 98:
            if (s1.equals("b")) {
                return new Color(85, 255, 255, alpha);
            }
            break;

        case 99:
            if (s1.equals("c")) {
                return new Color(255, 85, 85, alpha);
            }
            break;

        case 100:
            if (s1.equals("d")) {
                return new Color(255, 85, 255, alpha);
            }
            break;

        case 101:
            if (s1.equals("e")) {
                return new Color(255, 255, 85, alpha);
            }
        }

        return new Color(255, 255, 255, alpha);
    }

    public static Color colorCode$default(ColorUtils1 colorutils1, String s, int i, int j, Object object) {
        if ((j & 2) != 0) {
            i = 255;
        }

        return colorutils1.colorCode(s, i);
    }

    @NotNull
    public final Color healthColor(float hp, float maxHP, int alpha) {
        int pct = (int) (hp / maxHP * 255.0F);
        int i = 255 - pct;
        short short0 = 255;
        boolean flag = false;
        int j = Math.min(i, short0);
        byte b0 = 0;

        flag = false;
        j = Math.max(j, b0);
        short short1 = 255;
        boolean flag1 = false;
        int k = Math.min(pct, short1);

        b0 = 0;
        flag = false;
        k = Math.max(k, b0);
        byte b1 = 0;

        return new Color(j, k, b1, alpha);
    }

    public static Color healthColor$default(ColorUtils1 colorutils1, float f, float f1, int i, int j, Object object) {
        if ((j & 4) != 0) {
            i = 255;
        }

        return colorutils1.healthColor(f, f1, i);
    }

    @NotNull
    public final String randomMagicText(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        StringBuilder stringBuilder = new StringBuilder();
        String allowedCharacters = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├�?┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐�?αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000";
        boolean index = false;
        char[] achar = text.toCharArray();

        Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
        char[] achar1 = achar;
        int i = achar1.length;

        for (int j = 0; j < i; ++j) {
            char c = achar1[j];

            if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                int k = (new Random()).nextInt(allowedCharacters.length());
                boolean flag = false;

                achar = allowedCharacters.toCharArray();
                Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
                char[] achar2 = achar;

                stringBuilder.append(achar2[k]);
            }
        }

        String s = stringBuilder.toString();

        Intrinsics.checkExpressionValueIsNotNull(s, "stringBuilder.toString()");
        return s;
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow() {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + 400000L) / 1.0E10F % (float) 1, 1.0F, 1.0F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % (float) 1, 1.0F, 1.0F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(float alpha) {
        return rainbow(400000L, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbowWithAlpha(int alpha) {
        return reAlpha(net.ccbluex.liquidbounce.utils.render.ColorUtils.hslRainbow$default(net.ccbluex.liquidbounce.utils.render.ColorUtils.INSTANCE, 1, 0.0F, 0.0F, 0, 0, 30, (Object) null), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int alpha) {
        return rainbow(400000L, alpha / 255);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset, int alpha) {
        return rainbow(offset, (float) alpha / (float) 255);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % (float) 1, 1.0F, 1.0F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, alpha);
    }

    static {
        ColorUtils1 colorutils1 = new ColorUtils1();

        INSTANCE = colorutils1;
        COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
        hexColors = new int[16];
        byte b0 = 16;
        boolean flag = false;
        boolean flag1 = false;
        int i = 0;

        for (byte b1 = b0; i < b1; ++i) {
            boolean $i$a$-repeat-ColorUtils1$1 = false;
            int baseColor = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + baseColor + (i == 6 ? 85 : 0);
            int green = (i >> 1 & 1) * 170 + baseColor;
            int blue = (i & 1) * 170 + baseColor;

            ColorUtils1.hexColors[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }

        startTime = System.currentTimeMillis();
    }
}
