package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura4;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "NoLagBack",
    description = "./",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/NoLagBack;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "a", "", "b", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "ticks", "onEnable", "", "onUpdate", "LiquidBounce"}
)
public final class NoLagBack extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "AntiCheat", "AAC5"}, "AAC5");
    private int ticks;
    private int a;
    private int b;

    public void onEnable() {
        this.ticks = 0;
    }

    @EventTarget
    public final void onUpdate() {
        String s = (String) this.modeValue.get();
        boolean Killaura = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            IEntityPlayerSP ientityplayersp;

            switch (s.hashCode()) {
            case -2116882767:
                if (s.equals("anticheat")) {
                    if (this.ticks > 1000) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.isOnLadder() && MinecraftInstance.mc.getGameSettings().getKeyBindJump().getPressed()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionY(0.11D);
                        }
                    }

                    if (this.ticks > 2000) {
                        this.ticks = 0;
                    } else {
                        int i = this.ticks++;
                    }
                }
                break;

            case 2986066:
                if (s.equals("aac5")) {
                    Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura4.class);

                    if (module == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura4");
                    }

                    KillAura4 killaura4 = (KillAura4) module;

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    int j;

                    if (ientityplayersp.getOnGround()) {
                        if (this.b == 0) {
                            killaura4.getKeepSprintValue().set(Boolean.valueOf(true));
                            j = this.b++;
                        }
                    } else {
                        this.b = 0;
                        if (this.a == 0) {
                            killaura4.getKeepSprintValue().set(Boolean.valueOf(false));
                            j = this.a++;
                        }
                    }

                    if (this.ticks > 250) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.isOnLadder() && MinecraftInstance.mc.getGameSettings().getKeyBindJump().getPressed()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionY(0.11D);
                        }
                    }

                    if (this.ticks > 500) {
                        this.ticks = 0;
                    } else {
                        j = this.ticks++;
                    }
                }
            }

        }
    }

    @NotNull
    public String getTag() {
        return ((String) this.modeValue.get()).toString();
    }
}
