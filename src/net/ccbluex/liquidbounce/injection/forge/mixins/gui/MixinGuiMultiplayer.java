package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.injection.backend.GuiScreenImplKt;
import net.ccbluex.liquidbounce.ui.client.GuiAntiForge;
import net.ccbluex.liquidbounce.ui.client.tools.GuiTools;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ GuiMultiplayer.class})
public abstract class MixinGuiMultiplayer extends MixinGuiScreen {

    private GuiButton bungeeCordSpoofButton;

    @Inject(
        method = { "initGui"},
        at = {             @At("RETURN")}
    )
    private void initGui(CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(997, 5, 8, 98, 20, "AntiForge"));
        this.buttonList.add(this.bungeeCordSpoofButton = new GuiButton(998, 108, 8, 98, 20, "BungeeCord Spoof: " + (BungeeCordSpoof.enabled ? "On" : "Off")));
        this.buttonList.add(new GuiButton(999, this.width - 104, 8, 98, 20, "Tools"));
    }

    @Inject(
        method = { "actionPerformed"},
        at = {             @At("HEAD")}
    )
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        switch (button.id) {
        case 997:
            this.mc.displayGuiScreen(GuiScreenImplKt.unwrap(LiquidBounce.wrapper.getClassProvider().wrapGuiScreen(new GuiAntiForge(GuiScreenImplKt.wrap((GuiScreen) this)))));
            break;

        case 998:
            BungeeCordSpoof.enabled = !BungeeCordSpoof.enabled;
            this.bungeeCordSpoofButton.displayString = "BungeeCord Spoof: " + (BungeeCordSpoof.enabled ? "On" : "Off");
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
            break;

        case 999:
            this.mc.displayGuiScreen(GuiScreenImplKt.unwrap(LiquidBounce.wrapper.getClassProvider().wrapGuiScreen(new GuiTools(GuiScreenImplKt.wrap((GuiScreen) this)))));
        }

    }
}
