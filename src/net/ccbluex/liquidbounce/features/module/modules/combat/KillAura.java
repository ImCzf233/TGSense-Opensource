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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import me.utils.player.PlayerUtil;
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
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
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
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.Gident;
import net.ccbluex.liquidbounce.features.module.modules.color.Rainbow;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.render.OldHitting;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@ModuleInfo(
    name = "KillAura",
    description = "Automatically attacks targets around you.",
    category = ModuleCategory.COMBAT,
    keyBind = 19
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010!\n\u0002\b\u001f\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u0000 ¨\u00012\u00020\u0001:\u0002¨\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0083\u0001\u001a\u00030\u0084\u00012\u0006\u00109\u001a\u000203H\u0002J\t\u0010\u0085\u0001\u001a\u00020\u001bH\u0002J%\u0010\u0086\u0001\u001a\u00030\u0084\u00012\u0006\u00109\u001a\u0002032\u0007\u0010\u0087\u0001\u001a\u00020\u00152\b\u0010\u0088\u0001\u001a\u00030\u0089\u0001H\u0002J$\u0010\u008a\u0001\u001a\u00030\u0084\u00012\u0006\u00109\u001a\u0002032\u0007\u0010\u008b\u0001\u001a\u00020\t2\u0007\u0010\u008c\u0001\u001a\u00020\tH\u0002J\u0012\u0010\u008d\u0001\u001a\u00020\t2\u0007\u00109\u001a\u00030\u008e\u0001H\u0002J\u0011\u0010\u008f\u0001\u001a\u00020\u001b2\u0006\u00109\u001a\u000203H\u0002J\u0014\u0010\u0090\u0001\u001a\u00020\u001b2\t\u00109\u001a\u0005\u0018\u00010\u008e\u0001H\u0002J\n\u0010\u0091\u0001\u001a\u00030\u0084\u0001H\u0016J\n\u0010\u0092\u0001\u001a\u00030\u0084\u0001H\u0016J\u0014\u0010\u0093\u0001\u001a\u00030\u0084\u00012\b\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0007J\u0014\u0010\u0096\u0001\u001a\u00030\u0084\u00012\b\u0010\u0094\u0001\u001a\u00030\u0097\u0001H\u0007J\u0014\u0010\u0098\u0001\u001a\u00030\u0084\u00012\b\u0010\u0094\u0001\u001a\u00030\u0089\u0001H\u0007J\u0014\u0010\u0099\u0001\u001a\u00030\u0084\u00012\b\u0010\u0094\u0001\u001a\u00030\u009a\u0001H\u0007J\u0016\u0010\u009b\u0001\u001a\u00030\u0084\u00012\n\u0010\u0094\u0001\u001a\u0005\u0018\u00010\u009c\u0001H\u0007J\u0014\u0010\u009d\u0001\u001a\u00030\u0084\u00012\b\u0010\u0094\u0001\u001a\u00030\u009e\u0001H\u0007J\n\u0010\u009f\u0001\u001a\u00030\u0084\u0001H\u0002J\u001d\u0010 \u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030\u008e\u00012\u0007\u0010¢\u0001\u001a\u00020\u001bH\u0002J\n\u0010£\u0001\u001a\u00030\u0084\u0001H\u0002J\b\u0010¤\u0001\u001a\u00030\u0084\u0001J\n\u0010¥\u0001\u001a\u00030\u0084\u0001H\u0002J\u0012\u0010¦\u0001\u001a\u00020\u001b2\u0007\u00109\u001a\u00030\u008e\u0001H\u0002J\n\u0010§\u0001\u001a\u00030\u0084\u0001H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010!\u001a\u00020\u001b8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u001dR\u000e\u0010#\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000208X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010C\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\bC\u0010\u001dR\u000e\u0010D\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010L\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010R\u001a\u0004\u0018\u00010SX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010X\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bY\u0010ZR\u000e\u0010[\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010c\u001a\b\u0012\u0004\u0012\u00020\u00150dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010e\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010f\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010h\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010i\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010j\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010k\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010t\u001a\u0004\u0018\u000103X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010v\"\u0004\bw\u0010xR\u0014\u0010y\u001a\u0002088VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bz\u0010{R\u001c\u0010|\u001a\u0004\u0018\u000103X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b}\u0010v\"\u0004\b~\u0010xR\u000e\u0010\u007f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0080\u0001\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0081\u0001\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0082\u0001\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006©\u0001"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "BlockRangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "afterAttackValue", "al", "", "attackDelay", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoBlockFacing", "autoBlockPacketValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "autoBlockValue", "bb", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "blockKey", "", "getBlockKey", "()I", "blockRate", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blockingStatus", "", "getBlockingStatus", "()Z", "setBlockingStatus", "(Z)V", "brightnessValue", "cancelRun", "getCancelRun", "circleAccuracy", "circleAlpha", "circleBlue", "circleGreen", "circleRed", "circleValue", "clicks", "colorAlphaValue", "colorBlueValue", "colorGreenValue", "colorModeValue", "colorRedValue", "colorTeam", "containerOpen", "cooldownValue", "currentTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "delayedBlockValue", "direction", "", "displayText", "", "entity", "espAnimation", "failRateValue", "fakeSharpValue", "fakeSwingValue", "fovValue", "hitable", "hitableValue", "hurtTimeValue", "interactAutoBlockValue", "isBlockingChestAura", "isUp", "jelloAlphaValue", "jelloFadeSpeedValue", "jelloGradientHeightValue", "jelloWidthValue", "keepSprintValue", "lastDeltaMS", "lastMS", "lastTarget", "lightingModeValue", "lightingSoundValue", "lightingValue", "limitedMultiTargetsValue", "livingRaycastValue", "markEntity", "Lnet/minecraft/entity/EntityLivingBase;", "markTimer", "markValue", "maxCPS", "maxPredictSize", "maxRange", "getMaxRange", "()F", "maxTurnSpeed", "minCPS", "minPredictSize", "minTurnSpeed", "noInventoryAttackValue", "noInventoryDelayValue", "outborderValue", "predictValue", "prevTargetEntities", "", "priorityValue", "progress", "randomCenterValue", "rangeSprintReducementValue", "rangeValue", "raycastIgnoredValue", "raycastValue", "rotationStrafeValue", "rotations", "saturationValue", "silentRotationValue", "stopSprintAir", "swingValue", "switchDelayValue", "switchTimer", "syncEntity", "getSyncEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "setSyncEntity", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)V", "tag", "getTag", "()Ljava/lang/String;", "target", "getTarget", "setTarget", "targetModeValue", "throughWallsRangeValue", "vanillamode", "yPos", "attackEntity", "", "canBlock", "drawESP", "color", "e", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "esp", "partialTicks", "radius", "getRange", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "isAlive", "isEnemy", "onDisable", "onEnable", "onEntityMove", "event", "Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onRender3D", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "startBlocking", "interactEntity", "interact", "stopBlocking", "update", "updateHitable", "updateRotations", "updateTarget", "Companion", "LiquidBounce"}
)
public final class KillAura extends Module {

