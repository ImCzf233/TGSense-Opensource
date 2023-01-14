package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import me.Skid.Jello.Jello;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.LiquidBounceStyle;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.NullStyle;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(
    name = "ClickGUI",
    description = "Opens the ClickGUI.",
    category = ModuleCategory.RENDER,
    keyBind = 54,
    canEnable = false
)
public class ClickGUI extends Module {

    public static final ListValue mode = new ListValue("Mode", new String[] { "Jello", "LiquidBounce"}, "LiquidBounce");
    private final ListValue styleValue = new ListValue("Style", new String[] { "LiquidBounce", "Null", "Slowly"}, "Null") {
        protected void onChanged(String oldValue, String newValue) {
            ClickGUI.this.updateStyle();
        }
    };
    public final FloatValue scaleValue = new FloatValue("Scale", 1.0F, 0.7F, 2.0F);
    public final IntegerValue maxElementsValue = new IntegerValue("MaxElements", 15, 1, 20);
    private static final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private static final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    private static final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private static final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    public final ListValue backgroundValue = new ListValue("Background", new String[] { "Default", "None"}, "Default");
    public final ListValue animationValue = new ListValue("Animation", new String[] { "Azura", "Slide", "SlideBounce", "Zoom", "ZoomBounce", "None"}, "Azura");

    public static Color generateColor() {
        return ((Boolean) ClickGUI.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Integer) ClickGUI.colorRedValue.get()).intValue(), ((Integer) ClickGUI.colorGreenValue.get()).intValue(), ((Integer) ClickGUI.colorBlueValue.get()).intValue());
    }

    public void onEnable() {
        this.updateStyle();
        String s = (String) ClickGUI.mode.get();
        byte b0 = -1;

        switch (s.hashCode()) {
        case -1367768316:
            if (s.equals("LiquidBounce")) {
                b0 = 0;
            }
            break;

        case 71456692:
            if (s.equals("Jello")) {
                b0 = 1;
            }
        }

        switch (b0) {
        case 0:
            this.updateStyle();
            ClickGUI.mc.displayGuiScreen(ClickGUI.classProvider.wrapGuiScreen(LiquidBounce.clickGui));
            break;

        case 1:
            ClickGUI.mc.displayGuiScreen(ClickGUI.classProvider.wrapGuiScreen(new Jello()));
        }

    }

    private void updateStyle() {
        String s = ((String) this.styleValue.get()).toLowerCase();
        byte b0 = -1;

        switch (s.hashCode()) {
        case -899450034:
            if (s.equals("slowly")) {
                b0 = 2;
            }
            break;

        case -615955772:
            if (s.equals("liquidbounce")) {
                b0 = 0;
            }
            break;

        case 3392903:
            if (s.equals("null")) {
                b0 = 1;
            }
        }

        switch (b0) {
        case 0:
            LiquidBounce.clickGui.style = new LiquidBounceStyle();
            break;

        case 1:
            LiquidBounce.clickGui.style = new NullStyle();
            break;

        case 2:
            LiquidBounce.clickGui.style = new SlowlyStyle();
        }

    }

    @EventTarget(
        ignoreCondition = true
    )
    public void onPacket(PacketEvent event) {
        IPacket packet = event.getPacket();

        if (ClickGUI.classProvider.isSPacketCloseWindow(packet) && ClickGUI.classProvider.isClickGui(ClickGUI.mc.getCurrentScreen())) {
            event.cancelEvent();
        }

    }
}
