package net.ccbluex.liquidbounce.utils.block;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J*\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0018\u0010\n\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00040\u000bj\u0002`\rH\u0007J*\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0018\u0010\n\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00040\u000bj\u0002`\rH\u0007J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0011\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0087\bJ\u001c\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\f0\u001d2\u0006\u0010\u001e\u001a\u00020\u0013H\u0007¨\u0006\u001f"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/block/BlockUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "canBeClicked", "", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "collideBlock", "axisAlignedBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "collide", "Lkotlin/Function1;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "Lnet/ccbluex/liquidbounce/utils/block/Collidable;", "collideBlockIntersects", "getBlock", "getBlockName", "", "id", "", "getCenterDistance", "", "getMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "getState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "isFullBlock", "isReplaceable", "searchBlocks", "", "radius", "LiquidBounce"}
)
public final class BlockUtils extends MinecraftInstance {

    public static final BlockUtils INSTANCE;

    @JvmStatic
    @Nullable
    public static final IBlock getBlock(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();
        IBlock iblock;

        if (iworldclient != null) {
            IIBlockState iiblockstate = iworldclient.getBlockState(blockPos);

            if (iiblockstate != null) {
                iblock = iiblockstate.getBlock();
                return iblock;
            }
        }

        iblock = null;
        return iblock;
    }

    @JvmStatic
    @Nullable
    public static final IMaterial getMaterial(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IIBlockState state = getState(blockPos);
        IMaterial imaterial;

        if (state != null) {
            IBlock iblock = state.getBlock();

            if (iblock != null) {
                imaterial = iblock.getMaterial(state);
                return imaterial;
            }
        }

        imaterial = null;
        return imaterial;
    }

    @JvmStatic
    public static final boolean isReplaceable(@NotNull WBlockPos blockPos) {
        byte $i$f$isReplaceable = 0;

        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IMaterial imaterial = getMaterial(blockPos);

        return imaterial != null ? imaterial.isReplaceable() : false;
    }

    @JvmStatic
    @Nullable
    public static final IIBlockState getState(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        return iworldclient != null ? iworldclient.getBlockState(blockPos) : null;
    }

