package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import net.ccbluex.liquidbounce.injection.backend.FontRendererImpl;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({ GuiButton.class})
public abstract class MixinGuiButton extends Gui {

    @Shadow
    @Final
    protected static ResourceLocation BUTTON_TEXTURES;
    @Shadow
    public boolean visible;
    @Shadow
    public int x;
    @Shadow
    public int y;
    @Shadow
    public int width;
    @Shadow
    public int height;
    @Shadow
    public boolean enabled;
    @Shadow
    public String displayString;
    @Shadow
    protected boolean hovered;
    private float cut;
    private float alpha;

    @Shadow
    protected abstract void mouseDragged(Minecraft minecraft, int i, int j);

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

            RenderUtils.drawRoundRect((float) (this.x + (int) this.cut), (float) this.y, (float) (this.x + this.width - (int) this.cut), (float) (this.y + this.height), 3.0F, this.enabled ? (new Color(0.0F, 0.0F, 0.0F, this.alpha / 255.0F)).getRGB() : (new Color(0.5F, 0.5F, 0.5F, 0.5F)).getRGB());
            this.mouseDragged(mc, mouseX, mouseY);
            AWTFontRenderer.Companion.setAssumeNonVolatile(true);
            fontRenderer.drawStringWithShadow(this.displayString, (float) (this.x + this.width / 2 - fontRenderer.getStringWidth(this.displayString) / 2), (float) this.y + (float) (this.height - 5) / 2.0F, 14737632);
            AWTFontRenderer.Companion.setAssumeNonVolatile(false);
            GlStateManager.resetColor();
        }

    }
}
