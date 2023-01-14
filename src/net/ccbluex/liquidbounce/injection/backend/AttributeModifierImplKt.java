package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.entity.ai.attributes.IAttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\u0086\b¨\u0006\u0004"},
    d2 = { "unwrap", "Lnet/minecraft/entity/ai/attributes/AttributeModifier;", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "wrap", "LiquidBounce"}
)
public final class AttributeModifierImplKt {

    @NotNull
    public static final AttributeModifier unwrap(@NotNull IAttributeModifier $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        return ((AttributeModifierImpl) $this$unwrap).getWrapped();
    }

    @NotNull
    public static final IAttributeModifier wrap(@NotNull AttributeModifier $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        return (IAttributeModifier) (new AttributeModifierImpl($this$wrap));
    }
}