    @JvmStatic
    public static final boolean canBeClicked(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IBlock iblock = getBlock(blockPos);
        boolean flag;

        if (iblock != null ? iblock.canCollideCheck(getState(blockPos), false) : false) {
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            if (iworldclient.getWorldBorder().contains(blockPos)) {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    @JvmStatic
    @NotNull
    public static final String getBlockName(int id) {
        IBlock iblock = MinecraftInstance.functions.getBlockById(id);

        if (iblock == null) {
            Intrinsics.throwNpe();
        }

        return iblock.getLocalizedName();
    }

    @JvmStatic
    public static final boolean isFullBlock(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IBlock iblock = getBlock(blockPos);

        if (iblock != null) {
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            IWorld iworld = (IWorld) iworldclient;
            IIBlockState iiblockstate = getState(blockPos);

            if (iiblockstate == null) {
                return false;
            }

            IAxisAlignedBB iaxisalignedbb = iblock.getCollisionBoundingBox(iworld, blockPos, iiblockstate);

            if (iaxisalignedbb != null) {
                IAxisAlignedBB axisAlignedBB = iaxisalignedbb;

                return axisAlignedBB.getMaxX() - axisAlignedBB.getMinX() == 1.0D && axisAlignedBB.getMaxY() - axisAlignedBB.getMinY() == 1.0D && axisAlignedBB.getMaxZ() - axisAlignedBB.getMinZ() == 1.0D;
            }
        }

        return false;
    }

    @JvmStatic
    public static final double getCenterDistance(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        return ientityplayersp.getDistance((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D);
    }

    @JvmStatic
    @NotNull
    public static final Map searchBlocks(int radius) {
        boolean thePlayer = false;
        Map blocks = (Map) (new LinkedHashMap());
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            return blocks;
        } else {
            IEntityPlayerSP ientityplayersp1 = ientityplayersp;
            int x = radius;
            int i = -radius + 1;

            if (radius >= i) {
                while (true) {
                    int y = radius;
                    int j = -radius + 1;

                    if (radius >= j) {
                        while (true) {
                            int z = radius;
                            int k = -radius + 1;

                            if (radius >= k) {
                                while (true) {
                                    WBlockPos blockPos = new WBlockPos((int) ientityplayersp1.getPosX() + x, (int) ientityplayersp1.getPosY() + y, (int) ientityplayersp1.getPosZ() + z);
                                    IBlock iblock = getBlock(blockPos);

                                    if (iblock != null) {
                                        IBlock block = iblock;

                                        blocks.put(blockPos, block);
                                    }

                                    if (z == k) {
                                        break;
                                    }

                                    --z;
                                }
                            }

                            if (y == j) {
                                break;
                            }

                            --y;
                        }
                    }

                    if (x == i) {
                        break;
                    }

                    --x;
                }
            }

            return blocks;
        }
    }

    @JvmStatic
    public static final boolean collideBlock(@NotNull IAxisAlignedBB axisAlignedBB, @NotNull Function1 collide) {
        Intrinsics.checkParameterIsNotNull(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkParameterIsNotNull(collide, "collide");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        double z = thePlayer.getEntityBoundingBox().getMinX();
        boolean block = false;
        long x = (long) ((int) Math.floor(z));

        z = thePlayer.getEntityBoundingBox().getMaxX();
        block = false;

        for (long i = (long) ((int) Math.floor(z)) + 1L; x < i; ++x) {
            double d0 = thePlayer.getEntityBoundingBox().getMinZ();
            boolean flag = false;
            int j = (int) Math.floor(d0);

            d0 = thePlayer.getEntityBoundingBox().getMaxZ();
            flag = false;

            for (int k = (int) Math.floor(d0) + 1; j < k; ++j) {
                IBlock iblock = getBlock(new WBlockPos((double) x, axisAlignedBB.getMinY(), (double) j));

                if (!((Boolean) collide.invoke(iblock)).booleanValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    @JvmStatic
    public static final boolean collideBlockIntersects(@NotNull IAxisAlignedBB axisAlignedBB, @NotNull Function1 collide) {
        Intrinsics.checkParameterIsNotNull(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkParameterIsNotNull(collide, "collide");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        IWorldClient world = iworldclient;
        double z = thePlayer.getEntityBoundingBox().getMinX();
        boolean blockPos = false;
        int x = (int) Math.floor(z);

        z = thePlayer.getEntityBoundingBox().getMaxX();
        blockPos = false;

        for (int i = (int) Math.floor(z) + 1; x < i; ++x) {
            double d0 = thePlayer.getEntityBoundingBox().getMinZ();
            boolean boundingBox = false;
            int j = (int) Math.floor(d0);

            d0 = thePlayer.getEntityBoundingBox().getMaxZ();
            boundingBox = false;

            for (int k = (int) Math.floor(d0) + 1; j < k; ++j) {
                WBlockPos wblockpos = new WBlockPos((double) x, axisAlignedBB.getMinY(), (double) j);
                IBlock block = getBlock(wblockpos);

                if (((Boolean) collide.invoke(block)).booleanValue()) {
                    IIBlockState iiblockstate = getState(wblockpos);

                    if (iiblockstate != null) {
                        IIBlockState iiblockstate1 = iiblockstate;
                        boolean flag = false;
                        boolean flag1 = false;
                        boolean $i$a$-let-BlockUtils$collideBlockIntersects$boundingBox$1 = false;
                        IAxisAlignedBB iaxisalignedbb = block != null ? block.getCollisionBoundingBox((IWorld) world, wblockpos, iiblockstate1) : null;

                        if (iaxisalignedbb != null) {
                            IAxisAlignedBB iaxisalignedbb1 = iaxisalignedbb;

                            if (thePlayer.getEntityBoundingBox().intersectsWith(iaxisalignedbb1)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    static {
        BlockUtils blockutils = new BlockUtils();

        INSTANCE = blockutils;
    }
}
