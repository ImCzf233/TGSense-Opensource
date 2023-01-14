package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Translate;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/hotbarutil;", "", "()V", "size", "", "getSize", "()F", "setSize", "(F)V", "translate", "Lnet/ccbluex/liquidbounce/utils/render/Translate;", "getTranslate", "()Lnet/ccbluex/liquidbounce/utils/render/Translate;", "renderHotbarItem", "", "index", "", "xPos", "yPos", "partialTicks", "LiquidBounce"}
)
public final class hotbarutil {

    @NotNull
    private final Translate translate = new Translate(0.0F, 0.0F);
    private float size = 1.0F;

    @NotNull
    public final Translate getTranslate() {
        return this.translate;
    }

    public final float getSize() {
        return this.size;
    }

    public final void setSize(float <set-?>) {
        this.size = <set-?>;
    }

    public final void renderHotbarItem(int index, float xPos, float yPos, float partialTicks) {
        Object object = MinecraftInstance.mc2.player.inventory.mainInventory.get(index);

        Intrinsics.checkExpressionValueIsNotNull(object, "MinecraftInstance.mc2.pl…tory.mainInventory[index]");
        ItemStack itemStack = (ItemStack) object;

        if (itemStack != null) {
            float lvt_7_1_ = (float) itemStack.getAnimationsToGo() - partialTicks;

            if (lvt_7_1_ > 0.0F) {
                GlStateManager.pushMatrix();
                float lvt_8_1_ = 1.0F + lvt_7_1_ / 5.0F;

                GlStateManager.translate(xPos + (float) 8, yPos + (float) 12, 0.0F);
                GlStateManager.scale(1.0F / lvt_8_1_, (lvt_8_1_ + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate(-(xPos + (float) 8), -(yPos + (float) 12), 0.0F);
            }

            RenderUtils.drawTexturedRect((int) (xPos - (float) 7), (int) (yPos - (float) 7), 30, 30, "hotbar", new ScaledResolution(MinecraftInstance.mc2));
            RenderUtils.drawTexturedRect((int) (xPos - (float) 7), (int) (yPos - (float) 7), 30, 30, "hotbar", new ScaledResolution(MinecraftInstance.mc2));
        }

    }
}
