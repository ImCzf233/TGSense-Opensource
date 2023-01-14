package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

@ModuleInfo(
    name = "Trail",
    category = ModuleCategory.RENDER,
    description = "Leaves a trail behind you"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001*B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020&H\u0007J\u0010\u0010\'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020)H\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Trail;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "color", "Ljava/awt/Color;", "getColor", "()Ljava/awt/Color;", "colorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorBlueValue", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "drawTargets", "drawThePlayer", "fade", "fadeTime", "lineWidth", "points", "", "", "", "Lnet/ccbluex/liquidbounce/features/module/modules/render/Trail$BreadcrumbPoint;", "precision", "sphereList", "sphereScale", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "typeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "updatePoints", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "BreadcrumbPoint", "LiquidBounce"}
)
public final class Trail extends Module {

    private final ListValue typeValue = new ListValue("Type", new String[] { "Line", "Rect", "Sphere"}, "Line");
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private final IntegerValue colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final BoolValue fade = new BoolValue("Fade", true);
    private final BoolValue drawThePlayer = new BoolValue("DrawThePlayer", true);
    private final BoolValue drawTargets = new BoolValue("DrawTargets", true);
    private final IntegerValue fadeTime = new IntegerValue("FadeTime", 5, 1, 20);
    private final IntegerValue precision = new IntegerValue("Precision", 1, 1, 20);
    private final IntegerValue lineWidth = new IntegerValue("LineWidth", 1, 1, 10);
    private final FloatValue sphereScale = new FloatValue("SphereScale", 1.0F, 0.1F, 2.0F);
    private final Map points;
    private final int sphereList;

