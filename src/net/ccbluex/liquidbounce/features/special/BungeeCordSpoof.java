package net.ccbluex.liquidbounce.features.special;

import java.util.Arrays;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u00012\u00020\u0002:\u0001\fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/special/BungeeCordSpoof;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "getRandomIpPart", "", "handleEvents", "", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "Companion", "LiquidBounce"}
)
public final class BungeeCordSpoof extends MinecraftInstance implements Listenable {

    private static final Random RANDOM = new Random();
    @JvmField
    public static boolean enabled;
    public static final BungeeCordSpoof.Companion Companion = new BungeeCordSpoof.Companion((DefaultConstructorMarker) null);

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (MinecraftInstance.classProvider.isCPacketHandshake(packet) && BungeeCordSpoof.enabled && packet.asCPacketHandshake().getRequestedState().isHandshake()) {
            ICPacketHandshake handshake = packet.asCPacketHandshake();
            StringBuilder stringbuilder = (new StringBuilder()).append(handshake.getIp()).append("\u0000");
            StringCompanionObject stringcompanionobject = StringCompanionObject.INSTANCE;
            String s = "%d.%d.%d.%d";
            Object[] aobject = new Object[] { this.getRandomIpPart(), this.getRandomIpPart(), this.getRandomIpPart(), this.getRandomIpPart()};
            StringBuilder stringbuilder1 = stringbuilder;
            boolean flag = false;
            String s1 = String.format(s, Arrays.copyOf(aobject, aobject.length));

            Intrinsics.checkExpressionValueIsNotNull(s1, "java.lang.String.format(format, *args)");
            String s2 = s1;

            handshake.setIp(stringbuilder1.append(s2).append("\u0000").append(StringsKt.replace$default(MinecraftInstance.mc.getSession().getPlayerId(), "-", "", false, 4, (Object) null)).toString());
        }

    }

    private final String getRandomIpPart() {
        return String.valueOf(BungeeCordSpoof.RANDOM.nextInt(256));
    }

    public boolean handleEvents() {
        return true;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/special/BungeeCordSpoof$Companion;", "", "()V", "RANDOM", "Ljava/util/Random;", "enabled", "", "LiquidBounce"}
    )
    public static final class Companion {

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
