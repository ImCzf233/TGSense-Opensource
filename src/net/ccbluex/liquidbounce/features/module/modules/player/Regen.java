package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Regen",
    description = "Regenerates your health much faster.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/Regen;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "foodValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "noAirValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "potionEffectValue", "resetTimer", "", "speedValue", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Regen extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Vanilla", "Spartan", "NewSpartan"}, "Vanilla");
    private final IntegerValue healthValue = new IntegerValue("Health", 18, 0, 20);
    private final IntegerValue foodValue = new IntegerValue("Food", 18, 0, 20);
    private final IntegerValue speedValue = new IntegerValue("Speed", 100, 1, 100);
    private final BoolValue noAirValue = new BoolValue("NoAir", false);
    private final BoolValue potionEffectValue = new BoolValue("PotionEffect", false);
    private boolean resetTimer;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.resetTimer) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
        }

        this.resetTimer = false;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if ((!((Boolean) this.noAirValue.get()).booleanValue() || thePlayer.getOnGround()) && !thePlayer.getCapabilities().isCreativeMode() && thePlayer.getFoodStats().getFoodLevel() > ((Number) this.foodValue.get()).intValue() && thePlayer.isEntityAlive() && thePlayer.getHealth() < ((Number) this.healthValue.get()).floatValue()) {
                if (((Boolean) this.potionEffectValue.get()).booleanValue() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION))) {
                    return;
                }

                String s = (String) this.modeValue.get();
                boolean flag = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                boolean flag1;
                boolean flag2;
                boolean $i$a$-repeat-Regen$onUpdate$2;
                int i;

                switch (s.hashCode()) {
                case -2011701869:
                    if (s.equals("spartan")) {
                        if (MovementUtils.isMoving() || !thePlayer.getOnGround()) {
                            return;
                        }

                        byte b0 = 9;

                        flag1 = false;
                        flag2 = false;
                        i = 0;

                        for (byte b1 = b0; i < b1; ++i) {
                            $i$a$-repeat-Regen$onUpdate$2 = false;
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                        }

                        MinecraftInstance.mc.getTimer().setTimerSpeed(0.45F);
                        this.resetTimer = true;
                    }
                    break;

                case 233102203:
                    if (s.equals("vanilla")) {
                        int j = ((Number) this.speedValue.get()).intValue();

                        flag1 = false;
                        flag2 = false;
                        i = 0;

                        for (int k = j; i < k; ++i) {
                            $i$a$-repeat-Regen$onUpdate$2 = false;
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                        }
                    }
                    break;

                case 1213341683:
                    if (s.equals("newspartan")) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getTicksExisted() % 5 == 0) {
                            this.resetTimer = true;
                        }

                        MinecraftInstance.mc.getTimer().setTimerSpeed(0.98F);
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(true));
                    }
                }
            }

        }
    }
}
