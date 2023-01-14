package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007\u001a\n\u0010\t\u001a\u00020\n*\u00020\u000b\u001a\n\u0010\f\u001a\u00020\r*\u00020\u0007\u001a\n\u0010\u000e\u001a\u00020\r*\u00020\u000b\u001a\n\u0010\u000f\u001a\u00020\r*\u00020\u0007\u001a$\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014\u001a\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0017¨\u0006\u0018"},
    d2 = { "getNearestPointBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "eye", "box", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "getDistanceToEntityBox", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "entity", "getPing", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "isAnimal", "", "isClientFriend", "isMob", "rayTraceWithCustomRotation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "blockReachDistance", "yaw", "", "pitch", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "LiquidBounce"}
)
public final class PlayerExtensionKt {

    public static final double getDistanceToEntityBox(@NotNull IEntity $this$getDistanceToEntityBox, @NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull($this$getDistanceToEntityBox, "$this$getDistanceToEntityBox");
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        WVec3 eyes = $this$getDistanceToEntityBox.getPositionEyes(1.0F);
        WVec3 pos = getNearestPointBB(eyes, entity.getEntityBoundingBox());
        double yDist = pos.getXCoord() - eyes.getXCoord();
        boolean zDist = false;
        double xDist = Math.abs(yDist);
        double zDist1 = pos.getYCoord() - eyes.getYCoord();
        boolean flag = false;

        yDist = Math.abs(zDist1);
        double d0 = pos.getZCoord() - eyes.getZCoord();
        boolean flag1 = false;

        zDist1 = Math.abs(d0);
        byte b0 = 2;
        boolean flag2 = false;
        double d1 = Math.pow(xDist, (double) b0);

        b0 = 2;
        double d2 = d1;

        flag2 = false;
        double d3 = Math.pow(yDist, (double) b0);

        d1 = d2 + d3;
        b0 = 2;
        d2 = d1;
        flag2 = false;
        d3 = Math.pow(zDist1, (double) b0);
        d0 = d2 + d3;
        flag1 = false;
        return Math.sqrt(d0);
    }

    @Nullable
    public static final IMovingObjectPosition rayTraceWithCustomRotation(@NotNull IEntity $this$rayTraceWithCustomRotation, double blockReachDistance, @NotNull Rotation rotation) {
        Intrinsics.checkParameterIsNotNull($this$rayTraceWithCustomRotation, "$this$rayTraceWithCustomRotation");
        Intrinsics.checkParameterIsNotNull(rotation, "rotation");
        return rayTraceWithCustomRotation($this$rayTraceWithCustomRotation, blockReachDistance, rotation.getYaw(), rotation.getPitch());
    }

    @Nullable
    public static final IMovingObjectPosition rayTraceWithCustomRotation(@NotNull IEntity $this$rayTraceWithCustomRotation, double blockReachDistance, float yaw, float pitch) {
        Intrinsics.checkParameterIsNotNull($this$rayTraceWithCustomRotation, "$this$rayTraceWithCustomRotation");
        WVec3 vec3 = $this$rayTraceWithCustomRotation.getPositionEyes(1.0F);
        WVec3 vec31 = ClientUtils.getVectorForRotation(pitch, yaw);
        double x$iv = vec31.getXCoord() * blockReachDistance;
        double y$iv = vec31.getYCoord() * blockReachDistance;
        double z$iv = vec31.getZCoord() * blockReachDistance;
        boolean $i$f$addVector = false;
        WVec3 vec32 = new WVec3(vec3.getXCoord() + x$iv, vec3.getYCoord() + y$iv, vec3.getZCoord() + z$iv);
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        return iworldclient.rayTraceBlocks(vec3, vec32, false, false, true);
    }

    @NotNull
    public static final WVec3 getNearestPointBB(@NotNull WVec3 eye, @NotNull IAxisAlignedBB box) {
        Intrinsics.checkParameterIsNotNull(eye, "eye");
        Intrinsics.checkParameterIsNotNull(box, "box");
        double[] origin = new double[] { eye.getXCoord(), eye.getYCoord(), eye.getZCoord()};
        double[] destMins = new double[] { box.getMinX(), box.getMinY(), box.getMinZ()};
        double[] destMaxs = new double[] { box.getMaxX(), box.getMaxY(), box.getMaxZ()};
        int i = 0;

        for (byte b0 = 2; i <= b0; ++i) {
            if (origin[i] > destMaxs[i]) {
                origin[i] = destMaxs[i];
            } else if (origin[i] < destMins[i]) {
                origin[i] = destMins[i];
            }
        }

        return new WVec3(origin[0], origin[1], origin[2]);
    }

    public static final int getPing(@NotNull IEntityPlayer $this$getPing) {
        Intrinsics.checkParameterIsNotNull($this$getPing, "$this$getPing");
        INetworkPlayerInfo playerInfo = MinecraftInstance.mc.getNetHandler().getPlayerInfo($this$getPing.getUniqueID());

        return playerInfo != null ? playerInfo.getResponseTime() : 0;
    }

    public static final boolean isAnimal(@NotNull IEntity $this$isAnimal) {
        Intrinsics.checkParameterIsNotNull($this$isAnimal, "$this$isAnimal");
        return MinecraftInstance.classProvider.isEntityAnimal($this$isAnimal) || MinecraftInstance.classProvider.isEntitySquid($this$isAnimal) || MinecraftInstance.classProvider.isEntityGolem($this$isAnimal) || MinecraftInstance.classProvider.isEntityBat($this$isAnimal);
    }

    public static final boolean isMob(@NotNull IEntity $this$isMob) {
        Intrinsics.checkParameterIsNotNull($this$isMob, "$this$isMob");
        return MinecraftInstance.classProvider.isEntityMob($this$isMob) || MinecraftInstance.classProvider.isEntityVillager($this$isMob) || MinecraftInstance.classProvider.isEntitySlime($this$isMob) || MinecraftInstance.classProvider.isEntityGhast($this$isMob) || MinecraftInstance.classProvider.isEntityDragon($this$isMob) || MinecraftInstance.classProvider.isEntityShulker($this$isMob);
    }

    public static final boolean isClientFriend(@NotNull IEntityPlayer $this$isClientFriend) {
        Intrinsics.checkParameterIsNotNull($this$isClientFriend, "$this$isClientFriend");
        String s = $this$isClientFriend.getName();

        if (s != null) {
            String entityName = s;

            return LiquidBounce.INSTANCE.getFileManager().friendsConfig.isFriend(ColorUtils.stripColor(entityName));
        } else {
            return false;
        }
    }
}
