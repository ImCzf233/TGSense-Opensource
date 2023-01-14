package me.ui.Fonts;

import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import net.ccbluex.liquidbounce.utils.render.Colors;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class FontUtils {

    public float FONT_HEIGHT;
    private final FluxFont unicodeFont;
    private final int[] colorCodes;
    private float kerning;
    public HashMap widthMap;
    public HashMap heightMap;

    public FontUtils(Font font, int fontType, int size, boolean allChar) {
        this(font, fontType, size, 0, allChar);
    }

    public FontUtils(Font font, int fontType, int size, int kerning, boolean allChar) {
        this(font, fontType, size, kerning, allChar, 0);
    }

    public FontUtils(Font font, int fontType, int size, int kerning, boolean allChar, int yAddon) {
        this.FONT_HEIGHT = 0.0F;
        this.colorCodes = new int[32];
        this.widthMap = new HashMap();
        this.heightMap = new HashMap();
        this.unicodeFont = new FluxFont(font, true, kerning, allChar, yAddon);
        this.kerning = 0.0F;

        for (int i = 0; i < 32; ++i) {
            int shadow = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + shadow;
            int green = (i >> 1 & 1) * 170 + shadow;
            int blue = (i & 1) * 170 + shadow;

            if (i == 6) {
                red += 85;
            }

            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }

        this.FONT_HEIGHT = this.getHeight("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    private Font getFont(String fontName, int fontType, int size) {
        Font font = null;

        try {
            InputStream inputstream = FontManager.class.getResourceAsStream("/assets/minecraft/langya/font/" + fontName);

            font = Font.createFont(0, inputstream);
            font = font.deriveFont(fontType, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Failed to load custom font");
        }

        return font;
    }

    public int drawString(String text, float x, float y, int color) {
        if (color == 16777215) {
            color = Colors.WHITE.c;
        }

        return this.drawStringWithAlpha(text, x, y, color, (float) (color >> 24 & 255) / 255.0F);
    }

    public void drawLimitedString(String text, float x, float y, int color, float maxWidth) {
        this.drawLimitedStringWithAlpha(text, x, y, color, (float) (color >> 24 & 255) / 255.0F, maxWidth);
    }

    public void drawLimitedStringWithAlpha(String text, float x, float y, int color, float alpha, float maxWidth) {
        text = this.processString(text);
        x *= 2.0F;
        y *= 2.0F;
        float originalX = x;
        float curWidth = 0.0F;

        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        boolean wasBlend = GL11.glGetBoolean(3042);

        GlStateManager.enableAlpha();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        char[] achar = characters;
        int i = characters.length;

        for (int j = 0; j < i; ++j) {
            char c = achar[j];

            if (c == 13) {
                x = originalX;
            }

            if (c == 10) {
                y += this.getHeight(Character.toString(c)) * 2.0F;
            }

            if (c != 167 && (index == 0 || index == characters.length - 1 || characters[index - 1] != 167)) {
                if (index >= 1 && characters[index - 1] == 167) {
                    continue;
                }

                GL11.glPushMatrix();
                this.unicodeFont.drawString(Character.toString(c), (double) x, (double) y, RenderUtils.reAlpha(currentColor, alpha), false);
                GL11.glPopMatrix();
                curWidth += this.getStringWidth(Character.toString(c)) * 2.0F;
                x += this.getStringWidth(Character.toString(c)) * 2.0F;
                if (curWidth > maxWidth) {
                    break;
                }
            } else if (c == 32) {
                x += (float) this.unicodeFont.getWidth(" ");
            } else if (c == 167 && index != characters.length - 1) {
                int codeIndex = "0123456789abcdefklmnor".indexOf(text.charAt(index + 1));

                if (codeIndex < 0) {
                    continue;
                }

                if (codeIndex < 16) {
                    currentColor = this.colorCodes[codeIndex];
                } else if (codeIndex == 21) {
                    currentColor = Color.WHITE.getRGB();
                }
            }

            ++index;
        }

        if (!wasBlend) {
            GL11.glDisable(3042);
        }

        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public int drawStringWithAlpha(String text, float x, float y, int color, float alpha) {
        text = this.processString(text);
        x *= 2.0F;
        y *= 2.0F;
        float originalX = x;

        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        boolean wasBlend = GL11.glGetBoolean(3042);

        GlStateManager.enableAlpha();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        char[] achar = characters;
        int i = characters.length;

        for (int j = 0; j < i; ++j) {
            char c = achar[j];

            if (c == 13) {
                x = originalX;
            }

            if (c == 10) {
                y += this.getHeight(Character.toString(c)) * 2.0F;
            }

            if (c != 167 && (index == 0 || index == characters.length - 1 || characters[index - 1] != 167)) {
                if (index >= 1 && characters[index - 1] == 167) {
                    continue;
                }

                GL11.glPushMatrix();
                this.unicodeFont.drawString(Character.toString(c), (double) x, (double) y, RenderUtils.reAlpha(currentColor, alpha), false);
                GL11.glPopMatrix();
                x += this.getStringWidth(Character.toString(c)) * 2.0F;
            } else if (c == 32) {
                x += (float) this.unicodeFont.getWidth(" ");
            } else if (c == 167 && index != characters.length - 1) {
                int codeIndex = "0123456789abcdefklmnor".indexOf(text.charAt(index + 1));

                if (codeIndex < 0) {
                    continue;
                }

                if (codeIndex < 16) {
                    currentColor = this.colorCodes[codeIndex];
                } else if (codeIndex == 21) {
                    currentColor = Color.WHITE.getRGB();
                }
            }

            ++index;
        }

        if (!wasBlend) {
            GL11.glDisable(3042);
        }

        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        return (int) x;
    }

    private String processString(String text) {
        String str = "";
        char[] achar = text.toCharArray();
        int i = achar.length;

        for (int j = 0; j < i; ++j) {
            char c = achar[j];

            if ((c < 'ì?' || c > '\uea60') && c != 9917) {
                str = str + c;
            }
        }

        text = str.replace("Â§r", "").replace('â–?', '=').replace('â?', 'â™?').replace('â‹?', 'â˜?').replace('â˜?', 'â˜?').replace('âœ?', 'â˜?').replace("âœ?", "â˜?").replace("âœ?", "+");
        text = text.replace('â¬?', 'â†?').replace('â¬?', 'â†?').replace('â¬?', 'â†?').replace('âž?', 'â†?').replace('â¬?', 'â†?').replace('â¬?', 'â†?').replace('â¬?', 'â†?').replace('â¬?', 'â†?');
        return text;
    }

    public void drawStringWithGudShadow(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 1.0F, y + 1.0F, this.getShadowColor(color).getRGB());
        this.drawString(text, x, y, color);
    }

    public void drawStringWithShadowForChat(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 1.0F, y + 1.0F, this.getShadowColor(color).getRGB());
        this.drawString(text, x, y, color);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        this.drawStringWithAlpha(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.4F, 0, (float) (color >> 24 & 255) / 255.0F);
        return this.drawString(text, x, y, color);
    }

    public void drawStringWithSuperShadow(String text, float x, float y, int color) {
        this.drawStringWithAlpha(StringUtils.stripControlCodes(text), x + 0.8F, y + 0.8F, 0, (float) (color >> 24 & 255) / 255.0F);
        this.drawString(text, x, y, color);
    }

    public int drawCenteredString(String text, float x, float y, int color) {
        return this.drawString(text, x - this.getStringWidth(text) / 2.0F, y, color);
    }

    public void drawCenteredStringWithAlpha(String text, float x, float y, int color, float alpha) {
        this.drawStringWithAlpha(text, x - this.getStringWidth(text) / 2.0F, y, color, alpha);
    }

    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, -16777216);
        this.drawCenteredString(text, x, y, color);
    }

    private Color getShadowColor(int hex) {
        float a = (float) (hex >> 24 & 255) / 255.0F;
        float r = (float) (hex >> 16 & 255) / 255.0F;
        float g = (float) (hex >> 8 & 255) / 255.0F;
        float b = (float) (hex & 255) / 255.0F;

        return new Color(r * 0.2F, g * 0.2F, b * 0.2F, a * 0.9F);
    }

    public void drawOutlinedString(String str, float x, float y, int internalCol, int externalCol) {
        this.drawString(str, x - 0.5F, y, externalCol);
        this.drawString(str, x + 0.5F, y, externalCol);
        this.drawString(str, x, y - 0.5F, externalCol);
        this.drawString(str, x, y + 0.5F, externalCol);
        this.drawString(str, x, y, internalCol);
    }

    public float getStringWidth(String s) {
        if (this.widthMap.containsKey(s)) {
            return ((Float) this.widthMap.get(s)).floatValue();
        } else {
            float width = 0.0F;
            String str = StringUtils.stripControlCodes(s);
            char[] achar = str.toCharArray();
            int i = achar.length;

            for (int j = 0; j < i; ++j) {
                char c = achar[j];

                width += (float) this.unicodeFont.getWidth(Character.toString(c)) + this.kerning;
            }

            this.widthMap.put(s, Float.valueOf(width / 2.0F));
            return width / 2.0F;
        }
    }

    public float getCharWidth(char c) {
        return (float) this.unicodeFont.getWidth(String.valueOf(c));
    }

    public float getHeight(String s) {
        if (this.heightMap.containsKey(s)) {
            return ((Float) this.heightMap.get(s)).floatValue();
        } else {
            float height = (float) this.unicodeFont.getHeight(s) / 2.0F;

            this.heightMap.put(s, Float.valueOf(height));
            return height;
        }
    }

    public float getHeight() {
        return (float) this.unicodeFont.getHeight("FluxClientIsThatBestClarinet.") / 2.0F;
    }

    public FluxFont getFont() {
        return this.unicodeFont;
    }

    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    public String trimStringToWidth(String text, int width, boolean reverse) {
        text = this.processString(text);
        StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0F;
        int i = reverse ? text.length() - 1 : 0;
        int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;

        for (int k = i; k >= 0 && k < text.length() && f < (float) width; k += j) {
            char c0 = text.charAt(k);
            float f1 = this.getCharWidth(c0);

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
                f = (float) ((double) f + (double) f1 / (text.contains("=====") ? 2.2D : 2.0D));
                if (flag1) {
                    ++f;
                }
            }

            if (f > (float) width) {
                break;
            }

            if (reverse) {
                stringbuilder.insert(0, c0);
            } else {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

    public String trimStringToWidth(String text, float width, boolean reverse) {
        text = this.processString(text);
        StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0F;
        int i = reverse ? text.length() - 1 : 0;
        int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;

        for (int k = i; k >= 0 && k < text.length() && f < width; k += j) {
            char c0 = text.charAt(k);
            float f1 = this.getCharWidth(c0);

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
                f = (float) ((double) f + (double) f1 / (text.contains("=====") ? 2.2D : 2.0D));
                if (flag1) {
                    ++f;
                }
            }

            if (f > width) {
                break;
            }

            if (reverse) {
                stringbuilder.insert(0, c0);
            } else {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }
}
