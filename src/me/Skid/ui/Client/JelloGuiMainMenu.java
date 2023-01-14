package me.Skid.ui.Client;

import java.awt.Color;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.utils.DrawUtils;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J \u0010\u0013\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\nH\u0016J\b\u0010\u0015\u001a\u00020\rH\u0016J\u0018\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0005H\u0014J \u0010\u001a\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"},
    d2 = { "Lme/Skid/ui/Client/JelloGuiMainMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/minecraft/client/gui/GuiYesNoCallback;", "()V", "Anim_AltManager", "", "Anim_Multi", "Anim_Setting", "Anim_Single", "animatedMouseX", "", "animatedMouseY", "Anim", "", "mouseX", "mouseY", "actionPerformed", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "partialTicks", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "LiquidBounce"}
)
public final class JelloGuiMainMenu extends GuiScreen implements GuiYesNoCallback {

    private int Anim_Single;
    private int Anim_Multi;
    private int Anim_Setting;
    private int Anim_AltManager;
    private float animatedMouseX;
    private float animatedMouseY;

    public void initGui() {
        this.animatedMouseX = 0.0F;
        this.animatedMouseY = 0.0F;
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        Minecraft minecraft = this.mc;

        Intrinsics.checkExpressionValueIsNotNull(this.mc, "mc");
        minecraft.getTextureManager().bindTexture(new ResourceLocation("langya/jellomenu.png"));
        DrawUtils.drawModalRectWithCustomSizedTexture(-this.animatedMouseX / 1.43F, -this.animatedMouseY / 10.15F, 0.0F, 0.0F, (double) this.width * 1.7D, (double) this.height * 1.1D, (double) this.width * 1.7D, (double) this.height * 1.1D);
        int defaultHeight = this.height / 2 + 6;

        RenderUtils.drawImage3(new ResourceLocation("langya/mainmenu/Singleplayer.png"), this.width / 2 - 104, defaultHeight - this.Anim_Single, 48, 48);
        RenderUtils.drawImage3(new ResourceLocation("assets/minecraft/liquidbounce+/cape/Multiplayer.png"), this.width / 2 - 44, defaultHeight - this.Anim_Multi, 48, 48);
        RenderUtils.drawImage3(new ResourceLocation("assets/minecraft/langya/mainmenu/Settings.png"), this.width / 2 - 48 + 56, defaultHeight - this.Anim_Setting, 48, 48);
        RenderUtils.drawImage3(new ResourceLocation("liquidbounce+/cape/AltManager.png"), this.width / 2 - 48 + 112, defaultHeight - this.Anim_AltManager, 48, 48);
        this.Anim(mouseX, mouseY);
        RenderUtils.drawRect(0.0F, 0.0F, 20.0F, 20.0F, new Color(0, 0, 0, 0));
        this.animatedMouseX = (float) mouseX;
        this.animatedMouseY = (float) mouseY;
    }

    protected void actionPerformed(@NotNull GuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
    }

    protected void keyTyped(char typedChar, int keyCode) {}

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int defaultHeight = this.height / 2 + 6;
        boolean isSinglePlayer = mouseX > this.width / 2 - 104 && mouseY > defaultHeight + this.Anim_Single && mouseY < defaultHeight + this.Anim_Single + 48 && mouseX < this.width / 2 - 104 + 48;
        boolean isMultiPlayer = mouseX > this.width / 2 - 48 && mouseY > defaultHeight + this.Anim_Multi && mouseY < defaultHeight + this.Anim_Multi + 48 && mouseX < this.width / 2 - 48 + 48;
        boolean isSettings = mouseX > this.width / 2 - 48 + 56 && mouseY > defaultHeight + this.Anim_Setting && mouseY < defaultHeight + this.Anim_Setting + 48 && mouseX < this.width / 2 - 48 + 56 + 48;
        boolean isAltManager = mouseX > this.width / 2 - 48 + 112 && mouseY > defaultHeight + this.Anim_AltManager && mouseY < defaultHeight + this.Anim_AltManager + 48 && mouseX < this.width / 2 - 48 + 112 + 48;

