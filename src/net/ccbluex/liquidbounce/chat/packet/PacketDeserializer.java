package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J$\u0010\t\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00062\u000e\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0007R:\u0010\u0004\u001a.\u0012\u0004\u0012\u00020\u0006\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00070\u0005j\u0016\u0012\u0004\u0012\u00020\u0006\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/packet/PacketDeserializer;", "Lcom/google/gson/JsonDeserializer;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "()V", "packetRegistry", "Ljava/util/HashMap;", "", "Ljava/lang/Class;", "Lkotlin/collections/HashMap;", "deserialize", "json", "Lcom/google/gson/JsonElement;", "typeOfT", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonDeserializationContext;", "registerPacket", "", "packetName", "packetClass", "LiquidBounce"}
)
public final class PacketDeserializer implements JsonDeserializer {

    private final HashMap packetRegistry = new HashMap();

    public final void registerPacket(@NotNull String packetName, @NotNull Class packetClass) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        Intrinsics.checkParameterIsNotNull(packetClass, "packetClass");
        ((Map) this.packetRegistry).put(packetName, packetClass);
    }

    @Nullable
    public Packet deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @Nullable JsonDeserializationContext context) {
        Intrinsics.checkParameterIsNotNull(json, "json");
        Intrinsics.checkParameterIsNotNull(typeOfT, "typeOfT");
        JsonObject packetObject = json.getAsJsonObject();
        JsonElement jsonelement = packetObject.get("m");

        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "packetObject.get(\"m\")");
        String packetName = jsonelement.getAsString();

        if (!this.packetRegistry.containsKey(packetName)) {
            return null;
        } else {
            if (!packetObject.has("c")) {
                packetObject.add("c", (JsonElement) (new JsonObject()));
            }

            return (Packet) (new Gson()).fromJson(packetObject.get("c"), (Class) this.packetRegistry.get(packetName));
        }
    }
}
