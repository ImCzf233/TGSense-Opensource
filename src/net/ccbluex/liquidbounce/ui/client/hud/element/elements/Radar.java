package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.utils.render.VisualUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.Gident;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

@ElementInfo(
    name = "NewRadar"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\n\u0010(\u001a\u0004\u0018\u00010)H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundAlphaValue", "backgroundBlueValue", "backgroundGreenValue", "backgroundRedValue", "blueValue", "blur", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "borderAlphaValue", "borderBlueValue", "borderGreenValue", "borderRedValue", "borderStrengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "borderValue", "brightnessValue", "cRainbowSecValue", "distanceValue", "exhiValue", "fovSizeValue", "gidentspeed", "gradientAmountValue", "greenValue", "lineValue", "playerShapeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "playerSizeValue", "rainbowList", "redValue", "saturationValue", "shadowValue", "sizeValue", "viewDistanceValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "Companion", "LiquidBounce"}
)
public final class Radar extends Element {

    private final FloatValue sizeValue;
    private final FloatValue viewDistanceValue;
    private final ListValue playerShapeValue;
    private final FloatValue playerSizeValue;
    private final FloatValue fovSizeValue;
    private final BoolValue exhiValue;
    private final BoolValue blur;
    private final BoolValue lineValue;
    private final ListValue rainbowList;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue alphaValue;
    private final IntegerValue gidentspeed;
    private final FloatValue saturationValue;
    private final FloatValue brightnessValue;
    private final IntegerValue cRainbowSecValue;
    private final IntegerValue distanceValue;
    private final IntegerValue gradientAmountValue;
    private final IntegerValue backgroundRedValue;
    private final IntegerValue backgroundGreenValue;
    private final IntegerValue backgroundBlueValue;
    private final IntegerValue backgroundAlphaValue;
    private final BoolValue borderValue;
    private final FloatValue borderStrengthValue;
    private final IntegerValue borderRedValue;
    private final IntegerValue borderGreenValue;
    private final IntegerValue borderBlueValue;
    private final IntegerValue borderAlphaValue;
    private final BoolValue shadowValue;
    private static final float SQRT_OF_TWO;
    public static final Radar.Companion Companion = new Radar.Companion((DefaultConstructorMarker) null);

