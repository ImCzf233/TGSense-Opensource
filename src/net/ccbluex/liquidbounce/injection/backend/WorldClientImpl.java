package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0016J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0014H\u0016J\b\u0010\u001d\u001a\u00020\u0012H\u0016J$\u0010\u001e\u001a\u00020\u001f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020\u0014H\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\n¨\u0006#"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/WorldClientImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/WorldImpl;", "Lnet/minecraft/client/multiplayer/WorldClient;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "wrapped", "(Lnet/minecraft/client/multiplayer/WorldClient;)V", "loadedEntityList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getLoadedEntityList", "()Ljava/util/Collection;", "loadedTileEntityList", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "getLoadedTileEntityList", "playerEntities", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "getPlayerEntities", "addEntityToWorld", "", "entityId", "", "fakePlayer", "removeEntity", "entity", "removeEntityFromWorld", "sendBlockBreakProgress", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "damage", "sendQuittingDisconnectingPacket", "setBlockState", "", "blockstate", "Lnet/minecraft/block/state/IBlockState;", "size", "LiquidBounce"}
)
public final class WorldClientImpl extends WorldImpl implements IWorldClient {

    @NotNull
    public Collection getPlayerEntities() {
        return (Collection) (new WrappedCollection((Collection) ((WorldClient) this.getWrapped()).playerEntities, (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    @NotNull
    public Collection getLoadedEntityList() {
        return (Collection) (new WrappedCollection((Collection) ((WorldClient) this.getWrapped()).loadedEntityList, (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    @NotNull
    public Collection getLoadedTileEntityList() {
        return (Collection) (new WrappedCollection((Collection) ((WorldClient) this.getWrapped()).loadedTileEntityList, (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    public void sendQuittingDisconnectingPacket() {
        ((WorldClient) this.getWrapped()).sendQuittingDisconnectingPacket();
    }

    public void sendBlockBreakProgress(int entityId, @NotNull WBlockPos blockPos, int damage) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        WorldClient worldclient = (WorldClient) this.getWrapped();
        boolean $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        worldclient.sendBlockBreakProgress(entityId, blockpos, damage);
    }

    public void addEntityToWorld(int entityId, @NotNull IEntity fakePlayer) {
        Intrinsics.checkParameterIsNotNull(fakePlayer, "fakePlayer");
        WorldClient worldclient = (WorldClient) this.getWrapped();
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) fakePlayer).getWrapped();

        worldclient.addEntityToWorld(entityId, entity);
    }

    public void removeEntityFromWorld(int entityId) {
        ((WorldClient) this.getWrapped()).removeEntityFromWorld(entityId);
    }

    public void removeEntity(@NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        WorldClient worldclient = (WorldClient) this.getWrapped();
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) entity).getWrapped();

        worldclient.removeEntity(entity);
    }

    public boolean setBlockState(@Nullable WBlockPos blockPos, @Nullable IBlockState blockstate, int size) {
        WorldClient worldclient = (WorldClient) this.getWrapped();
        BlockPos blockpos = new BlockPos;

        if (blockPos == null) {
            Intrinsics.throwNpe();
        }

        blockpos.<init>(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return worldclient.setBlockState(blockpos, blockstate, size);
    }

    public WorldClientImpl(@NotNull WorldClient wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((World) wrapped);
    }
}
