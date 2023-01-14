package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovementInput;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.MovementInput;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\u0010\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020\'H\u0016J\b\u0010+\u001a\u00020\'H\u0016J\u0010\u0010,\u001a\u00020\'2\u0006\u0010-\u001a\u00020.H\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR$\u0010\f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\u000fR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u00108V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR$\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u000b\u001a\u00020\u001e8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010!¨\u0006/"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/EntityPlayerSPImpl;", "T", "Lnet/minecraft/client/entity/EntityPlayerSP;", "Lnet/ccbluex/liquidbounce/injection/backend/AbstractClientPlayerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "wrapped", "(Lnet/minecraft/client/entity/EntityPlayerSP;)V", "absorptionAmount", "", "getAbsorptionAmount", "()F", "value", "horseJumpPower", "getHorseJumpPower", "setHorseJumpPower", "(F)V", "", "horseJumpPowerCounter", "getHorseJumpPowerCounter", "()I", "setHorseJumpPowerCounter", "(I)V", "movementInput", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "getMovementInput", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "sendQueue", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "getSendQueue", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "", "serverSprintState", "getServerSprintState", "()Z", "setServerSprintState", "(Z)V", "sneaking", "getSneaking", "addChatMessage", "", "component", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "closeScreen", "respawnPlayer", "sendChatMessage", "msg", "", "LiquidBounce"}
)
public class EntityPlayerSPImpl extends AbstractClientPlayerImpl implements IEntityPlayerSP {

    public float getAbsorptionAmount() {
        return ((EntityPlayerSP) this.getWrapped()).getAbsorptionAmount();
    }

    public int getHorseJumpPowerCounter() {
        return ((EntityPlayerSP) this.getWrapped()).horseJumpPowerCounter;
    }

    public void setHorseJumpPowerCounter(int value) {
        ((EntityPlayerSP) this.getWrapped()).horseJumpPowerCounter = value;
    }

    public float getHorseJumpPower() {
        return ((EntityPlayerSP) this.getWrapped()).horseJumpPower;
    }

    public void setHorseJumpPower(float value) {
        ((EntityPlayerSP) this.getWrapped()).horseJumpPower = value;
    }

    @NotNull
    public IINetHandlerPlayClient getSendQueue() {
        NetHandlerPlayClient nethandlerplayclient = ((EntityPlayerSP) this.getWrapped()).connection;

        Intrinsics.checkExpressionValueIsNotNull(nethandlerplayclient, "wrapped.connection");
        NetHandlerPlayClient $this$wrap$iv = nethandlerplayclient;
        boolean $i$f$wrap = false;

        return (IINetHandlerPlayClient) (new INetHandlerPlayClientImpl($this$wrap$iv));
    }

    @NotNull
    public IMovementInput getMovementInput() {
        MovementInput movementinput = ((EntityPlayerSP) this.getWrapped()).movementInput;

        Intrinsics.checkExpressionValueIsNotNull(movementinput, "wrapped.movementInput");
        MovementInput $this$wrap$iv = movementinput;
        boolean $i$f$wrap = false;

        return (IMovementInput) (new MovementInputImpl($this$wrap$iv));
    }

    public boolean getSneaking() {
        return ((EntityPlayerSP) this.getWrapped()).isSneaking();
    }

    public boolean getServerSprintState() {
        return ((EntityPlayerSP) this.getWrapped()).serverSprintState;
    }

    public void setServerSprintState(boolean value) {
        ((EntityPlayerSP) this.getWrapped()).serverSprintState = value;
    }

    public void sendChatMessage(@NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        ((EntityPlayerSP) this.getWrapped()).sendChatMessage(msg);
    }

    public void respawnPlayer() {
        ((EntityPlayerSP) this.getWrapped()).respawnPlayer();
    }

    public void addChatMessage(@NotNull IIChatComponent component) {
        Intrinsics.checkParameterIsNotNull(component, "component");
        EntityPlayerSP entityplayersp = (EntityPlayerSP) this.getWrapped();
        boolean $i$f$unwrap = false;
        ITextComponent itextcomponent = ((IChatComponentImpl) component).getWrapped();

        entityplayersp.sendMessage(itextcomponent);
    }

    public void closeScreen() {
        ((EntityPlayerSP) this.getWrapped()).closeScreen();
    }

    public EntityPlayerSPImpl(@NotNull EntityPlayerSP wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((AbstractClientPlayer) wrapped);
    }
}
