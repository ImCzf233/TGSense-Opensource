package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketClientStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketResourcePackStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3i;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorldSettings;
import net.ccbluex.liquidbounce.injection.backend.Backend;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.client.CPacketClientStatus.State;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000\u0088\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0086\b\u001a\r\u0010\u0005\u001a\u00020\u0002*\u00020\u0001H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\t*\u00020\nH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u000b*\u00020\fH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\r*\u00020\u000eH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u000f*\u00020\u0010H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0011*\u00020\u0012H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0013*\u00020\u0014H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0015*\u00020\u0016H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0017*\u00020\u0018H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0019*\u00020\u001aH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u001b*\u00020\u001cH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u001d*\u00020\u001eH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u001f*\u00020 H\u0086\b\u001a\r\u0010!\u001a\u00020\n*\u00020\tH\u0086\b\u001a\r\u0010!\u001a\u00020\u0012*\u00020\u0011H\u0086\b\u001a\r\u0010!\u001a\u00020\u0016*\u00020\u0015H\u0086\b\u001a\r\u0010!\u001a\u00020\u0018*\u00020\u0017H\u0086\b\u001a\r\u0010!\u001a\u00020\"*\u00020#H\u0086\b\u001a\r\u0010!\u001a\u00020\u001c*\u00020\u001bH\u0086\b\u001a\r\u0010!\u001a\u00020\u001e*\u00020\u001dH\u0086\b\u001a\r\u0010!\u001a\u00020\u001a*\u00020\u0019H\u0086\b\u001a\r\u0010!\u001a\u00020 *\u00020\u001fH\u0086\b¨\u0006$"},
    d2 = { "toClickType", "Lnet/minecraft/inventory/ClickType;", "", "toEntityEquipmentSlot", "Lnet/minecraft/inventory/EntityEquipmentSlot;", "toInt", "unwrap", "Lnet/minecraft/util/EnumHand;", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "Lnet/minecraft/entity/player/EnumPlayerModelParts;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "Lnet/minecraft/util/text/event/ClickEvent$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent$WAction;", "Lnet/minecraft/network/play/client/CPacketClientStatus$State;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState;", "Lnet/minecraft/network/play/client/CPacketEntityAction$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", "Lnet/minecraft/network/play/client/CPacketPlayerDigging$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "Lnet/minecraft/network/play/client/CPacketResourcePackStatus$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketResourcePackStatus$WAction;", "Lnet/minecraft/network/play/client/CPacketUseEntity$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "Lnet/minecraft/util/math/BlockPos;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "Lnet/minecraft/util/text/TextFormatting;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "Lnet/minecraft/util/math/Vec3d;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "Lnet/minecraft/util/math/Vec3i;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "Lnet/minecraft/world/GameType;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "wrap", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "Lnet/minecraft/util/math/RayTraceResult$Type;", "LiquidBounce"}
)
public final class BackendExtentionsKt {

    @NotNull
    public static final Vec3d unwrap(@NotNull WVec3 $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        return new Vec3d($this$unwrap.getXCoord(), $this$unwrap.getYCoord(), $this$unwrap.getZCoord());
    }

    @NotNull
    public static final Vec3i unwrap(@NotNull WVec3i $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        return new Vec3i($this$unwrap.getX(), $this$unwrap.getY(), $this$unwrap.getZ());
    }

    @NotNull
    public static final BlockPos unwrap(@NotNull WBlockPos $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        return new BlockPos($this$unwrap.getX(), $this$unwrap.getY(), $this$unwrap.getZ());
    }

    @NotNull
    public static final WBlockPos wrap(@NotNull BlockPos $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        return new WBlockPos($this$wrap.getX(), $this$wrap.getY(), $this$wrap.getZ());
    }

    @NotNull
    public static final WVec3 wrap(@NotNull Vec3d $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        return new WVec3($this$wrap.x, $this$wrap.y, $this$wrap.z);
    }

