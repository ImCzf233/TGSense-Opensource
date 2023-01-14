package net.ccbluex.liquidbounce.features.module.modules.color;

import java.awt.Color;
import java.lang.reflect.Field;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(
    name = "ColorMixer",
    description = "Mix two colors together.",
    category = ModuleCategory.COLOR,
    canEnable = false
)
public class ColorMixer extends Module {

    private static float[] lastFraction = new float[0];
    public static Color[] lastColors = new Color[0];
    public final IntegerValue blendAmount = new IntegerValue("Mixer-Amount", 2, 2, 10) {
        protected void onChanged(Integer oldValue, Integer newValue) {
            ColorMixer.regenerateColors(oldValue != newValue);
        }
    };
    public final ColorElement col1RedValue;
    public final ColorElement col1GreenValue;
    public final ColorElement col1BlueValue;

    public ColorMixer() {
        this.col1RedValue = new ColorElement(1, ColorElement.Material.RED);
        this.col1GreenValue = new ColorElement(1, ColorElement.Material.GREEN);
        this.col1BlueValue = new ColorElement(1, ColorElement.Material.BLUE);
    }

    public static Color getMixedColor(int index, int seconds) {
        ColorMixer colMixer = (ColorMixer) LiquidBounce.moduleManager.getModule(ColorMixer.class);

        if (colMixer == null) {
            return Color.white;
        } else {
            if (ColorMixer.lastColors.length <= 0 || ColorMixer.lastFraction.length <= 0) {
                regenerateColors(true);
            }

            return BlendUtils.blendColors(ColorMixer.lastFraction, ColorMixer.lastColors, (float) ((System.currentTimeMillis() + (long) index) % (long) (seconds * 1000)) / (float) (seconds * 1000));
        }
    }

    public static void regenerateColors(boolean forceValue) {
        ColorMixer colMixer = (ColorMixer) LiquidBounce.moduleManager.getModule(ColorMixer.class);

        if (colMixer != null) {
            int i;

            if (forceValue || ColorMixer.lastColors.length <= 0 || ColorMixer.lastColors.length != ((Integer) colMixer.blendAmount.get()).intValue() * 2 - 1) {
                Color[] colorFraction = new Color[((Integer) colMixer.blendAmount.get()).intValue() * 2 - 1];

                for (i = 1; i <= ((Integer) colMixer.blendAmount.get()).intValue(); ++i) {
                    Color z = Color.white;

                    try {
                        Field e = ColorMixer.class.getField("col" + i + "RedValue");
                        Field green = ColorMixer.class.getField("col" + i + "GreenValue");
                        Field blue = ColorMixer.class.getField("col" + i + "BlueValue");
                        int r = ((Integer) ((ColorElement) e.get(colMixer)).get()).intValue();
                        int g = ((Integer) ((ColorElement) green.get(colMixer)).get()).intValue();
                        int b = ((Integer) ((ColorElement) blue.get(colMixer)).get()).intValue();

                        z = new Color(Math.max(0, Math.min(r, 255)), Math.max(0, Math.min(g, 255)), Math.max(0, Math.min(b, 255)));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    colorFraction[i - 1] = z;
                }

                i = ((Integer) colMixer.blendAmount.get()).intValue();

                for (int i = ((Integer) colMixer.blendAmount.get()).intValue() - 2; i >= 0; --i) {
                    colorFraction[i] = colorFraction[i];
                    ++i;
                }

                ColorMixer.lastColors = colorFraction;
            }

            if (forceValue || ColorMixer.lastFraction.length <= 0 || ColorMixer.lastFraction.length != ((Integer) colMixer.blendAmount.get()).intValue() * 2 - 1) {
                float[] afloat = new float[((Integer) colMixer.blendAmount.get()).intValue() * 2 - 1];

                for (i = 0; i <= ((Integer) colMixer.blendAmount.get()).intValue() * 2 - 2; ++i) {
                    afloat[i] = (float) i / (float) (((Integer) colMixer.blendAmount.get()).intValue() * 2 - 2);
                }

                ColorMixer.lastFraction = afloat;
            }

        }
    }
}
