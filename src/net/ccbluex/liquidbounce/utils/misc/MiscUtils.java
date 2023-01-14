package net.ccbluex.liquidbounce.utils.misc;

import java.awt.Component;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl.Type;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public final class MiscUtils extends MinecraftInstance {

    public static void showErrorPopup(String title, String message) {
        JOptionPane.showMessageDialog((Component) null, message, title, 0);
    }

    public static void showURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    public void playSound(MiscUtils.SoundType st, float volume) {
        (new Thread(run<invokedynamic>(this, st, volume))).start();
    }

    public static File openFileChooser() {
        if (MiscUtils.mc.isFullScreen()) {
            MiscUtils.mc.toggleFullscreen();
        }

        JFileChooser fileChooser = new JFileChooser();
        JFrame frame = new JFrame();

        fileChooser.setFileSelectionMode(0);
        frame.setVisible(true);
        frame.toFront();
        frame.setVisible(false);
        int action = fileChooser.showOpenDialog(frame);

        frame.dispose();
        return action == 0 ? fileChooser.getSelectedFile() : null;
    }

    public static File saveFileChooser() {
        if (MiscUtils.mc.isFullScreen()) {
            MiscUtils.mc.toggleFullscreen();
        }

        JFileChooser fileChooser = new JFileChooser();
        JFrame frame = new JFrame();

        fileChooser.setFileSelectionMode(0);
        frame.setVisible(true);
        frame.toFront();
        frame.setVisible(false);
        int action = fileChooser.showSaveDialog(frame);

        frame.dispose();
        return action == 0 ? fileChooser.getSelectedFile() : null;
    }

    private void lambda$playSound$0(MiscUtils.SoundType st, float volume) {
        try {
            AudioInputStream as = AudioSystem.getAudioInputStream(new BufferedInputStream((InputStream) Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/minecraft/sounds/" + st.getName()))));
            Clip e = AudioSystem.getClip();

            e.open(as);
            e.start();
            FloatControl gainControl = (FloatControl) e.getControl(Type.MASTER_GAIN);

            gainControl.setValue(volume);
            e.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException unsupportedaudiofileexception) {
            unsupportedaudiofileexception.printStackTrace();
        }

    }

    public static enum SoundType {

        MUSIC("music.wav"), VICTORY("victory.wav");

        final String music;

        private SoundType(String fileName) {
            this.music = fileName;
        }

        String getName() {
            return this.music;
        }
    }
}
