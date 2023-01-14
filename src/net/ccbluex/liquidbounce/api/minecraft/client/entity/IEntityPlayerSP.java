package net.ccbluex.liquidbounce.api.minecraft.client.entity;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovementInput;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H&J\b\u0010\"\u001a\u00020\u001fH&J\b\u0010#\u001a\u00020\u001fH&J\u0010\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020&H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\u0005\"\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\u00020\u000bX¦\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0018\u0010\u0018\u001a\u00020\u0019X¦\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006\'"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IAbstractClientPlayer;", "absorptionAmount", "", "getAbsorptionAmount", "()F", "horseJumpPower", "getHorseJumpPower", "setHorseJumpPower", "(F)V", "horseJumpPowerCounter", "", "getHorseJumpPowerCounter", "()I", "setHorseJumpPowerCounter", "(I)V", "movementInput", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "getMovementInput", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "sendQueue", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "getSendQueue", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "serverSprintState", "", "getServerSprintState", "()Z", "setServerSprintState", "(Z)V", "addChatMessage", "", "component", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "closeScreen", "respawnPlayer", "sendChatMessage", "msg", "", "LiquidBounce"}
)
public interface IEntityPlayerSP extends IAbstractClientPlayer {

    float getAbsorptionAmount();

    int getHorseJumpPowerCounter();

    void setHorseJumpPowerCounter(int i);

    float getHorseJumpPower();

    void setHorseJumpPower(float f);

    @NotNull
    IINetHandlerPlayClient getSendQueue();

    @NotNull
    IMovementInput getMovementInput();

    boolean getServerSprintState();

    void setServerSprintState(boolean flag);

    void sendChatMessage(@NotNull String s);

    void respawnPlayer();

    void addChatMessage(@NotNull IIChatComponent iichatcomponent);

    void closeScreen();
}
