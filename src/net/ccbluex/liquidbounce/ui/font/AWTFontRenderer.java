package net.ccbluex.liquidbounce.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\b\u0007\u0018\u0000 42\u00020\u0001:\u000234B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010 \u001a\u00020!H\u0002J\u0006\u0010\"\u001a\u00020!J \u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020&H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J&\u0010,\u001a\u00020!2\u0006\u0010-\u001a\u00020\f2\u0006\u0010%\u001a\u00020.2\u0006\u0010\'\u001a\u00020.2\u0006\u0010/\u001a\u00020\u0005J\u0006\u00100\u001a\u00020!J\u000e\u00101\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\fJ\u0018\u00102\u001a\u00020!2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002R*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0010X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "font", "Ljava/awt/Font;", "startChar", "", "stopChar", "loadingScreen", "", "(Ljava/awt/Font;IIZ)V", "cachedStrings", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "Lkotlin/collections/HashMap;", "charLocations", "", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "[Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "getFont", "()Ljava/awt/Font;", "fontHeight", "height", "getHeight", "()I", "getLoadingScreen", "()Z", "setLoadingScreen", "(Z)V", "textureHeight", "textureID", "textureWidth", "collectGarbage", "", "delete", "drawChar", "char", "x", "", "y", "drawCharToImage", "Ljava/awt/image/BufferedImage;", "ch", "", "drawString", "text", "", "color", "finalize", "getStringWidth", "renderBitmap", "CharLocation", "Companion", "LiquidBounce"}
)
public final class AWTFontRenderer extends MinecraftInstance {

    private int fontHeight;
    private final AWTFontRenderer.CharLocation[] charLocations;
    private final HashMap cachedStrings;
    private int textureID;
    private int textureWidth;
    private int textureHeight;
    @NotNull
    private final Font font;
    private boolean loadingScreen;
    private static boolean assumeNonVolatile;
    @NotNull
    private static final ArrayList activeFontRenderers = new ArrayList();
    private static int gcTicks;
    private static final int GC_TICKS = 600;
    private static final int CACHED_FONT_REMOVAL_TIME = 30000;
    public static final AWTFontRenderer.Companion Companion = new AWTFontRenderer.Companion((DefaultConstructorMarker) null);

