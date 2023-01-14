package me.Skid.utils.particles;

import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.vecmath.Vector3f;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class PlayerParticles {

    public static float[] getRotations(IEntity ent) {
        double x = ent.getPosX();
        double z = ent.getPosZ();
        double y = ent.getPosY() + (double) (ent.getEyeHeight() / 4.0F);

        return getRotationFromPosition(x, z, y);
    }

    public static IBlock getBlock(double offsetX, double offsetY, double offsetZ) {
        return MinecraftInstance.mc.getTheWorld().getBlockState(new WBlockPos(offsetX, offsetY, offsetZ)).getBlock();
    }

    public static void damagePlayer() {
        for (int i = 0; i <= 45; ++i) {
            MinecraftInstance.mc.getThePlayer().getSendQueue().getNetworkManager().sendPacket(MinecraftInstance.classProvider.createCPacketPlayerPosition(MinecraftInstance.mc.getThePlayer().getPosX(), MinecraftInstance.mc.getThePlayer().getPosY() + 0.0624986421D, MinecraftInstance.mc.getThePlayer().getPosZ(), false));
            MinecraftInstance.mc.getThePlayer().getSendQueue().getNetworkManager().sendPacket(MinecraftInstance.classProvider.createCPacketPlayerPosition(MinecraftInstance.mc.getThePlayer().getPosX(), MinecraftInstance.mc.getThePlayer().getPosY() + 0.0625D, MinecraftInstance.mc.getThePlayer().getPosZ(), false));
            MinecraftInstance.mc.getThePlayer().getSendQueue().getNetworkManager().sendPacket(MinecraftInstance.classProvider.createCPacketPlayerPosition(MinecraftInstance.mc.getThePlayer().getPosX(), MinecraftInstance.mc.getThePlayer().getPosY() + 0.0624986421D, MinecraftInstance.mc.getThePlayer().getPosZ(), false));
            MinecraftInstance.mc.getThePlayer().getSendQueue().getNetworkManager().sendPacket(MinecraftInstance.classProvider.createCPacketPlayerPosition(MinecraftInstance.mc.getThePlayer().getPosX(), MinecraftInstance.mc.getThePlayer().getPosY() + 1.3579E-6D, MinecraftInstance.mc.getThePlayer().getPosZ(), false));
        }

        MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(MinecraftInstance.mc.getThePlayer().getPosX(), MinecraftInstance.mc.getThePlayer().getPosY(), MinecraftInstance.mc.getThePlayer().getPosZ(), true));
    }

    public static boolean isBlockUnder() {
        if (MinecraftInstance.mc.getThePlayer().getPosY() < 0.0D) {
            return false;
        } else {
            for (int off = 0; off < (int) MinecraftInstance.mc.getThePlayer().getPosY() + 2; off += 2) {
                IAxisAlignedBB bb = MinecraftInstance.mc.getThePlayer().getEntityBoundingBox().offset(0.0D, (double) (-off), 0.0D);

                if (!MinecraftInstance.mc.getTheWorld().getCollidingBoundingBoxes(MinecraftInstance.mc.getThePlayer(), bb).isEmpty()) {
                    return true;
                }
            }

            return false;
        }
    }

    private static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - MinecraftInstance.mc.getThePlayer().getPosX();
        double zDiff = z - MinecraftInstance.mc.getThePlayer().getPosZ();
        double yDiff = y - MinecraftInstance.mc.getThePlayer().getPosY() - 0.6D;
        double dist = (double) MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D));

        return new float[] { yaw, pitch};
    }

    public static float getDirection() {
        float yaw = MinecraftInstance.mc.getThePlayer().getRotationYawHead();
        float forward = MinecraftInstance.mc.getThePlayer().getMoveForward();
        float strafe = MinecraftInstance.mc.getThePlayer().getMoveStrafing();

        yaw += (float) (forward < 0.0F ? 180 : 0);
        if (strafe < 0.0F) {
            yaw += (float) (forward < 0.0F ? -45 : (forward == 0.0F ? 90 : 45));
        }

        if (strafe > 0.0F) {
            yaw -= (float) (forward < 0.0F ? -45 : (forward == 0.0F ? 90 : 45));
        }

        return yaw * 0.017453292F;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873D;

        if (MinecraftInstance.mc.getThePlayer().isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
            int amplifier = MinecraftInstance.mc.getThePlayer().getActivePotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED)).getAmplifier();

            baseSpeed *= 1.0D + 0.2D * (double) (amplifier + 1);
        }

        return baseSpeed;
    }

    public static IBlock getBlock(WBlockPos pos) {
        return MinecraftInstance.mc.getTheWorld().getBlockState(pos).getBlock();
    }

    public static IBlock getBlockAtPosC(IEntityPlayer inPlayer, double x, double y, double z) {
        return getBlock(new WBlockPos(inPlayer.getPosX() - x, inPlayer.getPosY() - y, inPlayer.getPosZ() - z));
    }

    public static ArrayList vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
        ArrayList positions = new ArrayList();
        double posX = tpX - MinecraftInstance.mc.getThePlayer().getPosX();
        double posY = tpY - (MinecraftInstance.mc.getThePlayer().getPosY() + (double) MinecraftInstance.mc.getThePlayer().getEyeHeight() + 1.1D);
        double posZ = tpZ - MinecraftInstance.mc.getThePlayer().getPosZ();
        float yaw = (float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D - 90.0D);
        float pitch = (float) (-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ)) * 180.0D / 3.141592653589793D);
        double tmpX = MinecraftInstance.mc.getThePlayer().getPosX();
        double tmpY = MinecraftInstance.mc.getThePlayer().getPosY();
        double tmpZ = MinecraftInstance.mc.getThePlayer().getPosZ();
        double steps = 1.0D;

        double d;

        for (d = speed; d < getDistance(MinecraftInstance.mc.getThePlayer().getPosX(), MinecraftInstance.mc.getThePlayer().getPosY(), MinecraftInstance.mc.getThePlayer().getPosZ(), tpX, tpY, tpZ); d += speed) {
            ++steps;
        }

        for (d = speed; d < getDistance(MinecraftInstance.mc.getThePlayer().getPosX(), MinecraftInstance.mc.getThePlayer().getPosY(), MinecraftInstance.mc.getThePlayer().getPosZ(), tpX, tpY, tpZ); d += speed) {
            tmpX = MinecraftInstance.mc.getThePlayer().getPosX() - Math.sin((double) getDirection(yaw)) * d;
            tmpZ = MinecraftInstance.mc.getThePlayer().getPosZ() + Math.cos((double) getDirection(yaw)) * d;
            positions.add(new Vector3f((float) tmpX, (float) (tmpY -= (MinecraftInstance.mc.getThePlayer().getPosY() - tpY) / steps), (float) tmpZ));
        }

        positions.add(new Vector3f((float) tpX, (float) tpY, (float) tpZ));
        return positions;
    }

    public static float getDirection(float yaw) {
        if (MinecraftInstance.mc.getThePlayer().getMoveForward() < 0.0F) {
            yaw += 180.0F;
        }

        float forward = 1.0F;

        if (MinecraftInstance.mc.getThePlayer().getMoveForward() < 0.0F) {
            forward = -0.5F;
        } else if (MinecraftInstance.mc.getThePlayer().getMoveForward() > 0.0F) {
            forward = 0.5F;
        }

        if (MinecraftInstance.mc.getThePlayer().getMoveStrafing() > 0.0F) {
            yaw -= 90.0F * forward;
        }

        if (MinecraftInstance.mc.getThePlayer().getMoveStrafing() < 0.0F) {
            yaw += 90.0F * forward;
        }

        return yaw *= 0.017453292F;
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d2 = y1 - y2;
        double d3 = z1 - z2;

        return (double) MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }

    public static float getItemAtkDamage(IItemStack itemStack) {
        Multimap multimap = itemStack.getAttributeModifiers();
        Iterator iterator;

        if (!multimap.isEmpty() && (iterator = multimap.entries().iterator()).hasNext()) {
            Entry entry = (Entry) iterator.next();
            AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
            double damage = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? attributeModifier.getAmount() : attributeModifier.getAmount() * 100.0D;

            return attributeModifier.getAmount() > 1.0D ? 1.0F + (float) damage : 1.0F;
        } else {
            return 1.0F;
        }
    }

    public static int bestWeapon(IEntity target) {
        MinecraftInstance.mc.getThePlayer().getInventory().setCurrentItem(0);
        byte firstSlot = 0;
        byte bestWeapon = -1;
        boolean j = true;

        for (byte i = 0; i < 9; ++i) {
            MinecraftInstance.mc.getThePlayer().getInventory().setCurrentItem(i);
            IItemStack itemStack = MinecraftInstance.mc.getThePlayer().getHeldItem();

            if (itemStack != null) {
                int itemAtkDamage = (int) getItemAtkDamage(itemStack);

                bestWeapon = i;
            }
        }

        if (bestWeapon != -1) {
            return bestWeapon;
        } else {
            return firstSlot;
        }
    }

    public static void shiftClick(Item i) {
        for (int i1 = 9; i1 < 37; ++i1) {
            IItemStack itemstack = MinecraftInstance.mc.getThePlayer().getInventoryContainer().getSlot(i1).getStack();

            if (itemstack != null && itemstack.getItem() == i) {
                MinecraftInstance.mc.getPlayerController().windowClick(0, i1, 0, 1, MinecraftInstance.mc.getThePlayer());
                break;
            }
        }

    }

    public static boolean hotbarIsFull() {
        for (int i = 0; i <= 36; ++i) {
            IItemStack itemstack = MinecraftInstance.mc.getThePlayer().getInventory().getStackInSlot(i);

            if (itemstack == null) {
                return false;
            }
        }

        return true;
    }

    public static WVec3 getLook(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);

        return new WVec3((double) (f1 * f2), (double) f3, (double) (f * f2));
    }

    public static void tellPlayer(String string) {
        MinecraftInstance.mc.getThePlayer().addChatMessage(MinecraftInstance.classProvider.createChatComponentText(string));
    }

    public static boolean isMoving() {
        return !MinecraftInstance.mc.getThePlayer().isCollidedHorizontally() && !MinecraftInstance.mc.getThePlayer().isSneaking() ? MinecraftInstance.mc.getThePlayer().getMovementInput().getMoveForward() != 0.0F || MinecraftInstance.mc.getThePlayer().getMovementInput().getMoveStrafe() != 0.0F : false;
    }

    public IEntityLivingBase getEntity() {
        return null;
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0D / inc;

        return (double) Math.round(val * one) / one;
    }
}
