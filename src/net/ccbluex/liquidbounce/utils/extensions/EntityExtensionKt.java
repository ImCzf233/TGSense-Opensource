package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0007"},
    d2 = { "hurtPercent", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getHurtPercent", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)F", "renderHurtTime", "getRenderHurtTime", "LiquidBounce"}
)
public final class EntityExtensionKt {

    public static final float getRenderHurtTime(@NotNull IEntityLivingBase $this$renderHurtTime) {
        Intrinsics.checkParameterIsNotNull($this$renderHurtTime, "$this$renderHurtTime");
        return (float) $this$renderHurtTime.getHurtTime() - ($this$renderHurtTime.getHurtTime() != 0 ? MinecraftInstance.mc.getTimer().getRenderPartialTicks() : 0.0F);
    }

    public static final float getHurtPercent(@NotNull IEntityLivingBase $this$hurtPercent) {
        Intrinsics.checkParameterIsNotNull($this$hurtPercent, "$this$hurtPercent");
        return getRenderHurtTime($this$hurtPercent) / (float) 10;
    }
}
