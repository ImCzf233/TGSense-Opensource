package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.entity.ai.attributes.IAttributeModifier;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoWeapon",
    description = "Automatically selects the best weapon in your hotbar.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoWeapon;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackEnemy", "", "silentValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "spoofedSlot", "", "ticksValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "update", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AutoWeapon extends Module {

    private final BoolValue silentValue = new BoolValue("SpoofItem", false);
    private final IntegerValue ticksValue = new IntegerValue("SpoofTicks", 10, 1, 20);
    private boolean attackEnemy;
    private int spoofedSlot;

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.attackEnemy = true;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isCPacketUseEntity(event.getPacket())) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IEntityPlayerSP thePlayer = ientityplayersp;
                ICPacketUseEntity packet = event.getPacket().asCPacketUseEntity();

                if (packet.getAction() == ICPacketUseEntity.WAction.ATTACK && this.attackEnemy) {
                    this.attackEnemy = false;
                    byte $this$maxBy$iv = 0;
                    Iterable $this$maxBy$iv1 = (Iterable) (new IntRange($this$maxBy$iv, 8));
                    boolean $i$f$maxBy = false;
                    Collection maxElem$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$maxBy$iv1, 10)));
                    boolean maxValue$iv = false;
                    Iterator e$iv = $this$maxBy$iv1.iterator();

                    boolean $i$a$-filter-AutoWeapon$onPacket$2;

                    while (e$iv.hasNext()) {
                        int v$iv = ((IntIterator) e$iv).nextInt();

                        $i$a$-filter-AutoWeapon$onPacket$2 = false;
                        Pair pair = new Pair(Integer.valueOf(v$iv), thePlayer.getInventory().getStackInSlot(v$iv));

                        maxElem$iv.add(pair);
                    }

                    $this$maxBy$iv1 = (Iterable) ((List) maxElem$iv);
                    $i$f$maxBy = false;
                    maxElem$iv = (Collection) (new ArrayList());
                    maxValue$iv = false;
                    e$iv = $this$maxBy$iv1.iterator();

                    while (e$iv.hasNext()) {
                        Object v$iv1;
                        boolean flag;
                        label88: {
                            v$iv1 = e$iv.next();
                            Pair $i$a$-maxBy-AutoWeapon$onPacket$3 = (Pair) v$iv1;

                            $i$a$-filter-AutoWeapon$onPacket$2 = false;
                            if ($i$a$-maxBy-AutoWeapon$onPacket$3.getSecond() != null) {
                                label109: {
                                    IClassProvider iclassprovider = MinecraftInstance.classProvider;
                                    IItemStack iitemstack = (IItemStack) $i$a$-maxBy-AutoWeapon$onPacket$3.getSecond();

                                    if (!iclassprovider.isItemSword(iitemstack != null ? iitemstack.getItem() : null)) {
                                        iclassprovider = MinecraftInstance.classProvider;
                                        iitemstack = (IItemStack) $i$a$-maxBy-AutoWeapon$onPacket$3.getSecond();
                                        if (!iclassprovider.isItemTool(iitemstack != null ? iitemstack.getItem() : null)) {
                                            break label109;
                                        }
                                    }

                                    flag = true;
                                    break label88;
                                }
                            }

                            flag = false;
                        }

                        if (flag) {
                            maxElem$iv.add(v$iv1);
                        }
                    }

                    $this$maxBy$iv1 = (Iterable) ((List) maxElem$iv);
                    $i$f$maxBy = false;
                    Iterator iterator$iv = $this$maxBy$iv1.iterator();
                    Object object;

                    if (!iterator$iv.hasNext()) {
                        object = null;
                    } else {
                        Object maxElem$iv1 = iterator$iv.next();

                        if (!iterator$iv.hasNext()) {
                            object = maxElem$iv1;
                        } else {
                            Pair maxValue$iv1 = (Pair) maxElem$iv1;
                            boolean e$iv1 = false;

                            object = maxValue$iv1.getSecond();
                            if (object == null) {
                                Intrinsics.throwNpe();
                            }

                            double maxValue$iv2 = ((IAttributeModifier) CollectionsKt.first((Iterable) ((IItemStack) object).getAttributeModifier("generic.attackDamage"))).getAmount() + 1.25D * (double) ItemUtils.getEnchantment((IItemStack) maxValue$iv1.getSecond(), MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS));

                            do {
                                Object e$iv2 = iterator$iv.next();
                                Pair v$iv2 = (Pair) e$iv2;
                                boolean $i$a$-maxBy-AutoWeapon$onPacket$31 = false;

                                object = v$iv2.getSecond();
                                if (object == null) {
                                    Intrinsics.throwNpe();
                                }

                                double v$iv3 = ((IAttributeModifier) CollectionsKt.first((Iterable) ((IItemStack) object).getAttributeModifier("generic.attackDamage"))).getAmount() + 1.25D * (double) ItemUtils.getEnchantment((IItemStack) v$iv2.getSecond(), MinecraftInstance.classProvider.getEnchantmentEnum(EnchantmentType.SHARPNESS));

                                if (Double.compare(maxValue$iv2, v$iv3) < 0) {
                                    maxElem$iv1 = e$iv2;
                                    maxValue$iv2 = v$iv3;
                                }
                            } while (iterator$iv.hasNext());

                            object = maxElem$iv1;
                        }
                    }

                    Pair pair1 = (Pair) object;

                    if (pair1 == null) {
                        return;
                    }

                    Pair pair2 = pair1;
                    int slot = ((Number) pair2.component1()).intValue();

                    if (slot == thePlayer.getInventory().getCurrentItem()) {
                        return;
                    }

                    if (((Boolean) this.silentValue.get()).booleanValue()) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(slot));
                        this.spoofedSlot = ((Number) this.ticksValue.get()).intValue();
                    } else {
                        thePlayer.getInventory().setCurrentItem(slot);
                        MinecraftInstance.mc.getPlayerController().updateController();
                    }

                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) packet);
                    event.cancelEvent();
                }

            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent update) {
        Intrinsics.checkParameterIsNotNull(update, "update");
        if (this.spoofedSlot > 0) {
            if (this.spoofedSlot == 1) {
                IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketHeldItemChange(ientityplayersp.getInventory().getCurrentItem()));
            }

            int i = this.spoofedSlot;

            this.spoofedSlot += -1;
        }

    }
}
