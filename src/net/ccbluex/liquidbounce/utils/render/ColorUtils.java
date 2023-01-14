package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.GodLightSync;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0002\b\u000e\n\u0002\u0010\u0006\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015H\u0007J\u000e\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bJ*\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 H\u0007J\u0018\u0010\"\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010#\u001a\u00020 H\u0007J\u001a\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&2\b\b\u0002\u0010#\u001a\u00020\u001bH\u0007J\u0018\u0010\'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020 H\u0007J \u0010*\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001bH\u0007J\u001e\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020 2\u0006\u0010-\u001a\u00020 2\u0006\u0010.\u001a\u00020 J\u0016\u0010/\u001a\u00020\u001b2\u0006\u00100\u001a\u00020 2\u0006\u00101\u001a\u00020 J\"\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u00020 2\u0006\u00104\u001a\u00020 2\b\b\u0002\u0010#\u001a\u00020\u001bH\u0007J6\u00105\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u00106\u001a\u00020 2\b\b\u0002\u00107\u001a\u00020 2\b\b\u0002\u00108\u001a\u00020\u001b2\b\b\u0002\u00109\u001a\u00020\u001bJ\u000e\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=JJ\u0010>\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u00106\u001a\u00020 2\b\b\u0002\u00107\u001a\u00020 2\b\b\u0002\u00108\u001a\u00020\u001b2\b\b\u0002\u00109\u001a\u00020\u001b2\b\b\u0002\u0010-\u001a\u00020 2\b\b\u0002\u0010.\u001a\u00020 J\u0010\u0010?\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015H\u0007J\b\u0010@\u001a\u00020\u0017H\u0007J\u0010\u0010@\u001a\u00020\u00172\u0006\u0010#\u001a\u00020 H\u0007J\u0010\u0010@\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u001bH\u0007J\u0010\u0010@\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015H\u0007J\u0018\u0010@\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010#\u001a\u00020 H\u0007J\u0018\u0010@\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010#\u001a\u00020\u001bH\u0007J\u0018\u0010A\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010-\u001a\u00020 H\u0007J\u0010\u0010B\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u001bH\u0007J\u000e\u0010C\u001a\u00020&2\u0006\u0010D\u001a\u00020&J\u0018\u0010E\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\u00172\u0006\u0010#\u001a\u00020 H\u0007J\u0016\u0010E\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u001bJ\u0018\u0010F\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u001bH\u0007J(\u0010G\u001a\u00020\u00172\u0006\u0010H\u001a\u00020\u001b2\u0006\u0010I\u001a\u00020 2\u0006\u0010J\u001a\u00020 2\u0006\u0010K\u001a\u00020LH\u0007J\u0014\u0010M\u001a\u0004\u0018\u00010&2\b\u0010N\u001a\u0004\u0018\u00010&H\u0007J\u0010\u0010O\u001a\u00020&2\u0006\u0010P\u001a\u00020&H\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Q"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/render/ColorUtils;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "allowedCharactersArray", "", "getAllowedCharactersArray", "()[C", "gl", "Lnet/ccbluex/liquidbounce/features/module/modules/color/GodLightSync;", "getGl", "()Lnet/ccbluex/liquidbounce/features/module/modules/color/GodLightSync;", "hexColors", "", "hud", "Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "getHud", "()Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "startTime", "", "ALLColor", "Ljava/awt/Color;", "offset", "GodLight", "index", "", "LiquidSlowly", "time", "count", "qd", "", "sq", "TwoRainbow", "alpha", "colorCode", "code", "", "darker", "color", "percentage", "fade", "getColor", "hueoffset", "saturation", "brightness", "getHealthColor", "health", "maxHealth", "healthColor", "hp", "maxHP", "hslRainbow", "lowest", "bigest", "indexOffset", "timeSplit", "isAllowedCharacter", "", "character", "", "novoRainbow", "originalrainbow", "rainbow", "rainbow2", "rainbowWithAlpha", "randomMagicText", "text", "reAlpha", "reAlpha1", "skyRainbow", "var2", "bright", "st", "speed", "", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", "LiquidBounce"}
)
public final class ColorUtils {

    @NotNull
    private static final char[] allowedCharactersArray;
    private static final Pattern COLOR_PATTERN;
    @JvmField
    @NotNull
    public static final int[] hexColors;
    private static final long startTime;
    @NotNull
    private static final HUD hud;
    @NotNull
    private static final GodLightSync gl;
    public static final ColorUtils INSTANCE;

    @NotNull
    public final char[] getAllowedCharactersArray() {
        return ColorUtils.allowedCharactersArray;
    }

