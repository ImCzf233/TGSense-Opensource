package me.Skid.ui.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import me.Skid.ui.elements.targets.TargetStyle;
import me.Skid.ui.elements.targets.impl.Chill;
import me.Skid.ui.elements.targets.impl.Moon;
import me.Skid.ui.elements.targets.impl.Rice;
import me.Skid.ui.elements.targets.impl.Slowly;
import me.Skid.utils.BlurUtils;
import me.Skid.utils.ShadowUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "Target",
    disableScale = true
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\'\u0010]\u001a\b\u0012\u0004\u0012\u00020^0X2\u0012\u0010_\u001a\n\u0012\u0006\b\u0001\u0012\u00020R0`\"\u00020RH\u0007¢\u0006\u0002\u0010aJ\n\u0010b\u001a\u0004\u0018\u00010cH\u0016J\u0010\u0010d\u001a\u0004\u0018\u00010R2\u0006\u0010e\u001a\u00020^J\u0006\u0010f\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u0011\u0010\u0018\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0011\u0010\u001a\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0012R\u0011\u0010\u001c\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u0011\u0010\u001e\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b\'\u0010!R\u0011\u0010(\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010,\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b-\u0010!R\u0011\u0010.\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b/\u0010%R\u0011\u00100\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b1\u0010!R\u0011\u00102\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\u0012R\u001c\u00104\u001a\u0004\u0018\u000105X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u0011\u0010:\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b;\u0010%R\u0011\u0010<\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b=\u0010\u0012R\u0011\u0010>\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b?\u0010%R\u0011\u0010@\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\bA\u0010!R\u0011\u0010B\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u0012R\u0011\u0010D\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\u0012R\u0011\u0010F\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\bG\u0010+R\u0011\u0010H\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\bI\u0010\u0012R\u0011\u0010J\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\bK\u0010!R\u0011\u0010L\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\bM\u0010%R\u0011\u0010N\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\bO\u0010%R\u0017\u0010P\u001a\b\u0012\u0004\u0012\u00020R0Q¢\u0006\b\n\u0000\u001a\u0004\bS\u0010TR\u0011\u0010U\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\bV\u0010+R\u001e\u0010W\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030Y0X8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010TR\u0011\u0010[\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010\u0012¨\u0006g"},
    d2 = { "Lme/Skid/ui/elements/LBplusTarget;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "animProgress", "", "getAnimProgress", "()F", "setAnimProgress", "(F)V", "barColor", "Ljava/awt/Color;", "getBarColor", "()Ljava/awt/Color;", "setBarColor", "(Ljava/awt/Color;)V", "bgAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getBgAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "bgBlueValue", "getBgBlueValue", "bgColor", "getBgColor", "setBgColor", "bgGreenValue", "getBgGreenValue", "bgRedValue", "getBgRedValue", "blueValue", "getBlueValue", "blurStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getBlurStrength", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "blurValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getBlurValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "brightnessValue", "getBrightnessValue", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getColorModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "fadeSpeed", "getFadeSpeed", "fadeValue", "getFadeValue", "globalAnimSpeed", "getGlobalAnimSpeed", "greenValue", "getGreenValue", "mainTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getMainTarget", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "setMainTarget", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)V", "noAnimValue", "getNoAnimValue", "redValue", "getRedValue", "resetBar", "getResetBar", "saturationValue", "getSaturationValue", "shadowColorBlueValue", "getShadowColorBlueValue", "shadowColorGreenValue", "getShadowColorGreenValue", "shadowColorMode", "getShadowColorMode", "shadowColorRedValue", "getShadowColorRedValue", "shadowStrength", "getShadowStrength", "shadowValue", "getShadowValue", "showWithChatOpen", "getShowWithChatOpen", "styleList", "", "Lme/Skid/ui/elements/targets/TargetStyle;", "getStyleList", "()Ljava/util/List;", "styleValue", "getStyleValue", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "waveSecondValue", "getWaveSecondValue", "addStyles", "", "styles", "", "([Lme/Skid/ui/elements/targets/TargetStyle;)Ljava/util/List;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getCurrentStyle", "styleName", "getFadeProgress", "LiquidBounce"}
)
public final class LBplusTarget extends Element {

