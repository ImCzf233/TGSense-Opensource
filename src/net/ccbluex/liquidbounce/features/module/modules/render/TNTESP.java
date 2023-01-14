package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "TNTESP",
    description = "Allows you to see ignited TNT blocks through walls.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/TNTESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"}
)
public final class TNTESP extends Module {

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterable $this$forEach$iv = (Iterable) iworldclient.getLoadedEntityList();
        IClassProvider $i$f$forEach = MinecraftInstance.classProvider;
        boolean $i$f$filter = false;
        Collection it = (Collection) (new ArrayList());
        boolean $i$a$-forEach-TNTESP$onRender3D$2 = false;
        Iterator iterator = $this$forEach$iv.iterator();

        while (iterator.hasNext()) {
            Object element$iv$iv = iterator.next();
            boolean $i$a$-unknown-TNTESP$onRender3D$1 = false;

            if ($i$f$forEach.isEntityTNTPrimed(element$iv$iv)) {
                it.add(element$iv$iv);
            }
        }

        $this$forEach$iv = (Iterable) ((List) it);
        boolean $i$f$forEach1 = false;
        Iterator $i$f$filter1 = $this$forEach$iv.iterator();

        while ($i$f$filter1.hasNext()) {
            Object element$iv = $i$f$filter1.next();
            IEntity it1 = (IEntity) element$iv;

            $i$a$-forEach-TNTESP$onRender3D$2 = false;
            RenderUtils.drawEntityBox(it1, Color.RED, false);
        }

    }
}
