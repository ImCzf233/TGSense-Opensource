package me.Skid.ui.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.Verify.font.CFontRenderer;
import me.Skid.Verify.font.FontLoaders;
import me.Skid.utils.LiquidColorUtils;
import me.Skid.utils.NovoUtils;
import me.Skid.utils.Render.Translate;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "HotBar"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JP\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0002J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J2\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ(\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b¨\u0006 "},
    d2 = { "Lme/Skid/ui/elements/HotBar;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "draw", "", "p_draw_1_", "Lnet/minecraft/client/renderer/BufferBuilder;", "p_draw_2_", "", "p_draw_3_", "p_draw_4_", "p_draw_5_", "p_draw_6_", "p_draw_7_", "p_draw_8_", "p_draw_9_", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "renderItemOverlayIntoGUI", "p_renderItemOverlayIntoGUI_1_", "Lme/Skid/Verify/font/CFontRenderer;", "p_renderItemOverlayIntoGUI_2_", "Lnet/minecraft/item/ItemStack;", "p_renderItemOverlayIntoGUI_3_", "p_renderItemOverlayIntoGUI_4_", "p_renderItemOverlayIntoGUI_5_", "", "renderItemOverlays", "p_renderItemOverlays_1_", "p_renderItemOverlays_2_", "p_renderItemOverlays_3_", "p_renderItemOverlays_4_", "LiquidBounce"}
)
public final class HotBar extends Element {

    @Nullable
    public Border drawElement() {
        ArrayList arraylist = new ArrayList();
        int x = 0;

        for (byte novo = 8; x <= novo; ++x) {
            arraylist.add(new NovoUtils(MinecraftInstance.mc2.player.inventory.getStackInSlot(x), new Translate(0.9F, 0.0F), 10.0F, LiquidColorUtils.rainbow(x + 1, 40), LiquidColorUtils.rainbow(x + 1, 255), LiquidColorUtils.rainbow(x + 2, 255), LiquidColorUtils.rainbow(x + 1, 130)));
        }

        x = 0;

        for (Iterator iterator = arraylist.iterator(); iterator.hasNext(); x += 35) {
            NovoUtils novoutils = (NovoUtils) iterator.next();
            double d0 = (double) (x - 13);
            double d1 = (double) ((int) novoutils.y + 18);
            double d2 = (double) (x + 14);
            double d3 = (double) ((int) novoutils.y + 19);
            Color color = novoutils.color2;

            Intrinsics.checkExpressionValueIsNotNull(novoutils.color2, "novo.color2");
            int i = color.getRGB();
            Color color1 = novoutils.color3;

            Intrinsics.checkExpressionValueIsNotNull(novoutils.color3, "novo.color3");
            RenderUtils.drawGradientSideways(d0, d1, d2, d3, i, color1.getRGB());
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (Intrinsics.areEqual(MinecraftInstance.mc2.player.inventory.getCurrentItem(), novoutils.stack)) {
                d0 = (double) (x - 13);
                d1 = (double) ((int) novoutils.y - 5);
                d2 = (double) (x + 14);
                d3 = (double) ((int) novoutils.y + 18);
                color = novoutils.color4;
                Intrinsics.checkExpressionValueIsNotNull(novoutils.color4, "novo.color4");
                RenderUtils.drawGradientSidewaysV(d0, d1, d2, d3, color.getRGB(), 0);
            } else {
                d0 = (double) (x - 13);
                d1 = (double) ((int) novoutils.y - 5);
                d2 = (double) (x + 14);
                d3 = (double) ((int) novoutils.y + 18);
                color = novoutils.color;
                Intrinsics.checkExpressionValueIsNotNull(novoutils.color, "novo.color");
                RenderUtils.drawGradientSidewaysV(d0, d1, d2, d3, color.getRGB(), 0);
            }

            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            Minecraft minecraft = MinecraftInstance.mc2;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2, "mc2");
            minecraft.getRenderItem().zLevel = -150.0F;
            if (Intrinsics.areEqual(MinecraftInstance.mc2.player.inventory.getCurrentItem(), novoutils.stack) && MinecraftInstance.mc2.player.inventory.getCurrentItem() != null) {
                novoutils.Translate.interpolate(1.3F, 0.1F, 5.0D);
                novoutils.y = 3.0F;
            }

            GL11.glPushMatrix();
            Translate translate = novoutils.Translate;

            Intrinsics.checkExpressionValueIsNotNull(novoutils.Translate, "novo.Translate");
            d0 = (double) translate.getX();
            Translate translate1 = novoutils.Translate;

            Intrinsics.checkExpressionValueIsNotNull(novoutils.Translate, "novo.Translate");
            d1 = (double) translate1.getX();
            Translate translate2 = novoutils.Translate;

            Intrinsics.checkExpressionValueIsNotNull(novoutils.Translate, "novo.Translate");
            GL11.glScaled(d0, d1, (double) translate2.getX());
            minecraft = MinecraftInstance.mc2;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2, "mc2");
            RenderItem renderitem = minecraft.getRenderItem();
            ItemStack itemstack = novoutils.stack;
            float f = (float) x;
            Translate translate3 = novoutils.Translate;

            Intrinsics.checkExpressionValueIsNotNull(novoutils.Translate, "novo.Translate");
            renderitem.renderItemAndEffectIntoGUI(itemstack, (int) (f / translate3.getX() - 7.0F), (int) novoutils.y);
            GL11.glPopMatrix();
            CFontRenderer cfontrenderer;

            if (Intrinsics.areEqual(MinecraftInstance.mc2.player.inventory.getCurrentItem(), novoutils.stack)) {
                cfontrenderer = FontLoaders.C16;
                Intrinsics.checkExpressionValueIsNotNull(FontLoaders.C16, "me.Skid.Verify.font.FontLoaders.C16");
                this.renderItemOverlays(cfontrenderer, novoutils.stack, x - 3, (int) (novoutils.y + 7.0F));
            } else {
                cfontrenderer = FontLoaders.C16;
                Intrinsics.checkExpressionValueIsNotNull(FontLoaders.C16, "me.Skid.Verify.font.FontLoaders.C16");
                ItemStack itemstack1 = novoutils.stack;
                int j = x - 3;
                float f1 = (float) ((int) novoutils.y);
                Translate translate4 = novoutils.Translate;

                Intrinsics.checkExpressionValueIsNotNull(novoutils.Translate, "novo.Translate");
                this.renderItemOverlays(cfontrenderer, itemstack1, j, (int) (f1 / translate4.getX()));
            }

            minecraft = MinecraftInstance.mc2;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2, "mc2");
            minecraft.getRenderItem().zLevel = 0.0F;
            GlStateManager.disableBlend();
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
            GL11.glPopMatrix();
        }

        return new Border(-16.0F, 8.0F, (float) (arraylist.size() * 33), 30.0F);
    }

    public final void renderItemOverlays(@NotNull CFontRenderer p_renderItemOverlays_1_, @Nullable ItemStack p_renderItemOverlays_2_, int p_renderItemOverlays_3_, int p_renderItemOverlays_4_) {
        Intrinsics.checkParameterIsNotNull(p_renderItemOverlays_1_, "p_renderItemOverlays_1_");
        this.renderItemOverlayIntoGUI(p_renderItemOverlays_1_, p_renderItemOverlays_2_, p_renderItemOverlays_3_, p_renderItemOverlays_4_, (String) null);
    }

    public final void renderItemOverlayIntoGUI(@NotNull CFontRenderer p_renderItemOverlayIntoGUI_1_, @Nullable ItemStack p_renderItemOverlayIntoGUI_2_, int p_renderItemOverlayIntoGUI_3_, int p_renderItemOverlayIntoGUI_4_, @Nullable String p_renderItemOverlayIntoGUI_5_) {
        Intrinsics.checkParameterIsNotNull(p_renderItemOverlayIntoGUI_1_, "p_renderItemOverlayIntoGUI_1_");
        if (p_renderItemOverlayIntoGUI_2_ != null) {
            if (p_renderItemOverlayIntoGUI_2_.stackSize != 1 || p_renderItemOverlayIntoGUI_5_ != null) {
                String s = p_renderItemOverlayIntoGUI_5_;

                if (p_renderItemOverlayIntoGUI_5_ == null) {
                    s = String.valueOf(p_renderItemOverlayIntoGUI_2_.stackSize);
                }

                String health = s;

                if (p_renderItemOverlayIntoGUI_5_ == null && p_renderItemOverlayIntoGUI_2_.stackSize < 1) {
                    health = WEnumChatFormatting.RED.toString() + String.valueOf(p_renderItemOverlayIntoGUI_2_.stackSize);
                }

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                p_renderItemOverlayIntoGUI_1_.drawStringWithShadow(health, (double) ((float) (p_renderItemOverlayIntoGUI_3_ + 19 - 2 - p_renderItemOverlayIntoGUI_1_.getStringWidth(health))), (double) ((float) (p_renderItemOverlayIntoGUI_4_ + 6 + 3)), 16777215);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }

            if (p_renderItemOverlayIntoGUI_2_.getItem().showDurabilityBar(p_renderItemOverlayIntoGUI_2_)) {
                double health1 = p_renderItemOverlayIntoGUI_2_.getItem().getDurabilityForDisplay(p_renderItemOverlayIntoGUI_2_);
                int j = (int) Math.round(13.0D - health1 * 13.0D);
                int i = (int) Math.round(255.0D - health1 * 255.0D);

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                Tessellator tessellator = Tessellator.getInstance();

                Intrinsics.checkExpressionValueIsNotNull(tessellator, "tessellator");
                BufferBuilder worldrenderer = tessellator.getBuffer();

                Intrinsics.checkExpressionValueIsNotNull(worldrenderer, "worldrenderer");
                this.draw(worldrenderer, p_renderItemOverlayIntoGUI_3_ + 2, p_renderItemOverlayIntoGUI_4_ + 13, 13, 2, 0, 0, 0, 255);
                this.draw(worldrenderer, p_renderItemOverlayIntoGUI_3_ + 2, p_renderItemOverlayIntoGUI_4_ + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
                this.draw(worldrenderer, p_renderItemOverlayIntoGUI_3_ + 2, p_renderItemOverlayIntoGUI_4_ + 13, j, 1, 255 - i, i, 0, 255);
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }

    }

    private final void draw(BufferBuilder p_draw_1_, int p_draw_2_, int p_draw_3_, int p_draw_4_, int p_draw_5_, int p_draw_6_, int p_draw_7_, int p_draw_8_, int p_draw_9_) {
        p_draw_1_.begin(7, DefaultVertexFormats.POSITION_COLOR);
        p_draw_1_.pos((double) (p_draw_2_ + 0), (double) (p_draw_3_ + 0), 0.0D).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        p_draw_1_.pos((double) (p_draw_2_ + 0), (double) (p_draw_3_ + p_draw_5_), 0.0D).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        p_draw_1_.pos((double) (p_draw_2_ + p_draw_4_), (double) (p_draw_3_ + p_draw_5_), 0.0D).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        p_draw_1_.pos((double) (p_draw_2_ + p_draw_4_), (double) (p_draw_3_ + 0), 0.0D).color(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).endVertex();
        Tessellator.getInstance().draw();
    }

    public HotBar() {
        super(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
        this.setX(338.0D);
        this.setY(478.0D);
    }
}
