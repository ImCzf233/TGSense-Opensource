package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.utils.render.VisualUtils;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.util.IWrappedArray;
import net.ccbluex.liquidbounce.features.module.modules.color.Gident;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "Inventory"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010$\u001a\u00020%H\u0016J\u0012\u0010&\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\u0012\u0010*\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\u0012\u0010+\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J \u0010,\u001a\u00020\'2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0002\u001a\u00020\u001a2\u0006\u0010\u0004\u001a\u00020\u001aH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Inventory;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "(DDF)V", "astolfoRainbowIndex", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "astolfoRainbowOffset", "astolfoclient", "blueValue", "blur", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "distanceValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "gidentspeed", "gradientAmountValue", "greenValue", "inventoryRows", "", "lowerInv", "Lnet/minecraft/inventory/IInventory;", "mc1", "Lnet/minecraft/client/Minecraft;", "modeValue", "newRainbowIndex", "redValue", "saturationValue", "title", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "renderInventory1", "", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "renderInventory2", "renderInventory3", "renderItemStack", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "LiquidBounce"}
)
public final class Inventory extends Element {

    private int inventoryRows;
    private final IInventory lowerInv;
    private final BoolValue blur;
    private final BoolValue title;
    private final ListValue modeValue;
    private final FloatValue brightnessValue;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue gidentspeed;
    private final IntegerValue newRainbowIndex;
    private final IntegerValue astolfoRainbowOffset;
    private final IntegerValue astolfoclient;
    private final IntegerValue astolfoRainbowIndex;
    private final FloatValue saturationValue;
    private final ListValue colorModeValue;
    private final IntegerValue distanceValue;
    private final IntegerValue gradientAmountValue;
    private FontValue fontValue;
    private Minecraft mc1;

