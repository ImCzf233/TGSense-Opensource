package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.AAC4Hop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.AAC4SlowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.CustomSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.SlowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spartan.SpartanYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.vulcan.VulcanHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.vulcan.VulcanHop2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.vulcan.VulcanYPort;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "Speed",
    description = "Allows you to move faster.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010%\u001a\u00020&H\u0016J\b\u0010\'\u001a\u00020&H\u0016J\u0010\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020*H\u0007J\u0012\u0010+\u001a\u00020&2\b\u0010)\u001a\u0004\u0018\u00010,H\u0007J\u0012\u0010-\u001a\u00020&2\b\u0010)\u001a\u0004\u0018\u00010.H\u0007J\u0012\u0010/\u001a\u00020&2\b\u0010)\u001a\u0004\u0018\u000100H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\nR\u0011\u0010\u001e\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\nR\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00100\u0018X\u0082\u0004¢\u0006\u0004\n\u0002\u0010!R\u0014\u0010\"\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$¨\u00061"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "customSpeedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getCustomSpeedValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "customStrafeValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getCustomStrafeValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "customTimerValue", "getCustomTimerValue", "customYValue", "getCustomYValue", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "modes", "", "", "getModes", "()[Ljava/lang/String;", "resetXZValue", "getResetXZValue", "resetYValue", "getResetYValue", "speedModes", "[Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "tag", "getTag", "()Ljava/lang/String;", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Speed extends Module {

    private final SpeedMode[] speedModes = new SpeedMode[] { (SpeedMode) (new VulcanHop()), (SpeedMode) (new VulcanHop2()), (SpeedMode) (new VulcanYPort()), (SpeedMode) (new AAC4Hop()), (SpeedMode) (new AAC4SlowHop()), (SpeedMode) (new SpartanYPort()), (SpeedMode) (new SlowHop()), (SpeedMode) (new CustomSpeed())};
    @NotNull
    private final ListValue modeValue = (ListValue) (new ListValue("Mode", this.getModes(), "NCPBHop") {
        protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
            Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
            Intrinsics.checkParameterIsNotNull(newValue, "newValue");
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }

        }

        protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
            Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
            Intrinsics.checkParameterIsNotNull(newValue, "newValue");
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }

        }
    });
    @NotNull
    private final FloatValue customSpeedValue = new FloatValue("CustomSpeed", 1.6F, 0.2F, 2.0F);
    @NotNull
    private final FloatValue customYValue = new FloatValue("CustomY", 0.0F, 0.0F, 4.0F);
    @NotNull
    private final FloatValue customTimerValue = new FloatValue("CustomTimer", 1.0F, 0.1F, 2.0F);
    @NotNull
    private final BoolValue customStrafeValue = new BoolValue("CustomStrafe", true);
    @NotNull
    private final BoolValue resetXZValue = new BoolValue("CustomResetXZ", false);
    @NotNull
    private final BoolValue resetYValue = new BoolValue("CustomResetY", false);

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final FloatValue getCustomSpeedValue() {
        return this.customSpeedValue;
    }

    @NotNull
    public final FloatValue getCustomYValue() {
        return this.customYValue;
    }

    @NotNull
    public final FloatValue getCustomTimerValue() {
        return this.customTimerValue;
    }

    @NotNull
    public final BoolValue getCustomStrafeValue() {
        return this.customStrafeValue;
    }

    @NotNull
    public final BoolValue getResetXZValue() {
        return this.resetXZValue;
    }

    @NotNull
    public final BoolValue getResetYValue() {
        return this.resetYValue;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (!thePlayer.isSneaking()) {
                if (MovementUtils.isMoving()) {
                    thePlayer.setSprinting(true);
                }

                SpeedMode speedmode = this.getMode();

                if (speedmode != null) {
                    speedmode.onUpdate();
                }

            }
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (!thePlayer.isSneaking() && event.getEventState() == EventState.PRE) {
                if (MovementUtils.isMoving()) {
                    thePlayer.setSprinting(true);
                }

                SpeedMode speedmode = this.getMode();

                if (speedmode != null) {
                    speedmode.onMotion(event);
                }

            }
        }
    }

    @EventTarget
    public final void onMove(@Nullable MoveEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (!ientityplayersp.isSneaking()) {
            SpeedMode speedmode = this.getMode();

            if (speedmode != null) {
                if (event == null) {
                    Intrinsics.throwNpe();
                }

                speedmode.onMove(event);
            }

        }
    }

    @EventTarget
    public final void onTick(@Nullable TickEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (!ientityplayersp.isSneaking()) {
            SpeedMode speedmode = this.getMode();

            if (speedmode != null) {
                speedmode.onTick();
            }

        }
    }

    public void onEnable() {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
            SpeedMode speedmode = this.getMode();

            if (speedmode != null) {
                speedmode.onEnable();
            }

        }
    }

    public void onDisable() {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
            SpeedMode speedmode = this.getMode();

            if (speedmode != null) {
                speedmode.onDisable();
            }

        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    private final SpeedMode getMode() {
        String mode = (String) this.modeValue.get();
        SpeedMode[] aspeedmode = this.speedModes;
        int i = aspeedmode.length;

        for (int j = 0; j < i; ++j) {
            SpeedMode speedMode = aspeedmode[j];

            if (StringsKt.equals(speedMode.getModeName(), mode, true)) {
                return speedMode;
            }
        }

        return null;
    }

    private final String[] getModes() {
        List list = (List) (new ArrayList());
        SpeedMode[] thisCollection$iv = this.speedModes;
        int i = thisCollection$iv.length;

        for (int $i$f$toTypedArray = 0; $i$f$toTypedArray < i; ++$i$f$toTypedArray) {
            SpeedMode $this$toTypedArray$iv = thisCollection$iv[$i$f$toTypedArray];

            list.add($this$toTypedArray$iv.getModeName());
        }

        Collection collection = (Collection) list;
        boolean flag = false;
        Object[] aobject = collection.toArray(new String[0]);

        if (aobject == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } else {
            return (String[]) aobject;
        }
    }
}