    public final boolean isAllowedCharacter(char character) {
        return character != 167 && character >= 32 && character != 127;
    }

    public final int getColor(float hueoffset, float saturation, float brightness) {
        float speed = 4500.0F;
        float hue = (float) (System.currentTimeMillis() % (long) ((int) speed)) / speed;

        return Color.HSBtoRGB(hue - hueoffset / (float) 54, saturation, brightness);
    }

    @JvmStatic
    @Nullable
    public static final String stripColor(@Nullable String input) {
        return input != null ? ColorUtils.COLOR_PATTERN.matcher((CharSequence) input).replaceAll("") : null;
    }

    @NotNull
    public final Color hslRainbow(int index, float lowest, float bigest, int indexOffset, int timeSplit) {
        float f = (float) ((int) (System.currentTimeMillis() - ColorUtils.startTime) + index * indexOffset) / (float) timeSplit % (float) 2 - (float) 1;
        boolean flag = false;
        Color color = Color.getHSBColor(Math.abs(f) * (bigest - lowest) + lowest, 0.7F, 1.0F);

        Intrinsics.checkExpressionValueIsNotNull(color, "Color.getHSBColor((abs((�?-lowest))+lowest,0.7f,1f)");
        return color;
    }

    public static Color hslRainbow$default(ColorUtils colorutils, int i, float f, float f1, int j, int k, int l, Object object) {
        if ((l & 2) != 0) {
            f = 0.41F;
        }

        if ((l & 4) != 0) {
            f1 = 0.58F;
        }

        if ((l & 8) != 0) {
            j = 300;
        }

        if ((l & 16) != 0) {
            k = 1500;
        }

        return colorutils.hslRainbow(i, f, f1, j, k);
    }

    @JvmStatic
    @NotNull
    public static final Color skyRainbow(int i, float bright, float st, double speed) {
        double d0 = (double) System.currentTimeMillis() / speed + (double) ((long) i * 109L);
        boolean flag = false;
        double d1 = Math.ceil(d0);
        double v1 = d1 / (double) 5;

        d0 = 360.0D;
        boolean flag1 = false;

        flag = false;
        boolean $i$a$-also-ColorUtils$skyRainbow$1 = false;

        v1 %= d0;
        Color color = Color.getHSBColor(d0 / 360.0D < 0.5D ? -((float) (v1 / 360.0D)) : (float) (v1 / 360.0D), st, bright);

        Intrinsics.checkExpressionValueIsNotNull(color, "Color.getHSBColor(if ((3�?.toFloat() }, st, bright)");
        return color;
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

            if (this.isAllowedCharacter(c)) {
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
    public static final Color reAlpha(@NotNull Color color, float alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, RangesKt.coerceIn(alpha, 0.0F, 1.0F));
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(float alpha) {
        return rainbow(400000L, alpha);
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
    public static final Color ALLColor(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float) ((double) Minecraft.getMinecraft().player.ticksExisted / 50.0D + Math.sin(1.6D) % (double) 1), 0.4F, 0.9F));

        return new Color(currentColor.getRGB());
    }

