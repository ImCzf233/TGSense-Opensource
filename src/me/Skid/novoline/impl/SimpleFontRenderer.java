package me.Skid.novoline.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import me.Skid.novoline.api.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public final class SimpleFontRenderer implements FontRenderer {

    private static final int[] COLOR_CODES = setupMinecraftColorCodes();
    private static final String COLORS = "0123456789abcdefklmnor";
    private static final char COLOR_PREFIX = '§';
    private static final short CHARS = 256;
    private static final float IMG_SIZE = 512.0F;
    private static final float CHAR_OFFSET = 0.0F;
    private final SimpleFontRenderer.CharData[] charData = new SimpleFontRenderer.CharData[256];
    private final SimpleFontRenderer.CharData[] boldChars = new SimpleFontRenderer.CharData[256];
    private final SimpleFontRenderer.CharData[] italicChars = new SimpleFontRenderer.CharData[256];
    private final SimpleFontRenderer.CharData[] boldItalicChars = new SimpleFontRenderer.CharData[256];
    private final Font awtFont;
    private final boolean antiAlias;
    private final boolean fractionalMetrics;
    private DynamicTexture texturePlain;
    private DynamicTexture textureBold;
    private DynamicTexture textureItalic;
    private DynamicTexture textureItalicBold;
    private int fontHeight = -1;

    private SimpleFontRenderer(Font awtFont, boolean antiAlias, boolean fractionalMetrics) {
        this.awtFont = awtFont;
        this.antiAlias = antiAlias;
        this.fractionalMetrics = fractionalMetrics;
        this.setupBoldItalicFonts();
    }

    static FontRenderer create(Font font, boolean antiAlias, boolean fractionalMetrics) {
        return new SimpleFontRenderer(font, antiAlias, fractionalMetrics);
    }

    public static FontRenderer create(Font font) {
        return create(font, true, true);
    }

    private DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, SimpleFontRenderer.CharData[] chars) {
        return new DynamicTexture(this.generateFontImage(font, antiAlias, fractionalMetrics, chars));
    }

    private BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, SimpleFontRenderer.CharData[] chars) {
        boolean imgSize = true;
        BufferedImage bufferedImage = new BufferedImage(512, 512, 2);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

        graphics.setFont(font);
        graphics.setColor(new Color(255, 255, 255, 0));
        graphics.fillRect(0, 0, 512, 512);
        graphics.setColor(Color.WHITE);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if (this.fractionalMetrics) {
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        } else {
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }

        FontMetrics fontMetrics = graphics.getFontMetrics();
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;

        for (int i = 0; i < chars.length; ++i) {
            char ch = (char) i;
            SimpleFontRenderer.CharData charData = new SimpleFontRenderer.CharData(null);
            Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), graphics);

            charData.width = dimensions.getBounds().width + 8;
            charData.height = dimensions.getBounds().height;
            if (positionX + charData.width >= 512) {
                positionX = 0;
                positionY += charHeight;
                charHeight = 0;
            }

            if (charData.height > charHeight) {
                charHeight = charData.height;
            }

            charData.storedX = positionX;
            charData.storedY = positionY;
            if (charData.height > this.fontHeight) {
                this.fontHeight = charData.height;
            }

            chars[i] = charData;
            graphics.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
            positionX += charData.width;
        }

        return bufferedImage;
    }

    private void setupBoldItalicFonts() {
        this.texturePlain = this.setupTexture(this.awtFont, this.antiAlias, this.fractionalMetrics, this.charData);
        this.textureBold = this.setupTexture(this.awtFont.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.textureItalic = this.setupTexture(this.awtFont.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.textureItalicBold = this.setupTexture(this.awtFont.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    public float drawString(CharSequence text, double x, double y, int color, boolean dropShadow) {
        if (dropShadow) {
            float shadowWidth = this.drawStringInternal(text, x + 0.5D, y + 0.5D, color, true);

            return Math.max(shadowWidth, this.drawStringInternal(text, x, y, color, false));
        } else {
            return this.drawStringInternal(text, x, y, color, false);
        }
    }

    public float drawString(CharSequence text, float x, float y, int color, boolean dropShadow) {
        if (dropShadow) {
            float shadowWidth = this.drawStringInternal(text, (double) x + 0.5D, (double) y + 0.5D, color, true);

            return Math.max(shadowWidth, this.drawStringInternal(text, (double) x, (double) y, color, false));
        } else {
            return this.drawStringInternal(text, (double) x, (double) y, color, false);
        }
    }

    private float drawStringInternal(CharSequence text, double x, double y, int color, boolean shadow) {
        --x;
        if (text == null) {
            return 0.0F;
        } else {
            if (color == 553648127) {
                color = 16777215;
            }

            if ((color & -67108864) == 0) {
                color |= -16777216;
            }

            if (shadow) {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            SimpleFontRenderer.CharData[] charData = this.charData;
            float alpha = (float) (color >> 24 & 255) / 255.0F;
            boolean randomCase = false;

            x *= 2.0D;
            y = (y - 3.0D) * 2.0D;
            GL11.glPushMatrix();
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GL11.glColor4f((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);
            GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(this.texturePlain.getGlTextureId());
            GL11.glBindTexture(3553, this.texturePlain.getGlTextureId());
            GL11.glTexParameterf(3553, 10240, 9729.0F);
            boolean underline = false;
            boolean strikethrough = false;
            boolean italic = false;
            boolean bold = false;
            int i = 0;

            for (int size = text.length(); i < size; ++i) {
                char character = text.charAt(i);

                if (character == 167 && i + 1 < size) {
                    int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));

                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.bindTexture(this.texturePlain.getGlTextureId());
                        charData = this.charData;
                        if (colorIndex < 0) {
                            colorIndex = 15;
                        }

                        if (shadow) {
                            colorIndex += 16;
                        }

                        int colorCode = SimpleFontRenderer.COLOR_CODES[colorIndex];

                        GlStateManager.color((float) (colorCode >> 16 & 255) / 255.0F, (float) (colorCode >> 8 & 255) / 255.0F, (float) (colorCode & 255) / 255.0F, 255.0F);
                    } else if (colorIndex == 17) {
                        bold = true;
                        if (italic) {
                            GlStateManager.bindTexture(this.textureItalicBold.getGlTextureId());
                            charData = this.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(this.textureBold.getGlTextureId());
                            charData = this.boldChars;
                        }
                    } else if (colorIndex == 18) {
                        strikethrough = true;
                    } else if (colorIndex == 19) {
                        underline = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                        if (bold) {
                            GlStateManager.bindTexture(this.textureItalicBold.getGlTextureId());
                            charData = this.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(this.textureItalic.getGlTextureId());
                            charData = this.italicChars;
                        }
                    } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 255.0F);
                        GlStateManager.bindTexture(this.texturePlain.getGlTextureId());
                        charData = this.charData;
                    }

                    ++i;
                } else if (character < charData.length) {
                    GL11.glBegin(4);
                    drawChar(charData, character, (float) x, (float) y);
                    GL11.glEnd();
                    if (strikethrough) {
                        drawLine(x, y + (double) ((float) charData[character].height / 2.0F), x + (double) charData[character].width - 8.0D, y + (double) ((float) charData[character].height / 2.0F), 1.0F);
                    }

                    if (underline) {
                        drawLine(x, y + (double) charData[character].height - 2.0D, x + (double) charData[character].width - 8.0D, y + (double) charData[character].height - 2.0D, 1.0F);
                    }

                    x += (double) (charData[character].width - (character == 32 ? 8 : 9));
                }
            }

            GL11.glTexParameterf(3553, 10240, 9728.0F);
            GL11.glHint(3155, 4352);
            GL11.glPopMatrix();
            return (float) x / 2.0F;
        }
    }

    public String trimStringToWidth(CharSequence text, int width, boolean reverse) {
        StringBuilder builder = new StringBuilder();
        float f = 0.0F;
        int i = reverse ? text.length() - 1 : 0;
        int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;

        for (int k = i; k >= 0 && k < text.length() && f < (float) width; k += j) {
            char c0 = text.charAt(k);
            float f1 = (float) this.stringWidth(String.valueOf(c0));

            if (flag) {
                flag = false;
                if (c0 != 108 && c0 != 76) {
                    if (c0 == 114 || c0 == 82) {
                        flag1 = false;
                    }
                } else {
                    flag1 = true;
                }
            } else if (f1 < 0.0F) {
                flag = true;
            } else {
                f += f1;
                if (flag1) {
                    ++f;
                }
            }

            if (f > (float) width) {
                break;
            }

            if (reverse) {
                builder.insert(0, c0);
            } else {
                builder.append(c0);
            }
        }

        return builder.toString();
    }

    public int stringWidth(CharSequence text) {
        if (text == null) {
            return 0;
        } else {
            int width = 0;
            SimpleFontRenderer.CharData[] currentData = this.charData;
            boolean bold = false;
            boolean italic = false;
            int i = 0;

            for (int size = text.length(); i < size; ++i) {
                char character = text.charAt(i);

                if (character == 167 && i + 1 < size) {
                    int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));

                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                    } else if (colorIndex == 17) {
                        bold = true;
                        if (italic) {
                            currentData = this.boldItalicChars;
                        } else {
                            currentData = this.boldChars;
                        }
                    } else if (colorIndex == 20) {
                        italic = true;
                        if (bold) {
                            currentData = this.boldItalicChars;
                        } else {
                            currentData = this.italicChars;
                        }
                    } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                        currentData = this.charData;
                    }

                    ++i;
                } else if (character < currentData.length) {
                    width += currentData[character].width - (character == 32 ? 8 : 9);
                }
            }

            return width / 2;
        }
    }

    public float charWidth(char s) {
        return (float) ((this.charData[s].width - 8) / 2);
    }

    public SimpleFontRenderer.CharData[] getCharData() {
        return this.charData;
    }

    private static int[] setupMinecraftColorCodes() {
        int[] colorCodes = new int[32];

        for (int i = 0; i < 32; ++i) {
            int noClue = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + noClue;
            int green = (i >> 1 & 1) * 170 + noClue;
            int blue = (i & 1) * 170 + noClue;

            if (i == 6) {
                red += 85;
            }

            if (i >= 16) {
                red >>= 2;
                green >>= 2;
                blue >>= 2;
            }

            colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }

        return colorCodes;
    }

    private static void drawChar(SimpleFontRenderer.CharData[] chars, char c, float x, float y) {
        drawQuad(x, y, (float) chars[c].width, (float) chars[c].height, (float) chars[c].storedX, (float) chars[c].storedY, (float) chars[c].width, (float) chars[c].height);
    }

    private static void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / 512.0F;
        float renderSRCY = srcY / 512.0F;
        float renderSRCWidth = srcWidth / 512.0F;
        float renderSRCHeight = srcHeight / 512.0F;

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
    }

    private static void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public String getName() {
        return this.awtFont.getFamily();
    }

    public int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public boolean isAntiAlias() {
        return this.antiAlias;
    }

    public boolean isFractionalMetrics() {
        return this.fractionalMetrics;
    }

    private static class CharData {

        private int width;
        private int height;
        private int storedX;
        private int storedY;

        private CharData() {}

        CharData(Object x0) {
            this();
        }
    }
}
