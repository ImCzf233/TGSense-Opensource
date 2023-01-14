package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.ShaderEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;

@ModuleInfo(
    name = "Health",
    description = "sb.",
    category = ModuleCategory.RENDER,
    keyBind = 54
)
public class Health extends Module {

    private IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    private IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
    private IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private BoolValue cColorValue = new BoolValue("CustomColor", false);
    private BoolValue cFontValue = new BoolValue("CustomFont", false);

    @EventTarget
    public void onShader(ShaderEvent event) {}

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        IScaledResolution sr = Health.classProvider.createScaledResolution(Health.mc);
        float healthNum = (float) Math.round((double) Health.mc.getThePlayer().getHealth() * 10.0D / 10.0D);
        float abNum = (float) Math.round((double) Minecraft.getMinecraft().player.getAbsorptionAmount() * 10.0D / 10.0D);
        String abString = ((Boolean) this.cFontValue.get()).booleanValue() ? "§e" + abNum + "§r" : "§e" + abNum + "§e�?";

        if (Minecraft.getMinecraft().player.getAbsorptionAmount() <= 0.0F) {
            abString = "";
        }

        String text = ((Boolean) this.cFontValue.get()).booleanValue() ? healthNum + "§r " + abString + "" : healthNum + "§c�? " + abString + "";
        int c = ((Boolean) this.cColorValue.get()).booleanValue() ? (new Color(((Integer) this.colorRedValue.get()).intValue(), ((Integer) this.colorGreenValue.get()).intValue(), ((Integer) this.colorBlueValue.get()).intValue())).getRGB() : ColorUtils.INSTANCE.getHealthColor(Health.mc.getThePlayer().getHealth(), Health.mc.getThePlayer().getMaxHealth());

        if (((Boolean) this.cFontValue.get()).booleanValue()) {
            Fonts.Posterama35.drawCenteredString(text, (float) (sr.getScaledWidth() / 2), (float) (sr.getScaledHeight() / 2 - 25), c, true);
        } else {
            Health.mc.getFontRendererObj().drawCenteredString(text, (float) (sr.getScaledWidth() / 2), (float) (sr.getScaledHeight() / 2 - 25), c, true);
        }

    }
}
