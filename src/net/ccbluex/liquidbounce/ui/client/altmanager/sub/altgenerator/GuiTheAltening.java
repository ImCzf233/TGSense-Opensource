package net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService.EnumAltService;
import com.thealtening.api.TheAltening;
import com.thealtening.api.TheAltening.Asynchronous;
import com.thealtening.api.data.AccountData;
import java.net.Proxy;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.vitox.mcleaks.MCLeaks;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0016J \u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0012H\u0016J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0012H\u0016J\b\u0010\u001d\u001a\u00020\u000eH\u0016J\b\u0010\u001e\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006 "},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "apiKeyField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "generateButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "loginButton", "status", "", "tokenField", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "Companion", "LiquidBounce"}
)
public final class GuiTheAltening extends WrappedGuiScreen {

    private IGuiButton loginButton;
    private IGuiButton generateButton;
    private IGuiTextField apiKeyField;
    private IGuiTextField tokenField;
    private String status;
    private final GuiAltManager prevGui;
    @NotNull
    private static String apiKey = "";
    public static final GuiTheAltening.Companion Companion = new GuiTheAltening.Companion((DefaultConstructorMarker) null);

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.loginButton = MinecraftInstance.classProvider.createGuiButton(2, this.getRepresentedScreen().getWidth() / 2 - 100, 75, "Login");
        List list = this.getRepresentedScreen().getButtonList();
        IGuiButton iguibutton = this.loginButton;

        if (this.loginButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }

