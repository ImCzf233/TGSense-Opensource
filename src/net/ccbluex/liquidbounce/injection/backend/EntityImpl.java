package net.ccbluex.liquidbounce.injection.backend;

import java.util.UUID;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityTNTPrimed;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000¤\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\n\u0010\u0090\u0001\u001a\u00030\u0091\u0001H\u0016J\n\u0010\u0092\u0001\u001a\u00030\u0093\u0001H\u0016J\n\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0016J\t\u0010\u0096\u0001\u001a\u00020\u0007H\u0016J\t\u0010\u0097\u0001\u001a\u00020\u0007H\u0016J\u0014\u0010\u0098\u0001\u001a\u00030\u0099\u00012\b\u0010\u009a\u0001\u001a\u00030\u009b\u0001H\u0016J\u0016\u0010\u009c\u0001\u001a\u00020\u00072\n\u0010\u009d\u0001\u001a\u0005\u0018\u00010\u009e\u0001H\u0096\u0002J$\u0010\u009f\u0001\u001a\u00020B2\u0007\u0010 \u0001\u001a\u00020B2\u0007\u0010¡\u0001\u001a\u00020B2\u0007\u0010¢\u0001\u001a\u00020BH\u0016J\u0012\u0010£\u0001\u001a\u00020B2\u0007\u0010¤\u0001\u001a\u00020iH\u0016J\u0012\u0010¥\u0001\u001a\u00020B2\u0007\u0010¦\u0001\u001a\u00020\u0003H\u0016J\u0012\u0010§\u0001\u001a\u00020\u000b2\u0007\u0010¦\u0001\u001a\u00020\u0003H\u0016J\u0015\u0010¨\u0001\u001a\u00020\u000b2\n\u0010¦\u0001\u001a\u0005\u0018\u00010\u0091\u0001H\u0016J\u0012\u0010©\u0001\u001a\u00020J2\u0007\u0010ª\u0001\u001a\u00020\u000bH\u0016J\u0012\u0010«\u0001\u001a\u00020J2\u0007\u0010ª\u0001\u001a\u00020\u000bH\u0016J\u0013\u0010¬\u0001\u001a\u00020\u00072\b\u0010\u00ad\u0001\u001a\u00030®\u0001H\u0016J%\u0010¯\u0001\u001a\u00030\u0099\u00012\u0007\u0010 \u0001\u001a\u00020B2\u0007\u0010¡\u0001\u001a\u00020B2\u0007\u0010¢\u0001\u001a\u00020BH\u0016J\u001e\u0010°\u0001\u001a\u0005\u0018\u00010±\u00012\u0007\u0010²\u0001\u001a\u00020B2\u0007\u0010ª\u0001\u001a\u00020\u000bH\u0016J%\u0010³\u0001\u001a\u00030\u0099\u00012\u0007\u0010 \u0001\u001a\u00020B2\u0007\u0010¡\u0001\u001a\u00020B2\u0007\u0010¢\u0001\u001a\u00020BH\u0016J5\u0010´\u0001\u001a\u00030\u0099\u00012\u0007\u0010µ\u0001\u001a\u00020B2\u0007\u0010¶\u0001\u001a\u00020B2\u0007\u0010·\u0001\u001a\u00020B2\u0006\u0010z\u001a\u00020\u000b2\u0006\u0010w\u001a\u00020\u000bH\u0016J\"\u0010¸\u0001\u001a\u00030\u0099\u00012\u0006\u0010a\u001a\u00020B2\u0006\u0010c\u001a\u00020B2\u0006\u0010f\u001a\u00020BH\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R$\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\r\"\u0004\b\u0019\u0010\u0016R\u0014\u0010\u001a\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\tR$\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u001c8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\'\u0010\rR$\u0010(\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b)\u0010\r\"\u0004\b*\u0010\u0016R\u0014\u0010+\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\rR\u0014\u0010-\u001a\u00020.8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u00101\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u0010%R\u0014\u00103\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\tR$\u00105\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b5\u0010\t\"\u0004\b6\u00107R\u0014\u00108\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u0010\tR\u0014\u00109\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010\tR$\u0010:\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b:\u0010\t\"\u0004\b;\u00107R\u0014\u0010<\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010\tR\u0014\u0010=\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010\tR$\u0010>\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b>\u0010\t\"\u0004\b?\u00107R\u0014\u0010@\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010\tR\u0014\u0010A\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0014\u0010E\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010DR\u0014\u0010G\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010DR\u0016\u0010I\u001a\u0004\u0018\u00010J8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR$\u0010M\u001a\u00020B2\u0006\u0010\u0012\u001a\u00020B8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bN\u0010D\"\u0004\bO\u0010PR$\u0010Q\u001a\u00020B2\u0006\u0010\u0012\u001a\u00020B8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bR\u0010D\"\u0004\bS\u0010PR$\u0010T\u001a\u00020B2\u0006\u0010\u0012\u001a\u00020B8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bU\u0010D\"\u0004\bV\u0010PR\u0016\u0010W\u001a\u0004\u0018\u00010X8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bY\u0010ZR$\u0010[\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\\\u0010\t\"\u0004\b]\u00107R$\u0010^\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b_\u0010\t\"\u0004\b`\u00107R\u0014\u0010a\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bb\u0010DR$\u0010c\u001a\u00020B2\u0006\u0010\u0012\u001a\u00020B8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bd\u0010D\"\u0004\be\u0010PR\u0014\u0010f\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bg\u0010DR\u0014\u0010h\u001a\u00020i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bj\u0010kR\u0014\u0010l\u001a\u00020J8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bm\u0010LR\u0014\u0010n\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bo\u0010DR\u0014\u0010p\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bq\u0010DR\u0014\u0010r\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bs\u0010DR\u0016\u0010t\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bu\u0010vR$\u0010w\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bx\u0010\r\"\u0004\by\u0010\u0016R$\u0010z\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b{\u0010\r\"\u0004\b|\u0010\u0016R\u0014\u0010}\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b~\u0010\tR&\u0010\u007f\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e¢\u0006\u000e\u001a\u0005\b\u0080\u0001\u0010\t\"\u0005\b\u0081\u0001\u00107R\'\u0010\u0082\u0001\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e¢\u0006\u000e\u001a\u0005\b\u0083\u0001\u0010\r\"\u0005\b\u0084\u0001\u0010\u0016R\u0016\u0010\u0085\u0001\u001a\u00020#8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0086\u0001\u0010%R\u0018\u0010\u0087\u0001\u001a\u00030\u0088\u00018VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0089\u0001\u0010\u008a\u0001R\u0016\u0010\u008b\u0001\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u008c\u0001\u0010\rR\u0016\u0010\u0004\u001a\u00028\u0000¢\u0006\r\n\u0003\u0010\u008f\u0001\u001a\u0006\b\u008d\u0001\u0010\u008e\u0001¨\u0006¹\u0001"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/EntityImpl;", "T", "Lnet/minecraft/entity/Entity;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "wrapped", "(Lnet/minecraft/entity/Entity;)V", "burning", "", "getBurning", "()Z", "collisionBorderSize", "", "getCollisionBorderSize", "()F", "displayName", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "getDisplayName", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "value", "distanceWalkedModified", "getDistanceWalkedModified", "setDistanceWalkedModified", "(F)V", "distanceWalkedOnStepModified", "getDistanceWalkedOnStepModified", "setDistanceWalkedOnStepModified", "entityAlive", "getEntityAlive", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "entityBoundingBox", "getEntityBoundingBox", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "setEntityBoundingBox", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;)V", "entityId", "", "getEntityId", "()I", "eyeHeight", "getEyeHeight", "fallDistance", "getFallDistance", "setFallDistance", "height", "getHeight", "horizontalFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getHorizontalFacing", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "hurtResistantTime", "getHurtResistantTime", "invisible", "getInvisible", "isAirBorne", "setAirBorne", "(Z)V", "isCollidedHorizontally", "isCollidedVertically", "isDead", "setDead", "isInLava", "isInWater", "isInWeb", "setInWeb", "isRiding", "lastTickPosX", "", "getLastTickPosX", "()D", "lastTickPosY", "getLastTickPosY", "lastTickPosZ", "getLastTickPosZ", "lookVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "getLookVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "motionX", "getMotionX", "setMotionX", "(D)V", "motionY", "getMotionY", "setMotionY", "motionZ", "getMotionZ", "setMotionZ", "name", "", "getName", "()Ljava/lang/String;", "noClip", "getNoClip", "setNoClip", "onGround", "getOnGround", "setOnGround", "posX", "getPosX", "posY", "getPosY", "setPosY", "posZ", "getPosZ", "position", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getPosition", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "positionVector", "getPositionVector", "prevPosX", "getPrevPosX", "prevPosY", "getPrevPosY", "prevPosZ", "getPrevPosZ", "ridingEntity", "getRidingEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "rotationPitch", "getRotationPitch", "setRotationPitch", "rotationYaw", "getRotationYaw", "setRotationYaw", "sneaking", "getSneaking", "sprinting", "getSprinting", "setSprinting", "stepHeight", "getStepHeight", "setStepHeight", "ticksExisted", "getTicksExisted", "uniqueID", "Ljava/util/UUID;", "getUniqueID", "()Ljava/util/UUID;", "width", "getWidth", "getWrapped", "()Lnet/minecraft/entity/Entity;", "Lnet/minecraft/entity/Entity;", "asEntityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "asEntityPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "asEntityTNTPrimed", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityTNTPrimed;", "canBeCollidedWith", "canRiderInteract", "copyLocationAndAnglesFrom", "", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "equals", "other", "", "getDistance", "x", "y", "z", "getDistanceSq", "blockPos", "getDistanceSqToEntity", "it", "getDistanceToEntity", "getDistanceToEntity2", "getLook", "partialTicks", "getPositionEyes", "isInsideOfMaterial", "material", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "moveEntity", "rayTrace", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "range", "setPosition", "setPositionAndRotation", "oldX", "oldY", "oldZ", "setPositionAndUpdate", "LiquidBounce"}
)
public class EntityImpl implements IEntity {

