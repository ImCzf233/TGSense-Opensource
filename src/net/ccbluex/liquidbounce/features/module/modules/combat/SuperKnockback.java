package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.utils.player.PlayerUtil;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "SuperKnockback",
    description = "Fix by JIEMO",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0017"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/SuperKnockback;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hurtTimeValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onlyGroundValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onlyMoveValue", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "LiquidBounce"}
)
public final class SuperKnockback extends Module {

    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Normal", "MCYC", "ExtraPacket", "WTap", "Packet", "HYTPacket", "Tick"}, "Normal");
    private final BoolValue onlyMoveValue = new BoolValue("OnlyMove", false);
    private final BoolValue onlyGroundValue = new BoolValue("OnlyGround", false);
    private final IntegerValue delay = new IntegerValue("Delay", 0, 0, 500);
    @NotNull
    private final MSTimer timer = new MSTimer();

    @NotNull
    public final MSTimer getTimer() {
        return this.timer;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.getTargetEntity() instanceof EntityLivingBase) {
            if (((EntityLivingBase) event.getTargetEntity()).hurtTime <= ((Number) this.hurtTimeValue.get()).intValue() && this.timer.hasTimePassed((long) ((Number) this.delay.get()).intValue()) && (PlayerUtil.isMoving() || !((Boolean) this.onlyMoveValue.get()).booleanValue())) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getOnGround() || !((Boolean) this.onlyGroundValue.get()).booleanValue()) {
                    String s = (String) this.modeValue.get();
                    EntityPlayerSP entityplayersp;
                    IINetHandlerPlayClient iinethandlerplayclient;
                    IClassProvider iclassprovider;
                    IEntityPlayerSP ientityplayersp1;

                    switch (s.hashCode()) {
                    case -2117036904:
                        if (s.equals("extrapacket")) {
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            if (entityplayersp.isSprinting()) {
                                entityplayersp = MinecraftInstance.mc2.player;
                                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                                entityplayersp.setSprinting(true);
                            }

                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setServerSprintState(true);
                        }
                        break;

                    case -1039745817:
                        if (s.equals("normal")) {
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            if (entityplayersp.isSprinting()) {
                                entityplayersp = MinecraftInstance.mc2.player;
                                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                                entityplayersp.setSprinting(true);
                            }

                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setServerSprintState(true);
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                        }
                        break;

                    case -995865464:
                        if (s.equals("packet")) {
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            if (entityplayersp.isSprinting()) {
                                entityplayersp = MinecraftInstance.mc2.player;
                                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                                entityplayersp.setSprinting(true);
                            }

                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setServerSprintState(true);
                        }
                        break;

                    case 3346208:
                        if (s.equals("mcyc")) {
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            if (entityplayersp.isSprinting()) {
                                entityplayersp = MinecraftInstance.mc2.player;
                                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                                entityplayersp.setSprinting(false);
                            }

                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            entityplayersp.setSprinting(true);
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setServerSprintState(true);
                        }
                        break;

                    case 3559837:
                        if (s.equals("tick")) {
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            if (entityplayersp.isSprinting()) {
                                entityplayersp = MinecraftInstance.mc2.player;
                                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                                entityplayersp.setSprinting(false);
                            }

                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setServerSprintState(true);
                        }
                        break;

                    case 3659724:
                        if (s.equals("wtap")) {
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            if (entityplayersp.isSprinting()) {
                                entityplayersp = MinecraftInstance.mc2.player;
                                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                                entityplayersp.setSprinting(false);
                            }

                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setServerSprintState(true);
                        }
                        break;

                    case 305296331:
                        if (s.equals("hytpacket")) {
                            entityplayersp = MinecraftInstance.mc2.player;
                            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                            if (entityplayersp.isSprinting()) {
                                entityplayersp = MinecraftInstance.mc2.player;
                                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                                entityplayersp.setSprinting(true);
                            }

                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SPRINTING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.STOP_SPRINTING));
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setServerSprintState(true);
                        }
                    }

                    this.timer.reset();
                    return;
                }
            }

        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
