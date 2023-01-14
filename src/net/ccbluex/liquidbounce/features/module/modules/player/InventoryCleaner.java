package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.entity.ai.attributes.IAttributeModifier;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "InventoryCleaner",
    description = "自动丢弃物品栏中没用的东�?",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J!\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020 2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002¢\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020#2\u0006\u0010(\u001a\u00020 J(\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020#0)2\b\b\u0002\u0010*\u001a\u00020 2\b\b\u0002\u0010+\u001a\u00020 H\u0002J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0007J\b\u00100\u001a\u00020-H\u0002J\u0010\u00101\u001a\u00020\r2\u0006\u0010!\u001a\u00020 H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/InventoryCleaner;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "hotbarValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "ignoreVehiclesValue", "invOpenValue", "itemDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "items", "", "", "[Ljava/lang/String;", "maxDelayValue", "minDelayValue", "noMoveValue", "randomSlotValue", "simulateInventory", "sortSlot1Value", "Lnet/ccbluex/liquidbounce/value/ListValue;", "sortSlot2Value", "sortSlot3Value", "sortSlot4Value", "sortSlot5Value", "sortSlot6Value", "sortSlot7Value", "sortSlot8Value", "sortSlot9Value", "sortValue", "findBetterItem", "", "targetSlot", "slotStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "(ILnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;)Ljava/lang/Integer;", "isUseful", "", "itemStack", "slot", "", "start", "end", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "sortHotbar", "type", "LiquidBounce"}
)
public final class InventoryCleaner extends Module {

