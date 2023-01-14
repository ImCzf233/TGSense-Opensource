package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.utils.Debug;
import me.utils.PacketUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketAnimation;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerBlockPlacement;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "NoSlow",
    description = "Cancels slowness effects caused by soulsand and using items.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010/\u001a\u00020\u00112\u0006\u00100\u001a\u000201H\u0002J\u0010\u00102\u001a\u00020\u00112\u0006\u00100\u001a\u000201H\u0002J\u000e\u00103\u001a\u00020\u00112\u0006\u00104\u001a\u000205J\u001a\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u0001092\u0006\u0010:\u001a\u00020\u0011H\u0002J\u0006\u0010;\u001a\u00020\u0011J\b\u0010<\u001a\u00020=H\u0016J\u0010\u0010>\u001a\u00020=2\u0006\u00100\u001a\u000201H\u0007J\u0010\u0010?\u001a\u00020=2\u0006\u00100\u001a\u00020@H\u0007J\u0010\u0010A\u001a\u00020=2\u0006\u00100\u001a\u00020BH\u0007J\u0010\u0010C\u001a\u00020=2\u0006\u00100\u001a\u00020DH\u0007JB\u0010E\u001a\u00020=2\u0006\u0010F\u001a\u0002012\u0006\u0010G\u001a\u00020\u00112\u0006\u0010H\u001a\u00020\u00112\u0006\u0010I\u001a\u00020\u00112\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00112\b\b\u0002\u0010M\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010%\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\"R\u0016\u0010\'\u001a\u0004\u0018\u00010(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006N"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoSlow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "blockForwardMultiplier", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blockStrafeMultiplier", "bowForwardMultiplier", "bowStrafeMultiplier", "consumeForwardMultiplier", "consumeStrafeMultiplier", "customDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "customOnGround", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "isBlocking", "", "()Z", "killAura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "getKillAura", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "lastBlockingStat", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "nextTemp", "packetBuf", "Ljava/util/LinkedList;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "packetValue", "getPacketValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "pendingFlagApplyPacket", "sendBuf", "soulsandValue", "getSoulsandValue", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "waitC03", "OnPost", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "OnPre", "fuckKotline", "value", "", "getMultiplier", "", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "isForward", "isBlock", "onDisable", "", "onMotion", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onSlowDown", "Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "sendPacket", "Event", "SendC07", "SendC08", "Delay", "DelayValue", "", "onGround", "Hypixel", "LiquidBounce"}
)
public final class NoSlow extends Module {

    private final ListValue modeValue = new ListValue("PacketMode", new String[] { "None", "HuaYuTing", "Vanilla", "NoPacket", "AAC", "AAC5", "Matrix", "Vulcan", "Custom", "HYT"}, "HuaYuTing");
    private final FloatValue blockForwardMultiplier = new FloatValue("BlockForwardMultiplier", 1.0F, 0.2F, 1.0F);
    private final FloatValue blockStrafeMultiplier = new FloatValue("BlockStrafeMultiplier", 1.0F, 0.2F, 1.0F);
    private final FloatValue consumeForwardMultiplier = new FloatValue("ConsumeForwardMultiplier", 1.0F, 0.2F, 1.0F);
    private final FloatValue consumeStrafeMultiplier = new FloatValue("ConsumeStrafeMultiplier", 1.0F, 0.2F, 1.0F);
    private final FloatValue bowForwardMultiplier = new FloatValue("BowForwardMultiplier", 1.0F, 0.2F, 1.0F);
    private final FloatValue bowStrafeMultiplier = new FloatValue("BowStrafeMultiplier", 1.0F, 0.2F, 1.0F);
    private final BoolValue customOnGround = new BoolValue("CustomOnGround", false);
    private final IntegerValue customDelayValue = new IntegerValue("CustomDelay", 60, 10, 200);
    @NotNull
    private final BoolValue packetValue = new BoolValue("Renderer", true);
    @NotNull
    private final BoolValue soulsandValue = new BoolValue("Soulsand", true);
    @NotNull
    private final MSTimer timer = new MSTimer();
    private final MSTimer Timer = new MSTimer();
    private boolean pendingFlagApplyPacket;
    private final MSTimer msTimer = new MSTimer();
    private boolean sendBuf;
    private LinkedList packetBuf = new LinkedList();
    private boolean nextTemp;
    private boolean waitC03;
    private boolean lastBlockingStat;
    @NotNull
    private final KillAura killAura;

