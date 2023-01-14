package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class LiquidBounceStyle extends Style {

    private boolean mouseDown;
    private boolean rightMouseDown;

    public void drawPanel(int mouseX, int mouseY, Panel panel) {
        RenderUtils.drawBorderedRect((float) panel.getX(), (float) panel.getY() - 3.0F, (float) panel.getX() + (float) panel.getWidth(), (float) panel.getY() + 17.0F, 3.0F, (new Color(97, 95, 95, 34)).getRGB(), (new Color(20, 20, 20, 174)).getRGB());
        float textWidth = (float) Fonts.font35.getStringWidth("§f" + StringUtils.stripControlCodes(panel.getName()));

        Fonts.font35.drawString("§f" + panel.getName(), (int) ((float) panel.getX() - (textWidth - 100.0F) / 2.0F), panel.getY() + 7, -16777216);
        if (panel.getScrollbar() && panel.getFade() > 0) {
            RenderUtils.drawRect((float) (panel.getX() - 2), (float) (panel.getY() + 21), (float) panel.getX(), (float) (panel.getY() + 16 + panel.getFade()), Integer.MAX_VALUE);
            RenderUtils.drawRect((float) (panel.getX() - 2), (float) (panel.getY() + 30) + ((float) panel.getFade() - 24.0F) / (float) (panel.getElements().size() - ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue()) * (float) panel.getDragged() - 10.0F, (float) panel.getX(), (float) (panel.getY() + 40) + ((float) panel.getFade() - 24.0F) / (float) (panel.getElements().size() - ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue()) * (float) panel.getDragged(), Integer.MIN_VALUE);
            RenderUtils.drawBorderedRect((float) panel.getX(), (float) panel.getY() + 17.0F, (float) panel.getX() + (float) panel.getWidth(), (float) (panel.getY() + 19 + panel.getFade()), 3.0F, (new Color(33, 33, 33, 65)).getRGB(), (new Color(29, 29, 29, 86)).getRGB());
            RenderUtils.drawBorderedRect((float) panel.getX(), (float) (panel.getY() + 17 + panel.getFade()), (float) panel.getX() + (float) panel.getWidth(), (float) (panel.getY() + 19 + panel.getFade() + 5), 3.0F, (new Color(47, 46, 46, 0)).getRGB(), (new Color(20, 20, 20, 0)).getRGB());
        }

    }

    public void drawDescription(int mouseX, int mouseY, String text) {
        int textWidth = Fonts.font35.getStringWidth(text);

        RenderUtils.drawBorderedRect((float) (mouseX + 9), (float) mouseY, (float) (mouseX + textWidth + 14), (float) (mouseY + Fonts.fontSFUI35.getFontHeight() + 3), 1.0F, (new Color(255, 255, 255, 89)).getRGB(), Integer.MIN_VALUE);
        GlStateManager.resetColor();
        Fonts.font35.drawString(text, mouseX + 12, mouseY + Fonts.font35.getFontHeight() / 2, Integer.MAX_VALUE);
    }

    public void drawButtonElement(int mouseX, int mouseY, ButtonElement buttonElement) {
        GlStateManager.resetColor();
        Fonts.font35.drawString(buttonElement.getDisplayName(), (int) ((float) buttonElement.getX() - ((float) Fonts.font35.getStringWidth(buttonElement.getDisplayName()) - 100.0F) / 2.0F), buttonElement.getY() + 6, buttonElement.getColor());
    }

    public void drawModuleElement(int mouseX, int mouseY, ModuleElement moduleElement) {
        int guiColor = ClickGUI.generateColor().getRGB();

        GlStateManager.resetColor();
        Fonts.font35.drawString(moduleElement.getDisplayName(), (int) ((float) moduleElement.getX() - ((float) Fonts.font35.getStringWidth(moduleElement.getDisplayName()) - 100.0F) / 2.0F), moduleElement.getY() + 6, moduleElement.getModule().getState() ? guiColor : Integer.MAX_VALUE);
        List moduleValues = moduleElement.getModule().getValues();

        if (!moduleValues.isEmpty()) {
            Fonts.font35.drawString("+", moduleElement.getX() + moduleElement.getWidth() - 8, moduleElement.getY() + moduleElement.getHeight() / 2, Color.WHITE.getRGB());
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
                        textWidth = (float) Fonts.font35.getStringWidth(text);
                        if (moduleElement.getSettingsWidth() < textWidth + 8.0F) {
                            moduleElement.setSettingsWidth(textWidth + 8.0F);
                        }

                        RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                        if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                            BoolValue displayString = (BoolValue) value;

                            displayString.set(Boolean.valueOf(!((Boolean) displayString.get()).booleanValue()));
                            LiquidBounceStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                        GlStateManager.resetColor();
                        Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, ((Boolean) ((BoolValue) value).get()).booleanValue() ? guiColor : Integer.MAX_VALUE);
                        yPos += 12;
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

                            RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                            GlStateManager.resetColor();
                            Fonts.font35.drawString("§c" + s, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                            Fonts.font35.drawString(listvalue.openList ? "-" : "+", (int) ((float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - (float) (listvalue.openList ? 5 : 6)), yPos + 4, 16777215);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                listvalue.openList = !listvalue.openList;
                                LiquidBounceStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                            }

                            yPos += 12;
                            String[] stringWidth = listvalue.getValues();
                            int fonts = stringWidth.length;

                            for (i = 0; i < fonts; ++i) {
                                String font = stringWidth[i];
                                float textWidth2 = (float) Fonts.font35.getStringWidth(">" + font);

                                if (moduleElement.getSettingsWidth() < textWidth2 + 8.0F) {
                                    moduleElement.setSettingsWidth(textWidth2 + 8.0F);
                                }

                                if (listvalue.openList) {
                                    RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                                    if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                        listvalue.set(font);
                                        LiquidBounceStyle.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                                    }

                                    GlStateManager.resetColor();
                                    Fonts.font35.drawString(">", moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Integer.MAX_VALUE);
                                    Fonts.font35.drawString(font, moduleElement.getX() + moduleElement.getWidth() + 14, yPos + 4, listvalue.get() != null && ((String) listvalue.get()).equalsIgnoreCase(font) ? guiColor : Integer.MAX_VALUE);
                                    yPos += 12;
                                }
                            }
                        } else {
                            float f1;
                            double d0;

                            if (value instanceof FloatValue) {
                                FloatValue floatvalue = (FloatValue) value;

                                s = value.getName() + "§f: §c" + this.round(((Float) floatvalue.get()).floatValue());
                                f = (float) Fonts.font35.getStringWidth(s);
                                if (moduleElement.getSettingsWidth() < f + 8.0F) {
                                    moduleElement.setSettingsWidth(f + 8.0F);
                                }

                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 24), Integer.MIN_VALUE);
                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 8), (float) (yPos + 18), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - 4.0F, (float) (yPos + 19), Integer.MAX_VALUE);
                                f1 = (float) (moduleElement.getX() + moduleElement.getWidth()) + (moduleElement.getSettingsWidth() - 12.0F) * (((Float) floatvalue.get()).floatValue() - floatvalue.getMinimum()) / (floatvalue.getMaximum() - floatvalue.getMinimum());
                                RenderUtils.drawRect(8.0F + f1, (float) (yPos + 15), f1 + 11.0F, (float) (yPos + 21), guiColor);
                                if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - 4.0F && mouseY >= yPos + 15 && mouseY <= yPos + 21 && Mouse.isButtonDown(0)) {
                                    d0 = WMathHelper.clamp_double((double) ((float) (mouseX - moduleElement.getX() - moduleElement.getWidth() - 8) / (moduleElement.getSettingsWidth() - 12.0F)), 0.0D, 1.0D);
                                    floatvalue.set((Object) Float.valueOf(this.round((float) ((double) floatvalue.getMinimum() + (double) (floatvalue.getMaximum() - floatvalue.getMinimum()) * d0)).floatValue()));
                                }

                                GlStateManager.resetColor();
                                Fonts.font35.drawString(s, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                                yPos += 22;
                            } else if (value instanceof IntegerValue) {
                                IntegerValue integervalue = (IntegerValue) value;

                                s = value.getName() + "§f: §c" + (value instanceof BlockValue ? BlockUtils.getBlockName(((Integer) integervalue.get()).intValue()) + " (" + integervalue.get() + ")" : (Serializable) integervalue.get());
                                f = (float) Fonts.font35.getStringWidth(s);
                                if (moduleElement.getSettingsWidth() < f + 8.0F) {
                                    moduleElement.setSettingsWidth(f + 8.0F);
                                }

                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 24), Integer.MIN_VALUE);
                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 8), (float) (yPos + 18), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() - 4.0F, (float) (yPos + 19), Integer.MAX_VALUE);
                                f1 = (float) (moduleElement.getX() + moduleElement.getWidth()) + (moduleElement.getSettingsWidth() - 12.0F) * (float) (((Integer) integervalue.get()).intValue() - integervalue.getMinimum()) / (float) (integervalue.getMaximum() - integervalue.getMinimum());
                                RenderUtils.drawRect(8.0F + f1, (float) (yPos + 15), f1 + 11.0F, (float) (yPos + 21), guiColor);
                                if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 15 && mouseY <= yPos + 21 && Mouse.isButtonDown(0)) {
                                    d0 = WMathHelper.clamp_double((double) ((float) (mouseX - moduleElement.getX() - moduleElement.getWidth() - 8) / (moduleElement.getSettingsWidth() - 12.0F)), 0.0D, 1.0D);
                                    integervalue.set((Object) Integer.valueOf((int) ((double) integervalue.getMinimum() + (double) (integervalue.getMaximum() - integervalue.getMinimum()) * d0)));
                                }

                                GlStateManager.resetColor();
                                Fonts.font35.drawString(s, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
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

                                Fonts.font35.drawString(s1, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Color.WHITE.getRGB());
                                int i = Fonts.font35.getStringWidth(s1);

                                if (moduleElement.getSettingsWidth() < (float) (i + 8)) {
                                    moduleElement.setSettingsWidth((float) (i + 8));
                                }

                                if ((Mouse.isButtonDown(0) && !this.mouseDown || Mouse.isButtonDown(1) && !this.rightMouseDown) && mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && (float) mouseX <= (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth() && mouseY >= yPos + 4 && mouseY <= yPos + 12) {
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

                                yPos += 11;
                            } else {
                                text = value.getName() + "§f: §c" + value.get();
                                textWidth = (float) Fonts.font35.getStringWidth(text);
                                if (moduleElement.getSettingsWidth() < textWidth + 8.0F) {
                                    moduleElement.setSettingsWidth(textWidth + 8.0F);
                                }

                                RenderUtils.drawRect((float) (moduleElement.getX() + moduleElement.getWidth() + 4), (float) (yPos + 2), (float) (moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth(), (float) (yPos + 14), Integer.MIN_VALUE);
                                GlStateManager.resetColor();
                                Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
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
