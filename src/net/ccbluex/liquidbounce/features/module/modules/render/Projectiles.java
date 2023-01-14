package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@ModuleInfo(
    name = "Projectiles",
    description = "Allows you to see where arrows will land.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Projectiles;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorRedValue", "interpolateHSB", "Ljava/awt/Color;", "startColor", "endColor", "process", "", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"}
)
public final class Projectiles extends Module {

    private final ListValue colorMode = new ListValue("Color", new String[] { "Custom", "BowPower", "Rainbow"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient != null) {
                IWorldClient theWorld = iworldclient;
                IItemStack iitemstack = thePlayer.getHeldItem();

                if (iitemstack != null) {
                    IItemStack heldItem = iitemstack;
                    IItem item = heldItem.getItem();
                    IRenderManager renderManager = MinecraftInstance.mc.getRenderManager();
                    boolean isBow = false;
                    float motionFactor = 1.5F;
                    float motionSlowdown = 0.99F;
                    float gravity = 0.0F;
                    float size = 0.0F;
                    float yaw;

                    if (MinecraftInstance.classProvider.isItemBow(item)) {
                        if (!thePlayer.isUsingItem()) {
                            return;
                        }

                        isBow = true;
                        gravity = 0.05F;
                        size = 0.3F;
                        yaw = (float) thePlayer.getItemInUseDuration() / 20.0F;
                        yaw = (yaw * yaw + yaw * 2.0F) / 3.0F;
                        if (yaw < 0.1F) {
                            return;
                        }

                        if (yaw > 1.0F) {
                            yaw = 1.0F;
                        }

                        motionFactor = yaw * 3.0F;
                    } else if (MinecraftInstance.classProvider.isItemFishingRod(item)) {
                        gravity = 0.04F;
                        size = 0.25F;
                        motionSlowdown = 0.92F;
                    } else if (MinecraftInstance.classProvider.isItemPotion(item) && heldItem.isSplash()) {
                        gravity = 0.05F;
                        size = 0.25F;
                        motionFactor = 0.5F;
                    } else {
                        if (!MinecraftInstance.classProvider.isItemSnowball(item) && !MinecraftInstance.classProvider.isItemEnderPearl(item) && !MinecraftInstance.classProvider.isItemEgg(item)) {
                            return;
                        }

                        gravity = 0.03F;
                        size = 0.25F;
                    }

                    yaw = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getYaw() : thePlayer.getRotationYaw();
                    float pitch = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getPitch() : thePlayer.getRotationPitch();
                    float yawRadians = yaw / 180.0F * (float) 3.141592653589793D;
                    float pitchRadians = pitch / 180.0F * (float) 3.141592653589793D;
                    double d0 = renderManager.getRenderPosX();
                    boolean posY = false;
                    float f = (float) Math.cos((double) yawRadians);
                    double posX = d0 - (double) (f * 0.16F);
                    double d1 = renderManager.getRenderPosY() + (double) thePlayer.getEyeHeight() - 0.10000000149011612D;

                    d0 = renderManager.getRenderPosZ();
                    boolean motionX = false;

                    f = (float) Math.sin((double) yawRadians);
                    double posZ = d0 - (double) (f * 0.16F);
                    boolean motionY = false;
                    float f1 = -((float) Math.sin((double) yawRadians));

                    motionY = false;
                    float f2 = (float) Math.cos((double) pitchRadians);
                    double d2 = (double) (f1 * f2) * (isBow ? 1.0D : 0.4D);
                    float motionZ = (pitch + (float) (MinecraftInstance.classProvider.isItemPotion(item) && heldItem.isSplash() ? -20 : 0)) / 180.0F * 3.1415927F;
                    boolean flag = false;
                    double d3 = (double) (-((float) Math.sin((double) motionZ))) * (isBow ? 1.0D : 0.4D);
                    boolean distance = false;

                    f1 = (float) Math.cos((double) yawRadians);
                    distance = false;
                    f2 = (float) Math.cos((double) pitchRadians);
                    double d4 = (double) (f1 * f2) * (isBow ? 1.0D : 0.4D);
                    double landingPosition = d2 * d2 + d3 * d3 + d4 * d4;
                    boolean hitEntity = false;
                    double d5 = Math.sqrt(landingPosition);

                    d2 /= d5;
                    d3 /= d5;
                    d4 /= d5;
                    d2 *= (double) motionFactor;
                    d3 *= (double) motionFactor;
                    d4 *= (double) motionFactor;
                    IMovingObjectPosition imovingobjectposition = (IMovingObjectPosition) null;
                    boolean hasLanded = false;

                    hitEntity = false;
                    ITessellator tessellator = MinecraftInstance.classProvider.getTessellatorInstance();
                    IWorldRenderer worldRenderer = tessellator.getWorldRenderer();

                    GL11.glDepthMask(false);
                    RenderUtils.enableGlCap(new int[] { 3042, 2848});
                    RenderUtils.disableGlCap(new int[] { 2929, 3008, 3553});
                    GL11.glBlendFunc(770, 771);
                    GL11.glHint(3154, 4354);
                    String cylinder = (String) this.colorMode.get();
                    boolean posAfter = false;

                    if (cylinder == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    } else {
                        String s = cylinder.toLowerCase();

                        Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                        cylinder = s;
                        switch (cylinder.hashCode()) {
                        case -2055857797:
                            if (cylinder.equals("bowpower")) {
                                Color color = Color.RED;

                                Intrinsics.checkExpressionValueIsNotNull(Color.RED, "Color.RED");
                                Color color1 = Color.GREEN;

                                Intrinsics.checkExpressionValueIsNotNull(Color.GREEN, "Color.GREEN");
                                RenderUtils.glColor(this.interpolateHSB(color, color1, motionFactor / (float) 30 * (float) 10));
                            }
                            break;

                        case -1349088399:
                            if (cylinder.equals("custom")) {
                                RenderUtils.glColor(new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), 255));
                            }
                            break;

                        case 973576630:
                            if (cylinder.equals("rainbow")) {
                                RenderUtils.glColor(ColorUtils.rainbow());
                            }
                        }

                        GL11.glLineWidth(2.0F);
                        worldRenderer.begin(3, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));

                        while (!hasLanded && d1 > 0.0D) {
                            WVec3 wvec3 = new WVec3(posX, d1, posZ);
                            WVec3 wvec31 = new WVec3(posX + d2, d1 + d3, posZ + d4);

                            imovingobjectposition = theWorld.rayTraceBlocks(wvec3, wvec31, false, true, false);
                            wvec3 = new WVec3(posX, d1, posZ);
                            wvec31 = new WVec3(posX + d2, d1 + d3, posZ + d4);
                            if (imovingobjectposition != null) {
                                hasLanded = true;
                                wvec31 = new WVec3(imovingobjectposition.getHitVec().getXCoord(), imovingobjectposition.getHitVec().getYCoord(), imovingobjectposition.getHitVec().getZCoord());
                            }

                            IAxisAlignedBB arrowBox = MinecraftInstance.classProvider.createAxisAlignedBB(posX - (double) size, d1 - (double) size, posZ - (double) size, posX + (double) size, d1 + (double) size, posZ + (double) size).addCoord(d2, d3, d4).expand(1.0D, 1.0D, 1.0D);
                            double chunkMaxX = (arrowBox.getMinX() - 2.0D) / 16.0D;
                            boolean chunkMaxZ = false;
                            int chunkMinX = (int) Math.floor(chunkMaxX);
                            double chunkMinZ = (arrowBox.getMaxX() + 2.0D) / 16.0D;
                            boolean collidedEntities = false;
                            int i = (int) Math.floor(chunkMinZ);
                            double d6 = (arrowBox.getMinZ() - 2.0D) / 16.0D;
                            boolean blockState = false;
                            int j = (int) Math.floor(d6);
                            double d7 = (arrowBox.getMaxZ() + 2.0D) / 16.0D;
                            boolean flag1 = false;
                            int k = (int) Math.floor(d7);

                            blockState = false;
                            List list = (List) (new ArrayList());
                            int l = chunkMinX;
                            int i1 = i;

                            if (chunkMinX <= i) {
                                while (true) {
                                    int possibleEntityBoundingBox = j;
                                    int possibleEntityLanding = k;

                                    if (j <= k) {
                                        while (true) {
                                            theWorld.getChunkFromChunkCoords(l, possibleEntityBoundingBox).getEntitiesWithinAABBForEntity(thePlayer, arrowBox, list, (Void) null);
                                            if (possibleEntityBoundingBox == possibleEntityLanding) {
                                                break;
                                            }

                                            ++possibleEntityBoundingBox;
                                        }
                                    }

                                    if (l == i1) {
                                        break;
                                    }

                                    ++l;
                                }
                            }

                            Iterator iterator = list.iterator();

                            while (iterator.hasNext()) {
                                IEntity ientity = (IEntity) iterator.next();

                                if (ientity.canBeCollidedWith() && Intrinsics.areEqual(ientity, thePlayer) ^ true) {
                                    IAxisAlignedBB iaxisalignedbb = ientity.getEntityBoundingBox().expand((double) size, (double) size, (double) size);
                                    IMovingObjectPosition imovingobjectposition1 = iaxisalignedbb.calculateIntercept(wvec3, wvec31);

                                    if (imovingobjectposition1 != null) {
                                        IMovingObjectPosition imovingobjectposition2 = imovingobjectposition1;

                                        hitEntity = true;
                                        hasLanded = true;
                                        imovingobjectposition = imovingobjectposition2;
                                    }
                                }
                            }

                            posX += d2;
                            d1 += d3;
                            posZ += d4;
                            IIBlockState iiblockstate = theWorld.getBlockState(new WBlockPos(posX, d1, posZ));

                            if (Intrinsics.areEqual(iiblockstate.getBlock().getMaterial(iiblockstate), MinecraftInstance.classProvider.getMaterialEnum(MaterialType.WATER))) {
                                d2 *= 0.6D;
                                d3 *= 0.6D;
                                d4 *= 0.6D;
                            } else {
                                d2 *= (double) motionSlowdown;
                                d3 *= (double) motionSlowdown;
                                d4 *= (double) motionSlowdown;
                            }

                            d3 -= (double) gravity;
                            worldRenderer.pos(posX - renderManager.getRenderPosX(), d1 - renderManager.getRenderPosY(), posZ - renderManager.getRenderPosZ()).endVertex();
                        }

                        tessellator.draw();
                        GL11.glPushMatrix();
                        GL11.glTranslated(posX - renderManager.getRenderPosX(), d1 - renderManager.getRenderPosY(), posZ - renderManager.getRenderPosZ());
                        if (imovingobjectposition != null) {
                            IEnumFacing ienumfacing = imovingobjectposition.getSideHit();

                            if (ienumfacing == null) {
                                Intrinsics.throwNpe();
                            }

                            switch (ienumfacing.getAxisOrdinal()) {
                            case 0:
                                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);

                            case 1:
                            default:
                                break;

                            case 2:
                                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                            }

                            if (hitEntity) {
                                RenderUtils.glColor(new Color(255, 0, 0, 150));
                            }
                        }

                        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                        Cylinder cylinder = new Cylinder();

                        cylinder.setDrawStyle(100011);
                        cylinder.draw(0.2F, 0.0F, 0.0F, 60, 1);
                        GL11.glPopMatrix();
                        GL11.glDepthMask(true);
                        RenderUtils.resetCaps();
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Nullable
    public final Color interpolateHSB(@NotNull Color startColor, @NotNull Color endColor, float process) {
        Intrinsics.checkParameterIsNotNull(startColor, "startColor");
        Intrinsics.checkParameterIsNotNull(endColor, "endColor");
        float[] startHSB = Color.RGBtoHSB(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), (float[]) null);
        float[] endHSB = Color.RGBtoHSB(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), (float[]) null);
        float brightness = (startHSB[2] + endHSB[2]) / (float) 2;
        float saturation = (startHSB[1] + endHSB[1]) / (float) 2;
        float hueMax = startHSB[0] > endHSB[0] ? startHSB[0] : endHSB[0];
        float hueMin = startHSB[0] > endHSB[0] ? endHSB[0] : startHSB[0];
        float hue = (hueMax - hueMin) * process + hueMin;

        return Color.getHSBColor(hue, saturation, brightness);
    }
}
