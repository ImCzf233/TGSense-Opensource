package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityTNTPrimed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\n"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/EntityTNTPrimedImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/EntityImpl;", "Lnet/minecraft/entity/item/EntityTNTPrimed;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityTNTPrimed;", "wrapped", "(Lnet/minecraft/entity/item/EntityTNTPrimed;)V", "fuse", "", "getFuse", "()I", "LiquidBounce"}
)
public final class EntityTNTPrimedImpl extends EntityImpl implements IEntityTNTPrimed {

    public int getFuse() {
        return ((EntityTNTPrimed) this.getWrapped()).getFuse();
    }

    public EntityTNTPrimedImpl(@NotNull EntityTNTPrimed wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Entity) wrapped);
    }
}
