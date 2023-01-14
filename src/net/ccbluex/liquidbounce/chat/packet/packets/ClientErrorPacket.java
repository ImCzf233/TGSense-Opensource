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
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientErrorPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidBounce"}
)
public final class ClientErrorPacket implements Packet {

    @SerializedName("message")
    @NotNull
    private final String message;

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    public ClientErrorPacket(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        super();
        this.message = message;
    }

    @NotNull
    public final String component1() {
        return this.message;
    }

    @NotNull
    public final ClientErrorPacket copy(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        return new ClientErrorPacket(message);
    }

    public static ClientErrorPacket copy$default(ClientErrorPacket clienterrorpacket, String s, int i, Object object) {
        if ((i & 1) != 0) {
            s = clienterrorpacket.message;
        }

        return clienterrorpacket.copy(s);
    }

    @NotNull
    public String toString() {
        return "ClientErrorPacket(message=" + this.message + ")";
    }

    public int hashCode() {
        return this.message != null ? this.message.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof ClientErrorPacket) {
                ClientErrorPacket clienterrorpacket = (ClientErrorPacket) object;

                if (Intrinsics.areEqual(this.message, clienterrorpacket.message)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
