package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "AutoLeave",
    category = ModuleCategory.COMBAT,
    description = "idk."
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0004H\u0002J\u0012\u0010%\u001a\u00020!2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0007J\u0012\u0010(\u001a\u00020!2\b\u0010&\u001a\u0004\u0018\u00010)H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006*"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoLeave;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "check", "", "getCheck", "()Z", "setCheck", "(Z)V", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "leavemessageValue", "Lnet/ccbluex/liquidbounce/value/TextValue;", "lobbyValue", "getLobbyValue", "()Lnet/ccbluex/liquidbounce/value/TextValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "randomhub", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getRandomhub", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "setRandomhub", "(Lnet/ccbluex/liquidbounce/value/BoolValue;)V", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Ljava/util/Timer;", "getTimer", "()Ljava/util/Timer;", "move", "", "item", "", "isArmorSlot", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class AutoLeave extends Module {

    private boolean check = true;
    private final FloatValue healthValue = new FloatValue("Health", 8.0F, 0.0F, 20.0F);
    private final ListValue modeValue = new ListValue("Mode", new String[] { "Quit", "InvalidPacket", "SelfHurt", "IllegalChat", "HytPit"}, "Quit");
    private final TextValue leavemessageValue = new TextValue("LeaveMessage", "[Move] Leave!");
    @NotNull
    private final TextValue lobbyValue = new TextValue("LobbyValue", "/hub 1");
    @NotNull
    private BoolValue randomhub = new BoolValue("OnLeaveMessage", false);
    @NotNull
    private final Timer timer = new Timer();

    public final boolean getCheck() {
        return this.check;
    }

    public final void setCheck(boolean <set-?>) {
        this.check = <set-?>;
    }

    @NotNull
    public final TextValue getLobbyValue() {
        return this.lobbyValue;
    }

    @NotNull
    public final BoolValue getRandomhub() {
        return this.randomhub;
    }

    public final void setRandomhub(@NotNull BoolValue <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.randomhub = <set-?>;
    }

    @NotNull
    public final Timer getTimer() {
        return this.timer;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getHealth() <= ((Number) this.healthValue.get()).floatValue()) {
            String s = (String) this.modeValue.get();
            boolean i = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            IINetHandlerPlayClient iinethandlerplayclient;
            IClassProvider iclassprovider;

            switch (s.hashCode()) {
            case -1647903794:
                if (s.equals("illegalchat")) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.sendChatMessage((new Random()).nextInt() + "§§§" + (new Random()).nextInt());
                    this.setState(false);
                }
                break;

            case -1202222792:
                if (s.equals("hytpit")) {
                    Module module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);

                    if (module == null) {
                        Intrinsics.throwNpe();
                    }

                    module.setState(false);
                    int i = 0;

                    for (byte b0 = 3; i <= b0; ++i) {
                        int armorSlot = 3 - i;

                        this.move(8 - armorSlot, true);
                    }

                    if (this.check) {
                        this.check = false;
                        this.timer.schedule((TimerTask) (new TimerTask() {
                            public void run() {
                                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.sendChatMessage((String) AutoLeave.this.getLobbyValue().get());
                            }
                        }), 300L);
                    }

                    if (((Boolean) this.randomhub.get()).booleanValue()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ientityplayersp.sendChatMessage((String) this.leavemessageValue.get());
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.sendChatMessage((String) this.leavemessageValue.get());
                    module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
                    if (module == null) {
                        Intrinsics.throwNpe();
                    }

                    module.setState(false);
                    module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);
                    if (module == null) {
                        Intrinsics.throwNpe();
                    }

                    module.setState(false);
                    module = LiquidBounce.INSTANCE.getModuleManager().get(Velocity.class);
                    if (module == null) {
                        Intrinsics.throwNpe();
                    }

                    module.setState(false);
                    this.check = false;
                }
                break;

            case -367089345:
                if (s.equals("invalidpacket")) {
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    iclassprovider = MinecraftInstance.classProvider;
                    double d0 = DoubleCompanionObject.INSTANCE.getNaN();
                    double d1 = DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY();
                    double d2 = DoubleCompanionObject.INSTANCE.getPOSITIVE_INFINITY();
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketPlayerPosition(d0, d1, d2, !ientityplayersp1.getOnGround()));
                    this.setState(false);
                }
                break;

            case 3482191:
                if (s.equals("quit")) {
                    IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                    if (iworldclient == null) {
                        Intrinsics.throwNpe();
                    }

                    iworldclient.sendQuittingDisconnectingPacket();
                    this.setState(false);
                }
                break;

            case 1192645979:
                if (s.equals("selfhurt")) {
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    iclassprovider = MinecraftInstance.classProvider;
                    IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp2 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketUseEntity((IEntity) ientityplayersp2, ICPacketUseEntity.WAction.ATTACK));
                    this.setState(false);
                }
            }
        }

    }

    private final void move(int item, boolean isArmorSlot) {
        if (item != -1) {
            boolean openInventory = !(MinecraftInstance.mc.getCurrentScreen() instanceof GuiInventory);

            if (openInventory) {
                IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.OPEN_INVENTORY));
            }

            IPlayerControllerMP iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            int i = ientityplayersp1.getInventoryContainer().getWindowId();
            int j = isArmorSlot ? item : (item < 9 ? item + 36 : item);
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            iplayercontrollermp.windowClick(i, j, 0, 1, ientityplayersp2);
            if (openInventory) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketCloseWindow());
            }
        }

    }

    @EventTarget
    public final void onWorld(@Nullable WorldEvent event) {
        this.check = true;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
