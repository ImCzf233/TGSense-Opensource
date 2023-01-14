package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IAbstractClientPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/AbstractClientPlayerImpl;", "T", "Lnet/minecraft/client/entity/AbstractClientPlayer;", "Lnet/ccbluex/liquidbounce/injection/backend/EntityPlayerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IAbstractClientPlayer;", "wrapped", "(Lnet/minecraft/client/entity/AbstractClientPlayer;)V", "LiquidBounce"}
)
public class AbstractClientPlayerImpl extends EntityPlayerImpl implements IAbstractClientPlayer {

    public AbstractClientPlayerImpl(@NotNull AbstractClientPlayer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((EntityPlayer) wrapped);
    }
}
