package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "FreeCam",
    description = "Allows you to move out of your body.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\u0010\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0012\u0010\u0014\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0015H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/FreeCam;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fakePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "flyValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "noClipValue", "oldX", "", "oldY", "oldZ", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class FreeCam extends Module {

    private final FloatValue speedValue = new FloatValue("Speed", 0.8F, 0.1F, 2.0F);
    private final BoolValue flyValue = new BoolValue("Fly", true);
    private final BoolValue noClipValue = new BoolValue("NoClip", true);
    private IEntityOtherPlayerMP fakePlayer;
    private double oldX;
    private double oldY;
    private double oldZ;

    public void onEnable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            this.oldX = thePlayer.getPosX();
            this.oldY = thePlayer.getPosY();
            this.oldZ = thePlayer.getPosZ();
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            IEntityOtherPlayerMP playerMP = iclassprovider.createEntityOtherPlayerMP(iworldclient, thePlayer.getGameProfile());

            playerMP.setRotationYawHead(thePlayer.getRotationYawHead());
            playerMP.setRenderYawOffset(thePlayer.getRenderYawOffset());
            playerMP.setRotationYawHead(thePlayer.getRotationYawHead());
            playerMP.copyLocationAndAnglesFrom(thePlayer);
            IWorldClient iworldclient1 = MinecraftInstance.mc.getTheWorld();

            if (iworldclient1 == null) {
                Intrinsics.throwNpe();
            }

            iworldclient1.addEntityToWorld(-1000, (IEntity) playerMP);
            if (((Boolean) this.noClipValue.get()).booleanValue()) {
                thePlayer.setNoClip(true);
            }

            this.fakePlayer = playerMP;
        }
    }

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null && this.fakePlayer != null) {
            thePlayer.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, thePlayer.getRotationYaw(), thePlayer.getRotationPitch());
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            IEntityOtherPlayerMP ientityotherplayermp = this.fakePlayer;

            if (this.fakePlayer == null) {
                Intrinsics.throwNpe();
            }

            iworldclient.removeEntityFromWorld(ientityotherplayermp.getEntityId());
            this.fakePlayer = (IEntityOtherPlayerMP) null;
            thePlayer.setMotionX(0.0D);
            thePlayer.setMotionY(0.0D);
            thePlayer.setMotionZ(0.0D);
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;

        if (((Boolean) this.noClipValue.get()).booleanValue()) {
            thePlayer.setNoClip(true);
        }

        thePlayer.setFallDistance(0.0F);
        if (((Boolean) this.flyValue.get()).booleanValue()) {
            float value = ((Number) this.speedValue.get()).floatValue();

            thePlayer.setMotionY(0.0D);
            thePlayer.setMotionX(0.0D);
            thePlayer.setMotionZ(0.0D);
            if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                thePlayer.setMotionY(thePlayer.getMotionY() + (double) value);
            }

            if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                thePlayer.setMotionY(thePlayer.getMotionY() - (double) value);
            }

            MovementUtils.strafe(value);
        }

    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (MinecraftInstance.classProvider.isCPacketPlayer(packet) || MinecraftInstance.classProvider.isCPacketEntityAction(packet)) {
            event.cancelEvent();
        }

    }
}
