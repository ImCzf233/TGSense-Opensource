package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Timer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoLeos",
    description = "Automatically makes you leave the server whenever your health is low.",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0011H\u0002J\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\"H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015¨\u0006#"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoLeos;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "keepArmor", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "message", "messages", "Lnet/ccbluex/liquidbounce/value/TextValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Ljava/util/Timer;", "getTimer", "()Ljava/util/Timer;", "wating", "", "getWating", "()Z", "setWating", "(Z)V", "wating2", "getWating2", "setWating2", "move", "", "item", "", "isArmorSlot", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class AutoLeos extends Module {

    private final BoolValue keepArmor = new BoolValue("KeepArmor", false);
    private final BoolValue message = new BoolValue("Message", false);
    private final TextValue messages = new TextValue("Messages", "TGSense@01-15");
    private boolean wating;
    private boolean wating2;
    @NotNull
    private final Timer timer;

    public final boolean getWating() {
        return this.wating;
    }

    public final void setWating(boolean <set-?>) {
        this.wating = <set-?>;
    }

    public final boolean getWating2() {
        return this.wating2;
    }

    public final void setWating2(boolean <set-?>) {
        this.wating2 = <set-?>;
    }

    @NotNull
    public final Timer getTimer() {
        return this.timer;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getHealth() <= 6.0F) {
            if (((Boolean) this.keepArmor.get()).booleanValue()) {
                int i = 0;

                for (byte b0 = 3; i <= b0; ++i) {
                    int armorSlot = 3 - i;

                    this.move(8 - armorSlot, true);
                }

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getTotalArmorValue() < 4 && this.wating2) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.sendChatMessage("/hub 875");
                    this.wating2 = false;
                }
            } else if (this.wating2) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.sendChatMessage("/hub 875");
                this.wating2 = false;
            }

            if (((Boolean) this.message.get()).booleanValue() && this.wating) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.sendChatMessage((String) this.messages.get());
                this.wating = false;
            }

            LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class).setState(false);
        }

    }

    private final void move(int item, boolean isArmorSlot) {
        if (item != -1) {
            boolean openInventory = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen());
            IEntityPlayerSP ientityplayersp;

            if (openInventory) {
                IClassProvider iclassprovider = MinecraftInstance.classProvider;

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.OPEN_INVENTORY);
            }

            IPlayerControllerMP iplayercontrollermp = MinecraftInstance.mc.getPlayerController();

            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            int i = ientityplayersp.getInventoryContainer().getWindowId();
            int j = isArmorSlot ? item : (item < 9 ? item + 36 : item);
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            iplayercontrollermp.windowClick(i, j, 0, 1, ientityplayersp1);
            if (openInventory) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketCloseWindow());
            }
        }

    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.wating = true;
        this.wating2 = true;
    }

    @NotNull
    public String getTag() {
        return "Hyt";
    }

    public AutoLeos() {
        this.setState(true);
        this.wating = true;
        this.wating2 = true;
        this.timer = new Timer();
    }
}
