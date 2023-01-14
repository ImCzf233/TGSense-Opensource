package me.Skid.utils;

import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\"\n\u0002\u0010\u0006\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002?\u0006\u0002\u0010\u0002J*\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0007J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0011H\u0007J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u000fH\u0007J\u001a\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u0014H\u0007J\u0018\u0010 \u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u0016H\u0007J \u0010\"\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0016\u0010$\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0014J\u0016\u0010&\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0014J\u0016\u0010\'\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0014J\u0010\u0010(\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u000fH\u0007J\"\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020\u00162\b\b\u0002\u0010\u001f\u001a\u00020\u0014H\u0007JL\u0010,\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u00142\b\b\u0002\u0010-\u001a\u00020\u00162\b\b\u0002\u0010.\u001a\u00020\u00162\b\b\u0002\u0010/\u001a\u00020\u00142\b\b\u0002\u00100\u001a\u00020\u00142\b\b\u0002\u00101\u001a\u00020\u00162\b\b\u0002\u00102\u001a\u00020\u0016H\u0007J\b\u00103\u001a\u00020\u0011H\u0007J\u0010\u00103\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0016H\u0007J\u0010\u00103\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u0014H\u0007J\u0018\u00103\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0016H\u0007J\u0018\u00103\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014H\u0007J\u000e\u00103\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u000fJ \u00104\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u00105\u001a\u00020\u00162\u0006\u00106\u001a\u00020\u0016H\u0007J\u0010\u00107\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u000fH\u0007J\u0010\u00108\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0014H\u0007J\u000e\u00109\u001a\u00020\u001e2\u0006\u0010:\u001a\u00020\u001eJ\u0018\u0010;\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0016H\u0007J\u0018\u0010;\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0014H\u0007J\u0018\u0010;\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u000fH\u0007J\u0010\u0010<\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u000fH\u0007J(\u0010=\u001a\u00020\u00112\u0006\u0010>\u001a\u00020\u00142\u0006\u0010?\u001a\u00020\u00162\u0006\u0010@\u001a\u00020\u00162\u0006\u0010%\u001a\u00020AH\u0007J(\u0010B\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0007J\u0010\u0010C\u001a\u00020\u001e2\u0006\u0010D\u001a\u00020\u001eH\u0007J\u0010\u0010E\u001a\u00020\u001e2\u0006\u0010F\u001a\u00020\u001eH\u0007J\u0018\u0010G\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u0016H\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004?\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e?\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004?\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004?\u0006\u0002\n\u0000��\u0006H"},
    d2 = { "Ltemple/Color/LiquidColorUtils;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "HUD", "Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "getHUD", "()Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "setHUD", "(Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;)V", "hexColors", "", "startTime", "", "LiquidSlowly", "Ljava/awt/Color;", "time", "count", "", "qd", "", "sq", "antiColor", "color", "blueRainbow", "offset", "colorCode", "code", "", "alpha", "darker", "percentage", "fade", "index", "getRainbow", "speed", "getRainbow2", "getRainbow3", "greenRainbow", "healthColor", "hp", "maxHP", "hslRainbow", "lowest", "bigest", "indexOffset", "timeSplit", "saturation", "brightness", "rainbow", "rainbow3", "rainbowSpeed", "rainbowBright", "rainbowW", "rainbowWithAlpha", "randomMagicText", "text", "reAlpha", "redRainbow", "skyRainbow", "var2", "bright", "st", "", "slowlyRainbow", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", "twoRainbow", "LiquidBounce"}
)
public final class LiquidColorUtils {

