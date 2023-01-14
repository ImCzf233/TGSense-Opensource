package net.ccbluex.liquidbounce.injection.backend;

import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiGameOver;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiChest;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiContainer;
import net.ccbluex.liquidbounce.api.util.WrappedMutableList;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000j\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0011H\u0016J\b\u0010#\u001a\u00020!H\u0016J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0096\u0002J \u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020!H\u0016J\u0018\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0011H\u0016J \u00102\u001a\u00020!2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00112\u0006\u00103\u001a\u00020\u0011H\u0016J \u00104\u001a\u00020!2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00112\u0006\u00105\u001a\u00020\u0011H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016¨\u00066"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/GuiScreenImpl;", "T", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/ccbluex/liquidbounce/injection/backend/GuiImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "wrapped", "(Lnet/minecraft/client/gui/GuiScreen;)V", "buttonList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "getButtonList", "()Ljava/util/List;", "fontRendererObj", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "getFontRendererObj", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "value", "", "height", "getHeight", "()I", "setHeight", "(I)V", "width", "getWidth", "setWidth", "asGuiChest", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiChest;", "asGuiContainer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiContainer;", "asGuiGameOver", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiGameOver;", "drawBackground", "", "i", "drawDefaultBackground", "equals", "", "other", "", "superDrawScreen", "mouseX", "mouseY", "partialTicks", "", "superHandleMouseInput", "superKeyTyped", "typedChar", "", "keyCode", "superMouseClicked", "mouseButton", "superMouseReleased", "state", "LiquidBounce"}
)
public class GuiScreenImpl extends GuiImpl implements IGuiScreen {

    @NotNull
    public IFontRenderer getFontRendererObj() {
        FontRenderer fontrenderer = ((GuiScreen) this.getWrapped()).fontRenderer;

        Intrinsics.checkExpressionValueIsNotNull(fontrenderer, "wrapped.fontRenderer");
        FontRenderer $this$wrap$iv = fontrenderer;
        boolean $i$f$wrap = false;

        return (IFontRenderer) (new FontRendererImpl($this$wrap$iv));
    }

    @NotNull
    public List getButtonList() {
        Function1 function1 = (Function1) null.INSTANCE;

        return (List) (new WrappedMutableList(((GuiScreen) this.getWrapped()).buttonList, function1, (Function1) null.INSTANCE));
    }

    @NotNull
    public IGuiContainer asGuiContainer() {
        GuiContainerImpl guicontainerimpl = new GuiContainerImpl;
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.gui.inventory.GuiContainer");
        } else {
            guicontainerimpl.<init>((GuiContainer) gui);
            return (IGuiContainer) guicontainerimpl;
        }
    }

    @NotNull
    public IGuiGameOver asGuiGameOver() {
        GuiGameOverImpl guigameoverimpl = new GuiGameOverImpl;
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.gui.GuiGameOver");
        } else {
            guigameoverimpl.<init>((GuiGameOver) gui);
            return (IGuiGameOver) guigameoverimpl;
        }
    }

    @NotNull
    public IGuiChest asGuiChest() {
        GuiChestImpl guichestimpl = new GuiChestImpl;
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.gui.inventory.GuiChest");
        } else {
            guichestimpl.<init>((GuiChest) gui);
            return (IGuiChest) guichestimpl;
        }
    }

    public void superMouseReleased(int mouseX, int mouseY, int state) {
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        } else {
            ((GuiScreenWrapper) gui).superMouseReleased(mouseX, mouseY, state);
        }
    }

    public void drawBackground(int i) {
        ((GuiScreen) this.getWrapped()).drawBackground(i);
    }

    public void drawDefaultBackground() {
        ((GuiScreen) this.getWrapped()).drawDefaultBackground();
    }

    public void superKeyTyped(char typedChar, int keyCode) {
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        } else {
            ((GuiScreenWrapper) gui).superKeyTyped(typedChar, keyCode);
        }
    }

    public void superHandleMouseInput() {
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        } else {
            ((GuiScreenWrapper) gui).superHandleMouseInput();
        }
    }

    public void superMouseClicked(int mouseX, int mouseY, int mouseButton) {
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        } else {
            ((GuiScreenWrapper) gui).superMouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void superDrawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper");
        } else {
            ((GuiScreenWrapper) gui).superDrawScreen(mouseX, mouseY, partialTicks);
        }
    }

    public int getHeight() {
        return ((GuiScreen) this.getWrapped()).height;
    }

    public void setHeight(int value) {
        ((GuiScreen) this.getWrapped()).height = value;
    }

    public int getWidth() {
        return ((GuiScreen) this.getWrapped()).width;
    }

    public void setWidth(int value) {
        ((GuiScreen) this.getWrapped()).width = value;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof GuiScreenImpl && Intrinsics.areEqual((GuiScreen) ((GuiScreenImpl) other).getWrapped(), (GuiScreen) this.getWrapped());
    }

    public GuiScreenImpl(@NotNull GuiScreen wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Gui) wrapped);
    }
}
