package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientMojangInfoPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "sessionHash", "", "(Ljava/lang/String;)V", "getSessionHash", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidBounce"}
)
public final class ClientMojangInfoPacket implements Packet {

    @SerializedName("session_hash")
    @NotNull
    private final String sessionHash;

    @NotNull
    public final String getSessionHash() {
        return this.sessionHash;
    }

    public ClientMojangInfoPacket(@NotNull String sessionHash) {
        Intrinsics.checkParameterIsNotNull(sessionHash, "sessionHash");
        super();
        this.sessionHash = sessionHash;
    }

    @NotNull
    public final String component1() {
        return this.sessionHash;
    }

    @NotNull
    public final ClientMojangInfoPacket copy(@NotNull String sessionHash) {
        Intrinsics.checkParameterIsNotNull(sessionHash, "sessionHash");
        return new ClientMojangInfoPacket(sessionHash);
    }

    public static ClientMojangInfoPacket copy$default(ClientMojangInfoPacket clientmojanginfopacket, String s, int i, Object object) {
        if ((i & 1) != 0) {
            s = clientmojanginfopacket.sessionHash;
        }

        return clientmojanginfopacket.copy(s);
    }

    @NotNull
    public String toString() {
        return "ClientMojangInfoPacket(sessionHash=" + this.sessionHash + ")";
    }

    public int hashCode() {
        return this.sessionHash != null ? this.sessionHash.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof ClientMojangInfoPacket) {
                ClientMojangInfoPacket clientmojanginfopacket = (ClientMojangInfoPacket) object;

                if (Intrinsics.areEqual(this.sessionHash, clientmojanginfopacket.sessionHash)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
