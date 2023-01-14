package net.ccbluex.liquidbounce.ui.client.clickgui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Panel extends MinecraftInstance {

    private final String name;
    public int x;
    public int y;
    public int x2;
    public int y2;
    private final int width;
    private final int height;
    private int scroll;
    private int dragged;
    private boolean open;
    public boolean drag;
    private boolean scrollbar;
    private final List elements;
    private boolean visible;
    private float elementsHeight;
    private float fade;

    public Panel(String name, int x, int y, int width, int height, boolean open) {
        this.name = name;
        this.elements = new ArrayList();
        this.scrollbar = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.open = open;
        this.visible = true;
        this.setupItems();
    }

    public abstract void setupItems();

    public void drawScreen(int mouseX, int mouseY, float button) {
        if (this.visible) {
            int maxElements = ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue();
            int y;

            if (this.drag) {
                int scrollbar = this.x2 + mouseX;

                y = this.y2 + mouseY;
                if (scrollbar > -1) {
                    this.x = scrollbar;
                }

                if (y > -1) {
                    this.y = y;
                }
            }

            this.elementsHeight = (float) (this.getElementsHeight() - 1);
            boolean flag = this.elements.size() >= maxElements;

            if (this.scrollbar != flag) {
                this.scrollbar = flag;
            }

            LiquidBounce.clickGui.style.drawPanel(mouseX, mouseY, this);
            y = this.y + this.height - 2;
            int count = 0;
            Iterator iterator = this.elements.iterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                ++count;
                if (count > this.scroll && count < this.scroll + maxElements + 1 && this.scroll < this.elements.size()) {
                    element.setLocation(this.x, y);
                    element.setWidth(this.getWidth());
                    if ((float) y <= (float) this.getY() + this.fade) {
                        element.drawScreen(mouseX, mouseY, button);
                    }

                    y += element.getHeight() + 1;
                    element.setVisible(true);
                } else {
                    element.setVisible(false);
                }
            }

        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.visible) {
            if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
                this.open = !this.open;
                Panel.mc.getSoundHandler().playSound("random.bow", 1.0F);
            } else {
                Iterator iterator = this.elements.iterator();

                while (iterator.hasNext()) {
                    Element element = (Element) iterator.next();

                    if ((float) element.getY() <= (float) this.getY() + this.fade) {
                        element.mouseClicked(mouseX, mouseY, mouseButton);
                    }
                }

            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.visible) {
            this.drag = false;
            if (this.open) {
                Iterator iterator = this.elements.iterator();

                while (iterator.hasNext()) {
                    Element element = (Element) iterator.next();

                    element.mouseReleased(mouseX, mouseY, state);
                }

            }
        }
    }

    public boolean handleScroll(int mouseX, int mouseY, int wheel) {
        int maxElements = ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue();

        if (mouseX >= this.getX() && mouseX <= this.getX() + 100 && mouseY >= this.getY() && (float) mouseY <= (float) (this.getY() + 19) + this.elementsHeight) {
            if (wheel < 0 && this.scroll < this.elements.size() - maxElements) {
                ++this.scroll;
                if (this.scroll < 0) {
                    this.scroll = 0;
                }
            } else if (wheel > 0) {
                --this.scroll;
                if (this.scroll < 0) {
                    this.scroll = 0;
                }
            }

            if (wheel < 0) {
                if (this.dragged < this.elements.size() - maxElements) {
                    ++this.dragged;
                }
            } else if (wheel > 0 && this.dragged >= 1) {
                --this.dragged;
            }

            return true;
        } else {
            return false;
        }
    }

    void updateFade(int delta) {
        if (this.open) {
            if (this.fade < this.elementsHeight) {
                this.fade += 0.4F * (float) delta;
            }

            if (this.fade > this.elementsHeight) {
                this.fade = (float) ((int) this.elementsHeight);
            }
        } else {
            if (this.fade > 0.0F) {
                this.fade -= 0.4F * (float) delta;
            }

            if (this.fade < 0.0F) {
                this.fade = 0.0F;
            }
        }

    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int dragX) {
        this.x = dragX;
    }

    public void setY(int dragY) {
        this.y = dragY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getScrollbar() {
        return this.scrollbar;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public List getElements() {
        return this.elements;
    }

    public int getFade() {
        return (int) this.fade;
    }

    public int getDragged() {
        return this.dragged;
    }

    private int getElementsHeight() {
        int height = 0;
        int count = 0;
        Iterator iterator = this.elements.iterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();

            if (count < ((Integer) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()).intValue()) {
                height += element.getHeight() + 1;
                ++count;
            }
        }

        return height;
    }

    boolean isHovering(int mouseX, int mouseY) {
        float textWidth = (float) Panel.mc.getFontRendererObj().getStringWidth(StringUtils.stripControlCodes(this.name)) - 100.0F;

        return (float) mouseX >= (float) this.x - textWidth / 2.0F - 19.0F && (float) mouseX <= (float) this.x - textWidth / 2.0F + (float) Panel.mc.getFontRendererObj().getStringWidth(StringUtils.stripControlCodes(this.name)) + 19.0F && mouseY >= this.y && mouseY <= this.y + this.height - (this.open ? 2 : 0);
    }
}
