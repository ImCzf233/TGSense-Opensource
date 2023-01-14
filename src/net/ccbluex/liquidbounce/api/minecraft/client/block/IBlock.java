package net.ccbluex.liquidbounce.api.minecraft.client.block;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0015\u001a\u00020\u0013H&J\"\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u0003H&J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0000H&J \u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001bH&J\u0012\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u0014\u001a\u00020\u0003H&J \u0010&\u001a\u00020\r2\u0006\u0010\'\u001a\u00020(2\u0006\u0010!\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u001bH&J \u0010*\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u001bH&J\u0010\u0010+\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0003H&J\u0010\u0010,\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u0003H&J\u0018\u0010-\u001a\u00020.2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u001bH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0018\u0010\f\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006/"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "", "defaultState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "getDefaultState", "()Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "localizedName", "", "getLocalizedName", "()Ljava/lang/String;", "registryName", "getRegistryName", "slipperiness", "", "getSlipperiness", "()F", "setSlipperiness", "(F)V", "canCollideCheck", "", "state", "hitIfLiquid", "getCollisionBoundingBox", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "pos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getIdFromBlock", "", "block", "getMapColor", "blockState", "theWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "bp", "getMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "getPlayerRelativeBlockHardness", "thePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "blockPos", "getSelectedBoundingBox", "isFullCube", "isTranslucent", "setBlockBoundsBasedOnState", "", "LiquidBounce"}
)
public interface IBlock {

    @NotNull
    String getRegistryName();

    float getSlipperiness();

    void setSlipperiness(float f);

    @Nullable
    IIBlockState getDefaultState();

    @NotNull
    String getLocalizedName();

    @NotNull
    IAxisAlignedBB getSelectedBoundingBox(@NotNull IWorld iworld, @NotNull IIBlockState iiblockstate, @NotNull WBlockPos wblockpos);

    @Nullable
    IAxisAlignedBB getCollisionBoundingBox(@NotNull IWorld iworld, @NotNull WBlockPos wblockpos, @NotNull IIBlockState iiblockstate);

    boolean canCollideCheck(@Nullable IIBlockState iiblockstate, boolean flag);

    void setBlockBoundsBasedOnState(@NotNull IWorld iworld, @NotNull WBlockPos wblockpos);

    float getPlayerRelativeBlockHardness(@NotNull IEntityPlayerSP ientityplayersp, @NotNull IWorld iworld, @NotNull WBlockPos wblockpos);

    int getIdFromBlock(@NotNull IBlock iblock);

    boolean isTranslucent(@NotNull IIBlockState iiblockstate);

    int getMapColor(@NotNull IIBlockState iiblockstate, @NotNull IWorldClient iworldclient, @NotNull WBlockPos wblockpos);

    @Nullable
    IMaterial getMaterial(@NotNull IIBlockState iiblockstate);

    boolean isFullCube(@NotNull IIBlockState iiblockstate);
}
