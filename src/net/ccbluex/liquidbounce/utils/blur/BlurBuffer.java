package net.ccbluex.liquidbounce.utils.blur;

import java.awt.Color;
import me.Skid.Tenacity.utils.render.RoundedUtil;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;

public class BlurBuffer {

    public static void blurArea(float x, float y, float width, float height) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x, y, x + width, y + height, (new Color(-2)).getRGB());
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(((Float) hud.getBlurStrength().getValue()).floatValue());
        StencilUtil.uninitStencilBuffer();
    }

    public static void CustomBlurArea(float x, float y, float width, float height, float BlurStrength) {
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x, y, x + width, y + height, (new Color(-2)).getRGB());
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(BlurStrength);
        StencilUtil.uninitStencilBuffer();
    }

    public static void CustomBlurRoundArea(float x, float y, float width, float height, float radius, float BlurStrength) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        StencilUtil.initStencilToWrite();
        RoundedUtil.drawRound(x, y, width, height, radius, new Color(-2));
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(BlurStrength);
        StencilUtil.uninitStencilBuffer();
    }

    public static void blurRoundArea(float x, float y, float width, float height, float radius) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        StencilUtil.initStencilToWrite();
        RenderUtils.drawRoundedRect2(x, y, x + width, y + height, radius, 6, (new Color(-2)).getRGB());
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(((Float) hud.getBlurStrength().getValue()).floatValue());
        StencilUtil.uninitStencilBuffer();
    }
}
