package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntity;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SPacketEntityImpl;", "T", "Lnet/minecraft/network/play/server/SPacketEntity;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntity;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketEntity;)V", "onGround", "", "getOnGround", "()Z", "getEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "LiquidBounce"}
)
public final class SPacketEntityImpl extends PacketImpl implements ISPacketEntity {

    public boolean getOnGround() {
        return ((SPacketEntity) this.getWrapped()).getOnGround();
    }

    @Nullable
    public IEntity getEntity(@NotNull IWorld world) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        SPacketEntity spacketentity = (SPacketEntity) this.getWrapped();
        boolean $i$f$wrap = false;
        World world = ((WorldImpl) world).getWrapped();
        Entity entity = spacketentity.getEntity(world);
        IEntity ientity;

        if (entity != null) {
            Entity $this$wrap$iv = entity;

            $i$f$wrap = false;
            ientity = (IEntity) (new EntityImpl($this$wrap$iv));
        } else {
            ientity = null;
        }

        return ientity;
    }

    public SPacketEntityImpl(@NotNull SPacketEntity wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}
