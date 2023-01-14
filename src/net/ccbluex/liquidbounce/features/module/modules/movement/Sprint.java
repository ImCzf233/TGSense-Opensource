package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;

@ModuleInfo(
    name = "Sprint",
    description = "Automatically sprints all the time.",
    category = ModuleCategory.MOVEMENT
)
public class Sprint extends Module {

    public final BoolValue allDirectionsValue = new BoolValue("AllDirections", true);
    public final BoolValue blindnessValue = new BoolValue("Blindness", true);
    public final BoolValue foodValue = new BoolValue("Food", true);
    public final BoolValue checkServerSide = new BoolValue("CheckServerSide", false);
    public final BoolValue checkServerSideGround = new BoolValue("CheckServerSideOnlyGround", false);

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (MovementUtils.isMoving() && !Sprint.mc.getThePlayer().isSneaking() && (!((Boolean) this.blindnessValue.get()).booleanValue() || !Sprint.mc.getThePlayer().isPotionActive(Sprint.classProvider.getPotionEnum(PotionType.BLINDNESS))) && (!((Boolean) this.foodValue.get()).booleanValue() || (float) Sprint.mc.getThePlayer().getFoodStats().getFoodLevel() > 6.0F || Sprint.mc.getThePlayer().getCapabilities().getAllowFlying()) && (!((Boolean) this.checkServerSide.get()).booleanValue() || !Sprint.mc.getThePlayer().getOnGround() && ((Boolean) this.checkServerSideGround.get()).booleanValue() || ((Boolean) this.allDirectionsValue.get()).booleanValue() || RotationUtils.targetRotation == null || RotationUtils.getRotationDifference(new Rotation(Sprint.mc.getThePlayer().getRotationYaw(), Sprint.mc.getThePlayer().getRotationPitch())) <= 30.0D)) {
            if (((Boolean) this.allDirectionsValue.get()).booleanValue() || Sprint.mc.getThePlayer().getMovementInput().getMoveForward() >= 0.8F) {
                Sprint.mc.getThePlayer().setSprinting(true);
            }

        } else {
            Sprint.mc.getThePlayer().setSprinting(false);
        }
    }
}
