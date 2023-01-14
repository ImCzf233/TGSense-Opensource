package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiChest;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IIInventory;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.util.IWrappedArray;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "ChestStealer",
    description = "Automatically steals all items from a chest.",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0003J\u0012\u0010\'\u001a\u00020 2\b\u0010%\u001a\u0004\u0018\u00010(H\u0007J\u001b\u0010)\u001a\u00020\u00112\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-H\u0082\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestStealer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoCloseMaxDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "autoCloseMinDelayValue", "autoCloseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoCloseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "chestTitleValue", "closeOnFullValue", "contentReceived", "", "delayOnFirstValue", "delayTimer", "fullInventory", "", "getFullInventory", "()Z", "maxDelayValue", "minDelayValue", "nextCloseDelay", "", "nextDelay", "noCompassValue", "onlyItemsValue", "takeRandomizedValue", "isEmpty", "chest", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiChest;", "move", "", "screen", "slot", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "shouldTake", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "inventoryCleaner", "Lnet/ccbluex/liquidbounce/features/module/modules/player/InventoryCleaner;", "LiquidBounce"}
)
public final class ChestStealer extends Module {

    private final IntegerValue maxDelayValue = (IntegerValue) (new IntegerValue("MaxDelay", 200, 0, 400) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) ChestStealer.this.minDelayValue.get()).intValue();

            if (i > newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            ChestStealer.this.nextDelay = TimeUtils.randomDelay(((Number) ChestStealer.this.minDelayValue.get()).intValue(), ((Number) this.get()).intValue());
        }
    });
    private final IntegerValue minDelayValue = (IntegerValue) (new IntegerValue("MinDelay", 150, 0, 400) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) ChestStealer.this.maxDelayValue.get()).intValue();

            if (i < newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            ChestStealer.this.nextDelay = TimeUtils.randomDelay(((Number) this.get()).intValue(), ((Number) ChestStealer.this.maxDelayValue.get()).intValue());
        }
    });
    private final BoolValue delayOnFirstValue = new BoolValue("DelayOnFirst", false);
    private final BoolValue takeRandomizedValue = new BoolValue("TakeRandomized", false);
    private final BoolValue onlyItemsValue = new BoolValue("OnlyItems", false);
    private final BoolValue noCompassValue = new BoolValue("NoCompass", false);
    private final BoolValue autoCloseValue = new BoolValue("AutoClose", true);
    private final IntegerValue autoCloseMaxDelayValue = (IntegerValue) (new IntegerValue("AutoCloseMaxDelay", 0, 0, 400) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) ChestStealer.this.autoCloseMinDelayValue.get()).intValue();

            if (i > newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            ChestStealer.this.nextCloseDelay = TimeUtils.randomDelay(((Number) ChestStealer.this.autoCloseMinDelayValue.get()).intValue(), ((Number) this.get()).intValue());
        }
    });
    private final IntegerValue autoCloseMinDelayValue = (IntegerValue) (new IntegerValue("AutoCloseMinDelay", 0, 0, 400) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) ChestStealer.this.autoCloseMaxDelayValue.get()).intValue();

            if (i < newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            ChestStealer.this.nextCloseDelay = TimeUtils.randomDelay(((Number) this.get()).intValue(), ((Number) ChestStealer.this.autoCloseMaxDelayValue.get()).intValue());
        }
    });
    private final BoolValue closeOnFullValue = new BoolValue("CloseOnFull", true);
    private final BoolValue chestTitleValue = new BoolValue("ChestTitle", false);
    private final MSTimer delayTimer = new MSTimer();
    private long nextDelay;
    private final MSTimer autoCloseTimer;
    private long nextCloseDelay;
    private int contentReceived;

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;

        if (MinecraftInstance.classProvider.isGuiChest(MinecraftInstance.mc.getCurrentScreen()) && MinecraftInstance.mc.getCurrentScreen() != null) {
            if (!this.delayTimer.hasTimePassed(this.nextDelay)) {
                this.autoCloseTimer.reset();
            } else {
                IGuiScreen iguiscreen = MinecraftInstance.mc.getCurrentScreen();

                if (iguiscreen == null) {
                    Intrinsics.throwNpe();
                }

                IGuiChest screen = iguiscreen.asGuiChest();

                if (((Boolean) this.noCompassValue.get()).booleanValue()) {
                    String s;
                    label163: {
                        IItemStack iitemstack = thePlayer.getInventory().getCurrentItemInHand();

                        if (iitemstack != null) {
                            IItem iitem = iitemstack.getItem();

                            if (iitem != null) {
                                s = iitem.getUnlocalizedName();
                                break label163;
                            }
                        }

                        s = null;
                    }

                    if (Intrinsics.areEqual(s, "item.compass")) {
                        return;
                    }
                }

                if (((Boolean) this.chestTitleValue.get()).booleanValue()) {
                    label157: {
                        if (screen.getLowerChestInventory() != null) {
                            IIInventory iiinventory = screen.getLowerChestInventory();

                            if (iiinventory == null) {
                                Intrinsics.throwNpe();
                            }

                            CharSequence charsequence = (CharSequence) iiinventory.getName();
                            IClassProvider iclassprovider = MinecraftInstance.classProvider;
                            IItem iitem1 = MinecraftInstance.functions.getObjectFromItemRegistry(MinecraftInstance.classProvider.createResourceLocation("minecraft:chest"));

                            if (iitem1 == null) {
                                Intrinsics.throwNpe();
                            }

                            if (StringsKt.contains$default(charsequence, (CharSequence) iclassprovider.createItemStack(iitem1).getDisplayName(), false, 2, (Object) null)) {
                                break label157;
                            }
                        }

                        return;
                    }
                }

                Module module = LiquidBounce.INSTANCE.getModuleManager().get(InventoryCleaner.class);

                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner");
                } else {
                    InventoryCleaner inventoryCleaner = (InventoryCleaner) module;
                    IContainer icontainer;

                    if (!this.isEmpty(screen) && (!((Boolean) this.closeOnFullValue.get()).booleanValue() || !this.getFullInventory())) {
                        this.autoCloseTimer.reset();
                        int randomSlot;
                        ISlot slot;

                        if (((Boolean) this.takeRandomizedValue.get()).booleanValue()) {
                            Collection collection;

                            do {
                                boolean flag = false;
                                List list = (List) (new ArrayList());

                                randomSlot = 0;

                                for (int i = screen.getInventoryRows() * 9; randomSlot < i; ++randomSlot) {
                                    icontainer = screen.getInventorySlots();
                                    if (icontainer == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ISlot islot = icontainer.getSlot(randomSlot);
                                    IItemStack this_$iv = islot.getStack();

                                    if (this_$iv != null && (!((Boolean) this.onlyItemsValue.get()).booleanValue() || !MinecraftInstance.classProvider.isItemBlock(this_$iv.getItem())) && (!inventoryCleaner.getState() || inventoryCleaner.isUseful(this_$iv, -1))) {
                                        list.add(islot);
                                    }
                                }

                                randomSlot = Random.Default.nextInt(list.size());
                                slot = (ISlot) list.get(randomSlot);
                                this.move(screen, slot);
                                if (!this.delayTimer.hasTimePassed(this.nextDelay)) {
                                    break;
                                }

                                collection = (Collection) list;
                                boolean flag1 = false;
                            } while (!collection.isEmpty());

                            return;
                        }

                        int slotIndex = 0;

                        for (randomSlot = screen.getInventoryRows() * 9; slotIndex < randomSlot; ++slotIndex) {
                            icontainer = screen.getInventorySlots();
                            if (icontainer == null) {
                                Intrinsics.throwNpe();
                            }

                            slot = icontainer.getSlot(slotIndex);
                            IItemStack stack = slot.getStack();

                            if (this.delayTimer.hasTimePassed(this.nextDelay)) {
                                boolean $i$f$shouldTake = false;

                                if (stack != null && !ItemUtils.isStackEmpty(stack) && (!((Boolean) access$getOnlyItemsValue$p(this).get()).booleanValue() || !MinecraftInstance.classProvider.isItemBlock(stack.getItem())) && (!inventoryCleaner.getState() || inventoryCleaner.isUseful(stack, -1))) {
                                    this.move(screen, slot);
                                }
                            }
                        }
                    } else if (((Boolean) this.autoCloseValue.get()).booleanValue()) {
                        icontainer = screen.getInventorySlots();
                        if (icontainer == null) {
                            Intrinsics.throwNpe();
                        }

                        if (icontainer.getWindowId() == this.contentReceived && this.autoCloseTimer.hasTimePassed(this.nextCloseDelay)) {
                            thePlayer.closeScreen();
                            this.nextCloseDelay = TimeUtils.randomDelay(((Number) this.autoCloseMinDelayValue.get()).intValue(), ((Number) this.autoCloseMaxDelayValue.get()).intValue());
                        }
                    }

                }
            }
        } else {
            if (((Boolean) this.delayOnFirstValue.get()).booleanValue()) {
                this.delayTimer.reset();
            }

            this.autoCloseTimer.reset();
        }
    }

    @EventTarget
    private final void onPacket(PacketEvent event) {
        IPacket packet = event.getPacket();

        if (MinecraftInstance.classProvider.isSPacketWindowItems(packet)) {
            this.contentReceived = packet.asSPacketWindowItems().getWindowId();
        }

    }

    private final boolean shouldTake(IItemStack stack, InventoryCleaner inventoryCleaner) {
        byte $i$f$shouldTake = 0;

        return stack != null && !ItemUtils.isStackEmpty(stack) && (!((Boolean) access$getOnlyItemsValue$p(this).get()).booleanValue() || !MinecraftInstance.classProvider.isItemBlock(stack.getItem())) && (!inventoryCleaner.getState() || inventoryCleaner.isUseful(stack, -1));
    }

    private final void move(IGuiChest screen, ISlot slot) {
        screen.handleMouseClick(slot, slot.getSlotNumber(), 0, 1);
        this.delayTimer.reset();
        this.nextDelay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
    }

    private final boolean isEmpty(IGuiChest chest) {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(InventoryCleaner.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner");
        } else {
            InventoryCleaner inventoryCleaner = (InventoryCleaner) module;
            int i = 0;

            for (int i = chest.getInventoryRows() * 9; i < i; ++i) {
                IContainer icontainer = chest.getInventorySlots();

                if (icontainer == null) {
                    Intrinsics.throwNpe();
                }

                ISlot slot = icontainer.getSlot(i);
                IItemStack stack = slot.getStack();
                boolean $i$f$shouldTake = false;

                if (stack != null && !ItemUtils.isStackEmpty(stack) && (!((Boolean) access$getOnlyItemsValue$p(this).get()).booleanValue() || !MinecraftInstance.classProvider.isItemBlock(stack.getItem())) && (!inventoryCleaner.getState() || inventoryCleaner.isUseful(stack, -1))) {
                    return false;
                }
            }

            return true;
        }
    }

    private final boolean getFullInventory() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();
        boolean flag;

        if (ientityplayersp != null) {
            IInventoryPlayer iinventoryplayer = ientityplayersp.getInventory();

            if (iinventoryplayer != null) {
                IWrappedArray iwrappedarray = iinventoryplayer.getMainInventory();

                if (iwrappedarray != null) {
                    Iterable $this$none$iv = (Iterable) iwrappedarray;
                    boolean $i$f$none = false;

                    if ($this$none$iv instanceof Collection && ((Collection) $this$none$iv).isEmpty()) {
                        flag = true;
                        return flag;
                    } else {
                        Iterator iterator = $this$none$iv.iterator();

                        while (true) {
                            if (iterator.hasNext()) {
                                Object element$iv = iterator.next();
                                IItemStack p1 = (IItemStack) element$iv;
                                boolean $i$a$-unknown-ChestStealer$fullInventory$1 = false;

                                if (!ItemUtils.isStackEmpty(p1)) {
                                    continue;
                                }

                                flag = false;
                                return flag;
                            }

                            flag = true;
                            return flag;
                        }
                    }
                }
            }
        }

        flag = false;
        return flag;
    }

    public ChestStealer() {
        this.nextDelay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
        this.autoCloseTimer = new MSTimer();
        this.nextCloseDelay = TimeUtils.randomDelay(((Number) this.autoCloseMinDelayValue.get()).intValue(), ((Number) this.autoCloseMaxDelayValue.get()).intValue());
    }

    public static final BoolValue access$getOnlyItemsValue$p(ChestStealer $this) {
        return $this.onlyItemsValue;
    }

    public static final long access$getNextDelay$p(ChestStealer $this) {
        return $this.nextDelay;
    }

    public static final long access$getNextCloseDelay$p(ChestStealer $this) {
        return $this.nextCloseDelay;
    }
}
