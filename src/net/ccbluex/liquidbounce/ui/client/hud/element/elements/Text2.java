package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ElementInfo(
    name = "Text2"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 D2\u00020\u0001:\u0001DB-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u00102\u001a\u0004\u0018\u000103H\u0016J\u0016\u00104\u001a\u00020\u00122\u0006\u00105\u001a\u00020\u001d2\u0006\u00106\u001a\u00020\u001dJ\u0012\u00107\u001a\u0004\u0018\u00010\u00122\u0006\u00108\u001a\u00020\u0012H\u0002J\u0018\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u001dH\u0016J \u0010>\u001a\u00020:2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010?\u001a\u00020\u001dH\u0016J\u0010\u0010@\u001a\u00020\u00122\u0006\u00108\u001a\u00020\u0012H\u0002J\u000e\u0010A\u001a\u00020\u00002\u0006\u0010;\u001a\u00020BJ\b\u0010C\u001a\u00020:H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\'\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0010R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010-\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0010R\u000e\u0010/\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006E"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text2;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blueValue", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getColorModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "display", "", "getDisplay", "()Ljava/lang/String;", "displayString", "Lnet/ccbluex/liquidbounce/value/TextValue;", "getDisplayString", "()Lnet/ccbluex/liquidbounce/value/TextValue;", "displayText", "editMode", "", "editTicks", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "prevClick", "", "rainbowIndex", "rainbowSpeed", "rectAlphaValue", "rectBlueValue", "rectColorModeValue", "getRectColorModeValue", "rectExpandValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rectGreenValue", "rectRedValue", "rectValue", "getRectValue", "redValue", "shadow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getClientName", "i", "i2", "getReplacement", "str", "handleKey", "", "c", "", "keyCode", "handleMouseClick", "mouseButton", "multiReplace", "setColor", "Ljava/awt/Color;", "updateElement", "Companion", "LiquidBounce"}
)
public final class Text2 extends Element {

    @NotNull
    private final TextValue displayString;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue alphaValue;
    @NotNull
    private final ListValue colorModeValue;
    private final BoolValue shadow;
    private final IntegerValue rectRedValue;
    private final IntegerValue rectGreenValue;
    private final IntegerValue rectBlueValue;
    private final IntegerValue rectAlphaValue;
    @NotNull
    private final ListValue rectColorModeValue;
    @NotNull
    private final ListValue rectValue;
    private final FloatValue rectExpandValue;
    private final IntegerValue rainbowSpeed;
    private final IntegerValue rainbowIndex;
    private final FontValue fontValue;
    private boolean editMode;
    private int editTicks;
    private long prevClick;
    private String displayText;
    @NotNull
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @NotNull
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    public static final Text2.Companion Companion = new Text2.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final TextValue getDisplayString() {
        return this.displayString;
    }

    @NotNull
    public final ListValue getColorModeValue() {
        return this.colorModeValue;
    }

    @NotNull
    public final ListValue getRectColorModeValue() {
        return this.rectColorModeValue;
    }

    @NotNull
    public final ListValue getRectValue() {
        return this.rectValue;
    }

    private final String getDisplay() {
        CharSequence charsequence = (CharSequence) this.displayString.get();
        boolean flag = false;
        String textContent = charsequence.length() == 0 && !this.editMode ? "Text Element" : (String) this.displayString.get();

        return this.multiReplace(textContent);
    }