    @NotNull
    public static final WVec3i wrap(@NotNull Vec3i $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        return new WVec3i($this$wrap.getX(), $this$wrap.getY(), $this$wrap.getZ());
    }

    @NotNull
    public static final IMovingObjectPosition.WMovingObjectType wrap(@NotNull Type $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        IMovingObjectPosition.WMovingObjectType imovingobjectposition_wmovingobjecttype;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$0[$this$wrap.ordinal()]) {
        case 1:
            imovingobjectposition_wmovingobjecttype = IMovingObjectPosition.WMovingObjectType.MISS;
            break;

        case 2:
            imovingobjectposition_wmovingobjecttype = IMovingObjectPosition.WMovingObjectType.BLOCK;
            break;

        case 3:
            imovingobjectposition_wmovingobjecttype = IMovingObjectPosition.WMovingObjectType.ENTITY;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return imovingobjectposition_wmovingobjecttype;
    }

    @NotNull
    public static final EnumPlayerModelParts unwrap(@NotNull WEnumPlayerModelParts $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        EnumPlayerModelParts enumplayermodelparts;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$1[$this$unwrap.ordinal()]) {
        case 1:
            enumplayermodelparts = EnumPlayerModelParts.CAPE;
            break;

        case 2:
            enumplayermodelparts = EnumPlayerModelParts.JACKET;
            break;

        case 3:
            enumplayermodelparts = EnumPlayerModelParts.LEFT_SLEEVE;
            break;

        case 4:
            enumplayermodelparts = EnumPlayerModelParts.RIGHT_SLEEVE;
            break;

        case 5:
            enumplayermodelparts = EnumPlayerModelParts.LEFT_PANTS_LEG;
            break;

        case 6:
            enumplayermodelparts = EnumPlayerModelParts.RIGHT_PANTS_LEG;
            break;

        case 7:
            enumplayermodelparts = EnumPlayerModelParts.HAT;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return enumplayermodelparts;
    }

    @NotNull
    public static final WEnumPlayerModelParts wrap(@NotNull EnumPlayerModelParts $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        WEnumPlayerModelParts wenumplayermodelparts;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$2[$this$wrap.ordinal()]) {
        case 1:
            wenumplayermodelparts = WEnumPlayerModelParts.CAPE;
            break;

        case 2:
            wenumplayermodelparts = WEnumPlayerModelParts.JACKET;
            break;

        case 3:
            wenumplayermodelparts = WEnumPlayerModelParts.LEFT_SLEEVE;
            break;

        case 4:
            wenumplayermodelparts = WEnumPlayerModelParts.RIGHT_SLEEVE;
            break;

        case 5:
            wenumplayermodelparts = WEnumPlayerModelParts.LEFT_PANTS_LEG;
            break;

        case 6:
            wenumplayermodelparts = WEnumPlayerModelParts.RIGHT_PANTS_LEG;
            break;

        case 7:
            wenumplayermodelparts = WEnumPlayerModelParts.HAT;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return wenumplayermodelparts;
    }