    @NotNull
    private final Entity wrapped;

    public float getDistanceWalkedOnStepModified() {
        return this.wrapped.distanceWalkedOnStepModified;
    }

    public void setDistanceWalkedOnStepModified(float value) {
        this.wrapped.distanceWalkedOnStepModified = value;
    }

    public float getDistanceWalkedModified() {
        return this.wrapped.distanceWalkedModified;
    }

    public void setDistanceWalkedModified(float value) {
        this.wrapped.distanceWalkedModified = value;
    }

    public boolean getSneaking() {
        return this.wrapped.isSneaking();
    }

    public float getStepHeight() {
        return this.wrapped.stepHeight;
    }

    public void setStepHeight(float value) {
        this.wrapped.stepHeight = value;
    }

    @NotNull
    public IEnumFacing getHorizontalFacing() {
        EnumFacing enumfacing = this.wrapped.getHorizontalFacing();

        Intrinsics.checkExpressionValueIsNotNull(enumfacing, "wrapped.horizontalFacing");
        EnumFacing $this$wrap$iv = enumfacing;
        boolean $i$f$wrap = false;

        return (IEnumFacing) (new EnumFacingImpl($this$wrap$iv));
    }

    @Nullable
    public WVec3 getLookVec() {
        Vec3d vec3d = this.wrapped.getLookVec();

        Intrinsics.checkExpressionValueIsNotNull(vec3d, "wrapped.lookVec");
        Vec3d $this$wrap$iv = vec3d;
        boolean $i$f$wrap = false;

        return new WVec3($this$wrap$iv.x, $this$wrap$iv.y, $this$wrap$iv.z);
    }

