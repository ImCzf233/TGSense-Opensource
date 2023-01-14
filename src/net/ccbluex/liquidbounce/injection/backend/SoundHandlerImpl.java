package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.audio.ISoundHandler;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SoundHandlerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler;", "wrapped", "Lnet/minecraft/client/audio/SoundHandler;", "(Lnet/minecraft/client/audio/SoundHandler;)V", "getWrapped", "()Lnet/minecraft/client/audio/SoundHandler;", "equals", "", "other", "", "playSound", "", "name", "", "pitch", "", "LiquidBounce"}
)
public final class SoundHandlerImpl implements ISoundHandler {

    @NotNull
    private final SoundHandler wrapped;

    public void playSound(@NotNull String name, float pitch) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.wrapped.playSound((ISound) PositionedSoundRecord.getRecord(new SoundEvent(new ResourceLocation(name)), pitch, 1.0F));
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof SoundHandlerImpl && Intrinsics.areEqual(((SoundHandlerImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final SoundHandler getWrapped() {
        return this.wrapped;
    }

    public SoundHandlerImpl(@NotNull SoundHandler wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
