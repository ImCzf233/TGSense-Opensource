package net.ccbluex.liquidbounce.api.minecraft;

import javax.crypto.SecretKey;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u001e\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH&¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/INetworkManager;", "", "enableEncryption", "", "secretKey", "Ljavax/crypto/SecretKey;", "sendPacket", "packet", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "any", "Lkotlin/Function0;", "LiquidBounce"}
)
public interface INetworkManager {

    void sendPacket(@NotNull IPacket ipacket);

    void enableEncryption(@NotNull SecretKey secretkey);

    void sendPacket(@NotNull IPacket ipacket, @NotNull Function0 function0);
}