    private final IntegerValue maxDelayValue = (IntegerValue) (new IntegerValue("MaxDelay", 600, 0, 1000) {
        protected void onChanged(int oldValue, int newValue) {
            int minCPS = ((Number) InventoryCleaner.this.minDelayValue.get()).intValue();

            if (minCPS > newValue) {
                this.set((Object) Integer.valueOf(minCPS));
            }

        }
    });
    private final IntegerValue minDelayValue = (IntegerValue) (new IntegerValue("MinDelay", 400, 0, 1000) {
        protected void onChanged(int oldValue, int newValue) {
            int maxDelay = ((Number) InventoryCleaner.this.maxDelayValue.get()).intValue();

            if (maxDelay < newValue) {
                this.set((Object) Integer.valueOf(maxDelay));
            }

        }
    });
    private final BoolValue invOpenValue = new BoolValue("InvOpen", false);
    private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    private final BoolValue ignoreVehiclesValue = new BoolValue("IgnoreVehicles", false);
    private final BoolValue hotbarValue = new BoolValue("Hotbar", true);
    private final BoolValue randomSlotValue = new BoolValue("RandomSlot", false);
    private final BoolValue sortValue = new BoolValue("Sort", true);
    private final IntegerValue itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
    private final String[] items = new String[] { "None", "Ignore", "Sword", "Bow", "Pickaxe", "Axe", "Food", "Block", "Water", "Gapple", "Pearl"};
    private final ListValue sortSlot1Value;
    private final ListValue sortSlot2Value;
    private final ListValue sortSlot3Value;
    private final ListValue sortSlot4Value;
    private final ListValue sortSlot5Value;
    private final ListValue sortSlot6Value;
    private final ListValue sortSlot7Value;
    private final ListValue sortSlot8Value;
    private final ListValue sortSlot9Value;
    private long delay;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (InventoryUtils.CLICK_TIMER.hasTimePassed(this.delay) && (MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen()) || !((Boolean) this.invOpenValue.get()).booleanValue()) && (!((Boolean) this.noMoveValue.get()).booleanValue() || !MovementUtils.isMoving())) {
                if (thePlayer.getOpenContainer() != null) {
                    IContainer icontainer = thePlayer.getOpenContainer();

                    if (icontainer == null) {
                        Intrinsics.throwNpe();
                    }

                    if (icontainer.getWindowId() != 0) {
                        return;
                    }
                }

                Module module = LiquidBounce.INSTANCE.getModuleManager().get(AutoArmor.class);

                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor");
                }

                if (!((AutoArmor) module).isLocked()) {
                    if (((Boolean) this.sortValue.get()).booleanValue()) {
                        this.sortHotbar();
                    }

                    for (; InventoryUtils.CLICK_TIMER.hasTimePassed(this.delay); this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue())) {
                        Map garbageItem = this.items(9, ((Boolean) this.hotbarValue.get()).booleanValue() ? 45 : 36);
                        boolean openInventory = false;
                        Map destination$iv$iv = (Map) (new LinkedHashMap());
                        boolean $i$f$filterTo = false;
                        boolean flag = false;
                        Iterator iterator = garbageItem.entrySet().iterator();

                        while (iterator.hasNext()) {
                            Entry element$iv$iv = (Entry) iterator.next();
                            boolean $i$a$-filter-InventoryCleaner$onUpdate$garbageItems$1 = false;

                            if (!this.isUseful((IItemStack) element$iv$iv.getValue(), ((Number) element$iv$iv.getKey()).intValue())) {
                                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                            }
                        }

                        List garbageItems = CollectionsKt.toMutableList((Collection) destination$iv$iv.keySet());

                        if (((Boolean) this.randomSlotValue.get()).booleanValue()) {
                            openInventory = false;
                            Collections.shuffle(garbageItems);
                        }

                        Integer integer = (Integer) CollectionsKt.firstOrNull(garbageItems);

                        if (integer == null) {
                            break;
                        }

                        int garbageItem1 = integer.intValue();

                        openInventory = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen()) && ((Boolean) this.simulateInventory.get()).booleanValue();
                        if (openInventory) {
                            IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            boolean $i$f$createOpenInventoryPacket = false;
                            IClassProvider iclassprovider = WrapperImpl.INSTANCE.getClassProvider();
                            IEntityPlayerSP ientityplayersp1 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();

                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            IPacket ipacket = (IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.OPEN_INVENTORY);

                            iinethandlerplayclient.addToSendQueue(ipacket);
                        }

                        IPlayerControllerMP iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                        IContainer icontainer1 = thePlayer.getOpenContainer();

                        if (icontainer1 == null) {
                            Intrinsics.throwNpe();
                        }

                        iplayercontrollermp.windowClick(icontainer1.getWindowId(), garbageItem1, 1, 4, thePlayer);
                        if (openInventory) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketCloseWindow());
                        }
                    }

                    return;
                }
            }

        }
    }

    public final boolean isUseful(@NotNull IItemStack itemStack, int slot) {
        Intrinsics.checkParameterIsNotNull(itemStack, "itemStack");

        boolean item;

        try {
            IItem iitem = itemStack.getItem();
            boolean $i$f$none2;
            boolean stack;
            boolean flag;

            if (!MinecraftInstance.classProvider.isItemSword(iitem) && !MinecraftInstance.classProvider.isItemTool(iitem)) {
                Iterator element$iv;
                Map map;
                boolean flag1;
                Entry entry;
                boolean flag2;

                if (MinecraftInstance.classProvider.isItemBow(iitem)) {
                    int i = ItemUtils.getEnchantment(itemStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER));

                    map = items$default(this, 0, 0, 3, (Object) null);
                    flag1 = false;
                    if (map.isEmpty()) {
                        flag = true;
                    } else {
                        $i$f$none2 = false;
                        element$iv = map.entrySet().iterator();

                        while (true) {
                            if (element$iv.hasNext()) {
                                entry = (Entry) element$iv.next();
                                flag2 = false;
                                stack = false;
                                IItemStack slot1 = (IItemStack) entry.getValue();

                                if (!(Intrinsics.areEqual(itemStack, slot1) ^ true) || !MinecraftInstance.classProvider.isItemBow(slot1.getItem()) || i >= ItemUtils.getEnchantment(slot1, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER))) {
                                    continue;
                                }

                                flag = false;
                                break;
                            }

                            flag = true;
                            break;
                        }
                    }
                } else if (MinecraftInstance.classProvider.isItemArmor(iitem)) {
                    ArmorPiece armorpiece = new ArmorPiece(itemStack, slot);

                    map = items$default(this, 0, 0, 3, (Object) null);
                    flag1 = false;
                    if (map.isEmpty()) {
                        flag = true;
                    } else {
                        $i$f$none2 = false;
                        element$iv = map.entrySet().iterator();

                        while (true) {
                            if (element$iv.hasNext()) {
                                entry = (Entry) element$iv.next();
                                flag2 = false;
                                stack = false;
                                int j = ((Number) entry.getKey()).intValue();

                                stack = false;
                                IItemStack iitemstack = (IItemStack) entry.getValue();

                                if (Intrinsics.areEqual(iitemstack, itemStack) ^ true && MinecraftInstance.classProvider.isItemArmor(iitemstack.getItem())) {
                                    ArmorPiece armor = new ArmorPiece(iitemstack, j);

                                    flag = armor.getArmorType() != armorpiece.getArmorType() ? false : AutoArmor.ARMOR_COMPARATOR.compare(armorpiece, armor) <= 0;
                                } else {
                                    flag = false;
                                }

                                if (!flag) {
                                    continue;
                                }

                                flag = false;
                                break;
                            }

                            flag = true;
                            break;
                        }
                    }
                } else if (Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.compass")) {
                    Map map1 = this.items(0, 45);
                    boolean flag3 = false;

                    if (map1.isEmpty()) {
                        flag = true;
                    } else {
                        boolean flag4 = false;
                        Iterator iterator = map1.entrySet().iterator();

                        while (true) {
                            if (iterator.hasNext()) {
                                Entry entry1 = (Entry) iterator.next();
                                boolean flag5 = false;
                                boolean flag6 = false;
                                IItemStack iitemstack1 = (IItemStack) entry1.getValue();

                                if (!(Intrinsics.areEqual(itemStack, iitemstack1) ^ true) || !Intrinsics.areEqual(iitemstack1.getUnlocalizedName(), "item.compass")) {
                                    continue;
                                }

                                flag = false;
                                break;
                            }

                            flag = true;
                            break;
                        }
                    }
                } else {
                    flag = MinecraftInstance.classProvider.isItemFood(iitem) || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.arrow") || MinecraftInstance.classProvider.isItemBlock(iitem) && !StringsKt.contains$default((CharSequence) itemStack.getUnlocalizedName(), (CharSequence) "flower", false, 2, (Object) null) || MinecraftInstance.classProvider.isItemBed(iitem) || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.diamond") || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.ingotIron") || MinecraftInstance.classProvider.isItemPotion(iitem) || MinecraftInstance.classProvider.isItemEnderPearl(iitem) || MinecraftInstance.classProvider.isItemEnchantedBook(iitem) || MinecraftInstance.classProvider.isItemBucket(iitem) || Intrinsics.areEqual(itemStack.getUnlocalizedName(), "item.stick") || ((Boolean) this.ignoreVehiclesValue.get()).booleanValue() && (MinecraftInstance.classProvider.isItemBoat(iitem) || MinecraftInstance.classProvider.isItemMinecart(iitem));
                }
            } else {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    return true;
                }

                IEntityPlayerSP ex = ientityplayersp;
                int $i$f$none;

                if (slot >= 36) {
                    Integer integer = this.findBetterItem(slot - 36, ex.getInventory().getStackInSlot(slot - 36));

                    $i$f$none = slot - 36;
                    if (integer != null) {
                        if (integer.intValue() == $i$f$none) {
                            return true;
                        }
                    }
                }

                $i$f$none = 0;

                for (byte $i$f$none1 = 8; $i$f$none <= $i$f$none1; ++$i$f$none) {
                    if ((StringsKt.equals(this.type($i$f$none), "sword", true) && MinecraftInstance.classProvider.isItemSword(iitem) || StringsKt.equals(this.type($i$f$none), "pickaxe", true) && MinecraftInstance.classProvider.isItemPickaxe(iitem) || StringsKt.equals(this.type($i$f$none), "axe", true) && MinecraftInstance.classProvider.isItemAxe(iitem)) && this.findBetterItem($i$f$none, ex.getInventory().getStackInSlot($i$f$none)) == null) {
                        return true;
                    }
                }

                IAttributeModifier iattributemodifier = (IAttributeModifier) CollectionsKt.firstOrNull((Iterable) itemStack.getAttributeModifier("generic.attackDamage"));
                double d0 = (iattributemodifier != null ? iattributemodifier.getAmount() : 0.0D) + 1.25D * (double) ItemUtils.getEnchantment(itemStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS));
                Map $this$none$iv = this.items(0, 45);

                $i$f$none2 = false;
                if ($this$none$iv.isEmpty()) {
                    flag = true;
                } else {
                    boolean $dstr$_u24__u24$stack = false;
                    Iterator $i$a$-none-InventoryCleaner$isUseful$4 = $this$none$iv.entrySet().iterator();

                    while (true) {
                        if ($i$a$-none-InventoryCleaner$isUseful$4.hasNext()) {
                            label190: {
                                Entry $i$a$-none-InventoryCleaner$isUseful$3 = (Entry) $i$a$-none-InventoryCleaner$isUseful$4.next();

                                stack = false;
                                boolean stack1 = false;
                                IItemStack stack2 = (IItemStack) $i$a$-none-InventoryCleaner$isUseful$3.getValue();

                                if (Intrinsics.areEqual(stack2, itemStack) ^ true && Intrinsics.areEqual(stack2.getClass(), itemStack.getClass())) {
                                    IAttributeModifier iattributemodifier1 = (IAttributeModifier) CollectionsKt.firstOrNull((Iterable) stack2.getAttributeModifier("generic.attackDamage"));

                                    if (d0 < (iattributemodifier1 != null ? iattributemodifier1.getAmount() : 0.0D) + 1.25D * (double) ItemUtils.getEnchantment(stack2, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS))) {
                                        flag = true;
                                        break label190;
                                    }
                                }

                                flag = false;
                            }

                            if (!flag) {
                                continue;
                            }

                            flag = false;
                            break;
                        }

                        flag = true;
                        break;
                    }
                }
            }

            item = flag;
        } catch (Exception exception) {
            ClientUtils.getLogger().error("(InventoryCleaner) Failed to check item: " + itemStack.getUnlocalizedName() + '.', (Throwable) exception);
            item = true;
        }

        return item;
    }

    private final void sortHotbar() {
        int index = 0;

        for (byte b0 = 8; index <= b0; ++index) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                return;
            }

            IEntityPlayerSP thePlayer = ientityplayersp;
            Integer integer = this.findBetterItem(index, thePlayer.getInventory().getStackInSlot(index));

            if (integer != null) {
                int bestItem = integer.intValue();

                if (bestItem != index) {
                    boolean openInventory = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen()) && ((Boolean) this.simulateInventory.get()).booleanValue();

                    if (openInventory) {
                        IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        boolean $i$f$createOpenInventoryPacket = false;
                        IClassProvider iclassprovider = WrapperImpl.INSTANCE.getClassProvider();
                        IEntityPlayerSP ientityplayersp1 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();

                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        IPacket ipacket = (IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.OPEN_INVENTORY);

                        iinethandlerplayclient.addToSendQueue(ipacket);
                    }

                    MinecraftInstance.mc.getPlayerController().windowClick(0, bestItem < 9 ? bestItem + 36 : bestItem, index, 2, thePlayer);
                    if (openInventory) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketCloseWindow());
                    }

                    this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
                    break;
                }
            }
        }

    }

    private final Integer findBetterItem(int targetSlot, IItemStack slotStack) {
        String type = this.type(targetSlot);
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            boolean $this$forEachIndexed$iv = false;

            if (type == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s = type.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                String s1 = s;
                boolean $i$f$forEachIndexed;
                int index$iv;
                Iterator $i$f$forEachIndexed1;
                Object item$iv;
                int i;
                boolean item$iv1;
                int j;
                IItemStack stack;
                boolean $i$a$-forEachIndexed-InventoryCleaner$findBetterItem$7;
                IItem item;
                boolean replaceCurr;
                Iterable iterable;
                int k;
                Iterable iterable1;
                boolean flag;
                int l;
                Iterator iterator;
                Object object;
                boolean flag1;
                IItemStack iitemstack;
                IItem iitem;

                switch (s1.hashCode()) {
                case -1253135533:
                    if (s1.equals("gapple")) {
                        iterable = (Iterable) thePlayer.getInventory().getMainInventory();
                        $i$f$forEachIndexed = false;
                        index$iv = 0;
                        $i$f$forEachIndexed1 = iterable.iterator();

                        while ($i$f$forEachIndexed1.hasNext()) {
                            item$iv = $i$f$forEachIndexed1.next();
                            i = index$iv++;
                            item$iv1 = false;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            stack = (IItemStack) item$iv;
                            $i$a$-forEachIndexed-InventoryCleaner$findBetterItem$7 = false;
                            if (stack != null) {
                                iitem = stack.getItem();
                                if (iitem == null) {
                                    Intrinsics.throwNpe();
                                }

                                item = iitem;
                                if (MinecraftInstance.classProvider.isItemAppleGold(item) && !StringsKt.equals(this.type(i), "Gapple", true)) {
                                    replaceCurr = ItemUtils.isStackEmpty(slotStack) || !MinecraftInstance.classProvider.isItemAppleGold(slotStack != null ? slotStack.getItem() : null);
                                    return replaceCurr ? Integer.valueOf(i) : null;
                                }
                            }
                        }
                    }

                    return null;

                case -578028723:
                    if (!s1.equals("pickaxe")) {
                        return null;
                    }
                    break;

                case 97038:
                    if (!s1.equals("axe")) {
                        return null;
                    }
                    break;

                case 97738:
                    if (s1.equals("bow")) {
                        int i1 = MinecraftInstance.classProvider.isItemBow(slotStack != null ? slotStack.getItem() : null) ? targetSlot : -1;

                        k = i1 != -1 ? ItemUtils.getEnchantment(slotStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER)) : 0;
                        iterable1 = (Iterable) thePlayer.getInventory().getMainInventory();
                        flag = false;
                        l = 0;
                        iterator = iterable1.iterator();

                        while (iterator.hasNext()) {
                            object = iterator.next();
                            j = l++;
                            flag1 = false;
                            if (j < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            iitemstack = (IItemStack) object;
                            replaceCurr = false;
                            if (MinecraftInstance.classProvider.isItemBow(iitemstack != null ? iitemstack.getItem() : null) && !StringsKt.equals(this.type(j), type, true)) {
                                if (i1 == -1) {
                                    i1 = j;
                                } else {
                                    int power = ItemUtils.getEnchantment(iitemstack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER));

                                    if (ItemUtils.getEnchantment(iitemstack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.POWER)) > k) {
                                        i1 = j;
                                        k = power;
                                    }
                                }
                            }
                        }

                        return i1 != -1 ? Integer.valueOf(i1) : null;
                    }

                    return null;

                case 3148894:
                    if (s1.equals("food")) {
                        iterable = (Iterable) thePlayer.getInventory().getMainInventory();
                        $i$f$forEachIndexed = false;
                        index$iv = 0;
                        $i$f$forEachIndexed1 = iterable.iterator();

                        while ($i$f$forEachIndexed1.hasNext()) {
                            item$iv = $i$f$forEachIndexed1.next();
                            i = index$iv++;
                            item$iv1 = false;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            stack = (IItemStack) item$iv;
                            $i$a$-forEachIndexed-InventoryCleaner$findBetterItem$7 = false;
                            if (stack != null) {
                                item = stack.getItem();
                                if (MinecraftInstance.classProvider.isItemFood(item) && !MinecraftInstance.classProvider.isItemAppleGold(item) && !StringsKt.equals(this.type(i), "Food", true)) {
                                    replaceCurr = ItemUtils.isStackEmpty(slotStack) || !MinecraftInstance.classProvider.isItemFood(item);
                                    return replaceCurr ? Integer.valueOf(i) : null;
                                }
                            }
                        }
                    }

                    return null;

                case 93832333:
                    if (s1.equals("block")) {
                        iterable = (Iterable) thePlayer.getInventory().getMainInventory();
                        $i$f$forEachIndexed = false;
                        index$iv = 0;
                        $i$f$forEachIndexed1 = iterable.iterator();

                        while ($i$f$forEachIndexed1.hasNext()) {
                            item$iv = $i$f$forEachIndexed1.next();
                            i = index$iv++;
                            item$iv1 = false;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            stack = (IItemStack) item$iv;
                            $i$a$-forEachIndexed-InventoryCleaner$findBetterItem$7 = false;
                            if (stack != null) {
                                iitem = stack.getItem();
                                if (iitem == null) {
                                    Intrinsics.throwNpe();
                                }

                                item = iitem;
                                if (MinecraftInstance.classProvider.isItemBlock(item) && !InventoryUtils.BLOCK_BLACKLIST.contains(item.asItemBlock().getBlock()) && !StringsKt.equals(this.type(i), "Block", true)) {
                                    replaceCurr = ItemUtils.isStackEmpty(slotStack) || !MinecraftInstance.classProvider.isItemBlock(item);
                                    return replaceCurr ? Integer.valueOf(i) : null;
                                }
                            }
                        }
                    }

                    return null;

                case 106540102:
                    if (s1.equals("pearl")) {
                        iterable = (Iterable) thePlayer.getInventory().getMainInventory();
                        $i$f$forEachIndexed = false;
                        index$iv = 0;
                        $i$f$forEachIndexed1 = iterable.iterator();

                        while ($i$f$forEachIndexed1.hasNext()) {
                            item$iv = $i$f$forEachIndexed1.next();
                            i = index$iv++;
                            item$iv1 = false;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            stack = (IItemStack) item$iv;
                            $i$a$-forEachIndexed-InventoryCleaner$findBetterItem$7 = false;
                            if (stack != null) {
                                item = stack.getItem();
                                if (MinecraftInstance.classProvider.isItemEnderPearl(item) && !StringsKt.equals(this.type(i), "Pearl", true)) {
                                    replaceCurr = ItemUtils.isStackEmpty(slotStack) || !MinecraftInstance.classProvider.isItemEnderPearl(slotStack != null ? slotStack.getItem() : null);
                                    return replaceCurr ? Integer.valueOf(i) : null;
                                }
                            }
                        }
                    }

                    return null;

                case 109860349:
                    if (!s1.equals("sword")) {
                        return null;
                    }
                    break;

                case 112903447:
                    if (s1.equals("water")) {
                        iterable = (Iterable) thePlayer.getInventory().getMainInventory();
                        $i$f$forEachIndexed = false;
                        index$iv = 0;
                        $i$f$forEachIndexed1 = iterable.iterator();

                        while ($i$f$forEachIndexed1.hasNext()) {
                            item$iv = $i$f$forEachIndexed1.next();
                            i = index$iv++;
                            item$iv1 = false;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }

                            stack = (IItemStack) item$iv;
                            $i$a$-forEachIndexed-InventoryCleaner$findBetterItem$7 = false;
                            if (stack != null) {
                                iitem = stack.getItem();
                                if (iitem == null) {
                                    Intrinsics.throwNpe();
                                }

                                item = iitem;
                                if (MinecraftInstance.classProvider.isItemBucket(item) && Intrinsics.areEqual(item.asItemBucket().isFull(), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_WATER)) && !StringsKt.equals(this.type(i), "Water", true)) {
                                    replaceCurr = ItemUtils.isStackEmpty(slotStack) || !MinecraftInstance.classProvider.isItemBucket(item) || Intrinsics.areEqual(item.asItemBucket().isFull(), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_WATER)) ^ true;
                                    return replaceCurr ? Integer.valueOf(i) : null;
                                }
                            }
                        }
                    }

                    return null;

                default:
                    return null;
                }

                Function1 function1;

                if (StringsKt.equals(type, "Sword", true)) {
                    function1 = (Function1) null.INSTANCE;
                } else if (StringsKt.equals(type, "Pickaxe", true)) {
                    function1 = (Function1) null.INSTANCE;
                } else {
                    if (!StringsKt.equals(type, "Axe", true)) {
                        return null;
                    }

                    function1 = (Function1) null.INSTANCE;
                }

                Function1 function11 = function1;

                k = ((Boolean) function11.invoke(slotStack != null ? slotStack.getItem() : null)).booleanValue() ? targetSlot : -1;
                iterable1 = (Iterable) thePlayer.getInventory().getMainInventory();
                flag = false;
                l = 0;
                iterator = iterable1.iterator();

                while (iterator.hasNext()) {
                    object = iterator.next();
                    j = l++;
                    flag1 = false;
                    if (j < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }

                    iitemstack = (IItemStack) object;
                    replaceCurr = false;
                    if (iitemstack != null && ((Boolean) function11.invoke(iitemstack.getItem())).booleanValue() && !StringsKt.equals(this.type(j), type, true)) {
                        if (k == -1) {
                            k = j;
                        } else {
                            IAttributeModifier iattributemodifier = (IAttributeModifier) CollectionsKt.firstOrNull((Iterable) iitemstack.getAttributeModifier("generic.attackDamage"));
                            double d0 = (iattributemodifier != null ? iattributemodifier.getAmount() : 0.0D) + 1.25D * (double) ItemUtils.getEnchantment(iitemstack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS));
                            IItemStack iitemstack1 = thePlayer.getInventory().getStackInSlot(k);

                            if (iitemstack1 != null) {
                                IItemStack bestStack = iitemstack1;

                                iattributemodifier = (IAttributeModifier) CollectionsKt.firstOrNull((Iterable) bestStack.getAttributeModifier("generic.attackDamage"));
                                double bestDamage = (iattributemodifier != null ? iattributemodifier.getAmount() : 0.0D) + 1.25D * (double) ItemUtils.getEnchantment(bestStack, MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS));

                                if (bestDamage < d0) {
                                    k = j;
                                }
                            }
                        }
                    }
                }

                return k == -1 && k != targetSlot ? null : Integer.valueOf(k);
            }
        } else {
            return null;
        }
    }

    private final Map items(int start, int end) {
        boolean i = false;
        Map items = (Map) (new LinkedHashMap());
        int i = end - 1;
        int j = start;

        if (i >= start) {
            while (true) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp != null) {
                    IContainer icontainer = ientityplayersp.getInventoryContainer();

                    if (icontainer != null) {
                        ISlot islot = icontainer.getSlot(i);

                        if (islot != null) {
                            IItemStack iitemstack = islot.getStack();

                            if (iitemstack != null) {
                                IItemStack itemStack = iitemstack;

                                if (!ItemUtils.isStackEmpty(itemStack)) {
                                    label28: {
                                        if (36 <= i) {
                                            if (44 >= i && StringsKt.equals(this.type(i), "Ignore", true)) {
                                                break label28;
                                            }
                                        }

                                        if (System.currentTimeMillis() - itemStack.getItemDelay() >= ((Number) this.itemDelayValue.get()).longValue()) {
                                            items.put(Integer.valueOf(i), itemStack);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (i == j) {
                    break;
                }

                --i;
            }
        }

        return items;
    }

    static Map items$default(InventoryCleaner inventorycleaner, int i, int j, int k, Object object) {
        if ((k & 1) != 0) {
            i = 0;
        }

        if ((k & 2) != 0) {
            j = 45;
        }

        return inventorycleaner.items(i, j);
    }

    private final String type(int targetSlot) {
        String s;

        switch (targetSlot) {
        case 0:
            s = (String) this.sortSlot1Value.get();
            break;

        case 1:
            s = (String) this.sortSlot2Value.get();
            break;

        case 2:
            s = (String) this.sortSlot3Value.get();
            break;

        case 3:
            s = (String) this.sortSlot4Value.get();
            break;

        case 4:
            s = (String) this.sortSlot5Value.get();
            break;

        case 5:
            s = (String) this.sortSlot6Value.get();
            break;

        case 6:
            s = (String) this.sortSlot7Value.get();
            break;

        case 7:
            s = (String) this.sortSlot8Value.get();
            break;

        case 8:
            s = (String) this.sortSlot9Value.get();
            break;

        default:
            s = "";
        }

        return s;
    }

    public InventoryCleaner() {
        this.sortSlot1Value = new ListValue("SortSlot-1", this.items, "Sword");
        this.sortSlot2Value = new ListValue("SortSlot-2", this.items, "Bow");
        this.sortSlot3Value = new ListValue("SortSlot-3", this.items, "Pickaxe");
        this.sortSlot4Value = new ListValue("SortSlot-4", this.items, "Axe");
        this.sortSlot5Value = new ListValue("SortSlot-5", this.items, "None");
        this.sortSlot6Value = new ListValue("SortSlot-6", this.items, "None");
        this.sortSlot7Value = new ListValue("SortSlot-7", this.items, "Food");
        this.sortSlot8Value = new ListValue("SortSlot-8", this.items, "Block");
        this.sortSlot9Value = new ListValue("SortSlot-9", this.items, "Block");
    }
}
