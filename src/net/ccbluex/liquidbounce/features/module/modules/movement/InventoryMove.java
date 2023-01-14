package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
    name = "InvMove",
    description = "Allows you to walk while an inventory is opened.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\"H\u0007J\b\u0010#\u001a\u00020\u0019H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006$"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/InventoryMove;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacAdditionProValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAacAdditionProValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "blinkPacketList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "<set-?>", "", "invOpen", "getInvOpen", "()Z", "lastInvOpen", "getLastInvOpen", "noDetectableValue", "noMoveClicksValue", "rotateValue", "tag", "", "getTag", "()Ljava/lang/String;", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "onDisable", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onScreen", "Lnet/ccbluex/liquidbounce/event/ScreenEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "updateKeyState", "LiquidBounce"}
)
public final class InventoryMove extends Module {

    private final BoolValue noDetectableValue = new BoolValue("NoDetectable", false);
    private final BoolValue rotateValue = new BoolValue("Rotate", true);
    @NotNull
    private final BoolValue aacAdditionProValue = new BoolValue("AACAdditionPro", false);
    private final BoolValue noMoveClicksValue = new BoolValue("NoMoveClicks", false);
    private final List blinkPacketList;
    private boolean lastInvOpen;
    private boolean invOpen;

    @NotNull
    public final BoolValue getAacAdditionProValue() {
        return this.aacAdditionProValue;
    }

    public final boolean getLastInvOpen() {
        return this.lastInvOpen;
    }

    public final boolean getInvOpen() {
        return this.invOpen;
    }

    private final void updateKeyState() {
        if (MinecraftInstance.mc2.currentScreen != null && !(MinecraftInstance.mc2.currentScreen instanceof GuiChat) && (!((Boolean) this.noDetectableValue.get()).booleanValue() || !(MinecraftInstance.mc2.currentScreen instanceof GuiContainer))) {
            MinecraftInstance.mc.getGameSettings().getKeyBindForward().setPressed(MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindForward()));
            MinecraftInstance.mc.getGameSettings().getKeyBindBack().setPressed(MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindBack()));
            MinecraftInstance.mc.getGameSettings().getKeyBindRight().setPressed(MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindRight()));
            MinecraftInstance.mc.getGameSettings().getKeyBindLeft().setPressed(MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindLeft()));
            MinecraftInstance.mc.getGameSettings().getKeyBindJump().setPressed(MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindJump()));
            MinecraftInstance.mc.getGameSettings().getKeyBindSprint().setPressed(MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindSprint()));
            if (((Boolean) this.rotateValue.get()).booleanValue()) {
                IEntityPlayerSP ientityplayersp;

                if (Keyboard.isKeyDown(200)) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getRotationPitch() > (float) -90) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.setRotationPitch(ientityplayersp.getRotationPitch() - (float) 5);
                    }
                }

                if (Keyboard.isKeyDown(208)) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getRotationPitch() < (float) 90) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.setRotationPitch(ientityplayersp.getRotationPitch() + (float) 5);
                    }
                }

                if (Keyboard.isKeyDown(203)) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setRotationYaw(ientityplayersp.getRotationYaw() - (float) 5);
                }

                if (Keyboard.isKeyDown(205)) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setRotationYaw(ientityplayersp.getRotationYaw() + (float) 5);
                }
            }
        }

    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.updateKeyState();
    }

    @EventTarget
    public final void onScreen(@NotNull ScreenEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.updateKeyState();
    }

    @EventTarget
    public final void onClick(@NotNull ClickWindowEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.noMoveClicksValue.get()).booleanValue() && MovementUtils.isMoving()) {
            event.cancelEvent();
        }

    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.blinkPacketList.clear();
        this.invOpen = false;
        this.lastInvOpen = false;
    }

    public void onDisable() {
        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindForward()) || MinecraftInstance.mc.getCurrentScreen() != null) {
            MinecraftInstance.mc.getGameSettings().getKeyBindForward().setPressed(false);
        }

        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindBack()) || MinecraftInstance.mc.getCurrentScreen() != null) {
            MinecraftInstance.mc.getGameSettings().getKeyBindBack().setPressed(false);
        }

        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindRight()) || MinecraftInstance.mc.getCurrentScreen() != null) {
            MinecraftInstance.mc.getGameSettings().getKeyBindRight().setPressed(false);
        }

        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindLeft()) || MinecraftInstance.mc.getCurrentScreen() != null) {
            MinecraftInstance.mc.getGameSettings().getKeyBindLeft().setPressed(false);
        }

        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindJump()) || MinecraftInstance.mc.getCurrentScreen() != null) {
            MinecraftInstance.mc.getGameSettings().getKeyBindJump().setPressed(false);
        }

        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindSprint()) || MinecraftInstance.mc.getCurrentScreen() != null) {
            MinecraftInstance.mc.getGameSettings().getKeyBindSprint().setPressed(false);
        }

    }

    @Nullable
    public String getTag() {
        return ((Boolean) this.aacAdditionProValue.get()).booleanValue() ? "AACAdditionPro" : "Bypass";
    }

    public InventoryMove() {
        boolean flag = false;
        List list = (List) (new ArrayList());

        this.blinkPacketList = list;
    }
}