    private final IntegerValue maxCPS = (IntegerValue) (new IntegerValue("MaxCPS", 8, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) KillAura.this.minCPS.get()).intValue();

            if (i > newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            KillAura.this.attackDelay = TimeUtils.randomClickDelay(((Number) KillAura.this.minCPS.get()).intValue(), ((Number) this.get()).intValue());
        }
    });
    private final IntegerValue minCPS = (IntegerValue) (new IntegerValue("MinCPS", 5, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) KillAura.this.maxCPS.get()).intValue();

            if (i < newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            KillAura.this.attackDelay = TimeUtils.randomClickDelay(((Number) this.get()).intValue(), ((Number) KillAura.this.maxCPS.get()).intValue());
        }
    });
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    private final FloatValue cooldownValue = new FloatValue("Cooldown", 1.0F, 0.0F, 1.0F);
    private final FloatValue rangeValue = new FloatValue("Range", 3.7F, 1.0F, 8.0F);
    private final FloatValue throughWallsRangeValue = new FloatValue("ThroughWallsRange", 3.0F, 0.0F, 8.0F);
    private final FloatValue rangeSprintReducementValue = new FloatValue("RangeSprintReducement", 0.0F, 0.0F, 0.4F);
    private final ListValue priorityValue = new ListValue("Priority", new String[] { "Health", "Distance", "Direction", "LivingTime", "HurtResitanTime"}, "Distance");
    private final ListValue targetModeValue = new ListValue("TargetMode", new String[] { "Single", "Switch", "Multi"}, "Switch");
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private final BoolValue keepSprintValue = new BoolValue("KeepSprint", true);
    private final BoolValue stopSprintAir = new BoolValue("StopSprintOnAir", true);
    private final ListValue autoBlockValue = new ListValue("AutoBlock", new String[] { "AllTime", "Range", "Off"}, "Off");
    private final FloatValue BlockRangeValue = new FloatValue("BlockRange", 3.0F, 0.0F, 8.0F);
    private final ListValue autoBlockPacketValue = new ListValue("AutoBlockPacket", new String[] { "Vanilla", "Fake", "Mouse", "GameSettings", "UseItem"}, "Simple");
    private final ListValue vanillamode = new ListValue("VanillaMode", new String[] { "TryUseItem", "UseItem", "C08", "OldC08"}, "TryUseItem");
    private final BoolValue interactAutoBlockValue = new BoolValue("InteractAutoBlock", true);
    private final BoolValue delayedBlockValue = new BoolValue("AutoBlock-AfterTck", false);
    private final BoolValue afterAttackValue = new BoolValue("AutoBlock-AfterAttack", false);
    private final BoolValue autoBlockFacing = new BoolValue("AutoBlockFacing", false);
    private final IntegerValue blockRate = new IntegerValue("BlockRate", 100, 1, 100);
    private final BoolValue raycastValue = new BoolValue("RayCast", true);
    private final BoolValue raycastIgnoredValue = new BoolValue("RayCastIgnored", false);
    private final BoolValue livingRaycastValue = new BoolValue("LivingRayCast", true);
    private final BoolValue aacValue = new BoolValue("AAC", false);
    private final FloatValue maxTurnSpeed = (FloatValue) (new FloatValue("MaxTurnSpeed", 180.0F, 0.0F, 180.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura.this.minTurnSpeed.get()).floatValue();

            if (v > newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final FloatValue minTurnSpeed = (FloatValue) (new FloatValue("MinTurnSpeed", 180.0F, 0.0F, 180.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura.this.maxTurnSpeed.get()).floatValue();

            if (v < newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final BoolValue lightingValue = new BoolValue("Lighting", false);
    private final ListValue lightingModeValue = new ListValue("Lighting-Mode", new String[] { "Dead", "Attack"}, "Dead");
    private final BoolValue lightingSoundValue = new BoolValue("Lighting-Sound", true);
    private final BoolValue randomCenterValue = new BoolValue("RandomCenter", true);
    private final ListValue rotations = new ListValue("RotationMode", new String[] { "None", "New", "Liquidbounce", "BackTrack", "Test1", "Test2", "HytRotation"}, "New");
    private final BoolValue outborderValue = new BoolValue("Outborder", false);
    private final BoolValue silentRotationValue = new BoolValue("SilentRotation", true);
    private final ListValue rotationStrafeValue = new ListValue("Strafe", new String[] { "Off", "Strict", "Silent"}, "Off");
    private final FloatValue fovValue = new FloatValue("FOV", 180.0F, 0.0F, 180.0F);
    private final BoolValue hitableValue = new BoolValue("AlwaysHitable", true);
    private final IntegerValue switchDelayValue = new IntegerValue("SwitchDelay", 300, 1, 2000);
    private final BoolValue predictValue = new BoolValue("Predict", true);
    private final FloatValue maxPredictSize = (FloatValue) (new FloatValue("MaxPredictSize", 1.0F, 0.1F, 5.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura.this.minPredictSize.get()).floatValue();

            if (v > newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final FloatValue minPredictSize = (FloatValue) (new FloatValue("MinPredictSize", 1.0F, 0.1F, 5.0F) {
        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number) KillAura.this.maxPredictSize.get()).floatValue();

            if (v < newValue) {
                this.set((Object) Float.valueOf(v));
            }

        }
    });
    private final FloatValue failRateValue = new FloatValue("FailRate", 0.0F, 0.0F, 100.0F);
    private final BoolValue fakeSwingValue = new BoolValue("FakeSwing", true);
    private final BoolValue noInventoryAttackValue = new BoolValue("NoInvAttack", false);
    private final IntegerValue noInventoryDelayValue = new IntegerValue("NoInvDelay", 200, 0, 500);
    private final IntegerValue limitedMultiTargetsValue = new IntegerValue("LimitedMultiTargets", 0, 0, 50);
    private final ListValue markValue = new ListValue("Mark", new String[] { "Liquid", "FDP", "Block", "Jello", "Plat", "Red", "Sims", "None"}, "FDP");
    private final ListValue colorModeValue = new ListValue("JelloColor", new String[] { "Custom", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Health", "Gident"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("JelloRed", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("JelloGreen", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("JelloBlue", 255, 0, 255);
    private final IntegerValue colorAlphaValue = new IntegerValue("JelloAlpha", 255, 0, 255);
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0F, 0.0F, 1.0F);
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
    private final BoolValue colorTeam = new BoolValue("JelloTeam", false);
    private final FloatValue jelloAlphaValue = new FloatValue("JelloEndAlphaPercent", 0.4F, 0.0F, 1.0F);
    private final FloatValue jelloWidthValue = new FloatValue("JelloCircleWidth", 3.0F, 0.01F, 5.0F);
    private final FloatValue jelloGradientHeightValue = new FloatValue("JelloGradientHeight", 3.0F, 1.0F, 8.0F);
    private final FloatValue jelloFadeSpeedValue = new FloatValue("JelloFadeSpeed", 0.1F, 0.01F, 0.5F);
    private final BoolValue fakeSharpValue = new BoolValue("FakeSharp", true);
    private final BoolValue circleValue = new BoolValue("Circle", true);
    private final IntegerValue circleRed = new IntegerValue("CircleRed", 255, 0, 255);
    private final IntegerValue circleGreen = new IntegerValue("CircleGreen", 255, 0, 255);
    private final IntegerValue circleBlue = new IntegerValue("CircleBlue", 255, 0, 255);
    private final IntegerValue circleAlpha = new IntegerValue("CircleAlpha", 255, 0, 255);
    private final IntegerValue circleAccuracy = new IntegerValue("CircleAccuracy", 15, 0, 60);
    private final int blockKey;
    private final MSTimer switchTimer;
    @Nullable
    private IEntityLivingBase target;
    private IEntityLivingBase currentTarget;
    private boolean hitable;
    private final List prevTargetEntities;
    private IEntityLivingBase lastTarget;
    private double direction;
    private double yPos;
    private double progress;
    private long lastMS;
    private long lastDeltaMS;
    private float al;
    private final MSTimer attackTimer;
    private long attackDelay;
    private int clicks;
    private EntityLivingBase markEntity;
    private final MSTimer markTimer;
    private long containerOpen;
    private String displayText;
    private IAxisAlignedBB bb;
    private IEntityLivingBase entity;
    private boolean blockingStatus;
    private double espAnimation;
    private boolean isUp;
    @Nullable
    private IEntityLivingBase syncEntity;
    private static int killCounts;
    public static final KillAura.Companion Companion = new KillAura.Companion((DefaultConstructorMarker) null);

    public final int getBlockKey() {
        return this.blockKey;
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
        if (((Boolean) this.stopSprintAir.get()).booleanValue()) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getOnGround()) {
                this.keepSprintValue.set(Boolean.valueOf(true));
            } else {
                this.keepSprintValue.set(Boolean.valueOf(false));
            }
        }

        if (event.getEventState() == EventState.POST) {
            if (this.target != null) {
                if (this.currentTarget != null) {
                    this.updateHitable();
                    if (!StringsKt.equals((String) this.autoBlockValue.get(), "off", true) && ((Boolean) this.delayedBlockValue.get()).booleanValue() && this.canBlock()) {
                        IEntityLivingBase ientitylivingbase = this.currentTarget;

                        if (this.currentTarget == null) {
                            Intrinsics.throwNpe();
                        }

                        this.startBlocking((IEntity) ientitylivingbase, ((Boolean) this.interactAutoBlockValue.get()).booleanValue());
                    }

                }
            }
        } else {
            if (StringsKt.equals((String) this.rotationStrafeValue.get(), "Off", true)) {
                this.update();
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
    public final void onTick(@Nullable TickEvent event) {
        if (StringsKt.equals((String) this.markValue.get(), "jello", true)) {
            this.al = AnimationUtils.changer(this.al, this.target != null ? ((Number) this.jelloFadeSpeedValue.get()).floatValue() : -((Number) this.jelloFadeSpeedValue.get()).floatValue(), 0.0F, ((Number) this.colorAlphaValue.get()).floatValue() / 255.0F);
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
                ++KillAura.killCounts;
                int i = KillAura.killCounts;

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
        <undefinedtype> $fun$post3D$1 = null.INSTANCE;
        <undefinedtype> $fun$drawCircle$2 = null.INSTANCE;
        <undefinedtype> $fun$pre3D$3 = null.INSTANCE;
        Function1 $fun$getColor$4 = new Function1(1) {
            @Nullable
            public final Color invoke(@Nullable IEntityLivingBase ent) {
                int[] counter = new int[] { 0};

                if (ent instanceof EntityLivingBase) {
                    if (StringsKt.equals((String) KillAura.this.colorModeValue.get(), "Health", true)) {
                        return BlendUtils.getHealthColor(ent.getHealth(), ent.getMaxHealth());
                    }

                    if (((Boolean) KillAura.this.colorTeam.get()).booleanValue()) {
                        IIChatComponent iichatcomponent = ent.getDisplayName();

                        if (iichatcomponent == null) {
                            Intrinsics.throwNpe();
                        }

                        String color = iichatcomponent.getFormattedText();
                        boolean i = false;

                        if (color == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        char[] achar = color.toCharArray();

                        Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
                        char[] chars = achar;
                        int i = Integer.MAX_VALUE;
                        int j = 0;

                        for (int k = chars.length; j < k; ++j) {
                            if (chars[j] == 167 && j + 1 < chars.length) {
                                int index = GameFontRenderer.Companion.getColorIndex(chars[j + 1]);

                                if (index >= 0 && index <= 15) {
                                    i = ColorUtils.hexColors[index];
                                    break;
                                }
                            }
                        }

                        return new Color(i);
                    }
                }

                String entityLivingBase = (String) KillAura.this.colorModeValue.get();
                Color color;

                switch (entityLivingBase.hashCode()) {
                case -1656737386:
                    if (entityLivingBase.equals("Rainbow")) {
                        color = ColorUtils.hslRainbow$default(ColorUtils.INSTANCE, counter[0] * 100 + 1, 0.0F, 0.0F, 100 * ((Number) Rainbow.rainbowSpeed.get()).intValue(), 0, 22, (Object) null);
                        return color;
                    }
                    break;

                case -884013110:
                    if (entityLivingBase.equals("LiquidSlowly")) {
                        color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, ((Number) KillAura.this.saturationValue.get()).floatValue(), ((Number) KillAura.this.brightnessValue.get()).floatValue());
                        return color;
                    }
                    break;

                case 2029746065:
                    if (entityLivingBase.equals("Custom")) {
                        color = new Color(((Number) KillAura.this.colorRedValue.get()).intValue(), ((Number) KillAura.this.colorGreenValue.get()).intValue(), ((Number) KillAura.this.colorBlueValue.get()).intValue());
                        return color;
                    }
                    break;

                case 2132719113:
                    if (entityLivingBase.equals("Gident")) {
                        color = RenderUtils.getGradientOffset(new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue()), new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue()), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Gident.gidentspeed.get()).intValue()));
                        return color;
                    }
                }

                color = ColorUtils.fade(new Color(((Number) KillAura.this.colorRedValue.get()).intValue(), ((Number) KillAura.this.colorGreenValue.get()).intValue(), ((Number) KillAura.this.colorBlueValue.get()).intValue()), 0, 100);
                return color;
            }
        };
        int this_$iv;
        int lastY;
        boolean flag;
        double d0;
        double d1;
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
            label424: {
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
                byte $fun$easeInOutQuart$5 = 0;
                IntProgression intprogression = RangesKt.step((IntProgression) (new IntRange($fun$easeInOutQuart$5, 360)), 61 - ((Number) this.circleAccuracy.get()).intValue());

                this_$iv = intprogression.getFirst();
                lastY = intprogression.getLast();
                int drawMode = intprogression.getStep();

                if (drawMode >= 0) {
                    if (this_$iv > lastY) {
                        break label424;
                    }
                } else if (this_$iv < lastY) {
                    break label424;
                }

                while (true) {
                    d0 = (double) this_$iv * 3.141592653589793D / 180.0D;
                    boolean bb = false;

                    f1 = (float) Math.cos(d0) * ((Number) this.rangeValue.get()).floatValue();
                    d0 = (double) this_$iv * 3.141592653589793D / 180.0D;
                    f = f1;
                    bb = false;
                    d5 = Math.sin(d0);
                    GL11.glVertex2f(f, (float) d5 * ((Number) this.rangeValue.get()).floatValue());
                    if (this_$iv == lastY) {
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
        label413: {
            if (!ientityplayersp.isSpectator()) {
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (access$isAlive(this, (IEntityLivingBase) ientityplayersp1) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                    flag2 = false;
                    break label413;
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
                double height;
                double posX;
                double posY;
                double posZ;
                double deltaY;
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
                        lastY = (int) (System.currentTimeMillis() % (long) 1500);
                        flag = lastY > 750;
                        d0 = (double) lastY / 750.0D;
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

                        height = iaxisalignedbb.getMaxX() - iaxisalignedbb.getMinX() + 0.3D;
                        posX = iaxisalignedbb.getMaxY() - iaxisalignedbb.getMinY();
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

                        posY = d1 + (d2 - ientitylivingbase2.getLastTickPosX()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosX();
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

                        posZ = d1 + (d2 - ientitylivingbase2.getLastTickPosY()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosY() + posX * d0;
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

                        deltaY = d1 + (d2 - ientitylivingbase2.getLastTickPosZ()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosZ();
                        GL11.glLineWidth((float) (height * (double) 5.0F));
                        GL11.glBegin(3);
                        int i = 0;

                        for (short short0 = 360; i <= short0; ++i) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            d1 = (double) ientityplayersp.getTicksExisted() / 70.0D;
                            double d7 = (double) i / 50.0D * 1.75D;
                            double d8 = d1;
                            boolean flag3 = false;
                            double d9 = Math.sin(d7);
                            int j = Color.HSBtoRGB((float) (d8 + d9) % 1.0F, 0.7F, 1.0F);
                            Color color = new Color(j);

                            GL11.glColor3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F);
                            d7 = (double) i * 6.283185307179586D / 45.0D;
                            flag3 = false;
                            d9 = Math.cos(d7);
                            d1 = posY + height * d9;
                            d7 = (double) i * 6.283185307179586D / 45.0D;
                            double d10 = d1;

                            flag3 = false;
                            double d11 = Math.sin(d7);

                            GL11.glVertex3d(d10, posZ, deltaY + height * d11);
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
                        double d12 = this.yPos;
                        <undefinedtype> 0 = null.INSTANCE;

                        if (this.al > 0.0F) {
                            if (System.currentTimeMillis() - this.lastMS >= 1000L) {
                                this.direction = -this.direction;
                                this.lastMS = System.currentTimeMillis();
                            }

                            long radius = this.direction > (double) 0 ? System.currentTimeMillis() - this.lastMS : 1000L - (System.currentTimeMillis() - this.lastMS);

                            this.progress = (double) radius / 1000.0D;
                            this.lastDeltaMS = System.currentTimeMillis() - this.lastMS;
                        } else {
                            this.lastMS = System.currentTimeMillis() - this.lastDeltaMS;
                        }

                        if (this.target != null) {
                            this.entity = this.target;
                            ientitylivingbase1 = this.entity;
                            if (this.entity == null) {
                                Intrinsics.throwNpe();
                            }

                            this.bb = ientitylivingbase1.getEntityBoundingBox();
                        }

                        if (this.bb == null || this.entity == null) {
                            return;
                        }

                        IAxisAlignedBB iaxisalignedbb2 = this.bb;

                        if (this.bb == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = iaxisalignedbb2.getMaxX();
                        IAxisAlignedBB iaxisalignedbb3 = this.bb;

                        if (this.bb == null) {
                            Intrinsics.throwNpe();
                        }

                        double d13 = d1 - iaxisalignedbb3.getMinX();

                        iaxisalignedbb2 = this.bb;
                        if (this.bb == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = iaxisalignedbb2.getMaxY();
                        iaxisalignedbb3 = this.bb;
                        if (this.bb == null) {
                            Intrinsics.throwNpe();
                        }

                        height = d1 - iaxisalignedbb3.getMinY();
                        ientitylivingbase = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosX();
                        ientitylivingbase1 = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosX();
                        ientitylivingbase2 = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        posX = d1 + (d2 - ientitylivingbase2.getLastTickPosX()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks();
                        ientitylivingbase = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosY();
                        ientitylivingbase1 = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosY();
                        ientitylivingbase2 = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        posY = d1 + (d2 - ientitylivingbase2.getLastTickPosY()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks();
                        ientitylivingbase = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getLastTickPosZ();
                        ientitylivingbase1 = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = ientitylivingbase1.getPosZ();
                        ientitylivingbase2 = this.entity;
                        if (this.entity == null) {
                            Intrinsics.throwNpe();
                        }

                        posZ = d1 + (d2 - ientitylivingbase2.getLastTickPosZ()) * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks();
                        this.yPos = 0.invoke(this.progress) * height;
                        deltaY = (this.direction > (double) 0 ? this.yPos - d12 : d12 - this.yPos) * -this.direction * ((Number) this.jelloGradientHeightValue.get()).doubleValue();
                        if (this.al <= (float) 0 && this.entity != null) {
                            this.entity = (IEntityLivingBase) null;
                            return;
                        }

                        Color colour = $fun$getColor$4.invoke(this.entity);

                        if (colour == null) {
                            Intrinsics.throwNpe();
                        }

                        float r = (float) colour.getRed() / 255.0F;
                        float g = (float) colour.getGreen() / 255.0F;
                        float b = (float) colour.getBlue() / 255.0F;

                        $fun$pre3D$3.invoke();
                        GL11.glTranslated(-MinecraftInstance.mc.getRenderManager().getViewerPosX(), -MinecraftInstance.mc.getRenderManager().getViewerPosY(), -MinecraftInstance.mc.getRenderManager().getViewerPosZ());
                        GL11.glBegin(8);
                        int i = 0;

                        for (short short1 = 360; i <= short1; ++i) {
                            double calc = (double) i * 3.141592653589793D / (double) 180;
                            double posX2 = posX - Math.sin(calc) * d13;
                            double posZ2 = posZ + Math.cos(calc) * d13;

                            GL11.glColor4f(r, g, b, 0.0F);
                            GL11.glVertex3d(posX2, posY + this.yPos + deltaY, posZ2);
                            GL11.glColor4f(r, g, b, this.al * ((Number) this.jelloAlphaValue.get()).floatValue());
                            GL11.glVertex3d(posX2, posY + this.yPos, posZ2);
                        }

                        GL11.glEnd();
                        $fun$drawCircle$2.invoke(posX, posY + this.yPos, posZ, ((Number) this.jelloWidthValue.get()).floatValue(), d13, r, g, b, this.al);
                        $fun$post3D$1.invoke();
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

                            List list;
                            int i;

                            if (StringsKt.equals((String) this.targetModeValue.get(), "Switch", true)) {
                                if (this.switchTimer.hasTimePassed((long) ((Number) this.switchDelayValue.get()).intValue())) {
                                    list = this.prevTargetEntities;
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
                                    this.switchTimer.reset();
                                }
                            } else {
                                list = this.prevTargetEntities;
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
                            }

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
                        comparator = (Comparator) (new KillAura$updateTarget$$inlined$sortBy$2());
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case -962590849:
                if (entity1.equals("direction")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura$updateTarget$$inlined$sortBy$3());
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case 288459765:
                if (entity1.equals("distance")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura$updateTarget$$inlined$sortBy$1(thePlayer));
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case 525193846:
                if (entity1.equals("HurtResitanTime")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura$updateTarget$$inlined$sortBy$5());
                        CollectionsKt.sortWith(targets, comparator);
                    }
                }
                break;

            case 886905078:
                if (entity1.equals("livingtime")) {
                    $i$f$sortBy1 = false;
                    if (targets.size() > 1) {
                        entityFov1 = false;
                        comparator = (Comparator) (new KillAura$updateTarget$$inlined$sortBy$4());
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

        if (!StringsKt.equals((String) this.autoBlockPacketValue.get(), "Vanilla", true)) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.isBlocking() || this.blockingStatus) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                if (((Boolean) this.afterAttackValue.get()).booleanValue()) {
                    this.blockingStatus = false;
                }
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
            int i = 0;

            for (byte b0 = 2; i <= b0; ++i) {
                IEntityLivingBase ientitylivingbase;

                if (thePlayer.getFallDistance() > 0.0F && !thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) && thePlayer.getRidingEntity() == null || criticals.getState() && criticals.getMsTimer().hasTimePassed((long) ((Number) criticals.getDelayValue().get()).intValue()) && !thePlayer.isInWater() && !thePlayer.isInLava() && !thePlayer.isInWeb()) {
                    ientitylivingbase = this.target;
                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    thePlayer.onCriticalHit((IEntity) ientitylivingbase);
                }

                IExtractedFunctions iextractedfunctions = MinecraftInstance.functions;
                IItemStack iitemstack = thePlayer.getHeldItem();
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

            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.isBlocking() || !StringsKt.equals((String) this.autoBlockValue.get(), "off", true) && this.canBlock()) {
                if (((Boolean) this.delayedBlockValue.get()).booleanValue()) {
                    return;
                }

                if (((Number) this.blockRate.get()).intValue() <= 0 || (new Random()).nextInt(100) > ((Number) this.blockRate.get()).intValue()) {
                    return;
                }

                this.startBlocking((IEntity) entity, ((Boolean) this.interactAutoBlockValue.get()).booleanValue());
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

        if (StringsKt.equals((String) this.rotations.get(), "test1", true)) {
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
                    rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, RotationUtils.getNCPRotations(RotationUtils.getCenter(entity.getEntityBoundingBox()), false), (float) (Math.random() * (double) (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue()) + ((Number) this.minTurnSpeed.get()).doubleValue()));
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
        } else if (StringsKt.equals((String) this.rotations.get(), "test2", true)) {
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
                    rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, RotationUtils.toRotation(RotationUtils.getCenter(entity.getEntityBoundingBox()), false), (float) (Math.random() * (double) (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue()) + ((Number) this.minTurnSpeed.get()).doubleValue()));
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
        } else {
            Rotation limitedRotation2;

            if (StringsKt.equals((String) this.rotations.get(), "HytRotation", true)) {
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

                vecrotation = RotationUtils.lockView(boundingBox, flag, flag1, flag2, PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp1, entity) < ((Number) this.throughWallsRangeValue.get()).doubleValue(), this.getMaxRange());
                if (vecrotation != null) {
                    VecRotation rotation1 = vecrotation;

                    limitedRotation2 = rotation1.component2();
                    rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, limitedRotation2, (float) (Math.random() * (double) (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue()) + ((Number) this.minTurnSpeed.get()).doubleValue()));
                    Intrinsics.checkExpressionValueIsNotNull(rotation, "RotationUtils.limitAngle…rnSpeed.get()).toFloat())");
                    rotation = rotation;
                    if (((Boolean) this.silentRotationValue.get()).booleanValue()) {
                        RotationUtils.setTargetRotation(rotation, ((Boolean) this.aacValue.get()).booleanValue() ? 15 : 0);
                    } else {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        rotation.toPlayer((IEntityPlayer) ientityplayersp);
                    }

                    return true;
                } else {
                    return false;
                }
            } else if (StringsKt.equals((String) this.rotations.get(), "New", true)) {
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
                        rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, net.ccbluex.liquidbounce.utils.render.RotationUtils.getNewRotations(RotationUtils.getCenter(entity.getEntityBoundingBox()), false), (float) (Math.random() * (double) (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue()) + ((Number) this.minTurnSpeed.get()).doubleValue()));
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
                    limitedRotation2 = rotation;
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

                        if (!((Boolean) KillAura.this.livingRaycastValue.get()).booleanValue() || MinecraftInstance.classProvider.isEntityLivingBase(entity) && !MinecraftInstance.classProvider.isEntityArmorStand(entity)) {
                            label56: {
                                if (!KillAura.this.isEnemy(entity) && !((Boolean) KillAura.this.raycastIgnoredValue.get()).booleanValue()) {
                                    if (!((Boolean) KillAura.this.aacValue.get()).booleanValue()) {
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

    private final void startBlocking(IEntity interactEntity, boolean interact) {
        if (LiquidBounce.INSTANCE.getModuleManager().get(OldHitting.class).getState()) {
            if (this.autoBlockValue.equals("Range")) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, interactEntity) > ((Number) this.BlockRangeValue.get()).doubleValue()) {
                    return;
                }
            }

            if (this.blockingStatus) {
                return;
            }

            if (interact) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketUseEntity(interactEntity, interactEntity.getPositionVector()));
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketUseEntity(interactEntity, ICPacketUseEntity.WAction.INTERACT));
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "UseItem", true)) {
                KeyBinding.setKeyBindState(MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().getKeyCode(), true);
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "GameSettings", true)) {
                MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().setPressed(true);
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "Mouse", true)) {
                (new Robot()).mousePress(4096);
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "Vanilla", true)) {
                if (StringsKt.equals((String) this.vanillamode.get(), "TryUseItem", true)) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.MAIN_HAND));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.OFF_HAND));
                }

                IINetHandlerPlayClient iinethandlerplayclient;

                if (StringsKt.equals((String) this.vanillamode.get(), "UseItem", true)) {
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    IItemStack itemStack$iv = ientityplayersp1.getInventory().getCurrentItemInHand();
                    WEnumHand hand$iv = WEnumHand.MAIN_HAND;
                    IINetHandlerPlayClient iinethandlerplayclient1 = iinethandlerplayclient;
                    boolean $i$f$createUseItemPacket = false;
                    IPacket ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand$iv);

                    iinethandlerplayclient1.addToSendQueue(ipacket);
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    itemStack$iv = ientityplayersp1.getInventory().getCurrentItemInHand();
                    hand$iv = WEnumHand.OFF_HAND;
                    iinethandlerplayclient1 = iinethandlerplayclient;
                    $i$f$createUseItemPacket = false;
                    ipacket = (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand$iv);
                    iinethandlerplayclient1.addToSendQueue(ipacket);
                }

                if (StringsKt.equals((String) this.vanillamode.get(), "OldC08", true)) {
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    IClassProvider iclassprovider = MinecraftInstance.classProvider;
                    WBlockPos wblockpos = new WBlockPos(-0.5534147541D, -0.5534147541D, -0.5534147541D);
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerBlockPlacement(wblockpos, 255, ientityplayersp2.getInventory().getCurrentItemInHand(), 0.0F, 0.0F, 0.0F));
                }
            }

            if (StringsKt.equals((String) this.vanillamode.get(), "C08", true)) {
                Minecraft minecraft = MinecraftInstance.mc2;

                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2, "mc2");
                NetHandlerPlayClient nethandlerplayclient = minecraft.getConnection();

                if (nethandlerplayclient == null) {
                    Intrinsics.throwNpe();
                }

                nethandlerplayclient.sendPacket((Packet) (new CPacketPlayerTryUseItemOnBlock(new BlockPos(-0.5534147541D, -0.5534147541D, -0.5534147541D), EnumFacing.WEST, EnumHand.OFF_HAND, 0.0F, 0.0F, 0.0F)));
                minecraft = MinecraftInstance.mc2;
                Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2, "mc2");
                nethandlerplayclient = minecraft.getConnection();
                if (nethandlerplayclient == null) {
                    Intrinsics.throwNpe();
                }

                nethandlerplayclient.sendPacket((Packet) (new CPacketPlayerTryUseItemOnBlock(new BlockPos(-0.5534147541D, -0.5534147541D, -0.5534147541D), EnumFacing.WEST, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F)));
            }
        }

        this.blockingStatus = true;
    }

    private final void stopBlocking() {
        if (this.blockingStatus) {
            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "Vanilla", true)) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, PlayerUtil.isMoving() ? new WBlockPos(-1, -1, -1) : WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "UseItem", true)) {
                KeyBinding.setKeyBindState(MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().getKeyCode(), false);
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "GameSettings", true)) {
                MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().setPressed(false);
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "Mouse", true)) {
                (new Robot()).mouseRelease(4096);
            }

            if (StringsKt.equals((String) this.autoBlockPacketValue.get(), "Vanilla", true)) {
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

    private final boolean canBlock() {
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
                if (((Boolean) this.autoBlockFacing.get()).booleanValue()) {
                    IEntityLivingBase ientitylivingbase = this.target;

                    if (this.target == null) {
                        Intrinsics.throwNpe();
                    }

                    IEntity ientity = (IEntity) ientitylivingbase;

                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (PlayerExtensionKt.getDistanceToEntityBox(ientity, (IEntity) ientityplayersp1) < (double) this.getMaxRange()) {
                        ientitylivingbase = this.target;
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }

                        IMovingObjectPosition imovingobjectposition = ientitylivingbase.rayTrace((double) this.getMaxRange(), 1.0F);

                        if (imovingobjectposition == null) {
                            Intrinsics.throwNpe();
                        }

                        flag = imovingobjectposition.getTypeOfHit() != IMovingObjectPosition.WMovingObjectType.MISS;
                        return flag;
                    }
                }

                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    private final float getMaxRange() {
        float f = ((Number) this.rangeValue.get()).floatValue();
        float f1 = ((Number) this.throughWallsRangeValue.get()).floatValue();
        boolean flag = false;

        return Math.max(f, f1);
    }

    private final float getRange(IEntity entity) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        float f = PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, entity) >= ((Number) this.throughWallsRangeValue.get()).doubleValue() ? ((Number) this.rangeValue.get()).floatValue() : ((Number) this.rangeValue.get()).floatValue();
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        return f - (ientityplayersp1.getSprinting() ? ((Number) this.rangeSprintReducementValue.get()).floatValue() : 0.0F);
    }

    @NotNull
    public String getTag() {
        return (String) this.targetModeValue.get() + "/" + (String) this.rotations.get() + "-" + ((Number) this.rangeValue.get()).floatValue();
    }

    public final boolean isBlockingChestAura() {
        return this.getState() && this.target != null;
    }

    public KillAura() {
        this.blockKey = MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().getKeyCode();
        this.switchTimer = new MSTimer();
        boolean flag = false;
        List list = (List) (new ArrayList());

        this.prevTargetEntities = list;
        this.direction = 1.0D;
        this.lastMS = System.currentTimeMillis();
        this.attackTimer = new MSTimer();
        this.markTimer = new MSTimer();
        this.containerOpen = -1L;
        this.displayText = "";
        this.isUp = true;
    }

    public static final boolean access$isAlive(KillAura $this, IEntityLivingBase entity) {
        return $this.isAlive(entity);
    }

    public static final long access$getAttackDelay$p(KillAura $this) {
        return $this.attackDelay;
    }

    public static final int getKillCounts() {
        KillAura.Companion killaura_companion = KillAura.Companion;

        return KillAura.killCounts;
    }

    public static final void setKillCounts(int <set-?>) {
        KillAura.Companion killaura_companion = KillAura.Companion;

        KillAura.killCounts = <set-?>;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura$Companion;", "", "()V", "killCounts", "", "killCounts$annotations", "getKillCounts", "()I", "setKillCounts", "(I)V", "LiquidBounce"}
    )
    public static final class Companion {

        /** @deprecated */
        @JvmStatic
        public static void killCounts$annotations() {}

        public final int getKillCounts() {
            return KillAura.killCounts;
        }

        public final void setKillCounts(int <set-?>) {
            KillAura.killCounts = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
