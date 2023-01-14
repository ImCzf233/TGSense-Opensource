package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "BufferSpeed",
    description = "Allows you to walk faster on slabs and stairs.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0011\u0010&\u001a\u00020\'2\u0006\u0010&\u001a\u00020(H\u0082\bJ\b\u0010)\u001a\u00020\'H\u0016J\b\u0010*\u001a\u00020\'H\u0016J\u0010\u0010+\u001a\u00020\'2\u0006\u0010,\u001a\u00020-H\u0007J\u0012\u0010.\u001a\u00020\'2\b\u0010,\u001a\u0004\u0018\u00010/H\u0007J\b\u00100\u001a\u00020\'H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/BufferSpeed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "airStrafeValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "bufferValue", "down", "", "fastHop", "forceDown", "hadFastHop", "headBlockBoostValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "headBlockValue", "iceBoostValue", "iceValue", "isNearBlock", "()Z", "legitHop", "maxSpeedValue", "noHurtValue", "slabsBoostValue", "slabsModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "slabsValue", "slimeValue", "snowBoostValue", "snowPortValue", "snowValue", "speed", "", "speedLimitValue", "stairsBoostValue", "stairsModeValue", "stairsValue", "wallBoostValue", "wallModeValue", "wallValue", "boost", "", "", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "reset", "LiquidBounce"}
)
public final class BufferSpeed extends Module {

