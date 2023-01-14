package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ RenderEntityItem.class})
public class MixinRenderEntityItem {

    @Inject(
        method = { "doRender"},
        at = {             @At("HEAD")}
    )
    private void injectChamsPre(CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);

        if (chams.getState() && ((Boolean) chams.getItemsValue().get()).booleanValue()) {
            GL11.glEnable('è€?');
            GL11.glPolygonOffset(1.0F, -1000000.0F);
        }

    }

    @Inject(
        method = { "doRender"},
        at = {             @At("RETURN")}
    )
    private void injectChamsPost(CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);

        if (chams.getState() && ((Boolean) chams.getItemsValue().get()).booleanValue()) {
            GL11.glPolygonOffset(1.0F, 1000000.0F);
            GL11.glDisable('è€?');
        }

    }
}