    @NotNull
    public final Color reAlpha(@NotNull Color color, int alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbowWithAlpha(int alpha) {
        return reAlpha1(hslRainbow$default(ColorUtils.INSTANCE, 1, 0.0F, 0.0F, 0, 0, 30, (Object) null), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha1(@NotNull Color color, int alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color originalrainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % (float) 1, 1.0F, 1.0F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color healthColor(float hp, float maxHP, int alpha) {
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

    public static Color healthColor$default(float f, float f1, int i, int j, Object object) {
        if ((j & 4) != 0) {
            i = 255;
        }

        return healthColor(f, f1, i);
    }

    @JvmStatic
    @NotNull
    public static final Color darker(@NotNull Color color, float percentage) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color((int) ((float) color.getRed() * percentage), (int) ((float) color.getGreen() * percentage), (int) ((float) color.getBlue() * percentage), (int) ((float) color.getAlpha() * percentage));
    }

    @JvmStatic
    @Nullable
    public static final Color LiquidSlowly(long time, int count, float qd, float sq) {
        Color color = new Color(Color.HSBtoRGB(((float) time + (float) count * -3000000.0F) / (float) 2 / 1.0E9F, qd, sq));

        return new Color((float) color.getRed() / 255.0F * (float) 1, (float) color.getGreen() / 255.0F * (float) 1, (float) color.getBlue() / 255.0F * (float) 1, (float) color.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % (float) 1, 1.0F, 1.0F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color TwoRainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 8.9999999E10F % (float) 1, 0.75F, 0.8F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, alpha);
    }

    public final int getHealthColor(float health, float maxHealth) {
        float health1 = health;

        if (health > (float) 20) {
            health1 = 20.0F;
        }

        float[] fractions = new float[] { 0.0F, 0.5F, 1.0F};
        Color[] colors = new Color[] { Color.RED, Color.YELLOW, Color.GREEN};
        float progress = health1 * (float) 5 * 0.01F;
        Color color = BlendUtils.blendColors(fractions, colors, progress);

        if (color == null) {
            Intrinsics.throwNpe();
        }

        Color customColor = color.brighter();

        Intrinsics.checkExpressionValueIsNotNull(customColor, "customColor");
        return customColor.getRGB();
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow2(long offset, float saturation) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % (float) 1, saturation, 1.0F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @NotNull
    public final HUD getHud() {
        return ColorUtils.hud;
    }

    @NotNull
    public final GodLightSync getGl() {
        return ColorUtils.gl;
    }

    @NotNull
    public final Color novoRainbow(int index, float lowest, float bigest, int indexOffset, int timeSplit, float saturation, float brightness) {
        float f = (float) ((int) (System.currentTimeMillis() - ColorUtils.startTime) + index * indexOffset) / (float) timeSplit % (float) 2 - (float) 1;
        boolean flag = false;
        Color color = Color.getHSBColor(Math.abs(f) * (bigest - lowest) + lowest, saturation, brightness);

        Intrinsics.checkExpressionValueIsNotNull(color, "Color.getHSBColor((abs((�?, saturation, brightness)");
        return color;
    }

    public static Color novoRainbow$default(ColorUtils colorutils, int i, float f, float f1, int j, int k, float f2, float f3, int l, Object object) {
        if ((l & 2) != 0) {
            f = ((Number) ColorUtils.hud.getRainbowStartValue().get()).floatValue();
        }

        if ((l & 4) != 0) {
            f1 = ((Number) ColorUtils.hud.getRainbowStopValue().get()).floatValue();
        }

        if ((l & 8) != 0) {
            j = 300;
        }

        if ((l & 16) != 0) {
            k = ((Number) ColorUtils.hud.getRainbowSpeedValue().get()).intValue();
        }

        if ((l & 32) != 0) {
            f2 = ((Number) ColorUtils.hud.getRainbowSaturationValue().get()).floatValue();
        }

        if ((l & 64) != 0) {
            f3 = ((Number) ColorUtils.hud.getRainbowBrightnessValue().get()).floatValue();
        }

        return colorutils.novoRainbow(i, f, f1, j, k, f2, f3);
    }

    @NotNull
    public final Color GodLight(int index) {
        Color color = VisualUtils.getGradientOffset(new Color(((Number) ColorUtils.gl.getR().get()).intValue(), ((Number) ColorUtils.gl.getG().get()).intValue(), ((Number) ColorUtils.gl.getB().get()).intValue(), 1), new Color(((Number) ColorUtils.gl.getR2().get()).intValue(), ((Number) ColorUtils.gl.getG2().get()).intValue(), ((Number) ColorUtils.gl.getB2().get()).intValue(), 1), Math.abs((double) (System.currentTimeMillis() / (long) 10140) + (double) index / 1.5D) / (double) 10);

        Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO�?        .toDouble())/10))");
        return color;
    }

    @JvmStatic
    @NotNull
    public static final Color colorCode(@NotNull String code, int alpha) {
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

    public static Color colorCode$default(String s, int i, int j, Object object) {
        if ((j & 2) != 0) {
            i = 255;
        }

        return colorCode(s, i);
    }

    static {
        ColorUtils colorutils = new ColorUtils();

        INSTANCE = colorutils;
        allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};
        COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
        hexColors = new int[16];
        byte b0 = 16;
        boolean flag = false;
        boolean flag1 = false;
        int i = 0;

        for (byte b1 = b0; i < b1; ++i) {
            boolean $i$a$-repeat-ColorUtils$1 = false;
            int baseColor = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + baseColor + (i == 6 ? 85 : 0);
            int green = (i >> 1 & 1) * 170 + baseColor;
            int blue = (i & 1) * 170 + baseColor;

            ColorUtils.hexColors[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }

        startTime = System.currentTimeMillis();
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(HUD.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.HUD");
        } else {
            hud = (HUD) module;
            module = LiquidBounce.INSTANCE.getModuleManager().getModule(GodLightSync.class);
            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.color.GodLightSync");
            } else {
                gl = (GodLightSync) module;
            }
        }
    }
}
