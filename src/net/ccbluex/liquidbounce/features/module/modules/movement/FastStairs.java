package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "FastStairs",
    description = "Allows you to climb up stairs faster.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/FastStairs;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canJump", "", "longJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "walkingDown", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class FastStairs extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Step", "NCP", "AAC3.1.0", "AAC3.3.6", "AAC3.3.13"}, "NCP");
    private final BoolValue longJumpValue = new BoolValue("LongJump", false);
    private boolean canJump;
    private boolean walkingDown;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (MovementUtils.isMoving()) {
                Module module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);

                if (module == null) {
                    Intrinsics.throwNpe();
                }

                if (!module.getState()) {
                    if (thePlayer.getFallDistance() > (float) 0 && !this.walkingDown) {
                        this.walkingDown = true;
                    } else if (thePlayer.getPosY() > thePlayer.getPrevChasingPosY()) {
                        this.walkingDown = false;
                    }

                    String mode = (String) this.modeValue.get();

                    if (!thePlayer.getOnGround()) {
                        return;
                    }

                    WBlockPos blockPos = new WBlockPos(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ());
                    double motion;

                    if (MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(blockPos)) && !this.walkingDown) {
                        thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.5D, thePlayer.getPosZ());
                        motion = StringsKt.equals(mode, "NCP", true) ? 1.4D : (StringsKt.equals(mode, "AAC3.1.0", true) ? 1.5D : (StringsKt.equals(mode, "AAC3.3.13", true) ? 1.2D : 1.0D));
                        thePlayer.setMotionX(thePlayer.getMotionX() * motion);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * motion);
                    }

                    if (MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(blockPos.down()))) {
                        if (this.walkingDown) {
                            if (StringsKt.equals(mode, "NCP", true)) {
                                thePlayer.setMotionY(-1.0D);
                            } else if (StringsKt.equals(mode, "AAC3.3.13", true)) {
                                thePlayer.setMotionY(thePlayer.getMotionY() - 0.014D);
                            }

                            return;
                        }

                        motion = StringsKt.equals(mode, "NCP", true) ? 1.3D : (StringsKt.equals(mode, "AAC3.1.0", true) ? 1.3D : (StringsKt.equals(mode, "AAC3.3.6", true) ? 1.48D : (StringsKt.equals(mode, "AAC3.3.13", true) ? 1.52D : 1.3D)));
                        thePlayer.setMotionX(thePlayer.getMotionX() * motion);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * motion);
                        this.canJump = true;
                    } else if (StringsKt.startsWith(mode, "AAC", true) && this.canJump) {
                        if (((Boolean) this.longJumpValue.get()).booleanValue()) {
                            thePlayer.jump();
                            thePlayer.setMotionX(thePlayer.getMotionX() * 1.35D);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.35D);
                        }

                        this.canJump = false;
                    }

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
