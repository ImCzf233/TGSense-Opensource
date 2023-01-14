package net.ccbluex.liquidbounce.font;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CFontRenderer extends CFont {

    protected CFont.CharData[] boldChars = new CFont.CharData[256];
    protected CFont.CharData[] italicChars = new CFont.CharData[256];
    protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
    private final int[] colorCode = new int[32];
    private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        return Math.max(this.drawString(text, x + 0.5D, y + 0.5D, color, true), this.drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, int color) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        return this.drawString(text, (double) x, (double) y, color, false);
    }

    public float drawCenteredString(String text, double x, double y, int color) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        return this.drawString(text, (float) (x - (double) ((float) (this.getStringWidth(text) / 2))), (float) y, color);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        return this.drawStringWithShadow(text, (double) (x - (float) (this.getStringWidth(text) / 2)), (double) y, color);
    }

    public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
        return this.drawStringWithShadow(text, x - (double) (this.getStringWidth(text) / 2), y, color);
    }

    public static boolean isChinese(char c) {
        String s = String.valueOf(c);

        return !"1234567890abcdefghijklmnopqrstuvwxyz!<>@#$%^&*()-_=+[]{}|\\/\'\",.~`".contains(s.toLowerCase());
    }

    public static char validateLegalString(String content) {
        String illegal = "`~!#%^&*=+\\|{};:\'\",<>/?○●★☆☉♀♂�?�¤╬の�??";
        char isLegalChar = 116;

        for (int i = 0; i < content.length(); ++i) {
            for (int j = 0; j < illegal.length(); ++j) {
                if (content.charAt(i) == illegal.charAt(j)) {
                    char c0 = content.charAt(i);

                    return c0;
                }
            }
        }

        return isLegalChar;
    }

    public static int DisplayFontWidth(String str, CFontRenderer font) {
        int x = 0;

        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);

            if (s.contains("§") && iF + 1 <= str.length()) {
                ++iF;
            } else if (!isChinese(s.charAt(0))) {
                x = (int) ((float) x + (float) font.getStringWidth(s));
            } else {
                x = (int) ((float) x + (float) Fonts.Posterama35.getStringWidth(s));
            }
        }

        return x + 5;
    }

    public int DisplayFontWidths(CFontRenderer font, String str) {
        return this.DisplayFontWidths(str, font);
    }

    public int DisplayFontWidths(String str, CFontRenderer font) {
        int x = 0;

        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);

            if (s.contains("§") && iF + 1 <= str.length()) {
                ++iF;
            } else if (!isChinese(s.charAt(0))) {
                x = (int) ((float) x + (float) font.getStringWidth(s));
            } else {
                x = (int) ((float) x + (float) Fonts.Posterama35.getStringWidth(s));
            }
        }

        return x + 5;
    }

    public static float DisplayFont(CFontRenderer font, String str, float x, float y, int color) {
        return DisplayFont(str, x, y, color, font);
    }

    public static float DisplayFonts(CFontRenderer font, String str, float x, float y, int color) {
        return DisplayFont(str, x, y, color, font);
    }

    public float DisplayFont2(CFontRenderer font, String str, float x, float y, int color, boolean shadow) {
        return shadow ? DisplayFont(str, x, y, color, shadow, font) : DisplayFont(str, x, y, color, font);
    }

    public static float DisplayFont(String str, float x, float y, int color, boolean shadow, CFontRenderer font) {
        str = " " + str;

        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);

            if (s.contains("§") && iF + 1 <= str.length()) {
                color = getColor(String.valueOf(str.toCharArray()[iF + 1]));
                ++iF;
            } else if (!isChinese(s.charAt(0))) {
                font.drawString(s, x + 0.5F, y + 1.5F, (new Color(0, 0, 0, 100)).getRGB());
                font.drawString(s, x - 0.5F, y + 0.5F, color);
                x += (float) font.getStringWidth(s);
            } else {
                Fonts.Posterama35.drawString(s, x + 1.5F, y + 2.0F, (new Color(0, 0, 0, 50)).getRGB());
                Fonts.Posterama35.drawString(s, x + 0.5F, y + 1.0F, color);
                x += (float) Fonts.Posterama35.getStringWidth(s);
            }
        }

        return x;
    }

    public static float DisplayFont(String str, float x, float y, int color, CFontRenderer font) {
        str = " " + str;

        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);

            if (s.contains("§") && iF + 1 <= str.length()) {
                color = getColor(String.valueOf(str.toCharArray()[iF + 1]));
                ++iF;
            } else if (!isChinese(s.charAt(0))) {
                font.drawString(s, x - 0.5F, y + 1.0F, color);
                x += (float) font.getStringWidth(s);
            } else {
                Fonts.Posterama35.drawString(s, x + 0.5F, y + 1.0F, color);
                x += (float) Fonts.Posterama35.getStringWidth(s);
            }
        }

        return x;
    }

    public float DisplayFonts(String str, float x, float y, int color, CFontRenderer font) {
        str = " " + str;

        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);

            if (s.contains("§") && iF + 1 <= str.length()) {
                color = getColor(String.valueOf(str.toCharArray()[iF + 1]));
                ++iF;
            } else if (!isChinese(s.charAt(0))) {
                font.drawString(s, x - 0.5F, y + 1.0F, color);
                x += (float) font.getStringWidth(s);
            } else {
                Fonts.Posterama35.drawString(s, x + 0.5F, y + 1.0F, color);
                x += (float) Fonts.Posterama35.getStringWidth(s);
            }
        }

        return x;
    }

    public static int getColor(String str) {
        switch (str.hashCode()) {
        case 48:
            if (str.equals("0")) {
                return (new Color(0, 0, 0)).getRGB();
            }
            break;

        case 49:
            if (str.equals("1")) {
                return (new Color(0, 0, 189)).getRGB();
            }
            break;

        case 50:
            if (str.equals("2")) {
                return (new Color(0, 192, 0)).getRGB();
            }
            break;

        case 51:
            if (str.equals("3")) {
                return (new Color(0, 190, 190)).getRGB();
            }
            break;

        case 52:
            if (str.equals("4")) {
                return (new Color(190, 0, 0)).getRGB();
            }
            break;

        case 53:
            if (str.equals("5")) {
                return (new Color(189, 0, 188)).getRGB();
            }
            break;

        case 54:
            if (str.equals("6")) {
                return (new Color(218, 163, 47)).getRGB();
            }
            break;

        case 55:
            if (str.equals("7")) {
                return (new Color(190, 190, 190)).getRGB();
            }
            break;

        case 56:
            if (str.equals("8")) {
                return (new Color(63, 63, 63)).getRGB();
            }
            break;

        case 57:
            if (str.equals("9")) {
                return (new Color(63, 64, 253)).getRGB();
            }

        case 58:
        case 59:
        case 60:
        case 61:
        case 62:
        case 63:
        case 64:
        case 65:
        case 66:
        case 67:
        case 68:
        case 69:
        case 70:
        case 71:
        case 72:
        case 73:
        case 74:
        case 75:
        case 76:
        case 77:
        case 78:
        case 79:
        case 80:
        case 81:
        case 82:
        case 83:
        case 84:
        case 85:
        case 86:
        case 87:
        case 88:
        case 89:
        case 90:
        case 91:
        case 92:
        case 93:
        case 94:
        case 95:
        case 96:
        default:
            break;

        case 97:
            if (str.equals("a")) {
                return (new Color(63, 254, 63)).getRGB();
            }
            break;

        case 98:
            if (str.equals("b")) {
                return (new Color(62, 255, 254)).getRGB();
            }
            break;

        case 99:
            if (str.equals("c")) {
                return (new Color(254, 61, 62)).getRGB();
            }
            break;

        case 100:
            if (str.equals("d")) {
                return (new Color(255, 64, 255)).getRGB();
            }
            break;

        case 101:
            if (str.equals("e")) {
                return (new Color(254, 254, 62)).getRGB();
            }
            break;

        case 102:
            if (str.equals("f")) {
                return (new Color(255, 255, 255)).getRGB();
            }
        }

        return (new Color(255, 255, 255)).getRGB();
    }

    public float drawString(String text, float x, float y, int color, boolean shadow) {
        return this.drawString(text, Double.valueOf((double) x).doubleValue(), Double.valueOf((double) y).doubleValue(), color, shadow);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow) {
        double x2 = x - 1.0D;

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
                color = (new Color(0, 0, 0)).getRGB();
            }

            CFont.CharData[] currentData = this.charData;
            float alpha = (float) (color >> 24 & 255) / 255.0F;
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            boolean underline = false;
            char c = (char) ((int) (x2 * 2.0D));
            double y2 = (y - 3.0D) * 2.0D;
            boolean texture = GL11.glIsEnabled(3553);
            boolean blend = GL11.glIsEnabled(3042);

            GL11.glPushMatrix();
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            if (!blend) {
                GlStateManager.enableBlend();
            }

            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);
            int size = text.length();

            if (!texture) {
                GL11.glEnable(3553);
            }

            GlStateManager.bindTexture(this.tex.getGlTextureId());

            for (int i = 0; i < size; ++i) {
                char character = text.charAt(i);

                if (character == 167 && i < size) {
                    int colorIndex = 21;

                    try {
                        colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        currentData = this.charData;
                        if (colorIndex < 0 || colorIndex > 15) {
                            colorIndex = 15;
                        }

                        if (shadow) {
                            colorIndex += 16;
                        }

                        int colorcode = this.colorCode[colorIndex];

                        GlStateManager.color((float) (colorcode >> 16 & 255) / 255.0F, (float) (colorcode >> 8 & 255) / 255.0F, (float) (colorcode & 255) / 255.0F, alpha);
                    } else if (colorIndex != 16) {
                        if (colorIndex == 17) {
                            bold = true;
                            if (italic) {
                                GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                currentData = this.boldItalicChars;
                            } else {
                                GlStateManager.bindTexture(this.texBold.getGlTextureId());
                                currentData = this.boldChars;
                            }
                        } else if (colorIndex == 18) {
                            strikethrough = true;
                        } else if (colorIndex == 19) {
                            underline = true;
                        } else if (colorIndex == 20) {
                            italic = true;
                            if (bold) {
                                GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                currentData = this.boldItalicChars;
                            } else {
                                GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                                currentData = this.italicChars;
                            }
                        } else if (colorIndex == 21) {
                            bold = false;
                            italic = false;
                            underline = false;
                            strikethrough = false;
                            GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);
                            GlStateManager.bindTexture(this.tex.getGlTextureId());
                            currentData = this.charData;
                        }
                    }

                    ++i;
                } else if (character < currentData.length && character >= 0) {
                    GL11.glBegin(4);
                    this.drawChar(currentData, character, (float) c, (float) y2);
                    GL11.glEnd();
                    if (strikethrough) {
                        this.drawLine((double) c, y2 + (double) (currentData[character].height / 2), (double) c + (double) currentData[character].width - 8.0D, y2 + (double) (currentData[character].height / 2), 1.0F);
                    }

                    if (underline) {
                        this.drawLine((double) c, y2 + (double) currentData[character].height - 2.0D, (double) c + (double) currentData[character].width - 8.0D, y2 + (double) currentData[character].height - 2.0D, 1.0F);
                    }

                    c = (char) ((int) ((double) c + (double) (currentData[character].width - 8 + this.charOffset)));
                }
            }

            if (!blend) {
                GlStateManager.disableBlend();
            }

            if (!texture) {
                GL11.glDisable(3553);
            }

            GL11.glHint(3155, 4352);
            GL11.glPopMatrix();
            return (float) c / 2.0F;
        }
    }

    public int drawStringi(String text, double x, double y, int color, boolean shadow) {
        double x2 = x - 1.0D;

        if (text == null) {
            return 0;
        } else {
            if (color == 553648127) {
                color = 16777215;
            }

            if ((color & -67108864) == 0) {
                color |= -16777216;
            }

            if (shadow) {
                color = (new Color(0, 0, 0)).getRGB();
            }

            CFont.CharData[] currentData = this.charData;
            float alpha = (float) (color >> 24 & 255) / 255.0F;
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            boolean underline = false;
            char c = (char) ((int) (x2 * 2.0D));
            double y2 = (y - 3.0D) * 2.0D;
            boolean texture = GL11.glIsEnabled(3553);
            boolean blend = GL11.glIsEnabled(3042);

            GL11.glPushMatrix();
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            if (!blend) {
                GlStateManager.enableBlend();
            }

            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);
            int size = text.length();

            if (!texture) {
                GL11.glEnable(3553);
            }

            GlStateManager.bindTexture(this.tex.getGlTextureId());

            for (int i = 0; i < size; ++i) {
                char character = text.charAt(i);

                if (character == 167 && i < size) {
                    int colorIndex = 21;

                    try {
                        colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        currentData = this.charData;
                        if (colorIndex < 0 || colorIndex > 15) {
                            colorIndex = 15;
                        }

                        if (shadow) {
                            colorIndex += 16;
                        }

                        int colorcode = this.colorCode[colorIndex];

                        GlStateManager.color((float) (colorcode >> 16 & 255) / 255.0F, (float) (colorcode >> 8 & 255) / 255.0F, (float) (colorcode & 255) / 255.0F, alpha);
                    } else if (colorIndex != 16) {
                        if (colorIndex == 17) {
                            bold = true;
                            if (italic) {
                                GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                currentData = this.boldItalicChars;
                            } else {
                                GlStateManager.bindTexture(this.texBold.getGlTextureId());
                                currentData = this.boldChars;
                            }
                        } else if (colorIndex == 18) {
                            strikethrough = true;
                        } else if (colorIndex == 19) {
                            underline = true;
                        } else if (colorIndex == 20) {
                            italic = true;
                            if (bold) {
                                GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                currentData = this.boldItalicChars;
                            } else {
                                GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                                currentData = this.italicChars;
                            }
                        } else if (colorIndex == 21) {
                            bold = false;
                            italic = false;
                            underline = false;
                            strikethrough = false;
                            GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);
                            GlStateManager.bindTexture(this.tex.getGlTextureId());
                            currentData = this.charData;
                        }
                    }

                    ++i;
                } else if (character < currentData.length && character >= 0) {
                    GL11.glBegin(4);
                    this.drawChar(currentData, character, (float) c, (float) y2);
                    GL11.glEnd();
                    if (strikethrough) {
                        this.drawLine((double) c, y2 + (double) (currentData[character].height / 2), (double) c + (double) currentData[character].width - 8.0D, y2 + (double) (currentData[character].height / 2), 1.0F);
                    }

                    if (underline) {
                        this.drawLine((double) c, y2 + (double) currentData[character].height - 2.0D, (double) c + (double) currentData[character].width - 8.0D, y2 + (double) currentData[character].height - 2.0D, 1.0F);
                    }

                    c = (char) ((int) ((double) c + (double) (currentData[character].width - 8 + this.charOffset)));
                }
            }

            if (!blend) {
                GlStateManager.disableBlend();
            }

            if (!texture) {
                GL11.glDisable(3553);
            }

            GL11.glHint(3155, 4352);
            GL11.glPopMatrix();
            return c / 2;
        }
    }

    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        } else {
            int width = 0;
            CFont.CharData[] currentData = this.charData;
            boolean bold = false;
            boolean italic = false;
            int size = text.length();

            for (int i = 0; i < size; ++i) {
                char character = text.charAt(i);

                if (character == 167 && i < size) {
                    int colorIndex = "0123456789abcdefklmnor".indexOf(character);

                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                    } else if (colorIndex == 17) {
                        bold = true;
                        currentData = italic ? this.boldItalicChars : this.boldChars;
                    } else if (colorIndex == 20) {
                        italic = true;
                        currentData = bold ? this.boldItalicChars : this.italicChars;
                    } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                        currentData = this.charData;
                    }

                    ++i;
                } else if (character < currentData.length && character >= 0) {
                    width += currentData[character].width - 8 + this.charOffset;
                }
            }

            return width / 2;
        }
    }

    public void setFont(Font font) {
        this.setFont(font);
        this.setupBoldItalicIDs();
    }

    public void setAntiAlias(boolean antiAlias) {
        this.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    public void setFractionalMetrics(boolean fractionalMetrics) {
        this.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
    }

    private void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public List wrapWords(String text, double width) {
        ArrayList finalWords = new ArrayList();

        if ((double) this.getStringWidth(text) > width) {
            String[] words = text.split(" ");
            String currentWord = "";
            char c = '\uffff';
            String[] astring = words;
            int s = words.length;

            for (int i = 0; i < s; ++i) {
                String word = astring[i];

                for (int i = 0; i < word.toCharArray().length; ++i) {
                    if (word.toCharArray()[i] == 167 && i < word.toCharArray().length - 1) {
                        c = word.toCharArray()[i + 1];
                    }
                }

                if ((double) this.getStringWidth(currentWord + word + " ") < width) {
                    currentWord = currentWord + word + " ";
                } else {
                    finalWords.add(currentWord);
                    currentWord = 167 + c + word + " ";
                }
            }

            if (currentWord.length() > 0) {
                if ((double) this.getStringWidth(currentWord) < width) {
                    finalWords.add(167 + c + currentWord + " ");
                } else {
                    Iterator iterator = this.formatString(currentWord, width).iterator();

                    while (iterator.hasNext()) {
                        String s = (String) iterator.next();

                        finalWords.add(s);
                    }
                }
            }
        } else {
            finalWords.add(text);
        }

        return finalWords;
    }

    public List formatString(String string, double width) {
        ArrayList finalWords = new ArrayList();
        String currentWord = "";
        char lastColorCode = '\uffff';
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];

            if (c == 167 && i < chars.length - 1) {
                lastColorCode = chars[i + 1];
            }

            if ((double) this.getStringWidth(currentWord + c) < width) {
                currentWord = currentWord + c;
            } else {
                finalWords.add(currentWord);
                currentWord = 167 + lastColorCode + String.valueOf(c);
            }
        }

        if (currentWord.length() > 0) {
            finalWords.add(currentWord);
        }

        return finalWords;
    }
}
