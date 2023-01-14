package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.util.List;
import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016J \u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J \u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "stateButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "status", "", "transferCodeField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "Companion", "LiquidBounce"}
)
public final class GuiDonatorCape extends WrappedGuiScreen {

    private IGuiButton stateButton;
    private IGuiTextField transferCodeField;
    private String status;
    private final GuiAltManager prevGui;
    @NotNull
    private static String transferCode = "";
    private static boolean capeEnabled = true;
    public static final GuiDonatorCape.Companion Companion = new GuiDonatorCape.Companion((DefaultConstructorMarker) null);

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.stateButton = MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, 105, "Disable Cape");
        List list = this.getRepresentedScreen().getButtonList();
        IGuiButton iguibutton = this.stateButton;

        if (this.stateButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stateButton");
        }

        list.add(iguibutton);
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 96, "Donate to get Cape"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 120, "Back"));
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.transferCodeField = iclassprovider.createGuiPasswordField(666, ifontrenderer, this.getRepresentedScreen().getWidth() / 2 - 100, 80, 200, 20);
        IGuiTextField iguitextfield = this.transferCodeField;

        if (this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        iguitextfield.setFocused(true);
        iguitextfield = this.transferCodeField;
        if (this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        iguitextfield.setMaxStringLength(Integer.MAX_VALUE);
        iguitextfield = this.transferCodeField;
        if (this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        iguitextfield.setText(GuiDonatorCape.transferCode);
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30.0F, 30.0F, (float) this.getRepresentedScreen().getWidth() - 30.0F, (float) this.getRepresentedScreen().getHeight() - 30.0F, Integer.MIN_VALUE);
        Fonts.font35.drawCenteredString("Donator Cape", (float) this.getRepresentedScreen().getWidth() / 2.0F, 36.0F, 16777215);
        Fonts.font35.drawCenteredString(this.status, (float) this.getRepresentedScreen().getWidth() / 2.0F, (float) this.getRepresentedScreen().getHeight() / 4.0F + (float) 80, 16777215);
        IGuiTextField iguitextfield = this.transferCodeField;

        if (this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        iguitextfield.drawTextBox();
        Fonts.font40.drawCenteredString("§7Transfer Code:", (float) this.getRepresentedScreen().getWidth() / 2.0F - (float) 65, 66.0F, 16777215);
        IGuiButton iguibutton = this.stateButton;

        if (this.stateButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stateButton");
        }

        iguibutton.setDisplayString(GuiDonatorCape.capeEnabled ? "Disable Cape" : "Enable Cape");
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
                IGuiButton iguibutton = this.stateButton;

                if (this.stateButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("stateButton");
                }

                iguibutton.setEnabled(false);
                ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
                    public final void invoke() {
                        CloseableHttpClient httpClient = HttpClients.createDefault();
                        BasicHeader[] headers = new BasicHeader[] { new BasicHeader("Content-Type", "application/json"), new BasicHeader("Authorization", GuiDonatorCape.access$getTransferCodeField$p(GuiDonatorCape.this).getText())};
                        HttpRequestBase request = GuiDonatorCape.Companion.getCapeEnabled() ? (HttpRequestBase) (new HttpDelete("http://capes.liquidbounce.net/api/v1/cape/self")) : (HttpRequestBase) (new HttpPut("http://capes.liquidbounce.net/api/v1/cape/self"));

                        request.setHeaders((Header[]) headers);
                        CloseableHttpResponse response = httpClient.execute((HttpUriRequest) request);

                        Intrinsics.checkExpressionValueIsNotNull(response, "response");
                        StatusLine statusline = response.getStatusLine();

                        Intrinsics.checkExpressionValueIsNotNull(statusline, "response.statusLine");
                        int statusCode = statusline.getStatusCode();
                        GuiDonatorCape guidonatorcape = GuiDonatorCape.this;
                        String s;

                        if (statusCode == 204) {
                            GuiDonatorCape.Companion.setCapeEnabled(!GuiDonatorCape.Companion.getCapeEnabled());
                            s = GuiDonatorCape.Companion.getCapeEnabled() ? "§aSuccessfully enabled cape" : "§aSuccessfully disabled cape";
                        } else {
                            s = "§cFailed to toggle cape (" + statusCode + ')';
                        }

                        guidonatorcape.status = s;
                        GuiDonatorCape.access$getStateButton$p(GuiDonatorCape.this).setEnabled(true);
                    }
                }), 31, (Object) null);
                break;

            case 2:
                MiscUtils.showURL("https://donate.liquidbounce.net");
            }

        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
        } else {
            IGuiTextField iguitextfield = this.transferCodeField;

            if (this.transferCodeField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
            }

            if (iguitextfield.isFocused()) {
                iguitextfield = this.transferCodeField;
                if (this.transferCodeField == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
                }

                iguitextfield.textboxKeyTyped(typedChar, keyCode);
            }

            super.keyTyped(typedChar, keyCode);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        IGuiTextField iguitextfield = this.transferCodeField;

        if (this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        iguitextfield.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        IGuiTextField iguitextfield = this.transferCodeField;

        if (this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        iguitextfield.updateCursorCounter();
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        IGuiTextField iguitextfield = this.transferCodeField;

        if (this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        GuiDonatorCape.transferCode = iguitextfield.getText();
        super.onGuiClosed();
    }

    public GuiDonatorCape(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        super();
        this.prevGui = prevGui;
        this.status = "";
    }

    public static final IGuiTextField access$getTransferCodeField$p(GuiDonatorCape $this) {
        IGuiTextField iguitextfield = $this.transferCodeField;

        if ($this.transferCodeField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
        }

        return iguitextfield;
    }

    public static final void access$setTransferCodeField$p(GuiDonatorCape $this, IGuiTextField <set-?>) {
        $this.transferCodeField = <set-?>;
    }

    public static final String access$getStatus$p(GuiDonatorCape $this) {
        return $this.status;
    }

    public static final IGuiButton access$getStateButton$p(GuiDonatorCape $this) {
        IGuiButton iguibutton = $this.stateButton;

        if ($this.stateButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stateButton");
        }

        return iguibutton;
    }

    public static final void access$setStateButton$p(GuiDonatorCape $this, IGuiButton <set-?>) {
        $this.stateButton = <set-?>;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape$Companion;", "", "()V", "capeEnabled", "", "getCapeEnabled", "()Z", "setCapeEnabled", "(Z)V", "transferCode", "", "getTransferCode", "()Ljava/lang/String;", "setTransferCode", "(Ljava/lang/String;)V", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final String getTransferCode() {
            return GuiDonatorCape.transferCode;
        }

        public final void setTransferCode(@NotNull String <set-?>) {
            Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
            GuiDonatorCape.transferCode = <set-?>;
        }

        public final boolean getCapeEnabled() {
            return GuiDonatorCape.capeEnabled;
        }

        public final void setCapeEnabled(boolean <set-?>) {
            GuiDonatorCape.capeEnabled = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
