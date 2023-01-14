package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u000e\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R:\u0010\u0004\u001a.\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/PacketSerializer;", "Lcom/google/gson/JsonSerializer;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "()V", "packetRegistry", "Ljava/util/HashMap;", "Ljava/lang/Class;", "", "Lkotlin/collections/HashMap;", "registerPacket", "", "packetName", "packetClass", "serialize", "Lcom/google/gson/JsonElement;", "src", "typeOfSrc", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonSerializationContext;", "LiquidBounce"}
)
public final class PacketSerializer implements JsonSerializer {

    private final HashMap packetRegistry = new HashMap();

    public final void registerPacket(@NotNull String packetName, @NotNull Class packetClass) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        Intrinsics.checkParameterIsNotNull(packetClass, "packetClass");
        ((Map) this.packetRegistry).put(packetClass, packetName);
    }

    @NotNull
    public JsonElement serialize(@NotNull Packet src, @NotNull Type typeOfSrc, @NotNull JsonSerializationContext context) {
        Intrinsics.checkParameterIsNotNull(src, "src");
        Intrinsics.checkParameterIsNotNull(typeOfSrc, "typeOfSrc");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Object object = this.packetRegistry.getOrDefault(src.getClass(), "UNKNOWN");

        Intrinsics.checkExpressionValueIsNotNull(object, "packetRegistry.getOrDefa…src.javaClass, \"UNKNOWN\")");
        String packetName = (String) object;
        Constructor[] aconstructor = src.getClass().getConstructors();

        Intrinsics.checkExpressionValueIsNotNull(aconstructor, "src.javaClass.constructors");
        Constructor[] $this$none$iv = aconstructor;
        boolean $i$f$none = false;
        Constructor[] aconstructor1 = $this$none$iv;
        int i = $this$none$iv.length;
        int j = 0;

        boolean flag;

        while (true) {
            if (j >= i) {
                flag = true;
                break;
            }

            Constructor element$iv = aconstructor1[j];
            boolean $i$a$-none-PacketSerializer$serialize$serializedPacket$1 = false;

            Intrinsics.checkExpressionValueIsNotNull(element$iv, "it");
            if (element$iv.getParameterCount() != 0) {
                flag = false;
                break;
            }

            ++j;
        }

        boolean flag1 = flag;
        Packet packet = flag1 ? null : src;
        SerializedPacket serializedPacket = new SerializedPacket(packetName, packet);
        JsonElement jsonelement = (new Gson()).toJsonTree(serializedPacket);

        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "Gson().toJsonTree(serializedPacket)");
        return jsonelement;
    }
}
