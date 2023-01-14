package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ GuiNewChat.class})
public abstract class MixinGuiNewChat {

    @Inject(
        method = { "drawChat"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void drawChat(int p_drawChat_1_, CallbackInfo callbackInfo) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
    }

    @Inject(
        method = { "getChatComponent"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getChatComponent(int p_getChatComponent_1_, int p_getChatComponent_2_, CallbackInfoReturnable callbackInfo) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        if (hud.getState() && ((Boolean) hud.getFontChatValue().get()).booleanValue()) {
            callbackInfo.setReturnValue((Object) null);
        }

    }
}
