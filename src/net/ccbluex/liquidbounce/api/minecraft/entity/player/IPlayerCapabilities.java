package net.ccbluex.liquidbounce.api.minecraft.entity.player;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0005R\u0018\u0010\u0007\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\u0005\"\u0004\b\b\u0010\t¨\u0006\n"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IPlayerCapabilities;", "", "allowFlying", "", "getAllowFlying", "()Z", "isCreativeMode", "isFlying", "setFlying", "(Z)V", "LiquidBounce"}
)
public interface IPlayerCapabilities {

    boolean getAllowFlying();

    boolean isFlying();

    void setFlying(boolean flag);

    boolean isCreativeMode();
}
