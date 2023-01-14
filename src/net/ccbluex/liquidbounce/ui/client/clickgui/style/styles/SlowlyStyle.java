package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper;
import net.ccbluex.liquidbounce.ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class SlowlyStyle extends Style {

    private boolean mouseDown;
    private boolean rightMouseDown;

    public static float drawSlider(float value, float min, float max, int x, int y, int width, int mouseX, int mouseY, Color color) {
        float displayValue = Math.max(min, Math.min(value, max));
        float sliderValue = (float) x + (float) width * (displayValue - min) / (max - min);

        RenderUtils.drawRect(x, y, x + width, y + 2, Integer.MAX_VALUE);
        RenderUtils.drawRect((float) x, (float) y, sliderValue, (float) (y + 2), color);
        RenderUtils.drawFilledCircle((int) sliderValue, y + 1, 3.0F, color);
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 3 && Mouse.isButtonDown(0)) {
            double i = WMathHelper.clamp_double(((double) mouseX - (double) x) / ((double) width - 3.0D), 0.0D, 1.0D);
            BigDecimal bigDecimal = new BigDecimal(Double.toString((double) min + (double) (max - min) * i));

            bigDecimal = bigDecimal.setScale(2, 4);
            return bigDecimal.floatValue();
        } else {
            return value;
        }
    }

    public void drawPanel(int mouseX, int mouseY, Panel panel) {
        RenderUtils.drawBorderedRect((float) panel.getX(), (float) panel.getY() - 3.0F, (float) panel.getX() + (float) panel.getWidth(), (float) panel.getY() + 17.0F, 3.0F, (new Color(42, 57, 79)).getRGB(), (new Color(42, 57, 79)).getRGB());
        if (panel.getFade() > 0) {
            RenderUtils.drawBorderedRect((float) panel.getX(), (float) panel.getY() + 17.0F, (float) panel.getX() + (float) panel.getWidth(), (float) (panel.getY() + 19 + panel.getFade()), 3.0F, (new Color(54, 71, 96)).getRGB(), (new Color(54, 71, 96)).getRGB());
            RenderUtils.drawBorderedRect((float) panel.getX(), (float) (panel.getY() + 17 + panel.getFade()), (float) panel.getX() + (float) panel.getWidth(), (float) (panel.getY() + 19 + panel.getFade() + 5), 3.0F, (new Color(42, 57, 79)).getRGB(), (new Color(42, 57, 79)).getRGB());
        }

        GlStateManager.resetColor();
        float textWidth = (float) Fonts.font35.getStringWidth("§f" + StringUtils.stripControlCodes(panel.getName()));

        Fonts.font35.drawString(panel.getName(), (int) ((float) panel.getX() - (textWidth - 100.0F) / 2.0F), panel.getY() + 7 - 3, Color.WHITE.getRGB());
    }

    public void drawDescription(int mouseX, int mouseY, String text) {
        int textWidth = Fonts.font35.getStringWidth(text);

        RenderUtils.drawBorderedRect((float) (mouseX + 9), (float) mouseY, (float) (mouseX + textWidth + 14), (float) (mouseY + Fonts.font35.getFontHeight() + 3), 3.0F, (new Color(42, 57, 79)).getRGB(), (new Color(42, 57, 79)).getRGB());
        GlStateManager.resetColor();
        Fonts.font35.drawString(text, mouseX + 12, mouseY + Fonts.font35.getFontHeight() / 2, Color.WHITE.getRGB());
    }

    public void drawButtonElement(int mouseX, int mouseY, ButtonElement buttonElement) {
        Gui.drawRect(buttonElement.getX() - 1, buttonElement.getY() - 1, buttonElement.getX() + buttonElement.getWidth() + 1, buttonElement.getY() + buttonElement.getHeight() + 1, this.hoverColor(buttonElement.getColor() != Integer.MAX_VALUE ? new Color(7, 152, 252) : new Color(54, 71, 96), buttonElement.hoverTime).getRGB());
        GlStateManager.resetColor();
        Fonts.font35.drawString(buttonElement.getDisplayName(), buttonElement.getX() + 5, buttonElement.getY() + 5, Color.WHITE.getRGB());
    }

    public void drawModuleElement(int mouseX, int mouseY, ModuleElement moduleElement) {
        Gui.drawRect(moduleElement.getX() - 1, moduleElement.getY() - 1, moduleElement.getX() + moduleElement.getWidth() + 1, moduleElement.getY() + moduleElement.getHeight() + 1, this.hoverColor(new Color(54, 71, 96), moduleElement.hoverTime).getRGB());
        Gui.drawRect(moduleElement.getX() - 1, moduleElement.getY() - 1, moduleElement.getX() + moduleElement.getWidth() + 1, moduleElement.getY() + moduleElement.getHeight() + 1, this.hoverColor(new Color(7, 152, 252, moduleElement.slowlyFade), moduleElement.hoverTime).getRGB());
        GlStateManager.resetColor();
        Fonts.font35.drawString(moduleElement.getDisplayName(), moduleElement.getX() + 5, moduleElement.getY() + 5, Color.WHITE.getRGB());
        List moduleValues = moduleElement.getModule().getValues();

        if (!moduleValues.isEmpty()) {
            Fonts.font35.drawString(">", moduleElement.getX() + moduleElement.getWidth() - 8, moduleElement.getY() + 5, Color.WHITE.getRGB());
            if (moduleElement.isShowSettings()) {
                if (moduleElement.getSettingsWidth() > 0.0F && moduleElement.slowlySettingsYPos > moduleElement.getY() + 6) {
                    RenderUtils.drawBorderedRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (moduleElement.getY() + 6), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (moduleElement.slowlySettingsYPos + 2), 3.0F, (new Color(54, 71, 96)).getRGB(), (new Color(54, 71, 96)).getRGB());
                }

                moduleElement.slowlySettingsYPos = moduleElement.getY() + 6;
                Iterator iterator = moduleValues.iterator();

                while (iterator.hasNext()) {
                    Value value = (Value) iterator.next();
                    boolean isNumber = value.get() instanceof Number;

                    if (isNumber) {
                        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
                    }

                    String text;
                    float textWidth;

                    if (value instanceof BoolValue) {
                        text = value.getName();
                        textWidth = (float) Fonts.font35.getStringWidth(text);
                        if (moduleElement.getSettingsWidth() < textWidth + 8.0F) {
                            moduleElement.setSettingsWidth(textWidth + 8.0F);
                        }

                        if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos && mouseY <= moduleElement.slowlySettingsYPos + 12 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                            BoolValue displayString = (BoolValue) value;

                            displayString.set(Boolean.valueOf(!((Boolean) displayString.get()).booleanValue()));
                            SlowlyStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                        Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, ((Boolean) ((BoolValue) value).get()).booleanValue() ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                        moduleElement.slowlySettingsYPos += 11;
                    } else {
                        int i;
                        String s;
                        float f;

                        if (value instanceof ListValue) {
                            ListValue listvalue = (ListValue) value;

                            s = value.getName();
                            f = (float) Fonts.font35.getStringWidth(s);
                            if (moduleElement.getSettingsWidth() < f + 16.0F) {
                                moduleElement.setSettingsWidth(f + 16.0F);
                            }

                            Fonts.font35.drawString(s, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, 16777215);
                            Fonts.font35.drawString(listvalue.openList ? "-" : "+", (int) ((float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - (float) (listvalue.openList ? 5 : 6)), moduleElement.slowlySettingsYPos + 2, 16777215);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos && mouseY <= moduleElement.slowlySettingsYPos + Fonts.font35.getFontHeight() && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                listvalue.openList = !listvalue.openList;
                                SlowlyStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                            }

                            moduleElement.slowlySettingsYPos += Fonts.font35.getFontHeight() + 1;
                            String[] stringWidth = listvalue.getValues();
                            int fonts = stringWidth.length;

                            for (i = 0; i < fonts; ++i) {
                                String font = stringWidth[i];
                                float textWidth2 = (float) Fonts.font35.getStringWidth("> " + font);

                                if (moduleElement.getSettingsWidth() < textWidth2 + 12.0F) {
                                    moduleElement.setSettingsWidth(textWidth2 + 12.0F);
                                }

                                if (listvalue.openList) {
                                    if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos + 2 && mouseY <= moduleElement.slowlySettingsYPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                        listvalue.set(font);
                                        SlowlyStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                                    }

                                    GlStateManager.resetColor();
                                    Fonts.font35.drawString("> " + font, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, listvalue.get() != null && ((String) listvalue.get()).equalsIgnoreCase(font) ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                                    moduleElement.slowlySettingsYPos += Fonts.font35.getFontHeight() + 1;
                                }
                            }

                            if (!listvalue.openList) {
                                ++moduleElement.slowlySettingsYPos;
                            }
                        } else {
                            float f1;

                            if (value instanceof FloatValue) {
                                FloatValue floatvalue = (FloatValue) value;

                                s = value.getName() + "§f: " + this.round(((Float) floatvalue.get()).floatValue());
                                f = (float) Fonts.font35.getStringWidth(s);
                                if (moduleElement.getSettingsWidth() < f + 8.0F) {
                                    moduleElement.setSettingsWidth(f + 8.0F);
                                }

                                f1 = drawSlider(((Float) floatvalue.get()).floatValue(), floatvalue.getMinimum(), floatvalue.getMaximum(), moduleElement.getX() + moduleElement.getWidth() + 8, moduleElement.slowlySettingsYPos + 14, (int) moduleElement.getSettingsWidth() - 12, mouseX, mouseY, new Color(7, 152, 252));
                                if (f1 != ((Float) floatvalue.get()).floatValue()) {
                                    floatvalue.set((Object) Float.valueOf(f1));
                                }

                                Fonts.font35.drawString(s, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 3, 16777215);
                                moduleElement.slowlySettingsYPos += 19;
                            } else if (value instanceof IntegerValue) {
                                IntegerValue integervalue = (IntegerValue) value;

                                s = value.getName() + "§f: " + (value instanceof BlockValue ? BlockUtils.getBlockName(((Integer) integervalue.get()).intValue()) + " (" + integervalue.get() + ")" : (Serializable) integervalue.get());
                                f = (float) Fonts.font35.getStringWidth(s);
                                if (moduleElement.getSettingsWidth() < f + 8.0F) {
                                    moduleElement.setSettingsWidth(f + 8.0F);
                                }

                                f1 = drawSlider((float) ((Integer) integervalue.get()).intValue(), (float) integervalue.getMinimum(), (float) integervalue.getMaximum(), moduleElement.getX() + moduleElement.getWidth() + 8, moduleElement.slowlySettingsYPos + 14, (int) moduleElement.getSettingsWidth() - 12, mouseX, mouseY, new Color(7, 152, 252));
                                if (f1 != (float) ((Integer) integervalue.get()).intValue()) {
                                    integervalue.set((Object) Integer.valueOf((int) f1));
                                }

                                Fonts.font35.drawString(s, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 3, 16777215);
                                moduleElement.slowlySettingsYPos += 19;
                            } else if (value instanceof FontValue) {
                                FontValue fontvalue = (FontValue) value;
                                IFontRenderer ifontrenderer = (IFontRenderer) fontvalue.get();
                                String s1 = "Font: Unknown";

                                if (ifontrenderer.isGameFontRenderer()) {
                                    GameFontRenderer gamefontrenderer = ifontrenderer.getGameFontRenderer();

                                    s1 = "Font: " + gamefontrenderer.getDefaultFont().getFont().getName() + " - " + gamefontrenderer.getDefaultFont().getFont().getSize();
                                } else if (ifontrenderer == Fonts.minecraftFont) {
                                    s1 = "Font: Minecraft";
                                } else {
                                    Fonts.FontInfo fonts_fontinfo = Fonts.getFontDetails(ifontrenderer);

                                    if (fonts_fontinfo != null) {
                                        s1 = fonts_fontinfo.getName() + (fonts_fontinfo.getFontSize() != -1 ? " - " + fonts_fontinfo.getFontSize() : "");
                                    }
                                }

                                Fonts.font35.drawString(s1, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, Color.WHITE.getRGB());
                                int i = Fonts.font35.getStringWidth(s1);

                                if (moduleElement.getSettingsWidth() < (float) (i + 8)) {
                                    moduleElement.setSettingsWidth((float) (i + 8));
                                }

                                if ((Mouse.isButtonDown(0) && !this.mouseDown || Mouse.isButtonDown(1) && !this.rightMouseDown) && mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos && mouseY <= moduleElement.slowlySettingsYPos + 12) {
                                    List list = Fonts.getFonts();
                                    IFontRenderer ifontrenderer1;

                                    if (Mouse.isButtonDown(0)) {
                                        for (i = 0; i < list.size(); ++i) {
                                            ifontrenderer1 = (IFontRenderer) list.get(i);
                                            if (ifontrenderer1.equals(ifontrenderer)) {
                                                ++i;
                                                if (i >= list.size()) {
                                                    i = 0;
                                                }

                                                fontvalue.set(list.get(i));
                                                break;
                                            }
                                        }
                                    } else {
                                        for (i = list.size() - 1; i >= 0; --i) {
                                            ifontrenderer1 = (IFontRenderer) list.get(i);
                                            if (ifontrenderer1.equals(ifontrenderer)) {
                                                --i;
                                                if (i >= list.size()) {
                                                    i = 0;
                                                }

                                                if (i < 0) {
                                                    i = list.size() - 1;
                                                }

                                                fontvalue.set(list.get(i));
                                                break;
                                            }
                                        }
                                    }
                                }

                                moduleElement.slowlySettingsYPos += 11;
                            } else {
                                text = value.getName() + "§f: " + value.get();
                                textWidth = (float) Fonts.font35.getStringWidth(text);
                                if (moduleElement.getSettingsWidth() < textWidth + 8.0F) {
                                    moduleElement.setSettingsWidth(textWidth + 8.0F);
                                }

                                GlStateManager.resetColor();
                                Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 4, 16777215);
                                moduleElement.slowlySettingsYPos += 12;
                            }
                        }
                    }

                    if (isNumber) {
                        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
                    }
                }

                moduleElement.updatePressed();
                this.mouseDown = Mouse.isButtonDown(0);
                this.rightMouseDown = Mouse.isButtonDown(1);
            }
        }

    }

    private BigDecimal round(float v) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(v));

        bigDecimal = bigDecimal.setScale(2, 4);
        return bigDecimal;
    }

    private Color hoverColor(Color color, int hover) {
        int r = color.getRed() - hover * 2;
        int g = color.getGreen() - hover * 2;
        int b = color.getBlue() - hover * 2;

        return new Color(Math.max(r, 0), Math.max(g, 0), Math.max(b, 0), color.getAlpha());
    }
}
