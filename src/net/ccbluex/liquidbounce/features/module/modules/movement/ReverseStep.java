package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "ReverseStep",
    description = "Allows you to step down blocks faster.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u0012\u0010\u000b\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/ReverseStep;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jumped", "", "motionValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class ReverseStep extends Module {

    private final FloatValue motionValue = new FloatValue("Motion", 1.0F, 0.21F, 1.0F);
    private boolean jumped;

    @EventTarget(
        ignoreCondition = true
    )
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (thePlayer.getOnGround()) {
                this.jumped = false;
            }

            if (thePlayer.getMotionY() > (double) 0) {
                this.jumped = true;
            }

            if (this.getState()) {
                if (!BlockUtils.collideBlock(thePlayer.getEntityBoundingBox(), (Function1) (new Function1(1, MinecraftInstance.classProvider) {
                    public final boolean invoke(@Nullable Object p1) {
                        return ((IClassProvider) this.receiver).isBlockLiquid(p1);
                    }

                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(IClassProvider.class);
                    }

                    public final String getName() {
                        return "isBlockLiquid";
                    }

                    public final String getSignature() {
                        return "isBlockLiquid(Ljava/lang/Object;)Z";
                    }
                })) && !BlockUtils.collideBlock(MinecraftInstance.classProvider.createAxisAlignedBB(thePlayer.getEntityBoundingBox().getMaxX(), thePlayer.getEntityBoundingBox().getMaxY(), thePlayer.getEntityBoundingBox().getMaxZ(), thePlayer.getEntityBoundingBox().getMinX(), thePlayer.getEntityBoundingBox().getMinY() - 0.01D, thePlayer.getEntityBoundingBox().getMinZ()), (Function1) (new Function1(1, MinecraftInstance.classProvider) {
                    public final boolean invoke(@Nullable Object p1) {
                        return ((IClassProvider) this.receiver).isBlockLiquid(p1);
                    }

                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(IClassProvider.class);
                    }

                    public final String getName() {
                        return "isBlockLiquid";
                    }

                    public final String getSignature() {
                        return "isBlockLiquid(Ljava/lang/Object;)Z";
                    }
                }))) {
                    if (!MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown() && !thePlayer.getOnGround() && !thePlayer.getMovementInput().getJump() && thePlayer.getMotionY() <= 0.0D && thePlayer.getFallDistance() <= 1.0F && !this.jumped) {
                        thePlayer.setMotionY((double) (-((Number) this.motionValue.get()).floatValue()));
                    }

                }
            }
        }
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onJump(@Nullable JumpEvent event) {
        this.jumped = true;
    }
}
