package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "HighJump",
    description = "Allows you to jump higher.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0012H\u0007J\u0012\u0010\u0013\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/HighJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "glassValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "heightValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class HighJump extends Module {

    private final FloatValue heightValue = new FloatValue("Height", 2.0F, 1.1F, 5.0F);
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Vanilla", "Damage", "AACv3", "DAC", "Mineplex"}, "Vanilla");
    private final BoolValue glassValue = new BoolValue("OnlyGlassPane", false);

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;

        if (!((Boolean) this.glassValue.get()).booleanValue() || MinecraftInstance.classProvider.isBlockPane(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ())))) {
            String s = (String) this.modeValue.get();
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case -1362669950:
                    if (s.equals("mineplex") && !thePlayer.getOnGround()) {
                        MovementUtils.strafe(0.35F);
                    }
                    break;

                case -1339126929:
                    if (s.equals("damage") && thePlayer.getHurtTime() > 0 && thePlayer.getOnGround()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + (double) (0.42F * ((Number) this.heightValue.get()).floatValue()));
                    }
                    break;

                case 99206:
                    if (s.equals("dac") && !thePlayer.getOnGround()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.049999D);
                    }
                    break;

                case 92570112:
                    if (s.equals("aacv3") && !thePlayer.getOnGround()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.059D);
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onMove(@Nullable MoveEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (!((Boolean) this.glassValue.get()).booleanValue() || MinecraftInstance.classProvider.isBlockPane(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ())))) {
                if (!thePlayer.getOnGround()) {
                    String s = (String) this.modeValue.get();
                    String s1 = "mineplex";
                    boolean flag = false;

                    if (s == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s2 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                    String s3 = s2;

                    if (Intrinsics.areEqual(s1, s3)) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + (thePlayer.getFallDistance() == 0.0F ? 0.0499D : 0.05D));
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (!((Boolean) this.glassValue.get()).booleanValue() || MinecraftInstance.classProvider.isBlockPane(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ())))) {
                String s = (String) this.modeValue.get();
                boolean flag = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                } else {
                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    switch (s.hashCode()) {
                    case -1362669950:
                        if (s.equals("mineplex")) {
                            event.setMotion(0.47F);
                        }
                        break;

                    case 233102203:
                        if (s.equals("vanilla")) {
                            event.setMotion(event.getMotion() * ((Number) this.heightValue.get()).floatValue());
                        }
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
