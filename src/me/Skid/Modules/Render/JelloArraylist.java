package me.Skid.Modules.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

@ModuleInfo(
    name = "JelloArraylist",
    description = "NO SIGMA HATAR",
    category = ModuleCategory.RENDER,
    array = false
)
public class JelloArraylist extends Module {

    ResourceLocation wtf = new ResourceLocation("langya/shadow/arraylistshadow.png");
    List modules = new ArrayList();
    private final BoolValue useTrueFont = new BoolValue("Use-TrueFont", true);
    private final IntegerValue animateSpeed = new IntegerValue("Animate-Speed", 5, 1, 20);

    public JelloArraylist() {
        this.setState(true);
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        this.updateElements(event.getPartialTicks());
        this.renderArraylist();
    }

    public void updateElements(float partialTicks) {
        this.modules = (List) LiquidBounce.moduleManager.getModules().stream().filter(test<invokedynamic>()).sorted(new JelloArraylist.ModComparator()).collect(Collectors.toCollection(get<invokedynamic>()));
        float tick = 1.0F - partialTicks;
        Iterator iterator = this.modules.iterator();

        while (iterator.hasNext()) {
            Module module = (Module) iterator.next();

            module.setAnimation(module.getAnimation() + (float) (module.getState() ? ((Integer) this.animateSpeed.get()).intValue() : -((Integer) this.animateSpeed.get()).intValue()) * tick);
            module.setAnimation(MathHelper.clamp(module.getAnimation(), 0.0F, 20.0F));
        }

    }

    public void renderArraylist() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float yStart = 1.0F;
        float xStart = 0.0F;
        Iterator iterator = this.modules.iterator();

        Module module;

        while (iterator.hasNext()) {
            module = (Module) iterator.next();
            if (module.getAnimation() > 0.0F) {
                xStart = (float) (sr.getScaledWidth() - Fonts.fontSFUI35.getStringWidth(module.getName()) - 5);
                GlStateManager.pushMatrix();
                GlStateManager.disableAlpha();
                RenderUtils.drawImage3(this.wtf, xStart - 8.0F - 2.0F - 1.0F, yStart + 2.0F - 2.5F - 1.5F - 1.5F - 1.5F - 6.0F - 1.0F, Fonts.fontSFUI35.getStringWidth(module.getName()) * 1 + 20 + 10, 38, 1.0F, 1.0F, 1.0F, module.getAnimation() / 20.0F * 0.7F);
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();
                yStart += 12.75F * (module.getAnimation() / 20.0F);
            }
        }

        yStart = 1.0F;
        xStart = 0.0F;
        iterator = this.modules.iterator();

        while (iterator.hasNext()) {
            module = (Module) iterator.next();
            if (module.getAnimation() > 0.0F) {
                xStart = (float) (sr.getScaledWidth() - Fonts.fontSFUI35.getStringWidth(module.getName()) - 5);
                GlStateManager.pushMatrix();
                if (((Boolean) this.useTrueFont.get()).booleanValue()) {
                    GlStateManager.disableAlpha();
                }

                Fonts.fontSFUI35.drawString(module.getName(), xStart, yStart + 7.5F, (new Color(1.0F, 1.0F, 1.0F, module.getAnimation() / 20.0F * 0.7F)).getRGB());
                if (((Boolean) this.useTrueFont.get()).booleanValue()) {
                    GlStateManager.enableAlpha();
                }

                GlStateManager.popMatrix();
                yStart += 12.75F * (module.getAnimation() / 20.0F);
            }
        }

        GlStateManager.resetColor();
    }

    private static boolean lambda$updateElements$0(Module mod) {
        return mod.getArray() && !mod.getName().equalsIgnoreCase("JelloArraylist") && !mod.getName().equalsIgnoreCase("JelloTabGui") && !mod.getName().equalsIgnoreCase("Compass");
    }

    class ModComparator implements Comparator {

        public int compare(Module e1, Module e2) {
            return Fonts.fontSFUI35.getStringWidth(e1.getName()) < Fonts.fontSFUI35.getStringWidth(e2.getName()) ? 1 : -1;
        }
    }
}