    private final BoolValue speedLimitValue = new BoolValue("SpeedLimit", true);
    private final FloatValue maxSpeedValue = new FloatValue("MaxSpeed", 2.0F, 1.0F, 5.0F);
    private final BoolValue bufferValue = new BoolValue("Buffer", true);
    private final BoolValue stairsValue = new BoolValue("Stairs", true);
    private final FloatValue stairsBoostValue = new FloatValue("StairsBoost", 1.87F, 1.0F, 2.0F);
    private final ListValue stairsModeValue = new ListValue("StairsMode", new String[] { "Old", "New"}, "New");
    private final BoolValue slabsValue = new BoolValue("Slabs", true);
    private final FloatValue slabsBoostValue = new FloatValue("SlabsBoost", 1.87F, 1.0F, 2.0F);
    private final ListValue slabsModeValue = new ListValue("SlabsMode", new String[] { "Old", "New"}, "New");
    private final BoolValue iceValue = new BoolValue("Ice", false);
    private final FloatValue iceBoostValue = new FloatValue("IceBoost", 1.342F, 1.0F, 2.0F);
    private final BoolValue snowValue = new BoolValue("Snow", true);
    private final FloatValue snowBoostValue = new FloatValue("SnowBoost", 1.87F, 1.0F, 2.0F);
    private final BoolValue snowPortValue = new BoolValue("SnowPort", true);
    private final BoolValue wallValue = new BoolValue("Wall", true);
    private final FloatValue wallBoostValue = new FloatValue("WallBoost", 1.87F, 1.0F, 2.0F);
    private final ListValue wallModeValue = new ListValue("WallMode", new String[] { "Old", "New"}, "New");
    private final BoolValue headBlockValue = new BoolValue("HeadBlock", true);
    private final FloatValue headBlockBoostValue = new FloatValue("HeadBlockBoost", 1.87F, 1.0F, 2.0F);
    private final BoolValue slimeValue = new BoolValue("Slime", true);
    private final BoolValue airStrafeValue = new BoolValue("AirStrafe", false);
    private final BoolValue noHurtValue = new BoolValue("NoHurt", true);
    private double speed;
    private boolean down;
    private boolean forceDown;
    private boolean fastHop;
    private boolean hadFastHop;
    private boolean legitHop;

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);

            if (module == null) {
                Intrinsics.throwNpe();
            }

            if (!module.getState() && (!((Boolean) this.noHurtValue.get()).booleanValue() || thePlayer.getHurtTime() <= 0)) {
                WBlockPos blockPos = new WBlockPos(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ());

                if (this.forceDown || this.down && thePlayer.getMotionY() == 0.0D) {
                    thePlayer.setMotionY(-1.0D);
                    this.down = false;
                    this.forceDown = false;
                }

                if (this.fastHop) {
                    thePlayer.setSpeedInAir(0.0211F);
                    this.hadFastHop = true;
                } else if (this.hadFastHop) {
                    thePlayer.setSpeedInAir(0.02F);
                    this.hadFastHop = false;
                }

                if (MovementUtils.isMoving() && !thePlayer.isSneaking() && !thePlayer.isInWater() && !MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                    if (thePlayer.getOnGround()) {
                        this.fastHop = false;
                        if (((Boolean) this.slimeValue.get()).booleanValue() && (MinecraftInstance.classProvider.isBlockSlime(BlockUtils.getBlock(blockPos.down())) || MinecraftInstance.classProvider.isBlockSlime(BlockUtils.getBlock(blockPos)))) {
                            thePlayer.jump();
                            thePlayer.setMotionX(thePlayer.getMotionY() * 1.132D);
                            thePlayer.setMotionY(0.08D);
                            thePlayer.setMotionZ(thePlayer.getMotionY() * 1.132D);
                            this.down = true;
                            return;
                        }

                        String currentSpeed;
                        boolean this_$iv;
                        float boost$iv;
                        boolean $i$f$boost;
                        IEntityPlayerSP thePlayer$iv;
                        String s;

                        if (((Boolean) this.slabsValue.get()).booleanValue() && MinecraftInstance.classProvider.isBlockSlab(BlockUtils.getBlock(blockPos))) {
                            currentSpeed = (String) this.slabsModeValue.get();
                            this_$iv = false;
                            if (currentSpeed == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s = currentSpeed.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                            currentSpeed = s;
                            switch (currentSpeed.hashCode()) {
                            case 108960:
                                if (currentSpeed.equals("new")) {
                                    this.fastHop = true;
                                    if (this.legitHop) {
                                        thePlayer.jump();
                                        thePlayer.setOnGround(false);
                                        this.legitHop = false;
                                        return;
                                    }

                                    thePlayer.setOnGround(false);
                                    MovementUtils.strafe(0.375F);
                                    thePlayer.jump();
                                    thePlayer.setMotionY(0.41D);
                                    return;
                                }
                                break;

                            case 110119:
                                if (currentSpeed.equals("old")) {
                                    boost$iv = ((Number) this.slabsBoostValue.get()).floatValue();
                                    $i$f$boost = false;
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    thePlayer$iv = ientityplayersp;
                                    thePlayer$iv.setMotionX(thePlayer$iv.getMotionX() * (double) boost$iv);
                                    thePlayer$iv.setMotionZ(thePlayer$iv.getMotionX() * (double) boost$iv);
                                    access$setSpeed$p(this, (double) MovementUtils.INSTANCE.getSpeed());
                                    if (((Boolean) access$getSpeedLimitValue$p(this).get()).booleanValue() && access$getSpeed$p(this) > ((Number) access$getMaxSpeedValue$p(this).get()).doubleValue()) {
                                        access$setSpeed$p(this, (double) ((Number) access$getMaxSpeedValue$p(this).get()).floatValue());
                                    }

                                    return;
                                }
                            }
                        }

                        if (((Boolean) this.stairsValue.get()).booleanValue() && (MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(blockPos.down())) || MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(blockPos)))) {
                            currentSpeed = (String) this.stairsModeValue.get();
                            this_$iv = false;
                            if (currentSpeed == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s = currentSpeed.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                            currentSpeed = s;
                            switch (currentSpeed.hashCode()) {
                            case 108960:
                                if (currentSpeed.equals("new")) {
                                    this.fastHop = true;
                                    if (this.legitHop) {
                                        thePlayer.jump();
                                        thePlayer.setOnGround(false);
                                        this.legitHop = false;
                                        return;
                                    }

                                    thePlayer.setOnGround(false);
                                    MovementUtils.strafe(0.375F);
                                    thePlayer.jump();
                                    thePlayer.setMotionY(0.41D);
                                    return;
                                }
                                break;

                            case 110119:
                                if (currentSpeed.equals("old")) {
                                    boost$iv = ((Number) this.stairsBoostValue.get()).floatValue();
                                    $i$f$boost = false;
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    thePlayer$iv = ientityplayersp;
                                    thePlayer$iv.setMotionX(thePlayer$iv.getMotionX() * (double) boost$iv);
                                    thePlayer$iv.setMotionZ(thePlayer$iv.getMotionX() * (double) boost$iv);
                                    access$setSpeed$p(this, (double) MovementUtils.INSTANCE.getSpeed());
                                    if (((Boolean) access$getSpeedLimitValue$p(this).get()).booleanValue() && access$getSpeed$p(this) > ((Number) access$getMaxSpeedValue$p(this).get()).doubleValue()) {
                                        access$setSpeed$p(this, (double) ((Number) access$getMaxSpeedValue$p(this).get()).floatValue());
                                    }

                                    return;
                                }
                            }
                        }

                        this.legitHop = true;
                        float this_$iv1;
                        boolean boost$iv1;
                        IEntityPlayerSP $i$f$boost1;

                        if (((Boolean) this.headBlockValue.get()).booleanValue() && Intrinsics.areEqual(BlockUtils.getBlock(blockPos.up(2)), MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR))) {
                            this_$iv1 = ((Number) this.headBlockBoostValue.get()).floatValue();
                            boost$iv1 = false;
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            $i$f$boost1 = ientityplayersp;
                            $i$f$boost1.setMotionX($i$f$boost1.getMotionX() * (double) this_$iv1);
                            $i$f$boost1.setMotionZ($i$f$boost1.getMotionX() * (double) this_$iv1);
                            access$setSpeed$p(this, (double) MovementUtils.INSTANCE.getSpeed());
                            if (((Boolean) access$getSpeedLimitValue$p(this).get()).booleanValue() && access$getSpeed$p(this) > ((Number) access$getMaxSpeedValue$p(this).get()).doubleValue()) {
                                access$setSpeed$p(this, (double) ((Number) access$getMaxSpeedValue$p(this).get()).floatValue());
                            }

                            return;
                        }

                        if (((Boolean) this.iceValue.get()).booleanValue() && (Intrinsics.areEqual(BlockUtils.getBlock(blockPos.down()), MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE)) || Intrinsics.areEqual(BlockUtils.getBlock(blockPos.down()), MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED)))) {
                            this_$iv1 = ((Number) this.iceBoostValue.get()).floatValue();
                            boost$iv1 = false;
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            $i$f$boost1 = ientityplayersp;
                            $i$f$boost1.setMotionX($i$f$boost1.getMotionX() * (double) this_$iv1);
                            $i$f$boost1.setMotionZ($i$f$boost1.getMotionX() * (double) this_$iv1);
                            access$setSpeed$p(this, (double) MovementUtils.INSTANCE.getSpeed());
                            if (((Boolean) access$getSpeedLimitValue$p(this).get()).booleanValue() && access$getSpeed$p(this) > ((Number) access$getMaxSpeedValue$p(this).get()).doubleValue()) {
                                access$setSpeed$p(this, (double) ((Number) access$getMaxSpeedValue$p(this).get()).floatValue());
                            }

                            return;
                        }

                        if (((Boolean) this.snowValue.get()).booleanValue() && Intrinsics.areEqual(BlockUtils.getBlock(blockPos), MinecraftInstance.classProvider.getBlockEnum(BlockType.SNOW_LAYER)) && (((Boolean) this.snowPortValue.get()).booleanValue() || thePlayer.getPosY() - (double) ((int) thePlayer.getPosY()) >= 0.125D)) {
                            if (thePlayer.getPosY() - (double) ((int) thePlayer.getPosY()) >= 0.125D) {
                                this_$iv1 = ((Number) this.snowBoostValue.get()).floatValue();
                                boost$iv1 = false;
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                $i$f$boost1 = ientityplayersp;
                                $i$f$boost1.setMotionX($i$f$boost1.getMotionX() * (double) this_$iv1);
                                $i$f$boost1.setMotionZ($i$f$boost1.getMotionX() * (double) this_$iv1);
                                access$setSpeed$p(this, (double) MovementUtils.INSTANCE.getSpeed());
                                if (((Boolean) access$getSpeedLimitValue$p(this).get()).booleanValue() && access$getSpeed$p(this) > ((Number) access$getMaxSpeedValue$p(this).get()).doubleValue()) {
                                    access$setSpeed$p(this, (double) ((Number) access$getMaxSpeedValue$p(this).get()).floatValue());
                                }
                            } else {
                                thePlayer.jump();
                                this.forceDown = true;
                            }

                            return;
                        }

                        if (((Boolean) this.wallValue.get()).booleanValue()) {
                            currentSpeed = (String) this.wallModeValue.get();
                            this_$iv = false;
                            if (currentSpeed == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s = currentSpeed.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                            currentSpeed = s;
                            switch (currentSpeed.hashCode()) {
                            case 108960:
                                if (currentSpeed.equals("new") && this.isNearBlock() && !thePlayer.getMovementInput().getJump()) {
                                    thePlayer.jump();
                                    thePlayer.setMotionY(0.08D);
                                    thePlayer.setMotionX(thePlayer.getMotionX() * 0.99D);
                                    thePlayer.setMotionZ(thePlayer.getMotionX() * 0.99D);
                                    this.down = true;
                                    return;
                                }
                                break;

                            case 110119:
                                if (currentSpeed.equals("old") && (thePlayer.isCollidedVertically() && this.isNearBlock() || !MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 2.0D, thePlayer.getPosZ()))))) {
                                    boost$iv = ((Number) this.wallBoostValue.get()).floatValue();
                                    $i$f$boost = false;
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    thePlayer$iv = ientityplayersp;
                                    thePlayer$iv.setMotionX(thePlayer$iv.getMotionX() * (double) boost$iv);
                                    thePlayer$iv.setMotionZ(thePlayer$iv.getMotionX() * (double) boost$iv);
                                    access$setSpeed$p(this, (double) MovementUtils.INSTANCE.getSpeed());
                                    if (((Boolean) access$getSpeedLimitValue$p(this).get()).booleanValue() && access$getSpeed$p(this) > ((Number) access$getMaxSpeedValue$p(this).get()).doubleValue()) {
                                        access$setSpeed$p(this, (double) ((Number) access$getMaxSpeedValue$p(this).get()).floatValue());
                                    }

                                    return;
                                }
                            }
                        }

                        float currentSpeed1 = MovementUtils.INSTANCE.getSpeed();

                        if (this.speed < (double) currentSpeed1) {
                            this.speed = (double) currentSpeed1;
                        }

                        if (((Boolean) this.bufferValue.get()).booleanValue() && this.speed > (double) 0.2F) {
                            this.speed /= 1.0199999809265137D;
                            MovementUtils.strafe((float) this.speed);
                        }
                    } else {
                        this.speed = 0.0D;
                        if (((Boolean) this.airStrafeValue.get()).booleanValue()) {
                            MovementUtils.strafe$default(0.0F, 1, (Object) null);
                        }
                    }

                } else {
                    this.reset();
                }
            } else {
                this.reset();
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (MinecraftInstance.classProvider.isSPacketPlayerPosLook(packet)) {
            this.speed = 0.0D;
        }

    }

    public void onEnable() {
        this.reset();
    }

    public void onDisable() {
        this.reset();
    }

    private final void reset() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            this.legitHop = true;
            this.speed = 0.0D;
            if (this.hadFastHop) {
                thePlayer.setSpeedInAir(0.02F);
                this.hadFastHop = false;
            }

        }
    }

    private final void boost(float boost) {
        byte $i$f$boost = 0;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;

        thePlayer.setMotionX(thePlayer.getMotionX() * (double) boost);
        thePlayer.setMotionZ(thePlayer.getMotionX() * (double) boost);
        access$setSpeed$p(this, (double) MovementUtils.INSTANCE.getSpeed());
        if (((Boolean) access$getSpeedLimitValue$p(this).get()).booleanValue() && access$getSpeed$p(this) > ((Number) access$getMaxSpeedValue$p(this).get()).doubleValue()) {
            access$setSpeed$p(this, (double) ((Number) access$getMaxSpeedValue$p(this).get()).floatValue());
        }

    }

    private final boolean isNearBlock() {
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();
        IWorldClient theWorld = MinecraftInstance.mc.getTheWorld();
        List blocks = (List) (new ArrayList());
        WBlockPos wblockpos = new WBlockPos;

        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }

        wblockpos.<init>(thePlayer.getPosX(), thePlayer.getPosY() + (double) 1, thePlayer.getPosZ() - 0.7D);
        blocks.add(wblockpos);
        blocks.add(new WBlockPos(thePlayer.getPosX() + 0.7D, thePlayer.getPosY() + (double) 1, thePlayer.getPosZ()));
        blocks.add(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + (double) 1, thePlayer.getPosZ() + 0.7D));
        blocks.add(new WBlockPos(thePlayer.getPosX() - 0.7D, thePlayer.getPosY() + (double) 1, thePlayer.getPosZ()));
        Iterator iterator = blocks.iterator();

        IIBlockState blockState;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            WBlockPos blockPos = (WBlockPos) iterator.next();

            if (theWorld == null) {
                Intrinsics.throwNpe();
            }

            blockState = theWorld.getBlockState(blockPos);
            IAxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox((IWorld) theWorld, blockPos, blockState);

            if ((collisionBoundingBox == null || collisionBoundingBox.getMaxX() == collisionBoundingBox.getMinY() + (double) 1) && !blockState.getBlock().isTranslucent(blockState) && Intrinsics.areEqual(blockState.getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.WATER)) && !MinecraftInstance.classProvider.isBlockSlab(blockState.getBlock())) {
                break;
            }
        } while (!Intrinsics.areEqual(blockState.getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.BARRIER)));

        return true;
    }

    public static final double access$getSpeed$p(BufferSpeed $this) {
        return $this.speed;
    }

    public static final void access$setSpeed$p(BufferSpeed $this, double <set-?>) {
        $this.speed = <set-?>;
    }

    public static final BoolValue access$getSpeedLimitValue$p(BufferSpeed $this) {
        return $this.speedLimitValue;
    }

    public static final FloatValue access$getMaxSpeedValue$p(BufferSpeed $this) {
        return $this.maxSpeedValue;
    }
}
