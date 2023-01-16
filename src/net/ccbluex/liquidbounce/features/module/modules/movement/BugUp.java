package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.utils.FallingPlayer;
import me.utils.PacketUtils;
import me.utils.timer.TimeHelper;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AntiVoid",
    category = ModuleCategory.MOVEMENT,
    description = "544"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0013\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u00100\u001a\u00020\u0006H\u0002J\b\u00101\u001a\u00020\u0006H\u0016J\b\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u0002032\u0006\u00105\u001a\u000206H\u0007J\u0010\u00107\u001a\u0002032\u0006\u00105\u001a\u000208H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u001b0\u001aj\b\u0012\u0004\u0012\u00020\u001b`\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u001b0\u001aj\b\u0012\u0004\u0012\u00020\u001b`\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u00020)X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/BugUp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoScaffoldValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blink", "", "canBlink", "canSpoof", "flagged", "lastGroundPos", "", "getLastGroundPos", "()[D", "setLastGroundPos", "([D)V", "lastRecY", "", "maxFallDistValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "motionX", "motionY", "motionZ", "packetCache", "Ljava/util/ArrayList;", "Lnet/minecraft/network/play/client/CPacketPlayer;", "Lkotlin/collections/ArrayList;", "packets", "getPackets", "()Ljava/util/ArrayList;", "setPackets", "(Ljava/util/ArrayList;)V", "posX", "posY", "posZ", "pullbackTime", "resetMotionValue", "startFallDistValue", "timer", "Lme/utils/timer/TimeHelper;", "getTimer", "()Lme/utils/timer/TimeHelper;", "setTimer", "(Lme/utils/timer/TimeHelper;)V", "tried", "voidOnlyValue", "checkVoid", "isInVoid", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public class BugUp extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Hypixel", "Blink", "TPBack", "MotionFlag", "PacketFlag", "GroundSpoof", "OldHypixel", "Jartex", "OldCubecraft"}, "Blink");
    private final FloatValue pullbackTime = new FloatValue("Hypixel-PullbackTime", 800.0F, 800.0F, 1800.0F);
    private final FloatValue maxFallDistValue = new FloatValue("MaxFallDistance", 10.0F, 5.0F, 20.0F);
    private final BoolValue resetMotionValue = new BoolValue("ResetMotion", false);
    private final FloatValue startFallDistValue = new FloatValue("BlinkStartFallDistance", 2.0F, 0.0F, 5.0F);
    private final BoolValue autoScaffoldValue = new BoolValue("BlinkAutoScaffold", true);
    private final BoolValue voidOnlyValue = new BoolValue("OnlyVoid", true);
    private final ArrayList packetCache = new ArrayList();
    private boolean blink;
    private boolean canBlink;
    private boolean canSpoof;
    private boolean tried;
    private boolean flagged;
    private double posX;
    private double posY;
    private double posZ;
    private double motionX;
    private double motionY;
    private double motionZ;
    private double lastRecY;
    @NotNull
    private TimeHelper timer = new TimeHelper();
    @NotNull
    private double[] lastGroundPos = new double[3];
    @NotNull
    private ArrayList packets = new ArrayList();

    @NotNull
    public final TimeHelper getTimer() {
        return this.timer;
    }

    public final void setTimer(@NotNull TimeHelper <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.timer = <set-?>;
    }

    @NotNull
    public final double[] getLastGroundPos() {
        return this.lastGroundPos;
    }

    public final void setLastGroundPos(@NotNull double[] <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.lastGroundPos = <set-?>;
    }

    @NotNull
    public final ArrayList getPackets() {
        return this.packets;
    }

    public final void setPackets(@NotNull ArrayList <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.packets = <set-?>;
    }

    public boolean isInVoid() {
        int i = 0;

        for (short short0 = 128; i <= short0; ++i) {
            if (MovementUtils.INSTANCE.isOnGround((double) i)) {
                return false;
            }
        }

        return true;
    }

    public void onEnable() {
        this.blink = false;
        this.canBlink = false;
        this.canSpoof = false;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        this.lastRecY = ientityplayersp.getPosY();
        this.tried = false;
        this.flagged = false;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getOnGround()) {
            this.tried = false;
            this.flagged = false;
        }

        String s = (String) this.modeValue.get();
        boolean packet = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            IINetHandlerPlayClient iinethandlerplayclient;
            IEntityPlayerSP ientityplayersp1;
            double d0;
            double d1;
            ClassProviderImpl classproviderimpl;
            IEntityPlayerSP ientityplayersp2;
            IEntityPlayerSP ientityplayersp3;
            IEntityPlayerSP ientityplayersp4;

            switch (s.hashCode()) {
            case -1167184852:
                if (s.equals("jartex")) {
                    this.canSpoof = false;
                    if (!((Boolean) this.voidOnlyValue.get()).booleanValue() || this.checkVoid()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getFallDistance() > ((Number) this.maxFallDistValue.get()).floatValue()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getPosY() < this.lastRecY + 0.01D) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getMotionY() <= (double) 0) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (!ientityplayersp.getOnGround() && !this.flagged) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionY(0.0D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.838D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.838D);
                                        this.canSpoof = true;
                                    }
                                }
                            }
                        }
                    }

                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    this.lastRecY = ientityplayersp1.getPosY();
                }
                break;

            case -867535517:
                if (s.equals("tpback")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getOnGround()) {
                        WBlockPos wblockpos = new WBlockPos;

                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        d0 = ientityplayersp2.getPosX();
                        ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp3 == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientityplayersp3.getPosY() - 1.0D;
                        ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp4 == null) {
                            Intrinsics.throwNpe();
                        }

                        wblockpos.<init>(d0, d1, ientityplayersp4.getPosZ());
                        if (!(BlockUtils.getBlock(wblockpos) instanceof BlockAir)) {
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            this.posX = ientityplayersp1.getPrevPosX();
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            this.posY = ientityplayersp1.getPrevPosY();
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            this.posZ = ientityplayersp1.getPrevPosZ();
                        }
                    }

                    if (!((Boolean) this.voidOnlyValue.get()).booleanValue() || this.checkVoid()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getFallDistance() > ((Number) this.maxFallDistValue.get()).floatValue() && !this.tried) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setFallDistance(0.0F);
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionX(0.0D);
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionY(0.0D);
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setMotionZ(0.0D);
                            this.tried = true;
                        }
                    }
                }
                break;

            case -720374750:
                if (s.equals("motionflag") && (!((Boolean) this.voidOnlyValue.get()).booleanValue() || this.checkVoid())) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getFallDistance() > ((Number) this.maxFallDistValue.get()).floatValue() && !this.tried) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.setMotionY(ientityplayersp.getMotionY() + (double) 1);
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.setFallDistance(0.0F);
                        this.tried = true;
                    }
                }
                break;

            case -529978910:
                if (s.equals("groundspoof") && (!((Boolean) this.voidOnlyValue.get()).booleanValue() || this.checkVoid())) {
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    this.canSpoof = ientityplayersp1.getFallDistance() > ((Number) this.maxFallDistValue.get()).floatValue();
                }
                break;

            case 93826908:
                if (s.equals("blink")) {
                    if (!this.blink) {
                        FallingPlayer fallingplayer = new FallingPlayer;

                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        d0 = ientityplayersp2.getPosX();
                        ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp3 == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientityplayersp3.getPosY();
                        ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp4 == null) {
                            Intrinsics.throwNpe();
                        }

                        fallingplayer.<init>(d0, d1, ientityplayersp4.getPosZ(), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F, 0.0F);
                        WBlockPos packet1 = fallingplayer.findCollision(60);

                        if (this.canBlink) {
                            label434: {
                                if (packet1 != null) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getPosY() - (double) packet1.getY() <= ((Number) this.startFallDistValue.get()).doubleValue()) {
                                        break label434;
                                    }
                                }

                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                this.posX = ientityplayersp1.getPosX();
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                this.posY = ientityplayersp1.getPosY();
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                this.posZ = ientityplayersp1.getPosZ();
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                this.motionX = ientityplayersp1.getMotionX();
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                this.motionY = ientityplayersp1.getMotionY();
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                this.motionZ = ientityplayersp1.getMotionZ();
                                this.packetCache.clear();
                                this.blink = true;
                            }
                        }

                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getOnGround()) {
                            this.canBlink = true;
                        }
                    } else {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getFallDistance() > ((Number) this.maxFallDistValue.get()).floatValue()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                            if (((Boolean) this.resetMotionValue.get()).booleanValue()) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionX(0.0D);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionY(0.0D);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionZ(0.0D);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setJumpMovementFactor(0.0F);
                            } else {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionX(this.motionX);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionY(this.motionY);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionZ(this.motionZ);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setJumpMovementFactor(0.0F);
                            }

                            if (((Boolean) this.autoScaffoldValue.get()).booleanValue()) {
                                Module module = LiquidBounce.INSTANCE.getModuleManager().get(Scaffold.class);

                                if (module == null) {
                                    Intrinsics.throwNpe();
                                }

                                module.setState(true);
                            }

                            this.packetCache.clear();
                            this.blink = false;
                            this.canBlink = false;
                        } else {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getOnGround()) {
                                this.blink = false;
                                Iterator iterator = this.packetCache.iterator();

                                while (iterator.hasNext()) {
                                    CPacketPlayer packet2 = (CPacketPlayer) iterator.next();

                                    Intrinsics.checkExpressionValueIsNotNull(packet2, "packet");
                                    PacketUtils.sendPacketNoEvent((Packet) packet2);
                                }
                            }
                        }
                    }
                }
                break;

            case 155895796:
                if (s.equals("packetflag") && (!((Boolean) this.voidOnlyValue.get()).booleanValue() || this.checkVoid())) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getFallDistance() > ((Number) this.maxFallDistValue.get()).floatValue() && !this.tried) {
                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        classproviderimpl = ClassProviderImpl.INSTANCE;
                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        d0 = ientityplayersp2.getPosX() + (double) 1;
                        ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp3 == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientityplayersp3.getPosY() + (double) 1;
                        ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp4 == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue((IPacket) classproviderimpl.createCPacketPlayerPosition(d0, d1, ientityplayersp4.getPosZ() + (double) 1, false));
                        this.tried = true;
                    }
                }
                break;

            case 1438074052:
                if (s.equals("oldcubecraft")) {
                    this.canSpoof = false;
                    if (!((Boolean) this.voidOnlyValue.get()).booleanValue() || this.checkVoid()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getFallDistance() > ((Number) this.maxFallDistValue.get()).floatValue()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getPosY() < this.lastRecY + 0.01D) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getMotionY() <= (double) 0) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (!ientityplayersp.getOnGround() && !this.flagged) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionY(0.0D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionZ(0.0D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionX(0.0D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setJumpMovementFactor(0.0F);
                                        this.canSpoof = true;
                                        if (!this.tried) {
                                            this.tried = true;
                                            iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                            classproviderimpl = ClassProviderImpl.INSTANCE;
                                            ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                            if (ientityplayersp2 == null) {
                                                Intrinsics.throwNpe();
                                            }

                                            d0 = ientityplayersp2.getPosX();
                                            ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                            if (ientityplayersp4 == null) {
                                                Intrinsics.throwNpe();
                                            }

                                            iinethandlerplayclient.addToSendQueue((IPacket) classproviderimpl.createCPacketPlayerPosition(d0, 32000.0D, ientityplayersp4.getPosZ(), false));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    this.lastRecY = ientityplayersp1.getPosY();
                }
            }

        }
    }

    private final boolean checkVoid() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        int i = (int) (-(ientityplayersp.getPosY() - 1.4857625D));
        boolean dangerous = true;

        while (i <= 0) {
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            IAxisAlignedBB iaxisalignedbb = ientityplayersp1.getEntityBoundingBox();
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            double d0 = ientityplayersp2.getMotionX() * 0.5D;
            double d1 = (double) i;
            IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp3 == null) {
                Intrinsics.throwNpe();
            }

            dangerous = iworldclient.getCollisionBoxes(iaxisalignedbb.offset(d0, d1, ientityplayersp3.getMotionZ() * 0.5D)).isEmpty();
            ++i;
            if (!dangerous) {
                break;
            }
        }

        return dangerous;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        String s = (String) this.modeValue.get();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            IEntityPlayerSP ientityplayersp;

            switch (s.hashCode()) {
            case -1167184852:
                if (s.equals("jartex")) {
                    if (this.canSpoof && packet instanceof CPacketPlayer) {
                        ((CPacketPlayer) packet).onGround = true;
                    }

                    if (this.canSpoof && packet instanceof SPacketPlayerPosLook) {
                        this.flagged = true;
                    }
                }
                break;

            case -529978910:
                if (s.equals("groundspoof") && this.canSpoof && packet instanceof CPacketPlayer) {
                    ((CPacketPlayer) packet).onGround = true;
                }
                break;

            case 93826908:
                if (s.equals("blink") && this.blink && packet instanceof CPacketPlayer) {
                    this.packetCache.add(packet);
                    event.cancelEvent();
                }
                break;

            case 1381910549:
                if (s.equals("hypixel")) {
                    Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Fly.class);

                    if (module == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!module.getState()) {
                        module = LiquidBounce.INSTANCE.getModuleManager().getModule(Scaffold.class);
                        if (module == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!module.getState()) {
                            if (!this.packets.isEmpty()) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getTicksExisted() < 100) {
                                    this.packets.clear();
                                }
                            }

                            if (packet instanceof CPacketPlayer) {
                                if (this.isInVoid()) {
                                    event.cancelEvent();
                                    this.packets.add(packet);
                                    if (this.timer.delay(((Number) this.pullbackTime.get()).floatValue())) {
                                        PacketUtils.sendPacketNoEvent((Packet) (new Position(this.lastGroundPos[0], this.lastGroundPos[1] - 1.0D, this.lastGroundPos[2], true)));
                                    }
                                } else {
                                    double[] adouble = this.lastGroundPos;
                                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    adouble[0] = ientityplayersp1.getPosX();
                                    adouble = this.lastGroundPos;
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    adouble[1] = ientityplayersp1.getPosY();
                                    adouble = this.lastGroundPos;
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    adouble[2] = ientityplayersp1.getPosZ();
                                    if (!this.packets.isEmpty()) {
                                        Iterator iterator = this.packets.iterator();

                                        Intrinsics.checkExpressionValueIsNotNull(iterator, "packets.iterator()");
                                        Iterator iterator1 = iterator;

                                        ClientUtils.displayChatMessage("[AntiVoid] Release Packets - " + this.packets.size());

                                        while (iterator1.hasNext()) {
                                            Object object = iterator1.next();

                                            if (object == null) {
                                                throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketPlayer");
                                            }

                                            CPacketPlayer p = (CPacketPlayer) object;

                                            PacketUtils.sendPacketNoEvent((Packet) p);
                                        }

                                        this.packets.clear();
                                    }

                                    this.timer.reset();
                                }
                            }
                        }
                    }

                    if (packet instanceof SPacketPlayerPosLook && this.packets.size() > 1) {
                        ClientUtils.displayChatMessage("[AntiVoid] Pullbacks Detected, clear packets list!");
                        this.packets.clear();
                    }
                }
                break;

            case 1438074052:
                if (s.equals("oldcubecraft")) {
                    if (this.canSpoof && packet instanceof CPacketPlayer && ((CPacketPlayer) packet).y < 1145.14191981D) {
                        event.cancelEvent();
                    }

                    if (this.canSpoof && packet instanceof SPacketPlayerPosLook) {
                        this.flagged = true;
                    }
                }
                break;

            case 1594535950:
                if (s.equals("oldhypixel")) {
                    if (packet instanceof SPacketPlayerPosLook) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if ((double) ientityplayersp.getFallDistance() > 3.125D) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.setFallDistance(3.125F);
                        }
                    }

                    if (packet instanceof CPacketPlayer) {
                        if (((Boolean) this.voidOnlyValue.get()).booleanValue()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getFallDistance() >= ((Number) this.maxFallDistValue.get()).floatValue()) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getMotionY() <= (double) 0 && this.checkVoid()) {
                                    ((CPacketPlayer) packet).y += 11.0D;
                                }
                            }
                        }

                        if (!((Boolean) this.voidOnlyValue.get()).booleanValue()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getFallDistance() >= ((Number) this.maxFallDistValue.get()).floatValue()) {
                                ((CPacketPlayer) packet).y += 11.0D;
                            }
                        }
                    }
                }
            }

        }
    }
}
