package me.Skid.ui.Client;

import java.awt.Color;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\u0018\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0002J0\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0013H\u0002J \u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0013H\u0016J\b\u0010\u001b\u001a\u00020\tH\u0016J \u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u0005H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "},
    d2 = { "Lme/Skid/ui/Client/QINGRAN;", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/minecraft/client/gui/GuiYesNoCallback;", "()V", "anim_x", "", "singleColor", "Ljava/awt/Color;", "actionPerformed", "", "p_actionPerformed_1_", "Lnet/minecraft/client/gui/GuiButton;", "animSwitch", "mouseX", "mouseY", "drawButton", "string", "", "x", "", "y", "width", "height", "drawScreen", "p_drawScreen_1_", "p_drawScreen_2_", "p_drawScreen_3_", "initGui", "mouseClicked", "p_mouseClicked_1_", "p_mouseClicked_2_", "p_mouseClicked_3_", "LiquidBounce"}
)
public final class QINGRAN extends GuiScreen implements GuiYesNoCallback {

    private int anim_x;
    private Color singleColor = new Color(200, 200, 200);

    public void initGui() {
        LiquidBounce.INSTANCE.setMainmenu((GuiScreen) this);
    }

    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        this.drawBackground(0);
        Gui.drawRect(0, 0, 2, 2, (new Color(0, 0, 0, 0)).getRGB());
        RenderUtils.drawImage(new ResourceLocation("liquidbounce/Jello/Switch.png"), this.width - 20 - this.anim_x, this.height - 80, 80, 30);
        BlurBuffer.blurRoundArea((float) this.width / 2.0F - 80.0F, (float) this.height / 2.0F - (float) 70, 80.0F, 170.0F, 10.0F);
        RenderUtils.drawBorderedRect((float) this.width / 2.0F - (float) 80, (float) this.height / 2.0F - (float) 70, (float) this.width / 2.0F + (float) 80, (float) this.height / 2.0F + (float) 100 + (float) 5 - (float) 30, 1.0F, this.singleColor.getRGB(), (new Color(0, 0, 0, 120)).getRGB());
        Fonts.regular40.drawCenteredString("TGSense", (float) this.width / 2.0F, (float) this.height / 2.0F - (float) 70 + (float) (Fonts.regular35.getFontHeight() / 2) + 2.0F, this.singleColor.getRGB(), true);
        this.drawButton("Single Player", (float) this.width / 2.0F - (float) 75, (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight(), 150.0F, 20.0F);
        this.drawButton("Multi Player", (float) this.width / 2.0F - (float) 75, (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + 25.0F, 150.0F, 20.0F);
        this.drawButton("Settings", (float) this.width / 2.0F - (float) 75, (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + 50.0F, 150.0F, 20.0F);
        this.drawButton("Alt Manager", (float) this.width / 2.0F - (float) 75, (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + 75.0F, 150.0F, 20.0F);
        this.drawButton("Exit", (float) this.width / 2.0F - (float) 75, (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + 100.0F, 150.0F, 20.0F);
        this.animSwitch(p_drawScreen_1_, p_drawScreen_2_);
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    protected void actionPerformed(@Nullable GuiButton p_actionPerformed_1_) {}

    private final void drawButton(String string, float x, float y, float width, float height) {
        BlurBuffer.blurRoundArea(x, y, width, height, 10.0F);
        RenderUtils.drawBorderedRect(x, y, x + width, y + height, 1.0F, this.singleColor.getRGB(), (new Color(0, 0, 0, 120)).getRGB());
        Fonts.regular40.drawCenteredString(string, x + width / 2.0F, y + (float) Fonts.regular35.getFontHeight() / 2.0F + 2.0F, this.singleColor.getRGB(), true);
    }

    private final void animSwitch(int mouseX, int mouseY) {
        boolean canAnim = mouseX >= this.width - 20 - this.anim_x && mouseX <= this.width && mouseY >= this.height - 80 && mouseY <= this.height - 50;
        int i;

        if (canAnim && this.anim_x <= 61) {
            i = this.anim_x++;
        }

        if (!canAnim && this.anim_x >= 0) {
            i = this.anim_x;
            this.anim_x += -1;
        }

    }

    protected void mouseClicked(int p_mouseClicked_1_, int p_mouseClicked_2_, int p_mouseClicked_3_) {
        boolean isSingle = (float) p_mouseClicked_1_ > (float) this.width / 2.0F - (float) 75 && (float) p_mouseClicked_2_ > (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() && (float) p_mouseClicked_1_ < (float) this.width / 2.0F + (float) 75 && (float) p_mouseClicked_2_ < (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 20;
        boolean isMulti = (float) p_mouseClicked_1_ > (float) this.width / 2.0F - (float) 75 && (float) p_mouseClicked_2_ > (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 25 && (float) p_mouseClicked_1_ < (float) this.width / 2.0F + (float) 75 && (float) p_mouseClicked_2_ < (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 20 + (float) 25;
        boolean isSettings = (float) p_mouseClicked_1_ > (float) this.width / 2.0F - (float) 75 && (float) p_mouseClicked_2_ > (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 50 && (float) p_mouseClicked_1_ < (float) this.width / 2.0F + (float) 75 && (float) p_mouseClicked_2_ < (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 20 + (float) 50;
        boolean isAlt = (float) p_mouseClicked_1_ > (float) this.width / 2.0F - (float) 75 && (float) p_mouseClicked_2_ > (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 75 && (float) p_mouseClicked_1_ < (float) this.width / 2.0F + (float) 75 && (float) p_mouseClicked_2_ < (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 20 + (float) 75;
        boolean isExit = (float) p_mouseClicked_1_ > (float) this.width / 2.0F - (float) 75 && (float) p_mouseClicked_2_ > (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 100 && (float) p_mouseClicked_1_ < (float) this.width / 2.0F + (float) 75 && (float) p_mouseClicked_2_ < (float) this.height / 2.0F - (float) 60 + (float) Fonts.regular40.getFontHeight() + (float) 20 + (float) 100;
        boolean isSwitch = p_mouseClicked_1_ >= this.width - 20 - this.anim_x && p_mouseClicked_1_ <= this.width && p_mouseClicked_2_ >= this.height - 80 && p_mouseClicked_2_ <= this.height - 50;

        if (p_mouseClicked_3_ == 0) {
            if (isSingle) {
                this.mc.displayGuiScreen((GuiScreen) (new GuiWorldSelection((GuiScreen) this)));
            } else if (isMulti) {
                this.mc.displayGuiScreen((GuiScreen) (new GuiMultiplayer((GuiScreen) this)));
            } else if (isSettings) {
                this.mc.displayGuiScreen((GuiScreen) (new GuiOptions((GuiScreen) this, this.mc.gameSettings)));
            } else if (isAlt) {
                MinecraftInstance.mc.displayGuiScreen(ClassProviderImpl.INSTANCE.wrapGuiScreen((WrappedGuiScreen) (new GuiAltManager())));
            } else if (isSwitch) {
                this.mc.displayGuiScreen((GuiScreen) (new GuiMainMenu()));
            } else if (isExit) {
                this.mc.shutdown();
            }
        }

        super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_);
    }
}
