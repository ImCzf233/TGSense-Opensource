package me.Skid.Jello;

import java.awt.Color;
import java.util.Iterator;
import java.util.Objects;
import me.Skid.Tfont.FontLoaders;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Category {

    ModuleCategory moduleCategory;
    int x;
    int y;
    public boolean dragged;
    int mouseX2;
    int mouseY2;
    int width;
    int height;
    int wheel;
    int mouseWheel;
    boolean mouseClicked;
    int slider;
    boolean mouseClicked2;
    public final Translate translate = new Translate(0.0F, 0.0F);
    public final Translate RECTtranslate = new Translate(0.0F, 0.0F);

    public Category(ModuleCategory moduleCategory, int x, int y, int width, int height) {
        this.moduleCategory = moduleCategory;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(int mouseX, int mouseY) {
        if (this.dragged) {
            this.x = this.mouseX2 + mouseX;
            this.y = this.mouseY2 + mouseY;
        }

        if (this.isHovered((float) this.x, (float) this.y, (float) (this.x + this.width), (float) (this.y + this.height), mouseX, mouseY) && Mouse.isButtonDown(0)) {
            this.dragged = true;
            this.mouseX2 = this.x - mouseX;
            this.mouseY2 = this.y - mouseY;
        } else {
            this.dragged = false;
        }

        Stencil.write(false);
        Stencil.erase(false);
        this.drawShadow((float) this.x, (float) this.y, (float) this.width, (float) this.height);
        Stencil.dispose();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Stencil.write(false);
        Stencil.erase(true);
        Stencil.dispose();
        GlStateManager.resetColor();
        RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + 30, (new Color(230, 230, 230)).getRGB());
        RenderUtils.drawRect(this.x, this.y + 30, this.x + this.width, this.y + this.height, -1);
        FontLoaders.JelloTitle20.drawString(this.moduleCategory.getDisplayName(), (double) (this.x + 6), (double) (this.y + 12), (new Color(55, 55, 55)).getRGB(), true);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        new ScaledResolution(Minecraft.getMinecraft());
        RenderUtils.doGlScissor(this.x, this.y + 30, this.x + this.width, this.height - 30);
        float moduleY = (float) (this.y + 35) + this.translate.getY();

        for (Iterator moduleHeight = LiquidBounce.moduleManager.getModuleInCategory(this.moduleCategory).iterator(); moduleHeight.hasNext(); moduleY += 15.0F) {
            Module rectHeight = (Module) moduleHeight.next();

            rectHeight.getModuleTranslate().interpolate(0.0F, moduleY, 0.1D);
            float modulePosY = rectHeight.getModuleTranslate().getY();

            RenderUtils.drawRect((float) this.x, modulePosY - 5.0F, (float) (this.x + this.width), modulePosY + 10.0F, ClientUtils.reAlpha(Colors.getColor(65, 155, 255), rectHeight.getHoverOpacity()));
            if (Mouse.hasWheel() && this.isHovered((float) this.x, (float) (this.y + 30), (float) (this.x + this.width), (float) (this.y + this.height), mouseX, mouseY)) {
                RenderUtils.drawRoundedRect((float) (this.x + this.width - 10), (float) (this.y + 30), (float) (this.x + this.width - 5), (float) (this.y + this.height), 6, (new Color(100, 100, 100)).getRGB());
            }

            if (this.isHovered((float) this.x, modulePosY - 5.0F, (float) (this.x + this.width), modulePosY + 10.0F, mouseX, mouseY)) {
                float rectHoved = (float) RenderUtils.getAnimationState((double) rectHeight.getRectHoved(), 0.25D, 1.0D);

                rectHeight.setRectHoved(rectHoved);
                if (LiquidBounce.INSTANCE.getModule() == null) {
                    RenderUtils.drawRect((float) this.x, modulePosY - 5.0F, (float) (this.x + this.width), modulePosY + 10.0F, ClientUtils.reAlpha(Colors.BLACK.c, rectHeight.getRectHoved()));
                }
            }

            float hoverOpacity;

            if (rectHeight.getState()) {
                hoverOpacity = (float) RenderUtils.getAnimationState((double) rectHeight.getHoverOpacity(), 1.0D, 1.0D);
                rectHeight.setHoverOpacity(hoverOpacity);
                FontLoaders.JelloTitle18.drawString(rectHeight.getName(), (float) (this.x + 8), modulePosY, -1);
            } else {
                hoverOpacity = (float) RenderUtils.getAnimationState((double) rectHeight.getHoverOpacity(), 0.0D, 1.5D);
                rectHeight.setHoverOpacity(hoverOpacity);
                FontLoaders.JelloTitle18.drawString(rectHeight.getName(), (float) (this.x + 6), modulePosY, (new Color(1, 1, 1)).getRGB());
            }

            if (this.isHovered((float) this.x, (float) (this.y + 30), (float) (this.x + this.width), (float) (this.y + this.height), mouseX, mouseY) && this.isHovered((float) this.x, modulePosY - 5.0F, (float) (this.x + this.width), modulePosY + 10.0F, mouseX, mouseY)) {
                if (Mouse.isButtonDown(0)) {
                    if (!this.mouseClicked && Objects.equals(rectHeight.getCategory(), this.moduleCategory)) {
                        rectHeight.toggle();
                        rectHeight.setHovered(!rectHeight.getHovered());
                    }

                    this.mouseClicked = true;
                } else {
                    this.mouseClicked = false;
                }
            }

            if (this.isHovered((float) this.x, (float) (this.y + 30), (float) (this.x + this.width), (float) (this.y + this.height), mouseX, mouseY) && this.isHovered((float) this.x, modulePosY - 5.0F, (float) (this.x + this.width), modulePosY + 10.0F, mouseX, mouseY)) {
                if (Mouse.isButtonDown(1)) {
                    if (!this.mouseClicked2 && !this.mouseClicked2 && rectHeight.getValues().size() > 0) {
                        Iterator iterator = LiquidBounce.moduleManager.getModules().iterator();

                        while (iterator.hasNext()) {
                            Module mod = (Module) iterator.next();

                            if (mod != LiquidBounce.INSTANCE.getModule() && !rectHeight.showSettings && mod.showSettings) {
                                mod.showSettings = false;
                            }
                        }

                        if (LiquidBounce.INSTANCE.getModule() == null) {
                            LiquidBounce.INSTANCE.setModule(rectHeight);
                            ((Module) Objects.requireNonNull(LiquidBounce.INSTANCE.getModule())).showSettings = !LiquidBounce.INSTANCE.getModule().showSettings;
                            if (!rectHeight.getHovvv()) {
                                rectHeight.setHovvv(true);
                            }
                        }
                    }

                    this.mouseClicked2 = true;
                } else {
                    this.mouseClicked2 = false;
                }
            }
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();
        float moduleHeight1 = moduleY - this.translate.getY();
        float rectHeight1 = moduleY - this.RECTtranslate.getY();

        if (Mouse.hasWheel() && this.isHovered((float) this.x, (float) (this.y + 30), (float) (this.x + this.width), (float) (this.y + this.height), mouseX, mouseY)) {
            if (this.wheel > 0) {
                if (this.mouseWheel < 0) {
                    this.mouseWheel += 10;
                }

                if (this.slider < 0) {
                    this.slider += 10;
                }
            }

            if (this.wheel < 0) {
                if ((float) Math.abs(this.mouseWheel) < moduleHeight1 - (float) (this.y - 16) - 200.0F) {
                    this.mouseWheel -= 10;
                }

                if ((float) Math.abs(this.slider) < rectHeight1 - 50.0F) {
                    this.slider -= 10;
                }
            }
        }

        this.translate.interpolate(0.0F, (float) this.mouseWheel, 0.15000000596046448D);
        this.RECTtranslate.interpolate(0.0F, (float) this.slider, 0.15000000596046448D);
    }

    public void setWheel(int Wheel) {
        this.wheel = Wheel;
    }

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
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
