package net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator;

import com.thealtening.AltService.EnumAltService;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.vitox.mcleaks.Callback;
import net.vitox.mcleaks.MCLeaks;
import net.vitox.mcleaks.RedeemResponse;
import net.vitox.mcleaks.Session;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\nH\u0016J\u0018\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J \u0010\u0018\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000fH\u0016J\b\u0010\u001a\u001a\u00020\nH\u0016J\b\u0010\u001b\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiMCLeaks;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "status", "", "tokenField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "LiquidBounce"}
)
public final class GuiMCLeaks extends WrappedGuiScreen {

    private IGuiTextField tokenField;
    private String status;
    private final GuiAltManager prevGui;

    public void updateScreen() {
        IGuiTextField iguitextfield = this.tokenField;

        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.updateCursorCounter();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        if (MCLeaks.isAltActive()) {
            StringBuilder stringbuilder = (new StringBuilder()).append("§aToken active. Using §9");
            Session session = MCLeaks.getSession();

            Intrinsics.checkExpressionValueIsNotNull(session, "MCLeaks.getSession()");
            this.status = stringbuilder.append(session.getUsername()).append("§a to login!").toString();
        }

        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 65, 200, 20, "Login"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() - 54, 98, 20, "Get Token"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, this.getRepresentedScreen().getWidth() / 2 + 2, this.getRepresentedScreen().getHeight() - 54, 98, 20, "Back"));
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.tokenField = iclassprovider.createGuiTextField(0, ifontrenderer, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 40, 200, 20);
        IGuiTextField iguitextfield = this.tokenField;

        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.setFocused(true);
        iguitextfield = this.tokenField;
        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.setMaxStringLength(16);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void actionPerformed(@NotNull final IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getEnabled()) {
            switch (button.getId()) {
            case 1:
                IGuiTextField iguitextfield = this.tokenField;

                if (this.tokenField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                }

                if (iguitextfield.getText().length() != 16) {
                    this.status = "§cThe token has to be 16 characters long!";
                    return;
                }

                button.setEnabled(false);
                button.setDisplayString("Please wait ...");
                iguitextfield = this.tokenField;
                if (this.tokenField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                }

                MCLeaks.redeem(iguitextfield.getText(), (Callback) (new Callback() {
                    public final void done(Object it) {
                        if (it instanceof String) {
                            GuiMCLeaks.this.status = "§c" + it;
                            button.setEnabled(true);
                            button.setDisplayString("Login");
                        } else if (it == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.vitox.mcleaks.RedeemResponse");
                        } else {
                            RedeemResponse redeemResponse = (RedeemResponse) it;

                            MCLeaks.refresh(new Session(redeemResponse.getUsername(), redeemResponse.getToken()));

                            try {
                                GuiAltManager.altService.switchService(EnumAltService.MOJANG);
                            } catch (Exception exception) {
                                ClientUtils.getLogger().error("Failed to change alt service to Mojang.", (Throwable) exception);
                            }

                            GuiMCLeaks.this.status = "§aYour token was redeemed successfully!";
                            button.setEnabled(true);
                            button.setDisplayString("Login");
                            GuiMCLeaks.this.prevGui.status = GuiMCLeaks.this.status;
                            MinecraftInstance.mc.displayGuiScreen(GuiMCLeaks.this.prevGui.getRepresentedScreen());
                        }
                    }
                }));
                break;

            case 2:
                MiscUtils.showURL("https://mcleaks.net/");
                break;

            case 3:
                MinecraftInstance.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
            }

        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        IGuiTextField iguitextfield;

        switch (keyCode) {
        case 1:
            MinecraftInstance.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
            break;

        case 15:
            iguitextfield = this.tokenField;
            if (this.tokenField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }

            IGuiTextField iguitextfield1 = this.tokenField;

            if (this.tokenField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }

            iguitextfield.setFocused(!iguitextfield1.isFocused());
            break;

        case 28:
        case 156:
            this.actionPerformed((IGuiButton) this.getRepresentedScreen().getButtonList().get(1));
            break;

        default:
            iguitextfield = this.tokenField;
            if (this.tokenField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }

            iguitextfield.textboxKeyTyped(typedChar, keyCode);
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        IGuiTextField iguitextfield = this.tokenField;

        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0F, 30.0F, (float) this.getRepresentedScreen().getWidth() - 30.0F, (float) this.getRepresentedScreen().getHeight() - 30.0F, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("MCLeaks", (float) this.getRepresentedScreen().getWidth() / 2.0F, 6.0F, 16777215);
        Fonts.font40.drawString("Token:", (float) this.getRepresentedScreen().getWidth() / 2.0F - (float) 100, (float) this.getRepresentedScreen().getHeight() / 4.0F + (float) 30, 10526880);
        String status = this.status;

        if (status != null) {
            Fonts.font40.drawCenteredString(status, (float) this.getRepresentedScreen().getWidth() / 2.0F, 18.0F, 16777215);
        }

        IGuiTextField iguitextfield = this.tokenField;

        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public GuiMCLeaks(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        super();
        this.prevGui = prevGui;
    }
}
