package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import me.utils.render.GLUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorldSettings;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.render.OldHitting;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@ModuleInfo(
    name = "KillAura4",
    description = "天坑�?戮转头�?�new.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0018\n\u0002\u0010\u0007\n\u0002\b#\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u0000 \u008c\u00012\u00020\u0001:\u0002\u008c\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020&H\u0002J \u0010m\u001a\u00020k2\u0006\u0010l\u001a\u00020&2\u0006\u0010n\u001a\u00020\"2\u0006\u0010o\u001a\u00020pH\u0002J \u0010q\u001a\u00020k2\u0006\u0010l\u001a\u00020&2\u0006\u0010r\u001a\u00020G2\u0006\u0010s\u001a\u00020GH\u0002J\u0010\u0010t\u001a\u00020G2\u0006\u0010l\u001a\u00020uH\u0002J\u0010\u0010v\u001a\u00020\u00112\u0006\u0010l\u001a\u00020&H\u0002J\u0012\u0010w\u001a\u00020\u00112\b\u0010l\u001a\u0004\u0018\u00010uH\u0002J\b\u0010x\u001a\u00020kH\u0016J\b\u0010y\u001a\u00020kH\u0016J\u0010\u0010z\u001a\u00020k2\u0006\u0010{\u001a\u00020|H\u0007J\u0010\u0010}\u001a\u00020k2\u0006\u0010{\u001a\u00020~H\u0007J\u0010\u0010\u007f\u001a\u00020k2\u0006\u0010{\u001a\u00020pH\u0007J\u0012\u0010\u0080\u0001\u001a\u00020k2\u0007\u0010{\u001a\u00030\u0081\u0001H\u0007J\u0012\u0010\u0082\u0001\u001a\u00020k2\u0007\u0010{\u001a\u00030\u0083\u0001H\u0007J\t\u0010\u0084\u0001\u001a\u00020kH\u0002J\u0012\u0010\u0085\u0001\u001a\u00020k2\u0007\u0010\u0086\u0001\u001a\u00020uH\u0002J\t\u0010\u0087\u0001\u001a\u00020kH\u0002J\u0007\u0010\u0088\u0001\u001a\u00020kJ\t\u0010\u0089\u0001\u001a\u00020kH\u0002J\u0011\u0010\u008a\u0001\u001a\u00020\u00112\u0006\u0010l\u001a\u00020uH\u0002J\t\u0010\u008b\u0001\u001a\u00020kH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0015\u0010\u0017\u001a\u00020\u00118Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0013R\u0015\u0010\u0019\u001a\u00020\u00118Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0013R\u000e\u0010\u001b\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u00106\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b6\u0010\u0013R\u000e\u00107\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u00108\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0010\u0010;\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010A\u001a\u0004\u0018\u00010*X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010F\u001a\u00020G8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u000e\u0010J\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010R\u001a\b\u0012\u0004\u0012\u00020\"0)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Y\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010]\u001a\u0004\u0018\u00010&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR\u0016\u0010b\u001a\u0004\u0018\u00010,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bc\u0010dR\u001c\u0010e\u001a\u0004\u0018\u00010&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010_\"\u0004\bg\u0010aR\u000e\u0010h\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010i\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u008d\u0001"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura4;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "BlockRangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "afterAttackValue", "attackDelay", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "blockModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blockRate", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blockingStatus", "", "getBlockingStatus", "()Z", "setBlockingStatus", "(Z)V", "blockingnew", "canBlock", "getCanBlock", "cancelRun", "getCancelRun", "circleAccuracy", "circleAlpha", "circleBlue", "circleGreen", "circleRed", "circleValue", "clicks", "", "containerOpen", "cooldownValue", "currentTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "delayedBlockValue", "discoveredTargets", "", "Lnet/minecraft/entity/EntityLivingBase;", "displayText", "", "espAnimation", "", "failRateValue", "fakeSharpValue", "fakeSwingValue", "fovValue", "hitable", "hitableValue", "hurtTimeValue", "isBlockingChestAura", "isUp", "keepSprintValue", "getKeepSprintValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "lastTarget", "lightingModeValue", "lightingSoundValue", "lightingValue", "limitedMultiTargetsValue", "livingRaycastValue", "markEntity", "markTimer", "markValue", "maxCPS", "maxPredictSize", "maxRange", "", "getMaxRange", "()F", "maxTurnSpeed", "minCPS", "minPredictSize", "minTurnSpeed", "noInventoryAttackValue", "noInventoryDelayValue", "outborderValue", "predictValue", "prevTargetEntities", "priorityValue", "randomCenterValue", "rangeSprintReducementValue", "rangeValue", "raycastIgnoredValue", "raycastValue", "rotationStrafeValue", "rotations", "silentRotationValue", "swingValue", "syncEntity", "getSyncEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "setSyncEntity", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)V", "tag", "getTag", "()Ljava/lang/String;", "target", "getTarget", "setTarget", "targetModeValue", "throughWallsRangeValue", "attackEntity", "", "entity", "drawESP", "color", "e", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "esp", "partialTicks", "radius", "getRange", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "isAlive", "isEnemy", "onDisable", "onEnable", "onEntityMove", "event", "Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onRender3D", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "startBlocking", "interactEntity", "stopBlocking", "update", "updateHitable", "updateRotations", "updateTarget", "Companion", "LiquidBounce"}
)
public final class KillAura4 extends Module {

    private final ListValue markValue = new ListValue("Mark", new String[] { "Liquid", "FDP", "Block", "Jello", "Plat", "Red", "Sims", "None"}, "FDP");
    private final BoolValue fakeSharpValue = new BoolValue("FakeSharp", true);
    private final BoolValue circleValue = new BoolValue("Circle", true);
    private final IntegerValue circleRed = new IntegerValue("CircleRed", 255, 0, 255);
    private final IntegerValue circleGreen = new IntegerValue("CircleGreen", 255, 0, 255);
    private final IntegerValue circleBlue = new IntegerValue("CircleBlue", 255, 0, 255);
    private final IntegerValue circleAlpha = new IntegerValue("CircleAlpha", 255, 0, 255);
    private final IntegerValue circleAccuracy = new IntegerValue("CircleAccuracy", 15, 0, 60);
    private final IntegerValue maxCPS = (IntegerValue) (new IntegerValue("MaxCPS", 8, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) KillAura4.this.minCPS.get()).intValue();

