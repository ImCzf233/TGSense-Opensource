package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.Colors;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "PotionRender",
    description = "PotionRender",
    category = ModuleCategory.RENDER
)
public class PotionRender extends Module {

    private final Map potionMaxDurations = new HashMap();
    Map timerMap = new HashMap();
    private int x;

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        IScaledResolution sr = PotionRender.classProvider.createScaledResolution(PotionRender.mc);
        float width = (float) sr.getScaledWidth();
        float height = (float) sr.getScaledHeight();

        this.renderPotionStatusNew((int) width, (int) height);
    }

    public void renderPotionStatusNew(int width, int height) {
        this.x = 0;
        int length;
        int tempY = (length = HUD.mc.getThePlayer().getActivePotionEffects().size()) * -30;

        if (length != 0) {
            RenderUtils.drawCircleRect((float) (width - 120), (float) (height - 30 + tempY), (float) (width - 10), (float) (height - 30), 3.0F, (new Color(0, 0, 0, 100)).getRGB());
        }

        ArrayList needRemove = new ArrayList();
        Iterator iterator = this.potionMaxDurations.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry effect = (Entry) iterator.next();

            if (HUD.mc.getThePlayer().getActivePotionEffect(PotionRender.functions.getPotionById(((Integer) effect.getKey()).intValue())) == null) {
                needRemove.add(effect.getKey());
            }
        }

        iterator = needRemove.iterator();

        while (iterator.hasNext()) {
            int effect1 = ((Integer) iterator.next()).intValue();

            this.potionMaxDurations.remove(Integer.valueOf(effect1));
        }

        for (iterator = HUD.mc.getThePlayer().getActivePotionEffects().iterator(); iterator.hasNext(); this.x -= 30) {
            IPotionEffect effect2 = (IPotionEffect) iterator.next();

            if (!this.potionMaxDurations.containsKey(Integer.valueOf(effect2.getPotionID())) || ((Integer) this.potionMaxDurations.get(Integer.valueOf(effect2.getPotionID()))).intValue() < effect2.getDuration()) {
                this.potionMaxDurations.put(Integer.valueOf(effect2.getPotionID()), Integer.valueOf(effect2.getDuration()));
            }

            IPotion potion = PotionRender.functions.getPotionById(effect2.getPotionID());
            String PType = PotionRender.functions.formatI18n(potion.getName(), new String[] { Arrays.toString(new Object[0])});

            int minutes;
            int seconds;

            try {
                minutes = Integer.parseInt(effect2.getDurationString().split(":")[0]);
                seconds = Integer.parseInt(effect2.getDurationString().split(":")[1]);
            } catch (Exception exception) {
                minutes = 0;
                seconds = 0;
            }

            double total = (double) (minutes * 60 + seconds);

            if (!this.timerMap.containsKey(potion)) {
                this.timerMap.put(potion, Double.valueOf(total));
            }

            if (((Double) this.timerMap.get(potion)).doubleValue() == 0.0D || total > ((Double) this.timerMap.get(potion)).doubleValue()) {
                this.timerMap.replace(potion, Double.valueOf(total));
            }

            int color = Colors.blendColors(new float[] { 0.0F, 0.5F, 1.0F}, new Color[] { new Color(250, 50, 56), new Color(236, 129, 44), new Color(5, 134, 105)}, (float) effect2.getDuration() / (1.0F * (float) ((Integer) this.potionMaxDurations.get(Integer.valueOf(effect2.getPotionID()))).intValue())).getRGB();
            int x1 = (int) ((float) (width - 6) * 1.33F);
            int y1 = (int) ((float) (height - 52 - HUD.mc.getFontRendererObj().getFontHeight() + this.x + 5) * 1.33F);
            float rectX = (float) (width - 120) + 110.0F * ((float) effect2.getDuration() / (1.0F * (float) ((Integer) this.potionMaxDurations.get(Integer.valueOf(effect2.getPotionID()))).intValue()));
            int y2;

            if (potion.getHasStatusIcon()) {
                PotionRender.classProvider.getGlStateManager().pushMatrix();
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glDepthMask(false);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                y2 = potion.getStatusIconIndex();
                IResourceLocation location = PotionRender.classProvider.createResourceLocation("textures/gui/container/inventory.png");

                HUD.mc.getTextureManager().bindTexture(location);
                GlStateManager.scale(0.75D, 0.75D, 0.75D);
                PotionRender.mc2.ingameGUI.drawTexturedModalRect(x1 - 138, y1 + 8, y2 % 8 * 18, 198 + y2 / 8 * 18, 18, 18);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GlStateManager.popMatrix();
            }

            y2 = height - HUD.mc.getFontRendererObj().getFontHeight() + this.x - 38;
            RenderUtils.drawArc((float) width - 104.75F, (float) y2 + 2.5F, 10.0D, (new Color(22, 28, 15)).getRGB(), 0, 360.0D, 3);
            RenderUtils.drawArc((float) width - 104.75F, (float) y2 + 2.5F, 10.0D, color, 0, (double) (360.0F * ((float) effect2.getDuration() / (1.0F * (float) ((Integer) this.potionMaxDurations.get(Integer.valueOf(effect2.getPotionID()))).intValue()))), 3);
            Fonts.font35.drawString(PType.replaceAll("§.", ""), (float) width - 85.0F, (float) (y2 - HUD.mc.getFontRendererObj().getFontHeight() + 2), -1);
            RenderUtils.drawRect((float) width - 91.0F, (float) y2 - 3.0F, (float) width - 89.5F, (float) y2 + 10.0F, (new Color(255, 255, 255, 100)).getRGB());
            Fonts.Comfortaa35.drawString(effect2.getDurationString().replaceAll("§.", ""), (float) width - 85.0F, (float) y2 + 3.5F, color);
        }

    }
}
