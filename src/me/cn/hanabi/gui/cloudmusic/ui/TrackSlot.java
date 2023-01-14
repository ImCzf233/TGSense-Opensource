package me.cn.hanabi.gui.cloudmusic.ui;

import java.awt.Color;
import me.cn.RenderUtils;
import me.cn.hanabi.gui.cloudmusic.MusicManager;
import me.cn.hanabi.gui.cloudmusic.impl.Track;
import net.minecraft.client.Minecraft;

public class TrackSlot {

    public Track track;
    public float x;
    public float y;

    public TrackSlot(Track t) {
        this.track = t;
    }

    public void render(float a, float b, int mouseX, int mouseY) {
        this.x = a;
        this.y = b;
        RenderUtils.drawRoundedRect(this.x, this.y, this.x + 137.0F, this.y + 20.0F, 2.0F, -13355204);
        int i = (int) (this.x + 2.0F);

        Minecraft.getMinecraft().fontRenderer.drawString(this.track.name, i, (int) (this.y + 1.0F), Color.WHITE.getRGB());
        i = (int) (this.x + 2.0F);
        Minecraft.getMinecraft().fontRenderer.drawString(this.track.artists, i, (int) (this.y + 10.0F), Color.WHITE.getRGB());
        RenderUtils.drawRoundedRect(this.x + 122.0F, this.y, this.x + 137.0F, this.y + 20.0F, 2.0F, -13355204);
        i = (int) (this.x + 125.5F);
        Minecraft.getMinecraft().fontRenderer.drawString(">", i, (int) (this.y + 5.5F), Color.WHITE.getRGB());
    }

    public void click(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtils.isHovering(mouseX, mouseY, this.x + 125.0F, this.y + 5.0F, this.x + 135.0F, this.y + 15.0F) && mouseButton == 0) {
            try {
                MusicManager.INSTANCE.play(this.track);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
}
