package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "Criticals",
    description = "Automatically deals critical hits.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0007J\b\u0010\"\u001a\u00020\u001fH\u0016J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020$H\u0007J.\u0010%\u001a\u00020\u001f2\b\b\u0002\u0010&\u001a\u00020\'2\b\b\u0002\u0010(\u001a\u00020\'2\b\b\u0002\u0010)\u001a\u00020\'2\u0006\u0010*\u001a\u00020+H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/Criticals;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attacks", "", "getAttacks", "()I", "setAttacks", "(I)V", "debugValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getDelayValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hurtTimeValue", "lookValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getMsTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "tag", "", "getTag", "()Ljava/lang/String;", "target", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onEnable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "sendCriticalPacket", "xOffset", "", "yOffset", "zOffset", "ground", "", "LiquidBounce"}
)
public final class Criticals extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[] { "NewPacket", "Lite", "HytTest", "HytVulcan", "Vulcan", "vulcanfake", "Spartan", "StarPacket", "Packet", "NcpPacket", "NoGround", "Hop", "TPHop", "Jump", "LowJump", "Visual", "HuaYuTingPacket", "Test", "sb", "fw", "nmsl", "huayuting"}, "Packet");
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 500);
    private final BoolValue lookValue = new BoolValue("SendC06", false);
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    private final BoolValue debugValue = new BoolValue("DebugMessage", false);
    private int attacks;
    @NotNull
    private final MSTimer msTimer = new MSTimer();
    private int target;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final IntegerValue getDelayValue() {
        return this.delayValue;
    }

    public final int getAttacks() {
        return this.attacks;
    }

    public final void setAttacks(int <set-?>) {
        this.attacks = <set-?>;
    }

    @NotNull
    public final MSTimer getMsTimer() {
        return this.msTimer;
    }

    private final void sendCriticalPacket(double xOffset, double yOffset, double zOffset, boolean ground) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double x = ientityplayersp.getPosX() + xOffset;

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double y = ientityplayersp.getPosY() + yOffset;

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double z = ientityplayersp.getPosZ() + zOffset;

        if (((Boolean) this.lookValue.get()).booleanValue()) {
            IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            float f = ientityplayersp1.getRotationYaw();
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosLook(x, y, z, f, ientityplayersp2.getRotationPitch(), ground));
        } else {
            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, ground));
        }

    }

    static void sendCriticalPacket$default(Criticals criticals, double d0, double d1, double d2, boolean flag, int i, Object object) {
        if ((i & 1) != 0) {
            d0 = 0.0D;
        }

        if ((i & 2) != 0) {
            d1 = 0.0D;
        }

        if ((i & 4) != 0) {
            d2 = 0.0D;
        }

        criticals.sendCriticalPacket(d0, d1, d2, flag);
    }

    public void onEnable() {
        if (StringsKt.equals((String) this.modeValue.get(), "NoGround", true)) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.jump();
        }

    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isEntityLivingBase(event.getTargetEntity())) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                return;
            }

            IEntityPlayerSP thePlayer = ientityplayersp;
            IEntity ientity = event.getTargetEntity();

            if (ientity == null) {
                Intrinsics.throwNpe();
            }

            IEntityLivingBase entity = ientity.asEntityLivingBase();

            this.target = entity.getEntityId();
            if (!thePlayer.getOnGround() || thePlayer.isOnLadder() || thePlayer.isInWeb() || thePlayer.isInWater() || thePlayer.isInLava() || thePlayer.getRidingEntity() != null || entity.getHurtTime() > ((Number) this.hurtTimeValue.get()).intValue() || LiquidBounce.INSTANCE.getModuleManager().get(Fly.class).getState() || !this.msTimer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
                return;
            }

            double x = thePlayer.getPosX();
            double y = thePlayer.getPosY();
            double z = thePlayer.getPosZ();
            String s = (String) this.modeValue.get();
            boolean normalOffset = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            double motionZ;
            double d0;
            int i;
            StringBuilder stringbuilder;
            Module module;
            IEntityLivingBase ientitylivingbase;

            switch (s.hashCode()) {
            case -2011701869:
                if (s.equals("spartan")) {
                    d0 = 0.0D;
                    motionZ = 0.0D;
                    if (MovementUtils.isMoving()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        d0 = ientityplayersp.getMotionX();
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        motionZ = ientityplayersp.getMotionZ();
                    } else {
                        d0 = 0.0D;
                        motionZ = 0.0D;
                    }

                    this.sendCriticalPacket(d0 / (double) 3, 0.20000004768372D, motionZ / (double) 3, false);
                    this.sendCriticalPacket(d0 / 1.5D, 0.12160004615784D, motionZ / 1.5D, false);
                }
                break;

            case -1777040898:
                if (s.equals("huayuting")) {
                    double[] adouble = new double[] { 0.01D, 0.023D, 0.09D, 0.011D};

                    thePlayer.setSprinting(false);
                    thePlayer.setServerSprintState(false);
                    double[] adouble1 = adouble;
                    int j = adouble.length;

                    for (int k = 0; k < j; ++k) {
                        double offSet = adouble1[k];

                        sendCriticalPacket$default(this, 0.0D, offSet, 0.0D, false, 5, (Object) null);
                    }
                }
                break;

            case -1686319782:
                if (s.equals("starpacket")) {
                    sendCriticalPacket$default(this, 0.0D, 6.6666E-6D, 0.0D, false, 5, (Object) null);
                    sendCriticalPacket$default(this, 0.0D, 7.8E-7D, 0.0D, false, 5, (Object) null);
                    sendCriticalPacket$default(this, 0.0D, 1.14514E-6D, 0.0D, false, 5, (Object) null);
                    sendCriticalPacket$default(this, 0.0D, 0.0D, 0.0D, false, 7, (Object) null);
                }
                break;

            case -995865464:
                if (s.equals("packet")) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0625D, z, true));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.1E-5D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y, z, false));
                    thePlayer.onCriticalHit((IEntity) entity);
                }
                break;

            case -891664989:
                if (s.equals("ncppacket")) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.11D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.1100013579D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.3579E-6D, z, false));
                    thePlayer.onCriticalHit((IEntity) entity);
                }
                break;

            case -816216256:
                if (s.equals("visual")) {
                    thePlayer.onCriticalHit((IEntity) entity);
                }
                break;

            case -805359837:
                if (s.equals("vulcan")) {
                    i = this.attacks++;
                    if (this.attacks > 6) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.2D, z, false));
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.1216D, z, false));
                        this.attacks = 0;
                    }
                }
                break;

            case -66922490:
                if (s.equals("huayutingpacket")) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0151314D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.3251314D, z, false));
                    thePlayer.onCriticalHit((IEntity) entity);
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0111314D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.0141314D, z, false));
                    thePlayer.onCriticalHit((IEntity) entity);
                }
                break;

            case 3281:
                if (s.equals("fw")) {
                    i = this.attacks++;
                    if (this.attacks < 5) {
                        d0 = 0.05123213776456501D;
                        stringbuilder = (new StringBuilder()).append("§6Send-§f");
                        module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
                        if (module == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                        }

                        ientitylivingbase = ((KillAura) module).getTarget();
                        if (ientitylivingbase == null) {
                            Intrinsics.throwNpe();
                        }

                        ClientUtils.displayChatMessage(stringbuilder.append(ientitylivingbase.getName()).append(",§7").append(String.valueOf(d0)).toString());
                        sendCriticalPacket$default(this, 0.0D, d0, 0.0D, false, 5, (Object) null);
                        this.attacks = 0;
                    }
                }
                break;

            case 3663:
                if (s.equals("sb")) {
                    i = this.attacks++;
                    if (this.attacks < 5) {
                        d0 = 0.015659921803854882D;
                        stringbuilder = (new StringBuilder()).append("§6Send-§f");
                        module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
                        if (module == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                        }

                        ientitylivingbase = ((KillAura) module).getTarget();
                        if (ientitylivingbase == null) {
                            Intrinsics.throwNpe();
                        }

                        ClientUtils.displayChatMessage(stringbuilder.append(ientitylivingbase.getName()).append(",§7").append(String.valueOf(d0)).toString());
                        sendCriticalPacket$default(this, 0.0D, d0, 0.0D, false, 5, (Object) null);
                        this.attacks = 0;
                    }
                }
                break;

            case 103497:
                if (s.equals("hop")) {
                    thePlayer.setMotionY(0.1D);
                    thePlayer.setFallDistance(0.1F);
                    thePlayer.setOnGround(false);
                }
                break;

            case 2603186:
                if (s.equals("Test")) {
                    i = this.attacks++;
                    if (this.attacks > 4) {
                        sendCriticalPacket$default(this, 0.0D, 0.01684689747D, 0.0D, false, 5, (Object) null);
                        ClientUtils.displayChatMessage("§6别用这个暴击，草拟吗");
                        sendCriticalPacket$default(this, 0.0D, 0.0D, 0.0D, true, 7, (Object) null);
                        thePlayer.setSprinting(false);
                        this.attacks = 0;
                    }
                }
                break;

            case 3273774:
                if (s.equals("jump")) {
                    thePlayer.setMotionY(0.42D);
                }
                break;

            case 3322030:
                if (s.equals("lite")) {
                    sendCriticalPacket$default(this, 0.0D, 0.015626D, 0.0D, false, 5, (Object) null);
                    sendCriticalPacket$default(this, 0.0D, 3.43E-9D, 0.0D, false, 5, (Object) null);
                }
                break;

            case 3385432:
                if (s.equals("nmsl")) {
                    i = this.attacks++;
                    if (this.attacks < 5) {
                        d0 = 0.04913150905855286D;
                        stringbuilder = (new StringBuilder()).append("§6Send-§f");
                        module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
                        if (module == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                        }

                        ientitylivingbase = ((KillAura) module).getTarget();
                        if (ientitylivingbase == null) {
                            Intrinsics.throwNpe();
                        }

                        ClientUtils.displayChatMessage(stringbuilder.append(ientitylivingbase.getName()).append(",§7").append(String.valueOf(d0)).toString());
                        sendCriticalPacket$default(this, 0.0D, d0, 0.0D, false, 5, (Object) null);
                        this.attacks = 0;
                    }
                }
                break;

            case 110568525:
                if (s.equals("tphop")) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.02D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 0.01D, z, false));
                    thePlayer.setPosition(x, y + 0.01D, z);
                }
                break;

            case 216546856:
                if (s.equals("newpacket")) {
                    sendCriticalPacket$default(this, 0.0D, 0.05250000001304D, 0.0D, true, 5, (Object) null);
                    sendCriticalPacket$default(this, 0.0D, 0.00150000001304D, 0.0D, false, 5, (Object) null);
                    sendCriticalPacket$default(this, 0.0D, 0.01400000001304D, 0.0D, false, 5, (Object) null);
                    sendCriticalPacket$default(this, 0.0D, 0.00150000001304D, 0.0D, false, 5, (Object) null);
                }
                break;

            case 357158274:
                if (s.equals("lowjump")) {
                    thePlayer.setMotionY(0.3425D);
                }
                break;

            case 495801958:
                if (s.equals("hytvulcan")) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 5.11322554E-4D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 1.1119999543618E-4D, z, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(x, y + 6.221E-5D, z, false));
                }
                break;

            case 1357692152:
                if (s.equals("vulcanfake")) {
                    d0 = 0.0D;
                    motionZ = 0.0D;
                    if (MovementUtils.isMoving()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        d0 = ientityplayersp.getMotionX();
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        motionZ = ientityplayersp.getMotionZ();
                    } else {
                        d0 = 0.0D;
                        motionZ = 0.0D;
                    }

                    this.sendCriticalPacket(d0 / (double) 3, 0.20000004768372D, motionZ / (double) 3, false);
                    this.sendCriticalPacket(d0 / 1.5D, 0.12160004615784D, motionZ / 1.5D, false);
                }
                break;

            case 1385914517:
                if (s.equals("hyttest")) {
                    IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    IClassProvider iclassprovider = MinecraftInstance.classProvider;
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d1 = ientityplayersp1.getPosX();
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d2 = ientityplayersp2.getPosY() + 1.100134977413E-5D;
                    IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp3 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d1, d2, ientityplayersp3.getPosZ(), false));
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    iclassprovider = MinecraftInstance.classProvider;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    d1 = ientityplayersp1.getPosX();
                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    d2 = ientityplayersp2.getPosY() + 1.3487744E-10D;
                    ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp3 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d1, d2, ientityplayersp3.getPosZ(), false));
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    iclassprovider = MinecraftInstance.classProvider;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    d1 = ientityplayersp1.getPosX();
                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    d2 = ientityplayersp2.getPosY() + 5.71003114589E-6D;
                    ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp3 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d1, d2, ientityplayersp3.getPosZ(), false));
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    iclassprovider = MinecraftInstance.classProvider;
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    d1 = ientityplayersp1.getPosX();
                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    d2 = ientityplayersp2.getPosY() + 1.578887744E-8D;
                    ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp3 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d1, d2, ientityplayersp3.getPosZ(), false));
                }
            }

            this.msTimer.reset();
        }

    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (MinecraftInstance.classProvider.isCPacketPlayer(packet) && StringsKt.equals((String) this.modeValue.get(), "NoGround", true)) {
            packet.asCPacketPlayer().setOnGround(false);
        }

        if (MinecraftInstance.classProvider.isSPacketAnimation(packet) && ((Boolean) this.debugValue.get()).booleanValue() && packet.asSPacketAnimation().getAnimationType() == 4 && packet.asSPacketAnimation().getEntityID() == this.target) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);

            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
            }

            IEntityLivingBase ientitylivingbase = ((KillAura) module).getTarget();

            if (ientitylivingbase == null) {
                Intrinsics.throwNpe();
            }

            String name = ientitylivingbase.getName();

            ClientUtils.displayChatMessage("§b[§bTGSense]§f触发§c暴击§b(§6玩家:§a" + name + ')');
        }

    }

    @Nullable
    public String getTag() {
        return (String) this.modeValue.get() + "/" + ((Number) this.delayValue.get()).intValue();
    }
}
