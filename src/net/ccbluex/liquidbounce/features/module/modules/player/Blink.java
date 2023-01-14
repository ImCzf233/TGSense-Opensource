package net.ccbluex.liquidbounce.features.module.modules.player;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "Blink",
    description = "Suspends all movement packets.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\b\u0010\u001a\u001a\u00020\u0018H\u0016J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0012\u0010\u001e\u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010\u001fH\u0007J\u0012\u0010 \u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010!H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\""},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/Blink;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "disableLogger", "", "fakePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "positions", "Ljava/util/LinkedList;", "", "pulseDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "pulseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "pulseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "blink", "", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Blink extends Module {

    private final LinkedBlockingQueue packets = new LinkedBlockingQueue();
    private IEntityOtherPlayerMP fakePlayer;
    private boolean disableLogger;
    private final LinkedList positions = new LinkedList();
    private final BoolValue pulseValue = new BoolValue("Pulse", false);
    private final IntegerValue pulseDelayValue = new IntegerValue("PulseDelay", 1000, 500, 5000);
    private final MSTimer pulseTimer = new MSTimer();

    public void onEnable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (!((Boolean) this.pulseValue.get()).booleanValue()) {
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                IEntityOtherPlayerMP faker = iclassprovider.createEntityOtherPlayerMP(iworldclient, thePlayer.getGameProfile());

                faker.setRotationYawHead(thePlayer.getRotationYawHead());
                faker.setRenderYawOffset(thePlayer.getRenderYawOffset());
                faker.copyLocationAndAnglesFrom(thePlayer);
                faker.setRotationYawHead(thePlayer.getRotationYawHead());
                IWorldClient iworldclient1 = MinecraftInstance.mc.getTheWorld();

                if (iworldclient1 == null) {
                    Intrinsics.throwNpe();
                }

                iworldclient1.addEntityToWorld(-1337, (IEntity) faker);
                this.fakePlayer = faker;
            }

            LinkedList faker1 = this.positions;
            boolean flag = false;
            boolean flag1 = false;

            synchronized (faker1){}

            try {
                boolean $i$a$-synchronized-Blink$onEnable$1 = false;

                this.positions.add(new double[] { thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) (thePlayer.getEyeHeight() / (float) 2), thePlayer.getPosZ()});
                flag1 = this.positions.add(new double[] { thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
            } finally {
                ;
            }

            this.pulseTimer.reset();
        }
    }

    public void onDisable() {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            this.blink();
            IEntityOtherPlayerMP faker = this.fakePlayer;

            if (faker != null) {
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient != null) {
                    iworldclient.removeEntityFromWorld(faker.getEntityId());
                }

                this.fakePlayer = (IEntityOtherPlayerMP) null;
            }

        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (MinecraftInstance.mc.getThePlayer() != null && !this.disableLogger) {
            if (MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
                event.cancelEvent();
            }

            if (MinecraftInstance.classProvider.isCPacketPlayerPosition(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.classProvider.isCPacketPlayerBlockPlacement(packet) || MinecraftInstance.classProvider.isCPacketAnimation(packet) || MinecraftInstance.classProvider.isCPacketEntityAction(packet) || MinecraftInstance.classProvider.isCPacketUseEntity(packet)) {
                event.cancelEvent();
                this.packets.add(packet);
            }

        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            LinkedList linkedlist = this.positions;
            boolean flag = false;
            boolean flag1 = false;

            synchronized (linkedlist){}

            try {
                boolean $i$a$-synchronized-Blink$onUpdate$1 = false;

                flag1 = this.positions.add(new double[] { thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
            } finally {
                ;
            }

            if (((Boolean) this.pulseValue.get()).booleanValue() && this.pulseTimer.hasTimePassed((long) ((Number) this.pulseDelayValue.get()).intValue())) {
                this.blink();
                this.pulseTimer.reset();
            }

        }
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        Breadcrumbs breadcrumbs = (Breadcrumbs) LiquidBounce.INSTANCE.getModuleManager().getModule(Breadcrumbs.class);

        if (breadcrumbs == null) {
            Intrinsics.throwNpe();
        }

        Color color = ((Boolean) breadcrumbs.getColorRainbow().get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) breadcrumbs.getColorRedValue().get()).intValue(), ((Number) breadcrumbs.getColorGreenValue().get()).intValue(), ((Number) breadcrumbs.getColorBlueValue().get()).intValue());
        LinkedList linkedlist = this.positions;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (linkedlist){}

        try {
            boolean $i$a$-synchronized-Blink$onRender3D$1 = false;

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

    @NotNull
    public String getTag() {
        return String.valueOf(this.packets.size());
    }

    private final void blink() {
        try {
            this.disableLogger = true;

            while (!this.packets.isEmpty()) {
                INetworkManager inetworkmanager = MinecraftInstance.mc.getNetHandler().getNetworkManager();
                Object object = this.packets.take();

                Intrinsics.checkExpressionValueIsNotNull(object, "packets.take()");
                inetworkmanager.sendPacket((IPacket) object);
            }

            this.disableLogger = false;
        } catch (Exception exception) {
            exception.printStackTrace();
            this.disableLogger = false;
        }

        LinkedList e = this.positions;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (e){}

        try {
            boolean $i$a$-synchronized-Blink$blink$1 = false;

            this.positions.clear();
            Unit unit = Unit.INSTANCE;
        } finally {
            ;
        }

    }
}
