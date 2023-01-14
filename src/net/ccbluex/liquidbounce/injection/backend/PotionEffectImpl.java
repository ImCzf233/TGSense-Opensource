package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/PotionEffectImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "wrapped", "Lnet/minecraft/potion/PotionEffect;", "(Lnet/minecraft/potion/PotionEffect;)V", "amplifier", "", "getAmplifier", "()I", "duration", "getDuration", "potionID", "getPotionID", "getWrapped", "()Lnet/minecraft/potion/PotionEffect;", "equals", "", "other", "", "getDurationString", "", "LiquidBounce"}
)
public final class PotionEffectImpl implements IPotionEffect {

    @NotNull
    private final PotionEffect wrapped;

    @NotNull
    public String getDurationString() {
        String s = Potion.getPotionDurationString(this.wrapped, 1.0F);

        Intrinsics.checkExpressionValueIsNotNull(s, "Potion.getPotionDurationString(wrapped, 1.0f)");
        return s;
    }

    public int getAmplifier() {
        return this.wrapped.getAmplifier();
    }

    public int getDuration() {
        return this.wrapped.getDuration();
    }

    public int getPotionID() {
        return Potion.getIdFromPotion(this.wrapped.getPotion());
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof PotionEffectImpl && Intrinsics.areEqual(((PotionEffectImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final PotionEffect getWrapped() {
        return this.wrapped;
    }

    public PotionEffectImpl(@NotNull PotionEffect wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
