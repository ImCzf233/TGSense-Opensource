package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class NullStyle extends Style {

    private boolean mouseDown;
    private boolean rightMouseDown;

    private Color modifyAlpha(Color col, int alpha) {
        return new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha);
    }

    public void drawPanel(int mouseX, int mouseY, Panel panel) {
        RenderUtils.drawGradientSideways((double) ((float) panel.getX() - 3.0F), (double) ((float) panel.getY()), (double) ((float) panel.getX() + (float) panel.getWidth() + 3.0F), (double) ((float) panel.getY() + 19.0F), this.modifyAlpha(ClickGUI.generateColor(), 120).getRGB(), this.modifyAlpha(ClickGUI.generateColor().darker().darker(), 120).getRGB());
        GlStateManager.resetColor();
        if (panel.getFade() > 0) {
            RenderUtils.drawRect((float) panel.getX(), (float) panel.getY() + 19.0F, (float) panel.getX() + (float) panel.getWidth(), (float) (panel.getY() + 19 + panel.getFade()), Integer.MIN_VALUE);
        }

        GlStateManager.resetColor();
        float textWidth = (float) Fonts.fontSFUI35.getStringWidth("§f" + StringUtils.stripControlCodes(panel.getName()));

        Fonts.fontSFUI35.drawString("§f" + panel.getName(), (int) ((float) panel.getX() - (textWidth - 100.0F) / 2.0F), panel.getY() + 7, Integer.MAX_VALUE);
    }

    public void drawDescription(int mouseX, int mouseY, String text) {
        int textWidth = Fonts.fontSFUI35.getStringWidth(text);

        RenderUtils.drawRect(mouseX + 9, mouseY, mouseX + textWidth + 14, mouseY + Fonts.fontSFUI35.getFontHeight() + 3, this.modifyAlpha(ClickGUI.generateColor(), 120).getRGB());
        GlStateManager.resetColor();
        Fonts.fontSFUI35.drawString(text, mouseX + 12, mouseY + Fonts.fontSFUI35.getFontHeight() / 2, Integer.MAX_VALUE);
    }

    public void drawButtonElement(int mouseX, int mouseY, ButtonElement buttonElement) {
        GlStateManager.resetColor();
        Fonts.fontSFUI35.drawString(buttonElement.getDisplayName(), buttonElement.getX() + 5, buttonElement.getY() + 6, buttonElement.getColor());
    }

    public void drawModuleElement(int mouseX, int mouseY, ModuleElement moduleElement) {
        int guiColor = ClickGUI.generateColor().getRGB();

        GlStateManager.resetColor();
        Fonts.fontSFUI35.drawString(moduleElement.getDisplayName(), moduleElement.getX() + 5, moduleElement.getY() + 6, moduleElement.getModule().getState() ? guiColor : Integer.MAX_VALUE);
        List moduleValues = moduleElement.getModule().getValues();

        if (!moduleValues.isEmpty()) {
            Fonts.fontSFUI35.drawString("+", moduleElement.getX() + moduleElement.getWidth() - 8, moduleElement.getY() + moduleElement.getHeight() / 2, Color.WHITE.getRGB());
            if (moduleElement.isShowSettings()) {
                int yPos = moduleElement.getY() + 4;
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
                        textWidth = (float) Fonts.fontSFUI35.getStringWidth(text);
                        if (moduleElement.getSettingsWidth() < textWidth + 8.0F) {
                            moduleElement.setSettingsWidth(textWidth + 8.0F);
                        }

                        RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                        if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                            BoolValue displayString = (BoolValue) value;

                            displayString.set(Boolean.valueOf(!((Boolean) displayString.get()).booleanValue()));
                            NullStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                        GlStateManager.resetColor();
                        Fonts.fontSFUI35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, ((Boolean) ((BoolValue) value).get()).booleanValue() ? guiColor : Integer.MAX_VALUE);
                        yPos += 12;
                    } else {
                        int i;
                        String s;
                        float f;

                        if (value instanceof ListValue) {
                            ListValue listvalue = (ListValue) value;

                            s = value.getName();
                            f = (float) Fonts.fontSFUI35.getStringWidth(s);
                            if (moduleElement.getSettingsWidth() < f + 16.0F) {
                                moduleElement.setSettingsWidth(f + 16.0F);
                            }

                            RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                            GlStateManager.resetColor();
                            Fonts.fontSFUI35.drawString("§c" + s, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                            Fonts.fontSFUI35.drawString(listvalue.openList ? "-" : "+", (int) ((float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - (float) (listvalue.openList ? 5 : 6)), yPos + 4, 16777215);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                listvalue.openList = !listvalue.openList;
                                NullStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                            }

                            yPos += 12;
                            String[] stringWidth = listvalue.getValues();
                            int fonts = stringWidth.length;

                            for (i = 0; i < fonts; ++i) {
                                String font = stringWidth[i];
                                float textWidth2 = (float) Fonts.fontSFUI35.getStringWidth(">" + font);

                                if (moduleElement.getSettingsWidth() < textWidth2 + 12.0F) {
                                    moduleElement.setSettingsWidth(textWidth2 + 12.0F);
                                }

                                if (listvalue.openList) {
                                    RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                                    if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                        listvalue.set(font);
                                        NullStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                                    }

                                    GlStateManager.resetColor();
                                    Fonts.fontSFUI35.drawString(">", moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Integer.MAX_VALUE);
                                    Fonts.fontSFUI35.drawString(font, moduleElement.getX() + moduleElement.getWidth() + 14, yPos + 4, listvalue.get() != null && ((String) listvalue.get()).equalsIgnoreCase(font) ? guiColor : Integer.MAX_VALUE);
                                    yPos += 12;
                                }
                            }
                        } else {
                            float f1;
                            double d0;

                            if (value instanceof FloatValue) {
                                FloatValue floatvalue = (FloatValue) value;

                                s = value.getName() + "§f: §c" + this.round(((Float) floatvalue.get()).floatValue());
                                f = (float) Fonts.fontSFUI35.getStringWidth(s);
                                if (moduleElement.getSettingsWidth() < f + 8.0F) {
                                    moduleElement.setSettingsWidth(f + 8.0F);
                                }

                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 24), Integer.MIN_VALUE);
                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 8), (float) (yPos + 18), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - 4.0F, (float) (yPos + 19), Integer.MAX_VALUE);
                                f1 = (float) (moduleElement.getX() + moduleElement.getWidth()) + (moduleElement.getSettingsWidth() - 12.0F) * (((Float) floatvalue.get()).floatValue() - floatvalue.getMinimum()) / (floatvalue.getMaximum() - floatvalue.getMinimum());
                                RenderUtils.drawRect(8.0F + f1, (float) (yPos + 15), f1 + 11.0F, (float) (yPos + 21), guiColor);
                                if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - 4.0F && mouseY >= yPos + 15 && mouseY <= yPos + 21 && Mouse.isButtonDown(0)) {
                                    d0 = (double) MathHelper.clamp((float) (mouseX - moduleElement.getX() - moduleElement.getWidth() - 8) / (moduleElement.getSettingsWidth() - 12.0F), 0.0F, 1.0F);
                                    floatvalue.set((Object) Float.valueOf(this.round((float) ((double) floatvalue.getMinimum() + (double) (floatvalue.getMaximum() - floatvalue.getMinimum()) * d0)).floatValue()));
                                }

                                GlStateManager.resetColor();
                                Fonts.fontSFUI35.drawString(s, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                                yPos += 22;
                            } else if (value instanceof IntegerValue) {
                                IntegerValue integervalue = (IntegerValue) value;

                                s = value.getName() + "§f: §c" + (value instanceof BlockValue ? BlockUtils.getBlockName(((Integer) integervalue.get()).intValue()) + " (" + integervalue.get() + ")" : (Serializable) integervalue.get());
                                f = (float) Fonts.fontSFUI35.getStringWidth(s);
                                if (moduleElement.getSettingsWidth() < f + 8.0F) {
                                    moduleElement.setSettingsWidth(f + 8.0F);
                                }

                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 24), Integer.MIN_VALUE);
                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 8), (float) (yPos + 18), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - 4.0F, (float) (yPos + 19), Integer.MAX_VALUE);
                                f1 = (float) (moduleElement.getX() + moduleElement.getWidth()) + (moduleElement.getSettingsWidth() - 12.0F) * (float) (((Integer) integervalue.get()).intValue() - integervalue.getMinimum()) / (float) (integervalue.getMaximum() - integervalue.getMinimum());
                                RenderUtils.drawRect(8.0F + f1, (float) (yPos + 15), f1 + 11.0F, (float) (yPos + 21), guiColor);
                                if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 15 && mouseY <= yPos + 21 && Mouse.isButtonDown(0)) {
                                    d0 = (double) MathHelper.clamp((float) (mouseX - moduleElement.getX() - moduleElement.getWidth() - 8) / (moduleElement.getSettingsWidth() - 12.0F), 0.0F, 1.0F);
                                    integervalue.set((Object) Integer.valueOf((int) ((double) integervalue.getMinimum() + (double) (integervalue.getMaximum() - integervalue.getMinimum()) * d0)));
                                }

                                GlStateManager.resetColor();
                                Fonts.fontSFUI35.drawString(s, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                                yPos += 22;
                            } else if (value instanceof FontValue) {
                                FontValue fontvalue = (FontValue) value;
                                IFontRenderer ifontrenderer = (IFontRenderer) fontvalue.get();

                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
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

                                Fonts.fontSFUI35.drawString(s1, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Color.WHITE.getRGB());
                                int i = Fonts.fontSFUI35.getStringWidth(s1);

                                if (moduleElement.getSettingsWidth() < (float) (i + 8)) {
                                    moduleElement.setSettingsWidth((float) (i + 8));
                                }

                                if ((Mouse.isButtonDown(0) && !this.mouseDown || Mouse.isButtonDown(1) && !this.rightMouseDown) && mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 4 && mouseY <= yPos + 12) {
                                    List list = Fonts.getFonts();
                                    IFontRenderer ifontrenderer1;

                                    if (Mouse.isButtonDown(0)) {
                                        for (i = 0; i < list.size(); ++i) {
                                            ifontrenderer1 = (IFontRenderer) list.get(i);
                                            if (ifontrenderer1 == ifontrenderer) {
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
                                            if (ifontrenderer1 == ifontrenderer) {
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

                                yPos += 11;
                            } else {
                                text = value.getName() + "§f: §c" + value.get();
                                textWidth = (float) Fonts.fontSFUI35.getStringWidth(text);
                                if (moduleElement.getSettingsWidth() < textWidth + 8.0F) {
                                    moduleElement.setSettingsWidth(textWidth + 8.0F);
                                }

                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                                GlStateManager.resetColor();
                                Fonts.fontSFUI35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                                yPos += 12;
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
                if (moduleElement.getSettingsWidth() > 0.0F && yPos > moduleElement.getY() + 4) {
                    RenderUtils.drawBorderedRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (moduleElement.getY() + 6), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 2), 1.0F, Integer.MIN_VALUE, 0);
                }
            }
        }

    }

    private BigDecimal round(float f) {
        BigDecimal bd = new BigDecimal(Float.toString(f));

        bd = bd.setScale(2, 4);
        return bd;
    }
}