    private final void collectGarbage() {
        long currentTime = System.currentTimeMillis();
        Map $this$forEach$iv = (Map) this.cachedStrings;
        boolean $i$f$forEach = false;
        Map destination$iv$iv = (Map) (new LinkedHashMap());
        boolean $i$f$filterTo = false;
        boolean it = false;
        Iterator $i$a$-forEach-AWTFontRenderer$collectGarbage$2 = $this$forEach$iv.entrySet().iterator();

        while ($i$a$-forEach-AWTFontRenderer$collectGarbage$2.hasNext()) {
            Entry element$iv$iv = (Entry) $i$a$-forEach-AWTFontRenderer$collectGarbage$2.next();
            boolean $i$a$-filter-AWTFontRenderer$collectGarbage$1 = false;

            if (currentTime - ((CachedFont) element$iv$iv.getValue()).getLastUsage() > (long) 30000) {
                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
        }

        $i$f$forEach = false;
        Map $this$filterTo$iv$iv = destination$iv$iv;
        boolean destination$iv$iv1 = false;
        Iterator $i$f$filterTo1 = $this$filterTo$iv$iv.entrySet().iterator();

        while ($i$f$filterTo1.hasNext()) {
            Entry element$iv = (Entry) $i$f$filterTo1.next();
            boolean $i$a$-forEach-AWTFontRenderer$collectGarbage$21 = false;

            GL11.glDeleteLists(((CachedFont) element$iv.getValue()).getDisplayList(), 1);
            ((CachedFont) element$iv.getValue()).setDeleted(true);
            this.cachedStrings.remove(element$iv.getKey());
        }

    }

    public final int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public final void drawString(@NotNull String text, double x, double y, int color) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        double scale = 0.25D;
        double reverse = (double) 1 / scale;

        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);
        GL11.glTranslated(x * (double) 2.0F, y * 2.0D - 2.0D, 0.0D);
        if (this.loadingScreen) {
            GL11.glBindTexture(3553, this.textureID);
        } else {
            MinecraftInstance.classProvider.getGlStateManager().bindTexture(this.textureID);
        }

        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        float alpha = (float) (color >> 24 & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
        double currX = 0.0D;
        CachedFont cached = (CachedFont) this.cachedStrings.get(text);

        if (cached != null) {
            GL11.glCallList(cached.getDisplayList());
            cached.setLastUsage(System.currentTimeMillis());
            GL11.glPopMatrix();
        } else {
            int list = -1;

            if (AWTFontRenderer.assumeNonVolatile) {
                list = GL11.glGenLists(1);
                GL11.glNewList(list, 4865);
            }

            GL11.glBegin(7);
            boolean fontChar = false;
            char[] achar = text.toCharArray();

            Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
            char[] achar1 = achar;
            int i = achar1.length;

            for (int j = 0; j < i; ++j) {
                char char = achar1[j];

                if (char >= this.charLocations.length) {
                    GL11.glEnd();
                    GL11.glScaled(reverse, reverse, reverse);
                    MinecraftInstance.mc.getFontRendererObj().drawString(String.valueOf(char), (float) currX * (float) scale + (float) 1, 2.0F, color, false);
                    currX += (double) MinecraftInstance.mc.getFontRendererObj().getStringWidth(String.valueOf(char)) * reverse;
                    GL11.glScaled(scale, scale, scale);
                    if (this.loadingScreen) {
                        GL11.glBindTexture(3553, this.textureID);
                    } else {
                        MinecraftInstance.classProvider.getGlStateManager().bindTexture(this.textureID);
                    }

                    GL11.glColor4f(red, green, blue, alpha);
                    GL11.glBegin(7);
                } else {
                    AWTFontRenderer.CharLocation awtfontrenderer_charlocation = this.charLocations[char];

                    if (this.charLocations[char] != null) {
                        AWTFontRenderer.CharLocation awtfontrenderer_charlocation1 = awtfontrenderer_charlocation;

                        this.drawChar(awtfontrenderer_charlocation1, (float) currX, 0.0F);
                        currX += (double) awtfontrenderer_charlocation1.getWidth() - 8.0D;
                    }
                }
            }

            GL11.glEnd();
            if (AWTFontRenderer.assumeNonVolatile) {
                ((Map) this.cachedStrings).put(text, new CachedFont(list, System.currentTimeMillis(), false, 4, (DefaultConstructorMarker) null));
                GL11.glEndList();
            }

            GL11.glPopMatrix();
        }
    }

    private final void drawChar(AWTFontRenderer.CharLocation char, float x, float y) {
        float width = (float) char.getWidth();
        float height = (float) char.getHeight();
        float srcX = (float) char.getX();
        float srcY = (float) char.getY();
        float renderX = srcX / (float) this.textureWidth;
        float renderY = srcY / (float) this.textureHeight;
        float renderWidth = width / (float) this.textureWidth;
        float renderHeight = height / (float) this.textureHeight;

        GL11.glTexCoord2f(renderX, renderY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderX, renderY + renderHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY);
        GL11.glVertex2f(x + width, y);
    }

