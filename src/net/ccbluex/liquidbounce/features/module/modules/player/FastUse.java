package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.utils.PacketUtils;
import me.utils.player.PlayerUtil;
import me.utils.timer.TimeHelper;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "FastUse",
    description = "Allows you to use items faster.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0012\u0010\u001f\u001a\u00020\u001b2\b\u0010\u001d\u001a\u0004\u0018\u00010 H\u0007J\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\"H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/FastUse;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "customSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "customTimer", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "delayValue", "fixed", "", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "noMoveValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "packet", "", "stopMoving", "tag", "", "getTag", "()Ljava/lang/String;", "time", "Lme/utils/timer/TimeHelper;", "usedTimer", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class FastUse extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Instant", "NCP", "AAC", "Custom", "Vulcan", "Hypixel"}, "NCP");
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    private final IntegerValue delayValue = new IntegerValue("CustomDelay", 0, 0, 300);
    private final IntegerValue customSpeedValue = new IntegerValue("CustomSpeed", 2, 1, 35);
    private final FloatValue customTimer = new FloatValue("CustomTimer", 1.1F, 0.5F, 2.0F);
    private final BoolValue stopMoving = new BoolValue("StopMoving", false);
    private final MSTimer msTimer = new MSTimer();
    private boolean usedTimer;
    private boolean fixed;
    private int packet;
    private final TimeHelper time = new TimeHelper();

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.isPre()) {
            EntityPlayerSP entityplayersp = MinecraftInstance.mc2.player;

            if (MinecraftInstance.mc2.player == null) {
                Intrinsics.throwNpe();
            }

            if (entityplayersp.inventory.getCurrentItem() != null && MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().isKeyDown() && (!((Boolean) this.stopMoving.get()).booleanValue() || !PlayerUtil.isMoving())) {
                entityplayersp = MinecraftInstance.mc2.player;
                if (MinecraftInstance.mc2.player == null) {
                    Intrinsics.throwNpe();
                }

                ItemStack itemstack = entityplayersp.inventory.getCurrentItem();

                Intrinsics.checkExpressionValueIsNotNull(itemstack, "mc2.player!!.inventory.getCurrentItem()");
                Item heldItem = itemstack.getItem();

                if (heldItem instanceof ItemFood || heldItem instanceof ItemPotion) {
                    this.fixed = false;
                    String s = (String) this.modeValue.get();
                    boolean flag = false;

                    if (s == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    int i;
                    IEntityPlayerSP ientityplayersp;
                    IPlayerControllerMP iplayercontrollermp;
                    IEntityPlayerSP ientityplayersp1;

                    switch (s.hashCode()) {
                    case -805359837:
                        if (s.equals("vulcan")) {
                            if (this.packet != 16) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getTicksExisted() % 2 == 0) {
                                    MinecraftInstance.mc.getTimer().setTimerSpeed(0.33F);
                                } else {
                                    MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                                }

                                PacketUtils.sendPacketNoEvent((Packet) (new CPacketPlayer(true)));
                                i = this.packet++;
                            } else {
                                iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                iplayercontrollermp.onStoppedUsingItem(ientityplayersp1);
                                MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().setPressed(false);
                            }
                        }
                        break;

                    case 1381910549:
                        if (s.equals("hypixel")) {
                            if (this.packet != 20) {
                                MinecraftInstance.mc.getTimer().setTimerSpeed(0.5F);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.getSendQueue().getNetworkManager().sendPacket((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(true));
                                i = this.packet++;
                            } else {
                                iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                iplayercontrollermp.onStoppedUsingItem(ientityplayersp1);
                            }
                        }
                    }
                }
            }

            if (!MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().isKeyDown() && !this.fixed) {
                this.packet = 0;
                MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                this.fixed = true;
                this.time.reset();
            }
        }

    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (this.usedTimer) {
                MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                this.usedTimer = false;
            }

            if (!thePlayer.isUsingItem()) {
                this.msTimer.reset();
            } else {
                IItemStack iitemstack = thePlayer.getItemInUse();

                if (iitemstack == null) {
                    Intrinsics.throwNpe();
                }

                IItem usingItem = iitemstack.getItem();

                if (MinecraftInstance.classProvider.isItemFood(usingItem) || MinecraftInstance.classProvider.isItemBucketMilk(usingItem) || MinecraftInstance.classProvider.isItemPotion(usingItem)) {
                    String s = (String) this.modeValue.get();
                    boolean flag = false;

                    if (s == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    boolean flag1;
                    boolean flag2;
                    byte b0;
                    boolean $i$a$-repeat-FastUse$onUpdate$3;
                    byte b1;
                    int i;

                    switch (s.hashCode()) {
                    case -1349088399:
                        if (s.equals("custom")) {
                            MinecraftInstance.mc.getTimer().setTimerSpeed(((Number) this.customTimer.get()).floatValue());
                            this.usedTimer = true;
                            if (!this.msTimer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
                                return;
                            }

                            int j = ((Number) this.customSpeedValue.get()).intValue();

                            flag1 = false;
                            flag2 = false;
                            i = 0;

                            for (int k = j; i < k; ++i) {
                                $i$a$-repeat-FastUse$onUpdate$3 = false;
                                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                            }

                            this.msTimer.reset();
                        }
                        break;

                    case 96323:
                        if (s.equals("aac")) {
                            MinecraftInstance.mc.getTimer().setTimerSpeed(1.22F);
                            this.usedTimer = true;
                        }
                        break;

                    case 108891:
                        if (s.equals("ncp") && thePlayer.getItemInUseDuration() > 14) {
                            b1 = 20;
                            flag1 = false;
                            flag2 = false;
                            i = 0;

                            for (b0 = b1; i < b0; ++i) {
                                $i$a$-repeat-FastUse$onUpdate$3 = false;
                                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                            }

                            MinecraftInstance.mc.getPlayerController().onStoppedUsingItem(thePlayer);
                        }
                        break;

                    case 1957570017:
                        if (s.equals("instant")) {
                            b1 = 35;
                            flag1 = false;
                            flag2 = false;
                            i = 0;

                            for (b0 = b1; i < b0; ++i) {
                                $i$a$-repeat-FastUse$onUpdate$3 = false;
                                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                            }

                            MinecraftInstance.mc.getPlayerController().onStoppedUsingItem(thePlayer);
                        }
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onMove(@Nullable MoveEvent event) {
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null && event != null) {
            if (this.getState() && thePlayer.isUsingItem() && ((Boolean) this.noMoveValue.get()).booleanValue()) {
                IItemStack iitemstack = thePlayer.getItemInUse();

                if (iitemstack == null) {
                    Intrinsics.throwNpe();
                }

                IItem usingItem = iitemstack.getItem();

                if (MinecraftInstance.classProvider.isItemFood(usingItem) || MinecraftInstance.classProvider.isItemBucketMilk(usingItem) || MinecraftInstance.classProvider.isItemPotion(usingItem)) {
                    event.zero();
                }

            }
        }
    }

    public void onDisable() {
        if (this.usedTimer) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
            this.usedTimer = false;
        }

    }

    @Nullable
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
