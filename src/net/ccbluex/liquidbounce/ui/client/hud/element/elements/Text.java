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
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
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
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "Text"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\f\n\u0002\b\b\b\u0007\u0018\u0000 g2\u00020\u0001:\u0001gB-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010N\u001a\u0004\u0018\u00010OH\u0016J.\u0010P\u001a\u00020Q2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010R\u001a\u00020\u00032\u0006\u0010S\u001a\u00020\u00032\u0006\u0010T\u001a\u00020,J.\u0010P\u001a\u00020Q2\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u00062\u0006\u0010R\u001a\u00020\u00062\u0006\u0010S\u001a\u00020\u00062\u0006\u0010T\u001a\u00020,J\u0012\u0010U\u001a\u0004\u0018\u00010\u001d2\u0006\u0010V\u001a\u00020\u001dH\u0002J\u000e\u0010W\u001a\u00020Q2\u0006\u0010T\u001a\u00020XJ\u000e\u0010W\u001a\u00020Q2\u0006\u0010Y\u001a\u00020,J&\u0010W\u001a\u00020Q2\u0006\u0010Z\u001a\u00020,2\u0006\u0010[\u001a\u00020,2\u0006\u0010\\\u001a\u00020,2\u0006\u0010]\u001a\u00020,J\u0018\u0010^\u001a\u00020Q2\u0006\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020,H\u0016J \u0010b\u001a\u00020Q2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010c\u001a\u00020,H\u0016J\u0010\u0010d\u001a\u00020\u001d2\u0006\u0010V\u001a\u00020\u001dH\u0002J\u000e\u0010e\u001a\u00020\u00002\u0006\u0010_\u001a\u00020XJ\b\u0010f\u001a\u00020QH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u00020\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u000e\u0010*\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000209X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010B\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020HX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u000e\u0010M\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006h"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "Mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "amountValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "astolfoRainbowIndex", "astolfoRainbowOffset", "astolfoclient", "balpha", "blueValue", "bord", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "char", "colorBlueValue2", "colorGreenValue2", "colorModeValue", "colorRedValue2", "display", "", "getDisplay", "()Ljava/lang/String;", "displayString", "Lnet/ccbluex/liquidbounce/value/TextValue;", "displayText", "distanceValue", "doslide", "", "getDoslide", "()Z", "setDoslide", "(Z)V", "editMode", "editTicks", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "gidentspeed", "greenValue", "markB2Value", "markBValue", "markG2Value", "markGValue", "markR2Value", "markRValue", "newRainbowIndex", "prevClick", "", "rainbowWM", "rainbowX", "rainbowY", "redValue", "saturationValue", "shadow", "slide", "slidedelay", "slidetext", "getSlidetext", "()I", "setSlidetext", "(I)V", "slidetimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getSlidetimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "setSlidetimer", "(Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;)V", "speedStr", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawRect", "", "x2", "y2", "color", "getReplacement", "str", "glColor", "Ljava/awt/Color;", "hex", "red", "green", "blue", "alpha", "handleKey", "c", "", "keyCode", "handleMouseClick", "mouseButton", "multiReplace", "setColor", "updateElement", "Companion", "LiquidBounce"}
)
public final class Text extends Element {

