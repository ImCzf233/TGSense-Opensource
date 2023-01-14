package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.injection.implementations.IMixinGuiSlot;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({ GuiSlot.class})
public abstract class MixinGuiSlot implements IMixinGuiSlot {

    @Shadow
    public int left;
    @Shadow
    public int top;
    @Shadow
    public int width;
    @Shadow
    public int right;
    @Shadow
    public int bottom;
    @Shadow
    public int height;
    @Shadow
    protected int mouseX;
    @Shadow
    protected int mouseY;
    @Shadow
    protected float amountScrolled;
    @Shadow
    protected boolean hasListHeader;
    @Shadow
    @Final
    protected Minecraft mc;
    @Shadow
    protected boolean visible;
    private int listWidth = 220;
    private boolean enableScissor = false;

    @Shadow
    protected abstract void drawBackground();

    @Shadow
    protected abstract void bindAmountScrolled();

    @Shadow
    protected abstract void drawListHeader(int i, int j, Tessellator tessellator);

    @Shadow
    protected abstract int getContentHeight();

    @Shadow
    protected abstract void drawSelectionBox(int i, int j, int k, int l, float f);

    @Shadow
    protected abstract void overlayBackground(int i, int j, int k, int l);

    @Shadow
    public abstract int getMaxScroll();

    @Shadow
    protected abstract void drawContainerBackground(Tessellator tessellator);

    @Shadow
    protected abstract void renderDecorations(int i, int j);

    @Overwrite
    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
        if (this.visible) {
            this.mouseX = mouseXIn;
            this.mouseY = mouseYIn;
            this.drawBackground();
            int i = this.getScrollBarX();
            int j = i + 6;

            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            int k = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            int l = this.top + 4 - (int) this.amountScrolled;

            if (this.hasListHeader) {
                this.drawListHeader(k, l, tessellator);
            }

            RenderUtils.makeScissorBox((float) this.left, (float) this.top, (float) this.right, (float) this.bottom);
            GL11.glEnable(3089);
            this.drawSelectionBox(k, l, mouseXIn, mouseYIn, partialTicks);
            GL11.glDisable(3089);
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);

            GlStateManager.disableDepth();
            Gui.drawRect(0, 0, scaledResolution.getScaledWidth(), this.top, Integer.MIN_VALUE);
            Gui.drawRect(0, this.bottom, scaledResolution.getScaledWidth(), this.height, Integer.MIN_VALUE);
            GL11.glEnable(3042);
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            byte i1 = 4;

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos((double) this.left, (double) (this.top + i1), 0.0D).color(0, 0, 0, 0).endVertex();
            bufferbuilder.pos((double) this.right, (double) (this.top + i1), 0.0D).color(0, 0, 0, 0).endVertex();
            bufferbuilder.pos((double) this.right, (double) this.top, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos((double) this.left, (double) this.top, 0.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos((double) this.left, (double) this.bottom, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos((double) this.right, (double) this.bottom, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos((double) this.right, (double) (this.bottom - i1), 0.0D).color(0, 0, 0, 0).endVertex();
            bufferbuilder.pos((double) this.left, (double) (this.bottom - i1), 0.0D).color(0, 0, 0, 0).endVertex();
            tessellator.draw();
            int j1 = this.getMaxScroll();

            if (j1 > 0) {
                int k1 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();

                k1 = MathHelper.clamp(k1, 32, this.bottom - this.top - 8);
                int l1 = (int) this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;

                if (l1 < this.top) {
                    l1 = this.top;
                }

                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos((double) i, (double) this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos((double) j, (double) this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos((double) j, (double) this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos((double) i, (double) this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos((double) i, (double) (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos((double) j, (double) (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos((double) j, (double) l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos((double) i, (double) l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                tessellator.draw();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos((double) i, (double) (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                bufferbuilder.pos((double) (j - 1), (double) (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                bufferbuilder.pos((double) (j - 1), (double) l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                bufferbuilder.pos((double) i, (double) l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                tessellator.draw();
            }

            this.renderDecorations(mouseXIn, mouseYIn);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }

    }

    @Overwrite
    protected int getScrollBarX() {
        return this.width - 5;
    }

    public void setEnableScissor(boolean enableScissor) {
        this.enableScissor = enableScissor;
    }

    @Overwrite
    public int getListWidth() {
        return this.listWidth;
    }

    public void setListWidth(int listWidth) {
        this.listWidth = listWidth;
    }
}