    public boolean isDead() {
        return this.wrapped.isDead;
    }

    public void setDead(boolean value) {
        this.wrapped.isDead = value;
    }

    public boolean isCollidedVertically() {
        return this.wrapped.collidedVertically;
    }

    public boolean isCollidedHorizontally() {
        return this.wrapped.collidedHorizontally;
    }

    public boolean isAirBorne() {
        return this.wrapped.isAirBorne;
    }

    public void setAirBorne(boolean value) {
        this.wrapped.isAirBorne = value;
    }

    public int getHurtResistantTime() {
        return this.wrapped.hurtResistantTime;
    }

    public boolean getNoClip() {
        return this.wrapped.noClip;
    }

    public void setNoClip(boolean value) {
        this.wrapped.noClip = value;
    }

    public boolean getSprinting() {
        return this.wrapped.isSprinting();
    }

    public void setSprinting(boolean value) {
        this.wrapped.setSprinting(value);
    }

    @NotNull
    public WVec3 getPositionVector() {
        Vec3d vec3d = this.wrapped.getPositionVector();

        Intrinsics.checkExpressionValueIsNotNull(vec3d, "wrapped.positionVector");
        Vec3d $this$wrap$iv = vec3d;
        boolean $i$f$wrap = false;

        return new WVec3($this$wrap$iv.x, $this$wrap$iv.y, $this$wrap$iv.z);
    }