    @NotNull
    public final BoolValue getPacketValue() {
        return this.packetValue;
    }

    @NotNull
    public final BoolValue getSoulsandValue() {
        return this.soulsandValue;
    }

    @NotNull
    public final MSTimer getTimer() {
        return this.timer;
    }

    @NotNull
    public final KillAura getKillAura() {
        return this.killAura;
    }

    public final boolean isBlock() {
        Boolean obool = Debug.thePlayerisBlocking;

        Intrinsics.checkExpressionValueIsNotNull(Debug.thePlayerisBlocking, "thePlayerisBlocking");
        return obool.booleanValue() || this.killAura.getBlockingStatus();
    }

    public final boolean fuckKotline(int value) {
        return value == 1;
    }

    private final boolean OnPre(MotionEvent event) {
        return event.getEventState() == EventState.PRE;
    }

    private final boolean OnPost(MotionEvent event) {
        return event.getEventState() == EventState.POST;
    }

    private final boolean isBlocking() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag;
        label43: {
            if (!ientityplayersp.isUsingItem()) {
                Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                }

                if (!((KillAura) module).getBlockingStatus()) {
                    break label43;
                }
            }

            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getHeldItem() != null) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IItemStack iitemstack = ientityplayersp.getHeldItem();

                if (iitemstack == null) {
                    Intrinsics.throwNpe();
                }

                if (iitemstack.getItem() instanceof ItemSword) {
                    flag = true;
                    return flag;
                }
            }
        }

        flag = false;
        return flag;
    }

    public void onDisable() {
        this.Timer.reset();
        this.msTimer.reset();
        this.pendingFlagApplyPacket = false;
        this.sendBuf = false;
        this.packetBuf.clear();
        this.nextTemp = false;
        this.waitC03 = false;
    }

    private final void sendPacket(MotionEvent Event, boolean SendC07, boolean SendC08, boolean Delay, long DelayValue, boolean onGround, boolean Hypixel) {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        } else {
            KillAura aura = (KillAura) module;
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            ICPacketPlayerDigging.WAction icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM;
            WBlockPos wblockpos = new WBlockPos(-1, -1, -1);
            EnumFacing enumfacing = EnumFacing.DOWN;

            if (EnumFacing.DOWN == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing");
            } else {
                IPacket digging = iclassprovider.createCPacketPlayerDigging(icpacketplayerdigging_waction, wblockpos, (IEnumFacing) enumfacing);

                iclassprovider = MinecraftInstance.classProvider;
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ICPacketPlayerBlockPlacement blockPlace = iclassprovider.createCPacketPlayerBlockPlacement((IItemStack) Integer.valueOf(ientityplayersp.getInventory().getCurrentItem()));

                iclassprovider = MinecraftInstance.classProvider;
                WBlockPos wblockpos1 = new WBlockPos(-1, -1, -1);
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                ICPacketPlayerBlockPlacement blockMent = iclassprovider.createCPacketPlayerBlockPlacement(wblockpos1, 255, (IItemStack) Integer.valueOf(ientityplayersp1.getInventory().getCurrentItem()), 0.0F, 0.0F, 0.0F);

                if (onGround) {
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!ientityplayersp2.getOnGround()) {
                        return;
                    }
                }

                if (SendC07 && this.OnPre(Event)) {
                    if (Delay && this.Timer.hasTimePassed(DelayValue)) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue(digging);
                    } else if (!Delay) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue(digging);
                    }
                }

                if (SendC08 && this.OnPost(Event)) {
                    if (Delay && this.Timer.hasTimePassed(DelayValue) && !Hypixel) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) blockPlace);
                        this.Timer.reset();
                    } else if (!Delay && !Hypixel) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) blockPlace);
                    } else if (Hypixel) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) blockMent);
                    }
                }

            }
        }
    }

    static void sendPacket$default(NoSlow noslow, MotionEvent motionevent, boolean flag, boolean flag1, boolean flag2, long i, boolean flag3, boolean flag4, int j, Object object) {
        if ((j & 64) != 0) {
            flag4 = false;
        }

        noslow.sendPacket(motionevent, flag, flag1, flag2, i, flag3, flag4);
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            this.fuckKotline(ientityplayersp1.getTicksExisted() & 1);
            IItemStack heldItem = thePlayer.getHeldItem();

            if (MovementUtils.isMoving()) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                String s;

                if (ientityplayersp.isUsingItem() && heldItem != null && !(heldItem.getItem() instanceof ItemSword)) {
                    String killAura1 = (String) this.modeValue.get();
                    boolean flag = false;

                    if (killAura1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s = killAura1.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    killAura1 = s;
                    switch (killAura1.hashCode()) {
                    case 2986066:
                        if (killAura1.equals("aac5") && this.OnPost(event)) {
                            IClassProvider iclassprovider = MinecraftInstance.classProvider;

                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iclassprovider.createCPacketPlayerBlockPlacement((IItemStack) Integer.valueOf(ientityplayersp1.getInventory().getCurrentItem()));
                        }
                    }
                } else {
                    Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

                    if (module == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                    }

                    KillAura killAura = (KillAura) module;

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!ientityplayersp.isBlocking() && !killAura.getBlockingStatus()) {
                        return;
                    }

                    String s1 = (String) this.modeValue.get();
                    boolean flag1 = false;

                    if (s1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s = s1.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    s1 = s;
                    IINetHandlerPlayClient iinethandlerplayclient;
                    IClassProvider iclassprovider1;
                    IItemStack iitemstack;
                    IEntityPlayerSP ientityplayersp2;

                    switch (s1.hashCode()) {
                    case -1703304794:
                        if (s1.equals("hauyuting") && event.getEventState() == EventState.PRE) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider1 = MinecraftInstance.classProvider;
                            ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp2 == null) {
                                Intrinsics.throwNpe();
                            }

                            iitemstack = ientityplayersp2.getInventory().getCurrentItemInHand();
                            if (iitemstack == null) {
                                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.item.IItemStack");
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerBlockPlacement(iitemstack));
                        }
                        break;

                    case -1349088399:
                        if (s1.equals("custom")) {
                            sendPacket$default(this, event, true, true, true, (long) ((Number) this.customDelayValue.get()).intValue(), ((Boolean) this.customOnGround.get()).booleanValue(), false, 64, (Object) null);
                        }
                        break;

                    case 72035:
                        if (s1.equals("HYT") && event.getEventState() == EventState.PRE) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider1 = MinecraftInstance.classProvider;
                            ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp2 == null) {
                                Intrinsics.throwNpe();
                            }

                            iitemstack = ientityplayersp2.getInventory().getCurrentItemInHand();
                            if (iitemstack == null) {
                                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.item.IItemStack");
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerBlockPlacement(iitemstack));
                        }
                        break;

                    case 2986066:
                        if (s1.equals("aac5")) {
                            sendPacket$default(this, event, true, true, true, 200L, false, false, 64, (Object) null);
                        }
                        break;

                    case 233102203:
                        if (s1.equals("vanilla")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionX(ientityplayersp1.getMotionX());
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionY(ientityplayersp1.getMotionY());
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionZ(ientityplayersp1.getMotionZ());
                        }
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (!this.modeValue.equals("Matrix") && (!this.modeValue.equals("Vulcan") || !this.nextTemp)) {
            if (packet instanceof CPacketPlayer || packet instanceof CPacketAnimation || packet instanceof CPacketEntityAction || packet instanceof CPacketUseEntity || packet instanceof CPacketPlayerDigging || packet instanceof ICPacketPlayerBlockPlacement) {
                if (this.modeValue.equals("Vulcan") && this.waitC03 && packet instanceof ICPacketPlayer) {
                    this.waitC03 = false;
                    return;
                }

                this.packetBuf.add((Packet) packet);
            }
        } else {
            if ((packet instanceof CPacketPlayerDigging || packet instanceof ICPacketPlayerBlockPlacement) && this.isBlocking()) {
                event.cancelEvent();
            }

            event.cancelEvent();
        }

    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if ((this.modeValue.equals("Matrix") || this.modeValue.equals("Vulcan")) && (this.lastBlockingStat || this.isBlocking())) {
            IClassProvider iclassprovider;

            if (this.msTimer.hasTimePassed(230L) && this.nextTemp) {
                this.nextTemp = false;
                iclassprovider = MinecraftInstance.classProvider;
                ICPacketPlayerDigging.WAction icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM;
                WBlockPos wblockpos = new WBlockPos(-1, -1, -1);
                EnumFacing enumfacing = EnumFacing.DOWN;

                if (EnumFacing.DOWN == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing");
                }

                iclassprovider.createCPacketPlayerDigging(icpacketplayerdigging_waction, wblockpos, (IEnumFacing) enumfacing);
                Collection canAttack = (Collection) this.packetBuf;
                boolean packet = false;

                if (!canAttack.isEmpty()) {
                    boolean canAttack1 = false;
                    Iterator iterator = this.packetBuf.iterator();

                    while (iterator.hasNext()) {
                        Packet packet1 = (Packet) iterator.next();

                        if (packet1 instanceof CPacketPlayer) {
                            canAttack1 = true;
                        }

                        if (!(packet1 instanceof ICPacketUseEntity) && !(packet1 instanceof ICPacketAnimation) || canAttack1) {
                            Intrinsics.checkExpressionValueIsNotNull(packet1, "packet");
                            PacketUtils.sendPacketNoEvent(packet1);
                        }
                    }

                    this.packetBuf.clear();
                }
            }

            if (!this.nextTemp) {
                this.lastBlockingStat = this.isBlocking();
                if (!this.isBlocking()) {
                    return;
                }

                iclassprovider = MinecraftInstance.classProvider;
                WBlockPos wblockpos1 = new WBlockPos(-1, -1, -1);
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                iclassprovider.createCPacketPlayerBlockPlacement(wblockpos1, 255, (IItemStack) Integer.valueOf(ientityplayersp.getInventory().getCurrentItem()), 0.0F, 0.0F, 0.0F);
                this.nextTemp = true;
                this.waitC03 = this.modeValue.equals("Vulcan");
                this.msTimer.reset();
            }
        }

    }

    @EventTarget
    public final void onSlowDown(@NotNull SlowDownEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IItemStack iitemstack = ientityplayersp.getHeldItem();
        IItem heldItem = iitemstack != null ? iitemstack.getItem() : null;

        event.setForward(this.getMultiplier(heldItem, true));
        event.setStrafe(this.getMultiplier(heldItem, false));
    }

    private final float getMultiplier(IItem item, boolean isForward) {
        return !MinecraftInstance.classProvider.isItemFood(item) && !MinecraftInstance.classProvider.isItemPotion(item) && !MinecraftInstance.classProvider.isItemBucketMilk(item) ? (MinecraftInstance.classProvider.isItemSword(item) ? (isForward ? ((Number) this.blockForwardMultiplier.get()).floatValue() : ((Number) this.blockStrafeMultiplier.get()).floatValue()) : (MinecraftInstance.classProvider.isItemBow(item) ? (isForward ? ((Number) this.bowForwardMultiplier.get()).floatValue() : ((Number) this.bowStrafeMultiplier.get()).floatValue()) : 0.2F)) : (isForward ? ((Number) this.consumeForwardMultiplier.get()).floatValue() : ((Number) this.consumeStrafeMultiplier.get()).floatValue());
    }

    @Nullable
    public String getTag() {
        return (String) this.modeValue.get();
    }

    public NoSlow() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        } else {
            this.killAura = (KillAura) module;
        }
    }
}
