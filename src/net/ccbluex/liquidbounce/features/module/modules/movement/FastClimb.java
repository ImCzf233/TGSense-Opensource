package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "FastClimb",
    description = "Allows you to climb up ladders and vines faster.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0012H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/FastClimb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "LiquidBounce"}
)
public final class FastClimb extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Vanilla", "Clip", "AAC3.0.0", "AAC3.0.5", "SAAC3.1.2", "AAC3.1.2"}, "Vanilla");
    private final FloatValue speedValue = new FloatValue("Speed", 0.2872F, 0.01F, 5.0F);

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String mode = (String) this.modeValue.get();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (StringsKt.equals(mode, "Vanilla", true) && thePlayer.isCollidedHorizontally() && thePlayer.isOnLadder()) {
                event.setY((double) ((Number) this.speedValue.get()).floatValue());
                thePlayer.setMotionY(0.0D);
            } else if (StringsKt.equals(mode, "AAC3.0.0", true) && thePlayer.isCollidedHorizontally()) {
                double d0 = 0.0D;
                double d1 = 0.0D;
                IEnumFacing horizontalFacing = thePlayer.getHorizontalFacing();

                if (horizontalFacing.isNorth()) {
                    d1 = -0.99D;
                } else if (horizontalFacing.isEast()) {
                    d0 = 0.99D;
                } else if (horizontalFacing.isSouth()) {
                    d1 = 0.99D;
                } else if (horizontalFacing.isWest()) {
                    d0 = -0.99D;
                }

                IBlock iblock = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX() + d0, thePlayer.getPosY(), thePlayer.getPosZ() + d1));

                if (MinecraftInstance.classProvider.isBlockLadder(iblock) || MinecraftInstance.classProvider.isBlockVine(iblock)) {
                    event.setY(0.5D);
                    thePlayer.setMotionY(0.0D);
                }
            } else if (StringsKt.equals(mode, "AAC3.0.5", true) && MinecraftInstance.mc.getGameSettings().getKeyBindForward().isKeyDown() && BlockUtils.collideBlockIntersects(thePlayer.getEntityBoundingBox(), (Function1) null.INSTANCE)) {
                event.setX(0.0D);
                event.setY(0.5D);
                event.setZ(0.0D);
                thePlayer.setMotionX(0.0D);
                thePlayer.setMotionY(0.0D);
                thePlayer.setMotionZ(0.0D);
            } else if (StringsKt.equals(mode, "SAAC3.1.2", true) && thePlayer.isCollidedHorizontally() && thePlayer.isOnLadder()) {
                event.setY(0.1649D);
                thePlayer.setMotionY(0.0D);
            } else if (StringsKt.equals(mode, "AAC3.1.2", true) && thePlayer.isCollidedHorizontally() && thePlayer.isOnLadder()) {
                event.setY(0.1699D);
                thePlayer.setMotionY(0.0D);
            } else if (StringsKt.equals(mode, "Clip", true) && thePlayer.isOnLadder() && MinecraftInstance.mc.getGameSettings().getKeyBindForward().isKeyDown()) {
                int i = (int) thePlayer.getPosY();
                int i = (int) thePlayer.getPosY() + 8;

                if (i <= i) {
                    while (true) {
                        IBlock block = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), (double) i, thePlayer.getPosZ()));

                        if (!MinecraftInstance.classProvider.isBlockLadder(block)) {
                            double x = 0.0D;
                            double z = 0.0D;
                            IEnumFacing horizontalFacing1 = thePlayer.getHorizontalFacing();

                            if (horizontalFacing1.isNorth()) {
                                z = -1.0D;
                            } else if (horizontalFacing1.isEast()) {
                                x = 1.0D;
                            } else if (horizontalFacing1.isSouth()) {
                                z = 1.0D;
                            } else if (horizontalFacing1.isWest()) {
                                x = -1.0D;
                            }

                            thePlayer.setPosition(thePlayer.getPosX() + x, (double) i, thePlayer.getPosZ() + z);
                            break;
                        }

                        thePlayer.setPosition(thePlayer.getPosX(), (double) i, thePlayer.getPosZ());
                        if (i == i) {
                            break;
                        }

                        ++i;
                    }
                }
            }

        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null && (MinecraftInstance.classProvider.isBlockLadder(event.getBlock()) || MinecraftInstance.classProvider.isBlockVine(event.getBlock())) && StringsKt.equals((String) this.modeValue.get(), "AAC3.0.5", true)) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.isOnLadder()) {
                event.setBoundingBox((IAxisAlignedBB) null);
            }
        }

    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
