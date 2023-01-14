package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer;
import net.ccbluex.liquidbounce.injection.implementations.IMixinGuiContainer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ GuiContainer.class})
public abstract class MixinGuiContainer implements IMixinGuiContainer {

    @Shadow
    protected int xSize;
    @Shadow
    protected int ySize;

    @Shadow
    protected abstract void handleMouseClick(Slot slot, int i, int j, ClickType clicktype);

    public void publicHandleMouseClick(Slot slot, int slotNumber, int clickedButton, ClickType clickType) {
        this.handleMouseClick(slot, slotNumber, clickedButton, clickType);
    }

    @Inject(
        method = { "drawScreen"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void drawScreenHead(CallbackInfo callbackInfo) {
        Animations animMod = (Animations) LiquidBounce.moduleManager.getModule(Animations.class);
        ChestStealer chestStealer = (ChestStealer) LiquidBounce.moduleManager.getModule(ChestStealer.class);
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        Minecraft mc = Minecraft.getMinecraft();

        if (((Boolean) hud.getContainerBackground().get()).booleanValue()) {
            RenderUtils.drawGradientSideways(0.0D, 0.0D, (double) this.xSize, (double) this.ySize, 0, 0);
        }

    }
}