    @NotNull
    private final List styleList;
    @NotNull
    private final ListValue styleValue;
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final FloatValue blurStrength;
    @NotNull
    private final BoolValue shadowValue;
    @NotNull
    private final FloatValue shadowStrength;
    @NotNull
    private final ListValue shadowColorMode;
    @NotNull
    private final IntegerValue shadowColorRedValue;
    @NotNull
    private final IntegerValue shadowColorGreenValue;
    @NotNull
    private final IntegerValue shadowColorBlueValue;
    @NotNull
    private final BoolValue fadeValue;
    @NotNull
    private final FloatValue fadeSpeed;
    @NotNull
    private final BoolValue noAnimValue;
    @NotNull
    private final FloatValue globalAnimSpeed;
    @NotNull
    private final BoolValue showWithChatOpen;
    @NotNull
    private final BoolValue resetBar;
    @NotNull
    private final ListValue colorModeValue;
    @NotNull
    private final IntegerValue redValue;
    @NotNull
    private final IntegerValue greenValue;
    @NotNull
    private final IntegerValue blueValue;
    @NotNull
    private final FloatValue saturationValue;
    @NotNull
    private final FloatValue brightnessValue;
    @NotNull
    private final IntegerValue waveSecondValue;
    @NotNull
    private final IntegerValue bgRedValue;
    @NotNull
    private final IntegerValue bgGreenValue;
    @NotNull
    private final IntegerValue bgBlueValue;
    @NotNull
    private final IntegerValue bgAlphaValue;
    @Nullable
    private IEntityLivingBase mainTarget;
    private float animProgress;
    @NotNull
    private Color barColor;
    @NotNull
    private Color bgColor;

    @NotNull
    public final List getStyleList() {
        return this.styleList;
    }

    @NotNull
    public final ListValue getStyleValue() {
        return this.styleValue;
    }

    @NotNull
    public final BoolValue getBlurValue() {
        return this.blurValue;
    }

    @NotNull
    public final FloatValue getBlurStrength() {
        return this.blurStrength;
    }

    @NotNull
    public final BoolValue getShadowValue() {
        return this.shadowValue;
    }

    @NotNull
    public final FloatValue getShadowStrength() {
        return this.shadowStrength;
    }

    @NotNull
    public final ListValue getShadowColorMode() {
        return this.shadowColorMode;
    }

    @NotNull
    public final IntegerValue getShadowColorRedValue() {
        return this.shadowColorRedValue;
    }

    @NotNull
    public final IntegerValue getShadowColorGreenValue() {
        return this.shadowColorGreenValue;
    }

    @NotNull
    public final IntegerValue getShadowColorBlueValue() {
        return this.shadowColorBlueValue;
    }

    @NotNull
    public final BoolValue getFadeValue() {
        return this.fadeValue;
    }

    @NotNull
    public final FloatValue getFadeSpeed() {
        return this.fadeSpeed;
    }

    @NotNull
    public final BoolValue getNoAnimValue() {
        return this.noAnimValue;
    }

    @NotNull
    public final FloatValue getGlobalAnimSpeed() {
        return this.globalAnimSpeed;
    }

    @NotNull
    public final BoolValue getShowWithChatOpen() {
        return this.showWithChatOpen;
    }

    @NotNull
    public final BoolValue getResetBar() {
        return this.resetBar;
    }

    @NotNull
    public final ListValue getColorModeValue() {
        return this.colorModeValue;
    }

    @NotNull
    public final IntegerValue getRedValue() {
        return this.redValue;
    }

    @NotNull
    public final IntegerValue getGreenValue() {
        return this.greenValue;
    }

