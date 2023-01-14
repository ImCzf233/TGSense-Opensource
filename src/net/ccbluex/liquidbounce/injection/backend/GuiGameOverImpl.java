package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiGameOver;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/GuiGameOverImpl;", "T", "Lnet/minecraft/client/gui/GuiGameOver;", "Lnet/ccbluex/liquidbounce/injection/backend/GuiScreenImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiGameOver;", "wrapped", "(Lnet/minecraft/client/gui/GuiGameOver;)V", "enableButtonsTimer", "", "getEnableButtonsTimer", "()I", "LiquidBounce"}
)
public final class GuiGameOverImpl extends GuiScreenImpl implements IGuiGameOver {

    public int getEnableButtonsTimer() {
        return ((GuiGameOver) this.getWrapped()).enableButtonsTimer;
    }

    public GuiGameOverImpl(@NotNull GuiGameOver wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((GuiScreen) wrapped);
    }
}
