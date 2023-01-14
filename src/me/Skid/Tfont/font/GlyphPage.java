package me.Skid.Tfont.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class GlyphPage {

    private int imgSize;
    private int maxFontHeight = -1;
    private Font font;
    private boolean antiAliasing;
    private boolean fractionalMetrics;
    private HashMap glyphCharacterMap = new HashMap();
    public int texID;
    private BufferedImage bufferedImage;

    public GlyphPage(Font font, boolean antiAliasing, boolean fractionalMetrics) {
        this.font = font;
        this.antiAliasing = antiAliasing;
        this.fractionalMetrics = fractionalMetrics;
    }

    public void generateGlyphPage(char[] chars, boolean allChars) {
        double maxWidth = -1.0D;
        double maxHeight = -1.0D;
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, this.antiAliasing, this.fractionalMetrics);
        int currentCharHeight;

        if (allChars) {
            this.imgSize = 8192;
        } else {
            char[] g = chars;
            int fontMetrics = chars.length;

            for (currentCharHeight = 0; currentCharHeight < fontMetrics; ++currentCharHeight) {
                char posX = g[currentCharHeight];
                Rectangle2D posY = this.font.getStringBounds(Character.toString(posX), fontRenderContext);

                if (maxWidth < posY.getWidth()) {
                    maxWidth = posY.getWidth();
                }

                if (maxHeight < posY.getHeight()) {
                    maxHeight = posY.getHeight();
                }
            }

            maxWidth += 2.0D;
            maxHeight += 2.0D;
            this.imgSize = (int) Math.ceil(Math.max(Math.ceil(Math.sqrt(maxWidth * maxWidth * (double) chars.length) / maxWidth), Math.ceil(Math.sqrt(maxHeight * maxHeight * (double) chars.length) / maxHeight)) * Math.max(maxWidth, maxHeight)) + 1;
        }

        this.bufferedImage = new BufferedImage(this.imgSize, this.imgSize, 2);
        Graphics2D graphics2d = (Graphics2D) this.bufferedImage.getGraphics();

        graphics2d.setFont(this.font);
        graphics2d.setColor(new Color(255, 255, 255, 0));
        graphics2d.fillRect(0, 0, this.imgSize, this.imgSize);
        graphics2d.setColor(Color.white);
        graphics2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        FontMetrics fontmetrics = graphics2d.getFontMetrics();

        currentCharHeight = 0;
        int i = 0;
        int j = 1;
        char[] ex = chars;
        int k = chars.length;

        for (int l = 0; l < k; ++l) {
            char ch2 = ex[l];
            GlyphPage.Glyph glyph = new GlyphPage.Glyph();
            Rectangle2D bounds2 = fontmetrics.getStringBounds(Character.toString(ch2), graphics2d);

            glyph.width = bounds2.getBounds().width + 8;
            glyph.height = bounds2.getBounds().height;
            if (i + glyph.width >= this.imgSize) {
                i = 0;
                j += currentCharHeight;
                currentCharHeight = 0;
            }

            glyph.x = i;
            glyph.y = j;
            if (glyph.height > this.maxFontHeight) {
                this.maxFontHeight = glyph.height;
            }

            if (glyph.height > currentCharHeight) {
                currentCharHeight = glyph.height;
            }

            graphics2d.drawString(Character.toString(ch2), i + 2, j + fontmetrics.getAscent());
            i += glyph.width;
            this.glyphCharacterMap.put(Character.valueOf(ch2), glyph);
        }

        try {
            this.texID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), this.bufferedImage, true, !allChars);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void bindTexture() {
        GlStateManager.bindTexture(this.texID);
    }

    public void unbindTexture() {
        GlStateManager.bindTexture(0);
    }

    public float drawChar(char ch, float x, float y) {
        GlyphPage.Glyph glyph = (GlyphPage.Glyph) this.glyphCharacterMap.get(Character.valueOf(ch));

        if (glyph == null) {
            throw new IllegalArgumentException("\'" + ch + "\' wasn\'t found");
        } else {
            float pageX = (float) glyph.x / (float) this.imgSize;
            float pageY = (float) glyph.y / (float) this.imgSize;
            float pageWidth = (float) glyph.width / (float) this.imgSize;
            float pageHeight = (float) glyph.height / (float) this.imgSize;
            float width = (float) glyph.width;
            float height = (float) glyph.height;

            GL11.glBegin(4);
            GL11.glTexCoord2f(pageX + pageWidth, pageY);
            GL11.glVertex2f(x + width, y);
            GL11.glTexCoord2f(pageX, pageY);
            GL11.glVertex2f(x, y);
            GL11.glTexCoord2f(pageX, pageY + pageHeight);
            GL11.glVertex2f(x, y + height);
            GL11.glTexCoord2f(pageX, pageY + pageHeight);
            GL11.glVertex2f(x, y + height);
            GL11.glTexCoord2f(pageX + pageWidth, pageY + pageHeight);
            GL11.glVertex2f(x + width, y + height);
            GL11.glTexCoord2f(pageX + pageWidth, pageY);
            GL11.glVertex2f(x + width, y);
            GL11.glEnd();
            return width - 8.0F;
        }
    }

    public float getWidth(char ch) {
        return (float) ((GlyphPage.Glyph) this.glyphCharacterMap.get(Character.valueOf(ch))).width;
    }

    public int getMaxFontHeight() {
        return this.maxFontHeight;
    }

    public boolean isAntiAliasingEnabled() {
        return this.antiAliasing;
    }

    public boolean isFractionalMetricsEnabled() {
        return this.fractionalMetrics;
    }

    static class Glyph {

        private int x;
        private int y;
        private int width;
        private int height;

        Glyph(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        Glyph() {}

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
