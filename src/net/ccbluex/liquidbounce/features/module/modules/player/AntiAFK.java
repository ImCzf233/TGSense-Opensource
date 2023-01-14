package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AntiAFK",
    description = "Prevents you from getting kicked for being AFK.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiAFK;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "jumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "moveValue", "randomTimerDelay", "", "rotateValue", "rotationAngleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "shouldMove", "", "swingDelayTimer", "swingDelayValue", "swingValue", "getRandomMoveKeyBind", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AntiAFK extends Module {

    private final MSTimer swingDelayTimer = new MSTimer();
    private final MSTimer delayTimer = new MSTimer();
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Old", "Random", "Custom"}, "Random");
    private final IntegerValue swingDelayValue = new IntegerValue("SwingDelay", 100, 0, 1000);
    private final IntegerValue rotationDelayValue = new IntegerValue("RotationDelay", 100, 0, 1000);
    private final FloatValue rotationAngleValue = new FloatValue("RotationAngle", 1.0F, -180.0F, 180.0F);
    private final BoolValue jumpValue = new BoolValue("Jump", true);
    private final BoolValue moveValue = new BoolValue("Move", true);
    private final BoolValue rotateValue = new BoolValue("Rotate", true);
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private boolean shouldMove;
    private long randomTimerDelay = 500L;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            String s = (String) this.modeValue.get();
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case -1349088399:
                    if (s.equals("custom")) {
                        if (((Boolean) this.moveValue.get()).booleanValue()) {
                            MinecraftInstance.mc.getGameSettings().getKeyBindForward().setPressed(true);
                        }

                        if (((Boolean) this.jumpValue.get()).booleanValue() && thePlayer.getOnGround()) {
                            thePlayer.jump();
                        }

                        if (((Boolean) this.rotateValue.get()).booleanValue() && this.delayTimer.hasTimePassed((long) ((Number) this.rotationDelayValue.get()).intValue())) {
                            thePlayer.setRotationYaw(thePlayer.getRotationYaw() + ((Number) this.rotationAngleValue.get()).floatValue());
                            if (thePlayer.getRotationPitch() <= (float) -90 || thePlayer.getRotationPitch() >= (float) 90) {
                                thePlayer.setRotationPitch(0.0F);
                            }

                            thePlayer.setRotationPitch(thePlayer.getRotationPitch() + (RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F) * (float) 2 - (float) 1));
                            this.delayTimer.reset();
                        }

                        if (((Boolean) this.swingValue.get()).booleanValue() && !thePlayer.isSwingInProgress() && this.swingDelayTimer.hasTimePassed((long) ((Number) this.swingDelayValue.get()).intValue())) {
                            thePlayer.swingItem();
                            this.swingDelayTimer.reset();
                        }
                    }
                    break;

                case -938285885:
                    if (s.equals("random")) {
                        IKeyBinding ikeybinding = this.getRandomMoveKeyBind();

                        if (ikeybinding == null) {
                            Intrinsics.throwNpe();
                        }

                        ikeybinding.setPressed(this.shouldMove);
                        if (!this.delayTimer.hasTimePassed(this.randomTimerDelay)) {
                            return;
                        }

                        this.shouldMove = false;
                        this.randomTimerDelay = 500L;
                        switch (RandomUtils.nextInt(0, 6)) {
                        case 0:
                            if (thePlayer.getOnGround()) {
                                thePlayer.jump();
                            }

                            this.delayTimer.reset();
                            break;

                        case 1:
                            if (!thePlayer.isSwingInProgress()) {
                                thePlayer.swingItem();
                            }

                            this.delayTimer.reset();
                            break;

                        case 2:
                            this.randomTimerDelay = (long) RandomUtils.nextInt(0, 1000);
                            this.shouldMove = true;
                            this.delayTimer.reset();
                            break;

                        case 3:
                            thePlayer.getInventory().setCurrentItem(RandomUtils.nextInt(0, 9));
                            MinecraftInstance.mc.getPlayerController().updateController();
                            this.delayTimer.reset();
                            break;

                        case 4:
                            thePlayer.setRotationYaw(thePlayer.getRotationYaw() + RandomUtils.INSTANCE.nextFloat(-180.0F, 180.0F));
                            this.delayTimer.reset();
                            break;

                        case 5:
                            if (thePlayer.getRotationPitch() <= (float) -90 || thePlayer.getRotationPitch() >= (float) 90) {
                                thePlayer.setRotationPitch(0.0F);
                            }

                            thePlayer.setRotationPitch(thePlayer.getRotationPitch() + RandomUtils.INSTANCE.nextFloat(-10.0F, 10.0F));
                            this.delayTimer.reset();
                        }
                    }
                    break;

                case 110119:
                    if (s.equals("old")) {
                        MinecraftInstance.mc.getGameSettings().getKeyBindForward().setPressed(true);
                        if (this.delayTimer.hasTimePassed(500L)) {
                            thePlayer.setRotationYaw(thePlayer.getRotationYaw() + 180.0F);
                            this.delayTimer.reset();
                        }
                    }
                }

            }
        }
    }

    private final IKeyBinding getRandomMoveKeyBind() {
        switch (RandomUtils.nextInt(0, 4)) {
        case 0:
            return MinecraftInstance.mc.getGameSettings().getKeyBindRight();

        case 1:
            return MinecraftInstance.mc.getGameSettings().getKeyBindLeft();

        case 2:
            return MinecraftInstance.mc.getGameSettings().getKeyBindBack();

        case 3:
            return MinecraftInstance.mc.getGameSettings().getKeyBindForward();

        default:
            return null;
        }
    }

    public void onDisable() {
        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindForward())) {
            MinecraftInstance.mc.getGameSettings().getKeyBindForward().setPressed(false);
        }

    }
}
