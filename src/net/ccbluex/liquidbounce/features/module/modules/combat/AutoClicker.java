package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoClicker",
    description = "Constantly clicks when holding down a mouse button.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jitterValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "leftDelay", "", "leftLastSwing", "leftValue", "maxCPSValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minCPSValue", "rightDelay", "rightLastSwing", "rightValue", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AutoClicker extends Module {

    private final IntegerValue maxCPSValue = (IntegerValue) (new IntegerValue("MaxCPS", 8, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int minCPS = ((Number) AutoClicker.this.minCPSValue.get()).intValue();

            if (minCPS > newValue) {
                this.set((Object) Integer.valueOf(minCPS));
            }

        }
    });
    private final IntegerValue minCPSValue = (IntegerValue) (new IntegerValue("MinCPS", 5, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int maxCPS = ((Number) AutoClicker.this.maxCPSValue.get()).intValue();

            if (maxCPS < newValue) {
                this.set((Object) Integer.valueOf(maxCPS));
            }

        }
    });
    private final BoolValue rightValue = new BoolValue("Right", true);
    private final BoolValue leftValue = new BoolValue("Left", true);
    private final BoolValue jitterValue = new BoolValue("Jitter", false);
    private long rightDelay;
    private long rightLastSwing;
    private long leftDelay;
    private long leftLastSwing;

    @EventTarget
    public final void onRender(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getGameSettings().getKeyBindAttack().isKeyDown() && ((Boolean) this.leftValue.get()).booleanValue() && System.currentTimeMillis() - this.leftLastSwing >= this.leftDelay && MinecraftInstance.mc.getPlayerController().getCurBlockDamageMP() == 0.0F) {
            MinecraftInstance.mc.getGameSettings().getKeyBindAttack().onTick(MinecraftInstance.mc.getGameSettings().getKeyBindAttack().getKeyCode());
            this.leftLastSwing = System.currentTimeMillis();
            this.leftDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
        }

        if (MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().isKeyDown()) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (!ientityplayersp.isUsingItem() && ((Boolean) this.rightValue.get()).booleanValue() && System.currentTimeMillis() - this.rightLastSwing >= this.rightDelay) {
                MinecraftInstance.mc.getGameSettings().getKeyBindAttack().onTick(MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().getKeyCode());
                this.rightLastSwing = System.currentTimeMillis();
                this.rightDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
            }
        }

    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (((Boolean) this.jitterValue.get()).booleanValue() && (((Boolean) this.leftValue.get()).booleanValue() && MinecraftInstance.mc.getGameSettings().getKeyBindAttack().isKeyDown() && MinecraftInstance.mc.getPlayerController().getCurBlockDamageMP() == 0.0F || ((Boolean) this.rightValue.get()).booleanValue() && MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().isKeyDown() && !thePlayer.isUsingItem())) {
                if (Random.Default.nextBoolean()) {
                    thePlayer.setRotationYaw(thePlayer.getRotationYaw() + (Random.Default.nextBoolean() ? -RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F) : RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F)));
                }

                if (Random.Default.nextBoolean()) {
                    thePlayer.setRotationPitch(thePlayer.getRotationPitch() + (Random.Default.nextBoolean() ? -RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F) : RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F)));
                    if (thePlayer.getRotationPitch() > (float) 90) {
                        thePlayer.setRotationPitch(90.0F);
                    } else if (thePlayer.getRotationPitch() < (float) -90) {
                        thePlayer.setRotationPitch(-90.0F);
                    }
                }
            }

        }
    }

    public AutoClicker() {
        this.rightDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
        this.leftDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
    }
}
