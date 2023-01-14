package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import me.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.player.FastUse;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "ModuleInFos"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010(\u001a\u00020)H\u0016J\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+J\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\tJ\u0010\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\tH\u0002J\u0006\u0010/\u001a\u00020\u0006R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u000e\u0010\u001d\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010&\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001a¨\u00060"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/ModuleInFos;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "(DDF)V", "GameInfoRows", "", "bgalphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "bgblueValue", "bggreenValue", "bgredValue", "blueValue", "blueValue2", "blur", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "gidentspeed", "gradientDistanceValue", "getGradientDistanceValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "gradientLoopValue", "getGradientLoopValue", "greenValue", "greenValue2", "hud", "Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "getHud", "()Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "redValue", "redValue2", "saturationValue", "waveSecondValue", "getWaveSecondValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getColor", "Ljava/awt/Color;", "color", "getColorAtIndex", "i", "getFadeProgress", "LiquidBounce"}
)
public final class ModuleInFos extends Element {

    private final BoolValue blur;
    private final IntegerValue bgredValue;
    private final IntegerValue bggreenValue;
    private final IntegerValue bgblueValue;
    private final IntegerValue bgalphaValue;
    private final FloatValue brightnessValue;
    @NotNull
    private final IntegerValue waveSecondValue;
    @NotNull
    private final IntegerValue gradientLoopValue;
    @NotNull
    private final IntegerValue gradientDistanceValue;
    private final FloatValue saturationValue;
    private final IntegerValue gidentspeed;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue redValue2;
    private final IntegerValue greenValue2;
    private final IntegerValue blueValue2;
    private final ListValue colorModeValue;
    private int GameInfoRows;
    @NotNull
    private final HUD hud;

    @NotNull
    public final IntegerValue getWaveSecondValue() {
        return this.waveSecondValue;
    }

    @NotNull
    public final IntegerValue getGradientLoopValue() {
        return this.gradientLoopValue;
    }

    @NotNull
    public final IntegerValue getGradientDistanceValue() {
        return this.gradientDistanceValue;
    }

    @NotNull
    public final HUD getHud() {
        return this.hud;
    }

