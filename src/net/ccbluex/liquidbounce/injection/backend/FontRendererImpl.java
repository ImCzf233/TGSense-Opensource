package net.ccbluex.liquidbounce.injection.backend;

import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.ccbluex.liquidbounce.injection.backend.utils.FontRendererWrapper;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J0\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J(\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J0\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J(\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J(\u0010\u0016\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\u0013\u0010\u0017\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\rH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J<\u0010\u001e\u001a&\u0012\f\u0012\n  *\u0004\u0018\u00010\r0\r  *\u0012\u0012\f\u0012\n  *\u0004\u0018\u00010\r0\r\u0018\u00010!0\u001f2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006#"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/FontRendererImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "wrapped", "Lnet/minecraft/client/gui/FontRenderer;", "(Lnet/minecraft/client/gui/FontRenderer;)V", "fontHeight", "", "getFontHeight", "()I", "getWrapped", "()Lnet/minecraft/client/gui/FontRenderer;", "drawCenteredString", "text", "", "x", "", "y", "color", "shadow", "", "drawString", "str", "drawStringWithShadow", "equals", "other", "", "getGameFontRenderer", "Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "getStringWidth", "isGameFontRenderer", "listFormattedStringToWidth", "", "kotlin.jvm.PlatformType", "", "p2", "LiquidBounce"}
)
public final class FontRendererImpl implements IFontRenderer {

    @NotNull
    private final FontRenderer wrapped;

    public int getFontHeight() {
        return this.wrapped.FONT_HEIGHT;
    }

    public int getStringWidth(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.getStringWidth(str);
    }

    public int drawString(@NotNull String str, int x, int y, int color) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.drawString(str, x, y, color);
    }

    public int drawString(@NotNull String str, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.drawString(str, (int) x, (int) y, color);
    }

    public List listFormattedStringToWidth(@NotNull String str, int p2) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.listFormattedStringToWidth(str, p2);
    }

    public int drawString(@NotNull String str, float x, float y, int color, boolean shadow) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        return this.wrapped.drawString(str, x, y, color, shadow);
    }

    public int drawCenteredString(@NotNull String text, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return this.drawString(text, x - (float) this.getStringWidth(text) / 2.0F, y, color);
    }

    public int drawCenteredString(@NotNull String text, float x, float y, int color, boolean shadow) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return this.drawString(text, x - (float) this.getStringWidth(text) / 2.0F, y, color, shadow);
    }

    public int drawStringWithShadow(@NotNull String text, int x, int y, int color) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return this.wrapped.drawStringWithShadow(text, (float) x, (float) y, color);
    }

    public boolean isGameFontRenderer() {
        return this.wrapped instanceof FontRendererWrapper;
    }

    @NotNull
    public GameFontRenderer getGameFontRenderer() {
        FontRenderer fontrenderer = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.utils.FontRendererWrapper");
        } else {
            IWrappedFontRenderer iwrappedfontrenderer = ((FontRendererWrapper) fontrenderer).getWrapped();

            if (iwrappedfontrenderer == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.ui.font.GameFontRenderer");
            } else {
                return (GameFontRenderer) iwrappedfontrenderer;
            }
        }
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof FontRendererImpl && Intrinsics.areEqual(((FontRendererImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final FontRenderer getWrapped() {
        return this.wrapped;
    }

    public FontRendererImpl(@NotNull FontRenderer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
