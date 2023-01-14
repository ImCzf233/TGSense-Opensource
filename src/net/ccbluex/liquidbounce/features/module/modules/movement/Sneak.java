package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Sneak",
    description = "Automatically sneaks all the time.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Sneak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "sneaking", "", "stopMoveValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onWorld", "worldEvent", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class Sneak extends Module {

    @JvmField
    @NotNull
    public final ListValue modeValue = new ListValue("Mode", new String[] { "Legit", "Vanilla", "Switch", "MineSecure"}, "MineSecure");
    @JvmField
    @NotNull
    public final BoolValue stopMoveValue = new BoolValue("StopMove", false);
    private boolean sneaking;

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.stopMoveValue.get()).booleanValue() && MovementUtils.isMoving()) {
            if (this.sneaking) {
                this.onDisable();
            }

        } else {
            String s = (String) this.modeValue.get();
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                IINetHandlerPlayClient iinethandlerplayclient;
                IClassProvider iclassprovider;
                IEntityPlayerSP ientityplayersp;

                switch (s.hashCode()) {
                case -889473228:
                    if (s.equals("switch")) {
                        switch (Sneak$WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                        case 1:
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.START_SNEAKING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.STOP_SNEAKING));
                            break;

                        case 2:
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.STOP_SNEAKING));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.START_SNEAKING));
                        }
                    }
                    break;

                case 102851513:
                    if (s.equals("legit")) {
                        MinecraftInstance.mc.getGameSettings().getKeyBindSneak().setPressed(true);
                    }
                    break;

                case 233102203:
                    if (s.equals("vanilla")) {
                        if (this.sneaking) {
                            return;
                        }

                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        iclassprovider = MinecraftInstance.classProvider;
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.START_SNEAKING));
                    }
                    break;

                case 518567306:
                    if (s.equals("minesecure")) {
                        if (event.getEventState() == EventState.PRE) {
                            return;
                        }

                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        iclassprovider = MinecraftInstance.classProvider;
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.START_SNEAKING));
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent worldEvent) {
        Intrinsics.checkParameterIsNotNull(worldEvent, "worldEvent");
        this.sneaking = false;
    }

    public void onDisable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP player = ientityplayersp;
            String s = (String) this.modeValue.get();
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                label32: {
                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    switch (s.hashCode()) {
                    case -889473228:
                        if (!s.equals("switch")) {
                            break label32;
                        }
                        break;

                    case 102851513:
                        if (s.equals("legit") && !MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindSneak())) {
                            MinecraftInstance.mc.getGameSettings().getKeyBindSneak().setPressed(false);
                        }
                        break label32;

                    case 233102203:
                        if (!s.equals("vanilla")) {
                            break label32;
                        }
                        break;

                    case 518567306:
                        if (!s.equals("minesecure")) {
                            break label32;
                        }
                        break;

                    default:
                        break label32;
                    }

                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketEntityAction((IEntity) player, ICPacketEntityAction.WAction.STOP_SNEAKING));
                }

                this.sneaking = false;
            }
        }
    }
}
