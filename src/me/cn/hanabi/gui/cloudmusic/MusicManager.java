package me.cn.hanabi.gui.cloudmusic;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D.Double;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import me.cn.hanabi.gui.cloudmusic.api.CloudMusicAPI;
import me.cn.hanabi.gui.cloudmusic.impl.Lyric;
import me.cn.hanabi.gui.cloudmusic.impl.Track;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class MusicManager {

    public static MusicManager INSTANCE = new MusicManager();
    public static boolean showMsg = false;
    private final HashMap artsLocations = new HashMap();
    private final File musicFolder;
    private final File artPicFolder;
    public Track currentTrack = null;
    public ArrayList playlist = new ArrayList();
    public Thread loadingThread = null;
    public Thread analyzeThread = null;
    public float downloadProgress = 0.0F;
    public boolean repeat = false;
    public float cacheProgress = 0.0F;
    public float[] magnitudes;
    public float[] smoothMagnitudes;
    public Thread lyricAnalyzeThread = null;
    public boolean lyric = false;
    public boolean noUpdate = false;
    public CopyOnWriteArrayList lrc = new CopyOnWriteArrayList();
    public CopyOnWriteArrayList tlrc = new CopyOnWriteArrayList();
    public HashMap circleLocations = new HashMap();
    public String lrcCur = "_EMPTY_";
    public String tlrcCur = "_EMPTY_";
    public int lrcIndex = 0;
    public int tlrcIndex = 0;
    public File circleImage;
    private MediaPlayer mediaPlayer;

    public MusicManager() {
        Minecraft mc = Minecraft.getMinecraft();

        this.musicFolder = new File(mc.gameDir, ".cache/musicCache");
        this.artPicFolder = new File(mc.gameDir, ".cache/artCache");
        File cookie = new File(mc.gameDir, ".cache/cookies.txt");

        if (!this.artPicFolder.exists()) {
            this.artPicFolder.mkdirs();
        }

        if (!this.musicFolder.exists()) {
            this.musicFolder.mkdirs();
        }

        this.circleImage = new File(Minecraft.getMinecraft().gameDir.toString() + File.separator + "TGSense" + File.separator + "circleImage");
        if (!this.circleImage.exists()) {
            this.circleImage.mkdirs();
        }

        if (cookie.exists()) {
            try {
                String[] ex = FileUtils.readFileToString(cookie).split(";");

                CloudMusicAPI.INSTANCE.cookies = new String[ex.length][2];

                for (int i = 0; i < ex.length; ++i) {
                    CloudMusicAPI.INSTANCE.cookies[i][0] = ex[i].split("=")[0];
                    CloudMusicAPI.INSTANCE.cookies[i][1] = ex[i].split("=")[1];
                }

                (new Thread(run<invokedynamic>())).start();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    public void loadFromCache(long id) {
        if (!this.artsLocations.containsKey(Long.valueOf(id))) {
            File path = new File(this.artPicFolder.getAbsolutePath() + File.separator + id);

            if (path.exists()) {
                (new Thread(run<invokedynamic>(this, id, path))).start();
            }
        }
    }

    public ResourceLocation getArt(long id) {
        return (ResourceLocation) this.artsLocations.get(Long.valueOf(id));
    }

    public void play(Track track) throws Exception {
        this.noUpdate = false;
        this.lrcIndex = 0;
        this.tlrcIndex = 0;
        if (this.currentTrack != null && this.currentTrack.id == track.id) {
            this.noUpdate = true;
        } else {
            this.lrc.clear();
            this.tlrc.clear();
            this.lrcCur = "等待歌词解析回应...";
            this.tlrcCur = "等待歌词解析回应...";
        }

        this.currentTrack = track;
        MusicManager.INSTANCE.loadFromCache(track.id);
        this.downloadProgress = 0.0F;
        if (!MusicManager.showMsg) {
            MusicManager.showMsg = true;
        }

        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
        }

        File mp3File = new File(this.musicFolder, track.id + ".mp3");
        File flacFile = new File(this.musicFolder, track.id + ".flac");
        File artFile = new File(this.artPicFolder, "" + track.id);

        if (!mp3File.exists() && !flacFile.exists()) {
            if (this.loadingThread != null) {
                this.loadingThread.interrupt();
            }

            this.loadingThread = new Thread(run<invokedynamic>(this, track, flacFile, mp3File, artFile));
            this.loadingThread.start();
        } else {
            Media hit = new Media(mp3File.exists() ? mp3File.toURI().toString() : flacFile.toURI().toString());

            this.mediaPlayer = new MediaPlayer(hit);
            this.mediaPlayer.setVolume(1.0D);
            this.mediaPlayer.setAutoPlay(true);
            this.mediaPlayer.setAudioSpectrumNumBands(128);
            this.mediaPlayer.setAudioSpectrumListener(spectrumDataUpdate<invokedynamic>(this));
            this.mediaPlayer.setOnEndOfMedia(run<invokedynamic>(this));
        }

        if (!this.noUpdate) {
            if (this.lyricAnalyzeThread != null) {
                this.lyricAnalyzeThread.interrupt();
            }

            this.lyricAnalyzeThread = new Thread(run<invokedynamic>(this, track));
            this.lyricAnalyzeThread.start();
        }

    }

    @EventTarget
    public void onTick(TickEvent evt) {
        if (this.getMediaPlayer() != null) {
            long mill = (long) this.getMediaPlayer().getCurrentTime().toMillis();

            if (!this.lrc.isEmpty() && ((Lyric) this.lrc.get(this.lrcIndex)).time < mill) {
                ++this.lrcIndex;
                this.lrcCur = ((Lyric) this.lrc.get(this.lrcIndex - 1)).text;
                if (this.tlrc.isEmpty()) {
                    this.tlrcCur = this.lrcIndex > this.lrc.size() - 1 ? "" : ((Lyric) this.lrc.get(this.lrcIndex)).text;
                }
            }

            if (!this.tlrc.isEmpty() && ((Lyric) this.tlrc.get(this.tlrcIndex)).time < mill) {
                ++this.tlrcIndex;
                this.tlrcCur = this.tlrcIndex - 1 > this.tlrc.size() - 1 ? "" : ((Lyric) this.tlrc.get(this.tlrcIndex - 1)).text;
            }
        }

    }

    public void getCircle(final Track track) {
        if (!this.circleLocations.containsKey(Long.valueOf(track.id))) {
            try {
                if (!(new File(this.circleImage.getAbsolutePath() + File.separator + track.id)).exists()) {
                    this.makeCirclePicture(track, 128, this.circleImage.getAbsolutePath() + File.separator + track.id);
                }

                final ResourceLocation ex = new ResourceLocation("circle/" + track.id);
                IImageBuffer iib2 = new IImageBuffer() {
                    public BufferedImage parseUserSkin(BufferedImage a) {
                        return a;
                    }

                    public void skinAvailable() {
                        MusicManager.this.circleLocations.put(Long.valueOf(track.id), ex);
                    }
                };
                ThreadDownloadImageData textureArt2 = new ThreadDownloadImageData(new File(this.circleImage.getAbsolutePath() + File.separator + track.id), (String) null, (ResourceLocation) null, iib2);

                Minecraft.getMinecraft().getTextureManager().loadTexture(ex, textureArt2);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    public void makeCirclePicture(Track track, int wid, String path) {
        try {
            BufferedImage ex = ImageIO.read(new URL(track.picUrl));
            BufferedImage formatAvatarImage = new BufferedImage(wid, wid, 6);
            Graphics2D graphics = formatAvatarImage.createGraphics();

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            byte os = 0;
            Double shape = new Double((double) os, (double) os, (double) (wid - os * 2), (double) (wid - os * 2));

            graphics.setClip(shape);
            graphics.drawImage(ex, os, os, wid - os * 2, wid - os * 2, (ImageObserver) null);
            graphics.dispose();

            try {
                FileOutputStream os1 = new FileOutputStream(path);
                Throwable shape1 = null;

                try {
                    ImageIO.write(formatAvatarImage, "png", os1);
                } catch (Throwable throwable) {
                    shape1 = throwable;
                    throw throwable;
                } finally {
                    if (os1 != null) {
                        if (shape1 != null) {
                            try {
                                os1.close();
                            } catch (Throwable throwable1) {
                                shape1.addSuppressed(throwable1);
                            }
                        } else {
                            os1.close();
                        }
                    }

                }
            } catch (Exception exception) {
                ;
            }
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }

    }

    public void downloadFile(String url, String filepath) {
        try {
            CloseableHttpClient e = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = e.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            File file = new File(filepath);
            FileOutputStream fileout = new FileOutputStream(file);
            byte[] buffer = new byte[10240];
            boolean ch = false;

            int ch1;

            while ((ch1 = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch1);
            }

            is.close();
            fileout.flush();
            fileout.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void next() {
        try {
            if (!this.playlist.isEmpty()) {
                if (this.currentTrack == null) {
                    this.play((Track) this.playlist.get(0));
                } else {
                    boolean e = false;
                    Iterator iterator = this.playlist.iterator();

                    while (iterator.hasNext()) {
                        Track t = (Track) iterator.next();

                        if (e) {
                            this.play(t);
                            break;
                        }

                        if (t.id == this.currentTrack.id) {
                            e = true;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void prev() {
        try {
            if (!this.playlist.isEmpty()) {
                if (this.currentTrack == null) {
                    this.play((Track) this.playlist.get(0));
                } else {
                    boolean e = false;

                    for (int i = 0; i < this.playlist.size(); ++i) {
                        Track t = (Track) this.playlist.get(i);

                        if (e) {
                            if (i - 2 < 0) {
                                this.play((Track) this.playlist.get(this.playlist.size() - 1));
                            } else {
                                this.play((Track) this.playlist.get(i - 2));
                            }
                            break;
                        }

                        if (t.id == this.currentTrack.id) {
                            e = true;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public Track getCurrentTrack() {
        return this.currentTrack;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public Thread getLoadingThread() {
        return this.loadingThread;
    }

    private void lambda$play$5(Track track) {
        try {
            String[] ex = CloudMusicAPI.INSTANCE.requestLyric(CloudMusicAPI.INSTANCE.getLyricJson(String.valueOf(track.id)));

            this.lrc.clear();
            this.tlrc.clear();
            if (!ex[0].equals("")) {
                if (ex[0].equals("_NOLYRIC_")) {
                    this.lrcCur = this.currentTrack.name;
                } else {
                    CloudMusicAPI.INSTANCE.analyzeLyric(this.lrc, ex[0]);
                }
            } else {
                this.lrcCur = "(解析时发生错误或歌词不存�?)";
                this.lrc.clear();
            }

            if (!ex[1].equals("")) {
                if (ex[1].equals("_NOLYRIC_")) {
                    this.tlrcCur = "纯音�?, 请欣�?";
                } else if (ex[1].equals("_UNCOLLECT_")) {
                    this.tlrcCur = "该歌曲暂无歌�?";
                } else {
                    CloudMusicAPI.INSTANCE.analyzeLyric(this.tlrc, ex[1]);
                }
            } else {
                this.tlrcCur = "(解析时发生错误或翻译歌词不存�?)";
                this.tlrc.clear();
            }
        } catch (Exception exception) {
            this.lrc.clear();
            this.tlrc.clear();
            this.lrcCur = this.currentTrack.name;
            this.tlrcCur = "(获取歌词时出现错�?)";
            exception.printStackTrace();
        }

    }

    private void lambda$play$4() {
        if (this.repeat) {
            try {
                this.play(this.currentTrack);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            this.next();
        }

    }

    private void lambda$play$3(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (this.magnitudes == null || this.magnitudes.length < magnitudes.length || this.magnitudes.length > magnitudes.length) {
            this.magnitudes = new float[magnitudes.length];
            this.smoothMagnitudes = new float[magnitudes.length];
        }

        for (int i = 0; i < magnitudes.length; ++i) {
            this.magnitudes[i] = magnitudes[i] - (float) this.mediaPlayer.getAudioSpectrumThreshold();
        }

    }

    private void lambda$play$2(Track track, File flacFile, File mp3File, File artFile) {
        try {
            String ex = (String) CloudMusicAPI.INSTANCE.getDownloadUrl(String.valueOf(track.id), 128000L)[1];

            CloudMusicAPI.INSTANCE.downloadFile(ex, ex.endsWith(".flac") ? flacFile.getAbsolutePath() : mp3File.getAbsolutePath());
            MusicManager.INSTANCE.downloadFile(track.picUrl, artFile.getAbsolutePath());
            this.play(track);
        } catch (Exception exception) {
            ClientUtils.displayChatMessage("缓存音乐时发生错�?, 可能是因为该歌曲已被下架或需要VIP!");
            if (mp3File.exists()) {
                mp3File.delete();
            }

            if (flacFile.exists()) {
                flacFile.delete();
            }

            exception.printStackTrace();
        }

        this.loadingThread = null;
    }

    private void lambda$loadFromCache$1(final long id, File path) {
        this.artsLocations.put(Long.valueOf(id), (Object) null);
        final ResourceLocation rl = new ResourceLocation("cloudMusicCache/" + id);
        IImageBuffer iib = new IImageBuffer() {
            final ImageBufferDownload ibd = new ImageBufferDownload();

            public BufferedImage parseUserSkin(BufferedImage image) {
                return image;
            }

            public void skinAvailable() {
                MusicManager.this.artsLocations.put(Long.valueOf(id), rl);
            }
        };
        ThreadDownloadImageData textureArt = new ThreadDownloadImageData(path, (String) null, (ResourceLocation) null, iib);

        Minecraft.getMinecraft().getTextureManager().loadTexture(rl, textureArt);
    }

    private static void lambda$new$0() {
        try {
            CloudMusicAPI.INSTANCE.refreshState();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