    private final void renderBitmap(int startChar, int stopChar) {
        BufferedImage[] fontImages = new BufferedImage[stopChar];
        int rowHeight = 0;
        int charX = 0;
        int charY = 0;
        int bufferedImage = startChar;

        for (int graphics2D = stopChar; bufferedImage < graphics2D; ++bufferedImage) {
            BufferedImage targetChar = this.drawCharToImage((char) bufferedImage);
            AWTFontRenderer.CharLocation fontChar = new AWTFontRenderer.CharLocation(charX, charY, targetChar.getWidth(), targetChar.getHeight());

            if (fontChar.getHeight() > this.fontHeight) {
                this.fontHeight = fontChar.getHeight();
            }

            if (fontChar.getHeight() > rowHeight) {
                rowHeight = fontChar.getHeight();
            }

            this.charLocations[bufferedImage] = fontChar;
            fontImages[bufferedImage] = targetChar;
            charX += fontChar.getWidth();
            if (charX > 2048) {
                if (charX > this.textureWidth) {
                    this.textureWidth = charX;
                }

                charX = 0;
                charY += rowHeight;
                rowHeight = 0;
            }
        }

        this.textureHeight = charY + rowHeight;
        BufferedImage bufferedimage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
        Graphics graphics = bufferedimage.getGraphics();

        if (graphics == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
        } else {
            Graphics2D graphics2d = (Graphics2D) graphics;

            graphics2d.setFont(this.font);
            graphics2d.setColor(new Color(255, 255, 255, 0));
            graphics2d.fillRect(0, 0, this.textureWidth, this.textureHeight);
            graphics2d.setColor(Color.white);
            int i = startChar;

            for (int j = stopChar; i < j; ++i) {
                if (fontImages[i] != null && this.charLocations[i] != null) {
                    Image image = (Image) fontImages[i];
                    AWTFontRenderer.CharLocation awtfontrenderer_charlocation = this.charLocations[i];

                    if (this.charLocations[i] == null) {
                        Intrinsics.throwNpe();
                    }

                    int k = awtfontrenderer_charlocation.getX();
                    AWTFontRenderer.CharLocation awtfontrenderer_charlocation1 = this.charLocations[i];

                    if (this.charLocations[i] == null) {
                        Intrinsics.throwNpe();
                    }

                    graphics2d.drawImage(image, k, awtfontrenderer_charlocation1.getY(), (ImageObserver) null);
                }
            }

            this.textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), bufferedimage, true, true);
        }
    }

    private final BufferedImage drawCharToImage(char ch) {
        Graphics graphics = (new BufferedImage(1, 1, 2)).getGraphics();

        if (graphics == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
        } else {
            Graphics2D graphics2D = (Graphics2D) graphics;

            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics2D.setFont(this.font);
            FontMetrics fontMetrics = graphics2D.getFontMetrics();
            int charWidth = fontMetrics.charWidth(ch) + 8;

            if (charWidth <= 0) {
                charWidth = 7;
            }

            Intrinsics.checkExpressionValueIsNotNull(fontMetrics, "fontMetrics");
            int charHeight = fontMetrics.getHeight() + 3;

            if (charHeight <= 0) {
                charHeight = this.font.getSize();
            }

            BufferedImage fontImage = new BufferedImage(charWidth, charHeight, 2);

            graphics = fontImage.getGraphics();
            if (graphics == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
            } else {
                Graphics2D graphics = (Graphics2D) graphics;

                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setFont(this.font);
                graphics.setColor(Color.WHITE);
                graphics.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
                return fontImage;
            }
        }
    }

    public final int getStringWidth(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        int width = 0;
        boolean fontChar = false;
        char[] achar = text.toCharArray();

        Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
        char[] achar1 = achar;
        int i = achar1.length;

        for (int j = 0; j < i; ++j) {
            char c = achar1[j];
            AWTFontRenderer.CharLocation awtfontrenderer_charlocation = this.charLocations[c < this.charLocations.length ? c : 3];

            if (this.charLocations[c < this.charLocations.length ? c : 3] != null) {
                AWTFontRenderer.CharLocation awtfontrenderer_charlocation1 = awtfontrenderer_charlocation;

                width += awtfontrenderer_charlocation1.getWidth() - 8;
            }
        }

        return width / 2;
    }

    public final void delete() {
        if (this.textureID != -1) {
            GL11.glDeleteTextures(this.textureID);
            this.textureID = -1;
        }

        AWTFontRenderer.activeFontRenderers.remove(this);
    }

    public final void finalize() {
        this.delete();
    }

    @NotNull
    public final Font getFont() {
        return this.font;
    }

    public final boolean getLoadingScreen() {
        return this.loadingScreen;
    }

    public final void setLoadingScreen(boolean <set-?>) {
        this.loadingScreen = <set-?>;
    }

    public AWTFontRenderer(@NotNull Font font, int startChar, int stopChar, boolean loadingScreen) {
        Intrinsics.checkParameterIsNotNull(font, "font");
        super();
        this.font = font;
        this.loadingScreen = loadingScreen;
        this.fontHeight = -1;
        this.charLocations = new AWTFontRenderer.CharLocation[stopChar];
        this.cachedStrings = new HashMap();
        this.textureID = -1;
        this.renderBitmap(startChar, stopChar);
        AWTFontRenderer.activeFontRenderers.add(this);
    }

    public AWTFontRenderer(Font font, int i, int j, boolean flag, int k, DefaultConstructorMarker defaultconstructormarker) {
        if ((k & 2) != 0) {
            i = 0;
        }

        if ((k & 4) != 0) {
            j = 255;
        }

        if ((k & 8) != 0) {
            flag = false;
        }

        this(font, i, j, flag);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001d"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "", "x", "", "y", "width", "height", "(IIII)V", "getHeight", "()I", "setHeight", "(I)V", "getWidth", "setWidth", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "LiquidBounce"}
    )
    private static final class CharLocation {

        private int x;
        private int y;
        private int width;
        private int height;

        public final int getX() {
            return this.x;
        }

        public final void setX(int <set-?>) {
            this.x = <set-?>;
        }

        public final int getY() {
            return this.y;
        }

        public final void setY(int <set-?>) {
            this.y = <set-?>;
        }

        public final int getWidth() {
            return this.width;
        }

        public final void setWidth(int <set-?>) {
            this.width = <set-?>;
        }

        public final int getHeight() {
            return this.height;
        }

        public final void setHeight(int <set-?>) {
            this.height = <set-?>;
        }

        public CharLocation(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public final int component1() {
            return this.x;
        }

        public final int component2() {
            return this.y;
        }

        public final int component3() {
            return this.width;
        }

        public final int component4() {
            return this.height;
        }

        @NotNull
        public final AWTFontRenderer.CharLocation copy(int x, int y, int width, int height) {
            return new AWTFontRenderer.CharLocation(x, y, width, height);
        }

        public static AWTFontRenderer.CharLocation copy$default(AWTFontRenderer.CharLocation awtfontrenderer_charlocation, int i, int j, int k, int l, int i1, Object object) {
            if ((i1 & 1) != 0) {
                i = awtfontrenderer_charlocation.x;
            }

            if ((i1 & 2) != 0) {
                j = awtfontrenderer_charlocation.y;
            }

            if ((i1 & 4) != 0) {
                k = awtfontrenderer_charlocation.width;
            }

            if ((i1 & 8) != 0) {
                l = awtfontrenderer_charlocation.height;
            }

            return awtfontrenderer_charlocation.copy(i, j, k, l);
        }

        @NotNull
        public String toString() {
            return "CharLocation(x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ")";
        }

        public int hashCode() {
            return ((Integer.hashCode(this.x) * 31 + Integer.hashCode(this.y)) * 31 + Integer.hashCode(this.width)) * 31 + Integer.hashCode(this.height);
        }

        public boolean equals(@Nullable Object object) {
            if (this != object) {
                if (object instanceof AWTFontRenderer.CharLocation) {
                    AWTFontRenderer.CharLocation awtfontrenderer_charlocation = (AWTFontRenderer.CharLocation) object;

                    if (this.x == awtfontrenderer_charlocation.x && this.y == awtfontrenderer_charlocation.y && this.width == awtfontrenderer_charlocation.width && this.height == awtfontrenderer_charlocation.height) {
                        return true;
                    }
                }

                return false;
            } else {
                return true;
            }
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$Companion;", "", "()V", "CACHED_FONT_REMOVAL_TIME", "", "GC_TICKS", "activeFontRenderers", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "Lkotlin/collections/ArrayList;", "getActiveFontRenderers", "()Ljava/util/ArrayList;", "assumeNonVolatile", "", "getAssumeNonVolatile", "()Z", "setAssumeNonVolatile", "(Z)V", "gcTicks", "garbageCollectionTick", "", "LiquidBounce"}
    )
    public static final class Companion {

        public final boolean getAssumeNonVolatile() {
            return AWTFontRenderer.assumeNonVolatile;
        }

        public final void setAssumeNonVolatile(boolean <set-?>) {
            AWTFontRenderer.assumeNonVolatile = <set-?>;
        }

        @NotNull
        public final ArrayList getActiveFontRenderers() {
            return AWTFontRenderer.activeFontRenderers;
        }

        public final void garbageCollectionTick() {
            int $this$forEach$iv;

            AWTFontRenderer.gcTicks = ($this$forEach$iv = AWTFontRenderer.gcTicks) + 1;
            if ($this$forEach$iv > 600) {
                Iterable $this$forEach$iv1 = (Iterable) ((AWTFontRenderer.Companion) this).getActiveFontRenderers();
                boolean $i$f$forEach = false;
                Iterator iterator = $this$forEach$iv1.iterator();

                while (iterator.hasNext()) {
                    Object element$iv = iterator.next();
                    AWTFontRenderer it = (AWTFontRenderer) element$iv;
                    boolean $i$a$-forEach-AWTFontRenderer$Companion$garbageCollectionTick$1 = false;

                    it.collectGarbage();
                }

                AWTFontRenderer.gcTicks = 0;
            }

        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