    @NotNull
    public final Color getColor() {
        return ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        int fTime = ((Number) this.fadeTime.get()).intValue() * 1000;
        long fadeSec = System.currentTimeMillis() - (long) fTime;
        float colorAlpha = ((Number) this.colorAlphaValue.get()).floatValue() / 255.0F;

        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        MinecraftInstance.mc.getEntityRenderer().disableLightmap();
        double renderPosX = MinecraftInstance.mc.getRenderManager().getViewerPosX();
        double renderPosY = MinecraftInstance.mc.getRenderManager().getViewerPosY();
        double renderPosZ = MinecraftInstance.mc.getRenderManager().getViewerPosZ();
        Map $this$forEach$iv = this.points;
        boolean $i$f$forEach = false;
        boolean flag = false;
        Iterator iterator = $this$forEach$iv.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry element$iv = (Entry) iterator.next();
            boolean $i$a$-forEach-Trail$onRender3D$1 = false;
            boolean flag1 = false;
            List mutableList = (List) element$iv.getValue();
            double lastPosX = 114514.0D;
            double lastPosY = 114514.0D;
            double lastPosZ = 114514.0D;
            String point = (String) this.typeValue.get();
            boolean flag2 = false;

            if (point == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String s = point.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            point = s;
            switch (point.hashCode()) {
            case 3321844:
                if (point.equals("line")) {
                    GL11.glLineWidth((float) ((Number) this.lineWidth.get()).intValue());
                    GL11.glEnable(2848);
                    GL11.glBegin(3);
                }
                break;

            case 3496420:
                if (point.equals("rect")) {
                    GL11.glDisable(2884);
                }
            }

            Iterator iterator1 = CollectionsKt.reversed((Iterable) mutableList).iterator();

            while (iterator1.hasNext()) {
                Trail.BreadcrumbPoint point1 = (Trail.BreadcrumbPoint) iterator1.next();
                float f;

                if (((Boolean) this.fade.get()).booleanValue()) {
                    float pct = (float) (point1.getTime() - fadeSec) / (float) fTime;

                    if (pct < (float) 0 || pct > (float) 1) {
                        mutableList.remove(point1);
                        continue;
                    }

                    f = pct;
                } else {
                    f = 1.0F;
                }

                float alpha = f * colorAlpha;

                RenderUtils.glColor2(point1.getColor(), alpha);
                String pct1 = (String) this.typeValue.get();
                boolean flag3 = false;

                if (pct1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s = pct1.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                pct1 = s;
                switch (pct1.hashCode()) {
                case -895981619:
                    if (pct1.equals("sphere")) {
                        GL11.glPushMatrix();
                        GL11.glTranslated(point1.getX() - renderPosX, point1.getY() - renderPosY, point1.getZ() - renderPosZ);
                        GL11.glScalef(((Number) this.sphereScale.get()).floatValue(), ((Number) this.sphereScale.get()).floatValue(), ((Number) this.sphereScale.get()).floatValue());
                        GL11.glCallList(this.sphereList);
                        GL11.glPopMatrix();
                    }
                    break;

                case 3321844:
                    if (pct1.equals("line")) {
                        GL11.glVertex3d(point1.getX() - renderPosX, point1.getY() - renderPosY, point1.getZ() - renderPosZ);
                    }
                    break;

                case 3496420:
                    if (pct1.equals("rect")) {
                        if (lastPosX != 114514.0D || lastPosY != 114514.0D || lastPosZ != 114514.0D) {
                            GL11.glBegin(7);
                            GL11.glVertex3d(point1.getX() - renderPosX, point1.getY() - renderPosY, point1.getZ() - renderPosZ);
                            GL11.glVertex3d(lastPosX, lastPosY, lastPosZ);
                            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            GL11.glVertex3d(lastPosX, lastPosY + (double) ientityplayersp.getHeight(), lastPosZ);
                            double d0 = point1.getX() - renderPosX;
                            double d1 = point1.getY() - renderPosY;

                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            GL11.glVertex3d(d0, d1 + (double) ientityplayersp.getHeight(), point1.getZ() - renderPosZ);
                            GL11.glEnd();
                        }

                        lastPosX = point1.getX() - renderPosX;
                        lastPosY = point1.getY() - renderPosY;
                        lastPosZ = point1.getZ() - renderPosZ;
                    }
                }
            }

            point = (String) this.typeValue.get();
            flag2 = false;
            if (point == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            s = point.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            point = s;
            switch (point.hashCode()) {
            case 3321844:
                if (point.equals("line")) {
                    GL11.glEnd();
                    GL11.glDisable(2848);
                }
                break;

            case 3496420:
                if (point.equals("rect")) {
                    GL11.glEnable(2884);
                }
            }
        }

        GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        Map $this$forEach$iv = this.points;
        boolean $i$f$forEach = false;
        boolean element$iv = false;
        Iterator it = $this$forEach$iv.entrySet().iterator();

        IWorldClient iworldclient;

        while (it.hasNext()) {
            Entry $i$a$-forEach-Trail$onUpdate$2 = (Entry) it.next();
            boolean $i$a$-forEach-Trail$onUpdate$1 = false;
            boolean flag = false;
            int id = ((Number) $i$a$-forEach-Trail$onUpdate$2.getKey()).intValue();

            iworldclient = MinecraftInstance.mc.getTheWorld();
            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            if (iworldclient.getEntityByID(id) == null) {
                this.points.remove(Integer.valueOf(id));
            }
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getTicksExisted() % ((Number) this.precision.get()).intValue() == 0) {
            if (((Boolean) this.drawTargets.get()).booleanValue()) {
                iworldclient = MinecraftInstance.mc.getTheWorld();
                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                Iterable $this$forEach$iv1 = (Iterable) iworldclient.getLoadedEntityList();

                $i$f$forEach = false;
                Iterator iterator = $this$forEach$iv1.iterator();

                while (iterator.hasNext()) {
                    Object element$iv1 = iterator.next();
                    IEntity it1 = (IEntity) element$iv1;
                    boolean $i$a$-forEach-Trail$onUpdate$21 = false;

                    if (EntityUtils.isSelected(it1, true)) {
                        if (it1 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                        }

                        this.updatePoints((EntityLivingBase) it1);
                    }
                }
            }

            if (((Boolean) this.drawThePlayer.get()).booleanValue()) {
                EntityPlayerSP entityplayersp = MinecraftInstance.mc2.player;

                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
                this.updatePoints((EntityLivingBase) entityplayersp);
            }

        }
    }

    private final void updatePoints(EntityLivingBase entity) {
        List list = (List) this.points.get(Integer.valueOf(entity.getEntityId()));

        if (list == null) {
            boolean flag = false;
            List list1 = (List) (new ArrayList());
            boolean flag1 = false;
            boolean flag2 = false;
            boolean $i$a$-also-Trail$updatePoints$1 = false;

            this.points.put(Integer.valueOf(entity.getEntityId()), list1);
            list = list1;
        }

        list.add(new Trail.BreadcrumbPoint(entity.posX, entity.getEntityBoundingBox().minY, entity.posZ, System.currentTimeMillis(), this.getColor().getRGB()));
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.points.clear();
    }

    public void onDisable() {
        this.points.clear();
    }

    public Trail() {
        boolean shaft = false;
        Map map = (Map) (new LinkedHashMap());

        this.points = map;
        this.sphereList = GL11.glGenLists(1);
        GL11.glNewList(this.sphereList, 4864);
        Sphere shaft1 = new Sphere();

        shaft1.setDrawStyle(100012);
        shaft1.draw(0.3F, 25, 10);
        GL11.glEndList();
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006\u0013"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Trail$BreadcrumbPoint;", "", "x", "", "y", "z", "time", "", "color", "", "(DDDJI)V", "getColor", "()I", "getTime", "()J", "getX", "()D", "getY", "getZ", "LiquidBounce"}
    )
    public static final class BreadcrumbPoint {

        private final double x;
        private final double y;
        private final double z;
        private final long time;
        private final int color;

        public final double getX() {
            return this.x;
        }

        public final double getY() {
            return this.y;
        }

        public final double getZ() {
            return this.z;
        }

        public final long getTime() {
            return this.time;
        }

        public final int getColor() {
            return this.color;
        }

        public BreadcrumbPoint(double x, double y, double z, long time, int color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.time = time;
            this.color = color;
        }
    }
}
