package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.ComponentOnHover;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.injection.backend.ResourceLocationImplKt;
import net.ccbluex.liquidbounce.ui.client.GuiBackground;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.BackgroundShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
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
@Mixin({ GuiScreen.class})
public abstract class MixinGuiScreen {

    @Shadow
    public Minecraft mc;
    @Shadow
    public List buttonList;
    @Shadow
    public int width;
    @Shadow
    public int height;
    @Shadow
    public FontRenderer fontRenderer;

    @Shadow
    public void updateScreen() {}

    @Shadow
    protected abstract void handleComponentHover(ITextComponent itextcomponent, int i, int j);

    @Shadow
    public abstract void drawHoveringText(List list, int i, int j);

    @Shadow
    public abstract void drawDefaultBackground();

    @Inject(
        method = { "drawWorldBackground"},
        at = {             @At("HEAD")}
    )
    private void drawWorldBackground(CallbackInfo callbackInfo) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        if (((Boolean) hud.getInventoryParticle().get()).booleanValue() && this.mc.player != null) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            int width = scaledResolution.getScaledWidth();
            int height = scaledResolution.getScaledHeight();

            ParticleUtils.drawParticles(Mouse.getX() * width / this.mc.displayWidth, height - Mouse.getY() * height / this.mc.displayHeight - 1);
        }

    }

    @Inject(
        method = { "drawBackground"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void drawClientBackground(CallbackInfo callbackInfo) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        if (GuiBackground.Companion.getEnabled()) {
            if (LiquidBounce.INSTANCE.getBackground() == null) {
                BackgroundShader.BACKGROUND_SHADER.startShader();
                Tessellator scaledResolution = Tessellator.getInstance();
                BufferBuilder width = scaledResolution.getBuffer();

                width.begin(7, DefaultVertexFormats.POSITION);
                width.pos(0.0D, (double) this.height, 0.0D).endVertex();
                width.pos((double) this.width, (double) this.height, 0.0D).endVertex();
                width.pos((double) this.width, 0.0D, 0.0D).endVertex();
                width.pos(0.0D, 0.0D, 0.0D).endVertex();
                scaledResolution.draw();
                BackgroundShader.BACKGROUND_SHADER.stopShader();
            } else {
                ScaledResolution scaledResolution1 = new ScaledResolution(this.mc);
                int width1 = scaledResolution1.getScaledWidth();
                int height = scaledResolution1.getScaledHeight();

                this.mc.getTextureManager().bindTexture(ResourceLocationImplKt.unwrap(LiquidBounce.INSTANCE.getBackground()));
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, width1, height, width1, height, (float) width1, (float) height);
            }

            if (GuiBackground.Companion.getParticles()) {
                ParticleUtils.drawParticles(Mouse.getX() * this.width / this.mc.displayWidth, this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1);
            }

            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "drawBackground"},
        at = {             @At("RETURN")}
    )
    private void drawParticles(CallbackInfo callbackInfo) {
        if (GuiBackground.Companion.getParticles()) {
            ParticleUtils.drawParticles(Mouse.getX() * this.width / this.mc.displayWidth, this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1);
        }

    }

    @Inject(
        method = { "drawDefaultBackground"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    public void drawDefaultBackground(CallbackInfo callbackInfo) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        if (!((Boolean) hud.getContainerBackground().get()).booleanValue() && this.mc.currentScreen instanceof GuiContainer) {
            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "sendChatMessage(Ljava/lang/String;Z)V"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void messageSend(String msg, boolean addToChat, CallbackInfo callbackInfo) {
        if (msg.startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix())) && addToChat) {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
            LiquidBounce.commandManager.executeCommands(msg);
            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "handleComponentHover"},
        at = {             @At("HEAD")}
    )
    private void handleHoverOverComponent(ITextComponent component, int x, int y, CallbackInfo callbackInfo) {
        if (component != null && component.getStyle().getClickEvent() != null && LiquidBounce.moduleManager.getModule(ComponentOnHover.class).getState()) {
            Style chatStyle = component.getStyle();
            ClickEvent clickEvent = chatStyle.getClickEvent();
            HoverEvent hoverEvent = chatStyle.getHoverEvent();

            this.drawHoveringText(Collections.singletonList("§c§l" + clickEvent.getAction().getCanonicalName().toUpperCase() + ": §a" + clickEvent.getValue()), x, y - (hoverEvent != null ? 17 : 0));
        }
    }

    @Overwrite
    protected void actionPerformed(GuiButton button) throws IOException {
        this.injectedActionPerformed(button);
    }

    protected void injectedActionPerformed(GuiButton button) {}
}
