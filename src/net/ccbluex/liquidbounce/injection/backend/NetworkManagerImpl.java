package net.ccbluex.liquidbounce.injection.backend;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import javax.crypto.SecretKey;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001e\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/NetworkManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/INetworkManager;", "wrapped", "Lnet/minecraft/network/NetworkManager;", "(Lnet/minecraft/network/NetworkManager;)V", "getWrapped", "()Lnet/minecraft/network/NetworkManager;", "enableEncryption", "", "secretKey", "Ljavax/crypto/SecretKey;", "equals", "", "other", "", "sendPacket", "packet", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "listener", "Lkotlin/Function0;", "LiquidBounce"}
)
public final class NetworkManagerImpl implements INetworkManager {

    @NotNull
    private final NetworkManager wrapped;

    public void sendPacket(@NotNull IPacket packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        NetworkManager networkmanager = this.wrapped;
        boolean $i$f$unwrap = false;
        Packet packet = ((PacketImpl) packet).getWrapped();

        networkmanager.sendPacket(packet);
    }

    public void sendPacket(@NotNull IPacket packet, @NotNull final Function0 listener) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        NetworkManager networkmanager = this.wrapped;
        boolean $i$f$unwrap = false;
        Packet packet = ((PacketImpl) packet).getWrapped();

        networkmanager.sendPacket(packet, (GenericFutureListener) (new GenericFutureListener() {
            public final void operationComplete(Future it) {
                listener.invoke();
            }
        }), new GenericFutureListener[0]);
    }

    public void enableEncryption(@NotNull SecretKey secretKey) {
        Intrinsics.checkParameterIsNotNull(secretKey, "secretKey");
        this.wrapped.enableEncryption(secretKey);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof NetworkManagerImpl && Intrinsics.areEqual(((NetworkManagerImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final NetworkManager getWrapped() {
        return this.wrapped;
    }

    public NetworkManagerImpl(@NotNull NetworkManager wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
