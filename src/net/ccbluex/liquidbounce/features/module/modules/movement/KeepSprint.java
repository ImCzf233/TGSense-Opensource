package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "KeepSprint",
    category = ModuleCategory.MOVEMENT,
    description = "das"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/KeepSprint;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "motionValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onAttack", "", "attackEvent", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"}
)
public final class KeepSprint extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Vanilla", "Motion"}, "Vanilla");
    private final FloatValue motionValue = new FloatValue("SlowDown", 100.0F, 20.0F, 100.0F);

    @EventTarget
    public final void onAttack(@NotNull AttackEvent attackEvent) {
        Intrinsics.checkParameterIsNotNull(attackEvent, "attackEvent");
        IEntityLivingBase entity = attackEvent.getTargetEntity() instanceof IEntityLivingBase ? (IEntityLivingBase) attackEvent.getTargetEntity() : null;

        if (!((String) this.modeValue.get()).equals("Vanilla")) {
            if (entity != null) {
                double dist = 0.0D;
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (!ientityplayersp.getCapabilities().isCreativeMode()) {
                    IMovingObjectPosition imovingobjectposition = MinecraftInstance.mc.getObjectMouseOver();

                    if (imovingobjectposition == null) {
                        Intrinsics.throwNpe();
                    }

                    WVec3 wvec3 = imovingobjectposition.getHitVec();
                    IEntity ientity = MinecraftInstance.mc.getRenderViewEntity();

                    if (ientity == null) {
                        Intrinsics.throwNpe();
                    }

                    dist = wvec3.distanceTo(ientity.getPositionEyes(1.0F));
                    double val = 0.0D;

                    val = dist > 3.0D ? (100.0D - ((Number) this.motionValue.get()).doubleValue()) / 100.0D : 0.6D;
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionX(ientityplayersp.getMotionX() * val);
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * val);
                } else {
                    dist = (100.0D - ((Number) this.motionValue.get()).doubleValue()) / 100.0D;
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionX(ientityplayersp.getMotionX() * dist);
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * dist);
                }
            }

        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket $this$unwrap$iv = event.getPacket();
        boolean $i$f$unwrap = false;

        if ($this$unwrap$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        } else {
            Packet packet = ((PacketImpl) $this$unwrap$iv).getWrapped();

            if (((String) this.modeValue.get()).equals("Vanilla")) {
                if (packet instanceof CPacketEntityAction && ((CPacketEntityAction) packet).getAction() == Action.STOP_SPRINTING) {
                    event.cancelEvent();
                }

            }
        }
    }

    @NotNull
    public String getTag() {
        String s = (String) this.modeValue.get();
        Locale locale = Locale.getDefault();

        Intrinsics.checkExpressionValueIsNotNull(locale, "Locale.getDefault()");
        Locale locale1 = locale;
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase(locale1);

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase(locale)");
            s = s1;
            switch (s.hashCode()) {
            case -1068318794:
                if (s.equals("motion")) {
                    s1 = "% " + ((Number) this.motionValue.get()).floatValue();
                    return s1;
                }
                break;

            case 233102203:
                if (s.equals("vanilla")) {
                    s1 = "Vanilla";
                    return s1;
                }
            }

            s1 = (String) this.modeValue.get();
            return s1;
        }
    }
}
