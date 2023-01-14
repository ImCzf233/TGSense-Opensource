package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "LongJump",
    description = "Allows you to jump further.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0017H\u0007J\u0012\u0010\u0018\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/LongJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "canBoost", "", "canMineplexBoost", "jumped", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "ncpBoostValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "teleported", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class LongJump extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "NCP", "AACv1", "AACv2", "AACv3", "Mineplex", "Mineplex2", "Mineplex3", "Redesky"}, "NCP");
    private final FloatValue ncpBoostValue = new FloatValue("NCPBoost", 4.25F, 1.0F, 10.0F);
    private final BoolValue autoJumpValue = new BoolValue("AutoJump", false);
    private boolean jumped;
    private boolean canBoost;
    private boolean teleported;
    private boolean canMineplexBoost;

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (LadderJump.jumped) {
            MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * 1.08F);
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (this.jumped) {
                label117: {
                    String mode = (String) this.modeValue.get();

                    if (thePlayer.getOnGround() || thePlayer.getCapabilities().isFlying()) {
                        this.jumped = false;
                        this.canMineplexBoost = false;
                        if (StringsKt.equals(mode, "NCP", true)) {
                            thePlayer.setMotionX(0.0D);
                            thePlayer.setMotionZ(0.0D);
                        }

                        return;
                    }

                    boolean flag = false;
                    boolean flag1 = false;
                    LongJump $this$run = (LongJump) this;
                    boolean $i$a$-run-LongJump$onUpdate$1 = false;
                    boolean flag2 = false;

                    if (mode == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s = mode.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    String s1 = s;

                    switch (s1.hashCode()) {
                    case -1362669950:
                        if (s1.equals("mineplex")) {
                            thePlayer.setMotionY(thePlayer.getMotionY() + 0.01321D);
                            thePlayer.setJumpMovementFactor(0.08F);
                            MovementUtils.strafe$default(0.0F, 1, (Object) null);
                        }
                        break label117;

                    case 108891:
                        if (s1.equals("ncp")) {
                            MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * ($this$run.canBoost ? ((Number) $this$run.ncpBoostValue.get()).floatValue() : 1.0F));
                            $this$run.canBoost = false;
                        }
                        break label117;

                    case 92570110:
                        if (s1.equals("aacv1")) {
                            thePlayer.setMotionY(thePlayer.getMotionY() + 0.05999D);
                            MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * 1.08F);
                        }
                        break label117;

                    case 92570111:
                        if (!s1.equals("aacv2")) {
                            break label117;
                        }
                        break;

                    case 92570112:
                        if (s1.equals("aacv3") && thePlayer.getFallDistance() > 0.5F && !$this$run.teleported) {
                            double value = 3.0D;
                            IEnumFacing horizontalFacing = thePlayer.getHorizontalFacing();
                            double x = 0.0D;
                            double z = 0.0D;

                            if (horizontalFacing.isNorth()) {
                                z = -value;
                            } else if (horizontalFacing.isEast()) {
                                x = value;
                            } else if (horizontalFacing.isSouth()) {
                                z = value;
                            } else if (horizontalFacing.isWest()) {
                                x = -value;
                            }

                            thePlayer.setPosition(thePlayer.getPosX() + x, thePlayer.getPosY(), thePlayer.getPosZ() + z);
                            $this$run.teleported = true;
                        }
                        break label117;

                    case 706904560:
                        if (s1.equals("mineplex2") && $this$run.canMineplexBoost) {
                            thePlayer.setJumpMovementFactor(0.1F);
                            if (thePlayer.getFallDistance() > 1.5F) {
                                thePlayer.setJumpMovementFactor(0.0F);
                                thePlayer.setMotionY((double) -10.0F);
                            }

                            MovementUtils.strafe$default(0.0F, 1, (Object) null);
                        }
                        break label117;

                    case 706904561:
                        if (!s1.equals("mineplex3")) {
                            break label117;
                        }
                        break;

                    case 1083223725:
                        if (s1.equals("redesky")) {
                            thePlayer.setJumpMovementFactor(0.15F);
                            thePlayer.setMotionY(thePlayer.getMotionY() + (double) 0.05F);
                        }

                    default:
                        break label117;
                    }

                    thePlayer.setJumpMovementFactor(0.09F);
                    thePlayer.setMotionY(thePlayer.getMotionY() + 0.01321D);
                    thePlayer.setJumpMovementFactor(0.08F);
                    MovementUtils.strafe$default(0.0F, 1, (Object) null);
                }
            }

            if (((Boolean) this.autoJumpValue.get()).booleanValue() && thePlayer.getOnGround() && MovementUtils.isMoving()) {
                this.jumped = true;
                thePlayer.jump();
            }

        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            String mode = (String) this.modeValue.get();

            if (StringsKt.equals(mode, "mineplex3", true)) {
                if (thePlayer.getFallDistance() != 0.0F) {
                    thePlayer.setMotionY(thePlayer.getMotionY() + 0.037D);
                }
            } else if (StringsKt.equals(mode, "ncp", true) && !MovementUtils.isMoving() && this.jumped) {
                thePlayer.setMotionX(0.0D);
                thePlayer.setMotionZ(0.0D);
                event.zeroXZ();
            }

        }
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.jumped = true;
        this.canBoost = true;
        this.teleported = false;
        if (this.getState()) {
            String s = (String) this.modeValue.get();
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            switch (s.hashCode()) {
            case -1362669950:
                if (s.equals("mineplex")) {
                    event.setMotion(event.getMotion() * 4.08F);
                }
                break;

            case 706904560:
                if (s.equals("mineplex2")) {
                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.isCollidedHorizontally()) {
                        event.setMotion(2.31F);
                        this.canMineplexBoost = true;
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.setOnGround(false);
                    }
                }
            }
        }

    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
