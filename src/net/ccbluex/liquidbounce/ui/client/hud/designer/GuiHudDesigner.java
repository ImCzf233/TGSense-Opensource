package net.ccbluex.liquidbounce.ui.client.hud.designer;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.client.hud.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J \u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0010H\u0016J\b\u0010\u001d\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u001e"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "()V", "buttonAction", "", "editorPanel", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "selectedElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getSelectedElement", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "setSelectedElement", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;)V", "drawScreen", "", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", "LiquidBounce"}
)
public final class GuiHudDesigner extends WrappedGuiScreen {

    private EditorPanel editorPanel = new EditorPanel(this, 2, 2);
    @Nullable
    private Element selectedElement;
    private boolean buttonAction;

    @Nullable
    public final Element getSelectedElement() {
        return this.selectedElement;
    }

    public final void setSelectedElement(@Nullable Element <set-?>) {
        this.selectedElement = <set-?>;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.editorPanel = new EditorPanel(this, this.getRepresentedScreen().getWidth() / 2, this.getRepresentedScreen().getHeight() / 2);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        LiquidBounce.INSTANCE.getHud().render(true);
        LiquidBounce.INSTANCE.getHud().handleMouseMove(mouseX, mouseY);
        if (!CollectionsKt.contains((Iterable) LiquidBounce.INSTANCE.getHud().getElements(), this.selectedElement)) {
            this.selectedElement = (Element) null;
        }

        int wheel = Mouse.getDWheel();

        this.editorPanel.drawPanel(mouseX, mouseY, wheel);
        if (wheel != 0) {
            Iterator iterator = LiquidBounce.INSTANCE.getHud().getElements().iterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                if (element.isInBorder((double) ((float) mouseX / element.getScale()) - element.getRenderX(), (double) ((float) mouseY / element.getScale()) - element.getRenderY())) {
                    element.setScale(element.getScale() + (wheel > 0 ? 0.05F : -0.05F));
                    break;
                }
            }
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.getRepresentedScreen().superMouseClicked(mouseX, mouseY, mouseButton);
        if (this.buttonAction) {
            this.buttonAction = false;
        } else {
            label33: {
                LiquidBounce.INSTANCE.getHud().handleMouseClick(mouseX, mouseY, mouseButton);
                if (mouseX >= this.editorPanel.getX() && mouseX <= this.editorPanel.getX() + this.editorPanel.getWidth() && mouseY >= this.editorPanel.getY()) {
                    int i = this.editorPanel.getY();
                    int element = this.editorPanel.getRealHeight();
                    short short0 = 200;
                    int j = i;
                    boolean flag = false;
                    int k = Math.min(element, short0);

                    if (mouseY <= j + k) {
                        break label33;
                    }
                }

                this.selectedElement = (Element) null;
                this.editorPanel.setCreate(false);
            }

            if (mouseButton == 0) {
                Iterator iterator = LiquidBounce.INSTANCE.getHud().getElements().iterator();

                while (iterator.hasNext()) {
                    Element element1 = (Element) iterator.next();

                    if (element1.isInBorder((double) ((float) mouseX / element1.getScale()) - element1.getRenderX(), (double) ((float) mouseY / element1.getScale()) - element1.getRenderY())) {
                        this.selectedElement = element1;
                        break;
                    }
                }
            }

        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.getRepresentedScreen().superMouseReleased(mouseX, mouseY, state);
        LiquidBounce.INSTANCE.getHud().handleMouseReleased();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
        super.onGuiClosed();
    }

    public void keyTyped(char typedChar, int keyCode) {
        switch (keyCode) {
        case 1:
            this.selectedElement = (Element) null;
            this.editorPanel.setCreate(false);
            break;

        case 211:
            if (211 == keyCode && this.selectedElement != null) {
                HUD hud = LiquidBounce.INSTANCE.getHud();
                Element element = this.selectedElement;

                if (this.selectedElement == null) {
                    Intrinsics.throwNpe();
                }

                hud.removeElement(element);
            }
            break;

        default:
            LiquidBounce.INSTANCE.getHud().handleKey(typedChar, keyCode);
        }

        super.keyTyped(typedChar, keyCode);
    }
}
