package net.ccbluex.liquidbounce.ui.client;

import com.google.gson.Gson;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\tH\u0016J\u0018\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\b\u0010\u0017\u001a\u00020\tH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiServerStatus;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "status", "Ljava/util/HashMap;", "", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "loadInformation", "LiquidBounce"}
)
public final class GuiServerStatus extends WrappedGuiScreen {

    private final HashMap status;
    private final IGuiScreen prevGui;

    public void initGui() {
        this.getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 145, "Back"));
        ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
            public final void invoke() {
                GuiServerStatus.this.loadInformation();
            }
        }), 31, (Object) null);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        int i = this.getRepresentedScreen().getHeight() / 4 + 40;

        RenderUtils.drawRect((float) this.getRepresentedScreen().getWidth() / 2.0F - (float) 115, (float) i - 5.0F, (float) this.getRepresentedScreen().getWidth() / 2.0F + (float) 115, (float) this.getRepresentedScreen().getHeight() / 4.0F + (float) 43 + (float) (this.status.keySet().isEmpty() ? 10 : this.status.keySet().size() * Fonts.font40.getFontHeight()), Integer.MIN_VALUE);
        IFontRenderer ifontrenderer;
        float f;
        float f1;
        Color color;

        if (this.status.isEmpty()) {
            ifontrenderer = Fonts.font40;
            f = (float) this.getRepresentedScreen().getWidth() / 2.0F;
            f1 = (float) this.getRepresentedScreen().getHeight() / 4.0F + (float) 40;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawCenteredString("Loading...", f, f1, color.getRGB());
        } else {
            for (Iterator iterator = this.status.keySet().iterator(); iterator.hasNext(); i += Fonts.font40.getFontHeight()) {
                String server = (String) iterator.next();
                String color = (String) this.status.get(server);

                ifontrenderer = Fonts.font40;
                String s = server + ": " + (StringsKt.equals(color, "red", true) ? "§c" : (StringsKt.equals(color, "yellow", true) ? "§e" : "§a")) + (StringsKt.equals(color, "red", true) ? "Offline" : (StringsKt.equals(color, "yellow", true) ? "Slow" : "Online"));

                f = (float) this.getRepresentedScreen().getWidth() / 2.0F;
                f1 = (float) i;
                color = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                ifontrenderer.drawCenteredString(s, f, f1, color.getRGB());
            }
        }

        Fonts.fontBold180.drawCenteredString("Server Status", (float) this.getRepresentedScreen().getWidth() / 2.0F, (float) this.getRepresentedScreen().getHeight() / 8.0F + 5.0F, 4673984, true);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private final void loadInformation() {
        this.status.clear();

        try {
            Object object = (new Gson()).fromJson(HttpUtils.get("https://status.mojang.com/check"), List.class);

            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<kotlin.collections.Map<kotlin.String, kotlin.String>>");
            }

            List e = (List) object;
            Iterator iterator = e.iterator();

            while (iterator.hasNext()) {
                Map linkedTreeMap = (Map) iterator.next();
                boolean flag = false;
                Iterator iterator1 = linkedTreeMap.entrySet().iterator();

                while (iterator1.hasNext()) {
                    Entry entry = (Entry) iterator1.next();

                    ((Map) this.status).put(entry.getKey(), entry.getValue());
                }
            }
        } catch (IOException ioexception) {
            ((Map) this.status).put("status.mojang.com/check", "red");
        }

    }

    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getId() == 1) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui);
        }

    }

    public void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            MinecraftInstance.mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    public GuiServerStatus(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        super();
        this.prevGui = prevGui;
        this.status = new HashMap();
    }
}
