package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
import me.Skid.utils.BlurUtils;
import me.Skid.utils.ShadowUtils;
import me.utils.render.VisualUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "Arraylist2",
    single = true
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010W\u001a\u0004\u0018\u00010XH\u0016J\u000e\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u000208J\u0010\u0010\\\u001a\u00020Z2\u0006\u0010]\u001a\u000208H\u0002J\b\u0010^\u001a\u00020_H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001cR\u000e\u0010\u001f\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001cR\u000e\u0010\"\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010$\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001cR\u000e\u0010&\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010*\u001a\n ,*\u0004\u0018\u00010+0+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020807X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010F\u001a\b\u0012\u0004\u0012\u00020807X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020UX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006`"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist2;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "abcOrder", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "animationSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "blurStrength", "blurValue", "brightnessValue", "cRainbowDistValue", "cRainbowSecValue", "caseValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorAlphaValue", "getColorAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorBlueValue", "getColorBlueValue", "colorBlueValue2", "colorGreenValue", "getColorGreenValue", "colorGreenValue2", "colorModeValue", "colorRedValue", "getColorRedValue", "colorRedValue2", "fadeDistanceValue", "fadeOffset", "fadeSpeed", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "kotlin.jvm.PlatformType", "getFontRenderer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "setFontRenderer", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;)V", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "gidentspeed", "hAnimation", "liquidSlowlyDistanceValue", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "nameBreak", "rectLeftValue", "rectRightValue", "saturationValue", "shadow", "shadowColorBlueValue", "shadowColorGreenValue", "shadowColorMode", "shadowColorRedValue", "shadowNoCutValue", "shadowShaderValue", "shadowStrength", "skyDistanceValue", "sortedModules", "spaceValue", "tags", "tagsArrayColor", "tagsStyleValue", "textBlue", "textBlue2", "textGreen", "textGreen2", "textHeightValue", "textRed", "textRed2", "textYValue", "vAnimation", "x2", "", "y2", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getModName", "", "mod", "getModTag", "m", "updateElement", "", "LiquidBounce"}
)
public final class Arraylist2 extends Element {

    private final ListValue colorModeValue;
    private final BoolValue blurValue;
    private final FloatValue blurStrength;
    private final BoolValue shadow;
    private final BoolValue shadowShaderValue;
    private final BoolValue shadowNoCutValue;
    private final IntegerValue shadowStrength;
    private final ListValue shadowColorMode;
    private final IntegerValue shadowColorRedValue;
    private final IntegerValue shadowColorGreenValue;
    private final IntegerValue shadowColorBlueValue;
    @NotNull
    private final IntegerValue colorRedValue;
    @NotNull
    private final IntegerValue colorGreenValue;
    @NotNull
    private final IntegerValue colorBlueValue;
    private final IntegerValue colorRedValue2;
    private final IntegerValue colorGreenValue2;
    private final IntegerValue colorBlueValue2;
    private final IntegerValue gidentspeed;
    @NotNull
    private final IntegerValue colorAlphaValue;
    private final FloatValue fadeOffset;
    private final FloatValue fadeSpeed;
    private final IntegerValue textRed;
    private final IntegerValue textGreen;
    private final IntegerValue textBlue;
    private final IntegerValue textRed2;
    private final IntegerValue textGreen2;
    private final IntegerValue textBlue2;
    private final FloatValue saturationValue;
    private final FloatValue brightnessValue;
    private final IntegerValue skyDistanceValue;
    private final IntegerValue cRainbowSecValue;
    private final IntegerValue cRainbowDistValue;
    private final IntegerValue liquidSlowlyDistanceValue;
    private final IntegerValue fadeDistanceValue;
    private final ListValue hAnimation;
    private final ListValue vAnimation;
    private final FloatValue animationSpeed;
    private final BoolValue nameBreak;
    private final BoolValue abcOrder;
    private final BoolValue tags;
    private final ListValue tagsStyleValue;
    private final IntegerValue backgroundColorRedValue;
    private final IntegerValue backgroundColorGreenValue;
    private final IntegerValue backgroundColorBlueValue;
    private final IntegerValue backgroundColorAlphaValue;
    private final ListValue rectRightValue;
    private final ListValue rectLeftValue;
    private final ListValue caseValue;
    private final FloatValue spaceValue;
    private final FloatValue textHeightValue;
    private final FloatValue textYValue;
    private final BoolValue tagsArrayColor;
    private final FontValue fontValue;
    private int x2;
    private float y2;
    private List modules;
    private List sortedModules;
    private IFontRenderer fontRenderer;

    @NotNull
    public final IntegerValue getColorRedValue() {
        return this.colorRedValue;
    }

    @NotNull
    public final IntegerValue getColorGreenValue() {
        return this.colorGreenValue;
    }

    @NotNull
    public final IntegerValue getColorBlueValue() {
        return this.colorBlueValue;
    }

    @NotNull
    public final IntegerValue getColorAlphaValue() {
        return this.colorAlphaValue;
    }

    public final IFontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public final void setFontRenderer(IFontRenderer <set-?>) {
        this.fontRenderer = <set-?>;
    }