    @NotNull
    public final IntegerValue getBlueValue() {
        return this.blueValue;
    }

    @NotNull
    public final FloatValue getSaturationValue() {
        return this.saturationValue;
    }

    @NotNull
    public final FloatValue getBrightnessValue() {
        return this.brightnessValue;
    }

    @NotNull
    public final IntegerValue getWaveSecondValue() {
        return this.waveSecondValue;
    }

    @NotNull
    public final IntegerValue getBgRedValue() {
        return this.bgRedValue;
    }

    @NotNull
    public final IntegerValue getBgGreenValue() {
        return this.bgGreenValue;
    }

    @NotNull
    public final IntegerValue getBgBlueValue() {
        return this.bgBlueValue;
    }

    @NotNull
    public final IntegerValue getBgAlphaValue() {
        return this.bgAlphaValue;
    }

    @NotNull
    public List getValues() {
        boolean $this$forEach$iv = false;
        List valueList = (List) (new ArrayList());
        Iterable $this$forEach$iv1 = (Iterable) this.styleList;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv1.iterator();

        while (iterator.hasNext()) {
            Object element$iv = iterator.next();
            TargetStyle it = (TargetStyle) element$iv;
            boolean $i$a$-forEach-LBplusTarget$values$1 = false;

            valueList.addAll((Collection) it.getValues());
        }

        return CollectionsKt.plus((Collection) CollectionsKt.toMutableList((Collection) super.getValues()), (Iterable) valueList);
    }

    @Nullable
    public final IEntityLivingBase getMainTarget() {
        return this.mainTarget;
    }

    public final void setMainTarget(@Nullable IEntityLivingBase <set-?>) {
        this.mainTarget = <set-?>;
    }

    public final float getAnimProgress() {
        return this.animProgress;
    }

    public final void setAnimProgress(float <set-?>) {
        this.animProgress = <set-?>;
    }

    @NotNull
    public final Color getBarColor() {
        return this.barColor;
    }

    public final void setBarColor(@NotNull Color <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.barColor = <set-?>;
    }

    @NotNull
    public final Color getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(@NotNull Color <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.bgColor = <set-?>;
    }

