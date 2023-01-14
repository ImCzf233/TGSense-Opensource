package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.chat.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientMessagePacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "id", "", "user", "Lnet/ccbluex/liquidbounce/chat/User;", "content", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/chat/User;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "getId", "getUser", "()Lnet/ccbluex/liquidbounce/chat/User;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidBounce"}
)
public final class ClientMessagePacket implements Packet {

    @SerializedName("author_id")
    @NotNull
    private final String id;
    @SerializedName("author_info")
    @NotNull
    private final User user;
    @SerializedName("content")
    @NotNull
    private final String content;

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final User getUser() {
        return this.user;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    public ClientMessagePacket(@NotNull String id, @NotNull User user, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull(user, "user");
        Intrinsics.checkParameterIsNotNull(content, "content");
        super();
        this.id = id;
        this.user = user;
        this.content = content;
    }

    @NotNull
    public final String component1() {
        return this.id;
    }

    @NotNull
    public final User component2() {
        return this.user;
    }

    @NotNull
    public final String component3() {
        return this.content;
    }

    @NotNull
    public final ClientMessagePacket copy(@NotNull String id, @NotNull User user, @NotNull String content) {
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull(user, "user");
        Intrinsics.checkParameterIsNotNull(content, "content");
        return new ClientMessagePacket(id, user, content);
    }

    public static ClientMessagePacket copy$default(ClientMessagePacket clientmessagepacket, String s, User user, String s1, int i, Object object) {
        if ((i & 1) != 0) {
            s = clientmessagepacket.id;
        }

        if ((i & 2) != 0) {
            user = clientmessagepacket.user;
        }

        if ((i & 4) != 0) {
            s1 = clientmessagepacket.content;
        }

        return clientmessagepacket.copy(s, user, s1);
    }

    @NotNull
    public String toString() {
        return "ClientMessagePacket(id=" + this.id + ", user=" + this.user + ", content=" + this.content + ")";
    }

    public int hashCode() {
        return ((this.id != null ? this.id.hashCode() : 0) * 31 + (this.user != null ? this.user.hashCode() : 0)) * 31 + (this.content != null ? this.content.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof ClientMessagePacket) {
                ClientMessagePacket clientmessagepacket = (ClientMessagePacket) object;

                if (Intrinsics.areEqual(this.id, clientmessagepacket.id) && Intrinsics.areEqual(this.user, clientmessagepacket.user) && Intrinsics.areEqual(this.content, clientmessagepacket.content)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
