package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
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
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.init.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "Gapple",
    description = "Eat Gapples.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\u0012\u0010\u001a\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/Gapple;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "eating", "", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "noAbsorption", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "doEat", "", "warn", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Gapple extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Auto", "LegitAuto", "Once", "Head"}, "Once");
    private final FloatValue healthValue = new FloatValue("Health", 10.0F, 1.0F, 20.0F);
    private final IntegerValue delayValue = new IntegerValue("Delay", 150, 0, 1000);
    private final BoolValue noAbsorption = new BoolValue("NoAbsorption", true);
    private final MSTimer timer = new MSTimer();
    private int eating = -1;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    public void onEnable() {
        this.eating = -1;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        String s = (String) this.modeValue.get();
        boolean headInHotbar = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            int headInHotbar1;
            IEntityPlayerSP ientityplayersp;
            IINetHandlerPlayClient iinethandlerplayclient;
            IClassProvider iclassprovider;
            IEntityPlayerSP ientityplayersp1;

            switch (s.hashCode()) {
            case -1961575192:
                if (s.equals("legitauto")) {
                    if (this.eating == -1) {
                        headInHotbar1 = InventoryUtils.findItem2(36, 45, Items.GOLDEN_APPLE);
                        if (headInHotbar1 == -1) {
                            return;
                        }

                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(headInHotbar1 - 36));
                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        iclassprovider = MinecraftInstance.classProvider;
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerBlockPlacement(ientityplayersp1.getHeldItem()));
                        this.eating = 0;
                    } else if (this.eating > 35) {
                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        iclassprovider = MinecraftInstance.classProvider;
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketHeldItemChange(ientityplayersp1.getInventory().getCurrentItem()));
                        this.timer.reset();
                    }
                }
                break;

            case 3005871:
                if (s.equals("auto")) {
                    if (!this.timer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
                        return;
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getHealth() <= ((Number) this.healthValue.get()).floatValue()) {
                        this.doEat(false);
                        this.timer.reset();
                    }
                }
                break;

            case 3198432:
                if (s.equals("head")) {
                    if (!this.timer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
                        return;
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getHealth() <= ((Number) this.healthValue.get()).floatValue()) {
                        headInHotbar1 = InventoryUtils.findItem2(36, 45, Items.SKULL);
                        if (headInHotbar1 != -1) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(headInHotbar1 - 36));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerBlockPlacement(ientityplayersp1.getHeldItem()));
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            iclassprovider = MinecraftInstance.classProvider;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketHeldItemChange(ientityplayersp1.getInventory().getCurrentItem()));
                            this.timer.reset();
                        }
                    }
                }
                break;

            case 3415681:
                if (s.equals("once")) {
                    this.doEat(true);
                    this.setState(false);
                }
            }

        }
    }

    private final void doEat(boolean warn) {
        if (!((Boolean) this.noAbsorption.get()).booleanValue() || warn) {
            int gappleInHotbar = InventoryUtils.findItem2(36, 45, Items.GOLDEN_APPLE);

            if (gappleInHotbar != -1) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(gappleInHotbar - 36));
                IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerBlockPlacement(ientityplayersp.getHeldItem()));
                byte b0 = 35;
                boolean flag = false;
                boolean flag1 = false;
                int i = 0;

                for (byte b1 = b0; i < b1; ++i) {
                    boolean $i$a$-repeat-Gapple$doEat$1 = false;

                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    iclassprovider = MinecraftInstance.classProvider;
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayer(ientityplayersp.getOnGround()));
                }

                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                iclassprovider = MinecraftInstance.classProvider;
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketHeldItemChange(ientityplayersp.getInventory().getCurrentItem()));
            }

        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
