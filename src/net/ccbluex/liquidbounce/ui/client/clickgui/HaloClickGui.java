package net.ccbluex.liquidbounce.ui.client.clickgui;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class HaloClickGui extends GuiScreen {

    public static ModuleCategory selectCategory = ModuleCategory.COMBAT;
    public static int CategoryIndex = 0;
    public static int ModuleIndex = 0;
    public static int CategoryY;
    public static int ModuleY;
    private boolean mouse_Down;
    private boolean mouse_Downing;
    private boolean mouse_Down_R;
    private boolean mouse_Downing_R;
    private int mX;
    private int mY;

    public void initGui() {
        HaloClickGui.CategoryY = this.height / 2 - 120 + 105;
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.mX = mouseX;
        this.mY = mouseY;
        int midX = this.width / 2;
        int midY = this.height / 2;
        int left = midX - 180;
        int right = midX + 180;
        int top = midY - 120;
        int under = midY + 120;

        if (Mouse.isButtonDown(0) && mouseX >= 5 && mouseX <= 50 && mouseY <= this.height - 5 && mouseY >= this.height - 50) {
            MinecraftInstance.classProvider.isGuiHudDesigner(this.mc.currentScreen);
        }

        RenderUtils.drawRoundRect((float) (midX - 180), (float) (midY - 120), (float) (midX + 180), (float) (midY + 120), 17.0F, (new Color(255, 255, 255)).getRGB());
        RenderUtils.drawRoundRect((float) left, (float) (midY - 120), (float) (left + 80), (float) (midY + 120), 17.0F, (new Color(250, 250, 250)).getRGB());
        RenderUtils.drawImage(new ResourceLocation("liquidbounce/singleplayer.png"), left + 10, top + 25, 60, 60);
        if (Mouse.isButtonDown(0) && !this.mouse_Downing) {
            this.mouse_Down = true;
            this.mouse_Downing = true;
        }

        if (Mouse.isButtonDown(1) && !this.mouse_Downing_R) {
            this.mouse_Down_R = true;
            this.mouse_Downing_R = true;
        }

        GL11.glEnable(3089);
        RenderUtils.makeScissorBox((float) left, (float) (top + 105 - 8), (float) (left + 80), (float) (top + 105 + (Fonts.font40.getFontHeight() + 10) * 4 - 1));
        int dWheel = Mouse.getDWheel();

        if (this.mouseIn(left, top, left + 80, under) && Mouse.hasWheel()) {
            if (dWheel < 0 && HaloClickGui.CategoryIndex < ModuleCategory.values().length) {
                ++HaloClickGui.CategoryIndex;
            }

            if (dWheel > 0 && HaloClickGui.CategoryIndex > 0) {
                --HaloClickGui.CategoryIndex;
            }

            HaloClickGui.CategoryY = (int) AnimationUtils.animate((double) (top + 105 - HaloClickGui.CategoryIndex * (Fonts.font40.getFontHeight() + 10)), (double) HaloClickGui.CategoryY, 0.5D);
        }

        int catY = HaloClickGui.CategoryY;

        int leftModule;

        for (leftModule = 0; leftModule < ModuleCategory.values().length; ++leftModule) {
            ModuleCategory modY = ModuleCategory.values()[leftModule];
            boolean fontColor = true;

            if (modY == HaloClickGui.selectCategory) {
                fontColor = false;
                RenderUtils.drawRect(left, catY - 5, left + 80, catY + Fonts.font40.getFontHeight() + 1, (new Color(255, 160, 200)).getRGB());
            }

            if (this.mouseIn(left, catY - 8, left + 80, catY + Fonts.font40.getFontHeight() + 4) && this.mouse_Down && HaloClickGui.selectCategory != modY && catY + Fonts.font35.getFontHeight() + 10 > top + 105 && catY < under) {
                HaloClickGui.selectCategory = modY;
                HaloClickGui.ModuleIndex = 0;
                HaloClickGui.ModuleY = top + 15;
            }

            Fonts.font40.drawCenteredString(modY.getDisplayName(), (float) (left + 40), (float) catY, (new Color(0, 0, 0)).getRGB(), false);
            catY += Fonts.font40.getFontHeight() + 10;
        }

        GL11.glDisable(3089);
        GL11.glEnable(3089);
        RenderUtils.makeScissorBox(0.0F, (float) (top + 15), (float) this.width, (float) (under - 15));
        leftModule = left + 95;
        if (this.mouseIn(leftModule, top + 15, right - 15, under - 15) & Mouse.hasWheel()) {
            if (dWheel < 0) {
                ++HaloClickGui.ModuleIndex;
            }

            if (dWheel > 0 && HaloClickGui.ModuleIndex > 0) {
                --HaloClickGui.ModuleIndex;
            }

            HaloClickGui.ModuleY = (int) AnimationUtils.animate((double) (top + 15 - 26 * HaloClickGui.ModuleIndex), (double) HaloClickGui.ModuleY, 0.5D);
        }

        int i = HaloClickGui.ModuleY;

        for (Iterator iterator = LiquidBounce.moduleManager.getModuleInCategory(HaloClickGui.selectCategory).iterator(); iterator.hasNext(); i += 35) {
            Module module = (Module) iterator.next();
            int valueSize = module.getOpenList() ? module.getValues().size() : 0;
            int addHigh = (Fonts.font30.getFontHeight() + 5) * valueSize;

            RenderUtils.drawRoundRect((float) leftModule, (float) i, (float) (right - 15), (float) (i + 26 + addHigh), 2.5F, (new Color(250, 250, 250, 200)).getRGB());
            int color = 0;

            if (module.getState()) {
                color = (new Color(255, 160, 200)).getRGB();
            }

            if (this.mouseIn(leftModule, i, right - 15, i + 26) && i + 26 > top + 15 && i < under - 15) {
                if (this.mouse_Down) {
                    module.setState(!module.getState());
                }

                if (this.mouse_Down_R && !module.getValues().isEmpty()) {
                    module.setOpenList(!module.getOpenList());
                }
            }

            Fonts.fontSFUI35.drawString(module.getName(), leftModule + 10, i + 13 - Fonts.fontSFUI35.getFontHeight() / 2 + 1, color);
            int valueY = i + 28;

            if (module.getOpenList()) {
                Iterator iterator1 = module.getValues().iterator();

                while (iterator1.hasNext()) {
                    Value value = (Value) iterator1.next();

                    if (value.display()) {
                        Fonts.font30.drawString(value.getName(), leftModule + 15, valueY, 0);
                        if (value instanceof IntegerValue) {
                            IntegerValue fontValue = (IntegerValue) value;
                            String fontRenderer = value instanceof BlockValue ? BlockUtils.getBlockName(((Integer) fontValue.get()).intValue()) + " (" + fontValue.get() + ")" : ((Integer) fontValue.get()).toString();
                            float displayString = this.drawSlider((float) ((Integer) fontValue.get()).intValue(), (float) fontValue.getMinimum(), (float) fontValue.getMaximum(), right - 18 - 100, valueY - 1, 96, new Color(255, 160, 200));

                            if (displayString != (float) ((Integer) fontValue.get()).intValue()) {
                                fontValue.set((Number) Float.valueOf(displayString));
                            }

                            Fonts.font30.drawString(fontRenderer + " - ", right - 18 - 100 - 2 - Fonts.font30.getStringWidth(fontRenderer + " - "), valueY - 1, 0);
                        }

                        if (value instanceof FloatValue) {
                            FloatValue floatvalue = (FloatValue) value;
                            float f = this.drawSlider(((Float) floatvalue.get()).floatValue(), floatvalue.getMinimum(), floatvalue.getMaximum(), right - 18 - 100, valueY, 96, new Color(255, 160, 200));

                            if (f != ((Float) floatvalue.get()).floatValue()) {
                                floatvalue.set((Object) Float.valueOf(f));
                            }

                            Fonts.font30.drawString(((Float) floatvalue.get()).toString() + " - ", right - 18 - 100 - 2 - Fonts.font30.getStringWidth(((Float) floatvalue.get()).toString() + " - "), valueY, 0);
                        }

                        int i;

                        if (value instanceof ListValue) {
                            ListValue listvalue = (ListValue) value;

                            Fonts.font30.drawString((String) listvalue.get(), right - 18 - Fonts.font30.getStringWidth((String) listvalue.get()), valueY, 0);
                            if (this.mouseIn(leftModule + 15, valueY - 2, right - 15, valueY + 6) && this.mouse_Down) {
                                listvalue.openList = !listvalue.openList;
                            }

                            if (listvalue.openList) {
                                int j = valueY;
                                String[] astring = listvalue.getValues();
                                int fonts = astring.length;

                                for (i = 0; i < fonts; ++i) {
                                    String font = astring[i];

                                    Fonts.font30.drawStringWithShadow(font, right + 25, j, 0);
                                    if (this.mouseIn(right + 25, j - 1, right + 80, j + Fonts.font30.getFontHeight() + 2) && this.mouse_Down && j + Fonts.font30.getFontHeight() + 5 > top && j < under) {
                                        listvalue.set(font);
                                    }

                                    j += Fonts.font30.getFontHeight() + 5;
                                }
                            }
                        }

                        if (value instanceof BoolValue) {
                            int k = -1;

                            if (((Boolean) ((BoolValue) value).get()).booleanValue()) {
                                k = (new Color(255, 160, 200)).getRGB();
                            }

                            RenderUtils.drawRoundRect((float) (right - 26), (float) (valueY - 2), (float) (right - 18), (float) (valueY + 6), 1.0F, k);
                            if (this.mouseIn(right - 26, valueY - 2, right - 18, valueY + 6) && valueY > top + 15 && valueY < under - 15 && this.mouse_Down) {
                                ((BoolValue) value).set(Boolean.valueOf(!((Boolean) ((BoolValue) value).get()).booleanValue()));
                            }
                        }

                        if (value instanceof FontValue) {
                            FontValue fontvalue = (FontValue) value;
                            IFontRenderer ifontrenderer = (IFontRenderer) fontvalue.get();
                            String s = "Font: Unknown";

                            if (ifontrenderer.isGameFontRenderer()) {
                                GameFontRenderer gamefontrenderer = ifontrenderer.getGameFontRenderer();

                                s = "Font: " + gamefontrenderer.getDefaultFont().getFont().getName() + " - " + gamefontrenderer.getDefaultFont().getFont().getSize();
                            } else if (ifontrenderer == Fonts.minecraftFont) {
                                s = "Font: Minecraft";
                            } else {
                                Fonts.FontInfo fonts_fontinfo = Fonts.getFontDetails(ifontrenderer);

                                if (fonts_fontinfo != null) {
                                    s = fonts_fontinfo.getName() + (fonts_fontinfo.getFontSize() != -1 ? " - " + fonts_fontinfo.getFontSize() : "");
                                }
                            }

                            Fonts.font30.drawString(s, right - 18 - Fonts.font30.getStringWidth(s), valueY, 0);
                            if (this.mouseIn(leftModule + 15, valueY - 2, right - 18, valueY + 6) && valueY > top + 15 && valueY < under - 15 && this.mouse_Down) {
                                List list = Fonts.getFonts();

                                for (i = 0; i < list.size(); ++i) {
                                    FontRenderer fontrenderer = (FontRenderer) list.get(i);

                                    if (fontrenderer == ifontrenderer) {
                                        ++i;
                                        if (i >= list.size()) {
                                            i = 0;
                                        }

                                        fontvalue.set(list.get(i));
                                        break;
                                    }
                                }
                            }
                        }

                        if (value instanceof TextValue) {
                            Fonts.font30.drawString((String) ((TextValue) value).get(), right - 18 - Fonts.font30.getStringWidth((String) ((TextValue) value).get()), valueY, (new Color(165, 45, 65)).getRGB());
                        }

                        valueY += Fonts.font30.getFontHeight() + 5;
                        i += Fonts.font30.getFontHeight() + 5;
                    }
                }
            }
        }

        GL11.glDisable(3089);
        if (this.mouse_Down) {
            this.mouse_Down = false;
        }

        if (this.mouse_Down_R) {
            this.mouse_Down_R = false;
        }

        if (!Mouse.isButtonDown(0)) {
            this.mouse_Downing = false;
        }

        if (!Mouse.isButtonDown(1)) {
            this.mouse_Downing_R = false;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private boolean mouseIn(int x, int y, int x1, int y2) {
        return this.mX > x && this.mX < x1 && this.mY > y && this.mY < y2;
    }

    public float drawSlider(float value, float min, float max, int x, int y, int width, Color color) {
        float displayValue = Math.max(min, Math.min(value, max));
        float sliderValue = (float) x + (float) width * (displayValue - min) / (max - min);

        RenderUtils.drawRoundRect((float) x, (float) y, (float) (x + width), (float) (y + 2), 0.5F, Integer.MAX_VALUE);
        RenderUtils.drawRoundRect((float) x, (float) y, sliderValue, (float) (y + 2), 0.5F, color.getRGB());
        RenderUtils.drawFilledCircle((int) sliderValue, y + 1, 3.0F, color);
        if (this.mX >= x && this.mX <= x + width && this.mY >= y && this.mY <= y + 3 && Mouse.isButtonDown(0)) {
            double i = MathHelper.clamp(((double) this.mX - (double) x) / ((double) width - 3.0D), 0.0D, 1.0D);
            BigDecimal bigDecimal = new BigDecimal(Double.toString((double) min + (double) (max - min) * i));

            bigDecimal = bigDecimal.setScale(2, 4);
            return bigDecimal.floatValue();
        } else {
            return value;
        }
    }
}
