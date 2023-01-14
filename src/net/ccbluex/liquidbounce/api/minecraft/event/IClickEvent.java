package net.ccbluex.liquidbounce.api.minecraft.event;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "", "WAction", "LiquidBounce"}
)
public interface IClickEvent {    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0003\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003¨\u0006\u0004"},
        d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent$WAction;", "", "(Ljava/lang/String;I)V", "OPEN_URL", "LiquidBounce"}
    )
    public static enum WAction {

        OPEN_URL;
    }
}
