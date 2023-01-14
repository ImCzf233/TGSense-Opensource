package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "AutoSoup",
    description = "Makes you automatically eat soup whenever your health is low.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoSoup;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "bowlValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "openInventoryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "simulateInventoryValue", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AutoSoup extends Module {

    private final FloatValue healthValue = new FloatValue("Health", 15.0F, 0.0F, 20.0F);
    private final IntegerValue delayValue = new IntegerValue("Delay", 150, 0, 500);
    private final BoolValue openInventoryValue = new BoolValue("OpenInv", false);
    private final BoolValue simulateInventoryValue = new BoolValue("SimulateInventory", true);
    private final ListValue bowlValue = new ListValue("Bowl", new String[] { "Drop", "Move", "Stay"}, "Drop");
    private final MSTimer timer = new MSTimer();

    @NotNull
    public String getTag() {
        return String.valueOf(((Number) this.healthValue.get()).floatValue());
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (this.timer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IEntityPlayerSP thePlayer = ientityplayersp;
                int soupInHotbar = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.MUSHROOM_STEW));
                IINetHandlerPlayClient iinethandlerplayclient;
                IPacket ipacket;
                boolean flag;

                if (thePlayer.getHealth() <= ((Number) this.healthValue.get()).floatValue() && soupInHotbar != -1) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(soupInHotbar - 36));
                    IINetHandlerPlayClient iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                    IItemStack iitemstack = thePlayer.getInventory().getStackInSlot(soupInHotbar);
                    WEnumHand wenumhand = WEnumHand.MAIN_HAND;

                    iinethandlerplayclient = iinethandlerplayclient1;
                    flag = false;
                    ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(wenumhand);
                    iinethandlerplayclient.addToSendQueue(ipacket);
                    if (StringsKt.equals((String) this.bowlValue.get(), "Drop", true)) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.DROP_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                    }

                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                    this.timer.reset();
                } else {
                    int bowlInHotbar = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.BOWL));
                    boolean flag1;
                    IClassProvider iclassprovider;
                    IEntityPlayerSP ientityplayersp1;

                    if (StringsKt.equals((String) this.bowlValue.get(), "Move", true) && bowlInHotbar != -1) {
                        if (((Boolean) this.openInventoryValue.get()).booleanValue() && !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen())) {
                            return;
                        }

                        boolean soupInInventory = false;
                        int openInventory = 9;

                        for (byte $i$f$createOpenInventoryPacket = 36; openInventory <= $i$f$createOpenInventoryPacket; ++openInventory) {
                            IItemStack itemStack = thePlayer.getInventory().getStackInSlot(openInventory);

                            if (itemStack == null) {
                                soupInInventory = true;
                                break;
                            }

                            if (Intrinsics.areEqual(itemStack.getItem(), MinecraftInstance.classProvider.getItemEnum(ItemType.BOWL)) && itemStack.getStackSize() < 64) {
                                soupInInventory = true;
                                break;
                            }
                        }

                        if (soupInInventory) {
                            flag = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen()) && ((Boolean) this.simulateInventoryValue.get()).booleanValue();
                            if (flag) {
                                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                flag1 = false;
                                iclassprovider = WrapperImpl.INSTANCE.getClassProvider();
                                ientityplayersp1 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                ipacket = (IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.OPEN_INVENTORY);
                                iinethandlerplayclient.addToSendQueue(ipacket);
                            }

                            MinecraftInstance.mc.getPlayerController().windowClick(0, bowlInHotbar, 0, 1, thePlayer);
                        }
                    }

                    int i = InventoryUtils.findItem(9, 36, MinecraftInstance.classProvider.getItemEnum(ItemType.MUSHROOM_STEW));

                    if (i != -1 && InventoryUtils.hasSpaceHotbar()) {
                        if (((Boolean) this.openInventoryValue.get()).booleanValue() && !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen())) {
                            return;
                        }

                        flag = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen()) && ((Boolean) this.simulateInventoryValue.get()).booleanValue();
                        if (flag) {
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            flag1 = false;
                            iclassprovider = WrapperImpl.INSTANCE.getClassProvider();
                            ientityplayersp1 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            ipacket = (IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.OPEN_INVENTORY);
                            iinethandlerplayclient.addToSendQueue(ipacket);
                        }

                        MinecraftInstance.mc.getPlayerController().windowClick(0, i, 0, 1, thePlayer);
                        if (flag) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketCloseWindow());
                        }

                        this.timer.reset();
                    }

                }
            }
        }
    }
}
