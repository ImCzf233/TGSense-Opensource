package me.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\u0005H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001bH\u0007R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001c"},
    d2 = { "Lme/manager/CombatManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "inCombat", "", "getInCombat", "()Z", "setInCombat", "(Z)V", "lastAttackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "target", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getTarget", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "setTarget", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)V", "getNearByEntity", "radius", "", "handleEvents", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class CombatManager extends MinecraftInstance implements Listenable {

    private boolean inCombat;
    private final MSTimer lastAttackTimer = new MSTimer();
    @Nullable
    private IEntityLivingBase target;

    public final boolean getInCombat() {
        return this.inCombat;
    }

    public final void setInCombat(boolean <set-?>) {
        this.inCombat = <set-?>;
    }

    @Nullable
    public final IEntityLivingBase getTarget() {
        return this.target;
    }

    public final void setTarget(@Nullable IEntityLivingBase <set-?>) {
        this.target = <set-?>;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null) {
            MovementUtils.INSTANCE.updateBlocksPerSecond();
            this.inCombat = false;
            if (!this.lastAttackTimer.hasTimePassed(1000L)) {
                this.inCombat = true;
            } else {
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                Iterator iterator = iworldclient.getLoadedEntityList().iterator();

                while (iterator.hasNext()) {
                    IEntity entity = (IEntity) iterator.next();

                    if (entity instanceof IEntityLivingBase) {
                        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (entity.getDistanceToEntity((IEntity) ientityplayersp) < (float) 7 && EntityUtils.isSelected(entity, true)) {
                            this.inCombat = true;
                            break;
                        }
                    }
                }

                if (this.target != null) {
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    IEntityLivingBase ientitylivingbase = this.target;

                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp1.getDistanceToEntity((IEntity) ientitylivingbase) <= (float) 7 && this.inCombat) {
                        IEntityLivingBase ientitylivingbase1 = this.target;

                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!ientitylivingbase1.isDead()) {
                            return;
                        }
                    }

                    this.target = (IEntityLivingBase) null;
                }

            }
        }
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.getTargetEntity() instanceof IEntityLivingBase && EntityUtils.isSelected(event.getTargetEntity(), true)) {
            this.target = (IEntityLivingBase) event.getTargetEntity();
        }

        this.lastAttackTimer.reset();
    }

    @Nullable
    public final IEntityLivingBase getNearByEntity(float radius) {
        IEntityLivingBase $this$sortedBy$iv;

        try {
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            Iterable $this$sortedBy$iv1 = (Iterable) iworldclient.getLoadedEntityList();
            boolean e = false;
            Collection destination$iv$iv = (Collection) (new ArrayList());
            boolean $i$f$filterTo = false;
            Iterator iterator = $this$sortedBy$iv1.iterator();

            while (iterator.hasNext()) {
                Object element$iv$iv = iterator.next();
                IEntity it = (IEntity) element$iv$iv;
                boolean $i$a$-filter-CombatManager$getNearByEntity$1 = false;
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getDistanceToEntity(it) < radius && EntityUtils.isSelected(it, true)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }

            $this$sortedBy$iv1 = (Iterable) ((List) destination$iv$iv);
            e = false;
            boolean destination$iv$iv1 = false;
            Comparator $i$f$filterTo1 = (Comparator) (new CombatManager$getNearByEntity$$inlined$sortedBy$1());

            $this$sortedBy$iv = (IEntityLivingBase) CollectionsKt.sortedWith($this$sortedBy$iv1, $i$f$filterTo1).get(0);
        } catch (Exception exception) {
            $this$sortedBy$iv = null;
        }

        return $this$sortedBy$iv;
    }

    public boolean handleEvents() {
        return true;
    }
}
