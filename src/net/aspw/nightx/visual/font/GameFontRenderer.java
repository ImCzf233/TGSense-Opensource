package net.aspw.nightx.visual.font;

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
import me.utils.ClassUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 02\u00020\u0001:\u00010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J&\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ.\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 J&\u0010!\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ0\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 H\u0016J(\u0010#\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eH\u0016J2\u0010$\u001a\u00020\u000e2\b\u0010\"\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020 H\u0002J\u0010\u0010\'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020)H\u0016J\u0010\u0010,\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001aH\u0016J\u0010\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020/H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010¨\u00061"},
    d2 = { "Lnet/aspw/nightx/visual/font/GameFontRenderer;", "Lnet/minecraft/client/gui/FontRenderer;", "font", "Ljava/awt/Font;", "(Ljava/awt/Font;)V", "boldFont", "Lnet/aspw/nightx/visual/font/AWTFontRenderer;", "boldItalicFont", "defaultFont", "getDefaultFont", "()Lnet/aspw/nightx/visual/font/AWTFontRenderer;", "setDefaultFont", "(Lnet/aspw/nightx/visual/font/AWTFontRenderer;)V", "height", "", "getHeight", "()I", "italicFont", "size", "getSize", "bindTexture", "", "location", "Lnet/minecraft/util/ResourceLocation;", "drawCenteredString", "s", "", "x", "", "y", "color", "shadow", "", "drawString", "text", "drawStringWithShadow", "drawText", "colorHex", "ignoreColor", "getCharWidth", "character", "", "getColorCode", "charCode", "getStringWidth", "onResourceManagerReload", "resourceManager", "Lnet/minecraft/client/resources/IResourceManager;", "Companion", "LiquidBounce"}
)
public final class GameFontRenderer extends FontRenderer {

    @NotNull
    private AWTFontRenderer defaultFont;
    private AWTFontRenderer boldFont;
    private AWTFontRenderer italicFont;
    private AWTFontRenderer boldItalicFont;
    public static final GameFontRenderer.Companion Companion = new GameFontRenderer.Companion((DefaultConstructorMarker) null);

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

    public final int drawString(@NotNull String s, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return this.drawString(s, x, y, color, false);
    }

    public int drawStringWithShadow(@NotNull String text, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return this.drawString(text, x, y, color, true);
    }

