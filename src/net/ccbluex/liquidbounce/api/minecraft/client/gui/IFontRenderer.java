package net.ccbluex.liquidbounce.api.minecraft.client.gui;

import java.util.List;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J(\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0003H&J0\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&J(\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0003H&J0\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&J(\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003H&J(\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003H&J\b\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\bH&J\b\u0010\u0015\u001a\u00020\u000eH&J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00172\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0019"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "", "fontHeight", "", "getFontHeight", "()I", "drawCenteredString", "text", "", "x", "", "y", "color", "shadow", "", "drawString", "str", "drawStringWithShadow", "getGameFontRenderer", "Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "getStringWidth", "isGameFontRenderer", "listFormattedStringToWidth", "", "p2", "LiquidBounce"}
)
public interface IFontRenderer {

    int getFontHeight();

    @NotNull
    List listFormattedStringToWidth(@NotNull String s, int i);

    int getStringWidth(@NotNull String s);

    int drawString(@NotNull String s, int i, int j, int k);

    int drawString(@NotNull String s, float f, float f1, int i, boolean flag);

    int drawCenteredString(@NotNull String s, float f, float f1, int i);

    int drawCenteredString(@NotNull String s, float f, float f1, int i, boolean flag);

    int drawStringWithShadow(@NotNull String s, int i, int j, int k);

    boolean isGameFontRenderer();

    @NotNull
    GameFontRenderer getGameFontRenderer();

    int drawString(@NotNull String s, float f, float f1, int i);
}
