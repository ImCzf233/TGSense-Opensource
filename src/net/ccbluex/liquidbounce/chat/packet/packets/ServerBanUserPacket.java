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
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerBanUserPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "user", "", "(Ljava/lang/String;)V", "getUser", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidBounce"}
)
public final class ServerBanUserPacket implements Packet {

    @SerializedName("user")
    @NotNull
    private final String user;

    @NotNull
    public final String getUser() {
        return this.user;
    }

    public ServerBanUserPacket(@NotNull String user) {
        Intrinsics.checkParameterIsNotNull(user, "user");
        super();
        this.user = user;
    }

    @NotNull
    public final String component1() {
        return this.user;
    }

    @NotNull
    public final ServerBanUserPacket copy(@NotNull String user) {
        Intrinsics.checkParameterIsNotNull(user, "user");
        return new ServerBanUserPacket(user);
    }

    public static ServerBanUserPacket copy$default(ServerBanUserPacket serverbanuserpacket, String s, int i, Object object) {
        if ((i & 1) != 0) {
            s = serverbanuserpacket.user;
        }

        return serverbanuserpacket.copy(s);
    }

    @NotNull
    public String toString() {
        return "ServerBanUserPacket(user=" + this.user + ")";
    }

    public int hashCode() {
        return this.user != null ? this.user.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof ServerBanUserPacket) {
                ServerBanUserPacket serverbanuserpacket = (ServerBanUserPacket) object;

                if (Intrinsics.areEqual(this.user, serverbanuserpacket.user)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
