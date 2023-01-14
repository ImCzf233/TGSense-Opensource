package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.minecraft.client.renderer.RenderItem;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\u0086\b¨\u0006\u0004"},
    d2 = { "unwrap", "Lnet/minecraft/client/renderer/RenderItem;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/entity/IRenderItem;", "wrap", "LiquidBounce"}
)
public final class RenderItemImplKt {

    @NotNull
    public static final RenderItem unwrap(@NotNull IRenderItem $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        return ((RenderItemImpl) $this$unwrap).getWrapped();
    }

    @NotNull
    public static final IRenderItem wrap(@NotNull RenderItem $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        return (IRenderItem) (new RenderItemImpl($this$wrap));
    }
}
