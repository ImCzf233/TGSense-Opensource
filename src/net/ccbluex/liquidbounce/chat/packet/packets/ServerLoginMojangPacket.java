package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerLoginMojangPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "name", "", "uuid", "Ljava/util/UUID;", "allowMessages", "", "(Ljava/lang/String;Ljava/util/UUID;Z)V", "getAllowMessages", "()Z", "getName", "()Ljava/lang/String;", "getUuid", "()Ljava/util/UUID;", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "", "toString", "LiquidBounce"}
)
public final class ServerLoginMojangPacket implements Packet {

    @SerializedName("name")
    @NotNull
    private final String name;
    @SerializedName("uuid")
    @NotNull
    private final UUID uuid;
    @SerializedName("allow_messages")
    private final boolean allowMessages;

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    public final boolean getAllowMessages() {
        return this.allowMessages;
    }

    public ServerLoginMojangPacket(@NotNull String name, @NotNull UUID uuid, boolean allowMessages) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        super();
        this.name = name;
        this.uuid = uuid;
        this.allowMessages = allowMessages;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final UUID component2() {
        return this.uuid;
    }

    public final boolean component3() {
        return this.allowMessages;
    }

    @NotNull
    public final ServerLoginMojangPacket copy(@NotNull String name, @NotNull UUID uuid, boolean allowMessages) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        return new ServerLoginMojangPacket(name, uuid, allowMessages);
    }

    public static ServerLoginMojangPacket copy$default(ServerLoginMojangPacket serverloginmojangpacket, String s, UUID uuid, boolean flag, int i, Object object) {
        if ((i & 1) != 0) {
            s = serverloginmojangpacket.name;
        }

        if ((i & 2) != 0) {
            uuid = serverloginmojangpacket.uuid;
        }

        if ((i & 4) != 0) {
            flag = serverloginmojangpacket.allowMessages;
        }

        return serverloginmojangpacket.copy(s, uuid, flag);
    }

    @NotNull
    public String toString() {
        return "ServerLoginMojangPacket(name=" + this.name + ", uuid=" + this.uuid + ", allowMessages=" + this.allowMessages + ")";
    }

    public int hashCode() {
        int i = ((this.name != null ? this.name.hashCode() : 0) * 31 + (this.uuid != null ? this.uuid.hashCode() : 0)) * 31;
        byte b0 = this.allowMessages;

        if (this.allowMessages) {
            b0 = 1;
        }

        return i + b0;
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof ServerLoginMojangPacket) {
                ServerLoginMojangPacket serverloginmojangpacket = (ServerLoginMojangPacket) object;

                if (Intrinsics.areEqual(this.name, serverloginmojangpacket.name) && Intrinsics.areEqual(this.uuid, serverloginmojangpacket.uuid) && this.allowMessages == serverloginmojangpacket.allowMessages) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