    @Nullable
    public Border drawElement() {
        TargetStyle targetstyle = this.getCurrentStyle((String) this.styleValue.get());

        if (targetstyle != null) {
            final TargetStyle mainStyle = targetstyle;
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
            } else {
                IEntityLivingBase actualTarget;
                Color color;
                label125: {
                    IEntityLivingBase kaTarget = ((KillAura) module).getTarget();

                    actualTarget = kaTarget != null && MinecraftInstance.classProvider.isEntityPlayer(kaTarget) ? kaTarget : (IEntityLivingBase) ((!(MinecraftInstance.mc.getCurrentScreen() instanceof GuiChat) || !((Boolean) this.showWithChatOpen.get()).booleanValue()) && !MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen()) ? null : MinecraftInstance.mc.getThePlayer());
                    String preBgColor = (String) this.colorModeValue.get();

                    switch (preBgColor.hashCode()) {
                    case -2137395588:
                        if (preBgColor.equals("Health")) {
                            color = actualTarget != null ? BlendUtils.getHealthColor(actualTarget.getHealth(), actualTarget.getMaxHealth()) : Color.green;
                            break label125;
                        }
                        break;

                    case -1656737386:
                        if (preBgColor.equals("Rainbow")) {
                            color = new Color(RenderUtils.getRainbowOpaque(((Number) this.waveSecondValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), 0));
                            break label125;
                        }
                        break;

                    case 83201:
                        if (preBgColor.equals("Sky")) {
                            color = ColorUtils.skyRainbow(0, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), 3.0D);
                            break label125;
                        }
                        break;

                    case 2181788:
                        if (preBgColor.equals("Fade")) {
                            color = ColorUtils.fade(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), 0, 100);
                            break label125;
                        }
                        break;

                    case 74357737:
                        if (preBgColor.equals("Mixer")) {
                            color = ColorMixer.getMixedColor(0, ((Number) this.waveSecondValue.get()).intValue());
                            break label125;
                        }
                        break;

                    case 2029746065:
                        if (preBgColor.equals("Custom")) {
                            color = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue());
                            break label125;
                        }
                    }

                    color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                    if (color == null) {
                        Intrinsics.throwNpe();
                    }
                }

                Color preBarColor = color;
                Color preBgColor1 = new Color(((Number) this.bgRedValue.get()).intValue(), ((Number) this.bgGreenValue.get()).intValue(), ((Number) this.bgBlueValue.get()).intValue(), ((Number) this.bgAlphaValue.get()).intValue());

                if (((Boolean) this.fadeValue.get()).booleanValue()) {
                    this.animProgress += 0.0075F * ((Number) this.fadeSpeed.get()).floatValue() * (float) RenderUtils.deltaTime * (actualTarget != null ? -1.0F : 1.0F);
                } else {
                    this.animProgress = 0.0F;
                }

                this.animProgress = RangesKt.coerceIn(this.animProgress, 0.0F, 1.0F);
                if (preBarColor == null) {
                    Intrinsics.throwNpe();
                }

                this.barColor = ColorUtils.reAlpha(preBarColor, (float) preBarColor.getAlpha() / 255.0F * (1.0F - this.animProgress));
                this.bgColor = ColorUtils.reAlpha(preBgColor1, (float) preBgColor1.getAlpha() / 255.0F * (1.0F - this.animProgress));
                if (actualTarget == null && ((Boolean) this.fadeValue.get()).booleanValue()) {
                    if (this.animProgress >= 1.0F) {
                        this.mainTarget = (IEntityLivingBase) null;
                    }
                } else {
                    this.mainTarget = actualTarget;
                }

                Border border = mainStyle.getBorder(this.mainTarget);

                if (border != null) {
                    Border returnBorder = border;
                    float borderWidth = returnBorder.getX2() - returnBorder.getX();
                    float borderHeight = returnBorder.getY2() - returnBorder.getY();

                    if (this.mainTarget == null) {
                        if (((Boolean) this.resetBar.get()).booleanValue()) {
                            mainStyle.setEasingHealth(0.0F);
                        }

                        if (mainStyle instanceof Rice) {
                            ((Rice) mainStyle).getParticleList().clear();
                        }

                        return returnBorder;
                    } else {
                        IEntityLivingBase ientitylivingbase = this.mainTarget;

                        if (this.mainTarget == null) {
                            Intrinsics.throwNpe();
                        }

                        final IEntityPlayer convertTarget = ientitylivingbase.asEntityPlayer();
                        final float calcScaleX = this.animProgress * (4.0F / (borderWidth / 2.0F));
                        final float calcScaleY = this.animProgress * (4.0F / (borderHeight / 2.0F));
                        final float calcTranslateX = borderWidth / 2.0F * calcScaleX;
                        final float calcTranslateY = borderHeight / 2.0F * calcScaleY;
                        float floatX;
                        float floatY;

                        if (((Boolean) this.shadowValue.get()).booleanValue() && mainStyle.getShaderSupport()) {
                            floatX = (float) this.getRenderX();
                            floatY = (float) this.getRenderY();
                            GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                            GL11.glPushMatrix();
                            ShadowUtils.INSTANCE.shadow(((Number) this.shadowStrength.get()).floatValue(), (Function0) (new Function0(0) {
                                public final void invoke() {
                                    GL11.glPushMatrix();
                                    GL11.glTranslated(LBplusTarget.this.getRenderX(), LBplusTarget.this.getRenderY(), 0.0D);
                                    if (((Boolean) LBplusTarget.this.getFadeValue().get()).booleanValue()) {
                                        GL11.glTranslatef(calcTranslateX, calcTranslateY, 0.0F);
                                        GL11.glScalef(1.0F - calcScaleX, 1.0F - calcScaleY, 1.0F - calcScaleX);
                                    }

                                    mainStyle.handleShadow(convertTarget);
                                    GL11.glPopMatrix();
                                }
                            }), (Function0) (new Function0(0) {
                                public final void invoke() {
                                    GL11.glPushMatrix();
                                    GL11.glTranslated(LBplusTarget.this.getRenderX(), LBplusTarget.this.getRenderY(), 0.0D);
                                    if (((Boolean) LBplusTarget.this.getFadeValue().get()).booleanValue()) {
                                        GL11.glTranslatef(calcTranslateX, calcTranslateY, 0.0F);
                                        GL11.glScalef(1.0F - calcScaleX, 1.0F - calcScaleY, 1.0F - calcScaleX);
                                    }

                                    mainStyle.handleShadowCut(convertTarget);
                                    GL11.glPopMatrix();
                                }
                            }));
                            GL11.glPopMatrix();
                            GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
                        }

                        if (((Boolean) this.blurValue.get()).booleanValue() && mainStyle.getShaderSupport()) {
                            floatX = (float) this.getRenderX();
                            floatY = (float) this.getRenderY();
                            GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                            GL11.glPushMatrix();
                            BlurUtils.blur(floatX + returnBorder.getX(), floatY + returnBorder.getY(), floatX + returnBorder.getX2(), floatY + returnBorder.getY2(), ((Number) this.blurStrength.get()).floatValue() * (1.0F - this.animProgress), false, (Function0) (new Function0(0) {
                                public final void invoke() {
                                    GL11.glPushMatrix();
                                    GL11.glTranslated(LBplusTarget.this.getRenderX(), LBplusTarget.this.getRenderY(), 0.0D);
                                    if (((Boolean) LBplusTarget.this.getFadeValue().get()).booleanValue()) {
                                        GL11.glTranslatef(calcTranslateX, calcTranslateY, 0.0F);
                                        GL11.glScalef(1.0F - calcScaleX, 1.0F - calcScaleY, 1.0F - calcScaleX);
                                    }

                                    mainStyle.handleBlur(convertTarget);
                                    GL11.glPopMatrix();
                                }
                            }));
                            GL11.glPopMatrix();
                            GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
                        }

                        if (((Boolean) this.fadeValue.get()).booleanValue()) {
                            GL11.glPushMatrix();
                            GL11.glTranslatef(calcTranslateX, calcTranslateY, 0.0F);
                            GL11.glScalef(1.0F - calcScaleX, 1.0F - calcScaleY, 1.0F - calcScaleX);
                        }

                        if (mainStyle instanceof Chill) {
                            ((Chill) mainStyle).updateData((float) this.getRenderX() + calcTranslateX, (float) this.getRenderY() + calcTranslateY, calcScaleX, calcScaleY);
                        }

                        mainStyle.drawTarget((IEntityLivingBase) convertTarget);
                        if (((Boolean) this.fadeValue.get()).booleanValue()) {
                            GL11.glPopMatrix();
                        }

                        GlStateManager.resetColor();
                        return returnBorder;
                    }
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public final float getFadeProgress() {
        return this.animProgress;
    }

    @SafeVarargs
    @NotNull
    public final List addStyles(@NotNull TargetStyle... styles) {
        Intrinsics.checkParameterIsNotNull(styles, "styles");
        boolean $this$forEach$iv = false;
        List nameList = (List) (new ArrayList());
        boolean $i$f$forEach = false;
        TargetStyle[] atargetstyle = styles;
        int i = styles.length;

        for (int j = 0; j < i; ++j) {
            TargetStyle element$iv = atargetstyle[j];
            boolean $i$a$-forEach-LBplusTarget$addStyles$1 = false;

            this.styleList.add(element$iv);
            nameList.add(element$iv.getName());
        }

        return nameList;
    }

    @Nullable
    public final TargetStyle getCurrentStyle(@NotNull String styleName) {
        Intrinsics.checkParameterIsNotNull(styleName, "styleName");
        Iterable iterable = (Iterable) this.styleList;
        boolean flag = false;
        boolean flag1 = false;
        Iterator iterator = iterable.iterator();

        Object object;

        while (true) {
            if (iterator.hasNext()) {
                Object object1 = iterator.next();
                TargetStyle it = (TargetStyle) object1;
                boolean $i$a$-find-LBplusTarget$getCurrentStyle$1 = false;

                if (!StringsKt.equals(it.getName(), styleName, true)) {
                    continue;
                }

                object = object1;
                break;
            }

            object = null;
            break;
        }

        return (TargetStyle) object;
    }

    public LBplusTarget() {
        super(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
        boolean $this$toTypedArray$iv = false;
        List list = (List) (new ArrayList());

        this.styleList = list;
        this.blurValue = new BoolValue("Blur", false);
        this.blurStrength = new FloatValue("Blur-Strength", 1.0F, 0.01F, 40.0F);
        this.shadowValue = new BoolValue("Shadow", false);
        this.shadowStrength = new FloatValue("Shadow-Strength", 1.0F, 0.01F, 40.0F);
        this.shadowColorMode = new ListValue("Shadow-Color", new String[] { "Background", "Custom", "Bar"}, "Background");
        this.shadowColorRedValue = new IntegerValue("Shadow-Red", 0, 0, 255);
        this.shadowColorGreenValue = new IntegerValue("Shadow-Green", 111, 0, 255);
        this.shadowColorBlueValue = new IntegerValue("Shadow-Blue", 255, 0, 255);
        this.fadeValue = new BoolValue("FadeAnim", false);
        this.fadeSpeed = new FloatValue("Fade-Speed", 1.0F, 0.0F, 5.0F);
        this.noAnimValue = new BoolValue("No-Animation", false);
        this.globalAnimSpeed = new FloatValue("Global-AnimSpeed", 3.0F, 1.0F, 9.0F);
        this.showWithChatOpen = new BoolValue("Show-ChatOpen", true);
        this.resetBar = new BoolValue("ResetBarWhenHiding", false);
        this.colorModeValue = new ListValue("Color", new String[] { "Custom", "Rainbow", "Sky", "Slowly", "Fade", "Mixer", "Health"}, "Custom");
        this.redValue = new IntegerValue("Red", 252, 0, 255);
        this.greenValue = new IntegerValue("Green", 96, 0, 255);
        this.blueValue = new IntegerValue("Blue", 66, 0, 255);
        this.saturationValue = new FloatValue("Saturation", 1.0F, 0.0F, 1.0F);
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.waveSecondValue = new IntegerValue("Seconds", 2, 1, 10);
        this.bgRedValue = new IntegerValue("Background-Red", 0, 0, 255);
        this.bgGreenValue = new IntegerValue("Background-Green", 0, 0, 255);
        this.bgBlueValue = new IntegerValue("Background-Blue", 0, 0, 255);
        this.bgAlphaValue = new IntegerValue("Background-Alpha", 160, 0, 255);
        Collection $this$toTypedArray$iv1 = (Collection) this.addStyles(new TargetStyle[] { (TargetStyle) (new me.Skid.ui.elements.targets.impl.LiquidBounce(this)), (TargetStyle) (new Chill(this)), (TargetStyle) (new Rice(this)), (TargetStyle) (new Slowly(this)), (TargetStyle) (new Moon(this))});
        String s = "Style";
        boolean $i$f$toTypedArray = false;
        Object[] aobject = $this$toTypedArray$iv1.toArray(new String[0]);

        if (aobject == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } else {
            Object[] aobject1 = aobject;
            String[] astring = (String[]) aobject1;
            String s1 = "LiquidBounce";
            String[] astring1 = astring;

            this.styleValue = new ListValue(s, astring1, s1);
            this.barColor = new Color(-1);
            this.bgColor = new Color(-1);
        }
    }
}