    public boolean isRiding() {
        return this.wrapped.isRiding();
    }

    @NotNull
    public WBlockPos getPosition() {
        BlockPos blockpos = this.wrapped.getPosition();

        Intrinsics.checkExpressionValueIsNotNull(blockpos, "wrapped.position");
        BlockPos $this$wrap$iv = blockpos;
        boolean $i$f$wrap = false;

        return new WBlockPos($this$wrap$iv.getX(), $this$wrap$iv.getY(), $this$wrap$iv.getZ());
    }

    public boolean getBurning() {
        return this.wrapped.isBurning();
    }

    public float getFallDistance() {
        return this.wrapped.fallDistance;
    }

    public void setFallDistance(float value) {
        this.wrapped.fallDistance = value;
    }

    public boolean isInWater() {
        return this.wrapped.isInWater();
    }

    public boolean isInWeb() {
        return this.wrapped.isInWeb;
    }

    public void setInWeb(boolean value) {
        this.wrapped.isInWeb = value;
    }

    public boolean isInLava() {
        return this.wrapped.isInLava();
    }

    public float getWidth() {
        return this.wrapped.width;
    }

    public float getHeight() {
        return this.wrapped.height;
    }

    public boolean getOnGround() {
        return this.wrapped.onGround;
    }

    public void setOnGround(boolean value) {
        this.wrapped.onGround = value;
    }

    @Nullable
    public IEntity getRidingEntity() {
        Entity entity = this.wrapped.ridingEntity;
        IEntity ientity;

        if (this.wrapped.ridingEntity != null) {
            Entity $this$wrap$iv = entity;
            boolean $i$f$wrap = false;

            ientity = (IEntity) (new EntityImpl($this$wrap$iv));
        } else {
            ientity = null;
        }

        return ientity;
    }

    public float getCollisionBorderSize() {
        return this.wrapped.getCollisionBorderSize();
    }

    public double getMotionX() {
        return this.wrapped.motionX;
    }

    public void setMotionX(double value) {
        this.wrapped.motionX = value;
    }

    public double getMotionY() {
        return this.wrapped.motionY;
    }

    public void setMotionY(double value) {
        this.wrapped.motionY = value;
    }

    public double getMotionZ() {
        return this.wrapped.motionZ;
    }

    public void setMotionZ(double value) {
        this.wrapped.motionZ = value;
    }

    public float getEyeHeight() {
        return this.wrapped.getEyeHeight();
    }

    @NotNull
    public IAxisAlignedBB getEntityBoundingBox() {
        AxisAlignedBB axisalignedbb = this.wrapped.getEntityBoundingBox();

        Intrinsics.checkExpressionValueIsNotNull(axisalignedbb, "wrapped.entityBoundingBox");
        AxisAlignedBB $this$wrap$iv = axisalignedbb;
        boolean $i$f$wrap = false;

        return (IAxisAlignedBB) (new AxisAlignedBBImpl($this$wrap$iv));
    }

