package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "NewLogo",
    description = "idk..",
    category = ModuleCategory.COLOR
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/NewLogo;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "AlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "BlueValue", "Clientname", "Lnet/ccbluex/liquidbounce/value/TextValue;", "GreenValue", "RedValue", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "info", "rainbowIndex", "rainbowSpeed", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"}
)
public final class NewLogo extends Module {

    private final TextValue info = new TextValue("InFo", "Fix1");
    private final TextValue Clientname = new TextValue("Clientname", "TGSense");
    private final ListValue colorModeValue = new ListValue("Color", new String[] { "Custom", "GodLightSync", "novo", "rainbow", "skyrainbow", "anptherrainbow"}, "Custom");
    private final IntegerValue RedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue GreenValue = new IntegerValue("G", 255, 0, 255);
    private final IntegerValue BlueValue = new IntegerValue("B", 255, 0, 255);
    private final IntegerValue AlphaValue = new IntegerValue("A", 255, 0, 255);
    private final IntegerValue rainbowSpeed = new IntegerValue("RainbowSpeed", 10, 1, 10);
    private final IntegerValue rainbowIndex = new IntegerValue("RainbowIndex", 1, 1, 20);

    @EventTarget
    public final void onRender(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String s = (String) this.colorModeValue.get();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            int i;
            label34: {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case 3387450:
                    if (s.equals("novo")) {
                        i = ColorUtils.novoRainbow$default(ColorUtils.INSTANCE, 40, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null).getRGB();
                        break label34;
                    }
                    break;

                case 151913845:
                    if (s.equals("skyrainbow")) {
                        Color color = RenderUtils.skyRainbow(((Number) this.rainbowIndex.get()).intValue(), 1.0F, 1.0F);

                        Intrinsics.checkExpressionValueIsNotNull(color, "RenderUtils.skyRainbow(rainbowIndex.get(), 1F, 1F)");
                        i = color.getRGB();
                        break label34;
                    }
                    break;

                case 404830547:
                    if (s.equals("anotherrainbow")) {
                        i = ColorUtils.fade(new Color(((Number) this.RedValue.get()).intValue(), ((Number) this.GreenValue.get()).intValue(), ((Number) this.BlueValue.get()).intValue(), ((Number) this.AlphaValue.get()).intValue()), 100, ((Number) this.rainbowIndex.get()).intValue()).getRGB();
                        break label34;
                    }
                    break;

                case 973576630:
                    if (s.equals("rainbow")) {
                        i = ColorUtils.hslRainbow$default(ColorUtils.INSTANCE, ((Number) this.rainbowIndex.get()).intValue(), 0.0F, 0.0F, 100 * ((Number) this.rainbowSpeed.get()).intValue(), 0, 22, (Object) null).getRGB();
                        break label34;
                    }
                    break;

                case 1027608501:
                    if (s.equals("godlightsync")) {
                        i = ColorUtils.INSTANCE.GodLight(40).getRGB();
                        break label34;
                    }
                }

                i = (new Color(((Number) this.RedValue.get()).intValue(), ((Number) this.GreenValue.get()).intValue(), ((Number) this.BlueValue.get()).intValue(), 1)).getRGB();
            }

            int TextColor = i;

            Fonts.com96.drawStringWithShadow((String) this.Clientname.get(), 10, 15, TextColor);
            Fonts.com35.drawStringWithShadow("Release".toString(), Fonts.com96.getStringWidth("TGSense") + 13, 14, TextColor);
            Fonts.com35.drawStringWithShadow((String) this.info.get(), Fonts.com96.getStringWidth("TGSense") + 13, 24, TextColor);
        }
    }
}
