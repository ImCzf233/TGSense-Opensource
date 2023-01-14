package net.ccbluex.liquidbounce.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u0000 ,2\u00020\u0001:\u0001,B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016J0\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J*\u0010\u001f\u001a\u00020\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016J2\u0010\u001f\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J*\u0010!\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016J&\u0010\"\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000eJ<\u0010#\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020\u001e2\b\b\u0002\u0010%\u001a\u00020\u001eH\u0002J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010\'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020(H\u0016J\u0012\u0010+\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u0018H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010¨\u0006-"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "font", "Ljava/awt/Font;", "(Ljava/awt/Font;)V", "boldFont", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "boldItalicFont", "defaultFont", "getDefaultFont", "()Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "setDefaultFont", "(Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;)V", "fontHeight", "", "getFontHeight", "()I", "height", "getHeight", "italicFont", "size", "getSize", "drawCenteredString", "s", "", "x", "", "y", "color", "shadow", "", "drawString", "text", "drawStringWithShadow", "drawStringWithShadow2", "drawText", "ignoreColor", "rainbow", "getCharWidth", "character", "", "getColorCode", "charCode", "getStringWidth", "Companion", "LiquidBounce"}
)
public final class GameFontRenderer implements IWrappedFontRenderer {

    private final int fontHeight;
    @NotNull
    private AWTFontRenderer defaultFont;
    private AWTFontRenderer boldFont;
    private AWTFontRenderer italicFont;
    private AWTFontRenderer boldItalicFont;
    public static final GameFontRenderer.Companion Companion = new GameFontRenderer.Companion((DefaultConstructorMarker) null);

    public final int getFontHeight() {
        return this.fontHeight;
    }

    @NotNull
    public final AWTFontRenderer getDefaultFont() {
        return this.defaultFont;
    }

    public final void setDefaultFont(@NotNull AWTFontRenderer <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.defaultFont = <set-?>;
    }

    public final int getHeight() {
        return this.defaultFont.getHeight() / 2;
    }

    public final int getSize() {
        return this.defaultFont.getFont().getSize();
    }

    public final int drawStringWithShadow2(@NotNull String text, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        this.drawString(text, x + 0.5F, y + 0.5F, -16777216);
        return this.drawString(text, x, y, color);
    }

    public int drawString(@Nullable String s, float x, float y, int color) {
        return this.drawString(s, x, y, color, false);
    }

