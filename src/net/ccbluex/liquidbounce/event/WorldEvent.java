package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "worldClient", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;)V", "getWorldClient", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "LiquidBounce"}
)
public final class WorldEvent extends Event {

    @Nullable
    private final IWorldClient worldClient;

    @Nullable
    public final IWorldClient getWorldClient() {
        return this.worldClient;
    }

    public WorldEvent(@Nullable IWorldClient worldClient) {
        this.worldClient = worldClient;
    }
}
