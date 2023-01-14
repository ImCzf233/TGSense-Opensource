package net.ccbluex.liquidbounce.features.module.modules.color;

import net.ccbluex.liquidbounce.value.IntegerValue;

public class ColorElement extends IntegerValue {

    public ColorElement(int counter, ColorElement.Material m, IntegerValue basis) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255, invoke<invokedynamic>(basis, counter));
    }

    public ColorElement(int counter, ColorElement.Material m) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255);
    }

    protected void onChanged(Integer oldValue, Integer newValue) {
        ColorMixer.regenerateColors(true);
    }

    private static Boolean lambda$new$0(IntegerValue basis, int counter) {
        return Boolean.valueOf(((Integer) basis.get()).intValue() >= counter);
    }

    static enum Material {

        RED("Red"), GREEN("Green"), BLUE("Blue");

        private final String colName;

        private Material(String name) {
            this.colName = name;
        }

        public String getColorName() {
            return this.colName;
        }
    }
}
