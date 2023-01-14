package net.ccbluex.liquidbounce.api.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiSlot;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nJ\b\u0010\u0011\u001a\u00020\u0012H&J8\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005H&J(\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H&J\b\u0010\u001f\u001a\u00020\u0005H&J\u0010\u0010 \u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u0005H&R\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006!"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "", "mc", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "width", "", "height", "top", "bottom", "slotHeight", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;IIIII)V", "represented", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiSlot;", "getRepresented", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiSlot;", "setRepresented", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiSlot;)V", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "var1", "doubleClick", "", "var3", "getSize", "isSelected", "LiquidBounce"}
)
public abstract class WrappedGuiSlot {

    @NotNull
    public IGuiSlot represented;

    @NotNull
    public final IGuiSlot getRepresented() {
        IGuiSlot iguislot = this.represented;

        if (this.represented == null) {
            Intrinsics.throwUninitializedPropertyAccessException("represented");
        }

        return iguislot;
    }

    public final void setRepresented(@NotNull IGuiSlot <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.represented = <set-?>;
    }

    public abstract void drawSlot(int i, int j, int k, int l, int i1, int j1);

    public abstract void drawBackground();

    public abstract void elementClicked(int i, boolean flag, int j, int k);

    public abstract int getSize();

    public abstract boolean isSelected(int i);

    public WrappedGuiSlot(@NotNull IMinecraft mc, int width, int height, int top, int bottom, int slotHeight) {
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        super();
        WrapperImpl.INSTANCE.getClassProvider().wrapGuiSlot(this, mc, width, height, top, bottom, slotHeight);
    }
}
