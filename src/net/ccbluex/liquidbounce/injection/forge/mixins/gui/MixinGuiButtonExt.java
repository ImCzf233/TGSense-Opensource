package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import net.ccbluex.liquidbounce.injection.backend.FontRendererImpl;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@SideOnly(Side.CLIENT)
@Mixin({ GuiButtonExt.class})
public abstract class MixinGuiButtonExt extends GuiButton {

    private float cut;
    private float alpha;

    public MixinGuiButtonExt(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public MixinGuiButtonExt(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            FontRenderer fontRenderer = mc.getLanguageManager().isCurrentLocaleUnicode() ? mc.fontRenderer : ((FontRendererImpl) Fonts.fontSFUI35).getWrapped();

            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int delta = RenderUtils.deltaTime;

            if (this.enabled && this.hovered) {
                this.cut += 0.05F * (float) delta;
                if (this.cut >= 4.0F) {
                    this.cut = 4.0F;
                }

                this.alpha += 0.3F * (float) delta;
                if (this.alpha >= 210.0F) {
                    this.alpha = 210.0F;
                }
            } else {
                this.cut -= 0.05F * (float) delta;
                if (this.cut <= 0.0F) {
                    this.cut = 0.0F;
                }

                this.alpha -= 0.3F * (float) delta;
                if (this.alpha <= 120.0F) {
                    this.alpha = 120.0F;
                }
            }

            RenderUtils.drawRect(this.x + (int) this.cut, this.y, this.x + this.width - (int) this.cut, this.y + this.height, this.enabled ? (new Color(0.0F, 0.0F, 0.0F, this.alpha / 255.0F)).getRGB() : (new Color(0.5F, 0.5F, 0.5F, 0.5F)).getRGB());
            RenderUtils.drawShadow(this.x + (int) this.cut, this.y, this.width - (int) this.cut - (int) this.cut, this.height);
            RenderUtils.drawShadow(this.x + (int) this.cut, this.y, this.width - (int) this.cut - (int) this.cut, this.height);
            mc.getTextureManager().bindTexture(MixinGuiButtonExt.BUTTON_TEXTURES);
            this.mouseDragged(mc, mouseX, mouseY);
            fontRenderer.drawStringWithShadow(this.displayString, (float) (this.x + this.width / 2 - fontRenderer.getStringWidth(this.displayString) / 2), (float) this.y + (float) (this.height - 5) / 2.0F, 14737632);
            GlStateManager.resetColor();
        }

    }
}