    public void setEntityBoundingBox(@NotNull IAxisAlignedBB value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Entity entity = this.wrapped;
        boolean $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb = ((AxisAlignedBBImpl) value).getWrapped();

        entity.setEntityBoundingBox(axisalignedbb);
    }

    public double getPosX() {
        return this.wrapped.posX;
    }

    public double getPosY() {
        return this.wrapped.posY;
    }

    public void setPosY(double value) {
        this.wrapped.posY = value;
    }

    public double getPosZ() {
        return this.wrapped.posZ;
    }

    public double getLastTickPosX() {
        return this.wrapped.lastTickPosX;
    }

    public double getLastTickPosY() {
        return this.wrapped.lastTickPosY;
    }

    public double getLastTickPosZ() {
        return this.wrapped.lastTickPosZ;
    }

    public double getPrevPosX() {
        return this.wrapped.prevPosX;
    }

    public double getPrevPosY() {
        return this.wrapped.prevPosY;
    }

    public double getPrevPosZ() {
        return this.wrapped.prevPosZ;
    }

    public float getRotationYaw() {
        return this.wrapped.rotationYaw;
    }

    public void setRotationYaw(float value) {
        this.wrapped.rotationYaw = value;
    }

    public float getRotationPitch() {
        return this.wrapped.prevRotationPitch;
    }

    public void setRotationPitch(float value) {
        this.wrapped.rotationPitch = value;
    }

    public int getEntityId() {
        return this.wrapped.getEntityId();
    }

    @Nullable
    public IIChatComponent getDisplayName() {
        ITextComponent itextcomponent = this.wrapped.getDisplayName();

        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "wrapped.displayName");
        ITextComponent $this$wrap$iv = itextcomponent;
        boolean $i$f$wrap = false;

