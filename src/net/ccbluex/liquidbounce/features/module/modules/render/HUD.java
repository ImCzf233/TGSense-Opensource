package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import me.Skid.Tenacity.utils.normal.Utils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "HUD",
    description = "Toggles visibility of the HUD.",
    category = ModuleCategory.RENDER,
    array = false
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 c2\u00020\u0001:\u0001cB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010U\u001a\u0002042\u0006\u0010V\u001a\u000204J\u0010\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0007J\u0012\u0010[\u001a\u00020X2\b\u0010Y\u001a\u0004\u0018\u00010\\H\u0007J\u0010\u0010]\u001a\u00020X2\u0006\u0010Y\u001a\u00020^H\u0007J\u0010\u0010_\u001a\u00020X2\u0006\u0010Y\u001a\u00020`H\u0007J\u0012\u0010a\u001a\u00020X2\b\u0010Y\u001a\u0004\u0018\u00010bH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\u0017\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\u0019\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\u001b\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u001d\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0010R\u000e\u0010\u001f\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0014R\u000e\u0010\"\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010#\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0010R\u0011\u0010%\u001a\u00020&¢\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010)\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0010R\u0011\u0010+\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0014R\u0011\u0010-\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0014R\u0011\u0010/\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0014R\u0011\u00101\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0014R\u000e\u00103\u001a\u000204X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u00105\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0010R\u0011\u00107\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0010R\u0011\u00109\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0010R\u0011\u0010;\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0014R\u0011\u0010=\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0014R\u0011\u0010?\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0014R\u0011\u0010A\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0006R\u0011\u0010C\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u0006R\u0011\u0010E\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\u0014R\u0011\u0010G\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\u0006R\u0011\u0010I\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u0006R\u0011\u0010K\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\u0014R\u001a\u0010M\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u000e\u0010R\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020TX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006d"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "BlurStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getBlurStrength", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "ClientName", "Lnet/ccbluex/liquidbounce/value/TextValue;", "getClientName", "()Lnet/ccbluex/liquidbounce/value/TextValue;", "DevName", "getDevName", "Info", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getInfo", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "Radius", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getRadius", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "animHotbarValue", "getAnimHotbarValue", "b", "getB", "b2", "getB2", "b3", "getB3", "blackHotbarValue", "getBlackHotbarValue", "blurValue", "bs", "getBs", "changeValue", "containerBackground", "getContainerBackground", "customColor", "Ljava/awt/Color;", "getCustomColor", "()Ljava/awt/Color;", "fontChatValue", "getFontChatValue", "g", "getG", "g2", "getG2", "g3", "getG3", "gs", "getGs", "hotBarX", "", "hueInterpolation", "getHueInterpolation", "inventoryParticle", "getInventoryParticle", "modeValue", "getModeValue", "r", "getR", "r2", "getR2", "r3", "getR3", "rainbowBrightnessValue", "getRainbowBrightnessValue", "rainbowSaturationValue", "getRainbowSaturationValue", "rainbowSpeedValue", "getRainbowSpeedValue", "rainbowStartValue", "getRainbowStartValue", "rainbowStopValue", "getRainbowStopValue", "rs", "getRs", "ticks", "getTicks", "()F", "setTicks", "(F)V", "toggleMessageValue", "toggleSoundValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getAnimPos", "pos", "onKey", "", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onScreen", "Lnet/ccbluex/liquidbounce/event/ScreenEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "Companion", "LiquidBounce"}
)
public final class HUD extends Module {

    private final BoolValue toggleMessageValue = new BoolValue("DisplayToggleMessage", true);
    private final BoolValue changeValue = new BoolValue("ChangeModeName", true);
    @NotNull
    private final BoolValue Info = new BoolValue("Info", true);
    @NotNull
    private final BoolValue modeValue = new BoolValue("Jellotext", true);
    private final ListValue toggleSoundValue = new ListValue("ToggleSound", new String[] { "None", "Default", "Custom"}, "Custom");
    @NotNull
    private final BoolValue containerBackground = new BoolValue("Container-Background", false);
    @NotNull
    private final BoolValue animHotbarValue = new BoolValue("AnimatedHotbar", true);
    @NotNull
    private final BoolValue blackHotbarValue = new BoolValue("BlackHotbar", true);
    @NotNull
    private final BoolValue inventoryParticle = new BoolValue("InventoryParticle", false);
    private final BoolValue blurValue = new BoolValue("Blur", false);
    @NotNull
    private final IntegerValue Radius = new IntegerValue("BlurRadius", 10, 1, 50);
    @NotNull
    private final BoolValue fontChatValue = new BoolValue("FontChat", false);
    @NotNull
    private final TextValue ClientName = new TextValue("ClientName", "TGSense");
    @NotNull
    private final TextValue DevName = new TextValue("DevName", "me.Skid");
    @NotNull
    private final BoolValue hueInterpolation = new BoolValue("Hue Interpolate", false);
    @NotNull
    private final IntegerValue r = new IntegerValue("NovolineRed", 0, 0, 255);
    @NotNull
    private final IntegerValue g = new IntegerValue("NovolineGreen", 255, 0, 255);
    @NotNull
    private final IntegerValue b = new IntegerValue("NovolineBlue", 255, 0, 255);
    @NotNull
    private final IntegerValue r2 = new IntegerValue("NovolineRed2", 255, 0, 255);
    @NotNull
    private final IntegerValue g2 = new IntegerValue("NovolineGreen2", 40, 0, 255);
    @NotNull
    private final IntegerValue b2 = new IntegerValue("NovolineBlue2", 255, 0, 255);
    @NotNull
    private final IntegerValue r3 = new IntegerValue("INFORed", 225, 225, 255);
    @NotNull
    private final IntegerValue g3 = new IntegerValue("INFOGreen", 255, 0, 255);
    @NotNull
    private final IntegerValue b3 = new IntegerValue("INFOBlue", 255, 0, 255);
    @NotNull
    private final IntegerValue rs = new IntegerValue("ShadowRed", 0, 0, 0);
    @NotNull
    private final IntegerValue gs = new IntegerValue("ShadowGreen", 0, 0, 0);
    @NotNull
    private final IntegerValue bs = new IntegerValue("ShadowBlue", 0, 0, 0);
    @NotNull
    private final FloatValue rainbowStartValue = new FloatValue("RainbowStart", 0.41F, 0.0F, 1.0F);
    @NotNull
    private final FloatValue rainbowStopValue = new FloatValue("RainbowStop", 0.58F, 0.0F, 1.0F);
    @NotNull
    private final FloatValue BlurStrength = new FloatValue("BlurStrength", 15.0F, 0.0F, 30.0F);
    @NotNull
    private final FloatValue rainbowSaturationValue = new FloatValue("RainbowSaturation", 0.7F, 0.0F, 1.0F);
    @NotNull
    private final FloatValue rainbowBrightnessValue = new FloatValue("RainbowBrightness", 1.0F, 0.0F, 1.0F);
    @NotNull
    private final IntegerValue rainbowSpeedValue = new IntegerValue("RainbowSpeed", 1500, 500, 7000);
    @NotNull
    private final Color customColor;
    private float hotBarX;
    private float ticks;
    @JvmField
    @NotNull
    public static final BoolValue Hotbarblur = new BoolValue("BlurGuiButton", false);
    @NotNull
    private static final ListValue shadowValue = new ListValue("ShadowMode", new String[] { "Test", "LiquidBounce", "Outline", "Default", "TGSense", "Autumn", "Blue", "TGSense2"}, "Outline");
    public static final HUD.Companion Companion = new HUD.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final BoolValue getInfo() {
        return this.Info;
    }

    @NotNull
    public final BoolValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final BoolValue getContainerBackground() {
        return this.containerBackground;
    }

    @NotNull
    public final BoolValue getAnimHotbarValue() {
        return this.animHotbarValue;
    }

    @NotNull
    public final BoolValue getBlackHotbarValue() {
        return this.blackHotbarValue;
    }

    @NotNull
    public final BoolValue getInventoryParticle() {
        return this.inventoryParticle;
    }

    @NotNull
    public final IntegerValue getRadius() {
        return this.Radius;
    }

    @NotNull
    public final BoolValue getFontChatValue() {
        return this.fontChatValue;
    }

    @NotNull
    public final TextValue getClientName() {
        return this.ClientName;
    }

    @NotNull
    public final TextValue getDevName() {
        return this.DevName;
    }

    @NotNull
    public final BoolValue getHueInterpolation() {
        return this.hueInterpolation;
    }

    @NotNull
    public final IntegerValue getR() {
        return this.r;
    }

    @NotNull
    public final IntegerValue getG() {
        return this.g;
    }

    @NotNull
    public final IntegerValue getB() {
        return this.b;
    }

    @NotNull
    public final IntegerValue getR2() {
        return this.r2;
    }

    @NotNull
    public final IntegerValue getG2() {
        return this.g2;
    }

    @NotNull
    public final IntegerValue getB2() {
        return this.b2;
    }

    @NotNull
    public final IntegerValue getR3() {
        return this.r3;
    }

    @NotNull
    public final IntegerValue getG3() {
        return this.g3;
    }

    @NotNull
    public final IntegerValue getB3() {
        return this.b3;
    }

    @NotNull
    public final IntegerValue getRs() {
        return this.rs;
    }

    @NotNull
    public final IntegerValue getGs() {
        return this.gs;
    }

    @NotNull
    public final IntegerValue getBs() {
        return this.bs;
    }

    @NotNull
    public final FloatValue getRainbowStartValue() {
        return this.rainbowStartValue;
    }

    @NotNull
    public final FloatValue getRainbowStopValue() {
        return this.rainbowStopValue;
    }

    @NotNull
    public final FloatValue getBlurStrength() {
        return this.BlurStrength;
    }

    @NotNull
    public final FloatValue getRainbowSaturationValue() {
        return this.rainbowSaturationValue;
    }

    @NotNull
    public final FloatValue getRainbowBrightnessValue() {
        return this.rainbowBrightnessValue;
    }

    @NotNull
    public final IntegerValue getRainbowSpeedValue() {
        return this.rainbowSpeedValue;
    }

    @NotNull
    public final Color getCustomColor() {
        return this.customColor;
    }

    public final float getTicks() {
        return this.ticks;
    }

    public final void setTicks(float <set-?>) {
        this.ticks = <set-?>;
    }

    @EventTarget
    public final void onRender2D(@Nullable Render2DEvent event) {
        new ScaledResolution(Minecraft.getMinecraft());

        if (((Boolean) this.modeValue.get()).booleanValue()) {
            byte xDif = 4;
            byte sigmaX = 8;

            if (!((Boolean) this.changeValue.get()).booleanValue()) {
                RenderUtils.drawShadowImage(sigmaX - 12 - Fonts.jellolight38.getStringWidth("Sigma") / 2 - 8, xDif + 1, 125, 50, new ResourceLocation("langya/shadow/arraylistshadow.png"));
                Fonts.jellolightBig.drawString("Sigma", sigmaX, xDif + 1, (new Color(255, 255, 255, 130)).getRGB());
                Fonts.jellolight38.drawCenteredString("Jello", (float) (sigmaX + 10), (float) (xDif + 28), (new Color(255, 255, 255, 170)).getRGB());
            } else {
                RenderUtils.drawShadowImage(sigmaX - 12 - Utils.fr.getStringWidth((String) this.ClientName.get()) / 2 - 8, xDif + 5, 125, 50, new ResourceLocation("langya/shadow/arraylistshadow.png"));
                Fonts.jellolightBig.drawString((String) this.ClientName.get(), sigmaX, xDif + 1, (new Color(255, 255, 255, 130)).getRGB());
                Fonts.jellolight38.drawCenteredString("Jello", (float) (sigmaX + 10), (float) (xDif + 28), (new Color(255, 255, 255, 170)).getRGB());
            }
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double d0 = ientityplayersp.getPosX();
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        double d1 = d0 - ientityplayersp1.getPrevPosX();

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        d0 = ientityplayersp.getPosZ();
        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        double zDif = d0 - ientityplayersp1.getPrevPosZ();
        double lastDist = Math.sqrt(d1 * d1 + zDif * zDif) * 20.0D;
        <undefinedtype> $fun$heights$1 = null.INSTANCE;

        if (((Boolean) this.Info.get()).booleanValue()) {
            String stringZ1 = "";
            String Vis = "230110";
            String name11 = "Ver 100223";

            GlStateManager.resetColor();
            int stringZ = 0;

            for (int name1 = ((CharSequence) name11).length(); stringZ < name1; ++stringZ) {
                Fonts.font40.drawString(String.valueOf(name11.charAt(stringZ)), 0.0F + (float) Fonts.font40.getStringWidth(stringZ1), (float) ($fun$heights$1.invoke() - 30), (new Color(((Number) this.r3.get()).intValue(), ((Number) this.g3.get()).intValue(), ((Number) this.b3.get()).intValue())).getRGB(), true);
                stringZ1 = stringZ1 + String.valueOf(name11.charAt(stringZ));
            }

            String s = "";
            String s1 = "XYZ: ";

            GlStateManager.resetColor();
            int string1 = 0;
            int name = s1.length() - 1;
            IFontRenderer ifontrenderer;

            if (string1 <= name) {
                while (true) {
                    Fonts.font40.drawString(String.valueOf(s1.charAt(string1)), 0.0F + (float) Fonts.font40.getStringWidth(s), (float) ($fun$heights$1.invoke() - 20), (new Color(((Number) this.r3.get()).intValue(), ((Number) this.g3.get()).intValue(), ((Number) this.b3.get()).intValue())).getRGB(), true);
                    s = s + String.valueOf(s1.charAt(string1));
                    ifontrenderer = Fonts.font40;
                    StringBuilder stringbuilder = new StringBuilder();
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    stringbuilder = stringbuilder.append(String.valueOf((int) ientityplayersp2.getPosX())).append(" ");
                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    stringbuilder = stringbuilder.append(String.valueOf((int) ientityplayersp2.getPosY())).append(" ");
                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    ifontrenderer.drawString(stringbuilder.append(String.valueOf((int) ientityplayersp2.getPosZ())).toString(), (float) Fonts.font40.getStringWidth("XYZ: "), (float) ($fun$heights$1.invoke() - 20), -1);
                    if (string1 == name) {
                        break;
                    }

                    ++string1;
                }
            }

            String s2 = "";
            String s3 = "Block/s: ";

            GlStateManager.resetColor();
            int i = 0;
            int i = s3.length() - 1;

            if (i <= i) {
                while (true) {
                    Fonts.font40.drawString(String.valueOf(s3.charAt(i)), 0.0F + (float) Fonts.font40.getStringWidth(s2), (float) ($fun$heights$1.invoke() - 10), (new Color(((Number) this.r3.get()).intValue(), ((Number) this.g3.get()).intValue(), ((Number) this.b3.get()).intValue())).getRGB(), true);
                    s2 = s2 + String.valueOf(s3.charAt(i));
                    ifontrenderer = Fonts.font40;
                    StringCompanionObject stringcompanionobject = StringCompanionObject.INSTANCE;
                    String s4 = "%.2f";
                    Object[] aobject = new Object[] { Double.valueOf(lastDist)};
                    IFontRenderer ifontrenderer1 = ifontrenderer;
                    boolean flag = false;
                    String s5 = String.format(s4, Arrays.copyOf(aobject, aobject.length));

                    Intrinsics.checkExpressionValueIsNotNull(s5, "java.lang.String.format(format, *args)");
                    String s6 = s5;

                    ifontrenderer1.drawString(s6, (float) Fonts.font40.getStringWidth("Block/s: "), (float) ($fun$heights$1.invoke() - 10), -1);
                    if (i == i) {
                        break;
                    }

                    ++i;
                }
            }

            Fonts.fontBold120.drawStringWithShadow("TGSense", 10, 15, (new Color(((Number) this.r3.get()).intValue(), ((Number) this.g3.get()).intValue(), ((Number) this.b3.get()).intValue())).getRGB());
            Fonts.bold35.drawStringWithShadow("Release".toString(), Fonts.com96.getStringWidth("TGSense") + 45, 14, (new Color(((Number) this.r3.get()).intValue(), ((Number) this.g3.get()).intValue(), ((Number) this.b3.get()).intValue())).getRGB());
        }

        if (!MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
            LiquidBounce.INSTANCE.getHud().render(false);
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        LiquidBounce.INSTANCE.getHud().update();
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onTick(@NotNull TickEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        LiquidBounce.INSTANCE.getModuleManager().setShouldNotify(((Boolean) this.toggleMessageValue.get()).booleanValue());
        LiquidBounce.INSTANCE.getModuleManager().setToggleSoundMode(ArraysKt.indexOf(this.toggleSoundValue.getValues(), this.toggleSoundValue.get()));
    }

    @EventTarget
    public final void onKey(@NotNull KeyEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        LiquidBounce.INSTANCE.getHud().handleKey('a', event.getKey());
    }

    public final float getAnimPos(float pos) {
        if (this.getState() && ((Boolean) this.animHotbarValue.get()).booleanValue()) {
            this.hotBarX = AnimationUtils.animate(pos, this.hotBarX, 0.02F * (float) RenderUtils.deltaTime);
        } else {
            this.hotBarX = pos;
        }

        return this.hotBarX;
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onScreen(@NotNull ScreenEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getTheWorld() != null && MinecraftInstance.mc.getThePlayer() != null) {
            if (this.getState() && ((Boolean) this.blurValue.get()).booleanValue() && !MinecraftInstance.mc.getEntityRenderer().isShaderActive() && event.getGuiScreen() != null && !MinecraftInstance.classProvider.isGuiChat(event.getGuiScreen()) && !MinecraftInstance.classProvider.isGuiHudDesigner(event.getGuiScreen())) {
                MinecraftInstance.mc.getEntityRenderer().loadShader(MinecraftInstance.classProvider.createResourceLocation("langya/blur.json"));
            } else if (MinecraftInstance.mc.getEntityRenderer().getShaderGroup() != null) {
                IShaderGroup ishadergroup = MinecraftInstance.mc.getEntityRenderer().getShaderGroup();

                if (ishadergroup == null) {
                    Intrinsics.throwNpe();
                }

                if (StringsKt.contains$default((CharSequence) ishadergroup.getShaderGroupName(), (CharSequence) "langya/blur.json", false, 2, (Object) null)) {
                    MinecraftInstance.mc.getEntityRenderer().stopUseShader();
                }
            }

        }
    }

    public HUD() {
        this.customColor = new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue());
        this.setState(true);
        this.setState(true);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD$Companion;", "", "()V", "Hotbarblur", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "shadowValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getShadowValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final ListValue getShadowValue() {
            return HUD.shadowValue;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
