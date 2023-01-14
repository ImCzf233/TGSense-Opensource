package me.Skid.utils;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class CompassUtil extends MinecraftInstance {

    public float innerWidth;
    public float outerWidth;
    public boolean shadow;
    public float scale;
    public int accuracy;
    public List degrees = Lists.newArrayList();

    public CompassUtil(float i, float o, float s, int a, boolean sh) {
        this.innerWidth = i;
        this.outerWidth = o;
        this.scale = s;
        this.accuracy = a;
        this.shadow = sh;
        this.degrees.add(new Degree("N", 1));
        this.degrees.add(new Degree("195", 2));
        this.degrees.add(new Degree("210", 2));
        this.degrees.add(new Degree("NE", 3));
        this.degrees.add(new Degree("240", 2));
        this.degrees.add(new Degree("255", 2));
        this.degrees.add(new Degree("E", 1));
        this.degrees.add(new Degree("285", 2));
        this.degrees.add(new Degree("300", 2));
        this.degrees.add(new Degree("SE", 3));
        this.degrees.add(new Degree("330", 2));
        this.degrees.add(new Degree("345", 2));
        this.degrees.add(new Degree("S", 1));
        this.degrees.add(new Degree("15", 2));
        this.degrees.add(new Degree("30", 2));
        this.degrees.add(new Degree("SW", 3));
        this.degrees.add(new Degree("60", 2));
        this.degrees.add(new Degree("75", 2));
        this.degrees.add(new Degree("W", 1));
        this.degrees.add(new Degree("105", 2));
        this.degrees.add(new Degree("120", 2));
        this.degrees.add(new Degree("NW", 3));
        this.degrees.add(new Degree("150", 2));
        this.degrees.add(new Degree("165", 2));
    }

    public void draw(ScaledResolution sr) {
        this.preRender(sr);
        float center = (float) (sr.getScaledWidth() / 2);
        int count = 0;
        int cardinals = 0;
        int subCardinals = 0;
        int markers = 0;
        float offset = 0.0F;
        float yaaahhrewindTime = CompassUtil.mc2.player.rotationYaw % 360.0F * 2.0F + 1080.0F;

        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtils.doGlScissor(sr.getScaledWidth() / 2 - 120, 25, 220, 25);

        Iterator iterator;
        Degree d;
        float location;
        float completeLocation;

        for (iterator = this.degrees.iterator(); iterator.hasNext(); ++count) {
            d = (Degree) iterator.next();
            location = center + (float) (count * 30) - yaaahhrewindTime;
            completeLocation = location - (float) (Fonts.fontSFUI35.getStringWidth(d.text) / 2);
            int opacity = this.opacity(sr, completeLocation);

            if (d.type == 1 && opacity != 16777215) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, 25.0F, this.opacity(sr, completeLocation), false);
                ++cardinals;
            }

            if (d.type == 2 && opacity != 16777215) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                RenderUtils.drawRect(location - 0.5F, 29.0F, location + 0.5F, 34.0F, this.opacity(sr, completeLocation));
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, 37.5F, this.opacity(sr, completeLocation), false);
                ++markers;
            }

            if (d.type == 3 && opacity != 16777215) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, (float) (25 + Fonts.fontSFUI35.getFontHeight() / 2 - Fonts.fontSFUI35.getFontHeight() / 2), this.opacity(sr, completeLocation), false);
                ++subCardinals;
            }
        }

        for (iterator = this.degrees.iterator(); iterator.hasNext(); ++count) {
            d = (Degree) iterator.next();
            location = center + (float) (count * 30) - yaaahhrewindTime;
            completeLocation = d.type == 1 ? location - (float) (Fonts.fontSFUI35.getStringWidth(d.text) / 2) : (d.type == 2 ? location - (float) (Fonts.fontSFUI35.getStringWidth(d.text) / 2) : location - (float) (Fonts.fontSFUI35.getStringWidth(d.text) / 2));
            if (d.type == 1) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, 25.0F, this.opacity(sr, completeLocation), false);
                ++cardinals;
            }

            if (d.type == 2) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                RenderUtils.drawRect(location - 0.5F, 29.0F, location + 0.5F, 34.0F, this.opacity(sr, completeLocation));
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, 37.5F, this.opacity(sr, completeLocation), false);
                ++markers;
            }

            if (d.type == 3) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, (float) (25 + Fonts.fontSFUI35.getFontHeight() / 2 - Fonts.fontSFUI35.getFontHeight() / 2), this.opacity(sr, completeLocation), false);
                ++subCardinals;
            }
        }

        for (iterator = this.degrees.iterator(); iterator.hasNext(); ++count) {
            d = (Degree) iterator.next();
            location = center + (float) (count * 30) - yaaahhrewindTime;
            completeLocation = d.type == 1 ? location - (float) (Fonts.fontSFUI35.getStringWidth(d.text) / 2) : (d.type == 2 ? location - (float) (Fonts.fontSFUI35.getStringWidth(d.text) / 2) : location - (float) (Fonts.fontSFUI35.getStringWidth(d.text) / 2));
            if (d.type == 1) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, 25.0F, this.opacity(sr, completeLocation), false);
                ++cardinals;
            }

            if (d.type == 2) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                RenderUtils.drawRect(location - 0.5F, 29.0F, location + 0.5F, 34.0F, this.opacity(sr, completeLocation));
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, 37.5F, this.opacity(sr, completeLocation), false);
                ++markers;
            }

            if (d.type == 3) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Fonts.fontSFUI35.drawString(d.text, completeLocation, (float) (25 + Fonts.fontSFUI35.getFontHeight() / 2 - Fonts.fontSFUI35.getFontHeight() / 2), this.opacity(sr, completeLocation), false);
                ++subCardinals;
            }
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public void preRender(ScaledResolution sr) {
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
    }

    public int opacity(ScaledResolution sr, float offset) {
        boolean op = false;
        float offs = 255.0F - Math.abs((float) (sr.getScaledWidth() / 2) - offset) * 1.8F;
        Color c = new Color(255, 255, 255, (int) Math.min(Math.max(0.0F, offs), 255.0F));

        return c.getRGB();
    }
}