    private final String getReplacement(String str) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp != null) {
            DecimalFormat decimalformat;
            IEntityPlayerSP ientityplayersp1;

            switch (str.hashCode()) {
            case 120:
                if (str.equals("x")) {
                    decimalformat = Text2.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosX());
                }
                break;

            case 121:
                if (str.equals("y")) {
                    decimalformat = Text2.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosY());
                }
                break;

            case 122:
                if (str.equals("z")) {
                    decimalformat = Text2.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosZ());
                }
                break;

            case 118532:
                if (str.equals("xdp")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    return String.valueOf(ientityplayersp.getPosX());
                }
                break;

            case 119493:
                if (str.equals("ydp")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    return String.valueOf(ientityplayersp.getPosY());
                }
                break;

            case 120454:
                if (str.equals("zdp")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    return String.valueOf(ientityplayersp.getPosZ());
                }
                break;

            case 3441010:
                if (str.equals("ping")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    return String.valueOf(PlayerExtensionKt.getPing((IEntityPlayer) ientityplayersp));
                }
                break;

            case 2134260957:
                if (str.equals("velocity")) {
                    decimalformat = Text2.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d0 = ientityplayersp1.getMotionX();
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    d0 *= ientityplayersp2.getMotionX();
                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d1 = ientityplayersp2.getMotionZ();
                    IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp3 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d2 = d0 + d1 * ientityplayersp3.getMotionZ();
                    DecimalFormat decimalformat1 = decimalformat;
                    boolean flag = false;
                    double d3 = Math.sqrt(d2);

                    return decimalformat1.format(d3);
                }
            }
        }

        String s;

        switch (str.hashCode()) {
        case 98726:
            if (str.equals("cps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
            }
            break;

        case 101609:
            if (str.equals("fps")) {
                s = String.valueOf(Minecraft.getDebugFPS());
                return s;
            }
            break;

        case 3076014:
            if (str.equals("date")) {
                s = Text2.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                return s;
            }
            break;

        case 3316154:
            if (str.equals("lcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
            }
            break;

        case 3345945:
            if (str.equals("mcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.MIDDLE));
            }
            break;

        case 3494900:
            if (str.equals("rcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT));
            }
            break;

        case 3560141:
            if (str.equals("time")) {
                s = Text2.HOUR_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                return s;
            }
            break;

        case 771880589:
            if (str.equals("clientVersion")) {
                s = "230110".toString();
                return s;
            }
            break;

        case 1102251254:
            if (str.equals("clientName")) {
                s = "TGSense";
                return s;
            }
            break;

        case 1379103690:
            if (str.equals("serverIp")) {
                s = ServerUtils.getRemoteIp();
                return s;
            }
            break;

        case 1448827361:
            if (str.equals("clientCreator")) {
                s = "";
                return s;
            }
        }

        s = null;
        return s;
    }

    private final String multiReplace(String str) {
        int lastPercent = -1;
        StringBuilder result = new StringBuilder();
        int i = 0;

        String s;

        for (int i = ((CharSequence) str).length(); i < i; ++i) {
            if (str.charAt(i) == 37) {
                if (lastPercent != -1) {
                    if (lastPercent + 1 != i) {
                        int j = lastPercent + 1;
                        boolean flag = false;

                        if (str == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        s = str.substring(j, i);
                        Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        String s1 = s;
                        String replacement = this.getReplacement(s1);

                        if (replacement != null) {
                            result.append(replacement);
                            lastPercent = -1;
                            continue;
                        }
                    }

                    result.append((CharSequence) str, lastPercent, i);
                }

                lastPercent = i;
            } else if (lastPercent == -1) {
                result.append(str.charAt(i));
            }
        }

        if (lastPercent != -1) {
            result.append((CharSequence) str, lastPercent, str.length());
        }

        s = result.toString();
        Intrinsics.checkExpressionValueIsNotNull(s, "result.toString()");
        return s;
    }

    @NotNull
    public final String getClientName(int i, int i2) {
        String s = "TGSense";
        boolean flag = false;
        String s1 = s.substring(i, i2);

        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return s1;
    }

    @Nullable
    public Border drawElement() {
        Color color = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue(), ((Number) this.alphaValue.get()).intValue());
        IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();
        String expand = (String) this.rectColorModeValue.get();
        boolean flag = false;

        if (expand == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            int i;
            String s;
            label97: {
                s = expand.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                expand = s;
                switch (expand.hashCode()) {
                case 151913845:
                    if (expand.equals("skyrainbow")) {
                        i = RenderUtils.SkyRainbow(((Number) this.rainbowIndex.get()).intValue(), 1.0F, 1.0F);
                        break label97;
                    }
                    break;

                case 404830547:
                    if (expand.equals("anotherrainbow")) {
                        i = ColorUtils.fade(new Color(((Number) this.rectRedValue.get()).intValue(), ((Number) this.rectGreenValue.get()).intValue(), ((Number) this.rectBlueValue.get()).intValue(), ((Number) this.rectAlphaValue.get()).intValue()), 100, ((Number) this.rainbowIndex.get()).intValue()).getRGB();
                        break label97;
                    }
                    break;

                case 973576630:
                    if (expand.equals("rainbow")) {
                        i = ColorUtils.hslRainbow$default(ColorUtils.INSTANCE, ((Number) this.rainbowIndex.get()).intValue(), 0.0F, 0.0F, 100 * ((Number) this.rainbowSpeed.get()).intValue(), 0, 22, (Object) null).getRGB();
                        break label97;
                    }
                }

                i = (new Color(((Number) this.rectRedValue.get()).intValue(), ((Number) this.rectGreenValue.get()).intValue(), ((Number) this.rectBlueValue.get()).intValue(), ((Number) this.rectAlphaValue.get()).intValue())).getRGB();
            }

            int rectColor = i;
            float expand1 = (float) fontRenderer.getFontHeight() * ((Number) this.rectExpandValue.get()).floatValue();
            String s1 = (String) this.rectValue.get();
            boolean flag1 = false;

            if (s1 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                s = s1.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                s1 = s;
                Color color;

                switch (s1.hashCode()) {
                case -1039745817:
                    if (s1.equals("normal")) {
                        RenderUtils.drawRect(-expand1, -expand1, (float) fontRenderer.getStringWidth(this.displayText) + expand1, (float) fontRenderer.getFontHeight() + expand1, rectColor);
                    }
                    break;

                case -1012420739:
                    if (s1.equals("onetap")) {
                        RenderUtils.drawRect(-4.0F, -8.0F, (float) (fontRenderer.getStringWidth(this.displayText) + 3), (float) fontRenderer.getFontHeight(), (new Color(43, 43, 43)).getRGB());
                        double d0 = (double) fontRenderer.getStringWidth(this.displayText) + 2.0D;

                        color = (new Color(rectColor)).darker();
                        Intrinsics.checkExpressionValueIsNotNull(color, "Color(rectColor).darker()");
                        RenderUtils.drawGradientSidewaysH(-3.0D, -7.0D, d0, -3.0D, color.getRGB(), rectColor);
                    }
                    break;

                case 3327403:
                    if (s1.equals("logo")) {
                        ;
                    }
                    break;

                case 109492860:
                    if (s1.equals("skeet")) {
                        RenderUtils.drawRect(-11, -11, fontRenderer.getStringWidth(this.displayText) + 10, fontRenderer.getFontHeight() + 8, (new Color(0, 0, 0)).getRGB());
                        RenderUtils.R2DUtils.drawOutlinedRect(-10, -10, fontRenderer.getStringWidth(this.displayText) + 9, fontRenderer.getFontHeight() + 7, 8, new Color(59, 59, 59), new Color(59, 59, 59));
                        RenderUtils.R2DUtils.drawOutlinedRect(-9, -9, fontRenderer.getStringWidth(this.displayText) + 8, fontRenderer.getFontHeight() + 6, 4, new Color(59, 59, 59), new Color(40, 40, 40));
                        RenderUtils.R2DUtils.drawOutlinedRect(-4, -4, fontRenderer.getStringWidth(this.displayText) + 3, fontRenderer.getFontHeight() + 1, 1, new Color(18, 18, 18), new Color(0, 0, 0));
                    }
                    break;

                case 1351426009:
                    if (s1.equals("rnormal")) {
                        RenderUtils.drawRect(-expand1, -expand1 - (float) 1, (float) fontRenderer.getStringWidth(this.displayText) + expand1, -expand1, ColorUtils.rainbow());
                        RenderUtils.drawRect(-expand1, -expand1, (float) fontRenderer.getStringWidth(this.displayText) + expand1, (float) fontRenderer.getFontHeight() + expand1, rectColor);
                    }
                }

                if (!StringsKt.contains$default((CharSequence) this.rectValue.get(), (CharSequence) "Logo", false, 2, (Object) null)) {
                    String s2 = this.displayText;

                    s1 = (String) this.colorModeValue.get();
                    float f = 0.0F;
                    float f1 = 0.0F;
                    String s3 = s2;

                    flag1 = false;
                    if (s1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    int j;
                    label82: {
                        s = s1.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                        String s4 = s;

                        switch (s4.hashCode()) {
                        case 151913845:
                            if (s4.equals("skyrainbow")) {
                                j = RenderUtils.SkyRainbow(((Number) this.rainbowIndex.get()).intValue(), 1.0F, 1.0F);
                                break label82;
                            }
                            break;

                        case 404830547:
                            if (s4.equals("anotherrainbow")) {
                                j = ColorUtils.fade(color, 100, ((Number) this.rainbowIndex.get()).intValue()).getRGB();
                                break label82;
                            }
                            break;

                        case 973576630:
                            if (s4.equals("rainbow")) {
                                j = ColorUtils.hslRainbow$default(ColorUtils.INSTANCE, ((Number) this.rainbowIndex.get()).intValue(), 0.0F, 0.0F, 100 * ((Number) this.rainbowSpeed.get()).intValue(), 0, 22, (Object) null).getRGB();
                                break label82;
                            }
                        }

                        j = color.getRGB();
                    }

                    fontRenderer.drawString(s3, f1, f, j, ((Boolean) this.shadow.get()).booleanValue());
                } else {
                    Fonts.font40.drawString(this.getClientName(0, 3), 5.0F, 0.0F, (new Color(255, 255, 255, 220)).getRGB());
                    Fonts.font25.drawString("CN", 5.0F + (float) Fonts.font40.getStringWidth("Pride"), 13.0F, (new Color(255, 255, 255, 220)).getRGB());
                    RenderUtils.drawRect(5.0F, 22.5F, 70.0F, 22.8F, (new Color(200, 200, 200, 220)).getRGB());
                    Fonts.font25.drawString("230110".toString(), 5.0F, 27.0F, (new Color(255, 255, 255, 220)).getRGB());
                    Fonts.font25.drawString("By Happy & Jiemo", 5.0F, 37.0F, (new Color(255, 255, 255, 220)).getRGB());
                }

                if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen()) && this.editTicks <= 40) {
                    float f2 = (float) fontRenderer.getStringWidth(this.displayText) + 2.0F;

                    color = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                    fontRenderer.drawString("_", f2, 0.0F, color.getRGB(), ((Boolean) this.shadow.get()).booleanValue());
                }

                if (this.editMode && !MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
                    this.editMode = false;
                    this.updateElement();
                }

                return new Border(-2.0F, -2.0F, (float) fontRenderer.getStringWidth(this.displayText) + 2.0F, (float) fontRenderer.getFontHeight());
            }
        }
    }

    public void updateElement() {
        this.editTicks += 5;
        if (this.editTicks > 80) {
            this.editTicks = 0;
        }

        this.displayText = this.editMode ? (String) this.displayString.get() : this.getDisplay();
    }

    public void handleMouseClick(double x, double y, int mouseButton) {
        if (this.isInBorder(x, y) && mouseButton == 0) {
            if (System.currentTimeMillis() - this.prevClick <= 250L) {
                this.editMode = true;
            }

            this.prevClick = System.currentTimeMillis();
        } else {
            this.editMode = false;
        }

    }

    public void handleKey(char c, int keyCode) {
        if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
            if (keyCode == 14) {
                CharSequence charsequence = (CharSequence) this.displayString.get();
                boolean flag = false;

                if (charsequence.length() > 0) {
                    TextValue textvalue = this.displayString;
                    String s = (String) this.displayString.get();
                    byte b0 = 0;
                    int i = ((String) this.displayString.get()).length() - 1;
                    TextValue textvalue1 = textvalue;
                    boolean flag1 = false;

                    if (s == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s1 = s.substring(b0, i);

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    String s2 = s1;

                    textvalue1.set(s2);
                }

                this.updateElement();
                return;
            }

            if (ColorUtils.INSTANCE.isAllowedCharacter(c) || c == 167) {
                this.displayString.set((String) this.displayString.get() + c);
            }

            this.updateElement();
        }

    }

    @NotNull
    public final Text2 setColor(@NotNull Color c) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        this.redValue.set((Object) Integer.valueOf(c.getRed()));
        this.greenValue.set((Object) Integer.valueOf(c.getGreen()));
        this.blueValue.set((Object) Integer.valueOf(c.getBlue()));
        return this;
    }

    public Text2(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.displayString = new TextValue("DisplayText", "");
        this.redValue = new IntegerValue("Red", 255, 0, 255);
        this.greenValue = new IntegerValue("Green", 255, 0, 255);
        this.blueValue = new IntegerValue("Blue", 255, 0, 255);
        this.alphaValue = new IntegerValue("Alpha", 255, 0, 255);
        this.colorModeValue = new ListValue("Color", new String[] { "Custom", "Rainbow", "AnotherRainbow", "SkyRainbow"}, "Custom");
        this.shadow = new BoolValue("Shadow", false);
        this.rectRedValue = new IntegerValue("RectRed", 0, 0, 255);
        this.rectGreenValue = new IntegerValue("RectGreen", 0, 0, 255);
        this.rectBlueValue = new IntegerValue("RectBlue", 0, 0, 255);
        this.rectAlphaValue = new IntegerValue("RectAlpha", 255, 0, 255);
        this.rectColorModeValue = new ListValue("RectColor", new String[] { "Custom", "Rainbow", "AnotherRainbow", "SkyRainbow"}, "Custom");
        this.rectValue = new ListValue("Rect", new String[] { "Normal", "RNormal", "OneTap", "Skeet", "None"}, "None");
        this.rectExpandValue = new FloatValue("RectExpand", 0.3F, 0.0F, 1.0F);
        this.rainbowSpeed = new IntegerValue("RainbowSpeed", 10, 1, 10);
        this.rainbowIndex = new IntegerValue("RainbowIndex", 1, 1, 20);
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.displayText = this.getDisplay();
    }

    public Text2(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 10.0D;
        }

        if ((i & 2) != 0) {
            d1 = 10.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = Side.Companion.default();
        }

        this(d0, d1, f, side);
    }

    public Text2() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006¨\u0006\r"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text2$Companion;", "", "()V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "getDATE_FORMAT", "()Ljava/text/SimpleDateFormat;", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "HOUR_FORMAT", "getHOUR_FORMAT", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final SimpleDateFormat getDATE_FORMAT() {
            return Text2.DATE_FORMAT;
        }

        @NotNull
        public final SimpleDateFormat getHOUR_FORMAT() {
            return Text2.HOUR_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT() {
            return Text2.DECIMAL_FORMAT;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