            if (i > newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            KillAura4.this.attackDelay = TimeUtils.randomClickDelay(((Number) KillAura4.this.minCPS.get()).intValue(), ((Number) this.get()).intValue());
        }
    });
    private final IntegerValue minCPS = (IntegerValue) (new IntegerValue("MinCPS", 5, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) KillAura4.this.maxCPS.get()).intValue();

            if (i < newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            KillAura4.this.attackDelay = TimeUtils.randomClickDelay(((Number) this.get()).intValue(), ((Number) KillAura4.this.maxCPS.get()).intValue());
        }
    });
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    private final FloatValue cooldownValue = new FloatValue("Cooldown", 1.0F, 0.0F, 1.0F);
    private final FloatValue rangeValue = new FloatValue("Range", 3.7F, 1.0F, 8.0F);
    private final FloatValue BlockRangeValue = new FloatValue("BlockRange", 3.0F, 0.0F, 8.0F);
    private final FloatValue throughWallsRangeValue = new FloatValue("ThroughWallsRange", 3.0F, 0.0F, 8.0F);
    private final FloatValue rangeSprintReducementValue = new FloatValue("RangeSprintReducement", 0.0F, 0.0F, 0.4F);
    private final ListValue priorityValue = new ListValue("Priority", new String[] { "Health", "Distance", "Direction", "LivingTime", "HYT"}, "Distance");
    private final ListValue targetModeValue = new ListValue("TargetMode", new String[] { "Single", "Switch", "Multi"}, "Switch");
    private final BoolValue swingValue = new BoolValue("Swing", true);
    @NotNull
    private final BoolValue keepSprintValue = new BoolValue("KeepSprint", true);
    private final BoolValue delayedBlockValue = new BoolValue("DelayedBlock", true);
    private final BoolValue afterAttackValue = new BoolValue("AutoBlock-AfterAttack", false);
    private final ListValue blockModeValue = new ListValue("BlockMode", new String[] { "None", "C07C08", "C07", "Packet", "Fake", "Mouse", "GameSettings", "UseItem"}, "Packet");
    private final IntegerValue blockRate = new IntegerValue("BlockRate", 100, 1, 100);
    private final BoolValue raycastValue = new BoolValue("RayCast", true);
    private final BoolValue raycastIgnoredValue = new BoolValue("RayCastIgnored", false);
    private final BoolValue livingRaycastValue = new BoolValue("LivingRayCast", true);
    private final BoolValue aacValue = new BoolValue("AAC", false);
    private final FloatValue maxTurnSpeed = (FloatValue) (new FloatValue("MaxTurnSpeed", 180.0F, 0.0F, 180.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura4.this.minTurnSpeed.get()).floatValue();

            if (v > newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final FloatValue minTurnSpeed = (FloatValue) (new FloatValue("MinTurnSpeed", 180.0F, 0.0F, 180.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura4.this.maxTurnSpeed.get()).floatValue();

            if (v < newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final BoolValue predictValue = new BoolValue("Predict", true);
    private final FloatValue maxPredictSize = (FloatValue) (new FloatValue("MaxPredictSize", 1.0F, 0.1F, 5.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura4.this.minPredictSize.get()).floatValue();

            if (v > newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final FloatValue minPredictSize = (FloatValue) (new FloatValue("MinPredictSize", 1.0F, 0.1F, 5.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura4.this.maxPredictSize.get()).floatValue();

            if (v < newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final BoolValue lightingValue = new BoolValue("Lighting", false);
    private final ListValue lightingModeValue = new ListValue("Lighting-Mode", new String[] { "Dead", "Attack"}, "Dead");
    private final BoolValue lightingSoundValue = new BoolValue("Lighting-Sound", true);
    private final BoolValue randomCenterValue = new BoolValue("RandomCenter", true);
    private final ListValue rotations = new ListValue("RotationMode", new String[] { "None", "New", "Liquidbounce", "BackTrack"}, "BackTrack");
    private final BoolValue outborderValue = new BoolValue("Outborder", false);
    private final BoolValue silentRotationValue = new BoolValue("SilentRotation", true);
    private final ListValue rotationStrafeValue = new ListValue("Strafe", new String[] { "Off", "Strict", "Silent"}, "Off");
    private final FloatValue fovValue = new FloatValue("FOV", 180.0F, 0.0F, 180.0F);
    private final BoolValue hitableValue = new BoolValue("AlwaysHitable", true);
    private final FloatValue failRateValue = new FloatValue("FailRate", 0.0F, 0.0F, 100.0F);
    private final BoolValue fakeSwingValue = new BoolValue("FakeSwing", true);
    private final BoolValue noInventoryAttackValue = new BoolValue("NoInvAttack", false);
    private final IntegerValue noInventoryDelayValue = new IntegerValue("NoInvDelay", 200, 0, 500);
    private final IntegerValue limitedMultiTargetsValue = new IntegerValue("LimitedMultiTargets", 0, 0, 50);
    @Nullable
    private IEntityLivingBase target;
    private IEntityLivingBase currentTarget;
    private boolean hitable;
    private boolean blockingnew = true;
    private final List discoveredTargets;
    private final List prevTargetEntities;
    private IEntityLivingBase lastTarget;
    private final MSTimer attackTimer;
    private long attackDelay;
    private int clicks;
    private EntityLivingBase markEntity;
    private final MSTimer markTimer;
    private long containerOpen;
    private String displayText;
    private boolean blockingStatus;
    private double espAnimation;
    private boolean isUp;
    @Nullable
    private IEntityLivingBase syncEntity;
    private static int killCounts;
    public static final KillAura4.Companion Companion = new KillAura4.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final BoolValue getKeepSprintValue() {
        return this.keepSprintValue;
    }

    @Nullable
    public final IEntityLivingBase getTarget() {
        return this.target;
    }

    public final void setTarget(@Nullable IEntityLivingBase <set-?>) {
        this.target = <set-?>;
    }

    public final boolean getBlockingStatus() {
        return this.blockingStatus;
    }

    public final void setBlockingStatus(boolean <set-?>) {
        this.blockingStatus = <set-?>;
    }

    @Nullable
    public final IEntityLivingBase getSyncEntity() {
        return this.syncEntity;
    }

    public final void setSyncEntity(@Nullable IEntityLivingBase <set-?>) {
        this.syncEntity = <set-?>;
    }

    public void onEnable() {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            if (MinecraftInstance.mc.getTheWorld() != null) {
                this.updateTarget();
            }
        }
    }

    public void onDisable() {
        this.target = (IEntityLivingBase) null;
        this.currentTarget = (IEntityLivingBase) null;
        this.lastTarget = (IEntityLivingBase) null;
        this.hitable = false;
        this.prevTargetEntities.clear();
        this.attackTimer.reset();
        this.clicks = 0;
        this.stopBlocking();
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.getEventState() != EventState.POST) {
            if (StringsKt.equals((String) this.rotationStrafeValue.get(), "Off", true)) {
                this.update();
            }

        } else if (this.target != null) {
            if (this.currentTarget != null) {
                this.updateHitable();
                if (!StringsKt.equals((String) this.blockModeValue.get(), "None", true) && ((Boolean) this.delayedBlockValue.get()).booleanValue()) {
                    boolean $i$f$getCanBlock = false;
                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    boolean flag;
                    label48: {
                        if (ientityplayersp.getHeldItem() != null) {
                            IClassProvider iclassprovider = MinecraftInstance.classProvider;
                            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            IItemStack iitemstack = ientityplayersp1.getHeldItem();

                            if (iitemstack == null) {
                                Intrinsics.throwNpe();
                            }

                            if (iclassprovider.isItemSword(iitemstack.getItem())) {
                                flag = true;
                                break label48;
                            }
                        }

                        flag = false;
                    }

                    if (flag) {
                        IEntityLivingBase ientitylivingbase = this.currentTarget;

                        if (this.currentTarget == null) {
                            Intrinsics.throwNpe();
                        }

                        this.startBlocking((IEntity) ientitylivingbase);
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!StringsKt.equals((String) this.rotationStrafeValue.get(), "Off", true)) {
            this.update();
            if (this.currentTarget != null && RotationUtils.targetRotation != null) {
                String s = (String) this.rotationStrafeValue.get();
                boolean yaw = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case -902327211:
                    if (s.equals("silent")) {
                        this.update();
                        RotationUtils.targetRotation.applyStrafeToPlayer(event);
                        event.cancelEvent();
                    }
                    break;

                case -891986231:
                    if (s.equals("strict")) {
                        Rotation rotation = RotationUtils.targetRotation;

                        if (RotationUtils.targetRotation == null) {
                            return;
                        }

                        Rotation strafe = rotation;
                        float yaw1 = strafe.component1();
                        float strafe1 = event.getStrafe();
                        float forward = event.getForward();
                        float friction = event.getFriction();
                        float f = strafe1 * strafe1 + forward * forward;

                        if (f >= 1.0E-4F) {
                            boolean yawSin = false;

                            f = (float) Math.sqrt((double) f);
                            if (f < 1.0F) {
                                f = 1.0F;
                            }

                            f = friction / f;
                            strafe1 *= f;
                            forward *= f;
                            float yawCos = (float) ((double) yaw1 * 3.141592653589793D / (double) 180.0F);
                            boolean player = false;
                            float yawSin1 = (float) Math.sin((double) yawCos);
                            float player1 = (float) ((double) yaw1 * 3.141592653589793D / (double) 180.0F);
                            boolean flag = false;

                            yawCos = (float) Math.cos((double) player1);
                            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            IEntityPlayerSP player2 = ientityplayersp;

                            player2.setMotionX(player2.getMotionX() + (double) (strafe1 * yawCos - forward * yawSin1));
                            player2.setMotionZ(player2.getMotionZ() + (double) (forward * yawCos + strafe1 * yawSin1));
                        }

                        event.cancelEvent();
                    }
                }
            }

        }
    }

    public final void update() {
        boolean $i$f$getCancelRun = false;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag;
        label47: {
            if (!ientityplayersp.isSpectator()) {
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (access$isAlive(this, (IEntityLivingBase) ientityplayersp1) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                    flag = false;
                    break label47;
                }
            }

            flag = true;
        }

        if (!flag && (!((Boolean) this.noInventoryAttackValue.get()).booleanValue() || !MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.mc.getCurrentScreen()) && System.currentTimeMillis() - this.containerOpen >= ((Number) this.noInventoryDelayValue.get()).longValue())) {
            this.updateTarget();
            if (this.target == null) {
                this.stopBlocking();
            } else {
                this.currentTarget = this.target;
                if (!StringsKt.equals((String) this.targetModeValue.get(), "Switch", true) && this.isEnemy((IEntity) this.currentTarget)) {
                    this.target = this.currentTarget;
                }

            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        boolean $i$f$getCancelRun;
        IEntityLivingBase ientitylivingbase;

        if (((Boolean) this.lightingValue.get()).booleanValue()) {
            String this_$iv = (String) this.lightingModeValue.get();

            $i$f$getCancelRun = false;
            if (this_$iv == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String s = this_$iv.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            this_$iv = s;
            INetHandlerPlayClient inethandlerplayclient;
            SPacketSpawnGlobalEntity spacketspawnglobalentity;
            IEntityLivingBase ientitylivingbase1;
            double d0;
            double d1;
            EntityLightningBolt entitylightningbolt;
            World world;
            IEntityLivingBase ientitylivingbase2;
            IEntityLivingBase ientitylivingbase3;

            switch (this_$iv.hashCode()) {
            case -1407259064:
                if (this_$iv.equals("attack")) {
                    inethandlerplayclient = MinecraftInstance.mc.getNetHandler2();
                    spacketspawnglobalentity = new SPacketSpawnGlobalEntity;
                    entitylightningbolt = new EntityLightningBolt;
                    world = (World) MinecraftInstance.mc2.world;
                    ientitylivingbase1 = this.target;
                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    d0 = ientitylivingbase1.getPosX();
                    ientitylivingbase2 = this.target;
                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    d1 = ientitylivingbase2.getPosY();
                    ientitylivingbase3 = this.target;
                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    entitylightningbolt.<init>(world, d0, d1, ientitylivingbase3.getPosZ(), true);
                    spacketspawnglobalentity.<init>((Entity) entitylightningbolt);
                    inethandlerplayclient.handleSpawnGlobalEntity(spacketspawnglobalentity);
                    if (((Boolean) this.lightingSoundValue.get()).booleanValue()) {
                        MinecraftInstance.mc.getSoundHandler().playSound("entity.lightning.impact", 0.5F);
                    }
                }
                break;

            case 3079268:
                if (this_$iv.equals("dead")) {
                    if (this.target != null) {
                        IEntityLivingBase ientitylivingbase4;

                        if (this.lastTarget == null) {
                            ientitylivingbase4 = this.target;
                        } else {
                            ientitylivingbase4 = this.lastTarget;
                            if (this.lastTarget == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientitylivingbase4.getHealth() <= (float) 0) {
                                INetHandlerPlayClient inethandlerplayclient1 = MinecraftInstance.mc.getNetHandler2();
                                SPacketSpawnGlobalEntity spacketspawnglobalentity1 = new SPacketSpawnGlobalEntity;
                                EntityLightningBolt entitylightningbolt1 = new EntityLightningBolt;
                                World world1 = (World) MinecraftInstance.mc2.world;

                                ientitylivingbase2 = this.lastTarget;
                                if (this.lastTarget == null) {
                                    Intrinsics.throwNpe();
                                }

                                d1 = ientitylivingbase2.getPosX();
                                ientitylivingbase3 = this.lastTarget;
                                if (this.lastTarget == null) {
                                    Intrinsics.throwNpe();
                                }

                                double d2 = ientitylivingbase3.getPosY();
                                IEntityLivingBase ientitylivingbase5 = this.lastTarget;

                                if (this.lastTarget == null) {
                                    Intrinsics.throwNpe();
                                }

                                entitylightningbolt1.<init>(world1, d1, d2, ientitylivingbase5.getPosZ(), true);
                                spacketspawnglobalentity1.<init>((Entity) entitylightningbolt1);
                                inethandlerplayclient1.handleSpawnGlobalEntity(spacketspawnglobalentity1);
                                if (((Boolean) this.lightingSoundValue.get()).booleanValue()) {
                                    MinecraftInstance.mc.getSoundHandler().playSound("entity.lightning.impact", 0.5F);
                                }
                            }

                            ientitylivingbase4 = this.target;
                        }

                        this.lastTarget = ientitylivingbase4;
                    } else if (this.lastTarget != null) {
                        ientitylivingbase = this.lastTarget;
                        if (this.lastTarget == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientitylivingbase.getHealth() <= (float) 0) {
                            inethandlerplayclient = MinecraftInstance.mc.getNetHandler2();
                            spacketspawnglobalentity = new SPacketSpawnGlobalEntity;
                            entitylightningbolt = new EntityLightningBolt;
                            world = (World) MinecraftInstance.mc2.world;
                            ientitylivingbase1 = this.lastTarget;
                            if (this.lastTarget == null) {
                                Intrinsics.throwNpe();
                            }

                            d0 = ientitylivingbase1.getPosX();
                            ientitylivingbase2 = this.lastTarget;
                            if (this.lastTarget == null) {
                                Intrinsics.throwNpe();
                            }

                            d1 = ientitylivingbase2.getPosY();
                            ientitylivingbase3 = this.lastTarget;
                            if (this.lastTarget == null) {
                                Intrinsics.throwNpe();
                            }

                            entitylightningbolt.<init>(world, d0, d1, ientitylivingbase3.getPosZ(), true);
                            spacketspawnglobalentity.<init>((Entity) entitylightningbolt);
                            inethandlerplayclient.handleSpawnGlobalEntity(spacketspawnglobalentity);
                            if (((Boolean) this.lightingSoundValue.get()).booleanValue()) {
                                MinecraftInstance.mc.getSoundHandler().playSound("entity.lightning.impact", 0.5F);
                            }

                            this.lastTarget = this.target;
                        }
                    }
                }
            }
        }

        if (this.syncEntity != null) {
            ientitylivingbase = this.syncEntity;
            if (this.syncEntity == null) {
                Intrinsics.throwNpe();
            }

            if (ientitylivingbase.isDead()) {
                ++KillAura4.killCounts;
                int i = KillAura4.killCounts;

                this.syncEntity = (IEntityLivingBase) null;
            }
        }

        $i$f$getCancelRun = false;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag;
        label153: {
            if (!ientityplayersp.isSpectator()) {
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (access$isAlive(this, (IEntityLivingBase) ientityplayersp1) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                    flag = false;
                    break label153;
                }
            }

            flag = true;
        }

        if (flag) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            this.stopBlocking();
        } else if (((Boolean) this.noInventoryAttackValue.get()).booleanValue() && (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.mc.getCurrentScreen()) || System.currentTimeMillis() - this.containerOpen < ((Number) this.noInventoryDelayValue.get()).longValue())) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            if (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.mc.getCurrentScreen())) {
                this.containerOpen = System.currentTimeMillis();
            }

        } else {
            if (this.target != null && this.currentTarget != null) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getCooledAttackStrength(0.0F) >= ((Number) this.cooldownValue.get()).floatValue()) {
                    while (this.clicks > 0) {
                        this.runAttack();
                        int this_$iv1 = this.clicks;

                        this.clicks += -1;
                    }
                }
            }

        }
    }

    private final void esp(IEntityLivingBase entity, float partialTicks, float radius) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GLUtils.startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0F);
        GL11.glBegin(3);
        double x = entity.getLastTickPosX() + (entity.getPosX() - entity.getLastTickPosX()) * (double) partialTicks - MinecraftInstance.mc.getRenderManager().getViewerPosX();
        double y = entity.getLastTickPosY() + (entity.getPosY() - entity.getLastTickPosY()) * (double) partialTicks - MinecraftInstance.mc.getRenderManager().getViewerPosY();
        double z = entity.getLastTickPosZ() + (entity.getPosZ() - entity.getLastTickPosZ()) * (double) partialTicks - MinecraftInstance.mc.getRenderManager().getViewerPosZ();
        int i = 0;

        for (short short0 = 360; i <= short0; ++i) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            double d0 = (double) ientityplayersp.getTicksExisted() / 70.0D;
            double d1 = (double) i / 50.0D * 1.75D;
            double d2 = d0;
            boolean flag = false;
            double d3 = Math.sin(d1);
            int i = Color.HSBtoRGB((float) (d2 + d3) % 1.0F, 0.7F, 1.0F);
            Color rainbow = new Color(i);

            GL11.glColor3f((float) rainbow.getRed() / 255.0F, (float) rainbow.getGreen() / 255.0F, (float) rainbow.getBlue() / 255.0F);
            double d4 = (double) radius;

            d1 = (double) i * 6.283185307179586D / 45.0D;
            d2 = d4;
            flag = false;
            d3 = Math.cos(d1);
            d0 = x + d2 * d3;
            d4 = y + this.espAnimation;
            double d5 = (double) radius;

            d1 = (double) i * 6.283185307179586D / 45.0D;
            double d6 = d5;

            d2 = d4;
            double d7 = d0;

            flag = false;
            double d8 = Math.sin(d1);

            GL11.glVertex3d(d7, d2, z + d6 * d8);
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GLUtils.endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    private final void drawESP(IEntityLivingBase entity, int color, Render3DEvent e) {
        double x = entity.getLastTickPosX() + (entity.getPosX() - entity.getLastTickPosX()) * (double) e.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getRenderPosX();
        double y = entity.getLastTickPosY() + (entity.getPosY() - entity.getLastTickPosY()) * (double) e.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getRenderPosY();
        double z = entity.getLastTickPosZ() + (entity.getPosZ() - entity.getLastTickPosZ()) * (double) e.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getRenderPosZ();
        float radius = 0.15F;
        byte side = 4;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y + (double) 2, z);
        GL11.glRotatef(-entity.getWidth(), 0.0F, 1.0F, 0.0F);
        RenderUtils.glColor1(color);
        RenderUtils.enableSmoothLine(1.5F);
        Cylinder c = new Cylinder();

        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        c.setDrawStyle(100012);
        RenderUtils.glColor(new Color(80, 255, 80, 200));
        c.draw(0.0F, radius, 0.3F, side, 1);
        c.setDrawStyle(100012);
        GL11.glTranslated(0.0D, 0.0D, 0.3D);
        c.draw(radius, 0.0F, 0.3F, side, 1);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        c.setDrawStyle(100011);
        GL11.glTranslated(0.0D, 0.0D, -0.3D);
        RenderUtils.glColor1(color);
        c.draw(0.0F, radius, 0.3F, side, 1);
        c.setDrawStyle(100011);
        GL11.glTranslated(0.0D, 0.0D, 0.3D);
        c.draw(radius, 0.0F, 0.3F, side, 1);
        RenderUtils.disableSmoothLine();
        GL11.glPopMatrix();
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        int this_$iv;
        int drawTime;
        boolean flag;
        double d0;
        double d1;
        IntProgression intprogression;
        double d2;
        double d3;
        double d4;
        IEntityPlayerSP ientityplayersp;
        IEntityPlayerSP ientityplayersp1;

        if (((Boolean) this.circleValue.get()).booleanValue()) {
            GL11.glPushMatrix();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            d1 = ientityplayersp.getLastTickPosX();
            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            d2 = ientityplayersp1.getPosX();
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            d1 = d1 + (d2 - ientityplayersp2.getLastTickPosX()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks() - MinecraftInstance.mc.getRenderManager().getRenderPosX();
            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            d2 = ientityplayersp1.getLastTickPosY();
            ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            d3 = ientityplayersp2.getPosY();
            IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp3 == null) {
                Intrinsics.throwNpe();
            }

            d2 = d2 + (d3 - ientityplayersp3.getLastTickPosY()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks() - MinecraftInstance.mc.getRenderManager().getRenderPosY();
            ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            d3 = ientityplayersp2.getLastTickPosZ();
            ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp3 == null) {
                Intrinsics.throwNpe();
            }

            d4 = ientityplayersp3.getPosZ();
            IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp4 == null) {
                Intrinsics.throwNpe();
            }

            float f;
            double d5;
            float f1;
            label421: {
                GL11.glTranslated(d1, d2, d3 + (d4 - ientityplayersp4.getLastTickPosZ()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks() - MinecraftInstance.mc.getRenderManager().getRenderPosZ());
                GL11.glEnable(3042);
                GL11.glEnable(2848);
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(1.0F);
                GL11.glColor4f((float) ((Number) this.circleRed.get()).intValue() / 255.0F, (float) ((Number) this.circleGreen.get()).intValue() / 255.0F, (float) ((Number) this.circleBlue.get()).intValue() / 255.0F, (float) ((Number) this.circleAlpha.get()).intValue() / 255.0F);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glBegin(3);
                byte drawPercent = 0;

                intprogression = RangesKt.step((IntProgression) (new IntRange(drawPercent, 360)), 61 - ((Number) this.circleAccuracy.get()).intValue());
                this_$iv = intprogression.getFirst();
                drawTime = intprogression.getLast();
                int drawMode = intprogression.getStep();

                if (drawMode >= 0) {
                    if (this_$iv > drawTime) {
                        break label421;
                    }
                } else if (this_$iv < drawTime) {
                    break label421;
                }

                while (true) {
                    d0 = (double) this_$iv * 3.141592653589793D / 180.0D;
                    boolean points = false;

                    f1 = (float) Math.cos(d0) * ((Number) this.rangeValue.get()).floatValue();
                    d0 = (double) this_$iv * 3.141592653589793D / 180.0D;
                    f = f1;
                    points = false;
                    d5 = Math.sin(d0);
                    GL11.glVertex2f(f, (float) d5 * ((Number) this.rangeValue.get()).floatValue());
                    if (this_$iv == drawTime) {
                        break;
                    }

                    this_$iv += drawMode;
                }
            }

            double d6 = 6.283185307179586D;

            flag = false;
            f1 = (float) Math.cos(d6) * ((Number) this.rangeValue.get()).floatValue();
            d6 = 6.283185307179586D;
            f = f1;
            flag = false;
            d5 = Math.sin(d6);
            GL11.glVertex2f(f, (float) d5 * ((Number) this.rangeValue.get()).floatValue());
            GL11.glEnd();
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
        }

        boolean flag1 = false;

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag2;
        label410: {
            if (!ientityplayersp.isSpectator()) {
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (access$isAlive(this, (IEntityLivingBase) ientityplayersp1) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                    flag2 = false;
                    break label410;
                }
            }

            flag2 = true;
        }

        if (flag2) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            this.stopBlocking();
        } else if (((Boolean) this.noInventoryAttackValue.get()).booleanValue() && (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.mc.getCurrentScreen()) || System.currentTimeMillis() - this.containerOpen < ((Number) this.noInventoryDelayValue.get()).longValue())) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            if (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.mc.getCurrentScreen())) {
                this.containerOpen = System.currentTimeMillis();
            }

        } else if (this.target != null) {
            String s = (String) this.markValue.get();

            flag1 = false;
            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                double d7;
                IEntityLivingBase ientitylivingbase;
                IEntity ientity;
                IEntityLivingBase ientitylivingbase1;
                IEntityLivingBase ientitylivingbase2;

                switch (s.hashCode()) {
                case -1102567108:
                    if (s.equals("liquid")) {
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        ientity = (IEntity) ientitylivingbase;
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        RenderUtils.drawPlatform(ientity, ientitylivingbase1.getHurtTime() <= 0 ? new Color(37, 126, 255, 170) : new Color(255, 0, 0, 170));
                    }
                    break;

                case 101234:
                    if (s.equals("fdp")) {
                        drawTime = (int) (System.currentTimeMillis() % (long) 1500);
                        flag = drawTime > 750;
                        d0 = (double) drawTime / 750.0D;
                        if (!flag) {
                            d0 = (double) 1 - d0;
                        } else {
                            d0 -= (double) 1;
                        }

                        d0 = EaseUtils.easeInOutQuad(d0);
                        GL11.glPushMatrix();
                        GL11.glDisable(3553);
                        GL11.glEnable(2848);
                        GL11.glEnable(2881);
                        GL11.glEnable(2832);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        GL11.glHint(3154, 4354);
                        GL11.glHint(3155, 4354);
                        GL11.glHint(3153, 4354);
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        IAxisAlignedBB iaxisalignedbb = ientitylivingbase.getEntityBoundingBox();
                        double d8 = iaxisalignedbb.getMaxX() - iaxisalignedbb.getMinX() + 0.3D;
                        double height = iaxisalignedbb.getMaxY() - iaxisalignedbb.getMinY();

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosX();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosX();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        double x = d1 + (d2 - ientitylivingbase2.getLastTickPosX()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosX();

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosY();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosY();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        double y = d1 + (d2 - ientitylivingbase2.getLastTickPosY()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosY() + height * d0;

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosZ();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosZ();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        double z = d1 + (d2 - ientitylivingbase2.getLastTickPosZ()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosZ();

                        GL11.glLineWidth((float) (d8 * (double) 5.0F));
                        GL11.glBegin(3);
                        int i = 0;

                        for (short short0 = 360; i <= short0; ++i) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            d1 = (double) ientityplayersp.getTicksExisted() / 70.0D;
                            d7 = (double) i / 50.0D * 1.75D;
                            double d9 = d1;
                            boolean flag3 = false;
                            double d10 = Math.sin(d7);
                            int i = Color.HSBtoRGB((float) (d9 + d10) % 1.0F, 0.7F, 1.0F);
                            Color color = new Color(i);

                            GL11.glColor3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F);
                            d7 = (double) i * 6.283185307179586D / 45.0D;
                            flag3 = false;
                            d10 = Math.cos(d7);
                            d1 = x + d8 * d10;
                            d7 = (double) i * 6.283185307179586D / 45.0D;
                            double d11 = d1;

                            flag3 = false;
                            double d12 = Math.sin(d7);

                            GL11.glVertex3d(d11, y, z + d8 * d12);
                        }

                        GL11.glEnd();
                        GL11.glDepthMask(true);
                        GL11.glEnable(2929);
                        GL11.glDisable(2848);
                        GL11.glDisable(2881);
                        GL11.glEnable(2832);
                        GL11.glEnable(3553);
                        GL11.glPopMatrix();
                    }
                    break;

                case 112785:
                    if (s.equals("red")) {
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        ientity = (IEntity) ientitylivingbase;
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        RenderUtils.drawPlatform(ientity, ientitylivingbase1.getHurtTime() <= 0 ? new Color(255, 255, 255, 255) : new Color(124, 215, 255, 255));
                    }
                    break;

                case 3443503:
                    if (s.equals("plat")) {
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        RenderUtils.drawPlatform((IEntity) ientitylivingbase, this.hitable ? new Color(37, 126, 255, 70) : new Color(255, 0, 0, 70));
                    }
                    break;

                case 3530364:
                    if (s.equals("sims")) {
                        float f2 = 0.15F;
                        byte b0 = 4;

                        GL11.glPushMatrix();
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosX();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosX();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = d1 + (d2 - ientitylivingbase2.getLastTickPosX()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosX();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getLastTickPosY();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d3 = ientitylivingbase2.getPosY();
                        IEntityLivingBase ientitylivingbase3 = this.target;

                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = d2 + (d3 - ientitylivingbase3.getLastTickPosY()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosY();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 += (double) ientitylivingbase2.getHeight() * 1.1D;
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d3 = ientitylivingbase2.getLastTickPosZ();
                        ientitylivingbase3 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d4 = ientitylivingbase3.getPosZ();
                        IEntityLivingBase ientitylivingbase4 = this.target;

                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        GL11.glTranslated(d1, d2, d3 + (d4 - ientitylivingbase4.getLastTickPosZ()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosZ());
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        GL11.glRotatef(-ientitylivingbase.getWidth(), 0.0F, 1.0F, 0.0F);
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        GL11.glRotatef(((float) ientityplayersp.getTicksExisted() + MinecraftInstance.mc.getTimer().getRenderPartialTicks()) * (float) 5, 0.0F, 1.0F, 0.0F);
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        RenderUtils.glColor(ientitylivingbase.getHurtTime() <= 0 ? new Color(80, 255, 80) : new Color(255, 0, 0));
                        RenderUtils.enableSmoothLine(1.5F);
                        Cylinder cylinder = new Cylinder();

                        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                        cylinder.draw(0.0F, f2, 0.3F, b0, 1);
                        cylinder.setDrawStyle(100012);
                        GL11.glTranslated(0.0D, 0.0D, 0.3D);
                        cylinder.draw(f2, 0.0F, 0.3F, b0, 1);
                        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslated(0.0D, 0.0D, -0.3D);
                        cylinder.draw(0.0F, f2, 0.3F, b0, 1);
                        GL11.glTranslated(0.0D, 0.0D, 0.3D);
                        cylinder.draw(f2, 0.0F, 0.3F, b0, 1);
                        RenderUtils.disableSmoothLine();
                        GL11.glPopMatrix();
                    }
                    break;

                case 93832333:
                    if (s.equals("block")) {
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        IAxisAlignedBB iaxisalignedbb1 = ientitylivingbase.getEntityBoundingBox();

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        ientitylivingbase.setEntityBoundingBox(iaxisalignedbb1.expand(0.2D, 0.2D, 0.2D));
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        ientity = (IEntity) ientitylivingbase;
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        RenderUtils.drawEntityBox(ientity, ientitylivingbase1.getHurtTime() <= 0 ? Color.GREEN : Color.RED, true);
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        ientitylivingbase.setEntityBoundingBox(iaxisalignedbb1);
                    }
                    break;

                case 101009364:
                    if (s.equals("jello")) {
                        drawTime = (int) (System.currentTimeMillis() % (long) 2000);
                        flag = drawTime > 1000;
                        d0 = (double) drawTime / 1000.0D;
                        if (!flag) {
                            d0 = (double) 1 - d0;
                        } else {
                            d0 -= (double) 1;
                        }

                        d0 = EaseUtils.easeInOutQuad(d0);
                        boolean bb = false;
                        List list = (List) (new ArrayList());

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        IAxisAlignedBB iaxisalignedbb2 = ientitylivingbase.getEntityBoundingBox();
                        double radius = iaxisalignedbb2.getMaxX() - iaxisalignedbb2.getMinX();
                        double height1 = iaxisalignedbb2.getMaxY() - iaxisalignedbb2.getMinY();

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosX();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosX();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        double posX = d1 + (d2 - ientitylivingbase2.getLastTickPosX()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks();

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosY();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosY();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        double posY = d1 + (d2 - ientitylivingbase2.getLastTickPosY()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks();

                        if (flag) {
                            posY -= 0.5D;
                        } else {
                            posY += 0.5D;
                        }

                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosZ();
                        ientitylivingbase1 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosZ();
                        ientitylivingbase2 = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        label363: {
                            double posZ = d1 + (d2 - ientitylivingbase2.getLastTickPosZ()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks();
                            byte b1 = 0;

                            intprogression = RangesKt.step((IntProgression) (new IntRange(b1, 360)), 7);
                            int baseMove = intprogression.getFirst();
                            int rainbow = intprogression.getLast();
                            int min = intprogression.getStep();

                            if (min >= 0) {
                                if (baseMove > rainbow) {
                                    break label363;
                                }
                            } else if (baseMove < rainbow) {
                                break label363;
                            }

                            while (true) {
                                double d13 = (double) baseMove * 3.141592653589793D / (double) 180.0F;
                                boolean flag4 = false;
                                double d14 = Math.sin(d13);

                                d2 = posX - d14 * radius;
                                d3 = posY + height1 * d0;
                                d13 = (double) baseMove * 3.141592653589793D / (double) 180.0F;
                                d14 = d3;
                                double d15 = d2;

                                flag4 = false;
                                double d16 = Math.cos(d13);
                                double d17 = posZ + d16 * radius;

                                list.add(new WVec3(d15, d14, d17));
                                if (baseMove == rainbow) {
                                    break;
                                }

                                baseMove += min;
                            }
                        }

                        list.add(list.get(0));
                        MinecraftInstance.mc.getEntityRenderer().disableLightmap();
                        GL11.glPushMatrix();
                        GL11.glDisable(3553);
                        GL11.glBlendFunc(770, 771);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glDisable(2929);
                        GL11.glBegin(3);
                        double d18 = (d0 > 0.5D ? (double) 1 - d0 : d0) * (double) 2;

                        d7 = height1 / (double) 60 * (double) 20 * ((double) 1 - d18) * (double) (flag ? -1 : 1);
                        int i1 = 0;

                        for (byte b2 = 20; i1 <= b2; ++i1) {
                            double moveFace = height1 / (double) 60.0F * (double) i1 * d18;

                            if (flag) {
                                moveFace = -moveFace;
                            }

                            WVec3 firstPoint = (WVec3) list.get(0);

                            GL11.glVertex3d(firstPoint.getXCoord() - MinecraftInstance.mc.getRenderManager().getViewerPosX(), firstPoint.getYCoord() - moveFace - d7 - MinecraftInstance.mc.getRenderManager().getViewerPosY(), firstPoint.getZCoord() - MinecraftInstance.mc.getRenderManager().getViewerPosZ());
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.7F * ((float) i1 / 20.0F));
                            Iterator iterator = list.iterator();

                            while (iterator.hasNext()) {
                                WVec3 vec3 = (WVec3) iterator.next();

                                GL11.glVertex3d(vec3.getXCoord() - MinecraftInstance.mc.getRenderManager().getViewerPosX(), vec3.getYCoord() - moveFace - d7 - MinecraftInstance.mc.getRenderManager().getViewerPosY(), vec3.getZCoord() - MinecraftInstance.mc.getRenderManager().getViewerPosZ());
                            }

                            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
                        }

                        GL11.glEnd();
                        GL11.glEnable(2929);
                        GL11.glDisable(2848);
                        GL11.glDisable(3042);
                        GL11.glEnable(3553);
                        GL11.glPopMatrix();
                    }
                }

                if (this.currentTarget != null && this.attackTimer.hasTimePassed(this.attackDelay)) {
                    ientitylivingbase = this.currentTarget;
                    if (this.currentTarget == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientitylivingbase.getHurtTime() <= ((Number) this.hurtTimeValue.get()).intValue()) {
                        this_$iv = this.clicks++;
                        this.attackTimer.reset();
                        this.attackDelay = TimeUtils.randomClickDelay(((Number) this.minCPS.get()).intValue(), ((Number) this.maxCPS.get()).intValue());
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onEntityMove(@NotNull EntityMovementEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntity movedEntity = event.getMovedEntity();

        if (this.target != null && !(Intrinsics.areEqual(movedEntity, this.currentTarget) ^ true)) {
            this.updateHitable();
        }
    }

    private final void runAttack() {
        if (this.target != null) {
            if (this.currentTarget != null) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp != null) {
                    IEntityPlayerSP thePlayer = ientityplayersp;
                    IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                    if (iworldclient != null) {
                        IWorldClient theWorld = iworldclient;
                        float failRate = ((Number) this.failRateValue.get()).floatValue();
                        boolean swing = ((Boolean) this.swingValue.get()).booleanValue();
                        boolean multi = StringsKt.equals((String) this.targetModeValue.get(), "Multi", true);
                        boolean openInventory = ((Boolean) this.aacValue.get()).booleanValue() && MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.mc.getCurrentScreen());
                        boolean failHit = failRate > (float) 0 && (float) (new Random()).nextInt(100) <= failRate;

                        if (openInventory) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketCloseWindow());
                        }

                        if (this.hitable && !failHit) {
                            IEntityLivingBase ientitylivingbase;

                            if (!multi) {
                                ientitylivingbase = this.currentTarget;
                                if (this.currentTarget == null) {
                                    Intrinsics.throwNpe();
                                }

                                this.attackEntity(ientitylivingbase);
                            } else {
                                int $i$f$createOpenInventoryPacket = 0;
                                Iterator iterator = theWorld.getLoadedEntityList().iterator();

                                while (iterator.hasNext()) {
                                    IEntity entity = (IEntity) iterator.next();
                                    double distance = PlayerExtensionKt.getDistanceToEntityBox((IEntity) thePlayer, entity);

                                    if (MinecraftInstance.classProvider.isEntityLivingBase(entity) && this.isEnemy(entity) && distance <= (double) this.getRange(entity)) {
                                        this.attackEntity(entity.asEntityLivingBase());
                                        ++$i$f$createOpenInventoryPacket;
                                        if (((Number) this.limitedMultiTargetsValue.get()).intValue() != 0 && ((Number) this.limitedMultiTargetsValue.get()).intValue() <= $i$f$createOpenInventoryPacket) {
                                            break;
                                        }
                                    }
                                }
                            }

                            List list = this.prevTargetEntities;
                            int i;

                            if (((Boolean) this.aacValue.get()).booleanValue()) {
                                ientitylivingbase = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                i = ientitylivingbase.getEntityId();
                            } else {
                                ientitylivingbase = this.currentTarget;
                                if (this.currentTarget == null) {
                                    Intrinsics.throwNpe();
                                }

                                i = ientitylivingbase.getEntityId();
                            }

                            list.add(Integer.valueOf(i));
                            if (Intrinsics.areEqual(this.target, this.currentTarget)) {
                                this.target = (IEntityLivingBase) null;
                            }
                        } else if (swing && (((Boolean) this.fakeSwingValue.get()).booleanValue() || failHit)) {
                            thePlayer.swingItem();
                        }

                        if (openInventory) {
                            IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                            boolean flag = false;
                            IClassProvider iclassprovider = WrapperImpl.INSTANCE.getClassProvider();
                            IEntityPlayerSP ientityplayersp1 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();

                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            IPacket ipacket = (IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.OPEN_INVENTORY);

                            iinethandlerplayclient.addToSendQueue(ipacket);
                        }

                    }
                }
            }
        }
    }

    private final void updateTarget() {
        this.target = (IEntityLivingBase) null;
        int hurtTime = ((Number) this.hurtTimeValue.get()).intValue();
        float fov = ((Number) this.fovValue.get()).floatValue();
        boolean switchMode = StringsKt.equals((String) this.targetModeValue.get(), "Switch", true);
        boolean theWorld = false;
        List targets = (List) (new ArrayList());
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        IWorldClient theWorld1 = iworldclient;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        Iterator $this$sortBy$iv = theWorld1.getLoadedEntityList().iterator();

        while ($this$sortBy$iv.hasNext()) {
            IEntity entity = (IEntity) $this$sortBy$iv.next();

            if (MinecraftInstance.classProvider.isEntityLivingBase(entity) && this.isEnemy(entity) && (!switchMode || !this.prevTargetEntities.contains(Integer.valueOf(entity.getEntityId())))) {
                double $i$f$sortBy = PlayerExtensionKt.getDistanceToEntityBox((IEntity) thePlayer, entity);
                double entityFov = RotationUtils.getRotationDifference(entity);

                if ($i$f$sortBy <= (double) this.getMaxRange() && (fov == 180.0F || entityFov <= (double) fov) && entity.asEntityLivingBase().getHurtTime() <= hurtTime) {
                    targets.add(entity.asEntityLivingBase());
                }
            }
        }

        String entity1 = (String) this.priorityValue.get();
        boolean $this$sortBy$iv1 = false;

        if (entity1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s = entity1.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            entity1 = s;
            Comparator comparator;
            boolean $i$f$sortBy1;
            boolean entityFov1;

            switch (entity1.hashCode()) {
            case -1221262756:
                if (entity1.equals("health")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura4$updateTarget$$inlined$sortBy$2());
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case -962590849:
                if (entity1.equals("direction")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura4$updateTarget$$inlined$sortBy$3());
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case 103811:
                if (entity1.equals("hyt")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura4$updateTarget$$inlined$sortBy$5());
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case 288459765:
                if (entity1.equals("distance")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura4$updateTarget$$inlined$sortBy$1(thePlayer));
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case 886905078:
                if (entity1.equals("livingtime")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura4$updateTarget$$inlined$sortBy$4());
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
            }

            $this$sortBy$iv = targets.iterator();

            IEntityLivingBase entity2;

            do {
                if (!$this$sortBy$iv.hasNext()) {
                    Collection entity3 = (Collection) this.prevTargetEntities;

                    $this$sortBy$iv1 = false;
                    if (!entity3.isEmpty()) {
                        this.prevTargetEntities.clear();
                        this.updateTarget();
                    }

                    return;
                }

                entity2 = (IEntityLivingBase) $this$sortBy$iv.next();
            } while (!this.updateRotations((IEntity) entity2));

            this.target = entity2;
        }
    }

    private final boolean isEnemy(IEntity entity) {
        if (MinecraftInstance.classProvider.isEntityLivingBase(entity) && entity != null && (EntityUtils.targetDead || this.isAlive(entity.asEntityLivingBase())) && Intrinsics.areEqual(entity, MinecraftInstance.mc.getThePlayer()) ^ true) {
            if (!EntityUtils.targetInvisible && entity.isInvisible()) {
                return false;
            } else if (EntityUtils.targetPlayer && MinecraftInstance.classProvider.isEntityPlayer(entity)) {
                IEntityPlayer player = entity.asEntityPlayer();

                if (!player.isSpectator() && !AntiBot.isBot((IEntityLivingBase) player)) {
                    if (PlayerExtensionKt.isClientFriend(player) && !LiquidBounce.INSTANCE.getModuleManager().get(NoFriends.class).getState()) {
                        return false;
                    } else {
                        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Teams.class);

                        if (module == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.Teams");
                        } else {
                            Teams teams = (Teams) module;

                            return !teams.getState() || !teams.isInYourTeam(entity.asEntityLivingBase());
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return EntityUtils.targetMobs && PlayerExtensionKt.isMob(entity) || EntityUtils.targetAnimals && PlayerExtensionKt.isAnimal(entity);
            }
        } else {
            return false;
        }
    }

    private final void attackEntity(IEntityLivingBase entity) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;

        if (thePlayer.isBlocking() || this.blockingStatus) {
            MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
            if (((Boolean) this.afterAttackValue.get()).booleanValue()) {
                this.blockingStatus = false;
            }
        }

        LiquidBounce.INSTANCE.getEventManager().callEvent((Event) (new AttackEvent((IEntity) entity)));
        if (((Boolean) this.swingValue.get()).booleanValue()) {
            ;
        }

        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketUseEntity((IEntity) entity, ICPacketUseEntity.WAction.ATTACK));
        if (((Boolean) this.swingValue.get()).booleanValue()) {
            thePlayer.swingItem();
        }

        if (((Boolean) this.keepSprintValue.get()).booleanValue()) {
            if (thePlayer.getFallDistance() > 0.0F && !thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) && !thePlayer.isRiding()) {
                thePlayer.onCriticalHit((IEntity) entity);
            }

            if (MinecraftInstance.functions.getModifierForCreature(thePlayer.getHeldItem(), entity.getCreatureAttribute()) > 0.0F) {
                thePlayer.onEnchantmentCritical(entity);
            }
        } else if (MinecraftInstance.mc.getPlayerController().getCurrentGameType() != IWorldSettings.WGameType.SPECTATOR) {
            thePlayer.attackTargetEntityWithCurrentItem((IEntity) entity);
        }

        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Criticals.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Criticals");
        } else {
            Criticals criticals = (Criticals) module;
            int this_$iv = 0;

            IItemStack iitemstack;

            for (byte $i$f$getCanBlock = 2; this_$iv <= $i$f$getCanBlock; ++this_$iv) {
                IEntityLivingBase ientitylivingbase;

                if (thePlayer.getFallDistance() > 0.0F && !thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) && thePlayer.getRidingEntity() == null || criticals.getState() && criticals.getMsTimer().hasTimePassed((long) ((Number) criticals.getDelayValue().get()).intValue()) && !thePlayer.isInWater() && !thePlayer.isInLava() && !thePlayer.isInWeb()) {
                    ientitylivingbase = this.target;
                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    thePlayer.onCriticalHit((IEntity) ientitylivingbase);
                }

                IExtractedFunctions iextractedfunctions = MinecraftInstance.functions;

                iitemstack = thePlayer.getHeldItem();
                IEntityLivingBase ientitylivingbase1 = this.target;

                if (this.target == null) {
                    Intrinsics.throwNpe();
                }

                if (iextractedfunctions.getModifierForCreature(iitemstack, ientitylivingbase1.getCreatureAttribute()) > 0.0F || ((Boolean) this.fakeSharpValue.get()).booleanValue()) {
                    ientitylivingbase = this.target;
                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    thePlayer.onEnchantmentCritical(ientitylivingbase);
                }
            }

            label174: {
                if (!thePlayer.isBlocking()) {
                    if (((String) this.blockModeValue.get()).equals("None")) {
                        break label174;
                    }

                    boolean flag = false;

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    boolean flag1;
                    label107: {
                        if (ientityplayersp.getHeldItem() != null) {
                            IClassProvider iclassprovider = MinecraftInstance.classProvider;
                            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            iitemstack = ientityplayersp1.getHeldItem();
                            if (iitemstack == null) {
                                Intrinsics.throwNpe();
                            }

                            if (iclassprovider.isItemSword(iitemstack.getItem())) {
                                flag1 = true;
                                break label107;
                            }
                        }

                        flag1 = false;
                    }

                    if (!flag1) {
                        break label174;
                    }
                }

                if (((Number) this.blockRate.get()).intValue() <= 0 || (new Random()).nextInt(100) > ((Number) this.blockRate.get()).intValue()) {
                    return;
                }

                if (((Boolean) this.delayedBlockValue.get()).booleanValue()) {
                    return;
                }

                this.startBlocking((IEntity) entity);
            }

            thePlayer.resetCooldown();
        }
    }

    private final boolean updateRotations(IEntity entity) {
        IAxisAlignedBB boundingBox = entity.getEntityBoundingBox();
        WVec3 limitedRotation;
        Rotation rotation;
        VecRotation limitedRotation1;
        Rotation limitedRotation3;
        Rotation rotation;
        IEntityPlayerSP ientityplayersp;
        VecRotation vecrotation;
        boolean flag;
        boolean flag1;
        boolean flag2;
        IEntityPlayerSP ientityplayersp1;

        if (StringsKt.equals((String) this.rotations.get(), "New", true)) {
            if (((Number) this.maxTurnSpeed.get()).floatValue() <= 0.0F) {
                return true;
            } else {
                if (((Boolean) this.predictValue.get()).booleanValue()) {
                    boundingBox = boundingBox.offset((entity.getPosX() - entity.getPrevPosX()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (entity.getPosY() - entity.getPrevPosY()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (entity.getPosZ() - entity.getPrevPosZ()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()));
                }

                flag = ((Boolean) this.outborderValue.get()).booleanValue() && !this.attackTimer.hasTimePassed(this.attackDelay / (long) 2);
                flag1 = ((Boolean) this.randomCenterValue.get()).booleanValue();
                flag2 = ((Boolean) this.predictValue.get()).booleanValue();
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                vecrotation = RotationUtils.searchCenter(boundingBox, flag, flag1, flag2, PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp1, entity) < ((Number) this.throughWallsRangeValue.get()).doubleValue(), this.getMaxRange());
                if (vecrotation != null) {
                    limitedRotation1 = vecrotation;
                    limitedRotation = limitedRotation1.component1();
                    rotation = limitedRotation1.component2();
                    rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, RotationUtils.getHypixelRotations(RotationUtils.getCenter(entity.getEntityBoundingBox()), false), (float) (Math.random() * (double) (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue()) + ((Number) this.minTurnSpeed.get()).doubleValue()));
                    Intrinsics.checkExpressionValueIsNotNull(rotation, "RotationUtils.limitAngle…rnSpeed.get()).toFloat())");
                    limitedRotation3 = rotation;
                    if (((Boolean) this.silentRotationValue.get()).booleanValue()) {
                        RotationUtils.setTargetRotation(limitedRotation3, ((Boolean) this.aacValue.get()).booleanValue() ? 15 : 0);
                    } else {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        limitedRotation3.toPlayer((IEntityPlayer) ientityplayersp);
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else if (!StringsKt.equals((String) this.rotations.get(), "LiquidBounce", true)) {
            if (StringsKt.equals((String) this.rotations.get(), "BackTrack", true)) {
                if (((Boolean) this.predictValue.get()).booleanValue()) {
                    boundingBox = boundingBox.offset((entity.getPosX() - entity.getPrevPosX()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (entity.getPosY() - entity.getPrevPosY()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (entity.getPosZ() - entity.getPrevPosZ()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()));
                }

                rotation = RotationUtils.serverRotation;
                WVec3 wvec3 = RotationUtils.getCenter(entity.getEntityBoundingBox());

                flag2 = ((Boolean) this.predictValue.get()).booleanValue();
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                rotation = RotationUtils.limitAngleChange(rotation, RotationUtils.OtherRotation(boundingBox, wvec3, flag2, PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp1, entity) < ((Number) this.throughWallsRangeValue.get()).doubleValue(), this.getMaxRange()), (float) (Math.random() * (double) (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue()) + ((Number) this.minTurnSpeed.get()).doubleValue()));
                Intrinsics.checkExpressionValueIsNotNull(rotation, "RotationUtils.limitAngle…rnSpeed.get()).toFloat())");
                Rotation limitedRotation2 = rotation;

                if (!((Boolean) this.silentRotationValue.get()).booleanValue()) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    limitedRotation2.toPlayer((IEntityPlayer) ientityplayersp);
                    return true;
                }

                RotationUtils.setTargetRotation(limitedRotation2, ((Boolean) this.aacValue.get()).booleanValue() ? 15 : 0);
            }

            return true;
        } else if (((Number) this.maxTurnSpeed.get()).floatValue() <= 0.0F) {
            return true;
        } else {
            if (((Boolean) this.predictValue.get()).booleanValue()) {
                boundingBox = boundingBox.offset((entity.getPosX() - entity.getPrevPosX()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (entity.getPosY() - entity.getPrevPosY()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (entity.getPosZ() - entity.getPrevPosZ()) * (double) RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()));
            }

            flag = ((Boolean) this.outborderValue.get()).booleanValue() && !this.attackTimer.hasTimePassed(this.attackDelay / (long) 2);
            flag1 = ((Boolean) this.randomCenterValue.get()).booleanValue();
            flag2 = ((Boolean) this.predictValue.get()).booleanValue();
            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            vecrotation = RotationUtils.searchCenter(boundingBox, flag, flag1, flag2, PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp1, entity) < ((Number) this.throughWallsRangeValue.get()).doubleValue(), this.getMaxRange());
            if (vecrotation != null) {
                limitedRotation1 = vecrotation;
                limitedRotation = limitedRotation1.component1();
                rotation = limitedRotation1.component2();
                rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, rotation, (float) (Math.random() * (double) (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue()) + ((Number) this.minTurnSpeed.get()).doubleValue()));
                Intrinsics.checkExpressionValueIsNotNull(rotation, "RotationUtils.limitAngle…rnSpeed.get()).toFloat())");
                limitedRotation3 = rotation;
                if (((Boolean) this.silentRotationValue.get()).booleanValue()) {
                    RotationUtils.setTargetRotation(limitedRotation3, ((Boolean) this.aacValue.get()).booleanValue() ? 15 : 0);
                } else {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    limitedRotation3.toPlayer((IEntityPlayer) ientityplayersp);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    private final void updateHitable() {
        if (((Boolean) this.hitableValue.get()).booleanValue()) {
            this.hitable = true;
        } else if (((Number) this.maxTurnSpeed.get()).floatValue() <= 0.0F) {
            this.hitable = true;
        } else {
            double raycastedEntity = (double) this.getMaxRange();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IEntity ientity = (IEntity) ientityplayersp;
            IEntityLivingBase ientitylivingbase = this.target;

            if (this.target == null) {
                Intrinsics.throwNpe();
            }

            double d0 = PlayerExtensionKt.getDistanceToEntityBox(ientity, (IEntity) ientitylivingbase);
            boolean flag = false;
            double reach = Math.min(raycastedEntity, d0) + (double) 1;

            if (((Boolean) this.raycastValue.get()).booleanValue()) {
                IEntity raycastedEntity1 = RaycastUtils.raycastEntity(reach, (RaycastUtils.EntityFilter) (new RaycastUtils.EntityFilter() {
                    public boolean canRaycast(@Nullable IEntity entity) {
                        boolean flag;

                        if (!((Boolean) KillAura4.this.livingRaycastValue.get()).booleanValue() || MinecraftInstance.classProvider.isEntityLivingBase(entity) && !MinecraftInstance.classProvider.isEntityArmorStand(entity)) {
                            label56: {
                                if (!KillAura4.this.isEnemy(entity) && !((Boolean) KillAura4.this.raycastIgnoredValue.get()).booleanValue()) {
                                    if (!((Boolean) KillAura4.this.aacValue.get()).booleanValue()) {
                                        break label56;
                                    }

                                    IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                                    if (iworldclient == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (entity == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    Collection collection = iworldclient.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox());
                                    boolean flag1 = false;

                                    if (collection.isEmpty()) {
                                        break label56;
                                    }
                                }

                                flag = true;
                                return flag;
                            }
                        }

                        flag = false;
                        return flag;
                    }
                }));

                if (((Boolean) this.raycastValue.get()).booleanValue() && raycastedEntity1 != null && MinecraftInstance.classProvider.isEntityLivingBase(raycastedEntity1) && (LiquidBounce.INSTANCE.getModuleManager().get(NoFriends.class).getState() || !MinecraftInstance.classProvider.isEntityPlayer(raycastedEntity1) || !PlayerExtensionKt.isClientFriend(raycastedEntity1.asEntityPlayer()))) {
                    this.currentTarget = raycastedEntity1.asEntityLivingBase();
                }

                this.hitable = ((Number) this.maxTurnSpeed.get()).floatValue() > 0.0F ? Intrinsics.areEqual(this.currentTarget, raycastedEntity1) : true;
            } else {
                this.hitable = RotationUtils.isFaced((IEntity) this.currentTarget, reach);
            }

        }
    }

    private final void startBlocking(IEntity interactEntity) {
        if (LiquidBounce.INSTANCE.getModuleManager().get(OldHitting.class).getState()) {
            IItemStack itemStack$iv;
            WEnumHand hand$iv;
            boolean $i$f$createUseItemPacket;
            IINetHandlerPlayClient iinethandlerplayclient;
            IPacket ipacket;
            IINetHandlerPlayClient iinethandlerplayclient1;
            IEntityPlayerSP ientityplayersp;

            if (StringsKt.equals((String) this.blockModeValue.get(), "c07c08", true)) {
                iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                itemStack$iv = ientityplayersp.getInventory().getCurrentItemInHand();
                hand$iv = WEnumHand.OFF_HAND;
                iinethandlerplayclient = iinethandlerplayclient1;
                $i$f$createUseItemPacket = false;
                ipacket = WrapperImpl.INSTANCE.getClassProvider().createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                if (ipacket == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue(ipacket);
                iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                itemStack$iv = ientityplayersp.getInventory().getCurrentItemInHand();
                hand$iv = WEnumHand.OFF_HAND;
                iinethandlerplayclient = iinethandlerplayclient1;
                $i$f$createUseItemPacket = false;
                ipacket = WrapperImpl.INSTANCE.getClassProvider().createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                if (ipacket == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue(ipacket);
                this.blockingStatus = true;
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "c07", true)) {
                iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                itemStack$iv = ientityplayersp.getHeldItem();
                hand$iv = WEnumHand.MAIN_HAND;
                iinethandlerplayclient = iinethandlerplayclient1;
                $i$f$createUseItemPacket = false;
                ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand$iv);
                if (ipacket == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue(ipacket);
                iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                itemStack$iv = ientityplayersp.getHeldItem();
                hand$iv = WEnumHand.OFF_HAND;
                iinethandlerplayclient = iinethandlerplayclient1;
                $i$f$createUseItemPacket = false;
                ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand$iv);
                if (ipacket == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue(ipacket);
                this.blockingStatus = true;
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "UseItem", true)) {
                KeyBinding.setKeyBindState(MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().getKeyCode(), true);
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "GameSettings", true)) {
                MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().setPressed(true);
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "Mouse", true)) {
                (new Robot()).mousePress(4096);
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "Packet", true)) {
                iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                itemStack$iv = ientityplayersp.getInventory().getCurrentItemInHand();
                hand$iv = WEnumHand.MAIN_HAND;
                iinethandlerplayclient = iinethandlerplayclient1;
                $i$f$createUseItemPacket = false;
                ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand$iv);
                iinethandlerplayclient.addToSendQueue(ipacket);
                iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                itemStack$iv = ientityplayersp.getInventory().getCurrentItemInHand();
                hand$iv = WEnumHand.OFF_HAND;
                iinethandlerplayclient = iinethandlerplayclient1;
                $i$f$createUseItemPacket = false;
                ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand$iv);
                iinethandlerplayclient.addToSendQueue(ipacket);
            }
        }

        this.blockingStatus = true;
    }

    private final void stopBlocking() {
        if (this.blockingStatus) {
            if (StringsKt.equals((String) this.blockModeValue.get(), "c07c08", true)) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "UseItem", true)) {
                KeyBinding.setKeyBindState(MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().getKeyCode(), false);
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "c08", true)) {
                MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().setPressed(false);
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setItemInUseCount(0);
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "GameSettings", true)) {
                MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().setPressed(false);
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "Mouse", true)) {
                (new Robot()).mouseRelease(4096);
            }

            if (StringsKt.equals((String) this.blockModeValue.get(), "Packet", true)) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
            }

            this.blockingStatus = false;
        }

    }

    private final boolean getCancelRun() {
        byte $i$f$getCancelRun = 0;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag;

        if (!ientityplayersp.isSpectator()) {
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            if (access$isAlive(this, (IEntityLivingBase) ientityplayersp1) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                flag = false;
                return flag;
            }
        }

        flag = true;
        return flag;
    }

    private final boolean isAlive(IEntityLivingBase entity) {
        return entity.isEntityAlive() && entity.getHealth() > (float) 0 || ((Boolean) this.aacValue.get()).booleanValue() && entity.getHurtTime() > 5;
    }

    private final boolean getCanBlock() {
        byte $i$f$getCanBlock = 0;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag;

        if (ientityplayersp.getHeldItem() != null) {
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            IItemStack iitemstack = ientityplayersp1.getHeldItem();

            if (iitemstack == null) {
                Intrinsics.throwNpe();
            }

            if (iclassprovider.isItemSword(iitemstack.getItem())) {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    private final float getMaxRange() {
        float f = ((Number) this.rangeValue.get()).floatValue();
        float f1 = ((Number) this.BlockRangeValue.get()).floatValue();
        boolean flag = false;

        return Math.max(f, f1);
    }

    private final float getRange(IEntity entity) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        float f = PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, entity) >= ((Number) this.BlockRangeValue.get()).doubleValue() ? ((Number) this.rangeValue.get()).floatValue() : ((Number) this.rangeValue.get()).floatValue();
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        return f - (ientityplayersp1.getSprinting() ? ((Number) this.rangeSprintReducementValue.get()).floatValue() : 0.0F);
    }

    @Nullable
    public String getTag() {
        return "Fix";
    }

    public final boolean isBlockingChestAura() {
        return this.getState() && this.target != null;
    }

    public KillAura4() {
        boolean flag = false;
        List list = (List) (new ArrayList());

        this.discoveredTargets = list;
        flag = false;
        list = (List) (new ArrayList());
        this.prevTargetEntities = list;
        this.attackTimer = new MSTimer();
        this.markTimer = new MSTimer();
        this.containerOpen = -1L;
        this.displayText = "";
        this.isUp = true;
    }

    public static final boolean access$isAlive(KillAura4 $this, IEntityLivingBase entity) {
        return $this.isAlive(entity);
    }

    public static final long access$getAttackDelay$p(KillAura4 $this) {
        return $this.attackDelay;
    }

    public static final int getKillCounts() {
        KillAura4.Companion killaura4_companion = KillAura4.Companion;

        return KillAura4.killCounts;
    }

    public static final void setKillCounts(int <set-?>) {
        KillAura4.Companion killaura4_companion = KillAura4.Companion;

        KillAura4.killCounts = <set-?>;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura4$Companion;", "", "()V", "killCounts", "", "killCounts$annotations", "getKillCounts", "()I", "setKillCounts", "(I)V", "LiquidBounce"}
    )
    public static final class Companion {

        /** @deprecated */
        @JvmStatic
        public static void killCounts$annotations() {}

        public final int getKillCounts() {
            return KillAura4.killCounts;
        }

        public final void setKillCounts(int <set-?>) {
            KillAura4.killCounts = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
