package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "BowAimbot",
    description = "Automatically aims at players when using a bow.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0006\u0010\u0013\u001a\u00020\u0010J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/BowAimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "markValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "predictSizeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "predictValue", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "silentValue", "target", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "throughWallsValue", "getTarget", "throughWalls", "", "priorityMode", "", "hasTarget", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class BowAimbot extends Module {

    private final BoolValue silentValue = new BoolValue("Silent", true);
    private final BoolValue predictValue = new BoolValue("Predict", true);
    private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
    private final FloatValue predictSizeValue = new FloatValue("PredictSize", 2.0F, 0.1F, 5.0F);
    private final ListValue priorityValue = new ListValue("Priority", new String[] { "Health", "Distance", "Direction"}, "Direction");
    private final BoolValue markValue = new BoolValue("Mark", true);
    private IEntity target;

    public void onDisable() {
        this.target = (IEntity) null;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        IItem iitem;
        IClassProvider iclassprovider;
        label21: {
            Intrinsics.checkParameterIsNotNull(event, "event");
            this.target = (IEntity) null;
            iclassprovider = MinecraftInstance.classProvider;
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IItemStack iitemstack = ientityplayersp.getItemInUse();

                if (iitemstack != null) {
                    iitem = iitemstack.getItem();
                    break label21;
                }
            }

            iitem = null;
        }

        if (iclassprovider.isItemBow(iitem)) {
            IEntity ientity = this.getTarget(((Boolean) this.throughWallsValue.get()).booleanValue(), (String) this.priorityValue.get());

            if (ientity == null) {
                return;
            }

            IEntity entity = ientity;

            this.target = entity;
            RotationUtils.faceBow(this.target, ((Boolean) this.silentValue.get()).booleanValue(), ((Boolean) this.predictValue.get()).booleanValue(), ((Number) this.predictSizeValue.get()).floatValue());
        }

    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.target != null && !StringsKt.equals((String) this.priorityValue.get(), "Multi", true) && ((Boolean) this.markValue.get()).booleanValue()) {
            RenderUtils.drawPlatform(this.target, new Color(37, 126, 255, 70));
        }

    }

    private final IEntity getTarget(boolean throughWalls, String priorityMode) {
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterable $this$minBy$iv = (Iterable) iworldclient.getLoadedEntityList();
        boolean $i$f$minBy = false;
        Collection minElem$iv = (Collection) (new ArrayList());
        boolean minValue$iv = false;
        Iterator e$iv = $this$minBy$iv.iterator();

        IEntityPlayerSP ientityplayersp;

        while (e$iv.hasNext()) {
            Object v$iv;
            boolean flag;
            label111: {
                v$iv = e$iv.next();
                IEntity $i$a$-minBy-BowAimbot$getTarget$3 = (IEntity) v$iv;
                boolean $i$a$-filter-BowAimbot$getTarget$targets$1 = false;

                if (MinecraftInstance.classProvider.isEntityLivingBase($i$a$-minBy-BowAimbot$getTarget$3) && EntityUtils.isSelected($i$a$-minBy-BowAimbot$getTarget$3, true)) {
                    label108: {
                        if (!throughWalls) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (!ientityplayersp.canEntityBeSeen($i$a$-minBy-BowAimbot$getTarget$3)) {
                                break label108;
                            }
                        }

                        flag = true;
                        break label111;
                    }
                }

                flag = false;
            }

            if (flag) {
                minElem$iv.add(v$iv);
            }
        }

        List targets = (List) minElem$iv;
        Iterator iterator$iv;
        Object minElem$iv1;
        IEntity minValue$iv1;
        float minValue$iv2;
        boolean e$iv1;
        Object e$iv2;
        IEntity v$iv1;
        float v$iv2;
        boolean $i$a$-minBy-BowAimbot$getTarget$31;
        Object object;
        IEntity ientity;

        if (StringsKt.equals(priorityMode, "distance", true)) {
            $this$minBy$iv = (Iterable) targets;
            $i$f$minBy = false;
            iterator$iv = $this$minBy$iv.iterator();
            if (!iterator$iv.hasNext()) {
                object = null;
            } else {
                minElem$iv1 = iterator$iv.next();
                if (!iterator$iv.hasNext()) {
                    object = minElem$iv1;
                } else {
                    minValue$iv1 = (IEntity) minElem$iv1;
                    e$iv1 = false;
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    minValue$iv2 = ientityplayersp.getDistanceToEntity(minValue$iv1);

                    do {
                        e$iv2 = iterator$iv.next();
                        v$iv1 = (IEntity) e$iv2;
                        $i$a$-minBy-BowAimbot$getTarget$31 = false;
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        v$iv2 = ientityplayersp.getDistanceToEntity(v$iv1);
                        if (Float.compare(minValue$iv2, v$iv2) > 0) {
                            minElem$iv1 = e$iv2;
                            minValue$iv2 = v$iv2;
                        }
                    } while (iterator$iv.hasNext());

                    object = minElem$iv1;
                }
            }

            ientity = (IEntity) object;
        } else if (StringsKt.equals(priorityMode, "direction", true)) {
            $this$minBy$iv = (Iterable) targets;
            $i$f$minBy = false;
            iterator$iv = $this$minBy$iv.iterator();
            if (!iterator$iv.hasNext()) {
                object = null;
            } else {
                minElem$iv1 = iterator$iv.next();
                if (!iterator$iv.hasNext()) {
                    object = minElem$iv1;
                } else {
                    minValue$iv1 = (IEntity) minElem$iv1;
                    e$iv1 = false;
                    double minValue$iv3 = RotationUtils.getRotationDifference(minValue$iv1);

                    do {
                        e$iv2 = iterator$iv.next();
                        v$iv1 = (IEntity) e$iv2;
                        $i$a$-minBy-BowAimbot$getTarget$31 = false;
                        double v$iv3 = RotationUtils.getRotationDifference(v$iv1);

                        if (Double.compare(minValue$iv3, v$iv3) > 0) {
                            minElem$iv1 = e$iv2;
                            minValue$iv3 = v$iv3;
                        }
                    } while (iterator$iv.hasNext());

                    object = minElem$iv1;
                }
            }

            ientity = (IEntity) object;
        } else if (StringsKt.equals(priorityMode, "health", true)) {
            $this$minBy$iv = (Iterable) targets;
            $i$f$minBy = false;
            iterator$iv = $this$minBy$iv.iterator();
            if (!iterator$iv.hasNext()) {
                object = null;
            } else {
                minElem$iv1 = iterator$iv.next();
                if (!iterator$iv.hasNext()) {
                    object = minElem$iv1;
                } else {
                    minValue$iv1 = (IEntity) minElem$iv1;
                    e$iv1 = false;
                    minValue$iv2 = minValue$iv1.asEntityLivingBase().getHealth();

                    do {
                        e$iv2 = iterator$iv.next();
                        v$iv1 = (IEntity) e$iv2;
                        $i$a$-minBy-BowAimbot$getTarget$31 = false;
                        v$iv2 = v$iv1.asEntityLivingBase().getHealth();
                        if (Float.compare(minValue$iv2, v$iv2) > 0) {
                            minElem$iv1 = e$iv2;
                            minValue$iv2 = v$iv2;
                        }
                    } while (iterator$iv.hasNext());

                    object = minElem$iv1;
                }
            }

            ientity = (IEntity) object;
        } else {
            ientity = null;
        }

        return ientity;
    }

    public final boolean hasTarget() {
        boolean flag;

        if (this.target != null) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IEntity ientity = this.target;

            if (this.target == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.canEntityBeSeen(ientity)) {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }
}
