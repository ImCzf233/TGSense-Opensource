package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/SerializedPacket;", "", "packetName", "", "packetContent", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;)V", "getPacketContent", "()Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "getPacketName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "LiquidBounce"}
)
public final class SerializedPacket {

    @SerializedName("m")
    @NotNull
    private final String packetName;
    @SerializedName("c")
    @Nullable
    private final Packet packetContent;

    @NotNull
    public final String getPacketName() {
        return this.packetName;
    }

    @Nullable
    public final Packet getPacketContent() {
        return this.packetContent;
    }

    public SerializedPacket(@NotNull String packetName, @Nullable Packet packetContent) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        super();
        this.packetName = packetName;
        this.packetContent = packetContent;
    }

    @NotNull
    public final String component1() {
        return this.packetName;
    }

    @Nullable
    public final Packet component2() {
        return this.packetContent;
    }

    @NotNull
    public final SerializedPacket copy(@NotNull String packetName, @Nullable Packet packetContent) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        return new SerializedPacket(packetName, packetContent);
    }

    public static SerializedPacket copy$default(SerializedPacket serializedpacket, String s, Packet packet, int i, Object object) {
        if ((i & 1) != 0) {
            s = serializedpacket.packetName;
        }

        if ((i & 2) != 0) {
            packet = serializedpacket.packetContent;
        }

        return serializedpacket.copy(s, packet);
    }

    @NotNull
    public String toString() {
        return "SerializedPacket(packetName=" + this.packetName + ", packetContent=" + this.packetContent + ")";
    }

    public int hashCode() {
        return (this.packetName != null ? this.packetName.hashCode() : 0) * 31 + (this.packetContent != null ? this.packetContent.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof SerializedPacket) {
                SerializedPacket serializedpacket = (SerializedPacket) object;

                if (Intrinsics.areEqual(this.packetName, serializedpacket.packetName) && Intrinsics.areEqual(this.packetContent, serializedpacket.packetContent)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
