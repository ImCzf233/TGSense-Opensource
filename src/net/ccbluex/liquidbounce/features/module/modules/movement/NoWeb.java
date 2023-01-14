package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "NoWeb",
    description = "Prevents you from getting slowed down in webs.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoWeb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "usedTimer", "", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class NoWeb extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "None", "AAC", "LAAC", "Rewi", "Matrix", "Spartan", "AAC5"}, "None");
    private boolean usedTimer;

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

            if (thePlayer.isInWeb()) {
                String s = (String) this.modeValue.get();
                boolean flag = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                } else {
                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    switch (s.hashCode()) {
                    case -2011701869:
                        if (s.equals("spartan")) {
                            MovementUtils.strafe(0.27F);
                            MinecraftInstance.mc.getTimer().setTimerSpeed(3.7F);
                            if (!MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionY(0.0D);
                            }

                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getTicksExisted() % 2 == 0) {
                                MinecraftInstance.mc.getTimer().setTimerSpeed(1.7F);
                            }

                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getTicksExisted() % 40 == 0) {
                                MinecraftInstance.mc.getTimer().setTimerSpeed(3.0F);
                            }

                            this.usedTimer = true;
                        }
                        break;

                    case -1081239615:
                        if (s.equals("matrix")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setJumpMovementFactor(0.12425F);
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionY(-0.0125D);
                            if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionY(-0.1625D);
                            }

                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getTicksExisted() % 40 == 0) {
                                MinecraftInstance.mc.getTimer().setTimerSpeed(3.0F);
                                this.usedTimer = true;
                            }
                        }
                        break;

                    case 96323:
                        if (s.equals("aac")) {
                            thePlayer.setJumpMovementFactor(0.59F);
                            if (!MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                                thePlayer.setMotionY(0.0D);
                            }
                        }
                        break;

                    case 2986066:
                        if (s.equals("aac5")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setJumpMovementFactor(0.42F);
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getOnGround()) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.jump();
                            }
                        }
                        break;

                    case 3313751:
                        if (s.equals("laac")) {
                            thePlayer.setJumpMovementFactor(thePlayer.getMovementInput().getMoveStrafe() != 0.0F ? 1.0F : 1.21F);
                            if (!MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                                thePlayer.setMotionY(0.0D);
                            }

                            if (thePlayer.getOnGround()) {
                                thePlayer.jump();
                            }
                        }
                        break;

                    case 3387192:
                        if (s.equals("none")) {
                            thePlayer.setInWeb(false);
                        }
                        break;

                    case 3497029:
                        if (s.equals("rewi")) {
                            thePlayer.setJumpMovementFactor(0.42F);
                            if (thePlayer.getOnGround()) {
                                thePlayer.jump();
                            }
                        }
                    }

                }
            }
        }
    }

    @Nullable
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