    @NotNull
    public static final WEnumChatFormatting wrap(@NotNull TextFormatting $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        WEnumChatFormatting wenumchatformatting;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$3[$this$wrap.ordinal()]) {
        case 1:
            wenumchatformatting = WEnumChatFormatting.BLACK;
            break;

        case 2:
            wenumchatformatting = WEnumChatFormatting.DARK_BLUE;
            break;

        case 3:
            wenumchatformatting = WEnumChatFormatting.DARK_GREEN;
            break;

        case 4:
            wenumchatformatting = WEnumChatFormatting.DARK_AQUA;
            break;

        case 5:
            wenumchatformatting = WEnumChatFormatting.DARK_RED;
            break;

        case 6:
            wenumchatformatting = WEnumChatFormatting.DARK_PURPLE;
            break;

        case 7:
            wenumchatformatting = WEnumChatFormatting.GOLD;
            break;

        case 8:
            wenumchatformatting = WEnumChatFormatting.GRAY;
            break;

        case 9:
            wenumchatformatting = WEnumChatFormatting.DARK_GRAY;
            break;

        case 10:
            wenumchatformatting = WEnumChatFormatting.BLUE;
            break;

        case 11:
            wenumchatformatting = WEnumChatFormatting.GREEN;
            break;

        case 12:
            wenumchatformatting = WEnumChatFormatting.AQUA;
            break;

        case 13:
            wenumchatformatting = WEnumChatFormatting.RED;
            break;

        case 14:
            wenumchatformatting = WEnumChatFormatting.LIGHT_PURPLE;
            break;

        case 15:
            wenumchatformatting = WEnumChatFormatting.YELLOW;
            break;

        case 16:
            wenumchatformatting = WEnumChatFormatting.WHITE;
            break;

        case 17:
            wenumchatformatting = WEnumChatFormatting.OBFUSCATED;
            break;

        case 18:
            wenumchatformatting = WEnumChatFormatting.BOLD;
            break;

        case 19:
            wenumchatformatting = WEnumChatFormatting.STRIKETHROUGH;
            break;

        case 20:
            wenumchatformatting = WEnumChatFormatting.UNDERLINE;
            break;

        case 21:
            wenumchatformatting = WEnumChatFormatting.ITALIC;
            break;

        case 22:
            wenumchatformatting = WEnumChatFormatting.RESET;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return wenumchatformatting;
    }

    @NotNull
    public static final TextFormatting unwrap(@NotNull WEnumChatFormatting $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        TextFormatting textformatting;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$4[$this$unwrap.ordinal()]) {
        case 1:
            textformatting = TextFormatting.BLACK;
            break;

        case 2:
            textformatting = TextFormatting.DARK_BLUE;
            break;

        case 3:
            textformatting = TextFormatting.DARK_GREEN;
            break;

        case 4:
            textformatting = TextFormatting.DARK_AQUA;
            break;

        case 5:
            textformatting = TextFormatting.DARK_RED;
            break;

        case 6:
            textformatting = TextFormatting.DARK_PURPLE;
            break;

        case 7:
            textformatting = TextFormatting.GOLD;
            break;

        case 8:
            textformatting = TextFormatting.GRAY;
            break;

        case 9:
            textformatting = TextFormatting.DARK_GRAY;
            break;

        case 10:
            textformatting = TextFormatting.BLUE;
            break;

        case 11:
            textformatting = TextFormatting.GREEN;
            break;

        case 12:
            textformatting = TextFormatting.AQUA;
            break;

        case 13:
            textformatting = TextFormatting.RED;
            break;

        case 14:
            textformatting = TextFormatting.LIGHT_PURPLE;
            break;

        case 15:
            textformatting = TextFormatting.YELLOW;
            break;

        case 16:
            textformatting = TextFormatting.WHITE;
            break;

        case 17:
            textformatting = TextFormatting.OBFUSCATED;
            break;

        case 18:
            textformatting = TextFormatting.BOLD;
            break;

        case 19:
            textformatting = TextFormatting.STRIKETHROUGH;
            break;

        case 20:
            textformatting = TextFormatting.UNDERLINE;
            break;

        case 21:
            textformatting = TextFormatting.ITALIC;
            break;

        case 22:
            textformatting = TextFormatting.RESET;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return textformatting;
    }

    @NotNull
    public static final GameType unwrap(@NotNull IWorldSettings.WGameType $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        GameType gametype;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$5[$this$unwrap.ordinal()]) {
        case 1:
            gametype = GameType.NOT_SET;
            break;

        case 2:
            gametype = GameType.SURVIVAL;
            break;

        case 3:
            gametype = GameType.CREATIVE;
            break;

        case 4:
            gametype = GameType.ADVENTURE;
            break;

        case 5:
            gametype = GameType.SPECTATOR;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return gametype;
    }