    private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)��[0-9A-FK-OR]");
    private static final long startTime = System.currentTimeMillis();
    @JvmField
    @NotNull
    public static final int[] hexColors = new int[16];
    @NotNull
    private static HUD HUD;
    public static final LiquidColorUtils INSTANCE = new LiquidColorUtils();

    @JvmStatic
    @NotNull
    public static final Color rainbowW(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % 1.0F, 0.6F, 1.0F));

        return new Color(0.0F, (float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow3(long offset, float rainbowSpeed, float rainbowBright) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % 1.0F, rainbowSpeed, rainbowBright));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color redRainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % 1.0F, 0.5F, 1.0F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, 0.0F, 0.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color greenRainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % 1.0F, 0.5F, 1.0F));

        return new Color(0.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, 0.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color blueRainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 1.0E10F % 1.0F, 0.5F, 1.0F));

        return new Color(0.0F, 0.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, (float) currentColor.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final String stripColor(@NotNull String input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        String replaceAll = LiquidColorUtils.COLOR_PATTERN.matcher(input).replaceAll("");

        Intrinsics.checkExpressionValueIsNotNull(replaceAll, "COLOR_PATTERN.matcher(input).replaceAll(\"\")");
        return replaceAll;
    }

    public final int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);
        float it = (float) speed;
        boolean n = false;

        hue /= it;
        Color hsbColor = Color.getHSBColor(hue, 0.4F, 1.0F);

        Intrinsics.checkExpressionValueIsNotNull(hsbColor, "Color.getHSBColor(speed.�?/= it; hue }, 0.4f, 1.0f)");
        return hsbColor.getRGB();
    }

    public final int getRainbow2(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);
        float it = (float) speed;
        boolean n = false;

        hue /= it;
        Color hsbColor = Color.getHSBColor(hue, 0.5F, 0.555F);

        Intrinsics.checkExpressionValueIsNotNull(hsbColor, "Color.getHSBColor(speed.�? it; hue }, 0.5f, 0.555f)");
        return hsbColor.getRGB();
    }

    public final int getRainbow3(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);
        float it = (float) speed;
        boolean n = false;

        hue /= it;
        Color hsbColor = Color.getHSBColor(hue, 0.8F, 1.001F);

        Intrinsics.checkExpressionValueIsNotNull(hsbColor, "Color.getHSBColor(speed.�? it; hue }, 0.8f, 1.001f)");
        return hsbColor.getRGB();
    }

    @JvmStatic
    @Nullable
    public static final Color LiquidSlowly(long time, int count, float qd, float sq) {
        Color color = new Color(Color.HSBtoRGB(((float) time + (float) count * -3000000.0F) / 2.0F / 1.0E9F, qd, sq));

        return new Color((float) color.getRed() / 255.0F * 1.0F, (float) color.getGreen() / 255.0F * 1.0F, (float) color.getBlue() / 255.0F * 1.0F, (float) color.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final String translateAlternateColorCodes(@NotNull String textToTranslate) {
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }

    @NotNull
    public final String randomMagicText(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        StringBuilder stringBuilder = new StringBuilder();
        String allowedCharacters = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø?Ø×ƒáíóúñÑ??????????░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├�?┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐�?αβΓπΣσμτΦΘΩδ∞∅∈∩≡��≥≤⌠⌡÷≈��∙��√�??■\u0000";
        char[] charArray = text.toCharArray();

        Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
        char[] string = charArray;
        int i = charArray.length;

        for (int j = 0; j < i; ++j) {
            char c = string[j];

            if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                int index = (new Random()).nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø?Ø×ƒáíóúñÑ??????????░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├�?┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐�?αβΓπΣσμτΦΘΩδ∞∅∈∩≡��≥≤⌠⌡÷≈��∙��√�??■\u0000".length());
                String s = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø?Ø×ƒáíóúñÑ??????????░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├�?┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐�?αβΓπΣσμτΦΘΩδ∞∅∈∩≡��≥≤⌠⌡÷≈��∙��√�??■\u0000";
                char[] charArray2 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø?Ø×ƒáíóúñÑ??????????░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├�?┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐�?αβΓπΣσμτΦΘΩδ∞∅∈∩≡��≥≤⌠⌡÷≈��∙��√�??■\u0000".toCharArray();

                Intrinsics.checkExpressionValueIsNotNull(charArray2, "(this as java.lang.String).toCharArray()");
                stringBuilder.append(charArray2[index]);
            }
        }

        String s = stringBuilder.toString();

        Intrinsics.checkExpressionValueIsNotNull(s, "stringBuilder.toString()");
        return s;
    }

    @JvmStatic
    @NotNull
    public static final Color colorCode(@NotNull String code, int alpha) {
        Intrinsics.checkParameterIsNotNull(code, "code");
        String lowerCase = code.toLowerCase();

        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
        case 48:
            if (lowerCase.equals("0")) {
                return new Color(0, 0, 0, alpha);
            }
            break;

        case 49:
            if (lowerCase.equals("1")) {
                return new Color(0, 0, 170, alpha);
            }
            break;

        case 50:
            if (lowerCase.equals("2")) {
                return new Color(0, 170, 0, alpha);
            }
            break;

        case 51:
            if (lowerCase.equals("3")) {
                return new Color(0, 170, 170, alpha);
            }
            break;

        case 52:
            if (lowerCase.equals("4")) {
                return new Color(170, 0, 0, alpha);
            }
            break;

        case 53:
            if (lowerCase.equals("5")) {
                return new Color(170, 0, 170, alpha);
            }
            break;

        case 54:
            if (lowerCase.equals("6")) {
                return new Color(255, 170, 0, alpha);
            }
            break;

        case 55:
            if (lowerCase.equals("7")) {
                return new Color(170, 170, 170, alpha);
            }
            break;

        case 56:
            if (lowerCase.equals("8")) {
                return new Color(85, 85, 85, alpha);
            }
            break;

        case 57:
            if (lowerCase.equals("9")) {
                return new Color(85, 85, 255, alpha);
            }

        case 58:
        case 59:
        case 60:
        case 61:
        case 62:
        case 63:
        case 64:
        case 65:
        case 66:
        case 67:
        case 68:
        case 69:
        case 70:
        case 71:
        case 72:
        case 73:
        case 74:
        case 75:
        case 76:
        case 77:
        case 78:
        case 79:
        case 80:
        case 81:
        case 82:
        case 83:
        case 84:
        case 85:
        case 86:
        case 87:
        case 88:
        case 89:
        case 90:
        case 91:
        case 92:
        case 93:
        case 94:
        case 95:
        case 96:
        default:
            break;

        case 97:
            if (lowerCase.equals("a")) {
                return new Color(85, 255, 85, alpha);
            }
            break;

        case 98:
            if (lowerCase.equals("b")) {
                return new Color(85, 255, 255, alpha);
            }
            break;

        case 99:
            if (lowerCase.equals("c")) {
                return new Color(255, 85, 85, alpha);
            }
            break;

        case 100:
            if (lowerCase.equals("d")) {
                return new Color(255, 85, 255, alpha);
            }
            break;

        case 101:
            if (lowerCase.equals("e")) {
                return new Color(255, 255, 85, alpha);
            }
        }

        return new Color(255, 255, 255, alpha);
    }

    @NotNull
    public final HUD getHUD() {
        return LiquidColorUtils.HUD;
    }

    @JvmStatic
    @NotNull
    public static final Color hslRainbow(int index, float lowest, float bigest, int indexOffset, int timeSplit, float saturation, float brightness) {
        Color hsbColor = Color.getHSBColor(Math.abs((float) ((int) (System.currentTimeMillis() - LiquidColorUtils.startTime) + index * indexOffset) / (float) timeSplit % 2.0F - 1.0F) * (bigest - lowest) + lowest, saturation, brightness);

        Intrinsics.checkExpressionValueIsNotNull(hsbColor, "Color.getHSBColor((abs((�?, saturation, brightness)");
        return hsbColor;
    }

    public static Color hslRainbow$default(int index, float floatValue, float floatValue2, int indexOffset, int intValue, float floatValue3, float floatValue4, int n, Object o) {
        if ((n & 2) != 0) {
            floatValue = ((Number) LiquidColorUtils.HUD.getRainbowStartValue().get()).floatValue();
        }

        if ((n & 4) != 0) {
            floatValue2 = ((Number) LiquidColorUtils.HUD.getRainbowStopValue().get()).floatValue();
        }

        if ((n & 8) != 0) {
            indexOffset = 300;
        }

        if ((n & 16) != 0) {
            intValue = ((Number) LiquidColorUtils.HUD.getRainbowSpeedValue().get()).intValue();
        }

        if ((n & 32) != 0) {
            floatValue3 = ((Number) LiquidColorUtils.HUD.getRainbowSaturationValue().get()).floatValue();
        }

        if ((n & 64) != 0) {
            floatValue4 = ((Number) LiquidColorUtils.HUD.getRainbowBrightnessValue().get()).floatValue();
        }

        return hslRainbow(index, floatValue, floatValue2, indexOffset, intValue, floatValue3, floatValue4);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow() {
        return hslRainbow$default(1, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int index) {
        return hslRainbow$default(index, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(float alpha) {
        return reAlpha(hslRainbow$default(1, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null), alpha);
    }

    @NotNull
    public final Color rainbow(long alpha) {
        return reAlpha(hslRainbow$default(1, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbowWithAlpha(int alpha) {
        return reAlpha(hslRainbow$default(1, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int index, int alpha) {
        return reAlpha(hslRainbow$default(index, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int index, float alpha) {
        return reAlpha(hslRainbow$default(index, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, int alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, float alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, long alpha) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) alpha / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color slowlyRainbow(long time, int count, float qd, float sq) {
        Color color = new Color(Color.HSBtoRGB(((float) time + (float) count * -3000000.0F) / 2.0F / 1.0E9F, qd, sq));

        return new Color((float) color.getRed() / 255.0F * 1.0F, (float) color.getGreen() / 255.0F * 1.0F, (float) color.getBlue() / 255.0F * 1.0F, (float) color.getAlpha() / 255.0F);
    }

    @JvmStatic
    @NotNull
    public static final Color twoRainbow(long offset, float alpha) {
        Color currentColor = new Color(Color.HSBtoRGB((float) (System.nanoTime() + offset) / 8.9999999E10F % 1.0F, 0.75F, 0.8F));

        return new Color((float) currentColor.getRed() / 255.0F * 1.0F, (float) currentColor.getGreen() / 255.0F * 1.0F, (float) currentColor.getBlue() / 255.0F * 1.0F, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color skyRainbow(int i, float bright, float st, double speed) {
        double v1 = Math.ceil((double) System.currentTimeMillis() / speed + (double) ((long) i * 109L)) / 5.0D;
        double it = 360.0D;
        boolean n = false;

        v1 %= 360.0D;
        Color hsbColor = Color.getHSBColor((float) (v1 / 360.0D), st, bright);

        Intrinsics.checkExpressionValueIsNotNull(hsbColor, "Color.getHSBColor(if ((3�?.toFloat() }, st, bright)");
        return hsbColor;
    }

    @JvmStatic
    @NotNull
    public static final Color fade(@NotNull Color color, int index, int count) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        float[] hsb = new float[3];

        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F - 1.0F);

        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    @JvmStatic
    @NotNull
    public static final Color antiColor(@NotNull Color color) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
    }

    @JvmStatic
    @NotNull
    public static final Color healthColor(float hp, float maxHP, int alpha) {
        int pct = (int) (hp / maxHP * 255.0F);

        return new Color(Math.max(Math.min(255 - pct, 255), 0), Math.max(Math.min(pct, 255), 0), 0, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color darker(@NotNull Color color, float percentage) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return new Color((int) ((float) color.getRed() * percentage), (int) ((float) color.getGreen() * percentage), (int) ((float) color.getBlue() * percentage), (int) ((float) color.getAlpha() * percentage));
    }

    static {
        byte module = 16;

        for (int j = 0; j < module; ++j) {
            boolean n2 = false;
            int baseColor = (j >> 3 & 1) * 85;
            int red = (j >> 2 & 1) * 170 + baseColor + (j == 6 ? 85 : 0);
            int green = (j >> 1 & 1) * 170 + baseColor;
            int blue = (j & 1) * 170 + baseColor;

            LiquidColorUtils.hexColors[j] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }

        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(HUD.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.HUD");
        } else {
            LiquidColorUtils.HUD = (HUD) module;
        }
    }
}
