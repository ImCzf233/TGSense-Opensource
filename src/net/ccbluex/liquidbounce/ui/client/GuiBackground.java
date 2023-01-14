package net.ccbluex.liquidbounce.ui.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0016J \u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000bH\u0016J\u0018\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0019"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiBackground;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "enabledButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "particlesButton", "getPrevGui", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "Companion", "LiquidBounce"}
)
public final class GuiBackground extends WrappedGuiScreen {

    private IGuiButton enabledButton;
    private IGuiButton particlesButton;
    @NotNull
    private final IGuiScreen prevGui;
    private static boolean enabled = true;
    private static boolean particles;
    public static final GuiBackground.Companion Companion = new GuiBackground.Companion((DefaultConstructorMarker) null);

    public void initGui() {
        this.enabledButton = MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 35, "Enabled (" + (GuiBackground.enabled ? "On" : "Off") + ')');
        List list = this.getRepresentedScreen().getButtonList();
        IGuiButton iguibutton = this.enabledButton;

        if (this.enabledButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
        }

        list.add(iguibutton);
        this.particlesButton = MinecraftInstance.classProvider.createGuiButton(2, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 50 + 25, "Particles (" + (GuiBackground.particles ? "On" : "Off") + ')');
        list = this.getRepresentedScreen().getButtonList();
        iguibutton = this.particlesButton;
        if (this.particlesButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
        }

        list.add(iguibutton);
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 50 + 50, 98, 20, "Change wallpaper"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(4, this.getRepresentedScreen().getWidth() / 2 + 2, this.getRepresentedScreen().getHeight() / 4 + 50 + 50, 98, 20, "Reset wallpaper"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 55 + 100 + 5, "Back"));
    }

    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        IGuiButton iguibutton;

        switch (button.getId()) {
        case 0:
            MinecraftInstance.mc.displayGuiScreen(this.prevGui);
            break;

        case 1:
            GuiBackground.enabled = !GuiBackground.enabled;
            iguibutton = this.enabledButton;
            if (this.enabledButton == null) {
                Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
            }

            iguibutton.setDisplayString("Enabled (" + (GuiBackground.enabled ? "On" : "Off") + ')');
            break;

        case 2:
            GuiBackground.particles = !GuiBackground.particles;
            iguibutton = this.particlesButton;
            if (this.particlesButton == null) {
                Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
            }

            iguibutton.setDisplayString("Particles (" + (GuiBackground.particles ? "On" : "Off") + ')');
            break;

        case 3:
            File file = MiscUtils.openFileChooser();

            if (file == null) {
                return;
            }

            File file = file;

            if (file.isDirectory()) {
                return;
            }

            try {
                Files.copy(file.toPath(), (OutputStream) (new FileOutputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile)));
                BufferedImage e = ImageIO.read((InputStream) (new FileInputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile)));
            } catch (Exception exception) {
                exception.printStackTrace();
                MiscUtils.showErrorPopup("Error", "Exception class: " + exception.getClass().getName() + "\nMessage: " + exception.getMessage());
                LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
            }
            break;

        case 4:
            LiquidBounce.INSTANCE.setBackground((IResourceLocation) null);
            LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        Fonts.fontBold180.drawCenteredString("Background", (float) this.getRepresentedScreen().getWidth() / 2.0F, (float) this.getRepresentedScreen().getHeight() / 8.0F + 5.0F, 4673984, true);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @NotNull
    public final IGuiScreen getPrevGui() {
        return this.prevGui;
    }

    public GuiBackground(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        super();
        this.prevGui = prevGui;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiBackground$Companion;", "", "()V", "enabled", "", "getEnabled", "()Z", "setEnabled", "(Z)V", "particles", "getParticles", "setParticles", "LiquidBounce"}
    )
    public static final class Companion {

        public final boolean getEnabled() {
            return GuiBackground.enabled;
        }

        public final void setEnabled(boolean <set-?>) {
            GuiBackground.enabled = <set-?>;
        }

        public final boolean getParticles() {
            return GuiBackground.particles;
        }

        public final void setParticles(boolean <set-?>) {
            GuiBackground.particles = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
