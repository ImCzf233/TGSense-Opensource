package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;

@ModuleInfo(
    name = "AntiFireBall",
    category = ModuleCategory.COMBAT,
    description = "Fuck"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AntiFireBall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "rotationValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "swingValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AntiFireBall extends Module {

    private final ListValue swingValue = new ListValue("Swing", new String[] { "Normal", "Packet", "None"}, "Normal");
    private final BoolValue rotationValue = new BoolValue("Rotation", true);
    private final MSTimer timer = new MSTimer();

    @EventTarget
    private final void onUpdate(UpdateEvent event) {
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterator iterator = iworldclient.getLoadedEntityList().iterator();

        while (iterator.hasNext()) {
            IEntity entity = (IEntity) iterator.next();

            if (entity instanceof EntityFireball) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if ((double) ientityplayersp.getDistanceToEntity(entity) < 5.5D && this.timer.hasTimePassed(300L)) {
                    if (((Boolean) this.rotationValue.get()).booleanValue()) {
                        RotationUtils.setTargetRotation(RotationUtils.getRotationsNonLivingEntity((Entity) entity));
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketUseEntity(entity, ICPacketUseEntity.WAction.ATTACK));
                    if (this.swingValue.equals("Normal")) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.swingItem();
                    } else if (this.swingValue.equals("Packet")) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketAnimation());
                    }

                    this.timer.reset();
                    break;
                }
            }
        }

    }
}
