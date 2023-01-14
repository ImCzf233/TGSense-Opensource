package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.Skid.Tenacity.utils.render.RoundedUtil;
import me.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.utils.render.VisualUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "Armor"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010#\u001a\u00020$H\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0010\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\rR\u0011\u0010\u0019\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\rR\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001c\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\rR\u0011\u0010\u001e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\rR\u000e\u0010 \u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Armor;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "a", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getA", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "b", "getB", "b2", "getB2", "blueValue", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "g", "getG", "g2", "getG2", "greenValue", "r", "getR", "r2", "getR2", "redValue", "saturationValue", "speed", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class Armor extends Element {

    private final ListValue colorModeValue;
    private final FloatValue brightnessValue;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final FloatValue saturationValue;
    private final IntegerValue speed;
    @NotNull
    private final IntegerValue r;
    @NotNull
    private final IntegerValue g;
    @NotNull
    private final IntegerValue b;
    @NotNull
    private final IntegerValue r2;
    @NotNull
    private final IntegerValue g2;
    @NotNull
    private final IntegerValue b2;
    @NotNull
    private final IntegerValue a;

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
    public final IntegerValue getA() {
        return this.a;
    }

    @NotNull
    public Border drawElement() {
        int x2 = 0;

        if (MinecraftInstance.mc.getPlayerController().isNotCreative()) {
            GL11.glPushMatrix();
            IRenderItem renderItem = MinecraftInstance.mc.getRenderItem();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            boolean isInsideWater = ientityplayersp.isInsideOfMaterial(MinecraftInstance.classProvider.getMaterialEnum(MaterialType.WATER));
            int x = 1;
            int i = 0;
            int y = isInsideWater ? -10 : 0;
            String colorMode = (String) this.colorModeValue.get();
            int color = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue())).getRGB();
            boolean rainbow = StringsKt.equals(colorMode, "Rainbow", true);
            int hud = 0;

            for (byte index = 3; hud <= index; ++hud) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getInventory().getArmorInventory().get(hud) != null) {
                    x2 += 20;
                }
            }

            RenderUtils.drawRect(-2.0F, -4.0F, 2.0F + (float) x2, 29.0F, new Color(50, 50, 50, 60));
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(HUD.class);

            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.HUD");
            }

            HUD hud = (HUD) module;
            int i = 3;

            for (boolean flag = false; i >= 0; --i) {
                int j;

                if (rainbow) {
                    j = 0;
                } else if (StringsKt.equals(colorMode, "Astolfo", true)) {
                    j = RenderUtils.Astolfo(i * ((Number) this.speed.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                } else if (StringsKt.equals(colorMode, "gradient", true)) {
                    Color color = RenderUtils.getGradientOffset(new Color(((Number) hud.getR().get()).intValue(), ((Number) hud.getG().get()).intValue(), ((Number) hud.getB().get()).intValue()), new Color(((Number) hud.getR2().get()).intValue(), ((Number) hud.getG2().get()).intValue(), ((Number) hud.getB2().get()).intValue()), 40000.0D);

                    Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.getGradientO�?, hud.b2.get())),40000.0)");
                    j = color.getRGB();
                } else {
                    j = color;
                }

                int colorall = j;

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IItemStack iitemstack = (IItemStack) ientityplayersp.getInventory().getArmorInventory().get(i);

                if (iitemstack != null) {
                    IItemStack stack = iitemstack;
                    double d0 = (double) x;
                    double d1 = (double) x + (double) 18;
                    Color color1 = VisualUtils.getGradientOffset(new Color(((Number) hud.getR().get()).intValue(), ((Number) hud.getG().get()).intValue(), ((Number) hud.getB().get()).intValue()), new Color(((Number) hud.getR2().get()).intValue(), ((Number) hud.getG2().get()).intValue(), ((Number) hud.getB2().get()).intValue()), 10.0D);

                    Intrinsics.checkExpressionValueIsNotNull(color1, "VisualUtils.getGradientO…t(), hud.b2.get())),10.0)");
                    RenderUtils.drawGradientSidewaysV(d0, 0.0D, d1, 17.0D, colorall, color1.getRGB());
                    Fonts.tenacitybold30.drawStringWithShadow(String.valueOf(stack.getMaxDamage() - stack.getItemDamage()), x + 4, 20, colorall);
                    RoundedUtil.drawRoundOutline((float) x, 25.0F, (float) x + 18.0F, 26.0F, 4.3F, 0.4F, new Color(((Number) this.r.get()).intValue(), ((Number) this.g.get()).intValue(), ((Number) this.b.get()).intValue(), ((Number) this.a.get()).intValue()), Palette.fade1(new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue())));
                    RoundedUtil.drawRoundOutline((float) x, 25.0F, 70.0F, 76.2F, 4.3F, 1.7F, new Color(0, 0, 0, 0), new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue(), 90));
                    RenderUtils.drawRect((float) x, 25.0F, (float) x + 18.0F * (float) (stack.getMaxDamage() - stack.getItemDamage()) / (float) stack.getMaxDamage(), 26.0F, colorall);
                    renderItem.renderItemIntoGUI(stack, x + 1, y);
                    x += 20;
                    ++i;
                }
            }

            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GL11.glPopMatrix();
        }

        return new Border(-2.0F, -4.0F, 82.0F, 29.0F);
    }

    public Armor(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.colorModeValue = new ListValue("Text-Color", new String[] { "Custom", "Astolfo", "gradient"}, "Custom");
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.redValue = new IntegerValue("Text-R", 255, 0, 255);
        this.greenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.blueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.saturationValue = new FloatValue("Saturation", 0.9F, 0.0F, 1.0F);
        this.speed = new IntegerValue("AllSpeed", 0, 0, 400);
        this.r = new IntegerValue("Red", 229, 0, 255);
        this.g = new IntegerValue("Green", 100, 0, 255);
        this.b = new IntegerValue("Blue", 173, 0, 255);
        this.r2 = new IntegerValue("RectR", 109, 0, 255);
        this.g2 = new IntegerValue("RectG", 255, 0, 255);
        this.b2 = new IntegerValue("RectB", 255, 0, 255);
        this.a = new IntegerValue("Alpha", 100, 0, 255);
    }

    public Armor(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = -8.0D;
        }

        if ((i & 2) != 0) {
            d1 = 57.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN);
        }

        this(d0, d1, f, side);
    }

    public Armor() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }
}
