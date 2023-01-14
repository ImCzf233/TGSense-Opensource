package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.HanaBiColors;
import net.ccbluex.liquidbounce.utils.render.PotionData;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Translate;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "NewEffects"
)
public class NewEffects extends Element {

    private final Map potionMap = new HashMap();

    public Border drawElement() {
        GlStateManager.pushMatrix();
        int y = 0;

        for (Iterator iterator = NewEffects.mc.getThePlayer().getActivePotionEffects().iterator(); iterator.hasNext(); y -= 35) {
            IPotionEffect potionEffect = (IPotionEffect) iterator.next();
            IPotion potion = NewEffects.functions.getPotionById(potionEffect.getPotionID());
            String name = NewEffects.functions.formatI18n(potion.getName(), new String[0]);
            PotionData potionData;

            if (this.potionMap.containsKey(potion) && ((PotionData) this.potionMap.get(potion)).level == potionEffect.getAmplifier()) {
                potionData = (PotionData) this.potionMap.get(potion);
            } else {
                this.potionMap.put(potion, potionData = new PotionData(potion, new Translate(0.0F, -40.0F + (float) y), potionEffect.getAmplifier()));
            }

            boolean flag = true;
            Iterator potionTime = NewEffects.mc.getThePlayer().getActivePotionEffects().iterator();

            while (potionTime.hasNext()) {
                IPotionEffect potionMaxTime = (IPotionEffect) potionTime.next();

                if (potionMaxTime.getAmplifier() == potionData.level) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                this.potionMap.remove(potion);
            }

            int potionTime1;
            int potionMaxTime1;

            try {
                potionTime1 = Integer.parseInt(potionEffect.getDurationString().split(":")[0]);
                potionMaxTime1 = Integer.parseInt(potionEffect.getDurationString().split(":")[1]);
            } catch (Exception exception) {
                potionTime1 = 100;
                potionMaxTime1 = 1000;
            }

            int lifeTime = potionTime1 * 60 + potionMaxTime1;

            if (potionData.getMaxTimer() == 0 || (double) lifeTime > (double) potionData.getMaxTimer()) {
                potionData.maxTimer = lifeTime;
            }

            float state = 0.0F;

            if ((double) lifeTime >= 0.0D) {
                state = (float) ((double) lifeTime / (double) ((float) potionData.getMaxTimer()) * 100.0D);
            }

            int position = Math.round(potionData.translate.getY() + 5.0F);

            state = Math.max(state, 2.0F);
            potionData.translate.interpolate(0.0F, (float) y, 0.1D);
            potionData.animationX = (float) RenderUtils.getAnimationState2((double) potionData.getAnimationX(), (double) (1.2F * state), (double) (Math.max(10.0F, Math.abs(potionData.animationX - 1.2F * state) * 15.0F) * 0.3F));
            RenderUtils.drawRectPotion(0.0F, potionData.translate.getY(), 120.0F, potionData.translate.getY() + 30.0F, ClientUtils.reAlpha(HanaBiColors.GREY.c, 0.1F));
            RenderUtils.drawShadowWithCustomAlpha(0.0F, (float) Math.round(potionData.translate.getY()), 120.0F, 30.0F, 200.0F);
            float posY = potionData.translate.getY() + 13.0F;

            Fonts.fontSFUI35.drawString(name + " " + this.intToRomanByGreedy(potionEffect.getAmplifier() + 1), 29.0F, posY - (float) NewEffects.mc.getFontRendererObj().getFontHeight(), ClientUtils.reAlpha(HanaBiColors.WHITE.c, 0.8F));
            Fonts.font35.drawString(potionEffect.getDurationString(), 29.0F, posY + 4.0F, ClientUtils.reAlpha((new Color(180, 180, 180)).getRGB(), 0.5F));
            if (potion.getHasStatusIcon()) {
                GlStateManager.pushMatrix();
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glDepthMask(false);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                int statusIconIndex = potion.getStatusIconIndex();

                NewEffects.mc.getTextureManager().bindTexture(NewEffects.classProvider.createResourceLocation("textures/gui/container/inventory.png"));
                NewEffects.mc2.ingameGUI.drawTexturedModalRect(6.0F, (float) (position + 40), statusIconIndex % 8 * 18, 198 + statusIconIndex / 8 * 18, 18, 18);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();
        return new Border(0.0F, 0.0F, 120.0F, 30.0F);
    }

    private String intToRomanByGreedy(int num) {
        int[] values = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < values.length && num >= 0; ++i) {
            while (values[i] <= num) {
                num -= values[i];
                stringBuilder.append(symbols[i]);
            }
        }

        return stringBuilder.toString();
    }
}
