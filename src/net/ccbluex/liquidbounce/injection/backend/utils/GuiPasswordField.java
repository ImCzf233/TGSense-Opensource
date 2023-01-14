package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/utils/GuiPasswordField;", "Lnet/minecraft/client/gui/GuiTextField;", "componentId", "", "fontrendererObj", "Lnet/minecraft/client/gui/FontRenderer;", "x", "y", "par5Width", "par6Height", "(ILnet/minecraft/client/gui/FontRenderer;IIII)V", "drawTextBox", "", "LiquidBounce"}
)
public final class GuiPasswordField extends GuiTextField {

    public void drawTextBox() {
        String realText = this.getText();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        String s = this.getText();

        Intrinsics.checkExpressionValueIsNotNull(s, "text");

        for (int i = ((CharSequence) s).length(); i < i; ++i) {
            stringBuilder.append('*');
        }

        this.setText(stringBuilder.toString());
        super.drawTextBox();
        this.setText(realText);
    }

    public GuiPasswordField(int componentId, @NotNull FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        Intrinsics.checkParameterIsNotNull(fontrendererObj, "fontrendererObj");
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }
}
