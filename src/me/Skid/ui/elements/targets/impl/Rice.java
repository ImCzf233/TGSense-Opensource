package me.Skid.ui.elements.targets.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import me.Skid.ui.elements.LBplusTarget;
import me.Skid.ui.elements.targets.TargetStyle;
import me.Skid.ui.elements.targets.utils.Particle;
import me.Skid.ui.elements.targets.utils.ShapeType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0014\u00106\u001a\u0004\u0018\u0001072\b\u00104\u001a\u0004\u0018\u000105H\u0016J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0002J\u0010\u0010;\u001a\u0002032\u0006\u0010<\u001a\u00020=H\u0016J\u0010\u0010>\u001a\u0002032\u0006\u0010<\u001a\u00020=H\u0016J\u0010\u0010?\u001a\u0002032\u0006\u0010<\u001a\u00020=H\u0016J\u0010\u0010@\u001a\u0002032\u0006\u0010<\u001a\u00020=H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u001e\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0011\u0010 \u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0012R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0012R\u0011\u0010(\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0016R\u0011\u0010*\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b+\u0010%R\u0011\u0010,\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0016R\u0011\u0010.\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u0012R\u0011\u00100\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b1\u0010%¨\u0006A"},
    d2 = { "Lme/Skid/ui/elements/targets/impl/Rice;", "Lme/Skid/ui/elements/targets/TargetStyle;", "inst", "Lme/Skid/ui/elements/LBplusTarget;", "(Lme/Skid/ui/elements/LBplusTarget;)V", "generateAmountValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getGenerateAmountValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "gotDamaged", "", "gradientDistanceValue", "getGradientDistanceValue", "gradientLoopValue", "getGradientLoopValue", "gradientRoundedBarValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getGradientRoundedBarValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "maxParticleSize", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getMaxParticleSize", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "minParticleSize", "getMinParticleSize", "particleList", "", "Lme/Skid/ui/elements/targets/utils/Particle;", "getParticleList", "()Ljava/util/List;", "particleRange", "getParticleRange", "riceParticle", "getRiceParticle", "riceParticleCircle", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getRiceParticleCircle", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "riceParticleFade", "getRiceParticleFade", "riceParticleFadingSpeed", "getRiceParticleFadingSpeed", "riceParticleRect", "getRiceParticleRect", "riceParticleSpeed", "getRiceParticleSpeed", "riceParticleSpin", "getRiceParticleSpin", "riceParticleTriangle", "getRiceParticleTriangle", "drawTarget", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getColorAtIndex", "", "i", "handleBlur", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "handleDamage", "handleShadow", "handleShadowCut", "LiquidBounce"}
)
public final class Rice extends TargetStyle {

    @NotNull
    private final IntegerValue gradientLoopValue;
    @NotNull
    private final IntegerValue gradientDistanceValue;
    @NotNull
    private final BoolValue gradientRoundedBarValue;
    @NotNull
    private final BoolValue riceParticle;
    @NotNull
    private final BoolValue riceParticleSpin;
    @NotNull
    private final IntegerValue generateAmountValue;
    @NotNull
    private final ListValue riceParticleCircle;
    @NotNull
    private final ListValue riceParticleRect;
    @NotNull
    private final ListValue riceParticleTriangle;
    @NotNull
    private final FloatValue riceParticleSpeed;
    @NotNull
    private final BoolValue riceParticleFade;
    @NotNull
    private final FloatValue riceParticleFadingSpeed;
    @NotNull
    private final FloatValue particleRange;
    @NotNull
    private final FloatValue minParticleSize;
    @NotNull
    private final FloatValue maxParticleSize;
    @NotNull
    private final List particleList;
    private boolean gotDamaged;

    @NotNull
    public final IntegerValue getGradientLoopValue() {
        return this.gradientLoopValue;
    }

    @NotNull
    public final IntegerValue getGradientDistanceValue() {
        return this.gradientDistanceValue;
    }

    @NotNull
    public final BoolValue getGradientRoundedBarValue() {
        return this.gradientRoundedBarValue;
    }

    @NotNull
    public final BoolValue getRiceParticle() {
        return this.riceParticle;
    }

    @NotNull
    public final BoolValue getRiceParticleSpin() {
        return this.riceParticleSpin;
    }

    @NotNull
    public final IntegerValue getGenerateAmountValue() {
        return this.generateAmountValue;
    }

    @NotNull
    public final ListValue getRiceParticleCircle() {
        return this.riceParticleCircle;
    }

    @NotNull
    public final ListValue getRiceParticleRect() {
        return this.riceParticleRect;
    }

    @NotNull
    public final ListValue getRiceParticleTriangle() {
        return this.riceParticleTriangle;
    }

    @NotNull
    public final FloatValue getRiceParticleSpeed() {
        return this.riceParticleSpeed;
    }

    @NotNull
    public final BoolValue getRiceParticleFade() {
        return this.riceParticleFade;
    }

    @NotNull
    public final FloatValue getRiceParticleFadingSpeed() {
        return this.riceParticleFadingSpeed;
    }

    @NotNull
    public final FloatValue getParticleRange() {
        return this.particleRange;
    }

    @NotNull
    public final FloatValue getMinParticleSize() {
        return this.minParticleSize;
    }

    @NotNull
    public final FloatValue getMaxParticleSize() {
        return this.maxParticleSize;
    }

    @NotNull
    public final List getParticleList() {
        return this.particleList;
    }

    public void drawTarget(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        this.updateAnim(entity.getHealth());
        IFontRenderer font = Fonts.font40;
        String name = "Name: " + entity.getName();
        StringBuilder stringbuilder = (new StringBuilder()).append("Distance: ");
        DecimalFormat decimalformat = this.getDecimalFormat2();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        String info = stringbuilder.append(decimalformat.format(PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, (IEntity) entity))).toString();
        String healthName = this.getDecimalFormat2().format(Float.valueOf(this.getEasingHealth()));
        float length = RangesKt.coerceAtLeast((float) RangesKt.coerceAtLeast(font.getStringWidth(name), font.getStringWidth(info)) + 40.0F, 125.0F);
        String s = this.getDecimalFormat2().format(Float.valueOf(entity.getMaxHealth()));

        Intrinsics.checkExpressionValueIsNotNull(s, "decimalFormat2.format(entity.maxHealth)");
        float maxHealthLength = (float) font.getStringWidth(s);

        RenderUtils.drawRoundedRect(0.0F, 0.0F, 10.0F + length, 55.0F, 8, this.getTargetInstance().getBgColor().getRGB());
        String s1;

        if (((Boolean) this.riceParticle.get()).booleanValue()) {
            if (this.gotDamaged) {
                int scaleHT = 0;
                int barWidth = ((Number) this.generateAmountValue.get()).intValue();

                if (scaleHT <= barWidth) {
                    while (true) {
                        float $i$f$forEach;
                        float i;
                        float element$iv;
                        ShapeType.Companion shapetype_companion;
                        label137: {
                            $i$f$forEach = RandomUtils.INSTANCE.nextFloat(((Number) this.minParticleSize.get()).floatValue(), ((Number) this.maxParticleSize.get()).floatValue());
                            i = RandomUtils.INSTANCE.nextFloat(-((Number) this.particleRange.get()).floatValue(), ((Number) this.particleRange.get()).floatValue());
                            element$iv = RandomUtils.INSTANCE.nextFloat(-((Number) this.particleRange.get()).floatValue(), ((Number) this.particleRange.get()).floatValue());
                            String barStart = RandomUtils.INSTANCE.random(1, (StringsKt.equals((String) this.riceParticleCircle.get(), "none", true) ? "" : "c") + (StringsKt.equals((String) this.riceParticleRect.get(), "none", true) ? "" : "r") + (StringsKt.equals((String) this.riceParticleTriangle.get(), "none", true) ? "" : "t"));

                            shapetype_companion = ShapeType.Companion;
                            String s2;
                            boolean flag;
                            ShapeType.Companion shapetype_companion1;
                            StringBuilder stringbuilder1;
                            String s3;
                            StringBuilder stringbuilder2;

                            switch (barStart.hashCode()) {
                            case 99:
                                if (barStart.equals("c")) {
                                    stringbuilder2 = (new StringBuilder()).append("c_");
                                    s2 = (String) this.riceParticleCircle.get();
                                    stringbuilder1 = stringbuilder2;
                                    shapetype_companion1 = shapetype_companion;
                                    flag = false;
                                    if (s2 == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    s1 = s2.toLowerCase();
                                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                                    s3 = s1;
                                    shapetype_companion = shapetype_companion1;
                                    s = stringbuilder1.append(s3).toString();
                                    break label137;
                                }
                                break;

                            case 114:
                                if (barStart.equals("r")) {
                                    stringbuilder2 = (new StringBuilder()).append("r_");
                                    s2 = (String) this.riceParticleRect.get();
                                    stringbuilder1 = stringbuilder2;
                                    shapetype_companion1 = shapetype_companion;
                                    flag = false;
                                    if (s2 == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    s1 = s2.toLowerCase();
                                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                                    s3 = s1;
                                    shapetype_companion = shapetype_companion1;
                                    s = stringbuilder1.append(s3).toString();
                                    break label137;
                                }
                            }

                            stringbuilder2 = (new StringBuilder()).append("t_");
                            s2 = (String) this.riceParticleTriangle.get();
                            stringbuilder1 = stringbuilder2;
                            shapetype_companion1 = shapetype_companion;
                            flag = false;
                            if (s2 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s1 = s2.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                            s3 = s1;
                            shapetype_companion = shapetype_companion1;
                            s = stringbuilder1.append(s3).toString();
                        }

                        ShapeType shapetype = shapetype_companion.getTypeFromName(s);

                        if (shapetype == null) {
                            break;
                        }

                        ShapeType $i$a$-forEach-Rice$drawTarget$1 = shapetype;
                        List list = this.particleList;
                        float[] afloat = new float[] { 0.0F, 1.0F};
                        Color[] acolor = new Color[2];
                        Color color = Color.white;

                        Intrinsics.checkExpressionValueIsNotNull(Color.white, "Color.white");
                        acolor[0] = color;
                        acolor[1] = this.getTargetInstance().getBarColor();
                        Color color1 = BlendUtils.blendColors(afloat, acolor, RandomUtils.INSTANCE.nextBoolean() ? RandomUtils.INSTANCE.nextFloat(0.5F, 1.0F) : 0.0F);

                        Intrinsics.checkExpressionValueIsNotNull(color1, "BlendUtils.blendColors(\n…loat(0.5F, 1.0F) else 0F)");
                        list.add(new Particle(color1, i, element$iv, $i$f$forEach, $i$a$-forEach-Rice$drawTarget$1));
                        if (scaleHT == barWidth) {
                            break;
                        }

                        ++scaleHT;
                    }
                }

                this.gotDamaged = false;
            }

            boolean flag1 = false;
            List list1 = (List) (new ArrayList());
            Iterable iterable = (Iterable) this.particleList;
            boolean flag2 = false;
            Iterator iterator = iterable.iterator();

            while (iterator.hasNext()) {
                Object object = iterator.next();
                Particle particle = (Particle) object;
                boolean flag3 = false;

                if (particle.getAlpha() > 0.0F) {
                    particle.render(20.0F, 20.0F, ((Boolean) this.riceParticleFade.get()).booleanValue(), ((Number) this.riceParticleSpeed.get()).floatValue(), ((Number) this.riceParticleFadingSpeed.get()).floatValue(), ((Boolean) this.riceParticleSpin.get()).booleanValue());
                } else {
                    list1.add(particle);
                }
            }

            this.particleList.removeAll((Collection) list1);
        }

        float f = RangesKt.coerceIn((float) entity.getHurtTime() / (float) RangesKt.coerceAtLeast(entity.getMaxHurtTime(), 1), 0.0F, 1.0F);

        if (MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.getUniqueID()) != null) {
            INetworkPlayerInfo inetworkplayerinfo = MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.getUniqueID());

            if (inetworkplayerinfo == null) {
                Intrinsics.throwNpe();
            }

            this.drawHead(inetworkplayerinfo.getLocationSkin(), 5.0F + 15.0F * f * 0.2F, 5.0F + 15.0F * f * 0.2F, 1.0F - f * 0.2F, 30, 30, 1.0F, 0.4F + (1.0F - f) * 0.6F, 0.4F + (1.0F - f) * 0.6F, 1.0F - this.getTargetInstance().getFadeProgress());
        }

        GlStateManager.resetColor();
        font.drawString(name, 39.0F, 11.0F, this.getColor(-1).getRGB());
        font.drawString(info, 39.0F, 23.0F, this.getColor(-1).getRGB());
        float f1 = (length - 5.0F - maxHealthLength) * RangesKt.coerceIn(this.getEasingHealth() / entity.getMaxHealth(), 0.0F, 1.0F);

        Stencil.write(false);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        if (((Boolean) this.gradientRoundedBarValue.get()).booleanValue()) {
            if (f1 > 0.0F) {
                RenderUtils.fastRoundedRect(5.0F, 42.0F, 5.0F + f1, 48.0F, 3.0F);
            }
        } else {
            RenderUtils.quickDrawRect(5.0F, 42.0F, 5.0F + f1, 48.0F);
        }

        GL11.glDisable(3042);
        Stencil.erase(true);
        String s4 = (String) this.getTargetInstance().getColorModeValue().get();
        boolean flag4 = false;

        if (s4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            label100: {
                label99: {
                    s1 = s4.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s4 = s1;
                    switch (s4.hashCode()) {
                    case -1349088399:
                        if (s4.equals("custom")) {
                            break label99;
                        }
                        break;

                    case -1221262756:
                        if (s4.equals("health")) {
                            break label99;
                        }
                    }

                    int i = 0;
                    int j = ((Number) this.gradientLoopValue.get()).intValue() - 1;

                    if (i <= j) {
                        while (true) {
                            double d0 = (double) i / (double) ((Number) this.gradientLoopValue.get()).intValue() * (double) (length - 5.0F - maxHealthLength);
                            double barEnd = (double) (i + 1) / (double) ((Number) this.gradientLoopValue.get()).intValue() * (double) (length - 5.0F - maxHealthLength);

                            RenderUtils.drawGradientSideways(5.0D + d0, 42.0D, 5.0D + barEnd, 48.0D, this.getColorAtIndex(i), this.getColorAtIndex(i + 1));
                            if (i == j) {
                                break label100;
                            }

                            ++i;
                        }
                    }
                    break label100;
                }

                RenderUtils.drawRect(5.0F, 42.0F, length - maxHealthLength, 48.0F, this.getTargetInstance().getBarColor().getRGB());
            }

            Stencil.dispose();
            GlStateManager.resetColor();
            Intrinsics.checkExpressionValueIsNotNull(healthName, "healthName");
            font.drawString(healthName, 10.0F + f1, 41.0F, this.getColor(-1).getRGB());
        }
    }

    private final int getColorAtIndex(int i) {
        String s = (String) this.getTargetInstance().getColorModeValue().get();
        int i;
        Color color;

        switch (s.hashCode()) {
        case -1815582866:
            if (s.equals("Slowly")) {
                color = ColorUtils.LiquidSlowly(System.nanoTime(), i * ((Number) this.gradientDistanceValue.get()).intValue(), ((Number) this.getTargetInstance().getSaturationValue().get()).floatValue(), ((Number) this.getTargetInstance().getBrightnessValue().get()).floatValue());
                if (color == null) {
                    Intrinsics.throwNpe();
                }

                i = color.getRGB();
                return this.getColor(i).getRGB();
            }
            break;

        case -1656737386:
            if (s.equals("Rainbow")) {
                i = RenderUtils.getRainbowOpaque(((Number) this.getTargetInstance().getWaveSecondValue().get()).intValue(), ((Number) this.getTargetInstance().getSaturationValue().get()).floatValue(), ((Number) this.getTargetInstance().getBrightnessValue().get()).floatValue(), i * ((Number) this.gradientDistanceValue.get()).intValue());
                return this.getColor(i).getRGB();
            }
            break;

        case 83201:
            if (s.equals("Sky")) {
                i = RenderUtils.SkyRainbow(i * ((Number) this.gradientDistanceValue.get()).intValue(), ((Number) this.getTargetInstance().getSaturationValue().get()).floatValue(), ((Number) this.getTargetInstance().getBrightnessValue().get()).floatValue());
                return this.getColor(i).getRGB();
            }
            break;

        case 2181788:
            if (s.equals("Fade")) {
                i = ColorUtils.fade(new Color(((Number) this.getTargetInstance().getRedValue().get()).intValue(), ((Number) this.getTargetInstance().getGreenValue().get()).intValue(), ((Number) this.getTargetInstance().getBlueValue().get()).intValue()), i * ((Number) this.gradientDistanceValue.get()).intValue(), 100).getRGB();
                return this.getColor(i).getRGB();
            }
            break;

        case 74357737:
            if (s.equals("Mixer")) {
                color = ColorMixer.getMixedColor(i * ((Number) this.gradientDistanceValue.get()).intValue(), ((Number) this.getTargetInstance().getWaveSecondValue().get()).intValue());
                Intrinsics.checkExpressionValueIsNotNull(color, "ColorMixer.getMixedColor…ce.waveSecondValue.get())");
                i = color.getRGB();
                return this.getColor(i).getRGB();
            }
        }

        i = -1;
        return this.getColor(i).getRGB();
    }

    public void handleDamage(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        this.gotDamaged = true;
    }

    public void handleBlur(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        IFontRenderer font = Fonts.font40;
        String name = "Name: " + player.getName();
        StringBuilder stringbuilder = (new StringBuilder()).append("Distance: ");
        DecimalFormat decimalformat = this.getDecimalFormat2();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        String info = stringbuilder.append(decimalformat.format(PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, (IEntity) player))).toString();
        float length = RangesKt.coerceAtLeast((float) RangesKt.coerceAtLeast(font.getStringWidth(name), font.getStringWidth(info)) + 40.0F, 125.0F);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtils.fastRoundedRect(0.0F, 0.0F, 10.0F + length, 55.0F, 8.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void handleShadowCut(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        this.handleBlur(player);
    }

    public void handleShadow(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        IFontRenderer font = Fonts.font40;
        String name = "Name: " + player.getName();
        StringBuilder stringbuilder = (new StringBuilder()).append("Distance: ");
        DecimalFormat decimalformat = this.getDecimalFormat2();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        String info = stringbuilder.append(decimalformat.format(PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, (IEntity) player))).toString();
        float length = RangesKt.coerceAtLeast((float) RangesKt.coerceAtLeast(font.getStringWidth(name), font.getStringWidth(info)) + 40.0F, 125.0F);

        RenderUtils.drawRoundedRect(0.0F, 0.0F, 10.0F + length, 55.0F, 8, this.getShadowOpaque().getRGB());
    }

    @Nullable
    public Border getBorder(@Nullable IEntityLivingBase entity) {
        if (entity != null) {
            IFontRenderer font = Fonts.font40;
            String name = "Name: " + entity.getName();
            StringBuilder stringbuilder = (new StringBuilder()).append("Distance: ");
            DecimalFormat decimalformat = this.getDecimalFormat2();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            String info = stringbuilder.append(decimalformat.format(PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, (IEntity) entity))).toString();
            float length = RangesKt.coerceAtLeast((float) RangesKt.coerceAtLeast(font.getStringWidth(name), font.getStringWidth(info)) + 40.0F, 125.0F);

            return new Border(0.0F, 0.0F, 10.0F + length, 55.0F);
        } else {
            return new Border(0.0F, 0.0F, 135.0F, 55.0F);
        }
    }

    public Rice(@NotNull LBplusTarget inst) {
        Intrinsics.checkParameterIsNotNull(inst, "inst");
        super("Rice", inst, true);
        this.gradientLoopValue = new IntegerValue("GradientLoop", 4, 1, 40);
        this.gradientDistanceValue = new IntegerValue("GradientDistance", 50, 1, 200);
        this.gradientRoundedBarValue = new BoolValue("GradientRoundedBar", true);
        this.riceParticle = new BoolValue("Rice-Particle", true);
        this.riceParticleSpin = new BoolValue("Rice-ParticleSpin", true);
        this.generateAmountValue = new IntegerValue("GenerateAmount", 10, 1, 40);
        this.riceParticleCircle = new ListValue("Circle-Particles", new String[] { "Outline", "Solid", "None"}, "Solid");
        this.riceParticleRect = new ListValue("Rect-Particles", new String[] { "Outline", "Solid", "None"}, "Outline");
        this.riceParticleTriangle = new ListValue("Triangle-Particles", new String[] { "Outline", "Solid", "None"}, "Outline");
        this.riceParticleSpeed = new FloatValue("Rice-ParticleSpeed", 0.05F, 0.01F, 0.2F);
        this.riceParticleFade = new BoolValue("Rice-ParticleFade", true);
        this.riceParticleFadingSpeed = new FloatValue("ParticleFadingSpeed", 0.05F, 0.01F, 0.2F);
        this.particleRange = new FloatValue("Rice-ParticleRange", 50.0F, 0.0F, 50.0F);
        this.minParticleSize = (FloatValue) (new FloatValue("MinParticleSize", 0.5F, 0.0F, 5.0F) {
            protected void onChanged(float oldValue, float newValue) {
                float v = ((Number) Rice.this.getMaxParticleSize().get()).floatValue();

                if (v < newValue) {
                    this.set((Object) Float.valueOf(v));
                }

            }
        });
        this.maxParticleSize = (FloatValue) (new FloatValue("MaxParticleSize", 2.5F, 0.0F, 5.0F) {
            protected void onChanged(float oldValue, float newValue) {
                float v = ((Number) Rice.this.getMinParticleSize().get()).floatValue();

                if (v > newValue) {
                    this.set((Object) Float.valueOf(v));
                }

            }
        });
        boolean flag = false;
        List list = (List) (new ArrayList());

        this.particleList = list;
    }
}
