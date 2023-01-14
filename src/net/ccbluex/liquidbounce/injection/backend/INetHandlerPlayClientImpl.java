package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/INetHandlerPlayClientImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "wrapped", "Lnet/minecraft/client/network/NetHandlerPlayClient;", "(Lnet/minecraft/client/network/NetHandlerPlayClient;)V", "networkManager", "Lnet/ccbluex/liquidbounce/api/minecraft/INetworkManager;", "getNetworkManager", "()Lnet/ccbluex/liquidbounce/api/minecraft/INetworkManager;", "playerInfoMap", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/INetworkPlayerInfo;", "getPlayerInfoMap", "()Ljava/util/Collection;", "getWrapped", "()Lnet/minecraft/client/network/NetHandlerPlayClient;", "addToSendQueue", "", "packet", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "equals", "", "other", "", "getPlayerInfo", "uuid", "Ljava/util/UUID;", "LiquidBounce"}
)
public final class INetHandlerPlayClientImpl implements IINetHandlerPlayClient {

    @NotNull
    private final NetHandlerPlayClient wrapped;

    @NotNull
    public INetworkManager getNetworkManager() {
        NetworkManager networkmanager = this.wrapped.getNetworkManager();

        Intrinsics.checkExpressionValueIsNotNull(networkmanager, "wrapped.networkManager");
        NetworkManager $this$wrap$iv = networkmanager;
        boolean $i$f$wrap = false;

        return (INetworkManager) (new NetworkManagerImpl($this$wrap$iv));
    }

    @NotNull
    public Collection getPlayerInfoMap() {
        return (Collection) (new WrappedCollection(this.wrapped.getPlayerInfoMap(), (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    @Nullable
    public INetworkPlayerInfo getPlayerInfo(@NotNull UUID uuid) {
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        NetworkPlayerInfo networkplayerinfo = this.wrapped.getPlayerInfo(uuid);
        INetworkPlayerInfo inetworkplayerinfo;

        if (networkplayerinfo != null) {
            NetworkPlayerInfo $this$wrap$iv = networkplayerinfo;
            boolean $i$f$wrap = false;

            inetworkplayerinfo = (INetworkPlayerInfo) (new NetworkPlayerInfoImpl($this$wrap$iv));
        } else {
            inetworkplayerinfo = null;
        }

        return inetworkplayerinfo;
    }

    public void addToSendQueue(@NotNull IPacket packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        NetHandlerPlayClient nethandlerplayclient = this.wrapped;
        boolean $i$f$unwrap = false;
        Packet packet = ((PacketImpl) packet).getWrapped();

        nethandlerplayclient.sendPacket(packet);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof INetHandlerPlayClientImpl && Intrinsics.areEqual(((INetHandlerPlayClientImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final NetHandlerPlayClient getWrapped() {
        return this.wrapped;
    }

    public INetHandlerPlayClientImpl(@NotNull NetHandlerPlayClient wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
