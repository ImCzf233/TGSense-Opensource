package me.Skid.ui.elements;

import java.awt.Color;
import me.Skid.novoline.impl.Fonts;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "TimerHud"
)
public class TimerHud extends Element {

    public final ListValue shadowValue = new ListValue("Shadow", new String[] { "None", "Basic", "Thick"}, "None");
    public BoolValue BlurValue = new BoolValue("blur", false);
    public float animWidth;
    public float animatedCircleEnd;
    float Width = 153.0F;
    float Height = 42.0F;
    int x = 0;
    int y = 0;
    Double renderX;
    Double renderY;

    public Border drawElement() {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        this.animWidth = (float) RenderUtils.interpolate((double) (100.0F - hud.getTicks() * 4.0F), (double) this.animWidth, 0.05000000074505806D);
        RenderUtils.drawSmoothRect((double) this.x, (double) this.y, (double) ((float) this.x + 40.0F), (double) ((float) this.y + 43.0F), (new Color(0, 0, 0, 100)).getRGB());
        String coef = (String) this.shadowValue.get();
        byte b0 = -1;

        switch (coef.hashCode()) {
        case 63955982:
            if (coef.equals("Basic")) {
                b0 = 0;
            }
            break;

        case 80778109:
            if (coef.equals("Thick")) {
                b0 = 1;
            }
        }

        switch (b0) {
        case 0:
            RenderUtils.drawShadow((float) this.x - 0.5F, (float) this.y - 0.5F, (float) this.x + 40.0F + 1.0F, (float) this.y + 43.0F);
            break;

        case 1:
            RenderUtils.drawShadow((float) this.x - 0.5F, (float) this.y - 0.5F, (float) this.x + 40.0F + 1.0F, (float) this.y + 43.0F);
            RenderUtils.drawShadow((float) this.x - 0.5F, (float) this.y - 0.5F, (float) this.x + 40.0F + 1.0F, (float) this.y + 43.0F);
        }

        if (((Boolean) this.BlurValue.get()).booleanValue()) {
            GL11.glTranslated(-this.renderX.doubleValue(), -this.renderY.doubleValue(), 0.0D);
            GL11.glPushMatrix();
            BlurBuffer.blurArea((float) this.x, (float) this.y, (float) this.x + 40.0F, (float) this.y + 43.0F);
            GL11.glPopMatrix();
            GL11.glTranslated(this.renderX.doubleValue(), this.renderY.doubleValue(), 0.0D);
        }

        Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.drawCenteredString("Timer", (float) (this.x + 20), (float) (this.y + 14 - 10), -1);
        RenderUtils.drawCircle((double) (this.x + 15 + 5), (double) this.y + 23.5D, 11.5D, -5.0F, 360.0F, Color.DARK_GRAY.darker().getRGB(), 5.5F);
        float coef1 = this.animWidth / 100.0F;
        float end;

        this.animatedCircleEnd = end = coef1 * 360.0F;
        RenderUtils.drawCircle((double) (this.x + 15 + 5), (double) this.y + 23.5D, 11.5D, -5.0F, this.animatedCircleEnd, (new Color(255, 255, 255)).getRGB(), 5.5F);
        Fonts.SFBOLD.SFBOLD_15.SFBOLD_15.drawCenteredString(Math.round(this.animWidth) + "%", (float) (this.x + 20), (float) (this.y + 22), -1);
        return new Border(0.0F, 0.0F, this.Width, this.Height);
    }
}
