package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({ GuiChat.class})
public abstract class MixinGuiChat extends MixinGuiScreen {

    @Shadow
    protected GuiTextField inputField;
    private float yPosOfInputField;
    private float fade = 0.0F;

    @Shadow
    public abstract void setCompletions(String... astring);

    @Inject(
        method = { "initGui"},
        at = {             @At("RETURN")}
    )
    private void init(CallbackInfo callbackInfo) {
        this.inputField.y = this.height + 1;
        this.yPosOfInputField = (float) this.inputField.y;
    }

    @Inject(
        method = { "keyTyped"},
        at = {             @At("RETURN")}
    )
    private void updateLength(CallbackInfo callbackInfo) {
        if (this.inputField.getText().startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix()))) {
            LiquidBounce.commandManager.autoComplete(this.inputField.getText());
            if (!this.inputField.getText().startsWith(LiquidBounce.commandManager.getPrefix() + "lc")) {
                this.inputField.setMaxStringLength(10000);
            } else {
                this.inputField.setMaxStringLength(100);
            }

        }
    }

    @Inject(
        method = { "updateScreen"},
        at = {             @At("HEAD")}
    )
    private void updateScreen(CallbackInfo callbackInfo) {
        int delta = RenderUtils.deltaTime;

        if (this.fade < 14.0F) {
            this.fade += 0.4F * (float) delta;
        }

        if (this.fade > 14.0F) {
            this.fade = 14.0F;
        }

        if (this.yPosOfInputField > (float) (this.height - 12)) {
            this.yPosOfInputField -= 0.4F * (float) delta;
        }

        if (this.yPosOfInputField < (float) (this.height - 12)) {
            this.yPosOfInputField = (float) (this.height - 12);
        }

        this.inputField.y = (int) this.yPosOfInputField;
    }

    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(2, this.height - (int) this.fade, this.width - 2, this.height, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        if (LiquidBounce.commandManager.getLatestAutoComplete().length > 0 && !this.inputField.getText().isEmpty() && this.inputField.getText().startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix()))) {
            String[] ichatcomponent = LiquidBounce.commandManager.getLatestAutoComplete();
            String[] textArray = this.inputField.getText().split(" ");
            String trimmedString = ichatcomponent[0].replaceFirst("(?i)" + textArray[textArray.length - 1], "");

            this.mc.fontRenderer.drawStringWithShadow(trimmedString, (float) (this.inputField.x + this.mc.fontRenderer.getStringWidth(this.inputField.getText())), (float) this.inputField.y, (new Color(165, 165, 165)).getRGB());
        }

        ITextComponent ichatcomponent1 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

        if (ichatcomponent1 != null) {
            this.handleComponentHover(ichatcomponent1, mouseX, mouseY);
        }

    }
}
