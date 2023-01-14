package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.utils.render.VisualUtils;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.Gident;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "AsianHat",
    description = "Yep. China Hat.",
    category = ModuleCategory.RENDER
)
public class AsianHat extends Module {

    private final ListValue colorModeValue = new ListValue("Color", new String[] { "Custom", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Gident"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private final IntegerValue colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
    private final IntegerValue colorEndAlphaValue = new IntegerValue("EndAlpha", 255, 0, 255);
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0F, 0.0F, 1.0F);
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
    private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    private final IntegerValue spaceValue = new IntegerValue("Color-Space", 0, 0, 100);
    private final BoolValue noFirstPerson = new BoolValue("NoFirstPerson", true);
    private final BoolValue hatBorder = new BoolValue("HatBorder", true);
    private final IntegerValue borderAlphaValue = new IntegerValue("BorderAlpha", 255, 0, 255);
    private final FloatValue borderWidthValue = new FloatValue("BorderWidth", 1.0F, 0.1F, 4.0F);
    private final List positions = new ArrayList();
    private double lastRadius = 0.0D;

    private void checkPosition(double radius) {
        if (radius != this.lastRadius) {
            this.positions.clear();

            for (int i = 0; i <= 360; ++i) {
                this.positions.add(new double[] { -Math.sin((double) i * 3.141592653589793D / 180.0D) * radius, Math.cos((double) i * 3.141592653589793D / 180.0D) * radius});
            }
        }

        this.lastRadius = radius;
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        EntityPlayerSP entity = AsianHat.mc2.player;

        if (entity != null && (!((Boolean) this.noFirstPerson.get()).booleanValue() || AsianHat.mc2.gameSettings.thirdPersonView != 0)) {
            AxisAlignedBB bb = entity.getEntityBoundingBox();
            double radius = bb.maxX - bb.minX;
            double height = bb.maxY - bb.minY;
            double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) AsianHat.mc2.timer.renderPartialTicks;
            double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) AsianHat.mc2.timer.renderPartialTicks;
            double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) AsianHat.mc2.timer.renderPartialTicks;
            Color colour = this.getColor(entity, 0);
            float r = (float) colour.getRed() / 255.0F;
            float g = (float) colour.getGreen() / 255.0F;
            float b = (float) colour.getBlue() / 255.0F;
            float al = (float) ((Integer) this.colorAlphaValue.get()).intValue() / 255.0F;
            float Eal = (float) ((Integer) this.colorEndAlphaValue.get()).intValue() / 255.0F;
            float partialTicks = event.getPartialTicks();
            double viewX = -AsianHat.mc.getRenderManager().getViewerPosX();
            double viewY = -AsianHat.mc.getRenderManager().getViewerPosY();
            double viewZ = -AsianHat.mc.getRenderManager().getViewerPosZ();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder worldrenderer = tessellator.getBuffer();

