package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.io.Closeable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.UiUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ElementInfo(
    name = "Text3"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 72\u00020\u0001:\u00017B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u0012\u0010*\u001a\u0004\u0018\u00010\r2\u0006\u0010+\u001a\u00020\rH\u0002J\u0018\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0016H\u0016J \u00101\u001a\u00020-2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u00102\u001a\u00020\u0016H\u0016J\u0010\u00103\u001a\u00020\r2\u0006\u0010+\u001a\u00020\rH\u0002J\u000e\u00104\u001a\u00020\u00002\u0006\u0010.\u001a\u000205J\b\u00106\u001a\u00020-H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text3;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "blueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "display", "", "getDisplay", "()Ljava/lang/String;", "displayString", "Lnet/ccbluex/liquidbounce/value/TextValue;", "displayText", "editMode", "", "editTicks", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "only", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "op", "outline", "prevClick", "", "rainbow", "rainbowX", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rainbowY", "rect", "redValue", "shadow", "sk", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getReplacement", "str", "handleKey", "", "c", "", "keyCode", "handleMouseClick", "mouseButton", "multiReplace", "setColor", "Ljava/awt/Color;", "updateElement", "Companion", "LiquidBounce"}
)
public final class Text3 extends Element {

    private final TextValue displayString;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final BoolValue rainbow;
    private final FloatValue rainbowX;
    private final FloatValue rainbowY;
    private final BoolValue shadow;
    private final BoolValue outline;
    private final BoolValue rect;
    private final BoolValue op;
    private final BoolValue sk;
    private final BoolValue only;
    private FontValue fontValue;
    private boolean editMode;
    private int editTicks;
    private long prevClick;
    private String displayText;
    @NotNull
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMddyy");
    @NotNull
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
    @NotNull
    private static final DecimalFormat Y_FORMAT = new DecimalFormat("0.000000000");
    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    @NotNull
    private static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("0");
    public static final Text3.Companion Companion = new Text3.Companion((DefaultConstructorMarker) null);

    private final String getDisplay() {
        CharSequence charsequence = (CharSequence) this.displayString.get();
        boolean flag = false;
        String textContent = charsequence.length() == 0 && !this.editMode ? "Text Element" : (String) this.displayString.get();

        return this.multiReplace(textContent);
    }

    private final String getReplacement(String str) {
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null) {
            switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    return "§0";
                }
                break;

            case 49:
                if (str.equals("1")) {
                    return "§1";
                }
                break;

            case 50:
                if (str.equals("2")) {
                    return "§2";
                }
                break;

            case 51:
                if (str.equals("3")) {
                    return "§3";
                }
                break;

            case 52:
                if (str.equals("4")) {
                    return "§4";
                }
                break;

            case 53:
                if (str.equals("5")) {
                    return "§5";
                }
                break;

            case 54:
                if (str.equals("6")) {
                    return "§6";
                }
                break;

            case 55:
                if (str.equals("7")) {
                    return "§7";
                }
                break;

            case 56:
                if (str.equals("8")) {
                    return "§8";
                }
                break;

            case 57:
                if (str.equals("9")) {
                    return "§9";
                }
                break;

            case 97:
                if (str.equals("a")) {
                    return "§a";
                }
                break;

            case 98:
                if (str.equals("b")) {
                    return "§b";
                }
                break;

            case 99:
                if (str.equals("c")) {
                    return "§c";
                }
                break;

            case 100:
                if (str.equals("d")) {
                    return "§d";
                }
                break;

            case 101:
                if (str.equals("e")) {
                    return "§e";
                }
                break;

            case 102:
                if (str.equals("f")) {
                    return "§f";
                }
                break;

            case 107:
                if (str.equals("k")) {
                    return "§k";
                }
                break;

            case 108:
                if (str.equals("l")) {
                    return "§l";
                }
                break;

            case 109:
                if (str.equals("m")) {
                    return "§m";
                }
                break;

            case 110:
                if (str.equals("n")) {
                    return "§n";
                }
                break;

            case 111:
                if (str.equals("o")) {
                    return "§o";
                }
                break;

            case 114:
                if (str.equals("r")) {
                    return "§r";
                }
                break;

            case 120:
                if (str.equals("x")) {
                    return Text3.DECIMAL_FORMAT.format(thePlayer.getPosX());
                }
                break;

            case 121:
                if (str.equals("y")) {
                    return Text3.DECIMAL_FORMAT.format(thePlayer.getPosY());
                }
                break;

            case 122:
                if (str.equals("z")) {
                    return Text3.DECIMAL_FORMAT.format(thePlayer.getPosZ());
                }
                break;

            case 118532:
                if (str.equals("xdp")) {
                    return String.valueOf(thePlayer.getPosX());
                }
                break;

            case 119493:
                if (str.equals("ydp")) {
                    return String.valueOf(thePlayer.getPosY());
                }
                break;

            case 120454:
                if (str.equals("zdp")) {
                    return String.valueOf(thePlayer.getPosZ());
                }
                break;