    @Nullable
    public Border drawElement() {
        IEntity renderViewEntity = MinecraftInstance.mc.getRenderViewEntity();
        float size = ((Number) this.sizeValue.get()).floatValue();
        float viewDistance = ((Number) this.viewDistanceValue.get()).floatValue() * 16.0F;
        double maxDisplayableDistanceSquare = ((double) viewDistance + (double) ((Number) this.fovSizeValue.get()).floatValue()) * ((double) viewDistance + (double) ((Number) this.fovSizeValue.get()).floatValue());
        float halfSize = size / 2.0F;
        String rainbowType = (String) this.rainbowList.get();
        int cColor = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue(), ((Number) this.alphaValue.get()).intValue())).getRGB();

        if (((Boolean) this.blur.get()).booleanValue()) {
            GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
            BlurBuffer.blurArea((float) this.getRenderX(), (float) this.getRenderY(), size, size);
            BlurBuffer.blurArea((float) this.getRenderX(), (float) this.getRenderY(), size, size);
            GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
        }

        if (((Boolean) this.exhiValue.get()).booleanValue()) {
            RenderUtils.drawExhiRect(0.0F, ((Boolean) this.lineValue.get()).booleanValue() ? -1.0F : 0.0F, size, size);
        } else {
            RenderUtils.drawRect(0.0F, 0.0F, size, size, (new Color(((Number) this.backgroundRedValue.get()).intValue(), ((Number) this.backgroundGreenValue.get()).intValue(), ((Number) this.backgroundBlueValue.get()).intValue(), ((Number) this.backgroundAlphaValue.get()).intValue())).getRGB());
        }

        if (((Boolean) this.shadowValue.get()).booleanValue()) {
            RenderUtils.drawShadowWithCustomAlpha(0.0F, 0.0F, size, size, 255.0F);
        }

        if (((Boolean) this.lineValue.get()).booleanValue()) {
            double circleMode = (double) size;
            int worldRenderer = 0;
            int playerSize = ((Number) this.gradientAmountValue.get()).intValue() - 1;

            if (worldRenderer <= playerSize) {
                while (true) {
                    double entity;
                    double positionRelativeToPlayer;
                    int i;
                    label137: {
                        entity = (double) worldRenderer / (double) ((Number) this.gradientAmountValue.get()).intValue() * circleMode;
                        positionRelativeToPlayer = (double) (worldRenderer + 1) / (double) ((Number) this.gradientAmountValue.get()).intValue() * circleMode;
                        Color color;

                        switch (rainbowType.hashCode()) {
                        case -884013110:
                            if (rainbowType.equals("LiquidSlowly")) {
                                color = ColorUtils.LiquidSlowly(System.nanoTime(), worldRenderer * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                                if (color == null) {
                                    Intrinsics.throwNpe();
                                }

                                i = color.getRGB();
                                break label137;
                            }
                            break;

                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                i = RenderUtils.getRainbowOpaque(((Number) this.cRainbowSecValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), worldRenderer * ((Number) this.distanceValue.get()).intValue());
                                break label137;
                            }
                            break;

                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                i = RenderUtils.SkyRainbow(worldRenderer * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                                break label137;
                            }
                            break;

                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                i = ColorUtils.fade(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), worldRenderer * ((Number) this.distanceValue.get()).intValue(), 100).getRGB();
                                break label137;
                            }
                            break;

                        case 2132719113:
                            if (rainbowType.equals("Gident")) {
                                color = VisualUtils.getGradientOffset(new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue()), new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Gident.gidentspeed.get()).intValue() + (double) (worldRenderer * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                                Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO…stanceValue.get()) / 10))");
                                i = color.getRGB();
                                break label137;
                            }
                        }

                        i = cColor;
                    }

                    int j;
                    label127: {
                        Color color1;

                        switch (rainbowType.hashCode()) {
                        case -884013110:
                            if (rainbowType.equals("LiquidSlowly")) {
                                color1 = ColorUtils.LiquidSlowly(System.nanoTime(), (worldRenderer + 1) * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                                if (color1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                j = color1.getRGB();
                                break label127;
                            }
                            break;

                        case -852561933:
                            if (rainbowType.equals("CRainbow")) {
                                j = RenderUtils.getRainbowOpaque(((Number) this.cRainbowSecValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), (worldRenderer + 1) * ((Number) this.distanceValue.get()).intValue());
                                break label127;
                            }
                            break;

                        case 83201:
                            if (rainbowType.equals("Sky")) {
                                j = RenderUtils.SkyRainbow((worldRenderer + 1) * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue());
                                break label127;
                            }
                            break;

                        case 2181788:
                            if (rainbowType.equals("Fade")) {
                                j = ColorUtils.fade(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), (worldRenderer + 1) * ((Number) this.distanceValue.get()).intValue(), 100).getRGB();
                                break label127;
                            }
                            break;

                        case 2132719113:
                            if (rainbowType.equals("Gident")) {
                                color1 = VisualUtils.getGradientOffset(new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue()), new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Gident.gidentspeed.get()).intValue() + (double) (worldRenderer * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                                Intrinsics.checkExpressionValueIsNotNull(color1, "VisualUtils.getGradientO…stanceValue.get()) / 10))");
                                j = color1.getRGB();
                                break label127;
                            }
                        }

                        j = cColor;
                    }

                    RenderUtils.drawGradientSideways(entity, -1.0D, positionRelativeToPlayer, 0.0D, i, j);
                    if (worldRenderer == playerSize) {
                        break;
                    }

                    ++worldRenderer;
                }
            }
        }

        if (((Boolean) this.borderValue.get()).booleanValue()) {
            float f = ((Number) this.borderStrengthValue.get()).floatValue() / 2.0F;
            int tessellator = (new Color(((Number) this.borderRedValue.get()).intValue(), ((Number) this.borderGreenValue.get()).intValue(), ((Number) this.borderBlueValue.get()).intValue(), ((Number) this.borderAlphaValue.get()).intValue())).getRGB();

            RenderUtils.drawRect(halfSize - f, 0.0F, halfSize + f, size, tessellator);
            RenderUtils.drawRect(0.0F, halfSize - f, halfSize - f, halfSize + f, tessellator);
            RenderUtils.drawRect(halfSize + f, halfSize - f, size, halfSize + f, tessellator);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        float f1 = (float) this.getX();
        float f2 = (float) this.getY();
        float f3 = (float) this.getX();
        float f4 = f2;
        float f5 = f1;
        boolean flag = false;
        float f6 = (float) Math.ceil((double) size);
        float f7 = f3 + f6;

        f6 = (float) this.getY();
        f3 = f7;
        flag = false;
        float f8 = (float) Math.ceil((double) size);

        RenderUtils.makeScissorBox(f5, f4, f3, f6 + f8);
        GL11.glEnable(3089);
        GL11.glPushMatrix();
        GL11.glTranslatef(halfSize, halfSize, 0.0F);
        if (renderViewEntity == null) {
            Intrinsics.throwNpe();
        }

        GL11.glRotatef(renderViewEntity.getRotationYaw(), 0.0F, 0.0F, -1.0F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        flag = StringsKt.equals((String) this.playerShapeValue.get(), "circle", true);
        Tessellator tessellator = Tessellator.getInstance();

        Intrinsics.checkExpressionValueIsNotNull(tessellator, "tessellator");
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        if (flag) {
            GL11.glEnable(2832);
        }

        float f9 = ((Number) this.playerSizeValue.get()).floatValue();

        GL11.glEnable(2881);
        bufferbuilder.begin(0, DefaultVertexFormats.POSITION_COLOR);
        GL11.glPointSize(f9);
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterator iterator = iworldclient.getLoadedEntityList().iterator();

        while (iterator.hasNext()) {
            IEntity ientity = (IEntity) iterator.next();

            if (ientity != null && ientity != MinecraftInstance.mc.getThePlayer() && EntityUtils.isSelected(ientity, false)) {
                Vector2f vector2f = new Vector2f((float) (renderViewEntity.getPosX() - ientity.getPosX()), (float) (renderViewEntity.getPosZ() - ientity.getPosZ()));

                if (maxDisplayableDistanceSquare >= (double) vector2f.lengthSquared()) {
                    boolean transform = ((Number) this.fovSizeValue.get()).floatValue() > 0.0F;

                    if (transform) {
                        GL11.glPushMatrix();
                        GL11.glTranslatef(vector2f.x / viewDistance * size, vector2f.y / viewDistance * size, 0.0F);
                        GL11.glRotatef(ientity.getRotationYaw(), 0.0F, 0.0F, 1.0F);
                    }

                    Module module = LiquidBounce.INSTANCE.getModuleManager().get(ESP.class);

                    if (module == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.ESP");
                    }

                    Color color = ((ESP) module).getColor(ientity);

                    bufferbuilder.pos((double) (vector2f.x / viewDistance * size), (double) (vector2f.y / viewDistance * size), 0.0D).color((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, 1.0F).endVertex();
                    if (transform) {
                        GL11.glPopMatrix();
                    }
                }
            }
        }

        tessellator.draw();
        if (flag) {
            GL11.glDisable(2832);
        }

        GL11.glDisable(2881);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        return new Border(0.0F, 0.0F, size, size);
    }

    public Radar(double x, double y) {
        super(x, y, 0.0F, (Side) null, 12, (DefaultConstructorMarker) null);
        this.sizeValue = new FloatValue("Size", 90.0F, 30.0F, 500.0F);
        this.viewDistanceValue = new FloatValue("View Distance", 4.0F, 0.5F, 32.0F);
        this.playerShapeValue = new ListValue("Player Shape", new String[] { "Rectangle", "Circle"}, "Triangle");
        this.playerSizeValue = new FloatValue("Player Size", 2.0F, 0.5F, 20.0F);
        this.fovSizeValue = new FloatValue("FOV Size", 10.0F, 0.0F, 50.0F);
        this.exhiValue = new BoolValue("Use Exhi Rect", false);
        this.blur = new BoolValue("Blur", true);
        this.lineValue = new BoolValue("Line", false);
        this.rainbowList = new ListValue("Line-Rainbow", new String[] { "Off", "CRainbow", "Sky", "LiquidSlowly", "Fade", "Gident"}, "Off");
        this.redValue = new IntegerValue("Line-Red", 255, 0, 255);
        this.greenValue = new IntegerValue("Line-Green", 255, 0, 255);
        this.blueValue = new IntegerValue("Line-Blue", 255, 0, 255);
        this.alphaValue = new IntegerValue("Line-Alpha", 255, 0, 255);
        this.gidentspeed = new IntegerValue("GidentSpeed", 100, 1, 1000);
        this.saturationValue = new FloatValue("Saturation", 0.9F, 0.0F, 1.0F);
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.cRainbowSecValue = new IntegerValue("Seconds", 2, 1, 10);
        this.distanceValue = new IntegerValue("Line-Distance", 0, 0, 400);
        this.gradientAmountValue = new IntegerValue("Gradient-Amount", 25, 1, 50);
        this.backgroundRedValue = new IntegerValue("Background Red", 0, 0, 255);
        this.backgroundGreenValue = new IntegerValue("Background Green", 0, 0, 255);
        this.backgroundBlueValue = new IntegerValue("Background Blue", 0, 0, 255);
        this.backgroundAlphaValue = new IntegerValue("Background Alpha", 50, 0, 255);
        this.borderValue = new BoolValue("Border", false);
        this.borderStrengthValue = new FloatValue("Border Strength", 2.0F, 1.0F, 5.0F);
        this.borderRedValue = new IntegerValue("Border Red", 0, 0, 255);
        this.borderGreenValue = new IntegerValue("Border Green", 0, 0, 255);
        this.borderBlueValue = new IntegerValue("Border Blue", 0, 0, 255);
        this.borderAlphaValue = new IntegerValue("Border Alpha", 150, 0, 255);
        this.shadowValue = new BoolValue("Shadow", true);
    }

    public Radar(double d0, double d1, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 5.0D;
        }

        if ((i & 2) != 0) {
            d1 = 130.0D;
        }

        this(d0, d1);
    }

    public Radar() {
        this(0.0D, 0.0D, 3, (DefaultConstructorMarker) null);
    }

    static {
        float f = 2.0F;
        boolean flag = false;

        SQRT_OF_TWO = (float) Math.sqrt((double) f);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar$Companion;", "", "()V", "SQRT_OF_TWO", "", "LiquidBounce"}
    )
    public static final class Companion {

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
