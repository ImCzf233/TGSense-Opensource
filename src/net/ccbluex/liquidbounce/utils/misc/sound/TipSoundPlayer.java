package net.ccbluex.liquidbounce.utils.misc.sound;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TipSoundPlayer {

    private final File file;

    public TipSoundPlayer(File file) {
        this.file = file;
    }

    public void asyncPlay() {
        (new Thread(this::playSound)).start();
    }

    public void playSound() {
        try {
            AudioInputStream ex = AudioSystem.getAudioInputStream(this.file);
            Clip clip = AudioSystem.getClip();

            clip.open(ex);
            clip.start();
        } catch (Exception exception) {
            System.out.println("Error with playing sound.");
            exception.printStackTrace();
        }

    }
}
