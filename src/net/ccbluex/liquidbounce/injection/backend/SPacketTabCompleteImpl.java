package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketTabComplete;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketTabComplete;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SPacketTabCompleteImpl;", "T", "Lnet/minecraft/network/play/server/SPacketTabComplete;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketTabComplete;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketTabComplete;)V", "completions", "", "", "getCompletions", "()[Ljava/lang/String;", "LiquidBounce"}
)
public final class SPacketTabCompleteImpl extends PacketImpl implements ISPacketTabComplete {

    @NotNull
    public String[] getCompletions() {
        String[] astring = ((SPacketTabComplete) this.getWrapped()).getMatches();

        Intrinsics.checkExpressionValueIsNotNull(astring, "wrapped.matches");
        return astring;
    }

    public SPacketTabCompleteImpl(@NotNull SPacketTabComplete wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}
