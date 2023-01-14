package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.ui.client.other.PopUI;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J \u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0018H\u0016J\u0018\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0004H\u0014J \u0010\"\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u0004H\u0014J\b\u0010$\u001a\u00020\u0018H\u0016J\u0006\u0010%\u001a\u00020\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006&"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "baseHeight", "", "baseWidth", "keys", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "Lkotlin/collections/ArrayList;", "nowDisplayKey", "getNowDisplayKey", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "setNowDisplayKey", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;)V", "popUI", "Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "getPopUI", "()Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "setPopUI", "(Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;)V", "doesGuiPauseGame", "", "drawScreen", "", "mouseX", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateAllKeys", "LiquidBounce"}
)
public final class KeyBindManager extends GuiScreen {

    private final int baseHeight = 205;
    private final int baseWidth = 500;
    private final ArrayList keys = new ArrayList();
    @Nullable
    private KeyInfo nowDisplayKey;
    @Nullable
    private PopUI popUI;

    @Nullable
    public final KeyInfo getNowDisplayKey() {
        return this.nowDisplayKey;
    }

    public final void setNowDisplayKey(@Nullable KeyInfo <set-?>) {
        this.nowDisplayKey = <set-?>;
    }

    @Nullable
    public final PopUI getPopUI() {
        return this.popUI;
    }

    public final void setPopUI(@Nullable PopUI <set-?>) {
        this.popUI = <set-?>;
    }

    public void initGui() {
        this.nowDisplayKey = (KeyInfo) null;
        this.popUI = (PopUI) null;
        this.updateAllKeys();
    }

