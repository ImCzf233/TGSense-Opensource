package me.Skid.Jello;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;
import me.Skid.Tfont.FontLoaders;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Jello extends WrappedGuiScreen {

    ArrayList categories = new ArrayList();
    public float lastPercent;
    public float percent;
    public float percent2;
    public final Opacity smooth = new Opacity(0);
    public float outro;
    public final Translate translate = new Translate(0.0F, 0.0F);
    public float lastOutro;
    public float lastPercent2;
    boolean close;
    int mouseWheel;
    boolean mouseClicked;
    boolean mouseClicked2;
    double ani;
    HashMap hashMap = new HashMap();

    public Jello() {
        int x = 20;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        ModuleCategory[] amodulecategory = ModuleCategory.values();
        int i = amodulecategory.length;

        for (int j = 0; j < i; ++j) {
            ModuleCategory moduleCategory = amodulecategory[j];

            this.categories.add(new Category(moduleCategory, x, 20, 100, 175));
            x += 110;
            this.loadClickGui();
        }

        this.Savevalue();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.start();
        int wheel = Mouse.getDWheel();

        this.categories.forEach((category) -> {
            category.setWheel(wheel);
        });
        this.categories.forEach((category) -> {
            category.draw(mouseX, mouseY);
        });
        if (!this.isHovered((float) this.representedScreen.getWidth() / 2.0F - 100.0F, (float) this.representedScreen.getHeight() / 2.0F - 130.0F, (float) this.representedScreen.getWidth() / 2.0F + 100.0F, (float) this.representedScreen.getHeight() / 2.0F + 130.0F, mouseX, mouseY) && LiquidBounce.INSTANCE.getModule() != null && Mouse.isButtonDown(0)) {
            this.SaveMouseWheel();
            LiquidBounce.INSTANCE.getModule().showSettings = false;
            LiquidBounce.INSTANCE.setModule((Module) null);
        }

        if (LiquidBounce.INSTANCE.getModule() != null && LiquidBounce.INSTANCE.getModule().showSettings) {
            RenderUtils.drawRect(0.0F, 0.0F, (float) this.representedScreen.getWidth(), (float) this.representedScreen.getHeight(), ClientUtils.reAlpha(Colors.BLACK.c, 0.45F));
        }

        if (LiquidBounce.INSTANCE.getModule() != null && LiquidBounce.INSTANCE.getModule().showSettings) {
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            int width = scaledResolution.getScaledWidth();
            int height = scaledResolution.getScaledHeight();

            if (LiquidBounce.INSTANCE.getModule().getHovvv()) {
                this.loadWheel();
                LiquidBounce.INSTANCE.getModule().setHovvv(false);
            }

            RenderUtils.drawBorderedRect((float) width / 2.0F - 100.0F, (float) height / 2.0F - 130.0F, (float) width / 2.0F + 100.0F, (float) height / 2.0F + 130.0F, 2.0F, Colors.WHITE.c, Colors.WHITE.c);
            FontLoaders.JelloTitle18.drawString(LiquidBounce.INSTANCE.getModule().getName(), (float) width / 2.0F - 100.0F, (float) height / 2.0F - 140.0F, -1);
            float y = (float) height / 2.0F - 130.0F;

            GL11.glPushMatrix();
            GL11.glEnable(3089);
            RenderUtils.doGlScissor((int) ((float) width / 2.0F - 100.0F), (int) ((float) height / 2.0F - 130.0F), (int) ((float) width / 2.0F + 100.0F), (int) ((float) height / 2.0F + 10.0F));
            float valueY = y + 5.0F + this.translate.getY();

            for (Iterator moduleHeight = LiquidBounce.INSTANCE.getModule().getValues().iterator(); moduleHeight.hasNext(); valueY += 20.0F) {
                Value value = (Value) moduleHeight.next();

                value.getValueTranslate().interpolate(0.0F, valueY, 0.1D);
                float valuePosY = value.getValueTranslate().getY();
                float modeOffset = valuePosY + 7.0F;
                float fontOffset = modeOffset + 1.0F;

                if (value instanceof ListValue) {
                    FontLoaders.JelloList16.drawString(((ListValue) value).getName(), (float) width / 2.0F - 99.0F, valuePosY, (new Color(0, 0, 0)).getRGB());
                    RenderUtils.drawBorderedRect((float) ((int) ((float) width / 2.0F + 60.0F)), valuePosY - 2.0F, (float) ((int) ((float) width / 2.0F + 99.0F)), valuePosY + 6.0F, 2.0F, (new Color(100, 100, 100)).getRGB(), (new Color(100, 100, 100)).getRGB());
                    FontLoaders.JelloList16.drawString((String) ((ListValue) value).get(), (float) ((int) ((float) width / 2.0F + 60.0F)), valuePosY, -1);
                    if (this.isHovered((float) width / 2.0F - 100.0F, (float) height / 2.0F - 130.0F, (float) width / 2.0F + 100.0F, (float) height / 2.0F + 130.0F, mouseX, mouseY) && this.isHovered((float) ((int) ((float) width / 2.0F + 60.0F)), valuePosY - 2.0F, (float) ((int) ((float) width / 2.0F + 99.0F)), valuePosY + 6.0F, mouseX, mouseY)) {
                        if (Mouse.isButtonDown(1)) {
                            if (!this.mouseClicked) {
                                ((ListValue) value).listtoggle();
                            }

                            this.mouseClicked = true;
                        } else {
                            this.mouseClicked = false;
                        }
                    }

                    if (!((ListValue) value).openList) {
                        Stencil.write(false);
                        Stencil.erase(false);
                        this.drawTexturedRect((float) ((int) ((float) width / 2.0F + 93.0F)), (float) ((int) valuePosY + 1), 4.0F, 4.0F, "selectedAltTriangle", scaledResolution);
                        Stencil.dispose();
                        GlStateManager.disableAlpha();
                        GlStateManager.enableBlend();
                        GL11.glEnable(3042);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        Stencil.write(false);
                        Stencil.erase(true);
                        Stencil.dispose();
                    } else {
                        String[] posX = ((ListValue) value).getValues();
                        int max = posX.length;

                        for (int i = 0; i < max; ++i) {
                            String optionDouble = posX[i];

                            this.ani = RenderUtils.getAnimationState((double) ((float) this.ani), (double) (7 * ((ListValue) value).getValues().length), 100.0D);
                            RenderUtils.drawRect((float) ((int) ((float) width / 2.0F + 60.0F)), modeOffset, (float) ((int) ((float) width / 2.0F + 99.0F)), (float) ((double) modeOffset + this.ani), (new Color(100, 100, 100)).getRGB());
                            if (!Objects.equals(optionDouble, ((ListValue) value).get())) {
                                FontLoaders.JelloList16.drawString(optionDouble, (float) ((int) ((float) width / 2.0F + 60.0F)), fontOffset, -1);
                                if (this.isHovered((float) ((int) ((float) width / 2.0F + 60.0F)), fontOffset - 5.0F, (float) ((int) ((float) width / 2.0F + 99.0F)), fontOffset + 5.0F, mouseX, mouseY)) {
                                    if (Mouse.isButtonDown(0)) {
                                        if (!this.mouseClicked2) {
                                            value.set(optionDouble);
                                        }

                                        ((ListValue) value).openList = false;
                                        this.mouseClicked2 = true;
                                    } else {
                                        this.mouseClicked2 = false;
                                    }
                                }

                                fontOffset += 12.0F;
                                modeOffset += 12.0F;
                                valueY += 12.0F;
                            }
                        }
                    }
                }

                if (value instanceof BoolValue) {
                    if (((Boolean) ((BoolValue) value).get()).booleanValue()) {
                        RenderUtils.drawImage(LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("langya/checked.png"), (int) ((float) width / 2.0F + 85.0F), (int) valuePosY - 3, 12, 12);
                    } else {
                        RenderUtils.drawImage(LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("langya/unchecked.png"), (int) ((float) width / 2.0F + 85.0F), (int) valuePosY - 3, 12, 12);
                    }

                    if (this.isHovered((float) width / 2.0F - 100.0F, (float) height / 2.0F - 130.0F, (float) width / 2.0F + 100.0F, (float) height / 2.0F + 130.0F, mouseX, mouseY) && this.isHovered((float) ((int) ((float) width / 2.0F + 85.0F)), valuePosY - 5.0F, (float) ((int) ((float) width / 2.0F + 100.0F)), valuePosY + 10.0F, mouseX, mouseY)) {
                        if (Mouse.isButtonDown(0)) {
                            if (!this.mouseClicked) {
                                ((BoolValue) value).toggle();
                            }

                            this.mouseClicked = true;
                        } else {
                            this.mouseClicked = false;
                        }
                    }

                    FontLoaders.JelloList16.drawString(((BoolValue) value).getName(), (float) width / 2.0F - 99.0F, valuePosY, (new Color(0, 0, 0)).getRGB());
                }

                float f;
                double d0;

                if (value instanceof IntegerValue) {
                    f = (float) width / 2.0F + 35.0F;
                    d0 = Math.max(0.0D, (double) ((float) mouseX - (f + 8.0F)) / 52.0D);
                    FontLoaders.JelloList16.drawString(value.getName(), (float) width / 2.0F - 99.0F, valuePosY, (new Color(0, 0, 0)).getRGB());
                    IntegerValue integervalue = (IntegerValue) value;

                    integervalue.getTranslate().interpolate(52.0F * (float) (((Integer) integervalue.get()).intValue() > integervalue.getMaximum() ? integervalue.getMaximum() : (((Integer) integervalue.get()).intValue() < integervalue.getMinimum() ? 0 : ((Integer) integervalue.get()).intValue() - integervalue.getMinimum())) / (float) (integervalue.getMaximum() - integervalue.getMinimum()) + 8.0F, 0.0F, 0.1D);
                    RenderUtils.drawRect(f + 8.0F, valuePosY + 1.0F, f + 60.0F, valuePosY + 3.0F, (new Color(Colors.GREY.c)).brighter().brighter().getRGB());
                    RenderUtils.drawRect(f + 8.0F, valuePosY + 1.0F, f + integervalue.getTranslate().getX(), valuePosY + 3.0F, (new Color(-14848033)).brighter().getRGB());
                    RenderUtils.drawCircle(f + integervalue.getTranslate().getX(), valueY + 2.5F, 2.0F, (new Color(-14848033)).brighter().getRGB());
                    FontLoaders.Sans18.drawString(((Integer) integervalue.get()).toString(), f + integervalue.getTranslate().getX() - 2.0F, valuePosY + 6.0F, (new Color(0, 0, 0)).getRGB());
                    if (this.isHovered(f + 8.0F, valuePosY + 1.0F, f + 60.0F, valuePosY + 4.0F, mouseX, mouseY) && !this.mouseClicked && Mouse.isButtonDown(0)) {
                        integervalue.set((Number) Double.valueOf((double) Math.round(((double) integervalue.getMinimum() + (double) (integervalue.getMaximum() - integervalue.getMinimum()) * Math.min(d0, 1.0D)) * 100.0D) / 100.0D));
                    }
                }

                if (value instanceof FloatValue) {
                    f = (float) width / 2.0F + 35.0F;
                    d0 = Math.max(0.0D, (double) ((float) mouseX - (f + 8.0F)) / 52.0D);
                    FontLoaders.JelloList16.drawString(value.getName(), (float) width / 2.0F - 99.0F, valuePosY, (new Color(0, 0, 0)).getRGB());
                    FloatValue floatvalue = (FloatValue) value;

                    floatvalue.getTranslate().interpolate(52.0F * (((Float) floatvalue.get()).floatValue() > floatvalue.getMaximum() ? floatvalue.getMaximum() : (((Float) floatvalue.get()).floatValue() < floatvalue.getMinimum() ? 0.0F : ((Float) floatvalue.get()).floatValue() - floatvalue.getMinimum())) / (floatvalue.getMaximum() - floatvalue.getMinimum()) + 8.0F, 0.0F, 0.1D);
                    RenderUtils.drawRect(f + 8.0F, valuePosY + 1.0F, f + 60.0F, valuePosY + 3.0F, (new Color(Colors.GREY.c)).brighter().brighter().getRGB());
                    RenderUtils.drawRect(f + 8.0F, valuePosY + 1.0F, f + floatvalue.getTranslate().getX(), valuePosY + 3.0F, (new Color(-14848033)).brighter().getRGB());
                    RenderUtils.drawCircle(f + floatvalue.getTranslate().getX(), valueY + 2.5F, 2.0F, (new Color(-14848033)).brighter().getRGB());
                    FontLoaders.Sans18.drawString(((Float) floatvalue.get()).toString(), f + floatvalue.getTranslate().getX() - 2.0F, valuePosY + 6.0F, (new Color(0, 0, 0)).getRGB());
                    if (this.isHovered(f + 8.0F, valuePosY + 1.0F, f + 60.0F, valuePosY + 4.0F, mouseX, mouseY) && !this.mouseClicked && Mouse.isButtonDown(0)) {
                        floatvalue.set((Number) Double.valueOf((double) Math.round(((double) floatvalue.getMinimum() + (double) (floatvalue.getMaximum() - floatvalue.getMinimum()) * Math.min(d0, 1.0D)) * 100.0D) / 100.0D));
                    }
                }
            }

            GL11.glDisable(3089);
            GL11.glPopMatrix();
            float f1 = valueY - this.translate.getY();

            if (Mouse.hasWheel() && this.isHovered((float) width / 2.0F - 100.0F, (float) height / 2.0F - 130.0F, (float) width / 2.0F + 100.0F, (float) height / 2.0F + 130.0F, mouseX, mouseY)) {
                if (wheel > 0 && this.mouseWheel < 0) {
                    this.mouseWheel += 10;
                }

                if (wheel < 0 && (float) Math.abs(this.mouseWheel) < f1 - ((float) height / 2.0F - 130.0F - 16.0F) - 200.0F) {
                    this.mouseWheel -= 10;
                }
            }

            this.translate.interpolate(0.0F, (float) this.mouseWheel, 0.15000000596046448D);
            this.hashMap.put(LiquidBounce.INSTANCE.getModule(), Integer.valueOf(this.mouseWheel));
        }

    }

    public void start() {
        IScaledResolution sr = Jello.classProvider.createScaledResolution(MinecraftInstance.mc);

        this.percent = smoothTrans((double) this.percent, (double) this.lastPercent);
        this.percent2 = smoothTrans((double) this.percent2, (double) this.lastPercent2);
        if (!this.close) {
            if ((double) this.percent > 0.981D) {
                GlStateManager.translate((float) (sr.getScaledWidth() / 2), (float) (sr.getScaledHeight() / 2), 0.0F);
                GlStateManager.scale(this.percent, this.percent, 0.0F);
            } else {
                this.percent2 = smoothTrans((double) this.percent2, (double) this.lastPercent2);
                GlStateManager.translate((float) (sr.getScaledWidth() / 2), (float) (sr.getScaledHeight() / 2), 0.0F);
                GlStateManager.scale(this.percent2, this.percent2, 0.0F);
            }
        } else {
            GlStateManager.translate((float) (sr.getScaledWidth() / 2), (float) (sr.getScaledHeight() / 2), 0.0F);
            GlStateManager.scale(this.percent, this.percent, 0.0F);
        }

        GlStateManager.translate((float) (-sr.getScaledWidth() / 2), (float) (-sr.getScaledHeight() / 2), 0.0F);
        if ((double) this.percent <= 1.5D && this.close) {
            this.percent = smoothTrans((double) this.percent, 12.0D);
        }

        if ((double) this.percent >= 1.4D && this.close) {
            Jello.mc2.currentScreen = null;
            Jello.mc2.mouseHelper.grabMouseCursor();
            Jello.mc2.inGameHasFocus = true;
        }

    }

    public void onGuiClosed() {
        this.SaveConfig();
        this.smooth.setOpacity(0.0F);

        try {
            Jello.mc2.entityRenderer.stopUseShader();
        } catch (Throwable throwable) {
            ;
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int Button) {
        this.SaveConfig();
    }

    public void SaveMouseWheel() {
        File file = new File(LiquidBounce.fileManager.dir.getName() + "/Wheelgui.txt");

        try {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            PrintWriter ex = new PrintWriter(file);
            Iterator iterator = this.hashMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry set = (Entry) iterator.next();

                ex.print(((Module) set.getKey()).getName() + ":" + set.getValue() + "\n");
            }

            ex.close();
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }

    }

    public void SaveConfig() {
        File file = new File(LiquidBounce.fileManager.dir.getName() + "/gui.txt");

        try {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            PrintWriter ex = new PrintWriter(file);
            Iterator iterator = this.categories.iterator();

            while (iterator.hasNext()) {
                Category menu = (Category) iterator.next();

                ex.print(menu.moduleCategory.getDisplayName() + ":" + menu.x + ":" + menu.y + ":" + menu.mouseWheel + ":" + menu.slider + "\n");
            }

            ex.close();
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }

    }

    public void loadValue() {}

    public void Savevalue() {
        File file = new File(LiquidBounce.fileManager.dir.getName() + "/valuex.txt");

        try {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            PrintWriter ex = new PrintWriter(file);
            Object len = null;
            String values = "";
            Iterator iterator = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

            while (iterator.hasNext()) {
                Module m = (Module) iterator.next();

                Value v;

                for (Iterator iterator1 = m.getValues().iterator(); iterator1.hasNext(); values = values + String.format("%s:%s:%s%s", new Object[] { m.getName(), v.getName(), v.getValue(), System.lineSeparator()})) {
                    v = (Value) iterator1.next();
                }
            }

            save("Valuesx.txt", values, false);
            ex.close();
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }

    }

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public static void save(String file, String content, boolean append) {
        try {
            File e = new File(LiquidBounce.INSTANCE.getFileManager().dir.getName(), file);

            if (!e.exists()) {
                e.createNewFile();
            }

            Object t = null;
            boolean flag = false;

            FileWriter t2;

            try {
                flag = true;
                t2 = new FileWriter(e, append);

                try {
                    t2.write(content);
                } finally {
                    if (t2 != null) {
                        t2.close();
                    }

                }

                flag = false;
            } finally {
                if(flag) {
                    Object t21;

                    if (t == null) {
                        t21 = null;
                    } else {
                        t21 = null;
                        if (t != t21) {
                            ((Throwable) t).addSuppressed((Throwable) t21);
                        }
                    }

                }
            }

            if (t == null) {
                t2 = null;
            } else {
                t2 = null;
                if (t != t2) {
                    ((Throwable) t).addSuppressed(t2);
                }
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    public void loadWheel() {
        File file = new File(LiquidBounce.fileManager.dir.getName() + "/Wheelgui.txt");

        try {
            BufferedReader e = new BufferedReader(new FileReader(file));
            String all = null;

            String len;

            while ((len = e.readLine()) != null) {
                all = all + len;
                if (LiquidBounce.INSTANCE.getModule() != null) {
                    String name = len.toString().split(":")[0];

                    if (name.equals(LiquidBounce.INSTANCE.getModule().getName()) && name.equals(LiquidBounce.INSTANCE.getModule().getName())) {
                        this.mouseWheel = Integer.parseInt(len.toString().split(":")[1]);
                        break;
                    }
                }
            }

            if (all != null && !all.contains(LiquidBounce.INSTANCE.getModule().getName())) {
                this.mouseWheel = 0;
            }

            e.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    public void loadClickGui() {
        File file = new File(LiquidBounce.fileManager.dir.getName() + "/gui.txt");

        try {
            BufferedReader e = new BufferedReader(new FileReader(file));

            String len;

            while ((len = e.readLine()) != null) {
                String str = len;
                String moduleCatrgory = len.toString().split(":")[0];
                Iterator iterator = this.categories.iterator();

                while (iterator.hasNext()) {
                    Category menu = (Category) iterator.next();

                    if (moduleCatrgory.equals(menu.moduleCategory.getDisplayName())) {
                        int newx = Integer.parseInt(str.toString().split(":")[1]);
                        int newy = Integer.parseInt(str.toString().split(":")[2]);
                        int newwheel = Integer.parseInt(str.toString().split(":")[3]);
                        int newslider = Integer.parseInt(str.toString().split(":")[4]);

                        menu.x = newx;
                        menu.y = newy;
                        menu.mouseWheel = newwheel;
                        menu.slider = newslider;
                    }
                }
            }

            e.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    public void initGui() {
        if (MinecraftInstance.mc.getTheWorld() != null) {
            Jello.mc2.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }

        super.initGui();
        this.percent = 1.33F;
        this.lastPercent = 1.0F;
        this.percent2 = 1.33F;
        this.lastPercent2 = 1.0F;
        this.outro = 1.0F;
        this.lastOutro = 1.0F;
    }

    public static float smoothTrans(double current, double last) {
        return (float) (current + (last - current) / (double) (Minecraft.getDebugFPS() / 10));
    }

    public void drawShadow(float x, float y, float width, float height) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        this.drawTexturedRect(x - 9.0F, y - 9.0F, 9.0F, 9.0F, "paneltopleft", sr);
        this.drawTexturedRect(x - 9.0F, y + height, 9.0F, 9.0F, "panelbottomleft", sr);
        this.drawTexturedRect(x + width, y + height, 9.0F, 9.0F, "panelbottomright", sr);
        this.drawTexturedRect(x + width, y - 9.0F, 9.0F, 9.0F, "paneltopright", sr);
        this.drawTexturedRect(x - 9.0F, y, 9.0F, height, "panelleft", sr);
        this.drawTexturedRect(x + width, y, 9.0F, height, "panelright", sr);
        this.drawTexturedRect(x, y - 9.0F, width, 9.0F, "paneltop", sr);
        this.drawTexturedRect(x, y + height, width, 9.0F, "panelbottom", sr);
    }

    public void drawTexturedRect(float x, float y, float width, float height, String image, ScaledResolution sr) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("langya/shadow/" + image + ".png"));
        Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, 0.0F, 0.0F, (int) width, (int) height, width, height);
    }
}
