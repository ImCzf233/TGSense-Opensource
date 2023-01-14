package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3i;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
    name = "Fly",
    description = "Allows you to fly in survival mode.",
    category = ModuleCategory.MOVEMENT,
    keyBind = 33
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010;\u001a\u00020\tH\u0002J\b\u0010<\u001a\u00020=H\u0002J\u0010\u0010>\u001a\u00020=2\u0006\u0010?\u001a\u00020@H\u0007J\b\u0010A\u001a\u00020=H\u0016J\b\u0010B\u001a\u00020=H\u0016J\u0010\u0010C\u001a\u00020=2\u0006\u0010D\u001a\u00020EH\u0007J\u0010\u0010F\u001a\u00020=2\u0006\u0010?\u001a\u00020GH\u0007J\u0010\u0010H\u001a\u00020=2\u0006\u0010?\u001a\u00020IH\u0007J\u0010\u0010J\u001a\u00020=2\u0006\u0010?\u001a\u00020KH\u0007J\u0012\u0010L\u001a\u00020=2\b\u0010?\u001a\u0004\u0018\u00010MH\u0007J\u0010\u0010N\u001a\u00020=2\u0006\u0010D\u001a\u00020OH\u0007J\u0012\u0010P\u001a\u00020=2\b\u0010?\u001a\u0004\u0018\u00010QH\u0007J\u0010\u0010R\u001a\u00020=2\u0006\u0010S\u001a\u00020\tH\u0002J\u0010\u0010T\u001a\u00020=2\u0006\u0010S\u001a\u00020\tH\u0002J\u0010\u0010U\u001a\u00020=2\u0006\u0010V\u001a\u00020\u0004H\u0002J\u0010\u0010W\u001a\u00020=2\u0006\u0010X\u001a\u00020\u0017H\u0002J\u0010\u0010Y\u001a\u00020=2\u0006\u0010X\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010(\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00104\u001a\u0002058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u000e\u00108\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006Z"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Fly;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aac3delay", "", "aac3glideDelay", "aacFast", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "aacJump", "", "aacMotion", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacMotion2", "aacSpeedValue", "boostHypixelState", "cubecraft2TickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "cubecraftTeleportTickTimer", "failedStart", "", "flyTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "freeHypixelPitch", "", "freeHypixelTimer", "freeHypixelYaw", "groundTimer", "hypixelBoost", "hypixelBoostDelay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hypixelBoostTimer", "hypixelTimer", "hytStartY", "lastDistance", "markValue", "mineSecureVClipTimer", "mineplexSpeedValue", "mineplexTimer", "minesuchtTP", "", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "moveSpeed", "ncpMotionValue", "neruxVaceTicks", "noFlag", "noPacketModify", "redeskyHeight", "spartanTimer", "startY", "tag", "", "getTag", "()Ljava/lang/String;", "vanillaKickBypassValue", "vanillaSpeedValue", "wasDead", "calculateGround", "handleVanillaKickBypass", "", "onBB", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "e", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "redeskyHClip1", "horizontal", "redeskyHClip2", "redeskySpeed", "speed", "redeskyVClip1", "vertical", "redeskyVClip2", "LiquidBounce"}
)
public final class Fly extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Vanilla", "SmoothVanilla", "NCP", "OldNCP", "AAC1.9.10", "AAC3.0.5", "AAC3.1.6-Gomme", "AAC3.3.12", "AAC3.3.12-Glide", "AAC3.3.13", "CubeCraft", "Hypixel", "BoostHypixel", "FreeHypixel", "Rewinside", "TeleportRewinside", "Mineplex", "NeruxVace", "Minesucht", "Redesky", "Spartan", "Spartan2", "BugSpartan", "HYT4v4Test", "MineSecure", "HawkEye", "HAC", "WatchCat", "Jetpack", "KeepAlive", "Flag"}, "Vanilla");
    private final FloatValue vanillaSpeedValue = new FloatValue("VanillaSpeed", 2.0F, 0.0F, 5.0F);
    private final BoolValue vanillaKickBypassValue = new BoolValue("VanillaKickBypass", false);
    private final FloatValue ncpMotionValue = new FloatValue("NCPMotion", 0.0F, 0.0F, 1.0F);
    private final FloatValue aacSpeedValue = new FloatValue("AAC1.9.10-Speed", 0.3F, 0.0F, 1.0F);
    private final BoolValue aacFast = new BoolValue("AAC3.0.5-Fast", true);
    private final FloatValue aacMotion = new FloatValue("AAC3.3.12-Motion", 10.0F, 0.1F, 10.0F);
    private final FloatValue aacMotion2 = new FloatValue("AAC3.3.13-Motion", 10.0F, 0.1F, 10.0F);
    private final BoolValue hypixelBoost = new BoolValue("Hypixel-Boost", true);
    private final IntegerValue hypixelBoostDelay = new IntegerValue("Hypixel-BoostDelay", 1200, 0, 2000);
    private final FloatValue hypixelBoostTimer = new FloatValue("Hypixel-BoostTimer", 1.0F, 0.0F, 5.0F);
    private final FloatValue mineplexSpeedValue = new FloatValue("MineplexSpeed", 1.0F, 0.5F, 10.0F);
    private final IntegerValue neruxVaceTicks = new IntegerValue("NeruxVace-Ticks", 6, 0, 20);
    private final FloatValue redeskyHeight = new FloatValue("Redesky-Height", 4.0F, 1.0F, 7.0F);
    private final BoolValue markValue = new BoolValue("Mark", true);
    private double startY;
    private final MSTimer flyTimer = new MSTimer();
    private final MSTimer groundTimer = new MSTimer();
    private boolean noPacketModify;
    private double aacJump;
    private int aac3delay;
    private int aac3glideDelay;
    private boolean noFlag;
    private final MSTimer mineSecureVClipTimer = new MSTimer();
    private final TickTimer spartanTimer = new TickTimer();
    private long minesuchtTP;
    private final MSTimer mineplexTimer = new MSTimer();
    private boolean wasDead;
    private final TickTimer hypixelTimer = new TickTimer();
    private int boostHypixelState = 1;
    private double moveSpeed;
    private double lastDistance;
    private boolean failedStart;
    private final TickTimer cubecraft2TickTimer = new TickTimer();
    private final TickTimer cubecraftTeleportTickTimer = new TickTimer();
    private final TickTimer freeHypixelTimer = new TickTimer();
    private float freeHypixelYaw;
    private float freeHypixelPitch;
    private double hytStartY;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    public void onEnable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            this.flyTimer.reset();
            this.noPacketModify = true;
            double x = thePlayer.getPosX();
            double y = thePlayer.getPosY();
            double z = thePlayer.getPosZ();
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            this.hytStartY = ientityplayersp1.getPosY();
            String mode = (String) this.modeValue.get();
            boolean flag = false;
            boolean flag1 = false;
            Fly $this$run = (Fly) this;
            boolean $i$a$-run-Fly$onEnable$1 = false;
            boolean i = false;

            if (mode == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s = mode.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                String s1 = s;
                byte b0;
                int i;

                switch (s1.hashCode()) {
                case -1693125473:
                    if (s1.equals("bugspartan")) {
                        i = 0;

                        for (b0 = 64; i <= b0; ++i) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.049D, z, false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, false));
                        }

                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.1D, z, true));
                        thePlayer.setMotionX(thePlayer.getMotionX() * 0.1D);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.1D);
                        thePlayer.swingItem();
                    }
                    break;

                case -1014303276:
                    if (s1.equals("oldncp") && thePlayer.getOnGround()) {
                        i = 0;

                        for (b0 = 3; i <= b0; ++i) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.01D, z, false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, false));
                        }

                        thePlayer.jump();
                        thePlayer.swingItem();
                    }
                    break;

                case -926713373:
                    if (s1.equals("infinitycubecraft")) {
                        ClientUtils.displayChatMessage("§8[§c§lCubeCraft-§a§lFly§8] §aPlace a block before landing.");
                    }
                    break;

                case 108891:
                    if (s1.equals("ncp") && thePlayer.getOnGround()) {
                        i = 0;

                        for (b0 = 64; i <= b0; ++i) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.049D, z, false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, false));
                        }

                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.1D, z, true));
                        thePlayer.setMotionX(thePlayer.getMotionX() * 0.1D);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.1D);
                        thePlayer.swingItem();
                    }
                    break;

                case 502330237:
                    if (s1.equals("infinityvcubecraft")) {
                        ClientUtils.displayChatMessage("§8[§c§lCubeCraft-§a§lFly§8] §aPlace a block before landing.");
                        thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + (double) 2, thePlayer.getPosZ());
                    }
                    break;

                case 1083223725:
                    if (s1.equals("redesky")) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getOnGround()) {
                            $this$run.redeskyVClip1(((Number) $this$run.redeskyHeight.get()).floatValue());
                        }
                    }
                    break;

                case 1814517522:
                    if (s1.equals("boosthypixel") && thePlayer.getOnGround()) {
                        i = 0;

                        for (b0 = 9; i <= b0; ++i) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), true));
                        }

                        for (double fallDistance = 3.0125D; fallDistance > (double) 0; fallDistance -= 0.0624986421D) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.0624986421D, thePlayer.getPosZ(), false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.0625D, thePlayer.getPosZ(), false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.0624986421D, thePlayer.getPosZ(), false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 1.3579E-6D, thePlayer.getPosZ(), false));
                        }

                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), true));
                        thePlayer.jump();
                        thePlayer.setPosY(thePlayer.getPosY() + (double) 0.42F);
                        $this$run.boostHypixelState = 1;
                        $this$run.moveSpeed = 0.1D;
                        $this$run.lastDistance = 0.0D;
                        $this$run.failedStart = false;
                    }
                }

                this.startY = thePlayer.getPosY();
                this.aacJump = -3.8D;
                this.noPacketModify = false;
                if (StringsKt.equals(mode, "freehypixel", true)) {
                    this.freeHypixelTimer.reset();
                    thePlayer.setPositionAndUpdate(thePlayer.getPosX(), thePlayer.getPosY() + 0.42D, thePlayer.getPosZ());
                    this.freeHypixelYaw = thePlayer.getRotationYaw();
                    this.freeHypixelPitch = thePlayer.getRotationPitch();
                }

                super.onEnable();
            }
        }
    }

    public void onDisable() {
        this.wasDead = false;
        this.redeskySpeed(0);
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            this.noFlag = false;
            String mode = (String) this.modeValue.get();
            boolean flag = false;

            if (mode == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s = mode.toUpperCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toUpperCase()");
                if (!StringsKt.startsWith$default(s, "AAC", false, 2, (Object) null) && !StringsKt.equals(mode, "Hypixel", true) && !StringsKt.equals(mode, "CubeCraft", true)) {
                    thePlayer.setMotionX(0.0D);
                    thePlayer.setMotionY(0.0D);
                    thePlayer.setMotionZ(0.0D);
                }

                if (StringsKt.equals(mode, "Redesky", true)) {
                    this.redeskyHClip2(0.0D);
                }

                thePlayer.getCapabilities().setFlying(false);
                MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                thePlayer.setSpeedInAir(0.02F);
            }
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        float vanillaSpeed = ((Number) this.vanillaSpeedValue.get()).floatValue();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        boolean flag = false;
        boolean flag1 = false;
        Fly $this$run = (Fly) this;
        boolean $i$a$-run-Fly$onUpdate$1 = false;
        String s = (String) $this$run.modeValue.get();
        boolean boostDelay = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            double posX;
            boolean $i$f$add;
            double posY;
            double posZ;
            double x$iv;
            double x$iv$iv;
            double z$iv$iv;
            int i;

            switch (s.hashCode()) {
            case -2011701869:
                if (s.equals("spartan")) {
                    thePlayer.setMotionY(0.0D);
                    $this$run.spartanTimer.update();
                    if ($this$run.spartanTimer.hasTimePassed(12)) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + (double) 8, thePlayer.getPosZ(), true));
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() - (double) 8, thePlayer.getPosZ(), true));
                        $this$run.spartanTimer.reset();
                    }
                }
                break;

            case -1848285483:
                if (s.equals("teleportrewinside")) {
                    WVec3 wvec3 = new WVec3(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ());
                    float f = -thePlayer.getRotationYaw();
                    float f1 = -thePlayer.getRotationPitch();

                    x$iv = 9.9D;
                    x$iv$iv = Math.toRadians((double) f);
                    boolean flag2 = false;
                    double d0 = Math.sin(x$iv$iv);

                    x$iv$iv = Math.toRadians((double) f1);
                    flag2 = false;
                    double d1 = Math.cos(x$iv$iv);
                    double d2 = d0 * d1 * x$iv + wvec3.getXCoord();

                    x$iv$iv = Math.toRadians((double) f1);
                    d0 = d2;
                    flag2 = false;
                    d1 = Math.sin(x$iv$iv);
                    double d3 = d1 * x$iv + wvec3.getYCoord();

                    x$iv$iv = Math.toRadians((double) f);
                    d1 = d3;
                    flag2 = false;
                    double d4 = Math.cos(x$iv$iv);

                    x$iv$iv = Math.toRadians((double) f1);
                    flag2 = false;
                    double d5 = Math.cos(x$iv$iv);
                    double d6 = d4 * d5 * x$iv + wvec3.getZCoord();
                    WVec3 this_$iv$iv = new WVec3(d0, d1, d6);

                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this_$iv$iv.getXCoord(), thePlayer.getPosY() + (double) 2, this_$iv$iv.getZCoord(), true));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(wvec3.getXCoord(), thePlayer.getPosY() + (double) 2, wvec3.getZCoord(), true));
                    thePlayer.setMotionY(0.0D);
                }
                break;

            case -1745954712:
                if (s.equals("keepalive")) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketKeepAlive());
                    thePlayer.getCapabilities().setFlying(false);
                    thePlayer.setMotionY(0.0D);
                    thePlayer.setMotionX(0.0D);
                    thePlayer.setMotionZ(0.0D);
                    if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + (double) vanillaSpeed);
                    }

                    if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - (double) vanillaSpeed);
                    }

                    MovementUtils.strafe(vanillaSpeed);
                }
                break;

            case -1706751950:
                if (s.equals("jetpack") && MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                    thePlayer.setMotionY(thePlayer.getMotionY() + 0.15D);
                    thePlayer.setMotionX(thePlayer.getMotionX() * 1.1D);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.1D);
                }
                break;

            case -1693125473:
                if (s.equals("bugspartan")) {
                    thePlayer.getCapabilities().setFlying(false);
                    thePlayer.setMotionY(0.0D);
                    thePlayer.setMotionX(0.0D);
                    thePlayer.setMotionZ(0.0D);
                    if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + (double) vanillaSpeed);
                    }

                    if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - (double) vanillaSpeed);
                    }

                    MovementUtils.strafe(vanillaSpeed);
                }
                break;

            case -1362669950:
                if (s.equals("mineplex")) {
                    if (thePlayer.getInventory().getCurrentItemInHand() == null) {
                        if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown() && $this$run.mineplexTimer.hasTimePassed(100L)) {
                            thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.6D, thePlayer.getPosZ());
                            $this$run.mineplexTimer.reset();
                        }

                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.isSneaking() && $this$run.mineplexTimer.hasTimePassed(100L)) {
                            thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() - 0.6D, thePlayer.getPosZ());
                            $this$run.mineplexTimer.reset();
                        }

                        WBlockPos wblockpos = new WBlockPos;
                        double d7 = thePlayer.getPosX();
                        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        wblockpos.<init>(d7, ientityplayersp1.getEntityBoundingBox().getMinY() - (double) 1, thePlayer.getPosZ());
                        WBlockPos wblockpos1 = wblockpos;
                        WVec3 this_$iv = new WVec3((WVec3i) wblockpos1);

                        x$iv = 0.4D;
                        double y$iv1 = 0.4D;
                        double z$iv1 = 0.4D;
                        boolean $i$f$addVector = false;

                        this_$iv = new WVec3(this_$iv.getXCoord() + x$iv, this_$iv.getYCoord() + y$iv1, this_$iv.getZCoord() + z$iv1);
                        WVec3 vec$iv = new WVec3(MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP).getDirectionVec());

                        $i$f$add = false;
                        x$iv$iv = vec$iv.getXCoord();
                        double y$iv$iv = vec$iv.getYCoord();

                        z$iv$iv = vec$iv.getZCoord();
                        boolean $i$f$addVector2 = false;
                        WVec3 vec = new WVec3(this_$iv.getXCoord() + x$iv$iv, this_$iv.getYCoord() + y$iv$iv, this_$iv.getZCoord() + z$iv$iv);
                        IPlayerControllerMP iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        IItemStack iitemstack = thePlayer.getInventory().getCurrentItemInHand();

                        if (iitemstack == null) {
                            Intrinsics.throwNpe();
                        }

                        iplayercontrollermp.onPlayerRightClick(thePlayer, iworldclient, iitemstack, wblockpos1, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP), new WVec3(vec.getXCoord() * (double) 0.4F, vec.getYCoord() * (double) 0.4F, vec.getZCoord() * (double) 0.4F));
                        MovementUtils.strafe(0.27F);
                        MinecraftInstance.mc.getTimer().setTimerSpeed((float) 1 + ((Number) $this$run.mineplexSpeedValue.get()).floatValue());
                    } else {
                        MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                        $this$run.setState(false);
                        ClientUtils.displayChatMessage("§8[§c§lMineplex-§a§lFly§8] §aSelect an empty slot to fly.");
                    }
                }
                break;

            case -1031473397:
                if (s.equals("cubecraft")) {
                    MinecraftInstance.mc.getTimer().setTimerSpeed(0.6F);
                    $this$run.cubecraftTeleportTickTimer.update();
                }
                break;

            case -1014303276:
                if (s.equals("oldncp")) {
                    if ($this$run.startY > thePlayer.getPosY()) {
                        thePlayer.setMotionY(-1.0E-33D);
                    }

                    if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(-0.2D);
                    }

                    if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown() && thePlayer.getPosY() < $this$run.startY - 0.1D) {
                        thePlayer.setMotionY(0.2D);
                    }

                    MovementUtils.strafe$default(0.0F, 1, (Object) null);
                }
                break;

            case -385327063:
                if (s.equals("freehypixel")) {
                    if ($this$run.freeHypixelTimer.hasTimePassed(10)) {
                        thePlayer.getCapabilities().setFlying(true);
                    } else {
                        thePlayer.setRotationYaw($this$run.freeHypixelYaw);
                        thePlayer.setRotationPitch($this$run.freeHypixelPitch);
                        thePlayer.setMotionY(0.0D);
                        thePlayer.setMotionZ(thePlayer.getMotionY());
                        thePlayer.setMotionX(thePlayer.getMotionZ());
                        if ($this$run.startY == (new BigDecimal(thePlayer.getPosY())).setScale(3, RoundingMode.HALF_DOWN).doubleValue()) {
                            $this$run.freeHypixelTimer.update();
                        }
                    }
                }
                break;

            case -278286751:
                if (s.equals("hyt4v4test")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getPosY() <= $this$run.hytStartY) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.setOnGround(true);
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.jump();
                    }
                }
                break;

            case -321358:
                if (s.equals("aac3.3.12-glide")) {
                    if (!thePlayer.getOnGround()) {
                        i = $this$run.aac3glideDelay++;
                    }

                    if ($this$run.aac3glideDelay == 2) {
                        MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                    }

                    if ($this$run.aac3glideDelay == 12) {
                        MinecraftInstance.mc.getTimer().setTimerSpeed(0.1F);
                    }

                    if ($this$run.aac3glideDelay >= 12 && !thePlayer.getOnGround()) {
                        $this$run.aac3glideDelay = 0;
                        thePlayer.setMotionY(0.015D);
                    }
                }
                break;

            case 103050:
                if (s.equals("hac")) {
                    thePlayer.setMotionX(thePlayer.getMotionX() * 0.8D);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.8D);
                    thePlayer.setMotionY(thePlayer.getMotionY() <= -0.42D ? 0.42D : -0.42D);
                }
                break;

            case 108891:
                if (s.equals("ncp")) {
                    thePlayer.setMotionY((double) (-((Number) $this$run.ncpMotionValue.get()).floatValue()));
                    if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(-0.5D);
                    }

                    MovementUtils.strafe$default(0.0F, 1, (Object) null);
                }
                break;

            case 3145580:
                if (s.equals("flag")) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosLook(thePlayer.getPosX() + thePlayer.getMotionX() * (double) 999, thePlayer.getPosY() + (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown() ? 1.5624D : 1.0E-8D) - (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown() ? 0.0624D : 2.0E-8D), thePlayer.getPosZ() + thePlayer.getMotionZ() * (double) 999, thePlayer.getRotationYaw(), thePlayer.getRotationPitch(), true));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosLook(thePlayer.getPosX() + thePlayer.getMotionX() * (double) 999, thePlayer.getPosY() - (double) 6969, thePlayer.getPosZ() + thePlayer.getMotionZ() * (double) 999, thePlayer.getRotationYaw(), thePlayer.getRotationPitch(), true));
                    thePlayer.setPosition(thePlayer.getPosX() + thePlayer.getMotionX() * (double) 11, thePlayer.getPosY(), thePlayer.getPosZ() + thePlayer.getMotionZ() * (double) 11);
                    thePlayer.setMotionY(0.0D);
                }
                break;

            case 65876907:
                if (s.equals("aac3.1.6-gomme")) {
                    thePlayer.getCapabilities().setFlying(true);
                    if ($this$run.aac3delay == 2) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.05D);
                    } else if ($this$run.aac3delay > 2) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - 0.05D);
                        $this$run.aac3delay = 0;
                    }

                    i = $this$run.aac3delay++;
                    if (!$this$run.noFlag) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), thePlayer.getOnGround()));
                    }

                    if (thePlayer.getPosY() <= 0.0D) {
                        $this$run.noFlag = true;
                    }
                }
                break;

            case 233102203:
                if (s.equals("vanilla")) {
                    thePlayer.getCapabilities().setFlying(false);
                    thePlayer.setMotionY(0.0D);
                    thePlayer.setMotionX(0.0D);
                    thePlayer.setMotionZ(0.0D);
                    if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + (double) vanillaSpeed);
                    }

                    if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - (double) vanillaSpeed);
                    }

                    MovementUtils.strafe(vanillaSpeed);
                    $this$run.handleVanillaKickBypass();
                }
                break;

            case 238938827:
                if (s.equals("neruxvace")) {
                    if (!thePlayer.getOnGround()) {
                        i = $this$run.aac3glideDelay++;
                    }

                    if ($this$run.aac3glideDelay >= ((Number) $this$run.neruxVaceTicks.get()).intValue() && !thePlayer.getOnGround()) {
                        $this$run.aac3glideDelay = 0;
                        thePlayer.setMotionY(0.015D);
                    }
                }
                break;

            case 325225305:
                if (s.equals("aac3.0.5")) {
                    if ($this$run.aac3delay == 2) {
                        thePlayer.setMotionY(0.1D);
                    } else if ($this$run.aac3delay > 2) {
                        $this$run.aac3delay = 0;
                    }

                    if (((Boolean) $this$run.aacFast.get()).booleanValue()) {
                        if (thePlayer.getMovementInput().getMoveStrafe() == 0.0F) {
                            thePlayer.setJumpMovementFactor(0.08F);
                        } else {
                            thePlayer.setJumpMovementFactor(0.0F);
                        }
                    }

                    i = $this$run.aac3delay++;
                }
                break;

            case 518567306:
                if (s.equals("minesecure")) {
                    thePlayer.getCapabilities().setFlying(false);
                    if (!MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(-0.01D);
                    }

                    thePlayer.setMotionX(0.0D);
                    thePlayer.setMotionZ(0.0D);
                    MovementUtils.strafe(vanillaSpeed);
                    if ($this$run.mineSecureVClipTimer.hasTimePassed(150L) && MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + (double) 5, thePlayer.getPosZ(), false));
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(0.5D, -1000.0D, 0.5D, false));
                        posX = Math.toRadians((double) thePlayer.getRotationYaw());
                        $i$f$add = false;
                        posY = -Math.sin(posX) * 0.4D;
                        boolean flag3 = false;

                        posZ = Math.cos(posX) * 0.4D;
                        thePlayer.setPosition(thePlayer.getPosX() + posY, thePlayer.getPosY(), thePlayer.getPosZ() + posZ);
                        $this$run.mineSecureVClipTimer.reset();
                    }
                }
                break;

            case 545150119:
                if (s.equals("watchcat")) {
                    MovementUtils.strafe(0.15F);
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setSprinting(true);
                    if (thePlayer.getPosY() < $this$run.startY + (double) 2) {
                        thePlayer.setMotionY(Math.random() * 0.5D);
                    } else if ($this$run.startY > thePlayer.getPosY()) {
                        MovementUtils.strafe(0.0F);
                    }
                }
                break;

            case 701317508:
                if (s.equals("hawkeye")) {
                    thePlayer.setMotionY(thePlayer.getMotionY() <= -0.42D ? 0.42D : -0.42D);
                }
                break;

            case 709940890:
                if (s.equals("minesucht")) {
                    posX = thePlayer.getPosX();
                    posY = thePlayer.getPosY();
                    posZ = thePlayer.getPosZ();
                    if (MinecraftInstance.mc.getGameSettings().getKeyBindForward().isKeyDown()) {
                        if (System.currentTimeMillis() - $this$run.minesuchtTP > (long) 99) {
                            WVec3 vec3 = thePlayer.getPositionEyes(0.0F);

                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            WVec3 vec31 = ientityplayersp.getLook(0.0F);

                            z$iv$iv = vec31.getXCoord() * (double) 7;
                            double y$iv = vec31.getYCoord() * (double) 7;
                            double z$iv = vec31.getZCoord() * (double) 7;
                            boolean $i$f$addVector1 = false;
                            WVec3 vec32 = new WVec3(vec3.getXCoord() + z$iv$iv, vec3.getYCoord() + y$iv, vec3.getZCoord() + z$iv);

                            if ((double) thePlayer.getFallDistance() > 0.8D) {
                                thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + (double) 50, posZ, false));
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.fall(100.0F, 100.0F);
                                thePlayer.setFallDistance(0.0F);
                                thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + (double) 20, posZ, true));
                            }

                            thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(vec32.getXCoord(), thePlayer.getPosY() + (double) 50, vec32.getZCoord(), true));
                            thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY, posZ, false));
                            thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(vec32.getXCoord(), posY, vec32.getZCoord(), true));
                            thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY, posZ, false));
                            $this$run.minesuchtTP = System.currentTimeMillis();
                        } else {
                            thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), false));
                            thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY, posZ, true));
                        }
                    }
                }
                break;

            case 1083223725:
                if (s.equals("redesky")) {
                    MinecraftInstance.mc.getTimer().setTimerSpeed(0.3F);
                    $this$run.redeskyHClip2(7.0D);
                    $this$run.redeskyVClip2(10.0D);
                    $this$run.redeskyVClip1(-0.5F);
                    $this$run.redeskyHClip1(2.0D);
                    $this$run.redeskySpeed(1);
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionY(-0.01D);
                }
                break;

            case 1381910549:
                if (s.equals("hypixel")) {
                    i = ((Number) $this$run.hypixelBoostDelay.get()).intValue();
                    if (((Boolean) $this$run.hypixelBoost.get()).booleanValue() && !$this$run.flyTimer.hasTimePassed((long) i)) {
                        MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F + ((Number) $this$run.hypixelBoostTimer.get()).floatValue() * ((float) $this$run.flyTimer.hasTimeLeft((long) i) / (float) i));
                    }

                    $this$run.hypixelTimer.update();
                    if ($this$run.hypixelTimer.hasTimePassed(2)) {
                        thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 1.0E-5D, thePlayer.getPosZ());
                        $this$run.hypixelTimer.reset();
                    }
                }
                break;

            case 1435059604:
                if (s.equals("aac1.9.10")) {
                    if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        $this$run.aacJump += 0.2D;
                    }

                    if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        $this$run.aacJump -= 0.2D;
                    }

                    if ($this$run.startY + $this$run.aacJump > thePlayer.getPosY()) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(true));
                        thePlayer.setMotionY(0.8D);
                        MovementUtils.strafe(((Number) $this$run.aacSpeedValue.get()).floatValue());
                    }

                    MovementUtils.strafe$default(0.0F, 1, (Object) null);
                }
                break;

            case 1457669645:
                if (s.equals("smoothvanilla")) {
                    thePlayer.getCapabilities().setFlying(true);
                    $this$run.handleVanillaKickBypass();
                }
                break;

            case 1492139162:
                if (s.equals("aac3.3.12")) {
                    if (thePlayer.getPosY() < (double) -70) {
                        thePlayer.setMotionY((double) ((Number) $this$run.aacMotion.get()).floatValue());
                    }

                    MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                    if (Keyboard.isKeyDown(29)) {
                        MinecraftInstance.mc.getTimer().setTimerSpeed(0.2F);
                        MinecraftInstance.mc.setRightClickDelayTimer(0);
                    }
                }
                break;

            case 1492139163:
                if (s.equals("aac3.3.13")) {
                    if (thePlayer.isDead()) {
                        $this$run.wasDead = true;
                    }

                    if ($this$run.wasDead || thePlayer.getOnGround()) {
                        $this$run.wasDead = false;
                        thePlayer.setMotionY((double) ((Number) $this$run.aacMotion2.get()).floatValue());
                        thePlayer.setOnGround(false);
                    }

                    MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                    if (Keyboard.isKeyDown(29)) {
                        MinecraftInstance.mc.getTimer().setTimerSpeed(0.2F);
                        MinecraftInstance.mc.setRightClickDelayTimer(0);
                    }
                }
                break;

            case 2061751551:
                if (s.equals("spartan2")) {
                    MovementUtils.strafe(0.264F);
                    if (thePlayer.getTicksExisted() % 8 == 0) {
                        thePlayer.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + (double) 10, thePlayer.getPosZ(), true));
                    }
                }
            }

            Unit unit = Unit.INSTANCE;
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (StringsKt.equals((String) this.modeValue.get(), "boosthypixel", true)) {
            IEntityPlayerSP ientityplayersp;
            IEntityPlayerSP ientityplayersp1;

            switch (Fly$WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
            case 1:
                this.hypixelTimer.update();
                if (this.hypixelTimer.hasTimePassed(2)) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d0 = ientityplayersp1.getPosX();
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d1 = ientityplayersp2.getPosY() + 1.0E-5D;
                    IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp3 == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setPosition(d0, d1, ientityplayersp3.getPosZ());
                    this.hypixelTimer.reset();
                }

                if (!this.failedStart) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionY(0.0D);
                }
                break;

            case 2:
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                double d2 = ientityplayersp.getPosX();

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                double xDist = d2 - ientityplayersp1.getPrevPosX();

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                d2 = ientityplayersp.getPosZ();
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                double zDist = d2 - ientityplayersp1.getPrevPosZ();
                double d3 = xDist * xDist + zDist * zDist;
                boolean flag = false;
                double d4 = Math.sqrt(d3);

                this.lastDistance = d4;
            }
        }

    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        String mode = (String) this.modeValue.get();

        if (((Boolean) this.markValue.get()).booleanValue() && !StringsKt.equals(mode, "Vanilla", true) && !StringsKt.equals(mode, "SmoothVanilla", true)) {
            double y = this.startY + 2.0D;
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            RenderUtils.drawPlatform(y, ientityplayersp.getEntityBoundingBox().getMaxY() < y ? new Color(0, 255, 0, 90) : new Color(255, 0, 0, 90), 1.0D);
            boolean flag = false;

            if (mode == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s = mode.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                String s1 = s;

                switch (s1.hashCode()) {
                case 1435059604:
                    if (s1.equals("aac1.9.10")) {
                        RenderUtils.drawPlatform(this.startY + this.aacJump, new Color(0, 0, 255, 90), 1.0D);
                    }
                    break;

                case 1492139162:
                    if (s1.equals("aac3.3.12")) {
                        RenderUtils.drawPlatform(-70.0D, new Color(0, 0, 255, 90), 1.0D);
                    }
                }

            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!this.noPacketModify) {
            if (MinecraftInstance.classProvider.isCPacketPlayer(event.getPacket())) {
                ICPacketPlayer mode;
                String mode1;
                label37: {
                    mode = event.getPacket().asCPacketPlayer();
                    mode1 = (String) this.modeValue.get();
                    if (!StringsKt.equals(mode1, "NCP", true) && !StringsKt.equals(mode1, "Rewinside", true)) {
                        if (!StringsKt.equals(mode1, "Mineplex", true)) {
                            break label37;
                        }

                        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getInventory().getCurrentItemInHand() != null) {
                            break label37;
                        }
                    }

                    mode.setOnGround(true);
                }

                if (StringsKt.equals(mode1, "Hypixel", true) || StringsKt.equals(mode1, "BoostHypixel", true)) {
                    mode.setOnGround(false);
                }
            }

            if (MinecraftInstance.classProvider.isSPacketPlayerPosLook(event.getPacket())) {
                String mode2 = (String) this.modeValue.get();

                if (StringsKt.equals(mode2, "BoostHypixel", true)) {
                    this.failedStart = true;
                    ClientUtils.displayChatMessage("§8[§c§lBoostHypixel-§a§lFly§8] §cSetback detected.");
                }
            }

        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String s = (String) this.modeValue.get();
        boolean amplifier = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            double d0;
            double amplifier1;
            IEntityPlayerSP ientityplayersp;

            switch (s.hashCode()) {
            case -1031473397:
                if (s.equals("cubecraft")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    amplifier1 = Math.toRadians((double) ientityplayersp.getRotationYaw());
                    boolean baseSpeed1;

                    if (this.cubecraftTeleportTickTimer.hasTimePassed(2)) {
                        baseSpeed1 = false;
                        d0 = Math.sin(amplifier1);
                        event.setX(-d0 * 2.4D);
                        baseSpeed1 = false;
                        d0 = Math.cos(amplifier1);
                        event.setZ(d0 * 2.4D);
                        this.cubecraftTeleportTickTimer.reset();
                    } else {
                        baseSpeed1 = false;
                        d0 = Math.sin(amplifier1);
                        event.setX(-d0 * 0.2D);
                        baseSpeed1 = false;
                        d0 = Math.cos(amplifier1);
                        event.setZ(d0 * 0.2D);
                    }
                }
                break;

            case -385327063:
                if (s.equals("freehypixel") && !this.freeHypixelTimer.hasTimePassed(10)) {
                    event.zero();
                }
                break;

            case 1814517522:
                if (s.equals("boosthypixel")) {
                    if (!MovementUtils.isMoving()) {
                        event.setX(0.0D);
                        event.setZ(0.0D);
                        return;
                    }

                    if (this.failedStart) {
                        return;
                    }

                    double d1 = (double) 1;
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d2;
                    IEntityPlayerSP ientityplayersp2;

                    if (ientityplayersp1.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        IPotionEffect ipotioneffect = ientityplayersp2.getActivePotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED));

                        if (ipotioneffect == null) {
                            Intrinsics.throwNpe();
                        }

                        d2 = 0.2D * ((double) ipotioneffect.getAmplifier() + 1.0D);
                    } else {
                        d2 = 0.0D;
                    }

                    amplifier1 = d1 + d2;
                    double baseSpeed = 0.29D * amplifier1;

                    switch (this.boostHypixelState) {
                    case 1:
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        this.moveSpeed = (ientityplayersp1.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED)) ? 1.56D : 2.034D) * baseSpeed;
                        this.boostHypixelState = 2;
                        break;

                    case 2:
                        this.moveSpeed *= 2.16D;
                        this.boostHypixelState = 3;
                        break;

                    case 3:
                        d2 = this.lastDistance;
                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        this.moveSpeed = d2 - (ientityplayersp2.getTicksExisted() % 2 == 0 ? 0.0103D : 0.0123D) * (this.lastDistance - baseSpeed);
                        this.boostHypixelState = 4;
                        break;

                    default:
                        this.moveSpeed = this.lastDistance - this.lastDistance / 159.8D;
                    }

                    double yaw = this.moveSpeed;
                    double d3 = 0.3D;
                    boolean flag = false;

                    d0 = Math.max(yaw, d3);
                    this.moveSpeed = d0;
                    yaw = MovementUtils.getDirection();
                    boolean flag1 = false;

                    d0 = Math.sin(yaw);
                    event.setX(-d0 * this.moveSpeed);
                    flag1 = false;
                    d0 = Math.cos(yaw);
                    event.setZ(d0 * this.moveSpeed);
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionX(event.getX());
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.setMotionZ(event.getZ());
                }
            }

        }
    }

    @EventTarget
    public final void onBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null) {
            String mode = (String) this.modeValue.get();

            if (MinecraftInstance.classProvider.isBlockAir(event.getBlock())) {
                if (!StringsKt.equals(mode, "Hypixel", true) && !StringsKt.equals(mode, "BoostHypixel", true) && !StringsKt.equals(mode, "Rewinside", true)) {
                    if (!StringsKt.equals(mode, "Mineplex", true)) {
                        return;
                    }

                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getInventory().getCurrentItemInHand() != null) {
                        return;
                    }
                }

                double d0 = (double) event.getY();
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (d0 < ientityplayersp1.getPosY()) {
                    IClassProvider iclassprovider = MinecraftInstance.classProvider;
                    double d1 = (double) event.getX();
                    double d2 = (double) event.getY();
                    double d3 = (double) event.getZ();
                    double d4 = (double) event.getX() + 1.0D;
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    event.setBoundingBox(iclassprovider.createAxisAlignedBB(d1, d2, d3, d4, ientityplayersp2.getPosY(), (double) event.getZ() + 1.0D));
                }
            }

        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        String mode = (String) this.modeValue.get();

        if (!StringsKt.equals(mode, "Hypixel", true) && !StringsKt.equals(mode, "BoostHypixel", true) && !StringsKt.equals(mode, "Rewinside", true)) {
            if (!StringsKt.equals(mode, "Mineplex", true)) {
                return;
            }

            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getInventory().getCurrentItemInHand() != null) {
                return;
            }
        }

        e.cancelEvent();
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        String mode = (String) this.modeValue.get();

        if (!StringsKt.equals(mode, "Hypixel", true) && !StringsKt.equals(mode, "BoostHypixel", true) && !StringsKt.equals(mode, "Rewinside", true)) {
            if (!StringsKt.equals(mode, "Mineplex", true)) {
                return;
            }

            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getInventory().getCurrentItemInHand() != null) {
                return;
            }
        }

        e.setStepHeight(0.0F);
    }

    private final void handleVanillaKickBypass() {
        if (((Boolean) this.vanillaKickBypassValue.get()).booleanValue() && this.groundTimer.hasTimePassed(1000L)) {
            double ground = this.calculateGround();
            boolean flag = false;
            boolean flag1 = false;
            Fly $this$run = (Fly) this;
            boolean $i$a$-run-Fly$handleVanillaKickBypass$1 = false;
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IINetHandlerPlayClient iinethandlerplayclient;
            double d0;
            IClassProvider iclassprovider;
            IEntityPlayerSP ientityplayersp1;
            IEntityPlayerSP ientityplayersp2;

            for (double posY1 = ientityplayersp.getPosY(); posY1 > ground; posY1 -= 8.0D) {
                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                iclassprovider = MinecraftInstance.classProvider;
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                d0 = ientityplayersp1.getPosX();
                ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp2 == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d0, posY1, ientityplayersp2.getPosZ(), true));
                if (posY1 - 8.0D < ground) {
                    break;
                }
            }

            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
            iclassprovider = MinecraftInstance.classProvider;
            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            d0 = ientityplayersp1.getPosX();
            ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d0, ground, ientityplayersp2.getPosZ(), true));
            double posY = ground;

            while (true) {
                IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp3 == null) {
                    Intrinsics.throwNpe();
                }

                if (posY >= ientityplayersp3.getPosY()) {
                    break;
                }

                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                iclassprovider = MinecraftInstance.classProvider;
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                d0 = ientityplayersp1.getPosX();
                ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp2 == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d0, posY, ientityplayersp2.getPosZ(), true));
                double d1 = posY + 8.0D;

                ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp3 == null) {
                    Intrinsics.throwNpe();
                }

                if (d1 > ientityplayersp3.getPosY()) {
                    break;
                }

                posY += 8.0D;
            }

            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
            iclassprovider = MinecraftInstance.classProvider;
            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            d0 = ientityplayersp1.getPosX();
            IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp4 == null) {
                Intrinsics.throwNpe();
            }

            double d2 = ientityplayersp4.getPosY();

            ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d0, d2, ientityplayersp2.getPosZ(), true));
            this.groundTimer.reset();
        }
    }

    private final void redeskyVClip1(float vertical) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        double d0 = ientityplayersp1.getPosX();
        IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp2 == null) {
            Intrinsics.throwNpe();
        }

        double d1 = ientityplayersp2.getPosY() + (double) vertical;
        IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp3 == null) {
            Intrinsics.throwNpe();
        }

        ientityplayersp.setPosition(d0, d1, ientityplayersp3.getPosZ());
    }

    private final void redeskyHClip1(double horizontal) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double playerYaw = Math.toRadians((double) ientityplayersp.getRotationYaw());

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        double d0 = ientityplayersp1.getPosX();
        IEntityPlayerSP ientityplayersp2 = ientityplayersp;
        boolean flag = false;
        double d1 = Math.sin(playerYaw);
        double d2 = d0 + horizontal * -d1;
        IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp3 == null) {
            Intrinsics.throwNpe();
        }

        double d3 = ientityplayersp3.getPosY();
        IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp4 == null) {
            Intrinsics.throwNpe();
        }

        d1 = ientityplayersp4.getPosZ();
        double d4 = d3;

        d0 = d2;
        flag = false;
        double d5 = Math.cos(playerYaw);

        ientityplayersp2.setPosition(d0, d4, d1 + horizontal * d5);
    }

    private final void redeskyHClip2(double horizontal) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double playerYaw = Math.toRadians((double) ientityplayersp.getRotationYaw());
        IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        double d0 = ientityplayersp1.getPosX();
        IClassProvider iclassprovider1 = iclassprovider;
        IINetHandlerPlayClient iinethandlerplayclient1 = iinethandlerplayclient;
        boolean flag = false;
        double d1 = Math.sin(playerYaw);
        double d2 = d0 + horizontal * -d1;
        IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp2 == null) {
            Intrinsics.throwNpe();
        }

        double d3 = ientityplayersp2.getPosY();
        IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp3 == null) {
            Intrinsics.throwNpe();
        }

        d1 = ientityplayersp3.getPosZ();
        double d4 = d3;

        d0 = d2;
        flag = false;
        double d5 = Math.cos(playerYaw);

        iinethandlerplayclient1.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerPosition(d0, d4, d1 + horizontal * d5, false));
    }

    private final void redeskyVClip2(double vertical) {
        IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double d0 = ientityplayersp.getPosX();
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        double d1 = ientityplayersp1.getPosY() + vertical;
        IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp2 == null) {
            Intrinsics.throwNpe();
        }

        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d0, d1, ientityplayersp2.getPosZ(), false));
    }

    private final void redeskySpeed(int speed) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double playerYaw = Math.toRadians((double) ientityplayersp.getRotationYaw());

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double d0 = (double) speed;
        IEntityPlayerSP ientityplayersp1 = ientityplayersp;
        boolean flag = false;
        double d1 = Math.sin(playerYaw);

        ientityplayersp1.setMotionX(d0 * -d1);
        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        d0 = (double) speed;
        ientityplayersp1 = ientityplayersp;
        flag = false;
        d1 = Math.cos(playerYaw);
        ientityplayersp1.setMotionZ(d0 * d1);
    }

    private final double calculateGround() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IAxisAlignedBB playerBoundingBox = ientityplayersp.getEntityBoundingBox();
        double blockHeight = 1.0D;

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        for (double ground = ientityplayersp.getPosY(); ground > 0.0D; ground -= blockHeight) {
            IAxisAlignedBB customBox = MinecraftInstance.classProvider.createAxisAlignedBB(playerBoundingBox.getMaxX(), ground + blockHeight, playerBoundingBox.getMaxZ(), playerBoundingBox.getMinX(), ground, playerBoundingBox.getMinZ());
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            if (iworldclient.checkBlockCollision(customBox)) {
                if (blockHeight <= 0.05D) {
                    return ground + blockHeight;
                }

                ground += blockHeight;
                blockHeight = 0.05D;
            }
        }

        return 0.0D;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