    public final int drawCenteredString(@NotNull String s, float x, float y, int color, boolean shadow) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return this.drawString(s, x - (float) this.getStringWidth(s) / 2.0F, y, color, shadow);
    }

    public final int drawCenteredString(@NotNull String s, float x, float y, int color) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        return this.drawStringWithShadow(s, x - (float) this.getStringWidth(s) / 2.0F, y, color);
    }

    public int drawString(@NotNull String text, float x, float y, int color, boolean shadow) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        TextEvent event = new TextEvent(text);

        LiquidBounce.INSTANCE.getEventManager().callEvent((Event) event);
        String s = event.getText();

        if (s != null) {
            String currentText = s;
            float currY = y - 3.0F;

            if (shadow) {
                this.drawText(currentText, x + 1.0F, currY + 1.0F, (new Color(0, 0, 0, 150)).getRGB(), true);
            }

            return this.drawText(currentText, x, currY, color, false);
        } else {
            return 0;
        }
    }

    private final int drawText(String text, float x, float y, int colorHex, boolean ignoreColor) {
        if (text == null) {
            return 0;
        } else {
            CharSequence hexColor = (CharSequence) text;
            boolean alpha = false;
            boolean parts = false;

            if (hexColor.length() == 0) {
                return (int) x;
            } else {
                GlStateManager.translate((double) x - 1.5D, (double) y + 0.5D, 0.0D);
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.enableTexture2D();
                int i = colorHex;

                if ((colorHex & -67108864) == 0) {
                    i = colorHex | -16777216;
                }

                int j = i >> 24 & 255;

                if (StringsKt.contains$default((CharSequence) text, (CharSequence) "§", false, 2, (Object) null)) {
                    List list = StringsKt.split$default((CharSequence) text, new String[] { "§"}, false, 0, 6, (Object) null);
                    AWTFontRenderer currentFont = this.defaultFont;
                    double width = 0.0D;
                    boolean randomCase = false;
                    boolean bold = false;
                    boolean italic = false;
                    boolean strikeThrough = false;
                    boolean underline = false;
                    Iterable $this$forEachIndexed$iv = (Iterable) list;
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
                                currentFont.drawString(part, width, 0.0D, i);
                                width += (double) currentFont.getStringWidth(part);
                            } else {
                                byte colorIndex = 1;
                                boolean flag1 = false;

                                if (part == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                }

                                String s;
                                label100: {
                                    String s1 = part.substring(colorIndex);

                                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).substring(startIndex)");
                                    s = s1;
                                    char c0 = part.charAt(0);
                                    int l = GameFontRenderer.Companion.getColorIndex(c0);

                                    if (0 <= l) {
                                        if (15 >= l) {
                                            if (!ignoreColor) {
                                                i = ColorUtils.hexColors[l] | j << 24;
                                            }

                                            bold = false;
                                            italic = false;
                                            randomCase = false;
                                            underline = false;
                                            strikeThrough = false;
                                            break label100;
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
                                        i = colorHex;
                                        if ((colorHex & -67108864) == 0) {
                                            i = colorHex | -16777216;
                                        }

                                        bold = false;
                                        italic = false;
                                        randomCase = false;
                                        underline = false;
                                        strikeThrough = false;
                                    }
                                }

                                currentFont = bold && italic ? this.boldItalicFont : (bold ? this.boldFont : (italic ? this.italicFont : this.defaultFont));
                                currentFont.drawString(randomCase ? ColorUtils.INSTANCE.randomMagicText(s) : s, width, 0.0D, i);
                                if (strikeThrough) {
                                    RenderUtils.drawLine(width / 2.0D + (double) 1, (double) currentFont.getHeight() / 3.0D, (width + (double) currentFont.getStringWidth(s)) / 2.0D + (double) 1, (double) currentFont.getHeight() / 3.0D, (float) this.FONT_HEIGHT / 16.0F);
                                }

                                if (underline) {
                                    RenderUtils.drawLine(width / 2.0D + (double) 1, (double) currentFont.getHeight() / 2.0D, (width + (double) currentFont.getStringWidth(s)) / 2.0D + (double) 1, (double) currentFont.getHeight() / 2.0D, (float) this.FONT_HEIGHT / 16.0F);
                                }

                                width += (double) currentFont.getStringWidth(s);
                            }
                        }
                    }
                } else {
                    this.defaultFont.drawString(text, 0.0D, 0.0D, i);
                }

                GlStateManager.disableBlend();
                GlStateManager.translate(-((double) x - 1.5D), -((double) y + 0.5D), 0.0D);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                return (int) (x + (float) this.getStringWidth(text));
            }
        }
    }

    public int getColorCode(char charCode) {
        return ColorUtils.hexColors[GameFontRenderer.Companion.getColorIndex(charCode)];
    }

    public int getStringWidth(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
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

    public void onResourceManagerReload(@NotNull IResourceManager resourceManager) {
        Intrinsics.checkParameterIsNotNull(resourceManager, "resourceManager");
    }

    protected void bindTexture(@Nullable ResourceLocation location) {}

    public GameFontRenderer(@NotNull Font font) {
        Intrinsics.checkParameterIsNotNull(font, "font");
        GameSettings gamesettings = Minecraft.getMinecraft().gameSettings;
        ResourceLocation resourcelocation = new ResourceLocation("textures/font/ascii.png");
        TextureManager texturemanager;

        if (ClassUtils.INSTANCE.hasForge()) {
            texturemanager = null;
        } else {
            Minecraft minecraft = Minecraft.getMinecraft();

            Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
            texturemanager = minecraft.getTextureManager();
        }

        super(gamesettings, resourcelocation, texturemanager, false);
        this.defaultFont = new AWTFontRenderer(font, 0, 0, 6, (DefaultConstructorMarker) null);
        Font font = font.deriveFont(1);

        Intrinsics.checkExpressionValueIsNotNull(font, "font.deriveFont(Font.BOLD)");
        this.boldFont = new AWTFontRenderer(font, 0, 0, 6, (DefaultConstructorMarker) null);
        font = font.deriveFont(2);
        Intrinsics.checkExpressionValueIsNotNull(font, "font.deriveFont(Font.ITALIC)");
        this.italicFont = new AWTFontRenderer(font, 0, 0, 6, (DefaultConstructorMarker) null);
        font = font.deriveFont(3);
        Intrinsics.checkExpressionValueIsNotNull(font, "font.deriveFont(Font.BOLD or Font.ITALIC)");
        this.boldItalicFont = new AWTFontRenderer(font, 0, 0, 6, (DefaultConstructorMarker) null);
        this.FONT_HEIGHT = this.getHeight();
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
        d2 = { "Lnet/aspw/nightx/visual/font/GameFontRenderer$Companion;", "", "()V", "getColorIndex", "", "type", "", "LiquidBounce"}
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
