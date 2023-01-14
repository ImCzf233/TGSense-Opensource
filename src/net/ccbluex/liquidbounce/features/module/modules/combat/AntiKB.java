package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "AntiKnockback",
    description = "使你改变自己受到的击�?",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0007J\b\u00104\u001a\u000201H\u0016J\u0010\u00105\u001a\u0002012\u0006\u00102\u001a\u000206H\u0007J\u0010\u00107\u001a\u0002012\u0006\u00102\u001a\u000208H\u0007J\u0010\u00109\u001a\u0002012\u0006\u00102\u001a\u00020:H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\'R\u000e\u0010(\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AntiKB;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aac4XZReducerValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacPushXZReducerValue", "aacPushYReducerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "alerts", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "getBlock", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "setBlock", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;)V", "canCancelJump", "", "canCleanJump", "cobwebValue", "customC06FakeLag", "customX", "customY", "customYStart", "customZ", "horizontalValue", "hytGround", "hytpacketaset", "hytpacketbset", "jump", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "newaac4XZReducerValue", "noFireValue", "reverse2StrengthValue", "reverseHurt", "reverseStrengthValue", "tag", "", "getTag", "()Ljava/lang/String;", "velocityInput", "velocityTick", "", "velocityTickValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "velocityTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "verticalValue", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AntiKB extends Module {

    private final FloatValue horizontalValue = new FloatValue("Horizontal", 0.0F, 0.0F, 1.0F);
    private final FloatValue verticalValue = new FloatValue("Vertical", 0.0F, 0.0F, 1.0F);
    private final BoolValue alerts = new BoolValue("alerts", true);
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Custom", "AAC4", "Simple", "SimpleFix", "AAC", "AACPush", "AACZero", "Reverse", "SmoothReverse", "Hytplus", "AAC5Reduce", "HytPacketA", "Glitch", "HytCancel", "HytTick", "Vanilla", "HytTest", "HytNewTest", "HytPacket", "NewAAC4", "Hyt", "FeiLe", "HytMotion", "NewHytMotion", "HytPacketB", "HytMotionB", "HytPacketFix", "aac4reduce", "HytBest", "hytplus"}, "Vanilla");
    private final FloatValue aac4XZReducerValue = new FloatValue("AAC4XZReducer", 1.36F, 1.0F, 3.0F);
    private final FloatValue newaac4XZReducerValue = new FloatValue("NewAAC4XZReducer", 0.45F, 0.0F, 1.0F);
    private final IntegerValue velocityTickValue = new IntegerValue("VelocityTick", 1, 0, 10);
    private final FloatValue reverseStrengthValue = new FloatValue("ReverseStrength", 1.0F, 0.1F, 1.0F);
    private final FloatValue reverse2StrengthValue = new FloatValue("SmoothReverseStrength", 0.05F, 0.02F, 0.1F);
    private final FloatValue hytpacketaset = new FloatValue("HytPacketASet", 0.35F, 0.1F, 1.0F);
    private final FloatValue hytpacketbset = new FloatValue("HytPacketBSet", 0.5F, 1.0F, 1.0F);
    private final FloatValue aacPushXZReducerValue = new FloatValue("AACPushXZReducer", 2.0F, 1.0F, 3.0F);
    private final BoolValue aacPushYReducerValue = new BoolValue("AACPushYReducer", true);
    @Nullable
    private IBlock block;
    private final BoolValue noFireValue = new BoolValue("noFire", false);
    private final BoolValue cobwebValue = new BoolValue("NoCobweb", true);
    private final BoolValue hytGround = new BoolValue("HytOnlyGround", true);
    private final FloatValue customX = new FloatValue("CustomX", 0.0F, 0.0F, 1.0F);
    private final BoolValue customYStart = new BoolValue("CanCustomY", false);
    private final FloatValue customY = new FloatValue("CustomY", 1.0F, 1.0F, 2.0F);
    private final FloatValue customZ = new FloatValue("CustomZ", 0.0F, 0.0F, 1.0F);
    private final BoolValue customC06FakeLag = new BoolValue("CustomC06FakeLag", false);
    private MSTimer velocityTimer = new MSTimer();
    private boolean velocityInput;
    private boolean canCleanJump;
    private int velocityTick;
    private boolean reverseHurt;
    private boolean jump;
    private boolean canCancelJump;

    @Nullable
    public final IBlock getBlock() {
        return this.block;
    }

    public final void setBlock(@Nullable IBlock <set-?>) {
        this.block = <set-?>;
    }

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
                if (((Boolean) this.noFireValue.get()).booleanValue()) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.isBurning()) {
                        return;
                    }
                }

                String s = (String) this.modeValue.get();
                boolean reduce = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                } else {
                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    float reduce1;

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

                    case -1513652168:
                        if (s.equals("aac5reduce")) {
                            if (thePlayer.getHurtTime() > 1 && this.velocityInput) {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.81D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.81D);
                            }

                            if (this.velocityInput && (thePlayer.getHurtTime() < 5 || thePlayer.getOnGround()) && this.velocityTimer.hasTimePassed(120L)) {
                                this.velocityInput = false;
                            }
                        }
                        break;

                    case -1466691239:
                        if (s.equals("newhytmotion") && thePlayer.getHurtTime() > 0 && !thePlayer.isDead() && !thePlayer.getOnGround()) {
                            if (!thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.47188D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.47188D);
                                if (thePlayer.getMotionY() == 0.42D || thePlayer.getMotionY() > 0.42D) {
                                    thePlayer.setMotionY(thePlayer.getMotionY() * 0.4D);
                                }
                            } else {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.65025D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.65025D);
                                if (thePlayer.getMotionY() == 0.42D || thePlayer.getMotionY() > 0.42D) {
                                    thePlayer.setMotionY(thePlayer.getMotionY() * 0.4D);
                                }
                            }
                        }
                        break;

                    case -1371801463:
                        if (s.equals("hytmotionb") && thePlayer.getHurtTime() > 0 && !thePlayer.isDead() && !thePlayer.getOnGround() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
                            thePlayer.setMotionX(thePlayer.getMotionX() * (double) 0.451145F);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * (double) 0.451145F);
                        }
                        break;

                    case -1349088399:
                        if (s.equals("custom") && thePlayer.getHurtTime() > 0 && !thePlayer.isDead()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (!ientityplayersp.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (!ientityplayersp.isInWater()) {
                                    thePlayer.setMotionX(thePlayer.getMotionX() * ((Number) this.customX.get()).doubleValue());
                                    thePlayer.setMotionZ(thePlayer.getMotionZ() * ((Number) this.customZ.get()).doubleValue());
                                    if (((Boolean) this.customYStart.get()).booleanValue()) {
                                        thePlayer.setMotionY(thePlayer.getMotionY() / ((Number) this.customY.get()).doubleValue());
                                    }

                                    if (((Boolean) this.customC06FakeLag.get()).booleanValue()) {
                                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosLook(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), thePlayer.getRotationYaw(), thePlayer.getRotationPitch(), thePlayer.getOnGround()));
                                    }
                                }
                            }
                        }
                        break;

                    case -1245889049:
                        if (s.equals("HytBest") && thePlayer.getHurtTime() > 0 && !thePlayer.getOnGround()) {
                            thePlayer.setMotionX(thePlayer.getMotionX() / (double) 1);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() / (double) 1);
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

                                if (thePlayer.getHurtResistantTime() > 0 && ((Boolean) this.aacPushYReducerValue.get()).booleanValue()) {
                                    Module module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);

                                    if (module == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (!module.getState()) {
                                        thePlayer.setMotionY(thePlayer.getMotionY() - 0.014999993D);
                                    }
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

                    case 96323:
                        if (s.equals("aac") && this.velocityInput && this.velocityTimer.hasTimePassed(80L)) {
                            thePlayer.setMotionX(thePlayer.getMotionX() * ((Number) this.horizontalValue.get()).doubleValue());
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * ((Number) this.horizontalValue.get()).doubleValue());
                            this.velocityInput = false;
                        }
                        break;

                    case 2986065:
                        if (s.equals("aac4")) {
                            if (!thePlayer.getOnGround()) {
                                if (this.velocityInput) {
                                    thePlayer.setSpeedInAir(0.02F);
                                    thePlayer.setMotionX(thePlayer.getMotionX() * 0.6D);
                                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.6D);
                                }
                            } else if (this.velocityTimer.hasTimePassed(80L)) {
                                this.velocityInput = false;
                                thePlayer.setSpeedInAir(0.02F);
                            }
                        }
                        break;

                    case 97312387:
                        if (s.equals("feile") && thePlayer.getOnGround()) {
                            this.canCleanJump = true;
                            thePlayer.setMotionY(1.5D);
                            thePlayer.setMotionZ(1.2D);
                            thePlayer.setMotionX(1.5D);
                            if (thePlayer.getOnGround() && this.velocityTick > 2) {
                                this.velocityInput = false;
                            }
                        }
                        break;

                    case 232843001:
                        if (s.equals("hytmotion")) {
                            if (((Boolean) this.hytGround.get()).booleanValue()) {
                                if (thePlayer.getHurtTime() > 0 && !thePlayer.isDead() && thePlayer.getHurtTime() <= 5 && thePlayer.getOnGround()) {
                                    thePlayer.setMotionX(thePlayer.getMotionX() * 0.4D);
                                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.4D);
                                    thePlayer.setMotionY(thePlayer.getMotionY() * (double) 0.381145F);
                                    thePlayer.setMotionY(thePlayer.getMotionY() / (double) 1.781145F);
                                }
                            } else if (thePlayer.getHurtTime() > 0 && !thePlayer.isDead() && thePlayer.getHurtTime() <= 5) {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.4D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.4D);
                                thePlayer.setMotionY(thePlayer.getMotionY() * (double) 0.381145F);
                                thePlayer.setMotionY(thePlayer.getMotionY() / (double) 1.781145F);
                            }
                        }
                        break;

                    case 305296331:
                        if (s.equals("hytpacket")) {
                            if (((Boolean) this.hytGround.get()).booleanValue()) {
                                if (thePlayer.getHurtTime() > 0 && !thePlayer.isDead() && thePlayer.getHurtTime() <= 5 && thePlayer.getOnGround()) {
                                    thePlayer.setMotionX(thePlayer.getMotionX() * 0.5D);
                                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.5D);
                                    thePlayer.setMotionY(thePlayer.getMotionY() / (double) 1.781145F);
                                }
                            } else if (thePlayer.getHurtTime() > 0 && !thePlayer.isDead() && thePlayer.getHurtTime() <= 5) {
                                thePlayer.setMotionX(thePlayer.getMotionX() * 0.5D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.5D);
                                thePlayer.setMotionY(thePlayer.getMotionY() / (double) 1.781145F);
                            }
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

                    case 1385802141:
                        if (s.equals("hytplus")) {
                            if (thePlayer.getHurtTime() > 0 && thePlayer.getOnGround() && LiquidBounce.INSTANCE.getCombatManager().getTarget() != null) {
                                thePlayer.setMotionY(0.42D);
                                reduce1 = thePlayer.getRotationYaw() * 0.017453292F;
                                double d0 = thePlayer.getMotionX();
                                boolean flag = false;
                                float f = (float) Math.sin((double) reduce1);

                                thePlayer.setMotionX(d0 - (double) f * 0.2D);
                                d0 = thePlayer.getMotionZ();
                                flag = false;
                                f = (float) Math.cos((double) reduce1);
                                thePlayer.setMotionZ(d0 + (double) f * 0.2D);
                            }
                        } else if (s.equals("hytplus") && thePlayer.getHurtTime() > 0 && thePlayer.getOnGround()) {
                            thePlayer.setMotionY(0.42D);
                            reduce1 = thePlayer.getRotationYaw() * 0.017453292F;
                            thePlayer.setMotionX(thePlayer.getMotionX() * (double) 0);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * (double) 0);
                        }
                        break;

                    case 1385917856:
                        if (s.equals("hyttick")) {
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

                    case 1893811447:
                        if (s.equals("aac4reduce")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (ientityplayersp.getHurtTime() > 0) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (!ientityplayersp.getOnGround() && this.velocityInput && this.velocityTimer.hasTimePassed(80L)) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(ientityplayersp.getMotionX() * 0.62D);
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(ientityplayersp.getMotionZ() * 0.62D);
                                }
                            }

                            if (this.velocityInput) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getHurtTime() >= 4) {
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
                    }

                }
            }
        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.block = event.getBlock();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            IPacket packet = event.getPacket();

            if (event.isCancelled() && event.getPacket() instanceof SPacketPlayerPosLook) {
                this.onDisable();
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Information", "Velocity was disabled to prevent flags/erros", NotifyType.WARNING, 0, 0, 24, (DefaultConstructorMarker) null));
            }

            if (MinecraftInstance.classProvider.isSPacketEntityVelocity(packet)) {
                ISPacketEntityVelocity packetEntityVelocity = packet.asSPacketEntityVelocity();

                if (((Boolean) this.noFireValue.get()).booleanValue()) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.isBurning()) {
                        return;
                    }
                }

                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient != null) {
                    IEntity ientity = iworldclient.getEntityByID(packetEntityVelocity.getEntityID());

                    if (ientity != null) {
                        if (Intrinsics.areEqual(ientity, thePlayer) ^ true) {
                            return;
                        }

                        this.velocityTimer.reset();
                        String s = (String) this.modeValue.get();
                        boolean horizontal = false;

                        if (s == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        String s1 = s.toLowerCase();

                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                        s = s1;
                        float vertical;
                        float horizontal1;

                        switch (s.hashCode()) {
                        case -1970553484:
                            if (!s.equals("smoothreverse")) {
                                return;
                            }
                            break;

                        case -1657634710:
                            if (s.equals("hytpacketfix")) {
                                if (thePlayer.getHurtTime() > 0 && !thePlayer.isDead()) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (!ientityplayersp.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        if (!ientityplayersp.isInWater()) {
                                            thePlayer.setMotionX(thePlayer.getMotionX() * 0.4D);
                                            thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.4D);
                                            thePlayer.setMotionY(thePlayer.getMotionY() / (double) 1.45F);
                                        }
                                    }
                                }

                                if (thePlayer.getHurtTime() < 1) {
                                    packetEntityVelocity.setMotionY(0);
                                }

                                if (thePlayer.getHurtTime() < 5) {
                                    packetEntityVelocity.setMotionX(0);
                                    packetEntityVelocity.setMotionZ(0);
                                    return;
                                }
                            }

                            return;

                        case -1513652168:
                            if (!s.equals("aac5reduce")) {
                                return;
                            }
                            break;

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
                                return;
                            }
                            break;

                        case -902286926:
                            if (s.equals("simple")) {
                                horizontal1 = ((Number) this.horizontalValue.get()).floatValue();
                                vertical = ((Number) this.verticalValue.get()).floatValue();
                                if (horizontal1 == 0.0F && vertical == 0.0F) {
                                    event.cancelEvent();
                                }

                                packetEntityVelocity.setMotionX((int) ((float) packetEntityVelocity.getMotionX() * horizontal1));
                                packetEntityVelocity.setMotionY((int) ((float) packetEntityVelocity.getMotionY() * vertical));
                                packetEntityVelocity.setMotionZ((int) ((float) packetEntityVelocity.getMotionZ() * horizontal1));
                            }

                            return;

                        case -767500465:
                            if (s.equals("hytnewtest") && thePlayer.getOnGround()) {
                                this.velocityInput = true;
                                horizontal1 = thePlayer.getRotationYaw() * 0.017453292F;
                                packetEntityVelocity.setMotionX((int) ((double) packetEntityVelocity.getMotionX() * 0.75D));
                                packetEntityVelocity.setMotionZ((int) ((double) packetEntityVelocity.getMotionZ() * 0.75D));
                                double d0 = thePlayer.getMotionX();
                                boolean vertical1 = false;
                                float f = (float) Math.sin((double) horizontal1);

                                thePlayer.setMotionX(d0 - (double) f * 0.2D);
                                d0 = thePlayer.getMotionZ();
                                vertical1 = false;
                                f = (float) Math.cos((double) horizontal1);
                                thePlayer.setMotionZ(d0 + (double) f * 0.2D);
                            }

                            return;

                        case -66562627:
                            if (s.equals("hytcancel")) {
                                event.cancelEvent();
                            }

                            return;

                        case 96323:
                            if (!s.equals("aac")) {
                                return;
                            }
                            break;

                        case 2986065:
                            if (!s.equals("aac4")) {
                                return;
                            }
                            break;

                        case 233102203:
                            if (s.equals("vanilla")) {
                                event.cancelEvent();
                            }

                            return;

                        case 874251766:
                            if (s.equals("hytpacketa")) {
                                packetEntityVelocity.setMotionX((int) ((double) ((float) packetEntityVelocity.getMotionX() * ((Number) this.hytpacketaset.get()).floatValue()) / 1.5D));
                                packetEntityVelocity.setMotionY((int) 0.7D);
                                packetEntityVelocity.setMotionZ((int) ((double) ((float) packetEntityVelocity.getMotionZ() * ((Number) this.hytpacketaset.get()).floatValue()) / 1.5D));
                                event.cancelEvent();
                            }

                            return;

                        case 874251767:
                            if (s.equals("hytpacketb")) {
                                packetEntityVelocity.setMotionX((int) ((double) ((float) packetEntityVelocity.getMotionX() * ((Number) this.hytpacketbset.get()).floatValue()) / 2.5D));
                                packetEntityVelocity.setMotionY((int) ((double) ((float) packetEntityVelocity.getMotionY() * ((Number) this.hytpacketbset.get()).floatValue()) / 2.5D));
                                packetEntityVelocity.setMotionZ((int) ((double) ((float) packetEntityVelocity.getMotionZ() * ((Number) this.hytpacketbset.get()).floatValue()) / 2.5D));
                            }

                            return;

                        case 1099846370:
                            if (!s.equals("reverse")) {
                                return;
                            }
                            break;

                        case 1385914517:
                            if (s.equals("hyttest") && thePlayer.getOnGround()) {
                                this.canCancelJump = false;
                                packetEntityVelocity.setMotionX((int) 0.985114D);
                                packetEntityVelocity.setMotionY((int) 0.885113D);
                                packetEntityVelocity.setMotionZ((int) 0.785112D);
                                thePlayer.setMotionX(thePlayer.getMotionX() / 1.75D);
                                thePlayer.setMotionZ(thePlayer.getMotionZ() / 1.75D);
                            }

                            return;

                        case 1385917856:
                            if (s.equals("hyttick")) {
                                this.velocityInput = true;
                                horizontal1 = 0.0F;
                                vertical = 0.0F;
                                event.cancelEvent();
                            }

                            return;

                        case 1845586417:
                            if (!s.equals("newaac4")) {
                                return;
                            }
                            break;

                        case 1893811447:
                            if (s.equals("aac4reduce")) {
                                this.velocityInput = true;
                                packetEntityVelocity.setMotionX((int) ((double) packetEntityVelocity.getMotionX() * 0.6D));
                                packetEntityVelocity.setMotionZ((int) ((double) packetEntityVelocity.getMotionZ() * 0.6D));
                            }

                            return;

                        default:
                            return;
                        }

                        this.velocityInput = true;
                        return;
                    }
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
                    break;

                case 2986065:
                    if (s.equals("aac4") && thePlayer.getHurtTime() > 0) {
                        event.cancelEvent();
                    }
                    break;

                case 1845586417:
                    if (s.equals("newaac4") && thePlayer.getHurtTime() > 0) {
                        this.velocityInput = false;
                        event.cancelEvent();
                    }
                }

            }
        }
    }
}
