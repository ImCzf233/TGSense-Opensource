package net.ccbluex.liquidbounce.ui.client;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.Skid.ui.Client.JelloMainMenu;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\b\u0010\u0010\u001a\u00020\u0006H\u0016J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000bH\u0016R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "()V", "list", "Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "GuiList", "LiquidBounce"}
)
public final class GuiScripts extends WrappedGuiScreen {

    private GuiScripts.GuiList list;

    public void initGui() {
        this.list = new GuiScripts.GuiList(this.getRepresentedScreen());
        GuiScripts.GuiList guiscripts_guilist = this.list;

        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guiscripts_guilist.getRepresented().registerScrollButtons(7, 8);
        guiscripts_guilist = this.list;
        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guiscripts_guilist.elementClicked(-1, false, 0, 0);
        byte j = 22;

        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, this.getRepresentedScreen().getWidth() - 80, this.getRepresentedScreen().getHeight() - 65, 70, 20, "Back"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() - 80, j + 24, 70, 20, "Import"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, this.getRepresentedScreen().getWidth() - 80, j + 48, 70, 20, "Delete"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, this.getRepresentedScreen().getWidth() - 80, j + 72, 70, 20, "Reload"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(4, this.getRepresentedScreen().getWidth() - 80, j + 96, 70, 20, "Folder"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(5, this.getRepresentedScreen().getWidth() - 80, j + 120, 70, 20, "Docs"));
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(6, this.getRepresentedScreen().getWidth() - 80, j + 144, 70, 20, "Find Scripts"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        GuiScripts.GuiList guiscripts_guilist = this.list;

        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guiscripts_guilist.getRepresented().drawScreen(mouseX, mouseY, partialTicks);
        Fonts.font40.drawCenteredString("§9§lScripts", (float) this.getRepresentedScreen().getWidth() / 2.0F, 28.0F, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        switch (button.getId()) {
        case 0:
            MinecraftInstance.mc2.displayGuiScreen((GuiScreen) (new JelloMainMenu()));
            break;

        case 1:
            try {
                File file = MiscUtils.openFileChooser();

                if (file == null) {
                    return;
                }

                File t1 = file;
                String fileName = t1.getName();

                Intrinsics.checkExpressionValueIsNotNull(fileName, "fileName");
                if (StringsKt.endsWith$default(fileName, ".js", false, 2, (Object) null)) {
                    LiquidBounce.INSTANCE.getScriptManager().importScript(t1);
                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    return;
                }

                if (StringsKt.endsWith$default(fileName, ".zip", false, 2, (Object) null)) {
                    ZipFile zipFile = new ZipFile(t1);
                    Enumeration entries = zipFile.entries();
                    ArrayList scriptFiles = new ArrayList();

                    while (entries.hasMoreElements()) {
                        ZipEntry $this$forEach$iv = (ZipEntry) entries.nextElement();

                        Intrinsics.checkExpressionValueIsNotNull($this$forEach$iv, "entry");
                        String $i$f$forEach = $this$forEach$iv.getName();
                        File entryFile = new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), $i$f$forEach);

                        if ($this$forEach$iv.isDirectory()) {
                            entryFile.mkdir();
                        } else {
                            InputStream element$iv = zipFile.getInputStream($this$forEach$iv);
                            FileOutputStream scriptFile = new FileOutputStream(entryFile);

                            IOUtils.copy(element$iv, (OutputStream) scriptFile);
                            scriptFile.close();
                            element$iv.close();
                            Intrinsics.checkExpressionValueIsNotNull($i$f$forEach, "entryName");
                            if (!StringsKt.contains$default((CharSequence) $i$f$forEach, (CharSequence) "/", false, 2, (Object) null)) {
                                scriptFiles.add(entryFile);
                            }
                        }
                    }

                    Iterable $this$forEach$iv1 = (Iterable) scriptFiles;
                    boolean $i$f$forEach1 = false;
                    Iterator entryFile1 = $this$forEach$iv1.iterator();

                    while (entryFile1.hasNext()) {
                        Object element$iv1 = entryFile1.next();
                        File scriptFile1 = (File) element$iv1;
                        boolean $i$a$-forEach-GuiScripts$actionPerformed$1 = false;

                        LiquidBounce.INSTANCE.getScriptManager().loadScript(scriptFile1);
                    }

                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                    return;
                }

                MiscUtils.showErrorPopup("Wrong file extension.", "The file extension has to be .js or .zip");
            } catch (Throwable throwable) {
                ClientUtils.getLogger().error("Something went wrong while importing a script.", throwable);
                MiscUtils.showErrorPopup(throwable.getClass().getName(), throwable.getMessage());
            }
            break;

        case 2:
            try {
                GuiScripts.GuiList guiscripts_guilist = this.list;

                if (this.list == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("list");
                }

                if (guiscripts_guilist.getSelectedSlot$LiquidBounce() != -1) {
                    List list = LiquidBounce.INSTANCE.getScriptManager().getScripts();
                    GuiScripts.GuiList guiscripts_guilist1 = this.list;

                    if (this.list == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("list");
                    }

                    Script t = (Script) list.get(guiscripts_guilist1.getSelectedSlot$LiquidBounce());

                    LiquidBounce.INSTANCE.getScriptManager().deleteScript(t);
                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                }
            } catch (Throwable throwable1) {
                ClientUtils.getLogger().error("Something went wrong while deleting a script.", throwable1);
                MiscUtils.showErrorPopup(throwable1.getClass().getName(), throwable1.getMessage());
            }
            break;

        case 3:
            try {
                LiquidBounce.INSTANCE.getScriptManager().reloadScripts();
            } catch (Throwable throwable2) {
                ClientUtils.getLogger().error("Something went wrong while reloading all scripts.", throwable2);
                MiscUtils.showErrorPopup(throwable2.getClass().getName(), throwable2.getMessage());
            }
            break;

        case 4:
            try {
                Desktop.getDesktop().open(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder());
            } catch (Throwable throwable3) {
                ClientUtils.getLogger().error("Something went wrong while trying to open your scripts folder.", throwable3);
                MiscUtils.showErrorPopup(throwable3.getClass().getName(), throwable3.getMessage());
            }
            break;

        case 5:
            try {
                Desktop.getDesktop().browse((new URL("https://liquidbounce.net/docs/ScriptAPI/Getting%20Started")).toURI());
            } catch (Exception exception) {
                ;
            }
            break;

        case 6:
            try {
                Desktop.getDesktop().browse((new URL("https://forum.ccbluex.net/viewforum.php?id=16")).toURI());
            } catch (Exception exception1) {
                ;
            }
        }

    }

