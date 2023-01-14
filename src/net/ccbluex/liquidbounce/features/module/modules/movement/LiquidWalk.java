package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "LiquidWalk",
    description = "Allows you to walk on water.",
    category = ModuleCategory.MOVEMENT,
    keyBind = 36
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u001aH\u0007J\u0012\u0010\u001b\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/LiquidWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacFlyValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "nextTick", "", "noJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class LiquidWalk extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Vanilla", "NCP", "AAC", "AAC3.3.11", "AACFly", "Spartan", "Dolphin"}, "NCP");
    private final BoolValue noJumpValue = new BoolValue("NoJump", false);
    private final FloatValue aacFlyValue = new FloatValue("AACFlyMotion", 0.5F, 0.1F, 1.0F);
    private boolean nextTick;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null && !thePlayer.isSneaking()) {
            String s = (String) this.modeValue.get();
            boolean block = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case -2011701869:
                    if (s.equals("spartan") && thePlayer.isInWater()) {
                        if (thePlayer.isCollidedHorizontally()) {
                            thePlayer.setMotionY(thePlayer.getMotionY() + 0.15D);
                            return;
                        }

                        IBlock block2 = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + (double) 1, thePlayer.getPosZ()));
                        IBlock blockUp = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 1.1D, thePlayer.getPosZ()));

                        if (MinecraftInstance.classProvider.isBlockLiquid(blockUp)) {
                            thePlayer.setMotionY(0.1D);
                        } else if (MinecraftInstance.classProvider.isBlockLiquid(block2)) {
                            thePlayer.setMotionY(0.0D);
                        }

                        thePlayer.setOnGround(true);
                        thePlayer.setMotionX(thePlayer.getMotionX() * 1.085D);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.085D);
                    }

                    return;

                case 96323:
                    if (s.equals("aac")) {
                        WBlockPos block1 = thePlayer.getPosition().down();

                        if (!thePlayer.getOnGround() && Intrinsics.areEqual(BlockUtils.getBlock(block1), MinecraftInstance.classProvider.getBlockEnum(BlockType.WATER)) || thePlayer.isInWater()) {
                            if (!thePlayer.getSprinting()) {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.99999D);
                                thePlayer.setMotionY(thePlayer.getMotionY() * 0.0D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.99999D);
                                if (thePlayer.isCollidedHorizontally()) {
                                    thePlayer.setMotionY((double) ((float) ((int) (thePlayer.getPosY() - (double) ((int) (thePlayer.getPosY() - (double) 1)))) / 8.0F));
                                }
                            } else {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.99999D);
                                thePlayer.setMotionY(thePlayer.getMotionY() * 0.0D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.99999D);
                                if (thePlayer.isCollidedHorizontally()) {
                                    thePlayer.setMotionY((double) ((float) ((int) (thePlayer.getPosY() - (double) ((int) (thePlayer.getPosY() - (double) 1)))) / 8.0F));
                                }
                            }

                            if (thePlayer.getFallDistance() >= (float) 4) {
                                thePlayer.setMotionY(-0.004D);
                            } else if (thePlayer.isInWater()) {
                                thePlayer.setMotionY(0.09D);
                            }
                        }

                        if (thePlayer.getHurtTime() != 0) {
                            thePlayer.setOnGround(false);
                            return;
                        }
                    }

                    return;

                case 108891:
                    if (!s.equals("ncp")) {
                        return;
                    }
                    break;

                case 233102203:
                    if (!s.equals("vanilla")) {
                        return;
                    }
                    break;

                case 1492139161:
                    if (s.equals("aac3.3.11") && thePlayer.isInWater()) {
                        thePlayer.setMotionX(thePlayer.getMotionX() * 1.17D);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.17D);
                        if (thePlayer.isCollidedHorizontally()) {
                            thePlayer.setMotionY(0.24D);
                        } else {
                            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                            if (iworldclient == null) {
                                Intrinsics.throwNpe();
                            }

                            if (Intrinsics.areEqual(iworldclient.getBlockState(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 1.0D, thePlayer.getPosZ())).getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR)) ^ true) {
                                thePlayer.setMotionY(thePlayer.getMotionY() + 0.04D);
                                return;
                            }
                        }

                        return;
                    }

                    return;

                case 1837070814:
                    if (s.equals("dolphin") && thePlayer.isInWater()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.03999999910593033D);
                    }

                    return;

                default:
                    return;
                }

                if (BlockUtils.collideBlock(thePlayer.getEntityBoundingBox(), (Function1) (new Function1(1, MinecraftInstance.classProvider) {
                    public final boolean invoke(@Nullable Object p1) {
                        return ((IClassProvider) this.receiver).isBlockLiquid(p1);
                    }

                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(IClassProvider.class);
                    }

                    public final String getName() {
                        return "isBlockLiquid";
                    }

                    public final String getSignature() {
                        return "isBlockLiquid(Ljava/lang/Object;)Z";
                    }
                })) && thePlayer.isInsideOfMaterial(MinecraftInstance.classProvider.getMaterialEnum(MaterialType.AIR)) && !thePlayer.isSneaking()) {
                    thePlayer.setMotionY(0.08D);
                }

            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String s = (String) this.modeValue.get();
        String s1 = "aacfly";
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s2 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
            String s3 = s2;

            if (Intrinsics.areEqual(s1, s3)) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.isInWater()) {
                    event.setY((double) ((Number) this.aacFlyValue.get()).floatValue());
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionY((double) ((Number) this.aacFlyValue.get()).floatValue());
                }
            }

        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null) {
            if (MinecraftInstance.classProvider.isBlockLiquid(event.getBlock())) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (!BlockUtils.collideBlock(ientityplayersp.getEntityBoundingBox(), (Function1) (new Function1(1, MinecraftInstance.classProvider) {
                    public final boolean invoke(@Nullable Object p1) {
                        return ((IClassProvider) this.receiver).isBlockLiquid(p1);
                    }

                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(IClassProvider.class);
                    }

                    public final String getName() {
                        return "isBlockLiquid";
                    }

                    public final String getSignature() {
                        return "isBlockLiquid(Ljava/lang/Object;)Z";
                    }
                }))) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!ientityplayersp.isSneaking()) {
                        String s = (String) this.modeValue.get();
                        boolean flag = false;

                        if (s == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        String s1 = s.toLowerCase();

                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                        s = s1;
                        switch (s.hashCode()) {
                        case 108891:
                            if (!s.equals("ncp")) {
                                return;
                            }
                            break;

                        case 233102203:
                            if (!s.equals("vanilla")) {
                                return;
                            }
                            break;

                        default:
                            return;
                        }

                        event.setBoundingBox(MinecraftInstance.classProvider.createAxisAlignedBB((double) event.getX(), (double) event.getY(), (double) event.getZ(), (double) event.getX() + (double) 1, (double) event.getY() + (double) 1, (double) event.getZ() + (double) 1));
                    }
                }
            }

        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null && StringsKt.equals((String) this.modeValue.get(), "NCP", true)) {
            if (MinecraftInstance.classProvider.isCPacketPlayer(event.getPacket())) {
                ICPacketPlayer packetPlayer = event.getPacket().asCPacketPlayer();

                if (BlockUtils.collideBlock(MinecraftInstance.classProvider.createAxisAlignedBB(thePlayer.getEntityBoundingBox().getMaxX(), thePlayer.getEntityBoundingBox().getMaxY(), thePlayer.getEntityBoundingBox().getMaxZ(), thePlayer.getEntityBoundingBox().getMinX(), thePlayer.getEntityBoundingBox().getMinY() - 0.01D, thePlayer.getEntityBoundingBox().getMinZ()), (Function1) (new Function1(1, MinecraftInstance.classProvider) {
                    public final boolean invoke(@Nullable Object p1) {
                        return ((IClassProvider) this.receiver).isBlockLiquid(p1);
                    }

                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(IClassProvider.class);
                    }

                    public final String getName() {
                        return "isBlockLiquid";
                    }

                    public final String getSignature() {
                        return "isBlockLiquid(Ljava/lang/Object;)Z";
                    }
                }))) {
                    this.nextTick = !this.nextTick;
                    if (this.nextTick) {
                        packetPlayer.setY(packetPlayer.getY() - 0.001D);
                    }
                }
            }

        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            IBlock block = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 0.01D, thePlayer.getPosZ()));

            if (((Boolean) this.noJumpValue.get()).booleanValue() && MinecraftInstance.classProvider.isBlockLiquid(block)) {
                event.cancelEvent();
            }

        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
