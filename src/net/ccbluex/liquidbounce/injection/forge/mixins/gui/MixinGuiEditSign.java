package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ GuiEditSign.class})
public class MixinGuiEditSign extends GuiScreen {

    @Shadow
    private int editLine;
    @Final
    @Shadow
    private TileEntitySign tileSign;
    @Shadow
    private GuiButton doneBtn;
    private boolean enabled;
    private GuiButton toggleButton;
    private GuiTextField signCommand1;
    private GuiTextField signCommand2;
    private GuiTextField signCommand3;
    private GuiTextField signCommand4;

    @Inject(
        method = { "initGui"},
        at = {             @At("RETURN")}
    )
    private void initGui(CallbackInfo callbackInfo) {
        this.buttonList.add(this.toggleButton = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 145, this.enabled ? "Disable Formatting codes" : "Enable Formatting codes"));
        this.signCommand1 = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height - 15, 200, 10);
        this.signCommand2 = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100, this.height - 30, 200, 10);
        this.signCommand3 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, this.height - 45, 200, 10);
        this.signCommand4 = new GuiTextField(3, this.fontRenderer, this.width / 2 - 100, this.height - 60, 200, 10);
        this.signCommand1.setText("");
        this.signCommand2.setText("");
        this.signCommand3.setText("");
        this.signCommand4.setText("");
    }

    @Inject(
        method = { "actionPerformed"},
        at = {             @At("HEAD")}
    )
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        switch (button.id) {
        case 0:
            if (!this.signCommand1.getText().isEmpty()) {
                this.tileSign.signText[0].setStyle((new Style()).setClickEvent(new ClickEvent(Action.RUN_COMMAND, this.signCommand1.getText())));
            }

            if (!this.signCommand2.getText().isEmpty()) {
                this.tileSign.signText[1].setStyle((new Style()).setClickEvent(new ClickEvent(Action.RUN_COMMAND, this.signCommand2.getText())));
            }

            if (!this.signCommand3.getText().isEmpty()) {
                this.tileSign.signText[2].setStyle((new Style()).setClickEvent(new ClickEvent(Action.RUN_COMMAND, this.signCommand3.getText())));
            }

            if (!this.signCommand4.getText().isEmpty()) {
                this.tileSign.signText[3].setStyle((new Style()).setClickEvent(new ClickEvent(Action.RUN_COMMAND, this.signCommand4.getText())));
            }
            break;

        case 1:
            this.enabled = !this.enabled;
            this.toggleButton.displayString = this.enabled ? "Disable Formatting codes" : "Enable Formatting codes";
        }

    }

    @Inject(
        method = { "drawScreen"},
        at = {             @At("RETURN")}
    )
    private void drawFields(CallbackInfo callbackInfo) {
        this.fontRenderer.drawString("§c§lCommands §7(§f§l1.8§7)", this.width / 2 - 100, this.height - 75, Color.WHITE.getRGB());
        this.signCommand1.drawTextBox();
        this.signCommand2.drawTextBox();
        this.signCommand3.drawTextBox();
        this.signCommand4.drawTextBox();
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.signCommand1.mouseClicked(mouseX, mouseY, mouseButton);
        this.signCommand2.mouseClicked(mouseX, mouseY, mouseButton);
        this.signCommand3.mouseClicked(mouseX, mouseY, mouseButton);
        this.signCommand4.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Overwrite
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.signCommand1.textboxKeyTyped(typedChar, keyCode);
        this.signCommand2.textboxKeyTyped(typedChar, keyCode);
        this.signCommand3.textboxKeyTyped(typedChar, keyCode);
        this.signCommand4.textboxKeyTyped(typedChar, keyCode);
        if (!this.signCommand1.isFocused() && !this.signCommand2.isFocused() && !this.signCommand3.isFocused() && !this.signCommand4.isFocused()) {
            if (keyCode == 200) {
                this.editLine = this.editLine - 1 & 3;
            }

            if (keyCode == 208 || keyCode == 28 || keyCode == 156) {
                this.editLine = this.editLine + 1 & 3;
            }

            String s = this.tileSign.signText[this.editLine].getUnformattedText();

            if (keyCode == 14 && s.length() > 0) {
                s = s.substring(0, s.length() - 1);
            }

            if ((ChatAllowedCharacters.isAllowedCharacter(typedChar) || this.enabled && typedChar == 167) && this.fontRenderer.getStringWidth(s + typedChar) <= 90) {
                s = s + typedChar;
            }

            this.tileSign.signText[this.editLine] = new TextComponentString(s);
            if (keyCode == 1) {
                this.actionPerformed(this.doneBtn);
            }

        }
    }
}