            this.checkPosition(radius);
            pre3D();
            worldrenderer.begin(9, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(viewX + posX, viewY + posY + height + 0.3D, viewZ + posZ).color(r, g, b, al).endVertex();
            int i = 0;

            float r2;
            float g2;

            for (Iterator lineAlp = this.positions.iterator(); lineAlp.hasNext(); ++i) {
                double[] smolPos = (double[]) lineAlp.next();
                double smolPos1 = posX + smolPos[0];
                double posZ2 = posZ + smolPos[1];

                if (((Integer) this.spaceValue.get()).intValue() > 0 && !((String) this.colorModeValue.get()).equalsIgnoreCase("Custom")) {
                    Color colour2 = this.getColor(entity, i * ((Integer) this.spaceValue.get()).intValue());
                    float colour21 = (float) colour2.getRed() / 255.0F;

                    r2 = (float) colour2.getGreen() / 255.0F;
                    g2 = (float) colour2.getBlue() / 255.0F;
                    worldrenderer.pos(viewX + smolPos1, viewY + posY + height, viewZ + posZ2).color(colour21, r2, g2, Eal).endVertex();
                } else {
                    worldrenderer.pos(viewX + smolPos1, viewY + posY + height, viewZ + posZ2).color(r, g, b, Eal).endVertex();
                }
            }

            worldrenderer.pos(viewX + posX, viewY + posY + height + 0.3D, viewZ + posZ).color(r, g, b, al).endVertex();
            tessellator.draw();
            if (((Boolean) this.hatBorder.get()).booleanValue()) {
                float f = (float) ((Integer) this.borderAlphaValue.get()).intValue() / 255.0F;

                GL11.glLineWidth(((Float) this.borderWidthValue.get()).floatValue());
                worldrenderer.begin(2, DefaultVertexFormats.POSITION_COLOR);
                i = 0;

                for (Iterator iterator = this.positions.iterator(); iterator.hasNext(); ++i) {
                    double[] adouble = (double[]) iterator.next();
                    double posX2 = posX + adouble[0];
                    double posZ21 = posZ + adouble[1];

                    if (((Integer) this.spaceValue.get()).intValue() > 0 && !((String) this.colorModeValue.get()).equalsIgnoreCase("Custom")) {
                        Color color = this.getColor(entity, i * ((Integer) this.spaceValue.get()).intValue());

                        r2 = (float) color.getRed() / 255.0F;
                        g2 = (float) color.getGreen() / 255.0F;
                        float b2 = (float) color.getBlue() / 255.0F;

                        worldrenderer.pos(viewX + posX2, viewY + posY + height, viewZ + posZ21).color(r2, g2, b2, f).endVertex();
                    } else {
                        worldrenderer.pos(viewX + posX2, viewY + posY + height, viewZ + posZ21).color(r, g, b, f).endVertex();
                    }
                }

                tessellator.draw();
            }

            post3D();
        }
    }

    public final Color getColor(Entity ent, int index) {
        String s = (String) this.colorModeValue.get();
        byte b0 = -1;

        switch (s.hashCode()) {
        case -1656737386:
            if (s.equals("Rainbow")) {
                b0 = 2;
            }
            break;

        case -884013110:
            if (s.equals("LiquidSlowly")) {
                b0 = 3;
            }
            break;

        case 2029746065:
            if (s.equals("Custom")) {
                b0 = 1;
            }
            break;

        case 2132719113:
            if (s.equals("Gident")) {
                b0 = 0;
            }
        }

        switch (b0) {
        case 0:
            return VisualUtils.getGradientOffset(new Color(((Integer) Gident.redValue.get()).intValue(), ((Integer) Gident.greenValue.get()).intValue(), ((Integer) Gident.blueValue.get()).intValue()), new Color(((Integer) Gident.redValue2.get()).intValue(), ((Integer) Gident.greenValue2.get()).intValue(), ((Integer) Gident.blueValue2.get()).intValue()), (double) ((float) index * 16.39F));

        case 1:
            return new Color(((Integer) this.colorRedValue.get()).intValue(), ((Integer) this.colorGreenValue.get()).intValue(), ((Integer) this.colorBlueValue.get()).intValue());

        case 2:
            return new Color(RenderUtils.getRainbowOpaque(((Integer) this.mixerSecondsValue.get()).intValue(), ((Float) this.saturationValue.get()).floatValue(), ((Float) this.brightnessValue.get()).floatValue(), index));

        case 3:
            return ColorUtils.LiquidSlowly(System.nanoTime(), index, ((Float) this.saturationValue.get()).floatValue(), ((Float) this.brightnessValue.get()).floatValue());

        default:
            return ColorUtils.fade(new Color(((Integer) this.colorRedValue.get()).intValue(), ((Integer) this.colorGreenValue.get()).intValue(), ((Integer) this.colorBlueValue.get()).intValue()), index, 100);
        }
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glHint(3154, 4354);
        GL11.glDisable(2884);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