    public int drawStringWithShadow(@Nullable String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, true);
    }

    public int drawCenteredString(@NotNull String s, float x, float y, int color, boolean shadow) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return this.drawString(s, x - (float) this.getStringWidth(s) / 2.0F, y, color, shadow);
    }

    public int drawCenteredString(@NotNull String s, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return this.drawStringWithShadow(s, x - (float) this.getStringWidth(s) / 2.0F, y, color);
    }

    public int drawString(@Nullable String text, float x, float y, int color, boolean shadow) {
        TextEvent event = new TextEvent(text);

        LiquidBounce.INSTANCE.getEventManager().callEvent((Event) event);
        String s = event.getText();

        if (s != null) {
            String currentText = s;
            float currY = y - 3.0F;
            boolean rainbow = RainbowFontShader.INSTANCE.isInUse();

            if (shadow) {
                GL20.glUseProgram(0);
                drawText$default(this, currentText, x + 1.0F, currY + 1.0F, (new Color(0, 0, 0, 150)).getRGB(), true, false, 32, (Object) null);
            }

            if (shadow) {
                GL20.glUseProgram(0);
                if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "Test", true)) {
                    drawText$default(this, currentText, x + 0.5F, currY + 0.5F, (new Color(200, 200, 200, 70)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY - 0.5F, (new Color(200, 200, 200, 70)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.5F, currY - 0.5F, (new Color(200, 200, 200, 70)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY + 0.5F, (new Color(200, 200, 200, 70)).getRGB(), true, false, 32, (Object) null);
                } else if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "LiquidBounce", true)) {
                    drawText$default(this, currentText, x + 1.0F, currY + 1.0F, (new Color(0, 0, 0, 150)).getRGB(), true, false, 32, (Object) null);
                } else if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "Default", true)) {
                    drawText$default(this, currentText, x + 0.5F, currY + 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                } else if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "blue", true)) {
                    drawText$default(this, currentText, x + 0.5F, currY + 0.5F, (new Color(0, 120, 225, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.5F, currY - 0.5F, (new Color(0, 120, 225, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY + 0.5F, (new Color(0, 120, 225, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY - 0.5F, (new Color(0, 120, 225, 130)).getRGB(), true, false, 32, (Object) null);
                } else if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "langya", true)) {
                    drawText$default(this, currentText, x + 0.25F, currY + 0.25F, (new Color(30, 30, 30, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.25F, currY - 0.25F, (new Color(20, 20, 20, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.25F, currY - 0.25F, (new Color(30, 30, 30, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.25F, currY + 0.25F, (new Color(20, 20, 20, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.5F, currY + 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.5F, currY - 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY + 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY - 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                } else if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "langya2", true)) {
                    drawText$default(this, currentText, x + 0.25F, currY + 0.25F, (new Color(60, 60, 60, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.25F, currY - 0.25F, (new Color(60, 60, 60, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.25F, currY - 0.25F, (new Color(60, 60, 60, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.25F, currY + 0.25F, (new Color(60, 60, 60, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.5F, currY + 0.5F, (new Color(60, 60, 60, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY - 0.5F, (new Color(60, 60, 60, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.5F, currY - 0.5F, (new Color(60, 60, 60, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY + 0.5F, (new Color(60, 60, 60, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 1.0F, currY + 1.0F, (new Color(60, 60, 60, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 1.0F, currY - 1.0F, (new Color(60, 60, 60, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 1.0F, currY - 1.0F, (new Color(60, 60, 60, 160)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 1.0F, currY + 1.0F, (new Color(60, 60, 60, 130)).getRGB(), true, false, 32, (Object) null);
                } else if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "Autumn", true)) {
                    drawText$default(this, currentText, x + 1.0F, currY + 1.0F, (new Color(20, 20, 20, 200)).getRGB(), true, false, 32, (Object) null);
                } else if (StringsKt.equals((String) HUD.Companion.getShadowValue().get(), "Outline", true)) {
                    drawText$default(this, currentText, x + 0.5F, currY + 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY - 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x + 0.5F, currY - 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                    drawText$default(this, currentText, x - 0.5F, currY + 0.5F, (new Color(0, 0, 0, 130)).getRGB(), true, false, 32, (Object) null);
                }
            }

            return this.drawText(currentText, x, currY, color, false, rainbow);
        } else {
            return 0;
        }
    }

    private final int drawText(String text, float x, float y, int color, boolean ignoreColor, boolean rainbow) {
        if (text == null) {
            return 0;
        } else {
            CharSequence rainbowShaderId = (CharSequence) text;
            boolean currentColor = false;
            boolean defaultColor = false;

            if (rainbowShaderId.length() == 0) {
                return (int) x;
            } else {
                int i = RainbowFontShader.INSTANCE.getProgramId();

                if (rainbow) {
                    GL20.glUseProgram(i);
                }

                GL11.glTranslated((double) x - 1.5D, (double) y + 0.5D, 0.0D);
                WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().enableAlpha();
                WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().enableBlend();
                WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().tryBlendFuncSeparate(770, 771, 1, 0);
                WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().enableTexture2D();
                int j = color;

                if ((color & -67108864) == 0) {
                    j = color | -16777216;
                }

                int alpha = j >> 24 & 255;

                if (StringsKt.contains$default((CharSequence) text, (CharSequence) "§", false, 2, (Object) null)) {
                    List parts = StringsKt.split$default((CharSequence) text, new String[] { "§"}, false, 0, 6, (Object) null);
                    AWTFontRenderer currentFont = this.defaultFont;
                    double width = 0.0D;
                    boolean randomCase = false;
                    boolean bold = false;
                    boolean italic = false;
                    boolean strikeThrough = false;
                    boolean underline = false;
                    Iterable $this$forEachIndexed$iv = (Iterable) parts;
                    boolean $i$f$forEachIndexed = false;
                    int index$iv = 0;
                    Iterator iterator = $this$forEachIndexed$iv.iterator();

                    while (iterator.hasNext()) {
                        Object item$iv = iterator.next();
                        int k = index$iv++;
                        boolean flag = false;

                        if (k < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }

                        String part = (String) item$iv;
                        boolean $i$a$-forEachIndexed-GameFontRenderer$drawText$1 = false;
                        CharSequence words = (CharSequence) part;
                        boolean type = false;

                        if (words.length() != 0) {
                            if (k == 0) {
                                currentFont.drawString(part, width, 0.0D, j);
                                width += (double) currentFont.getStringWidth(part);
                            } else {
                                byte colorIndex = 1;
                                boolean flag1 = false;

                                if (part == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                }

                                String s;
                                label110: {
                                    String s1 = part.substring(colorIndex);

                                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).substring(startIndex)");
                                    s = s1;
                                    char c0 = part.charAt(0);
                                    int l = GameFontRenderer.Companion.getColorIndex(c0);

                                    if (0 <= l) {
                                        if (15 >= l) {
                                            if (!ignoreColor) {
                                                j = ColorUtils.hexColors[l] | alpha << 24;
                                                if (rainbow) {
                                                    GL20.glUseProgram(0);
                                                }
                                            }

                                            bold = false;
                                            italic = false;
                                            randomCase = false;
                                            underline = false;
                                            strikeThrough = false;
                                            break label110;
                                        }
                                    }

                                    if (l == 16) {
                                        randomCase = true;
                                    } else if (l == 17) {
                                        bold = true;
                                    } else if (l == 18) {
                                        strikeThrough = true;
                                    } else if (l == 19) {
                                        underline = true;
                                    } else if (l == 20) {
                                        italic = true;
                                    } else if (l == 21) {
                                        j = color;
                                        if ((color & -67108864) == 0) {
                                            j = color | -16777216;
                                        }

                                        if (rainbow) {
                                            GL20.glUseProgram(i);
                                        }

                                        bold = false;
                                        italic = false;
                                        randomCase = false;
                                        underline = false;
                                        strikeThrough = false;
                                    }
                                }

                                currentFont = bold && italic ? this.boldItalicFont : (bold ? this.boldFont : (italic ? this.italicFont : this.defaultFont));
                                currentFont.drawString(randomCase ? ColorUtils.INSTANCE.randomMagicText(s) : s, width, 0.0D, j);
                                if (strikeThrough) {
                                    RenderUtils.drawLine(width / 2.0D + (double) 1, (double) currentFont.getHeight() / 3.0D, (width + (double) currentFont.getStringWidth(s)) / 2.0D + (double) 1, (double) currentFont.getHeight() / 3.0D, (float) this.fontHeight / 16.0F);
                                }

                                if (underline) {
                                    RenderUtils.drawLine(width / 2.0D + (double) 1, (double) currentFont.getHeight() / 2.0D, (width + (double) currentFont.getStringWidth(s)) / 2.0D + (double) 1, (double) currentFont.getHeight() / 2.0D, (float) this.fontHeight / 16.0F);
                                }

                                width += (double) currentFont.getStringWidth(s);
                            }
                        }
                    }
                } else {
                    this.defaultFont.drawString(text, 0.0D, 0.0D, j);
                }

                WrapperImpl.INSTANCE.getClassProvider().getGlStateManager().disableBlend();
                GL11.glTranslated(-((double) x - 1.5D), -((double) y + 0.5D), 0.0D);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                return (int) (x + (float) this.getStringWidth(text));
            }
        }
    }

    static int drawText$default(GameFontRenderer gamefontrenderer, String s, float f, float f1, int i, boolean flag, boolean flag1, int j, Object object) {
        if ((j & 32) != 0) {
            flag1 = false;
        }

        return gamefontrenderer.drawText(s, f, f1, i, flag, flag1);
    }

    public int getColorCode(char charCode) {
        return ColorUtils.hexColors[GameFontRenderer.Companion.getColorIndex(charCode)];
    }

    public int getStringWidth(@Nullable String text) {
        TextEvent event = new TextEvent(text);

        LiquidBounce.INSTANCE.getEventManager().callEvent((Event) event);
        String s = event.getText();

        if (s == null) {
            return 0;
        } else {
            String currentText = s;
            int i;

            if (StringsKt.contains$default((CharSequence) currentText, (CharSequence) "§", false, 2, (Object) null)) {
                List parts = StringsKt.split$default((CharSequence) currentText, new String[] { "§"}, false, 0, 6, (Object) null);
                AWTFontRenderer currentFont = this.defaultFont;
                int width = 0;
                boolean bold = false;
                boolean italic = false;
                Iterable $this$forEachIndexed$iv = (Iterable) parts;
                boolean $i$f$forEachIndexed = false;
                int index$iv = 0;
                Iterator iterator = $this$forEachIndexed$iv.iterator();

                while (iterator.hasNext()) {
                    Object item$iv = iterator.next();
                    int j = index$iv++;
                    boolean flag = false;

                    if (j < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }

                    String part = (String) item$iv;
                    boolean $i$a$-forEachIndexed-GameFontRenderer$getStringWidth$1 = false;
                    CharSequence words = (CharSequence) part;
                    boolean type = false;

                    if (words.length() != 0) {
                        if (j == 0) {
                            width += currentFont.getStringWidth(part);
                        } else {
                            byte colorIndex = 1;
                            boolean flag1 = false;

                            if (part == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s = part.substring(colorIndex);
                            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).substring(startIndex)");
                            String s1 = s;
                            char c0 = part.charAt(0);
                            int k = GameFontRenderer.Companion.getColorIndex(c0);

                            if (k < 16) {
                                bold = false;
                                italic = false;
                            } else if (k == 17) {
                                bold = true;
                            } else if (k == 20) {
                                italic = true;
                            } else if (k == 21) {
                                bold = false;
                                italic = false;
                            }

                            currentFont = bold && italic ? this.boldItalicFont : (bold ? this.boldFont : (italic ? this.italicFont : this.defaultFont));
                            width += currentFont.getStringWidth(s1);
                        }
                    }
                }

                i = width / 2;
            } else {
                i = this.defaultFont.getStringWidth(currentText) / 2;
            }

            return i;
        }
    }

    public int getCharWidth(char character) {
        return this.getStringWidth(String.valueOf(character));
    }

    public GameFontRenderer(@NotNull Font font) {
        Intrinsics.checkParameterIsNotNull(font, "font");
        super();
        this.defaultFont = new AWTFontRenderer(font, 0, 0, false, 14, (DefaultConstructorMarker) null);
        Font font = font.deriveFont(1);

        Intrinsics.checkExpressionValueIsNotNull(font, "font.deriveFont(Font.BOLD)");
        this.boldFont = new AWTFontRenderer(font, 0, 0, false, 14, (DefaultConstructorMarker) null);
        font = font.deriveFont(2);
        Intrinsics.checkExpressionValueIsNotNull(font, "font.deriveFont(Font.ITALIC)");
        this.italicFont = new AWTFontRenderer(font, 0, 0, false, 14, (DefaultConstructorMarker) null);
        font = font.deriveFont(3);
        Intrinsics.checkExpressionValueIsNotNull(font, "font.deriveFont(Font.BOLD or Font.ITALIC)");
        this.boldItalicFont = new AWTFontRenderer(font, 0, 0, false, 14, (DefaultConstructorMarker) null);
        this.fontHeight = this.getHeight();
    }

    @JvmStatic
    public static final int getColorIndex(char type) {
        return GameFontRenderer.Companion.getColorIndex(type);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer$Companion;", "", "()V", "getColorIndex", "", "type", "", "LiquidBounce"}
    )
    public static final class Companion {

        @JvmStatic
        public final int getColorIndex(char type) {
            int i;

            if (48 <= type) {
                if (57 >= type) {
                    i = type - 48;
                    return i;
                }
            }

            if (97 <= type) {
                if (102 >= type) {
                    i = type - 97 + 10;
                    return i;
                }
            }

            if (107 <= type) {
                if (111 >= type) {
                    i = type - 107 + 16;
                    return i;
                }
            }

            i = type == 114 ? 21 : -1;
            return i;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
