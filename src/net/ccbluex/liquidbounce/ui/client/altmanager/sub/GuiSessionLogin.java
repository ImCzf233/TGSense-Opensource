package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import com.thealtening.AltService;
import com.thealtening.AltService.EnumAltService;
import java.util.List;
import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
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
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.vitox.mcleaks.MCLeaks;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016J \u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J \u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiSessionLogin;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "loginButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "sessionTokenField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "status", "", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "LiquidBounce"}
)
public final class GuiSessionLogin extends WrappedGuiScreen {

    private IGuiButton loginButton;
    private IGuiTextField sessionTokenField;
    private String status;
    private final GuiAltManager prevGui;

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.loginButton = MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 96, "Login");
        List list = this.getRepresentedScreen().getButtonList();
        IGuiButton iguibutton = this.loginButton;

        if (this.loginButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }

        list.add(iguibutton);
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 120, "Back"));
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.sessionTokenField = iclassprovider.createGuiTextField(666, ifontrenderer, this.getRepresentedScreen().getWidth() / 2 - 100, 80, 200, 20);
        IGuiTextField iguitextfield = this.sessionTokenField;

        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }

        iguitextfield.setFocused(true);
        iguitextfield = this.sessionTokenField;
        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }

        iguitextfield.setMaxStringLength(Integer.MAX_VALUE);
        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }

        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0F, 30.0F, (float) this.getRepresentedScreen().getWidth() - 30.0F, (float) this.getRepresentedScreen().getHeight() - 30.0F, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("Session Login", (float) this.getRepresentedScreen().getWidth() / 2.0F, 36.0F, 16777215);
        Fonts.font35.drawCenteredString(this.status, (float) this.getRepresentedScreen().getWidth() / 2.0F, (float) this.getRepresentedScreen().getHeight() / 4.0F + 80.0F, 16777215);
        IGuiTextField iguitextfield = this.sessionTokenField;

        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }

        iguitextfield.drawTextBox();
        Fonts.font40.drawCenteredString("§7Session Token:", (float) this.getRepresentedScreen().getWidth() / 2.0F - 65.0F, 66.0F, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getEnabled()) {
            switch (button.getId()) {
            case 0:
                MinecraftInstance.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
                break;

            case 1:
                IGuiButton iguibutton = this.loginButton;

                if (this.loginButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                }

                iguibutton.setEnabled(false);
                this.status = "§aLogging in...";
                ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
                    public final void invoke() {
                        LoginUtils.LoginResult loginResult = LoginUtils.loginSessionId(GuiSessionLogin.access$getSessionTokenField$p(GuiSessionLogin.this).getText());
                        GuiSessionLogin guisessionlogin = GuiSessionLogin.this;
                        String s;

                        switch (GuiSessionLogin$WhenMappings.$EnumSwitchMapping$0[loginResult.ordinal()]) {
                        case 1:
                            AltService altservice = GuiAltManager.altService;

                            Intrinsics.checkExpressionValueIsNotNull(GuiAltManager.altService, "GuiAltManager.altService");
                            if (altservice.getCurrentService() != EnumAltService.MOJANG) {
                                GuiSessionLogin guisessionlogin1 = guisessionlogin;

                                try {
                                    guisessionlogin = guisessionlogin1;
                                    GuiAltManager.altService.switchService(EnumAltService.MOJANG);
                                } catch (NoSuchFieldException nosuchfieldexception) {
                                    guisessionlogin = guisessionlogin1;
                                    ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", (Throwable) nosuchfieldexception);
                                } catch (IllegalAccessException illegalaccessexception) {
                                    guisessionlogin = guisessionlogin1;
                                    ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", (Throwable) illegalaccessexception);
                                }
                            }

                            MCLeaks.remove();
                            s = "§cYour name is now §f§l" + MinecraftInstance.mc.getSession().getUsername() + "§c";
                            break;

                        case 2:
                            s = "§cFailed to parse Session ID!";
                            break;

                        case 3:
                            s = "§cInvalid Session ID!";
                            break;

                        default:
                            s = "";
                        }

                        guisessionlogin.status = s;
                        GuiSessionLogin.access$getLoginButton$p(GuiSessionLogin.this).setEnabled(true);
                    }
                }), 31, (Object) null);
            }

        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
        } else {
            IGuiTextField iguitextfield = this.sessionTokenField;

            if (this.sessionTokenField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            }

            if (iguitextfield.isFocused()) {
                iguitextfield = this.sessionTokenField;
                if (this.sessionTokenField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
                }

                iguitextfield.textboxKeyTyped(typedChar, keyCode);
            }

            super.keyTyped(typedChar, keyCode);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        IGuiTextField iguitextfield = this.sessionTokenField;

        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }

        iguitextfield.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        IGuiTextField iguitextfield = this.sessionTokenField;

        if (this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }

        iguitextfield.updateCursorCounter();
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    public GuiSessionLogin(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        super();
        this.prevGui = prevGui;
        this.status = "";
    }

    public static final IGuiTextField access$getSessionTokenField$p(GuiSessionLogin $this) {
        IGuiTextField iguitextfield = $this.sessionTokenField;

        if ($this.sessionTokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
        }

        return iguitextfield;
    }

    public static final void access$setSessionTokenField$p(GuiSessionLogin $this, IGuiTextField <set-?>) {
        $this.sessionTokenField = <set-?>;
    }

    public static final String access$getStatus$p(GuiSessionLogin $this) {
        return $this.status;
    }

    public static final IGuiButton access$getLoginButton$p(GuiSessionLogin $this) {
        IGuiButton iguibutton = $this.loginButton;

        if ($this.loginButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }

        return iguibutton;
    }

    public static final void access$setLoginButton$p(GuiSessionLogin $this, IGuiButton <set-?>) {
        $this.loginButton = <set-?>;
    }
}
