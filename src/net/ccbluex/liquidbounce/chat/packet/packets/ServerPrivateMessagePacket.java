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
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerPrivateMessagePacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "receiver", "", "content", "(Ljava/lang/String;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "getReceiver", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidBounce"}
)
public final class ServerPrivateMessagePacket implements Packet {

    @SerializedName("receiver")
    @NotNull
    private final String receiver;
    @SerializedName("content")
    @NotNull
    private final String content;

    @NotNull
    public final String getReceiver() {
        return this.receiver;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    public ServerPrivateMessagePacket(@NotNull String receiver, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(receiver, "receiver");
        Intrinsics.checkParameterIsNotNull(content, "content");
        super();
        this.receiver = receiver;
        this.content = content;
    }

    @NotNull
    public final String component1() {
        return this.receiver;
    }

    @NotNull
    public final String component2() {
        return this.content;
    }

    @NotNull
    public final ServerPrivateMessagePacket copy(@NotNull String receiver, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(receiver, "receiver");
        Intrinsics.checkParameterIsNotNull(content, "content");
        return new ServerPrivateMessagePacket(receiver, content);
    }

    public static ServerPrivateMessagePacket copy$default(ServerPrivateMessagePacket serverprivatemessagepacket, String s, String s1, int i, Object object) {
        if ((i & 1) != 0) {
            s = serverprivatemessagepacket.receiver;
        }

        if ((i & 2) != 0) {
            s1 = serverprivatemessagepacket.content;
        }

        return serverprivatemessagepacket.copy(s, s1);
    }

    @NotNull
    public String toString() {
        return "ServerPrivateMessagePacket(receiver=" + this.receiver + ", content=" + this.content + ")";
    }

    public int hashCode() {
        return (this.receiver != null ? this.receiver.hashCode() : 0) * 31 + (this.content != null ? this.content.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof ServerPrivateMessagePacket) {
                ServerPrivateMessagePacket serverprivatemessagepacket = (ServerPrivateMessagePacket) object;

                if (Intrinsics.areEqual(this.receiver, serverprivatemessagepacket.receiver) && Intrinsics.areEqual(this.content, serverprivatemessagepacket.content)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