            case 3441010:
                if (str.equals("ping")) {
                    return String.valueOf(EntityUtils.INSTANCE.getPing((EntityPlayer) MinecraftInstance.mc2.player));
                }
                break;

            case 2134260957:
                if (str.equals("velocity")) {
                    DecimalFormat decimalformat = Text3.DECIMAL_FORMAT;
                    double d0 = thePlayer.getMotionX() * thePlayer.getMotionX() + thePlayer.getMotionZ() * thePlayer.getMotionZ();
                    DecimalFormat decimalformat1 = decimalformat;
                    boolean flag = false;
                    double d1 = Math.sqrt(d0);

                    return decimalformat1.format(d1);
                }
            }
        }

        String s;

        switch (str.hashCode()) {
        case -892772691:
            if (str.equals("clientversion")) {
                s = "230116";
                return s;
            }
            break;

        case -265713450:
            if (str.equals("username")) {
                s = MinecraftInstance.mc.getSession().getUsername();
                return s;
            }
            break;

        case -215825919:
            if (str.equals("clientcreator")) {
                s = "";
                return s;
            }
            break;

        case 98726:
            if (str.equals("cps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
            }
            break;

        case 101609:
            if (str.equals("fps")) {
                s = String.valueOf(MinecraftInstance.mc.getDebugFPS());
                return s;
            }
            break;

        case 3076014:
            if (str.equals("date")) {
                s = Text3.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
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
                s = Text3.HOUR_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                return s;
            }
            break;

        case 1103204566:
            if (str.equals("clientname")) {
                s = "TGSense";
                return s;
            }
            break;

        case 1379104682:
            if (str.equals("serverip")) {
                s = ServerUtils.getRemoteIp();
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

    @Nullable
    public Border drawElement() {
        int color = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue())).getRGB();
        int colord = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue())).getRGB() + (new Color(0, 0, 0, 50)).getRGB();
        IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();

        if (((Boolean) this.rect.get()).booleanValue()) {
            RenderUtils.drawRect(-2.0F, -2.0F, (float) (fontRenderer.getStringWidth(this.displayText) + 1), (float) fontRenderer.getFontHeight(), (new Color(0, 0, 0, 150)).getRGB());
        }

        fontRenderer.drawString(this.displayText, 0.0F, 0.0F, ((Boolean) this.rainbow.get()).booleanValue() ? ColorUtils.rainbow(400000000L).getRGB() : (((Boolean) this.only.get()).booleanValue() ? -1 : color), ((Boolean) this.shadow.get()).booleanValue());
        if (((Boolean) this.op.get()).booleanValue()) {
            RenderUtils.drawRect(-4.0F, -8.0F, (float) (fontRenderer.getStringWidth(this.displayText) + 3), (float) fontRenderer.getFontHeight(), (new Color(43, 43, 43)).getRGB());
            RenderUtils.drawGradientSideways(-3.0D, -7.0D, (double) fontRenderer.getStringWidth(this.displayText) + 2.0D, -3.0D, ((Boolean) this.rainbow.get()).booleanValue() ? ColorUtils.rainbow(400000000L).getRGB() + (new Color(0, 0, 0, 40)).getRGB() : colord, ((Boolean) this.rainbow.get()).booleanValue() ? ColorUtils.rainbow(400000000L).getRGB() : color);
        }

        if (((Boolean) this.sk.get()).booleanValue()) {
            UiUtils.drawRect(-11.0D, -9.5D, (double) (fontRenderer.getStringWidth(this.displayText) + 9), (double) fontRenderer.getFontHeight() + (double) 6, (new Color(0, 0, 0)).getRGB());
            UiUtils.outlineRect(-10.0D, -8.5D, (double) (fontRenderer.getStringWidth(this.displayText) + 8), (double) fontRenderer.getFontHeight() + (double) 5, 8.0D, (new Color(59, 59, 59)).getRGB(), (new Color(59, 59, 59)).getRGB());
            UiUtils.outlineRect(-9.0D, -7.5D, (double) (fontRenderer.getStringWidth(this.displayText) + 7), (double) fontRenderer.getFontHeight() + (double) 4, 4.0D, (new Color(59, 59, 59)).getRGB(), (new Color(40, 40, 40)).getRGB());
            UiUtils.outlineRect(-4.0D, -3.0D, (double) (fontRenderer.getStringWidth(this.displayText) + 2), (double) fontRenderer.getFontHeight() + (double) 0, 1.0D, (new Color(18, 18, 18)).getRGB(), (new Color(0, 0, 0)).getRGB());
        }

        if (((Boolean) this.outline.get()).booleanValue()) {
            byte rainbow = 0;
            String x$iv = this.displayText;

            GlStateManager.resetColor();
            int i = fontRenderer.getStringWidth(this.displayText);
            int j = fontRenderer.getStringWidth(this.displayText);
            Color color = Color.BLACK;

            Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
            RenderUtils.R2DUtils.drawOutlinedString(x$iv, i, j, rainbow, color.getRGB());
        }

        boolean rainbow1 = ((Boolean) this.rainbow.get()).booleanValue();
        float x$iv1 = ((Number) this.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number) this.rainbowX.get()).floatValue();
        float y$iv = ((Number) this.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number) this.rainbowY.get()).floatValue();
        float offset$iv = (float) (System.currentTimeMillis() % (long) 10000) / 10000.0F;
        boolean it = false;

        if (rainbow1) {
            RainbowFontShader.INSTANCE.setStrengthX(x$iv1);
            RainbowFontShader.INSTANCE.setStrengthY(y$iv);
            RainbowFontShader.INSTANCE.setOffset(offset$iv);
            RainbowFontShader.INSTANCE.startShader();
        }

        Closeable x$iv2 = (Closeable) RainbowFontShader.INSTANCE;
        boolean y$iv1 = false;
        Throwable offset$iv1 = (Throwable) null;

        try {
            RainbowFontShader it1 = (RainbowFontShader) x$iv2;
            boolean $i$a$-use-Text3$drawElement$1 = false;

            fontRenderer.drawString(this.displayText, 0.0F, 0.0F, rainbow1 ? 0 : color, ((Boolean) this.shadow.get()).booleanValue());
            if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen()) && this.editTicks <= 40) {
                fontRenderer.drawString("_", (float) fontRenderer.getStringWidth(this.displayText) + 2.0F, 0.0F, rainbow1 ? ColorUtils.rainbow(400000000L).getRGB() : color, ((Boolean) this.shadow.get()).booleanValue());
            }

            Unit it2 = Unit.INSTANCE;
        } catch (Throwable throwable) {
            offset$iv1 = throwable;
            throw throwable;
        } finally {
            CloseableKt.closeFinally(x$iv2, offset$iv1);
        }

        if (this.editMode && !MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
            this.editMode = false;
            this.updateElement();
        }

        return new Border(-2.0F, -2.0F, (float) fontRenderer.getStringWidth(this.displayText) + 2.0F, (float) fontRenderer.getFontHeight());
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
    public final Text3 setColor(@NotNull Color c) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        this.redValue.set((Object) Integer.valueOf(c.getRed()));
        this.greenValue.set((Object) Integer.valueOf(c.getGreen()));
        this.blueValue.set((Object) Integer.valueOf(c.getBlue()));
        return this;
    }

    public Text3(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.displayString = new TextValue("DisplayText", "");
        this.redValue = new IntegerValue("Red", 255, 0, 255);
        this.greenValue = new IntegerValue("Green", 255, 0, 255);
        this.blueValue = new IntegerValue("Blue", 255, 0, 255);
        this.rainbow = new BoolValue("Rainbow", false);
        this.rainbowX = new FloatValue("Rainbow-X", -1000.0F, -2000.0F, 2000.0F);
        this.rainbowY = new FloatValue("Rainbow-Y", -1000.0F, -2000.0F, 2000.0F);
        this.shadow = new BoolValue("Shadow", true);
        this.outline = new BoolValue("Outline", false);
        this.rect = new BoolValue("Rect", false);
        this.op = new BoolValue("OneTapRect", false);
        this.sk = new BoolValue("SkeetRect", false);
        this.only = new BoolValue("OnlyWhtie", false);
        IFontRenderer ifontrenderer = Fonts.minecraftFont;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.minecraftFont, "Fonts.minecraftFont");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.displayText = this.getDisplay();
    }

    public Text3(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
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

    public Text3() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }

    public static final void access$setFontValue$p(Text3 $this, FontValue <set-?>) {
        $this.fontValue = <set-?>;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\n¨\u0006\u0013"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text3$Companion;", "", "()V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "getDATE_FORMAT", "()Ljava/text/SimpleDateFormat;", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "HOUR_FORMAT", "getHOUR_FORMAT", "INTEGER_FORMAT", "getINTEGER_FORMAT", "Y_FORMAT", "getY_FORMAT", "defaultClient", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text3;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final SimpleDateFormat getDATE_FORMAT() {
            return Text3.DATE_FORMAT;
        }

        @NotNull
        public final SimpleDateFormat getHOUR_FORMAT() {
            return Text3.HOUR_FORMAT;
        }

        @NotNull
        public final DecimalFormat getY_FORMAT() {
            return Text3.Y_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT() {
            return Text3.DECIMAL_FORMAT;
        }

        @NotNull
        public final DecimalFormat getINTEGER_FORMAT() {
            return Text3.INTEGER_FORMAT;
        }

        @NotNull
        public final Text3 defaultClient() {
            Text3 text3 = new Text3(2.0D, 2.0D, 1.0F, (Side) null, 8, (DefaultConstructorMarker) null);

            text3.displayString.set("%clientname% | %time% | %fps%FPS | %ping%ms");
            text3.shadow.set(Boolean.valueOf(true));
            FontValue fontvalue = text3.fontValue;
            IFontRenderer ifontrenderer = Fonts.com35;

            Intrinsics.checkExpressionValueIsNotNull(Fonts.com35, "Fonts.com35");
            fontvalue.set(ifontrenderer);
            text3.setColor(new Color(255, 255, 255));
            return text3;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
