package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.CrossVersionUtilsKt;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorComparator;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(
    name = "AutoArmor",
    description = "Automatically equips the best armor in your inventory.",
    category = ModuleCategory.COMBAT
)
public class AutoArmor extends Module {

    public static final ArmorComparator ARMOR_COMPARATOR = new ArmorComparator();
    private final IntegerValue minDelayValue = new IntegerValue("MinDelay", 100, 0, 400) {
        protected void onChanged(Integer oldValue, Integer newValue) {
            int maxDelay = ((Integer) AutoArmor.this.maxDelayValue.get()).intValue();

            if (maxDelay < newValue.intValue()) {
                this.set((Object) Integer.valueOf(maxDelay));
            }

        }
    };
    private final IntegerValue maxDelayValue = new IntegerValue("MaxDelay", 200, 0, 400) {
        protected void onChanged(Integer oldValue, Integer newValue) {
            int minDelay = ((Integer) AutoArmor.this.minDelayValue.get()).intValue();

            if (minDelay > newValue.intValue()) {
                this.set((Object) Integer.valueOf(minDelay));
            }

        }
    };
    private final BoolValue invOpenValue = new BoolValue("InvOpen", false);
    private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    private final IntegerValue itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
    private final BoolValue hotbarValue = new BoolValue("Hotbar", true);
    private long delay;
    private boolean locked = false;

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if (InventoryUtils.CLICK_TIMER.hasTimePassed(this.delay) && AutoArmor.mc.getThePlayer() != null && (AutoArmor.mc.getThePlayer().getOpenContainer() == null || AutoArmor.mc.getThePlayer().getOpenContainer().getWindowId() == 0)) {
            Map armorPieces = (Map) IntStream.range(0, 36).filter(test<invokedynamic>(this)).mapToObj(apply<invokedynamic>()).collect(Collectors.groupingBy(apply<invokedynamic>()));
            ArmorPiece[] bestArmor = new ArmorPiece[4];

            Entry armorPiece;

            for (Iterator i = armorPieces.entrySet().iterator(); i.hasNext(); bestArmor[((Integer) armorPiece.getKey()).intValue()] = (ArmorPiece) ((List) armorPiece.getValue()).stream().max(AutoArmor.ARMOR_COMPARATOR).orElse((Object) null)) {
                armorPiece = (Entry) i.next();
            }

            for (int i = 0; i < 4; ++i) {
                ArmorPiece armorpiece = bestArmor[i];

                if (armorpiece != null) {
                    int armorSlot = 3 - i;
                    ArmorPiece oldArmor = new ArmorPiece(AutoArmor.mc.getThePlayer().getInventory().armorItemInSlot(armorSlot), -1);

                    if (ItemUtils.isStackEmpty(oldArmor.getItemStack()) || !AutoArmor.classProvider.isItemArmor(oldArmor.getItemStack().getItem()) || AutoArmor.ARMOR_COMPARATOR.compare(oldArmor, armorpiece) < 0) {
                        if (!ItemUtils.isStackEmpty(oldArmor.getItemStack()) && this.move(8 - armorSlot, true)) {
                            this.locked = true;
                            return;
                        }

                        if (ItemUtils.isStackEmpty(AutoArmor.mc.getThePlayer().getInventory().armorItemInSlot(armorSlot)) && this.move(armorpiece.getSlot(), false)) {
                            this.locked = true;
                            return;
                        }
                    }
                }
            }

            this.locked = false;
        }
    }

    public boolean isLocked() {
        return !this.getState() || this.locked;
    }

    private boolean move(int item, boolean isArmorSlot) {
        if (!isArmorSlot && item < 9 && ((Boolean) this.hotbarValue.get()).booleanValue() && !AutoArmor.classProvider.isGuiInventory(AutoArmor.mc.getCurrentScreen())) {
            AutoArmor.mc.getNetHandler().addToSendQueue(AutoArmor.classProvider.createCPacketHeldItemChange(item));
            AutoArmor.mc.getNetHandler().addToSendQueue(CrossVersionUtilsKt.createUseItemPacket(AutoArmor.mc.getThePlayer().getInventoryContainer().getSlot(item).getStack(), WEnumHand.MAIN_HAND));
            AutoArmor.mc.getNetHandler().addToSendQueue(AutoArmor.classProvider.createCPacketHeldItemChange(AutoArmor.mc.getThePlayer().getInventory().getCurrentItem()));
            this.delay = TimeUtils.randomDelay(((Integer) this.minDelayValue.get()).intValue(), ((Integer) this.maxDelayValue.get()).intValue());
            return true;
        } else if ((!((Boolean) this.noMoveValue.get()).booleanValue() || !MovementUtils.isMoving()) && (!((Boolean) this.invOpenValue.get()).booleanValue() || AutoArmor.classProvider.isGuiInventory(AutoArmor.mc.getCurrentScreen())) && item != -1) {
            boolean openInventory = ((Boolean) this.simulateInventory.get()).booleanValue() && !AutoArmor.classProvider.isGuiInventory(AutoArmor.mc.getCurrentScreen());

            if (openInventory) {
                AutoArmor.mc.getNetHandler().addToSendQueue(CrossVersionUtilsKt.createOpenInventoryPacket());
            }

            boolean full = isArmorSlot;

            if (isArmorSlot) {
                Iterator iterator = AutoArmor.mc.getThePlayer().getInventory().getMainInventory().iterator();

                while (iterator.hasNext()) {
                    IItemStack iItemStack = (IItemStack) iterator.next();

                    if (ItemUtils.isStackEmpty(iItemStack)) {
                        full = false;
                        break;
                    }
                }
            }

            if (full) {
                AutoArmor.mc.getPlayerController().windowClick(AutoArmor.mc.getThePlayer().getInventoryContainer().getWindowId(), item, 1, 4, AutoArmor.mc.getThePlayer());
            } else {
                AutoArmor.mc.getPlayerController().windowClick(AutoArmor.mc.getThePlayer().getInventoryContainer().getWindowId(), isArmorSlot ? item : (item < 9 ? item + 36 : item), 0, 1, AutoArmor.mc.getThePlayer());
            }

            this.delay = TimeUtils.randomDelay(((Integer) this.minDelayValue.get()).intValue(), ((Integer) this.maxDelayValue.get()).intValue());
            if (openInventory) {
                AutoArmor.mc.getNetHandler().addToSendQueue(AutoArmor.classProvider.createCPacketCloseWindow());
            }

            return true;
        } else {
            return false;
        }
    }

    private static ArmorPiece lambda$onRender3D$1(int i) {
        return new ArmorPiece(AutoArmor.mc.getThePlayer().getInventory().getStackInSlot(i), i);
    }

    private boolean lambda$onRender3D$0(int i) {
        IItemStack itemStack = AutoArmor.mc.getThePlayer().getInventory().getStackInSlot(i);

        return itemStack != null && AutoArmor.classProvider.isItemArmor(itemStack.getItem()) && (i < 9 || System.currentTimeMillis() - itemStack.getItemDelay() >= (long) ((Integer) this.itemDelayValue.get()).intValue());
    }
}
