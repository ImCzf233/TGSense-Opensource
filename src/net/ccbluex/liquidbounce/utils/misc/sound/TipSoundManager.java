package net.ccbluex.liquidbounce.utils.misc.sound;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;", "", "()V", "disableSound", "Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundPlayer;", "getDisableSound", "()Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundPlayer;", "setDisableSound", "(Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundPlayer;)V", "enableSound", "getEnableSound", "setEnableSound", "LiquidBounce"}
)
public final class TipSoundManager {

    @NotNull
    private TipSoundPlayer enableSound;
    @NotNull
    private TipSoundPlayer disableSound;

    @NotNull
    public final TipSoundPlayer getEnableSound() {
        return this.enableSound;
    }

    public final void setEnableSound(@NotNull TipSoundPlayer <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.enableSound = <set-?>;
    }

    @NotNull
    public final TipSoundPlayer getDisableSound() {
        return this.disableSound;
    }

    public final void setDisableSound(@NotNull TipSoundPlayer <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.disableSound = <set-?>;
    }

    public TipSoundManager() {
        File enableSoundFile = new File(LiquidBounce.INSTANCE.getFileManager().soundsDir, "enable.wav");
        File disableSoundFile = new File(LiquidBounce.INSTANCE.getFileManager().soundsDir, "disable.wav");

        if (!enableSoundFile.exists()) {
            FileUtils.unpackFile(enableSoundFile, "assets/minecraft/langya/sound/enable.wav");
        }

        if (!disableSoundFile.exists()) {
            FileUtils.unpackFile(disableSoundFile, "assets/minecraft/langya/sound/disable.wav");
        }

        this.enableSound = new TipSoundPlayer(enableSoundFile);
        this.disableSound = new TipSoundPlayer(disableSoundFile);
    }
}
