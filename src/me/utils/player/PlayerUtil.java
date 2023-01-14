package me.utils.player;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;

public class PlayerUtil {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isBlockUnder() {
        if (MinecraftInstance.mc2.player.posY < 0.0D) {
            return false;
        } else {
            for (int off = 0; off < (int) PlayerUtil.mc.player.posY + 2; off += 2) {
                AxisAlignedBB bb = PlayerUtil.mc.player.getEntityBoundingBox().offset(0.0D, (double) (-off), 0.0D);

                if (!PlayerUtil.mc.world.getCollisionBoxes(PlayerUtil.mc.player, bb).isEmpty()) {
                    return true;
                }
            }

            return false;
        }
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d2 = y1 - y2;
        double d3 = z1 - z2;

        return Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }

    public static boolean isMoving() {
        return !PlayerUtil.mc.player.collidedHorizontally && !PlayerUtil.mc.player.isSneaking() ? PlayerUtil.mc.player.movementInput.moveForward != 0.0F || PlayerUtil.mc.player.movementInput.moveStrafe != 0.0F : false;
    }

    public EntityLivingBase getEntity() {
        return null;
    }
}
