package me.Skid.ui.elements;

import java.awt.Color;
import java.util.Iterator;
import me.Skid.Tenacity.utils.render.RoundedUtil;
import me.Skid.novoline.impl.Fonts;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "KeyBinds"
)
public class KeyBinds extends Element {

    public final BoolValue onlyState = new BoolValue("OnlyModuleState", false);
    public final BoolValue BlurValue = new BoolValue("blur", false);
    public final ListValue shadowValue = new ListValue("Shadow", new String[] { "None", "Basic", "Thick"}, "None");
    Double renderX;
    Double renderY;

    public Border drawElement() {
        int y2 = 0;

        RoundedUtil.drawRound(0.0F, 0.0F, 84.0F, (float) (17 + this.getmoduley()), 0.0F, new Color(0, 0, 0, 100));
        String s = (String) this.shadowValue.get();
        byte module = -1;

        switch (s.hashCode()) {
        case 63955982:
            if (s.equals("Basic")) {
                module = 0;
            }
            break;

        case 80778109:
            if (s.equals("Thick")) {
                module = 1;
            }
        }

        switch (module) {
        case 0:
            RenderUtils.drawShadow(-0.5F, -0.5F, 85.0F, (float) (17 + this.getmoduley() + 1));
            break;

        case 1:
            RenderUtils.drawShadow(-0.5F, -0.5F, 85.0F, (float) (17 + this.getmoduley() + 1));
            RenderUtils.drawShadow(-0.5F, -0.5F, 85.0F, (float) (17 + this.getmoduley() + 1));
        }

        Fonts.CsgoIcon.csgoicon_20.csgoicon_20.drawString("a", 3.0F, 6.5F, -1, true);
        Fonts.SFBOLD.SFBOLD_20.SFBOLD_20.drawString("KeyBinds", 23.0F, 5.5F, -1, true);
        Iterator iterator = LiquidBounce.moduleManager.getModules().iterator();

        while (iterator.hasNext()) {
            Module module1 = (Module) iterator.next();

            if (module1.getKeyBind() != 0 && (!((Boolean) this.onlyState.get()).booleanValue() || module1.getState())) {
                Fonts.SFBOLD.SFBOLD_14.SFBOLD_14.drawString(module1.getName(), 3.0F, (float) y2 + 21.0F, -1, true);
                Fonts.SFBOLD.SFBOLD_14.SFBOLD_14.drawString("[Toggle]", (float) (78 - Fonts.Tahoma.Tahoma_14.Tahoma_14.stringWidth("[Toggle]")), (float) y2 + 21.0F, module1.getState() ? (new Color(255, 255, 255)).getRGB() : (new Color(100, 100, 100)).getRGB(), true);
                y2 += 12;
            }
        }

        if (((Boolean) this.BlurValue.get()).booleanValue()) {
            GL11.glTranslated(-this.renderX.doubleValue(), -this.renderY.doubleValue(), 0.0D);
            GL11.glPushMatrix();
            BlurBuffer.blurArea(0.0F, 0.0F, 84.0F, (float) (17 + this.getmoduley()));
            GL11.glPopMatrix();
            GL11.glTranslated(this.renderX.doubleValue(), this.renderY.doubleValue(), 0.0D);
        }

        return new Border(0.0F, 0.0F, 84.0F, (float) (17 + this.getmoduley()));
    }

    public int getmoduley() {
        int y = 0;
        Iterator iterator = LiquidBounce.moduleManager.getModules().iterator();

        while (iterator.hasNext()) {
            Module module = (Module) iterator.next();

            if (module.getKeyBind() != 0 && (!((Boolean) this.onlyState.get()).booleanValue() || module.getState())) {
                y += 12;
            }
        }

        return y;
    }
}
