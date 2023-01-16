package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.math.MathKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3i;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "NoFall",
    description = "Prevents you from taking fall damage.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010C\u001a\u00020\bJ\u0016\u0010D\u001a\u00020\u00042\u0006\u0010E\u001a\u00020\'2\u0006\u0010F\u001a\u00020\'J\u0006\u0010G\u001a\u00020\u0004J\u0006\u0010H\u001a\u00020\u0004J\b\u0010I\u001a\u00020JH\u0016J\u0012\u0010K\u001a\u00020J2\b\u0010L\u001a\u0004\u0018\u00010MH\u0007J\u0010\u0010N\u001a\u00020J2\u0006\u0010L\u001a\u00020OH\u0003J\u0010\u0010P\u001a\u00020J2\u0006\u0010L\u001a\u00020QH\u0007J\u0010\u0010R\u001a\u00020J2\u0006\u0010L\u001a\u00020SH\u0007J\u0012\u0010T\u001a\u00020J2\b\u0010L\u001a\u0004\u0018\u00010UH\u0007J\u0010\u0010V\u001a\u00020J2\u0006\u0010L\u001a\u00020WH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u000e\"\u0004\b\u001e\u0010\u0010R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00100\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u000e\"\u0004\b2\u0010\u0010R\u000e\u00103\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020908X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010;\u001a\u00020<8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u000e\u0010?\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010\u000e\"\u0004\bB\u0010\u0010¨\u0006X"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/NoFall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aac4Fakelag", "", "aac4FlagCooldown", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "aac4FlagCount", "", "aac5Check", "aac5Timer", "aac5doFlag", "canNoFall", "getCanNoFall", "()Z", "setCanNoFall", "(Z)V", "count", "getCount", "()I", "setCount", "(I)V", "currentMlgBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "currentMlgItemIndex", "currentMlgRotation", "Lnet/ccbluex/liquidbounce/utils/VecRotation;", "currentState", "doSpoof", "getDoSpoof", "setDoSpoof", "fakelag", "isDmgFalling", "jumped", "matrixCanSpoof", "matrixFallTicks", "matrixFlagWait", "matrixIsFall", "matrixLastMotionY", "", "matrixSend", "minFallDistance", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "mlgTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "needSpoof", "nextSpoof", "getNextSpoof", "setNextSpoof", "oldaacState", "packet1Count", "packetModify", "packetmodify", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "spartanTimer", "tag", "", "getTag", "()Ljava/lang/String;", "wasTimer", "worldChange", "getWorldChange", "setWorldChange", "getJumpEffect", "inAir", "height", "plus", "inVoid", "isBlockUnder", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotionUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class NoFall extends Module {

    @JvmField
    @NotNull
    public final ListValue modeValue = new ListValue("Mode", new String[] { "Vulcan", "AAC4", "SpoofGround", "NoGround", "Packet", "LoyisaAAC4.4.2", "aac5.0.14", "AAC4.4.X-Flag", "MLG", "AAC", "LAAC", "AAC3.3.11", "AAC3.3.15", "Spartan", "CubeCraft", "Hypixel"}, "SpoofGround");
    private final FloatValue minFallDistance = new FloatValue("MinMLGHeight", 5.0F, 2.0F, 50.0F);
    private final TickTimer spartanTimer = new TickTimer();
    private final TickTimer mlgTimer = new TickTimer();
    private int currentState;
    private boolean aac4Fakelag;
    private boolean jumped;
    private boolean isDmgFalling;
    private VecRotation currentMlgRotation;
    private int currentMlgItemIndex;
    private WBlockPos currentMlgBlock;
    private boolean fakelag;
    private boolean packetmodify;
    private final MSTimer aac4FlagCooldown = new MSTimer();
    private int aac4FlagCount;
    private final LinkedBlockingQueue packets = new LinkedBlockingQueue();
    private int oldaacState;
    private boolean packetModify;
    private boolean aac5doFlag;
    private boolean aac5Check;
    private int aac5Timer;
    private boolean needSpoof;
    private int packet1Count;
    private boolean matrixIsFall;
    private boolean matrixCanSpoof;
    private int matrixFallTicks;
    private double matrixLastMotionY;
    private int matrixFlagWait;
    private boolean wasTimer;
    private boolean matrixSend;
    private boolean canNoFall;
    private boolean nextSpoof;
    private boolean doSpoof;
    private boolean worldChange;
    private int count;

    public final boolean getCanNoFall() {
        return this.canNoFall;
    }

    public final void setCanNoFall(boolean <set-?>) {
        this.canNoFall = <set-?>;
    }

    public final boolean getNextSpoof() {
        return this.nextSpoof;
    }

    public final void setNextSpoof(boolean <set-?>) {
        this.nextSpoof = <set-?>;
    }

    public final boolean getDoSpoof() {
        return this.doSpoof;
    }

    public final void setDoSpoof(boolean <set-?>) {
        this.doSpoof = <set-?>;
    }

    public final boolean getWorldChange() {
        return this.worldChange;
    }

    public final void setWorldChange(boolean <set-?>) {
        this.worldChange = <set-?>;
    }

    public final int getCount() {
        return this.count;
    }

    public final void setCount(int <set-?>) {
        this.count = <set-?>;
    }

    public void onEnable() {
        if (StringsKt.equals((String) this.modeValue.get(), "AAC4", true)) {
            this.fakelag = false;
            this.packetmodify = false;
        }

        this.canNoFall = false;
        this.nextSpoof = false;
        this.doSpoof = false;
        this.worldChange = true;
        this.count = 0;
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getOnGround()) {
            this.jumped = false;
        }

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getMotionY() > (double) 0) {
            this.jumped = true;
        }

        if (this.getState()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(FreeCam.class);

            if (module == null) {
                Intrinsics.throwNpe();
            }

            if (!module.getState()) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
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
                    IClassProvider iclassprovider = MinecraftInstance.classProvider;
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d0 = ientityplayersp1.getEntityBoundingBox().getMaxX();
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d1 = ientityplayersp2.getEntityBoundingBox().getMaxY();
                    IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp3 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d2 = ientityplayersp3.getEntityBoundingBox().getMaxZ();
                    IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp4 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d3 = ientityplayersp4.getEntityBoundingBox().getMinX();
                    IEntityPlayerSP ientityplayersp5 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp5 == null) {
                        Intrinsics.throwNpe();
                    }

                    double d4 = ientityplayersp5.getEntityBoundingBox().getMinY() - 0.01D;
                    IEntityPlayerSP ientityplayersp6 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp6 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!BlockUtils.collideBlock(iclassprovider.createAxisAlignedBB(d0, d1, d2, d3, d4, ientityplayersp6.getEntityBoundingBox().getMinZ()), (Function1) (new Function1(1, MinecraftInstance.classProvider) {
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
                        String s = (String) this.modeValue.get();
                        boolean offsetYs = false;

                        if (s == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        String s1 = s.toLowerCase();

                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                        s = s1;
                        IINetHandlerPlayClient iinethandlerplayclient;
                        IClassProvider iclassprovider1;

                        switch (s.hashCode()) {
                        case -2011701869:
                            if (s.equals("spartan")) {
                                this.spartanTimer.update();
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if ((double) ientityplayersp.getFallDistance() > 1.5D && this.spartanTimer.hasTimePassed(10)) {
                                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                    iclassprovider1 = MinecraftInstance.classProvider;
                                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp2 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d1 = ientityplayersp2.getPosX();
                                    ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp3 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = ientityplayersp3.getPosY() + (double) 10;
                                    ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp4 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerPosition(d1, d2, ientityplayersp4.getPosZ(), true));
                                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                    iclassprovider1 = MinecraftInstance.classProvider;
                                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp2 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d1 = ientityplayersp2.getPosX();
                                    ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp3 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = ientityplayersp3.getPosY() - (double) 10;
                                    ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp4 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerPosition(d1, d2, ientityplayersp4.getPosZ(), true));
                                    this.spartanTimer.reset();
                                    return;
                                }
                            }

                            return;

                        case -1031473397:
                            if (s.equals("cubecraft")) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getFallDistance() > 2.0F) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setOnGround(false);
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(true));
                                    return;
                                }
                            }

                            return;

                        case -995865464:
                            if (s.equals("packet")) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getFallDistance() > 2.0F) {
                                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(true));
                                    return;
                                }
                            }

                            return;

                        case -805359837:
                            if (s.equals("vulcan")) {
                                if (this.worldChange) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getOnGround()) {
                                        this.worldChange = true;
                                    }

                                    return;
                                }

                                label523: {
                                    if (!this.canNoFall) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        if ((double) ientityplayersp.getFallDistance() > 3.25D) {
                                            this.canNoFall = true;
                                            break label523;
                                        }
                                    }

                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getOnGround()) {
                                        this.canNoFall = false;
                                    }
                                }

                                if (this.nextSpoof) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionY(-0.1D);
                                    MovementUtils.strafe(0.343F);
                                    this.nextSpoof = false;
                                }

                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if ((double) ientityplayersp.getFallDistance() > 3.65D) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setFallDistance(0.0F);
                                    this.count = MinecraftInstance.mc.isIntegratedServerRunning() ? 2 : 1;
                                    this.doSpoof = true;
                                    this.nextSpoof = true;
                                    return;
                                }
                            }

                            return;

                        case 96323:
                            if (s.equals("aac")) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getFallDistance() > 2.0F) {
                                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(true));
                                    this.currentState = 2;
                                } else if (this.currentState == 2) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getFallDistance() < (float) 2) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        ientityplayersp.setMotionY(0.1D);
                                        this.currentState = 3;
                                        return;
                                    }
                                }

                                switch (this.currentState) {
                                case 3:
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionY(0.1D);
                                    this.currentState = 4;
                                    return;

                                case 4:
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionY(0.1D);
                                    this.currentState = 5;
                                    return;

                                case 5:
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionY(0.1D);
                                    this.currentState = 1;
                                }
                            }

                            return;

                        case 3313751:
                            if (s.equals("laac") && !this.jumped) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getOnGround()) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (!ientityplayersp.isOnLadder()) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        if (!ientityplayersp.isInWater()) {
                                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                            if (ientityplayersp == null) {
                                                Intrinsics.throwNpe();
                                            }

                                            if (!ientityplayersp.isInWeb()) {
                                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                ientityplayersp.setMotionY((double) -6);
                                            }

                                            return;
                                        }
                                    }

                                    return;
                                }
                            }

                            return;

                        case 327072346:
                            if (!s.equals("aac5.0.4")) {
                                return;
                            }
                            break;

                        case 1492139161:
                            if (s.equals("aac3.3.11")) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getFallDistance() > (float) 2) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionZ(0.0D);
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setMotionX(ientityplayersp1.getMotionZ());
                                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                    iclassprovider1 = MinecraftInstance.classProvider;
                                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp2 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d1 = ientityplayersp2.getPosX();
                                    ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp3 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = ientityplayersp3.getPosY() - 0.001D;
                                    ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp4 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d3 = ientityplayersp4.getPosZ();
                                    ientityplayersp5 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp5 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerPosition(d1, d2, d3, ientityplayersp5.getOnGround()));
                                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(true));
                                    return;
                                }
                            }

                            return;

                        case 1492139165:
                            if (s.equals("aac3.3.15")) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getFallDistance() > (float) 2) {
                                    if (!MinecraftInstance.mc.isIntegratedServerRunning()) {
                                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                        iclassprovider1 = MinecraftInstance.classProvider;
                                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp2 == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        d1 = ientityplayersp2.getPosX();
                                        d2 = DoubleCompanionObject.INSTANCE.getNaN();
                                        ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp4 == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerPosition(d1, d2, ientityplayersp4.getPosZ(), false));
                                    }

                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    ientityplayersp.setFallDistance((float) -9999);
                                    return;
                                }
                            }

                            return;

                        case 1549308093:
                            if (s.equals("aac5.0.14")) {
                                double d5 = 0.0D;

                                this.aac5Check = false;

                                while (true) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getMotionY() - 1.5D >= d5) {
                                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                        if (ientityplayersp == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        if (ientityplayersp.getOnGround()) {
                                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                            if (ientityplayersp == null) {
                                                Intrinsics.throwNpe();
                                            }

                                            ientityplayersp.setFallDistance(-2.0F);
                                            this.aac5Check = false;
                                        }

                                        if (this.aac5Timer > 0) {
                                            --this.aac5Timer;
                                        }

                                        label442: {
                                            if (this.aac5Check) {
                                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                if ((double) ientityplayersp.getFallDistance() > 2.5D) {
                                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                                    if (ientityplayersp == null) {
                                                        Intrinsics.throwNpe();
                                                    }

                                                    if (!ientityplayersp.getOnGround()) {
                                                        this.aac5doFlag = true;
                                                        this.aac5Timer = 18;
                                                        break label442;
                                                    }
                                                }
                                            }

                                            if (this.aac5Timer < 2) {
                                                this.aac5doFlag = false;
                                            }
                                        }

                                        if (this.aac5doFlag) {
                                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                            if (ientityplayersp == null) {
                                                Intrinsics.throwNpe();
                                            }

                                            if (ientityplayersp.getOnGround()) {
                                                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                                iclassprovider1 = MinecraftInstance.classProvider;
                                                ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp2 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                d1 = ientityplayersp2.getPosX();
                                                ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp3 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                d2 = ientityplayersp3.getPosY() + 0.5D;
                                                ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp4 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerPosition(d1, d2, ientityplayersp4.getPosZ(), true));
                                            } else {
                                                iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                                                iclassprovider1 = MinecraftInstance.classProvider;
                                                ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp2 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                d1 = ientityplayersp2.getPosX();
                                                ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp3 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                d2 = ientityplayersp3.getPosY() + 0.42D;
                                                ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                                if (ientityplayersp4 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketPlayerPosition(d1, d2, ientityplayersp4.getPosZ(), true));
                                            }

                                            return;
                                        }

                                        return;
                                    }

                                    WBlockPos wblockpos = new WBlockPos;

                                    ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp2 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d1 = ientityplayersp2.getPosX();
                                    ientityplayersp3 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp3 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = ientityplayersp3.getPosY() + d5;
                                    ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp4 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    wblockpos.<init>(d1, d2, ientityplayersp4.getPosZ());
                                    WBlockPos blockPos = wblockpos;
                                    IBlock block = BlockUtils.getBlock(blockPos);

                                    if (block == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                                    if (iworldclient == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    IWorld iworld = (IWorld) iworldclient;
                                    IIBlockState iiblockstate = BlockUtils.getState(blockPos);

                                    if (iiblockstate == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    IAxisAlignedBB axisAlignedBB = block.getCollisionBoundingBox(iworld, blockPos, iiblockstate);

                                    if (axisAlignedBB != null) {
                                        d5 = -999.9D;
                                        this.aac5Check = true;
                                    }

                                    d5 -= 0.5D;
                                }
                            }

                            return;

                        case 1736568380:
                            if (!s.equals("loyisaaac4.4.2")) {
                                return;
                            }
                            break;

                        default:
                            return;
                        }

                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getFallDistance() > (float) 3) {
                            this.isDmgFalling = true;
                        }

                        if (Intrinsics.areEqual((String) this.modeValue.get(), "LoyisaAAC4.4.2")) {
                            if (this.aac4FlagCount >= 3 || this.aac4FlagCooldown.hasTimePassed(1500L)) {
                                return;
                            }

                            if (!this.aac4FlagCooldown.hasTimePassed(1500L)) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (!ientityplayersp.getOnGround()) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if ((double) ientityplayersp.getFallDistance() >= 0.5D) {
                                        return;
                                    }
                                }

                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionX(0.0D);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setMotionZ(0.0D);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setOnGround(false);
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setJumpMovementFactor(0.0F);
                            }
                        }

                        return;
                    }
                }

                return;
            }
        }

    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (!ientityplayersp.isInWater()) {
            IPacket packet = event.getPacket();
            String mode = (String) this.modeValue.get();

            if (MinecraftInstance.classProvider.isSPacketEntityVelocity(event.getPacket()) && StringsKt.equals(mode, "AAC4.4.X-Flag", true)) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if ((double) ientityplayersp.getFallDistance() > 1.8D) {
                    event.getPacket().asSPacketEntityVelocity().setMotionY((int) ((double) event.getPacket().asSPacketEntityVelocity().getMotionY() * -0.1D));
                }
            }

            if (MinecraftInstance.classProvider.isSPacketPlayerPosLook(event.getPacket()) && StringsKt.equals(mode, "LoyisaAAC4.4.2", true)) {
                int playerPacket = this.aac4FlagCount++;

                if (this.matrixFlagWait > 0) {
                    this.aac4FlagCooldown.reset();
                    this.aac4FlagCount = 1;
                    event.cancelEvent();
                }
            }

            if (StringsKt.equals(mode, "AAC4", true) && MinecraftInstance.classProvider.isCPacketPlayer(packet) && this.fakelag) {
                event.cancelEvent();
                if (this.packetmodify) {
                    packet.asCPacketPlayer().setOnGround(true);
                    this.packetmodify = false;
                }

                this.packets.add(packet);
            }

            IEntityPlayerSP ientityplayersp1;

            if (StringsKt.equals(mode, "Vulcan", true) && MinecraftInstance.classProvider.isCPacketPlayer(packet) && this.doSpoof && this.count > 0) {
                this.count += -1;
                int i = this.count;

                if (this.count == 0) {
                    this.doSpoof = false;
                }

                packet.asCPacketPlayer().setOnGround(true);
                ICPacketPlayer icpacketplayer = packet.asCPacketPlayer();

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                icpacketplayer.setY((double) (MathKt.roundToInt(ientityplayersp1.getPosY() * (double) 2) / 2));
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                double d0 = ientityplayersp1.getPosX();
                double d1 = packet.asCPacketPlayer().getY();
                IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp2 == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setPosition(d0, d1, ientityplayersp2.getPosZ());
            }

            if (MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
                ICPacketPlayer icpacketplayer1 = packet.asCPacketPlayer();

                if (StringsKt.equals(mode, "SpoofGround", true)) {
                    icpacketplayer1.setOnGround(true);
                }

                if (StringsKt.equals(mode, "NoGround", true)) {
                    icpacketplayer1.setOnGround(false);
                }

                if (StringsKt.equals(mode, "Hypixel", true) && MinecraftInstance.mc.getThePlayer() != null) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if ((double) ientityplayersp.getFallDistance() > 1.5D) {
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        icpacketplayer1.setOnGround(ientityplayersp1.getTicksExisted() % 2 == 0);
                    }
                }
            }

        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
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
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            double d0 = ientityplayersp1.getEntityBoundingBox().getMaxX();
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            double d1 = ientityplayersp2.getEntityBoundingBox().getMaxY();
            IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp3 == null) {
                Intrinsics.throwNpe();
            }

            double d2 = ientityplayersp3.getEntityBoundingBox().getMaxZ();
            IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp4 == null) {
                Intrinsics.throwNpe();
            }

            double d3 = ientityplayersp4.getEntityBoundingBox().getMinX();
            IEntityPlayerSP ientityplayersp5 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp5 == null) {
                Intrinsics.throwNpe();
            }

            double d4 = ientityplayersp5.getEntityBoundingBox().getMinY() - 0.01D;
            IEntityPlayerSP ientityplayersp6 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp6 == null) {
                Intrinsics.throwNpe();
            }

            if (!BlockUtils.collideBlock(iclassprovider.createAxisAlignedBB(d0, d1, d2, d3, d4, ientityplayersp6.getEntityBoundingBox().getMinZ()), (Function1) (new Function1(1, MinecraftInstance.classProvider) {
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
                if (StringsKt.equals((String) this.modeValue.get(), "laac", true) && !this.jumped) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!ientityplayersp.getOnGround()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!ientityplayersp.isOnLadder()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (!ientityplayersp.isInWater()) {
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (!ientityplayersp.isInWeb()) {
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    if (ientityplayersp.getMotionY() < 0.0D) {
                                        event.setX(0.0D);
                                        event.setZ(0.0D);
                                    }
                                }
                            }
                        }
                    }
                }

                return;
            }
        }

    }

    @EventTarget
    private final void onMotionUpdate(MotionEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (!ientityplayersp.isInWater()) {
            IINetHandlerPlayClient iinethandlerplayclient;

            if (StringsKt.equals((String) this.modeValue.get(), "AAC4", true)) {
                EventState stack = event.getEventState();

                if (stack == EventState.PRE) {
                    Iterator iterator;
                    IPacket ipacket;

                    if (!this.inVoid()) {
                        if (this.fakelag) {
                            this.fakelag = false;
                            if (this.packets.size() > 0) {
                                iterator = this.packets.iterator();

                                while (iterator.hasNext()) {
                                    ipacket = (IPacket) iterator.next();
                                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    iinethandlerplayclient = ientityplayersp.getSendQueue();
                                    Intrinsics.checkExpressionValueIsNotNull(ipacket, "packet");
                                    iinethandlerplayclient.addToSendQueue(ipacket);
                                }

                                this.packets.clear();
                            }
                        }

                        return;
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getOnGround() && this.fakelag) {
                        this.fakelag = false;
                        if (this.packets.size() > 0) {
                            iterator = this.packets.iterator();

                            while (iterator.hasNext()) {
                                ipacket = (IPacket) iterator.next();
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                iinethandlerplayclient = ientityplayersp.getSendQueue();
                                Intrinsics.checkExpressionValueIsNotNull(ipacket, "packet");
                                iinethandlerplayclient.addToSendQueue(ipacket);
                            }

                            this.packets.clear();
                        }

                        return;
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getFallDistance() > (float) 3 && this.fakelag) {
                        this.packetmodify = true;
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.setFallDistance(0.0F);
                    }

                    if (this.inAir(4.0D, 1.0D)) {
                        return;
                    }

                    if (!this.fakelag) {
                        this.fakelag = true;
                    }
                }
            }

            if (StringsKt.equals((String) this.modeValue.get(), "MLG", true)) {
                IEntityPlayerSP ientityplayersp1;
                IEntityPlayerSP ientityplayersp2;

                if (event.getEventState() == EventState.PRE) {
                    this.currentMlgRotation = (VecRotation) null;
                    this.mlgTimer.update();
                    if (!this.mlgTimer.hasTimePassed(10)) {
                        return;
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getFallDistance() > ((Number) this.minFallDistance.get()).floatValue()) {
                        FallingPlayer fallingplayer = new FallingPlayer;

                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        double d0 = ientityplayersp2.getPosX();
                        IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp3 == null) {
                            Intrinsics.throwNpe();
                        }

                        double d1 = ientityplayersp3.getPosY();
                        IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp4 == null) {
                            Intrinsics.throwNpe();
                        }

                        double d2 = ientityplayersp4.getPosZ();
                        IEntityPlayerSP ientityplayersp5 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp5 == null) {
                            Intrinsics.throwNpe();
                        }

                        double d3 = ientityplayersp5.getMotionX();
                        IEntityPlayerSP ientityplayersp6 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp6 == null) {
                            Intrinsics.throwNpe();
                        }

                        double d4 = ientityplayersp6.getMotionY();
                        IEntityPlayerSP ientityplayersp7 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp7 == null) {
                            Intrinsics.throwNpe();
                        }

                        double d5 = ientityplayersp7.getMotionZ();
                        IEntityPlayerSP ientityplayersp8 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp8 == null) {
                            Intrinsics.throwNpe();
                        }

                        float f = ientityplayersp8.getRotationYaw();
                        IEntityPlayerSP ientityplayersp9 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp9 == null) {
                            Intrinsics.throwNpe();
                        }

                        float f1 = ientityplayersp9.getMoveStrafing();
                        IEntityPlayerSP ientityplayersp10 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp10 == null) {
                            Intrinsics.throwNpe();
                        }

                        fallingplayer.<init>(d0, d1, d2, d3, d4, d5, f, f1, ientityplayersp10.getMoveForward());
                        FallingPlayer fallingplayer1 = fallingplayer;
                        double dirVec = (double) MinecraftInstance.mc.getPlayerController().getBlockReachDistance() + 1.5D;

                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        double ok = 1.0D / ientityplayersp2.getMotionY() * -dirVec;
                        boolean i = false;
                        double d6 = Math.ceil(ok);
                        FallingPlayer.CollisionResult fallingplayer_collisionresult = fallingplayer1.findCollision((int) d6);

                        if (fallingplayer_collisionresult == null) {
                            return;
                        }

                        FallingPlayer.CollisionResult collision = fallingplayer_collisionresult;
                        WVec3 wvec3 = new WVec3;

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

                        d1 += (double) ientityplayersp4.getEyeHeight();
                        ientityplayersp4 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp4 == null) {
                            Intrinsics.throwNpe();
                        }

                        wvec3.<init>(d0, d1, ientityplayersp4.getPosZ());
                        WBlockPos wblockpos = collision.getPos();

                        Intrinsics.checkExpressionValueIsNotNull(wblockpos, "collision.pos");
                        WVec3 index = new WVec3((WVec3i) wblockpos);
                        double d7 = 0.5D;
                        double itemStack = 0.5D;
                        double z$iv = 0.5D;
                        WVec3 wvec31 = wvec3;
                        boolean $i$f$addVector = false;
                        WVec3 wvec32 = new WVec3(index.getXCoord() + d7, index.getYCoord() + itemStack, index.getZCoord() + z$iv);
                        double d8 = wvec31.distanceTo(wvec32);
                        double d9 = (double) MinecraftInstance.mc.getPlayerController().getBlockReachDistance();
                        double d10 = 0.75D;
                        double d11 = d9;
                        double d12 = d8;
                        boolean flag = false;
                        double d13 = Math.sqrt(d10);
                        boolean flag1 = d12 < d11 + d13;

                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        d8 = ientityplayersp.getMotionY();
                        d9 = (double) (collision.getPos().getY() + 1);
                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        if (d8 < d9 - ientityplayersp2.getPosY()) {
                            flag1 = true;
                        }

                        if (!flag1) {
                            return;
                        }

                        int i = -1;
                        int j = 36;

                        for (byte b0 = 44; j <= b0; ++j) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            IItemStack iitemstack = ientityplayersp.getInventoryContainer().getSlot(j).getStack();

                            if (iitemstack != null) {
                                if (!Intrinsics.areEqual(iitemstack.getItem(), MinecraftInstance.classProvider.getItemEnum(ItemType.WATER_BUCKET))) {
                                    if (!MinecraftInstance.classProvider.isItemBlock(iitemstack.getItem())) {
                                        continue;
                                    }

                                    IBlock iblock;
                                    label276: {
                                        IItem iitem = iitemstack.getItem();

                                        if (iitem != null) {
                                            IItemBlock iitemblock = iitem.asItemBlock();

                                            if (iitemblock != null) {
                                                iblock = iitemblock.getBlock();
                                                break label276;
                                            }
                                        }

                                        iblock = null;
                                    }

                                    if (!Intrinsics.areEqual(iblock, MinecraftInstance.classProvider.getBlockEnum(BlockType.WEB))) {
                                        continue;
                                    }
                                }

                                i = j - 36;
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (ientityplayersp.getInventory().getCurrentItem() == i) {
                                    break;
                                }
                            }
                        }

                        if (i == -1) {
                            return;
                        }

                        this.currentMlgItemIndex = i;
                        this.currentMlgBlock = collision.getPos();
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getInventory().getCurrentItem() != i) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.getSendQueue().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(i));
                        }

                        this.currentMlgRotation = RotationUtils.faceBlock(collision.getPos());
                        VecRotation vecrotation = this.currentMlgRotation;

                        if (this.currentMlgRotation == null) {
                            Intrinsics.throwNpe();
                        }

                        Rotation rotation = vecrotation.getRotation();

                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        rotation.toPlayer((IEntityPlayer) ientityplayersp1);
                    }
                } else if (this.currentMlgRotation != null) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    IItemStack iitemstack1 = ientityplayersp.getInventory().getStackInSlot(this.currentMlgItemIndex + 36);
                    IClassProvider iclassprovider = MinecraftInstance.classProvider;

                    if (iitemstack1 == null) {
                        Intrinsics.throwNpe();
                    }

                    IPlayerControllerMP iplayercontrollermp;
                    IEntityPlayer ientityplayer;
                    IWorldClient iworldclient;

                    if (iclassprovider.isItemBucket(iitemstack1.getItem())) {
                        iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayer = (IEntityPlayer) ientityplayersp1;
                        iworldclient = MinecraftInstance.mc.getTheWorld();
                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        iplayercontrollermp.sendUseItem(ientityplayer, (IWorld) iworldclient, iitemstack1);
                    } else {
                        WVec3i wvec3i = MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP).getDirectionVec();

                        iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayer = (IEntityPlayer) ientityplayersp1;
                        iworldclient = MinecraftInstance.mc.getTheWorld();
                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        if (iplayercontrollermp.sendUseItem(ientityplayer, (IWorld) iworldclient, iitemstack1)) {
                            this.mlgTimer.reset();
                        }
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getInventory().getCurrentItem() != this.currentMlgItemIndex) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient = ientityplayersp.getSendQueue();
                        IClassProvider iclassprovider1 = MinecraftInstance.classProvider;

                        ientityplayersp2 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketHeldItemChange(ientityplayersp2.getInventory().getCurrentItem()));
                    }
                }
            }

        }
    }

    public final boolean isBlockUnder() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getPosY() < 0.0D) {
            return false;
        } else {
            int off = 0;

            while (true) {
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (off >= (int) ientityplayersp1.getPosY() + 2) {
                    return false;
                }

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IAxisAlignedBB bb = ientityplayersp.getEntityBoundingBox().offset(0.0D, (double) (-off), 0.0D);
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (!iworldclient.getCollidingBoundingBoxes((IEntity) ientityplayersp1, bb).isEmpty()) {
                    return true;
                }

                off += 2;
            }
        }
    }

    public final int getJumpEffect() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        int i;

        if (ientityplayersp.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.JUMP))) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IPotionEffect ipotioneffect = ientityplayersp.getActivePotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.JUMP));

            if (ipotioneffect == null) {
                Intrinsics.throwNpe();
            }

            i = ipotioneffect.getAmplifier() + 1;
        } else {
            i = 0;
        }

        return i;
    }

    public final boolean inVoid() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getPosY() < (double) 0) {
            return false;
        } else {
            int off = 0;

            while (true) {
                double d0 = (double) off;
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (d0 >= ientityplayersp1.getPosY() + (double) 2) {
                    return false;
                }

                IClassProvider iclassprovider = MinecraftInstance.classProvider;

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                double d1 = ientityplayersp1.getPosX();
                IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp2 == null) {
                    Intrinsics.throwNpe();
                }

                double d2 = ientityplayersp2.getPosY();
                IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp3 == null) {
                    Intrinsics.throwNpe();
                }

                double d3 = ientityplayersp3.getPosZ();
                IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp4 == null) {
                    Intrinsics.throwNpe();
                }

                double d4 = ientityplayersp4.getPosX();
                double d5 = (double) off;
                IEntityPlayerSP ientityplayersp5 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp5 == null) {
                    Intrinsics.throwNpe();
                }

                IAxisAlignedBB bb = iclassprovider.createAxisAlignedBB(d1, d2, d3, d4, d5, ientityplayersp5.getPosZ());
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (!iworldclient.getCollidingBoundingBoxes((IEntity) ientityplayersp1, bb).isEmpty()) {
                    return true;
                }

                off += 2;
            }
        }
    }

    public final boolean inAir(double height, double plus) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getPosY() < (double) 0) {
            return false;
        } else {
            for (int off = 0; (double) off < height; off += (int) plus) {
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                double d0 = ientityplayersp1.getPosX();
                IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp2 == null) {
                    Intrinsics.throwNpe();
                }

                double d1 = ientityplayersp2.getPosY();
                IEntityPlayerSP ientityplayersp3 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp3 == null) {
                    Intrinsics.throwNpe();
                }

                double d2 = ientityplayersp3.getPosZ();
                IEntityPlayerSP ientityplayersp4 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp4 == null) {
                    Intrinsics.throwNpe();
                }

                double d3 = ientityplayersp4.getPosX();
                IEntityPlayerSP ientityplayersp5 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp5 == null) {
                    Intrinsics.throwNpe();
                }

                double d4 = ientityplayersp5.getPosY() - (double) off;
                IEntityPlayerSP ientityplayersp6 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp6 == null) {
                    Intrinsics.throwNpe();
                }

                IAxisAlignedBB bb = iclassprovider.createAxisAlignedBB(d0, d1, d2, d3, d4, ientityplayersp6.getPosZ());
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (!iworldclient.getCollidingBoundingBoxes((IEntity) ientityplayersp1, bb).isEmpty()) {
                    return true;
                }
            }

            return false;
        }
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onJump(@Nullable JumpEvent event) {
        this.jumped = true;
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.worldChange = true;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