    private final FloatValue brightnessValue;
    private final ListValue Mode;
    private final TextValue displayString;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue colorRedValue2;
    private final IntegerValue colorGreenValue2;
    private final IntegerValue colorBlueValue2;
    private final IntegerValue gidentspeed;
    private final IntegerValue newRainbowIndex;
    private final IntegerValue astolfoRainbowOffset;
    private final IntegerValue astolfoclient;
    private final IntegerValue astolfoRainbowIndex;
    private final FloatValue saturationValue;
    private final ListValue colorModeValue;
    private final BoolValue rainbowWM;
    private final IntegerValue markRValue;
    private final IntegerValue markGValue;
    private final IntegerValue markBValue;
    private final IntegerValue markR2Value;
    private final IntegerValue markG2Value;
    private final IntegerValue markB2Value;
    private final FloatValue rainbowX;
    private final FloatValue rainbowY;
    private final BoolValue shadow;
    private final BoolValue bord;
    private final BoolValue slide;
    private final BoolValue char;
    private final IntegerValue slidedelay;
    private final IntegerValue balpha;
    private final IntegerValue distanceValue;
    private final IntegerValue amountValue;
    private FontValue fontValue;
    private boolean editMode;
    private int editTicks;
    private long prevClick;
    private String speedStr;
    private String displayText;
    private int slidetext;
    @NotNull
    private MSTimer slidetimer;
    private boolean doslide;
    @NotNull
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @NotNull
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    @NotNull
    private static final DecimalFormat DECIMAL_FORMAT_INT = new DecimalFormat("0");
    public static final Text.Companion Companion = new Text.Companion((DefaultConstructorMarker) null);

    private final String getDisplay() {
        CharSequence charsequence = (CharSequence) this.displayString.get();
        boolean flag = false;
        String textContent = charsequence.length() == 0 && !this.editMode ? "TGSense | Fps:%fps% | %serverip%" : (String) this.displayString.get();

        return this.multiReplace(textContent);
    }

    private final String getReplacement(String str) {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            IEntityPlayerSP ientityplayersp;
            DecimalFormat decimalformat;
            IEntityPlayerSP ientityplayersp1;

            switch (str.hashCode()) {
            case -1394017553:
                if (str.equals("maxHealthInt")) {
                    decimalformat = Text.DECIMAL_FORMAT_INT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getMaxHealth()));
                }
                break;