    @NotNull
    public Border drawElement() {
        float floatX = (float) this.getRenderX();
        float floatY = (float) this.getRenderY();
        String colorMode = (String) this.colorModeValue.get();
        int color = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue())).getRGB();

        if (((Boolean) this.blur.get()).booleanValue()) {
            GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
            GL11.glPushMatrix();
            BlurBuffer.blurArea(floatX + 7.0F, floatY + (float) 18, 166.0F, 70.0F);
            GL11.glPopMatrix();
            GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
        }

        String barLength1 = (String) this.modeValue.get();
        boolean flag = false;

        if (barLength1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s = barLength1.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            barLength1 = s;
            switch (barLength1.hashCode()) {
            case 1544803905:
                if (barLength1.equals("default")) {
                    RenderUtils.drawRect(7.0F, 21.0F, 172.0F, 90.0F, (new Color(20, 19, 18, 170)).getRGB());
                }
            }

            double d0 = (double) 163.0F;

            if (StringsKt.equals((String) this.modeValue.get(), "Default", true)) {
                RenderUtils.drawShadowWithCustomAlpha(7.0F, 21.0F, 165.0F, 68.0F, 200.0F);
                int i = 0;

                for (int i = ((Number) this.gradientAmountValue.get()).intValue(); i < i; ++i) {
                    double barStart = (double) i / (double) ((Number) this.gradientAmountValue.get()).intValue() * d0;
                    double barEnd = (double) (i + 1) / (double) ((Number) this.gradientAmountValue.get()).intValue() * d0;
                    double d1 = (double) 8 + barStart;
                    double d2 = (double) 8 + barEnd;
                    int j;
                    Color color;

                    if (StringsKt.equals(colorMode, "Fade", true)) {
                        color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), i * ((Number) this.distanceValue.get()).intValue(), 1000);
                        Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(\n         �?                        )");
                        j = color.getRGB();
                    } else if (StringsKt.equals(colorMode, "Astolfo", true)) {
                        j = VisualUtils.Astolfo(i * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                    } else if (StringsKt.equals(colorMode, "Gident", true)) {
                        color = VisualUtils.getGradientOffset(new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue()), new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Gident.gidentspeed.get()).intValue() + (double) (i * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                        Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO�?                        )");
                        j = color.getRGB();
                    } else {
                        j = StringsKt.equals(colorMode, "NewRainbow", true) ? VisualUtils.getRainbow(i * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                    }

                    int k;
                    Color color1;

                    if (StringsKt.equals(colorMode, "Fade", true)) {
                        color1 = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), i * ((Number) this.distanceValue.get()).intValue(), 1000);
                        Intrinsics.checkExpressionValueIsNotNull(color1, "Palette.fade2(\n         �?                        )");
                        k = color1.getRGB();
                    } else if (StringsKt.equals(colorMode, "Astolfo", true)) {
                        k = VisualUtils.Astolfo(i * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                    } else if (StringsKt.equals(colorMode, "Gident", true)) {
                        color1 = VisualUtils.getGradientOffset(new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue()), new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Gident.gidentspeed.get()).intValue() + (double) (i * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                        Intrinsics.checkExpressionValueIsNotNull(color1, "VisualUtils.getGradientO�?                        )");
                        k = color1.getRGB();
                    } else {
                        k = StringsKt.equals(colorMode, "NewRainbow", true) ? VisualUtils.getRainbow(i * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                    }

                    RenderUtils.drawGradientSideways(d1, 20.0D, d2, 21.0D, j, k);
                }
            }

            if (StringsKt.equals((String) this.modeValue.get(), "Shadow", true)) {
                RenderUtils.drawShadowWithCustomAlpha(7.0F, 18.0F, 166.0F, 70.0F, 255.0F);
            }

            if (((Boolean) this.title.get()).booleanValue()) {
                ((IFontRenderer) this.fontValue.get()).drawString("Inventory", 10, 23, (new Color(16777215)).getRGB());
            }

            if (this.lowerInv != null) {
                this.inventoryRows = this.lowerInv.getSizeInventory();
            }

            this.renderInventory1(MinecraftInstance.mc.getThePlayer());
            this.renderInventory2(MinecraftInstance.mc.getThePlayer());
            this.renderInventory3(MinecraftInstance.mc.getThePlayer());
            return new Border(8.0F, (float) this.inventoryRows * 18.0F + 17.0F, 172.0F, 90.0F);
        }
    }

    private final void renderInventory1(IEntityPlayerSP player) {
        IItemStack armourStack = null;

        if (player == null) {
            Intrinsics.throwNpe();
        }

        IWrappedArray renderStack = player.getInventory().getMainInventory();
        int xOffset = 8;

        renderStack = player.getInventory().getMainInventory();
        int index = 9;

        for (byte b0 = 17; index <= b0; ++index) {
            armourStack = (IItemStack) renderStack.get(index);
            if (armourStack != null) {
                this.renderItemStack(armourStack, xOffset, 30);
            }

            xOffset += 18;
        }

    }

    private final void renderInventory2(IEntityPlayerSP player) {
        IItemStack armourStack = null;

        if (player == null) {
            Intrinsics.throwNpe();
        }

        IWrappedArray renderStack = player.getInventory().getMainInventory();
        int xOffset = 8;

        renderStack = player.getInventory().getMainInventory();
        int index = 18;

        for (byte b0 = 26; index <= b0; ++index) {
            armourStack = (IItemStack) renderStack.get(index);
            if (armourStack != null) {
                this.renderItemStack(armourStack, xOffset, 48);
            }

            xOffset += 18;
        }

    }

    private final void renderInventory3(IEntityPlayerSP player) {
        IItemStack armourStack = null;

        if (player == null) {
            Intrinsics.throwNpe();
        }

        IWrappedArray renderStack = player.getInventory().getMainInventory();
        int xOffset = 8;

        renderStack = player.getInventory().getMainInventory();
        int index = 27;

        for (byte b0 = 35; index <= b0; ++index) {
            armourStack = (IItemStack) renderStack.get(index);
            if (armourStack != null) {
                this.renderItemStack(armourStack, xOffset, 66);
            }

            xOffset += 18;
        }

    }

    private final void renderItemStack(IItemStack stack, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        MinecraftInstance.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        MinecraftInstance.mc.getRenderItem().renderItemOverlays(MinecraftInstance.mc.getFontRendererObj(), stack, x, y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public Inventory(double x, double y, float scale) {
        super(x, y, scale, (Side) null, 8, (DefaultConstructorMarker) null);
        this.blur = new BoolValue("Blur", true);
        this.title = new BoolValue("Title", false);
        this.modeValue = new ListValue("Background", new String[] { "Default", "Shadow"}, "Default");
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.redValue = new IntegerValue("Text-R", 255, 0, 255);
        this.greenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.blueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.gidentspeed = new IntegerValue("GidentSpeed", 100, 1, 1000);
        this.newRainbowIndex = new IntegerValue("NewRainbowOffset", 1, 1, 50);
        this.astolfoRainbowOffset = new IntegerValue("AstolfoOffset", 5, 1, 20);
        this.astolfoclient = new IntegerValue("AstolfoRange", 109, 1, 765);
        this.astolfoRainbowIndex = new IntegerValue("AstolfoIndex", 109, 1, 300);
        this.saturationValue = new FloatValue("Saturation", 0.9F, 0.0F, 1.0F);
        this.colorModeValue = new ListValue("Text-Color", new String[] { "Custom", "Rainbow", "Fade", "Astolfo", "NewRainbow", "Gident"}, "Custom");
        this.distanceValue = new IntegerValue("Distance", 0, 0, 400);
        this.gradientAmountValue = new IntegerValue("Gradient-Amount", 25, 1, 50);
        IFontRenderer ifontrenderer = Fonts.fontSFUI35;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.fontSFUI35, "Fonts.fontSFUI35");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.mc1 = Minecraft.getMinecraft();
    }

    public Inventory(double d0, double d1, float f, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = -1.0D;
        }

        if ((i & 2) != 0) {
            d1 = 121.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        this(d0, d1, f);
    }

    public Inventory() {
        this(0.0D, 0.0D, 0.0F, 7, (DefaultConstructorMarker) null);
    }
}
