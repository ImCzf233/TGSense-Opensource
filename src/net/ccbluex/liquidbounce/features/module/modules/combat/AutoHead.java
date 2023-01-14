package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

@ModuleInfo(
    name = "AutoHead",
    description = "faq",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\u0012\u0010\u001a\u001a\u00020\u00182\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007J\b\u0010\u001d\u001a\u00020\u0018H\u0002J\b\u0010\u001e\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoHead;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "doingStuff", "", "getDoingStuff", "()Z", "setDoingStuff", "(Z)V", "eatApples", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "eatHeads", "eatingApple", "health", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "switched", "", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TimeUtils;", "getItemFromHotbar", "id", "onDisable", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "repairItemPress", "repairItemSwitch", "LiquidBounce"}
)
public final class AutoHead extends Module {

    private boolean eatingApple;
    private int switched = -1;
    private boolean doingStuff;
    private final TimeUtils timer = new TimeUtils();
    private final BoolValue eatHeads = new BoolValue("EatHead", true);
    private final BoolValue eatApples = new BoolValue("EatApples", true);
    private final FloatValue health = new FloatValue("Health", 10.0F, 1.0F, 20.0F);
    private final IntegerValue delay = new IntegerValue("Delay", 750, 100, 2000);

    public final boolean getDoingStuff() {
        return this.doingStuff;
    }

    public final void setDoingStuff(boolean <set-?>) {
        this.doingStuff = <set-?>;
    }

    public void onEnable() {
        this.doingStuff = false;
        this.eatingApple = this.doingStuff;
        this.switched = -1;
        this.timer.reset();
        super.onEnable();
    }

    public void onDisable() {
        this.doingStuff = false;
        if (this.eatingApple) {
            this.repairItemPress();
            this.repairItemSwitch();
        }

        super.onDisable();
    }

    private final void repairItemPress() {
        if (MinecraftInstance.mc.getGameSettings() != null) {
            IKeyBinding keyBindUseItem = MinecraftInstance.mc.getGameSettings().getKeyBindUseItem();

            if (keyBindUseItem != null) {
                keyBindUseItem.setPressed(false);
            }
        }

    }

    @EventTarget
    public final void onUpdate(@Nullable MotionEvent event) {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IInventoryPlayer iinventoryplayer = ientityplayersp.getInventory();

            if (iinventoryplayer != null) {
                IInventoryPlayer inventory = iinventoryplayer;

                this.doingStuff = false;
                if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
                    label110: {
                        IKeyBinding useItem = MinecraftInstance.mc.getGameSettings().getKeyBindUseItem();

                        if (!this.timer.hasReached((double) ((Number) this.delay.get()).intValue())) {
                            this.eatingApple = false;
                            this.repairItemPress();
                            this.repairItemSwitch();
                            return;
                        }

                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!ientityplayersp.getCapabilities().isCreativeMode()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (!ientityplayersp.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION))) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getHealth() < ((Number) this.health.get()).floatValue()) {
                                    int i = 0;

                                    for (byte b0 = 1; i <= b0; ++i) {
                                        boolean doEatHeads = i != 0;

                                        if (doEatHeads) {
                                            if (!((Boolean) this.eatHeads.get()).booleanValue()) {
                                                continue;
                                            }
                                        } else if (!((Boolean) this.eatApples.get()).booleanValue()) {
                                            this.eatingApple = false;
                                            this.repairItemPress();
                                            this.repairItemSwitch();
                                            continue;
                                        }

                                        boolean slot = false;
                                        int i = doEatHeads ? this.getItemFromHotbar(397) : this.getItemFromHotbar(322);

                                        if (i != -1) {
                                            int tempSlot = inventory.getCurrentItem();

                                            this.doingStuff = true;
                                            if (doEatHeads) {
                                                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(i));
                                                IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                                                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                                                if (ientityplayersp1 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerBlockPlacement(ientityplayersp1.getInventory().getCurrentItemInHand()));
                                                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(tempSlot));
                                                this.timer.reset();
                                            } else {
                                                inventory.setCurrentItem(i);
                                                useItem.setPressed(true);
                                                if (!this.eatingApple) {
                                                    this.eatingApple = true;
                                                    this.switched = tempSlot;
                                                }
                                            }
                                        }
                                    }
                                    break label110;
                                }
                            }
                        }

                        this.timer.reset();
                        if (this.eatingApple) {
                            this.eatingApple = false;
                            this.repairItemPress();
                            this.repairItemSwitch();
                        }

                        return;
                    }
                }

            }
        }
    }

    private final void repairItemSwitch() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP p = ientityplayersp;
            IInventoryPlayer iinventoryplayer = p.getInventory();

            if (iinventoryplayer != null) {
                IInventoryPlayer inventory = iinventoryplayer;
                int switched = this.switched;

                if (switched != -1) {
                    inventory.setCurrentItem(switched);
                    byte switched1 = -1;

                    this.switched = switched1;
                }
            }
        }
    }

    private final int getItemFromHotbar(int id) {
        int i = 0;

        for (byte b0 = 8; i <= b0; ++i) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getInventory().getMainInventory().get(i) != null) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IItemStack a = (IItemStack) ientityplayersp.getInventory().getMainInventory().get(i);

                if (a == null) {
                    Intrinsics.throwNpe();
                }

                IItem item = a.getItem();
                IExtractedFunctions iextractedfunctions = MinecraftInstance.functions;

                if (item == null) {
                    Intrinsics.throwNpe();
                }

                if (Intrinsics.areEqual(iextractedfunctions.getIdFromItem(item), Integer.valueOf(id))) {
                    return i;
                }
            }
        }

        return -1;
    }
}
