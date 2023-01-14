package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "SlimeJump",
    description = "Allows you to to jump higher on slime blocks.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/SlimeJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "motionValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "LiquidBounce"}
)
public final class SlimeJump extends Module {

    private final FloatValue motionValue = new FloatValue("Motion", 0.42F, 0.2F, 1.0F);
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Set", "Add"}, "Add");

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (MinecraftInstance.mc.getThePlayer() != null && MinecraftInstance.mc.getTheWorld() != null && MinecraftInstance.classProvider.isBlockSlime(BlockUtils.getBlock(thePlayer.getPosition().down()))) {
                event.cancelEvent();
                String s = (String) this.modeValue.get();
                boolean flag = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case 96417:
                    if (s.equals("add")) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + ((Number) this.motionValue.get()).doubleValue());
                    }
                    break;

                case 113762:
                    if (s.equals("set")) {
                        thePlayer.setMotionY((double) ((Number) this.motionValue.get()).floatValue());
                    }
                }
            }

        }
    }
}