        return (IIChatComponent) (new IChatComponentImpl($this$wrap$iv));
    }

    @NotNull
    public UUID getUniqueID() {
        UUID uuid = this.wrapped.getUniqueID();

        Intrinsics.checkExpressionValueIsNotNull(uuid, "wrapped.uniqueID");
        return uuid;
    }

    @Nullable
    public String getName() {
        return this.wrapped.getName();
    }

    public int getTicksExisted() {
        return this.wrapped.ticksExisted;
    }

    public boolean getEntityAlive() {
        return this.wrapped.isEntityAlive();
    }

    public boolean getInvisible() {
        return this.wrapped.isInvisible();
    }

    @NotNull
    public WVec3 getPositionEyes(float partialTicks) {
        Vec3d vec3d = this.wrapped.getPositionEyes(partialTicks);

        Intrinsics.checkExpressionValueIsNotNull(vec3d, "wrapped.getPositionEyes(partialTicks)");
        Vec3d $this$wrap$iv = vec3d;
        boolean $i$f$wrap = false;

        return new WVec3($this$wrap$iv.x, $this$wrap$iv.y, $this$wrap$iv.z);
    }

    public boolean canBeCollidedWith() {
        return this.wrapped.canBeCollidedWith();
    }

    public boolean canRiderInteract() {
        return this.wrapped.canRiderInteract();
    }

    public void moveEntity(double x, double y, double z) {
        this.wrapped.move(MoverType.PLAYER, x, y, z);
    }

    public float getDistanceToEntity(@NotNull IEntity it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        Entity entity = this.wrapped;
        boolean $i$f$unwrap = false;
        Entity entity1 = ((EntityImpl) it).getWrapped();

        return entity.getDistance(entity1);
    }

    public float getDistanceToEntity2(@Nullable IEntityLivingBase it) {
        Entity entity = this.wrapped;

        if (it == null) {
            Intrinsics.throwNpe();
        }

        Entity entity1 = entity;
        boolean $i$f$unwrap = false;

        if (it == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityLivingBaseImpl<*>");
        } else {
            EntityLivingBase entitylivingbase = (EntityLivingBase) ((EntityLivingBaseImpl) it).getWrapped();

            return entity1.getDistance((Entity) entitylivingbase);
        }
    }

    public double getDistanceSqToEntity(@NotNull IEntity it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        Entity entity = this.wrapped;
        boolean $i$f$unwrap = false;
        Entity entity1 = ((EntityImpl) it).getWrapped();

        return entity.getDistanceSq(entity1);
    }

    @NotNull
    public IEntityPlayer asEntityPlayer() {
        EntityPlayerImpl entityplayerimpl = new EntityPlayerImpl;
        Entity entity = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.player.EntityPlayer");
        } else {
            entityplayerimpl.<init>((EntityPlayer) entity);
            return (IEntityPlayer) entityplayerimpl;
        }
    }

    @NotNull
    public IEntityLivingBase asEntityLivingBase() {
        EntityLivingBaseImpl entitylivingbaseimpl = new EntityLivingBaseImpl;
        Entity entity = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
        } else {
            entitylivingbaseimpl.<init>((EntityLivingBase) entity);
            return (IEntityLivingBase) entitylivingbaseimpl;
        }
    }

    @NotNull
    public IEntityTNTPrimed asEntityTNTPrimed() {
        EntityTNTPrimedImpl entitytntprimedimpl = new EntityTNTPrimedImpl;
        Entity entity = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.item.EntityTNTPrimed");
        } else {
            entitytntprimedimpl.<init>((EntityTNTPrimed) entity);
            return (IEntityTNTPrimed) entitytntprimedimpl;
        }
    }

    public double getDistance(double x, double y, double z) {
        return this.wrapped.getDistance(x, y, z);
    }

    public void setPosition(double x, double y, double z) {
        this.wrapped.setPosition(x, y, z);
    }

    public double getDistanceSq(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Entity entity = this.wrapped;
        boolean $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        return entity.getDistanceSq(blockpos);
    }

    public void setPositionAndUpdate(double posX, double posY, double posZ) {
        this.wrapped.setPositionAndUpdate(posX, posY, posZ);
    }

    @Nullable
    public IMovingObjectPosition rayTrace(double range, float partialTicks) {
        RayTraceResult raytraceresult = this.wrapped.rayTrace(range, partialTicks);
        IMovingObjectPosition imovingobjectposition;

        if (raytraceresult != null) {
            RayTraceResult $this$wrap$iv = raytraceresult;
            boolean $i$f$wrap = false;

            imovingobjectposition = (IMovingObjectPosition) (new MovingObjectPositionImpl($this$wrap$iv));
        } else {
            imovingobjectposition = null;
        }

        return imovingobjectposition;
    }

    @NotNull
    public WVec3 getLook(float partialTicks) {
        Vec3d vec3d = this.wrapped.getLook(partialTicks);

        Intrinsics.checkExpressionValueIsNotNull(vec3d, "wrapped.getLook(partialTicks)");
        Vec3d $this$wrap$iv = vec3d;
        boolean $i$f$wrap = false;

        return new WVec3($this$wrap$iv.x, $this$wrap$iv.y, $this$wrap$iv.z);
    }

    public boolean isInsideOfMaterial(@NotNull IMaterial material) {
        Intrinsics.checkParameterIsNotNull(material, "material");
        Entity entity = this.wrapped;
        boolean $i$f$unwrap = false;
        Material material = ((MaterialImpl) material).getWrapped();

        return entity.isInsideOfMaterial(material);
    }

    public void copyLocationAndAnglesFrom(@NotNull IEntityPlayerSP player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        Entity entity = this.wrapped;
        boolean $i$f$unwrap = false;
        EntityPlayerSP entityplayersp = (EntityPlayerSP) ((EntityPlayerSPImpl) player).getWrapped();

        entity.copyLocationAndAnglesFrom((Entity) entityplayersp);
    }

    public void setPositionAndRotation(double oldX, double oldY, double oldZ, float rotationYaw, float rotationPitch) {
        this.wrapped.setPositionAndRotation(oldX, oldY, oldZ, rotationYaw, rotationPitch);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof EntityImpl && Intrinsics.areEqual(((EntityImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Entity getWrapped() {
        return this.wrapped;
    }

    public EntityImpl(@NotNull Entity wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
