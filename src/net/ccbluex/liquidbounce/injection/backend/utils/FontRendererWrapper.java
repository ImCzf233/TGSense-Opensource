package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J2\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J*\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0016J*\u0010\u0011\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010\u0016\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0017"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/utils/FontRendererWrapper;", "Lnet/minecraft/client/gui/FontRenderer;", "wrapped", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "(Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;)V", "getWrapped", "()Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "drawString", "", "text", "", "x", "", "y", "color", "dropShadow", "", "drawStringWithShadow", "getCharWidth", "character", "", "getColorCode", "getStringWidth", "LiquidBounce"}
)
public final class FontRendererWrapper extends FontRenderer {

    @NotNull
    private final IWrappedFontRenderer wrapped;

    public int drawString(@Nullable String text, int x, int y, int color) {
        return this.wrapped.drawString(text, (float) x, (float) y, color);
    }

    public int drawString(@Nullable String text, float x, float y, int color, boolean dropShadow) {
        return this.wrapped.drawString(text, x, y, color, dropShadow);
    }

    public int drawStringWithShadow(@Nullable String text, float x, float y, int color) {
        return this.wrapped.drawStringWithShadow(text, x, y, color);
    }

    public int getColorCode(char character) {
        return this.wrapped.getColorCode(character);
    }

    public int getStringWidth(@Nullable String text) {
        return this.wrapped.getStringWidth(text);
    }

    public int getCharWidth(char character) {
        return this.wrapped.getCharWidth(character);
    }

    @NotNull
    public final IWrappedFontRenderer getWrapped() {
        return this.wrapped;
    }

    public FontRendererWrapper(@NotNull IWrappedFontRenderer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        GameSettings gamesettings = Minecraft.getMinecraft().gameSettings;
        ResourceLocation resourcelocation = new ResourceLocation("textures/font/ascii.png");
        Minecraft minecraft = Minecraft.getMinecraft();

        Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
        super(gamesettings, resourcelocation, minecraft.getTextureManager(), false);
        this.wrapped = wrapped;
    }
}
