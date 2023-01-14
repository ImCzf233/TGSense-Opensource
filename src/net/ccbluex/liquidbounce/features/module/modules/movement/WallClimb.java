package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "WallClimb",
    description = "Allows you to climb up walls like a spider.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/WallClimb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "checkerClimbMotionValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "clipMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "glitch", "", "modeValue", "waited", "", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"}
)
public final class WallClimb extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Simple", "CheckerClimb", "Clip", "AAC3.3.12", "AACGlide"}, "Simple");
    private final ListValue clipMode = new ListValue("ClipMode", new String[] { "Jump", "Fast"}, "Fast");
    private final FloatValue checkerClimbMotionValue = new FloatValue("CheckerClimbMotion", 0.0F, 0.0F, 1.0F);
    private boolean glitch;
    private int waited;

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isInLava()) {
                if (StringsKt.equals("simple", (String) this.modeValue.get(), true)) {
                    event.setY(0.2D);
                    thePlayer.setMotionY(0.0D);
                }

            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (event.getEventState() == EventState.POST && thePlayer != null) {
            String s = (String) this.modeValue.get();
            boolean isInsideBlock = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case 3056464:
                    if (s.equals("clip")) {
                        if (thePlayer.getMotionY() < (double) 0) {
                            this.glitch = true;
                        }

                        if (thePlayer.isCollidedHorizontally()) {
                            String s2 = (String) this.clipMode.get();
                            boolean flag = false;

                            if (s2 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s1 = s2.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                            s2 = s1;
                            switch (s2.hashCode()) {
                            case 3135580:
                                if (s2.equals("fast")) {
                                    if (thePlayer.getOnGround()) {
                                        thePlayer.setMotionY(0.42D);
                                    } else if (thePlayer.getMotionY() < (double) 0) {
                                        thePlayer.setMotionY(-0.3D);
                                    }
                                }
                                break;

                            case 3273774:
                                if (s2.equals("jump") && thePlayer.getOnGround()) {
                                    thePlayer.jump();
                                }
                            }
                        }
                    }
                    break;

                case 375151938:
                    if (s.equals("aacglide")) {
                        if (!thePlayer.isCollidedHorizontally() || thePlayer.isOnLadder()) {
                            return;
                        }

                        thePlayer.setMotionY(-0.19D);
                    }
                    break;

                case 1076723744:
                    if (s.equals("checkerclimb")) {
                        isInsideBlock = BlockUtils.collideBlockIntersects(thePlayer.getEntityBoundingBox(), (Function1) null.INSTANCE);
                        float motion = ((Number) this.checkerClimbMotionValue.get()).floatValue();

                        if (isInsideBlock && motion != 0.0F) {
                            thePlayer.setMotionY((double) motion);
                        }
                    }
                    break;

                case 1492139162:
                    if (s.equals("aac3.3.12")) {
                        if (thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder()) {
                            int i = this.waited++;

                            if (this.waited == 1) {
                                thePlayer.setMotionY(0.43D);
                            }

                            if (this.waited == 12) {
                                thePlayer.setMotionY(0.43D);
                            }

                            if (this.waited == 23) {
                                thePlayer.setMotionY(0.43D);
                            }

                            if (this.waited == 29) {
                                thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.5D, thePlayer.getPosZ());
                            }

                            if (this.waited >= 30) {
                                this.waited = 0;
                            }
                        } else if (thePlayer.getOnGround()) {
                            this.waited = 0;
                        }
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isCPacketPlayer(event.getPacket())) {
            ICPacketPlayer packetPlayer = event.getPacket().asCPacketPlayer();

            if (this.glitch) {
                float yaw = (float) MovementUtils.getDirection();
                double d0 = packetPlayer.getX();
                boolean flag = false;
                float f = (float) Math.sin((double) yaw);

                packetPlayer.setX(d0 - (double) f * 1.0E-8D);
                d0 = packetPlayer.getZ();
                flag = false;
                f = (float) Math.cos((double) yaw);
                packetPlayer.setZ(d0 + (double) f * 1.0E-8D);
                this.glitch = false;
            }
        }

    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            String mode = (String) this.modeValue.get();
            boolean flag = false;

            if (mode == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s = mode.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                String s1 = s;

                switch (s1.hashCode()) {
                case 3056464:
                    if (s1.equals("clip") && event.getBlock() != null && MinecraftInstance.mc.getThePlayer() != null && MinecraftInstance.classProvider.isBlockAir(event.getBlock()) && (double) event.getY() < thePlayer.getPosY() && thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isInLava()) {
                        event.setBoundingBox(MinecraftInstance.classProvider.createAxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D).offset(thePlayer.getPosX(), (double) ((int) thePlayer.getPosY()) - 1.0D, thePlayer.getPosZ()));
                    }
                    break;

                case 1076723744:
                    if (s1.equals("checkerclimb") && (double) event.getY() > thePlayer.getPosY()) {
                        event.setBoundingBox((IAxisAlignedBB) null);
                    }
                }

            }
        }
    }
}