            case -1221262756:
                if (str.equals("health")) {
                    decimalformat = Text.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getHealth()));
                }
                break;

            case -906299168:
                if (str.equals("maxHealth")) {
                    decimalformat = Text.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getMaxHealth()));
                }
                break;

            case -737639680:
                if (str.equals("yawInt")) {
                    decimalformat = Text.DECIMAL_FORMAT_INT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getRotationYaw()));
                }
                break;

            case -259550065:
                if (str.equals("pitchInt")) {
                    decimalformat = Text.DECIMAL_FORMAT_INT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getRotationPitch()));
                }
                break;

            case 120:
                if (str.equals("x")) {
                    decimalformat = Text.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosX());
                }
                break;

            case 121:
                if (str.equals("y")) {
                    decimalformat = Text.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosY());
                }
                break;

            case 122:
                if (str.equals("z")) {
                    decimalformat = Text.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosZ());
                }
                break;

            case 97765:
                if (str.equals("bps")) {
                    return this.speedStr;
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

            case 119407:
                if (str.equals("yaw")) {
                    decimalformat = Text.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getRotationYaw()));
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
                    EntityUtils entityutils = EntityUtils.INSTANCE;
                    EntityPlayerSP entityplayersp = MinecraftInstance.mc2.player;

                    if (MinecraftInstance.mc2.player == null) {
                        Intrinsics.throwNpe();
                    }

                    return String.valueOf(entityutils.getPing((EntityPlayer) entityplayersp));
                }
                break;

            case 3648599:
                if (str.equals("xInt")) {
                    decimalformat = Text.DECIMAL_FORMAT_INT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosX());
                }
                break;

            case 3678390:
                if (str.equals("yInt")) {
                    decimalformat = Text.DECIMAL_FORMAT_INT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosY());
                }
                break;

            case 3708181:
                if (str.equals("zInt")) {
                    decimalformat = Text.DECIMAL_FORMAT_INT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(ientityplayersp1.getPosZ());
                }
                break;

            case 29274099:
                if (str.equals("healthInt")) {
                    decimalformat = Text.DECIMAL_FORMAT_INT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getHealth()));
                }
                break;

            case 106677056:
                if (str.equals("pitch")) {
                    decimalformat = Text.DECIMAL_FORMAT;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    return decimalformat.format(Float.valueOf(ientityplayersp1.getRotationPitch()));
                }
                break;

            case 700855164:
                if (str.equals("hurtTime")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    return String.valueOf(ientityplayersp.getHurtTime());
                }
                break;

            case 1160949830:
                if (str.equals("onGround")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    return String.valueOf(ientityplayersp.getOnGround());
                }
                break;

            case 2134260957:
                if (str.equals("velocity")) {
                    decimalformat = Text.DECIMAL_FORMAT;
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
        case -892772691:
            if (str.equals("clientversion")) {
                s = "b230110";
                return s;
            }
            break;

        case -266666762:
            if (str.equals("userName")) {
                s = MinecraftInstance.mc.getSession().getUsername();
                return s;
            }
            break;

        case -265713450:
            if (str.equals("username")) {
                Session session = MinecraftInstance.mc2.getSession();

                Intrinsics.checkExpressionValueIsNotNull(session, "mc2.getSession()");
                s = session.getUsername();
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

            if (str.equals("cps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
            }
            break;

        case 101609:
            if (str.equals("fps")) {
                s = String.valueOf(Minecraft.getDebugFPS());
                return s;
            }

            if (str.equals("fps")) {
                s = String.valueOf(Minecraft.getDebugFPS());
                return s;
            }
            break;

        case 3076014:
            if (str.equals("date")) {
                s = Text.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                return s;
            }

            if (str.equals("date")) {
                s = Text.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                return s;
            }
            break;

        case 3316154:
            if (str.equals("lcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
            }

            if (str.equals("lcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
            }
            break;

        case 3345945:
            if (str.equals("mcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.MIDDLE));
            }

            if (str.equals("mcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.MIDDLE));
            }
            break;

        case 3494900:
            if (str.equals("rcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT));
            }

            if (str.equals("rcps")) {
                return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT));
            }
            break;

        case 3560141:
            if (str.equals("time")) {
                s = Text.HOUR_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
                return s;
            }

            if (str.equals("time")) {
                s = Text.HOUR_FORMAT.format(Long.valueOf(System.currentTimeMillis()));
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

        case 1103204566:
            if (str.equals("clientname")) {
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

        case 1379104682:
            if (str.equals("serverip")) {
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

    public final int getSlidetext() {
        return this.slidetext;
    }

    public final void setSlidetext(int <set-?>) {
        this.slidetext = <set-?>;
    }

    @NotNull
    public final MSTimer getSlidetimer() {
        return this.slidetimer;
    }

    public final void setSlidetimer(@NotNull MSTimer <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.slidetimer = <set-?>;
    }

    public final boolean getDoslide() {
        return this.doslide;
    }

    public final void setDoslide(boolean <set-?>) {
        this.doslide = <set-?>;
    }

    @Nullable
    public Border drawElement() {
        IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();
        float length2 = 4.5F;
        String mwc1 = this.displayText;
        boolean mwc2 = false;

        if (mwc1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            char[] achar = mwc1.toCharArray();

            Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
            char[] charArray = achar;
            int color;
            int i;

            if (((Boolean) this.char.get()).booleanValue()) {
                length2 = (float) fontRenderer.getStringWidth(this.displayText);
            } else {
                char[] colorMode = charArray;

                color = charArray.length;

                for (i = 0; i < color; ++i) {
                    char c0 = colorMode[i];

                    length2 += (float) fontRenderer.getStringWidth(String.valueOf(c0));
                }
            }

            if (((Boolean) this.slide.get()).booleanValue() && !MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
                if (this.slidetimer.hasTimePassed((long) ((Number) this.slidedelay.get()).intValue())) {
                    if (this.slidetext <= this.getDisplay().length() && this.doslide) {
                        ++this.slidetext;
                        this.slidetimer.reset();
                    } else if (!this.doslide && this.slidetext >= 0) {
                        --this.slidetext;
                        this.slidetimer.reset();
                    }
                }

                if (this.slidetext == this.getDisplay().length() && this.doslide) {
                    this.doslide = false;
                } else if (this.slidetext == 0 && !this.doslide) {
                    this.doslide = true;
                }

                mwc1 = this.getDisplay();
                byte b0 = 0;
                int j = this.slidetext;
                boolean flag = false;

                if (mwc1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s = mwc1.substring(b0, j);

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String s1 = s;

                this.displayText = s1;
            } else {
                this.displayText = this.getDisplay();
            }

            int k = (new Color(((Number) this.markRValue.get()).intValue(), ((Number) this.markGValue.get()).intValue(), ((Number) this.markBValue.get()).intValue())).getRGB();

            i = (new Color(((Number) this.markR2Value.get()).intValue(), ((Number) this.markG2Value.get()).intValue(), ((Number) this.markB2Value.get()).intValue())).getRGB();
            if (((String) this.Mode.get()).equals("Top")) {
                Gui.drawRect(-2, -2, fontRenderer.getStringWidth(this.displayText) + 2, fontRenderer.getFontHeight(), (new Color(0, 0, 0, 100)).getRGB());
                RenderUtils.drawGradientSideways(-2.0D, -3.0D, (double) (fontRenderer.getStringWidth(this.displayText) + 2), -2.0D, k, i);
            } else if (((String) this.Mode.get()).equals("Onetap")) {
                Gui.drawRect(-2, -5, fontRenderer.getStringWidth(this.displayText) + 2, fontRenderer.getFontHeight(), (new Color(0, 0, 0, 90)).getRGB());
                RenderUtils.drawGradientSideways(-1.0D, -2.0D, (double) (fontRenderer.getStringWidth(this.displayText) + 1), -4.0D, k, i);
            }

            String s2 = (String) this.colorModeValue.get();

            color = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue())).getRGB();
            boolean rainbow = StringsKt.equals(s2, "Rainbow", true);
            Color color;
            int l;

            if (((Boolean) this.bord.get()).booleanValue()) {
                double counter;
                int x$iv;
                int y$iv;
                double offset$iv;
                double $i$a$-use-Text$drawElement$2;
                double d0;
                Color color1;
                double d1;
                int i1;

                if (Intrinsics.areEqual((String) this.Mode.get(), "Skeet")) {
                    RenderUtils.autoExhibition(-4.0D, -5.2D, (double) length2, (double) fontRenderer.getFontHeight() + 1.5D, 1.0D);
                    counter = (double) length2;
                    x$iv = 0;
                    y$iv = ((Number) this.amountValue.get()).intValue() - 1;
                    if (x$iv <= y$iv) {
                        while (true) {
                            offset$iv = (double) x$iv / (double) ((Number) this.amountValue.get()).intValue() * counter;
                            $i$a$-use-Text$drawElement$2 = (double) (x$iv + 1) / (double) ((Number) this.amountValue.get()).intValue() * counter;
                            d0 = -1.4D + offset$iv;
                            d1 = -1.4D + $i$a$-use-Text$drawElement$2;
                            if (rainbow) {
                                l = 0;
                            } else if (StringsKt.equals(s2, "Fade", true)) {
                                color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), x$iv * ((Number) this.distanceValue.get()).intValue(), this.displayText.length() * 200);
                                Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(Color(redV…displayText.length * 200)");
                                l = color.getRGB();
                            } else if (StringsKt.equals(s2, "Astolfo", true)) {
                                l = RenderUtils.Astolfo(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                            } else if (StringsKt.equals(s2, "Gident", true)) {
                                color = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (x$iv * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                                Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO…stanceValue.get()) / 10))");
                                l = color.getRGB();
                            } else {
                                l = StringsKt.equals(s2, "NewRainbow", true) ? RenderUtils.getRainbow(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                            }

                            if (rainbow) {
                                i1 = 0;
                            } else if (StringsKt.equals(s2, "Fade", true)) {
                                color1 = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), x$iv * ((Number) this.distanceValue.get()).intValue(), this.displayText.length() * 200);
                                Intrinsics.checkExpressionValueIsNotNull(color1, "Palette.fade2(Color(redV…displayText.length * 200)");
                                i1 = color1.getRGB();
                            } else if (StringsKt.equals(s2, "Astolfo", true)) {
                                i1 = RenderUtils.Astolfo(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                            } else if (StringsKt.equals(s2, "Gident", true)) {
                                color1 = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (x$iv * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                                Intrinsics.checkExpressionValueIsNotNull(color1, "RenderUtils.getGradientO…stanceValue.get()) / 10))");
                                i1 = color1.getRGB();
                            } else {
                                i1 = StringsKt.equals(s2, "NewRainbow", true) ? RenderUtils.getRainbow(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                            }

                            RenderUtils.drawGradientSideways(d0, -2.7D, d1, -2.0D, l, i1);
                            if (x$iv == y$iv) {
                                break;
                            }

                            ++x$iv;
                        }
                    }
                }

                if (Intrinsics.areEqual((String) this.Mode.get(), "Slide")) {
                    RenderUtils.drawRect(-4.0F, -4.5F, length2, (float) fontRenderer.getFontHeight(), (new Color(0, 0, 0, ((Number) this.balpha.get()).intValue())).getRGB());
                    counter = (double) (length2 + (float) 1);
                    x$iv = 0;
                    y$iv = ((Number) this.amountValue.get()).intValue() - 1;
                    if (x$iv <= y$iv) {
                        while (true) {
                            offset$iv = (double) x$iv / (double) ((Number) this.amountValue.get()).intValue() * counter;
                            $i$a$-use-Text$drawElement$2 = (double) (x$iv + 1) / (double) ((Number) this.amountValue.get()).intValue() * counter;
                            d0 = -4.0D + offset$iv;
                            d1 = -1.0D + $i$a$-use-Text$drawElement$2;
                            if (rainbow) {
                                l = 0;
                            } else if (StringsKt.equals(s2, "Fade", true)) {
                                color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), x$iv * ((Number) this.distanceValue.get()).intValue(), this.displayText.length() * 200);
                                Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(Color(redV…displayText.length * 200)");
                                l = color.getRGB();
                            } else if (StringsKt.equals(s2, "Astolfo", true)) {
                                l = RenderUtils.Astolfo(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                            } else if (StringsKt.equals(s2, "Gident", true)) {
                                color = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (x$iv * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                                Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO…stanceValue.get()) / 10))");
                                l = color.getRGB();
                            } else {
                                l = StringsKt.equals(s2, "NewRainbow", true) ? RenderUtils.getRainbow(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                            }

                            if (rainbow) {
                                i1 = 0;
                            } else if (StringsKt.equals(s2, "Fade", true)) {
                                color1 = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), x$iv * ((Number) this.distanceValue.get()).intValue(), this.displayText.length() * 200);
                                Intrinsics.checkExpressionValueIsNotNull(color1, "Palette.fade2(Color(redV…displayText.length * 200)");
                                i1 = color1.getRGB();
                            } else if (StringsKt.equals(s2, "Astolfo", true)) {
                                i1 = RenderUtils.Astolfo(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                            } else if (StringsKt.equals(s2, "Gident", true)) {
                                color1 = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (x$iv * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                                Intrinsics.checkExpressionValueIsNotNull(color1, "RenderUtils.getGradientO…stanceValue.get()) / 10))");
                                i1 = color1.getRGB();
                            } else {
                                i1 = StringsKt.equals(s2, "NewRainbow", true) ? RenderUtils.getRainbow(x$iv * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                            }

                            RenderUtils.drawGradientSideways(d0, -4.2D, d1, -3.0D, l, i1);
                            if (x$iv == y$iv) {
                                break;
                            }

                            ++x$iv;
                        }
                    }
                }
            }

            int[] aint = new int[] { 0};
            boolean it;
            float f;
            Closeable closeable;
            float f1;
            boolean flag1;
            float f2;
            Throwable throwable;
            RainbowFontShader rainbowfontshader;
            Unit unit;
            boolean flag2;
            float f3;
            String s3;

            if (((Boolean) this.char.get()).booleanValue()) {
                boolean length = StringsKt.equals(s2, "Rainbow", true);

                f = ((Number) this.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number) this.rainbowX.get()).floatValue();
                f1 = ((Number) this.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number) this.rainbowY.get()).floatValue();
                f2 = (float) (System.currentTimeMillis() % (long) 10000) / 10000.0F;
                it = false;
                if (length) {
                    RainbowFontShader.INSTANCE.setStrengthX(f);
                    RainbowFontShader.INSTANCE.setStrengthY(f1);
                    RainbowFontShader.INSTANCE.setOffset(f2);
                    RainbowFontShader.INSTANCE.startShader();
                }

                closeable = (Closeable) RainbowFontShader.INSTANCE;
                flag1 = false;
                throwable = (Throwable) null;

                try {
                    rainbowfontshader = (RainbowFontShader) closeable;
                    flag2 = false;
                    s3 = this.displayText;
                    if (length) {
                        l = 0;
                    } else if (StringsKt.equals(s2, "Fade", true)) {
                        color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), aint[0] * 100, this.displayText.length() * 200);
                        Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(Color(redV…displayText.length * 200)");
                        l = color.getRGB();
                    } else if (StringsKt.equals(s2, "Astolfo", true)) {
                        l = RenderUtils.Astolfo(aint[0] * 100, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                    } else if (StringsKt.equals(s2, "NewRainbow", true)) {
                        l = RenderUtils.getRainbow(aint[0] * 100, ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                    } else if (StringsKt.equals(s2, "Gident", true)) {
                        color = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) aint[0]) / (double) 10);
                        Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO…le() + counter[0]) / 10))");
                        l = color.getRGB();
                    } else {
                        l = color;
                    }

                    fontRenderer.drawString(s3, 0.0F, 0.0F, l, ((Boolean) this.shadow.get()).booleanValue());
                    if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen()) && this.editTicks <= 40) {
                        f3 = (float) fontRenderer.getStringWidth(this.displayText);
                        if (length) {
                            l = 0;
                        } else if (StringsKt.equals(s2, "Fade", true)) {
                            color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), aint[0] * 100, this.displayText.length() * 200);
                            Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(Color(redV…displayText.length * 200)");
                            l = color.getRGB();
                        } else if (StringsKt.equals(s2, "Astolfo", true)) {
                            l = RenderUtils.Astolfo(aint[0] * 100, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                        } else if (StringsKt.equals(s2, "Gident", true)) {
                            color = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) aint[0]) / (double) 10);
                            Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO…le() + counter[0]) / 10))");
                            l = color.getRGB();
                        } else {
                            l = StringsKt.equals(s2, "NewRainbow", true) ? RenderUtils.getRainbow(aint[0] * 100, ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                        }

                        fontRenderer.drawString("_", f3, 0.0F, l, ((Boolean) this.shadow.get()).booleanValue());
                    }

                    ++aint[0];
                    unit = Unit.INSTANCE;
                } catch (Throwable throwable1) {
                    throwable = throwable1;
                    throw throwable1;
                } finally {
                    CloseableKt.closeFinally(closeable, throwable);
                }
            } else {
                int j1 = 0;

                f = ((Number) this.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number) this.rainbowX.get()).floatValue();
                f1 = ((Number) this.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number) this.rainbowY.get()).floatValue();
                f2 = (float) (System.currentTimeMillis() % (long) 10000) / 10000.0F;
                it = false;
                if (rainbow) {
                    RainbowFontShader.INSTANCE.setStrengthX(f);
                    RainbowFontShader.INSTANCE.setStrengthY(f1);
                    RainbowFontShader.INSTANCE.setOffset(f2);
                    RainbowFontShader.INSTANCE.startShader();
                }

                closeable = (Closeable) RainbowFontShader.INSTANCE;
                flag1 = false;
                throwable = (Throwable) null;

                try {
                    rainbowfontshader = (RainbowFontShader) closeable;
                    flag2 = false;
                    char[] achar1 = charArray;
                    int k1 = charArray.length;

                    for (int l1 = 0; l1 < k1; ++l1) {
                        char charIndex = achar1[l1];
                        boolean rainbow1 = StringsKt.equals(s2, "Rainbow", true);

                        s3 = String.valueOf(charIndex);
                        f3 = (float) j1;
                        if (rainbow1) {
                            l = 0;
                        } else if (StringsKt.equals(s2, "Fade", true)) {
                            color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), aint[0] * 100, this.displayText.length() * 200);
                            Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(Color(redV…displayText.length * 200)");
                            l = color.getRGB();
                        } else if (StringsKt.equals(s2, "Astolfo", true)) {
                            l = RenderUtils.Astolfo(aint[0] * 100, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                        } else if (StringsKt.equals(s2, "NewRainbow", true)) {
                            l = RenderUtils.getRainbow(aint[0] * 100, ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                        } else if (StringsKt.equals(s2, "Gident", true)) {
                            color = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) aint[0]) / (double) 10);
                            Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO…le() + counter[0]) / 10))");
                            l = color.getRGB();
                        } else {
                            l = color;
                        }

                        fontRenderer.drawString(s3, f3, 0.0F, l, ((Boolean) this.shadow.get()).booleanValue());
                        ++aint[0];
                        aint[0] = RangesKt.coerceIn(aint[0], 0, this.displayText.length());
                        j1 += fontRenderer.getStringWidth(String.valueOf(charIndex));
                    }

                    if (this.editMode && MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen()) && this.editTicks <= 40) {
                        if (rainbow) {
                            l = 0;
                        } else if (StringsKt.equals(s2, "Fade", true)) {
                            color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), aint[0] * 100, this.displayText.length() * 200);
                            Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(Color(redV…displayText.length * 200)");
                            l = color.getRGB();
                        } else if (StringsKt.equals(s2, "Astolfo", true)) {
                            l = RenderUtils.Astolfo(aint[0] * 100, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                        } else if (StringsKt.equals(s2, "Gident", true)) {
                            color = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) aint[0]) / (double) 10);
                            Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO…le() + counter[0]) / 10))");
                            l = color.getRGB();
                        } else {
                            l = StringsKt.equals(s2, "NewRainbow", true) ? RenderUtils.getRainbow(aint[0] * 100, ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                        }

                        fontRenderer.drawString("_", length2, 0.0F, l, ((Boolean) this.shadow.get()).booleanValue());
                    }

                    unit = Unit.INSTANCE;
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    CloseableKt.closeFinally(closeable, throwable);
                }
            }

            if (this.editMode && !MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
                this.editMode = false;
                this.updateElement();
            }

            return new Border(-2.0F, -2.0F, length2, (float) fontRenderer.getFontHeight());
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

            if (ChatAllowedCharacters.isAllowedCharacter(c) || c == 167) {
                this.displayString.set((String) this.displayString.get() + c);
            }

            this.updateElement();
        }

    }

    @NotNull
    public final Text setColor(@NotNull Color c) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        this.redValue.set((Object) Integer.valueOf(c.getRed()));
        this.greenValue.set((Object) Integer.valueOf(c.getGreen()));
        this.blueValue.set((Object) Integer.valueOf(c.getBlue()));
        return this;
    }

    public final void drawRect(float x, float y, float x2, float y2, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        this.glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public final void drawRect(double x, double y, double x2, double y2, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        this.glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public final void glColor(int red, int green, int blue, int alpha) {
        GlStateManager.color((float) red / 255.0F, (float) green / 255.0F, (float) blue / 255.0F, (float) alpha / 255.0F);
    }

    public final void glColor(@NotNull Color color) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;
        float alpha = (float) color.getAlpha() / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public final void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0F;
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public Text(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.Mode = new ListValue("Border-Mode", new String[] { "Slide", "Skeet", "Top", "Onetap"}, "Onetap");
        this.displayString = new TextValue("DisplayText", "");
        this.redValue = new IntegerValue("Text-R", 255, 0, 255);
        this.greenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.blueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.colorRedValue2 = new IntegerValue("Text-R2", 0, 0, 255);
        this.colorGreenValue2 = new IntegerValue("Text-G2", 111, 0, 255);
        this.colorBlueValue2 = new IntegerValue("Text-B2", 255, 0, 255);
        this.gidentspeed = new IntegerValue("GidentSpeed", 100, 1, 1000);
        this.newRainbowIndex = new IntegerValue("NewRainbowOffset", 1, 1, 50);
        this.astolfoRainbowOffset = new IntegerValue("AstolfoOffset", 5, 1, 20);
        this.astolfoclient = new IntegerValue("AstolfoRange", 109, 1, 765);
        this.astolfoRainbowIndex = new IntegerValue("AstolfoIndex", 109, 1, 300);
        this.saturationValue = new FloatValue("Saturation", 0.9F, 0.0F, 1.0F);
        this.colorModeValue = new ListValue("Text-Color", new String[] { "Custom", "Rainbow", "Fade", "Astolfo", "NewRainbow", "Gident"}, "Gident");
        this.rainbowWM = new BoolValue("Watermark-Rainbow", false);
        this.markRValue = new IntegerValue("Watermark-Red", 255, 0, 255);
        this.markGValue = new IntegerValue("Watermark-Green", 255, 0, 255);
        this.markBValue = new IntegerValue("Watermark-Blue", 255, 0, 255);
        this.markR2Value = new IntegerValue("Watermark-Red2", 255, 0, 255);
        this.markG2Value = new IntegerValue("Watermark-Green2", 255, 0, 255);
        this.markB2Value = new IntegerValue("Watermark-Blue2", 255, 0, 255);
        this.rainbowX = new FloatValue("Rainbow-X", -1000.0F, -2000.0F, 2000.0F);
        this.rainbowY = new FloatValue("Rainbow-Y", -1000.0F, -2000.0F, 2000.0F);
        this.shadow = new BoolValue("Shadow", true);
        this.bord = new BoolValue("Border", false);
        this.slide = new BoolValue("Slide", false);
        this.char = new BoolValue("NotChar", false);
        this.slidedelay = new IntegerValue("SlideDelay", 100, 0, 1000);
        this.balpha = new IntegerValue("BordAlpha", 255, 0, 255);
        this.distanceValue = new IntegerValue("Distance", 0, 0, 400);
        this.amountValue = new IntegerValue("Amount", 25, 1, 50);
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.speedStr = "";
        this.displayText = "";
        this.slidetimer = new MSTimer();
        this.doslide = true;
    }

    public Text(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
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

    public Text() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }

    public static final void access$setFontValue$p(Text $this, FontValue <set-?>) {
        $this.fontValue = <set-?>;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u0011"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text$Companion;", "", "()V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "getDATE_FORMAT", "()Ljava/text/SimpleDateFormat;", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "DECIMAL_FORMAT_INT", "getDECIMAL_FORMAT_INT", "HOUR_FORMAT", "getHOUR_FORMAT", "defaultClient", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final SimpleDateFormat getDATE_FORMAT() {
            return Text.DATE_FORMAT;
        }

        @NotNull
        public final SimpleDateFormat getHOUR_FORMAT() {
            return Text.HOUR_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT() {
            return Text.DECIMAL_FORMAT;
        }

        @NotNull
        public final DecimalFormat getDECIMAL_FORMAT_INT() {
            return Text.DECIMAL_FORMAT_INT;
        }

        @NotNull
        public final Text defaultClient() {
            Text text = new Text(2.0D, 2.0D, 2.0F, (Side) null, 8, (DefaultConstructorMarker) null);

            text.displayString.set("%clientName%");
            text.shadow.set(Boolean.valueOf(true));
            FontValue fontvalue = text.fontValue;
            IFontRenderer ifontrenderer = Fonts.font40;

            Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
            fontvalue.set(ifontrenderer);
            text.setColor(new Color(0, 111, 255));
            return text;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
