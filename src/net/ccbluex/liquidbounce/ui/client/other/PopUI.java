package net.ccbluex.liquidbounce.ui.client.other;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\u0018\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0016J&\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J\u0016\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006J\u0016\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006J.\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u0006J\b\u0010\u001e\u001a\u00020\u000eH\u0016J \u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006 "},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "", "title", "", "(Ljava/lang/String;)V", "baseHeight", "", "getBaseHeight", "()I", "baseWidth", "getBaseWidth", "getTitle", "()Ljava/lang/String;", "click", "", "mouseX", "", "mouseY", "close", "key", "typedChar", "", "keyCode", "onClick", "width", "height", "onKey", "onRender", "onStroll", "wheel", "render", "stroll", "LiquidBounce"}
)
public class PopUI {

    private final int baseWidth;
    private final int baseHeight;
    @NotNull
    private final String title;

    public final int getBaseWidth() {
        return this.baseWidth;
    }

    public final int getBaseHeight() {
        return this.baseHeight;
    }

    public final void onRender(int width, int height) {
        GL11.glPushMatrix();
        RenderUtils.drawRect(0.0F, 0.0F, (float) width, (float) height, (new Color(0, 0, 0, 50)).getRGB());
        float scale = (float) width * 0.2F / (float) this.baseWidth;

        GL11.glTranslatef((float) width * 0.4F, (float) height * 0.3F, 0.0F);
        GL11.glScalef(scale, scale, scale);
        float f = (float) this.baseWidth;
        float f1 = (float) this.baseHeight;
        Color color = Color.WHITE;

        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        RenderUtils.drawRect(0.0F, 0.0F, f, f1, color.getRGB());
        IFontRenderer ifontrenderer = Fonts.font40;
        String s = this.title;

        color = Color.DARK_GRAY;
        Intrinsics.checkExpressionValueIsNotNull(Color.DARK_GRAY, "Color.DARK_GRAY");
        ifontrenderer.drawString(s, 8.0F, 8.0F, color.getRGB());
        this.render();
        GL11.glPopMatrix();
    }

    public final void onClick(int width, int height, int mouseX, int mouseY) {
        float scale = (float) width * 0.2F / (float) this.baseWidth;
        float scaledMouseX = ((float) mouseX - (float) width * 0.4F) / scale;
        float scaledMouseY = ((float) mouseY - (float) height * 0.3F) / scale;

        if (scaledMouseX > (float) 0 && scaledMouseY > (float) 0 && scaledMouseX < (float) this.baseWidth && scaledMouseY < (float) this.baseHeight) {
            this.click(scaledMouseX, scaledMouseY);
        } else {
            this.close();
        }

    }

    public final void onStroll(int width, int height, int mouseX, int mouseY, int wheel) {
        float scale = (float) width * 0.2F / (float) this.baseWidth;
        float scaledMouseX = ((float) mouseX - (float) width * 0.4F) / scale;
        float scaledMouseY = ((float) mouseY - (float) height * 0.3F) / scale;

        if (scaledMouseX > (float) 0 && scaledMouseY > (float) 0 && scaledMouseX < (float) this.baseWidth && scaledMouseY < (float) this.baseHeight) {
            this.stroll(scaledMouseX, scaledMouseY, wheel);
        }

    }

    public final void onKey(char typedChar, int keyCode) {
        this.key(typedChar, keyCode);
    }

    public void render() {}

    public void key(char typedChar, int keyCode) {}

    public void close() {}

    public void click(float mouseX, float mouseY) {}

    public void stroll(float mouseX, float mouseY, int wheel) {}

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public PopUI(@NotNull String title) {
        Intrinsics.checkParameterIsNotNull(title, "title");
        super();
        this.title = title;
        this.baseWidth = 150;
        this.baseHeight = 210;
    }
}