    public void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            MinecraftInstance.mc2.displayGuiScreen((GuiScreen) (new JelloMainMenu()));
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        GuiScripts.GuiList guiscripts_guilist = this.list;

        if (this.list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }

        guiscripts_guilist.getRepresented().handleMouseInput();
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J8\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0016J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0016J\r\u0010\u0014\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0006H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "gui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "selectedSlot", "", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "doubleClick", "", "var3", "getSelectedSlot", "getSelectedSlot$LiquidBounce", "getSize", "isSelected", "LiquidBounce"}
    )
    private final class GuiList extends WrappedGuiSlot {

        private int selectedSlot;

        public boolean isSelected(int id) {
            return this.selectedSlot == id;
        }

        public final int getSelectedSlot$LiquidBounce() {
            return this.selectedSlot > LiquidBounce.INSTANCE.getScriptManager().getScripts().size() ? -1 : this.selectedSlot;
        }

        public int getSize() {
            return LiquidBounce.INSTANCE.getScriptManager().getScripts().size();
        }

        public void elementClicked(int id, boolean doubleClick, int i, int j) {
            this.selectedSlot = id;
        }

        public void drawSlot(int id, int x, int y, int i, int j, int k) {
            Script script = (Script) LiquidBounce.INSTANCE.getScriptManager().getScripts().get(id);
            IFontRenderer ifontrenderer = Fonts.font40;
            String s = "§9" + script.getScriptName() + " §7v" + script.getScriptVersion();
            float f = (float) GuiScripts.this.getRepresentedScreen().getWidth() / 2.0F;
            float f1 = (float) y + 2.0F;
            Color color = Color.LIGHT_GRAY;

            Intrinsics.checkExpressionValueIsNotNull(Color.LIGHT_GRAY, "Color.LIGHT_GRAY");
            ifontrenderer.drawCenteredString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.font40;
            s = "by §c" + ArraysKt.joinToString$default(script.getScriptAuthors(), (CharSequence) ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
            f = (float) GuiScripts.this.getRepresentedScreen().getWidth() / 2.0F;
            f1 = (float) y + 15.0F;
            color = Color.LIGHT_GRAY;
            Intrinsics.checkExpressionValueIsNotNull(Color.LIGHT_GRAY, "Color.LIGHT_GRAY");
            ifontrenderer.drawCenteredString(s, f, f1, color.getRGB());
        }

        public void drawBackground() {}

        public GuiList(@NotNull IGuiScreen gui) {
            Intrinsics.checkParameterIsNotNull(gui, "gui");
            IMinecraft iminecraft = MinecraftInstance.mc;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            super(iminecraft, gui.getWidth(), gui.getHeight(), 40, gui.getHeight() - 40, 30);
        }
    }
}
