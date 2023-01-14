package me.cn.hanabi.gui.cloudmusic.ui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer.Status;
import javax.swing.SwingUtilities;
import me.cn.RenderUtils;
import me.cn.hanabi.gui.cloudmusic.MusicManager;
import me.cn.hanabi.gui.cloudmusic.api.CloudMusicAPI;
import me.cn.hanabi.gui.cloudmusic.impl.Track;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class MusicPlayerUI extends GuiScreen {

    public float x = 10.0F;
    public float y = 10.0F;
    public float x2 = 0.0F;
    public float y2 = 0.0F;
    public boolean drag = false;
    public CopyOnWriteArrayList slots = new CopyOnWriteArrayList();
    public float width = 150.0F;
    public float height = 250.0F;
    public boolean extended = false;
    public float sidebarAnimation = 0.0F;
    public float scrollY = 0.0F;
    public float scrollAni = 0.0F;
    public float minY = -100.0F;
    public CustomTextField textField = new CustomTextField("");

    public void initGui() {
        SwingUtilities.invokeLater(JFXPanel::<init>);
        Keyboard.enableRepeatEvents(true);
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.sidebarAnimation = 200.0F;
        float progress;

        if (Math.ceil((double) this.sidebarAnimation) > 1.0D) {
            progress = this.x + this.sidebarAnimation;
            float songName = this.x + this.width + this.sidebarAnimation;

            RenderUtils.drawRoundedRect(progress, this.y, songName, this.y + this.height, 2.0F, -13684426);
            this.textField.draw(progress + 6.0F, this.y + 2.0F);
            RenderUtils.drawRoundedRect(songName - 26.0F, this.y + 5.0F, songName - 7.0F, this.y + 17.0F, 2.0F, !RenderUtils.isHovering(mouseX, mouseY, songName - 26.0F, this.y + 5.0F, songName - 7.0F, this.y + 17.0F) && MusicManager.INSTANCE.analyzeThread == null ? -13355204 : (new Color(80, 80, 80)).getRGB());
            Minecraft.getMinecraft().fontRenderer.drawString("导入", (int) (songName - 23.0F), (int) (this.y + 6.0F), Color.GRAY.getRGB());
            if (this.textField.textString.isEmpty()) {
                Minecraft.getMinecraft().fontRenderer.drawString("输入歌单ID", (int) (progress + 8.0F), (int) (this.y + 6.0F), Color.GRAY.getRGB());
            }

            if (RenderUtils.isHovering(mouseX, mouseY, progress + 5.0F, this.y + 20.0F, songName - 5.0F, this.y + this.height - 4.0F)) {
                int songArtist = Mouse.getDWheel() / 2;

                this.scrollY += (float) songArtist;
                if (this.scrollY <= this.minY) {
                    this.scrollY = this.minY;
                }

                if (this.scrollY >= 0.0F) {
                    this.scrollY = 0.0F;
                }

                this.minY = this.height - 24.0F;
            } else {
                Mouse.getDWheel();
            }

            this.scrollAni = RenderUtils.getAnimationState(this.scrollAni, this.scrollY, Math.max(10.0F, Math.abs(this.scrollAni - this.scrollY) * 50.0F) * 0.3F);
            float songArtist1 = this.y + 21.0F + this.scrollAni;
            float icon = 0.0F;

            GL11.glEnable(3089);
            GL11.glScissor((int) (progress + 6.0F), (int) (this.y + 21.0F), 137, 224);

            for (Iterator viewable = this.slots.iterator(); viewable.hasNext(); icon += 22.0F) {
                TrackSlot progress1 = (TrackSlot) viewable.next();

                if (songArtist1 > this.y && songArtist1 < this.y + this.height - 4.0F) {
                    progress1.render(progress + 6.0F, songArtist1, mouseX, mouseY);
                }

                songArtist1 += 22.0F;
            }

            GL11.glDisable(3089);
            if (RenderUtils.isHovering(mouseX, mouseY, progress + 5.0F, this.y + 20.0F, songName - 5.0F, this.y + this.height - 4.0F)) {
                this.minY -= icon;
            }

            if (this.slots.size() > 10) {
                float viewable1 = 224.0F;
                float progress2 = MathHelper.clamp(-this.scrollAni / -this.minY, 0.0F, 1.0F);
                float ratio = viewable1 / icon * viewable1;
                float barHeight = Math.max(ratio, 20.0F);
                float position = progress2 * (viewable1 - barHeight);

                RenderUtils.drawRect(songName - 5.0F, this.y + 21.0F, songName - 2.0F, this.y + 245.0F, (new Color(100, 100, 100)).getRGB());
                RenderUtils.drawRect(songName - 5.0F, this.y + 21.0F + position, songName - 2.0F, this.y + 21.0F + position + barHeight, (new Color(73, 73, 73)).getRGB());
            }
        } else {
            Mouse.getDWheel();
        }

        RenderUtils.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.height, 2.0F, -13684426);
        RenderUtils.drawRoundedRect(this.x, this.y + this.height - 60.0F, this.x + this.width, this.y + this.height, 2.0F, -13355204);
        RenderUtils.drawRect(this.x, this.y + this.height - 60.0F, this.x + this.width, this.y + this.height - 58.0F, -13355204);
        Minecraft.getMinecraft().fontRenderer.drawString("网易云音�?", (int) (this.x + this.width / 2.0F - (float) Minecraft.getMinecraft().fontRenderer.getStringWidth("网易云音�?") / 2.0F - 2.0F), (int) (this.y + 5.0F), -1);
        progress = 0.0F;
        if (MusicManager.INSTANCE.getMediaPlayer() != null) {
            progress = (float) MusicManager.INSTANCE.getMediaPlayer().getCurrentTime().toSeconds() / (float) MusicManager.INSTANCE.getMediaPlayer().getStopTime().toSeconds() * 100.0F;
        }

        RenderUtils.drawRoundedRect(this.x + 10.0F, this.y + this.height - 50.0F, this.x + this.width - 10.0F, this.y + this.height - 46.0F, 1.4F, Color.GRAY.getRGB());
        if (MusicManager.INSTANCE.loadingThread != null) {
            RenderUtils.drawRoundedRect(this.x + 10.0F, this.y + this.height - 50.0F, this.x + 10.0F + 1.3F * MusicManager.INSTANCE.downloadProgress, this.y + this.height - 46.0F, 1.4F, Color.WHITE.getRGB());
            RenderUtils.drawCircle2(this.x + 10.0F + 1.3F * MusicManager.INSTANCE.downloadProgress, this.y + this.height - 48.0F, 3.0F, (new Color(255, 255, 255)).getRGB());
            RenderUtils.drawCircle2(this.x + 10.0F + 1.3F * MusicManager.INSTANCE.downloadProgress, this.y + this.height - 48.0F, 2.0F, (new Color(255, 50, 50, 255)).getRGB());
        } else {
            RenderUtils.drawRoundedRect(this.x + 10.0F, this.y + this.height - 50.0F, this.x + 10.0F + 1.3F * progress, this.y + this.height - 46.0F, 1.4F, Color.WHITE.getRGB());
            RenderUtils.drawCircle2(this.x + 10.0F + 1.3F * progress, this.y + this.height - 48.0F, 3.0F, (new Color(255, 255, 255)).getRGB());
            RenderUtils.drawCircle2(this.x + 10.0F + 1.3F * progress, this.y + this.height - 48.0F, 2.0F, (new Color(50, 176, 255, 255)).getRGB());
        }

        RenderUtils.drawCircle2(this.x + this.width / 2.0F, this.y + this.height - 24.0F, 12.0F, -12565429);
        int i;

        if (this.extended) {
            i = (int) (this.x + this.width - 15.0F);
            Minecraft.getMinecraft().fontRenderer.drawString(" · ", i, (int) (this.y + 5.5F), Color.WHITE.getRGB());
        } else {
            i = (int) (this.x + this.width - 15.0F);
            Minecraft.getMinecraft().fontRenderer.drawString("···", i, (int) (this.y + 5.5F), Color.WHITE.getRGB());
        }

        i = (int) (this.x + 5.0F);
        Minecraft.getMinecraft().fontRenderer.drawString("QR", i, (int) (this.y + 5.5F), Color.WHITE.getRGB());
        String songName1 = MusicManager.INSTANCE.currentTrack == null ? "当前未在播放" : MusicManager.INSTANCE.currentTrack.name;
        String songArtist2 = MusicManager.INSTANCE.currentTrack == null ? "N/A" : MusicManager.INSTANCE.currentTrack.artists;

        GL11.glEnable(3089);
        GL11.glScissor((int) this.x, (int) this.y + (int) (this.height / 2.0F - 95.0F), (int) this.width, 25);
        Minecraft.getMinecraft().fontRenderer.drawString(songName1, (int) (this.x + this.width / 2.0F - (float) (Minecraft.getMinecraft().fontRenderer.getStringWidth(songName1) / 2) - 1.5F), (int) (this.y + (this.height / 2.0F - 95.0F)), -1);
        Minecraft.getMinecraft().fontRenderer.drawString(songArtist2, (int) (this.x + this.width / 2.0F - (float) (Minecraft.getMinecraft().fontRenderer.getStringWidth(songArtist2) / 2) - 1.5F), (int) (this.y + (this.height / 2.0F - 82.0F)), -1);
        GL11.glDisable(3089);
        if (MusicManager.INSTANCE.getMediaPlayer() != null) {
            if (MusicManager.INSTANCE.getMediaPlayer().getStatus() == Status.PLAYING) {
                Minecraft.getMinecraft().fontRenderer.drawString("| |", (int) (this.x + this.width / 2.0F - (float) (Minecraft.getMinecraft().fontRenderer.getStringWidth("K") / 2)), (int) (this.y + this.height - 25.5F), Color.WHITE.getRGB());
            } else {
                Minecraft.getMinecraft().fontRenderer.drawString("|>", (int) (this.x + this.width / 2.0F - (float) (Minecraft.getMinecraft().fontRenderer.getStringWidth("J") / 2)), (int) (this.y + this.height - 25.5F), Color.WHITE.getRGB());
            }
        } else {
            Minecraft.getMinecraft().fontRenderer.drawString("|>", (int) (this.x + this.width / 2.0F - (float) (Minecraft.getMinecraft().fontRenderer.getStringWidth("J") / 2)), (int) (this.y + this.height - 25.5F), Color.WHITE.getRGB());
        }

        Minecraft.getMinecraft().fontRenderer.drawString("�?", (int) (this.x + this.width / 2.0F - (float) (Minecraft.getMinecraft().fontRenderer.getStringWidth("L") / 2) - 30.0F), (int) (this.y + this.height - 25.5F), Color.WHITE.getRGB());
        Minecraft.getMinecraft().fontRenderer.drawString("�?", (int) (this.x + this.width / 2.0F - (float) (Minecraft.getMinecraft().fontRenderer.getStringWidth("M") / 2) + 27.5F), (int) (this.y + this.height - 25.5F), Color.WHITE.getRGB());
        if (MusicManager.INSTANCE.repeat) {
            i = (int) (this.x + this.width - 20.0F);
            Minecraft.getMinecraft().fontRenderer.drawString("�?", i, (int) (this.y + this.height - 25.5F), Color.WHITE.getRGB());
        } else {
            i = (int) (this.x + this.width - 20.0F);
            Minecraft.getMinecraft().fontRenderer.drawString("-", i, (int) (this.y + this.height - 25.5F), Color.WHITE.getRGB());
        }

        if (MusicManager.INSTANCE.lyric) {
            i = (int) (this.x + 19.0F);
            Minecraft.getMinecraft().fontRenderer.drawString("�?", i, (int) (this.y + this.height - 25.5F), -1);
        } else {
            i = (int) (this.x + 19.0F);
            Minecraft.getMinecraft().fontRenderer.drawString("�?", i, (int) (this.y + this.height - 25.5F), -9736591);
        }

        if (MusicManager.INSTANCE.currentTrack != null && MusicManager.INSTANCE.getArt(MusicManager.INSTANCE.currentTrack.id) != null) {
            GL11.glPushMatrix();
            IResourceLocation icon1 = WrapperImpl.INSTANCE.getClassProvider().createResourceLocation(MusicManager.INSTANCE.getArt(MusicManager.INSTANCE.currentTrack.id).toString());

            RenderUtils.drawImage(icon1, (int) (this.x + this.width / 2.0F - 50.0F), (int) (this.y + (this.height / 2.0F - 10.0F) - 50.0F), 100, 100);
            GL11.glPopMatrix();
        }

        this.dragWindow(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (RenderUtils.isHovering(mouseX, mouseY, this.x + this.width - 15.0F, this.y + 5.0F, this.x + this.width - 5.0F, this.y + 15.0F) && mouseButton == 0) {
            this.extended = !this.extended;
        }

        if (mouseButton == 0) {
            if (RenderUtils.isHovering(mouseX, mouseY, this.x + this.width / 2.0F - 12.0F, this.y + this.height - 36.0F, this.x + this.width / 2.0F + 12.0F, this.y + this.height - 12.0F) && !MusicManager.INSTANCE.playlist.isEmpty()) {
                if (MusicManager.INSTANCE.currentTrack == null) {
                    try {
                        MusicManager.INSTANCE.play((Track) MusicManager.INSTANCE.playlist.get(0));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else if (MusicManager.INSTANCE.getMediaPlayer() != null) {
                    if (MusicManager.INSTANCE.getMediaPlayer().getStatus() == Status.PLAYING) {
                        MusicManager.INSTANCE.getMediaPlayer().pause();
                    } else {
                        MusicManager.INSTANCE.getMediaPlayer().play();
                    }
                }
            }

            if (RenderUtils.isHovering(mouseX, mouseY, this.x + 39.0F, this.y + this.height - 32.0F, this.x + 55.0F, this.y + this.height - 16.0F)) {
                MusicManager.INSTANCE.prev();
            }

            if (RenderUtils.isHovering(mouseX, mouseY, this.x + 96.0F, this.y + this.height - 32.0F, this.x + 112.0F, this.y + this.height - 16.0F)) {
                MusicManager.INSTANCE.next();
            }

            if (RenderUtils.isHovering(mouseX, mouseY, this.x + 10.0F, this.y + this.height - 29.0F, this.x + 20.0F, this.y + this.height - 19.0F)) {
                MusicManager.INSTANCE.lyric = !MusicManager.INSTANCE.lyric;
            }

            if (RenderUtils.isHovering(mouseX, mouseY, this.x + this.width - 20.0F, this.y + this.height - 29.0F, this.x + this.width - 10.0F, this.y + this.height - 19.0F)) {
                MusicManager.INSTANCE.repeat = !MusicManager.INSTANCE.repeat;
            }
        }

        if (this.extended && Math.ceil((double) this.sidebarAnimation) >= (double) (this.width + 5.0F)) {
            float newX = this.x + this.sidebarAnimation;
            float newWidth = this.x + this.width + this.sidebarAnimation;

            if (mouseButton == 0 && RenderUtils.isHovering(mouseX, mouseY, newWidth - 26.0F, this.y + 5.0F, newWidth - 7.0F, this.y + 17.0F) && !this.textField.textString.isEmpty() && MusicManager.INSTANCE.analyzeThread == null) {
                MusicManager.INSTANCE.analyzeThread = new Thread(() -> {
                    try {
                        this.slots.clear();
                        MusicManager.INSTANCE.playlist = (ArrayList) CloudMusicAPI.INSTANCE.getPlaylistDetail(this.textField.textString)[1];
                        Iterator ex = MusicManager.INSTANCE.playlist.iterator();

                        while (ex.hasNext()) {
                            Track t = (Track) ex.next();

                            this.slots.add(new TrackSlot(t));
                        }
                    } catch (Exception exception) {
                        ClientUtils.displayChatMessage("解析歌单时发生错�?!");
                        exception.printStackTrace();
                    }

                    MusicManager.INSTANCE.analyzeThread = null;
                });
                MusicManager.INSTANCE.analyzeThread.start();
            }

            if (RenderUtils.isHovering(mouseX, mouseY, newX + 5.0F, this.y + 20.0F, newWidth - 5.0F, this.y + this.height - 4.0F)) {
                float startY = this.y + 21.0F + this.scrollAni;

                for (Iterator iterator = this.slots.iterator(); iterator.hasNext(); startY += 22.0F) {
                    TrackSlot s = (TrackSlot) iterator.next();

                    if (startY > this.y && startY < this.y + this.height - 4.0F) {
                        s.click(mouseX, mouseY, mouseButton);
                    }
                }
            }

            this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.extended) {
            this.textField.keyPressed(keyCode);
            this.textField.charTyped(typedChar);
        }

        super.keyTyped(typedChar, keyCode);
    }

    public void dragWindow(int mouseX, int mouseY) {
        if (!RenderUtils.isHovering(mouseX, mouseY, this.x + this.width - 15.0F, this.y + 5.0F, this.x + this.width - 5.0F, this.y + 15.0F)) {
            if (!Mouse.isButtonDown(0) && this.drag) {
                this.drag = false;
            }

            if (this.drag) {
                this.x = (float) mouseX - this.x2;
                this.y = (float) mouseY - this.y2;
            } else if (RenderUtils.isHovering(mouseX, mouseY, this.x, this.y, this.x + this.width, this.y + 20.0F) && Mouse.isButtonDown(0)) {
                this.drag = true;
                this.x2 = (float) mouseX - this.x;
                this.y2 = (float) mouseY - this.y;
            }

        }
    }

    public void updateScreen() {
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
