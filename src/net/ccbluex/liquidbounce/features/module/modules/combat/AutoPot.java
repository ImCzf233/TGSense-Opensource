package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemPotion;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "AutoPot",
    description = "Automatically throws healing potions.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001c"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoPot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "groundDistanceValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "openInventoryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "potion", "", "simulateInventory", "tag", "", "getTag", "()Ljava/lang/String;", "findPotion", "startSlot", "endSlot", "onMotion", "", "motionEvent", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"}
)
public final class AutoPot extends Module {

    private final FloatValue healthValue = new FloatValue("Health", 15.0F, 1.0F, 20.0F);
    private final IntegerValue delayValue = new IntegerValue("Delay", 500, 500, 1000);
    private final BoolValue openInventoryValue = new BoolValue("OpenInv", false);
    private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
    private final FloatValue groundDistanceValue = new FloatValue("GroundDistance", 2.0F, 0.0F, 5.0F);
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Normal", "Jump", "Port"}, "Normal");
    private final MSTimer msTimer = new MSTimer();
    private int potion = -1;

    @EventTarget
    public final void onMotion(@NotNull MotionEvent motionEvent) {
        Intrinsics.checkParameterIsNotNull(motionEvent, "motionEvent");
        if (this.msTimer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue()) && !MinecraftInstance.mc.getPlayerController().isInCreativeMode()) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IEntityPlayerSP thePlayer = ientityplayersp;
                boolean $i$f$createUseItemPacket;
                IINetHandlerPlayClient iinethandlerplayclient;
                IPacket ipacket;

                switch (AutoPot$WhenMappings.$EnumSwitchMapping$0[motionEvent.getEventState().ordinal()]) {
                case 1:
                    int itemStack1 = this.findPotion(36, 45);

                    if (thePlayer.getHealth() <= ((Number) this.healthValue.get()).floatValue() && itemStack1 != -1) {
                        if (thePlayer.getOnGround()) {
                            String hand$iv2 = (String) this.modeValue.get();

                            $i$f$createUseItemPacket = false;
                            if (hand$iv2 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            String s = hand$iv2.toLowerCase();

                            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                            hand$iv2 = s;
                            switch (hand$iv2.hashCode()) {
                            case 3273774:
                                if (hand$iv2.equals("jump")) {
                                    thePlayer.jump();
                                }
                                break;

                            case 3446913:
                                if (hand$iv2.equals("port")) {
                                    thePlayer.moveEntity(0.0D, 0.42D, 0.0D);
                                }
                            }
                        }

                        FallingPlayer hand$iv3 = new FallingPlayer(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), thePlayer.getMotionX(), thePlayer.getMotionY(), thePlayer.getMotionZ(), thePlayer.getRotationYaw(), thePlayer.getMoveStrafing(), thePlayer.getMoveForward());
                        FallingPlayer.CollisionResult fallingplayer_collisionresult = hand$iv3.findCollision(20);
                        WBlockPos $i$f$createUseItemPacket1 = fallingplayer_collisionresult != null ? fallingplayer_collisionresult.getPos() : null;

                        if (thePlayer.getPosY() - (double) ($i$f$createUseItemPacket1 != null ? $i$f$createUseItemPacket1.getY() : 0) >= ((Number) this.groundDistanceValue.get()).doubleValue()) {
                            return;
                        }

                        this.potion = itemStack1;
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(this.potion - 36));
                        if (thePlayer.getRotationPitch() <= 80.0F) {
                            RotationUtils.setTargetRotation(new Rotation(thePlayer.getRotationYaw(), RandomUtils.INSTANCE.nextFloat(80.0F, 90.0F)));
                        }

                        return;
                    }

                    int hand$iv1 = this.findPotion(9, 36);

                    if (hand$iv1 != -1 && InventoryUtils.hasSpaceHotbar()) {
                        if (((Boolean) this.openInventoryValue.get()).booleanValue() && !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen())) {
                            return;
                        }

                        $i$f$createUseItemPacket = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.mc.getCurrentScreen()) && ((Boolean) this.simulateInventory.get()).booleanValue();
                        if ($i$f$createUseItemPacket) {
                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            boolean $i$f$createOpenInventoryPacket = false;
                            IClassProvider iclassprovider = WrapperImpl.INSTANCE.getClassProvider();
                            IEntityPlayerSP ientityplayersp1 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();

                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            ipacket = (IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.OPEN_INVENTORY);
                            iinethandlerplayclient.addToSendQueue(ipacket);
                        }

                        MinecraftInstance.mc.getPlayerController().windowClick(0, hand$iv1, 0, 1, thePlayer);
                        if ($i$f$createUseItemPacket) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketCloseWindow());
                        }

                        this.msTimer.reset();
                    }
                    break;

                case 2:
                    if (this.potion >= 0 && RotationUtils.serverRotation.getPitch() >= 75.0F) {
                        IItemStack itemStack = thePlayer.getInventory().getStackInSlot(this.potion);

                        if (itemStack != null) {
                            IINetHandlerPlayClient iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                            WEnumHand hand$iv = WEnumHand.MAIN_HAND;

                            iinethandlerplayclient = iinethandlerplayclient1;
                            $i$f$createUseItemPacket = false;
                            ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand$iv);
                            iinethandlerplayclient.addToSendQueue(ipacket);
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                            this.msTimer.reset();
                        }

                        this.potion = -1;
                    }
                }

            }
        }
    }

    private final int findPotion(int startSlot, int endSlot) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        int i = startSlot;

        for (int i = endSlot; i < i; ++i) {
            IItemStack stack = thePlayer.getInventoryContainer().getSlot(i).getStack();

            if (stack != null && MinecraftInstance.classProvider.isItemPotion(stack.getItem()) && stack.isSplash()) {
                IItem iitem = stack.getItem();

                if (iitem == null) {
                    Intrinsics.throwNpe();
                }

                IItemPotion itemPotion = iitem.asItemPotion();
                Iterator iterator = itemPotion.getEffects(stack).iterator();

                IPotionEffect potionEffect;

                while (iterator.hasNext()) {
                    potionEffect = (IPotionEffect) iterator.next();
                    if (potionEffect.getPotionID() == MinecraftInstance.classProvider.getPotionEnum(PotionType.HEAL).getId()) {
                        return i;
                    }
                }

                if (!thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION))) {
                    iterator = itemPotion.getEffects(stack).iterator();

                    while (iterator.hasNext()) {
                        potionEffect = (IPotionEffect) iterator.next();
                        if (potionEffect.getPotionID() == MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION).getId()) {
                            return i;
                        }
                    }
                }
            }
        }

        return -1;
    }

    @Nullable
    public String getTag() {
        return String.valueOf(((Number) this.healthValue.get()).floatValue());
    }
}
