package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorldSettings;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0013\u0010#\u001a\u00020\u00192\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J\b\u0010&\u001a\u00020\u0019H\u0016J\u0018\u0010\'\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J:\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020 2\u0006\u00100\u001a\u00020\"2\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u00020*H\u0016J \u00106\u001a\u00020\u00192\u0006\u00107\u001a\u0002082\u0006\u0010+\u001a\u0002092\u0006\u0010-\u001a\u00020.H\u0016J\b\u0010:\u001a\u000204H\u0016J0\u0010;\u001a\u0002042\u0006\u0010<\u001a\u00020\u00062\u0006\u0010=\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u00062\u0006\u0010?\u001a\u00020\u00062\u0006\u0010@\u001a\u00020*H\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006A"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/PlayerControllerMPImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP;", "wrapped", "Lnet/minecraft/client/multiplayer/PlayerControllerMP;", "(Lnet/minecraft/client/multiplayer/PlayerControllerMP;)V", "value", "", "blockHitDelay", "getBlockHitDelay", "()I", "setBlockHitDelay", "(I)V", "blockReachDistance", "", "getBlockReachDistance", "()F", "curBlockDamageMP", "getCurBlockDamageMP", "setCurBlockDamageMP", "(F)V", "currentGameType", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "getCurrentGameType", "()Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "isInCreativeMode", "", "()Z", "isNotCreative", "getWrapped", "()Lnet/minecraft/client/multiplayer/PlayerControllerMP;", "clickBlock", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "enumFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "equals", "other", "", "extendedReach", "onPlayerDestroyBlock", "onPlayerRightClick", "playerSP", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "wWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "wItemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "wPosition", "wSideOpposite", "wHitVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "onStoppedUsingItem", "", "thePlayer", "sendUseItem", "wPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "updateController", "windowClick", "windowId", "slot", "mouseButton", "mode", "player", "LiquidBounce"}
)
public final class PlayerControllerMPImpl implements IPlayerControllerMP {

    @NotNull
    private final PlayerControllerMP wrapped;

    public boolean isNotCreative() {
        return this.wrapped.isNotCreative();
    }

    public float getBlockReachDistance() {
        return this.wrapped.getBlockReachDistance();
    }

    @NotNull
    public IWorldSettings.WGameType getCurrentGameType() {
        GameType gametype = this.wrapped.getCurrentGameType();

        Intrinsics.checkExpressionValueIsNotNull(gametype, "wrapped.currentGameType");
        GameType $this$wrap$iv = gametype;
        boolean $i$f$wrap = false;
        IWorldSettings.WGameType iworldsettings_wgametype;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$6[$this$wrap$iv.ordinal()]) {
        case 1:
            iworldsettings_wgametype = IWorldSettings.WGameType.NOT_SET;
            break;

        case 2:
            iworldsettings_wgametype = IWorldSettings.WGameType.SURVIVAL;
            break;

        case 3:
            iworldsettings_wgametype = IWorldSettings.WGameType.CREATIVE;
            break;

        case 4:
            iworldsettings_wgametype = IWorldSettings.WGameType.ADVENTUR;
            break;

        case 5:
            iworldsettings_wgametype = IWorldSettings.WGameType.SPECTATOR;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return iworldsettings_wgametype;
    }

    public boolean isInCreativeMode() {
        return this.wrapped.isInCreativeMode();
    }

    public float getCurBlockDamageMP() {
        return this.wrapped.curBlockDamageMP;
    }

    public void setCurBlockDamageMP(float value) {
        this.wrapped.curBlockDamageMP = value;
    }

    public int getBlockHitDelay() {
        return this.wrapped.blockHitDelay;
    }

    public void setBlockHitDelay(int value) {
        this.wrapped.blockHitDelay = value;
    }

    public void windowClick(int windowId, int slot, int mouseButton, int mode, @NotNull IEntityPlayerSP player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        PlayerControllerMP playercontrollermp = this.wrapped;
        boolean $i$f$unwrap = false;
        ClickType clicktype;

        switch (mode) {
        case 0:
            clicktype = ClickType.PICKUP;
            break;

        case 1:
            clicktype = ClickType.QUICK_MOVE;
            break;

        case 2:
            clicktype = ClickType.SWAP;
            break;

        case 3:
            clicktype = ClickType.CLONE;
            break;

        case 4:
            clicktype = ClickType.THROW;
            break;

        case 5:
            clicktype = ClickType.QUICK_CRAFT;
            break;

        case 6:
            clicktype = ClickType.PICKUP_ALL;
            break;

        default:
            throw (Throwable) (new IllegalArgumentException("Invalid mode " + mode));
        }

        ClickType clicktype1 = clicktype;

        $i$f$unwrap = false;
        EntityPlayerSP entityplayersp = (EntityPlayerSP) ((EntityPlayerSPImpl) player).getWrapped();

        playercontrollermp.windowClick(windowId, slot, mouseButton, clicktype1, (EntityPlayer) entityplayersp);
    }

    public void updateController() {
        this.wrapped.updateController();
    }

