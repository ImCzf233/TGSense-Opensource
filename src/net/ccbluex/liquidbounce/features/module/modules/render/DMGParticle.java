package net.ccbluex.liquidbounce.features.module.modules.render;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.render.DMGPutil.Location;
import net.ccbluex.liquidbounce.features.module.modules.render.DMGPutil.Particles;
import net.ccbluex.liquidbounce.injection.backend.EntityLivingBaseImplKt;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "DMGParticle",
    description = "Display heatlh volume change value",
    category = ModuleCategory.RENDER
)
public class DMGParticle extends Module {

    private HashMap healthMap = new HashMap();
    private List particles = new ArrayList();

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        KillAura ka = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);

        for (int i1 = 0; i1 < this.particles.size(); ++i1) {
            Particles entity = (Particles) this.particles.get(i1);
            int floatValue = ++entity.ticks;

            if (floatValue < 10) {
                entity.location.setY(entity.location.getY() + (double) entity.ticks * 0.002D);
            }

            if (floatValue > 20) {
                this.particles.remove(entity);
            }
        }

        EntityLivingBase entitylivingbase = ka.getTarget() == null ? null : EntityLivingBaseImplKt.unwrap(ka.getTarget());

        if (entitylivingbase != null && entitylivingbase != DMGParticle.mc.getThePlayer()) {
            if (!this.healthMap.containsKey(entitylivingbase)) {
                this.healthMap.put(entitylivingbase, Float.valueOf(entitylivingbase.getHealth()));
            }

            float f = ((Float) this.healthMap.get(entitylivingbase)).floatValue();
            float health = entitylivingbase.getHealth();
            Criticals criticals = (Criticals) LiquidBounce.moduleManager.get(Criticals.class);

            if (f != health) {
                String text;

                if (f - health < 0.0F) {
                    text = "§a" + roundToPlace((double) ((f - health) * -1.0F), 1);
                } else {
                    text = "§e" + roundToPlace((double) (f - health), 1);
                }

                Location location = new Location(entitylivingbase);

                location.setY(entitylivingbase.getEntityBoundingBox().minY + (entitylivingbase.getEntityBoundingBox().maxY - entitylivingbase.getEntityBoundingBox().minY) / 2.0D);
                location.setX(location.getX() - 0.5D + (double) (new Random(System.currentTimeMillis())).nextInt(5) * 0.15D);
                location.setZ(location.getZ() - 0.5D + (double) (new Random(System.currentTimeMillis() + 1L)).nextInt(5) * 0.15D);
                this.particles.add(new Particles(location, text));
                this.healthMap.remove(entitylivingbase);
                this.healthMap.put(entitylivingbase, Float.valueOf(entitylivingbase.getHealth()));
            }

        }
    }

    @EventTarget
    public void onRender(Render3DEvent event) {
        Iterator iterator = this.particles.iterator();

        while (iterator.hasNext()) {
            Particles p = (Particles) iterator.next();
            double x = p.location.getX();

            DMGParticle.mc.getRenderManager();
            double n = x - DMGParticle.mc.getRenderManager().getRenderPosX();
            double y = p.location.getY();

            DMGParticle.mc.getRenderManager();
            double n2 = y - DMGParticle.mc.getRenderManager().getRenderPosY();
            double z = p.location.getZ();

            DMGParticle.mc.getRenderManager();
            double n3 = z - DMGParticle.mc.getRenderManager().getRenderPosZ();

            GlStateManager.pushMatrix();
            GlStateManager.enablePolygonOffset();
            GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
            GlStateManager.translate((float) n, (float) n2, (float) n3);
            GlStateManager.rotate(-DMGParticle.mc.getRenderManager().getPlayerViewY(), 0.0F, 1.0F, 0.0F);
            float textY = 1.0F;

            GlStateManager.rotate(DMGParticle.mc.getRenderManager().getPlayerViewX(), textY, 0.0F, 0.0F);
            double size = 0.025D;

            GlStateManager.scale(-0.025D, -0.025D, 0.025D);
            enableGL2D();
            disableGL2D();
            GL11.glDepthMask(false);
            DMGParticle.mc.getFontRendererObj().drawString(p.text, (float) (-(DMGParticle.mc.getFontRendererObj().getStringWidth(p.text) / 2)), (float) (-(DMGParticle.mc.getFontRendererObj().getFontHeight() - 1)), 0, true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(true);
            GlStateManager.doPolygonOffset(1.0F, 1500000.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.popMatrix();
        }

    }

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static double roundToPlace(double p_roundToPlace_0_, int p_roundToPlace_2_) {
        if (p_roundToPlace_2_ < 0) {
            throw new IllegalArgumentException();
        } else {
            return (new BigDecimal(p_roundToPlace_0_)).setScale(p_roundToPlace_2_, RoundingMode.HALF_UP).doubleValue();
        }
    }
}
