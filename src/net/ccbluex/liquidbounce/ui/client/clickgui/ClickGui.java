package net.ccbluex.liquidbounce.ui.client.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ClickGui extends WrappedGuiScreen {

    public final List panels = new ArrayList();
    private final IResourceLocation hudIcon;
    public Style style;
    private Panel clickedPanel;
    private int mouseX;
    private int mouseY;
    private int scroll;

    public ClickGui() {
        this.hudIcon = ClickGui.classProvider.createResourceLocation("liquidbounce+/custom_hud_icon.png");
        this.style = new SlowlyStyle();
        boolean width = true;
        boolean height = true;
        final int yPos = 5;
        ModuleCategory[] amodulecategory = ModuleCategory.values();
        int i = amodulecategory.length;

        for (int j = 0; j < i; ++j) {
            final ModuleCategory category = amodulecategory[j];

            this.panels.add(new Panel(category.getDisplayName(), 100, yPos, 100, 18, false) {
                public void setupItems() {
                    Iterator iterator = LiquidBounce.moduleManager.getModules().iterator();

                    while (iterator.hasNext()) {
                        Module module = (Module) iterator.next();

                        if (module.getCategory() == category) {
                            this.getElements().add(new ModuleElement(module));
                        }
                    }

                }
            });
            yPos += 20;
        }

        yPos += 20;
        this.panels.add(new Panel("Targets", 100, yPos, 100, 18, false) {
            public void setupItems() {
                this.getElements().add(new ButtonElement("Players") {
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    public String getDisplayName() {
                        this.displayName = "Players";
                        this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.isVisible()) {
                            EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
                            this.displayName = "Players";
                            this.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            null.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                    }
                });
                this.getElements().add(new ButtonElement("Mobs") {
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    public String getDisplayName() {
                        this.displayName = "Mobs";
                        this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.isVisible()) {
                            EntityUtils.targetMobs = !EntityUtils.targetMobs;
                            this.displayName = "Mobs";
                            this.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            null.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                    }
                });
                this.getElements().add(new ButtonElement("Animals") {
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    public String getDisplayName() {
                        this.displayName = "Animals";
                        this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.isVisible()) {
                            EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
                            this.displayName = "Animals";
                            this.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            null.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                    }
                });
                this.getElements().add(new ButtonElement("Invisible") {
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    public String getDisplayName() {
                        this.displayName = "Invisible";
                        this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.isVisible()) {
                            EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
                            this.displayName = "Invisible";
                            this.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            null.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                    }
                });
                this.getElements().add(new ButtonElement("Dead") {
                    public void createButton(String displayName) {
                        this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        super.createButton(displayName);
                    }

                    public String getDisplayName() {
                        this.displayName = "Dead";
                        this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                        return super.getDisplayName();
                    }

                    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
                        if (mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.isVisible()) {
                            EntityUtils.targetDead = !EntityUtils.targetDead;
                            this.displayName = "Dead";
                            this.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                            null.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
                        }

                    }
                });
            }
        });
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        double scale = (double) ((Float) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get()).floatValue();

        GlStateManager.translate(0.0F, (float) this.scroll, 0.0F);
        mouseY -= this.scroll;
        mouseX = (int) ((double) mouseX / scale);
        mouseY = (int) ((double) mouseY / scale);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.representedScreen.drawDefaultBackground();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        RenderUtils.drawRect(0, sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight() - this.scroll, -804253680);
        RenderUtils.drawImage(this.hudIcon, 9, ClickGui.mc.getCurrentScreen().getHeight() - 41, 32, 32);
        if (Mouse.isButtonDown(0) && mouseX >= 5 && mouseX <= 50 && mouseY <= this.representedScreen.getHeight() - 5 && mouseY >= this.representedScreen.getHeight() - 50) {
            ClickGui.mc.displayGuiScreen(ClickGui.classProvider.wrapGuiScreen(new GuiHudDesigner()));
        }

        GL11.glScaled(scale, scale, scale);
        Iterator wheel = this.panels.iterator();

        Panel i;

        while (wheel.hasNext()) {
            i = (Panel) wheel.next();
            i.updateFade(RenderUtils.deltaTime);
            i.drawScreen(mouseX, mouseY, partialTicks);
        }

        wheel = this.panels.iterator();

        while (wheel.hasNext()) {
            i = (Panel) wheel.next();
            Iterator iterator = i.getElements().iterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                if (element instanceof ModuleElement) {
                    ModuleElement moduleElement = (ModuleElement) element;

                    if (mouseX != 0 && mouseY != 0 && moduleElement.isHovering(mouseX, mouseY) && moduleElement.isVisible() && element.getY() <= i.getY() + i.getFade()) {
                        this.style.drawDescription(mouseX, mouseY, moduleElement.getModule().getDescription());
                    }
                }
            }
        }

        ClickGui.classProvider.getGlStateManager().disableLighting();
        ClickGui.functions.disableStandardItemLighting();
        GL11.glScaled(1.0D, 1.0D, 1.0D);
        int i;
        int j;

        if (Mouse.hasWheel()) {
            i = Mouse.getDWheel();

            for (j = this.panels.size() - 1; j >= 0; --j) {
                if (((Panel) this.panels.get(j)).handleScroll(mouseX, mouseY, i)) {
                    return;
                }
            }

            if (i < 0) {
                this.scroll -= 15;
            } else if (i > 0) {
                this.scroll += 15;
                if (this.scroll > 0) {
                    this.scroll = 0;
                }
            }
        }

        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
        if (Mouse.hasWheel()) {
            i = Mouse.getDWheel();

            for (j = this.panels.size() - 1; j >= 0; --j) {
                if (((Panel) this.panels.get(j)).handleScroll(mouseX, mouseY, i)) {
                    return;
                }
            }

            if (i < 0) {
                this.scroll -= 15;
            } else if (i > 0) {
                this.scroll += 15;
                if (this.scroll > 0) {
                    this.scroll = 0;
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        double scale = (double) ((Float) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get()).floatValue();

        mouseY -= this.scroll;
        mouseX = (int) ((double) mouseX / scale);
        mouseY = (int) ((double) mouseY / scale);
        Iterator iterator = this.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            panel.mouseClicked(mouseX, mouseY, mouseButton);
            panel.drag = false;
            if (mouseButton == 0 && panel.isHovering(mouseX, mouseY)) {
                this.clickedPanel = panel;
            }
        }

        if (this.clickedPanel != null) {
            this.clickedPanel.x2 = this.clickedPanel.x - mouseX;
            this.clickedPanel.y2 = this.clickedPanel.y - mouseY;
            this.clickedPanel.drag = true;
            this.panels.remove(this.clickedPanel);
            this.panels.add(this.clickedPanel);
            this.clickedPanel = null;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        double scale = (double) ((Float) ((ClickGUI) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get()).floatValue();

        mouseY -= this.scroll;
        mouseX = (int) ((double) mouseX / scale);
        mouseY = (int) ((double) mouseY / scale);
        Iterator iterator = this.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            panel.mouseReleased(mouseX, mouseY, state);
        }

    }

    public void updateScreen() {
        Iterator iterator = this.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();
            Iterator iterator1 = panel.getElements().iterator();

            while (iterator1.hasNext()) {
                Element element = (Element) iterator1.next();

                if (element instanceof ButtonElement) {
                    ButtonElement buttonElement = (ButtonElement) element;

                    if (buttonElement.isHovering(this.mouseX, this.mouseY)) {
                        if (buttonElement.hoverTime < 7) {
                            ++buttonElement.hoverTime;
                        }
                    } else if (buttonElement.hoverTime > 0) {
                        --buttonElement.hoverTime;
                    }
                }

                if (element instanceof ModuleElement) {
                    if (((ModuleElement) element).getModule().getState()) {
                        if (((ModuleElement) element).slowlyFade < 255) {
                            ((ModuleElement) element).slowlyFade += 20;
                        }
                    } else if (((ModuleElement) element).slowlyFade > 0) {
                        ((ModuleElement) element).slowlyFade -= 20;
                    }

                    if (((ModuleElement) element).slowlyFade > 255) {
                        ((ModuleElement) element).slowlyFade = 255;
                    }

                    if (((ModuleElement) element).slowlyFade < 0) {
                        ((ModuleElement) element).slowlyFade = 0;
                    }
                }
            }
        }

        super.updateScreen();
    }

    public void onGuiClosed() {
        LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.clickGuiConfig);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
