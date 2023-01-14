package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J \u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J \u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J(\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J(\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0014\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006 "},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/RenderItemImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/entity/IRenderItem;", "wrapped", "Lnet/minecraft/client/renderer/RenderItem;", "(Lnet/minecraft/client/renderer/RenderItem;)V", "getWrapped", "()Lnet/minecraft/client/renderer/RenderItem;", "value", "", "zLevel", "getZLevel", "()F", "setZLevel", "(F)V", "equals", "", "other", "", "renderItemAndEffectIntoGUI", "", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "x", "", "y", "renderItemAndEffectIntoGUI2", "Lnet/minecraft/item/ItemStack;", "renderItemIntoGUI", "renderItemOverlays", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "renderItemOverlays2", "LiquidBounce"}
)
public final class RenderItemImpl implements IRenderItem {

    @NotNull
    private final RenderItem wrapped;

    public float getZLevel() {
        return this.wrapped.zLevel;
    }

    public void setZLevel(float value) {
        this.wrapped.zLevel = value;
    }

    public void renderItemAndEffectIntoGUI(@NotNull IItemStack stack, int x, int y) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        RenderItem renderitem = this.wrapped;
        boolean $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) stack).getWrapped();

        renderitem.renderItemAndEffectIntoGUI(itemstack, x, y);
    }

    public void renderItemIntoGUI(@NotNull IItemStack stack, int x, int y) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        RenderItem renderitem = this.wrapped;
        boolean $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) stack).getWrapped();

        renderitem.renderItemIntoGUI(itemstack, x, y);
    }

    public void renderItemOverlays(@NotNull IFontRenderer fontRenderer, @NotNull IItemStack stack, int x, int y) {
        Intrinsics.checkParameterIsNotNull(fontRenderer, "fontRenderer");
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        RenderItem renderitem = this.wrapped;
        boolean $i$f$unwrap = false;
        FontRenderer fontrenderer = ((FontRendererImpl) fontRenderer).getWrapped();

        $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) stack).getWrapped();

        renderitem.renderItemOverlays(fontrenderer, itemstack, x, y);
    }

    public void renderItemAndEffectIntoGUI2(@NotNull ItemStack stack, int x, int y) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        RenderItem renderitem = this.wrapped;
        boolean flag = false;
        boolean flag1 = false;
        boolean $i$a$-also-RenderItemImpl$renderItemAndEffectIntoGUI2$1 = false;
        boolean $i$f$unwrap = false;

        if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.RenderItemImpl");
        } else {
            ((RenderItemImpl) this).getWrapped();
            renderitem.renderItemAndEffectIntoGUI(stack, x, y);
        }
    }

    public void renderItemOverlays2(@NotNull IFontRenderer fontRenderer, @NotNull ItemStack stack, int x, int y) {
        Intrinsics.checkParameterIsNotNull(fontRenderer, "fontRenderer");
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        RenderItem renderitem = this.wrapped;
        boolean $i$f$unwrap = false;
        FontRenderer fontrenderer = ((FontRendererImpl) fontRenderer).getWrapped();

        $i$f$unwrap = false;
        boolean flag = false;
        boolean $i$a$-also-RenderItemImpl$renderItemOverlays2$1 = false;
        boolean $i$f$unwrap1 = false;

        if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.RenderItemImpl");
        } else {
            ((RenderItemImpl) this).getWrapped();
            renderitem.renderItemOverlays(fontrenderer, stack, x, y);
        }
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof RenderItemImpl && Intrinsics.areEqual(((RenderItemImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final RenderItem getWrapped() {
        return this.wrapped;
    }

    public RenderItemImpl(@NotNull RenderItem wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
