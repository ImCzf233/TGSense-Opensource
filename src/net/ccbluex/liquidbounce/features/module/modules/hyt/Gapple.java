package net.ccbluex.liquidbounce.features.module.modules.hyt;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Gapple",
    category = ModuleCategory.MISC,
    description = ""
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/hyt/Gapple;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "eating", "", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "noAbsorptionValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Gapple extends Module {

    private final FloatValue healthValue;
    private final IntegerValue delayValue;
    private final BoolValue noAbsorptionValue;
    private final MSTimer timer;
    private int eating;

    public void onEnable() {
        this.eating = -1;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getHealth() <= ((Number) this.healthValue.get()).floatValue() && this.timer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
            if (((Boolean) this.noAbsorptionValue.get()).booleanValue()) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getAbsorptionAmount() > (float) 0) {
                    return;
                }
            }

            int headInHotbar = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.SKULL));

            if (headInHotbar != -1) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(headInHotbar - 36));
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.MAIN_HAND));
                IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketHeldItemChange(ientityplayersp1.getInventory().getCurrentItem()));
                this.timer.reset();
            }

        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (this.eating != -1 && MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
            int i = this.eating++;
        } else if (MinecraftInstance.classProvider.isCPacketHeldItemChange(packet) || MinecraftInstance.classProvider.isCPacketHeldItemChange(packet)) {
            this.eating = -1;
        }

    }

    @NotNull
    public String getTag() {
        return "HytPlt";
    }

    public Gapple() {
        this.setState(true);
        this.healthValue = new FloatValue("Health", 19.95F, 1.0F, 20.0F);
        this.delayValue = new IntegerValue("Delay", 8750, 0, 1000);
        this.noAbsorptionValue = new BoolValue("NoAbsorption", true);
        this.timer = new MSTimer();
        this.eating = -1;
    }
}