    @NotNull
    public Border drawElement() {
        IFontRenderer font = Fonts.bold30;
        float floatX = (float) this.getRenderX();
        float floatY = (float) this.getRenderY();
        String colorMode = (String) this.colorModeValue.get();
        int color2 = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue())).getRGB();
        double barLength1 = (double) 130.0F;

        if (((Boolean) this.blur.get()).booleanValue()) {
            GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
            BlurBuffer.blurRoundArea(floatX, floatY + 7.1F, 144.0F, 68.7F, 10.0F);
            GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
        }

        int FastUse = 0;
        int killaura = ((Number) this.gradientLoopValue.get()).intValue() - 1;

        if (FastUse <= killaura) {
            while (true) {
                double scaffold = (double) FastUse / (double) ((Number) this.gradientLoopValue.get()).intValue() * barLength1;
                double velocity = (double) (FastUse + 1) / (double) ((Number) this.gradientLoopValue.get()).intValue() * barLength1;

                RenderUtils.drawGradientSideways((double) 8 + scaffold, 22.0D, (double) 8 + velocity, 23.0D, this.getColorAtIndex(FastUse), this.getColorAtIndex(FastUse + 1));
                if (FastUse == killaura) {
                    break;
                }

                ++FastUse;
            }
        }

        RenderUtils.drawRoundedRect(0.0F, 7.0F, 144.0F, 76.0F, 10.0F, (new Color(((Number) this.bgredValue.get()).intValue(), ((Number) this.bggreenValue.get()).intValue(), ((Number) this.bgblueValue.get()).intValue(), ((Number) this.bgalphaValue.get()).intValue())).getRGB());
        Fonts.bold35.drawStringWithShadow("ModuleInfos", 50, 12, (new Color(255, 255, 255)).getRGB());
        FastUse fastuse = (FastUse) LiquidBounce.INSTANCE.getModuleManager().getModule(FastUse.class);

        if (fastuse == null) {
            Intrinsics.throwNpe();
        }

        int i;

        if (fastuse.getState()) {
            i = font.drawStringWithShadow("Gapple:", 8, 25, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(fastuse.getKeyBind()), 120, 25, (new Color(255, 255, 255)).getRGB());
        } else {
            i = font.drawStringWithShadow("Gapple:", 8, 25, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(fastuse.getKeyBind()), 120, 25, (new Color(255, 255, 255)).getRGB());
        }

        KillAura killaura = (KillAura) LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);

        if (killaura == null) {
            Intrinsics.throwNpe();
        }

        if (killaura.getState()) {
            i = font.drawStringWithShadow("KillAura:", 8, 35, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(killaura.getKeyBind()), 120, 35, (new Color(255, 255, 255)).getRGB());
        } else {
            i = font.drawStringWithShadow("KillAura:", 8, 35, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(killaura.getKeyBind()), 120, 35, (new Color(255, 255, 255)).getRGB());
        }

        Scaffold scaffold = (Scaffold) LiquidBounce.INSTANCE.getModuleManager().getModule(Scaffold.class);

        if (scaffold == null) {
            Intrinsics.throwNpe();
        }

        if (scaffold.getState()) {
            i = font.drawStringWithShadow("Scaffold:", 8, 45, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(scaffold.getKeyBind()), 120, 45, (new Color(255, 255, 255)).getRGB());
        } else {
            i = font.drawStringWithShadow("Scaffold:", 8, 45, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(scaffold.getKeyBind()), 120, 45, (new Color(255, 255, 255)).getRGB());
        }

        Criticals criticals = (Criticals) LiquidBounce.INSTANCE.getModuleManager().getModule(Criticals.class);

        if (criticals == null) {
            Intrinsics.throwNpe();
        }

        if (criticals.getState()) {
            i = font.drawStringWithShadow("Criticals:", 8, 55, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(criticals.getKeyBind()), 120, 55, (new Color(255, 255, 255)).getRGB());
        } else {
            i = font.drawStringWithShadow("Criticals:", 8, 55, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(criticals.getKeyBind()), 120, 55, (new Color(255, 255, 255)).getRGB());
        }

        Velocity velocity = (Velocity) LiquidBounce.INSTANCE.getModuleManager().getModule(Velocity.class);

        if (velocity == null) {
            Intrinsics.throwNpe();
        }

        if (velocity.getState()) {
            i = font.drawStringWithShadow("Velocity:", 8, 65, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(velocity.getKeyBind()), 120, 65, (new Color(255, 255, 255)).getRGB());
        } else {
            i = font.drawStringWithShadow("Velocity:", 8, 65, (new Color(255, 255, 255)).getRGB()) + font.drawStringWithShadow(" " + Keyboard.getKeyName(velocity.getKeyBind()), 120, 65, (new Color(255, 255, 255)).getRGB());
        }

        return new Border(0.0F, (float) this.GameInfoRows * 18.0F + 12.0F, 150.0F, 74.5F);
    }

    public final float getFadeProgress() {
        return 0.0F;
    }

    @NotNull
    public final Color getColor(@NotNull Color color) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return ColorUtils.reAlpha(color, (float) color.getAlpha() / 255.0F * (1.0F - this.getFadeProgress()));
    }

    @NotNull
    public final Color getColor(int color) {
        return this.getColor(new Color(color));
    }

    private final int getColorAtIndex(int i) {
        String s = (String) this.colorModeValue.get();
        int i;
        Color color;

        switch (s.hashCode()) {
        case -1815582866:
            if (s.equals("Slowly")) {
                color = ColorUtils.LiquidSlowly(System.nanoTime(), i * ((Number) this.gradientDistanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                if (color == null) {
                    Intrinsics.throwNpe();
                }

                i = color.getRGB();
                return this.getColor(i).getRGB();
            }
            break;

        case -1656737386:
            if (s.equals("Rainbow")) {
                i = RenderUtils.getRainbowOpaque(((Number) this.waveSecondValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), i * ((Number) this.gradientDistanceValue.get()).intValue());
                return this.getColor(i).getRGB();
            }
            break;

        case 83201:
            if (s.equals("Sky")) {
                i = RenderUtils.SkyRainbow(i * ((Number) this.gradientDistanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                return this.getColor(i).getRGB();
            }
            break;

        case 2181788:
            if (s.equals("Fade")) {
                i = ColorUtils.fade(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), i * ((Number) this.gradientDistanceValue.get()).intValue(), 100).getRGB();
                return this.getColor(i).getRGB();
            }
            break;

        case 74357737:
            if (s.equals("Mixer")) {
                color = ColorMixer.getMixedColor(i * ((Number) this.gradientDistanceValue.get()).intValue(), ((Number) this.waveSecondValue.get()).intValue());
                Intrinsics.checkExpressionValueIsNotNull(color, "ColorMixer.getMixedColor�?), waveSecondValue.get())");
                i = color.getRGB();
                return this.getColor(i).getRGB();
            }
            break;

        case 2132719113:
            if (s.equals("Gident")) {
                color = RenderUtils.getGradientOffset(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), new Color(((Number) this.redValue2.get()).intValue(), ((Number) this.greenValue2.get()).intValue(), ((Number) this.blueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) this.gidentspeed.get()).intValue() + (double) (i * ((Number) this.gradientDistanceValue.get()).intValue())) / (double) 10);
                Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO…stanceValue.get()) / 10))");
                i = color.getRGB();
                return this.getColor(i).getRGB();
            }
        }

        i = -1;
        return this.getColor(i).getRGB();
    }

    public ModuleInFos(double x, double y, float scale) {
        super(x, y, scale, (Side) null, 8, (DefaultConstructorMarker) null);
        this.blur = new BoolValue("Blur", true);
        this.bgredValue = new IntegerValue("Bg-R", 0, 0, 255);
        this.bggreenValue = new IntegerValue("Bg-G", 0, 0, 255);
        this.bgblueValue = new IntegerValue("Bg-B", 0, 0, 255);
        this.bgalphaValue = new IntegerValue("Bg-Alpha", 150, 0, 255);
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.waveSecondValue = new IntegerValue("Seconds", 2, 1, 10);
        this.gradientLoopValue = new IntegerValue("GradientLoop", 4, 1, 40);
        this.gradientDistanceValue = new IntegerValue("GradientDistance", 50, 1, 200);
        this.saturationValue = new FloatValue("Saturation", 0.9F, 0.0F, 1.0F);
        this.gidentspeed = new IntegerValue("GidentSpeed", 120, 1, 1000);
        this.redValue = new IntegerValue("Text-R", 255, 0, 255);
        this.greenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.blueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.redValue2 = new IntegerValue("Text-R2", 255, 0, 255);
        this.greenValue2 = new IntegerValue("Text-G2", 255, 0, 255);
        this.blueValue2 = new IntegerValue("Text-B2", 255, 0, 255);
        this.colorModeValue = new ListValue("Text-Color", new String[] { "Custom", "Rainbow", "Gident", "Sky", "Slowly", "Fade", "Mixer", "Health"}, "Custom");
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(HUD.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.HUD");
        } else {
            this.hud = (HUD) module;
        }
    }

    public ModuleInFos(double d0, double d1, float f, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 5.0D;
        }

        if ((i & 2) != 0) {
            d1 = 87.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        this(d0, d1, f);
    }

    public ModuleInFos() {
        this(0.0D, 0.0D, 0.0F, 7, (DefaultConstructorMarker) null);
    }
}