    @Nullable
    public Border drawElement() {
        final IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();
        final int[] counter = new int[] { 0};

        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        int delta = RenderUtils.deltaTime;
        final String colorMode = (String) this.colorModeValue.get();
        String rectColorMode = (String) this.colorModeValue.get();
        final int customColor = (new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), ((Number) this.colorAlphaValue.get()).intValue())).getRGB();
        int rectCustomColor = (new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), ((Number) this.colorAlphaValue.get()).intValue())).getRGB();
        float space = ((Number) this.spaceValue.get()).floatValue();
        final float textHeight = ((Number) this.textHeightValue.get()).floatValue();
        float textY = ((Number) this.textYValue.get()).floatValue();
        int backgroundCustomColor = (new Color(((Number) this.backgroundColorRedValue.get()).intValue(), ((Number) this.backgroundColorGreenValue.get()).intValue(), ((Number) this.backgroundColorBlueValue.get()).intValue(), ((Number) this.backgroundColorAlphaValue.get()).intValue())).getRGB();
        boolean textShadow = ((Boolean) this.shadow.get()).booleanValue();
        float textSpacer = textHeight + space;
        final float saturation = ((Number) this.saturationValue.get()).floatValue();
        final float brightness = ((Number) this.brightnessValue.get()).floatValue();
        int inx = 0;
        Iterator $i$f$forEachIndexed = this.sortedModules.iterator();

        Module module;
        float f;
        float f1;

        while ($i$f$forEachIndexed.hasNext()) {
            module = (Module) $i$f$forEachIndexed.next();
            String item$iv;

            if (module.getArray() && (module.getState() || module.getSlide() != 0.0F)) {
                int xP;
                label455: {
                    String xPos = this.getModName(module);

                    xP = fontRenderer.getStringWidth(xPos);
                    item$iv = (String) this.hAnimation.get();
                    switch (item$iv.hashCode()) {
                    case -1085510111:
                        if (item$iv.equals("Default")) {
                            if (module.getState()) {
                                if (module.getSlide() < (float) xP) {
                                    module.setSlide(AnimationUtils.easeOut(module.getSlideStep(), (float) xP) * (float) xP);
                                    module.setSlideStep(module.getSlideStep() + (float) delta / 4.0F);
                                }
                            } else if (module.getSlide() > (float) 0) {
                                module.setSlide(AnimationUtils.easeOut(module.getSlideStep(), (float) xP) * (float) xP);
                                module.setSlideStep(module.getSlideStep() - (float) delta / 4.0F);
                            }
                            break label455;
                        }
                        break;

                    case 79973777:
                        if (item$iv.equals("Slide")) {
                            if (module.getState()) {
                                if (module.getSlide() < (float) xP) {
                                    module.setSlide((float) AnimationUtils.animate((double) xP, (double) module.getSlide(), (double) ((Number) this.animationSpeed.get()).floatValue() * 0.025D * (double) delta));
                                    module.setSlideStep((float) delta / 1.0F);
                                }
                            } else if (module.getSlide() > (float) 0) {
                                module.setSlide((float) AnimationUtils.animate(-((double) xP), (double) module.getSlide(), (double) ((Number) this.animationSpeed.get()).floatValue() * 0.025D * (double) delta));
                                module.setSlideStep(0.0F);
                            }
                            break label455;
                        }
                        break;

                    case 961091784:
                        if (item$iv.equals("Astolfo")) {
                            if (module.getState()) {
                                if (module.getSlide() < (float) xP) {
                                    module.setSlide(module.getSlide() + ((Number) this.animationSpeed.get()).floatValue() * (float) delta);
                                    module.setSlideStep((float) delta / 1.0F);
                                }
                            } else if (module.getSlide() > (float) 0) {
                                module.setSlide(module.getSlide() - ((Number) this.animationSpeed.get()).floatValue() * (float) delta);
                                module.setSlideStep(0.0F);
                            }

                            if (module.getSlide() > (float) xP) {
                                module.setSlide((float) xP);
                            }
                            break label455;
                        }
                    }

                    module.setSlide(module.getState() ? (float) xP : 0.0F);
                    module.setSlideStep(module.getSlideStep() + (float) (module.getState() ? delta : -delta));
                }

                module.setSlide(RangesKt.coerceIn(module.getSlide(), 0.0F, (float) xP));
                module.setSlideStep(RangesKt.coerceIn(module.getSlideStep(), 0.0F, (float) xP));
            }

            f = (this.getSide().getVertical() == Side.Vertical.DOWN ? -textSpacer : textSpacer) * (float) (this.getSide().getVertical() == Side.Vertical.DOWN ? inx + 1 : inx);
            if (module.getArray() && module.getSlide() > 0.0F) {
                if (StringsKt.equals((String) this.vAnimation.get(), "Rise", true) && !module.getState()) {
                    f = (float) (-fontRenderer.getFontHeight()) - textY;
                }

                label431: {
                    label430: {
                        f1 = (float) this.modules.size() * 0.02F;
                        item$iv = (String) this.vAnimation.get();
                        switch (item$iv.hashCode()) {
                        case -1275652174:
                            if (item$iv.equals("LiquidSense")) {
                                if (module.getState()) {
                                    if (module.getHigt() < f) {
                                        module.setHigt(module.getHigt() + (f1 - Math.min(module.getHigt() * 0.002F, f1 - module.getHigt() * 1.0E-4F)) * (float) delta);
                                        module.setHigt(Math.min(f, module.getHigt()));
                                    } else {
                                        module.setHigt(module.getHigt() - (f1 - Math.min(module.getHigt() * 0.002F, f1 - module.getHigt() * 1.0E-4F)) * (float) delta);
                                        module.setHigt(Math.max(module.getHigt(), f));
                                    }
                                }
                                break label431;
                            }
                            break;

                        case 2547433:
                            if (item$iv.equals("Rise")) {
                                break label430;
                            }
                            break;

                        case 79973777:
                            if (item$iv.equals("Slide")) {
                                break label430;
                            }
                            break;

                        case 961091784:
                            if (item$iv.equals("Astolfo")) {
                                if (module.getHigt() < f) {
                                    module.setHigt(module.getHigt() + ((Number) this.animationSpeed.get()).floatValue() / 2.0F * (float) delta);
                                    module.setHigt(Math.min(f, module.getHigt()));
                                } else {
                                    module.setHigt(module.getHigt() - ((Number) this.animationSpeed.get()).floatValue() / 2.0F * (float) delta);
                                    module.setHigt(Math.max(module.getHigt(), f));
                                }
                                break label431;
                            }
                        }

                        module.setHigt(f);
                        break label431;
                    }

                    module.setHigt((float) AnimationUtils.animate((double) f, (double) module.getHigt(), (double) ((Number) this.animationSpeed.get()).floatValue() * 0.025D * (double) delta));
                }

                ++inx;
            } else if (!StringsKt.equals((String) this.vAnimation.get(), "rise", true)) {
                module.setHigt(f);
            }
        }

        int i;

        boolean $i$f$forEachIndexed1;
        int index$iv;
        Iterator iterator;
        Object module1;
        int index;
        boolean $i$a$-forEachIndexed-Arraylist2$drawElement$10;
        String displayString;
        Module width;
        float xPos1;
        boolean moduleColor;
        String Sky;
        float CRainbow;
        float FadeColor;
        Integer test;
        int LiquidSlowly;
        int rectColor;
        final float f2;
        Iterable iterable;
        final float f3;
        boolean flag;
        Iterator iterator1;
        Iterable iterable1;
        Object object;
        int j;
        boolean flag1;
        Module module;
        int k;
        boolean flag2;
        int l;
        boolean flag3;
        int i1;
        int j1;
        Color color;
        Color color1;
        int k1;
        float f4;
        int l1;

        label402:
        switch (Arraylist2$WhenMappings.$EnumSwitchMapping$0[this.getSide().getHorizontal().ordinal()]) {
        case 1:
        case 2:
            if (((Boolean) this.shadowShaderValue.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                GL11.glPushMatrix();
                ShadowUtils.INSTANCE.shadow((float) ((Number) this.shadowStrength.get()).intValue(), (Function0) (new Function0(0) {
                    public final void invoke() {
                        GL11.glPushMatrix();
                        GL11.glTranslated(Arraylist2.this.getRenderX(), Arraylist2.this.getRenderY(), 0.0D);
                        Iterable $this$forEachIndexed$iv = (Iterable) Arraylist2.this.modules;
                        boolean $i$f$forEachIndexed = false;
                        int index$iv = 0;

                        float f;
                        float f1;
                        float f2;
                        float f3;
                        int i;

                        for (Iterator iterator = $this$forEachIndexed$iv.iterator(); iterator.hasNext(); RenderUtils.newDrawRect(f3, f2, f1, f, i)) {
                            Object item$iv = iterator.next();
                            int j = index$iv++;
                            boolean flag = false;

                            if (j < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            Module module = (Module) item$iv;
                            boolean $i$a$-forEachIndexed-Arraylist2$drawElement$1$1 = false;
                            float xPos = -module.getSlide() - (float) 2;
                            float f4 = xPos - (float) (StringsKt.equals((String) Arraylist2.this.rectRightValue.get(), "right", true) ? 3 : 2);
                            float f5 = module.getHigt();
                            float f6 = StringsKt.equals((String) Arraylist2.this.rectRightValue.get(), "right", true) ? -1.0F : 0.0F;
                            float f7 = module.getHigt() + textHeight;
                            String s = (String) Arraylist2.this.shadowColorMode.get();

                            f = f7;
                            f1 = f6;
                            f2 = f5;
                            f3 = f4;
                            boolean moduleColor = false;

                            if (s == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            String s1 = s.toLowerCase();

                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                            String s2 = s1;

                            switch (s2.hashCode()) {
                            case -1332194002:
                                if (s2.equals("background")) {
                                    i = (new Color(((Number) Arraylist2.this.backgroundColorRedValue.get()).intValue(), ((Number) Arraylist2.this.backgroundColorGreenValue.get()).intValue(), ((Number) Arraylist2.this.backgroundColorBlueValue.get()).intValue())).getRGB();
                                    continue;
                                }
                                break;

                            case 3556653:
                                if (s2.equals("text")) {
                                    Color color = Color.getHSBColor(module.getHue(), saturation, brightness);

                                    Intrinsics.checkExpressionValueIsNotNull(color, "Color.getHSBColor(module�?, saturation, brightness)");
                                    int k = color.getRGB();
                                    int Sky = RenderUtils.SkyRainbow(counter[0] * ((Number) Arraylist2.this.skyDistanceValue.get()).intValue() * 50, ((Number) Arraylist2.this.saturationValue.get()).floatValue(), ((Number) Arraylist2.this.brightnessValue.get()).floatValue());
                                    int CRainbow = RenderUtils.getRainbowOpaque(((Number) Arraylist2.this.cRainbowSecValue.get()).intValue(), ((Number) Arraylist2.this.saturationValue.get()).floatValue(), ((Number) Arraylist2.this.brightnessValue.get()).floatValue(), counter[0] * 50 * ((Number) Arraylist2.this.cRainbowDistValue.get()).intValue());
                                    int FadeColor = ColorUtils.fade(new Color(((Number) Arraylist2.this.getColorRedValue().get()).intValue(), ((Number) Arraylist2.this.getColorGreenValue().get()).intValue(), ((Number) Arraylist2.this.getColorBlueValue().get()).intValue(), ((Number) Arraylist2.this.getColorAlphaValue().get()).intValue()), j * ((Number) Arraylist2.this.fadeDistanceValue.get()).intValue(), 100).getRGB();

                                    --counter[0];
                                    color = ColorUtils.LiquidSlowly(System.nanoTime(), j * ((Number) Arraylist2.this.liquidSlowlyDistanceValue.get()).intValue(), ((Number) Arraylist2.this.saturationValue.get()).floatValue(), ((Number) Arraylist2.this.brightnessValue.get()).floatValue());
                                    Integer test = color != null ? Integer.valueOf(color.getRGB()) : null;

                                    if (test == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    int LiquidSlowly = test.intValue();

                                    if (StringsKt.equals(colorMode, "Random", true)) {
                                        i = k;
                                    } else if (StringsKt.equals(colorMode, "Sky", true)) {
                                        i = Sky;
                                    } else if (StringsKt.equals(colorMode, "CRainbow", true)) {
                                        i = CRainbow;
                                    } else if (StringsKt.equals(colorMode, "LiquidSlowly", true)) {
                                        i = LiquidSlowly;
                                    } else if (StringsKt.equals(colorMode, "Fade", true)) {
                                        i = FadeColor;
                                    } else if (StringsKt.equals(colorMode, "Gradinet", true)) {
                                        color = VisualUtils.getGradientOffset(new Color(((Number) Arraylist2.this.textRed.get()).intValue(), ((Number) Arraylist2.this.textGreen.get()).intValue(), ((Number) Arraylist2.this.textBlue.get()).intValue()), new Color(((Number) Arraylist2.this.textRed2.get()).intValue(), ((Number) Arraylist2.this.textGreen2.get()).intValue(), ((Number) Arraylist2.this.textBlue2.get()).intValue()), (double) j * (double) ((Number) Arraylist2.this.fadeOffset.get()).floatValue());
                                        Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO…eOffset.get().toDouble())");
                                        i = color.getRGB();
                                    } else {
                                        i = customColor;
                                    }
                                    continue;
                                }
                            }

                            i = (new Color(((Number) Arraylist2.this.shadowColorRedValue.get()).intValue(), ((Number) Arraylist2.this.shadowColorGreenValue.get()).intValue(), ((Number) Arraylist2.this.shadowColorBlueValue.get()).intValue())).getRGB();
                        }

                        GL11.glPopMatrix();
                        counter[0] = 0;
                    }
                }), (Function0) (new Function0(0) {
                    public final void invoke() {
                        if (!((Boolean) Arraylist2.this.shadowNoCutValue.get()).booleanValue()) {
                            GL11.glPushMatrix();
                            GL11.glTranslated(Arraylist2.this.getRenderX(), Arraylist2.this.getRenderY(), 0.0D);
                            Iterable $this$forEachIndexed$iv = (Iterable) Arraylist2.this.modules;
                            boolean $i$f$forEachIndexed = false;
                            int index$iv = 0;
                            Iterator iterator = $this$forEachIndexed$iv.iterator();

                            while (iterator.hasNext()) {
                                Object item$iv = iterator.next();
                                int i = index$iv++;
                                boolean flag = false;

                                if (i < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }

                                Module module = (Module) item$iv;
                                boolean $i$a$-forEachIndexed-Arraylist2$drawElement$2$1 = false;
                                float xPos = -module.getSlide() - (float) 2;

                                RenderUtils.quickDrawRect(xPos - (float) (StringsKt.equals((String) Arraylist2.this.rectRightValue.get(), "right", true) ? 3 : 2), module.getHigt(), StringsKt.equals((String) Arraylist2.this.rectRightValue.get(), "right", true) ? -1.0F : 0.0F, module.getHigt() + textHeight);
                            }

                            GL11.glPopMatrix();
                        }

                    }
                }));
                GL11.glPopMatrix();
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            if (((Boolean) this.blurValue.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                GL11.glPushMatrix();
                f2 = (float) this.getRenderX();
                f3 = (float) this.getRenderY();
                f = 0.0F;
                f1 = 0.0F;
                iterable1 = (Iterable) this.modules;
                $i$f$forEachIndexed1 = false;
                index$iv = 0;

                for (iterator = iterable1.iterator(); iterator.hasNext(); f1 = Math.min(f1, -CRainbow)) {
                    module1 = iterator.next();
                    index = index$iv++;
                    $i$a$-forEachIndexed-Arraylist2$drawElement$10 = false;
                    if (index < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }

                    width = (Module) module1;
                    moduleColor = false;
                    Sky = this.getModName(width);
                    CRainbow = (float) fontRenderer.getStringWidth(Sky) + 2.0F;
                    FadeColor = this.getSide().getVertical() == Side.Vertical.DOWN ? -textSpacer : textSpacer * (float) (this.getSide().getVertical() == Side.Vertical.DOWN ? index + 1 : index);
                    f += FadeColor;
                }

                BlurUtils.blur(f2, f3, f2 + f1, f3 + f, ((Number) this.blurStrength.get()).floatValue(), false, (Function0) (new Function0(0) {
                    public final void invoke() {
                        Iterable $this$forEachIndexed$iv = (Iterable) Arraylist2.this.modules;
                        boolean $i$f$forEachIndexed = false;
                        int index$iv = 0;
                        Iterator iterator = $this$forEachIndexed$iv.iterator();

                        while (iterator.hasNext()) {
                            Object item$iv = iterator.next();
                            int i = index$iv++;
                            boolean flag = false;

                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            Module module = (Module) item$iv;
                            boolean $i$a$-forEachIndexed-Arraylist2$drawElement$4$1 = false;
                            float xPos = -module.getSlide() - (float) 2;

                            RenderUtils.quickDrawRect(f + xPos - (float) (StringsKt.equals((String) Arraylist2.this.rectRightValue.get(), "right", true) ? 3 : 2), f1 + module.getHigt(), f + (StringsKt.equals((String) Arraylist2.this.rectRightValue.get(), "right", true) ? -1.0F : 0.0F), f1 + module.getHigt() + textHeight);
                        }

                    }
                }));
                GL11.glPopMatrix();
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            iterable = (Iterable) this.modules;
            flag = false;
            i = 0;
            iterator1 = iterable.iterator();

            while (true) {
                if (!iterator1.hasNext()) {
                    break label402;
                }

                object = iterator1.next();
                j = i++;
                flag1 = false;
                if (j < 0) {
                    CollectionsKt.throwIndexOverflow();
                }

                module = (Module) object;
                $i$a$-forEachIndexed-Arraylist2$drawElement$10 = false;
                displayString = this.getModName(module);
                fontRenderer.getStringWidth(displayString);
                xPos1 = -module.getSlide() - (float) 2;
                color = Color.getHSBColor(module.getHue(), saturation, brightness);
                Intrinsics.checkExpressionValueIsNotNull(color, "Color.getHSBColor(module�?, saturation, brightness)");
                k = color.getRGB();
                flag2 = false;
                l = RenderUtils.SkyRainbow(counter[0] * ((Number) this.skyDistanceValue.get()).intValue() * 50, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                flag3 = false;
                i1 = RenderUtils.getRainbowOpaque(((Number) this.cRainbowSecValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), counter[0] * 50 * ((Number) this.cRainbowDistValue.get()).intValue());
                j1 = ColorUtils.fade(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), ((Number) this.colorAlphaValue.get()).intValue()), j * ((Number) this.fadeDistanceValue.get()).intValue(), 100).getRGB();
                --counter[0];
                color = ColorUtils.LiquidSlowly(System.nanoTime(), j * ((Number) this.liquidSlowlyDistanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                test = color != null ? Integer.valueOf(color.getRGB()) : null;
                if (test == null) {
                    Intrinsics.throwNpe();
                }

                LiquidSlowly = test.intValue();
                RenderUtils.drawRect(xPos1 - (float) (StringsKt.equals((String) this.rectRightValue.get(), "right", true) ? 3 : 2), module.getHigt(), StringsKt.equals((String) this.rectRightValue.get(), "right", true) ? -1.0F : 0.0F, module.getHigt() + textHeight, backgroundCustomColor);
                float f5 = xPos1 - (float) (StringsKt.equals((String) this.rectRightValue.get(), "right", true) ? 1 : 0);

                f4 = module.getHigt() + textY;
                if (StringsKt.equals(colorMode, "Random", true)) {
                    l1 = k;
                } else if (StringsKt.equals(colorMode, "Sky", true)) {
                    l1 = l;
                } else if (StringsKt.equals(colorMode, "CRainbow", true)) {
                    l1 = i1;
                } else if (StringsKt.equals(colorMode, "LiquidSlowly", true)) {
                    l1 = LiquidSlowly;
                } else if (StringsKt.equals(colorMode, "Fade", true)) {
                    l1 = j1;
                } else if (StringsKt.equals(colorMode, "Gradinet", true)) {
                    color1 = VisualUtils.getGradientOffset(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 1), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (module.getHigt() / (float) fontRenderer.getFontHeight())) / (double) 10);
                    Intrinsics.checkExpressionValueIsNotNull(color1, "VisualUtils.getGradientO�?                 ) / 10))");
                    l1 = color1.getRGB();
                } else {
                    l1 = customColor;
                }

                fontRenderer.drawString(displayString, f5, f4, l1, textShadow);
                if (!StringsKt.equals((String) this.rectRightValue.get(), "none", true)) {
                    if (StringsKt.equals(rectColorMode, "Random", true)) {
                        k1 = k;
                    } else if (StringsKt.equals(rectColorMode, "Sky", true)) {
                        k1 = l;
                    } else if (StringsKt.equals(rectColorMode, "CRainbow", true)) {
                        k1 = i1;
                    } else if (StringsKt.equals(rectColorMode, "LiquidSlowly", true)) {
                        k1 = LiquidSlowly;
                    } else if (StringsKt.equals(rectColorMode, "Fade", true)) {
                        k1 = j1;
                    } else if (StringsKt.equals(rectColorMode, "Gradinet", true)) {
                        color = VisualUtils.getGradientOffset(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 1), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (module.getHigt() / (float) fontRenderer.getFontHeight())) / (double) 10);
                        Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO�?                 ) / 10))");
                        k1 = color.getRGB();
                    } else {
                        k1 = rectCustomColor;
                    }

                    rectColor = k1;
                    if (StringsKt.equals((String) this.rectRightValue.get(), "left", true)) {
                        RenderUtils.drawRect(xPos1 - (float) 3, module.getHigt(), xPos1 - (float) 2, module.getHigt() + textHeight, rectColor);
                    } else if (StringsKt.equals((String) this.rectRightValue.get(), "right", true)) {
                        RenderUtils.drawRect(-1.0F, module.getHigt(), 0.0F, module.getHigt() + textHeight, rectColor);
                    } else if (StringsKt.equals((String) this.rectRightValue.get(), "outline", true)) {
                        RenderUtils.drawRect(-1.0F, module.getHigt() - 1.0F, 0.0F, module.getHigt() + textHeight, rectColor);
                        RenderUtils.drawRect(xPos1 - (float) 3, module.getHigt(), xPos1 - (float) 2, module.getHigt() + textHeight, rectColor);
                        if (Intrinsics.areEqual(module, (Module) this.modules.get(0)) ^ true) {
                            String displayStrings = this.getModName((Module) this.modules.get(j - 1));

                            RenderUtils.drawRect(xPos1 - (float) 3 - (float) (fontRenderer.getStringWidth(displayStrings) - fontRenderer.getStringWidth(displayString)), module.getHigt(), xPos1 - (float) 2, module.getHigt() + (float) 1, rectColor);
                            if (Intrinsics.areEqual(module, (Module) this.modules.get(this.modules.size() - 1))) {
                                RenderUtils.drawRect(xPos1 - (float) 3, module.getHigt() + textHeight, 0.0F, module.getHigt() + textHeight + (float) 1, rectColor);
                            }
                        } else {
                            RenderUtils.drawRect(xPos1 - (float) 3, module.getHigt(), 0.0F, module.getHigt() - (float) 1, rectColor);
                        }
                    } else if (StringsKt.equals((String) this.rectRightValue.get(), "special", true)) {
                        if (Intrinsics.areEqual(module, (Module) this.modules.get(0))) {
                            RenderUtils.drawRect(xPos1 - (float) 2, module.getHigt(), 0.0F, module.getHigt() - (float) 1, rectColor);
                        }

                        if (Intrinsics.areEqual(module, (Module) this.modules.get(this.modules.size() - 1))) {
                            RenderUtils.drawRect(xPos1 - (float) 2, module.getHigt() + textHeight, 0.0F, module.getHigt() + textHeight + (float) 1, rectColor);
                        }
                    } else if (StringsKt.equals((String) this.rectRightValue.get(), "top", true) && Intrinsics.areEqual(module, (Module) this.modules.get(0))) {
                        RenderUtils.drawRect(xPos1 - (float) 2, module.getHigt(), 0.0F, module.getHigt() - (float) 1, rectColor);
                    }
                }
            }

        case 3:
            if (((Boolean) this.shadowShaderValue.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                GL11.glPushMatrix();
                ShadowUtils.INSTANCE.shadow((float) ((Number) this.shadowStrength.get()).intValue(), (Function0) (new Function0(0) {
                    public final void invoke() {
                        GL11.glPushMatrix();
                        GL11.glTranslated(Arraylist2.this.getRenderX(), Arraylist2.this.getRenderY(), 0.0D);
                        Iterable $this$forEachIndexed$iv = (Iterable) Arraylist2.this.modules;
                        boolean $i$f$forEachIndexed = false;
                        int index$iv = 0;

                        float f;
                        float f1;
                        float f2;
                        float f3;
                        int i;

                        for (Iterator iterator = $this$forEachIndexed$iv.iterator(); iterator.hasNext(); RenderUtils.drawRect(f3, f2, f1, f, i)) {
                            Object item$iv = iterator.next();
                            int j = index$iv++;
                            boolean flag = false;

                            if (j < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            Module module = (Module) item$iv;
                            boolean $i$a$-forEachIndexed-Arraylist2$drawElement$6$1 = false;
                            String displayString = Arraylist2.this.getModName(module);
                            int width = fontRenderer.getStringWidth(displayString);
                            float xPos = -((float) width - module.getSlide()) + (float) (StringsKt.equals((String) Arraylist2.this.rectLeftValue.get(), "left", true) ? 3 : 2);
                            float f4 = module.getHigt();
                            float f5 = xPos + (float) width + (StringsKt.equals((String) Arraylist2.this.rectLeftValue.get(), "right", true) ? 3.0F : 2.0F);
                            float f6 = module.getHigt() + textHeight;
                            String s = (String) Arraylist2.this.shadowColorMode.get();

                            f = f6;
                            f1 = f5;
                            f2 = f4;
                            f3 = 0.0F;
                            boolean moduleColor = false;

                            if (s == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            String s1 = s.toLowerCase();

                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                            String s2 = s1;

                            switch (s2.hashCode()) {
                            case -1332194002:
                                if (s2.equals("background")) {
                                    i = (new Color(((Number) Arraylist2.this.backgroundColorRedValue.get()).intValue(), ((Number) Arraylist2.this.backgroundColorGreenValue.get()).intValue(), ((Number) Arraylist2.this.backgroundColorBlueValue.get()).intValue())).getRGB();
                                    continue;
                                }
                                break;

                            case 3556653:
                                if (s2.equals("text")) {
                                    Color color = Color.getHSBColor(module.getHue(), saturation, brightness);

                                    Intrinsics.checkExpressionValueIsNotNull(color, "Color.getHSBColor(module�?, saturation, brightness)");
                                    int k = color.getRGB();
                                    int Sky = RenderUtils.SkyRainbow(counter[0] * ((Number) Arraylist2.this.skyDistanceValue.get()).intValue() * 50, ((Number) Arraylist2.this.saturationValue.get()).floatValue(), ((Number) Arraylist2.this.brightnessValue.get()).floatValue());
                                    int CRainbow = RenderUtils.getRainbowOpaque(((Number) Arraylist2.this.cRainbowSecValue.get()).intValue(), ((Number) Arraylist2.this.saturationValue.get()).floatValue(), ((Number) Arraylist2.this.brightnessValue.get()).floatValue(), counter[0] * 50 * ((Number) Arraylist2.this.cRainbowDistValue.get()).intValue());
                                    int FadeColor = ColorUtils.fade(new Color(((Number) Arraylist2.this.getColorRedValue().get()).intValue(), ((Number) Arraylist2.this.getColorGreenValue().get()).intValue(), ((Number) Arraylist2.this.getColorBlueValue().get()).intValue(), ((Number) Arraylist2.this.getColorAlphaValue().get()).intValue()), j * ((Number) Arraylist2.this.fadeDistanceValue.get()).intValue(), 100).getRGB();

                                    --counter[0];
                                    color = ColorUtils.LiquidSlowly(System.nanoTime(), j * ((Number) Arraylist2.this.liquidSlowlyDistanceValue.get()).intValue(), ((Number) Arraylist2.this.saturationValue.get()).floatValue(), ((Number) Arraylist2.this.brightnessValue.get()).floatValue());
                                    Integer test = color != null ? Integer.valueOf(color.getRGB()) : null;

                                    if (test == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    int LiquidSlowly = test.intValue();

                                    if (StringsKt.equals(colorMode, "Random", true)) {
                                        i = k;
                                    } else if (StringsKt.equals(colorMode, "Sky", true)) {
                                        i = Sky;
                                    } else if (StringsKt.equals(colorMode, "CRainbow", true)) {
                                        i = CRainbow;
                                    } else if (StringsKt.equals(colorMode, "LiquidSlowly", true)) {
                                        i = LiquidSlowly;
                                    } else if (StringsKt.equals(colorMode, "Fade", true)) {
                                        i = FadeColor;
                                    } else if (StringsKt.equals(colorMode, "Gradinet", true)) {
                                        color = VisualUtils.getGradientOffset(new Color(((Number) Arraylist2.this.getColorRedValue().get()).intValue(), ((Number) Arraylist2.this.getColorGreenValue().get()).intValue(), ((Number) Arraylist2.this.getColorBlueValue().get()).intValue(), 1), new Color(((Number) Arraylist2.this.colorRedValue2.get()).intValue(), ((Number) Arraylist2.this.colorGreenValue2.get()).intValue(), ((Number) Arraylist2.this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Arraylist2.this.gidentspeed.get()).intValue() + (double) (module.getHigt() / (float) fontRenderer.getFontHeight())) / (double) 10);
                                        Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO�?                 ) / 10))");
                                        i = color.getRGB();
                                    } else {
                                        i = customColor;
                                    }
                                    continue;
                                }
                            }

                            i = (new Color(((Number) Arraylist2.this.shadowColorRedValue.get()).intValue(), ((Number) Arraylist2.this.shadowColorGreenValue.get()).intValue(), ((Number) Arraylist2.this.shadowColorBlueValue.get()).intValue())).getRGB();
                        }

                        GL11.glPopMatrix();
                    }
                }), (Function0) (new Function0(0) {
                    public final void invoke() {
                        if (!((Boolean) Arraylist2.this.shadowNoCutValue.get()).booleanValue()) {
                            GL11.glPushMatrix();
                            GL11.glTranslated(Arraylist2.this.getRenderX(), Arraylist2.this.getRenderY(), 0.0D);
                            Iterable $this$forEachIndexed$iv = (Iterable) Arraylist2.this.modules;
                            boolean $i$f$forEachIndexed = false;
                            int index$iv = 0;
                            Iterator iterator = $this$forEachIndexed$iv.iterator();

                            while (iterator.hasNext()) {
                                Object item$iv = iterator.next();
                                int i = index$iv++;
                                boolean flag = false;

                                if (i < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }

                                Module module = (Module) item$iv;
                                boolean $i$a$-forEachIndexed-Arraylist2$drawElement$7$1 = false;
                                String displayString = Arraylist2.this.getModName(module);
                                int width = fontRenderer.getStringWidth(displayString);
                                float xPos = -((float) width - module.getSlide()) + (float) (StringsKt.equals((String) Arraylist2.this.rectLeftValue.get(), "left", true) ? 3 : 2);

                                RenderUtils.quickDrawRect(0.0F, module.getHigt(), xPos + (float) width + (float) (StringsKt.equals((String) Arraylist2.this.rectLeftValue.get(), "right", true) ? 3 : 2), module.getHigt() + textHeight);
                            }

                            GL11.glPopMatrix();
                        }

                    }
                }));
                GL11.glPopMatrix();
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            if (((Boolean) this.blurValue.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                GL11.glPushMatrix();
                f2 = (float) this.getRenderX();
                f3 = (float) this.getRenderY();
                f = 0.0F;
                f1 = 0.0F;
                iterable1 = (Iterable) this.modules;
                $i$f$forEachIndexed1 = false;
                index$iv = 0;

                for (iterator = iterable1.iterator(); iterator.hasNext(); f1 = Math.max(f1, CRainbow)) {
                    module1 = iterator.next();
                    index = index$iv++;
                    $i$a$-forEachIndexed-Arraylist2$drawElement$10 = false;
                    if (index < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }

                    width = (Module) module1;
                    moduleColor = false;
                    Sky = this.getModName(width);
                    CRainbow = (float) fontRenderer.getStringWidth(Sky) + 2.0F;
                    FadeColor = this.getSide().getVertical() == Side.Vertical.DOWN ? -textSpacer : textSpacer * (float) (this.getSide().getVertical() == Side.Vertical.DOWN ? index + 1 : index);
                    f += FadeColor;
                }

                BlurUtils.blur(f2, f3, f2 + f1, f3 + f, ((Number) this.blurStrength.get()).floatValue(), false, (Function0) (new Function0(0) {
                    public final void invoke() {
                        Iterable $this$forEachIndexed$iv = (Iterable) Arraylist2.this.modules;
                        boolean $i$f$forEachIndexed = false;
                        int index$iv = 0;
                        Iterator iterator = $this$forEachIndexed$iv.iterator();

                        while (iterator.hasNext()) {
                            Object item$iv = iterator.next();
                            int i = index$iv++;
                            boolean flag = false;

                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            Module module = (Module) item$iv;
                            boolean $i$a$-forEachIndexed-Arraylist2$drawElement$9$1 = false;
                            String displayString = Arraylist2.this.getModName(module);
                            int width = fontRenderer.getStringWidth(displayString);
                            float xPos = -((float) width - module.getSlide()) + (float) (StringsKt.equals((String) Arraylist2.this.rectLeftValue.get(), "left", true) ? 3 : 2);

                            RenderUtils.quickDrawRect(f, f1 + module.getHigt(), f + xPos + (float) width + (float) (StringsKt.equals((String) Arraylist2.this.rectLeftValue.get(), "right", true) ? 3 : 2), f1 + module.getHigt() + textHeight);
                        }

                    }
                }));
                GL11.glPopMatrix();
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            iterable = (Iterable) this.modules;
            flag = false;
            i = 0;
            iterator1 = iterable.iterator();

            while (iterator1.hasNext()) {
                object = iterator1.next();
                j = i++;
                flag1 = false;
                if (j < 0) {
                    CollectionsKt.throwIndexOverflow();
                }

                module = (Module) object;
                $i$a$-forEachIndexed-Arraylist2$drawElement$10 = false;
                displayString = this.getModName(module);
                int i2 = fontRenderer.getStringWidth(displayString);

                xPos1 = -((float) i2 - module.getSlide()) + (float) (StringsKt.equals((String) this.rectLeftValue.get(), "left", true) ? 3 : 2);
                color = Color.getHSBColor(module.getHue(), saturation, brightness);
                Intrinsics.checkExpressionValueIsNotNull(color, "Color.getHSBColor(module�?, saturation, brightness)");
                k = color.getRGB();
                flag2 = false;
                l = RenderUtils.SkyRainbow(counter[0] * ((Number) this.skyDistanceValue.get()).intValue() * 50, ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                flag3 = false;
                i1 = RenderUtils.getRainbowOpaque(((Number) this.cRainbowSecValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), counter[0] * 50 * ((Number) this.cRainbowDistValue.get()).intValue());
                j1 = ColorUtils.fade(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), ((Number) this.colorAlphaValue.get()).intValue()), j * ((Number) this.fadeDistanceValue.get()).intValue(), 100).getRGB();
                --counter[0];
                color = ColorUtils.LiquidSlowly(System.nanoTime(), j * ((Number) this.liquidSlowlyDistanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                test = color != null ? Integer.valueOf(color.getRGB()) : null;
                if (test == null) {
                    Intrinsics.throwNpe();
                }

                LiquidSlowly = test.intValue();
                RenderUtils.drawRect(0.0F, module.getHigt(), xPos1 + (float) i2 + (float) (StringsKt.equals((String) this.rectLeftValue.get(), "right", true) ? 3 : 2), module.getHigt() + textHeight, backgroundCustomColor);
                f4 = module.getHigt() + textY;
                if (StringsKt.equals(colorMode, "Random", true)) {
                    l1 = k;
                } else if (StringsKt.equals(colorMode, "Sky", true)) {
                    l1 = l;
                } else if (StringsKt.equals(colorMode, "CRainbow", true)) {
                    l1 = i1;
                } else if (StringsKt.equals(colorMode, "LiquidSlowly", true)) {
                    l1 = LiquidSlowly;
                } else if (StringsKt.equals(colorMode, "Fade", true)) {
                    l1 = j1;
                } else if (StringsKt.equals(colorMode, "Gradinet", true)) {
                    color1 = VisualUtils.getGradientOffset(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 1), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (module.getHigt() / (float) fontRenderer.getFontHeight())) / (double) 10);
                    Intrinsics.checkExpressionValueIsNotNull(color1, "VisualUtils.getGradientO�?                 ) / 10))");
                    l1 = color1.getRGB();
                } else {
                    l1 = customColor;
                }

                fontRenderer.drawString(displayString, xPos1, f4, l1, textShadow);
                if (!StringsKt.equals((String) this.rectLeftValue.get(), "none", true)) {
                    if (StringsKt.equals(rectColorMode, "Random", true)) {
                        k1 = k;
                    } else if (StringsKt.equals(rectColorMode, "Sky", true)) {
                        k1 = l;
                    } else if (StringsKt.equals(rectColorMode, "CRainbow", true)) {
                        k1 = i1;
                    } else if (StringsKt.equals(rectColorMode, "LiquidSlowly", true)) {
                        k1 = LiquidSlowly;
                    } else if (StringsKt.equals(rectColorMode, "Fade", true)) {
                        k1 = j1;
                    } else if (StringsKt.equals(rectColorMode, "Gradinet", true)) {
                        color = VisualUtils.getGradientOffset(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 1), new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (module.getHigt() / (float) fontRenderer.getFontHeight())) / (double) 10);
                        Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO�?                 ) / 10))");
                        k1 = color.getRGB();
                    } else {
                        k1 = rectCustomColor;
                    }

                    rectColor = k1;
                    if (StringsKt.equals((String) this.rectLeftValue.get(), "left", true)) {
                        RenderUtils.drawRect(0.0F, module.getHigt() - (float) 1, 1.0F, module.getHigt() + textHeight, rectColor);
                    } else if (StringsKt.equals((String) this.rectLeftValue.get(), "right", true)) {
                        RenderUtils.drawRect(xPos1 + (float) i2 + (float) 2, module.getHigt(), xPos1 + (float) i2 + (float) 2 + (float) 1, module.getHigt() + textHeight, rectColor);
                    }
                }
            }
        }

        if (!MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
            AWTFontRenderer.Companion.setAssumeNonVolatile(false);
            GlStateManager.resetColor();
            return null;
        } else {
            this.x2 = Integer.MIN_VALUE;
            if (this.modules.isEmpty()) {
                return this.getSide().getHorizontal() == Side.Horizontal.LEFT ? new Border(0.0F, -1.0F, 20.0F, 20.0F) : new Border(0.0F, -1.0F, -20.0F, 20.0F);
            } else {
                $i$f$forEachIndexed = this.modules.iterator();

                while ($i$f$forEachIndexed.hasNext()) {
                    module = (Module) $i$f$forEachIndexed.next();
                    switch (Arraylist2$WhenMappings.$EnumSwitchMapping$1[this.getSide().getHorizontal().ordinal()]) {
                    case 1:
                    case 2:
                        i = -((int) module.getSlide()) - 2;
                        if (this.x2 == Integer.MIN_VALUE || i < this.x2) {
                            this.x2 = i;
                        }
                        break;

                    case 3:
                        i = (int) module.getSlide() + 14;
                        if (this.x2 == Integer.MIN_VALUE || i > this.x2) {
                            this.x2 = i;
                        }
                    }
                }

                this.y2 = (this.getSide().getVertical() == Side.Vertical.DOWN ? -textSpacer : textSpacer) * (float) this.modules.size();
                return new Border(0.0F, 0.0F, (float) this.x2 - 7.0F, this.y2 - (this.getSide().getVertical() == Side.Vertical.DOWN ? 1.0F : 0.0F));
            }
        }
    }

    public void updateElement() {
        Iterable $this$sortedBy$iv;
        boolean $i$f$sortedBy;
        Collection destination$iv$iv;
        boolean $i$f$filterTo;
        Iterator iterator;
        Object element$iv$iv;
        Module it;
        boolean $i$a$-filter-Arraylist2$updateElement$2;
        List list;
        boolean destination$iv$iv1;
        Comparator $i$f$filterTo1;
        Arraylist2 arraylist2;
        List list1;

        if (((Boolean) this.abcOrder.get()).booleanValue()) {
            $this$sortedBy$iv = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
            $i$f$sortedBy = false;
            destination$iv$iv = (Collection) (new ArrayList());
            $i$f$filterTo = false;
            iterator = $this$sortedBy$iv.iterator();

            while (iterator.hasNext()) {
                element$iv$iv = iterator.next();
                it = (Module) element$iv$iv;
                $i$a$-filter-Arraylist2$updateElement$2 = false;
                if (it.getArray() && (StringsKt.equals((String) this.hAnimation.get(), "none", true) ? it.getState() : it.getSlide() > (float) 0)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }

            list = (List) destination$iv$iv;
            arraylist2 = this;
            list1 = list;
        } else {
            $this$sortedBy$iv = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
            $i$f$sortedBy = false;
            destination$iv$iv = (Collection) (new ArrayList());
            $i$f$filterTo = false;
            iterator = $this$sortedBy$iv.iterator();

            while (iterator.hasNext()) {
                element$iv$iv = iterator.next();
                it = (Module) element$iv$iv;
                $i$a$-filter-Arraylist2$updateElement$2 = false;
                if (it.getArray() && (StringsKt.equals((String) this.hAnimation.get(), "none", true) ? it.getState() : it.getSlide() > (float) 0)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }

            list = (List) destination$iv$iv;
            $this$sortedBy$iv = (Iterable) list;
            $i$f$sortedBy = false;
            destination$iv$iv1 = false;
            $i$f$filterTo1 = (Comparator) (new Arraylist2$updateElement$$inlined$sortedBy$1(this));
            list = CollectionsKt.sortedWith($this$sortedBy$iv, $i$f$filterTo1);
            arraylist2 = this;
            list1 = list;
        }

        arraylist2.modules = list1;
        arraylist2 = this;
        if (((Boolean) this.abcOrder.get()).booleanValue()) {
            list1 = CollectionsKt.toList((Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules());
        } else {
            $this$sortedBy$iv = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
            $i$f$sortedBy = false;
            destination$iv$iv1 = false;
            $i$f$filterTo1 = (Comparator) (new Arraylist2$updateElement$$inlined$sortedBy$2(this));
            list = CollectionsKt.sortedWith($this$sortedBy$iv, $i$f$filterTo1);
            arraylist2 = this;
            list1 = CollectionsKt.toList((Iterable) list);
        }

        arraylist2.sortedModules = list1;
    }

    private final String getModTag(Module m) {
        if (((Boolean) this.tags.get()).booleanValue() && m.getTag() != null) {
            String returnTag = ' ' + (((Boolean) this.tagsArrayColor.get()).booleanValue() ? "" : "§7");

            if (!StringsKt.equals((String) this.tagsStyleValue.get(), "default", true)) {
                returnTag = returnTag + String.valueOf(((String) this.tagsStyleValue.get()).charAt(0)) + (!StringsKt.equals((String) this.tagsStyleValue.get(), "-", true) && !StringsKt.equals((String) this.tagsStyleValue.get(), "|", true) ? "" : " ");
            }

            returnTag = returnTag + m.getTag();
            if (!StringsKt.equals((String) this.tagsStyleValue.get(), "default", true) && !StringsKt.equals((String) this.tagsStyleValue.get(), "-", true) && !StringsKt.equals((String) this.tagsStyleValue.get(), "|", true)) {
                returnTag = returnTag + String.valueOf(((String) this.tagsStyleValue.get()).charAt(1));
            }

            return returnTag;
        } else {
            return "";
        }
    }

    @NotNull
    public final String getModName(@NotNull Module mod) {
        Intrinsics.checkParameterIsNotNull(mod, "mod");
        String displayName = (((Boolean) this.nameBreak.get()).booleanValue() ? mod.getName() : mod.getName()) + this.getModTag(mod);
        String s = (String) this.caseValue.get();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            boolean flag1;

            switch (s.hashCode()) {
            case 103164673:
                if (s.equals("lower")) {
                    flag1 = false;
                    if (displayName == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s1 = displayName.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    displayName = s1;
                }
                break;

            case 111499426:
                if (s.equals("upper")) {
                    flag1 = false;
                    if (displayName == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s1 = displayName.toUpperCase();
                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toUpperCase()");
                    displayName = s1;
                }
            }

            return displayName;
        }
    }

    public Arraylist2(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.colorModeValue = new ListValue("Color", new String[] { "Custom", "Random", "Sky", "CRainbow", "LiquidSlowly", "Fade", "Gradinet"}, "Gradinet");
        this.blurValue = new BoolValue("Blur", false);
        this.blurStrength = new FloatValue("Blur-Strength", 0.0F, 0.0F, 30.0F);
        this.shadow = new BoolValue("ShadowText", true);
        this.shadowShaderValue = new BoolValue("Shadow", false);
        this.shadowNoCutValue = new BoolValue("Shadow-NoCut", false);
        this.shadowStrength = new IntegerValue("Shadow-Strength", 1, 1, 30);
        this.shadowColorMode = new ListValue("Shadow-Color", new String[] { "Background", "Text", "Custom"}, "Background");
        this.shadowColorRedValue = new IntegerValue("Shadow-Red", 0, 0, 255);
        this.shadowColorGreenValue = new IntegerValue("Shadow-Green", 111, 0, 255);
        this.shadowColorBlueValue = new IntegerValue("Shadow-Blue", 255, 0, 255);
        this.colorRedValue = new IntegerValue("Red", 0, 0, 255);
        this.colorGreenValue = new IntegerValue("Green", 111, 0, 255);
        this.colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
        this.colorRedValue2 = new IntegerValue("R2", 0, 0, 255);
        this.colorGreenValue2 = new IntegerValue("G2", 111, 0, 255);
        this.colorBlueValue2 = new IntegerValue("B2", 255, 0, 255);
        this.gidentspeed = new IntegerValue("GidentSpeed", 100, 1, 1000);
        this.colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
        this.fadeOffset = new FloatValue("Gradinet-Offset", 0.2F, 0.1F, 1.0F);
        this.fadeSpeed = new FloatValue("Gradinet-Speed", 2.0F, 1.0F, 10.0F);
        this.textRed = new IntegerValue("Gradinet-Red", 0, 0, 255);
        this.textGreen = new IntegerValue("Gradinet-Green", 0, 0, 255);
        this.textBlue = new IntegerValue("Gradinet-Blue", 255, 0, 255);
        this.textRed2 = new IntegerValue("Gradinet-Red-2", 25, 0, 255);
        this.textGreen2 = new IntegerValue("Gradinet-Green-2", 45, 0, 255);
        this.textBlue2 = new IntegerValue("Gradinet-Blue-2", 150, 0, 255);
        this.saturationValue = new FloatValue("Saturation", 0.9F, 0.0F, 1.0F);
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.skyDistanceValue = new IntegerValue("Sky-Distance", 2, 0, 4);
        this.cRainbowSecValue = new IntegerValue("CRainbow-Seconds", 2, 1, 10);
        this.cRainbowDistValue = new IntegerValue("CRainbow-Distance", 2, 1, 6);
        this.liquidSlowlyDistanceValue = new IntegerValue("LiquidSlowly-Distance", 90, 1, 90);
        this.fadeDistanceValue = new IntegerValue("Fade-Distance", 50, 1, 100);
        this.hAnimation = new ListValue("HorizontalAnimation", new String[] { "Default", "None", "Slide", "Astolfo"}, "None");
        this.vAnimation = new ListValue("VerticalAnimation", new String[] { "None", "LiquidSense", "Slide", "Rise", "Astolfo"}, "None");
        this.animationSpeed = new FloatValue("Animation-Speed", 0.25F, 0.01F, 1.0F);
        this.nameBreak = new BoolValue("NameBreak", true);
        this.abcOrder = new BoolValue("Alphabetical-Order", false);
        this.tags = new BoolValue("Tags", true);
        this.tagsStyleValue = new ListValue("TagsStyle", new String[] { "-", "|", "()", "[]", "<>", "Default"}, "-");
        this.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
        this.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
        this.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
        this.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 0, 0, 255);
        this.rectRightValue = new ListValue("Rect-Right", new String[] { "None", "Left", "Right", "Outline", "Special", "Top"}, "None");
        this.rectLeftValue = new ListValue("Rect-Left", new String[] { "None", "Left", "Right"}, "None");
        this.caseValue = new ListValue("Case", new String[] { "None", "Lower", "Upper"}, "None");
        this.spaceValue = new FloatValue("Space", 0.0F, 0.0F, 5.0F);
        this.textHeightValue = new FloatValue("TextHeight", 11.0F, 1.0F, 20.0F);
        this.textYValue = new FloatValue("TextY", 1.0F, 0.0F, 20.0F);
        this.tagsArrayColor = new BoolValue("TagsArrayColor", false);
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.modules = CollectionsKt.emptyList();
        this.sortedModules = CollectionsKt.emptyList();
        this.fontRenderer = Fonts.bold40;
    }

    public Arraylist2(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 1.0D;
        }

        if ((i & 2) != 0) {
            d1 = 2.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = new Side(Side.Horizontal.RIGHT, Side.Vertical.UP);
        }

        this(d0, d1, f, side);
    }

    public Arraylist2() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }

    public static final void access$setModules$p(Arraylist2 $this, List <set-?>) {
        $this.modules = <set-?>;
    }

    public static final FontValue access$getFontValue$p(Arraylist2 $this) {
        return $this.fontValue;
    }
}