        list.add(iguibutton);
        this.generateButton = MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, 140, "Generate");
        list = this.getRepresentedScreen().getButtonList();
        iguibutton = this.generateButton;
        if (this.generateButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
        }

        list.add(iguibutton);
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() - 54, 98, 20, "Buy"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, this.getRepresentedScreen().getWidth() / 2 + 2, this.getRepresentedScreen().getHeight() - 54, 98, 20, "Back"));
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.tokenField = iclassprovider.createGuiTextField(666, ifontrenderer, this.getRepresentedScreen().getWidth() / 2 - 100, 50, 200, 20);
        IGuiTextField iguitextfield = this.tokenField;

        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.setFocused(true);
        iguitextfield = this.tokenField;
        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.setMaxStringLength(Integer.MAX_VALUE);
        iclassprovider = MinecraftInstance.classProvider;
        ifontrenderer = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.apiKeyField = iclassprovider.createGuiPasswordField(1337, ifontrenderer, this.getRepresentedScreen().getWidth() / 2 - 100, 115, 200, 20);
        iguitextfield = this.apiKeyField;
        if (this.apiKeyField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }

        iguitextfield.setMaxStringLength(18);
        iguitextfield = this.apiKeyField;
        if (this.apiKeyField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }

        iguitextfield.setText(GuiTheAltening.apiKey);
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0F, 30.0F, (float) this.getRepresentedScreen().getWidth() - 30.0F, (float) this.getRepresentedScreen().getHeight() - 30.0F, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("TheAltening", (float) this.getRepresentedScreen().getWidth() / 2.0F, 6.0F, 16777215);
        Fonts.font35.drawCenteredString(this.status, (float) this.getRepresentedScreen().getWidth() / 2.0F, 18.0F, 16777215);
        IGuiTextField iguitextfield = this.apiKeyField;

        if (this.apiKeyField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }

        iguitextfield.drawTextBox();
        iguitextfield = this.tokenField;
        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.drawTextBox();
        Fonts.font40.drawCenteredString("§7Token:", (float) this.getRepresentedScreen().getWidth() / 2.0F - (float) 84, 40.0F, 16777215);
        Fonts.font40.drawCenteredString("§7API-Key:", (float) this.getRepresentedScreen().getWidth() / 2.0F - (float) 78, 105.0F, 16777215);
        Fonts.font40.drawCenteredString("§7Use coupon code \'liquidbounce\' for 20% off!", (float) this.getRepresentedScreen().getWidth() / 2.0F, (float) this.getRepresentedScreen().getHeight() - 65.0F, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getEnabled()) {
            IGuiButton iguibutton;

            switch (button.getId()) {
            case 0:
                MinecraftInstance.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
                break;

            case 1:
                iguibutton = this.loginButton;
                if (this.loginButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                }

                iguibutton.setEnabled(false);
                iguibutton = this.generateButton;
                if (this.generateButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generateButton");
                }

                iguibutton.setEnabled(false);
                IGuiTextField iguitextfield = this.apiKeyField;

                if (this.apiKeyField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
                }

                GuiTheAltening.apiKey = iguitextfield.getText();
                TheAltening altening = new TheAltening(GuiTheAltening.apiKey);
                Asynchronous asynchronous = new Asynchronous(altening);

                this.status = "§cGenerating account...";
                asynchronous.getAccountData().thenAccept((Consumer) (new Consumer() {
                    public final void accept(AccountData account) {
                        GuiTheAltening guithealtening = GuiTheAltening.this;
                        StringBuilder stringbuilder = (new StringBuilder()).append("§aGenerated account: §b§l");

                        Intrinsics.checkExpressionValueIsNotNull(account, "account");
                        guithealtening.status = stringbuilder.append(account.getUsername()).toString();

                        try {
                            GuiTheAltening.this.status = "§cSwitching Alt Service...";
                            GuiAltManager.altService.switchService(EnumAltService.THEALTENING);
                            GuiTheAltening.this.status = "§cLogging in...";
                            YggdrasilUserAuthentication throwable = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);

                            throwable.setUsername(account.getToken());
                            throwable.setPassword("TGSense");
                            GuiTheAltening guithealtening1 = GuiTheAltening.this;

                            String s;

                            try {
                                guithealtening = guithealtening1;
                                throwable.logIn();
                                IMinecraft iminecraft = MinecraftInstance.mc;
                                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                                GameProfile gameprofile = throwable.getSelectedProfile();

                                Intrinsics.checkExpressionValueIsNotNull(gameprofile, "yggdrasilUserAuthentication.selectedProfile");
                                String s1 = gameprofile.getName();

                                Intrinsics.checkExpressionValueIsNotNull(s1, "yggdrasilUserAuthentication.selectedProfile.name");
                                GameProfile gameprofile1 = throwable.getSelectedProfile();

                                Intrinsics.checkExpressionValueIsNotNull(gameprofile1, "yggdrasilUserAuthenticat�?         .selectedProfile");
                                String s2 = gameprofile1.getId().toString();

                                Intrinsics.checkExpressionValueIsNotNull(s2, "yggdrasilUserAuthenticat…ctedProfile.id.toString()");
                                String s3 = throwable.getAuthenticatedToken();

                                Intrinsics.checkExpressionValueIsNotNull(s3, "yggdrasilUserAuthentication.authenticatedToken");
                                iminecraft.setSession(iclassprovider.createSession(s1, s2, s3, "mojang"));
                                LiquidBounce.INSTANCE.getEventManager().callEvent((Event) (new SessionEvent()));
                                MCLeaks.remove();
                                GuiAltManager guialtmanager = GuiTheAltening.this.prevGui;
                                StringBuilder stringbuilder1 = (new StringBuilder()).append("§aYour name is now §b§l");

                                gameprofile = throwable.getSelectedProfile();
                                Intrinsics.checkExpressionValueIsNotNull(gameprofile, "yggdrasilUserAuthentication.selectedProfile");
                                guialtmanager.status = stringbuilder1.append(gameprofile.getName()).append("§c.").toString();
                                MinecraftInstance.mc.displayGuiScreen(GuiTheAltening.this.prevGui.getRepresentedScreen());
                                s = "";
                            } catch (AuthenticationException authenticationexception) {
                                guithealtening = guithealtening1;
                                GuiAltManager.altService.switchService(EnumAltService.MOJANG);
                                ClientUtils.getLogger().error("Failed to login.", (Throwable) authenticationexception);
                                s = "§cFailed to login: " + authenticationexception.getMessage();
                            }

                            guithealtening.status = s;
                        } catch (Throwable throwable) {
                            GuiTheAltening.this.status = "§cFailed to login. Unknown error.";
                            ClientUtils.getLogger().error("Failed to login.", throwable);
                        }

                        GuiTheAltening.access$getLoginButton$p(GuiTheAltening.this).setEnabled(true);
                        GuiTheAltening.access$getGenerateButton$p(GuiTheAltening.this).setEnabled(true);
                    }
                })).handle((BiFunction) (new BiFunction() {
                    public final void apply(Void $noName_0, Throwable err) {
                        GuiTheAltening.this.status = "§cFailed to generate account.";
                        ClientUtils.getLogger().error("Failed to generate account.", err);
                    }
                })).whenComplete((BiConsumer) (new BiConsumer() {
                    public final void accept(Unit $noName_0, Throwable $noName_1) {
                        GuiTheAltening.access$getLoginButton$p(GuiTheAltening.this).setEnabled(true);
                        GuiTheAltening.access$getGenerateButton$p(GuiTheAltening.this).setEnabled(true);
                    }
                }));
                break;

            case 2:
                iguibutton = this.loginButton;
                if (this.loginButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                }

                iguibutton.setEnabled(false);
                iguibutton = this.generateButton;
                if (this.generateButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generateButton");
                }

                iguibutton.setEnabled(false);
                (new Thread((Runnable) (new Runnable() {
                    public final void run() {
                        try {
                            GuiTheAltening.this.status = "§cSwitching Alt Service...";
                            GuiAltManager.altService.switchService(EnumAltService.THEALTENING);
                            GuiTheAltening.this.status = "§cLogging in...";
                            YggdrasilUserAuthentication throwable = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);

                            throwable.setUsername(GuiTheAltening.access$getTokenField$p(GuiTheAltening.this).getText());
                            throwable.setPassword("TGSense");
                            GuiTheAltening guithealtening = GuiTheAltening.this;

                            String s;
                            GuiTheAltening guithealtening1;

                            try {
                                guithealtening1 = guithealtening;
                                throwable.logIn();
                                IMinecraft iminecraft = MinecraftInstance.mc;
                                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                                GameProfile gameprofile = throwable.getSelectedProfile();

                                Intrinsics.checkExpressionValueIsNotNull(gameprofile, "yggdrasilUserAuthentication.selectedProfile");
                                String s1 = gameprofile.getName();

                                Intrinsics.checkExpressionValueIsNotNull(s1, "yggdrasilUserAuthentication.selectedProfile.name");
                                GameProfile gameprofile1 = throwable.getSelectedProfile();

                                Intrinsics.checkExpressionValueIsNotNull(gameprofile1, "yggdrasilUserAuthenticat�?         .selectedProfile");
                                String s2 = gameprofile1.getId().toString();

                                Intrinsics.checkExpressionValueIsNotNull(s2, "yggdrasilUserAuthenticat…ctedProfile.id.toString()");
                                String s3 = throwable.getAuthenticatedToken();

                                Intrinsics.checkExpressionValueIsNotNull(s3, "yggdrasilUserAuthentication.authenticatedToken");
                                iminecraft.setSession(iclassprovider.createSession(s1, s2, s3, "mojang"));
                                LiquidBounce.INSTANCE.getEventManager().callEvent((Event) (new SessionEvent()));
                                MCLeaks.remove();
                                GuiAltManager guialtmanager = GuiTheAltening.this.prevGui;
                                StringBuilder stringbuilder = (new StringBuilder()).append("§aYour name is now §b§l");

                                gameprofile = throwable.getSelectedProfile();
                                Intrinsics.checkExpressionValueIsNotNull(gameprofile, "yggdrasilUserAuthentication.selectedProfile");
                                guialtmanager.status = stringbuilder.append(gameprofile.getName()).append("§c.").toString();
                                MinecraftInstance.mc.displayGuiScreen(GuiTheAltening.this.prevGui.getRepresentedScreen());
                                StringBuilder stringbuilder1 = (new StringBuilder()).append("§aYour name is now §b§l");
                                GameProfile gameprofile2 = throwable.getSelectedProfile();

                                Intrinsics.checkExpressionValueIsNotNull(gameprofile2, "yggdrasilUserAuthentication.selectedProfile");
                                s = stringbuilder1.append(gameprofile2.getName()).append("§c.").toString();
                            } catch (AuthenticationException authenticationexception) {
                                guithealtening1 = guithealtening;
                                GuiAltManager.altService.switchService(EnumAltService.MOJANG);
                                ClientUtils.getLogger().error("Failed to login.", (Throwable) authenticationexception);
                                s = "§cFailed to login: " + authenticationexception.getMessage();
                            }

                            guithealtening1.status = s;
                        } catch (Throwable throwable) {
                            ClientUtils.getLogger().error("Failed to login.", throwable);
                            GuiTheAltening.this.status = "§cFailed to login. Unknown error.";
                        }

                        GuiTheAltening.access$getLoginButton$p(GuiTheAltening.this).setEnabled(true);
                        GuiTheAltening.access$getGenerateButton$p(GuiTheAltening.this).setEnabled(true);
                    }
                }))).start();
                break;

            case 3:
                MiscUtils.showURL("https://thealtening.com/?ref=liquidbounce");
            }

        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
        } else {
            IGuiTextField iguitextfield = this.apiKeyField;

            if (this.apiKeyField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            }

            if (iguitextfield.isFocused()) {
                iguitextfield = this.apiKeyField;
                if (this.apiKeyField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
                }

                iguitextfield.textboxKeyTyped(typedChar, keyCode);
            }

            iguitextfield = this.tokenField;
            if (this.tokenField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }

            if (iguitextfield.isFocused()) {
                iguitextfield = this.tokenField;
                if (this.tokenField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tokenField");
                }

                iguitextfield.textboxKeyTyped(typedChar, keyCode);
            }

            super.keyTyped(typedChar, keyCode);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        IGuiTextField iguitextfield = this.apiKeyField;

        if (this.apiKeyField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }

        iguitextfield.mouseClicked(mouseX, mouseY, mouseButton);
        iguitextfield = this.tokenField;
        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        IGuiTextField iguitextfield = this.apiKeyField;

        if (this.apiKeyField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }

        iguitextfield.updateCursorCounter();
        iguitextfield = this.tokenField;
        if (this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        iguitextfield.updateCursorCounter();
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        IGuiTextField iguitextfield = this.apiKeyField;

        if (this.apiKeyField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
        }

        GuiTheAltening.apiKey = iguitextfield.getText();
        super.onGuiClosed();
    }

    public GuiTheAltening(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        super();
        this.prevGui = prevGui;
        this.status = "";
    }

    public static final String access$getStatus$p(GuiTheAltening $this) {
        return $this.status;
    }

    public static final IGuiButton access$getLoginButton$p(GuiTheAltening $this) {
        IGuiButton iguibutton = $this.loginButton;

        if ($this.loginButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loginButton");
        }

        return iguibutton;
    }

    public static final void access$setLoginButton$p(GuiTheAltening $this, IGuiButton <set-?>) {
        $this.loginButton = <set-?>;
    }

    public static final IGuiButton access$getGenerateButton$p(GuiTheAltening $this) {
        IGuiButton iguibutton = $this.generateButton;

        if ($this.generateButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generateButton");
        }

        return iguibutton;
    }

    public static final void access$setGenerateButton$p(GuiTheAltening $this, IGuiButton <set-?>) {
        $this.generateButton = <set-?>;
    }

    public static final IGuiTextField access$getTokenField$p(GuiTheAltening $this) {
        IGuiTextField iguitextfield = $this.tokenField;

        if ($this.tokenField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
        }

        return iguitextfield;
    }

    public static final void access$setTokenField$p(GuiTheAltening $this, IGuiTextField <set-?>) {
        $this.tokenField = <set-?>;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening$Companion;", "", "()V", "apiKey", "", "getApiKey", "()Ljava/lang/String;", "setApiKey", "(Ljava/lang/String;)V", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final String getApiKey() {
            return GuiTheAltening.apiKey;
        }

        public final void setApiKey(@NotNull String <set-?>) {
            Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
            GuiTheAltening.apiKey = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
