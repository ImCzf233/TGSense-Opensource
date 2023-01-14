package net.ccbluex.liquidbounce.utils.render;

import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public final class RotationUtils extends MinecraftInstance implements Listenable {

    private static final Random random = new Random();
    private static int keepLength;
    private static int revTick;
    public static Rotation targetRotation;
    public static Rotation serverRotation = new Rotation(0.0F, 0.0F);
    public static boolean keepCurrentRotation = false;
    private static double x = RotationUtils.random.nextDouble();
    private static double y = RotationUtils.random.nextDouble();
    private static double z = RotationUtils.random.nextDouble();

    public static Rotation OtherRotation(IAxisAlignedBB bb, WVec3 vec, boolean predict, boolean throughWalls, float distance) {
        WVec3 eyesPos = new WVec3(RotationUtils.mc.getThePlayer().getPosX(), RotationUtils.mc.getThePlayer().getEntityBoundingBox().getMinY() + (double) RotationUtils.mc.getThePlayer().getEyeHeight(), RotationUtils.mc.getThePlayer().getPosZ());
        WVec3 eyes = RotationUtils.mc.getThePlayer().getPositionEyes(1.0F);
        VecRotation vecRotation = null;

        double diffX;
        double diffY;
        double diffZ;

        for (diffX = 0.15D; diffX < 0.85D; diffX += 0.1D) {
            for (diffY = 0.15D; diffY < 1.0D; diffY += 0.1D) {
                for (diffZ = 0.15D; diffZ < 0.85D; diffZ += 0.1D) {
                    WVec3 vec3 = new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * diffX, bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * diffY, bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * diffZ);
                    Rotation rotation = toRotation(vec3, predict);
                    double vecDist = eyes.distanceTo(vec3);

                    if (vecDist <= (double) distance && (throughWalls || isVisible(vec3))) {
                        VecRotation currentVec = new VecRotation(vec3, rotation);

                        if (vecRotation == null) {
                            vecRotation = currentVec;
                        }
                    }
                }
            }
        }

        if (predict) {
            eyesPos.addVector(RotationUtils.mc.getThePlayer().getMotionX(), RotationUtils.mc.getThePlayer().getMotionY(), RotationUtils.mc.getThePlayer().getMotionZ());
        }

        diffX = vec.getXCoord() - eyesPos.getXCoord();
        diffY = vec.getYCoord() - eyesPos.getYCoord();
        diffZ = vec.getZCoord() - eyesPos.getZCoord();
        return new Rotation(WMathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), WMathHelper.wrapAngleTo180_float((float) (-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))));
    }

    public static float getYawToEntity(IEntityLivingBase entity) {
        IEntityPlayerSP player = RotationUtils.mc.getThePlayer();

        return getYawBetween(player.getRotationYaw(), player.getPosX(), player.getPosZ(), entity.getPosX(), entity.getPosZ());
    }

    public static float getYawBetween(float yaw, double srcX, double srcZ, double destX, double destZ) {
        double xDist = destX - srcX;
        double zDist = destZ - srcZ;
        float f = (float) (StrictMath.atan2(zDist, xDist) * 180.0D / 3.141592653589793D) - 90.0F;

        return yaw + MathHelper.wrapDegrees(f - yaw);
    }

    public static VecRotation lockView(IAxisAlignedBB bb, boolean outborder, boolean random, boolean predict, boolean throughWalls, float distance) {
        WVec3 randomVec;

        if (outborder) {
            randomVec = new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * (RotationUtils.x * 0.3D + 1.0D), bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * (RotationUtils.y * 0.3D + 1.0D), bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * (RotationUtils.z * 0.3D + 1.0D));
            return new VecRotation(randomVec, toRotation(randomVec, predict));
        } else {
            randomVec = new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * RotationUtils.x * 0.8D, bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * RotationUtils.y * 0.8D, bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * RotationUtils.z * 0.8D);
            Rotation randomRotation = toRotation(randomVec, predict);
            WVec3 eyes = RotationUtils.mc.getThePlayer().getPositionEyes(1.0F);
            double xMin = 0.0D;
            double yMin = 0.0D;
            double zMin = 0.0D;
            double xMax = 0.0D;
            double yMax = 0.0D;
            double zMax = 0.0D;
            double xDist = 0.0D;
            double yDist = 0.0D;
            double zDist = 0.0D;
            VecRotation vecRotation = null;

            xMin = 0.45D;
            xMax = 0.55D;
            xDist = 0.0125D;
            yMin = 0.65D;
            yMax = 0.75D;
            yDist = 0.0125D;
            zMin = 0.45D;
            zMax = 0.55D;
            zDist = 0.0125D;

            for (double xSearch = xMin; xSearch < xMax; xSearch += xDist) {
                for (double ySearch = yMin; ySearch < yMax; ySearch += yDist) {
                    for (double zSearch = zMin; zSearch < zMax; zSearch += zDist) {
                        WVec3 vec3 = new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * xSearch, bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * ySearch, bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * zSearch);
                        Rotation rotation = toRotation(vec3, predict);
                        double vecDist = eyes.distanceTo(vec3);

                        if (vecDist <= (double) distance && (throughWalls || isVisible(vec3))) {
                            VecRotation currentVec = new VecRotation(vec3, rotation);

                            if (vecRotation != null) {
                                if (random) {
                                    if (getRotationDifference(currentVec.getRotation(), randomRotation) >= getRotationDifference(vecRotation.getRotation(), randomRotation)) {
                                        continue;
                                    }
                                } else if (getRotationDifference(currentVec.getRotation()) >= getRotationDifference(vecRotation.getRotation())) {
                                    continue;
                                }
                            }

                            vecRotation = currentVec;
                        }
                    }
                }
            }

            return vecRotation;
        }
    }

    public static VecRotation calculateCenter(String calMode, String randMode, double randomRange, AxisAlignedBB bb, boolean predict, boolean throughWalls) {
        VecRotation vecRotation = null;
        double xMin = 0.0D;
        double yMin = 0.0D;
        double zMin = 0.0D;
        double xMax = 0.0D;
        double yMax = 0.0D;
        double zMax = 0.0D;
        double xDist = 0.0D;
        double yDist = 0.0D;
        double zDist = 0.0D;

        xMin = 0.15D;
        xMax = 0.85D;
        xDist = 0.1D;
        yMin = 0.15D;
        yMax = 1.0D;
        yDist = 0.1D;
        zMin = 0.15D;
        zMax = 0.85D;
        zDist = 0.1D;
        WVec3 curVec3 = null;
        byte b0 = -1;

        switch (calMode.hashCode()) {
        case -2140768690:
            if (calMode.equals("HalfUp")) {
                b0 = 2;
            }
            break;

        case -1367768316:
            if (calMode.equals("LiquidBounce")) {
                b0 = 0;
            }
            break;

        case -1139762679:
            if (calMode.equals("CenterLine")) {
                b0 = 5;
            }
            break;

        case 2201263:
            if (calMode.equals("Full")) {
                b0 = 1;
            }
            break;

        case 10120085:
            if (calMode.equals("HalfDown")) {
                b0 = 3;
            }
            break;

        case 105114247:
            if (calMode.equals("CenterSimple")) {
                b0 = 4;
            }
        }

        switch (b0) {
        case 0:
            xMin = 0.15D;
            xMax = 0.85D;
            xDist = 0.1D;
            yMin = 0.15D;
            yMax = 1.0D;
            yDist = 0.1D;
            zMin = 0.15D;
            zMax = 0.85D;
            zDist = 0.1D;
            break;

        case 1:
            xMin = 0.0D;
            xMax = 1.0D;
            xDist = 0.1D;
            yMin = 0.0D;
            yMax = 1.0D;
            yDist = 0.1D;
            zMin = 0.0D;
            zMax = 1.0D;
            zDist = 0.1D;
            break;

        case 2:
            xMin = 0.1D;
            xMax = 0.9D;
            xDist = 0.1D;
            yMin = 0.5D;
            yMax = 0.9D;
            yDist = 0.1D;
            zMin = 0.1D;
            zMax = 0.9D;
            zDist = 0.1D;
            break;

        case 3:
            xMin = 0.1D;
            xMax = 0.9D;
            xDist = 0.1D;
            yMin = 0.1D;
            yMax = 0.5D;
            yDist = 0.1D;
            zMin = 0.1D;
            zMax = 0.9D;
            zDist = 0.1D;
            break;

        case 4:
            xMin = 0.45D;
            xMax = 0.55D;
            xDist = 0.0125D;
            yMin = 0.65D;
            yMax = 0.75D;
            yDist = 0.0125D;
            zMin = 0.45D;
            zMax = 0.55D;
            zDist = 0.0125D;
            break;

        case 5:
            xMin = 0.45D;
            xMax = 0.55D;
            xDist = 0.0125D;
            yMin = 0.1D;
            yMax = 0.9D;
            yDist = 0.1D;
            zMin = 0.45D;
            zMax = 0.55D;
            zDist = 0.0125D;
        }

        double rand1;
        double rand2;
        double rand3;

        for (rand1 = xMin; rand1 < xMax; rand1 += xDist) {
            for (rand2 = yMin; rand2 < yMax; rand2 += yDist) {
                for (rand3 = zMin; rand3 < zMax; rand3 += zDist) {
                    WVec3 xRange = new WVec3(bb.minX + (bb.maxX - bb.minX) * rand1, bb.minY + (bb.maxY - bb.minY) * rand2, bb.minZ + (bb.maxZ - bb.minZ) * rand3);
                    Rotation rotation = toRotation(xRange, predict);

                    if (throughWalls || isVisible(xRange)) {
                        VecRotation yRange = new VecRotation(xRange, rotation);

                        if (vecRotation == null || getRotationDifference(yRange.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                            vecRotation = yRange;
                            curVec3 = xRange;
                        }
                    }
                }
            }
        }

        if (vecRotation != null && randMode != "Off") {
            rand1 = RotationUtils.random.nextDouble();
            rand2 = RotationUtils.random.nextDouble();
            rand3 = RotationUtils.random.nextDouble();
            double xRange1 = bb.maxX - bb.minX;
            double yRange1 = bb.maxY - bb.minY;
            double zRange = bb.maxZ - bb.minZ;
            double minRange = 999999.0D;

            if (xRange1 <= minRange) {
                minRange = xRange1;
            }

            if (yRange1 <= minRange) {
                minRange = yRange1;
            }

            if (zRange <= minRange) {
                minRange = zRange;
            }

            rand1 = rand1 * minRange * randomRange;
            rand2 = rand2 * minRange * randomRange;
            rand3 = rand3 * minRange * randomRange;
            double xPrecent = minRange * randomRange / xRange1;
            double yPrecent = minRange * randomRange / yRange1;
            double zPrecent = minRange * randomRange / zRange;
            WVec3 randomVec3 = new WVec3(curVec3.getXCoord() - xPrecent * (curVec3.getXCoord() - bb.minX) + rand1, curVec3.getYCoord() - yPrecent * (curVec3.getYCoord() - bb.minY) + rand2, curVec3.getZCoord() - zPrecent * (curVec3.getZCoord() - bb.minZ) + rand3);
            byte b1 = -1;

            switch (randMode.hashCode()) {
            case -1919497322:
                if (randMode.equals("Vertical")) {
                    b1 = 1;
                }
                break;

            case 109066982:
                if (randMode.equals("Horizonal")) {
                    b1 = 0;
                }
            }

            switch (b1) {
            case 0:
                randomVec3 = new WVec3(curVec3.getXCoord() - xPrecent * (curVec3.getXCoord() - bb.minX) + rand1, curVec3.getYCoord(), curVec3.getZCoord() - zPrecent * (curVec3.getZCoord() - bb.minZ) + rand3);
                break;

            case 1:
                randomVec3 = new WVec3(curVec3.getXCoord(), curVec3.getYCoord() - yPrecent * (curVec3.getYCoord() - bb.minY) + rand2, curVec3.getZCoord());
            }

            Rotation randomRotation = toRotation(randomVec3, predict);

            vecRotation = new VecRotation(randomVec3, randomRotation);
            return vecRotation;
        } else {
            return vecRotation;
        }
    }

    public static Rotation getNewRotations(WVec3 vec, boolean predict) {
        WVec3 eyesPos = new WVec3(RotationUtils.mc2.player.posX, RotationUtils.mc2.player.getEntityBoundingBox().minY + (double) RotationUtils.mc2.player.getEyeHeight(), RotationUtils.mc2.player.posZ);
        double diffX = vec.getXCoord() - eyesPos.getXCoord();
        double diffY = vec.getYCoord() - eyesPos.getYCoord();
        double diffZ = vec.getZCoord() - eyesPos.getZCoord();
        double dist = (double) MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);

        return new Rotation(yaw, pitch);
    }

    public static Rotation getNCPRotations(WVec3 vec, boolean predict) {
        WVec3 eyesPos = new WVec3(RotationUtils.mc2.player.posX, RotationUtils.mc2.player.getEntityBoundingBox().minY + (double) RotationUtils.mc2.player.getEyeHeight(), RotationUtils.mc2.player.posZ);

        if (predict) {
            eyesPos.addVector(RotationUtils.mc2.player.motionX, RotationUtils.mc2.player.motionY, RotationUtils.mc2.player.motionZ);
        }

        double diffX = vec.getXCoord() - eyesPos.getXCoord();
        double diffY = vec.getYCoord() - eyesPos.getYCoord();
        double diffZ = vec.getZCoord() - eyesPos.getZCoord();
        double hypotenuse = (double) MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        return new Rotation((float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F, (float) (-Math.atan2(diffY, hypotenuse) * 180.0D / 3.141592653589793D));
    }

    public static void setTargetRotationReverse(Rotation rotation, int keepLength, int revTick) {
        if (!Double.isNaN((double) rotation.getYaw()) && !Double.isNaN((double) rotation.getPitch()) && rotation.getPitch() <= 90.0F && rotation.getPitch() >= -90.0F) {
            rotation.fixedSensitivity(RotationUtils.mc2.gameSettings.mouseSensitivity);
            RotationUtils.targetRotation = rotation;
            RotationUtils.keepLength = keepLength;
            RotationUtils.revTick = revTick + 1;
        }
    }

    public static Rotation getRotationsNonLivingEntity(IEntity entity) {
        return getRotations(entity.getPosX(), entity.getPosY() + (entity.getEntityBoundingBox().getMaxY() - entity.getEntityBoundingBox().getMinY()) * 0.5D, entity.getPosZ());
    }

    public static Rotation getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = (EntityPlayerSP) RotationUtils.mc.getThePlayer();

        assert player != null;

        double x = posX - player.posX;
        double y = posY - (player.posY + (double) player.getEyeHeight());
        double z = posZ - player.posZ;
        double dist = (double) MathHelper.sqrt(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / 3.141592653589793D));

        return new Rotation(yaw, pitch);
    }

    public static VecRotation faceBlock(WBlockPos blockPos) {
        if (blockPos == null) {
            return null;
        } else {
            VecRotation vecRotation = null;

            for (double xSearch = 0.1D; xSearch < 0.9D; xSearch += 0.1D) {
                for (double ySearch = 0.1D; ySearch < 0.9D; ySearch += 0.1D) {
                    for (double zSearch = 0.1D; zSearch < 0.9D; zSearch += 0.1D) {
                        WVec3 eyesPos = new WVec3(RotationUtils.mc.getThePlayer().getPosX(), RotationUtils.mc.getThePlayer().getEntityBoundingBox().getMinY() + (double) RotationUtils.mc.getThePlayer().getEyeHeight(), RotationUtils.mc.getThePlayer().getPosZ());
                        WVec3 posVec = (new WVec3(blockPos)).addVector(xSearch, ySearch, zSearch);
                        double dist = eyesPos.distanceTo(posVec);
                        double diffX = posVec.getXCoord() - eyesPos.getXCoord();
                        double diffY = posVec.getYCoord() - eyesPos.getYCoord();
                        double diffZ = posVec.getZCoord() - eyesPos.getZCoord();
                        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
                        Rotation rotation = new Rotation(WMathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), WMathHelper.wrapAngleTo180_float((float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
                        WVec3 rotationVector = getVectorForRotation(rotation);
                        WVec3 vector = eyesPos.addVector(rotationVector.getXCoord() * dist, rotationVector.getYCoord() * dist, rotationVector.getZCoord() * dist);
                        IMovingObjectPosition obj = RotationUtils.mc.getTheWorld().rayTraceBlocks(eyesPos, vector, false, false, true);

                        if (obj != null && obj.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK) {
                            VecRotation currentVec = new VecRotation(posVec, rotation);

                            if (vecRotation == null || getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                                vecRotation = currentVec;
                            }
                        }
                    }
                }
            }

            return vecRotation;
        }
    }

    public static void faceBow(IEntity target, boolean silent, boolean predict, float predictSize) {
        IEntityPlayerSP player = RotationUtils.mc.getThePlayer();
        double posX = target.getPosX() + (predict ? (target.getPosX() - target.getPrevPosX()) * (double) predictSize : 0.0D) - (player.getPosX() + (predict ? player.getPosX() - player.getPrevPosX() : 0.0D));
        double posY = target.getEntityBoundingBox().getMinY() + (predict ? (target.getEntityBoundingBox().getMinY() - target.getPrevPosY()) * (double) predictSize : 0.0D) + (double) target.getEyeHeight() - 0.15D - (player.getEntityBoundingBox().getMinY() + (predict ? player.getPosY() - player.getPrevPosY() : 0.0D)) - (double) player.getEyeHeight();
        double posZ = target.getPosZ() + (predict ? (target.getPosZ() - target.getPrevPosZ()) * (double) predictSize : 0.0D) - (player.getPosZ() + (predict ? player.getPosZ() - player.getPrevPosZ() : 0.0D));
        double posSqrt = Math.sqrt(posX * posX + posZ * posZ);
        float velocity = LiquidBounce.moduleManager.getModule(FastBow.class).getState() ? 1.0F : (float) player.getItemInUseDuration() / 20.0F;

        velocity = (velocity * velocity + velocity * 2.0F) / 3.0F;
        if (velocity > 1.0F) {
            velocity = 1.0F;
        }

        Rotation rotation = new Rotation((float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F, (float) (-Math.toDegrees(Math.atan(((double) (velocity * velocity) - Math.sqrt((double) (velocity * velocity * velocity * velocity) - 0.006000000052154064D * (0.006000000052154064D * posSqrt * posSqrt + 2.0D * posY * (double) (velocity * velocity)))) / (0.006000000052154064D * posSqrt)))));

        if (silent) {
            setTargetRotation(rotation);
        } else {
            limitAngleChange(new Rotation(player.getRotationYaw(), player.getRotationPitch()), rotation, (float) (10 + (new Random()).nextInt(6))).toPlayer(RotationUtils.mc.getThePlayer());
        }

    }

    public static Rotation toRotation(WVec3 vec, boolean predict) {
        WVec3 eyesPos = new WVec3(RotationUtils.mc.getThePlayer().getPosX(), RotationUtils.mc.getThePlayer().getEntityBoundingBox().getMinY() + (double) RotationUtils.mc.getThePlayer().getEyeHeight(), RotationUtils.mc.getThePlayer().getPosZ());

        if (predict) {
            eyesPos.addVector(RotationUtils.mc.getThePlayer().getMotionX(), RotationUtils.mc.getThePlayer().getMotionY(), RotationUtils.mc.getThePlayer().getMotionZ());
        }

        double diffX = vec.getXCoord() - eyesPos.getXCoord();
        double diffY = vec.getYCoord() - eyesPos.getYCoord();
        double diffZ = vec.getZCoord() - eyesPos.getZCoord();

        return new Rotation(WMathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), WMathHelper.wrapAngleTo180_float((float) (-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))));
    }

    public static WVec3 getCenter(IAxisAlignedBB bb) {
        return new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * 0.5D, bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * 0.5D, bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * 0.5D);
    }

    public static VecRotation searchCenter(IAxisAlignedBB bb, boolean outborder, boolean random, boolean predict, boolean throughWalls, float distance) {
        WVec3 randomVec;

        if (outborder) {
            randomVec = new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * (RotationUtils.x * 0.3D + 1.0D), bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * (RotationUtils.y * 0.3D + 1.0D), bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * (RotationUtils.z * 0.3D + 1.0D));
            return new VecRotation(randomVec, toRotation(randomVec, predict));
        } else {
            randomVec = new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * RotationUtils.x * 0.8D, bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * RotationUtils.y * 0.8D, bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * RotationUtils.z * 0.8D);
            Rotation randomRotation = toRotation(randomVec, predict);
            WVec3 eyes = RotationUtils.mc.getThePlayer().getPositionEyes(1.0F);
            VecRotation vecRotation = null;

            for (double xSearch = 0.15D; xSearch < 0.85D; xSearch += 0.1D) {
                for (double ySearch = 0.15D; ySearch < 1.0D; ySearch += 0.1D) {
                    for (double zSearch = 0.15D; zSearch < 0.85D; zSearch += 0.1D) {
                        WVec3 vec3 = new WVec3(bb.getMinX() + (bb.getMaxX() - bb.getMinX()) * xSearch, bb.getMinY() + (bb.getMaxY() - bb.getMinY()) * ySearch, bb.getMinZ() + (bb.getMaxZ() - bb.getMinZ()) * zSearch);
                        Rotation rotation = toRotation(vec3, predict);
                        double vecDist = eyes.distanceTo(vec3);

                        if (vecDist <= (double) distance && (throughWalls || isVisible(vec3))) {
                            VecRotation currentVec = new VecRotation(vec3, rotation);

                            if (vecRotation != null) {
                                if (random) {
                                    if (getRotationDifference(currentVec.getRotation(), randomRotation) >= getRotationDifference(vecRotation.getRotation(), randomRotation)) {
                                        continue;
                                    }
                                } else if (getRotationDifference(currentVec.getRotation()) >= getRotationDifference(vecRotation.getRotation())) {
                                    continue;
                                }
                            }

                            vecRotation = currentVec;
                        }
                    }
                }
            }

            return vecRotation;
        }
    }

    public static double getRotationDifference(IEntity entity) {
        Rotation rotation = toRotation(getCenter(entity.getEntityBoundingBox()), true);

        return getRotationDifference(rotation, new Rotation(RotationUtils.mc.getThePlayer().getRotationYaw(), RotationUtils.mc.getThePlayer().getRotationPitch()));
    }

    public static double getRotationDifference(Rotation rotation) {
        return RotationUtils.serverRotation == null ? 0.0D : getRotationDifference(rotation, RotationUtils.serverRotation);
    }

    public static double getRotationDifference(Rotation a, Rotation b) {
        return Math.hypot((double) getAngleDifference(a.getYaw(), b.getYaw()), (double) (a.getPitch() - b.getPitch()));
    }

    @NotNull
    public static Rotation limitAngleChange(Rotation currentRotation, Rotation targetRotation, float turnSpeed) {
        float yawDifference = getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
        float pitchDifference = getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());

        return new Rotation(currentRotation.getYaw() + (yawDifference > turnSpeed ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + (pitchDifference > turnSpeed ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
    }

    public static float getAngleDifference(float a, float b) {
        return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
    }

    public static WVec3 getVectorForRotation(Rotation rotation) {
        float yawCos = (float) Math.cos((double) (-rotation.getYaw() * 0.017453292F - 3.1415927F));
        float yawSin = (float) Math.sin((double) (-rotation.getYaw() * 0.017453292F - 3.1415927F));
        float pitchCos = (float) (-Math.cos((double) (-rotation.getPitch() * 0.017453292F)));
        float pitchSin = (float) Math.sin((double) (-rotation.getPitch() * 0.017453292F));

        return new WVec3((double) (yawSin * pitchCos), (double) pitchSin, (double) (yawCos * pitchCos));
    }

    public static Rotation getRotationFromEyeHasPrev(double x, double y, double z) {
        double xDiff = x - (RotationUtils.mc2.player.prevPosX + (RotationUtils.mc2.player.posX - RotationUtils.mc2.player.prevPosX));
        double yDiff = y - (RotationUtils.mc2.player.prevPosY + (RotationUtils.mc2.player.posY - RotationUtils.mc2.player.prevPosY) + (RotationUtils.mc2.player.getEntityBoundingBox().maxY - RotationUtils.mc2.player.getEntityBoundingBox().minY));
        double zDiff = z - (RotationUtils.mc2.player.prevPosZ + (RotationUtils.mc2.player.posZ - RotationUtils.mc2.player.prevPosZ));
        double dist = (double) MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);

        return new Rotation((float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F, (float) (-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D)));
    }

    public static Rotation getRotationFromEyeHasPrev(IEntityLivingBase target) {
        double x = target.getPrevPosX() + (target.getPosX() - target.getPrevPosX());
        double y = target.getPrevPosY() + (target.getPosY() - target.getPrevPosY());
        double z = target.getPrevPosZ() + (target.getPosZ() - target.getPosZ());

        return getRotationFromEyeHasPrev(x, y, z);
    }

    public static boolean isFaced(IEntity targetEntity, double blockReachDistance) {
        return RaycastUtils.raycastEntity(blockReachDistance, (entity) -> {
            return targetEntity != null && targetEntity.equals(entity);
        }) != null;
    }

    public static boolean isVisible(WVec3 vec3) {
        WVec3 eyesPos = new WVec3(RotationUtils.mc.getThePlayer().getPosX(), RotationUtils.mc.getThePlayer().getEntityBoundingBox().getMinY() + (double) RotationUtils.mc.getThePlayer().getEyeHeight(), RotationUtils.mc.getThePlayer().getPosZ());

        return RotationUtils.mc.getTheWorld().rayTraceBlocks(eyesPos, vec3) == null;
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (RotationUtils.targetRotation != null) {
            --RotationUtils.keepLength;
            if (RotationUtils.keepLength <= 0) {
                reset();
            }
        }

        if (RotationUtils.random.nextGaussian() > 0.8D) {
            RotationUtils.x = Math.random();
        }

        if (RotationUtils.random.nextGaussian() > 0.8D) {
            RotationUtils.y = Math.random();
        }

        if (RotationUtils.random.nextGaussian() > 0.8D) {
            RotationUtils.z = Math.random();
        }

    }

    public static void setTargetRotation(Rotation rotation, int keepLength) {
        if (!Double.isNaN((double) rotation.getYaw()) && !Double.isNaN((double) rotation.getPitch()) && rotation.getPitch() <= 90.0F && rotation.getPitch() >= -90.0F) {
            rotation.fixedSensitivity(RotationUtils.mc.getGameSettings().getMouseSensitivity());
            RotationUtils.targetRotation = rotation;
            RotationUtils.keepLength = keepLength;
        }
    }

    public static void setTargetRotation(Rotation rotation) {
        setTargetRotation(rotation, 0);
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        IPacket packet = event.getPacket();

        if (RotationUtils.classProvider.isCPacketPlayer(packet)) {
            ICPacketPlayer packetPlayer = packet.asCPacketPlayer();

            if (RotationUtils.targetRotation != null && !RotationUtils.keepCurrentRotation && (RotationUtils.targetRotation.getYaw() != RotationUtils.serverRotation.getYaw() || RotationUtils.targetRotation.getPitch() != RotationUtils.serverRotation.getPitch())) {
                packetPlayer.setYaw(RotationUtils.targetRotation.getYaw());
                packetPlayer.setPitch(RotationUtils.targetRotation.getPitch());
                packetPlayer.setRotating(true);
            }

            if (packetPlayer.isRotating()) {
                RotationUtils.serverRotation = new Rotation(packetPlayer.getYaw(), packetPlayer.getPitch());
            }
        }

    }

    public static void reset() {
        RotationUtils.keepLength = 0;
        RotationUtils.targetRotation = null;
    }

    public boolean handleEvents() {
        return true;
    }
}
