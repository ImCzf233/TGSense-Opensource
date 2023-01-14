package me.ui.Fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class FluxFont {

    public int IMAGE_WIDTH = 1024;
    public int IMAGE_HEIGHT = 1024;
    private int texID;
    private FluxFont.IntObject[] chars = new FluxFont.IntObject[2048];
    private final Font font;
    private boolean antiAlias;
    private int fontHeight = -1;
    private int charOffset = 8;

    public FluxFont(Font font, boolean antiAlias, int charOffset, boolean allChar, int yAddon) {
        this.font = font;
        this.antiAlias = antiAlias;
        this.charOffset = charOffset;
        if (charOffset == 0) {
            this.charOffset = 8;
        }

        this.setupTexture(antiAlias, allChar, yAddon);
    }

    public FluxFont(Font font, boolean antiAlias, boolean allChar, int yAddon) {
        this.font = font;
        this.antiAlias = antiAlias;
        this.charOffset = 6;
        this.setupTexture(antiAlias, allChar, yAddon);
    }

    private void setupTexture(boolean antiAlias, boolean allChar, int yAddon) {
        if (this.font.getSize() <= 15) {
            this.IMAGE_WIDTH = 256;
            this.IMAGE_HEIGHT = 256;
        }

        if (this.font.getSize() <= 43) {
            this.IMAGE_WIDTH = 512;
            this.IMAGE_HEIGHT = 512;
        } else if (this.font.getSize() <= 91) {
            this.IMAGE_WIDTH = 1024;
            this.IMAGE_HEIGHT = 1024;
        } else {
            this.IMAGE_WIDTH = 2048;
            this.IMAGE_HEIGHT = 2048;
        }

        if (allChar) {
            this.chars = new FluxFont.IntObject['\uffff'];
            this.IMAGE_WIDTH = 8192;
            this.IMAGE_HEIGHT = 8192;
        }

        BufferedImage img = new BufferedImage(this.IMAGE_WIDTH, this.IMAGE_HEIGHT, 2);
        Graphics2D g = (Graphics2D) img.getGraphics();

        g.setFont(this.font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, this.IMAGE_WIDTH, this.IMAGE_HEIGHT);
        g.setColor(Color.white);
        int rowHeight = 0;
        int positionX = 0;
        int positionY = 0;

        for (int e = 0; e < this.chars.length; ++e) {
            char ch = (char) e;
            BufferedImage fontImage = this.getFontImage(ch, antiAlias, yAddon);
            FluxFont.IntObject newIntObject = new FluxFont.IntObject(null);

            newIntObject.width = fontImage.getWidth();
            newIntObject.height = fontImage.getHeight();
            if (positionX + newIntObject.width >= this.IMAGE_WIDTH) {
                positionX = 0;
                positionY += rowHeight;
                rowHeight = 0;
            }

            newIntObject.storedX = positionX;
            newIntObject.storedY = positionY;
            if (newIntObject.height > this.fontHeight) {
                this.fontHeight = newIntObject.height;
            }

            if (newIntObject.height > rowHeight) {
                rowHeight = newIntObject.height;
            }

            this.chars[e] = newIntObject;
            g.drawImage(fontImage, positionX, positionY, (ImageObserver) null);
            positionX += newIntObject.width;
        }

        try {
            this.texID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), img, false, false);
        } catch (NullPointerException nullpointerexception) {
            nullpointerexception.printStackTrace();
        }

    }

    private BufferedImage getFontImage(char ch, boolean antiAlias, int yAddon) {
        BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
        Graphics2D g = (Graphics2D) tempfontImage.getGraphics();

        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }

        g.setFont(this.font);
        FontMetrics fontMetrics = g.getFontMetrics();
        int charwidth = fontMetrics.charWidth(ch) + 7;

        if (charwidth <= 0) {
            charwidth = 7;
        }

        int charheight = fontMetrics.getHeight() + 1 + yAddon;

        if (charheight <= 0) {
            charheight = this.font.getSize();
        }

        BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
        Graphics2D gt = (Graphics2D) fontImage.getGraphics();

        if (antiAlias) {
            gt.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            gt.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }

        gt.setFont(this.font);
        gt.setColor(Color.WHITE);
        boolean charx = true;
        boolean chary = true;

        gt.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
        return fontImage;
    }

    public void drawChar(char c, float x, float y) throws ArrayIndexOutOfBoundsException {
        try {
            this.drawQuad(x, y, (float) this.chars[c].width, (float) this.chars[c].height, (float) this.chars[c].storedX, (float) this.chars[c].storedY, (float) this.chars[c].width, (float) this.chars[c].height);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / (float) this.IMAGE_WIDTH;
        float renderSRCY = srcY / (float) this.IMAGE_HEIGHT;
        float renderSRCWidth = srcWidth / (float) this.IMAGE_WIDTH;
        float renderSRCHeight = srcHeight / (float) this.IMAGE_HEIGHT;

        GL11.glBegin(4);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d((double) (x + width), (double) y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d((double) x, (double) (y + height));
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d((double) x, (double) (y + height));
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2d((double) (x + width), (double) (y + height));
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d((double) (x + width), (double) y);
        GL11.glEnd();
    }

    public void drawString(String text, double x, double y, Color color, boolean shadow) {
        x -= 3.0D;
        --y;
        GL11.glPushMatrix();
        GlStateManager.bindTexture(this.texID);
        this.glColor(shadow ? new Color(0.05F, 0.05F, 0.05F, (float) color.getAlpha() / 255.0F) : color);
        int size = text.length();

        for (int indexInString = 0; indexInString < size; ++indexInString) {
            char character = text.charAt(indexInString);

            if (character < this.chars.length && character >= 0) {
                this.drawChar(character, (float) x, (float) y);
                x += (double) (this.chars[character].width - this.charOffset);
            }
        }

        GL11.glPopMatrix();
    }

    public void drawString(String text, double x, double y, int color, boolean shadow) {
        x -= 3.0D;
        --y;
        GL11.glPushMatrix();
        GlStateManager.bindTexture(this.texID);
        RenderUtils.color(color);
        int size = text.length();

        for (int indexInString = 0; indexInString < size; ++indexInString) {
            char character = text.charAt(indexInString);

            if (character < this.chars.length && character >= 0) {
                this.drawChar(character, (float) x, (float) y);
                x += (double) (this.chars[character].width - this.charOffset);
            }
        }

        GL11.glPopMatrix();
    }

    public void glColor(Color color) {
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;
        float alpha = (float) color.getAlpha() / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
    }

    public int getHeight(String text) {
        int lines = 1;
        char[] achar = text.toCharArray();
        int i = achar.length;

        for (int j = 0; j < i; ++j) {
            char c = achar[j];

            if (c == 10) {
                ++lines;
            }
        }

        return this.fontHeight * lines;
    }

    public int getHeight() {
        return this.fontHeight;
    }

    public int getWidth(String text) {
        int width = 0;
        char[] achar = text.toCharArray();
        int i = achar.length;

        for (int j = 0; j < i; ++j) {
            char c = achar[j];

            if (c < this.chars.length && c >= 0) {
                width += this.chars[c].width - this.charOffset;
            }
        }

        return width;
    }

    public boolean isAntiAlias() {
        return this.antiAlias;
    }

    public void setAntiAlias(boolean antiAlias, boolean allChar, int yAddon) {
        if (this.antiAlias != antiAlias) {
            this.antiAlias = antiAlias;
            this.setupTexture(antiAlias, allChar, yAddon);
        }

    }

    public Font getFont() {
        return this.font;
    }

    private static class IntObject {

        public int width;
        public int height;
        public int storedX;
        public int storedY;

        private IntObject() {}

        IntObject(Object x0) {
            this();
        }
    }
}
