package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.network.IPacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/PacketBufferImpl;", "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "wrapped", "Lnet/minecraft/network/PacketBuffer;", "(Lnet/minecraft/network/PacketBuffer;)V", "getWrapped", "()Lnet/minecraft/network/PacketBuffer;", "equals", "", "other", "", "writeBytes", "", "payload", "", "writeItemStackToBuffer", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "writeString", "vanilla", "", "LiquidBounce"}
)
public final class PacketBufferImpl implements IPacketBuffer {

    @NotNull
    private final PacketBuffer wrapped;

    public void writeBytes(@NotNull byte[] payload) {
        Intrinsics.checkParameterIsNotNull(payload, "payload");
        this.wrapped.writeBytes(payload);
    }

    public void writeItemStackToBuffer(@NotNull IItemStack itemStack) {
        Intrinsics.checkParameterIsNotNull(itemStack, "itemStack");
        PacketBuffer packetbuffer = this.wrapped;
        boolean $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) itemStack).getWrapped();

        packetbuffer.writeItemStack(itemstack);
    }

    @NotNull
    public IPacketBuffer writeString(@NotNull String vanilla) {
        Intrinsics.checkParameterIsNotNull(vanilla, "vanilla");
        this.wrapped.writeString(vanilla);
        return (IPacketBuffer) this;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof PacketBufferImpl && Intrinsics.areEqual(((PacketBufferImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final PacketBuffer getWrapped() {
        return this.wrapped;
    }

    public PacketBufferImpl(@NotNull PacketBuffer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
