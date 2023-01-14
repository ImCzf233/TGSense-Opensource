package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoKillAura",
    description = "AutoKillAura",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\b¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KAHelper;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "AutoDisable", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAutoDisable", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "setAutoDisable", "(Lnet/ccbluex/liquidbounce/value/BoolValue;)V", "AutoDisable2", "getAutoDisable2", "setAutoDisable2", "AutoDisable3", "getAutoDisable3", "setAutoDisable3", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class KAHelper extends Module {

    @NotNull
    private BoolValue AutoDisable = new BoolValue("AutoDisableKillAura1", true);
    @NotNull
    private BoolValue AutoDisable2 = new BoolValue("AutoDisableKillAura2", true);
    @NotNull
    private BoolValue AutoDisable3 = new BoolValue("AutoDisableKillAura3", true);

    @NotNull
    public final BoolValue getAutoDisable() {
        return this.AutoDisable;
    }

    public final void setAutoDisable(@NotNull BoolValue <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.AutoDisable = <set-?>;
    }

    @NotNull
    public final BoolValue getAutoDisable2() {
        return this.AutoDisable2;
    }

    public final void setAutoDisable2(@NotNull BoolValue <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.AutoDisable2 = <set-?>;
    }

    @NotNull
    public final BoolValue getAutoDisable3() {
        return this.AutoDisable3;
    }

    public final void setAutoDisable3(@NotNull BoolValue <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.AutoDisable3 = <set-?>;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        } else {
            KillAura KillAura1 = (KillAura) module;

            module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura2.class);
            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura2");
            } else {
                KillAura2 KillAura2 = (KillAura2) module;

                module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura3.class);
                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura3");
                } else {
                    KillAura3 KillAura3 = (KillAura3) module;

                    if (((Boolean) this.AutoDisable.get()).booleanValue()) {
                        KillAura1.setState(true);
                        KillAura2.setState(false);
                        KillAura3.setState(false);
                        if (((Boolean) this.AutoDisable2.get()).booleanValue()) {
                            KillAura1.setState(false);
                        }

                        KillAura2.setState(true);
                        KillAura3.setState(false);
                        if (((Boolean) this.AutoDisable3.get()).booleanValue()) {
                            KillAura1.setState(false);
                        }

                        KillAura2.setState(false);
                        KillAura3.setState(true);
                    }

                }
            }
        }
    }
}
