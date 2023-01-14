package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "Breadcrumbs",
    description = "Leaves a trail behind you.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u0012\u0010\u0015\u001a\u00020\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0007J\u0012\u0010\u0018\u001a\u00020\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u0019H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Breadcrumbs;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getColorBlueValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "getColorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getColorRainbow", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "getColorRedValue", "positions", "Ljava/util/LinkedList;", "", "onDisable", "", "onEnable", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Breadcrumbs extends Module {

    @NotNull
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    @NotNull
    private final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);
    @NotNull
    private final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);
    @NotNull
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final LinkedList positions = new LinkedList();

    @NotNull
    public final IntegerValue getColorRedValue() {
        return this.colorRedValue;
    }

    @NotNull
    public final IntegerValue getColorGreenValue() {
        return this.colorGreenValue;
    }

    @NotNull
    public final IntegerValue getColorBlueValue() {
        return this.colorBlueValue;
    }

    @NotNull
    public final BoolValue getColorRainbow() {
        return this.colorRainbow;
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        Color color = ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
        LinkedList linkedlist = this.positions;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (linkedlist){}

        try {
            boolean $i$a$-synchronized-Breadcrumbs$onRender3D$1 = false;

            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            MinecraftInstance.mc.getEntityRenderer().disableLightmap();
            GL11.glBegin(3);
            RenderUtils.glColor(color);
            double renderPosX = MinecraftInstance.mc.getRenderManager().getViewerPosX();
            double renderPosY = MinecraftInstance.mc.getRenderManager().getViewerPosY();
            double renderPosZ = MinecraftInstance.mc.getRenderManager().getViewerPosZ();
            Iterator iterator = this.positions.iterator();

            while (iterator.hasNext()) {
                double[] pos = (double[]) iterator.next();

                GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
            }

            GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
            GL11.glEnd();
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
            Unit unit = Unit.INSTANCE;
        } finally {
            ;
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        LinkedList linkedlist = this.positions;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (linkedlist){}

        try {
            boolean $i$a$-synchronized-Breadcrumbs$onUpdate$1 = false;
            LinkedList linkedlist1 = this.positions;
            double[] adouble = new double[3];
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            adouble[0] = ientityplayersp.getPosX();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            adouble[1] = ientityplayersp.getEntityBoundingBox().getMinY();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            adouble[2] = ientityplayersp.getPosZ();
            flag1 = linkedlist1.add(adouble);
        } finally {
            ;
        }

    }

    public void onEnable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            LinkedList linkedlist = this.positions;
            boolean flag = false;
            boolean flag1 = false;

            synchronized (linkedlist){}

            try {
                boolean $i$a$-synchronized-Breadcrumbs$onEnable$1 = false;

                this.positions.add(new double[] { thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) (thePlayer.getEyeHeight() * 0.5F), thePlayer.getPosZ()});
                flag1 = this.positions.add(new double[] { thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
            } finally {
                ;
            }

            super.onEnable();
        }
    }

    public void onDisable() {
        LinkedList linkedlist = this.positions;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (linkedlist){}

        try {
            boolean $i$a$-synchronized-Breadcrumbs$onDisable$1 = false;

            this.positions.clear();
            Unit unit = Unit.INSTANCE;
        } finally {
            ;
        }

        super.onDisable();
    }
}
