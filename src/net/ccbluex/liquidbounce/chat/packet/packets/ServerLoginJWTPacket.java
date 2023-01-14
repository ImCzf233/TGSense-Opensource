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
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerLoginJWTPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "token", "", "allowMessages", "", "(Ljava/lang/String;Z)V", "getAllowMessages", "()Z", "getToken", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", "LiquidBounce"}
)
public final class ServerLoginJWTPacket implements Packet {

    @SerializedName("token")
    @NotNull
    private final String token;
    @SerializedName("allow_messages")
    private final boolean allowMessages;

    @NotNull
    public final String getToken() {
        return this.token;
    }

    public final boolean getAllowMessages() {
        return this.allowMessages;
    }

    public ServerLoginJWTPacket(@NotNull String token, boolean allowMessages) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        super();
        this.token = token;
        this.allowMessages = allowMessages;
    }

    @NotNull
    public final String component1() {
        return this.token;
    }

    public final boolean component2() {
        return this.allowMessages;
    }

    @NotNull
    public final ServerLoginJWTPacket copy(@NotNull String token, boolean allowMessages) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        return new ServerLoginJWTPacket(token, allowMessages);
    }

    public static ServerLoginJWTPacket copy$default(ServerLoginJWTPacket serverloginjwtpacket, String s, boolean flag, int i, Object object) {
        if ((i & 1) != 0) {
            s = serverloginjwtpacket.token;
        }

        if ((i & 2) != 0) {
            flag = serverloginjwtpacket.allowMessages;
        }

        return serverloginjwtpacket.copy(s, flag);
    }

    @NotNull
    public String toString() {
        return "ServerLoginJWTPacket(token=" + this.token + ", allowMessages=" + this.allowMessages + ")";
    }

    public int hashCode() {
        int i = (this.token != null ? this.token.hashCode() : 0) * 31;
        byte b0 = this.allowMessages;

        if (this.allowMessages) {
            b0 = 1;
        }

        return i + b0;
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof ServerLoginJWTPacket) {
                ServerLoginJWTPacket serverloginjwtpacket = (ServerLoginJWTPacket) object;

                if (Intrinsics.areEqual(this.token, serverloginjwtpacket.token) && this.allowMessages == serverloginjwtpacket.allowMessages) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