    @NotNull
    public static final IWorldSettings.WGameType wrap(@NotNull GameType $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        IWorldSettings.WGameType iworldsettings_wgametype;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$6[$this$wrap.ordinal()]) {
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

    @NotNull
    public static final ICPacketUseEntity.WAction wrap(@NotNull Action $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        ICPacketUseEntity.WAction icpacketuseentity_waction;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$7[$this$wrap.ordinal()]) {
        case 1:
            icpacketuseentity_waction = ICPacketUseEntity.WAction.INTERACT;
            break;

        case 2:
            icpacketuseentity_waction = ICPacketUseEntity.WAction.ATTACK;
            break;

        case 3:
            icpacketuseentity_waction = ICPacketUseEntity.WAction.INTERACT_AT;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return icpacketuseentity_waction;
    }

    @NotNull
    public static final Action unwrap(@NotNull ICPacketUseEntity.WAction $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        Action action;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$8[$this$unwrap.ordinal()]) {
        case 1:
            action = Action.INTERACT;
            break;

        case 2:
            action = Action.ATTACK;
            break;

        case 3:
            action = Action.INTERACT_AT;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return action;
    }

    @NotNull
    public static final ICPacketPlayerDigging.WAction wrap(@NotNull net.minecraft.network.play.client.CPacketPlayerDigging.Action $this$wrap) {
        byte $i$f$wrap = 0;

        Intrinsics.checkParameterIsNotNull($this$wrap, "$this$wrap");
        ICPacketPlayerDigging.WAction icpacketplayerdigging_waction;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$9[$this$wrap.ordinal()]) {
        case 1:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.ABORT_DESTROY_BLOCK;
            break;

        case 2:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.DROP_ALL_ITEMS;
            break;

        case 3:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.DROP_ITEM;
            break;

        case 4:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM;
            break;

        case 5:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
            break;

        case 6:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
            break;

        case 7:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.SWAP_HELD_ITEMS;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return icpacketplayerdigging_waction;
    }

    @NotNull
    public static final net.minecraft.network.play.client.CPacketPlayerDigging.Action unwrap(@NotNull ICPacketPlayerDigging.WAction $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        net.minecraft.network.play.client.CPacketPlayerDigging.Action net_minecraft_network_play_client_cpacketplayerdigging_action;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$10[$this$unwrap.ordinal()]) {
        case 1:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK;
            break;

        case 2:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK;
            break;

        case 3:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK;
            break;

        case 4:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.DROP_ALL_ITEMS;
            break;

        case 5:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.DROP_ITEM;
            break;

        case 6:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.RELEASE_USE_ITEM;
            break;

        case 7:
            net_minecraft_network_play_client_cpacketplayerdigging_action = net.minecraft.network.play.client.CPacketPlayerDigging.Action.SWAP_HELD_ITEMS;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return net_minecraft_network_play_client_cpacketplayerdigging_action;
    }

    @NotNull
    public static final net.minecraft.util.text.event.ClickEvent.Action unwrap(@NotNull IClickEvent.WAction $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$11[$this$unwrap.ordinal()]) {
        case 1:
            return net.minecraft.util.text.event.ClickEvent.Action.OPEN_URL;

        default:
            throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final State unwrap(@NotNull ICPacketClientStatus.WEnumState $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        State state;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$12[$this$unwrap.ordinal()]) {
        case 1:
            state = State.PERFORM_RESPAWN;
            break;

        case 2:
            state = State.REQUEST_STATS;
            break;

        case 3:
            Backend backend = Backend.INSTANCE;
            boolean $i$f$BACKEND_UNSUPPORTED = false;

            throw (Throwable) (new NotImplementedError("1.12.2 doesn\'t support this feature\'"));

        default:
            throw new NoWhenBranchMatchedException();
        }

        return state;
    }

    @NotNull
    public static final net.minecraft.network.play.client.CPacketResourcePackStatus.Action unwrap(@NotNull ICPacketResourcePackStatus.WAction $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        net.minecraft.network.play.client.CPacketResourcePackStatus.Action net_minecraft_network_play_client_cpacketresourcepackstatus_action;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$13[$this$unwrap.ordinal()]) {
        case 1:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED;
            break;

        case 2:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.DECLINED;
            break;

        case 3:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.FAILED_DOWNLOAD;
            break;

        case 4:
            net_minecraft_network_play_client_cpacketresourcepackstatus_action = net.minecraft.network.play.client.CPacketResourcePackStatus.Action.ACCEPTED;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return net_minecraft_network_play_client_cpacketresourcepackstatus_action;
    }

    @NotNull
    public static final net.minecraft.network.play.client.CPacketEntityAction.Action unwrap(@NotNull ICPacketEntityAction.WAction $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        net.minecraft.network.play.client.CPacketEntityAction.Action net_minecraft_network_play_client_cpacketentityaction_action;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$14[$this$unwrap.ordinal()]) {
        case 1:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.START_SNEAKING;
            break;

        case 2:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SNEAKING;
            break;

        case 3:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SLEEPING;
            break;

        case 4:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.START_SPRINTING;
            break;

        case 5:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SPRINTING;
            break;

        case 6:
            net_minecraft_network_play_client_cpacketentityaction_action = net.minecraft.network.play.client.CPacketEntityAction.Action.OPEN_INVENTORY;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return net_minecraft_network_play_client_cpacketentityaction_action;
    }

    @NotNull
    public static final EntityEquipmentSlot toEntityEquipmentSlot(int $this$toEntityEquipmentSlot) {
        byte $i$f$toEntityEquipmentSlot = 0;
        EntityEquipmentSlot entityequipmentslot;

        switch ($this$toEntityEquipmentSlot) {
        case 0:
            entityequipmentslot = EntityEquipmentSlot.FEET;
            break;

        case 1:
            entityequipmentslot = EntityEquipmentSlot.LEGS;
            break;

        case 2:
            entityequipmentslot = EntityEquipmentSlot.CHEST;
            break;

        case 3:
            entityequipmentslot = EntityEquipmentSlot.HEAD;
            break;

        case 4:
            entityequipmentslot = EntityEquipmentSlot.MAINHAND;
            break;

        case 5:
            entityequipmentslot = EntityEquipmentSlot.OFFHAND;
            break;

        default:
            throw (Throwable) (new IllegalArgumentException("Invalid armorType " + $this$toEntityEquipmentSlot));
        }

        return entityequipmentslot;
    }

    @NotNull
    public static final ClickType toClickType(int $this$toClickType) {
        byte $i$f$toClickType = 0;
        ClickType clicktype;

        switch ($this$toClickType) {
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
            throw (Throwable) (new IllegalArgumentException("Invalid mode " + $this$toClickType));
        }

        return clicktype;
    }

    public static final int toInt(@NotNull ClickType $this$toInt) {
        byte $i$f$toInt = 0;

        Intrinsics.checkParameterIsNotNull($this$toInt, "$this$toInt");
        byte b0;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$15[$this$toInt.ordinal()]) {
        case 1:
            b0 = 0;
            break;

        case 2:
            b0 = 1;
            break;

        case 3:
            b0 = 2;
            break;

        case 4:
            b0 = 3;
            break;

        case 5:
            b0 = 4;
            break;

        case 6:
            b0 = 5;
            break;

        case 7:
            b0 = 6;
            break;

        default:
            throw (Throwable) (new IllegalArgumentException("Invalid mode " + $this$toInt));
        }

        return b0;
    }

    @NotNull
    public static final EnumHand unwrap(@NotNull WEnumHand $this$unwrap) {
        byte $i$f$unwrap = 0;

        Intrinsics.checkParameterIsNotNull($this$unwrap, "$this$unwrap");
        EnumHand enumhand;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$16[$this$unwrap.ordinal()]) {
        case 1:
            enumhand = EnumHand.MAIN_HAND;
            break;

        case 2:
            enumhand = EnumHand.OFF_HAND;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return enumhand;
    }
}