        if (mouseButton == 0) {
            if (isSinglePlayer) {
                this.mc.displayGuiScreen((GuiScreen) (new GuiWorldSelection((GuiScreen) this)));
            }

            if (isMultiPlayer) {
                this.mc.displayGuiScreen((GuiScreen) (new GuiMultiplayer((GuiScreen) this)));
            }

            if (isSettings) {
                this.mc.displayGuiScreen((GuiScreen) (new GuiOptions((GuiScreen) this, this.mc.gameSettings)));
            }

            if (isAltManager) {
                MinecraftInstance.mc.displayGuiScreen(ClassProviderImpl.INSTANCE.wrapGuiScreen((WrappedGuiScreen) (new GuiAltManager())));
            }
        }

    }

    public final void Anim(int mouseX, int mouseY) {
        int defaultHeight = this.height / 2 + 6;
        boolean isSinglePlayer = mouseX > this.width / 2 - 104 && mouseY > defaultHeight + this.Anim_Single && mouseY < defaultHeight + this.Anim_Single + 48 && mouseX < this.width / 2 - 104 + 48;
        boolean isMultiPlayer = mouseX > this.width / 2 - 48 && mouseY > defaultHeight + this.Anim_Multi && mouseY < defaultHeight + this.Anim_Multi + 48 && mouseX < this.width / 2 - 48 + 48;
        boolean isSettings = mouseX > this.width / 2 - 48 + 56 && mouseY > defaultHeight + this.Anim_Setting && mouseY < defaultHeight + this.Anim_Setting + 48 && mouseX < this.width / 2 - 48 + 56 + 48;
        boolean isAltManager = mouseX > this.width / 2 - 48 + 112 && mouseY > defaultHeight + this.Anim_AltManager && mouseY < defaultHeight + this.Anim_AltManager + 48 && mouseX < this.width / 2 - 48 + 112 + 48;
        int i;

        if (isSinglePlayer) {
            if (this.Anim_Single > -6) {
                i = this.Anim_Single;
                this.Anim_Single += -1;
            }

            if (this.Anim_Single < 0) {
                Fonts.fontSFUI40.drawCenteredString("SinglePlayer", (float) (this.width / 2 - 104 + 24), (float) (defaultHeight - this.Anim_Single + 48), (new Color(255, 255, 255, 120)).getRGB(), false);
            }
        } else if (!isSinglePlayer && this.Anim_Single < 1) {
            i = this.Anim_Single++;
        }

        if (isMultiPlayer) {
            if (this.Anim_Multi > -6) {
                i = this.Anim_Multi;
                this.Anim_Multi += -1;
            }

            if (this.Anim_Multi < 0) {
                Fonts.fontSFUI40.drawCenteredString("MultiPlayer", (float) (this.width / 2 - 48 + 24), (float) (defaultHeight - this.Anim_Multi + 48), (new Color(255, 255, 255, 120)).getRGB(), false);
            }
        } else if (!isMultiPlayer && this.Anim_Multi < 1) {
            i = this.Anim_Multi++;
        }

        if (isSettings) {
            if (this.Anim_Setting > -6) {
                i = this.Anim_Setting;
                this.Anim_Setting += -1;
            }

            if (this.Anim_Setting < 0) {
                Fonts.fontSFUI40.drawCenteredString("Settings", (float) (this.width / 2 - 48 + 56 + 24), (float) (defaultHeight - this.Anim_Setting + 48), (new Color(255, 255, 255, 120)).getRGB(), false);
            }
        } else if (!isSettings && this.Anim_Setting < 1) {
            i = this.Anim_Setting++;
        }

        if (isAltManager) {
            if (this.Anim_AltManager > -6) {
                i = this.Anim_AltManager;
                this.Anim_AltManager += -1;
            }

            if (this.Anim_AltManager < 0) {
                Fonts.fontSFUI40.drawCenteredString("AltManager", (float) (this.width / 2 - 48 + 112 + 24), (float) (defaultHeight - this.Anim_AltManager + 48), (new Color(255, 255, 255, 120)).getRGB(), false);
            }
        } else if (!isAltManager && this.Anim_AltManager < 1) {
            i = this.Anim_AltManager++;
        }

    }
}
