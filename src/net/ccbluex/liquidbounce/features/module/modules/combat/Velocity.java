package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Velocity",
    description = "Allows you to modify the amount of knockback you take.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&H\u0007J\u0010\u0010\'\u001a\u00020#2\u0006\u0010%\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020#2\u0006\u0010%\u001a\u00020*H\u0007J\u0010\u0010+\u001a\u00020#2\u0006\u0010%\u001a\u00020,H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/Velocity;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aac4XZReducerValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacPushXZReducerValue", "aacPushYReducerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "aacv4MotionReducerValue", "horizontalValue", "huayutingjumpflag", "", "jump", "legitFaceValue", "legitStrafeValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onlyCombatValue", "onlyGroundValue", "reverse2StrengthValue", "reverseHurt", "reverseStrengthValue", "tag", "", "getTag", "()Ljava/lang/String;", "velocityInput", "velocityTick", "", "velocityTickValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "velocityTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "verticalValue", "onDisable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Velocity extends Module {

    private final FloatValue horizontalValue = new FloatValue("Horizontal", 0.0F, 0.0F, 1.0F);
    private final FloatValue verticalValue = new FloatValue("Vertical", 0.0F, 0.0F, 1.0F);
    private final IntegerValue velocityTickValue = new IntegerValue("VelocityTick", 1, 0, 10);
    private final ListValue modeValue = new ListValue("Mode", new String[] { "HuaYuTing", "NewAAC4", "HuaYuTingJump", "HytJumpNew", "Legit", "aac5.2.0", "AAC5Reduce", "Cancel", "Vulcan", "Simple", "Vanilla", "Tick", "AAC", "AACPush", "AACZero", "AACv4", "Test", "Reverse", "SmoothReverse", "Jump", "Glitch", "TGSenseFull"}, "TGSenseFull");
    private final BoolValue onlyGroundValue = new BoolValue("OnlyGround", false);
    private final BoolValue onlyCombatValue = new BoolValue("OnlyCombat", false);
    private final FloatValue reverseStrengthValue = new FloatValue("ReverseStrength", 1.0F, 0.1F, 1.0F);
    private final FloatValue reverse2StrengthValue = new FloatValue("SmoothReverseStrength", 0.05F, 0.02F, 0.1F);
    private final FloatValue aac4XZReducerValue = new FloatValue("AAC4XZReducer", 2.0F, 1.0F, 3.0F);
    private final FloatValue aacPushXZReducerValue = new FloatValue("AACPushXZReducer", 2.0F, 1.0F, 3.0F);
    private final BoolValue aacPushYReducerValue = new BoolValue("AACPushYReducer", true);
    private final FloatValue aacv4MotionReducerValue = new FloatValue("AACv4MotionReducer", 0.62F, 0.0F, 1.0F);
    private final BoolValue legitStrafeValue = new BoolValue("LegitStrafe", false);
    private final BoolValue legitFaceValue = new BoolValue("LegitFace", true);
    private MSTimer velocityTimer = new MSTimer();
    private boolean velocityInput;
    private int velocityTick;
    private boolean huayutingjumpflag;
    private boolean reverseHurt;
    private boolean jump;

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    public void onDisable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            ientityplayersp.setSpeedInAir(0.02F);
        }

    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (!thePlayer.isInWater() && !thePlayer.isInLava() && !thePlayer.isInWeb()) {
                String s = (String) this.modeValue.get();
                boolean reduce = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                } else {
                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    float reduce1;
                    IINetHandlerPlayClient iinethandlerplayclient;
                    IClassProvider iclassprovider;
                    double d0;
                    IEntityPlayerSP ientityplayersp1;

                    switch (s.hashCode()) {
                    case -1970553484:
                        if (s.equals("smoothreverse")) {
                            if (!this.velocityInput) {
                                thePlayer.setSpeedInAir(0.02F);
                                return;
                            }

                            if (thePlayer.getHurtTime() > 0) {
                                this.reverseHurt = true;
                            }

                            if (!thePlayer.getOnGround()) {
                                if (this.reverseHurt) {
                                    thePlayer.setSpeedInAir(((Number) this.reverse2StrengthValue.get()).floatValue());
                                }
                            } else if (this.velocityTimer.hasTimePassed(80L)) {
                                this.velocityInput = false;
                                this.reverseHurt = false;
                            }
                        }
                        break;

                    case -1810282708:
                        if (s.equals("huayutingjump")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getHurtTime() > 0 && this.huayutingjumpflag) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getOnGround()) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getHurtTime() <= 6) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.600151164D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.600151164D);
                                    }

                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getHurtTime() <= 4) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.700151164D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.700151164D);
                                    }
                                } else {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getHurtTime() <= 9) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.6001421204D);
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.6001421204D);
                                    }
                                }

                                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                iclassprovider = MinecraftInstance.classProvider;
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SNEAKING));
                                this.huayutingjumpflag = false;
                            }
                        }
                        break;

                    case -1777040898:
                        if (s.equals("huayuting")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getHurtTime() > 0 && this.velocityInput) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getOnGround()) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionX();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(d0 * ientityplayersp1.getMotionX() * 0.56D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionY();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionY(d0 * ientityplayersp1.getMotionX() * 0.77D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionZ();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(d0 * ientityplayersp1.getMotionX() * 0.56D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setOnGround(false);
                                } else {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionX();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(d0 * ientityplayersp1.getMotionX() * 0.77D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setOnGround(true);
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionZ();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(d0 * ientityplayersp1.getMotionZ() * 0.77D * Math.random());
                                }

                                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                iclassprovider = MinecraftInstance.classProvider;
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SNEAKING));
                                this.velocityInput = false;
                            }
                        }
                        break;

                    case -1513652168:
                        if (s.equals("aac5reduce")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getHurtTime() > 1 && this.velocityInput) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.81D);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.81D);
                            }

                            if (this.velocityInput) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getHurtTime() >= 5) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (!ientityplayersp.getOnGround()) {
                                        return;
                                    }
                                }

                                if (this.velocityTimer.hasTimePassed(120L)) {
                                    this.velocityInput = false;
                                }
                            }
                        }
                        break;

                    case -1243181771:
                        if (s.equals("glitch")) {
                            thePlayer.setNoClip(this.velocityInput);
                            if (thePlayer.getHurtTime() == 7) {
                                thePlayer.setMotionY(0.4D);
                            }

                            this.velocityInput = false;
                        }
                        break;

                    case -1234547235:
                        if (s.equals("aacpush")) {
                            if (this.jump) {
                                if (thePlayer.getOnGround()) {
                                    this.jump = false;
                                }
                            } else {
                                if (thePlayer.getHurtTime() > 0 && thePlayer.getMotionX() != 0.0D && thePlayer.getMotionZ() != 0.0D) {
                                    thePlayer.setOnGround(true);
                                }

                                if (thePlayer.getHurtResistantTime() > 0 && ((Boolean) this.aacPushYReducerValue.get()).booleanValue() && !LiquidBounce.INSTANCE.getModuleManager().get(Speed.class).getState()) {
                                    thePlayer.setMotionY(thePlayer.getMotionY() - 0.014999993D);
                                }
                            }

                            if (thePlayer.getHurtResistantTime() >= 19) {
                                reduce1 = ((Number) this.aacPushXZReducerValue.get()).floatValue();
                                thePlayer.setMotionX(thePlayer.getMotionX() / (double) reduce1);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() / (double) reduce1);
                            }
                        }
                        break;

                    case -1234264725:
                        if (s.equals("aaczero")) {
                            if (thePlayer.getHurtTime() > 0) {
                                if (!this.velocityInput || thePlayer.getOnGround() || thePlayer.getFallDistance() > 2.0F) {
                                    return;
                                }

                                thePlayer.setMotionY(thePlayer.getMotionY() - 1.0D);
                                thePlayer.setAirBorne(true);
                                thePlayer.setOnGround(true);
                            } else {
                                this.velocityInput = false;
                            }
                        }
                        break;

                    case -805359837:
                        if (s.equals("vulcan")) {
                            if (this.velocityTick > 10) {
                                if (thePlayer.getMotionY() > (double) 0) {
                                    thePlayer.setMotionY(0.0D);
                                }

                                thePlayer.setMotionX(0.0D);
                                thePlayer.setMotionZ(0.0D);
                                thePlayer.setJumpMovementFactor(-1.0E-5F);
                                this.velocityInput = false;
                            }

                            if (thePlayer.getOnGround() && this.velocityTick > 1) {
                                this.velocityInput = false;
                            }
                        }
                        break;

                    case -210707537:
                        if (s.equals("HytJumpNew")) {
                            if (thePlayer.getHurtTime() > 0 && !thePlayer.getOnGround()) {
                                thePlayer.setMotionZ(0.0D);
                                thePlayer.getLastTickPosZ();
                                thePlayer.setMotionY(0.5D);
                                thePlayer.setMotionX(0.0D);
                            }

                            if (thePlayer.getHurtTime() > 5 && !thePlayer.getOnGround()) {
                                thePlayer.setMotionZ(0.8355555555555555D);
                                thePlayer.getLastTickPosZ();
                                thePlayer.setMotionY(0.5D);
                                thePlayer.setMotionX(0.835555555555555D);
                            }

                            if (thePlayer.getHurtTime() > 10 && !thePlayer.getOnGround()) {
                                thePlayer.setMotionZ(0.5D);
                                thePlayer.getLastTickPosZ();
                                thePlayer.setMotionY(0.5D);
                                thePlayer.setMotionX(0.5D);
                                thePlayer.getLastTickPosX();
                            }
                        }
                        break;

                    case 96323:
                        if (s.equals("aac") && this.velocityInput && this.velocityTimer.hasTimePassed(80L)) {
                            thePlayer.setMotionX(thePlayer.getMotionX() * ((Number) this.horizontalValue.get()).doubleValue());
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * ((Number) this.horizontalValue.get()).doubleValue());
                            this.velocityInput = false;
                        }
                        break;

                    case 2603186:
                        if (s.equals("Test")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getHurtTime() > 9 && this.velocityInput) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getOnGround()) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionX();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(d0 * ientityplayersp1.getMotionX() * 0.55D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionY();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionY(d0 * ientityplayersp1.getMotionX() * 0.64D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionZ();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(d0 * ientityplayersp1.getMotionX() * 0.55D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setOnGround(false);
                                } else {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionX();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(d0 * ientityplayersp1.getMotionX() * 0.77D * Math.random());
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setOnGround(true);
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d0 = ientityplayersp.getMotionZ();
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(d0 * ientityplayersp1.getMotionZ() * 0.77D * Math.random());
                                }

                                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                iclassprovider = MinecraftInstance.classProvider;
                                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SNEAKING));
                                this.velocityInput = false;
                            }

                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getHurtTime() <= 4) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.700151164D);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.700151164D);
                            }

                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            int reduce2 = ientityplayersp1.getHurtTime();

                            if (5 <= reduce2) {
                                if (9 >= reduce2) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.6001421204D);
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.6001421204D);
                                }
                            }
                        }
                        break;

                    case 3273774:
                        if (s.equals("jump") && thePlayer.getHurtTime() > 0 && thePlayer.getOnGround()) {
                            thePlayer.setMotionY(0.42D);
                            reduce1 = thePlayer.getRotationYaw() * 0.017453292F;
                            double d1 = thePlayer.getMotionX();
                            boolean flag = false;
                            float f = (float) Math.sin((double) reduce1);

                            thePlayer.setMotionX(d1 - (double) f * 0.2D);
                            d1 = thePlayer.getMotionZ();
                            flag = false;
                            f = (float) Math.cos((double) reduce1);
                            thePlayer.setMotionZ(d1 + (double) f * 0.2D);
                        }
                        break;

                    case 3559837:
                        if (s.equals("tick")) {
                            if (this.velocityTick > ((Number) this.velocityTickValue.get()).intValue()) {
                                if (thePlayer.getMotionY() > (double) 0) {
                                    thePlayer.setMotionY(0.0D);
                                }

                                thePlayer.setMotionX(0.0D);
                                thePlayer.setMotionZ(0.0D);
                                thePlayer.setJumpMovementFactor(-1.0E-5F);
                                this.velocityInput = false;
                            }

                            if (thePlayer.getOnGround() && this.velocityTick > 1) {
                                this.velocityInput = false;
                            }
                        }
                        break;

                    case 92570113:
                        if (s.equals("aacv4") && thePlayer.getHurtTime() > 0 && !thePlayer.getOnGround()) {
                            reduce1 = ((Number) this.aacv4MotionReducerValue.get()).floatValue();
                            thePlayer.setMotionX(thePlayer.getMotionX() * (double) reduce1);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * (double) reduce1);
                        }
                        break;

                    case 1099846370:
                        if (s.equals("reverse")) {
                            if (!this.velocityInput) {
                                return;
                            }

                            if (!thePlayer.getOnGround()) {
                                MovementUtils.strafe(MovementUtils.INSTANCE.getSpeed() * ((Number) this.reverseStrengthValue.get()).floatValue());
                            } else if (this.velocityTimer.hasTimePassed(80L)) {
                                this.velocityInput = false;
                            }
                        }
                        break;

                    case 1845586417:
                        if (s.equals("newaac4") && thePlayer.getHurtTime() > 0 && !thePlayer.getOnGround()) {
                            reduce1 = ((Number) this.aac4XZReducerValue.get()).floatValue();
                            thePlayer.setMotionX(thePlayer.getMotionX() / (double) reduce1);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() / (double) reduce1);
                        }
                    }

                }
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            IPacket packet = event.getPacket();

            if (MinecraftInstance.classProvider.isSPacketEntityVelocity(packet)) {
                ISPacketEntityVelocity packetEntityVelocity = packet.asSPacketEntityVelocity();
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient != null) {
                    IEntity ientity = iworldclient.getEntityByID(packetEntityVelocity.getEntityID());

                    if (ientity != null) {
                        if (Intrinsics.areEqual(ientity, thePlayer) ^ true) {
                            return;
                        }

                        this.velocityTimer.reset();
                        String s = (String) this.modeValue.get();
                        boolean yaw = false;

                        if (s == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        label261: {
                            String s1 = s.toLowerCase();

                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                            s = s1;
                            float vertical;
                            float yaw1;
                            IINetHandlerPlayClient iinethandlerplayclient;
                            IClassProvider iclassprovider;
                            IEntityPlayerSP ientityplayersp1;
                            IEntityPlayerSP ientityplayersp2;

                            switch (s.hashCode()) {
                            case -1970553484:
                                if (!s.equals("smoothreverse")) {
                                    if (!s.equals("smoothreverse")) {
                                        return;
                                    }
                                    break label261;
                                }
                                break;

                            case -1810282708:
                                if (s.equals("huayutingjump") && packet instanceof SPacketEntityVelocity) {
                                    this.huayutingjumpflag = true;
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getHurtTime() != 0) {
                                        event.cancelEvent();
                                        ((SPacketEntityVelocity) packet).motionX = 0;
                                        ((SPacketEntityVelocity) packet).motionY = 0;
                                        ((SPacketEntityVelocity) packet).motionZ = 0;
                                        return;
                                    }
                                }

                                return;

                            case -1777040898:
                                if (s.equals("huayuting")) {
                                    label262: {
                                        int yaw2 = packetEntityVelocity.getMotionX();

                                        if (-150 <= yaw2) {
                                            if (150 >= yaw2) {
                                                break label262;
                                            }
                                        }

                                        yaw2 = packetEntityVelocity.getMotionY();
                                        if (-150 <= yaw2) {
                                            if (150 >= yaw2) {
                                                break label262;
                                            }
                                        }

                                        yaw2 = packetEntityVelocity.getMotionZ();
                                        if (-150 <= yaw2) {
                                            if (150 >= yaw2) {
                                                break label262;
                                            }
                                        }

                                        event.cancelEvent();
                                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                        iclassprovider = MinecraftInstance.classProvider;
                                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp1 == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp1, ICPacketEntityAction.WAction.START_SNEAKING));
                                        return;
                                    }

                                    packetEntityVelocity.setMotionX((int) ((double) packetEntityVelocity.getMotionX() * 0.0D));
                                    packetEntityVelocity.setMotionY((int) ((double) packetEntityVelocity.getMotionY() * 0.0D));
                                    packetEntityVelocity.setMotionZ((int) ((double) packetEntityVelocity.getMotionZ() * 0.0D));
                                }

                                return;

                            case -1513652168:
                                if (!s.equals("aac5reduce")) {
                                    return;
                                }
                                break;

                            case -1367724422:
                                if (s.equals("cancel")) {
                                    event.cancelEvent();
                                }

                                return;

                            case -1243181771:
                                if (s.equals("glitch")) {
                                    if (!thePlayer.getOnGround()) {
                                        return;
                                    }

                                    this.velocityInput = true;
                                    event.cancelEvent();
                                }

                                return;

                            case -1234264725:
                                if (!s.equals("aaczero")) {
                                    if (!s.equals("aaczero")) {
                                        return;
                                    }
                                    break label261;
                                }
                                break;

                            case -1110057610:
                                if (s.equals("langya") && thePlayer.getOnGround()) {
                                    this.velocityInput = true;
                                    yaw1 = thePlayer.getRotationYaw() * 0.017453292F;
                                    packetEntityVelocity.setMotionX((int) ((double) packetEntityVelocity.getMotionX() * 0.8114514191981D));
                                    packetEntityVelocity.setMotionZ((int) ((double) packetEntityVelocity.getMotionZ() * 0.0114514191981D));
                                    double d0 = thePlayer.getMotionX();
                                    boolean vertical1 = false;
                                    float f = (float) Math.sin((double) yaw1);

                                    thePlayer.setMotionX(d0 - (double) f * 0.251111D);
                                    d0 = thePlayer.getMotionZ();
                                    vertical1 = false;
                                    f = (float) Math.cos((double) yaw1);
                                    thePlayer.setMotionZ(d0 + (double) f * 0.251111D);
                                }

                                return;

                            case -902286926:
                                if (s.equals("simple")) {
                                    yaw1 = ((Number) this.horizontalValue.get()).floatValue();
                                    vertical = ((Number) this.verticalValue.get()).floatValue();
                                    if (yaw1 == 0.0F && vertical == 0.0F) {
                                        event.cancelEvent();
                                    }

                                    packetEntityVelocity.setMotionX((int) ((float) packetEntityVelocity.getMotionX() * yaw1));
                                    packetEntityVelocity.setMotionY((int) ((float) packetEntityVelocity.getMotionY() * vertical));
                                    packetEntityVelocity.setMotionZ((int) ((float) packetEntityVelocity.getMotionZ() * yaw1));
                                }

                                return;

                            case -805359837:
                                if (s.equals("vulcan")) {
                                    this.velocityInput = true;
                                    yaw1 = ((Number) this.horizontalValue.get()).floatValue();
                                    vertical = ((Number) this.verticalValue.get()).floatValue();
                                    if (yaw1 == 0.0F && vertical == 0.0F) {
                                        event.cancelEvent();
                                    }

                                    packetEntityVelocity.setMotionX((int) ((float) packetEntityVelocity.getMotionX() * yaw1));
                                    packetEntityVelocity.setMotionY((int) ((float) packetEntityVelocity.getMotionY() * vertical));
                                    packetEntityVelocity.setMotionZ((int) ((float) packetEntityVelocity.getMotionZ() * yaw1));
                                }

                                return;

                            case 96323:
                                if (!s.equals("aac")) {
                                    if (!s.equals("aac")) {
                                        return;
                                    }
                                    break label261;
                                }
                                break;

                            case 3559837:
                                if (s.equals("tick")) {
                                    this.velocityInput = true;
                                    event.cancelEvent();
                                }

                                return;

                            case 102851513:
                                if (s.equals("legit")) {
                                    Fucker fucker = Fucker.INSTANCE;
                                    WBlockPos wblockpos = new WBlockPos;
                                    IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                                    if (ientityplayersp3 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    double d1 = ientityplayersp3.getPosX();

                                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp2 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    double d2 = ientityplayersp2.getPosY();
                                    IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

                                    if (ientityplayersp4 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    wblockpos.<init>(d1, d2, ientityplayersp4.getPosZ());
                                    fucker.setPos(wblockpos);
                                }

                                return;

                            case 233102203:
                                if (s.equals("vanilla")) {
                                    event.cancelEvent();
                                }

                                return;

                            case 327074264:
                                if (s.equals("aac5.2.0")) {
                                    event.cancelEvent();
                                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                    iclassprovider = MinecraftInstance.classProvider;
                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    double d3 = ientityplayersp1.getPosX();

                                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp2 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d3, Double.MAX_VALUE, ientityplayersp2.getPosZ(), true));
                                }

                                return;

                            case 367116670:
                                if (!s.equals("NikoNiko")) {
                                    return;
                                }
                                break;

                            case 1099846370:
                                if (!s.equals("reverse")) {
                                    if (!s.equals("reverse")) {
                                        return;
                                    }
                                    break label261;
                                }
                                break;

                            default:
                                return;
                            }

                            this.velocityInput = true;
                            return;
                        }

                        this.velocityInput = true;
                        return;
                    }
                }

                return;
            } else if (MinecraftInstance.classProvider.isSPacketExplosion(packet)) {
                event.cancelEvent();
            }

        }
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp;

        if (((Boolean) this.onlyGroundValue.get()).booleanValue()) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (!ientityplayersp.getOnGround()) {
                return;
            }
        }

        if (!((Boolean) this.onlyCombatValue.get()).booleanValue() || LiquidBounce.INSTANCE.getCombatManager().getInCombat()) {
            String s = (String) this.modeValue.get();
            boolean rot = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case 102851513:
                    if (s.equals("legit")) {
                        if (Fucker.INSTANCE.getPos() != null) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getHurtTime() > 0) {
                                WBlockPos wblockpos = Fucker.INSTANCE.getPos();

                                if (wblockpos == null) {
                                    Intrinsics.throwNpe();
                                }

                                double d0 = (double) wblockpos.getX();
                                WBlockPos wblockpos1 = Fucker.INSTANCE.getPos();

                                if (wblockpos1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                double d1 = (double) wblockpos1.getY();
                                WBlockPos wblockpos2 = Fucker.INSTANCE.getPos();

                                if (wblockpos2 == null) {
                                    Intrinsics.throwNpe();
                                }

                                Rotation rot1 = RotationUtils.getRotations(d0, d1, (double) wblockpos2.getZ());

                                if (((Boolean) this.legitFaceValue.get()).booleanValue()) {
                                    RotationUtils.setTargetRotation(rot1);
                                }

                                float yaw = rot1.getYaw();
                                float strafe;

                                if (((Boolean) this.legitStrafeValue.get()).booleanValue()) {
                                    strafe = MovementUtils.INSTANCE.getSpeed();
                                    double forward = Math.toRadians((double) yaw);

                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(-Math.sin(forward) * (double) strafe);
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(Math.cos(forward) * (double) strafe);
                                } else {
                                    strafe = event.getStrafe();
                                    float forward1 = event.getForward();
                                    float friction = event.getFriction();
                                    float f = strafe * strafe + forward1 * forward1;

                                    if (f >= 1.0E-4F) {
                                        f = MathHelper.sqrt(f);
                                        if (f < 1.0F) {
                                            f = 1.0F;
                                        }

                                        f = friction / f;
                                        strafe *= f;
                                        forward1 *= f;
                                        float yawSin = MathHelper.sin((float) ((double) yaw * 3.141592653589793D / (double) 180.0F));
                                        float yawCos = MathHelper.cos((float) ((double) yaw * 3.141592653589793D / (double) 180.0F));

                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionX(ientityplayersp.getMotionX() + (double) (strafe * yawCos - forward1 * yawSin));
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() + (double) (forward1 * yawCos + strafe * yawSin));
                                        return;
                                    }
                                }

                                return;
                            }
                        }

                        return;
                    }

                default:
                }
            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null && !thePlayer.isInWater() && !thePlayer.isInLava() && !thePlayer.isInWeb()) {
            String s = (String) this.modeValue.get();
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case -1234547235:
                    if (s.equals("aacpush")) {
                        this.jump = true;
                        if (!thePlayer.isCollidedVertically()) {
                            event.cancelEvent();
                        }
                    }
                    break;

                case -1234264725:
                    if (s.equals("aaczero") && thePlayer.getHurtTime() > 0) {
                        event.cancelEvent();
                    }
                }

            }
        }
    }
}
