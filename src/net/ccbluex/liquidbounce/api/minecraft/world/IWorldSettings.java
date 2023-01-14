package net.ccbluex.liquidbounce.api.minecraft.world;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings;", "", "WGameType", "LiquidBounce"}
)
public interface IWorldSettings {    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"},
        d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "", "(Ljava/lang/String;I)V", "NOT_SET", "SURVIVAL", "CREATIVE", "ADVENTUR", "SPECTATOR", "LiquidBounce"}
    )
    public static enum WGameType {

        NOT_SET, SURVIVAL, CREATIVE, ADVENTUR, SPECTATOR;
    }
}
