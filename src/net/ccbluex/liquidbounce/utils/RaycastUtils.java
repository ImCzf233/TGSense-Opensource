package net.ccbluex.liquidbounce.utils;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0007¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/RaycastUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "raycastEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "range", "", "yaw", "", "pitch", "entityFilter", "Lnet/ccbluex/liquidbounce/utils/RaycastUtils$EntityFilter;", "EntityFilter", "LiquidBounce"}
)
public final class RaycastUtils extends MinecraftInstance {

    public static final RaycastUtils INSTANCE;

    @JvmStatic
    @Nullable
    public static final IEntity raycastEntity(double range, @NotNull RaycastUtils.EntityFilter entityFilter) {
        Intrinsics.checkParameterIsNotNull(entityFilter, "entityFilter");
        return RaycastUtils.INSTANCE.raycastEntity(range, RotationUtils.serverRotation.getYaw(), RotationUtils.serverRotation.getPitch(), entityFilter);
    }

    private final IEntity raycastEntity(double range, float yaw, float pitch, RaycastUtils.EntityFilter entityFilter) {
        IEntity renderViewEntity = MinecraftInstance.mc.getRenderViewEntity();

        if (renderViewEntity != null && MinecraftInstance.mc.getTheWorld() != null) {
            double blockReachDistance = range;
            WVec3 eyePosition = renderViewEntity.getPositionEyes(1.0F);
            float yawSin = -yaw * 0.017453292F - (float) 3.141592653589793D;
            boolean pitchCos = false;
            float yawCos = (float) Math.cos((double) yawSin);
            float pitchCos1 = -yaw * 0.017453292F - (float) 3.141592653589793D;
            boolean pitchSin = false;

            yawSin = (float) Math.sin((double) pitchCos1);
            double pitchSin1 = (double) (-pitch) * (double) 0.017453292F;
            boolean vector = false;

            pitchCos1 = (float) (-Math.cos(pitchSin1));
            double entityLook = (double) (-pitch) * (double) 0.017453292F;
            boolean entityList = false;
            float pitchSin2 = (float) Math.sin(entityLook);
            WVec3 entityLook1 = new WVec3((double) (yawSin * pitchCos1), (double) pitchSin2, (double) (yawCos * pitchCos1));
            double pointedEntity = entityLook1.getXCoord() * range;
            double y$iv = entityLook1.getYCoord() * range;
            double z$iv = entityLook1.getZCoord() * range;
            boolean movingObjectPosition = false;
            WVec3 vector1 = new WVec3(eyePosition.getXCoord() + pointedEntity, eyePosition.getYCoord() + y$iv, eyePosition.getZCoord() + z$iv);
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            Collection entityList1 = iworldclient.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook1.getXCoord() * range, entityLook1.getYCoord() * range, entityLook1.getZCoord() * range).expand(1.0D, 1.0D, 1.0D), (Function1) null.INSTANCE);
            IEntity pointedEntity1 = (IEntity) null;
            Iterator y$iv1 = entityList1.iterator();

            while (y$iv1.hasNext()) {
                IEntity entity = (IEntity) y$iv1.next();

                if (entityFilter.canRaycast(entity)) {
                    double collisionBorderSize = (double) entity.getCollisionBorderSize();
                    IAxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                    IMovingObjectPosition movingObjectPosition1 = axisAlignedBB.calculateIntercept(eyePosition, vector1);

                    if (axisAlignedBB.isVecInside(eyePosition)) {
                        if (blockReachDistance >= 0.0D) {
                            pointedEntity1 = entity;
                            blockReachDistance = 0.0D;
                        }
                    } else if (movingObjectPosition1 != null) {
                        double eyeDistance = eyePosition.distanceTo(movingObjectPosition1.getHitVec());

                        if (eyeDistance < blockReachDistance || blockReachDistance == 0.0D) {
                            if (Intrinsics.areEqual(entity, renderViewEntity.getRidingEntity()) && !renderViewEntity.canRiderInteract()) {
                                if (blockReachDistance == 0.0D) {
                                    pointedEntity1 = entity;
                                }
                            } else {
                                pointedEntity1 = entity;
                                blockReachDistance = eyeDistance;
                            }
                        }
                    }
                }
            }

            return pointedEntity1;
        } else {
            return null;
        }
    }

    static {
        RaycastUtils raycastutils = new RaycastUtils();

        INSTANCE = raycastutils;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"},
        d2 = { "Lnet/ccbluex/liquidbounce/utils/RaycastUtils$EntityFilter;", "", "canRaycast", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "LiquidBounce"}
    )
    public interface EntityFilter {

        boolean canRaycast(@Nullable IEntity ientity);
    }
}
