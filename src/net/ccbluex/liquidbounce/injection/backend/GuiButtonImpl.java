package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.minecraft.client.gui.GuiButton;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/GuiButtonImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "wrapped", "Lnet/minecraft/client/gui/GuiButton;", "(Lnet/minecraft/client/gui/GuiButton;)V", "value", "", "displayString", "getDisplayString", "()Ljava/lang/String;", "setDisplayString", "(Ljava/lang/String;)V", "", "enabled", "getEnabled", "()Z", "setEnabled", "(Z)V", "id", "", "getId", "()I", "getWrapped", "()Lnet/minecraft/client/gui/GuiButton;", "LiquidBounce"}
)
public final class GuiButtonImpl implements IGuiButton {

    @NotNull
    private final GuiButton wrapped;

    @NotNull
    public String getDisplayString() {
        String s = this.wrapped.displayString;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.displayString, "wrapped.displayString");
        return s;
    }

    public void setDisplayString(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.wrapped.displayString = value;
    }

    public int getId() {
        return this.wrapped.id;
    }

    public boolean getEnabled() {
        return this.wrapped.enabled;
    }

    public void setEnabled(boolean value) {
        this.wrapped.enabled = value;
    }

    @NotNull
    public final GuiButton getWrapped() {
        return this.wrapped;
    }

    public GuiButtonImpl(@NotNull GuiButton wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
