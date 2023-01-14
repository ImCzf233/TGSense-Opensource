package me.Skid.ui.elements;

import java.awt.Color;
import me.Skid.novoline.impl.Fonts;
import me.Skid.utils.AnimationHelper;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "HurtTimeHud"
)
public class HurtTimeHud extends Element {

    public final ListValue shadowValue = new ListValue("Shadow", new String[] { "None", "Basic", "Thick"}, "None");
    public FloatValue indx = new FloatValue("noting", 1.0E-7F, 0.0F, 4.0E-6F);
    public BoolValue BlurValue = new BoolValue("blur", false);
    public float animWidth;
    public float animatedCircleEnd;
    float Width = 41.0F;
    float Height = 42.0F;
    int x = 0;
    int y = 0;
    public Float x2;
    Double renderX;
    Double renderY;

    public HurtTimeHud() {
        this.x2 = (Float) this.indx.get();
    }

    public Border drawElement() {
        double hurttimePercentage = MathHelper.clamp((double) HurtTimeHud.mc2.player.hurtTime, 0.0D, 0.6D);

        hurttimePercentage = MathHelper.clamp(hurttimePercentage, 0.0D, 1.0D);
        double newWidth = 51.0D * hurttimePercentage;

        this.animWidth = (float) AnimationHelper.animate(newWidth, (double) this.animWidth, 0.0429999852180481D);
        RenderUtils.drawSmoothRect((double) this.x, (double) this.y, (double) ((float) this.x + 40.0F), (double) ((float) this.y + 43.0F), (new Color(0, 0, 0, 100)).getRGB());
        String coef = (String) this.shadowValue.get();
        byte scoef = -1;

        switch (coef.hashCode()) {
        case 63955982:
            if (coef.equals("Basic")) {
                scoef = 0;
            }
            break;

        case 80778109:
            if (coef.equals("Thick")) {
                scoef = 1;
            }
        }

        switch (scoef) {
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

        Fonts.SFBOLD.SFBOLD_16.SFBOLD_16.drawCenteredString("HURT", (float) (this.x + 20), (float) (this.y + 14 - 10), -1);
        RenderUtils.drawCircle((double) (this.x + 15 + 5), (double) this.y + 23.5D, 11.5D, -5.0F, 360.0F, Color.DARK_GRAY.darker().getRGB(), 5.5F);
        float coef1 = this.animWidth / 100.0F;
        double scoef1 = (double) HurtTimeHud.mc2.player.hurtTime;

        this.animatedCircleEnd = coef1 * 360.0F;
        RenderUtils.drawCircle((double) (this.x + 15 + 5), (double) this.y + 23.5D, 11.5D, -5.0F, this.animatedCircleEnd * 2.0F + this.x2.floatValue(), (new Color(255, 255, 255)).getRGB(), 5.5F);
        Fonts.SFBOLD.SFBOLD_15.SFBOLD_15.drawCenteredString(Math.round(scoef1) + "s", (float) (this.x + 20), (float) (this.y + 22), -1);
        return new Border(0.0F, 0.0F, this.Width, this.Height);
    }
}
