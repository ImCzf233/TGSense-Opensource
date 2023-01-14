package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Zoot",
    description = "Removes all bad potion effects/fire.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/Zoot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "badEffectsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fireValue", "noAirValue", "hasBadEffect", "", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Zoot extends Module {

    private final BoolValue badEffectsValue = new BoolValue("BadEffects", true);
    private final BoolValue fireValue = new BoolValue("Fire", true);
    private final BoolValue noAirValue = new BoolValue("NoAir", false);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (!((Boolean) this.noAirValue.get()).booleanValue() || thePlayer.getOnGround()) {
                boolean $i$f$maxBy;

                if (((Boolean) this.badEffectsValue.get()).booleanValue()) {
                    Iterable $this$maxBy$iv = (Iterable) thePlayer.getActivePotionEffects();

                    $i$f$maxBy = false;
                    Iterator iterator$iv = $this$maxBy$iv.iterator();
                    boolean $i$a$-repeat-Zoot$onUpdate$1;
                    Object object;

                    if (!iterator$iv.hasNext()) {
                        object = null;
                    } else {
                        Object it = iterator$iv.next();

                        if (!iterator$iv.hasNext()) {
                            object = it;
                        } else {
                            IPotionEffect $i$a$-repeat-Zoot$onUpdate$2 = (IPotionEffect) it;

                            $i$a$-repeat-Zoot$onUpdate$1 = false;
                            int i = $i$a$-repeat-Zoot$onUpdate$2.getDuration();

                            do {
                                Object object1 = iterator$iv.next();
                                IPotionEffect v$iv = (IPotionEffect) object1;
                                boolean $i$a$-maxBy-Zoot$onUpdate$effect$1 = false;
                                int j = v$iv.getDuration();

                                if (i < j) {
                                    it = object1;
                                    i = j;
                                }
                            } while (iterator$iv.hasNext());

                            object = it;
                        }
                    }

                    IPotionEffect effect = (IPotionEffect) object;

                    if (effect != null) {
                        int k = effect.getDuration() / 20;

                        $i$f$maxBy = false;
                        boolean flag = false;
                        int l = 0;

                        for (int i1 = k; l < i1; ++l) {
                            $i$a$-repeat-Zoot$onUpdate$1 = false;
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                        }
                    }
                }

                if (((Boolean) this.fireValue.get()).booleanValue() && !thePlayer.getCapabilities().isCreativeMode() && thePlayer.isBurning()) {
                    byte b0 = 9;
                    boolean flag1 = false;

                    $i$f$maxBy = false;
                    int j1 = 0;

                    for (byte b1 = b0; j1 < b1; ++j1) {
                        boolean flag2 = false;

                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(thePlayer.getOnGround()));
                    }
                }

            }
        }
    }

    private final boolean hasBadEffect() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            return false;
        } else {
            IEntityPlayerSP thePlayer = ientityplayersp;

            return thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.HUNGER)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SLOWDOWN)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.DIG_SLOWDOWN)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.HARM)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.CONFUSION)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.WEAKNESS)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.WITHER)) || thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.POISON));
        }
    }
}
