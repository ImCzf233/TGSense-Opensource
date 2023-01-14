package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Aimbot",
    description = "Automatically faces selected entities around you.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "centerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "clickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "fovValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "jitterValue", "lockValue", "onClickValue", "rangeValue", "turnSpeedValue", "onStrafe", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "LiquidBounce"}
)
public final class Aimbot extends Module {

    private final FloatValue rangeValue = new FloatValue("Range", 4.4F, 1.0F, 8.0F);
    private final FloatValue turnSpeedValue = new FloatValue("TurnSpeed", 2.0F, 1.0F, 180.0F);
    private final FloatValue fovValue = new FloatValue("FOV", 180.0F, 1.0F, 180.0F);
    private final BoolValue centerValue = new BoolValue("Center", false);
    private final BoolValue lockValue = new BoolValue("Lock", true);
    private final BoolValue onClickValue = new BoolValue("OnClick", false);
    private final BoolValue jitterValue = new BoolValue("Jitter", false);
    private final MSTimer clickTimer = new MSTimer();

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getGameSettings().getKeyBindAttack().isKeyDown()) {
            this.clickTimer.reset();
        }

        if (!((Boolean) this.onClickValue.get()).booleanValue() || !this.clickTimer.hasTimePassed(500L)) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IEntityPlayerSP thePlayer = ientityplayersp;
                float range = ((Number) this.rangeValue.get()).floatValue();
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                Iterable rotation = (Iterable) iworldclient.getLoadedEntityList();
                boolean yaw = false;
                Collection yawNegative = (Collection) (new ArrayList());
                boolean pitchNegative = false;
                Iterator e$iv = rotation.iterator();

                while (e$iv.hasNext()) {
                    Object v$iv = e$iv.next();
                    IEntity $i$a$-minBy-Aimbot$onStrafe$entity$2 = (IEntity) v$iv;
                    boolean $i$a$-filter-Aimbot$onStrafe$entity$1 = false;

                    if (EntityUtils.isSelected($i$a$-minBy-Aimbot$onStrafe$entity$2, true) && thePlayer.canEntityBeSeen($i$a$-minBy-Aimbot$onStrafe$entity$2) && PlayerExtensionKt.getDistanceToEntityBox((IEntity) thePlayer, $i$a$-minBy-Aimbot$onStrafe$entity$2) <= (double) range && RotationUtils.getRotationDifference($i$a$-minBy-Aimbot$onStrafe$entity$2) <= ((Number) this.fovValue.get()).doubleValue()) {
                        yawNegative.add(v$iv);
                    }
                }

                rotation = (Iterable) ((List) yawNegative);
                yaw = false;
                Iterator pitch = rotation.iterator();
                Object object;

                if (!pitch.hasNext()) {
                    object = null;
                } else {
                    Object yawNegative1 = pitch.next();

                    if (!pitch.hasNext()) {
                        object = yawNegative1;
                    } else {
                        IEntity pitchNegative1 = (IEntity) yawNegative1;
                        boolean e$iv1 = false;
                        double pitchNegative2 = RotationUtils.getRotationDifference(pitchNegative1);

                        do {
                            Object e$iv2 = pitch.next();
                            IEntity v$iv1 = (IEntity) e$iv2;
                            boolean $i$a$-minBy-Aimbot$onStrafe$entity$21 = false;
                            double v$iv2 = RotationUtils.getRotationDifference(v$iv1);

                            if (Double.compare(pitchNegative2, v$iv2) > 0) {
                                yawNegative1 = e$iv2;
                                pitchNegative2 = v$iv2;
                            }
                        } while (pitch.hasNext());

                        object = yawNegative1;
                    }
                }

                IEntity ientity = (IEntity) object;

                if (ientity != null) {
                    IEntity entity = ientity;

                    if (((Boolean) this.lockValue.get()).booleanValue() || !RotationUtils.isFaced(entity, (double) range)) {
                        Rotation rotation = RotationUtils.limitAngleChange(new Rotation(thePlayer.getRotationYaw(), thePlayer.getRotationPitch()), ((Boolean) this.centerValue.get()).booleanValue() ? RotationUtils.toRotation(RotationUtils.getCenter(entity.getEntityBoundingBox()), true) : RotationUtils.searchCenter(entity.getEntityBoundingBox(), false, false, true, false, range).getRotation(), (float) (((Number) this.turnSpeedValue.get()).doubleValue() + Math.random()));

                        Intrinsics.checkExpressionValueIsNotNull(rotation, "RotationUtils.limitAngle…om()).toFloat()\n        )");
                        Rotation rotation1 = rotation;

                        rotation1.toPlayer((IEntityPlayer) thePlayer);
                        if (((Boolean) this.jitterValue.get()).booleanValue()) {
                            yaw = Random.Default.nextBoolean();
                            boolean pitch1 = Random.Default.nextBoolean();
                            boolean yawNegative2 = Random.Default.nextBoolean();

                            pitchNegative = Random.Default.nextBoolean();
                            if (yaw) {
                                thePlayer.setRotationYaw(thePlayer.getRotationYaw() + (yawNegative2 ? -RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F) : RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F)));
                            }

                            if (pitch1) {
                                thePlayer.setRotationPitch(thePlayer.getRotationPitch() + (pitchNegative ? -RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F) : RandomUtils.INSTANCE.nextFloat(0.0F, 1.0F)));
                                if (thePlayer.getRotationPitch() > (float) 90) {
                                    thePlayer.setRotationPitch(90.0F);
                                } else if (thePlayer.getRotationPitch() < (float) -90) {
                                    thePlayer.setRotationPitch(-90.0F);
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}