    public final void updateAllKeys() {
        (new Thread((Runnable) (new Runnable() {
            public final void run() {
                Iterator iterator = KeyBindManager.this.keys.iterator();

                while (iterator.hasNext()) {
                    KeyInfo key = (KeyInfo) iterator.next();

                    key.update();
                }

            }
        }))).start();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int mcWidth = (int) ((float) this.width * 0.8F - (float) this.width * 0.2F);

        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        IFontRenderer ifontrenderer = Fonts.font40;
        float f = (float) this.width * 0.21F * 0.5F;
        float f1 = (float) this.height * 0.2F * 0.5F;
        Color color = Color.WHITE;

        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        ifontrenderer.drawString("KeyBindManager", f, f1, color.getRGB(), false);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glTranslatef((float) this.width * 0.2F, (float) this.height * 0.2F + (float) Fonts.font40.getFontHeight() * 2.3F, 0.0F);
        float scale = (float) mcWidth / (float) this.baseWidth;

        GL11.glScalef(scale, scale, scale);
        f = (float) this.baseWidth;
        f1 = (float) this.baseHeight;
        Color color1 = Color.WHITE;

        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        RenderUtils.drawRoundedRect(0.0F, 0.0F, f, f1, 14.0F, color1.getRGB());
        Iterator scaledMouseX = this.keys.iterator();

        while (scaledMouseX.hasNext()) {
            KeyInfo wheel = (KeyInfo) scaledMouseX.next();

            wheel.render();
        }

        KeyInfo keyinfo;

        if (this.nowDisplayKey != null) {
            keyinfo = this.nowDisplayKey;
            if (this.nowDisplayKey == null) {
                Intrinsics.throwNpe();
            }

            keyinfo.renderTab();
        }

        GL11.glPopMatrix();
        PopUI popui;

        if (Mouse.hasWheel()) {
            int wheel1 = Mouse.getDWheel();

            if (wheel1 != 0) {
                if (this.popUI != null) {
                    popui = this.popUI;
                    if (this.popUI == null) {
                        Intrinsics.throwNpe();
                    }

                    popui.onStroll(this.width, this.height, mouseX, mouseY, wheel1);
                } else if (this.nowDisplayKey != null) {
                    float scaledMouseX1 = ((float) mouseX - (float) this.width * 0.2F) / scale;
                    float scaledMouseY = ((float) mouseY - ((float) this.height * 0.2F + (float) Fonts.font40.getFontHeight() * 2.3F)) / scale;

                    keyinfo = this.nowDisplayKey;
                    if (this.nowDisplayKey == null) {
                        Intrinsics.throwNpe();
                    }

                    keyinfo.stroll(scaledMouseX1, scaledMouseY, wheel1);
                }
            }
        }

        popui = this.popUI;
        if (this.popUI != null) {
            popui.onRender(this.width, this.height);
        }

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.popUI == null) {
            float scale = ((float) this.width * 0.8F - (float) this.width * 0.2F) / (float) this.baseWidth;
            float scaledMouseX = ((float) mouseX - (float) this.width * 0.2F) / scale;
            float scaledMouseY = ((float) mouseY - ((float) this.height * 0.2F + (float) Fonts.font40.getFontHeight() * 2.3F)) / scale;

            if (this.nowDisplayKey == null) {
                if (scaledMouseX < (float) 0 || scaledMouseY < (float) 0 || scaledMouseX > (float) this.baseWidth || scaledMouseY > (float) this.baseHeight) {
                    this.mc.displayGuiScreen((GuiScreen) null);
                    return;
                }

                Iterator iterator = this.keys.iterator();

                while (iterator.hasNext()) {
                    KeyInfo key = (KeyInfo) iterator.next();

                    if (scaledMouseX > key.getPosX() && scaledMouseY > key.getPosY() && scaledMouseX < key.getPosX() + key.getWidth() && scaledMouseY < key.getPosY() + key.getHeight()) {
                        key.click(scaledMouseX, scaledMouseY);
                        break;
                    }
                }
            } else {
                KeyInfo keyinfo = this.nowDisplayKey;

                if (this.nowDisplayKey == null) {
                    Intrinsics.throwNpe();
                }

                keyinfo.click(scaledMouseX, scaledMouseY);
            }
        } else {
            PopUI popui = this.popUI;

            if (this.popUI == null) {
                Intrinsics.throwNpe();
            }

            popui.onClick(this.width, this.height, mouseX, mouseY);
        }

    }

    public void onGuiClosed() {
        LiquidBounce.INSTANCE.getFileManager().saveAllConfigs();
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (1 == keyCode) {
            if (this.popUI != null) {
                this.popUI = (PopUI) null;
            } else if (this.nowDisplayKey != null) {
                this.nowDisplayKey = (KeyInfo) null;
            } else {
                this.mc.displayGuiScreen((GuiScreen) null);
            }

        } else {
            PopUI popui = this.popUI;

            if (this.popUI != null) {
                popui.onKey(typedChar, keyCode);
            }

        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public KeyBindManager() {
        this.keys.add(new KeyInfo(12.0F, 12.0F, 27.0F, 32.0F, 41, "`"));
        this.keys.add(new KeyInfo(44.0F, 12.0F, 27.0F, 32.0F, 2, "1"));
        this.keys.add(new KeyInfo(76.0F, 12.0F, 27.0F, 32.0F, 3, "2"));
        this.keys.add(new KeyInfo(108.0F, 12.0F, 27.0F, 32.0F, 4, "3"));
        this.keys.add(new KeyInfo(140.0F, 12.0F, 27.0F, 32.0F, 5, "4"));
        this.keys.add(new KeyInfo(172.0F, 12.0F, 27.0F, 32.0F, 6, "5"));
        this.keys.add(new KeyInfo(204.0F, 12.0F, 27.0F, 32.0F, 7, "6"));
        this.keys.add(new KeyInfo(236.0F, 12.0F, 27.0F, 32.0F, 8, "7"));
        this.keys.add(new KeyInfo(268.0F, 12.0F, 27.0F, 32.0F, 9, "8"));
        this.keys.add(new KeyInfo(300.0F, 12.0F, 27.0F, 32.0F, 10, "9"));
        this.keys.add(new KeyInfo(332.0F, 12.0F, 27.0F, 32.0F, 11, "0"));
        this.keys.add(new KeyInfo(364.0F, 12.0F, 27.0F, 32.0F, 12, "-"));
        this.keys.add(new KeyInfo(396.0F, 12.0F, 27.0F, 32.0F, 13, "="));
        this.keys.add(new KeyInfo(428.0F, 12.0F, 59.0F, 32.0F, 14, "Backspace"));
        this.keys.add(new KeyInfo(12.0F, 49.0F, 43.0F, 32.0F, 15, "Tab"));
        this.keys.add(new KeyInfo(60.0F, 49.0F, 27.0F, 32.0F, 16, "Q"));
        this.keys.add(new KeyInfo(92.0F, 49.0F, 27.0F, 32.0F, 17, "W"));
        this.keys.add(new KeyInfo(124.0F, 49.0F, 27.0F, 32.0F, 18, "E"));
        this.keys.add(new KeyInfo(156.0F, 49.0F, 27.0F, 32.0F, 19, "R"));
        this.keys.add(new KeyInfo(188.0F, 49.0F, 27.0F, 32.0F, 20, "T"));
        this.keys.add(new KeyInfo(220.0F, 49.0F, 27.0F, 32.0F, 21, "Y"));
        this.keys.add(new KeyInfo(252.0F, 49.0F, 27.0F, 32.0F, 22, "U"));
        this.keys.add(new KeyInfo(284.0F, 49.0F, 27.0F, 32.0F, 23, "I"));
        this.keys.add(new KeyInfo(316.0F, 49.0F, 27.0F, 32.0F, 24, "O"));
        this.keys.add(new KeyInfo(348.0F, 49.0F, 27.0F, 32.0F, 25, "P"));
        this.keys.add(new KeyInfo(380.0F, 49.0F, 27.0F, 32.0F, 26, "["));
        this.keys.add(new KeyInfo(412.0F, 49.0F, 27.0F, 32.0F, 27, "]"));
        this.keys.add(new KeyInfo(444.0F, 49.0F, 43.0F, 32.0F, 43, "\\"));
        this.keys.add(new KeyInfo(12.0F, 86.0F, 59.0F, 32.0F, 15, "Caps Lock"));
        this.keys.add(new KeyInfo(76.0F, 86.0F, 27.0F, 32.0F, 30, "A"));
        this.keys.add(new KeyInfo(108.0F, 86.0F, 27.0F, 32.0F, 31, "S"));
        this.keys.add(new KeyInfo(140.0F, 86.0F, 27.0F, 32.0F, 32, "D"));
        this.keys.add(new KeyInfo(172.0F, 86.0F, 27.0F, 32.0F, 33, "F"));
        this.keys.add(new KeyInfo(204.0F, 86.0F, 27.0F, 32.0F, 34, "G"));
        this.keys.add(new KeyInfo(236.0F, 86.0F, 27.0F, 32.0F, 35, "H"));
        this.keys.add(new KeyInfo(268.0F, 86.0F, 27.0F, 32.0F, 36, "J"));
        this.keys.add(new KeyInfo(300.0F, 86.0F, 27.0F, 32.0F, 37, "K"));
        this.keys.add(new KeyInfo(332.0F, 86.0F, 27.0F, 32.0F, 38, "L"));
        this.keys.add(new KeyInfo(364.0F, 86.0F, 27.0F, 32.0F, 39, ";"));
        this.keys.add(new KeyInfo(396.0F, 86.0F, 27.0F, 32.0F, 40, "\'"));
        this.keys.add(new KeyInfo(428.0F, 86.0F, 59.0F, 32.0F, 28, "Enter"));
        this.keys.add(new KeyInfo(12.0F, 123.0F, 75.0F, 32.0F, 42, "Shift", "LShift"));
        this.keys.add(new KeyInfo(92.0F, 123.0F, 27.0F, 32.0F, 44, "Z"));
        this.keys.add(new KeyInfo(124.0F, 123.0F, 27.0F, 32.0F, 45, "X"));
        this.keys.add(new KeyInfo(156.0F, 123.0F, 27.0F, 32.0F, 46, "C"));
        this.keys.add(new KeyInfo(188.0F, 123.0F, 27.0F, 32.0F, 47, "V"));
        this.keys.add(new KeyInfo(220.0F, 123.0F, 27.0F, 32.0F, 48, "B"));
        this.keys.add(new KeyInfo(252.0F, 123.0F, 27.0F, 32.0F, 49, "N"));
        this.keys.add(new KeyInfo(284.0F, 123.0F, 27.0F, 32.0F, 50, "M"));
        this.keys.add(new KeyInfo(316.0F, 123.0F, 27.0F, 32.0F, 51, ","));
        this.keys.add(new KeyInfo(348.0F, 123.0F, 27.0F, 32.0F, 52, "."));
        this.keys.add(new KeyInfo(380.0F, 123.0F, 27.0F, 32.0F, 53, "/"));
        this.keys.add(new KeyInfo(412.0F, 123.0F, 75.0F, 32.0F, 54, "Shift", "RShift"));
        this.keys.add(new KeyInfo(12.0F, 160.0F, 43.0F, 32.0F, 29, "Ctrl", "LCtrl"));
        this.keys.add(new KeyInfo(60.0F, 160.0F, 43.0F, 32.0F, 56, "Alt", "LAlt"));
        this.keys.add(new KeyInfo(108.0F, 160.0F, 251.0F, 32.0F, 57, " ", "Space"));
        this.keys.add(new KeyInfo(364.0F, 160.0F, 43.0F, 32.0F, 184, "Alt", "RAlt"));
        this.keys.add(new KeyInfo(412.0F, 160.0F, 27.0F, 32.0F, 199, "Ø", "Home"));
        this.keys.add(new KeyInfo(444.0F, 160.0F, 43.0F, 32.0F, 157, "Ctrl", "RCtrl"));
    }
}