    public boolean sendUseItem(@NotNull IEntityPlayer wPlayer, @NotNull IWorld wWorld, @NotNull IItemStack wItemStack) {
        Intrinsics.checkParameterIsNotNull(wPlayer, "wPlayer");
        Intrinsics.checkParameterIsNotNull(wWorld, "wWorld");
        Intrinsics.checkParameterIsNotNull(wItemStack, "wItemStack");
        boolean itemStack = false;
        EntityPlayer player = (EntityPlayer) ((EntityPlayerImpl) wPlayer).getWrapped();
        boolean cancelResult = false;
        World world = ((WorldImpl) wWorld).getWrapped();
        boolean i = false;
        ItemStack itemStack1 = ((ItemStackImpl) wItemStack).getWrapped();

        if (this.wrapped.getCurrentGameType() == GameType.SPECTATOR) {
            return false;
        } else {
            this.wrapped.syncCurrentPlayItem();
            Minecraft minecraft = Minecraft.getMinecraft();

            Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
            NetHandlerPlayClient nethandlerplayclient = minecraft.getConnection();

            if (nethandlerplayclient == null) {
                Intrinsics.throwNpe();
            }

            nethandlerplayclient.sendPacket((Packet) (new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND)));
            if (player.getCooldownTracker().hasCooldown(itemStack1.getItem())) {
                return false;
            } else {
                EnumActionResult cancelResult1 = ForgeHooks.onItemRightClick(player, EnumHand.MAIN_HAND);

                if (cancelResult1 != null) {
                    return cancelResult1 == EnumActionResult.SUCCESS;
                } else {
                    int i1 = itemStack1.getCount();
                    ActionResult result = itemStack1.useItemRightClick(world, player, EnumHand.MAIN_HAND);

                    Intrinsics.checkExpressionValueIsNotNull(result, "result");
                    ItemStack resultStack = (ItemStack) result.getResult();

                    if (Intrinsics.areEqual(resultStack, itemStack1) ^ true || resultStack.getCount() != i1) {
                        player.setHeldItem(EnumHand.MAIN_HAND, resultStack);
                        Intrinsics.checkExpressionValueIsNotNull(resultStack, "resultStack");
                        if (resultStack.isEmpty()) {
                            ForgeEventFactory.onPlayerDestroyItem(player, itemStack1, EnumHand.MAIN_HAND);
                        }
                    }

                    return result.getType() == EnumActionResult.SUCCESS;
                }
            }
        }
    }

    public boolean onPlayerRightClick(@NotNull IEntityPlayerSP playerSP, @NotNull IWorldClient wWorld, @Nullable IItemStack wItemStack, @NotNull WBlockPos wPosition, @NotNull IEnumFacing wSideOpposite, @NotNull WVec3 wHitVec) {
        Intrinsics.checkParameterIsNotNull(playerSP, "playerSP");
        Intrinsics.checkParameterIsNotNull(wWorld, "wWorld");
        Intrinsics.checkParameterIsNotNull(wPosition, "wPosition");
        Intrinsics.checkParameterIsNotNull(wSideOpposite, "wSideOpposite");
        Intrinsics.checkParameterIsNotNull(wHitVec, "wHitVec");
        PlayerControllerMP playercontrollermp = this.wrapped;
        boolean $i$f$unwrap = false;
        EntityPlayerSP entityplayersp = (EntityPlayerSP) ((EntityPlayerSPImpl) playerSP).getWrapped();

        $i$f$unwrap = false;
        WorldClient worldclient = (WorldClient) ((WorldClientImpl) wWorld).getWrapped();

        $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(wPosition.getX(), wPosition.getY(), wPosition.getZ());

        $i$f$unwrap = false;
        EnumFacing enumfacing = ((EnumFacingImpl) wSideOpposite).getWrapped();

        $i$f$unwrap = false;
        Vec3d vec3d = new Vec3d(wHitVec.getXCoord(), wHitVec.getYCoord(), wHitVec.getZCoord());

        return playercontrollermp.processRightClickBlock(entityplayersp, worldclient, blockpos, enumfacing, vec3d, EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS;
    }

    public void onStoppedUsingItem(@NotNull IEntityPlayerSP thePlayer) {
        Intrinsics.checkParameterIsNotNull(thePlayer, "thePlayer");
        PlayerControllerMP playercontrollermp = this.wrapped;
        boolean $i$f$unwrap = false;
        EntityPlayerSP entityplayersp = (EntityPlayerSP) ((EntityPlayerSPImpl) thePlayer).getWrapped();

        playercontrollermp.onStoppedUsingItem((EntityPlayer) entityplayersp);
    }

    public boolean clickBlock(@NotNull WBlockPos blockPos, @NotNull IEnumFacing enumFacing) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Intrinsics.checkParameterIsNotNull(enumFacing, "enumFacing");
        PlayerControllerMP playercontrollermp = this.wrapped;
        boolean $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        $i$f$unwrap = false;
        EnumFacing enumfacing = ((EnumFacingImpl) enumFacing).getWrapped();

        return playercontrollermp.clickBlock(blockpos, enumfacing);
    }

    public boolean onPlayerDestroyBlock(@NotNull WBlockPos blockPos, @NotNull IEnumFacing enumFacing) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Intrinsics.checkParameterIsNotNull(enumFacing, "enumFacing");
        PlayerControllerMP playercontrollermp = this.wrapped;
        boolean $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        return playercontrollermp.onPlayerDestroyBlock(blockpos);
    }

    public boolean extendedReach() {
        return this.wrapped.extendedReach();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof PlayerControllerMPImpl && Intrinsics.areEqual(((PlayerControllerMPImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final PlayerControllerMP getWrapped() {
        return this.wrapped;
    }

    public PlayerControllerMPImpl(@NotNull PlayerControllerMP wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
