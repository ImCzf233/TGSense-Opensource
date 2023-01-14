package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.injection.backend.GuiButtonImpl;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\r\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\bH\u0016J\b\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fH\u0014J \u0010\u0019\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0014J \u0010\u001b\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fH\u0014J\b\u0010\u001d\u001a\u00020\bH\u0016J\u001e\u0010\u001e\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u001f\u001a\u00020\bJ\u0016\u0010 \u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fJ\u001e\u0010!\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fJ\u001e\u0010\"\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fJ\b\u0010#\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006$"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/utils/GuiScreenWrapper;", "Lnet/minecraft/client/gui/GuiScreen;", "wrapped", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;)V", "getWrapped", "()Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "doesGuiPauseGame", "", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", "superDrawScreen", "superHandleMouseInput", "superKeyTyped", "superMouseClicked", "superMouseReleased", "updateScreen", "LiquidBounce"}
)
public final class GuiScreenWrapper extends GuiScreen {

    @NotNull
    private final WrappedGuiScreen wrapped;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.wrapped.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void initGui() {
        this.wrapped.initGui();
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.wrapped.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        this.wrapped.updateScreen();
    }

    public void handleMouseInput() {
        this.wrapped.handleMouseInput();
    }

    protected void keyTyped(char typedChar, int keyCode) {
        this.wrapped.keyTyped(typedChar, keyCode);
    }

    protected void actionPerformed(@Nullable GuiButton button) {
        if (button != null) {
            boolean flag = false;
            boolean flag1 = false;
            boolean $i$a$-let-GuiScreenWrapper$actionPerformed$1 = false;
            WrappedGuiScreen wrappedguiscreen = this.wrapped;
            boolean $i$f$wrap = false;
            IGuiButton iguibutton = (IGuiButton) (new GuiButtonImpl(button));

            wrappedguiscreen.actionPerformed(iguibutton);
        }

    }

    public void onGuiClosed() {
        this.wrapped.onGuiClosed();
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.wrapped.mouseReleased(mouseX, mouseY, state);
    }

    public boolean doesGuiPauseGame() {
        return this.wrapped.doesGuiPauseGame();
    }

    public final void superMouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    public final void superKeyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
    }

    public final void superHandleMouseInput() {
        super.handleMouseInput();
    }

    public final void superMouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public final void superDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @NotNull
    public final WrappedGuiScreen getWrapped() {
        return this.wrapped;
    }

    public GuiScreenWrapper(@NotNull WrappedGuiScreen wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
