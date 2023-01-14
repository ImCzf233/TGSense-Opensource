package me.cn.hanabi.gui.cloudmusic.ui;

import java.awt.Color;
import me.cn.RenderUtils;
import me.cn.hanabi.gui.cloudmusic.MusicManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.extensions.RendererExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public enum MusicOverlayRenderer {

    INSTANCE;

    public String downloadProgress = "0";
    public long readedSecs = 0L;
    public long totalSecs = 0L;
    public float animation = 0.0F;
    public MSTimer timer = new MSTimer();
    public boolean firstTime = true;

    public void renderOverlay() {
        byte addonX = 10;
        byte addonY = 60;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        if (MusicManager.INSTANCE.getCurrentTrack() != null && MusicManager.INSTANCE.getMediaPlayer() != null) {
            this.readedSecs = (long) ((int) MusicManager.INSTANCE.getMediaPlayer().getCurrentTime().toSeconds());
            this.totalSecs = (long) ((int) MusicManager.INSTANCE.getMediaPlayer().getStopTime().toSeconds());
        }

        if (MusicManager.INSTANCE.getCurrentTrack() != null && MusicManager.INSTANCE.getMediaPlayer() != null) {
            Fonts.com18.drawString(MusicManager.INSTANCE.getCurrentTrack().name + " - " + MusicManager.INSTANCE.getCurrentTrack().artists, 36.0F + (float) addonX, (float) (10 + addonY), Color.WHITE.getRGB());
            Fonts.com18.drawString(this.formatSeconds((int) this.readedSecs) + "/" + this.formatSeconds((int) this.totalSecs), 36.0F + (float) addonX, 20.0F + (float) addonY, -1);
            if (MusicManager.INSTANCE.circleLocations.containsKey(Long.valueOf(MusicManager.INSTANCE.getCurrentTrack().id))) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                IResourceLocation wqy = WrapperImpl.INSTANCE.getClassProvider().createResourceLocation(((ResourceLocation) MusicManager.INSTANCE.circleLocations.get(Long.valueOf(MusicManager.INSTANCE.getCurrentTrack().id))).toString());

                RenderUtils.drawImage(wqy, 4 + addonX, 6 + addonY, 28, 28);
                GL11.glPopMatrix();
            } else {
                MusicManager.INSTANCE.getCircle(MusicManager.INSTANCE.getCurrentTrack());
            }

            try {
                float wqy1 = (float) (MusicManager.INSTANCE.getMediaPlayer().getCurrentTime().toSeconds() / Math.max(1.0D, MusicManager.INSTANCE.getMediaPlayer().getStopTime().toSeconds())) * 100.0F;

                RenderUtils.drawArc((float) (18 + addonX), (float) (19 + addonY), 14.0D, Color.WHITE.getRGB(), 0, 360.0D, 4);
                RenderUtils.drawArc((float) (18 + addonX), (float) (19 + addonY), 14.0D, Color.BLUE.getRGB(), 180, (double) (180.0F + wqy1 * 3.6F), 4);
            } catch (Exception exception) {
                ;
            }
        }

        FontRenderer wqy2;

        if (MusicManager.INSTANCE.lyric) {
            wqy2 = Minecraft.getMinecraft().fontRenderer;
            byte sans = 50;
            int width1 = MusicManager.INSTANCE.tlrc.isEmpty() ? Color.GRAY.getRGB() : -16732281;

            GlStateManager.disableBlend();
            RendererExtensionKt.drawCenteredString(wqy2, MusicManager.INSTANCE.lrcCur.contains("_EMPTY_") ? "等待�?......." : MusicManager.INSTANCE.lrcCur, (float) sr.getScaledWidth() / 2.0F - 0.5F, (float) (sr.getScaledHeight() - 140 - 80 + sans), -16732281);
            RendererExtensionKt.drawCenteredString(wqy2, MusicManager.INSTANCE.tlrcCur.contains("_EMPTY_") ? "Waiting......." : MusicManager.INSTANCE.tlrcCur, (float) sr.getScaledWidth() / 2.0F, (float) (sr.getScaledHeight() - 125) + 0.5F - 80.0F + (float) sans, width1);
            GlStateManager.enableBlend();
        }

        if (MusicManager.showMsg) {
            if (this.firstTime) {
                this.timer.reset();
                this.firstTime = false;
            }

            wqy2 = Minecraft.getMinecraft().fontRenderer;
            FontRenderer sans1 = Minecraft.getMinecraft().fontRenderer;
            float width11 = (float) wqy2.getStringWidth(MusicManager.INSTANCE.getCurrentTrack().name);
            float width2 = (float) sans1.getStringWidth("Now playing");
            float allWidth = Math.max(Math.max(width11, width2), 150.0F);

            RenderUtils.drawRect((float) sr.getScaledWidth() - this.animation, 5.0F, (float) sr.getScaledWidth(), 40.0F, RenderUtils.reAlpha(Color.BLACK.getRGB(), 0.7F));
            if (MusicManager.INSTANCE.circleLocations.containsKey(Long.valueOf(MusicManager.INSTANCE.getCurrentTrack().id))) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                IResourceLocation icon = WrapperImpl.INSTANCE.getClassProvider().createResourceLocation(((ResourceLocation) MusicManager.INSTANCE.circleLocations.get(Long.valueOf(MusicManager.INSTANCE.getCurrentTrack().id))).toString());

                RenderUtils.drawImage(icon, (int) ((float) sr.getScaledWidth() - this.animation + 5.0F), 8, 28, 28);
                GL11.glPopMatrix();
            } else {
                MusicManager.INSTANCE.getCircle(MusicManager.INSTANCE.getCurrentTrack());
            }

            RenderUtils.drawArc((float) sr.getScaledWidth() - this.animation - 31.0F + 50.0F, 22.0F, 14.0D, Color.WHITE.getRGB(), 0, 360.0D, 2);
            sans1.drawString("Now playing", (int) ((float) sr.getScaledWidth() - this.animation - 12.0F + 50.0F), 8, Color.WHITE.getRGB());
            wqy2.drawString(MusicManager.INSTANCE.getCurrentTrack().name, (int) ((float) sr.getScaledWidth() - this.animation - 12.0F + 50.0F), 26, Color.WHITE.getRGB());
            if (this.timer.hasTimePassed(5000L)) {
                this.animation = (float) RenderUtils.getAnimationStateSmooth(0.0D, (double) this.animation, (double) (10.0F / (float) Minecraft.getDebugFPS()));
                if (this.animation <= 0.0F) {
                    MusicManager.showMsg = false;
                    this.firstTime = true;
                }
            } else {
                this.animation = (float) RenderUtils.getAnimationStateSmooth((double) allWidth, (double) this.animation, (double) (10.0F / (float) Minecraft.getDebugFPS()));
            }
        }

        GlStateManager.resetColor();
    }

    public String formatSeconds(int seconds) {
        String rstl = "";
        int mins = seconds / 60;

        if (mins < 10) {
            rstl = rstl + "0";
        }

        rstl = rstl + mins + ":";
        seconds %= 60;
        if (seconds < 10) {
            rstl = rstl + "0";
        }

        rstl = rstl + seconds;
        return rstl;
    }
}
